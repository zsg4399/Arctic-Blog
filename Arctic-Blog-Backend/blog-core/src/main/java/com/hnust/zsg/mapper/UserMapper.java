package com.hnust.zsg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnust.zsg.entity.dto.AuthorInfoDTO;
import com.hnust.zsg.entity.po.UserPO;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.entity.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
    @Transactional
    String haveUsername(@Param("username") String username);

    @Transactional
    String haveEmail(@Param("email") String email);


    @Transactional
    UserPO getUserByUsername(@Param("username") String username);

    @Transactional
    UserPO getUserByEmail(@Param("email") String email);

    @Transactional
    String getUsernameById(@Param("id") Long id);

    @Transactional
    List<String> getRolesByUserId(@Param("userId") Long id);

    UserVO getUserById(@Param("id") Long id);

    int registerUser(UserPO userPO);

    UserPO getBasicUserinfoById(@Param("id") Long id);

    int updateUsernameById(@Param("id") Long id, @Param("username") String username);

    int updateAddressById(@Param("id") Long id, @Param("address") String address);

    int updateBirthdayById(@Param("id") Long id, @Param("birthday") LocalDate birthday);

    int updateSexById(@Param("id") Long id, @Param("sex") String sex);

    int updateDescriptionById(@Param("id") Long id, @Param("description") String descritpion);

}
