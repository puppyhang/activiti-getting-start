/**
 * index的js文件
 */
$(function() {
	$('input[id=lefile]').change(function() {
		$('#photoCover').val($(this).val());
	});
	// 流程定义列表的数据绑定
	var processDefinitionTable = new Vue({
		el : "#process-definition-table",
		data : {
			definitions : []
		},
		methods : {
			deleteProcessById : function(event) {
				// 被点击的元素
				var $target = $(event.target);
				// ajax 删除
				$.get($target.attr('data-href'), function(data) {
					//被解析为数字！why？
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
			}
		}
	});

	// 初始化列表数据
	getProcessListAndBinding();

	function getProcessListAndBinding() {
		$.get("process/assign/list", function(data) {
			/* vue的内部属性用$** 开头，避免和其他数据混乱 */
			processDefinitionTable.$data['definitions'] = data['data'];
		});
	}

	// --------------------------------------------------deploy start
	var options = {
		type : 1,
		skin : 'layui-layer-rim',
		area : [ '500px', '300px' ],
		// type 位 为 1 的时候加载内容的方式content :
		// $("#deploy-pane"),type为2的时候会以一个http请求的方式加载
		content : $("#deploy-pane"),
		btn : [ "部署", "取消" ],
		yes : function(index, layero) {
			// 提交表单
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
		//被解析为数字
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
});
