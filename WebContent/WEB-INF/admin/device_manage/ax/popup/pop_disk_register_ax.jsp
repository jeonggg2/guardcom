<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="gcom.Model.UsbDevInfoModel"%>
<%
	
	int keyNo = Integer.parseInt(request.getAttribute("keyNo").toString());
	boolean modifyCheck = keyNo != 0? true : false ;
	String popTitle = "디스크정보수정";
	
	String name = "";
	String vid  = "";
	String pid  = "";
	String serialNumber  = "";
	String descriptor  = "";
	String mainclass  = "";
	String subclass  = "";
	String protocol  = "";

	boolean allow = true;	
	
	if(modifyCheck) {
		popTitle = "정책정보수정";
		UsbDevInfoModel data = (UsbDevInfoModel)request.getAttribute("data");
		name = data.getName();
		vid = data.getVid();
		pid = data.getPid();
		serialNumber = data.getSerialNumber();
		descriptor = data.getDescription();
		mainclass = data.getMainclass();
		subclass = data.getSubclass();
		protocol = data.getProtocol();
		allow = data.getAllow();
	}
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
<div id="modalPolicyRegUsb" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
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
													<td class="th-cell-gray center-cell" width="200px" style="vertical-align: middle;">장치이름</td>
													<td>
														<input type="text" id="att_usb_name" name="att_usb_name" class="form-control" value="<%= name %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >설명</td>
													<td>
														<input type="text" id="att_usb_descript" name="att_usb_descript" class="form-control" value="<%= descriptor %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;">VID</td>
													<td>
														<input type="text" id="att_usb_vid" name="att_usb_vid" class="form-control" value="<%= vid %>"  maxlength="4" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >PID</td>
													<td>
														<input type="text" id="att_usb_pid" name="att_usb_pid" class="form-control" value="<%= pid %>" maxlength="4"/>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >시리얼번호</td>
													<td>
														<input type="text" id="att_usb_serial" name="att_usb_serial" class="form-control" value="<%= serialNumber %>" maxlength="64"/>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >class</td>
													<td>
														<input type="text" id="att_usb_class" name="att_usb_class" maxlength="2" class="form-control" value="<%= mainclass %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >subclass</td>
													<td>
														<input type="text" id="att_usb_subclass" name="att_usb_subclass" maxlength="2" class="form-control" value="<%= subclass %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >protocol</td>
													<td>
														<input type="text" id="att_usb_protocol" name="att_usb_protocol" maxlength="2" class="form-control" value="<%= protocol %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >USB비교</td>
													<td>
														<div class="col-md-6">
															<label><input type="checkbox" id="compVid" ${data.compareVid == true ? 'checked' : ''} >VID</label>														
														</div>
														<div class="col-md-6">
															<label><input type="checkbox" id="compPid" ${data.comparePid == true ? 'checked' : ''}>PID</label>														
														</div>
														<div class="col-md-6">
															<label><input type="checkbox" id="compSerial" ${data.compareSerial == true ? 'checked' : ''}>시리얼번호</label>														
														</div>
														<div class="col-md-6">
															<label><input type="checkbox" id="compClass" ${data.compareMainclass == true ? 'checked' : ''}>class</label>														
														</div>
														<div class="col-md-6">
															<label><input type="checkbox" id="compSubclass" ${data.compareSubclass == true ? 'checked' : ''}>subclass</label>														
														</div>
														<div class="col-md-6">
															<label><input type="checkbox" id="compProtocol" ${data.compareProtocol == true ? 'checked' : ''}>protocol</label>														
														</div>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >사용여부</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_use_type" value="1" <% if (allow == true){ %> checked <%}%> /><i></i>사용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_use_type" value="0" <% if (allow == false){ %> checked <%}%>/><i></i>사용안함
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
						<button type="button" id="btnPolicyUsbModify" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<% } else { %>
						<button type="button" id="btnPolicyUsbSave" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 등록</button>
					<% } %>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">

console.log('${data}')

function get_policy_usb_setting_data(){
	
	var map = new Object();
	
	map['usb_no'] = $('#key').val();
	map['usb_name'] = $('#att_usb_name').val();
	map['vid'] = $('#att_usb_vid').val();
	map['pid'] = $('#att_usb_pid').val();

	map['serial'] = $('#att_usb_serial').val();
	map['mainclass'] = $('#att_usb_class').val();
	map['subclass'] = $('#att_usb_subclass').val();
	map['protocol'] = $('#att_usb_protocol').val();

	map['descript'] = $('#att_usb_descript').val();
	map['compare'] = getCompareCheckboxData();
	map['use_type'] = $(':radio[name="radio_use_type"]:checked').val();

	return map;
}

function fn_policy_usb_save () {
	vex.defaultOptions.className = 'vex-theme-os';
	
	var data = get_policy_usb_setting_data();
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/usb/save',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		vex.dialog.open({
	    			message: '정책 등록이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })],
	    			  callback: function(data) {
  				 	  	if (data) {
  				 	  		$('#modalPolicyRegMessenger').modal('hide');
  				 	  		location.href = '${context}/admin/device';
  				 	    }
  				 	  }
	    				
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

function getCompareCheckboxData(){
	var result = ""

	$('#compVid').is(":checked") == true ? result += '1' : result += '0';
	$('#compPid').is(":checked")== true ? result += '1' : result += '0';
	$('#compSerial').is(":checked")== true ? result += '1' : result += '0';
	$('#compClass').is(":checked")== true ? result += '1' : result += '0';
	$('#compSubclass').is(":checked")== true ? result += '1' : result += '0';
	$('#compProtocol').is(":checked")== true ? result += '1' : result += '0';
	
	return result;		
}


function fn_policy_usb_modify () {
	vex.defaultOptions.className = 'vex-theme-os';
	
	var data = get_policy_usb_setting_data();
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/usb/modify',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		vex.dialog.open({
	    			message: '정책 수정이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })],
	    			  callback: function(data) {
  				 	  	if (data) {
  				 	  		$('#modalPolicyRegUsb').modal('hide');
  				 	  		location.href = '${context}/admin/device';
  				 	    }
  				 	  }
	    				
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
   				 	  		$('#modalPolicyRegUsb').modal('hide');
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
	
	$('#btnPolicyUsbSave').click(function(){
		fn_policy_usb_save();				
	});
	
	$('#btnPolicyUsbModify').click(function(){
		fn_policy_usb_modify();				
	});
});
	
</script>







