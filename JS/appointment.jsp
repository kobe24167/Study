<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head lang="en">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="<%=path%>/img/ico.png">
<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/css/font-awesome.css" rel="stylesheet">
<link href="<%=path%>/css/animate.css" rel="stylesheet">
<link href="<%=path%>/css/style.css" rel="stylesheet">
<link href="<%=path%>/css/pickout.css" rel="stylesheet">
<link href="<%=path%>/css/detail.css" rel="stylesheet">
<!--
    <link rel='stylesheet' type='text/css' href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/start/jquery-ui.css' />
    -->
<style type="text/css">
#test-n2 {
	position: relative;
	min-height: 400px !important;
	width: 100% !important;
}

#test-n2  #layui-laydate1, #test-n2  #layui-laydate1 .layui-laydate-main,
	#test-n2 .layui-laydate-content table {
	width: 100%;
}

#test-n2 .layui-laydate-content table tbody {
	height: calc(100vh - 240px) !important;
}

.text_box {
	padding: 15px !important;
}

#wrapper {
	background-color: #f3f3f4;
}
</style>
</head>
<body class="fixed-sidebar gray-bg">
	<jsp:include page="${path}/pages/header.jsp" />
	<div id="wrapper">
		<jsp:include page="${path}/pages/leftNav.jsp" />
		<div id="page-wrapper">
			<div class="form-group">
				<label for="city" class="selectDoc">请选择一个治疗师：</label>
				<select name="city" id="city" class="city pickout" placeholder="选择一个治疗师">
				</select>
			</div>
			<br/><br/><br/><br/><br/>
			<div class="layui-container" style="padding: 15px;">
				<div class="layui-row">
					<div class="layui-col-md12">
						<div class="layui-inline" id="test-n2"></div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
<script src="<%=path%>/js/jquery.min.js"></script>
<script src="<%=path%>/js/bootstrap.min.js"></script>
<script src="<%=path%>/js/jqPaginator.min.js"></script>
<script src="<%=path%>/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="<%=path%>/js/pickout.js"></script>
<script src="<%=path%>/js/util.js"></script>
<script src="<%=path%>/js/plugins/laydate/laydate.js"></script>
<script src="<%=path%>/js/function/appointment.js"></script>
<script type="text/javascript">
        var path='<%=path%>';
        
	$("a.appointment").addClass("active").parent().parent().parent().addClass(
			"active");

						//定义json	
						var data = {};

						var new_date = new Date();
						loding_date(new_date, data);

						//日历插件调用方法  
						function loding_date(date_value, data) {
							console.log(data)
							laydate.render({
								elem : '#test-n2',
								type : 'date',
								theme : 'grid',
								max : '2099-06-16 23:59:59',
								position : 'static',
								range : false,
								value : date_value,
								btns : false,
								mark : data,
								done : function(value, date, endDate) {
									//调用弹出层方法
									date_chose(value, data);
								}

							//重要json！

							});
						}

						//定义弹出层方法
						function date_chose(obj_date, data) {
							var sel=document.getElementById("city");
				    		var userid=sel.options[sel.selectedIndex].id;
				    		if (userid == "") {
				    			alert("请选择治疗师！");
				    			return;
				    		}
							var chose_moban_val = "工作";
							var time;
							if (data[obj_date] == chose_moban_val) {
								delete data[obj_date];
								time = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
							} else {
								//添加属性
								data[obj_date] = chose_moban_val;
								time = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
							}
							
							var sel=document.getElementById("city");
				    		var userid=sel.options[sel.selectedIndex].id;
				    		var url=path+'/worktime/save'
						    var result=[]
						    var obj = {}
							obj.userid = userid;
							obj.date = obj_date;
							obj.time = time;
						    result.push(obj);
						    var d = {
						        json: JSON.stringify(result)
						    }
						    console.log(d)
						    $.post(url,d,function(n){
						    	console.log(n);
						    })
							
							$('#test-n2').html('');//重要！由于插件是嵌套指定容器，再次调用前需要清空原日历控件
							//再次调用日历控件，
							loding_date(obj_date, data);//重要！，再标注一个日期后会刷新当前日期变为初始值，所以必须调用当前选定日期。

						}
						
						 $("#city").change(function(){
					    		var sel=document.getElementById("city");
					    		var userid=sel.options[sel.selectedIndex].id;
					    		if (userid == "") {
					    			$('#test-n2').html('');
					    			loding_date(new Date(), {});
					    			return;
					    		}
					    		$.post(path+'/worktime/getDateByUserId',{
					    	        userid:userid
					    	    },function(data){
					    	        if(data!=''||data.length>0){
					    	        	var newDate = {};
					    	        	for (var i=0;i<data.length;i++){
					    	        		newDate[data[i]] = "工作";
					    	        	}
					    	        	var new_date = new Date();
					    	        	$('#test-n2').html('');
					    				loding_date(new_date, newDate);
					    	        }
					    	    })
					    	});
</script>
</html>
