<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="gcom.Model.PolicyPatternModel"%>
<%
	
	int keyNo = Integer.parseInt(request.getAttribute("keyNo").toString());
	boolean modifyCheck = keyNo != 0? true : false ;
	String popTitle = "정책정보등록";
	
	String PatName ="";
	String patData = "";
	String notice = "";
	int valid = 1;
	
	if(modifyCheck) {
		popTitle = "정책정보수정";
		PolicyPatternModel data = (PolicyPatternModel)request.getAttribute("data");
		PatName = data.getPatName();
		patData = data.getData();
		notice = data.getNotice();
		valid = data.getValid();
	}
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
<div id="modalPolicyRegPattern" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
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
													<td class="th-cell-gray center-cell" width="75px" style="vertical-align: middle;">패턴이름</td>
													<td colspan="2">
														<input type="text" id="att_pattern_name" name="att_pattern_name" class="form-control" value="<%= PatName %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >패턴 데이터</td>
													<td style="border-right:0px;">
														<input type="text" id="att_pattern_data" name="att_pattern_data" class="form-control" value="<%= patData %>" />
													</td>
													<td width="80px" style="border-left:0px;">
														<button type="button" id="btnSettingPattern" class="btn btn-primary" onclick="return false;" style="margin:0;"><i class="fa fa-cog"></i> 설정</button>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >설명</td>
													<td colspan="2">
														<input type="text" id="att_pattern_notice" name="att_pattern_notice" class="form-control" value="<%= notice %>" />
													</td>
												</tr>
												<tr class="hidden">
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >사용여부</td>
													<td colspan="2">
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
						<button type="button" id="btnPolicyPatternModify" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<% } else { %>
						<button type="button" id="btnPolicyPatternSave" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 등록</button>
					<% } %>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="modalSettingPattern" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" style="padding-top: 5%;">
	<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" aria-label="Close" onClick="document.getElementById('btnSettingClose').click();" ><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myModalLabel">패턴 데이터 설정</h4>
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
											<strong>민감정보 패턴 생성</strong> <!-- panel title -->
										</span>
									</div>
		
									<!-- panel content -->
									<div class="panel-body">
										<table class="table table-bordered">
											<tbody>
												<tr>
													<td class="th-cell-gray center-cell" width="110px" style="vertical-align: middle;">패턴</td>
													<td style="border-right:0px;">
														<input type="text" id="att_create_pattern_data" name="att_create_pattern_data" class="form-control" value="" />
													</td>
													<td width="110px" style="border-left:0px;">
														<button type="button" id="btnReMakePattern" class="btn btn-dirtygreen pull-right" onclick="return false;" style="margin:0;"><i class="fa fa-trash"></i>다시작성</button>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >패턴 타입</td>
													<td class="center-cell" colspan="2">
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_pattern_type" class="radio_pattern_item" value="1" /><i></i>숫자
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_pattern_type" class="radio_pattern_item" value="2" /><i></i>문자
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_pattern_type" class="radio_pattern_item" value="3" /><i></i>사용자 정의 문자
														</label>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >사용자 정의<br /> 패턴 입력</td>
													<td colspan="2">
														<input type="text" id="att_custom_pattern" name="att_custom_pattern" class="form-control" value="" disabled />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >패턴길이</td>
													<td style="border-right:0px;">
														<input type="number" id="att_pattern_len" name="att_pattern_len" class="form-control" value="" min="0" />
													</td>
													<td style="border-left:0px;">
														<button type="button" id="btnCheckPattern" class="btn btn-green pull-right" onclick="return false;" style="margin:0;"><i class="fa fa-eye"></i>패턴추가</button>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- /panel content -->
								</div>
							</div>
						</div>
					</div>
				<!-- /Modal body -->

				<!-- Modal Footer -->
				<div class="modal-footer">
					<button type="button" id="btnPatternComplete" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 완료</button>
					<button type="button" id="btnSettingClose" class="btn btn-default" onclick="return false;" ><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

function get_policy_pattern_setting_data(){
	
	var map = new Object();
	
	map['pat_no'] = $('#key').val();
	map['pat_name'] = $('#att_pattern_name').val();
	map['pat_data'] = $('#att_pattern_data').val();
	map['notice'] = $('#att_pattern_notice').val();
	map['use_type'] = $(':radio[name="radio_use_type"]:checked').val();
	
	return map;
}

