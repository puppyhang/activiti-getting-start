package com.ternence.activiti.bean;

import java.io.Serializable;

/**
 * 统一的请求响应数据对应的bean
 * 
 * @author Ternence
 * 
 */
public class UniformResponseBodyBean<T extends Serializable> {
	//请求状态
	private Integer status;
	//请求消息
	private String message;
	//请求返回的数据
	private T data;

	public UniformResponseBodyBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "UniformResponseBodyBean [status=" + status + ", message=" + message + ", data=" + data + "]";
	}

}
