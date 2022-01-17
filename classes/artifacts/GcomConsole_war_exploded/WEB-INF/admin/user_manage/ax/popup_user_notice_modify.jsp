<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="gcom.user.model.UserNoticeModel"%>
<%@ page import="gcom.Model.FileInfoModel"%>
<% 
	UserNoticeModel data = (UserNoticeModel)request.getAttribute("UserNoticeDetail");
	boolean fileFlag = "Y".equals(request.getAttribute("att_file_flag").toString())? true : false ;
	FileInfoModel file = (FileInfoModel)request.getAttribute("AttFileInfo");
	
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

<script type="text/javascript" src="${context}/se2/js/service/HuskyEZCreator.js"></script>

<div id="modalNoticeModify" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog" style="width:940px;">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="myModalLabel">공지사항 관리</h4>
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
										<strong>공지사항 수정</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12" style="padding:10px 40px;">
											<div>
												<ul class="inline-list">
													<li>제목 : </li>
													<li style="width:50%;"><input required type="text" class="form-control" id="att_notice_title" name ="att_notice_title" placeholder="제목*" value="<%= data.getBbsTitle() %>" /></li>
													<li>
														<label class="checkbox"><input type="checkbox" value="Y" id="chk_special_box" name="chk_special_box" <% if("Y".equals(data.getBbsSpecialYN())){ %> checked <% } %> /><i></i>중요!</label>
													</li>
												</ul>
											</div>
											
											<textarea name="ir1" id="ir1" rows="10" cols="100" style="width:100%; min-height: 300px;"><%= data.getBbsBody() %></textarea>
											
											<div class="fl_left">
												<% if (fileFlag) { %>
													<!-- 실제 파일 업로드 input -->
													<input type="file" class="form-control hidden" id="att_file_upload" name="att_file_upload" />												
													<!-- 파일 업로드 View -->
													<div class="input-group m-b-xxs" style="max-width: 500px;">
														<span class="input-group-btn">
							                            	<button type="button" class="btn btn-primary"  onclick="document.getElementById('att_file_upload').click();">파일선택</button> 
							                            </span>
							                            <input type="text" class="form-control" id="att_file_upload_view" name="att_file_upload_view" value="<%= file.getAttViewFileName() %>" disabled>
							                            <span class="input-group-btn" style="width:120px;">
							                            	<button id="btnFileDelete" type="button" class="btn btn-amber"  onclick="deleteFile();">첨부파일삭제</button>
							                            </span>
								                    </div>
													
													<div id="uploadPer">
										    		</div>
										    		
										    		<input type="text" id="att_upload_save_filename" class="hidden" value="<%= file.getAttSaveFileName() %>">
							 						<input type="text" id="att_upload_view_filename" class="hidden" value="<%= file.getAttViewFileName() %>">
							 						<input type="text" id="att_upload_filepath" class="hidden" value="<%= file.getAttFilePath() %>">
						 						
						 						<% } else { %>
							 						<!-- 실제 파일 업로드 input -->
													<input type="file" class="form-control hidden" id="att_file_upload" name="att_file_upload" />												
													<!-- 파일 업로드 View -->
													<div class="input-group m-b-xxs" style="max-width: 500px;">
														<span class="input-group-btn">
							                            	<button type="button" class="btn btn-primary"  onclick="document.getElementById('att_file_upload').click();">파일선택</button> 
							                            </span>
							                            <input type="text" class="form-control" id="att_file_upload_view" name="att_file_upload_view" value="" disabled>
							                            <!-- 삭제버튼 -->
							                            <span class="input-group-btn" style="width:120px;">
							                            	<button id="btnFileDelete" type="button" class="btn btn-amber"  onclick="deleteFile();" style="display: none;">첨부파일삭제</button>
							                            </span>
								                    </div>
								                     
								                    
													
													<div id="uploadPer">
										    		</div>
										    		
										    		<input type="text" id="att_upload_save_filename" class="hidden" value="">
							 						<input type="text" id="att_upload_view_filename" class="hidden" value="">
							 						<input type="text" id="att_upload_filepath" class="hidden" value="">
						 						<% }  %>
											</div>
											<div class="fl_right">
												<button id="btnNoticeUpdate" class="btn btn-green pull-right" style="margin:0;"><i class="fa fa-check"></i>수정등록</button>
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
			</div>
			
			<!-- Modal Footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
			</div>
		</div>
	</div>
