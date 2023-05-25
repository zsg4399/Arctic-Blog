package com.hnust.zsg.controller;

import com.hnust.zsg.annotation.RateLimit;
import com.hnust.zsg.context.user.UserContextHolder;
import com.hnust.zsg.entity.dto.AuthorInfoDTO;
import com.hnust.zsg.entity.vo.*;
import com.hnust.zsg.enumeration.LimitType;
import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.service.UserService;
import com.hnust.zsg.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JavaMailSender sender;


    @RateLimit(type = LimitType.IP, count = 1)
    @PostMapping("/createcode")
    public Result createVerificationCode(@RequestBody MyUserVO myUserVO) {
        String username = myUserVO.getUsername();
        username = username == null ? "" : username.trim();
        String email = myUserVO.getEmail();
        email = email == null ? "" : email.trim();

        //校验邮箱号和用户名
        userService.USERNAME_EMAIL_VERIFICATION(username, email);
        //生成6位随机数作为验证码
        String verification_code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));

        Map<String, String> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        RedisUtil.putAll(verification_code, user);
        //设置验证码十分钟过期时间
        RedisUtil.expire(verification_code, 10L, TimeUnit.MINUTES);

        //发送邮件
        SimpleMailMessage message = EmailUtil.messageFactory(verification_code, email);
        sender.send(message);

        return Result.ok();


    }

    @PostMapping("/register")
    public Result<String> userRegister(@RequestBody MyUserVO myUserVO) {
        String verification_code = myUserVO.getVerifyCode();
        verification_code = verification_code == null ? "" : verification_code.trim();
        String username = myUserVO.getUsername();
        username = username == null ? "" : username.trim();
        String password = myUserVO.getPassword();
        password = password == null ? "" : password.trim();
        String myEmail = myUserVO.getEmail();
        myEmail = myEmail == null ? "" : myEmail.trim();


        //查询redis库中是否存在验证码key
        if (RedisUtil.hasKey(verification_code)) {
            String email = (String) (RedisUtil.hget(verification_code, "email"));
            //邮箱账号必须前后一致,因为在构建验证码的时候存储的邮箱号一定是通过了校验的，所以这里只需判断二者内容是否相等即可
            if (email.equals(myEmail)) {
                //存储用户名和BCrypt算法加密后的密码以及邮箱
                userService.RegisterUser(username, password, email, encoder);
                //设置验证码过期
                RedisUtil.expire(verification_code, -1L);
                return Result.ok();

            }
        }

        return Result.fail("验证码错误");
    }

    @PostMapping("/login")
    public void userLogin(@RequestBody LoginUserVO user) {
    }

    @GetMapping("/getAvatar")
    public Result getAvatarInfo() {
        MyUserVO userVO = UserContextHolder.getContext().getMyUserVO();
        Long id = userVO.getId();
        AvatarVO avatarVO = userService.getAvatarVO(id);
        return Result.ok(avatarVO);
    }


    @PostMapping("/loginout")
    public void loginou(@RequestHeader("Authorization") String token) {
        RedisUtil.expire(token, -1L);
    }

    @GetMapping("/userinfo")
    public Result getUserInfo() {
        MyUserVO myUserVO = UserContextHolder.getContext().getMyUserVO();
        Long userId = myUserVO.getId();
        String key = new StringBuilder(RedisUtil.LOGIN_USERINFO).append(userId).toString();
        String value = (String) RedisUtil.get(key);
        UserInfoVO userInfoVO;
        if (value == null) {
            userInfoVO = userService.getBasicUserinfoById(userId);
            RedisUtil.set(key, JacksonUtil.toJsonString(userInfoVO), 1L, TimeUnit.HOURS);
        } else {
            userInfoVO = JacksonUtil.parseObject(value, UserInfoVO.class);
        }
        return Result.ok(userInfoVO);
    }

    /**
     * 根据用户ID修改对应field的value
     * @param editInfo
     * @return
     * @throws NoSuchFieldException
     */
    @PostMapping("/userinfo")
    public Result<String> modfiyUserinfo(@RequestBody EditInfo editInfo) throws NoSuchFieldException {
        Long userId = UserContextHolder.getContext().getMyUserVO().getId();
        if (userService.updateUserinfo(userId, editInfo.getField(), editInfo.getValue()) > 0) {
            return Result.ok("success");
        }
        return Result.fail();
    }

    @GetMapping("/AuthorInfo")
    public Result getAuthorInfo(){
        Object authorInfoDTO= RedisUtil.get(RedisUtil.AUTHOR_INFO);
        return Result.ok(authorInfoDTO);
    }
}
