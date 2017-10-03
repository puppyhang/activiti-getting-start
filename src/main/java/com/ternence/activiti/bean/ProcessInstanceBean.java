package com.ternence.activiti.bean;

public class ProcessInstanceBean {
	
	private String id;
	
	private String processDefinition;

	public ProcessInstanceBean() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(String processDefinition) {
		this.processDefinition = processDefinition;
	}

	@Override
	public String toString() {
		return "ProcessInstanceBean [id=" + id + ", processDefinition=" + processDefinition + "]";
	}
	
	
}
