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
			<% request.setAttribute("menu_parent", 2000); %> 
			<% request.setAttribute("menu_sub_first", 2100); %> 
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
					<h1>비인가USB목록</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">
						<div class="col-md-12">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>비인가USB목록</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
			
											<!-- Standard button -->
											<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle();"><i class="fa fa-filter" aria-hidden="true">&nbsp;검색필터</i></button>
		
											<!-- Info -->
											<button type="button" class="btn btn-info" onclick="searchTableLog()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;재검색</i></button>
											
											
											<!-- Primary -->
											<button type="button" class="btn btn-primary pull-right" onclick="onClickExcelButton()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;내보내기</i></button>
											<!-- Success -->
											<button type="button" class="btn btn-success pull-right" onclick="onClickPrintButton()"><i class="fa fa-print" aria-hidden="true">&nbsp;인쇄</i></button>
											<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:700px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="15%">이름</td>
															<td>
																<input type="text" name="filterUsbName" id="filterUsbName" value="" class="form-control required">
															</td>
															<td width="15%">시리얼번호</td>
															<td>
																<input type="text" name="filterSerial" id="filterSerial" value="" class="form-control required">
															</td>

														</tr>
														<tr>         
															<td width="15%">vid</td>
															<td>
																<input type="text" name="filterVid" id="filterVid" value="" class="form-control required">
															</td>
															<td width="15%">pid</td>
															<td>
																<input type="text" name="filterPid" id="filterPid" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="15%">비고</td>
															<td>
																<input type="text" name="filterDesc" id="filterDesc" value="" class="form-control required">
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
											<table class="table table-striped table-bordered table-hover x-scroll-table" id="table_unauthinfo" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th>번호</th>
														<th>이름</th>
														<th>vid</th>
														<th>pid</th>
														<th>시리얼번호</th>
														<th>비고</th>
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
	
	var getFilterInfo = function(){
		var param = new Object();
		param.user_id = $('#filterUserId').val();
		param.user_name = $('#filterUserName').val();
		param.user_phone = $('#filterUserPhone').val();
		param.user_installed = $('#filterUserIsInstalled option:selected').val();

		return param;
	};

	function searchTableLog(){
 		var datatable = $('#table_unauthinfo').dataTable().api();
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
	$(document).ready(function(){
		
		$(".select2theme").select2({
   			  minimumResultsForSearch: -1,
   			  dropdownAutoWidth : true,
   			  width: 'auto'
   		});


loadScript(plugin_path + "datatables/media/js/jquery.dataTables.min.js", function(){
loadScript(plugin_path + "datatables/media/js/dataTables.bootstrap.min.js", function(){
loadScript(plugin_path + "datatables/extensions/Buttons/js/dataTables.buttons.min.js", function(){
loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.print.min.js", function(){
loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.html5.min.js", function(){
loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.jqueryui.min.js", function(){
 
				if (jQuery().dataTable) {

					var export_filename = 'Filename';
					
					var table = jQuery('#table_unauthinfo');
					table.dataTable({
						"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
						//dom: 'Bfrtip',
						"ajax" : {
							"url":'${context}/ax/unauthusb/list',
						   	"type":'POST',
						   	"dataSrc" : "data",
						   	"data" :  function(param) {
								param.usb_name = $('#filterUsbName').val();
								param.serial = $('#filterSerial').val();
								param.vid = $('#filterVid').val();
								param.pid = $('#filterPid').val();

								param.desc = $('#filterDesc').val();
								param.allow = 0;								
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
							                columns: [1,2,3,4,5],
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
							                columns: [1,2,3,4,5],
					                      modifier: {
					                          search: 'applied',
					                          order: 'applied'
					                      }
					                  }
					              }, 

					     ],
						
				 		"serverSide" : true,
				 		"processing": true,
				 	    "ordering": true,
						"columns": [{
							data: "usbId",							
							"orderable": false		//번호
						}, {
							data: "name",
							"orderable": false	//이름
						}, {
							data: "vid",
							"orderable": false	//vid
						}, {
							data: "pid",
							"orderable": false	//pid
						}, {
							data: "serialNumber",
							"orderable": false	//시리얼
						}, {
							data: "description",
							"orderable": false	//비고
						}],
						// set the initial value
						"pageLength": 20,
						"iDisplayLength": 20,
						"pagingType": "bootstrap_full_number",
						"language": {
							"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_개 USB",
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
							'targets': [0]	//번호
							,"class":"center-cell"
						}, {	
							"targets": [1]	//이름
							,"class":"center-cell"
						}, {	
							"targets": [2]	//vid
							,"class":"center-cell"
						}, {	
							"targets": [3],	//pid
							"class":"center-cell"
						}, {	
							"targets": [4]	//시리얼
							,"class" : "center-cell"
						}, {	
							"targets": [5]	//비고
							,"class" : "center-cell"
						}],						
						"initComplete": function( settings, json ) {
							$('.export-print').hide();
//					        $('#table_userinfo').colResizable({liveDrag:true});

						}
					});
				}
			});
			});
			});
			});
			}); 
		});
		jQuery('#preloader').hide();

    });
</script>
		
		
	</body>
</html>