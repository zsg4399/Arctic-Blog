package com.hnust.zsg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hnust.zsg.Excption.validation.AccountDisabledException;
import com.hnust.zsg.Excption.validation.EmailillegalException;
import com.hnust.zsg.Excption.validation.UsernameillegalException;
import com.hnust.zsg.convert.UserConvert;
import com.hnust.zsg.entity.dto.AuthorInfoDTO;
import com.hnust.zsg.entity.po.ArticlePO;
import com.hnust.zsg.entity.po.CategoryPO;
import com.hnust.zsg.entity.po.TagPO;
import com.hnust.zsg.entity.po.UserPO;
import com.hnust.zsg.entity.vo.AvatarVO;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.entity.vo.UserInfoVO;
import com.hnust.zsg.entity.vo.UserVO;
import com.hnust.zsg.mapper.ArticleMapper;
import com.hnust.zsg.mapper.CategoryMapper;
import com.hnust.zsg.mapper.TagMapper;
import com.hnust.zsg.mapper.UserMapper;
import com.hnust.zsg.service.UserService;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.RedisUtil;
import com.hnust.zsg.utils.ValidataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("UserServiceimpl")
public class UserServiceimpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 判断邮箱是否已被其他账号绑定,及其格式是否正确
     *
     * @param email
     */
    @Override
    public void EMAIL_VERIFICATION(String email) {
        ValidataUtil.ValidEmail(email);
        if (userMapper.haveEmail(email) != null) {
            String message = new StringBuilder("邮箱号:").append(email).append("已被占用,请选择其他邮箱号进行注册").toString();
            throw new EmailillegalException(message);
        }

    }

    /**
     * 判断用户名是否已被占用，及其格式是否正确
     *
     * @param username
     */
    @Override
    public void USERNAME_VERIFICATION(String username) {
        ValidataUtil.ValidUserName(username);
        if (userMapper.haveUsername(username) != null) {
            String message = new StringBuilder("用户名:").append(username).append("已被占用,请选择其他用户名进行注册").toString();
            throw new UsernameillegalException(message);
        }
    }

    /**
     * 判断用户名和密码格式是否
     *
     * @param username
     * @param email
     */
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Override
    public void USERNAME_EMAIL_VERIFICATION(String username, String email) {
        EMAIL_VERIFICATION(email);
        USERNAME_VERIFICATION(username);
    }

    /**
     * 注册用户
     *
     * @param username
     * @param password
     * @param email
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Override
    public int RegisterUser(String username, String password, String email, PasswordEncoder encoder) {
        //判断用户名和邮箱号是否被占用，以及用户名邮箱号和头像格式是否正确
        USERNAME_VERIFICATION(username);
        EMAIL_VERIFICATION(email);
        ValidataUtil.ValidPassword(password);

        UserPO userPO = new UserPO();
        userPO.setUsername(username);
        userPO.setPassword(encoder.encode(password));
        userPO.setEmail(email);
        userPO.setSex("男");
        userPO.setEnabled(true);
        userPO.setAvatar("defaultAvatar.png");


        return userMapper.registerUser(userPO);
    }

    /**
     * 根据用户Id修改用户信息
     *
     * @param userId
     * @param field
     * @param value
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 5, isolation = Isolation.READ_COMMITTED)
    public int updateUserinfo(Long userId, String field, String value) throws NoSuchFieldException {
        field = field == null ? "" : field.trim();
        value = value == null ? "" : value.trim();
        int result = 0;
        switch (field) {
            case "username":
                ValidataUtil.ValidUserName(value);
                result = userMapper.updateUsernameById(userId, value);
                break;
            case "birthday":
                LocalDate birthday = ValidataUtil.ValidBirthday(value);
                result = userMapper.updateBirthdayById(userId, birthday);
                break;
            case "address":
                userMapper.updateAddressById(userId, value);
                break;
            case "sex":
                ValidataUtil.ValidSex(value);
                result = userMapper.updateSexById(userId, value);
                break;
            case "description":
                result = userMapper.updateDescriptionById(userId, ValidataUtil.ValidDescription(value));
                break;
            default:
                throw new NoSuchFieldException("非法修改用户信息");
        }
        String key = new StringBuilder(RedisUtil.LOGIN_USERINFO).append(userId).toString();
        RedisUtil.expire(key, -1L);
        return result;

    }

    /**
     * 修改默认数据库读取信息的方式
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPO userPO = null;
        if (username.contains("@")) {
            userPO = userMapper.getUserByEmail(username);
        } else {
            userPO = userMapper.getUserByUsername(username);
        }
        if (userPO == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (!userPO.getEnabled()) {
            throw new AccountDisabledException("账号已被禁用");
        }
        List<String> roles = userMapper.getRolesByUserId(userPO.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }


        MyUserVO user = UserConvert.INSTANCE.PO_TO_VO(userPO);
        user.setAuthorities(authorities);
        return user;
    }


    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Override
    public UserInfoVO getBasicUserinfoById(Long id) {
        UserPO userPO = userMapper.getBasicUserinfoById(id);
        userPO.setId(id);
        String description = userPO.getDescription();
        description = description.isEmpty() ? null : description;
        String address = userPO.getAddress();
        address = address.isEmpty() ? null : address;
        String phone = userPO.getPhone();
        phone = phone.isEmpty() ? null : phone;
        userPO.setDescription(description);
        userPO.setAddress(address);
        userPO.setPhone(phone);
        return UserConvert.INSTANCE.PO_TO_INFOVO(userPO);
    }

    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Override
    public AvatarVO getAvatarVO(Long id) {
        String key = new StringBuilder(RedisUtil.LOGIN_USERINFO).append(id).toString();
        String value = (String) RedisUtil.get(key);
        AvatarVO avatarVO = new AvatarVO();
        UserInfoVO userInfoVO;
        if (StringUtils.hasText(value)) {
            userInfoVO = JacksonUtil.parseObject(value, UserInfoVO.class);
            avatarVO.setAvatar(userInfoVO.getAvatar());
            avatarVO.setUsername(userInfoVO.getUsername());
        } else {
            UserVO userVO = userMapper.getUserById(id);
            avatarVO.setUsername(userVO.getUsername());
            avatarVO.setAvatar(userVO.getAvatar());
        }
        avatarVO.setFans(0L);
        avatarVO.setLikes(0L);
        avatarVO.setLikes(0L);
        return avatarVO;
    }

    @Transactional(rollbackFor = RuntimeException.class, timeout = 10, isolation = Isolation.READ_COMMITTED)
    @Override
    public void setAuthorInfoToRedis() {
        LambdaQueryWrapper<ArticlePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticlePO::getAuthorId, 1L);
        Integer articles = articleMapper.selectCount(queryWrapper);

        LambdaQueryWrapper<TagPO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(TagPO::getAuthorId, 1L);
        Integer tags = tagMapper.selectCount(queryWrapper1);

        LambdaQueryWrapper<CategoryPO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(CategoryPO::getUserId, 1L);
        Integer categorys = categoryMapper.selectCount(queryWrapper2);

        AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO(0, (categorys), (articles), tags);
        RedisUtil.set(RedisUtil.AUTHOR_INFO, authorInfoDTO, 1L, TimeUnit.DAYS);
    }
}
