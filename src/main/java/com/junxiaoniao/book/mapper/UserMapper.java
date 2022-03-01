package com.junxiaoniao.book.mapper;


import com.junxiaoniao.book.pojo.Novel;
import com.junxiaoniao.book.pojo.NovelCollection;
import com.junxiaoniao.book.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    //查询所有用户
    List<User> queryUserList();

    //根据名字查询指定用户
    User queryUerByName(String name);

    //注册用户
    void loginUser(User user);

    //用户添加收藏
    void collect(Novel novel);

    //获取用户收藏
    List<NovelCollection> queryUserCollection();



};
