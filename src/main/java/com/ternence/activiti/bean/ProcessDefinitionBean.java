package com.ternence.activiti.bean;

import java.io.Serializable;

/**
 * 存储一些流程定义的相关信息
 * 
 * @author Ternence
 *
 */
public class ProcessDefinitionBean implements Serializable {

	/**
	 * Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。
	 * 在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）
	 * 的serialVersionUID进行比较，
	 * 如果相同就认为是一致的，可以进行反序列化，
	 * 否则就会出现序列化版本不一致的异常。(InvalidCastException)
	 */
	private static final long serialVersionUID = 8858304664973856458L;

	//流程文件的保存路径
	private String path;
	//流程部署ID
	private String id;
	//流程部署名称
	private String name;

	public ProcessDefinitionBean() {
		super();
		// nothing to do,only placeholder for serialization/deserialization framework to use;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	@Override
	public String toString() {
		return "ProcessDefinitionBean [path=" + path + ", id=" + id + ", name=" + name + "]";
	}
	
}
