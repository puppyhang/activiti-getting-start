/**
 * index的js文件
 */
var vue = new Vue({
	el : "#deploy",
	methods : {
		clickDeploy : function(event) {
			// 打开一个弹窗
			layer.open({
				type : 1,
				skin : 'layui-layer-rim',
				area : [ '500px', '300px' ],
				// type 位 为 1 的时候加载内容的方式
				// content : $("#deploy-pane")
				// 内容为2的时候会以一个http请求的方式加载
				// '/res'是spring的静态资源映射
				content : $("#deploy-pane"),
				btn : [ "部署", "取消" ],
				// index唯一标识这个弹出层
				yes : function(index, layero) {
					// 提交表单
					var form = $(layero).find("form");
					alert("ajax表单提交" + form);
					form.ajaxSubmit({
						type : 'POST',
						url : '/process/def/deploy',
						dataType : 'json',
						success : function(data) {
							if (data && data['status'] === "200") {
								layer.close(index);
								layer.msg(data['message']);
							} else {
								layer.msg(data['message']);
							}
						},
						clearForm : true,
						resetForm : true
					});
				}
			});
		}
	}
});