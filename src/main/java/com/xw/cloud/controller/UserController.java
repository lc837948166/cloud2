package com.xw.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
@Api(tags = "用户管理",value = "用户管理", description = "用户控制器，用于处理用户登录、注销等操作")
@Controller
public class UserController {


    @ApiOperation(value = "跳转登录页面", notes = "导航到登录页面")
    @RequestMapping(value = {"/"})
    public String toLogin() {
        return "login";
    }

    @ApiOperation(value = "用户登录", notes = "处理用户登录请求")
    @RequestMapping(value = {"/login"})
    public String login(@RequestParam(value = "username", required = true) String userName,
                        @RequestParam(value = "password", required = true) String password,
                        Model model,
                        HttpSession session) {
        if ("admin".equals(userName) && "admin".equals(password)) {
            session.setAttribute("loginUser", userName);//UserName存入Session
            return "redirect:index";
        }
        model.addAttribute("msg", "userName or password error!");
        return "login";
    }

    @ApiOperation(value = "用户注销", notes = "处理用户注销请求")
    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        if (session.getAttribute("loginUser") != null) {
            session.removeAttribute("loginUser");          //移除Session 转到登陆界面
        }
        return "redirect:/";
    }

}
