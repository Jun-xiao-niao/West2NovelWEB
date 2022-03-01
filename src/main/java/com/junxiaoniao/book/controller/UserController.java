package com.junxiaoniao.book.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.junxiaoniao.book.mapper.NovelMapper;
import com.junxiaoniao.book.mapper.UserMapper;
import com.junxiaoniao.book.pojo.Json;
import com.junxiaoniao.book.pojo.NovelCollection;
import com.junxiaoniao.book.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
@Api("用户信息")
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    NovelMapper novelMapper;

    @ApiOperation("注册入口")
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object register(@RequestParam("username") String name, @RequestParam("password") String pwd, @RequestParam("email") String em) {
        User user = new User(name, pwd, em, "Normal User");
        User findUser = userMapper.queryUerByName(name);
        if (findUser != null) {
            return JSON.toJSONString(new Json(-1, "注册失败，用户名以被占用"));
        } else {
            userMapper.loginUser(user);
            return JSON.toJSONString(new Json(1, "注册成功"));
        }
    }

    @ApiOperation("登录入口")
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(@RequestParam("username") String name, @RequestParam("password") String pwd, HttpServletResponse response, HttpSession session) {

        User user = userMapper.queryUerByName(name);

        if (user != null) {
            if (pwd.equals(user.getPassword())) {
                String authority = user.getPrivilege();
                Cookie cookie = new Cookie("username", name);
                response.addCookie(cookie);
                session.setAttribute("username", name);
                if (authority.equals("admin")) {
                    return JSON.toJSONString(new Json(1, "管理员登录成功"));
                } else {
                    return JSON.toJSONString(new Json(0, "用户登录成功"));
                }
            } else {
                return JSON.toJSONString(new Json(-1, "密码错误"));
            }
        } else {
            return JSON.toJSONString(new Json(-1, "用户不存在"));
        }
    }

    @ApiOperation("判断是否登录")
    @ResponseBody
    @RequestMapping(value = "/isLogin", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object isLogin(HttpServletResponse response, HttpServletRequest request) {
        Object logUser = request.getSession().getAttribute("username");
        if (logUser == null) {
            return JSON.toJSONString(new Json(-1, "尚未登录"));
        } else {
            return JSON.toJSONString(new Json(1, "已登录"));
        }
    }

    @ApiOperation("查询用户收藏夹")
    @ResponseBody
    @RequestMapping(value = "/queryUserCollection", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<NovelCollection> getCollection() {
        List<NovelCollection> collection = userMapper.queryUserCollection();
        return collection;
    }

}