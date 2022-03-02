package com.junxiaoniao.book.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.junxiaoniao.book.mapper.NovelMapper;
import com.junxiaoniao.book.mapper.UserMapper;
import com.junxiaoniao.book.pojo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Api("面向用户的小说功能")
@RequestMapping("/novel")
@Slf4j

public class NovelController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    NovelMapper novelMapper;
    @Value("F:/novelUploading/novel")
    private String novelUpPath;
    @Value("F:/novelUploading/picture")
    private String pictureUpPath;

    @ApiOperation("模糊搜索")
    @PostMapping("/searchNovel/{name}")
    @ResponseBody
    public List<Novel> findNovel(@RequestParam("novelName") String novelName){
        List<Novel> list=novelMapper.fuzzyQueryNovelByName(novelName);
        return list;
    }

    @ApiOperation("用户收藏此小说")
    @RequestMapping(value = "/Collection/{name}", method = RequestMethod.POST)
    @ResponseBody
    public Object collect(@PathVariable("name") String name) {
        Novel novel = novelMapper.queryNovelByName(name);
        //用户"kamigama"添加小说到收藏夹
        userMapper.collect(novel);
        //该书的收藏量加1
        novel.setCollection(novel.getCollection() + 1);
        novelMapper.collect(novel);
        return new Json(200,"收藏成功");
    }

    @ApiOperation("查询所有小说")
    @RequestMapping(value = "/findAllnovel", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> findAll(){
        List<Novel> list=novelMapper.queryNovelList();
        return list;
    }

    @ApiOperation("获取随机推荐小说")
    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> getCommend() {
        List<Novel> list = new ArrayList<>();
        list.add(novelMapper.queryRandomNovel());
        return list;
    }

    @ApiOperation("查询收藏前10的小说")
    @RequestMapping(value = "/findRank", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> queryRankList() {
        List<Novel> list = novelMapper.queryNovelList();
        Collections.sort(list, (o1, o2) -> {
            //按收藏数量逆序排序(从大到小)
            return o2.getCollection() - o1.getCollection();
        });
        List<Novel> finalList = new ArrayList<>();
        for (int i = 0;i < 10; i++) {
            finalList.add(list.get(i));
        }
        return finalList;
    }

    @ApiOperation("上传txt小说文件 和 jpg/png图片文件")
    @ResponseBody
    @PostMapping("/uploadingNovel")
    public Object fileUploading(@RequestParam("novelFile") MultipartFile novelFile ,
                                @RequestParam("pictureFile") MultipartFile pictureFile ,
                                @RequestParam("novelName") String novelName,
                                @RequestParam("writer") String writer,
                                @RequestParam("introduction") String introduction,
                                @RequestParam("type") String type) {

        String originalFilename = novelFile.getOriginalFilename();//小说文件名，可以和小说名不一样
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String fileType = originalFilename.substring(lastIndexOf + 1);//判断小说文件类型

        String originalFilename2 = pictureFile.getOriginalFilename();//图片名
        int lastIndexOf2 = originalFilename.lastIndexOf(".");
        String fileType2 = originalFilename.substring(lastIndexOf2 + 1);//判断图片文件类型

        if (fileType.equals("txt")||fileType2.equals("jpg") || fileType2.equals("png")) { //只允许txt文本上传 只允许jpg和png图片上传
            String fileUrl = novelUpPath + "/" + originalFilename; //小说URL
            String fileUrl2 = pictureUpPath + "/" + originalFilename2; //图片URL
            File newFile = new File(fileUrl);
            File newFile2 = new File(fileUrl2);
            try {
                novelFile.transferTo(newFile);
                pictureFile.transferTo(newFile2);
            } catch (IOException e) {
                log.error("发生错误: {}", e);
                return new Json(500, e.getMessage());
            }
            Novel novel = new Novel(0, writer, novelName, type, introduction,0, fileUrl,fileUrl2);
            novelMapper.uploadingNovel(novel); //存审核数据库
            return new Json(200, "成功上传，等待审核中");
        } else {
            return new Json(500, "请上传正确的文件格式：上传txt小说文件 和 jpg/png图片文件");
        }
    }

    @ApiOperation("下载小说")
    @ResponseBody
    @GetMapping("/downloadFile")
    public Object fileDownload(HttpServletResponse response, @RequestParam("name") String fileName) throws IOException {
        JSONObject result=new JSONObject();
        Novel noveldownload = novelMapper.queryNovelByName(fileName);
        File file = new File(noveldownload.getFileURL());
        String downloadName=file.getName();
        if (!file.exists()){
            result.put("error","文件不存在");
            return result.toString();

        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition","attachment;filename="+java.net.URLEncoder.encode(downloadName, "UTF-8"));

        byte[] readBytes= FileUtil.readBytes(file);
        OutputStream os=response.getOutputStream();
        os.write(readBytes);
        result.put("success","下载成功");
        return result;
    }
}
