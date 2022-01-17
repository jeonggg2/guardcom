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
		
		<script type="text/javascript" src="${context}/assets/js/admin_function.js"></script>
		<script type="text/javascript" src="${context}/assets/js/date.js"></script>
		
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
			<% request.setAttribute("menu_sub_first", 3200); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>정책변경요청</h1>
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
										<strong>사용자요청리스트</strong> <!-- panel title -->
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
															<td width="15%">사번</td>
															<td>
																<input type="text" name="filterUserNumber" id="filterUserNumber" value="" class="form-control">
															</td>
															<td width="15%">연락처</td>
															<td>
																<input type="text" name="filterUserPhone" id="filterUserPhone" value="" class="form-control">
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
															<td>승인여부</td>
															<td>
																<select class="select2theme" id="filterPermit" name="filterPermit">
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
										</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-bordered x-scroll-table" id="table_request_info" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th></th>
														<th>요청코드</th>
														<th>부서</th>
														<th>아이디</th>
														<th>이름</th>
														<th>사번</th>
														<th>직책</th>
														<th>계급</th>
														<th>연락처</th>
														<th>요청시간</th>

														<th>승인여부</th>
														<th>승인일시</th>
														<th>승인자</th>
														<th>agnt삭제</th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>

														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>

														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>

														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>

														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>

														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>

														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th>요청사유</th>
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
		
		<!-- 정책 정보 Ajax PopUp Div -->
		<div id="pop_policy_setting_info"></div>

