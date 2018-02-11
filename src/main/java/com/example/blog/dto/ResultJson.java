package com.example.blog.dto;

public class ResultJson {

	public String code;
	public String msg;
	public Object obj;

	public ResultJson(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ResultJson(String code, String msg, Object obj) {
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}
}
