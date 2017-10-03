package com.ternence.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ternence.activiti.bean.ProcessDefinitionBean;

/**
 * 处理任务的接口，也就是待办任务的接口
 * 
 * @author Ternence
 */
@Controller
@RequestMapping("/process/assign")
public class ProcessAssignmentTaskController extends AbstractSystemController {

	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 查询代办任务列表
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
	public Object deleteWaitAssignProcessByDeploymentId(@PathVariable("deploymentId") String deploymentId) {

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

}
