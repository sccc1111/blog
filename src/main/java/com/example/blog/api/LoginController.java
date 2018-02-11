package com.example.blog.api;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dao.UserDao;
import com.example.blog.dto.ResultJson;
import com.mysql.jdbc.StringUtils;
@RestController
public class LoginController {
	
	 @Resource
	 UserDao userDao;
	 
	 @RequestMapping("/api/login")
	 public ResultJson login(@Param("user")String user,@Param("password")String password){
		 if(StringUtils.isNullOrEmpty(user)){
			 return new ResultJson("300","账号不能为空");
		 }
		 if(StringUtils.isNullOrEmpty(password)){
			 return new ResultJson("300","密码不能为空");
		 }
		 Subject subject = SecurityUtils.getSubject(); 
	     UsernamePasswordToken token = new UsernamePasswordToken(user, password);  
	     try {  
	        subject.login(token);  
	     } catch (IncorrectCredentialsException e) {  
	    	 return new ResultJson("300","密码错误");
	     } catch (LockedAccountException e) {  
	    	 return new ResultJson("300","登录失败，该用户已被冻结");
	     } catch (AuthenticationException e) {  
	    	 return new ResultJson("300","该用户不存在");
	     } catch (Exception e) {  
	        e.printStackTrace();  
	     }
		 return new ResultJson("200","登录成功",subject.getSession().getId());
	 }
	 
	 @RequestMapping("/unauth")
	 public ResultJson unauth(){
		 return new ResultJson("300","未登录");
	 }
	 
	 @RequestMapping("/hello")
	 public ResultJson hello(){
		 return new ResultJson("200","hello");
	 }
}