<script>

	
	var getFilterInfo = function(){
		var param = new Object();
		param.user_id = $('#filterUserId').val();
		param.user_name = $('#filterUserName').val();
		param.user_phone = $('#filterUserPhone').val();
		param.dept = getCheckedDept();

		return param;
	};
	
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
 		var datatable = $('#table_request_info').dataTable().api();
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
 	
 	function fn_sel_policy_detailOpen(type, code){
 		
		if(type == 'isWatermark') {
			alert('준비중입니다.');
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
 	
 	function fn_permit_request_policy(pol, req, user_id) {
 		
 		$.ajax({      
		    type:"POST",  
		    url:'${context}/admin/user/req/permit',
		    async: false,
		    data:{
		    	policy_no : pol,
		    	request_no : req,
		    	user_id : user_id,
		    	_ : $.now()
		    },
		    success:function(data){
		    	if(data.returnCode == "S") {
		    		var datatable = $('#table_request_info').dataTable().api();
		 			datatable.ajax.reload();
		    		
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책이 승인 되었습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })]
	    			})
		    		
		    	} else {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책 승인이 실패했습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })]
	    			})
		    	}
		    },   
		    error:function(e){  
		        console.log(e.responseText);  
		    }  
		});
 	}
 	
 	function fn_reject_request_policy(req, user_id) {
 		
 		$.ajax({      
		    type:"POST",  
		    url:'${context}/admin/user/req/reject',
		    async: false,
		    data:{
		    	request_no : req,
		    	user_id : user_id,
		    	_ : $.now()
		    },
		    success:function(data){
		    	if(data.returnCode == "S") {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책 변경 요청이 미승인 되었습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })],
	    				  callback: function(data) {
    				 	  	if (data) {
    				 	  		var datatable = $('#table_request_info').dataTable().api();
    				 			datatable.ajax.reload();
    				 	    }
    				 	  }
	    				  
	    			})
		    		
		    	} else {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책 변경 요청 미승인이 실패했습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })]
	    			})
		    	}
		    },   
		    error:function(e){  
		        console.log(e.responseText);  
		    }  
		});
 	}
 	
 	function requestPermitInfo(adminId, date){
		vex.defaultOptions.className = 'vex-theme-os';
		vex.dialog.open({
			input: [
			         '<label>승인관리자</label>',			        
			         '<input name="adminId" type="text" readonly value=" '+ adminId +' " />',
			         '<label>승인일자</label>',			        
			         '<input name="date" type="text" readonly value=" '+ date +' " />'
			     ].join(''),
			buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '확인'
		    })]
 		});
 	}
 	
 	function requestRejectInfo(adminId, date){
		vex.defaultOptions.className = 'vex-theme-os';
		vex.dialog.open({
			input: [
			         '<label>반려관리자</label>',			        
			         '<input name="adminId" type="text" readonly value=" '+ adminId +' " />',
			         '<label>반려일자</label>',			        
			         '<input name="date" type="text" readonly value=" '+ date +' " />'
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
			
			var table = jQuery('#table_request_info');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/admin/policy/requested/list',
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
						param.policy_permit = $('#filterPermit option:selected').val();	
						
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
	        				modifier: {
					            search: 'applied',
					            order: 'applied'
	        				},
		    				columns: [2,3,4,8,9,10,76],
	    	  				format: {
	  							body: function ( data, row, column, node) {
		  							 if (column === 5) {
	                                    var strStatus = $(node).text().trim();
	                                    
	                                    if (strStatus != '승인완료' && strStatus != '반려처리') {
	                                        strStatus = '대기';
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
							columns: [2,3,4,8,9,10,76],
	    	  				format: {
	  							body: function ( data, row, column, node) {
		  							 if (column === 5) {
		                                    var strStatus = $(node).text().trim();
		                                    
		                                    if (strStatus != '승인완료' && strStatus != '반려처리') {
		                                        strStatus = '대기';
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
					}
				],
		 		"serverSide" : true,
		 		"processing": true,
		 	    "ordering": true,
				"columns": [{
					data: "userNo",							
					"orderable": false	//추가정보
				}, {
					data: "requestNo",
					"orderable": false	//요청코드
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
					data: "userNumber",
					"orderable": false	//사번
				},{
					data: "duty",
					"orderable": false	//직책
				}, {
					data: "rank",
					"orderable": false	//계급
				}, {
					data: "phone",
					"orderable": false	//연락
				}, {
					data: "reqClientTime",
					"orderable": false	//요청시간
					,render : function (data,type,row) {
						var time = '';
						if (data != '') {
							time = new Date(data).format('yyyy-MM-dd hh:mm:ss');
						} 
						return data;
					}
				}, {
					data: "permitState",
					"orderable": false	//승인여부
					,render : function (data,type,row) {
						var tag = '';
						var state = data;
						if (state == 'W') {
							tag += '<button type="button" class="btn btn-success btn-xs" onclick="javascript:fn_permit_request_policy(' + row.oldPolicy.policyNo + ','+ row.requestNo +',\''+ row.userId +'\');"><i class="fa fa-check" aria-hidden="true">&nbsp;승인</i></button>';
							tag +='<button type="button" class="btn btn-danger btn-xs" onclick="javascript:fn_reject_request_policy('+ row.requestNo +',\''+ row.userId +'\');" ><i class="fa fa-remove" aria-hidden="true">&nbsp;미승인</i></button>'
						} else if (state == 'P') {
							tag += '<button type="button" class="btn btn-info btn-xs" onclick="javascript:requestPermitInfo(\''+ row.permitStaf  +'\', \'' + row.permitDate + '\')"><i class="fa fa-check" aria-hidden="true">&nbsp;승인완료</i></button>';
						} else if (state == 'R') {
							tag += '<button type="button" class="btn btn-purple btn-xs" onclick="javascript:requestRejectInfo(\''+ row.permitStaf  +'\', \'' + row.permitDate + '\')"><i class="fa fa-remove" aria-hidden="true">&nbsp;반려처리</i></button>';
						}
						
						return tag;
					}
				}, {
					data: "permitDate",
					"orderable": false	//승인일시
				}, {
					data: "permitStaf",
					"orderable": false	//승인자
				}, {
					data: "isUninstall",
					"orderable": false	//요청 에이전트삭제가능
				}, {
					data: "isFileEncryption",
					"orderable": false	//요청 파일실시간암호화
				}, {
					data: "isCdEncryption",
					"orderable": false	//요청 CD실시간암호화
				}, {
					data: "isPrint",
					"orderable": false	//요청 프린트사용여부
				}, {
					data: "isCdEnabled",
					"orderable": false	//요청 CD사용가능여부
				}, {
					data: "isCdExport",
					"orderable": false	//요청 CD반출여부
				}, {
					data: "isWlan",
					"orderable": false	//요청 무선랜사용가능여부
				}, {
					data: "isNetShare",
					"orderable": false	//요청 공유폴더사용여부
				}, {
					data: "isWebExport",
					"orderable": false	//요청 메일반출여부
				}, {
					data: "isSensitiveDirEnabled",
					"orderable": false	//요청 보호폴더 접근 사용여부
				}, {
					data: "isSensitiveFileAccess",
					"orderable": false	//요청 민감파일 접근 여부
				}, {
					data: "isUsbControlEnabled",
					"orderable": false	//요청 USB통제 여부
				}, {
					data: "isStorageExport",
					"orderable": false	//요청 디스크반출가능 여부
				}, {
					data: "isStorageAdmin",
					"orderable": false	//요청 디스크 관리자 여부
				}, {
					data: "isUsbBlock",
					"orderable": false	//요청 USB포트사용여부
				}, {
					data: "usbBlockCode",
					"orderable": false	//요청 USB차단코드
				}, {
					data: "isComPortBlock",
					"orderable": false	//요청 시리얼포트사용여부
				}, {
					data: "comPortBlockCode",
					"orderable": false	//요청 시리얼포트차단코드
				}, {
					data: "isNetPortBlock",
					"orderable": false	//요청 네트워크포트사용여부
				}, {
					data: "netPortBlockCode",
					"orderable": false	//요청 네트워크포트차단코드
				}, {
					data: "isProcessList",
					"orderable": false	//요청 프로세스차단여부
				}, {
					data: "processListCode",
					defaultContent: "",
					"orderable": false	//요청 프로세스차단코드
				}, {
					data: "isFilePattern",					
					"orderable": false	//요청 민감패턴차단여부
				}, {
					data: "filePatternCode",
					defaultContent: "",
					"orderable": false	//요청 민감패턴차단코드
				}, {
					data: "isWebAddr",
					"orderable": false	//요청 사이트차단여부
				}, {
					data: "webAddrCode",
					"orderable": false	//요청 사이트차단코드
				}, {
					data: "isMsgBlock",
					"orderable": false	//요청 메신저차단여부
				}, {
					data: "msgBlockCode",
					"orderable": false	//요청 메신저차단코드
				}, {
					data: "isWatermark",
					"orderable": false	//요청 워터마크
				}, {
					data: "watermarkCode",
					"orderable": false	//요청 워터적용코드
				}, {
					data: "printLogDesc",
					"orderable": false	//요청 프린터인쇄로그설정
				}, {
					data: "oldPolicy.policyNo",
					"orderable": false	//기존 정책코드
				}, {
					data: "oldPolicy.isUninstall",
					"orderable": false	//기존 에이전트삭제가능
				}, {
					data: "oldPolicy.isFileEncryption",
					"orderable": false	//기존 파일실시간암호화
				}, {
					data: "oldPolicy.isCdEncryption",
					"orderable": false	//기존 CD실시간암호화
				}, {
					data: "oldPolicy.isPrint",
					"orderable": false	//기존 프린트사용여부
				}, {
					data: "oldPolicy.isCdEnabled",
					"orderable": false	//기존 CD사용가능여부
				}, {
					data: "oldPolicy.isCdExport",
					"orderable": false	//기존 CD반출여부
				}, {
					data: "oldPolicy.isWlan",
					"orderable": false	//기존 무선랜사용가능여부
				}, {
					data: "oldPolicy.isNetShare",
					"orderable": false	//기존 공유폴더사용여부
				}, {
					data: "oldPolicy.isWebExport",
					"orderable": false	//기존 메일반출여부
				}, {
					data: "oldPolicy.isSensitiveDirEnabled",
					"orderable": false	//기존 보호폴더 접근 사용여부
				}, {
					data: "oldPolicy.isSensitiveFileAccess",
					"orderable": false	//기존 민감파일 접근 여부
				}, {
					data: "oldPolicy.isUsbControlEnabled",
					"orderable": false	//기존 USB통제 여부
				}, {
					data: "oldPolicy.isStorageExport",
					"orderable": false	//기존 디스크반출가능 여부
				}, {
					data: "oldPolicy.isStorageAdmin",
					"orderable": false	//기존 관리자 여부
				}, {
					data: "oldPolicy.isUsbBlock",
					"orderable": false	//기존 USB포트사용여부
				}, {
					data: "oldPolicy.usbBlockCode",
					"orderable": false	//기존 USB차단코드
				}, {
					data: "oldPolicy.isComPortBlock",
					"orderable": false	//기존 시리얼포트사용여부
				}, {
					data: "oldPolicy.comPortBlockCode",
					"orderable": false	//기존 시리얼포트차단코드
				}, {
					data: "oldPolicy.isNetPortBlock",
					"orderable": false	//기존 네트워크포트사용여부
				}, {
					data: "oldPolicy.netPortBlockCode",
					"orderable": false	//기존 네트워크포트차단코드
				}, {
					data: "oldPolicy.isProcessList",
					"orderable": false	//기존 프로세스차단여부
				}, {
					data: "oldPolicy.processListCode",
					defaultContent: "",
					"orderable": false	//기존 프로세스차단코드
				}, {
					data: "oldPolicy.isFilePattern",
					"orderable": false	//기존 민감패턴차단여부
				}, {
					data: "oldPolicy.filePatternCode",
					defaultContent: "",					
					"orderable": false	//기존 민감패턴차단코드
				}, {
					data: "oldPolicy.isWebAddr",
					"orderable": false	//기존 사이트차단여부
				}, {
					data: "oldPolicy.webAddrCode",
					"orderable": false	//기존 사이트차단코드
				}, {
					data: "oldPolicy.isMsgBlock",
					"orderable": false	//기존 메신저차단여부
				}, {
					data: "oldPolicy.msgBlockCode",
					"orderable": false	//기존 메신저차단코드
				}, {
					data: "oldPolicy.isWatermark",
					"orderable": false	//기존 워터마크
				}, {
					data: "oldPolicy.watermarkCode",
					"orderable": false	//기존 워터적용코드
				}, {
					data: "oldPolicy.printLogDesc",
					"orderable": false	//기존 프린터인쇄로그설정
				}, {
					data: "reqNotice",
					"orderable": false	//요청사유
				}],
				// set the initial value
				"pageLength": 20,
				"iDisplayLength": 20,
				"pagingType": "bootstrap_full_number",
				"language": {
					"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 사용자",
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
				"columnDefs": [{	
					"targets": [0],	//추가정보
					"class":"center-cell add_detail_info",
					"render":function(data,type,row){
						return '<span class="datables-td-detail datatables-close"></span>';
					}
				}, {
					'targets': [1]	//요청코드
					,"visible" : false
				}, {	
					"targets": [2]	//부서
					,"class":"center-cell"
				}, {	
					"targets": [3]	//아이디
					,"class":"center-cell"
				}, {	
					"targets": [4]	//이름
					,"class" : "center-cell"
				}, {	
					"targets": [5]	//사번
					,"class" : "center-cell"
				},{	
					"targets": [6]	//직책
					,"class" : "center-cell"
				}, {	
					"targets": [7]	//계급
					,"class" : "center-cell"
				}, {
					"targets": [8]	//연락처
					,"class" : "center-cell"
				}, {
					"targets": [9]	//요청시간
					,"class" : "center-cell"
				},{
					"targets": [10]	//승인여부
					,"class" : "center-cell"
				}, {
					"targets": [11]	//승인일시
					,"visible" : false	
				}, {
					"targets": [12]	//승인자
					,"visible" : false
				}, {
					"targets": [13]	//요청 에이전트삭제가능
					,"visible" : false
				}, {
					"targets": [14]	//요청 파일실시간암호화
					,"visible" : false
				}, {
					"targets": [15]	//요청 CD실시간암호화
				,"class" : "center-cell"
					,"visible" : false
				}, {
					"targets": [16]	//요청 프린트사용여부
					,"visible" : false
				}, {
					"targets": [17]	//요청 CD사용가능여부
					,"visible" : false
				}, {
					"targets": [18]	//요청 CD반출여부
					,"visible" : false
				}, {
					"targets": [19]	//요청 무선랜사용가능여부
					,"visible" : false
				}, {
					"targets": [20]	//요청 공유폴더사용여부
					,"visible" : false
				}, {
					"targets": [21]	//요청 메일반출여부
					,"visible" : false
				}, {
					"targets": [22]	//요청 보호폴더 접근 사용여부
					,"visible" : false
				}, {
					"targets": [23]	//요청 민감파일 접근 여부
					,"visible" : false
				}, {
					"targets": [24]	//요청 USB통제 여부
					,"visible" : false
				},{
					"targets": [25]	//요청 디스크반출가능 여부
					,"visible" : false
				}, {
					"targets": [26]	//요청 디스크 관리자 여부
					,"visible" : false
				}, {
					"targets": [27]	//요청 USB포트사용여부
					,"visible" : false
				}, {
					"targets": [28]	//요청 USB차단코드
					,"visible" : false
				}, {
					"targets": [29]	//요청 시리얼포트사용여부
					,"visible" : false
				}, {
					"targets": [30]	//요청 시리얼포트차단코드
					,"visible" : false
				}, {
					"targets": [31]	//요청 네트워크포트사용여부
					,"visible" : false
				}, {
					"targets": [32]	//요청 네트워크포트차단코드
					,"visible" : false
				}, {
					"targets": [33]	//요청 프로세스차단여부
					,"visible" : false
				}, {
					"targets": [34]	//요청 프로세스차단코드
					,"visible" : false
				}, {
					"targets": [35]	//요청 민감패턴차단여부
					,"visible" : false
				}, {
					"targets": [36]	//요청 민감패턴차단코드
					,"visible" : false
				}, {
					"targets": [37]	//요청 사이트차단여부
					,"visible" : false
				}, {
					"targets": [38]	//요청 사이트차단코드
					,"visible" : false
				}, {
					"targets": [39]	//요청 메신저차단여부
					,"visible" : false
				}, {
					"targets": [40]	//요청 메신저차단코드
					,"visible" : false
				}, {
					"targets": [41]	//요청 워터마크
					,"visible" : false
				}, {
					"targets": [42]	//요청 워터적용코드
					,"visible" : false
				}, {
					"targets": [43]	//요청 워터마크적용일시
					,"visible" : false
				}, {
					"targets": [44]	//요청 프린터인쇄로그설정
					,"visible" : false
				}, {
					"targets": [45]	//기존 정책코드
					,"visible" : false
				}, {
					"targets": [46]	//기존 에이전트삭제가능
					,"visible" : false
				}, {
					"targets": [47]	//기존 파일실시간암호화
					,"visible" : false
				}, {
					"targets": [48]	//기존 CD실시간암호화
					,"visible" : false
				}, {
					"targets": [49]	//기존 프린트사용여부
					,"visible" : false
				}, {
					"targets": [50]	//기존 CD사용가능여부
					,"visible" : false
				}, {
					"targets": [51]	//기존 CD반출여부
					,"visible" : false
				}, {
					"targets": [52]	//기존 무선랜사용가능여부
					,"visible" : false
				}, {
					"targets": [53]	//기존 공유폴더사용여부
					,"visible" : false
				}, {
					"targets": [54]	//기존 메일반출여부
					,"visible" : false
				}, {
					"targets": [55]	//기존 보호폴더 접근 사용여부
					,"visible" : false
				}, {
					"targets": [56]	//기존 민감파일 접근 여부
					,"visible" : false
				}, {
					"targets": [57]	//기존 USB통제 여부
					,"visible" : false
				}, {
					"targets": [58]	//기존 디스크반출가능 여부
					,"visible" : false
				}, {
					"targets": [59]	//기존 디스크 관리자 여부
					,"visible" : false
				}, {
					"targets": [60]	//기존 USB포트사용여부
					,"visible" : false
				}, {
					"targets": [61]	//기존 USB차단코드
					,"visible" : false
				}, {
					"targets": [62]	//기존 시리얼포트사용여부
					,"visible" : false
				}, {
					"targets": [63]	//기존 시리얼포트차단코드
					,"visible" : false
				}, {
					"targets": [64]	//기존 네트워크포트사용여부
					,"visible" : false
				}, {
					"targets": [65]	//기존 네트워크포트차단코드
					,"visible" : false
				}, {
					"targets": [66]	//기존 프로세스차단여부
					,"visible" : false
				}, {
					"targets": [67]	//기존 프로세스차단코드
					,"visible" : false
				}, {
					"targets": [68]	//기존 민감패턴차단여부
					,"visible" : false
				}, {
					"targets": [69]	//기존 민감패턴차단코드
					,"visible" : false
				}, {
					"targets": [70]	//기존 사이트차단여부
					,"visible" : false
				}, {
					"targets": [71]	//기존 사이트차단코드
					,"visible" : false
				}, {
					"targets": [72]	//기존 메신저차단여부
					,"visible" : false
				}, {
					"targets": [73]	//기존 메신저차단코드
					,"visible" : false
				}, {
					"targets": [74]	//기존 워터마크
					,"visible" : false
				}, {
					"targets": [75]	//기존 워터적용코드
					,"visible" : false
				}, {
					"targets": [76]	//요청사유
					,"visible" : false
				}],					
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
//			        $('#table_request_info').colResizable({liveDrag:true});
				}
			});
			
			function fnFormatDetails(oTable, nTr) {
				var aData = oTable.fnGetData(nTr);
				var sOut = getRequestPolicyDetailTable(aData);

				return sOut;
			}
			
			var jTable = jQuery('#table_request_info');
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