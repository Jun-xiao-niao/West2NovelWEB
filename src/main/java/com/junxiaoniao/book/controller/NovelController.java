package com.junxiaoniao.book.controller;

import cn.hutool.core.io.FileUtil;
import com.junxiaoniao.book.mapper.NovelMapper;
import com.junxiaoniao.book.mapper.UserMapper;
import com.junxiaoniao.book.pojo.Json;
import com.junxiaoniao.book.pojo.Novel;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@Api("面向用户的小说功能")
@RequestMapping("/novel")
@Slf4j

public class NovelController {


    @Autowired
    UserMapper userMapper;
    @Autowired
    NovelMapper novelMapper;
    //后端服务器的资源地址
    @Value("F:/novelUploading/novel/")
    private String novelUpPath;
    @Value("F:/novelUploading/picture/")
    private String pictureUpPath;
    //前端访问图片资源地址
    @Value("http://v4934114w1.wocp.fun/picture/")
    private String pictureUsePath;


    @ApiOperation("模糊搜索")
    @RequestMapping(value = "/searchNovel/{name}", method = RequestMethod.POST)
    @ResponseBody
    public List<Novel> findNovel(@RequestParam("novelName") String novelName) {
        List<Novel> list = novelMapper.fuzzyQueryNovelByName("%" +novelName+ "%"); //通配符
        return list;
    }

    @ApiOperation("用户收藏此小说")
    @RequestMapping(value = "/Collect/{name}", method = RequestMethod.POST)
    @ResponseBody
    public Object collect(@PathVariable("name") String name) {
        Novel novel = novelMapper.queryNovelByName(name);
        //用户"kamigama"添加小说到收藏夹
        userMapper.collect(novel);
        //该书的收藏量加1
        novel.setCollection(novel.getCollection() + 1);
        novelMapper.collect(novel);
        return new Json(200, "收藏成功");
    }

    @ApiOperation("查询所有小说")
    @RequestMapping(value = "/queryAllnovel", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> queryAll() {
        List<Novel> list = novelMapper.queryNovelList();
        return list;
    }

    @ApiOperation("通过ID查询小说")
    @RequestMapping(value = "/queryNovelID", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> queryNovelByID(@RequestParam("novelID") int novelID) {
        List<Novel> list = new ArrayList<>();
        list.add(novelMapper.queryNovelByID(novelID));
        return list;
    }

    @ApiOperation("获取随机推荐小说")
    @RequestMapping(value = "/recommendedNovel", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> queryRandomNovel() {
        List<Novel> list = novelMapper.queryNovelList();
        int size = 4;  //获取4个不重复随机数
        int num = 20; //书本数
        Random rd = new Random();
        List<Integer> lst = new ArrayList<>();//存放有序数字集合
        for (int i = 0; i < num; i++) {
            lst.add(i);
        }
        int index;//随机索引
        List<Novel> fanalList = new ArrayList<>();//存放随机数字
        for (int i = 0; i < size; i++) {
            index = rd.nextInt(num - i);
            fanalList.add(list.get(lst.get(index)));
            lst.remove(index);
        }
        return fanalList;
    }

    @ApiOperation("查询收藏前10的小说")
    @RequestMapping(value = "/queryRank", method = RequestMethod.GET)
    @ResponseBody
    public List<Novel> queryRankList() {
        List<Novel> list = novelMapper.queryNovelCollection();
        List<Novel> finalList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            finalList.add(list.get(i));
        }
        return finalList;
    }

    @ApiOperation("上传txt小说文件 和 jpg/png图片文件")
    @ResponseBody
    @PostMapping("/uploadingNovel")
    public Object fileUploading(@RequestParam("novelFile") MultipartFile novelFile,
                                @RequestParam("pictureFile") MultipartFile pictureFile,
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

        if (fileType.equals("txt") || fileType2.equals("jpg") || fileType2.equals("png")) { //只允许txt文本上传 只允许jpg和png图片上传
            String fileUrl = novelUpPath + originalFilename; //小说URL
            String fileUrl2 = pictureUpPath + originalFilename2; //图片URL
            File newFile = new File(fileUrl);
            File newFile2 = new File(fileUrl2);
            try {
                novelFile.transferTo(newFile);
                pictureFile.transferTo(newFile2);
            } catch (IOException e) {
                log.error("发生错误: {}", e);
                return new Json(500, e.getMessage());
            }
            String downPictureURL = pictureUsePath + originalFilename2;
            Novel novel = new Novel(0, writer, novelName, type, introduction, 0, fileUrl, downPictureURL);
            novelMapper.uploadingNovel(novel); //存审核数据库
            return new Json(200, "成功上传，等待审核中");
        } else {
            return new Json(500, "请上传正确的文件格式：上传txt小说文件 和 jpg/png图片文件");
        }
    }
    //https://blog.csdn.net/weixin_43100896/article/details/89880596
    @ApiOperation("下载小说txt文件")
    @ResponseBody
    @GetMapping("/downloadNovel")
    public Object fileDownload(HttpServletResponse response, @RequestParam("name") String novelName) throws IOException {
        Novel noveldownload = novelMapper.queryNovelByName(novelName);
        File file = new File(noveldownload.getFileURL());
        String downloadName = file.getName();
        if (!file.exists()) {
            return new Json(500, "文件不存在");
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadName, "UTF-8"));//通过设置响应头控制浏览器以UTF-8的编码显示数据
        byte[] readBytes = FileUtil.readBytes(file); //将文件转换成字节数组
        OutputStream os = response.getOutputStream();
        os.write(readBytes); //使用OutputStream流向客户端输出字节数组
        return new Json(200, "下载成功");
    }
}
