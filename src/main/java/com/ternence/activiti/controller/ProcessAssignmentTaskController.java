package com.ternence.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ternence.activiti.bean.TaskBean;

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
	 * 查询待办任务列表的接口
	 * 
	 * @return 待办任务列表
	 */
	@ResponseBody
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public Object queryAssignTaskList() {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery query = taskService.createTaskQuery();
		List<Task> tasks = query.list();
		List<TaskBean> resultBeans = new ArrayList<>();
		if (tasks != null) {
			for (Task task : tasks) {
				TaskBean bean = new TaskBean();
				bean.setId(task.getId());
				bean.setName(task.getName());
				bean.setAssignee(task.getAssignee());
				resultBeans.add(bean);
			}
		}
		return renderingSuccessResp(resultBeans);
	}

	/**
	 * 根据任务的id，完成当前的任务
	 * @param taskId 任务ID
	 * @return 完成状况
	 */
	@ResponseBody
	@RequestMapping("/complete/{taskId}")
	public Object completeTaskById(@PathVariable("taskId") String taskId) {
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//pass to external method parameter
		Map<String, Object> params = new HashMap<>();
		params.put("superior", "superior");
		try {
			taskService.complete(taskId, params);
		} catch (ActivitiObjectNotFoundException e) {
			e.printStackTrace();
			return renderingFaildResp("没有这条任务" + taskId);
		}
		TaskBean bean = new TaskBean();
		bean.setId(taskId);
		bean.setAssignee(task.getAssignee());
		bean.setName(task.getName());
		return renderingSuccessResp("任务完成", bean);
	}

}
