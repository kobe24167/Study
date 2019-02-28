package com.aa.ms3.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.aa.ms3.model.User;

@Mapper
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    @Select("select * from user where id = #{id}")
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}