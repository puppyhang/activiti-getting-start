package com.ternence.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ternence.activiti.bean.ProcessInstanceBean;

/**
 * 流程实例管理接口
 * 
 * @author Ternence
 */
@Controller
@RequestMapping("/process/ins")
public class ProcessInstanceController extends AbstractSystemController {

	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 查询流程实例列表数据,GET请求方法
	 * 
	 * @return 流程实例列表
	 */
	@ResponseBody
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public Object queryProcessInstanceList() {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		//流程实例查询器
		ProcessInstanceQuery instanceQuery = runtimeService.createProcessInstanceQuery();
		//流程实例列表
		List<ProcessInstance> instances = instanceQuery.list();
		//构造返回的数据
		List<ProcessInstanceBean> data = new ArrayList<>();
		for (ProcessInstance instance : instances) {
			ProcessInstanceBean bean = new ProcessInstanceBean();
			bean.setId(instance.getId());
			bean.setProcessDefinition(instance.getProcessDefinitionId());
			data.add(bean);
		}
		return renderingSuccessResp(data);
	}

}
