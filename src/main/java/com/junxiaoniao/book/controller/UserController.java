package com.junxiaoniao.book.controller;

import com.junxiaoniao.book.mapper.NovelMapper;
import com.junxiaoniao.book.mapper.UserMapper;
import com.junxiaoniao.book.pojo.Json;
import com.junxiaoniao.book.pojo.NovelCollection;
import com.junxiaoniao.book.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email) {
        User user = new User(username, password, email, "Normal User");
        User queryUser = userMapper.queryUerByName(username);
        if (queryUser != null) {
            return new Json(400, "注册失败，用户名以被占用");
        } else {
            userMapper.loginUser(user);
            return new Json(200, "注册成功");
        }
    }

    @ApiOperation("登录入口")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Object login(@RequestParam("username") String name,
                        @RequestParam("password") String pwd,
                        HttpServletResponse response,
                        HttpSession session) {
        User user = userMapper.queryUerByName(name);
        if (user != null) {
            if (pwd.equals(user.getPassword())) {
                String authority = user.getPrivilege();
                Cookie cookie = new Cookie("username", name);
                response.addCookie(cookie);
                session.setAttribute("username", name);
                if (authority.equals("admin")) {
                    return new Json(200, "管理员登录成功");
                } else {
                    return new Json(200, "用户登录成功");
                }
            } else {
                return new Json(500, "密码错误");
            }
        } else {
            return new Json(500, "用户不存在");
        }
    }

    @ApiOperation("判断是否登录")
    @ResponseBody
    @RequestMapping(value = "/isLogin", method = RequestMethod.GET)
    public Object isLogin(HttpServletRequest request) {
        Object logUser = request.getSession().getAttribute("username");
        if (logUser == null) {
            return new Json(500, "尚未登录");
        } else {
            return new Json(200, "已登录");
        }
    }

    @ApiOperation("查询用户收藏夹")
    @ResponseBody
    @RequestMapping(value = "/queryUserCollection", method = RequestMethod.GET)
    public List<NovelCollection> getCollection() {
        List<NovelCollection> collection = userMapper.queryUserCollection();
        return collection;
    }

}
