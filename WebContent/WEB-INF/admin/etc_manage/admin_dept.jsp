<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
	
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Console</title>

		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />

		<!-- CORE CSS -->
		<link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- THEME CSS -->
		<link href="${context}/assets/css/essentials.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/layout.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/color_scheme/green.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/jstree/themes/default/style.min.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.jqueryui.min.css" rel="stylesheet" type="text/css"  />
        <link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
        <link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />
		<script type="text/javascript" src="${context}/assets/js/admin_function.js"></script>

	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 8000); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>부서 관리</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">
						<div class="col-md-3">
							<div id="panel-2" class="panel panel-default">
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>조직도</strong> <!-- panel title -->
									</span>
								</div>

								<!-- panel content -->
								<div id="dept_tree" class="panel-body" style="padding-left: 0px; padding-right: 0px;">

								</div>
								<!-- /panel content -->

							</div>
							<!-- /PANEL -->
					
						</div>

						<div class="col-md-9">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>부서정보</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<form class="validate" data-toastr-position="top-right">

									<fieldset>
									<div class="row">
										<div class="form-group">
											<div class="col-md-6 col-sm-6">
												<label>부서코드 </label>
												<input type="text" name="contact[first_name]" id="dept_no" class="form-control" readonly>
											</div>
											<div class="col-md-6 col-sm-6">
												<label>부서 직속/소속 인원</label>
												<input type="text" name="contact[last_name]" id="member_count" class="form-control" readonly>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="form-group">
											<div class="col-md-12 col-sm-12">
												<label>부서이름</label>
												<input type="text" name="contact[first_name]" id="dept_name" class="form-control required">
											</div>
										</div>
									</div>										
									<div class="row">
										<div class="form-group">
											<div class="col-md-12 col-sm-12">
												<label>노출부서이름</label>
												<input type="text" name="contact[first_name]" id="dept_short" class="form-control required">
											</div>
										</div>
									</div>										
											<div class="row">
												<div class="form-group">
													<div class="col-md-12 col-sm-12" >
														<button type="button" class="btn btn-danger" onclick="javascript:removeDept()" ><i class="fa fa-remove" aria-hidden="true">&nbsp;&nbsp;부서삭제</i></button>

														<button type="button" class="btn btn-info pull-right" onclick="javascript:updateDept()"><i class="fa fa-save" aria-hidden="true">&nbsp;&nbsp;저장</i></button>
														<button type="button" class="btn btn-default pull-right" onclick="javascript:createDept()">하위부서생성</button>				
													</div>
												</div>
											</div>

									</fieldset>
									</form>
								</div>
								<!-- /panel content -->
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>

