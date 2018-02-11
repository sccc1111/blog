package com.example.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.example.blog.model.User;

@Mapper
public interface UserDao {
	List<User> findAll();
	
	@Select("Select * FROM SYS_USER WHERE name=#{user} or phone=#{user} or email=#{user}")
	@ResultMap("userResultMap")
	User findByName(String user);
}
