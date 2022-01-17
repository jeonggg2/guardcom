<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

	<!-- FOOTER -->
	<footer id="footer">
		<div class="container">

			<div class="row">
				
				<div class="col-md-4">
					<!-- Footer Logo -->
					<img class="footer-logo" src="${context}/assets/images/user_footer_logo.png" alt="" />

					<!-- Small Description -->
					<p>내부자료 유출 방지 시스템</p>

					<!-- Contact Address -->
					<address>
						<ul class="list-unstyled">
							<li class="footer-sprite address">
								서울시 금천구 가산동 50-3<br>
								대륭 포스트타워6차 1012호<br>
							</li>
							<li class="footer-sprite phone">
								TEL : 02-713-8084 / FAX : 02-3272-8757
							</li>
							<li class="footer-sprite email">
								<!-- <a href="#">support@guarcom.com</a> -->
								support@guarcom.com
							</li>
						</ul>
					</address>
					<!-- /Contact Address -->

				</div>

				<div class="col-md-4">

					<!-- Latest Blog Post -->
					<h4 class="letter-spacing-1">LATEST NOTIC</h4>
					<ul class="footer-posts list-unstyled" id="latest_notice">
					</ul>
					<!-- /Latest Blog Post -->

				</div>

				<div class="col-md-4">

					<!-- Links -->
					<h4 class="letter-spacing-1">LINK PARTNERS</h4>
					<ul class="footer-links list-unstyled">
						<li><a href="http://www.sammun.co.kr" target="_black">SAMMUN SYSTEM</a></li>
						<!-- <li><a href="#">Binary.C</a></li>
						<li><a href="#">Naver</a></li>
						<li><a href="#">Kakao</a></li> -->
					</ul>
					<!-- /Links -->

					<!-- Social Icons -->
					<!-- 
					<div class="margin-top-20">
						<a href="#" class="social-icon social-icon-border social-facebook pull-left" data-toggle="tooltip" data-placement="top" title="Facebook">
							<i class="icon-facebook"></i>
							<i class="icon-facebook"></i>
						</a>

						<a href="#" class="social-icon social-icon-border social-twitter pull-left" data-toggle="tooltip" data-placement="top" title="Twitter">
							<i class="icon-twitter"></i>
							<i class="icon-twitter"></i>
						</a>

						<a href="#" class="social-icon social-icon-border social-gplus pull-left" data-toggle="tooltip" data-placement="top" title="Google plus">
							<i class="icon-gplus"></i>
							<i class="icon-gplus"></i>
						</a>

						<a href="#" class="social-icon social-icon-border social-linkedin pull-left" data-toggle="tooltip" data-placement="top" title="Linkedin">
							<i class="icon-linkedin"></i>
							<i class="icon-linkedin"></i>
						</a>
					</div>
					 -->
				</div>

				<div class="col-md-4">


				</div>

			</div>
		</div>

		<div class="copyright">
			<div class="container">
				<ul class="pull-right nomargin list-inline mobile-block">
					<!-- <li><a href="#">Terms &amp; Conditions</a></li>
					<li>&bull;</li>
					<li><a href="#">Privacy</a></li> -->
				</ul>
				&copy;Copyright - SAMMUN SYSTEM CO.,LTD. All Rights Reserved.
			</div>
		</div>
	</footer>
	
	<div id="latest_notice_detail"></div>
	<!-- /FOOTER -->


<!-- /wrapper -->
	
	<!-- SCROLL TO TOP -->
	<a href="#" id="toTop"></a>
	
	<!-- PRELOADER -->
	<div id="preloader">
		<div class="inner">
			<span class="loader"></span>
		</div>
	</div><!-- /PRELOADER -->
	
	<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
	<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="${context}/assets/js/scripts.js"></script>
	
	<script type="text/javascript">
			$(document).ready(function(){
				fn_get_latest_notice_list();
			});
			
			function fn_get_latest_notice_list() {
				
				$.ajax({      
			        type:"POST",  
			        url:'${context}/ax/footer/latest',
			        async: false,
			        data:{
			        	_ : $.now()
			        },
			        befoer:function(){
			        	$('#latest_notice').append('');
			        },  
			        success:function(data){
			        	var retValue = data.items;
			        	
			        	if (retValue.length > 0) {
				        	$.each(retValue, function( index, item){
				        		var row = '';
								row +='<li><a href="javascript:;" onClick="javascript:open_popup_latest_item('+item.bbs_id+',\''+item.attfile_yn+'\','+item.attfile_id+')">'+item.bbs_title+'</a>';
					        	row +='<small>'+item.reg_dt+'</small>';
					        	row +='</li>';
					        	$('#latest_notice').append(row);
							});
			        	} else {
			        		$('#latest_notice').append('<li>공지사항이 없습니다.</li>');
			        	}
			        	
			        },   
			        error:function(e){
			        	$('#latest_notice').append('<li>공지사항 불러오기에 실패했습니다.</li>');
			            console.log(e.responseText);  
			        }  
			    });
			}
			
			function open_popup_latest_item(bbs_id, file_yn, file_id) {
		
				$.ajax({       
				    type:"POST",  
				    url:'${context}/notice/view',
				    async: false,
				    data:{
				    	bbs_id : bbs_id,
			        	file_yn : file_yn,
			        	file_id : file_id,
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#latest_notice_detail").html(data);
			            $('#modalNoticeView').modal('show');
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
			}
						
		</script>
	