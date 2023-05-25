package com.hnust.zsg.context.user;

/**
 * 管理存储用户信息的上下文
 */
public class UserContextHolder {


    private static final ThreadLocal<UserContext> contextHolder = new ThreadLocal<>();

    public UserContextHolder() {
    }
    public static void setContext(UserContext userContext){
        contextHolder.set(userContext);
    }
    public static UserContext getContext(){
        UserContext userContext=contextHolder.get();
        if(userContext==null){
            userContext=new UserContextImpl();
            setContext(userContext);
        }
        return userContext;
    }
}