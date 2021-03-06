package com.junxiaoniao.book.mapper;

import com.junxiaoniao.book.pojo.Novel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NovelMapper {

    //查询所有小说
    List<Novel> queryNovelList();

    //根据小说名查询小说
    Novel queryNovelByName(String novelName);

    //根据小说ID查询小说
    Novel queryNovelByID(int novelID);

    //根据收藏数降序 查询小说
    List<Novel> queryNovelCollection();

    //用户模糊查询小说
    List<Novel> fuzzyQueryNovelByName(String novelName);

    //小说收藏量加1
    void collect(Novel novel);

    //上传小说和图片
    void uploadingNovel(Novel novel);

    //查询所有待审核的小说
    List<Novel> queryUpNovel();

    //通过ID查询待审核的小说
    Novel queryUploadingNovelByID(int novelID);

    //通过待审核的小说和图片
    void pass(Novel novel);

    //通过ID删除待审核的小说
    void deleteUploadingNovelByID(int ID);

}
