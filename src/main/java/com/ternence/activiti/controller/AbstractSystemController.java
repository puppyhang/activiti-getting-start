package com.ternence.activiti.controller;

import java.io.Serializable;

import com.ternence.activiti.bean.UniformResponseBodyBean;

public class AbstractSystemController {

	public <T extends Serializable> Object renderingUniformResp(int status, String message, T data) {
		UniformResponseBodyBean<T> uniformResponseBodyBean = new UniformResponseBodyBean<>();
		uniformResponseBodyBean.setStatus(status);
		uniformResponseBodyBean.setMessage(message);
		uniformResponseBodyBean.setData(data);
		return uniformResponseBodyBean;
	}

	public <T extends Serializable> Object renderingFaildResp(String message) {

		return renderingUniformResp(400, message, null);
	}

	public <T extends Serializable> Object renderingSuccessResp(T data) {

		return renderingUniformResp(200, "请求成功", data);
	}
}
