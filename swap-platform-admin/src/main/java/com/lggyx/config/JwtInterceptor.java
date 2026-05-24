package com.lggyx.config;

import com.lggyx.context.BaseContext;
import com.lggyx.enumeration.RoleCode;
import com.lggyx.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return true; // 放行，由接口内部处理未登录情况
        }

        token = token.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return true; // token 无效，放行
        }

        String account = jwtUtil.getAccountFromToken(token);
        BaseContext.setCurrentAccount(account);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.removeCurrentAccount();
    }
}
