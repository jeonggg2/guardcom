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

			<% request.setAttribute("menu_parent", 2000); %> 
			<% request.setAttribute("menu_sub_first", 2700); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>사용자가입요청</h1>
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
										<strong>요청 리스트</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
			
											<!-- Standard button -->
											<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle(1,initLayout);"><i class="fa fa-filter" aria-hidden="true">&nbsp;검색필터</i></button>
		
											<!-- Info -->
											<button type="button" class="btn btn-info" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;재검색</i></button>
											
											<!-- Primary -->
											<button type="button" class="btn btn-primary pull-right" onclick="onClickExcelButton()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;내보내기</i></button>
											<!-- Success -->
											<button type="button" class="btn btn-success pull-right" onclick="onClickPrintButton()"><i class="fa fa-print" aria-hidden="true">&nbsp;인쇄</i></button>
											<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:700px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="15%">아이디</td>
															<td>
																<input type="text" name="filterUserId" id="filterUserId" value="" class="form-control required">
															</td>
															<td width="15%">이름</td>
															<td>
																<input type="text" name="filterUserName" id="filterUserName" value="" class="form-control required">
															</td>
														</tr>
														<tr>         

														</tr>
														<tr>         
															<td>요청시작일</td>
															<td>
							<input type="text" class="form-control datepicker" id="filterStartDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
															</td>
															<td>요청종료일</td>
															<td>
							<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
															</td>
														</tr>																													
														<tr >         
															<td>직책</td>
															<td>
																<input type="text" name="filterDuty" id="filterDuty" value="" class="form-control required">
															</td>
															<td>계급</td>
															<td>
																<input type="text" name="filterRank" id="filterRank" value="" class="form-control required">
															</td>
														<tr >         
															<td>번호</td>
															<td>
																<input type="text" name="filterPhoneNumber" id="filterPhoneNumber" value="" class="form-control required">
															</td>
															<td>승인여부</td>
															<td>
																<select class="select2theme" id="filterPermit">
																  <option value="">전체</option>
																  <option value="W">대기</option>
																  <option value="P">승인</option>
																  <option value="R">미승인</option>
																</select>
															</td>

														</tr>																															
														
													</tbody>
												</table>	
												
												<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle(1,initLayout);">접기</button>
																					
											</div>
<!-- 										
											<button type="button" class="btn btn-warning">Warning</button>
		
											
											<button type="button" class="btn btn-danger">Danger</button> -->
										</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-bordered x-scroll-table" id="table_apply_policy" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th>부서명</th>
														<th>직책</th>
														<th>계급</th>
														<th>아이디</th>
														<th>이름</th>
														<th>연락처</th>
														<th>요청일</th>
														<th>승인여부</th>														
														<th>승인일자</th>
														<th>승인버튼</th>
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
									
									<!-- 정책 할당 Ajax Div -->
									<div id="policy_apply_div"></div>
								</div>
								<!-- /panel content -->
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>

