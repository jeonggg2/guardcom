<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<% 
	String user_id = (String)session.getAttribute("user_id");
	String admin_mode =  session.getAttribute("admin_mode").toString();
%>
<header id="header">

				<!-- Mobile Button -->
				<button id="mobileMenuBtn"></button>

				<!-- Logo -->
				<span class="logo pull-left">
					<img src="${context}/assets/images/logo_light.png" alt="admin panel" height="35" />
				</span>
				<nav>
					<!-- OPTIONS LIST -->
					<ul class="nav pull-right">
						<li class="pull-left" style="height:50px; line-height: 50px;">
							<span >
								<i class="fa fa-clock-o" aria-hidden="true"></i>
								남은시간:
								<span class="hidden-xs" id="remain_time">
								</span>
							</span>
						</li>

						<!-- USER OPTIONS -->
						<li class="dropdown pull-left">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
								<img class="user-avatar" src='${context}/assets/images/EditProfile-512.png' alt="" height="34" /> 
								<span class="user-name">
									<span class="hidden-xs">
										<%= user_id %> <i class="fa fa-angle-down"></i>
									</span>
								</span>
							</a>
							<ul class="dropdown-menu hold-on-click">
								<li><!-- settings -->
									<a onclick="moveReport()"><i class="fa fa-th-large"></i>레포트페이지</a>
								</li>
								<li class="divider"></li>
								<li><!-- logout -->
									<a href="${context}/logout"><i class="fa fa-power-off"></i>로그아웃</a>
								</li>
							</ul>
						</li>
						<!-- /USER OPTIONS -->

					</ul>
					<!-- /OPTIONS LIST -->

				</nav>
</header>
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
		<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery-2.2.3.min.js"></script>
		<script type="text/javascript" src="${context}/assets/js/app.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/select2/js/select2.full.min.js"></script>
		<script type="text/javascript" src="${context}/assets/js/admin_function.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/jstree/jstree.min.js"></script>
	
	    <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
        <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
	
		<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/dataTables.bootstrap.min.js"></script>

		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/buttons.jqueryui.min.js"></script>

		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/buttons.print.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/buttons.html5.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/Buttons/js/buttons.colVis.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/idle-timer/idle-timer.min.js"></script>
		

<script>

var timeout = parseInt('${timeout}');	
var dy_time = timeout;	
var intervalID = 0;

$(document).ready(function(){
	setTimeUI(timeout)
    $( document ).idleTimer(3000);	//3초간 움직이지 않으면 타임아웃 시작

});

function setTimeUI(){
	
	if(dy_time == 0){
		clearInterval(intervalID);
		location.href = '${context}/logout'
	}else if(dy_time > 59){	//1분이 넘을시 분으로 표기
		$('#remain_time').html(Math.floor(dy_time/60) + '분')		
	}else if(dy_time < 60){	//1분미만일시 초로 표기
		$('#remain_time').html(dy_time + '초')				
	}
	dy_time = dy_time - 1;
}

function moveReport(){
	// 0 관리자, 1 콘솔, 2 레포트 -1 사용자
	if(<%= admin_mode %> == '0' ||  <%= admin_mode %> == '2'){
		location.href = '${context}/report/dashboard';
	}else{
		alert('권한이 없습니다.')
	}
}

$( document ).on( "idle.idleTimer", function(event, elem, obj){			//3초간 움직이지 않으면 카운트 다운 시작
	intervalID = setInterval('setTimeUI()', 1000)

});

$( document ).on( "active.idleTimer", function(event, elem, obj){		//액티브시 다시 timeout으로 세팅
	clearInterval(intervalID);
	dy_time = timeout;
	setTimeUI()
});
	
	
</script>

