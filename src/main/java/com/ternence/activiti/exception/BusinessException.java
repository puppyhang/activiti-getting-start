package com.ternence.activiti.exception;

/**
 * create by Ternence at 2017年10月3日 下午8:40:40
 * @description 运行时处理业务发生的异常
 * @version 1.0
 */
public class BusinessException extends RuntimeException {

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}
	
}
