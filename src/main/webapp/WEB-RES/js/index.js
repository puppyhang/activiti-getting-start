/**
 * index的js文件
 */
$(function() {
	// URL管理模块
	var URL = {
		PROCESS_INSTANCE_LIST : '/process/ins/list',
	};
	// 文件选择的JS
	$('input[id=lefile]').change(function() {
		$('#photoCover').val($(this).val());
	});
	// ------------------------------------------Common usage start
	// 查看流程实例图片
	function viewGraph(event) {
		var $target = $(event.target);
		// 对于这种异步加载的元素，对于layer的自适应是不太好使用的，最好还是制定一个宽度和高度，用户需要的时候自己拉伸
		var image = "<img src=" + $target.attr('data-href') + "/>";
		layer.open({
			type : 1,
			// 在设定宽高的情况下最好开启最大化最小化按钮
			maxmin : true,
			area : [ '700px', '300px' ],
			title : $target.attr('data-name'),
			content : image,
			btn : [ '图片太大？拖动右下角缩放,或点我下载' ],
			cancel : function(index, layero) {
				layer.close(index);
			}
		});
	}

	// 获取流程实例列表数据并且绑定到页面上
	function getProcessInstanceListAndBinding() {
		$.get(URL.PROCESS_INSTANCE_LIST, function(result) {
			if (result && result['status'] === 200) {
				// 刷新列表,这就是双向数据绑定的好处，实在是棒极了，只需要改变属性就得吃了
				processInstance.$data['instances'] = result['data'];
			} else {
				layer.alert(result['message'], {
					zIndex : 10000,
					icon : 5
				});
			}
		});
	}

	function getProcessListAndBinding() {
		$.get("process/def/list", function(result) {
			if (result && result['status'] === 200) {
				// Vue的内部属性用$xx 开头，避免和其他数据混乱
				processDefinitionTable.$data['definitions'] = result['data'];
			} else {
				layer.alert(result['message'], {
					zIndex : 10000,
					icon : 5
				});
			}

		});
	}
	// ------------------------------------------Common usage end
	//
	//
	//
	// -----------------------------------------Process Definition start
	// 流程定义列表的数据绑定
	var processDefinitionTable = new Vue({
		el : "#process-definition-table",
		data : {
			definitions : []
		},
		methods : {
			// 计算一个面向人类的可读索引
			humanIndex : function(index) {
				console.log(index);
				return index + 1;
			},
			// 根据id删除流程
			deleteProcessById : function(event) {
				// 被点击的元素
				var $target = $(event.target);
				// ajax 删除
				$.get($target.attr('data-href'), function(data) {
					// 被解析为数字！why？
					if (data['status'] === 200) {
						layer.alert(data['message'], {
							zIndex : 100000,
							icon : 1
						});
						// 如果删除成功就刷新列表
						getProcessListAndBinding();
					} else {
						layer.alert(data['message'], {
							zIndex : 100000,
							icon : 1
						});
					}
				});
			},
			// 根据流程定义id（不是流程部署ID）启动流程实例
			startProcessInstanceById : function(event) {
				var $target = $(event.target);
				$.get($target.attr("data-href"), function(result) {
					if (result && result['status'] == 200) {
						// 流程启动成功
						layer.alert(result['message'], {
							zIndex : 10000,
							icon : 1
						});
						// 刷新流程实例列表
						getProcessInstanceListAndBinding();
					} else {
						// 流程启动失败
						layer.alert(result['message'], {
							zIndex : 10000,
							icon : 5
						});
					}
				});
			},
			// 查看流程图片
			viewGraph : viewGraph
		}
	});

	// 初始化列表数据
	getProcessListAndBinding();
	// ----------------------------------------------Process Definition end
	//
	//
	//
	// --------------------------------------------------deploy start
	var options = {
		type : 1,
		skin : 'layui-layer-rim',
		area : [ '500px', '300px' ],
		// type 位 为 1 的时候加载内容的方式content :$("#deploy-pane")
		// type为2的时候会以一个http请求的方式加载
		content : $("#deploy-pane"),
		btn : [ "部署", "取消" ],
		yes : function(index, layero) {
			// / / 提交表单
			var form = $(layero).find("form");
			form.ajaxSubmit({
				type : 'POST',
				url : '/process/def/deploy',
				datatype : "json",
				contentType : "multipart/form-data",
				success : function(result) {
					onDeploySuccess(result, index);
				},
				clearForm : true,
				resetForm : true
			});
		}
	}

	function onDeploySuccess(data, index) {
		// 被解析为数字
		if (data['status'] === 200) {
			layer.alert(data['message'], {
				zIndex : 100000,
				icon : 1
			});
			// 部署成功的话刷新数据列表
			getProcessListAndBinding();
		} else {
			layer.alert(data['message'], {
				zIndex : 10000,
				icon : 5
			});
		}
		layer.close(index);
	}

	var deploy = new Vue({
		el : "#deploy",
		methods : {
			clickDeploy : function(event) {
				// 打开一个弹窗
				layer.open(options);
			}
		}
	});

	// -------------------------------------------------deploy end
	//
	//
	//
	// -------------------------------------------------Process Instance start
	var processInstance = new Vue({
		el : "#process-instance-table",
		data : {
			instances : [],
		},
		methods : {
			// TODO 思考有没有继承，实现方法复用
			humanIndex : function(index) {

				return index + 1;
			},
			// 查看流程图片的方法
			viewGraph : viewGraph
		}
	});
	// 初始化流程实例列表
	getProcessInstanceListAndBinding();
	// -------------------------------------------------Process Instance end
});
