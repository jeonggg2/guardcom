<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Admin</title>

		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />

		<!-- CORE CSS -->
		<link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- THEME CSS -->
		<link href="${context}/assets/css/essentials.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/layout.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/color_scheme/green.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">


			<% request.setAttribute("menu_parent", 1000); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
 			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			
 			<section id="middle">
				<div id="content" class="dashboard padding-20">

					<!-- 
						PANEL CLASSES:
							panel-default
							panel-danger
							panel-warning
							panel-info
							panel-success

						INFO: 	panel collapse - stored on user localStorage (handled by app.js _panels() function).
								All pannels should have an unique ID or the panel collapse status will not be stored!
					-->
					<div id="panel-1" class="panel panel-default">
						<div class="panel-heading" style="height: 65px;">
							<span class="title elipsis">
								<strong><i class="fa fa-area-chart"></i>&nbsp;&nbsp;정책통계</strong> <!-- panel title -->
								<small class="size-12 weight-300 text-mutted hidden-xs"></small>
								<%-- 
								<label class="radio" style="margin-left: 10px">
									<input type="radio" name="table-type" value="1" checked="checked" onclick="onTypeCheck(this)">
									<i></i> 일
								</label>
								<label class="radio">
									<input type="radio" name="table-type" value="2" onclick="onTypeCheck(this)">
									<i></i> 월
								</label>
								<small id='day_input'>
								<label class="text"> 

									<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false" placeholder="기준일 (기본:오늘)">
								</label>								
								<label class="text"> 
									<input type="text" class="form-control" id="filterEndDate" placeholder="범위 (기본:30)" onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
								</label>								
								</small>

								<small id='month_input'>
								<label class="text"> 

									<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm" data-lang="en" data-RTL="false" placeholder="기준월 (기본:이번달)">
								</label>								
								<label class="text"> 
									<input type="text" class="form-control" id="filterEndDate" placeholder="범위 (기본:12)" onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
								</label>								
								</small>
								<label class="text"> 
				
								<a href="#" class="btn btn-default btn-s btn-block">적용</a>								
								</label> --%>
								<!-- 연, 월 , 일  -->
								<!-- 기준일 -->								
								<!-- 범위 -->
							</span>
							<!-- right options -->
							<ul class="options pull-right list-inline">
								<li>								
								<label class="radio" style="margin-left: 10px">
									<input type="radio" name="chart-type" value="DAY" checked="checked" onclick="onTypeCheck(this)">
									<i></i> 일
								</label></li>
								<li style="border-left: 0px"><label class="radio">
									<input type="radio" name="chart-type" value="MONTH" onclick="onTypeCheck(this)">
									<i></i> 월
								</label></li>
								<li class="day_input" style="border-left: 0px">
								<label class="text"> 

									<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false" placeholder="기준일 (기본:오늘)"  >
								</label>								
								
								</li>
								<li class="day_input" style="border-left: 0px">
								<label class="text"> 
									<input type="text" class="form-control" id="filterDateRange" placeholder="범위 (기본:30)" onkeypress='return event.charCode >= 48 && event.charCode <= 57' onchange="setFlotChart()">
								</label>								
								
								</li>
								<li class="month_input" style="border-left: 0px">
								<label class="text"> 

									<input type="text" class="form-control datepicker" id="filterEndMonth" data-format="yyyy-mm" data-lang="en" data-RTL="false" placeholder="기준월 (기본:이번달)" >
								</label>								
								
								</li>
								<li class="month_input" style="border-left: 0px"> 
								<label class="text"> 
									<input type="text" class="form-control" id="filterMonthRange" placeholder="범위 (기본:12)" onkeypress='return event.charCode >= 48 && event.charCode <= 57' onchange="setFlotChart()">
								</label>								
								
								</li>

								<li><a href="#" class="opt" title="search" data-placement="bottom" onclick="chartReSetData()"><i class="fa fa-search" style="font-size: 22px"></i> </a></li>
							</ul>
							<!-- /right options -->

						</div>

						<!-- panel content -->
						<div class="panel-body">
							<div id="flot-sales" class="fullwidth height-250">
							
							</div>

						</div>
						<!-- /panel content -->

						<!-- panel footer -->
						<div class="panel-footer">

							<!-- 
								.md-4 is used for a responsive purpose only on col-md-4 column.
								remove .md-4 if you use on a larger column
							-->
							<ul class="easypiecharts list-unstyled">
								<li class="clearfix">
									<span class="stat-number">${data.totalUserCount}명</span>
									<span class="stat-title">사용자</span>

									<span class="easyPieChart" data-percent="${data.connectRate}" data-easing="easeOutBounce" data-barColor="#F8CB00" data-trackColor="#dddddd" data-scaleColor="#dddddd" data-size="60" data-lineWidth="4">
										<span class="percent"></span>
									</span> 
								</li>
								<li class="clearfix">
									<span class="stat-number">${data.installRate}%</span>
									<span class="stat-title">에이전트설치율</span>

									<span class="easyPieChart" data-percent="${data.installRate}" data-easing="easeOutBounce" data-barColor="#F86C6B" data-trackColor="#dddddd" data-scaleColor="#dddddd" data-size="60" data-lineWidth="4">
										<span class="percent"></span>
									</span> 
								</li>
								<li class="clearfix">
									<span class="stat-number">${data.commentRate}%</span>
									<span class="stat-title">문의답변율</span>

									<span class="easyPieChart" data-percent="${data.commentRate}" data-easing="easeOutBounce" data-barColor="#98AD4E" data-trackColor="#dddddd" data-scaleColor="#dddddd" data-size="60" data-lineWidth="4">
										<span class="percent"></span>
									</span> 
								</li>
							</ul>

						</div>
						<!-- /panel footer -->

					</div>
					<!-- /PANEL -->



					<!-- BOXES -->
					<div class="row">

						<!-- Feedback Box -->
						<div class="col-md-4 col-sm-6">

							<!-- BOX -->
							<div class="box danger"><!-- default, danger, warning, info, success -->

								<div class="box-title"><!-- add .noborder class if box-body is removed -->
									<h4><a href="#">총${data.totalUserCount}명 사용자</a></h4>
									<small class="block">접속자 ${data.connectAgentCount} 명</small>
									<i class="fa fa-users"></i>
								</div>

 								<div class="box-body text-center">
									<span class="sparkline" data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2", "disableTooltips":"true"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,
										402,319,416,355,416,371
									</span>
								</div>

							</div>
							<!-- /BOX -->

						</div>

						<!-- Profit Box -->
						<div class="col-md-4 col-sm-6">

							<!-- BOX -->
							<div class="box warning"><!-- default, danger, warning, info, success -->

								<div class="box-title"><!-- add .noborder class if box-body is removed -->
									<h4>${data.installedAgentCount}개 에이전트</h4>
									<small class="block">금일 PC ${data.todayInstalledCount}대 설치</small>
									<i class="fa fa-bar-chart-o"></i>
								</div>

								<div class="box-body text-center">
									<span class="sparkline" data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2", "disableTooltips":"true"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,402,319,416,355,416,371,423,259,361,312,269,402,327
									</span>
								</div>

							</div>
							<!-- /BOX -->

						</div>

						<div class="col-md-4 col-sm-6">

							<!-- BOX -->
							<div class="box default"><!-- default, danger, warning, info, success -->

								<div class="box-title"><!-- add .noborder class if box-body is removed -->
									<h4>${data.nonCommentContactCount}개 미처리 문의   </h4>
									<small class="block">금일 ${data.todayContactCount}개 문의</small>
									<i class="fa fa-commenting"></i>
								</div>

								<div class="box-body text-center">
									<span class="sparkline" data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2", "disableTooltips":"true", "disableTooltips":"true"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,402,319,416,355,416,371,423,259,361,312,269,402,327
									</span>
								</div>

							</div>
							<!-- /BOX -->

						</div>

						<!-- Online Box -->
						<!-- <div class="col-md-3 col-sm-6">

							BOX
							<div class="box success">default, danger, warning, info, success

								<div class="box-title">add .noborder class if box-body is removed
									<h4>3485 Online</h4>
									<small class="block">접속현황</small>
									<i class="fa fa-globe"></i>
								</div>

								<div class="box-body text-center">
									<span class="sparkline" data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,402,319,416,355,416,371,423,259,361,312,269,402,327
									</span>
								</div>

							</div>
							/BOX

						</div> -->

					</div>
					<!-- /BOXES -->



					<div class="row">

						<div class="col-md-6">

							<!-- 
								PANEL CLASSES:
									panel-default
									panel-danger
									panel-warning
									panel-info
									panel-success

								INFO: 	panel collapse - stored on user localStorage (handled by app.js _panels() function).
										All pannels should have an unique ID or the panel collapse status will not be stored!
							-->
							<div id="panel-2" class="panel panel-default">
								<div class="panel-heading">
									<span class="title elipsis">
										<strong><i class="fa fa-question-circle" aria-hidden="true"></i>&nbsp;&nbsp;사용자 요청/문의</strong> <!-- panel title -->
									</span>

									<!-- tabs nav -->
									<ul class="nav nav-tabs pull-right">
										<li class="active"><!-- TAB 1 -->
											<a href="#ttab1_nobg" data-toggle="tab">요청내역</a>
										</li>
										<li class=""><!-- TAB 2 -->
											<a href="#ttab2_nobg" data-toggle="tab">문의사항</a>
										</li>
									</ul>
									<!-- /tabs nav -->


								</div>

								<!-- panel content -->
								<div class="panel-body">

									<!-- tabs content -->
									<div class="tab-content transparent">

										<div id="ttab1_nobg" class="tab-pane active"><!-- TAB 1 요청내역 -->

											<div class="table-responsive">
												<table class="table table-striped table-hover table-bordered" id="request_table" style="width:100%">
													<thead>
														<tr>
															<th>요청구분</th>
															<th>부서</th>
															<th>요청자</th>
															<th></th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>

												<a class="size-12" onclick="goUriHelper('account')">
													<i class="fa fa-arrow-right text-muted"></i> 
													계정요청 페이지 이동
												</a>
												<a class="size-12" onclick="goUriHelper('policy')">
													<i class="fa fa-arrow-right text-muted"></i> 
													정책요청 페이지 이동
												</a>

											</div>

										</div><!-- /TAB 1 CONTENT -->

										<div id="ttab2_nobg" class="tab-pane"><!-- TAB 2 문의사항 -->

											<div class="table-responsive">
												<table class="table table-striped table-hover table-bordered" id="contact_table" style="width:100%">
													<thead>
														<tr>
															<th>제목</th>
															<th>부서</th>
															<th>작성자</th>
															<th></th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>

												<a class="size-12" onclick="goUriHelper('contact')">
													<i class="fa fa-arrow-right text-muted"></i> 
													문의사항 페이지이동
												</a>

											</div>

										</div><!-- /TAB 1 CONTENT -->

									</div>
									<!-- /tabs content -->

								</div>
								<!-- /panel content -->

							</div>
							<!-- /PANEL -->
					
						</div>

						<div class="col-md-6">

							<!-- 
								PANEL CLASSES:
									panel-default
									panel-danger
									panel-warning
									panel-info
									panel-success

								INFO: 	panel collapse - stored on user localStorage (handled by app.js _panels() function).
										All pannels should have an unique ID or the panel collapse status will not be stored!
							-->
							<div id="panel-3" class="panel panel-default">
								<div class="panel-heading">
									<span class="title elipsis">
										<strong><i class="fa fa-eye" aria-hidden="true"></i>&nbsp;&nbsp;클라이언트 감사</strong> <!-- panel title -->
									</span>
								</div>

								<!-- panel content -->
								<div class="panel-body" id="simple_audit_list">
								</div>
								<!-- /panel content -->

								<!-- panel footer -->
								<div class="panel-footer">

									<a onclick="goUriHelper('audit')">
									<i class="fa fa-arrow-right text-muted"></i>감사내역페이지 이동 </a>

								</div>
								<!-- /panel footer -->

							</div>
							<!-- /PANEL -->
						</div>
					</div>
				</div>
			</section>



		</div>
		
	
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
		<script type="text/javascript">var context = '${context}';</script>

