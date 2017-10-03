package com.ternence.activiti.utils;

import java.io.InputStream;

import org.activiti.engine.impl.cmd.GetDeploymentProcessDiagramCmd;
import org.activiti.engine.impl.interceptor.Command;
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
	 * 一个简单的工厂方法
	 * @param processType 流程类型---流程定义，流程实例，待办任务,不能为null
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
				//TODO 完成task模块对应的graph命令
				break;
			}
		}
		//else directly return null;
		return command;
	}

}
