<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Console : 자산관리 - 소프트웨어 관리</title>

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
		<!--  <link href="//cdn.datatables.net/rowgroup/1.0.2/css/rowGroup.dataTables.min.css" rel="stylesheet" type="text/css" /> -->
		
		<!-- Alert -->
		<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
        <link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

        <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
        <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
	</head>
	
	<style>
		.ui-autocomplete {
		  position: absolute;
		  top: 100%;
		  left: 0;
		  z-index: 1000;
		  float: left;
		  display: none;
		  min-width: 160px;
		  _width: 160px;
		  padding: 4px 0;
		  margin: 2px 0 0 0;
		  list-style: none;
		  background-color: #ffffff;
		  border-color: #ccc;
		  border-color: rgba(0, 0, 0, 0.2);
		  border-style: solid;
		  border-width: 1px;
		  -webkit-border-radius: 5px;
		  -moz-border-radius: 5px;
		  border-radius: 5px;
		  -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
		  -moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
		  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
		  -webkit-background-clip: padding-box;
		  -moz-background-clip: padding;
		  background-clip: padding-box;
		  *border-right-width: 2px;
		  *border-bottom-width: 2px;
		  
		  &:before {
		  	content: '';
		  	display: inline-block;
		  	border-left:   7px solid transparent;
		  	border-right:  7px solid transparent;
		  	border-bottom: 7px solid #ccc;
		  	border-bottom-color: rgba(0,0,0,.2);
		  	position: absolute;
		  	top: -7px;
		  	left: 6px;
		  }
		  &:after {
		  	content: '';
		  	display: inline-block;
		  	border-left:   6px solid transparent;
		  	border-right:  6px solid transparent;
		  	border-bottom: 6px solid #FFF;
		  	position: absolute;
		  	top: -6px;
		  	left: 7px;
		  }
		  
		  .ui-menu-item {
		  	cursor:pointer;
		  }
		  .ui-menu-item > a.ui-corner-all {
		    display: block;
		    padding: 3px 15px;
		    clear: both;
		    font-weight: normal;
		    line-height: 18px;
		    color: #555555;
		    white-space: nowrap;
		
		    &.ui-state-hover, &.ui-state-active {
		      color: #ffffff;
		      text-decoration: none;
		      background-color: #0088cc;
		      border-radius: 0px;
		      -webkit-border-radius: 0px;
		      -moz-border-radius: 0px;
		      background-image: none;
		    }
		  }
		}		
	</style>
	
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 7000); %> 
			<% request.setAttribute("menu_sub_first", 7200); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>소프트웨어 관리</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div id="layout-container" class="row" style="width: 100%; height: 100%;">
						<div class="ui-layout-center">
							<div id="panel-list" class="panel panel-default">
								
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>소프트웨어 목록</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
											<button type="button" class="btn btn-default" data-toggle="button" onclick="jQuery('#pre-1').slideToggle(1,initLayout);$(this).next().focus();"><i class="fa fa-filter" aria-hidden="true">&nbsp;검색 필터</i></button>
											<button type="button" class="btn btn-info" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;새로고침</i></button>
											<button type="button" class="btn btn-primary pull-right" onclick="fn_export_excel_file()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;내보내기</i></button>
											<button type="button" class="btn btn-success pull-right" onclick="fn_print_list()"><i class="fa fa-print" aria-hidden="true">&nbsp;인쇄</i></button>
											<button type="button" class="btn btn-red pull-right" onclick="fn_open_delete_popup()"><i class="fa fa-minus" aria-hidden="true">&nbsp;삭제</i></button>
											<button type="button" class="btn btn-blue pull-right" onclick="fn_open_commit_popup()"><i class="fa fa-check" aria-hidden="true">&nbsp;검증</i></button>
											<button type="button" class="btn btn-green pull-right" onclick="fn_open_add_popup();"><i class="fa fa-plus" aria-hidden="true">&nbsp;소프트웨어 등록</i></button>
											
											<!-- search filter content -->
											<div id="pre-1" class="margin-top-10 margin-bottom-20 text-left softhide" style="border-width:1px">
												<table id="searchConditionTable" class="table table-bordered" style="width: auto; margin-bottom:5px">
													<tbody> 
														<tr>       
															<td style="vertical-align:middle;" width="15%">소프트웨이 이름</td>
															<td colspan="6">
																<input type="text" name="filterName" id="filterName" value="" class="form-control autocomplete" data-field="name" autocomplete="off">																  
															</td>
														</tr>
														<tr>														
															<td  style="vertical-align:middle;" width="15%">소유 여부</td>
															<td>
																<select class="select2theme" id="filterHasOwn" name="filterHasOwn" data-field="has_own">
																  <option value="">전체</option>
																  <option value="1">사내 보유 중</option>
																  <option value="0">미 보유</option>
																  <option value="-1">검증 안됨</option>
																</select>
															</td>
															<td  style="vertical-align:middle;" width="15%">상용 여부</td>
															<td>
																<select class="select2theme" id="filterType" name="filterType" data-field="type">
																  <option value="">전체</option>
																  <option value="0">상용</option>
																  <option value="1">무료</option>
																  <option value="-1">검증 안됨</option>
																</select>
															</td>
															
															<td  style="vertical-align:middle;" width="15%">검증 여부</td>
															<td>
																<select class="select2theme" id="filterCommit" name="filterCommit" data-field="has_commited">
																  <option value="">전체</option>
																  <option value="1">검증</option>
																  <option value="0">미검증</option>
																</select>
															</td>
														</tr>
														<tr>														
															<td  style="vertical-align:middle;" width="auto">도입 날짜 (시작일)</td>
															<td>
																<input type="text" class="form-control datepicker" id="filterStartDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false" data-field="start_date">
															</td>
		
															<td  style="vertical-align:middle;" width="15%">도입 날짜 (종료일)</td>
															<td>
																<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false" data-field="end_date">
															</td>																														
															<td  style="vertical-align:middle;" width="15%">관리 여부</td>
															<td>
																<select class="select2theme" id="filterCommit" name="filterCommit" data-field="has_managed">
																  <option value="">전체</option>
																  <option value="1">관리</option>
																  <option value="0">관리안함</option>
																</select>
															</td>																									
														</tr>
													</tbody>
												</table>
												
												<button type="button" class="btn btn-warning" onclick="$('#searchConditionTable').find(':input, :button').val('');reloadTable();"><i class="fa fa-eraser" aria-hidden="true">&nbsp;검색 조건 초기화</i></button>
												<button type="button" class="btn btn-success" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;검색 조건 적용</i></button>
											</div>
											<!-- /search filter content -->
										</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-bordered x-scroll-table" id="table_sw_list" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th style="width:30px">상세</th>
														<th style="width:20px"><input type="checkbox" id="all_check_info" name="all_check_info" /></th>
														<th>소프트웨어 이름</th>
														<th>설명</th>
														<th>상용 여부</th>
														<th>보유 여부</th>
														<th>도입 날짜</th>
														<th>검증 날짜</th>          
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
			
		<div id="popup_div"></div>
		
        <script src="//cdn.datatables.net/rowgroup/1.0.2/js/dataTables.rowGroup.min.js"></script>
		<script src="${context}/assets/plugins/json2html.min.js"></script>
		<script type="text/javascript">

			var table;
			
			function getSearchConditonData() {
				var data = {};				
				$("#searchConditionTable").find('[data-field]').each( function (index, element) {
					data[$(element).attr('data-field')] = $(element).val();
				});				
				return data;
			}			
			
			
			$("#searchConditionTable").find('[data-field]').on('keyup', function (e) {
				if (e.keyCode == 13) {
					reloadTable();
				}
			});
			
			$.ajax({      
			    type:"GET",  
			    url:'${context}/admin/itasset/sw/list',
			    async: false,
			    success:function(sw_list){
			    	$("#filterName").autocomplete({
			    	      source: sw_list
			    	});
			    }
			});
			
		 	// 검색 버튼 클릭 시 
		 	function reloadTable(){
		 		var datatable = $('#table_sw_list').dataTable().api();
				datatable.ajax.reload();		 	
		 	}
			
		 	// 인쇄 버튼 클릭 시 
		 	function fn_print_list(){
		 		console.log('ptint')
		 		var $buttons = $('.export-print');
		 		$buttons.click();
		 	}
		 	
		 	// 내보내기 버튼 클릭 시 
		 	function fn_export_excel_file(){
				console.log('excel')
		 		var $buttons = $('.export-csv');
		 		$buttons.click();		 		
		 	}
		 	
		 	// 체크 박스 클릭 시 전체 체크 여부 확인
		 	var check_Info = function() {				
		 		var checkboxLen =  $("input:checkbox[name='item_check']").length;
		 		var checkedLen = $("input:checkbox[name='item_check']:checked").length;		
		 		$("#all_check_info").prop("checked", checkboxLen == checkedLen);
		 	}
		 	
		 	function formatDate(date) {
				var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				day = '' + d.getDate(),
				year = d.getFullYear();					 		    
				if (month.length < 2) month = '0' + month;
				if (day.length < 2) day = '0' + day;				
				return [year, month, day].join('-');
	 		}
		 			    
			function setDataTable(){
				if (jQuery().dataTable) {					
					{
						var export_filename = 'Filename';							
						apTable = $('#table_sw_list');						
						table = apTable.dataTable({
							"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB><"pull-right" ><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
							"ajax" : {
								"url":'${context}/admin/itasset/sw/list',
							   	"type":'POST',
							   	"dataSrc" : "data",
							   	"data" :  function(param) {
							   		param.search_condition = JSON.stringify(getSearchConditonData());
						        },
	 					        "beforeSend" : function(){
									jQuery('#preloader').show();
	 					        },
						        "dataSrc": function ( json ) {
									jQuery('#preloader').hide();
									// 그룹핑 기준이 되는 필드는 반드시 값을 가지고있어야하므로 null일때는 공백으로 초기화합니다.
									// 서버에서 null로 반환하는 속성은 json에 포함되지 않으므로 null인 경우 ''를 명시하여 초기화하도록 합니다.
									$.each(json.data, function () {
										if (this.name == null) this.name = '';
										if (this.desc == null) this.desc = '';										
										this.commitDate = (this.commitDate == null) ? '' : formatDate(this.commitDate);
										this.introDate = (this.introDate == null) ? '' : formatDate(this.introDate);
									});
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
					                	columns: [2,3,4,5,6,7]
					             	}
						     	},
						        {
					            	text: '<i class="fa fa-lg fa-clipboard">프린트</i>',
					                extend: 'print',
					                messageTop: '소프트웨어 현황',
					                className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-export-btn export-print ttip hidden',
					                exportOptions: {
					                	modifier: {
					                		search: 'applied',
					                    	order: 'applied'
					                	},
					                	columns: [2,3,4,5,6,7]
					             	},					             	 
					             	customize: function ( win ) {
					                    $(win.document.body)
					                        .css( 'font-size', '6px' );
					                    
					                    $(win.document.body).find( 'table' )
					                        .addClass( 'compact' )
					                        .css( 'font-size', 'inherit' );
					                 }
					            },
					     	],
					 		"serverSide" : true,
					 		"processing": true,
					 		ordering: true,
					        rowGroup: {
					        	emptyDataGroup: '미검증 소프트웨어',
					            startRender: function ( rows, group ) {
									var desc;									
									if (group == '') {
										desc = '미검증 소프트웨어 항목';
									} else {
										desc = '검증된 소프트웨어 항목 (' + (new Date(group)).toLocaleDateString("ko-KR") +')';
									}									
					            	return $('<tr/>')												
					                    .append( '<td colspan="3">'+desc+'</td>' )
					                    .append( '<td/>' )
					                    .append( '<td/>' )
					                    .append( '<td/>' )
					                    .append( '<td/>' )
					                    .append( '<td> 소프트웨어 수 : '+ rows.count() +'</td>' );
					            },
					            dataSrc:'commitDate'
					        },
							"columns": [{
											data: "no",
											orderable: false
										}, {
											data: "no",
											orderable: false,
											render: function(data,type,row){
												return '<input type="checkbox" name="item_check" class="item_check" value="' + data + '" onClick="javascript:check_Info()"/>';
											}
										}, {
											data: "name",
											orderable: false
										}, {
											data: "desc",
											orderable: false
										}, {                        	           
											data: "type"
											,render : function(data,type,row) {
												if (row.commitDate == '') return "";												
												switch (data) {
												case 0:
													return "<strong>상용</strong>";
													break;
												case 1:
													return "무료";
													break;
												default:
													return "";
													break;
												}
											}
										}, {                        	           
											data: "hasOwn",
											render : function(data,type,row) {
												if (row.commitDate == '') return "";
												return data == 1? '<strong style="color:green">보유</strong>' : '<strong style="color:red">미보유</strong>';
												}
										},{                        	           
											data: "introDate",
											render: function(data,type,row){
												if (row.commitDate == '') return "";
												if (! row.hasOwn) return "";
												if (data == '') return "";
												return (new Date(data)).toLocaleDateString("ko-KR");
											}
										}, {                        	           
											data: "commitDate",
											render: function(data,type,row){
												if (data == '') return '';
												return '<span style="color:green">검증됨 (' + (new Date(data)).toLocaleDateString("ko-KR") + ')</span>';
											}
										}],						
							"pageLength": 20,
							"iDisplayLength": 20,
							"pagingType": "bootstrap_full_number",
							lengthMenu : [ [20, 40, 80, -1], ["20개씩 보기", "40개씩 보기", "50개씩 보기", "모두"] ],
							"language": {
								"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 개",
								"infoEmpty": "검색된 데이터가 없습니다.",
								"zeroRecords" :"검색된 데이터가 없습니다.",
								"lengthMenu": "  _MENU_",
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
						debugger;
                    	var transform = {"tag":"tr","children":[
							{"tag":"td","html":"\${deptName}"},
							{"tag":"td","html":"\${duty}"},
							{"tag":"td","html":"\${rank}"},
							{"tag":"td","html":"\${userName}"},
							{"tag":"td","html":"\${userNumber}"},
							{"tag":"td","html":"\${ipAddr}"},
							{"tag":"td","html":"\${macAddr}"}
		               ]};
						
                    	var list = null;
                    	
            			$.ajax({      
            			    type:"POST",  
            			    url:'${context}/admin/itasset/sw/getUsageList',
            			    async: false,
						   	data : {sw_name : aData.name },
            			    success:function(data){
            			    	list = data;
            			    }
            			});
            			
            			if (list.recordsTotal == 0) {
            				return "해당 소프트웨어가 설치된 사용자가 존재하지않습니다.";
            			}
            			
						var data = list.data;
						
					    var sOut = "<table class='table table-striped' cellspacing = '0'>" +
					    			"<tthead> <tr>" +
					    					"<th style='white-space: nowrap; width: 1%;'>부서</th>" +
											"<th style='white-space: nowrap; width: 1%;'>직책</th>" +
											"<th style='white-space: nowrap; width: 1%;'>직급</th>" +
											"<th style='white-space: nowrap; width: 1%;'>이름</th>" +
											"<th style='white-space: nowrap; width: 1%;'>사번</th>" +
											"<th style='white-space: nowrap; width: 1%;'>IP</th>" +
											"<th style='white-space: nowrap; width: 1%;'>MAC</th>" +
										"</tr> </thead> <tbody>"
						
						sOut = sOut + json2html.transform(data,transform) + "</tbody> <table>";	
						return sOut;
					}
					
					var jTable = $('#table_sw_list');
					jTable.on('click', ' tbody td .datables-td-detail', function () {
						
						var nTr = jQuery(this).parents('tr')[0];
						var aData = table.fnGetData(nTr);	
						if (aData.name == null) return;
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
				
				var ctbl = $('#table_sw_list').DataTable();
				ctbl.on( 'click', 'td', function () {
					var data = ctbl.row( $(this).parent() ).data();						
					if($(this).index() != 0 && $(this).index() != 1) {
						fn_open_edit_popup(data.no);
					}
				});
		 	}
			
		 	var ajaxCount = 1;
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
				    	  $(".item_check").prop("checked", true);
				      } else {
				    	  $(".item_check").prop("checked", false);
				      }
				});
				
				setDataTable();
				
				jQuery('#preloader').hide();		
		    });
			
			$(document).ajaxComplete(function(){
				initLayout();
			});
			
			function fn_open_add_popup(no) {				
				fn_open_edit_popup(-1);
			}
			
			function fn_open_edit_popup(no){				
				$.ajax({
				    type:"POST",  
				    url:'${context}/admin/itasset/sw/getpopup',
				    async: false,
				    data:{ 
				    	no : no,
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#popup_div").html(data).children(".modal").modal('show');
				    },
				    error:function(e){  
				        console.log(e.responseText);  
				    }
				});
			}
			
			function fn_open_commit_popup(no){
				
				var apply_arr = new Array();
					gdTable = table.api();
				
		 		var checkedLen = $("input:checkbox[name='item_check']:checked").length;
		 		if (checkedLen < 1) {
		 			infoAlert("하나 이상의 소프트웨어를 선택해 주세요.");
		 			return false;
		 		}
			 	
				$(":checkbox[name='item_check']:checked").each(function(pi,po){
					var check_row = $(this).parents('tr').get(0);
					var check_item = gdTable.row(check_row).data();
					apply_arr.push(check_item);
				});
 				
				$.ajax({
				    type:"POST",  
				    url:'${context}/admin/itasset/sw/getcommitpopup',
				    async: false,
				    data:{ 
				    	apply_list : JSON.stringify(apply_arr),
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#popup_div").html(data).children(".modal").modal('show');
				    },
				    error:function(e){  
				        console.log(e.responseText);  
				    }
				});
			}
			
			function fn_open_delete_popup() {				
		 		var checkedLen = $("input:checkbox[name='item_check']:checked").length;
		 		if (checkedLen < 1) {
		 			infoAlert("하나 이상의 소프트웨어를 선택해 주세요.");
		 			return false;
		 		}
		 		
				vex.defaultOptions.className = 'vex-theme-os';	    		
	    		vex.dialog.open({
					message: '해당 소프트웨어 항목을 삭제 하시겠습니까?',
					buttons: [
				    	$.extend({}, vex.dialog.buttons.YES, {
				     	text: '삭제'
				  	}),
				  	$.extend({}, vex.dialog.buttons.NO, {
				    	text: '취소'
				  	})],
				  	callback: function(data) {
			 	  		if (data) {			 	  			
		 			 		var apply_arr = new Array();
		 			 		gdTable = table.api();
		 					
		 					$(":checkbox[name='item_check']:checked").each(function(pi,po){
		 						var check_row = $(this).parents('tr').get(0);
		 						var check_item = gdTable.row(check_row).data();
		 						apply_arr.push(check_item);
		 					});
		 					
		 					$.ajax({      
		 					    type:"POST",  
		 					    url:'${context}/admin/itasset/sw/unregister',
		 					    async: false,
		 					    data:{
		 					    	apply_list : JSON.stringify(apply_arr),
		 					    	_ : $.now()
		 					    },
		 					    success:function(data){
						    		var datatable = $('#table_sw_list').dataTable().api();
						    		datatable.ajax.reload();
						    		
						    		vex.dialog.open({
						    			message: '삭제가 완료되었습니다.',
						    			  buttons: [
						    			    $.extend({}, vex.dialog.buttons.YES, {
						    			      text: '확인'
						    			  })]
						    		});
		 					    }
		 					});	
			 	    	} else {
			 	  			return false;
			 	    	}
			 	  	}
				})
			}		 	
		</script>
	</body>
</html>