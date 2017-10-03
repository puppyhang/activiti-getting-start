package com.ternence.activiti.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ternence.activiti.bean.UniformResponseBodyBean;
import com.ternence.activiti.exception.BusinessException;

public class AbstractSystemController {

	public <T> UniformResponseBodyBean<T> renderingUniformResp(int status, String message, T data) {
		UniformResponseBodyBean<T> uniformResponseBodyBean = new UniformResponseBodyBean<>();
		uniformResponseBodyBean.setStatus(status);
		uniformResponseBodyBean.setMessage(message);
		uniformResponseBodyBean.setData(data);
		return uniformResponseBodyBean;
	}

	public <T> UniformResponseBodyBean<T> renderingFaildResp(String message) {

		return renderingUniformResp(400, message, null);
	}

	public <T> UniformResponseBodyBean<T> renderingSuccessResp(T data) {

		return renderingUniformResp(200, "请求成功", data);
	}

	public <T> UniformResponseBodyBean<T> renderingSuccessResp(String message, T data) {

		return renderingUniformResp(200, message, data);
	}

	@ResponseBody
	@ExceptionHandler(value = { BusinessException.class })
	protected Object exceptionHandler(Exception ex) {
		//简单返回一个消息就好了
		return renderingFaildResp(ex.getLocalizedMessage());

	}
}
