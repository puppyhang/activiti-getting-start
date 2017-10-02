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
						dataType : 'json',
						success : function(data) {
							if (data && data['status'] === "200") {
								layer.msg(data['message']);
							} else {
								layer.msg(data['message']);
							}
							layer.close(index);
						},
						clearForm : true,
						resetForm : true
					});
				}
			});
		}
	}
});