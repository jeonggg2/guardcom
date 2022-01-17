<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="gcom.Model.PolicyProcessModel"%>
<%
	
	int keyNo = Integer.parseInt(request.getAttribute("keyNo").toString());
	boolean modifyCheck = keyNo != 0? true : false ;
	String popTitle = "정책정보등록";
	
	String processName = "";
	String processPath = "";
	String hash = "";
	String notice = "";
	int valid = 1;
	
	if(modifyCheck) {
		popTitle = "정책정보수정";
		PolicyProcessModel data = (PolicyProcessModel)request.getAttribute("data");
		processName = data.getProcessName();
		processPath = data.getProcessPath();
		hash = data.getHash();
		notice = data.getNotice();
		valid = data.getValid();
	}
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
<div id="modalPolicyRegProcess" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
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
													<td class="th-cell-gray center-cell" width="200px" style="vertical-align: middle;">프로세스명</td>
													<td>
														<input type="text" id="att_process_name" name="att_process_name" class="form-control" value="<%= processName %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >프로세스 경로</td>
													<td>
														<input type="text" id="att_process_path" name="att_process_path" class="form-control" value="<%= processPath %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >해시데이터</td>
													<td>
														<input type="text" id="att_process_hash" name="att_process_hash" class="form-control" value="<%= hash %>" readonly />
														<input type="file" id="fileupload" name="hash"  />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >설명</td>
													<td>
														<input type="text" id="att_process_notice" name="att_process_notice" class="form-control" value="<%= notice %>" />
													</td>
												</tr>
												<tr class="hidden">
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >사용여부</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_use_type" value="1" <% if (valid == 1){ %> checked <%}%> /><i></i>사용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_use_type" value="0" <% if (valid == 0){ %> checked <%}%>/><i></i>사용안함
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
						<button type="button" id="btnPolicyProcessModify" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<% } else { %>
						<button type="button" id="btnPolicyProcessSave" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 등록</button>
					<% } %>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">

var upload = $('#fileupload')[0];

upload.onchange = function(e){
	console.log('file upload');
	var file = upload.files[0];
	var formData = new FormData();
	formData.append('hash', file);
	
	$.ajax({
		  url: '${context}/ax/admin/filehash',
		  type: 'POST',
		  processData: false, // important
		  contentType: false, // important
		  dataType : 'json',
		  data: formData,
		  success:function(data){
			  $('#att_process_hash').val(data.hash);
		  },   
		    error:function(e){
		    	vex.defaultOptions.className = 'vex-theme-os';
				vex.dialog.open({
					message: '파일 해시값 변환에 실패하였습니다.',
					  buttons: [
					    $.extend({}, vex.dialog.buttons.YES, {
					      text: '확인'
					  })]
				});
		        console.log(e.responseText);  
		    }  
		})
		
	    
	    
	    
}

function get_policy_process_setting_data(){
	
	var map = new Object();
	
	map['pro_no'] = $('#key').val();
	map['pro_name'] = $('#att_process_name').val();
	map['pro_path'] = $('#att_process_path').val();
	map['hash'] = $('#att_process_hash').val();
	map['notice'] = $('#att_process_notice').val();
	map['use_type'] = $(':radio[name="radio_use_type"]:checked').val();
	
	return map;
}

function isValied(data) {
	vex.defaultOptions.className = 'vex-theme-os';
	
	if(data.pro_name.trim() == '' || data.pro_name.trim().length < 1) {
		vex.dialog.open({
			message: '프로세스명은 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	if(data.pro_path.trim() == '' || data.pro_path.trim().length < 1) {
		vex.dialog.open({
			message: '프로세스 경로는 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
		
	return true;
}

function fn_policy_process_save () {
	
	var data = get_policy_process_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/process/save',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	vex.defaultOptions.className = 'vex-theme-os';
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegProcess').modal('hide');
	    		
	    		var datatable = $('#table-process-policy').dataTable().api();
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
   				 	  		$('#modalPolicyRegProcess').modal('hide');
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


function fn_policy_process_modify () {
	
	var data = get_policy_process_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/process/modify',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	vex.defaultOptions.className = 'vex-theme-os';
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegProcess').modal('hide');
	    		
	    		var datatable = $('#table-process-policy').dataTable().api();
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
   				 	  		$('#modalPolicyRegProcess').modal('hide');
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
	
	$('#btnPolicyProcessSave').click(function(){
		fn_policy_process_save();				
	});
	
	$('#btnPolicyProcessModify').click(function(){
		fn_policy_process_modify();				
	});
});
	
</script>







