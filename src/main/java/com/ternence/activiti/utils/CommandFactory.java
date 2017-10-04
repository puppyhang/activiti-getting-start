package com.ternence.activiti.utils;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDiagramCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.task.Task;
import org.springframework.util.StringUtils;

import com.ternence.activiti.cmd.ProcessInstanceDiagramCmd;

/**
 * 创建Activiti的命令对象的工厂
 * 
 * @author Ternence
 *
 */
public class CommandFactory {
	/**
	 * 一个简单的获取Command对象工厂方法
	 * 
	 * @param processType 流程类型---流程定义，流程实例，待办任务,不能为null
	 * @param processId 流程id---流程定义id，流程实例id
	 * @return 各种类型对应的{@link Command}对象
	 */
	public static Command<InputStream> getCommand(String processType, String processId) {
		Command<InputStream> command = null;
		if (StringUtils.hasText(processType) && StringUtils.hasText(processId)) {
			switch (processType) {
			case "def":
				String processDefinitionId = processId;
				command = new GetDeploymentProcessDiagramCmd(processDefinitionId);
				break;
			case "ins":
				String processInstanceId = processId;
				command = new ProcessInstanceDiagramCmd(processInstanceId);
				break;
			case "task":
				String taskId = processId;
				command = new ProcessInstanceDiagramCmd(taskId);
				break;
			}
		}
		//else directly return null;
		return command;
	}

	/**
	 * @see #getCommand(String, String)
	 * 
	 * @param engine 流程引擎对象
	 * @param processType
	 * @param processId 
	 * @return 各种类型对应的{@link Command}对象
	 */
	public static Command<InputStream> getCommand(ProcessEngine engine, String processType, String processId) {
		//对任务列表的图片查看做一点转换
		if (StringUtils.hasLength(processType) && "task".equals(processType)) {
			if (engine != null) {
				Task task = engine.getTaskService().createTaskQuery().taskId(processId).singleResult();
				//其实是查看流程实例的图片
				processId = task.getProcessInstanceId();
			}
		}
		return getCommand(processType, processId);
	}

}
