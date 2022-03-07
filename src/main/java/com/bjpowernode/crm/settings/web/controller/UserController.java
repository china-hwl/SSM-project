package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入用户控制器");
        String path = req.getServletPath();
        System.out.println(path);
        if ("/settings/user/login.do".equals(path)){
            System.out.println("进入验证登录操作");
            String loginAct = req.getParameter("loginAct");
            String loginPwd = req.getParameter("loginPwd");
            loginPwd = MD5Util.getMD5(loginPwd);
            String ip = req.getRemoteAddr();
            UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
            try {
                User user = us.login(loginAct,loginPwd,ip);
                req.getSession().setAttribute("user",user);
                PrintJson.printJsonFlag(resp,true);
            } catch (LoginException e) {
                e.printStackTrace();
                Map<String,Object> map = new HashMap<>();
                map.put("success",false);
                map.put("msg",e.getMessage());
                PrintJson.printJsonObj(resp,map);
            }

        }else if("/settings/user/xxx.do".equals(path)){

        }
    }
}
