<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	String commentId = request.getAttribute("comment_id").toString();
	HashMap<String, Object> comment_info = (HashMap<String, Object>)request.getAttribute("comment_info");
%>
<script type="text/javascript" src="${context}/se2/js/service/HuskyEZCreator.js"></script>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
<div id="modalCommentModify" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myModalLabel">문의사항 답변 수정</h4>
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
											<strong>답변 수정</strong> <!-- panel title -->
										</span>
									</div>
		
									<!-- panel content -->
									<div class="panel-body">
									
										<textarea name="ir2" id="ir2" rows="10" cols="100" style="width:100%; min-height: 300px;"><%= comment_info.get("reply_content") %></textarea>

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
				<!-- /Modal body -->

				<!-- Modal Footer -->
				<div class="modal-footer">
					<button type="button" id="btnCommentModify" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">
var oEditors = [];

nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "ir2",
    sSkinURI: "${context}/se2/SmartEditor2Skin_ko_KR.html",
    fCreator: "createSEditor2"
});

function fn_comment_modify () {
	var commentId = '<%= commentId%>';
	oEditors.getById["ir2"].exec("UPDATE_CONTENTS_FIELD", []);
	var reply_content = $('#ir2').val();
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/user/comment/modify',
	    async: false,
	    data:{
	    	comment_id : commentId,
	    	reply_content : reply_content,
	    	_ : $.now()
	    },
	    success:function(data){
	    	vex.defaultOptions.className = 'vex-theme-os';
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalCommentModify').modal('hide');
	 	  		var datatable = $('#table_contact').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '문의 답변 수정이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		})
	    		
	    	} else {
	    		
    			vex.dialog.open({
    				message: '문의 답변 수정중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '확인'
    				  })],
    				  callback: function(data) {
   				 	  	if (data) {
   				 	  		$('#modalCommentModify').modal('hide');
   				 	    }
   				 	  }
    			});
	    	}
	    },   
	    error:function(e){
			vex.defaultOptions.className = 'vex-theme-os';
    		
    		vex.dialog.open({
				message: '서버와의 연결이 되지 않습니다.',
				buttons: [
			    	$.extend({}, vex.dialog.buttons.YES, {
			     	text: '확인'
			  	})]
			});
	        console.log(e.responseText);  
	    }  
	});
}

$(document).ready(function(){
	jQuery('#preloader').hide();
	
	$('#btnCommentModify').click(function(){
		fn_comment_modify();				
	});
});
	
</script>







