package com.example.blog.config;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.example.blog.dao.UserDao;
import com.example.blog.model.User;

public class MyShiroRealm extends AuthorizingRealm{

	 @Resource
	 UserDao userDao;
	 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");
		String account = (String) arg0.getPrincipal();
		User u = userDao.findByName(account);
		 if (u == null) {  
	            return null;  
	     }
		 SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(  
	                u, //用户名  
	                u.getPassword(), //密码  
	                ByteSource.Util.bytes(u.getSalt()),
	                getName()  //realm name  
	     );  
		return authenticationInfo;
	}

}
