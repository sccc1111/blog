package com.example.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.blog.model.Logger;

@Mapper
public interface LoggerDao {
	
	int save(Logger log);
}
