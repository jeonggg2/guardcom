<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="gcom.Model.PolicyNetworkModel"%>
<%
	
	int keyNo = Integer.parseInt(request.getAttribute("keyNo").toString());
	boolean modifyCheck = keyNo != 0? true : false ;
	String popTitle = "정책정보등록";
	
	String netName ="";
	String port = "";
	String descriptor = "";
	int allow = 1;
	
	if(modifyCheck) {
		popTitle = "정책정보수정";
		PolicyNetworkModel data = (PolicyNetworkModel)request.getAttribute("data");
		netName = data.getNetName();
		port = data.getPort();
		descriptor = data.getDescriptor();
		allow = data.getAllow();
	}
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
<div id="modalPolicyRegNetwork" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
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
													<td class="th-cell-gray center-cell" width="200px" style="vertical-align: middle;">네트워크 포트 이름</td>
													<td>
														<input type="text" id="att_net_work_name" name="att_net_work_name" class="form-control" value="<%= netName %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >포트</td>
													<td>
														<input type="text" id="att_net_work_port" name="att_net_work_port" class="form-control width-100 pull-left" value="<%= port %>" />
														<% if (!modifyCheck) { %>
															<span class="pull-left" style="font-weight: bold; padding-top: 10px;">&nbsp;~&nbsp;</span>
															<input type="text" id="att_relate_port" name="att_relate_port" class="form-control width-100 pull-left" value="" disabled placeholder="연속포트"/>
															<input type="checkbox" id="att_relate_port_yn" name="att_relate_port_yn" class="form-control width-20 pull-left" style="margin:0 0 0 4px;"/>
															<span class="pull-left">연속<br/>생성</span>
														<% } %>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >설명</td>
													<td>
														<input type="text" id="att_net_work_descript" name="att_net_work_descript" class="form-control" value="<%= descriptor %>" />
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
						<button type="button" id="btnPolicyNetworkModify" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<% } else { %>
						<button type="button" id="btnPolicyNetworkSave" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 등록</button>
					<% } %>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">

function get_policy_network_setting_data(){
	
	var map = new Object();
	
	map['net_no'] = $('#key').val();
	map['net_name'] = $('#att_net_work_name').val();
	map['net_port'] = $('#att_net_work_port').val();
	map['descript'] = $('#att_net_work_descript').val();
	map['use_type'] = $(':radio[name="radio_use_type"]:checked').val();
	map['net_relate_port'] = '';
	
	if($('#att_relate_port_yn').is(':checked') == true) {
		map['net_relate_port'] = $('#att_relate_port').val();
	}
	
	return map;
}

function isValied(data) {
	vex.defaultOptions.className = 'vex-theme-os';
	
	if(data.net_name.trim() == '' || data.net_name.trim().length < 1) {
		vex.dialog.open({
			message: '포트이름은 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	if(data.net_port.trim() == '' || data.net_port.trim().length < 1) {
		vex.dialog.open({
			message: '포트는 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	if($.isNumeric(data.net_port) == false || data.net_port < 0) {
		vex.dialog.open({
			message: '포트는 숫자만 입력 가능하며 0이상 입력 하셔야합니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	if($('#att_relate_port_yn').is(':checked') == true && (data.net_relate_port == '' || data.net_relate_port.length < 1)) {
		vex.dialog.open({
			message: '연속 포트 생성 시 연속포트 필드는 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	if($('#att_relate_port_yn').is(':checked') == true && ($.isNumeric(data.net_relate_port) == false || data.net_relate_port < 0)) {
		vex.dialog.open({
			message: '연속 포트는 숫자만 입력 가능하며 0이상 입력하셔야 합니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	return true;
}

function fn_policy_network_save () {
	
	var data = get_policy_network_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	vex.defaultOptions.className = 'vex-theme-os';
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/network/save',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegNetwork').modal('hide');
	    		
	    		var datatable = $('#table-network-policy').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '정책 등록이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		})
	    		
	    	} else if(data.returnCode == 'SO') {
				$('#modalPolicyRegNetwork').modal('hide');
	    		
	    		var datatable = $('#table-network-policy').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '중복된 포트가 존재합니다. 중복된 포트를 제외하고 정책 등록이 완료되었습니다.',
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
   				 	  		$('#modalPolicyRegNetwork').modal('hide');
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


function fn_policy_network_modify () {
	
	var data = get_policy_network_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	vex.defaultOptions.className = 'vex-theme-os';
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/network/modify',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegNetwork').modal('hide');
	    		
	    		var datatable = $('#table-network-policy').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '정책 수정이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		})
	    		
	    	} else if(data.returnCode == 'SO') {
				$('#modalPolicyRegNetwork').modal('hide');
	    		
	    		vex.dialog.open({
	    			message: '정책 수정에 실패하였습니다. 중복된 포트가 존재합니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		})
	    	} else {
	    		$('#modalPolicyRegNetwork').modal('hide');
    			vex.dialog.open({
    				message: '정책 수정중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '확인'
    				  })]
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
	
	$('#btnPolicyNetworkSave').click(function(){
		fn_policy_network_save();				
	});
	
	$('#btnPolicyNetworkModify').click(function(){
		fn_policy_network_modify();				
	});
	
	$('#att_relate_port_yn').change(function(){
		
		if($('#att_relate_port_yn').is(':checked') == true) {
			$('#att_relate_port').prop('disabled', false);
		} else {
			$('#att_relate_port').prop('disabled', true);
			$('#att_relate_port').val('');
		}
	});
});
	
</script>







