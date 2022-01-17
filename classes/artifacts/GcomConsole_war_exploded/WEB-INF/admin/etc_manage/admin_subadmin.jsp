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

	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 6000); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			
			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>관리자 계정 관리</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div id="layout-container" style="width: 100%; height: 100%;">
						<div class="ui-layout-west">
							<div id="panel-tree" class="panel panel-default">
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

						<div class="ui-layout-center">
							<div id="panel-list" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>관리자 목록</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">	
											<!-- Standard button -->
											<button type="button" class="btn btn-default" onclick="javascript:onClickAddAdmin()"><i class="fa fa-plus" aria-hidden="true">&nbsp;&nbsp;관리자 추가</i></button>
											<!-- Info -->
<!-- 											<button type="button" class="btn btn-info" onclick="searchAdminInfo()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;재검색</i></button>
 -->											
											</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-striped table-bordered table-hover x-scroll-table" id="table_admininfo" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th>번호</th>
														<th>아이디</th>
														<th>담당부서</th>
														<th>권한</th>
														<th>아이피1</th>
														<th>아이피2</th>
														<th></th>
													</tr>
												</thead>				
												<tbody>
												</tbody>
											</table>									
										</div>
									</div>
									
									
									<div class="ld_modal hidden" >
									    <div class="ld_center" >
									        <img alt="" src="${context}/assets/images/loaders/loading.gif" />
									    </div>
									</div>
									
								</div>
								<!-- /panel content -->
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
		<div id="admin_input_popup">
		
		
		
		</div>
	
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
    
