<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
	
	<style type="text/css">
table td {
	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
	-o-text-overflow:ellipsis;
	-ms-text-overflow:ellipsis;
}
	</style>
	
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
		<link href="${context}/assets/plugins/jstree/themes/default/style.min.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/css/color_scheme/black.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.jqueryui.min.css" rel="stylesheet" type="text/css"  />
	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 3000); %> 
			<% request.setAttribute("menu_sub_first", 3300); %> 
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
					<h1>서버감사로그</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">		
						<div class="col-md-12">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>서버감사로그</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
			
											<!-- Standard button -->
											<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle();"><i class="fa fa-filter" aria-hidden="true">&nbsp;검색필터</i></button>
		
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
															<td width="15%">IP</td>
															<td>
																<input type="text" name="filterUserIp" id="filterUserIp" value="" class="form-control required">
															</td>
															<td width="15%">작업정보</td>
															<td>
																<input type="text" name="filterDescription" id="filterDescription" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="15%">작업파라메터</td>
															<td>
																<input type="text" name="filterParameter" id="filterParameter" value="" class="form-control required">
															</td>
															<td width="15%">상태</td>
															<td>
																<input type="text" name="filterStatus" id="filterStatus" value="" class="form-control required">
															</td>
														</tr>

														<tr>         
															<td>작업시작일</td>
															<td>
							<input type="text" class="form-control datepicker" id="filterStartDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
															</td>
															<td>작업종료일</td>
															<td>
							<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
															</td>
														</tr>
													</tbody>
												</table>	
												
												<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle();">접기</button>
																					
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
														<th >번호</th>											
														<th>IP</th>
														<th>접속ID</th>
														<th>작업정보</th>
														<th>작업파라메터</th>
														<th >작업시간</th>
														<th >상태</th>														
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
	
	
	
<div id="detail-work-modal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="myModalLabel">파라메터정보</h4>
			</div><!-- /Modal Header -->

			<!-- body modal -->
			<div class="modal-body clearfix" id="detail-modal-data">
				<p>
				</p>
			</div>

		</div>
	</div>
</div>
	
	
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
<script>
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
 	
 	function onModalDetail(data){
		console.log(data)
 		
		$.ajax({
	        type: "GET",
	        url: "${context}/ax/audit/client/work",
	        async : false,
			data : {
				audit_id : data,
			},
	        beforeSend : function(){
				jQuery('#preloader').show();
			},
	        success: function(resultText)
	        {
	        	$('#detail-modal-data').html(resultText);
				jQuery('#preloader').hide();
				$('#detail-work-modal').modal('show');
	        },
	        error: function() {
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
					"url":'${context}/ax/audit/server/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
						param.user_id = $('#filterUserId').val();
						param.user_name = $('#filterUserName').val();

						param.user_ip = $('#filterUserIp').val();
						param.description = $('#filterDescription').val();
						param.parameter = $('#filterParameter').val();
						param.status = $('#filterStatus').val();
						
						param.start_date = $('#filterStartDate').val();
						param.end_date = $('#filterEndDate').val();
						
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
						                columns: [0,1,2,3,4,5,6],
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
					                columns: [0,1,2,3,4,5,6],
			                      modifier: {
			                          search: 'applied',
			                          order: 'applied'
			                      }
			                  }
			              }, 

			     ],
		 		"serverSide" : true,
		 	    "ordering": true,
				"columns": [
				{
					data: "auditNo",
					"orderable": false	//
				},{
					data: "ipAddr",							
					"orderable": false	//
				}, {
					data: "adminId",
					"orderable": false	//
				}, {
					data: "description",
					"orderable": false	//
				}, {
					data: "parameter",
					"orderable": false	//
				}, {
					data: "auditTime",
					"orderable": false	//
				},
				{
					data: "status",
					"orderable": false	//
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
					"targets": [0]	// audit_id
					,"class" : "center-cell"							
				},{	
					"targets": [1],	//
					"class":"center-cell",
				},         
				{  // set default column settings
					'targets': [2]	
					,"class":"center-cell"
				}, {	
					"targets": [3]	
					,"class":"center-cell"
				}, {	
					"targets": [4]	
					,"class":"center-cell"
					,"render":function(data,type,row){
						if(data.length > 30){
							return '<i title="상세보기" class="fa fa-search" aria-hidden="true" onclick="javascript:onModalDetail('+ row.auditNo +')">';
							
						}else{
							return data;
						}
					}
				}, {	
					"targets": [5],	
					"class":"center-cell"
				}, {	
					"targets": [6]	
					,"class" : "center-cell"
				}],						
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
//					$('#table_userinfo').colResizable({liveDrag:true});

				}
			});
			
			function fnFormatDetails(oTable, nTr) {
				var aData = oTable.fnGetData(nTr);
				var sOut = '<table class="table fixed"  style="width:100%;overflow:auto">';
				sOut += '<tr><td class="center-cell">MAC:</td><td>' + aData.macAddr + '</td>';
				sOut += '<td class="center-cell">PC명:</td><td>' + aData.pcName + '</td></tr>';
				sOut += '<tr><td class="center-cell">PC명:</td><td>' + aData.serverTime + '</td><td></td><td></td></tr>';
				
				sOut += '<td class="center-cell"></td><td></td></tr>';
										
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
				} else {
					/* Open this row */
					jQuery(this).addClass("datatables-open").removeClass("datatables-close");
					table.fnOpen(nTr, fnFormatDetails(table, nTr), 'details');
				}
			});
		}
 	}
 	
	$(document).ready(function(){
		
		$(".select2theme").select2({
   			  minimumResultsForSearch: -1,
   			  dropdownAutoWidth : true,
   			  width: 'auto'
   		});
		setDataTable();

jQuery('#preloader').hide();
       
    });
</script>
	</body>
</html>