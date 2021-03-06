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

			<% request.setAttribute("menu_parent", 3000); %> 
			<% request.setAttribute("menu_sub_first", 3100); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>????????????</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div id="layout-container" class="row" style="width: 100%; height: 100%;">
						<div class="ui-layout-west">
							<div id="panel-tree" class="panel panel-default">
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>?????????</strong> <!-- panel title -->
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
										<strong>???????????? ??????</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									
									<div class="row">
										<div class="col-md-12">
			
											<!-- search filter button -->
											<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle(1,initLayout);"><i class="fa fa-filter" aria-hidden="true">&nbsp;????????????</i></button>
											<!-- search button -->
											<button type="button" class="btn btn-info" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;?????????</i></button>
											<!-- export button -->
											<button type="button" class="btn btn-primary pull-right" onclick="onClickExcelButton()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;????????????</i></button>
											<!-- print button -->
											<button type="button" class="btn btn-success pull-right" onclick="onClickPrintButton()"><i class="fa fa-print" aria-hidden="true">&nbsp;??????</i></button>
											<!--policy apply button -->
											<button type="button" class="btn btn-blue pull-right" onclick="fn_apply_policy()"><i class="fa fa-check" aria-hidden="true">&nbsp;?????? ??????</i></button>
											<button type="button" class="btn btn-dirtygreen pull-right" onclick="fn_click_all_apply_policy()"><i class="fa fa-check" aria-hidden="true">&nbsp;?????? ?????? ??????</i></button>
											
											<!-- search text content -->
											<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:700px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="15%">?????????</td>
															<td>
																<input type="text" name="filterUserId" id="filterUserId" value="" class="form-control">
															</td>
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserName" id="filterUserName" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserDuty" id="filterUserDuty" value="" class="form-control">
															</td>
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserRank" id="filterUserRank" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">PC???</td>
															<td>
																<input type="text" name="filterUserPCName" id="filterUserPCName" value="" class="form-control">
															</td>
															<td width="15%">IP</td>
															<td>
																<input type="text" name="filterUserIPAddr" id="filterUserIPAddr" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserNumber" id="filterUserNumber" value="" class="form-control">
															</td>
															<td width="15%" style="visibility: hidden">?????????</td>
															<td style="visibility: hidden;">
																<input type="text" name="filterUserPhone" id="filterUserPhone" value="" class="form-control">
															</td>
														</tr>
													</tbody>
												</table>	
												
												<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle(1,initLayout);">??????</button>
																					
											</div>
											<!-- /search text content -->
										</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-bordered x-scroll-table" id="table_apply_policy" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th style="width:30px">??????</th>
														<th style="width:20px"><input type="checkbox" id="all_check_info" name="all_check_info" /></th>
														<th>??????</th>
														<th>?????????</th>
														<th>??????</th>
														<th>??????</th>
														<th>??????</th>
														<th>??????</th>														
														<th>IP</th>
														<th>MAC</th>
														<th>PC??????</th>
														<th>????????????</th>
                                                      
                                                        
                                                       	<th>??????NO</th> <!-- 12  -->
                                                        <th>??????????????????</th>   
                                                        <th>???????????????</th>
                                                        <th>CD?????????</th>
                                                        <th>?????????</th>
                                                        <th>CD</th>
                                                        <th>CD??????</th>
                                                        <th>?????????</th>
                                                        <th>????????????</th>
                                                        <th>????????????</th>
                                                        <th>????????????</th>
                                                        <th>????????????</th>
                                                        
                                                        <th>???????????????</th>
                                                        <th>???????????????</th>
                                                        
                                                        <th>USB??????</th>
                                                        <th>USB??????</th>
                                                        <th>USB????????????</th>
                                                        
                                                        <th>???????????????</th>

                                                        <th>???????????????????????????</th>

                                                        <th>??????????????????</th>
                                                        <th>??????????????????????????????</th>
                                                        <th>????????????</th>
                                                        <th>????????????????????????</th>
                                                        <th>????????????</th>
                                                        <th>????????????????????????</th>
                                                        <th>??????????????????????????????</th>
                                                        <th>?????????</th>
                                                        <th>?????????????????????</th>
                                                        <th>?????????</th>
                                                        <th>?????????????????????</th>
                                                        <th>????????????</th>
                                                        <th>??????????????????</th>
                                                        <th>???????????????????????????</th>
													 	
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
		
		<!-- ?????? ?????? Ajax Div -->
		<div id="policy_apply_div"></div>
		
		<!-- USER ????????? ?????? ?????? Ajax PopUp Div -->
		<div id="pop_policy_setting_info"></div>
		
		<script type="text/javascript">
			var table;
			
			// ????????? 
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
		 	
		 	// ?????? ?????? ?????? ??? 
		 	function reloadTable(){
		 		var datatable = $('#table_apply_policy').dataTable().api();
				datatable.ajax.reload();
		 	}
		
		 	// ?????? ?????? ?????? ??? 
		 	function onClickPrintButton(){
		 		console.log('ptint')
		 		var $buttons = $('.export-print');
		 		$buttons.click();
		 	}
		 	
		 	// ???????????? ?????? ?????? ??? 
		 	function onClickExcelButton(){
				console.log('excel')
		 		var $buttons = $('.export-csv');
		 		$buttons.click();
		 		
		 	}
		 	
		 	// ???????????? ?????? ?????? ??? 
		 	function fn_apply_policy() {
		 		var checkedLen = $("input:checkbox[name='policy_app_check']:checked").length;
		 		if (checkedLen < 1) {
		 			infoAlert("?????? ????????? ????????? ?????? ????????? ???????????? ????????? ?????????.");
		 			return false;
		 		}
		 		
		 		var apply_arr = new Array();
		 		gdTable = table.api();
				
				$(":checkbox[name='policy_app_check']:checked").each(function(pi,po){
					var check_row = $(this).parents('tr').get(0);
					var check_item = gdTable.row(check_row).data();
					apply_arr.push(check_item);
				});
				
				$.ajax({      
				    type:"POST",  
				    url:'${context}/admin/user/assign/apply',
				    async: false,
				    data:{
				    	apply_list : JSON.stringify(apply_arr),
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#policy_apply_div").html(data);
			            $('#modalApplyPolicy').modal('show').on("hidden.bs.modal", function () {
			            	//window.location = '${context}/admin/user/assign';
				    	});
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
		 		
		 	}
		 	
		 	// ?????????????????? ?????? ??????
		 	function fn_all_apply_policy() {
		 		
		 		$.ajax({      
				    type:"POST",  
				    url:'${context}/admin/user/assign/applyAll',
				    async: false,
				    data:{
				    	user_id 	: $('#filterUserId').val(),
						user_name 	: $('#filterUserName').val(),
						user_number : $('#filterUserNumber').val(),
						user_duty 	: $('#filterUserDuty').val(),
						user_rank 	: $('#filterUserRank').val(),
						user_pc 	: $('#filterUserPCName').val(),
						user_ip 	: $('#filterUserIPAddr').val(),
						user_phone 	: $('#filterUserPhone').val(),
						dept : getCheckedDept(),
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#policy_apply_div").html(data);
			            $('#modalApplyPolicy').modal('show');
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
		 	}
		 	
		 	// ?????????????????? ?????? ?????? ???
		 	function fn_click_all_apply_policy() {
		 		vex.defaultOptions.className = 'vex-theme-os'
	    			
    			vex.dialog.open({
    				message: '????????? ???????????? ?????? ???????????? ????????? ???????????????. ????????????????????? ?????????????????????????',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '??????'
    				 	 }),
    				  	$.extend({}, vex.dialog.buttons.NO, {
    				      text: '??????'
    				  })],
    				  callback: function(data) {
   				 	  	if (data) {
   				 	  		fn_all_apply_policy();
   				 	    }
   				 	  }
    				  
    			})
		 	};
		 	
		 	
		 	
		 	// ?????? ?????? ?????? ??? ?????? ?????? ?????? ??????
		 	var check_Info = function() {
				
		 		var checkboxLen =  $("input:checkbox[name='policy_app_check']").length;
		 		var checkedLen = $("input:checkbox[name='policy_app_check']:checked").length;
		
		 		if(checkboxLen == checkedLen){
		 			$("#all_check_info").prop("checked", true);
		 		} else {
		 			$("#all_check_info").prop("checked", false);
		 		}
		 		
		 	}
		 	
		 	function fn_sel_policy_detailOpen(type, code){
		 		
				if(type == 'isWatermark') {
					alert('??????????????????.');
					return false;
				}
		 		
		 		$.ajax({      
				    type:"POST",  
				    url:'${context}/admin/user/assign/setting/info',
				    async: false,
				    data:{
				    	type : type,
				    	code : code,
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#pop_policy_setting_info").html(data);
				    	
				    	if (type == 'isUsbBlock') {
				    		 $('#modalUsbSettingInfo').modal('show');
				    		 	usb_info_table();
				    	} else if (type == 'isComPortBlock') {
				    		$('#modalComPortSettingInfo').modal('show');
				    			 com_port_info_table();
				    	} else if (type == 'isNetPortBlock') {
				    		$('#modalNetPortSettingInfo').modal('show');
				    			net_port_info_table();
				    	} else if (type == 'isProcessList') {
				    		$('#modalProcessSettingInfo').modal('show');
				    			process_info_table();
				    	} else if (type == 'isFilePattern') {
				    		$('#modalPatternSettingInfo').modal('show');
				    			pattern_info_table();
				    	} else if (type == 'isWebAddr') {
				    		$('#modalWebAddrSettingInfo').modal('show');
				    			web_site_info_table();
				    	} else if (type == 'isMsgBlock') {
				    		$('#modalMsgBlockSettingInfo').modal('show');
				    			msg_block_info_table();
				    	} 
			           
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
		 	}
		 	
			function setDataTable(){
				if (jQuery().dataTable) {
					var export_filename = 'Filename';
					
					apTable = jQuery('#table_apply_policy');
					table = apTable.dataTable({

						"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB><"pull-right" ><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
						//dom: 'Bfrtip',
						"ajax" : {
							"url":'${context}/ax/policy/assign/list',
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
								param.user_phone = $('#filterUserPhone').val();								
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
					            bom: true,
					            exportOptions: {
						        	modifier: {
						            	search: 'applied',
						                order: 'applied'
						        	},
					                columns: [2,3,4,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,29,31,33,35,38,40,42],
									format: {
										body: function ( data, row, column, node) {
											if(column >= 3){
												if(data == true)
													return '??????'
												else
													return '??????'
											}else{
												return data;
											}

										}
					             	}
				             	 }
					     	},
					        {
				            	text: '<i class="fa fa-lg fa-clipboard">?????????</i>',
				                extend: 'print',
				                className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-export-btn export-print ttip hidden',
				                exportOptions: {
				                	modifier: {
				                		search: 'applied',
				                    	order: 'applied'
				                	},
				                	columns: [2,3,4,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,29,31,33,35,38,40,42],
									format: {
										body: function ( data, row, column, node) {
											if(column >= 3){
												if(data == true)
													return '??????'
												else
													return '??????'
											}else{
												return data;
											}
										}
					             	}
				             	 },
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
				 	    "ordering": true,
						"columns": [{
							data: "userNo",							
							"orderable": false	// ????????????
						}, {
							data: "agentNo",
							"orderable": false	// select box
							,"render":function(data,type,row){
								return '<input type="checkbox" name="policy_app_check" class="policy_app_check" value="' + data + '" onClick="javascript:check_Info()"/>';
							}
						}, {
							data: "deptName",
							"orderable": false	//??????
						}, {
							data: "userId",
							"orderable": false	//?????????
						}, {
							data: "userName",
							"orderable": false	//??????
						}, {
							data: "userNumber",
							"orderable": false	//??????
						}, {
							data: "duty",
							"orderable": false	//??????
						}, {
							data: "rank",
							"orderable": false	//??????
						}, {
							data: "ipAddr",
							"orderable": false	//IP
						}, {
							data: "macAddr",
							"orderable": false	//MAC
						}, {
							data: "pcName",
							"orderable": false	//PC??????
						}, {
							data: "policyNo",
							"orderable": false	//????????????
							,"render":function(data,type,row){
								return getPolicyIcon(row); 
							}
						}, {
							data: "policyNo",
							"orderable": false	//??????NO
						},{
							data: "isUninstall",
							"orderable": false	//????????????????????????
						},{
							data: "isFileEncryption",
							"orderable": false	//????????????????????????
						},{
							data: "isCdEncryption",
							"orderable": false	//CD??????????????????
						},{
							data: "isPrint",
							"orderable": false	//?????????????????????
						},{
							data: "isCdEnabled",
							"orderable": false	//CD??????????????????
						},{
							data: "isCdExport",
							"orderable": false	//CD????????????
						},{
							data: "isWlan",
							"orderable": false	//???????????????????????????
						},{
							data: "isNetShare",
							"orderable": false	//????????????????????????
						},{
							data: "isWebExport",
							"orderable": false	//??????????????????
						},{
							data: "isSensitiveDirEnabled",
							"orderable": false	//???????????? ?????? ????????????
						},{
							data: "isSensitiveFileAccess",
							"orderable": false	//???????????? ?????? ??????
						},{
							data: "isStorageExport",
							"orderable": false	//????????????????????? ??????
						},{
							data: "isStorageAdmin",
							"orderable": false	//????????? ????????? ??????
						},{
							data: "isUsbControlEnabled",
							"orderable": false	//USB?????? ??????
						},{
							data: "isUsbBlock",
							"orderable": false	//USB??????????????????
						},{
							data: "usbBlockCode",
							"orderable": false	//USB????????????
						},{
							data: "isComPortBlock",
							"orderable": false	//???????????????????????????
						},{
							data: "comPortBlockCode",
							"orderable": false	//???????????????????????????
						},{
							data: "isNetPortBlock",
							"orderable": false	//??????????????????????????????
						},{
							data: "netPortBlockCode",
							"orderable": false	//??????????????????????????????
						},{
							data: "isProcessList",
							"orderable": false	//????????????????????????
						},{
							data: "processListCode",
							"orderable": false	//????????????????????????
						},{
							data: "isFilePattern",
							"orderable": false	//????????????????????????
						},{
							data: "filePatternCode",
							"orderable": false	//????????????????????????
						},{
							data: "patternFileControl",
							"orderable": false	//??????????????????????????????
						},{
							data: "isWebAddr",
							"orderable": false	//?????????????????????
						},{
							data: "webAddrCode",
							"orderable": false	//?????????????????????
						},{
							data: "isMsgBlock",
							"orderable": false	//?????????????????????
						},{
							data: "msgBlockCode",
							"orderable": false	//?????????????????????
						},{
							data: "isWatermark",
							"orderable": false	//????????????
						},{
							data: "watermarkCode",
							"orderable": false	//????????????
						}, {
							data: "printLogDesc",
							"orderable": false	//???????????????????????????
						},{
							data: "quarantinePathAccessCode",
							"orderable": false	//???????????? ?????? ??????
						}],
						// set the initial value
						"pageLength": 20,
						"iDisplayLength": 20,
						"pagingType": "bootstrap_full_number",
						"language": {
							"info": " _PAGES_ ????????? ???  _PAGE_ ????????? / ??? _TOTAL_ ???",
							"infoEmpty": "????????? ???????????? ????????????.",
							"zeroRecords" :"????????? ???????????? ????????????.",
							"lengthMenu": "  _MENU_ ???",
							"paginate": {
								"previous":"Prev",
								"next": "Next",
								"last": "Last",
								"first": "First"
							},
							
						},
						"columnDefs": [
						{	
							"targets": [0],	// ????????????
							"class":"center-cell add_detail_info",
							"render":function(data,type,row){
								return '<span class="datables-td-detail datatables-close"></span>';
							}
						}, {
							'targets': [1]	// select box
							,"class":"center-cell"
						}, {	
							"targets": [2]	//??????
							,"class":"center-cell"
						}, {	
							"targets": [3]	//?????????
							,"class":"center-cell"
						}, {	
							"targets": [4],	//??????
							"class":"center-cell"
						}, {	
							"targets": [5]	//??????
							,"class" : "center-cell"
						}, {	
							"targets": [6]	//??????
							,"class" : "center-cell"
								,"visible" : false
						}, 
						{	
							"targets": [7]	//??????
							,"class" : "center-cell"
								,"visible" : false
						}, {	
							"targets": [8]	//IP
							,"class" : "center-cell"
								,"visible" : true
							,"render":function(data,type,row){
	 							if(data == ''){
	 								return '-'
	 							}else{
	 								return data;
	 							}
	 						}							
						}, {	
							"targets": [9]	//MAC
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
							"targets": [10]	//PC??????
							,"visible" : false
							,"class" : "center-cell"
							,"render":function(data,type,row){
	 							if(data == ''){
	 								return '-'
	 							}else{
	 								return data;
	 							}
	 						}	
						}, {	
							"targets": [11]	//????????????
							,"class" : "center-cell"
						}, {	
							"targets": [12]	//??????NO
							,"visible" : false
						}, {	
							"targets": [13]	// ????????????????????????
							,"visible" : false
						}, {	
							"targets": [14]	// ????????????????????????
							,"visible" : false
						}, {	
							"targets": [15]	// CD??????????????????
							,"visible" : false
						}, {	
							"targets": [16]	// ?????????????????????
							,"visible" : false
						}, {	
							"targets": [17]	// CD??????????????????
							,"visible" : false
						}, {	
							"targets": [18]	// CD????????????
							,"visible" : false
						}, {	
							"targets": [19]	// ???????????????????????????
							,"visible" : false
						}, {	
							"targets": [20]	// ????????????????????????
							,"visible" : false
						}, {	
							"targets": [21]	// ??????????????????
							,"visible" : false
						}, {	
							"targets": [22]	// ???????????? ?????? ????????????
							,"visible" : false
						},{	
							"targets": [23]	// ???????????? ?????? ??????
							,"visible" : false
						},{	
							"targets": [24]	// ????????????????????? ??????
							,"visible" : false
						},{	
							"targets": [25]	// ????????? ????????? ?????? 
							,"visible" : false
						},{	
							"targets": [26]	// USB?????? ??????
							,"visible" : false
						},{	
							"targets": [27]	// USB??????????????????
							,"visible" : false
						}, {	
							"targets": [28]	// USB????????????
							,"visible" : false
						}, {	
							"targets": [29]	// ???????????????????????????
							,"visible" : false
						}, {	
							"targets": [30]	// ???????????????????????????
							,"visible" : false
						}, {	
							"targets": [31]	// ??????????????????????????????
							,"visible" : false
						}, {	
							"targets": [32]	// ??????????????????????????????
							,"visible" : false
						}, {	
							"targets": [33]	// ????????????????????????
							,"visible" : false
						}, {	
							"targets": [34]	// ????????????????????????
							,"visible" : false
						}, {	
							"targets": [35]	// ????????????????????????
							,"visible" : false
						}, {	
							"targets": [36]	// ????????????????????????
							,"visible" : false
						}, {	
							"targets": [37]	// ??????????????????????????????
							,"visible" : false
						}, {	
							"targets": [38]	// ?????????????????????
							,"visible" : false
						}, {	
							"targets": [39]	// ?????????????????????
							,"visible" : false
						}, {	
							"targets": [40]	// ?????????????????????
							,"visible" : false
						}, {	
							"targets": [41]	// ?????????????????????
							,"visible" : false
						}, {	
							"targets": [42]	// ????????????
							,"visible" : false
						}, {	
							"targets": [43]	// ??????????????????
							,"visible" : false
						},  {	
							"targets": [44]	// ???????????????????????????
							,"visible" : false
						}, {	
							"targets": [45]	// ???????????? ?????? ??????
							,"visible" : false
						}],						
						"initComplete": function( settings, json ) {
							$('.export-print').hide();
//					        $('#table_apply_policy').colResizable({liveDrag:true});
						}

					});
					
					
					function fnFormatDetails(oTable, nTr) {
						var aData = oTable.fnGetData(nTr);
						var sOut = getApplyPolicyDetailItem(aData);
						return sOut;
					}
					
					var jTable = jQuery('#table_apply_policy');
					jTable.on('click', ' tbody td .datables-td-detail', function () {
						var nTr = jQuery(this).parents('tr')[0];
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

/* 				$(".select2theme").select2({
		   			  minimumResultsForSearch: -1,
		   			  dropdownAutoWidth : true,
		   			  width: 'auto'
		   		});
				 */
				//?????? ?????? ?????? ?????? ???
				$("#all_check_info").click(function(){
					
				      if($(this).is(":checked")) {
				    	  $(".policy_app_check").prop("checked", true);
				      } else {
				    	  $(".policy_app_check").prop("checked", false);
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