<script>

 	function setTree(){
		$.ajax({      
	        type:"POST",  
	        url:'${context}/common/tree/dept',
	        async: false,
	        //data:{},
	        success:function(args){   
	            $("#dept_tree").html(args);  
	            //treeSelectBind();
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	}
 	
 	function reloadTable(){
 		var datatable = $('#table_admininfo').dataTable().api();
		datatable.ajax.reload();   	
 	
 	}
 	function searchAdminInfo(){
 		var datatable = $('#table_admininfo').dataTable().api();
		datatable.ajax.reload();   	
 	
 	}
 	function onClickPrintButton(){
 		var $buttons = $('.export-print');
 		$buttons.click();
 	}
 	
 	function onClickExcelButton(){
		console.log('excel')
 		var $buttons = $('.export-csv');
 		$buttons.click();
 		
 	}

 	function tableReload(){
 		var datatable = $('#table_admininfo').dataTable().api();
		datatable.ajax.reload();   	
 	
 	}

 	
 	function onClickRemoveAdmin(admin_no){
		console.log(admin_no)
 		vex.defaultOptions.className = 'vex-theme-os'

 		vex.dialog.open({
			message: '해당 관리자가 삭제됩니다. 계속하시겠습니까?',
		  buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '확인'
		    }), $.extend({}, vex.dialog.buttons.NO, {
		      text: '취소'
		    })
		  ],
	 	    callback: function(data) {
	 	      if (data) {
	 	    	 DoRemoveAdmin(admin_no);
	 	      }
	 	    }
 		});
 	}
 	

	function DoRemoveAdmin(admin_no){
		$.ajax({      
	        type:"POST",  
	        url:'${context}/admin/admin/manage/do/remove',
	        async: false,
	        data:{
	        	admin_no : admin_no,
	        },
	        success:function(args){ 
	        	if(args.returnCode == 'S'){
	        		reloadTablePreventPage();
	    			infoAlert('관리자 삭제가 완료되었습니다.')
	        	}else{
	    			infoAlert('서버와의 통신에 실패하였습니다.')
	        	}
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	}

 	function onClickAddAdmin(){
 		$.ajax({      
	        type:"GET",  
	        url:'${context}/ax/admin/admininput/create',
	        async: false,
	        data:{
	        	_:$.now()
	        },
	        success:function(args){   
 	            $("#admin_input_popup").html(args);      
	            $("#modalAdminInfo").modal('show');
 	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 

 	}

 	function onClickModifyAdmin(admin_no){
 		$.ajax({      
	        type:"GET",  
	        url:'${context}/ax/admin/admininput/modify',
	        async: false,
	        data:{
	        	admin_no : admin_no,
	        	_:$.now()
	        },
	        success:function(args){   
 	            $("#admin_input_popup").html(args);      
	            $("#modalAdminInfo").modal('show').on('show.bs.modal', function (e) {
		    		return e.preventDefault();
		    	});
 	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
 		
 	}
 	
 	function fn_admin_create(){
		var data = getInputData();
		if(data != false){
			console.log('관리자수정')
			$.ajax({      
		        type:"POST",  
		        url:'${context}/admin/admin/manage/do/create',
		        async: false,
		        data: data,
		        dataType: "json",
		        success:function(args){
		        	if(args.returnCode == 'S'){
			            $("#modalAdminInfo").modal('hide');
		        		reloadTablePreventPage();
		    			infoAlert('관리자 추가가 완료되었습니다.')
		        	}else if(args.returnCode == 'EUN'){
		    			infoAlert('사번이 이미 존재합니다.')
		        	}else if(args.returnCode == 'DUI'){
		    			infoAlert('아이디가 이미 존재합니다.')
		        	}else if (args.returnCode =='ENP') {
		        		infoAlert('필수 입력란을 누락되었습니다.')
		        		$(args.param).focus();
		        	}else if (args.returnCode =='PP') {
		        		infoAlert('비밀번호 패턴을 확인하여 주십시오.')
		        	} else {
		    			infoAlert('서버와의 통신에 실패하였습니다.')
		        	}
		        },
		        //beforeSend:showRequest,
		        error:function(e){
		            console.log(e.responseText);
		        }  
		    }); 
		} 	
 	}
 	
	function fn_admin_modify(){
		var data = getInputData();
		
		if(data != false){
			console.log('관리자수정')
			$.ajax({      
		        type:"POST",  
		        url:'${context}/admin/admin/manage/do/update',
		        async: false,
		        data: data,
		        dataType: "json",
		        success:function(args){
		        	if(args.returnCode == 'S'){
			            $("#modalAdminInfo").modal('hide');
		        		reloadTablePreventPage();
		    			infoAlert('수정이 완료되었습니다.')
		        	}else if(args.returnCode == 'EUN'){
		    			infoAlert('사번이 이미 존재합니다.')
		        	}else if(args.returnCode == 'DUI'){
		    			infoAlert('아이디가 이미 존재합니다.')
		        	}else if (args.returnCode =='ENP') {
		        		infoAlert('필수 입력란을 누락되었습니다.')
		        		$(args.param).focus();
		        	} else if (args.returnCode =='PP') {
		        		infoAlert('비밀번호 패턴을 확인하여 주십시오.')
		        	} else if (args.returnCode === "PU") {
		    			infoAlert("최근 3개월 이내에 사용 된 비밀번호 입니다.");
		        	} else {
		    			infoAlert('서버와의 통신에 실패하였습니다.')
		        	}
		        },
		        //beforeSend:showRequest,
		        error:function(e){
		            console.log(e.responseText);
		        }  
		    }); 
		} 		
 	}
	
	
 	function reloadTablePreventPage(){
 		var datatable = $('#table_admininfo').dataTable().api();
		datatable.ajax.reload(null, false);   	
 	}

	function getInputData(){
		var data = new Object();

		data.admin_no = $('#admin_no').val();
		data.admin_dept = $('#admin_dept option:selected').val();
		data.admin_id = $('#admin_id').val();
		data.admin_ip0 = $('#admin_ip0').val();
		data.admin_ip1 = $('#admin_ip1').val();

		data.admin_password = $('#admin_pw1').val();
		data.admin_password2 = $('#admin_pw2').val();		
		data.admin_auth = $('#admin_auth option:selected').val();		
		
		var result = validCheck(data);
		if(result == true){
			return data;			
		}else{
			return false;
		}
	}

	function validCheck(data){
		
		var result = true;
		if(parseInt(data.admin_dept) < 1){
			infoAlert('부서선택 값이 유효하지 않습니다.')
			result = false;
		}else if(data.admin_id == ''){
			infoAlert('아이디를 입력하여주세요.')
			result = false;
		}else if(data.admin_password == '' && '${popup_type}' == 'create'){
			infoAlert('패스워드를 입력하여주세요.')
			result = false;
		}else if(data.admin_password2 == '' && '${popup_type}' == 'create'){
			infoAlert('확인 패스워드를 입력하여주세요.')
			result = false;
		}else if(data.admin_password != data.admin_password2 ){
			infoAlert('패스워드가 일치하지 않습니다.')
			result = false;
		}else if(data.admin_auth != 0 && data.admin_auth != 1 && data.admin_auth != 2  ){
			infoAlert('권한설정이 올바르지 않습니다.')
			result = false;
		}
		
		return result;		
	}
	
	function setDataTable(){
		if (jQuery().dataTable) {

			var export_filename = 'Filename';
			
			var table = jQuery('#table_admininfo');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/admin/subadmin/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
						param.dept = getCheckedDept();
			        },
				        "beforeSend" : function(){
						jQuery('#preloader').show();
				        },
			        "dataSrc": function ( json ) {
						jQuery('#preloader').hide();
		                return json.data;
		            }   
				},
				lengthMenu: [[20, 100, 1000], [20, 100, 1000]],
				tableTools: {
			          "sSwfPath": plugin_path + "datatables/extensions/Buttons/js/swf/flashExport.swf"
			        },
			    "buttons": [
					              {
				                  text: '<i class="fa fa-lg fa-clipboard">csv</i>',
				                  extend: 'csvHtml5',
				                  className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-csv-btn export-csv ttip hidden',
				                  bom: true,
				                  exportOptions: {
				                      modifier: {
				                          search: 'applied',
				                          order: 'applied'
				                      }
				                  }
				              },  					              {
			                  text: '<i class="fa fa-lg fa-clipboard">프린트</i>',
			                  extend: 'print',
			                  className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-export-btn export-print ttip hidden',
			                  exportOptions: {
			                      modifier: {
			                          search: 'applied',
			                          order: 'applied'
			                      }
			                  }
			              }, 

			     ],
		 		"serverSide" : true,
		 	    "ordering": true,
				"columns": [{
					data: "adminNo",
					"orderable": false	
				}, {
					data: "adminId",
					"orderable": false	
				},{
					data: "deptNm",
					"orderable": false	
				}, {
					data: "adminMode",
					"orderable": false	
				}, {
					data: "ipAddr",
					"orderable": false	
				},{
					data: "ipAddr1",
					"orderable": false	
				},  {
					data: "adminNo",
					"orderable": false	
				}],
				// set the initial value
				"pageLength": 20,
				"iDisplayLength": 20,
				"pagingType": "bootstrap_full_number",
				"language": {
					"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 관리자",
					"infoEmpty": "검색된 데이터가 없습니다.",
					"zeroRecords" :"검색된 데이터가 없습니다.",
					"lengthMenu": "  _MENU_ 개",
					"paginate": {
						"previous":"Prev",
						"next": "Next",
						"last": "Last",
						"first": "First"
					},
					
				},
				"columnDefs": [
				{	
					"targets": [0],	//번호
					"class":"center-cell"
				},         
				{  // set default column settings
					'targets': [1]	//아이디
					,"class":"center-cell"
				},{  // set default column settings
					'targets': [2]	//부서
					,"class":"center-cell"
				}, {	
					"targets": [3]	//권한
					,"class":"center-cell"
					,"render" : function(data, type, row){
						var result = "";
						if(data == 0 ){
							result = '콘솔/레포트'
						}else if(data == 1 ){
							result = '콘솔'
						}else if(data == 2 ){
							result = '레포트'
						}else{
							result = '권한 지정안됨'
						}
						return result;
					}

				}, {	
					"targets": [4]	//아이피
					,"class":"center-cell"
				}, {	
					"targets": [5]	//아이피2
					,"class":"center-cell"
				}, {	
					"targets": [6]	//패스워드
					,"class":"center-cell"
					,"render":function(data,type,row){
							var ret = '<button type="button" class="btn btn-info btn-xs" onclick="javascript:onClickModifyAdmin(' +row.adminNo+ ')"><i class="fa fa-gear" aria-hidden="true">&nbsp;수정</i></button>';
							ret += '<button type="button" class="btn btn-danger btn-xs" onclick="javascript:onClickRemoveAdmin(' +row.adminNo+ ')"><i class="fa fa-remove" aria-hidden="true">&nbsp;삭제</i></button>';

							return ret;
					}
				}

			],						
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
//			        $('#table_admininfo').colResizable({liveDrag:true});
				}
			});
		}
	}
	
 	var ajaxCount = 2;
 	function initLayout() {
 		if (ajaxCount > 0) ajaxCount--;
 		if (ajaxCount == 0) {
 			var treePanel = $('#panel-tree');
 			var listPanel = $('#panel-list');
 			var tree = $('#dept_tree');
 			var list = $('#panel-list').children('.panel-body');
 			
 	 		list.css('height','');
 	 		tree.css('height','');
 	 		
 			var height = Math.max(window.innerHeight -  $('#layout-container').offset().top - 20, $('#sideNav').height());
 			height = Math.max(height, listPanel.height());
 			height = Math.max(height, treePanel.height());
 			
			list.height(height);
 	 	 	tree.height(height - 20);
 			$('#layout-container').height(height);
 			$('#layout-container').layout().resizeAll();
 		}
 	}
 	
 	$(window).on('resize', function() {
 		initLayout();
 	});
 	
 	var layer = $('#layout-container').layout({ 
 		closable: false,
 		west__minWidth : 230,
 		center__minWidth : 500,
 		west__size : 230,
 	});

	$(document).ready(function(){
     	setTree();
		$('#org_tree')
		.bind('ready.jstree', function(e, data) {
			setDataTable();
		})
		jQuery('#preloader').hide();
    });
	
	$(document).ajaxComplete(function(){
		initLayout();
	});

	</script>
	</body>
</html>