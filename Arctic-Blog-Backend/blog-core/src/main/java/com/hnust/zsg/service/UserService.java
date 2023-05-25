package com.hnust.zsg.service;

import com.hnust.zsg.entity.vo.AvatarVO;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.entity.vo.UserInfoVO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface UserService extends UserDetailsService {
    void EMAIL_VERIFICATION(String email);

    void USERNAME_VERIFICATION(String username);

    void USERNAME_EMAIL_VERIFICATION(String username, String email);

    int RegisterUser(String username, String password, String email, PasswordEncoder encoder);

    int updateUserinfo(Long userId,String field,String value) throws NoSuchFieldException;

    UserInfoVO getBasicUserinfoById(Long id);

    AvatarVO getAvatarVO(Long id);

    void setAuthorInfoToRedis();
}
