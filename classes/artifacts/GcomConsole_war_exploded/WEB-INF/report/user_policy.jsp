<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
	
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Report</title>

		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />

		<!-- CORE CSS -->
		<link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- THEME CSS -->
		<link href="${context}/assets/css/essentials.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/layout.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/color_scheme/black.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/jstree/themes/default/style.min.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.jqueryui.min.css" rel="stylesheet" type="text/css"  />
	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<!-- 
				ASIDE 
				Keep it outside of #wrapper (responsive purpose)
			-->
			<% request.setAttribute("menu_parent", 3000); %> 
			<% request.setAttribute("menu_sub_first", 3100); %> 
			<jsp:include page="/WEB-INF/common/report_left_menu.jsp" flush="false" />
			
			<!-- /ASIDE -->
			<!-- HEADER -->
			<jsp:include page="/WEB-INF/common/report_top_navi.jsp" flush="false" />			
			<!-- /HEADER -->

			<!-- 
				MIDDLE 
			-->
			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>정책변경로그</h1>
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
										<strong>정책변경로그</strong> <!-- panel title -->
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
											<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:400px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="35%">아이디</td>
															<td>
																<input type="text" name="filterUserId" id="filterUserId" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="35%">이름</td>
															<td>
																<input type="text" name="filterUserName" id="filterUserName" value="" class="form-control required">
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
                                                        <th>정책정보</th>
                                                          
                                                        <th>에이전트삭제</th>   <!-- 11  -->
                                                        <th>프린트</th>
                                                        <th>워터마크</th>
                                                        <th>파일암호화</th>
                                                        <th>USB포트</th>
                                                        <th>시리얼포트</th>
                                                        <th>무선랜</th>
                                                        <th>패턴차단</th>
                                                        <th>민감파일</th>
                                                        <th>보호폴더</th>
                                                        <th>공유폴더</th>
                                                        <th>CD사용</th>
                                                        <th>적용시간</th>
                                                        <th>요청시간(서버)</th>
                                                        <th>요청시간(PC)</th>

                                                        <th>메일반출</th>                                                       
                                                        <th>디스크반출</th>
                                                        <th>디스크관리</th>
                                                        <th>USB통제</th>
                                                        
                                                        <th>USB차단코드</th>
                                                        <th>시리얼포트차단코드</th>
                                                        <th>네트워크포트</th>
                                                        <th>네트워크포트차단코드</th>
                                                        <th>프로그램</th>
                                                        <th>프로그램차단코드</th>
                                                        <th>사이트</th>
                                                        <th>사이트차단코드</th>
                                                        <th>메신저</th>
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
			<!-- USER 할당된 정책 정보 Ajax PopUp Div -->
		<div id="pop_policy_setting_info"></div>
	
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>

