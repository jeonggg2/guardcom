<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Console : Policy Template</title>

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

			<% request.setAttribute("menu_parent", 3000); %> 
			<% request.setAttribute("menu_sub_first", 3300); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>정책 템플릿 관리</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div id="layout-container" class="row" style="width: 100%; height: 100%;">
					
						<div class="ui-layout-west">
							<div id="panel-tree" class="panel panel-default">
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>조직도</strong> <!-- panel title -->
									</span>
								</div>

								<!-- panel content -->
								<div id="dept_tree" class="panel-body" style="padding-left: 0px; padding-right: 0px;">

								</div>
								<!-- /panel content -->

							</div>
							<!-- /PANEL -->
					
						</div>
						
						<div class="ui-layout-center">
							<div id="panel-list" class="panel panel-default">
								
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>정책 정보</strong> <!-- panel title -->
									</span>
								</div>
								
								<!-- panel content -->
								<div class="panel-body" id = "form_body">
									<div class="row">
										<div class="col-md-12">										
										</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">									
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
		
		<script type="text/javascript">
			
			var selectedPolicyNo = 0;
			
		 	function setTree(){
		 		
				$.ajax({      
			        type:"POST",  
			        url:'${context}/common/tree/selectdept',
			        async: false,
			        success:function(args){   
			        	$("#dept_tree").html(args);
			            $("#org_tree").bind(
					        "select_node.jstree", function(evt, data){
					        	var dept = data.selected[0];
					        	selectedDeptNo = dept;
					        	selectedPolicyNo = data.node.data;
					        	getDept();
					        }
						);
			            
			            $("#org_tree").on('loaded.jstree', function() {
				            $("#org_tree").jstree('select_node', '1');
			             });
			        }, 
			        error:function(e){  
			            console.log(e.responseText);  
			        }  
			    }); 
			}
		 			 	
		 	function getDept(){
				jQuery('#preloader').show();				
		 		$.ajax({      
			        type:"GET",  
			        url:'${context}/admin/policy/get_policy_form',
			        async: true,
			        data:{
			        	no: selectedPolicyNo,
			        	_:$.now()
			        },
			        success:function(args){   
			        	$("#form_body").html(args);
			    		jQuery('#preloader').hide();
			        },
			        error:function(e){  
			            console.log(e.responseText);
			    		jQuery('#preloader').hide();
			        }  
			    });  		
		 	}
		 	
			$(document).ready(function(){				
				jQuery('#preloader').hide();				
				setTree();
		    });		
			
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
		 	
			$(document).ajaxComplete(function(){
				initLayout();
			});
		</script>
	</body>
</html>