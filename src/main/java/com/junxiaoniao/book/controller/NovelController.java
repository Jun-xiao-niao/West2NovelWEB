package com.junxiaoniao.book.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
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
@RequestMapping("/file")
@Slf4j

public class NovelController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    NovelMapper novelMapper;

    @ApiOperation("模糊搜索")
    @PostMapping("/searchNovel/{name}")
    @ResponseBody
    public List<Novel> findNovel(@RequestParam("novelName") String novelName){
        List<Novel> list=novelMapper.fuzzyQueryNovelByName(novelName);
        return list;
    }

    @ApiOperation("用户收藏此小说")
    @RequestMapping(value = "/Collection/{name}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object collect(@PathVariable("name") String name) {
        Novel novel = novelMapper.queryNovelByName(name);
        //用户"kamigama"添加小说到收藏夹
        userMapper.collect(novel);
        //该书的收藏量加1
        novel.setCollection(novel.getCollection() + 1);
        novelMapper.collect(novel);
        return "{code:200,msg:收藏成功}";
    }

    @ApiOperation("获取随机推荐小说")
    @RequestMapping(value = "/recommended", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Novel> getCommend() {
        List<Novel> list = new ArrayList<>();
        list.add(novelMapper.queryRandomNovel());
        return list;
    }

    @ApiOperation("查询收藏前10的小说")
    @RequestMapping(value = "/findRank", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Novel> queryRankList() {
        List<Novel> list = novelMapper.queryNovelList();
        Collections.sort(list);
        List<Novel> finalList = new ArrayList<>();
        for (int i = list.size() - 1; i > list.size() - 10; i--) {
            finalList.add(list.get(i));
        }
        return finalList;
    }

    @ApiOperation("查询用户收藏夹")
    @ResponseBody
    @RequestMapping(value = "/queryUserCollection", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<NovelCollection> getCollection() {
        List<NovelCollection> collection = userMapper.queryUserCollection();
        return collection;
    }

    @Value("E:/fileUpload/file")
    private String uploadFilePath;

    @Value("E:/fileUpload/image")
    private String ImagePath;

    @ApiOperation("上传txt文件")
    @ResponseBody
    @PostMapping("/uploadFile")
    public Object fileUpload(@RequestParam("file") MultipartFile file , @RequestParam("novelName") String novelName,@RequestParam("writer") String writer, @RequestParam("introduction") String introduction, @RequestParam("type") String type) throws IOException {
        JSONObject result=new JSONObject();
        if (file.isEmpty()){
            result.put("error","空文件");
            return result;
        }
        String fileName = file.getOriginalFilename();//得到文件名
        String suffixName=fileName.substring(fileName.lastIndexOf("."));//得到后缀名
        String realFileName=fileName.substring(0,fileName.lastIndexOf(suffixName));//得到不包含后缀的文件名
        String fileUrl = uploadFilePath+"/"+fileName;
        File tempfile=new File(fileUrl);
        if (!tempfile.getParentFile().exists()) {
            tempfile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(tempfile);
        }catch (Exception e){
            log.error("发生错误: {}",e);
            result.put("error",e.getMessage());
            return result;

        }
        UploadingNovel novel=new UploadingNovel(0,writer,novelName,type,introduction,fileUrl);
        novelMapper.uploadingNovel(novel);
        result.put("success","文件上传审核成功");
        return result;
    }

    @ApiOperation("上传图片文件")
    @ResponseBody
    @PostMapping("/uploadImage")
    public Object ImageUpload(@RequestParam("file") MultipartFile file,@RequestParam("name") MultipartFile name ) throws IOException {
        JSONObject result=new JSONObject();
        if (file.isEmpty()){
            result.put("error","空文件");
            return result;
        }
        String fileName = file.getOriginalFilename();//得到文件名
        String suffixName=fileName.substring(fileName.lastIndexOf("."));//得到后缀名
        String realFileName=fileName.substring(0,fileName.lastIndexOf(suffixName));//得到不包含后缀的文件名
        String fileUrl = ImagePath+"/"+fileName;
        File tempfile=new File(fileUrl);
        if (!tempfile.getParentFile().exists()) {
            tempfile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(tempfile);
        }catch (Exception e){
            log.error("发生错误: {}",e);
            result.put("error",e.getMessage());
            return result;

        }
        UploadingPicture picture=new UploadingPicture(0,realFileName,fileUrl);
        novelMapper.uploadingPicture(picture);
        result.put("success","图片上传审核成功");
        return result;
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

    @ApiOperation("查找所有图书")
    @RequestMapping(value = "/findAllnovel", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Novel> findAll(){
        List<Novel> list=novelMapper.queryNovelList();
        return list;
    }
}
