package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入市场活动控制器");
        String path = req.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(req,resp);
        }else if("/workbench/activity/save.do".equals(path)){
            save(req,resp);
        }
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)req.getSession().getAttribute("user")).getName();
        Activity act = new Activity();
        act.setId(id);
        act.setOwner(owner);
        act.setName(name);
        act.setStartDate(startDate);
        act.setEndDate(endDate);
        act.setCost(cost);
        act.setDescription(description);
        act.setCreateBy(createBy);
        act.setCreateTime(createTime);
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag =  activityService.save(act);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("取得用户信息");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> ulist = us.getUserList();
        PrintJson.printJsonObj(resp,ulist);
    }
}
