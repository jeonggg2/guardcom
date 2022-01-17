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

			<!-- 
				ASIDE 2
				Keep it outside of #wrapper (responsive purpose)
			-->
			<% request.setAttribute("menu_parent", 4000); %> 
			<% request.setAttribute("menu_sub_first", 4200); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			
			<!-- /ASIDE -->
			<!-- HEADER -->
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			
			<!-- /HEADER -->

			<!-- 
				MIDDLE 
			-->
			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>디스크 관리</h1>
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
										<strong>디스크관리</strong> <!-- panel title -->
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
															<td width="15%">번호</td>
															<td>
																<input type="text" name="filterUserNumber" id="filterUserNumber" value="" class="form-control required">
															</td>
															<td width="15%">직책</td>
															<td>
																<input type="text" name="filterUserDuty" id="filterUserDuty" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="15%">계급</td>
															<td>
																<input type="text" name="filterUserRank" id="filterUserRank" value="" class="form-control required">
															</td>
															<td width="15%">레이블</td>
															<td>
																<input type="text" name="filterLabel" id="filterLabel" value="" class="form-control required">

 															</td>
														</tr>
														<tr>         
															<td width="15%">식별자</td>
															<td>
																<input type="text" name="filterGuid" id="filterGuid" value="" class="form-control required">
															</td>
															<td width="15%">하드웨어정보</td>
															<td>
																<input type="text" name="filterHwinfo" id="filterHwinfo" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="15%">타입</td>
															<td>
																<input type="text" name="filterType" id="filterType" value="" class="form-control required">
															</td>
															<td width="15%">상태</td>
															<td>
															
																<select class="select2theme" id="filterStatus" name="filterStatus">
																  <option value="">전체</option>
																  <option value="0">미허용</option>
																  <option value="1">허용</option>
																</select>
	
 															</td>
														</tr>														
														<tr>         
															<td >검색시작일</td>
															<td>
							<input type="text" class="form-control datepicker" id="filterStartDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
															</td>
															<td >검색종료일</td>
															<td>
							<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
															</td>

														</tr>																															
																												<tr>         
															<td width="15%">PC명</td>
															<td>
																<input type="text" name="filterPcName" id="filterPcName" value="" class="form-control required">
															</td>
	
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
											<table class="table table-striped table-bordered table-hover x-scroll-table" id="table_userinfo" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th style="width:20px"></th>
														<th>부서</th>
														<th>아이디</th>
														<th>이름</th>
														<th>번호</th>
														<th >직책</th>
														<th >계급</th>														
														<th >IP</th>
														<th >MAC</th>
														<th >PC이름</th>
														<th >레이블</th>
														<th >식별자</th>
														<th >하드웨어정보</th>
														<th >타입</th>
														<th >상태</th>
														<th >서버시간</th>
														<th >PC시간</th>
														<th >허용</th>
														<th >등록/차단</th>
													</tr>
												</thead>
				
												<tbody>
												</tbody>
											</table>
										
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
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	}
 	
 	function reloadTable(){
 		var datatable = $('#table_userinfo').dataTable().api();
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
 	
 	function setDataTable(){
 		if (jQuery().dataTable) {

			var export_filename = 'Filename';
			
			var table = jQuery('#table_userinfo');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/deviceinfo/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
						param.user_id = $('#filterUserId').val();
						param.user_name = $('#filterUserName').val();

						param.user_number = $('#filterUserNumber').val();
						param.user_duty = $('#filterUserDuty').val();
						param.user_rank = $('#filterUserRank').val();
						param.pc_name = $('#filterUserPCName').val();

						param.notice = $('#filterNotice').val();
						param.file_name = $('#filterFileName').val();
						param.pc_name = $('#filterPcName').val();

						param.guid = $('#filterGuid').val();
						param.label = $('#filterLabel').val();
						param.type = $('#filterType').val();
						param.hwinfo = $('#filterHwinfo').val();
						
						param.status = $('#filterStatus option:selected').val();

						param.start_date = $('#filterStartDate').val();
						param.end_date = $('#filterEndDate').val();
						
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
				lengthMenu: [[20, 100, 99999], [20, 100, "전체"]],
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
					                columns: [1,2,3,4,10,11,13,15,17],
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
					                columns: [1,2,3,4,10,11,13,15,17],
			                      modifier: {
			                          search: 'applied',
			                          order: 'applied'
			                      }
			                  }
			              }, 
			     ],
			    "serverSide": true,
		 		"processing": true,
		 	    "ordering": true,
				"columns": [{
					data: "diskNo",							
					"orderable": false	//추가정보
				}, {
					data: "deptName",
					"orderable": false	//부서
				}, {
					data: "userId",
					"orderable": false	//아이디
				}, {
					data: "userName",
					"orderable": false	//이름
				}, {
					data: "userNo",
					"orderable": false	//번호
				}, {
					data: "duty",
					"orderable": false	//직책
				}, {
					data: "rank",
					"orderable": false	//계급
				}, {
					data: "ipAddr",
					"orderable": false	//IP
				}, {
					data: "macAddr",
					"orderable": false	//MAC
				}, {
					data: "pcName",
					"orderable": false	//PC이름
				}, {
					data: "label",
					"orderable": false	//레이블
				}, {
					data: "guid",
					"orderable": false	//guid
				}, {
					data: "hwInfo",
					"orderable": false	//하드웨어정보
				}, {
					data: "type",
					"orderable": false	//타입
				}, {
					data: "status",
					"orderable": false	//상태
				}, {
					data: "createServerTime",
					"orderable": false	//서버시간
				},{
					data: "createClientTime",
					"orderable": false	//PC시간
				},{
					data: "status",
					"orderable": false	//상태
				},{
					data: "diskNo",
					"orderable": false	//상태
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
					"targets": [0],	//추가정보
					"class":"center-cell add_detail_info",
					"render":function(data,type,row){
						return '<span class="datables-td-detail datatables-close"></span>';
					}
				},         
				{  // set default column settings
					'targets': [1]	//부서
					,"class":"center-cell"
				}, {	
					"targets": [2]	//아이디
					,"class":"center-cell"
				}, {	
					"targets": [3]	//이름
					,"class":"center-cell"
				}, {	
					"targets": [4],	//번호
					"class":"center-cell"
				}, {	
					"targets": [5]	//직책
					,"class" : "center-cell"
				}, {	
					"targets": [6]	//계급
					,"class" : "center-cell"
				}, 
				{	
					"targets": [7]	//IP
					,"class" : "center-cell"
					,"visible" : false
					,"render":function(data,type,row){
							if(data == ''){
								return '-'
							}else{
								return data;
							}
						}								
				}, {	
					"targets": [8]	//MAC
					,"class" : "center-cell"
					,"visible" : false
						,"render":function(data,type,row){
							if(data == ''){
								return '-'
							}else{
								return data;
							}
						}								
				}, {	
					"targets": [9]	//PC이름
					,"class" : "center-cell"
					,"visible" : false
						,"render":function(data,type,row){
							if(data == ''){
								return '-'
							}else{
								return data;
							}
						}								
				}, {	
					"targets": [10]	//레이블
					,"class" : "center-cell"
				}, {	
					"targets": [11]	//guid
					,"class" : "center-cell"
				}, {	
					"targets": [12]	//하드웨어정보
					,"class" : "center-cell"
						,"visible" : false

				}, {	
					"targets": [13]	//타입
					,"class" : "center-cell"

				}, {	
					"targets": [14]	//상태
					,"class" : "center-cell"
					,"visible" : false	
				}, {	
					"targets": [15]	//서버시간
					,"class" : "center-cell"
				},{	
					"targets": [16]	//PC시간
					,"class" : "center-cell"
					,"visible" : false	
				},{	
					"targets": [17]	//버튼
					,"class" : "center-cell"
					,"render":function(data,type,row){
						if(data == '0'){
							return '미허용'
						}else{
							return '허용';
						}
					}	
				},{	
					"targets": [18]	//버튼
					,"class" : "center-cell"
					,"render":function(data,type,row){
						if(row.status == '0'){
							return '<button type="button" class="btn btn-xs btn-success" onclick="javascript:fn_allow_disk('+ data +', \'' + row.userId + '\');"><i aria-hidden="true"></i>허용등록</button>';
						}else{
							return '<button type="button" class="btn btn-xs btn-danger" onclick="javascript:fn_deny_disk('+ data +', \'' + row.userId + '\');"><i aria-hidden="true"></i>차단등록</button>';
						}
					}	
				}],						
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
				}
			});
			
			function fnFormatDetails(oTable, nTr) {
				var aData = oTable.fnGetData(nTr);
				var sOut = '<table class="table table-bordered">';
				sOut += '<col width="25%"><col width="25%"><col width="25%"><col width="25%">';
				sOut += '<tr><td class="center-cell th-cell-gray">MAC</td><td>' + aData.macAddr + '</td>';
				sOut += '<td class="center-cell th-cell-gray">PC명</td><td>' + aData.pcName + '</td></tr>';
				sOut += '<tr><td class="center-cell th-cell-gray">IP</td><td>' + aData.ipAddr + '</td>';
				sOut += '<td class="center-cell th-cell-gray">하드웨어정보</td><td>' + aData.hwInfo + '</td></tr>';
				sOut += '</tr>';
										
				sOut += '</table>';

				return sOut;
			}
			
			var jTable = jQuery('#table_userinfo');
			jTable.on('click', ' tbody td .datables-td-detail', function () {
				var nTr = jQuery(this).parents('tr')[0];
				if (table.fnIsOpen(nTr)) {
					/* This row is already open - close it */
					jQuery(this).addClass("datatables-close").removeClass("datatables-open");
					table.fnClose(nTr);
					initLayout()
				} else {
					/* Open this row */
					jQuery(this).addClass("datatables-open").removeClass("datatables-close");
					table.fnOpen(nTr, fnFormatDetails(table, nTr), 'details');
					initLayout()
				}
			});
		} 		
 	}
	function fn_allow_disk(key, user_id){
 		vex.defaultOptions.className = 'vex-theme-os'

 	 		vex.dialog.open({
 				message: '해당 디스크를 허용하시겠습니까??',
 			  buttons: [
 			    $.extend({}, vex.dialog.buttons.YES, {
 			      text: '확인'
 			    }), $.extend({}, vex.dialog.buttons.NO, {
 			      text: '취소'
 			    })
 			  ],
 		 	    callback: function(data) {
 		 	      if (data) {
 		 	    	updateDisk(key, 1, user_id);
 		 	      }
 		 	    }
 	 		});
		
	}
	
	function fn_deny_disk(key, user_id){
 		vex.defaultOptions.className = 'vex-theme-os'

 	 		vex.dialog.open({
 				message: '해당 디스크를 차단하시겠습니까??',
 			  buttons: [
 			    $.extend({}, vex.dialog.buttons.YES, {
 			      text: '확인'
 			    }), $.extend({}, vex.dialog.buttons.NO, {
 			      text: '취소'
 			    })
 			  ],
 		 	    callback: function(data) {
 		 	      if (data) {
 		 	    	updateDisk(key, 0, user_id);
 		 	      }
 		 	    }
 	 		});
		
	}
	
	function updateDisk(key, setData, user_id){
		$.ajax({      
	        type:"POST",  
	        url:'${context}/update/disk/info',
	        async: false,
	        data:{
	        	disk_no : key,
	        	status : setData,
	        	user_id : user_id
	        },
	        success:function(args){   
		 	    reloadTable()
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 

	}
	
	
 	
 	function onClickRemoveAdmin(text){
		console.log(admin_no)
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
		$('#panel-list').width();
		
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