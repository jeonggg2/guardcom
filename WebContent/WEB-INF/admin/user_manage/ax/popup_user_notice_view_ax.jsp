<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="gcom.user.model.UserNoticeModel"%>
<%@ page import="gcom.Model.FileInfoModel"%>
<% 
	UserNoticeModel data = (UserNoticeModel)request.getAttribute("UserNoticeDetail");
	boolean fileFlag = "Y".equals(request.getAttribute("att_file_flag").toString())? true : false ;
	FileInfoModel file = (FileInfoModel)request.getAttribute("AttFileInfo");
	
%>

<div id="modalNoticeDetailView" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog" style="width:940px;">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="myModalLabel">공지사항 상세보기</h4>
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
										<strong>공지사항 내용</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div style="padding: 10px 20px;">
											<h1 class="blog-post-title" style="margin: 5px 0;"><% if("Y".equals(data.getBbsSpecialYN())){%><i class="fa fa-star" style="color:#e46c6c;"></i>&nbsp;&nbsp;<%}%><%= data.getBbsTitle() %></h1>
											<ul class="blog-post-info list-inline" style="margin: 0">
												<li>
													<i class="fa fa-clock-o"></i> 
													<span class="font-lato">작성일 : <%= data.getBbsRegDate() %></span>
												</li>
												<li>
													<i class="fa fa-eye" aria-hidden="true"></i> 
													<span class="font-lato">조회 : <%= data.getBbsClickCnt() %></span>
												</li>
												<li>
													<i class="fa fa-user"></i> 
													<span class="font-lato"><%= data.getBbsRegStaf() %></span>
												</li>
											</ul>
											<!-- article content -->
											<div style="border:1px solid #f1f1f1; padding:10px 10px; min-height: 500px; margin-top: 15px; ">
												<%= data.getBbsBody() %>
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
					</div>
					<!-- file DownLoad -->
					<div class="row">
						<div class="fl_left" style="width:50%">
						<% if (fileFlag) { %>
							<ul class="blog-post-info list-inline" style="margin:0;">
								<li>
									<i class="fa fa-file" aria-hidden="true"></i> 
									<span class="font-lato">첨부파일 : </span>
								</li>
								<li>
									<a href="#" onClick="javascript:fn_file_download();">
										 <%= file.getAttViewFileName() %></span>
									</a>
								</li>
							</ul>
							
							<form id="formFileDown" action="${context}/common/filedownload" method="post">
								<input type="hidden" name="save_name" id="save_name" value="<%= file.getAttSaveFileName() %>" />
								<input type="hidden" name="real_name" id="real_name" value="<%= file.getAttViewFileName() %>" />
							</form>
						<% } %>
						</div>
						<div class="fl_right" style="width:50%; height:40px;">
							<button type="button" id="btnNoticeModify" class="btn btn-amber pull-right" style="margin:0 20px;" ><i class="fa fa-check"></i> 수정</button>
						</div>
					</div>
					<!-- /file DownLoad -->
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
		
		$('#btnNoticeModify').click(function(){
			fn_modify('<%= data.getBbsId() %>','<%= data.getBbsAttfileYN() %>','<%= data.getAttfileId() %>');
		});
		
	});
	
	function fn_file_download() {
		$('#formFileDown').submit();
	}
	
</script>
