package com.hnust.zsg.convert;

import com.hnust.zsg.entity.po.UserPO;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.entity.vo.UserInfoVO;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-23T23:01:36+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_121 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public MyUserVO PO_TO_VO(UserPO userPO) {
        if ( userPO == null ) {
            return null;
        }

        MyUserVO myUserVO = new MyUserVO();

        myUserVO.setId( userPO.getId() );
        myUserVO.setUsername( userPO.getUsername() );
        myUserVO.setPassword( userPO.getPassword() );
        myUserVO.setEmail( userPO.getEmail() );
        myUserVO.setEnabled( userPO.getEnabled() );

        return myUserVO;
    }

    @Override
    public UserInfoVO PO_TO_INFOVO(UserPO userPO) {
        if ( userPO == null ) {
            return null;
        }

        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setAddress( UserConvert.StringToStringArray( userPO.getAddress() ) );
        userInfoVO.setId( userPO.getId() );
        userInfoVO.setUsername( userPO.getUsername() );
        userInfoVO.setEnabled( userPO.getEnabled() );
        userInfoVO.setAvatar( userPO.getAvatar() );
        if ( userPO.getBirthday() != null ) {
            userInfoVO.setBirthday( DateTimeFormatter.ISO_LOCAL_DATE.format( userPO.getBirthday() ) );
        }
        userInfoVO.setDescription( userPO.getDescription() );
        userInfoVO.setSex( userPO.getSex() );

        return userInfoVO;
    }
}
