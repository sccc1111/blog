package com.example.blog.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.blog.dao.LoggerDao;
import com.example.blog.model.Logger;
import com.example.blog.tools.LoggerUtils;

public class LoggerInterceptor implements HandlerInterceptor {
	
	 //请求开始时间标识
    private static final String LOGGER_SEND_TIME = "start_time";
    //请求日志实体标识
    private static final String LOGGER_ENTITY = "logger_model";
    
    
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		//获取请求错误码
        int status = response.getStatus();
        //当前时间
        long currentTime = System.currentTimeMillis();
        //请求开始时间
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
        //获取本次请求日志实体
        Logger logger = (Logger) request.getAttribute(LOGGER_ENTITY);
        //设置返回时间
        logger.setReturnTime(currentTime + "");
        //设置返回错误码
        logger.setStatusCode(status+"");
        //设置返回值
        logger.setReturnData(JSON.toJSONString(request.getAttribute("logger_return"),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue));
        LoggerDao loggerDao= getDAO(LoggerDao.class,request);
        //执行将日志写入数据库
        loggerDao.save(logger);
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		Logger logger = new Logger();
		//获取请求参数信息
		String paramData = JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue);
		logger.setParamData(paramData);
		//ip
		logger.setIp(LoggerUtils.getCliectIp(request));
        //设置请求方法
        logger.setMethod(request.getMethod());
        //设置请求类型（json|普通请求）
        logger.setType(LoggerUtils.getRequestType(request));
        //设置请求参数内容json字符串
        logger.setParamData(paramData);
        //设置请求地址
        logger.setUrl(request.getRequestURI());
        //设置sessionId
        logger.setSessionId(request.getRequestedSessionId());
        //设置请求开始时间
        request.setAttribute(LOGGER_SEND_TIME,System.currentTimeMillis());
        //设置请求实体到request内，方面afterCompletion方法调用
        request.setAttribute(LOGGER_ENTITY,logger);
		return true;
	}
	 /**
     * 根据传入的类型获取spring管理的对应dao
     * @param clazz 类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    private <T> T getDAO(Class<T> clazz,HttpServletRequest request)
    {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
