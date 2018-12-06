package com.idea.permission.common;

import com.idea.permission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class HttpInterceptor  extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        log.info("request start. url:{}, params:{}", url, JsonMapper.objectToString(map));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        log.info("request finished. url:{}, params:{}", url, JsonMapper.objectToString(map));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        String url = request.getRequestURL().toString();
        Map map = request.getParameterMap();
        log.info("request completed. url:{}, params:{}", url, JsonMapper.objectToString(map));
    }
}
