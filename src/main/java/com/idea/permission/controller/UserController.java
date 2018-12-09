package com.idea.permission.controller;

import com.idea.permission.model.SysUser;
import com.idea.permission.service.SysUserService;
import com.idea.permission.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/loginout.page")
    public void loginout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect("signin.jsp");
    }

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SysUser sysUser = sysUserService.findUserByKeyword(username);
        String errMsg = "";
        String ret = request.getParameter("ret");
        if (StringUtils.isBlank(username)) {
            errMsg = "用户名不能为空";
        } else if (StringUtils.isBlank(password)) {
            errMsg = "密码不能为空";
        } else if (sysUser == null) {
            errMsg = "找不到指定的用户";
        } else if (!MD5Util.md5(password).equals(sysUser.getPassword())) {
            errMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errMsg = "该账户无法使用,请联系管理员";
        } else {
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");
                return;
            }
        }
        request.setAttribute("error", errMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }

}
