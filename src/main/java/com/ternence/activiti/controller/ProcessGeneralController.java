package com.ternence.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.interceptor.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ternence.activiti.utils.CommandFactory;

/**
 * 通用的流程相关的接口
 * 
 * @author Ternence
 */
@RequestMapping("/process")
@Controller
public class ProcessGeneralController extends AbstractSystemController {

	@Autowired
	private ProcessEngine processEngine;
	//日志记录
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 查看流程图的接口，这个接口不需要接口，直接将图片写入响应内容当中即可
	 * 
	 * @param type 		查看的流程图的类型
	 * @param processId 查看的流程图对应的流程id
	 * @param response 	响应对象
	 */
	@RequestMapping("/graph/{type}/{processId}")
	public void getProcessGraphById(@PathVariable("type") String type, @PathVariable("processId") String processId,
			HttpServletResponse response) {
		//参数就不做校验了，因为本身不会为null，如果查询的时候出现异常情况那么就处理异常就好了
		ManagementService managementService = processEngine.getManagementService();
		//会返回null
		Command<InputStream> command = CommandFactory.getCommand(type, processId);
		if (command == null) {
			//这个异常不用处理，这是为了确保程序不要这样写的一个技巧
			throw new IllegalArgumentException("命令对象不能为null");
		}
		//执行命令得到对应的输出
		InputStream in = managementService.executeCommand(command);
		OutputStream out = null;
		response.setContentType("image/png;image/jpeg");//查看图片而不是下载图片
		//写图片
		try {
			out = response.getOutputStream();
			if (in != null && in.available() > 0) {
				int len = 0;
				byte[] cache = new byte[1024];
				while ((len = in.read(cache)) > 0) {
					out.write(cache, 0, len);
				}
			}
		} catch (IOException e) {
			//Print the exception log then ignore this ex
			logger.error(e.getLocalizedMessage());
		} finally {
			try {
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				//Print the exception log then ignore this ex
				logger.error(e.getLocalizedMessage());
			}
		}

	}
}