<%--		<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery-2.2.3.min.js"></script>
 		<script type="text/javascript" src="${context}/assets/js/app.js"></script>
 --%>		<script type="text/javascript" src="${context}/assets/js/date.js"></script>

		<script type="text/javascript" src="${context}/assets/js/dashboard_function.js"></script>

		<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.resize.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.time.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.fillbetween.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.orderBars.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.pie.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.tooltip.min.js"></script>

<%-- 		<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/dataTables.bootstrap.min.js"></script>

		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/buttons.jqueryui.min.js"></script>

		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/buttons.print.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/buttons.html5.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script> --%>


		<!-- PAGE LEVEL SCRIPT -->
		<script type="text/javascript">
		
		var currentType = 'DAY';
		var setChardData = new Object();
		
		function goUriHelper(uri){
			console.log('${admin_mode}')
			if(uri == 'contact'){		// 콘솔	문의사항
				if('${admin_mode}' != 1 && '${admin_mode}' != 0){
					authAlert();
				}else{
					location.href = '${context}/admin/user/contact'
				}
			}else if(uri == 'audit'){	//레포트	에이전트감사로그
				if('${admin_mode}' != 2 && '${admin_mode}' != 0){
					authAlert();
				}else{
					location.href = '${context}/report/audit/agent'
				}								
			}else if(uri == 'account'){		//콘솔	사용자 등록요청
				if('${admin_mode}' != 1 && '${admin_mode}' != 0){
					authAlert();
				}else{
					location.href = '${context}/admin/user/enroll'
				}								
			}else if(uri == 'policy'){		//콘솔	정책변경요청
				if('${admin_mode}' != 1 && '${admin_mode}' != 0){
					authAlert();
				}else{
					location.href = '${context}/admin/user/request'
				}								
			}
			
		}
		
		function authAlert(){
			vex.defaultOptions.className = 'vex-theme-os'
    			
    			vex.dialog.open({
    				message: '해당 페이지의 권한이 없습니다.',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '확인'
    				  })]
    			})
				return false;
		}
		
		function getChartData(input){
			var data;
			$.ajax({      
		        type:"POST",  
		        url:'${context}/ax/admin/statistic/chart',
		        async: false,
		        data:input,
		        success:function(args){   
		        	//console.log(args)
		        	data = args;

		        },   
		        //beforeSend:showRequest,  
		        error:function(e){  
		            console.log(e.responseText);  
		        }  
		    }); 
			return data;
		}
		
		function onTypeCheck(radio){
			if(currentType != radio.value){
				currentType = radio.value;
				setChartData(currentType);
			}
		}
		
		function setChartData(type){
			filter = new Object();
			if(type == 'DAY'){	//일
				$('.day_input').show();
				$('.month_input').hide();

			}else if (type == 'MONTH'){	//월
				$('.day_input').hide();
				$('.month_input').show();

			}
			
		}		
		
		
