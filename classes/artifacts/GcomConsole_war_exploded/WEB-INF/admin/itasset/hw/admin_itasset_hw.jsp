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
		
		<!-- Alert -->
		<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
        <link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

        <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
        <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 7000); %> 
			<% request.setAttribute("menu_sub_first", 7100); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>장비 현황</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div id="layout-container" class="row" style="width: 100%; height: 100%;">
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
										<strong>장비 목록</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
			
											<!-- search filter button -->
											<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle(1,initLayout);"><i class="fa fa-filter" aria-hidden="true">&nbsp;검색필터</i></button>
											<!-- search button -->
											<button type="button" class="btn btn-info" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;재검색</i></button>
											<!-- export button -->
											<button type="button" class="btn btn-primary pull-right" onclick="onClickExcelButton()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;내보내기</i></button>
											<!-- print button -->
											<button type="button" class="btn btn-success pull-right" onclick="onClickPrintButton()"><i class="fa fa-print" aria-hidden="true">&nbsp;인쇄</i></button>
											<!-- delete button -->
											<!-- <button type="button" class="btn btn-red pull-right" onclick="fn_delete_device()"><i class="fa fa-check" aria-hidden="true">&nbsp;삭제</i></button> -->
											
											<!-- search text content -->
											<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:700px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="15%">아이디</td>
															<td>
																<input type="text" name="filterUserId" id="filterUserId" value="" class="form-control">
															</td>
															<td width="15%">이름</td>
															<td>
																<input type="text" name="filterUserName" id="filterUserName" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">직책</td>
															<td>
																<input type="text" name="filterUserDuty" id="filterUserDuty" value="" class="form-control">
															</td>
															<td width="15%">계급</td>
															<td>
																<input type="text" name="filterUserRank" id="filterUserRank" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">PC명</td>
															<td>
																<input type="text" name="filterUserPCName" id="filterUserPCName" value="" class="form-control">
															</td>
															<td width="15%">IP</td>
															<td>
																<input type="text" name="filterUserIPAddr" id="filterUserIPAddr" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">사번</td>
															<td>
																<input type="text" name="filterUserNumber" id="filterUserNumber" value="" class="form-control">
															</td>
															<td width="15%">소프트웨어명</td>
															<td>
																<input type="text" name="filterSwName" id="filterSwName" value="" class="form-control">
															</td>
															<td width="15%">하드웨어명</td>
															<td>
																<input type="text" name="filterHwName" id="filterHwName" value="" class="form-control">
															</td>															
														</tr>
													</tbody>
												</table>	
												
												<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle(1,initLayout);">접기</button>
																					
											</div>
											<!-- /search text content -->
										</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-bordered x-scroll-table" id="table_hw_list" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th style="width:30px">상세</th>
														<th style="width:20px"><input type="checkbox" id="all_check_info" name="all_check_info" /></th>
														<th>부서</th>
														<th>아이디</th>
														<th>이름</th>
														<th>사번</th>
														<th>직책</th>
														<th>계급</th>								
														<th>IP</th>		
										                <th>MAC</th>								
										                <th>컴퓨터 이름</th>
										                <th>사용자 이름</th>	                	
										                <th>운영체제</th>
										                <th>운영체제 일련번호</th>
										                <th>운영체제 제품번호</th>
										                <th>운영체제 설치일자</th>
										                <th>CPU</th>
										                <th>메모리 크기</th>										                
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
		
		<div id="hw_delete_div"></div>		
		<div id="pop_hw_info"></div>
		
		<script src="${context}/assets/plugins/json2html.min.js"></script>
		<script type="text/javascript">
			var table;
			
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
			        error:function(e){  
			            console.log(e.responseText);  
			        }  
			    }); 
			}
		 	
		 	// 검색 버튼 클릭 시 
		 	function reloadTable(){
		 		var datatable = $('#table_hw_list').dataTable().api();
				datatable.ajax.reload();		 	
		 	}
			
		 	// 인쇄 버튼 클릭 시 
		 	function onClickPrintButton(){
		 		console.log('ptint')
		 		var $buttons = $('.export-print');
		 		$buttons.click();
		 	}
		 	
		 	// 내보내기 버튼 클릭 시 
		 	function onClickExcelButton(){
				console.log('excel')
		 		var $buttons = $('.export-csv');
		 		$buttons.click();		 		
		 	}
		 	
		 	// 삭제 버튼 클릭 시 
		 	function fn_delete_device() {
		 		var checkedLen = $("input:checkbox[name='hw_check']:checked").length;
		 		if (checkedLen < 1) {
		 			infoAlert("하나 이상의 장비를 선택해 주세요.");
		 			return false;
		 		}
		 		
		 		var apply_arr = new Array();
		 		gdTable = table.api();
				
				$(":checkbox[name='hw_check']:checked").each(function(pi,po){
					var check_row = $(this).parents('tr').get(0);
					var check_item = gdTable.row(check_row).data();
					apply_arr.push(check_item);
				});
				
				$.ajax({      
				    type:"POST",  
				    url:'${context}/admin/itasset/hw/delete',
				    async: false,
				    data:{
				    	apply_list : JSON.stringify(apply_arr),
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#policy_apply_div").html(data);
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
		 		
		 	}
		 	
		 	// 체크 박스 클릭 시 전체 체크 여부 확인
		 	var check_Info = function() {
				
		 		var checkboxLen =  $("input:checkbox[name='hw_check']").length;
		 		var checkedLen = $("input:checkbox[name='hw_check']:checked").length;
		
		 		if(checkboxLen == checkedLen){
		 			$("#all_check_info").prop("checked", true);
		 		} else {
		 			$("#all_check_info").prop("checked", false);
		 		}
		 		
		 	}
		 	
			function setDataTable(){
				if (jQuery().dataTable) {
					
					/*
			       jqxhr = $.ajax('${context}/admin/itasset/hw/list').done(function () {
			    	   data = JSON.parse(jqxhr.responseText);
			       });
			       */
					{
						var export_filename = 'Filename';	
						
						apTable = jQuery('#table_hw_list');	
						
						table = apTable.dataTable({
							"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB><"pull-right" ><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
							"ajax" : {
								"url":'${context}/admin/itasset/hw/list',
							   	"type":'POST',
							   	"dataSrc" : "data",
							   	"data" :  function(param) {
									param.user_id = $('#filterUserId').val();
									param.user_name = $('#filterUserName').val();
									param.user_number = $('#filterUserNumber').val();
									param.user_duty = $('#filterUserDuty').val();
									param.user_rank = $('#filterUserRank').val();
									param.user_pc = $('#filterUserPCName').val();
									param.user_ip = $('#filterUserIPAddr').val();
									param.user_phone = "";
									param.sw_name = $('#filterSwName').val();
									param.hw_name = $('#filterHwName').val();
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
						"buttons": [
								{
						        	text: '<i class="fa fa-lg fa-clipboard">csv</i>',
						            extend: 'csvHtml5',
						            className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-csv-btn export-csv ttip hidden',
						            bom: true
						     	},
						        {
					            	text: '<i class="fa fa-lg fa-clipboard">프린트</i>',
					                extend: 'print',
					                className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-export-btn export-print ttip hidden',
					             	customize: function ( win ) {
					                    $(win.document.body)
					                        .css( 'font-size', '6px' )
					 
					                    $(win.document.body).find( 'table' )
					                        .addClass( 'compact' )
					                        .css( 'font-size', 'inherit' );
					                 }
					              },
					     	],
					 		"serverSide" : true,
					 		"processing": true,
					 		ordering: true,
							"columns": [{
											data: "userNo",
											orderable: false
										}, {
											data: "agentNo",
											orderable: false,
											render: function(data,type,row){
												return '<input type="checkbox" name="hw_check" class="hw_check" value="' + data + '" onClick="javascript:check_Info()"/>';
											}
										}, {
											data: "deptName",
											orderable: false
										}, {
											data: "userId",
											orderable: false
										}, {
											data: "userName",
											orderable: false
										}, {
											data: "userNumber",
											orderable: false
										}, {
											data: "duty",
											orderable: false
										}, {
											data: "rank",
											orderable: false
										}, {
											data: "ipAddr",
											orderable: false
										}, {
											data: "macAddr",
											orderable: false
										}, {
											data: "pcName",
											orderable: false
										}, {
											data: "hwList",
											orderable: false,
											render: function ( data, type, row ) {		
												if (data == null) 
												return "";
												
					                        	var data = JSON.parse(data);
					                        	return data.user_name;
					                       }
										}, {
											data: "hwList",
											orderable: false,
											visible:false,
											render: function ( data, type, row ) {		
												if (data == null) 
												return "";
												
					                        	var data = JSON.parse(data);
					                        	return data.os_name + (data.os_csdver =='' ? '' : ' 서비스팩 ' + data.os_csdver) + ' ('+ data.os_arc +')';
					                       }
										}, {
											data: "hwList",
											orderable: false,
											visible:false,
											render: function ( data, type, row ) {		
												if (data == null) 
												return "";
												
					                        	var data = JSON.parse(data);
					                        	return data.os_sn;
					                       }
										},{
											data: "hwList",
											orderable: false,
											visible:false,
											render: function ( data, type, row ) {		
												if (data == null) 
												return "";
												
					                        	var data = JSON.parse(data);
					                        	return data.os_pk;
					                       }
										},{
											data: "hwList",
											orderable: false,
											visible:false,
											render: function ( data, type, row ) {		
												if (data == null) 
												return "";
												
					                        	var data = JSON.parse(data);
					                        	return data.os_install_date;
					                       }
										},{
											data: "hwList",
											orderable: false,
											render: function ( data, type, row ) {		
												if (data == null) 
												return "";
												
					                        	var data = JSON.parse(data);
					                        	return data.cpu_name;
					                       }
										},{
											data: "hwList",
											orderable: false,
											render: function ( data, type, row ) {		
												if (data == null) 
												return "";
												
					                        	var data = JSON.parse(data);
					                        	return data.ram_size;
					                       }
										},{
											data: "swList",
											orderable: false,
											visible:false,
											render: function ( data, type, row ) {	
												return "";
					                       }
										}],						
							"pageLength": 20,
							"iDisplayLength": 20,
							"pagingType": "bootstrap_full_number",
							"language": {
								"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 개",
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
								"targets": [0],	// 추가정보
								"class":"center-cell add_detail_info",
								"render":function(data,type,row){
									return '<span class="datables-td-detail datatables-close"></span>';
								}
							}],						
							"initComplete": function( settings, json ) {
								$('.export-print').hide();
							}
	
						});
						
			       }
			       
					function fnFormatDetails(oTable, nTr) {
						
						var aData = oTable.fnGetData(nTr);	
						
                    	var transform = {"tag":"tr","children":[
							{"tag":"td","html":"\${name}"},
							{"tag":"td","html":"\${ver}"},
							{"tag":"td","html":"\${install_date}"}
		               ]};
                    	
                  		
                    	var data = JSON.parse(aData.hwList);
                    	var os_name = data.os_name + (data.os_csdver =='' ? '' : ' 서비스팩 ' + data.os_csdver) + ' ('+ data.os_arc +')';
                    	var os_sn = data.os_sn;
                    	var os_pk = data.os_pk;
                    	var os_install_date = data.os_install_date;
                    	
                    	var sOut = "<table class='table table-striped' cellspacing = '0'>" +
		    			"<tthead> <tr>" + 
						"<th style='white-space: nowrap; width: 1%;'>운영체제 </th>" +
						"<th style='white-space: nowrap; width: 1%;'>일련번호</th>" +
						"<th style='white-space: nowrap; width: 1%;'>제품번호</th>" +
						"<th style='white-space: nowrap; width: 1%;'>설치 날짜</th>" + 
						"</tr> </thead> <tbody>" + 
                    	"<tr>" + "<td>" + os_name + "</td>" + "<td>" + os_sn + "</td>" + "<td>" + os_pk + "</td>" + "<td>" + os_install_date + "</td>" + "</tr>" +
                    	"</tbody> <table>";

                    	
						data = JSON.parse(aData.swList).data;
						
						var x = [];
						for (var i = 0; i < data.length; i++) {
						    if (data[i].install_location !== '') {
						        x.push(data[i]);
						    }
						}
						
						//var x=$(data).filter(function (i,n){return n.install_location !== ""});
						
					    sOut = sOut + "<table class='table table-striped' cellspacing = '0'>" +
					    			"<tthead> <tr>" + 
											"<th style='white-space: nowrap; width: 1%;'>소프트웨어 이름</th>" +
											"<th style='white-space: nowrap; width: 1%;'>버전</th>" + 
											"<th style='white-space: nowrap; width: 1%;'>설치 날짜</th>" + 
										"</tr> </thead> <tbody>"
									
						sOut = sOut + json2html.transform(x,transform) + "</tbody> <table>";	
						return sOut;
					}
					
					var jTable = jQuery('#table_hw_list');
					jTable.on('click', ' tbody td .datables-td-detail', function () {
						var nTr = jQuery(this).parents('tr')[0];
						var aData = table.fnGetData(nTr);	
						if (aData.swList == null) return;
						if (table.fnIsOpen(nTr)) {
							/* This row is already open - close it */
							jQuery(this).addClass("datatables-close").removeClass("datatables-open");
							table.fnClose(nTr);
							initLayout();
						} else {
							/* Open this row */
							jQuery(this).addClass("datatables-open").removeClass("datatables-close");
							table.fnOpen(nTr, fnFormatDetails(table, nTr), 'details');
							initLayout();
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
				//전체 체크 박스 선택 시
				$("#all_check_info").click(function(){					
				      if($(this).is(":checked")) {
				    	  $(".hw_check").prop("checked", true);
				      } else {
				    	  $(".hw_check").prop("checked", false);
				      }
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