<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
	String user_name = (String)session.getAttribute("user_nm");
%> 

<!-- wrapper -->
<div id="wrapper">
	<!-- Top Bar -->
	<div id="topBar">
		<div class="container">
			<!-- right -->
			<ul class="top-links list-inline pull-right">
				<li class="text-welcome hidden-xs"><strong><%= user_name %>님&nbsp;</strong>환영합니다.</li>
				<li>
					<a class="dropdown-toggle no-text-underline" data-toggle="dropdown" href="#"><i class="fa fa-user hidden-xs"></i> MY INFO</a>
					<ul class="dropdown-menu pull-right">
						<li><a tabindex="-1" href="${context}/userinfo"><i class="fa fa-cog"></i> MY SETTINGS</a></li>
						<li class="divider"></li>
						<li><a tabindex="-1" href="${context}/logout"><i class="glyphicon glyphicon-off"></i> LOGOUT</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- /Top Bar -->
	
	<!-- HEADER -->
	<div id="header" class="sticky clearfix">
		<!-- TOP NAV -->
		<header id="topNav">
			<div class="container">

				<!-- Mobile Menu Button -->
				<button class="btn btn-mobile" data-toggle="collapse" data-target=".nav-main-collapse">
					<i class="fa fa-bars"></i>
				</button>

				<!-- Logo -->
				<a class="logo pull-left" href="${context}/main">
					<img src="${context}/assets/images/guardcom_user_logo.png" alt="" />
				</a>

				<!-- 
					Top Nav 
					AVAILABLE CLASSES:
					submenu-dark = dark sub menu
				-->
				<div class="navbar-collapse pull-right nav-main-collapse collapse submenu-dark">
					<nav class="nav-main">

						<!--
							NOTE
							
							For a regular link, remove "dropdown" class from LI tag and "dropdown-toggle" class from the href.
							Direct Link Example: 

							<li>
								<a href="#">HOME</a>
							</li>
						-->
						<ul id="topMain" class="nav nav-pills nav-main">
							<li class="dropdown"><!-- HOME -->
								<a href="${context}/main">HOME</a>
							</li>
							<li class="dropdown"><!-- NOTICE -->
								<a href="${context}/notice">NOTICE</a>
							</li>
<!-- 							<li class="dropdown mega-menu"> -->
<%-- 								<a href="${context}/contact">CONTACT</a> --%>
<!-- 							</li> -->
						</ul>
					</nav>
				</div>

			</div>
		</header>
		<!-- /Top Nav -->
	</div>
	</div>
	<!-- /HEADER -->