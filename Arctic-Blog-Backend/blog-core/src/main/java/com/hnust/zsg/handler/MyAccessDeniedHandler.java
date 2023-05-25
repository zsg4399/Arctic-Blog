package com.hnust.zsg.handler;

import com.hnust.zsg.enumeration.ResultCodeType;
import com.hnust.zsg.utils.JacksonUtil;
import com.hnust.zsg.utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
       accessDeniedException.printStackTrace();
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(JacksonUtil.toJsonString(Result.set(ResultCodeType.LOGIN_ACL)));
    }
}
