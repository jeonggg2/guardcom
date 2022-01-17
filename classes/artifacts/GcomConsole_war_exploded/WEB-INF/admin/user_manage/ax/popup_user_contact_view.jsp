<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="gcom.user.model.UserContactModel"%>
<%@ page import="gcom.Model.FileInfoModel"%>
<% 
	UserContactModel data = (UserContactModel)request.getAttribute("UserContactDetail");
%>


<div id="modalContactDetailView" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog" style="width:940px;">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="myModalLabel">문의 사항 관리</h4>
			</div>
			<!-- /Modal Header -->
			
			<!-- Modal body -->
			<div class="modal-body">
				<div id="content" class="dashboard padding-20">
					<div class="row">
						
						<div class="col-md-12">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>문의사항 상세보기</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div style="padding: 10px 20px;">
											<h1 class="blog-post-title" style="margin: 5px 0;"><%= data.getContactTitle() %></h1>
											<ul class="blog-post-info list-inline">
												<li>
													<i class="fa fa-clock-o"></i> 
													<span class="font-lato">작성일 : <%= data.getRegDt() %></span>
												</li>
												<li>
													<span class="font-lato">답변여부 : <% if( "Y".equals(data.getCommentYN())) {%> <i class="fa fa-pencil"></i> 답변됨. <% } else { %> 답변없음. <% } %> </span>
												</li>
												<li>
													<i class="fa fa-user"></i> 
													<span class="font-lato">문의등록자 : <%= data.getRegUserName() %></span>
												</li>
											</ul>
												
											<!-- article content -->
											<div style="border:1px solid #f1f1f1; min-height:500px; padding:10px 20px;">
												<%= data.getContactBody() %>
											</div>
											<!-- /article content -->
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
						
						<div class="row">
							<button id="btnRegComment" class="btn btn-green pull-right" style="color:#fff; margin:0 30px;" onClick="javascript:fn_open_comment('<%= data.getContactId() %>');" <% if( "Y".equals(data.getCommentYN())) {%> disabled <% } %>  ><i class="fa fa-check"></i>답변등록</button>
						</div>
					</div>
				</div>
				<!-- /Modal body -->

				<!-- Modal Footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>

		
		<script type="text/javascript">
		$(document).ready(function(){
			jQuery('#preloader').hide();
		});
		
		</script>
	</body>
</html>