package com.junxiaoniao.book.mapper;

import com.junxiaoniao.book.pojo.Novel;
import com.junxiaoniao.book.pojo.UploadingNovel;
import com.junxiaoniao.book.pojo.UploadingPicture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NovelMapper {
    //查询所有小说
    List<Novel> queryNovelList();

    //根据小说名查询书
    Novel queryNovelByName(String novelName);

    //用户模糊查询小说
    List<Novel> fuzzyQueryNovelByName(String novelName);

    //随机获取一本书（随机获取数据库一列）
    Novel queryRandomNovel();

    //小说收藏量加1
    void collect(Novel novel);

    //上传小说
    void uploadingNovel(UploadingNovel uploadingNovel);

    //上传图片
    void uploadingPicture(UploadingPicture uploadingPicture);

    //查询所有待审核的小说
    List<UploadingNovel> findUpnovel();

    //查询所有待审核的图片
    List<UploadingPicture> findUpPicture();

    //通过ID查询待审核的小说
    UploadingNovel findUploadingNovelByID(int novelID);

    //通过ID查询待审核的图片
    UploadingPicture findUploadingPictureByID(int pictureID);

    //通过待审核的小说和图片
    void pass(Novel novel);

    //通过ID删除待审核的小说
    void deleteUploadingNovelByID(int ID);

    //通过ID删除待审核的图片
    void deleteUploadingPictureByID(int ID);

}