<script type="text/javascript">
	
	// 조직도 
 	function setTree(){
		$.ajax({      
	        type:"POST",  
	        url:'${context}/common/tree/dept',
	        async: false,
	        //data:{},
	        success:function(args){   
	            $("#dept_tree").html(args);      
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	}
 	
 	// 검색 버튼 클릭 시 
 	function reloadTable(){
 		var datatable = $('#table_apply_policy').dataTable().api();
		datatable.ajax.reload();   	
 	}
 	// 검색 버튼 클릭 시 
 	function reloadTable(){
 		var datatable = $('#table_apply_policy').dataTable().api();
		datatable.ajax.reload();   	
 	}

 	// 인쇄 버튼 클릭 시 
 	function onClickPrintButton(){
 		var $buttons = $('.export-print');
 		$buttons.click();
 	}
 	
 	// 내보내기 버튼 클릭 시 
 	function onClickExcelButton(){
		console.log('excel')
 		var $buttons = $('.export-csv');
 		$buttons.click();
 		
 	}
 	
 	function reloadTablePreventPage(){
 		var datatable = $('#table_apply_policy').dataTable().api();
		datatable.ajax.reload(null, false);   	
 		
 	}
 	
 	function userSaveDo(req_id){

 		var dup = checkDupEnrollUser(req_id)
 		if(dup != 'S' ){
 			if(dup == 'DUN'){
 				vex.dialog.open({
 					message: '중복된 사번이 이미 등록되어 있습니다.',
 					  buttons: [
 					    $.extend({}, vex.dialog.buttons.YES, {
 					      text: '확인'
 					  })]
 				})
 			}else if(dup == 'DUI'){
 				vex.dialog.open({
 					message: '중복된 아이디가 이미 등록되어 있습니다.',
 					  buttons: [
 					    $.extend({}, vex.dialog.buttons.YES, {
 					      text: '확인'
 					  })]
 				})
 			}
			
 			return;
 		}
 		
 		$.ajax({      
	        type:"POST",  
	        url:'${context}/admin/enroll/do/save',
	        data:{
				req_id : req_id	        	
	        },
	        success:function(args){   
	        	if(args.returnCode == 'S'){
		     		jQuery('#preloader').hide();
		     		reloadTablePreventPage();
		     		completeAlert();	        		
	        	}else{
		     		jQuery('#preloader').hide();
		     		failAlert();	        			        		
	        	}
	        },   
	        beforeSend:function(args){
	     		jQuery('#preloader').show();
	        },  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	
 	}
 	
 	function userRejectDo(req_id){
 		$.ajax({      
	        type:"POST",  
	        url:'${context}/admin/enroll/do/reject',
	        data:{
				req_id : req_id	        	
	        },
	        success:function(args){   
	        	if(args.returnCode == 'S'){
		     		jQuery('#preloader').hide();
		     		reloadTablePreventPage();
		     		completeAlert();	        		
	        	}else{
		     		jQuery('#preloader').hide();
		     		failAlert();	        			        		
	        	}
	        },   
	        beforeSend:function(args){
	     		jQuery('#preloader').show();
	        },  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
 		
 	}

 	function checkDupEnrollUser(req_id){
		var result = 'E';
 		$.ajax({      
	        type:"POST",  
	        url:'${context}/admin/enroll/do/dupcheck',
	        async: false,
	        data:{
				req_id : req_id	        	
	        },
	        success:function(args){   
	     		jQuery('#preloader').hide();
	        	result = args.returnCode;
	        },   
	        beforeSend:function(args){
	     		jQuery('#preloader').show();
	        },  
	        error:function(e){  
	            result = 'E'	        
	           }  
	    }); 
 		
 		return result;
 	}

 	function enrollConfirm(userId, req_id){
		console.log('123123123')
 		vex.defaultOptions.className = 'vex-theme-os'
		
		vex.dialog.open({
			message: '해당 사용자 ['+ atob(userId) +'] 가 등록됩니다. 계속하시겠습니까?',
		  buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '확인'
		    }), $.extend({}, vex.dialog.buttons.NO, {
		      text: '취소'
		    })
		  ],
	 	    callback: function(data) {
	 	      if (data) {
	 	    	 userSaveDo(req_id);
	 	      }
	 	    }
 		});
 	}
 	
 	function enrollReject(userId, req_id){
		vex.defaultOptions.className = 'vex-theme-os'
			
			vex.dialog.open({
				message: '해당 사용자 ['+ atob(userId) +'] 등록을 반려합니다. 계속하시겠습니까?',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			    }), $.extend({}, vex.dialog.buttons.NO, {
			      text: '취소'
			    })
			  ],
		 	    callback: function(data) {
		 	      if (data) {
			 	     userRejectDo(req_id);
		 	      }
		 	    }
	 		});
 	}
 	
 	function enrollPassInfo(adminId, date){
		vex.defaultOptions.className = 'vex-theme-os';
		vex.dialog.open({
			input: [
			         '<label>승인관리자</label>',			        
			         '<input name="adminId" type="text" readonly value=" '+ atob(adminId) +' " />',
			         '<label>승인일자</label>',			        
			         '<input name="date" type="text" readonly value=" '+ atob(date) +' " />'
			     ].join(''),
			buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '확인'
		    })]
 		});
 	}
 	
 	function enrollRejectInfo(adminId, date){
		vex.defaultOptions.className = 'vex-theme-os';
		vex.dialog.open({
			input: [
			         '<label>반려관리자</label>',			        
			         '<input name="adminId" type="text" readonly value=" '+ atob(adminId) +' " />',
			         '<label>반려일자</label>',			        
			         '<input name="date" type="text" readonly value=" '+ atob(date) +' " />'
			     ].join(''),
			buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '확인'
		    })]
 		});
 	}
 	
 	
 	function setDataTable(){
 		if (jQuery().dataTable) {

			var export_filename = 'Filename';
			
			var table = jQuery('#table_apply_policy');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/userenroll/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
						param.user_id 	= $('#filterUserId').val();
						param.user_name = $('#filterUserName').val();
						param.user_phone = $('#filterPhoneNumber').val();								
						param.user_duty 	= $('#filterDuty').val();
						param.user_rank 	= $('#filterRank').val();
						param.user_permit 	= $('#filterPermit option:selected').val();								
						param.start_date = $('#filterStartDate').val();
						param.end_date 	= $('#filterEndDate').val();
						
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
						    },
							columns: [0,3,4,5,6,7],
							format: {
								body: function ( data, row, column, node) {
									if (column === 5) {
										var status = data.trim();
										var strStatus = '';
										
										if(status == 'W'){
											strStatus = '대기';
										}else if(status == 'P'){
											strStatus = '승인완료';
										}else if(status == 'R'){
											strStatus = '반려처리';
										}
									
										return strStatus;
									
									} else {
										return data;	
									}
								}
			             	}
						}
					},  					              
					{
						text: '<i class="fa fa-lg fa-clipboard">프린트</i>',
						extend: 'print',
						className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-export-btn export-print ttip hidden',
						exportOptions: {
						    modifier: {
						        search: 'applied',
						        order: 'applied'
						    },
							columns: [0,3,4,5,6,7],
							format: {
								body: function ( data, row, column, node) {
									if (column === 5) {
										var status = data.trim();
										var strStatus = '';
										
										if(status == 'W'){
											strStatus = '대기';
										}else if(status == 'P'){
											strStatus = '승인완료';
										}else if(status == 'R'){
											strStatus = '반려처리';
										}
									
										return strStatus;
									
									} else {
										return data;	
									}
								}
			             	}
						},
						customize: function ( win ) {
		                    $(win.document.body)
		                        .css( 'font-size', '15px' )
		 
		                    $(win.document.body).find( 'table' )
		                        .addClass( 'compact' )
		                        .css( 'font-size', 'inherit' );
		                }
					}, 
			    ],
		 		"serverSide" : true,
		 	    "ordering": true,
				"columns": [{
					data: "deptName",
					"orderable": false	//부서명
				},{
					data: "memberDuty",
					"orderable": false	//
				}, {
					data: "memberRank",
					"orderable": false	//
				},{
					data: "userId",							
					"orderable": false	//아이디
				}, {
					data: "memberName",
					"orderable": false	//요청자명
				}, {
					data: "memberPhone",
					"orderable": false	//폰번호
				},  {
					data: "requestDate",
					"orderable": false	//요청일
				}, {
					data: "permit",
					"orderable": false	//승인여부
				}, {
					data: "permitDate",
					"orderable": false	//승인일자
				}, {
					data: "requestId",
					"orderable": false	//승인버튼
				}, {
					data: "permitAdmin",
					"orderable": false	//승인자
				}],
				// set the initial value
				"pageLength": 20,
				"iDisplayLength": 20,
				"pagingType": "bootstrap_full_number",
				"language": {
					"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 개 로그",
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
					"targets": [0],	// 부서명
					"class":"center-cell",
				}, {
					'targets': [1]	// 직책
					,"class":"center-cell"
				}, {	
					"targets": [2]	//계급
					,"class":"center-cell"
				}, {	
					"targets": [3]	//아이디
					,"class":"center-cell"
				}, {	
					"targets": [4],	//이름
					"class":"center-cell"
				}, {	
					"targets": [5]	//번호
					,"class" : "center-cell"
				}, {	
					"targets": [6]	//요청일
					,"class" : "center-cell"
				}, 
				{	
					"targets": [7]	//승인여부
					,"class" : "center-cell"
					,visible:false
					
				}, {	
					"targets": [8]	//승인일자
					,"class" : "center-cell"
/* 							,"render":function(data,type,row){
							if(data == ''){
								return '-'
							}else{
								return data;
							}
						}
						 */
					,visible : false
				}, {	
					"targets": [9]	//승인버튼
					,"class" : "center-cell"
					,"render":function(data,type,row){
							if(row.permit == 'W'){
								var uid = btoa(row.userId);
								
								var ret = '<button type="button" class="btn btn-success btn-xs" onclick="javascript:enrollConfirm(\''+ uid  +'\', '+data+')"><i class="fa fa-check" aria-hidden="true">&nbsp;승인</i></button>';
								ret +='<button type="button" class="btn btn-danger btn-xs" onclick="javascript:enrollReject(\''+ uid  +'\', '+data+')" ><i class="fa fa-remove" aria-hidden="true">&nbsp;미승인</i></button>'
								return ret
							}else if(row.permit == 'P'){
								var ret = '<button type="button" class="btn btn-info btn-xs" onclick="javascript:enrollPassInfo(\''+ btoa(row.permitAdmin)  +'\', \'' + btoa(row.permitDate) + '\')"><i class="fa fa-check" aria-hidden="true">&nbsp;승인완료</i></button>';
								return ret;
							}else if(row.permit == 'R'){
								var ret = '<button type="button" class="btn btn-purple btn-xs" onclick="javascript:enrollRejectInfo(\''+ btoa(row.permitAdmin)  +'\', \'' + btoa(row.permitDate) + '\')"><i class="fa fa-remove" aria-hidden="true">&nbsp;반려처리</i></button>';
								return ret;
							}
						}
													
				},{	
					"targets": [10]	//승인자
					,"class" : "center-cell"
					,visible:false
					
				}],						
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
//			        $('#table_apply_policy').colResizable({liveDrag:true});
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
		vex.defaultOptions.className = 'vex-theme-os'

		$(".select2theme").select2({
   			  minimumResultsForSearch: -1,
   			  dropdownAutoWidth : true,
   			  width: 'auto'
   		});
		
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