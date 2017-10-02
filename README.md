# activiti-getting-start
activiti的一个入门项目

# 部署process definition (流程定义)核心代码

    ···
		ProcessDefinitionBean process = new ProcessDefinitionBean();
		//得到Activiti的服务
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//部署流程文件
		Deployment deployment = repositoryService.createDeployment()
			.addInputStream("process.bpmn20.xml", new FileInputStream(targetFile))
			.deploy();
    ···