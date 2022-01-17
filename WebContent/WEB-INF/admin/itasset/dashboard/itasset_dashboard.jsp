<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <title>GuardCom Admin</title>

    <!-- mobile settings -->
    <meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0"/>

    <!-- CORE CSS -->
    <link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

    <!-- THEME CSS -->
    <link href="${context}/assets/css/essentials.css" rel="stylesheet" type="text/css"/>
    <link href="${context}/assets/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="${context}/assets/css/color_scheme/green.css" rel="stylesheet" type="text/css" id="color_scheme"/>
    <link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"/>
    <link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<!-- WRAPPER -->
<div id="wrapper" class="clearfix">

    <% request.setAttribute("menu_parent", 7000); %>
    <% request.setAttribute("menu_sub_first", 7300); %>

    <jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false"/>
    <jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false"/>
    <section id="middle">
        <div id="content" class="dashboard padding-20">

            <div id="panel-1" class="panel panel-default">
                <div class="panel-heading" style="height: 65px;">
							<span class="title elipsis">
								<strong><i class="fa fa-area-chart"></i>&nbsp;&nbsp;자산 통계</strong> <!-- panel title -->
								<small class="size-12 weight-300 text-mutted hidden-xs"></small>
								
							</span>
                    <!-- right options -->
                    <ul class="options pull-right list-inline">
                        <li>
                            <label class="radio" style="margin-left: 10px">
                                <input type="radio" name="chart-type" value="DAY" checked="checked"
                                       onclick="onTypeCheck(this)">
                                <i></i> 일
                            </label></li>
                        <li style="border-left: 0px"><label class="radio">
                            <input type="radio" name="chart-type" value="MONTH" onclick="onTypeCheck(this)">
                            <i></i> 월
                        </label></li>
                        <li class="day_input" style="border-left: 0px">
                            <label class="text">

                                <input type="text" class="form-control datepicker" id="filterEndDate"
                                       data-format="yyyy-mm-dd" data-lang="en" data-RTL="false"
                                       placeholder="기준일 (기본:오늘)">
                            </label>

                        </li>
                        <li class="day_input" style="border-left: 0px">
                            <label class="text">
                                <input type="text" class="form-control" id="filterDateRange" placeholder="범위 (기본:30)"
                                       onkeypress='return event.charCode >= 48 && event.charCode <= 57'
                                       onchange="setFlotChart()">
                            </label>

                        </li>
                        <li class="month_input" style="border-left: 0px">
                            <label class="text">

                                <input type="text" class="form-control datepicker" id="filterEndMonth"
                                       data-format="yyyy-mm" data-lang="en" data-RTL="false" placeholder="기준월 (기본:이번달)">
                            </label>

                        </li>
                        <li class="month_input" style="border-left: 0px">
                            <label class="text">
                                <input type="text" class="form-control" id="filterMonthRange" placeholder="범위 (기본:12)"
                                       onkeypress='return event.charCode >= 48 && event.charCode <= 57'
                                       onchange="setFlotChart()">
                            </label>

                        </li>

                        <li><a href="#" class="opt" title="search" data-placement="bottom"
                               onclick="chartReSetData()"><span class="fas fa-search" style="font-size: 22px"></span>
                        </a></li>
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
                            <span class="stat-number">${data.totalUserCount}개</span>
                            <span class="stat-title">소프트웨어</span>

                            <span class="easyPieChart" data-percent="${data.connectRate}" data-easing="easeOutBounce"
                                  data-barColor="#F8CB00" data-trackColor="#dddddd" data-scaleColor="#dddddd"
                                  data-size="60" data-lineWidth="4">
										<span class="percent"></span>
									</span>
                        </li>
                        <li class="clearfix">
                            <span class="stat-number">${data.installRate}%</span>
                            <span class="stat-title">소프트웨어 검증률</span>

                            <span class="easyPieChart" data-percent="${data.installRate}" data-easing="easeOutBounce"
                                  data-barColor="#F86C6B" data-trackColor="#dddddd" data-scaleColor="#dddddd"
                                  data-size="60" data-lineWidth="4">
										<span class="percent"></span>
									</span>
                        </li>
                        <li class="clearfix">
                            <span class="stat-number">${data.commentRate}%</span>
                            <span class="stat-title">비인가 소프트웨어 설치</span>

                            <span class="easyPieChart" data-percent="${data.commentRate}" data-easing="easeOutBounce"
                                  data-barColor="#98AD4E" data-trackColor="#dddddd" data-scaleColor="#dddddd"
                                  data-size="60" data-lineWidth="4">
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

                <div class="col-md-3 col-sm-4">
                    <div class="box danger"><!-- default, danger, warning, info, success -->
                        <div class="box-title"><!-- add .noborder class if box-body is removed -->
                            <h4><a href="#">상용 소프트웨어 설치 사용자 : ${data.totalUserCount}명</a></h4>
                            <small class="block">불법 소프트웨어 설치 의심 :${data.connectAgentCount}명</small>
                            <i class="fa fa-users"></i>
                        </div>

                        <div class="box-body text-center">
									<span class="sparkline"
                                          data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2", "disableTooltips":"true"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,
										402,319,416,355,416,371
									</span>
                        </div>

                    </div>
                </div>

                <div class="col-md-3 col-sm-4">
                    <div class="box warning">
                        <div class="box-title">
                            <h4>보유중인 소프트웨어 ${data.installedAgentCount}개</h4>
                            <small class="block">상용 소프트웨어 ${data.todayInstalledCount}개</small>
                            <i class="fa fa-bar-chart-o"></i>
                        </div>
                        <div class="box-body text-center">
									<span class="sparkline"
                                          data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2", "disableTooltips":"true"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,402,319,416,355,416,371,423,259,361,312,269,402,327
									</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-4">
                    <div class="box default">
                        <div class="box-title">
                            <h4>미검증된 소프트웨어 ${data.nonCommentContactCount}개</h4>
                            <small class="block">당일 탐지된 미검증 소프트웨어 ${data.todayContactCount}개 </small>
                            <i class="fa fa-commenting"></i>
                        </div>
                        <div class="box-body text-center">
									<span class="sparkline"
                                          data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2", "disableTooltips":"true", "disableTooltips":"true"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,402,319,416,355,416,371,423,259,361,312,269,402,327
									</span>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 col-sm-4">
                    <div class="box success">
                        <div class="box-title">
                            <h4>정상적인 소프트웨어 설치 사용자 ${data.nonCommentContactCount}명</h4>
                            <small class="block"></small>
                            <i class="fa fa-globe"></i>
                        </div>
                        <div class="box-body text-center">
									<span class="sparkline"
                                          data-plugin-options='{"type":"bar","barColor":"#ffffff","height":"35px","width":"100%","zeroAxis":"false","barSpacing":"2"}'>
										331,265,456,411,367,319,402,312,300,312,283,384,372,269,402,319,416,355,416,371,423,259,361,312,269,402,327
									</span>
                        </div>
                    </div>
                </div>

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
										<strong><i class="fa fa-question-circle" aria-hidden="true"></i>&nbsp;&nbsp;소프트웨어 설치 현황</strong>
                                        <!-- panel title -->
									</span>
                        </div>

                        <!-- panel content -->
                        <div class="panel-body">
                            <canvas class="chart-container" id="myPieChart"
                                    style="position: relative; height:60vh; width:80vw"></canvas>
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
										<strong><i class="fa fa-eye"
                                                   aria-hidden="true"></i>&nbsp;&nbsp;소프트웨어 감사</strong>
                                        <!-- panel title -->
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

    <!--
							<canvas id="mycanvas" width="400" height="100"></canvas>
								
							<script type="text/javascript" src="${context}/assets/plugins/smoothie/smoothie.js"></script>
								<script>
								$(function(){
									
									var smoothie = new SmoothieChart({
									  grid: { strokeStyle:'rgb(200, 200, 200)', fillStyle:'rgb(255, 255, 255)',
									          lineWidth: 0, millisPerLine: 1, verticalSections: 6, },
									  labels: { fillStyle:'rgb(100, 100, 100)' }
									});
								
									// Data
									var line1 = new TimeSeries();
									var line2 = new TimeSeries();
								
									smoothie.addTimeSeries(line1,
									  { strokeStyle:'rgb(0, 255, 0)', fillStyle:'rgba(0, 255, 0, 0.4)', lineWidth:1 });
									smoothie.addTimeSeries(line2,
									  { strokeStyle:'rgb(255, 0, 255)', fillStyle:'rgba(255, 0, 255, 0.3)', lineWidth:1 });
									smoothie.streamTo(document.getElementById("mycanvas"), 1000);
								
									// Add a random value to each line every second
									setInterval(function() {
									  line1.append(new Date().getTime(), Math.random());
									  line2.append(new Date().getTime(), Math.random());
									}, 1000);
									
									// Add to SmoothieChart
									smoothie.addTimeSeries(line1);
									smoothie.addTimeSeries(line2)
								});
								</script>
 -->
