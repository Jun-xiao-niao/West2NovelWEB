package com.junxiaoniao.book.controller;

import com.junxiaoniao.book.mapper.NovelMapper;
import com.junxiaoniao.book.mapper.UserMapper;
import com.junxiaoniao.book.pojo.Json;
import com.junxiaoniao.book.pojo.Novel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api("面向管理员的小说功能")
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    NovelMapper novelMapper;
    @Autowired
    UserMapper userMapper;


    @ApiOperation("查询所有待审核小说")
    @RequestMapping(value = "/queryUploadingNovel", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> queryUpNovel() {
        List<Novel> list = novelMapper.queryUpNovel();
        return list;
    }

    @ApiOperation("管理员通过ID 审核小说和对应的图片")
    @GetMapping("/passNovel/{ID}")
    @ResponseBody
    public Object pass(@RequestParam("id") int id) {
        Novel novel = novelMapper.queryUploadingNovelByID(id);
        if (novel != null) {
            novelMapper.pass(novel);
            novelMapper.deleteUploadingNovelByID(novel.getNovelID());
            return new Json(200, "审核成功");
        } else {
            return new Json(500, "未找到图书");
        }
    }

    @ApiOperation("管理员通过ID删除审核不通过的小说和对应的图片")
    @GetMapping("/deleteNovel/{ID}")
    @ResponseBody
    public Object deleteNovel(@RequestParam("id") int id) {
        Novel novel = novelMapper.queryUploadingNovelByID(id);
        if (novel != null) {
            novelMapper.deleteUploadingNovelByID(novel.getNovelID());
            return new Json(200, "删除成功");
        } else {
            return new Json(200, "未找到小说");
        }
    }
}
