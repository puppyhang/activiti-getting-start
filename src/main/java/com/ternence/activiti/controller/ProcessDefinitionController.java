package com.ternence.activiti.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ternence.activiti.bean.ProcessDefinitionBean;
import com.ternence.activiti.utils.FileUtilsExtension;

/**
 * 流程定义的相关接口
 * 
 * @author Ternence
 */
@Controller
@RequestMapping("/process/def")
public class ProcessDefinitionController extends AbstractSystemController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 流程部署,这里包括文件上传和流程部署
	 * 上传的流程定义文件存储在部署目录下的WEB-RES下面
	 * 
	 * @param file 上传的文件信息
	 * @return 返回部署信息，包含是否成功等
	 */
	@ResponseBody
	@RequestMapping("/deploy")
	public Object deployProcess(@RequestPart(value = "file", required = false) MultipartFile multipart,
			HttpServletRequest request) {
		try {
			//对于这种情况，尽管FileUtilsExtension.saveUploadFile已经进行了判断
			//，但controller中还是需要有一个不同的处理方式，给前前端一个友好的提示
			if (multipart == null || multipart.getInputStream().available() == 0) {
				
				return renderingFaildResp("请选择一个有效的流程定义文件");
			}

			File targetFile = FileUtilsExtension.saveUploadFile(multipart, request);
			if (targetFile == null) {
				logger.info("流程文件上传失败");
				return renderingFaildResp("so sorry ! 流程文件上传失败,请稍后重试");
			}
			String contextPath = request.getSession().getServletContext().getContextPath();
			ProcessDefinitionBean process = new ProcessDefinitionBean();
			process.setPath(contextPath + "/res/process/" + targetFile.getName());
			//部署流程文件

			return renderingUniformResp(200, "流程部署成功", process);
		} catch (IOException e) {
			e.printStackTrace();
			return renderingFaildResp("上传文件失败");
		}
	}
}