function isValied(data) {
	vex.defaultOptions.className = 'vex-theme-os';
	
	if(data.pat_name.trim() == '' || data.pat_name.trim().length < 1) {
		vex.dialog.open({
			message: '패턴이름은 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	if(data.pat_data.trim() == '' || data.pat_data.trim().length < 1) {
		vex.dialog.open({
			message: '패턴 데이터는 필수 입력 사항입니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
		return false;
	}
	
	return true;
}

function fn_policy_pattern_save () {
	
	var data = get_policy_pattern_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	vex.defaultOptions.className = 'vex-theme-os';
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/pattern/save',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegPattern').modal('hide');
	    		
	    		var datatable = $('#table-pattern-policy').dataTable().api();
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
   				 	  		$('#modalPolicyRegPattern').modal('hide');
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


function fn_policy_pattern_modify () {
	
	var data = get_policy_pattern_setting_data();
	
	if(!isValied(data)) {
		return false;
	}
	
	vex.defaultOptions.className = 'vex-theme-os';
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/policy/pattern/modify',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegPattern').modal('hide');
	    		
	    		var datatable = $('#table-pattern-policy').dataTable().api();
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
   				 	  		$('#modalPolicyRegPattern').modal('hide');
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

function fn_create_pattern() {
	
	if($(':radio[name="radio_pattern_type"]').is(':checked') == false) {
		vex.defaultOptions.className = 'vex-theme-os';
		vex.dialog.open({
			message: '패턴 타입을 선택해주세요.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		});
		return false;
	}
	
	var patternType = $(':radio[name="radio_pattern_type"]:checked').val();
	
	if (patternType == 1) {
		var prxData = $('#att_create_pattern_data').val();
		var lenData = $('#att_pattern_len').val();
		var addData = '%';
		
		if (lenData == '') {
			addData += 'd';
		} else {
			addData += lenData + 'd';
		}
		
		$('#att_create_pattern_data').val(prxData + addData);
	} else if (patternType == 2) {
		var prxData = $('#att_create_pattern_data').val();
		var lenData = $('#att_pattern_len').val();
		var addData = '%';
		
		if (lenData == '') {
			addData += 's';
		} else {
			addData += lenData + 'd';
		}
		
		$('#att_create_pattern_data').val(prxData + addData);
		
	} else if (patternType == 3) {
		var prxData = $('#att_create_pattern_data').val();
		var addData = $('#att_custom_pattern').val();
		
		$('#att_create_pattern_data').val(prxData + addData);
	}
}

function initSettingDataForm(){
	$('#modalSettingPattern input:text').val('');
	$('#att_pattern_len').val('');
	$('#modalSettingPattern input:radio').prop('checked','');
}

$(document).ready(function(){
	jQuery('#preloader').hide();
	
	$('#btnPolicyPatternSave').click(function(){
		fn_policy_pattern_save();				
	});
	
	$('#btnPolicyPatternModify').click(function(){
		fn_policy_pattern_modify();				
	});
	
	$('#btnSettingPattern').click(function(){
		initSettingDataForm();
		
		var preexisPattern  = $('#att_pattern_data').val();
		$('#att_create_pattern_data').val(preexisPattern);
				
		$('#modalPolicyRegPattern').modal('hide');
		$('#modalSettingPattern').modal('show');
	});
	
	$('#btnSettingClose').click(function(){
		$('#modalSettingPattern').modal('hide');
		$('#modalPolicyRegPattern').modal('show');
	});
	
	$('#btnReMakePattern').click(function(){
		$('#att_create_pattern_data').val('');
	});
	
	$('#btnCheckPattern').click(function(){
		fn_create_pattern();
	});
	
	$('#btnPatternComplete').click(function(){
		var patternData = $('#att_create_pattern_data').val();
		
		if (patternData == '' || patternData.length < 1) {
			vex.defaultOptions.className = 'vex-theme-os';
			vex.dialog.open({
				message: '패턴이 비어있어 완료 할 수 없습니다. 패턴은 필수 입력 사항입니다.',
				  buttons: [
				    $.extend({}, vex.dialog.buttons.YES, {
				      text: '확인'
				  })]
			});
			return false;
		}
		
		$('#att_pattern_data').val(patternData);
		$('#modalSettingPattern').modal('hide');
		$('#modalPolicyRegPattern').modal('show');
	});
	
	$('.radio_pattern_item').change(function() {
		var patternType = $(':radio[name="radio_pattern_type"]:checked').val();
		
		if(patternType == 3) {
			$('#att_pattern_len').prop('disabled', true);
			$('#att_custom_pattern').prop('disabled', false);
		} else {
			$('#att_pattern_len').prop('disabled', false);
			$('#att_custom_pattern').prop('disabled', true);
		}
	});
});
	
</script>