<script>

	var selectedDeptNo = -1;

 	function setTree(){
		$.ajax({      
	        type:"POST",  
	        url:'${context}/common/tree/selectdept',
	        async: false,
	        //data:{},
	        success:function(args){   
	            $("#dept_tree").html(args);      
	            treeSelectBind();
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	}
 	
 	function getDept(){
		jQuery('#preloader').show();

 		$.ajax({      
	        type:"GET",  
	        url:'${context}/dept/data/info',
	        data:{
				dept_no : selectedDeptNo,
	        	_:$.now()
	        },
	        success:function(args){   
	        	setDeptInfo(args);
	    		jQuery('#preloader').hide();

	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    });  		
 	}
 	
 	function removeDept(){

 		if(selectedDeptNo == -1){
 			notSelectModel()
 			return;
 		}
 		
 		$.ajax({      
	        type:"POST",  
	        url:'${context}/admin/do/dept/remove',
	        data:{
				no : $('#dept_no').val(),
	        },
	        success:function(args){   
	        	console.log(args);
	        	if(args.returnCode == 'S'){
		        	setTree();
		    		jQuery('#preloader').hide();
		    		completeAlert();	        		
	        	}else if(args.returnCode == 'EDU'){
	        		infoAlert('사용자가 존재합니다.');
	        	}else if(args.returnCode == 'EDA'){
	        		infoAlert('에이전트가 존재합니다.');
	        	}else if(args.returnCode == 'ECD'){
	        		infoAlert('하위부서가 존재합니다.');
	        	}else {
	        		failAlert();	        		
	        	}

	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
 		
 	}
 	
 	function updateDept(){
 		if(selectedDeptNo == -1){
 			notSelectModel()
 			return;
 		}
 		
 		$.ajax({      
	        type:"POST",  
	        url:'${context}/admin/do/dept/update',
	        data:{
				no : $('#dept_no').val(),
				name : $('#dept_name').val(),
				short_name : $('#dept_short').val(),
	        },
	        success:function(args){   
	        	if(args.returnCode == 'S'){
		        	setTree();
		    		jQuery('#preloader').hide();
		    		completeAlert();	        		
	        	}else{
	        		failAlert();
	        	}
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
 	}
 	
 	function createDept(){
 		if(selectedDeptNo == -1){
 			notSelectModel()
 			return;
 		}
 		vex.dialog.open({
 		    message: '생성할 부서명과 노출부서명을 입력하여 주세요.',
			buttons: [
					    $.extend({}, vex.dialog.buttons.YES, {
					      text: '확인'
					    }),
					    $.extend({}, vex.dialog.buttons.NO, {
					      text: '취소'
					    })
					    ],
 		    input: [
			         '<label>부서이름</label>',			        
			         '<input name="create_dept_name" type="text" />',
			         '<label>노출부서이름</label>',			        
			         '<input name="create_dept_short" type="text" />'
			     ].join(''),
 		    callback: function (data) {
 		        if (!data) {
 		        	return;
 		        }else{
 		        	var obj = new Object();
 		        	obj.parent = $('#dept_no').val();
 		        	obj.name = data.create_dept_name;
 		        	obj.short_name = data.create_dept_short;
 		        	createDeptDo(obj);
 		        }
 		    }
 		})

 	}
 	
 	function createDeptDo(obj){
 		$.ajax({      
	        type:"POST",  
	        url:'${context}/admin/do/dept/create',
	        data:{
				parent : obj.parent,
				name : obj.name,
				short_name : obj.short_name,
	        },
	        success:function(args){       		
	        	if(args.returnCode == 'S'){
		        	setTree();
		    		jQuery('#preloader').hide();
		    		setDeptEmptyInfo()
		    		completeAlert();	        		
	        	}else{
	        		failAlert();
	        	}
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
 		
 	}
 	
 	function notSelectModel(){
        vex.dialog.open({
            message: '부서선택를 먼저 선택하여 주세요.',
              buttons: [
                $.extend({}, vex.dialog.buttons.YES, {
                  text: '확인'
              })]
      })
 	}
 	
 	function setDeptInfo(obj){
 		$('#dept_no').val(obj.deptNo)
		$('#member_count').val(obj.deptBelongMemberCount + '/' + obj.deptMemberCount)
		$('#dept_name').val(obj.name)
		$('#dept_short').val(obj.shortName)
		
 	}
 	
 	function setDeptEmptyInfo(){
 		$('#dept_no').val('')
		$('#member_count').val('')
		$('#dept_name').val('')
		$('#dept_short').val('')
		
 	}
 	
 	function treeSelectBind(){
		$("#org_tree").bind(
		        "select_node.jstree", function(evt, data){
		        	var dept = data.selected[0];
		        	selectedDeptNo = dept;
		        	getDept();

		        }
			);
 	}
 	
	$(document).ready(function(){
		vex.defaultOptions.className = 'vex-theme-os'

		$(".select2theme").select2({
   			  minimumResultsForSearch: -1,
   			  dropdownAutoWidth : true,
   			  width: 'auto'
   		});

		
     	setTree();

		jQuery('#preloader').hide();
    });
</script>
	</body>
</html>