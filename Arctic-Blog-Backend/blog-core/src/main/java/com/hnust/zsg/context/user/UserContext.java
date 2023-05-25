package com.hnust.zsg.context.user;

import com.hnust.zsg.entity.vo.MyUserVO;

import java.io.Serializable;
import java.util.Map;

public interface UserContext extends Serializable {
    MyUserVO getMyUserVO();
    // 对用户进行赋值的方法
    void setCurrentUserVO(MyUserVO myUserVO);

    // 获取变量
    Object getProperty(Object var);

    // 添加变量
    void addProperty(Object varKey, Object varValue);

    // 移出变量
    void removeProperty(Object var);

    // 获取所有变量
    Map<Object, Object> getProperties();

    // 初始化变量
    void setProperties(Map<Object, Object> map);

    // 移出所有变量
    void removeAllProperties();

    // 添加业务变量
    void addBusiProperty(Object busiVarKey, Object busiVarValue);

    // 获取业务变量
    Object getBusiProperty(Object busiVarKey);

    // 获取所有业务变量
    Map<Object, Object> getBusiProperties();

    // 初始化业务变量
    void setBusiProperties(Map<Object, Object> map);


}
