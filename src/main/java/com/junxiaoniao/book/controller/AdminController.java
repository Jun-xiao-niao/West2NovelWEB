package com.junxiaoniao.book.controller;

import com.alibaba.fastjson.JSON;
import com.junxiaoniao.book.mapper.NovelMapper;
import com.junxiaoniao.book.mapper.UserMapper;
import com.junxiaoniao.book.pojo.Json;
import com.junxiaoniao.book.pojo.Novel;
import com.junxiaoniao.book.pojo.UploadingNovel;
import com.junxiaoniao.book.pojo.UploadingPicture;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api("面向管理员的小说功能")
@RequestMapping("/file")
@Slf4j
public class AdminController {

    @Autowired
    NovelMapper novelMapper;
    @Autowired
    UserMapper userMapper;


    @ApiOperation("查看所有待审核小说")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<UploadingNovel> findAllCh() {
        List<UploadingNovel> list = novelMapper.findUpnovel();
        return list;
    }

    @ApiOperation("查找所有待审核图书")
    @RequestMapping(value = "/findAllUploading", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<UploadingPicture> findAll() {
        List<UploadingPicture> list = novelMapper.findUpPicture();
        return list;
    }

    @ApiOperation("管理员通过ID审核小说和对应的图片")
    @GetMapping("/checkNovel/{ID}")
    @ResponseBody
    public Object successChecked(@RequestParam("id") int id) {
        UploadingNovel novel = novelMapper.findUploadingNovelByID(id);
        UploadingPicture picture = novelMapper.findUploadingPictureByID(id);
        if (novel != null && picture != null) {
            Novel finalNovel = new Novel(novel.getNovelID(),novel.getWriterName(),novel.getNovelName(),novel.getNovelTag(),novel.getBrief(),0,novel.getFileURL(),picture.getPictureURL());
            novelMapper.pass(finalNovel);
            novelMapper.deleteUploadingNovelByID(novel.getNovelID());
            novelMapper.deleteUploadingPictureByID(picture.getPictureID());
            return JSON.toJSONString(new Json(1, "审核成功"));
        } else {
            return JSON.toJSONString(new Json(-1, "未找到图书"));
        }
    }

    @ApiOperation("管理员通过ID删除审核不通过的小说和对应的图片")
    @GetMapping("/deleteNovel/{ID}")
    @ResponseBody
    public Object deletenovel(@RequestParam("id") int id) {
        UploadingNovel novel = novelMapper.findUploadingNovelByID(id);
        UploadingPicture picture = novelMapper.findUploadingPictureByID(id);
        if (novel != null && picture != null) {
            novelMapper.deleteUploadingNovelByID(novel.getNovelID());
            novelMapper.deleteUploadingPictureByID(picture.getPictureID());
            return JSON.toJSONString(new Json(1, "删除成功"));
        } else {
            return JSON.toJSONString(new Json(-1, "未找到图书"));
        }
    }
}
