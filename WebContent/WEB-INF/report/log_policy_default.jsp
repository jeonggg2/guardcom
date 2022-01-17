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
		<!-- <link href="${context}/assets/css/layout-datatables.css" rel="stylesheet" type="text/css" /> -->
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
					<h1>정책로그</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">
						<div class="col-md-2">
							<div id="panel-2" class="panel panel-default">
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>조직도</strong> <!-- panel title -->
									</span>
								</div>

								<!-- panel content -->
								<div class="panel-body">

									<div id="org_tree" style="overflow: hidden;">
				                        <ul>
				                            <li class="jstree-open" data-jstree='{"type":"root"}'>Company
				                                <ul>
				                                    <li>사업부
				                                        <ul>
				                                            <li>사업 1팀</li>
				                                            <li>사입 2팀</li>
				                                            <li>디자인팀</li>
				                                        </ul>
				                                    </li>

				                                    <li>IT지원</li>
				                                </ul>
				                            </li>
				                        </ul>
				                    </div>
								</div>
								<!-- /panel content -->

							</div>
							<!-- /PANEL -->
					
						</div>

						<div class="col-md-10">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>정책로그</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
			
											<!-- Standard button -->
											<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle(1,initLayout);">검색필터</button>
		
											<!-- Info -->
											<button type="button" class="btn btn-info">새로고침</button>
											
											<!-- Primary -->
											<button type="button" class="btn btn-primary pull-right">내보내기</button>
											<!-- Success -->
											<button type="button" class="btn btn-success pull-right">인쇄</button>
											<div id="pre-1" class="margin-top-10 text-left noradius text-danger softhide" style="width:400px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="35%">접속여부</td>
															<td>
																<select class="select2theme">
																  <option value="0">전체</option>
																  <option value="1">접속</option>
																  <option value="2">미접속</option>
																</select>
															</td>
														</tr>	
														<tr>         
															<td width="35%">버전</td>
															<td>
																<select class="select2theme">
																  <option value="0">전체</option>
																  <option value="1">1.0.0</option>
																  <option value="2">1.0.1</option>
																</select>
															</td>
														</tr>		
														<tr>         
															<td width="35%">아이디</td>
															<td>
																<input type="text" name="userID" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="35%">이름</td>
															<td>
																<input type="text" name="userID" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="35%">연락처</td>
															<td>
																<input type="text" name="userID" value="" class="form-control required">
															</td>
														</tr>																															
														
													</tbody>
												</table>	
												
												<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle(1,initLayout);">필터적용</button>
																					
											</div>
<!-- 										
											<button type="button" class="btn btn-warning">Warning</button>
		
											
											<button type="button" class="btn btn-danger">Danger</button> -->
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<table class="table table-striped table-bordered x-scroll-table" id="table_userinfo" style="width:100%; min-width: 1000px;">
												<thead>
													<tr>
														<th style="width:20px"></th>
														<th>부서</th>
														<th>아이디</th>
														<th>이름</th>
														<th>번호</th>
														<th>직책</th><!-- 숨김  -->
														<th>계급</th><!-- 숨김  -->
														<th>연락처</th>		<!-- 숨김  -->
														<th>설치</th>									
														<th>IP</th>			<!-- 숨김  -->
														<th>MAC</th><!-- 숨김  -->
														<th>PC이름</th><!-- 숨김  -->
														<th>적용시간</th><!-- 숨김  -->
														<th>요청시간</th><!-- 숨김  -->
														<th>적용정책</th>

														<th>에이전트삭제가능</th>	
														<th>프린트사용가능</th>
														<th>워터마크</th>
														<th>파일실시간암호화</th>
														<th>USB포트사용가능</th>
														<th>시리얼포트사용가능</th>
														<th>무선랜사용가능</th>
														<th>메일반출가능</th>
														<th>민감파일접근시삭제</th>
														<th>보호폴더접근가능</th>
														<th>공유폴더사용여부</th>
														<th>CD사용여부</th>
													</tr>
												</thead>
				
												<tbody>
													
													
													

