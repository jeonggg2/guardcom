<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Console</title>

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
		
	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 4000); %> 
			<% request.setAttribute("menu_sub_first", 4100); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>USB관리</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">
						<div class="col-md-12">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>USB관리</strong> 
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
											<div class="aside-tabs">
												<ul class="nav nav-tabs nav-top-border">
													<li class="active"><a href="#usb_policy" data-toggle="tab" onClick="javascript:fn_get_tab_content('usb_policy');"><i class="fa fa-lock" title="usb정책 관리"></i>USB정책 관리</a></li>
													<li><a href="#usb_block" data-toggle="tab" onClick="javascript:fn_get_tab_content('usb_block');"><i class="fab fa-usb" title="USB 차단 현황"></i>비인가 USB차단현황</a></li>
												</ul>
												
												<div class="tab-content" id="tab-content-div">
												</div>
											</div>
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
			
			<div id="reg_usb_popup_div"></div>
		</div>

<script>
	$(document).ready(function(){
		fn_get_tab_content('usb_policy');
		jQuery('#preloader').hide();
	});
	
	function fn_get_tab_content(tabCode){
		
		$.ajax({      
		    type:"GET",  
		    url:'${context}/admin/device/tab',
		    async: false,
		    data:{ 
		    	tabCode : tabCode,
		    	_ : $.now()
		    },
		    success:function(data){
		    	$("#tab-content-div").html(data);
		    },   
		    error:function(e){  
		        console.log(e.responseText);  
		    }  
		});
	}
</script>
	</body>
</html>