package com.ternence.activiti.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ternence.activiti.bean.ProcessDefinitionBean;
import com.ternence.activiti.bean.ProcessInstanceBean;
import com.ternence.activiti.bean.SimpleProcessDefinitionBean;
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

	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 流程部署,这里包括文件上传和流程部署
	 * 上传的流程定义文件存储在部署目录下的WEB-RES下面
	 * 
	 * @param file 上传的文件信息
	 * @return 返回部署信息，包含是否成功等
	 * @formatter:off
	 */
	@ResponseBody
	@RequestMapping(path="/deploy",method=RequestMethod.POST)
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
			SimpleProcessDefinitionBean process = new SimpleProcessDefinitionBean();
			process.setPath(contextPath + "/res/process/" + targetFile.getName());
			//得到Activiti的服务
			RepositoryService repositoryService = processEngine.getRepositoryService();
			//部署流程文件
			Deployment deployment = null;
			try {
				deployment = repositoryService.createDeployment()
						.addInputStream("process.bpmn20.xml", new FileInputStream(targetFile))
						.name("项目审批流程")
						.deploy();
			}catch (XMLException e) {
				
				e.printStackTrace();
				
				return renderingFaildResp("请选择一个有效的XML流程定义文件");
			}
			
			process.setId(deployment.getId());
			process.setName(deployment.getName());
			return renderingUniformResp(200, "流程部署成功", process);
		} catch (IOException e) {
			e.printStackTrace();
			return renderingFaildResp("上传文件失败");
		}catch(Exception e) {
			e.printStackTrace();
			return renderingFaildResp("流程部署失败，请稍后重试");
		}
	}
	
	/**
	 * 查询部署好的流程定义列表数据
	 * 
	 * @return 所有的流程列表
	 */
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object queryWaitAssignProcessList() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinitionQuery processDefQuery = repositoryService.createProcessDefinitionQuery();
		//不能直接序列还这个List，里面引用的对象太多，还是需要转换一下
		List<ProcessDefinition> definitions = processDefQuery.list();
		List<ProcessDefinitionBean> definitionBeans = new ArrayList<>();
		if (definitions != null) {
			for (ProcessDefinition process : definitions) {
				ProcessDefinitionBean bean = new ProcessDefinitionBean();
				bean.setName(process.getName());
				bean.setId(process.getId());
				bean.setDeploymentId(process.getDeploymentId());
				definitionBeans.add(bean);
			}
		}
		return renderingSuccessResp(definitionBeans);
	}

	/**
	 * 根据流程部署ID删除对应的流程部署记录
	 * 
	 * @param deploymentId 流程部署ID
	 * @return  删除结果
	 */
	@ResponseBody
	@RequestMapping("/delete/{deploymentId}")
	public Object deleteProcessByDeploymentId(@PathVariable("deploymentId") String deploymentId) {

		if (StringUtils.trimWhitespace(deploymentId).isEmpty()) {

			return renderingFaildResp("必须包含有效的流程部署ID");
		}
		try {

			RepositoryService repositoryService = processEngine.getRepositoryService();
			//默认使用级联删除
			repositoryService.deleteDeployment(deploymentId, true);
			Map<String, String> data = new HashMap<String, String>();
			data.put("deploymentId", deploymentId);
			return renderingSuccessResp("删除成功", data);
		} catch (Exception e) {
			e.printStackTrace();//打印堆栈信息

			return renderingFaildResp(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * 根据流程定义ID启动流程实例，之后会产生一个流程实例记录
	 * 
	 * 流程定义就是模板，可以依据这个模板启动一条又一条的流程实例
	 * 
	 * @return 启动结果
	 * @param processDefinitionId 流程定义ID，而不是流程部署ID
	 */
	@ResponseBody
	@RequestMapping(path="/start/{deploymentId}",method=RequestMethod.GET)
	public Object startProcessInstanceById(@PathVariable("deploymentId") String processDefinitionId) {
		RuntimeService runtimeService= processEngine.getRuntimeService();
		try {
			Map<String,Object> params = new HashMap<>();
			//指定分配人
			params.put("assignee", "Lingo");
			//指定参与者
			params.put("participants", "user1,user2");
			//启动流程实例
			ProcessInstance instance=runtimeService.startProcessInstanceById(processDefinitionId,params);
			ProcessInstanceBean instanceBean = new ProcessInstanceBean();
			instanceBean.setId(instance.getId());
			instanceBean.setProcessDefinition(instance.getProcessDefinitionId());
			
			return renderingSuccessResp("流程启动成功",instanceBean);
		}catch (ActivitiObjectNotFoundException e) {
			e.printStackTrace();
			return renderingFaildResp("流程实例启动失败"+e.getLocalizedMessage());
		}
		
	}
	
	
	
	
	
}
