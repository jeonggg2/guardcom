<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <% String menu_parent = request.getParameter("menu_parent"); %>
 --%>
 
 
<aside id="aside">
	<nav id="sideNav">
		<!-- MAIN MENU -->
		<ul class="nav nav-list">
			<li ${menu_parent == 100 ? 'class="active"' : ''}><a href="${context}/report/dashboard"> <i class="main-icon fa fa-bar-chart"></i> <span>대시보드 </span>
			</a>
			<li ${menu_parent == 1000 ? 'class="active"' : ''}><a href="#"> <i class="main-icon fa fa-users"></i> <span>사용자정보 </span>
			</a>
				<ul>
					<li ${menu_sub_first == 1100 ? 'class="active"' : ''}><a href="${context}/report/users">사용자</a></li>
					<li ${menu_sub_first == 1300 ? 'class="active"' : ''}><a href="${context}/report/agents">에이전트정보</a></li>
					<li ${menu_sub_first == 1200 ? 'class="active"' : ''}><a href="${context}/report/login">로그인로그</a></li>
				</ul>
			</li>

			<li ${menu_parent == 2000 ? 'class="active"' : ''}><a href="#">
					<i class="main-icon fab fa-usb"></i> <span>장치로그</span>
			</a>
				<ul>
					<li ${menu_sub_first == 2100 ? 'class="active"' : ''}><a href="${context}/report/usbunauth">비인가 USB목록</a></li>
					<li ${menu_sub_first == 2200 ? 'class="active"' : ''}><a href="${context}/report/disktran">디스크 반출로그</a></li>
					<li ${menu_sub_first == 2300 ? 'class="active"' : ''}><a href="${context}/report/usbblock">USB차단로그</a></li>
					<li ${menu_sub_first == 2400 ? 'class="active"' : ''}><a href="${context}/report/print">프린트로그</a></li>

 					<li ${menu_sub_first == 2500 ? 'class="active"' : ''}><a href="${context}/report/diskconnectlog">디스크연결로그</a></li>
					<li ${menu_sub_first == 2600 ? 'class="active"' : ''}><a href="${context}/report/partitionconnectlog">파티션연결로그</a></li>
 					<li ${menu_sub_first == 2700 ? 'class="active"' : ''}><a href="${context}/report/rmvdisktran">이동식디스크 반출로그</a></li>
 					<!-- <li ${menu_sub_first == 2800 ? 'class="active"' : ''}><a href="${context}/report/cdexportlog">CD반출로그</a></li> -->
 				</ul>

			</li>
			<li ${menu_parent == 3000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-th-large"></i> <span>정책/감사로그</span>
			</a>
				<ul>
					<li ${menu_sub_first == 3100 ? 'class="active"' : ''}><a href="${context}/report/policy">정책변경로그</a></li>
					<li ${menu_sub_first == 3200 ? 'class="active"' : ''}><a href="${context}/report/audit/agent">에이전트감사로그</a></li>
					<li ${menu_sub_first == 3300 ? 'class="active"' : ''}><a href="${context}/report/audit/server">서버감사로그</a></li>
				</ul>
			</li>
			<li ${menu_parent == 4000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-commenting"></i> <span>정보로그</span>
			</a>
				<ul>
					<li ${menu_sub_first == 4100 ? 'class="active"' : ''}><a href="${context}/report/mail">메일로그</a></li>
					<li ${menu_sub_first == 4200 ? 'class="active"' : ''}><a href="${context}/report/msntalk">메신저대화로그</a></li>
					<li ${menu_sub_first == 4300 ? 'class="active"' : ''}><a href="${context}/report/msnfile">메신저파일로그</a></li>
					<li ${menu_sub_first == 4400 ? 'class="active"' : ''}><a href="${context}/report/privacy">민감정보로그</a></li>
				</ul>
			</li>

 			<li ${menu_parent == 5000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-tasks"></i> <span>파일/네트워크로그</span>
			</a>
				<ul>
					<li ${menu_sub_first == 5100 ? 'class="active"' : ''}><a href="${context}/report/fileownership">파일소유권변경로그</a></li>
					<li ${menu_sub_first == 5200 ? 'class="active"' : ''}><a href="${context}/report/fileexport">파일반출/생성로그</a></li>
					<li ${menu_sub_first == 5300 ? 'class="active"' : ''}><a href="${context}/report/netport">네트워크포트로그</a></li>
					<!-- <li ${menu_sub_first == 5400 ? 'class="active"' : ''}><a href="${context}/report/netexport">네트워크반출로그</a></li> -->
				</ul>
			</li>


		</ul>
	</nav>

	<span id="asidebg">
		<!-- aside fixed background -->
	</span>
</aside>

		<!-- PRELOADER -->
<div id="preloader" >
	<div class="inner">
		<span class="loader"></span>
	</div>
</div><!-- /PRELOADER -->
