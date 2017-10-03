<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="context.jsp"%>
<html>
<head>
<title>activiti管理中心</title>
<link rel="shortcut icon" href="/favicon.ico">
<link rel="stylesheet" type="text/css"
	href="/res/plugins/bootstrap3/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/res/css/index.css">
<linkrel ="stylesheet" type="text/css"
	href="/res/plugins/layer3/theme/default/layer.css">
</head>

<body>
	<div class="container">
		<div>
			<!-- 使用vue为其绑定一个点击事件 -->
			<a id="deploy" class="btn btn-default" role="button"
				v-on:click="clickDeploy($event)">部署流程</a>
			<!-- 使用vue为其绑定一个点击事件 -->
			<a class="btn btn-default" role="button">查看数据库</a>
		</div>

		<table class="table table-bordered table-hover">
			<caption>流程定义</caption>
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>DEPLOYMENT-ID</th>
					<th>NAME</th>
					<th>OPRATIONS</th>
				</tr>
			</thead>
			<tbody id="process-definition-table">
				<tr v-for="(process,index) in definitions">
					<th>{{humanIndex(index)}}</th>
					<th>{{process.id}}</th>
					<th>{{process.deploymentId}}</th>
					<th>{{process.name}}</th>
					<th>
					<!-- 启动流程定义 -->
					<a class="btn btn-success"
					v-bind:data-href="'/process/def/start/'+process.id"
					v-on:click="startProcessInstanceById($event)">start</a> 
					<!-- 查看流程图片 -->
					<a class="btn btn-success"
					v-bind:data-href="'/process/graph/def/'+process.id"
					v-on:click="viewGraph($event)">graph</a> 
					<!-- 使用Vue绑定数据属性 -->
					<a class="btn btn-danger"
						v-bind:data-href="'/process/def/delete/'+process.deploymentId"
						v-on:click="deleteProcessById($event)">delete</a>
					</th>
				</tr>
			</tbody>
		</table>
		
		<table class="table table-bordered table-hover">
			<caption>流程实例</caption>
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>PROCESS DEFINITION</th>
					<th>OPRATIONS</th>
				</tr>
			</thead>
			<tbody id="process-instance-table">
				<tr v-for="(instance,index) in instances">
					<th>{{humanIndex(index)}}</th>
					<th>{{instance.id}}</th>
					<th>{{instance.processDefinition}}</th>
					<th>
					<a class="btn btn-success">assign</a>
					<a class="btn btn-success">graph</a>
					<a class="btn btn-danger">delete</a>
					</th>
				</tr>
			</tbody>
		</table>



		<table class="table table-bordered table-hover">
			<caption>待办任务</caption>
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>NAME</th>
					<th>ASSIGNEE</th>
					<th>OPRATIONS</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>1</th>
					<th>0xa0bdcgff</th>
					<th>Ternence</th>
					<th>Veivian</th>
					<th><a class="btn btn-success">graph</a></th>
				</tr>
			</tbody>
		</table>
		<form id="deploy-pane" enctype="multipart/form-data"
			onsubmit="return false;" method="post" style="display: none">
			<!-- 隐藏用于真实上传文件的输入框 -->
			<input id="lefile" name="file" type="file" style="display: none"
				required>
			<div class="input-append">
				<input id="photoCover" class="input-large form-control" type="text"
					placeholder="选择xml流程定义文件"
					style="display: inline; width: 400px; margin-left: 10px" required />

				<a class="btn btn-success" onclick="$('input[id=lefile]').click();">浏览</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="/res/js/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="/res/plugins/bootstrap3/js/bootstrap.js"></script>
<script type="text/javascript" src="/res/plugins/layer3/layer.js"></script>
<script type="text/javascript"
	src="/res/plugins/jq-validate/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="/res/plugins/jq-validate/messages_zh.min.js"></script>
<script type="text/javascript" src="/res/js/vue.min.js"></script>
<script type="text/javascript" src="/res/js/jquery.form.js"></script>
<script type="text/javascript" src="/res/js/index.js"></script>
</html>
