package com.example.blog.tools;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5 {
	private static int SIZE = 1024;
	public static String get(String key,String salt) {
	    //shiro md5 加密
	    Object hash= new SimpleHash("MD5", key, salt, SIZE);
        return hash.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(get("123456","e95f97b5"));
	}
}
