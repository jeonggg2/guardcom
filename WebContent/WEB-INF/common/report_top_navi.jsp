<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<% 
	String user_id = (String)session.getAttribute("user_id");
	String admin_mode =  session.getAttribute("admin_mode").toString();
%>
	<!-- JAVASCRIPT FILES -->
	<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
	<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${context}/assets/plugins/stable/jquery.layout.js"></script>
	<script type="text/javascript" src="${context}/assets/js/app.js"></script>
	<script type="text/javascript" src="${context}/assets/plugins/select2/js/select2.full.min.js"></script>
	<script type="text/javascript" src="${context}/assets/js/admin_function.js"></script>
	<script type="text/javascript" src="${context}/assets/js/report_function.js"></script>
	<script type="text/javascript" src="${context}/assets/plugins/jstree/jstree.min.js"></script>

    <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
    <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

	<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/dataTables.bootstrap.min.js"></script>
<%-- 		<script type="text/javascript" src="${context}/assets/plugins/datatables/extensions/resize/colResizable.js"></script>
 --%>
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
    $( document ).idleTimer(3000);	//3?????? ???????????? ????????? ???????????? ??????

});

function setTimeUI(){
	
	if(dy_time == 0){
		clearInterval(intervalID);
		location.href = '${context}/logout'
	}else if(dy_time > 59){	//1?????? ????????? ????????? ??????
		$('#remain_time').html(Math.floor(dy_time/60) + '???')		
	}else if(dy_time < 60){	//1??????????????? ?????? ??????
		$('#remain_time').html(dy_time + '???')				
	}
	dy_time = dy_time - 1;
}

function moveReport(){
	// 0 ?????????, 1 ??????, 2 ????????? -1 ?????????
	if(<%= admin_mode %> == '0' ||  <%= admin_mode %> == '1'){
		location.href = '${context}/dashboard';
	}else{
		alert('????????? ????????????.')
	}
}


$( document ).on( "idle.idleTimer", function(event, elem, obj){			//3?????? ???????????? ????????? ????????? ?????? ??????
	intervalID = setInterval('setTimeUI()', 1000)

});

$( document ).on( "active.idleTimer", function(event, elem, obj){		//???????????? ?????? timeout?????? ??????
	clearInterval(intervalID);
	dy_time = timeout;
	setTimeUI()
});
</script>
 
<header id="header">

		<!-- Mobile Button -->
		<button id="mobileMenuBtn"></button>

		<!-- Logo -->
		<span class="logo pull-left">
			<img src="${context}/assets/images/logo_rpt_light.png" alt="admin panel" />
		</span>

		<nav>

			<!-- OPTIONS LIST -->
			<ul class="nav pull-right">
							<li class="hidden-xs pull-left" style="height:50px; line-height: 50px;">
	
								<span >
									<i class="fa fa-clock-o" aria-hidden="true"></i>
									????????????:
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
							<a onclick="moveReport()"><i class="fa fa-th-large"></i>????????????</a>
						</li>

						<li class="divider"></li>
						<li><!-- logout -->
							<a href="${context}/logout"><i class="fa fa-power-off"></i>????????????</a>
						</li>
					</ul>
				</li>
				<!-- /USER OPTIONS -->

			</ul>
			<!-- /OPTIONS LIST -->

		</nav>

</header>