/* 		var startdate="2017-03-20 00:00:00"
 */
		
		
		
		//console.log((new Date().getTime() / 1000));

		function setChartSetting(){
	
			var chartSetting = new Object();
			var startdate = new Date().format("yyyy-MM-dd 00:00:00");
			chartSetting.setType = 'DAY';		
			chartSetting.dateFormat = "%y-%0m-%0d";
			chartSetting.timeFormat = "%m-%d";
			
			
			if(currentType == 'DAY'){
				chartSetting.setRange = '30';
 				if($('#filterEndDate').val() != ''){
 					startdate = new Date($('#filterEndDate').val()).format("yyyy-MM-dd 00:00:00");
 					console.log(startdate);
 				}
 				if($('#filterDateRange').val() != ''){
 					chartSetting.setRange = $('#filterDateRange').val();
 				}
 				chartSetting.setType = 'DAY';		
 				chartSetting.dateFormat = "%y-%0m-%0d";
 				chartSetting.timeFormat = "%m/%d";
 				

			}else if(currentType == 'MONTH'){
				chartSetting.setRange = '12';

				if($('#filterEndMonth').val() != ''){
					startdate = new Date($('#filterEndMonth').val().toString() + '-01').format("yyyy-MM-01 00:00:00");
 					console.log(startdate);
 				}else{
 					startdate = new Date().format("yyyy-MM-01 00:00:00");
 				}
 				if($('#filterMonthRange').val() != ''){
 					chartSetting.setRange = $('#filterMonthRange').val();
 				}
 				chartSetting.setType = 'MONTH';		
 				chartSetting.dateFormat = "%y-%0m";
 				chartSetting.timeFormat = "%y/%m";
 				
			}

			var a=startdate.split(" ");
			var d=a[0].split("-");
			var t=a[1].split(":");
			criteriaTime= new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
			
			chartSetting.setValue = criteriaTime.getTime();
			
			return chartSetting;
		}
		
		function chartReSetData(){
			setChardData = setChartSetting();
			setFlotChart();
		}
		var option = null;
		var plot = null;
		var togglePlot = function(seriesIdx)
		{
		  var someData = plot.getData();
		  someData[seriesIdx].lines.show = !someData[seriesIdx].lines.show;
		  someData[seriesIdx].points.show = !someData[seriesIdx].points.show;
		  
		  plot.setData(someData);
		  //plot.setupGrid();
		  //plot.draw();
		  plot = jQuery.plot(jQuery("#flot-sales"), someData, options);

		}
		
		setChardData = setChartSetting();
		function setFlotChart(){
			var input = setChardData;
//			var input = setChartSetting();
		
			
/* 			var criteriaTime = 0;
			var startdate="2017-03-20 00:00:00"
			var a=startdate.split(" ");
			var d=a[0].split("-");
			var t=a[1].split(":");
			criteriaTime= new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
 */
			var chartData = getChartData(input);
			
			if (jQuery("#flot-sales").length > 0) {

				/* DEFAULTS FLOT COLORS */
			var $color_border_color = "#eaeaea";		/* light gray 	*/
				$color_grid_color 	= "#dddddd"			/* silver	 	*/
				$color_main 		= "#E24913";		/* red       	*/
				$color_second 		= "#6595b4";		/* blue      	*/
				$color_third 		= "#FF9F01";		/* orange   	*/
				$color_fourth 		= "#7e9d3a";		/* green     	*/
				$color_fifth 		= "#BD362F";		/* dark red  	*/
				$color_mono 		= "#000000";		/* black 	 	*/

				var datasets = {
						"USB": {
							label: "USB차단",
							data : chartData.USB.item,
							idx: 0
						},        
				 		"EXPORT": {
							label: "파일반출",
							data: chartData.EXPORT.item,
							idx: 1
						},
						"PRINT": {
							label: "프린트",
							data: chartData.PRINT.item,
							idx: 2
							
						},
						"PATTERN": {
							label: "개인정보",
							data: chartData.PATTERN.item,
							idx: 3
						}
				 }
				
				var data = []; var i = 0;
				data.push(datasets['USB']);
			 	data.push(datasets['EXPORT']);
			 	data.push(datasets['PRINT']);
				data.push(datasets['PATTERN']);
				
				
				options = {

					xaxis : {
						mode : "time",
						tickLength : 1,
						tickSize: [1, input.setType.toLowerCase()],
						tickDecimals: 0,
						timeformat: input.timeFormat,
						timezone: "browser"
					},
					yaxis : {
						tickDecimals: 0,
					},
					series : {
						lines : {
							show : true,
							lineWidth : 1,
							fill : true,
							fillColor : {
								colors : [{
									opacity : 0.1
								}, {
									opacity : 0.15
								}]
							}
						},
					   points: { show: true },
						shadowSize : 0
					},

					selection : {
						mode : "x"
					},
					points: {
						show: true
					},
					grid : {
						hoverable : true,
						clickable : true,
						tickColor : $color_border_color,
						borderWidth : 0,
						borderColor : $color_border_color,
					},

					tooltip : true,

					tooltipOpts : {
						content : "일자: %x <span class='block'>%y개</span>",
						dateFormat : input.dateFormat,
						defaultTheme : false
					},
					legend: {
				        labelFormatter: function(label, series){
				          return '<a href="#" onClick="togglePlot('+series.idx+'); return false;">'+label+'</a>';
				        }
				    },
					colors : [$color_main, $color_second, $color_third, $color_fourth],										
				};
			
/* 				loadScript(plugin_path + "chart.flot/jquery.flot.resize.min.js", function(){
 */
				plot = jQuery.plot(jQuery("#flot-sales"), data, options);
			}

		}

		jQuery('#preloader').hide();
		
		$(function(){
			$('.month_input').hide();
			getDashboardSimpleAuditForm();
			setFlotChart();
			setInterval(getDashboardSimpleAuditForm, 3000); 

			//setFlotChart();
			
			$("#flot-sales").bind("plotclick", function (event, pos, item) {
				if (item) {
/*					$("#clickdata").text(" - click point " + item.dataIndex + " in " + item.series.label);
					plot.highlight(item.series, item.datapoint);  */
				}
			});

			var requestTable = jQuery('#request_table');
			requestTable.dataTable({
				"dom": 't',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/simplerequest/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
			        },
				        "beforeSend" : function(){
				        },
			        "dataSrc": function ( json ) {
		                return json.data;
		            }   
				},
				tableTools: {
			        },
				"columns": [{
					data: "requestType",							
					"orderable": false	//추가정보
				}, {
					data: "requestDept",
					"orderable": false	//부서
				}, {
					data: "requestWriter",
					"orderable": false	//아이디
				}, {
					data: "requestNo",
					"orderable": false	//이름
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
					"targets": [0],	
					"class":"center-cell",
					"render": function(data, type, row){
						if(data == 2){
							return "계정등록요청"
						}else if(data == 1){
							return "정책변경요청"							
						}
						
					}
					
				},         
				{  // set default column settings
					'targets': [1]	
					,"class":"center-cell"
				}, {	
					"targets": [2]	
					,"class":"center-cell"
				}, {	
					"targets": [3]	
					,"class":"center-cell",
						"render":function(data,type,row){
							return '<a href="#" class="btn btn-default btn-xs btn-block">View</a>';
						},
						"visible":false
				}]
			});

			
			var contactTable = jQuery('#contact_table');
			
			$("#placeholder").bind("plotclick", function (event, pos, item) {
		        if (item) { 
		            //window.location = links[item.dataIndex];
		            console.log(item);
		           // here you can write location = "http://your-doamin.com";
		        }
		    });
			
			contactTable.dataTable({
				"dom": 't',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/simplecantact/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
			        },
				        "beforeSend" : function(){
				        },
			        "dataSrc": function ( json ) {
		                return json.data;
		            }   
				},
				tableTools: {
			        },
				"columns": [{
					data: "contactSubject",							
					"orderable": false	//추가정보
				}, {
					data: "contactDept",
					"orderable": false	//부서
				}, {
					data: "contactWriter",
					"orderable": false	//아이디
				}, {
					data: "contactNo",
					"orderable": false,	//이름
					"visible":false
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
					"targets": [0],	
					"class":"center-left"
				},         
				{  // set default column settings
					'targets': [1]	
					,"class":"center-cell"
				}, {	
					"targets": [2]	
					,"class":"center-cell"
				}, {	
					"targets": [3]	
					,"class":"center-cell",
						"render":function(data,type,row){
							return '<a href="#" class="btn btn-default btn-xs btn-block">View</a>';
						}
				}]
			});

		})
			
			</script>
	</body>

</html>