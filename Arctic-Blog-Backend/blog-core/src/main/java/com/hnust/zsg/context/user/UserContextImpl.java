package com.hnust.zsg.context.user;

import com.hnust.zsg.entity.vo.MyUserVO;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息的上下文信息
 */
public class UserContextImpl implements UserContext {
    private static final long serialVersionUID = 8383356012441014698L;
    // 用户类
    private MyUserVO myUserVO = new MyUserVO();
    // 变量
    private Map<Object, Object> properties = new HashMap<>();
    // 业务变量
    private Map<Object, Object> busiProperties = new HashMap<>();

    public UserContextImpl() {
    }

    @Override
    public MyUserVO getMyUserVO() {
        return this.myUserVO;
    }

    @Override
    public void setCurrentUserVO(MyUserVO myUserVO) {
        this.myUserVO = myUserVO;
    }

    @Override
    public Object getProperty(Object var) {
        return this.properties;
    }

    @Override
    public void addProperty(Object varKey, Object varValue) {
        this.properties.put(varKey, varValue);
    }

    @Override
    public void removeProperty(Object var) {
        this.properties.remove(var);
    }

    @Override
    public Map<Object, Object> getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Map<Object, Object> map) {
        this.properties = map;
    }

    @Override
    public void removeAllProperties() {
        this.properties.clear();
    }

    @Override
    public void addBusiProperty(Object busiVarKey, Object busiVarValue) {
        this.busiProperties.put(busiVarKey, busiVarValue);
    }

    @Override
    public Object getBusiProperty(Object busiVarKey) {
        return this.busiProperties.get(busiVarKey);
    }

    @Override
    public Map<Object, Object> getBusiProperties() {
        return this.busiProperties;
    }

    @Override
    public void setBusiProperties(Map<Object, Object> map) {
        this.busiProperties = map;
    }
}
