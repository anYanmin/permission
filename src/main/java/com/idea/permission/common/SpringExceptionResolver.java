package com.idea.permission.common;

import com.idea.permission.exception.ParamException;
import com.idea.permission.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 由springMVC管理的全局异常处理类
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        String defaultMsg = "System error";
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        if (url.endsWith(".json")) {
            if ( e instanceof PermissionException || e instanceof ParamException) {
                JsonData result = JsonData.fail(e.getMessage());
                //ModelAndView对象的viewName=jsonView根据springMVC-servlet.xml中MappingJackson2JsonView定义的bean id来确定
                mv = new ModelAndView("jsonView", result.toMap());
            } else {
                log.error("unknown json exception, url=" + url, e);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        } else if (url.endsWith(".page")){
            log.error("unknown page exception, url=" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());
        } else {
            log.error("unknown exception, url=" + url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
