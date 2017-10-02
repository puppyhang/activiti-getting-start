<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="context.jsp"%>
<html>
<head>
<title>activiti管理中心</title>
<link rel="stylesheet" type="text/css"
	href="${context}/res/plugins/bootstrap3/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${context}/res/css/index.css">
</head>

<body>
	<div class="container">
		<div>
			<a class="btn btn-default" role="button">部署流程</a> <a
				class="btn btn-default" role="button">查看数据库</a>
		</div>

		<table class="table table-bordered table-hover">
			<caption>流程定义</caption>
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>NAME</th>
					<th>OPRATIONS</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>1</th>
					<th>0xa0bdcgff</th>
					<th>Ternence</th>
					<th><a class="btn btn-success">start</a> <a
						class="btn btn-success">graph</a></th>
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
			<tbody>
				<tr>
					<th>1</th>
					<th>0xa0bdcgff</th>
					<th>Ternence</th>
					<th><a class="btn btn-success">graph</a></th>
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

	</div>
</body>


<script type="text/javascript" src="${context}/res/js/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="${context}/res/plugins/bootstrap3/js/bootstrap.js"></script>
<script type="text/javascript" src="${context}/res/js/vue.min.js"></script>
</html>
