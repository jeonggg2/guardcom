<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="gcom.Model.PolicySerialModel"%>
<%
	
	int keyNo = Integer.parseInt(request.getAttribute("keyNo").toString());
	boolean modifyCheck = keyNo != 0? true : false ;
	String popTitle = "정책정보등록";
	
	String serialName ="";
	String description = "";
	int allow = 1;
	
	if(modifyCheck) {
		popTitle = "정책정보수정";
		PolicySerialModel data = (PolicySerialModel)request.getAttribute("data");
		serialName = data.getSerialName();
		description = data.getDescription();
		allow = data.getAllow();
	}
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
<div id="modalPolicyRegSerial" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
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
													<td class="th-cell-gray center-cell" width="200px" style="vertical-align: middle;">시리얼 포트 이름</td>
													<td>
														<input type="text" id="att_serial_name" name="att_serial_name" class="form-control" value="<%= serialName %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >설명</td>
													<td>
														<input type="text" id="att_serial_descript" name="att_serial_descript" class="form-control" value="<%= description %>" />
													</td>
												</tr>
												<tr class="hidden">
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >사용여부</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_use_type" value="1" <% if (allow == 1){ %> checked <%}%> /><i></i>사용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_use_type" value="0" <% if (allow == 0){ %> checked <%}%>/><i></i>사용안함
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
						<button type="button" id="btnPolicySerialModify" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<% } else { %>
						<button type="button" id="btnPolicySerialSave" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 등록</button>
					<% } %>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">

function get_policy_serial_setting_data(){
	
	var map = new Object();
	
	map['serial_no'] = $('#key').val();
	map['serial_name'] = $('#att_serial_name').val();
	map['descript'] = $('#att_serial_descript').val();
	map['use_type'] = $(':radio[name="radio_use_type"]:checked').val();
	
	return map;
}

function isValied(data) {
	vex.defaultOptions.className = 'vex-theme-os';
	
	if(data.serial_name.trim() == '' || data.serial_name.trim().length < 1) {
		vex.dialog.open({
			message: '시리얼포트 이름은 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	return true;
}

function fn_policy_serial_save () {
	
	var data = get_policy_serial_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/serial/save',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	vex.defaultOptions.className = 'vex-theme-os';
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegSerial').modal('hide');
	    		
	    		var datatable = $('#table-serial-policy').dataTable().api();
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
   				 	  		$('#modalPolicyRegSerial').modal('hide');
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


function fn_policy_serial_modify () {
	
	var data = get_policy_serial_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/serial/modify',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	vex.defaultOptions.className = 'vex-theme-os';
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegSerial').modal('hide');
	    		
	    		var datatable = $('#table-serial-policy').dataTable().api();
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
   				 	  		$('#modalPolicyRegSerial').modal('hide');
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
	
	$('#btnPolicySerialSave').click(function(){
		fn_policy_serial_save();				
	});
	
	$('#btnPolicySerialModify').click(function(){
		fn_policy_serial_modify();				
	});
});
	
</script>