</div>
		
		<!-- file_upload-->
		<script src="${context}/assets/plugins/fileupload/jquery.ui.widget.js" charset="utf-8"></script>
		<script src="${context}/assets/plugins/fileupload/jquery.fileupload.js" charset="utf-8"></script>
		<script src="${context}/assets/plugins/fileupload/jquery.fileupload-ui.js" charset="utf-8"></script>
		<script src="${context}/assets/plugins/fileupload/jquery.iframe-transport.js" charset="utf-8"></script>
		
		<script type="text/javascript">
		var oEditors = [];
		
		nhn.husky.EZCreator.createInIFrame({
		    oAppRef: oEditors,
		    elPlaceHolder: "ir1",
		    sSkinURI: "${context}/se2/SmartEditor2Skin_ko_KR.html",
		    fCreator: "createSEditor2"
		});
		
		function fn_data_valid () {
			var title = $('#att_notice_title').val();
			
			if (title.length < 1 || title == '' ) {
				vex.defaultOptions.className = 'vex-theme-os';
	    		
	    		vex.dialog.open({
					message: '제목은 필수 입력 사항입니다.',
					buttons: [
				    	$.extend({}, vex.dialog.buttons.YES, {
				     	text: '확인'
				  	})]
				});
				return false;
			}			
			
			return true;
		}
		
		function fn_content_update () {
			
			if (!fn_data_valid()) {
				return false;
			}
			
			var bbsId = '<%= data.getBbsId() %>';
			var fileId = '<%= data.getAttfileId() %>';
			var title = $('#att_notice_title').val();
			oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
			var body = $('#ir1').val();
			var special = $('#chk_special_box').is(":checked") == true ? 'Y' : 'N';
			var body_trim = body.replace(/<\/?[^>]+(>|$)/g, "");
			var save_file_nm = $('#att_upload_save_filename').val();
       	 	var view_file_nm = $('#att_upload_view_filename').val();
       	 	var file_path = $('#att_upload_filepath').val();
       	 	
			var attfile_yn = 'N';
       	 	if(save_file_nm != '' || save_file_nm.length > 0) {
       	 		attfile_yn = 'Y'; 
       	 	}
       	 	
			$.ajax({      
			    type:"POST",  
			    url:'${context}/admin/user/notice/update',
			    async: false,
			    data:{
			    	bbsId : bbsId,
			    	fileId : fileId,
			    	title : title,
			    	body : body,
			    	special : special,
			    	body_trim : body_trim,
			    	save_file_nm : save_file_nm,
			    	view_file_nm : view_file_nm,
			    	file_path : file_path,
			    	attfile_yn : attfile_yn,
			    	_ : $.now()
			    },
			    success:function(data){
			    	vex.defaultOptions.className = 'vex-theme-os';
		    		
			    	if (data.returnCode == 'S') {
			    		vex.dialog.open({
			    			message: '공지사항이 수정 되었습니다.',
			    			  buttons: [
			    			    $.extend({}, vex.dialog.buttons.YES, {
			    			      text: '확인'
			    			  })],
			    			  callback: function(data) {
		    				 	  if (data) {
		    				 		  location.href = '${context}/admin/user/notice';
		    				 	  }
	    				 	  }
			   			});
			    	} else {
			    		vex.dialog.open({
			    			message: '공지사항 등록중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.',
			    			  buttons: [
			    			    $.extend({}, vex.dialog.buttons.YES, {
			    			      text: '확인'
			    			  })],
			    			  callback: function(data) {
			    				  if (data) {
			    					  $('#modalNoticeModify').modal('hide');
	    				 		  }
	    				 	  }
			   			});
			    	}
			    },   
			    error:function(e){  
			        console.log(e.responseText);  
			    }  
			});
		}
		
		var deleteFile = function() {
			 $('#att_upload_save_filename').val("");
        	 $('#att_upload_view_filename').val("");
        	 $('#att_upload_filepath').val("");
        	 $('#att_file_upload_view').val(""); 
        	 $('#btnFileDelete').css('display', 'none'); 
		}
		
		$(document).ready(function(){
			jQuery('#preloader').hide();
			
			$('#btnNoticeUpdate').click(function(){
				fn_content_update();				
			});
			
			$('#att_file_upload').fileupload({
				 url : '${context}/common/fileupload', 
		         dataType: 'json',
		         xhrFields: {
		             withCredentials: true
		         },
		         replaceFileInput : true,
		         add: function(e, data){
		             var uploadFile = data.files[0];
		             var isValid = true;
		             
		             /* if ((/png|jpe?g|gif/i).test(uploadFile.name)) {
		                 call_toast(4, '파일업로드실패', 'png, jpg, gif 만 가능합니다.');
		                 e.preventDefault();
		                 return false;
		             } else  */
		             if (uploadFile.size > 10000000) { 
		 				vex.defaultOptions.className = 'vex-theme-os';
			    		
			    		vex.dialog.open({
							message: '파일 용량은 10M 를 초과할 수 없습니다.',
							buttons: [
						    	$.extend({}, vex.dialog.buttons.YES, {
						     	text: '확인'
						  	})]
						});
		        		 e.preventDefault();
		        		 return false;
		             }
		             if (isValid) {
		                 data.submit();              
		             }
		         }, progressall: function(e,data) {
		             var progress = parseInt(data.loaded / data.total * 100, 10);
		                 $('#uploadPer').html("&nbsp;(" + progress + '%)')            	             	 
		            	 
		         }, done: function (e, data) {
		        	 if(data.result.returnCode == 'S'){
			        	 $('#uploadPer').html('<b style="color:blue">&nbsp;(업로드완료)</b>')  
			        	 $('#att_upload_save_filename').val(data.result.saveFileName);
			        	 $('#att_upload_view_filename').val(data.result.viewFileName);
			        	 $('#att_upload_filepath').val(data.result.filepath);
			        	 $('#att_file_upload_view').val(data.result.viewFileName); 
			        	 
			        	 $('#btnFileDelete').css('display', 'block');
		        	 }
		        	 else{
		        		 console.log(data.result.returnCode);
		        		 call_toast(4, '파일업로드실패', '서버와의 통신 장애로 파일업로드에 실패하였습니다. ( error code : ' + data.result.returnCode+  ' )');
			        	 $('#uploadPer').html('<b style="color:red">&nbsp;(전송실패)</b>')  

		        	 }
		        	 
		         }, fail: function(e, data){
		             // data.errorThrown
		             // data.textStatus;
		             // data.jqXHR;
		             vex.defaultOptions.className = 'vex-theme-os';
			    		
		    		vex.dialog.open({
						message: '서버와 통신 중 문제가 발생했습니다',
						buttons: [
					    	$.extend({}, vex.dialog.buttons.YES, {
					     	text: '확인'
					  	})]
					});
		             foo = data;
		             console.log(e);
		             console.log(data);
		             
		         }
			});
			
		});
		</script>
		