package com.hnust.zsg.convert;

import com.hnust.zsg.entity.po.UserPO;
import com.hnust.zsg.entity.vo.MyUserVO;
import com.hnust.zsg.entity.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE= Mappers.getMapper(UserConvert.class);

    MyUserVO PO_TO_VO(UserPO userPO);

    @Mapping(source = "address",target = "address",qualifiedByName = "StringToStringArray")
    UserInfoVO PO_TO_INFOVO(UserPO userPO);

    @Named("StringToStringArray")
    static String[] StringToStringArray(String address){
        if(address!=null){
            return address.split(",");
        }
        return null;
    }
}
