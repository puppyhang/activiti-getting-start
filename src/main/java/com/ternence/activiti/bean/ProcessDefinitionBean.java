package com.ternence.activiti.bean;

/**
 * 存储一些流程定义的相关信息
 * 
 * @author Ternence
 *
 */
public class ProcessDefinitionBean extends SimpleProcessDefinitionBean {
	/**
	 * Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。
	 * 在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）
	 * 的serialVersionUID进行比较，
	 * 如果相同就认为是一致的，可以进行反序列化，
	 * 否则就会出现序列化版本不一致的异常。(InvalidCastException)
	 */
	private static final long serialVersionUID = 4611941357179885319L;
	
	//流程部署ID，用于对流程进行操作
	private String deploymentId;

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

}
