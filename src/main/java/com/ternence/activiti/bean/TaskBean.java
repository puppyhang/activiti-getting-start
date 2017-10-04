package com.ternence.activiti.bean;

/**
 * create by Ternence at 2017年10月4日 上午10:13:42
 * @description 保存任务相关的信息
 * @version 1.0
 */
public class TaskBean {
	//任务ID
	private String id;
	//任务名称
	private String name;
	//任务承受方
	private String assignee;
	
	
	public TaskBean() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	@Override
	public String toString() {
		return "TaskBean [id=" + id + ", name=" + name + ", assignee=" + assignee + "]";
	}
	
	

}