<tr class="odd gradeX">	<td><span class="datables-td-detail datatables-close"></span></td><td>사업부</td><td>user_id</td><td>오무진</td><td>10000</td><td>팀장</td><td>과장</td><td>010-333-1111</td><td>설치</td><td>192.168.0.1</td><td>11:11:11:11:11:11</td><td>windows-PC-client001</td><td>2012-02-11</td><td>2012-02-11</td><td><i class="fa fa-trash policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-print policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-tint policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-lock policy_icon" style="color:red" title="테스트입니다."></i><i class="fab fa-usb policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-plug policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-wifi policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-envelope policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-text policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-archive-o policy_icon " style="color:blue" title="테스트입니다."></i><i class="fa fa-folder-open policy_icon" style="color:red" title="테스트입니다."></i><i class="glyphicon glyphicon-cd policy_icon" style="color:blue" title="테스트입니다."></i></td><td>허용</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>허용</td></tr>
<tr class="odd gradeX">	<td><span class="datables-td-detail datatables-close"></span></td><td>사업부</td><td>user_id</td><td>빈소희</td><td>10001</td><td>팀장</td><td>과장</td><td>010-333-2222</td><td>설치</td><td>192.168.0.2</td><td>11:11:11:11:11:22</td><td>windows-PC-client002</td><td>2012-02-11</td><td>2012-02-11</td><td><i class="fa fa-trash policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-print policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-tint policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-lock policy_icon" style="color:red" title="테스트입니다."></i><i class="fab fa-usb policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-plug policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-wifi policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-envelope policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-text policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-archive-o policy_icon " style="color:blue" title="테스트입니다."></i><i class="fa fa-folder-open policy_icon" style="color:red" title="테스트입니다."></i><i class="glyphicon glyphicon-cd policy_icon" style="color:blue" title="테스트입니다."></i></td><td>허용</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>허용</td></tr>													
<tr class="odd gradeX">	<td><span class="datables-td-detail datatables-close"></span></td><td>사업부</td><td>user_id</td><td>전길</td><td>10002</td><td>팀장</td><td>과장</td><td>010-333-3333</td><td>설치</td><td>192.168.0.4</td><td>11:11:11:11:11:33</td><td>windows-PC-client003</td><td>2012-02-11</td><td>2012-02-11</td><td><i class="fa fa-trash policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-print policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-tint policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-lock policy_icon" style="color:red" title="테스트입니다."></i><i class="fab fa-usb policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-plug policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-wifi policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-envelope policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-text policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-archive-o policy_icon " style="color:blue" title="테스트입니다."></i><i class="fa fa-folder-open policy_icon" style="color:red" title="테스트입니다."></i><i class="glyphicon glyphicon-cd policy_icon" style="color:blue" title="테스트입니다."></i></td><td>허용</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>허용</td></tr>													
<tr class="odd gradeX">	<td><span class="datables-td-detail datatables-close"></span></td><td>사업부</td><td>user_id</td><td>홍석희</td><td>10003</td><td>팀장</td><td>과장</td><td>010-333-5544</td><td>설치</td><td>192.168.0.3</td><td>11:11:11:11:11:44</td><td>windows-PC-client004</td><td>2012-02-11</td><td>2012-02-11</td><td><i class="fa fa-trash policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-print policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-tint policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-lock policy_icon" style="color:red" title="테스트입니다."></i><i class="fab fa-usb policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-plug policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-wifi policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-envelope policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-text policy_icon" style="color:blue" title="테스트입니다."></i><i class="fa fa-file-archive-o policy_icon " style="color:blue" title="테스트입니다."></i><i class="fa fa-folder-open policy_icon" style="color:red" title="테스트입니다."></i><i class="glyphicon glyphicon-cd policy_icon" style="color:blue" title="테스트입니다."></i></td><td>허용</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>불허</td><td>허용</td><td>허용</td><td>허용</td></tr>
												
												
												
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
	var getPageinfoHash = function(){
		//페이지번호
		//라디오타입
		//검색필터정보
	};
	
	var getFilterInfo = function(){
		
	};

	$(document).ready(function(){
     	$(document).ready(function() {
    		  $(".select2theme").select2({
    			  minimumResultsForSearch: -1,
    			  dropdownAutoWidth : true,
    			  width: 'auto'
    		  });
    	}); 
    	
        $('#org_tree').jstree({
            'core' : {
                'check_callback' : true
            },
            "contextmenu":{         
                "items": function($node) {
                    var tree = $("#org_tree").jstree(true);
                    return {
                        "Create": {
                            "separator_before": false,
                            "separator_after": false,
                            "label": "Create",
                            "action": function (obj) { 
                                $node = tree.create_node($node);
                                tree.edit($node);
                            }
                        },
                        "Rename": {
                            "separator_before": false,
                            "separator_after": false,
                            "label": "Rename",
                            "action": function (obj) { 
                                tree.edit($node);
                            }
                        },                         
                        "Remove": {
                            "separator_before": false,
                            "separator_after": false,
                            "label": "Remove",
                            "action": function (obj) { 
                                tree.delete_node($node);
                            }
                        }
                    };
                }
            },
            'plugins' : [ 'types', 'dnd', 'checkbox', 'contextmenu' ],
            'types' : {
                'default' : {
                    'icon' : 'fa fa-user-circle'
                },
                'root' : {
                    'icon' : 'fa fa-building'
                }

            }
        });

        
        loadScript(plugin_path + "datatables/js/jquery.dataTables.min.js", function(){
			loadScript(plugin_path + "datatables/dataTables.bootstrap.js", function(){
				loadScript(plugin_path + "datatables/js/dataTables.colResize.js", function(){
					loadScript(plugin_path + "datatables/js/dataTables.colVis.js", function(){

				if (jQuery().dataTable) {

					var table = jQuery('#table_userinfo');
					table.dataTable({
						//"autoWidth": true,
						//"dom": '<"row view-filter"<"col-sm-12"<"pull-left" i ><"pull-right"><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
						//dom: 'C<"clear">RZlfrtp',
						"dom": '<"row view-filter"<"col-sm-12"<"pull-left" i ><"pull-right"><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
						//l이 갯수
						/* "columns": [{
							"orderable": false		//추가정보
						}, {
							"orderable": false	//부서
						}, {
							"orderable": false	//아이디
						}, {
							"orderable": false	//이름
						}, {
							"orderable": false	//번호
						}, {
							"orderable": false	//직책
						}, {
							"orderable": false	//계급
						}, {
							"orderable": false	//연락
						}, {
							"orderable": false	//설치유무
						}, {
							"orderable": false	//IP
						}, {
							"orderable": false	//MAC
						}, {
							"orderable": false	//PC이름
						}, {
							"orderable": false	//버전
						}, {
							"orderable": false	//접속여부
						}, {
							"orderable": false	//설치시간
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}, {
							"orderable": false	//
						}], */
						// set the initial value
						"pageLength": 20,
						"iDisplayLength": 20,
						"pagingType": "bootstrap_full_number",
						"language": {
							"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 사용자",
							"infoEmpty":      "검색된 데이터가 없습니다.",
							"lengthMenu": "  _MENU_ 개",
							"paginate": {
								"previous":"Prev",
								"next": "Next",
								"last": "Last",
								"first": "First"
							}
						},
						"columnDefs": [
						{	
							"targets": [0],	//추가정보
							"class":"center-cell",
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
							,"visible":false
						}, {	
							"targets": [6]	//계급
							,"class" : "center-cell"
							,"visible":false
						}, {
							"targets": [7]	//연락처
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [8]	//설치유무
							,"class":"center-cell"
						}, {	
							"targets": [9]	//IP
							,"class" : "center-cell"
								,"visible":true
						}, {	
							"targets": [10]	//MAC
							,"class" : "center-cell"
								,"visible":false
						}, {	
							"targets": [11]	//PC이름
							,"class" : "center-cell"
								,"visible":false
						}, {	
							"targets": [12]	//적용시간
							,"class" : "center-cell"
								,"visible":false
						}, {	
							"targets": [13]	//요청시간
							,"visible":false
							,"class" : "center-cell"
						}, {	
							"targets": [14]	//적용정책
							,"class" : "center-cell"
						}, {	
							"targets": [15]	//적용정책
							,"class" : "center-cell"
							,"visible":false		
						}, {	
							"targets": [16]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [17]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [18]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [19]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [20]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [21]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [22]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [23]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [24]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [25]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}, {	
							"targets": [26]	//적용정책
							,"class" : "center-cell"
							,"visible":false
						}],		
						"initComplete": function( settings, json ) {
						}
					});
					
					function fnFormatDetails(oTable, nTr) {
						var aData = oTable.fnGetData(nTr);
						
						var sFrame = '<div class="tabs nomargin-top">'
						sFrame += '<ul class="nav nav-tabs"><li class="active"><a href="#tab1" data-toggle="tab"><i class="fa fa-th-large"></i>정책상세</a></li>';
						sFrame += '<li><a href="#tab2" data-toggle="tab"><i class="fa fa-info-circle"></i>사용자정보</a></li></ul>';		
					
					
						sFrame += '<div class="tab-content">';

					
						sFrame += '<div id="tab1" class="tab-pane active">';
						
 						var sOut = '<table class="table ">';
						sOut += '<tr><td class="center-cell">에이전트삭제가능:</td><td>' + aData[15] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">프린트사용가능:</td><td>' + aData[16] + '</td></tr>';
						sOut += '<tr><td class="center-cell">인쇄워터마크:</td><td>' + aData[17] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">파일신시간암호화:</td><td>' + aData[18] + '</td></tr>';
						sOut += '<tr><td class="center-cell">USB포트사용:</td><td>' + aData[19] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">시리얼포트사용:</td><td>' + aData[20] + '</td></tr>';
						sOut += '<tr><td class="center-cell">무선랜사용:</td><td>' + aData[21] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">메일반출사용:</td><td>' + aData[22] + '</td></tr>';
						sOut += '<tr><td class="center-cell">민감파일접근시삭제:</td><td>' + aData[23] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">보호파일접근가능:</td><td>' + aData[24] + '</td></tr>';
						sOut += '<tr><td class="center-cell">공유폴더사용가능:</td><td>' + aData[25] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">CD사용가능:</td><td>' + aData[26] + '</td></tr>';

						sOut += '</table>'; 
						sFrame += sOut;
						sFrame += '</div>';	
						
						sFrame += '<div id="tab2" class="tab-pane">';
						
 						var sOut = '<table class="table ">';
						sOut += '<tr><td class="center-cell">직책:</td><td>' + aData[5] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">계급:</td><td>' + aData[6] + '</td></tr>';
						sOut += '<tr><td class="center-cell">연락처:</td><td>' + aData[7] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">IP주소:</td><td>' + aData[9] + '</td></tr>';
						sOut += '<tr><td class="center-cell">MAC주소:</td><td>' + aData[10] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">PC이름:</td><td>' + aData[11] + '</td></tr>';
						sOut += '<tr><td class="center-cell">적용시간:</td><td>' + aData[12] + '</td>';
						sOut += '<td style="padding-left:100px;" class="center-cell">요청시간:</td><td>' + aData[13] + '</td></tr>';

						
						/*  
						
														<th>직책</th><!-- 숨김  -->
														<th>계급</th><!-- 숨김  -->
														<th>연락처</th>		<!-- 숨김  -->
																				
														<th>IP</th>			<!-- 숨김  -->
														<th>MAC</th><!-- 숨김  -->
														<th>PC이름</th><!-- 숨김  -->
														<th>적용시간</th><!-- 숨김  -->
														<th>요청시간</th><!-- 숨김  -->
														<th>적용정책</th><!-- 숨김  -->
						*/
						
						
						
						sOut += '</table>'; 
						sFrame += sOut;
						sFrame += '</div>';	
						
						
						
						sFrame += '</div>';	
						sFrame += '</div>';
					
						return sFrame;
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
			});
			});
			});
		});
		jQuery('#preloader').hide();
       
    });
</script>
		
		
	</body>
</html>