<script>

	//라디오타입에 따라 컬럼 hide/show
	var setColumnType = function(cType){
		
		var datatable = $('#table_userinfo').dataTable().api();
		var aColumn = datatable.columns('.agentinfo' );
		var uColumn = datatable.columns('.userinfo' );
		if(cType == 1){
			uColumn.visible(true);
			aColumn.visible(false);			

 			var jTable = $('#table_userinfo').dataTable();;

//			var nsTr = $('tbody > td > .datables-td-detail').parents('tr')[0];
			var nsTr = $('#table_userinfo tr');
			for(var i = 0; i < nsTr.length; i++){
				var nTr = nsTr[i];
				jTable.fnClose(nTr);
			}
		}else if(cType == 2){
			uColumn.visible(false);
			aColumn.visible(true);	

			var nsTr = $('#table_userinfo tr td').find('span.datables-td-detail');
			nsTr.addClass("datatables-close").removeClass("datatables-open");
		}		
	}

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
 	
 	
 	function setDataTable(){
 		if (jQuery().dataTable) {

			var export_filename = 'Filename';
			
			var table = jQuery('#table_userinfo');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/userpolicylog/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
						param.user_id = $('#filterUserId').val();
						param.user_name = $('#filterUserName').val();
						
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
				                          order: 'applied',
				                      },
				                      columns: [1,2,3,11,12,13,14,15,16,17,18,19,20,21,22,26,27,28,32,34,36,38],
									format: {
										body: function ( data, row, column, node) {
											if (column >= 3) {
												if(data == true)
													return '허용'
												else
													return '차단'
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
				                          order: 'applied',
			                     	 },
			                     	columns: [1,2,3,11,12,13,14,15,16,17,18,19,20,21,22,26,27,28,32,34,36,38],
									format: {
										body: function ( data, row, column, node) {
											if(column >= 3){
												if(data == true)
													return '허용'
												else
													return '차단'
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
		 	    "ordering": true,
				"columns": [{
					data: "exportNo",							
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
				},{
					data: "userNo",
					"orderable": false	//요약아이콘
				},{
					data: "isUninstall",
					"orderable": false	//에이전트삭제가능
				}, {
					data: "isPrint",
					"orderable": false	//프린트사용가능
				}, {
					data: "isWatermark",
					"orderable": false	//워터마크
				}, {
					data: "isFileEncryption",
					"orderable": false	//파일실시간암호화
				}, {
					data: "isUsbBlock",
					"orderable": false	//USB포트사용
				}, {
					data: "isComPortBlock",
					"orderable": false	//시리얼포트
				}, {
					data: "isWlan",
					"orderable": false	//무선랜사용가능
				}, {
					data: "isFilePattern",
					"orderable": false	//메일반출가능
				}, {
					data: "isSensitiveFileAccess",
					"orderable": false	//민감파일
				}, {
					data: "isSensitiveDirEnabled",
					"orderable": false	//보호폴더접근가능
				}, {
					data: "isNetShare",
					"orderable": false	//공유폴더사용여부
				}, {
					data: "isCdEnabled",
					"orderable": false	//CD사용여부
				}, {
					data: "applyTime",
					"orderable": false	//적용시간
				}, {
					data: "requestServerTime",
					"orderable": false	//요청시간
				}, {
					data: "requestClientTime",
					"orderable": false	//요청시간
				},{
					data: "isWebExport",
					"orderable": false	//메일반출여부
				},{
					data: "isStorageExport",
					"orderable": false	//디스크반출가능 여부
				},{
					data: "isStorageAdmin",
					"orderable": false	//디스크 관리자 여부
				},{
					data: "isUsbControlEnabled",
					"orderable": false	//USB통제 여부
				},{
					data: "usbBlockCode",
					"orderable": false	//USB차단코드
				},{
					data: "comPortBlockCode",
					"orderable": false	//시리얼포트차단코드
				},{
					data: "isNetPortBlock",
					"orderable": false	//네트워크포트사용여부
				},{
					data: "netPortBlockCode",
					"orderable": false	//네트워크포트차단코드
				},{
					data: "isProcessList",
					"orderable": false	//프로세스차단여부
				},{
					data: "processListCode",
					"orderable": false	//프로세스차단코드
				},{
					data: "isWebAddr",
					"orderable": false	//사이트차단여부
				},{
					data: "webAddrCode",
					"orderable": false	//사이트차단코드
				},{
					data: "isMsgBlock",
					"orderable": false	//메신저차단여부
				},{
					data: "msgBlockCode",
					"orderable": false	//메신저차단코드
				},{
					data: "watermarkCode",
					"orderable": false	//워터적용코드
				},{
					data: "printLogDesc",
					"orderable": false	//프린터인쇄로그설정
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
					,"visible" : true
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
					"targets": [10]	//요약
					,"class" : "center-cell"
					,"render" : function(data,type,row){
						return getPolicyIcon(row);
					}
				}, {	
					"targets": [11]	//에이전트삭제가능
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [12]	//프린트사용가능
					,"visible" : false
				}, {	
					"targets": [13]	//워터마크
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [14]	//파일실시간암호화
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [15]	//USB포트사용가능
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [16]	//시리얼포트사용가능
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [17]	//무선랜사용가능
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [18]	//메일반출가능
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [19]	//민감파일접근시삭제
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [20]	//보호폴더접근가능
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [21]	//공유폴더사용여부
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [22]	//CD사용여부
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [23]	//적용시간
					,"class" : "center-cell"
				}, {	
					"targets": [24]	//요청시간
					,"class" : "center-cell"
						,"visible" : false
				}, {	
					"targets": [25]	//요청시간(PC)
					,"class" : "center-cell"
						,"visible" : false
				}, {	
					"targets": [26]	//요청시간(PC)
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [27]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [28]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [29]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [30]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [31]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [32]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [33]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [34]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [35]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [36]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [37]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [38]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [39]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [40]	//요청시간(PC)
				,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [41]	//요청시간(PC)
					,"class" : "center-cell"
					,"visible" : false
				}],						
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
//					$('#table_userinfo').colResizable({liveDrag:true});

				}
			});
			
			function fnFormatDetails(oTable, nTr) {
				var aData = oTable.fnGetData(nTr);
				var sOut = getPolicyDetailTable(aData);

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

 	function initLayout() {
 		var hei = $('#panel-list').height();
 		$('#layout-container').height(hei);
 		$('#dept_tree').height(hei);
 		$('#layout-container').layout().resizeAll();
 	}
 	
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