</div>


<!-- JAVASCRIPT FILES -->
<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
<script type="text/javascript">var context = '${context}';</script>

<script type="text/javascript" src="${context}/assets/js/date.js"></script>

<script type="text/javascript" src="${context}/assets/js/dashboard_function.js"></script>

<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.resize.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.time.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.fillbetween.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.orderBars.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.pie.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/chart.flot/jquery.flot.tooltip.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/chart.js/Chart.min.js"></script>

<!-- PAGE LEVEL SCRIPT -->
<script type="text/javascript">

    var currentType = 'DAY';
    var setChardData = new Object();

    function goUriHelper(uri) {
        console.log('${admin_mode}')
        if (uri == 'contact') {		// 콘솔	문의사항
            if ('${admin_mode}' != 1 && '${admin_mode}' != 0) {
                authAlert();
            } else {
                location.href = '${context}/admin/user/contact'
            }
        } else if (uri == 'audit') {	//레포트	에이전트감사로그
            if ('${admin_mode}' != 2 && '${admin_mode}' != 0) {
                authAlert();
            } else {
                location.href = '${context}/report/audit/agent'
            }
        } else if (uri == 'account') {		//콘솔	사용자 등록요청
            if ('${admin_mode}' != 1 && '${admin_mode}' != 0) {
                authAlert();
            } else {
                location.href = '${context}/admin/user/enroll'
            }
        } else if (uri == 'policy') {		//콘솔	정책변경요청
            if ('${admin_mode}' != 1 && '${admin_mode}' != 0) {
                authAlert();
            } else {
                location.href = '${context}/admin/user/request'
            }
        }

    }

    function authAlert() {
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

    function getChartData(input) {
        var data;
        $.ajax({
            type: "POST",
            url: '${context}/ax/admin/statistic/chart',
            async: false,
            data: input,
            success: function (args) {
                //console.log(args)
                data = args;

            },
            //beforeSend:showRequest,
            error: function (e) {
                console.log(e.responseText);
            }
        });
        return data;
    }

    function onTypeCheck(radio) {
        if (currentType != radio.value) {
            currentType = radio.value;
            setChartData(currentType);
        }
    }

    function setChartData(type) {
        filter = new Object();
        if (type == 'DAY') {	//일
            $('.day_input').show();
            $('.month_input').hide();

        } else if (type == 'MONTH') {	//월
            $('.day_input').hide();
            $('.month_input').show();

        }

    }


    /* 		var startdate="2017-03-20 00:00:00"
     */



    //console.log((new Date().getTime() / 1000));

    function setChartSetting() {

        var chartSetting = new Object();
        var startdate = new Date().format("yyyy-MM-dd 00:00:00");
        chartSetting.setType = 'DAY';
        chartSetting.dateFormat = "%y-%0m-%0d";
        chartSetting.timeFormat = "%m-%d";


        if (currentType == 'DAY') {
            chartSetting.setRange = '30';
            if ($('#filterEndDate').val() != '') {
                startdate = new Date($('#filterEndDate').val()).format("yyyy-MM-dd 00:00:00");
                console.log(startdate);
            }
            if ($('#filterDateRange').val() != '') {
                chartSetting.setRange = $('#filterDateRange').val();
            }
            chartSetting.setType = 'DAY';
            chartSetting.dateFormat = "%y-%0m-%0d";
            chartSetting.timeFormat = "%m/%d";


        } else if (currentType == 'MONTH') {
            chartSetting.setRange = '12';

            if ($('#filterEndMonth').val() != '') {
                startdate = new Date($('#filterEndMonth').val().toString() + '-01').format("yyyy-MM-01 00:00:00");
                console.log(startdate);
            } else {
                startdate = new Date().format("yyyy-MM-01 00:00:00");
            }
            if ($('#filterMonthRange').val() != '') {
                chartSetting.setRange = $('#filterMonthRange').val();
            }
            chartSetting.setType = 'MONTH';
            chartSetting.dateFormat = "%y-%0m";
            chartSetting.timeFormat = "%y/%m";

        }

        var a = startdate.split(" ");
        var d = a[0].split("-");
        var t = a[1].split(":");
        criteriaTime = new Date(d[0], (d[1] - 1), d[2], t[0], t[1], t[2]);

        chartSetting.setValue = criteriaTime.getTime();

        return chartSetting;
    }

    function chartReSetData() {
        setChardData = setChartSetting();
        setFlotChart();
    }

    var option = null;
    var plot = null;
    var togglePlot = function (seriesIdx) {
        var someData = plot.getData();
        someData[seriesIdx].lines.show = !someData[seriesIdx].lines.show;
        someData[seriesIdx].points.show = !someData[seriesIdx].points.show;

        plot.setData(someData);
        //plot.setupGrid();
        //plot.draw();
        plot = jQuery.plot(jQuery("#flot-sales"), someData, options);

    }

    setChardData = setChartSetting();

    function setFlotChart() {
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
            $color_grid_color = "#dddddd"			/* silver	 	*/
            $color_main = "#E24913";		/* red       	*/
            $color_second = "#6595b4";		/* blue      	*/
            $color_third = "#FF9F01";		/* orange   	*/
            $color_fourth = "#7e9d3a";		/* green     	*/
            $color_fifth = "#BD362F";		/* dark red  	*/
            $color_mono = "#000000";		/* black 	 	*/

            var datasets = {
                "USB": {
                    label: "상용 소프트웨어",
                    data: chartData.PRINT.item,
                    idx: 0
                },
                "EXPORT": {
                    label: "무료 소프트웨어",
                    data: chartData.PATTERN.item,
                    idx: 1
                },
                "PRINT": {
                    label: "미검증 소프트웨어",
                    data: chartData.USB.item,
                    idx: 2

                },
                "PATTERN": {
                    label: "불법 소프트웨어",
                    data: chartData.EXPORT.item,
                    idx: 3
                }
            }

            var data = [];
            var i = 0;
            data.push(datasets['USB']);
            data.push(datasets['EXPORT']);
            data.push(datasets['PRINT']);
            data.push(datasets['PATTERN']);


            options = {

                xaxis: {
                    mode: "time",
                    tickLength: 1,
                    tickSize: [1, input.setType.toLowerCase()],
                    tickDecimals: 0,
                    timeformat: input.timeFormat,
                    timezone: "browser"
                },
                yaxis: {
                    tickDecimals: 0,
                },
                series: {
                    lines: {
                        show: true,
                        lineWidth: 1,
                        fill: true,
                        fillColor: {
                            colors: [{
                                opacity: 0.1
                            }, {
                                opacity: 0.15
                            }]
                        }
                    },
                    points: {show: true},
                    shadowSize: 0
                },

                selection: {
                    mode: "x"
                },
                points: {
                    show: true
                },
                grid: {
                    hoverable: true,
                    clickable: true,
                    tickColor: $color_border_color,
                    borderWidth: 0,
                    borderColor: $color_border_color,
                },

                tooltip: true,

                tooltipOpts: {
                    content: "일자: %x <span class='block'>%y개</span>",
                    dateFormat: input.dateFormat,
                    defaultTheme: false
                },
                legend: {
                    labelFormatter: function (label, series) {
                        return '<a href="#" onClick="togglePlot(' + series.idx + '); return false;">' + label + '</a>';
                    }
                },
                colors: [$color_main, $color_second, $color_third, $color_fourth],
            };

            /* 				loadScript(plugin_path + "chart.flot/jquery.flot.resize.min.js", function(){
             */
            plot = jQuery.plot(jQuery("#flot-sales"), data, options);
        }

    }

    jQuery('#preloader').hide();

    $(function () {
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
            "ajax": {
                "url": '${context}/ax/simplerequest/list',
                "type": 'POST',
                "dataSrc": "data",
                "data": function (param) {
                },
                "beforeSend": function () {
                },
                "dataSrc": function (json) {
                    return json.data;
                }
            },
            tableTools: {},
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
                "zeroRecords": "검색된 데이터가 없습니다.",
                "lengthMenu": "  _MENU_ 개",
                "paginate": {
                    "previous": "Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                },

            },
            "columnDefs": [
                {
                    "targets": [0],
                    "class": "center-cell",
                    "render": function (data, type, row) {
                        if (data == 2) {
                            return "계정등록요청"
                        } else if (data == 1) {
                            return "정책변경요청"
                        }

                    }

                },
                {  // set default column settings
                    'targets': [1]
                    , "class": "center-cell"
                }, {
                    "targets": [2]
                    , "class": "center-cell"
                }, {
                    "targets": [3]
                    , "class": "center-cell",
                    "render": function (data, type, row) {
                        return '<a href="#" class="btn btn-default btn-xs btn-block">View</a>';
                    },
                    "visible": false
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
            "ajax": {
                "url": '${context}/ax/simplecantact/list',
                "type": 'POST',
                "dataSrc": "data",
                "data": function (param) {
                },
                "beforeSend": function () {
                },
                "dataSrc": function (json) {
                    return json.data;
                }
            },
            tableTools: {},
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
                "visible": false
            }],
            // set the initial value
            "pageLength": 20,
            "iDisplayLength": 20,
            "pagingType": "bootstrap_full_number",
            "language": {
                "info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 개 로그",
                "infoEmpty": "검색된 데이터가 없습니다.",
                "zeroRecords": "검색된 데이터가 없습니다.",
                "lengthMenu": "  _MENU_ 개",
                "paginate": {
                    "previous": "Prev",
                    "next": "Next",
                    "last": "Last",
                    "first": "First"
                },

            },
            "columnDefs": [
                {
                    "targets": [0],
                    "class": "center-left"
                },
                {  // set default column settings
                    'targets': [1]
                    , "class": "center-cell"
                }, {
                    "targets": [2]
                    , "class": "center-cell"
                }, {
                    "targets": [3]
                    , "class": "center-cell",
                    "render": function (data, type, row) {
                        return '<a href="#" class="btn btn-default btn-xs btn-block">View</a>';
                    }
                }]
        });

    })


    // -- Pie Chart Example
    var ctx = document.getElementById("myPieChart");
    var myPieChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ["상용 소프트웨", "무료 소프트웨어", "비인가 소프트웨어", "미검증 소프트웨어"],
            datasets: [{
                data: [12.21, 15.58, 11.25, 8.32],
                backgroundColor: ['#007bff', '#28a745', '#dc3545', '#ffc107'],
            }],
        },
    });

</script>

</body>

</html>