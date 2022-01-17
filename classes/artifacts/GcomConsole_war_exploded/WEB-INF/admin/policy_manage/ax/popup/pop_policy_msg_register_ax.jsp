<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="gcom.Model.PolicyMessengerModel"%>
<%
	
	int keyNo = Integer.parseInt(request.getAttribute("keyNo").toString());
	boolean modifyCheck = keyNo != 0? true : false ;
	String popTitle = "정책정보등록";
	
	String msgName = "";
	String msgProName = "";
	boolean txtLog = false;
	boolean txtBlock = false;
	boolean fileLog = false;
	boolean fileBlock = false;
	
	if(modifyCheck) {
		popTitle = "정책정보수정";
		PolicyMessengerModel data = (PolicyMessengerModel)request.getAttribute("data");
		msgName = data.getMsgName();
		msgProName = data.getProcessName();
		txtLog = data.getTxtLog();
		txtBlock = data.getTxtBlock();
		fileLog = data.getFileLog();
		fileBlock = data.getFileBlock();
	}
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
<div id="modalPolicyRegMessenger" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myModalLabel"><%= popTitle %></h4>
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
											<strong>정책정보설정</strong> <!-- panel title -->
										</span>
									</div>
		
									<!-- panel content -->
									<div class="panel-body">
										<table class="table table-bordered">
											<tbody>
												<tr>
													<td class="th-cell-gray center-cell" width="200px" style="vertical-align: middle;">메신저명</td>
													<td>
														<input type="text" id="att_msg_name" name="att_msg_name" class="form-control" value="<%= msgName %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >메신저 프로세스명</td>
													<td>
														<input type="text" id="att_msg_pro_name" name="att_msg_pro_name" class="form-control" value="<%= msgProName %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >대화 내용 로그 기록</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_txt_log" value="1" <% if(txtLog) {%> checked <% }%> /><i></i> 사용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_txt_log" value="0" <% if(!txtLog) {%> checked <% }%> /><i></i> 미사용
														</label>
													</td>
												</tr>
												<tr class="hidden">
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >대화 차단</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_txt_block" value="1" <% if(txtBlock) {%> checked <% }%> /><i></i> 사용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_txt_block" value="0" <% if(!txtBlock) {%> checked <% }%> /><i></i> 미사용
														</label>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >파일 전송 로그 기록</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_file_log" value="1" <% if(fileLog) {%> checked <% }%> /><i></i> 사용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_file_log" value="0" <% if(!fileLog) {%> checked <% }%> /><i></i> 미사용
														</label>
													</td>
												</tr>
												<tr class="hidden">
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >파일 전송 차단</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_file_block" value="1" <% if(fileBlock) {%> checked <% }%> /><i></i> 사용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_msg_file_block" value="0" <% if(!fileBlock) {%> checked <% }%> /><i></i> 미사용
														</label>
													</td>
												</tr>
											</tbody>
										</table>
										
										<input type="hidden" id="key" name="key" value="<%= keyNo %>" />

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
					<% if (modifyCheck) { %>
						<button type="button" id="btnPolicyMsgModify" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<% } else { %>
						<button type="button" id="btnPolicyMsgSave" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 등록</button>
					<% } %>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">

function get_policy_msg_setting_data(){
	
	var map = new Object();
	
	map['msg_no'] = $('#key').val();
	map['msg_name'] = $('#att_msg_name').val();
	map['msg_pro_name'] = $('#att_msg_pro_name').val();
	map['txt_log'] = $(':radio[name="radio_msg_txt_log"]:checked').val();
	map['txt_block'] = $(':radio[name="radio_msg_txt_block"]:checked').val();
	map['file_log'] = $(':radio[name="radio_msg_file_log"]:checked').val();
	map['file_block'] = $(':radio[name="radio_msg_file_block"]:checked').val();
	
	return map;
}

function isValied(data) {
	vex.defaultOptions.className = 'vex-theme-os';
	
	if(data.msg_name.trim() == '' || data.msg_name.trim().length < 1) {
		vex.dialog.open({
			message: '메신저명은 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	if(data.msg_pro_name.trim() == '' || data.msg_pro_name.trim().length < 1) {
		vex.dialog.open({
			message: '메신저 프로세스명은 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	return true;
}

function fn_policy_msg_save () {
	
	var data = get_policy_msg_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	vex.defaultOptions.className = 'vex-theme-os';
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/msg/save',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegMessenger').modal('hide');
	    		
	    		var datatable = $('#table-messenger-policy').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '정책 등록이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		})
	    		
	    	} else {
	    		
    			vex.dialog.open({
    				message: '정책 등록중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '확인'
    				  })],
    				  callback: function(data) {
   				 	  	if (data) {
   				 	  		$('#modalPolicyRegMessenger').modal('hide');
   				 	    }
   				 	  }
    			});
	    	}
	    },   
	    error:function(e){
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

function fn_policy_msg_modify () {
	
	var data = get_policy_msg_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	vex.defaultOptions.className = 'vex-theme-os';
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/msg/modify',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegMessenger').modal('hide');
	    		
	    		var datatable = $('#table-messenger-policy').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '정책 수정이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		})
	    		
	    	} else {
	    		
    			vex.dialog.open({
    				message: '정책 수정중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '확인'
    				  })],
    				  callback: function(data) {
   				 	  	if (data) {
   				 	  		$('#modalPolicyRegMessenger').modal('hide');
   				 	    }
   				 	  }
    			});
	    	}
	    },   
	    error:function(e){
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
	
	$('#btnPolicyMsgSave').click(function(){
		fn_policy_msg_save();				
	});
	
	$('#btnPolicyMsgModify').click(function(){
		fn_policy_msg_modify();				
	});
});
	
</script>







