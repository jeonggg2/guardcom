<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <% String menu_parent = request.getParameter("menu_parent"); %>
 --%>
 
 
<aside id="aside">
	<nav id="sideNav">
		<!-- MAIN MENU -->
		<ul class="nav nav-list">
			<li ${menu_parent == 1000 ? 'class="active"' : ''}><a href="${context}/dashboard"> <i
					class="main-icon fa fa-bar-chart"></i> <span>대시보드 </span>
			</a>

			<li ${menu_parent == 2000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-users"></i> <span>사용자 관리 </span>
			</a>
				<ul>
					<li ${menu_sub_first == 2500 ? 'class="active"' : ''}><a href="${context}/admin/user/manage">사용자 정보</a></li>
					<li ${menu_sub_first == 2700 ? 'class="active"' : ''}><a href="${context}/admin/user/enroll">사용자 등록 요청</a></li>				
				</ul>

			<li ${menu_parent == 3000 ? 'class="active"' : ''}><a href="#">
					<i class="main-icon fa fa-th-large"></i> <span>정책 관리</span>
			</a>
				<ul>
					<li ${menu_sub_first == 3100 ? 'class="active"' : ''}><a href="${context}/admin/user/assign">정책 할당</a></li>
					<li ${menu_sub_first == 3200 ? 'class="active"' : ''}><a href="${context}/admin/user/request">정책 변경 요청</a></li>
 					<li ${menu_sub_first == 3300 ? 'class="active"' : ''}><a href="${context}/admin/policy/dept">부서 정책</a></li>
				</ul>
			</li>
			
			<li ${menu_parent == 4000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fab fa-usb"></i> <span>디바이스 관리</span>
			</a>
				<ul>
					<li ${menu_sub_first == 4100 ? 'class="active"' : ''}><a href="${context}/admin/device">USB 관리</a></li>
					<li ${menu_sub_first == 4200 ? 'class="active"' : ''}><a href="${context}/admin/diskadmin">디스크 관리</a></li>
				</ul>
			</li>
			
			<li ${menu_parent == 10000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-lock"></i> <span>암호화 관리</span>
			</a>
				<ul>
					<li ${menu_sub_first == 10100 ? 'class="active"' : ''}><a href="${context}/admin/drm/autodecprocess">열람 허용 프로세스</a></li>
					<li ${menu_sub_first == 10200 ? 'class="active"' : ''}><a href="${context}/admin/drm/autodecsite">자동 복호화 사이트</a></li>
					<li ${menu_sub_first == 10300 ? 'class="active"' : ''}><a href="${context}/admin/drm/autoencfileext">자동 암호화 확장자</a></li>
					<li ${menu_sub_first == 10400 ? 'class="active"' : ''}><a href="${context}/admin/drm/exceptprocess">예외 프로세스</a></li>
					<li ${menu_sub_first == 10500 ? 'class="active"' : ''}><a href="${context}/admin/drm/decreq">반출 요청</a></li>
				</ul>
			</li>
			
			<li ${menu_parent == 5000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-cogs"></i> <span>시스템 관리</span>
			</a>
				<ul>
					<li ${menu_sub_first == 5100 ? 'class="active"' : ''}><a href="${context}/admin/policy/messenger">메신저 감사</a></li>
					<li ${menu_sub_first == 5200 ? 'class="active"' : ''}><a href="${context}/admin/policy/process">프로세스 차단</a></li>
					<li ${menu_sub_first == 5300 ? 'class="active"' : ''}><a href="${context}/admin/policy/pattern">민감 정보</a></li>
					<li ${menu_sub_first == 5400 ? 'class="active"' : ''}><a href="${context}/admin/policy/network">네트워크 포트</a></li>
					<li ${menu_sub_first == 5500 ? 'class="active"' : ''}><a href="${context}/admin/policy/serial">시리얼 포트</a></li>
					<li ${menu_sub_first == 5600 ? 'class="active"' : ''}><a href="${context}/admin/policy/website">웹사이트 차단</a></li>					
					<!-- <li ${menu_sub_first == 3700 ? 'class="active"' : ''}><a href="${context}/admin/policy/water">워터마크</a></li> -->
					<li ${menu_sub_first == 5700 ? 'class="active"' : ''}><a href="${context}/admin/system/manage">공통 설정</a></li>
				</ul>
			</li>
			
			<li ${menu_parent == 7000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-server"></i> <span>자산 관리 </span>
			</a>
				<ul>
					<li ${menu_sub_first == 7100 ? 'class="active"' : ''}><a href="${context}/admin/itasset/hw">장비 현황</a></li>
					<li ${menu_sub_first == 7200 ? 'class="active"' : ''}><a href="${context}/admin/itasset/sw">소프트웨어 관리</a></li>
					<li ${menu_sub_first == 7300 ? 'class="active"' : ''}><a href="${context}/admin/itasset/dashboard">자산 분석</a></li>
				</ul>
				
			<li ${menu_parent == 8000 ? 'class="active"' : ''}><a href="${context}/admin/dept"> <i
					class="main-icon fa fa-sitemap"></i> <span>부서 관리</span>
			</a>
			</li>
			
			<li ${menu_parent == 9000 ? 'class="active"' : ''}><a href="#"> <i
					class="main-icon fa fa-list-alt"></i> <span>게시판 관리</span>
			</a>
				<ul>
					<li ${menu_sub_first == 9300 ? 'class="active"' : ''}><a href="${context}/admin/user/contact">문의 사항</a></li>
					<li ${menu_sub_first == 9600 ? 'class="active"' : ''}><a href="${context}/admin/user/notice">공지 사항</a></li>	
				</ul>
			</li>
			
			<li ${menu_parent == 6000 ? 'class="active"' : ''}><a href="${context}/admin/subadmin"> <i
					class="main-icon fa fa-vcard"></i> <span>계정 관리</span>
			</a>
			</li>
			
		</ul>
	</nav>

	<span id="asidebg">
		<!-- aside fixed background -->
	</span>
</aside>

<div id="preloader" >
	<div class="inner">
		<span class="loader"></span>
	</div>
</div> 
