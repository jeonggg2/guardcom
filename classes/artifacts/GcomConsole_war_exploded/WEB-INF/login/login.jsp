<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="utf-8">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GuardCom</title>
		
		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />
		<!--[if IE]><meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'><![endif]-->
	
		<!-- CORE CSS -->
		<link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		 
		<!-- THEME CSS -->
		<link href="${context}/assets/css/user_essentials.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/user_layout.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/color_scheme/darkblue.css" rel="stylesheet" type="text/css" id="color_scheme" />
		
		<!-- Alert -->
		<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />
		
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
		
		<style type="text/css">
			.blur-bg {
				position:relative;
				display: flex;
				align-items: center;
				flex-direction: column;
				justify-content: center;
				width: 100%;
				height: 100%;
				padding: 20px;
				background: url("${context}/assets/images/guard_login_bg.jpg") no-repeat center center fixed;
				background-size: cover; 
				/*background-color:#35373d;*/
			}

			.login-bg-color {
				background-color: rgb(242, 242, 242);
			}
		
			.radio_login {
				padding:0px 5px;
				display:inline-block;
			}
		</style>
	</head>
	<body class="smoothscroll enable-animation ">
		<!-- wrapper -->
		<div id="wrapper" class="blur-bg">
			<!--
			<section class="page-header page-header-xs">
				<div class="container">
				</div>
			</section> -->
		
			<!-- login section -->
			<section style="border:0;">
				<div class="container">
					<div class="row">
						<div class="col-md-6 col-md-offset-3" >
							<div class="box-static box-border-top padding-30 text-center login-bg-color">
								<div class="box-title margin-bottom-30">
									<div style="width:300px; margin:0 auto;"><img src="${context}/assets/images/guardcom_login_logo.png" width="100%"></div>
								</div>
		
								<form id="frmLogin" name="frmLogin" class="nomargin" role="form" action="${context}/login/check" method="post">
									<div class="clearfix">
										<div class="form-group">
											<div class="radio_login"><label><input type="radio" name="loginType" value="U" checked/>사용자</label></div>
											<div class="radio_login"><label><input type="radio" name="loginType" value="C" />관리자</label></div>
											<!-- <div class="radio_login"><label><input type="radio" name="loginType" value="R" />Report</label></div> -->
										</div>
										<!-- ID -->
										<div class="form-group">
											<input required type="text" name="att_staf_id" id="att_staf_id" class="form-control" placeholder="ID" />
										</div>
										
										<!-- Password -->
										<div class="form-group">
											<input required type="password" name="att_staf_pwd" id="att_staf_pwd"
												   class="form-control" placeholder="Password" pattern="[A-Za-z0-9]*" />
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12 text-center">
											<button id="btnLogin" class="btn btn-green" style="width:100%;" >로그인</button>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12 text-center">
											<button type="button" id="btnSignIn" class="btn btn-primary" style="width:100%;" onclick="return false;" >사용자 등록요청</button>
										</div>
									</div>
		
								</form>
							</div>
						</div>
					</div>
					<!--<div class="row">
						<div class="margin-top-30 text-center">
							<a href="page-register-1.html"><strong>Create Account</strong></a>
						</div>
					</div>-->
				</div>
			</section>
			<!-- /login section -->
		
		</div>
		<div id="account_request_modal">
		</div>
		<!-- /wrapper -->
		
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
		<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery-2.2.3.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery.form.js" ></script>
		<script type="text/javascript" src="${context}/assets/js/scripts.js"></script>
		

		<script type="text/javascript">
			$(document).ready(function(){
				$("#btnLogin").on("click" , function(e){
					e.preventDefault();
					fn_login_proc();
				});
				
				$('#btnSignIn').on("click", function(e){
					fn_request_user_account();
				})
			});
			
			function fn_request_user_account(){
				console.log('123')
				$.ajax({      
			        type:"GET",  
			        url:'${context}/account/request/view',
			        async: false,
			        //data:{},
			        success:function(args){   
			        	console.log('321');
			            $("#account_request_modal").html(args);   
			            $('#modalRequestDetail').modal('show');
			        },   
			        //beforeSend:showRequest,  
			        error:function(e){  
			            console.log(e.responseText);  
			        }  
			    }); 

				
				
			}
			
			function fn_login_input_valid() {
				var id = $('#att_staf_id').val();
				var pw = $('#att_staf_pwd').val();
				
				vex.defaultOptions.className = 'vex-theme-os';
				
				if (id.length < 1 || id == '' ) {
					
		   			vex.dialog.open({
						message: 'ID 입력은 필수 입니다. 확인해주세요.',
						buttons: [
					    	$.extend({}, vex.dialog.buttons.YES, {
					     	text: '확인'
					  	})]
					});
					return false;
				}
				
				if (pw.length < 1 || pw == '' ) {
					vex.dialog.open({
						message: 'Password 입력은 필수 입니다. 확인해주세요.',
						buttons: [
					    	$.extend({}, vex.dialog.buttons.YES, {
					     	text: '확인'
					  	})]
					});
					return false;
				}
				
				return true;
			}
			
			function fn_login_proc() {
				var validator = fn_login_input_valid();
				
				if(!validator){
					return false;
				}
				
				var option = {
				        url:       		"${context}/login/check",
				    	type:      		"post",       
				    	success:     	fn_login_callback,
				    	fail:			callbackFail,
				    	cache: 			false,
				        resetForm: 		false 
				};
				$("#frmLogin").ajaxSubmit(option);
				
			}
			
			function callbackFail(){}

			function fn_login_callback(data){
				vex.defaultOptions.className = 'vex-theme-os';
				
				if(data.returnCode == "S"){
					location.reload();
				}else{
					switch (data.returnCode){
					  case "E":
						alert(data.message);
					    break;
					  case "NCPI":
						  vex.dialog.open({
								message: '아이디나 패스워드가 일치하지 않습니다.',
								buttons: [
							    	$.extend({}, vex.dialog.buttons.YES, {
							     	text: '확인'
							  	})]
							});
					    break;
					  case "NCI":
						vex.dialog.open({
							message: '비인가된 PC입니다.',
							buttons: [
						    	$.extend({}, vex.dialog.buttons.YES, {
						     	text: '확인'
						  	})]
						});
					    break;
					  default:
						vex.dialog.open({
							message: '서버와의 통신에 실패하였습니다.',
							buttons: [
						    	$.extend({}, vex.dialog.buttons.YES, {
						     	text: '확인'
						  	})]
						});
						break;
					}
				}
				
			}
		</script>
	</body>
</html>