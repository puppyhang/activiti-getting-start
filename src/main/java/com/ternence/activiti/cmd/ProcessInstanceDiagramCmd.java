package com.ternence.activiti.cmd;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

/**
 * 使用Activiti的命令模式查看流程实例对应的图片，绘制带有节点高亮的图片
 * 
 * @author Ternence
 *
 */
public class ProcessInstanceDiagramCmd implements Command<InputStream> {
	protected String processInstanceId;

	public ProcessInstanceDiagramCmd(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public InputStream execute(CommandContext commandContext) {
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		ExecutionEntity executionEntity = executionEntityManager.findExecutionById(processInstanceId);
		List<String> activiityIds = executionEntity.findActiveActivityIds();
		String processDefinitionId = executionEntity.getProcessDefinitionId();
		GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(processDefinitionId);
		BpmnModel bpmnModel = getBpmnModelCmd.execute(commandContext);
		DefaultProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
		//生成图片
		InputStream is = processDiagramGenerator.generateDiagram(
				bpmnModel, "png", activiityIds, Collections.<String>emptyList(), "宋体", "宋体",
				getClass().getClassLoader(), 1.0
		);
		return is;
	}
}
