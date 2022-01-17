<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gcom.itasset.model.ITAssetSwModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	
	int no = Integer.parseInt(request.getAttribute("no").toString());
	boolean isEditMode = no != -1;
	String popTitle = "소프트웨어 등록";
	
	String name ="";
	String desc = "";
	Date introDate = null;
	Date commitDate = null;
	int type = -1;
	int hasOwn = -1;
	int hasManaged = -1;
	
	if(isEditMode) {
		popTitle = "소프트웨어 정보 수정";
		ITAssetSwModel data = (ITAssetSwModel)request.getAttribute("data");
		name = data.getName(); if (name == null) name = "";
		desc = data.getDesc(); if (desc == null) desc = "";
		type = data.getType();
		introDate = data.getIntroDate();
		commitDate = data.getCommitDate();
		hasOwn = data.getHasOwn();
		hasManaged = data.getHasManaged();
	}
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

<div id="modalPolicyRegWebsite" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
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
											<strong>소프트웨어 정보 설정</strong> <!-- panel title -->
										</span>
									</div>
		
									<!-- panel content -->
									<div class="panel-body">
										<table class="table table-bordered">
											<tbody>
												<tr>
													<td class="th-cell-gray center-cell" width="200px" style="vertical-align: middle;">소프트웨어 이름</td>
													<td>
														<input type="text" id="att_sw_name" name="att_sw_name" class="form-control" value="<%= name %>" data-default-value="<%= name %>"/>
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >설명</td>
													<td>
														<input type="text" id="att_sw_descript" name="att_sw_descript" class="form-control" value="<%= desc %>" />
													</td>
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >상용 여부</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_type" value="0" <% if (type == 0){ %> checked <%}%> /><i></i>상용
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_type" value="1" <% if (type == 1){ %> checked <%}%>/><i></i>무료
														</label>
													</td>										
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >보유 여부</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" id="radio_own" name="radio_has_own" value="1" <% if (hasOwn == 1){ %> checked <%}%> /><i></i>보유 중
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" id="radio_not_own" name="radio_has_own" value="0" <% if (hasOwn == 0){ %> checked <%}%>/><i></i>보유하지 않음
														</label>
													</td>										
												</tr>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >도입 날짜</td>
													<td>
														<input <% if (hasOwn == 0){ %> disabled="disabled" <%}%> type="text" class="form-control datapicker" id="intro_date" value=<%= introDate == null ? "": introDate %>>
													</td>
												</tr>		
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >관리 여부</td>
													<td>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_has_managed" value="1" <% if (hasManaged == 1){ %> checked <%}%> /><i></i>관리
														</label>
														<label class="radio nomargin-top nomargin-bottom">
															<input type="radio" name="radio_has_managed" value="0" <% if (hasManaged == 0){ %> checked <%}%>/><i></i>관리안함
														</label>
													</td>
												</tr>											
												<% if (isEditMode) { %>
												<tr>
													<td class="th-cell-gray center-cell" style="vertical-align: middle;" >검증</td>
													<td>													
														<% if (commitDate == null) { %>
														<input type="checkbox" class="form-control" style="height:20px" name="is_commit">
														<% } else { %>
														<span style="color:green">검증됨 (<%= (new SimpleDateFormat("yyyy. MM. dd.")).format(commitDate) %>)</span>
														<% } %>
													</td>
												</tr>
												<% } %>
											</tbody>
										</table>
										
										<input type="hidden" id="key" name="key" value="<%= no %>" />

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
					<% if (isEditMode) { %>
						<button type="button" id="modify_sw_info_btn" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 수정</button>
					<% } else { %>
						<button type="button" id="register_sw_info_btn" class="btn btn-primary" onclick="return false;" ><i class="fa fa-check"></i> 등록</button>
					<% } %>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">

function get_sw_data(){
	var map = new Object();	
	map['no'] = $('#key').val();
	map['name'] = $('#att_sw_name').val();
	map['desc'] = $('#att_sw_descript').val();
	map['type'] = $(':radio[name="radio_type"]:checked').val();
	map['has_own'] = $(':radio[name="radio_has_own"]:checked').val();
	map['has_managed'] = $(':radio[name="radio_has_managed"]:checked').val();
	map['intro_date'] = $('#intro_date').val();	
	map['is_commit'] = $(':checkbox[name=is_commit]:checked').length != 0;
	return map;
}

function isValied(data) {
	
	vex.defaultOptions.className = 'vex-theme-os';
	
	if(data.name.trim() == '' || data.name.length < 1) {
		vex.dialog.open({
			message: '소프트웨어 이름은 필수 입력 사항입니다.',
			buttons: [
			  $.extend({}, vex.dialog.buttons.YES, {
			    text: '확인'
			})],
		    callback: function (value) {
		    	$("#att_sw_name").focus();     
		    }
		});
		return false;
	}
	
	if ($("#att_sw_name").parent().find(".valid_text").legnth == 0) {
		vex.dialog.open({
			message: '이미 등록된 소프트웨어입니다.',
			buttons: [
			  $.extend({}, vex.dialog.buttons.YES, {
			    text: '확인'
			})],
		    callback: function (value) {
		    	$("#att_sw_name").focus();     
		    }
		});
		return false;
	}
	
	if(data.type == null) {
		vex.dialog.open({
			message: '소프트웨어 종류는 필수 선택 사항입니다.',
			buttons: [
				$.extend({}, vex.dialog.buttons.YES, 
				{
				    text: '확인'
				})
			],
		    callback: function (value) {
		    	$(":radio[name='radio_type']").focus();     
		    }
		})
		return false;
	}
	
	if(data.has_own== null) {
		vex.dialog.open({
			message: '소프트웨어 보유 여부는 필수 선택사항입니다.',
			buttons: [
				$.extend({}, vex.dialog.buttons.YES, 
				{
				    text: '확인'
				})
			],
		    callback: function (value) {
		    	$(":radio[name='radio_has_own']").focus();     
		    }
		})
		return false;
	}
	
	if (data.has_own == 1) {
		if(data.intro_date.trim() == '' || data.intro_date.length < 1) {
			vex.dialog.open({
				message: '소프트웨어 도입날짜는 필수 선택 사항입니다.',
				buttons: [
					$.extend({}, vex.dialog.buttons.YES, 
					{
					    text: '확인'
					})
				],
			    callback: function (value) {
			    	$("#intro_date").focus();     
			    }
			})
			return false;
		}
	}
	
	if(data.has_managed== null) {
		vex.dialog.open({
			message: '소프트웨어 관리 여부는 필수 선택사항입니다.',
			buttons: [
				$.extend({}, vex.dialog.buttons.YES, 
				{
				    text: '확인'
				})
			],
		    callback: function (value) {
		    	$(":radio[name='radio_has_managed']").focus();     
		    }
		})
		return false;
	}
	
	return true;
}

$.ajax({      
    type:"GET",  
    url:'${context}/admin/itasset/sw/list',
    async: false,
    success:function(sw_list){
    	debugger;
    	$("#att_sw_name").keyup(function () {
    		$("#att_sw_name").parent().find(".valid_text").remove();
    		var value = $(this).val().trim().toLowerCase().replace(/ /g, '');
    		var default_value = $("#att_sw_name").attr("data-default-value").trim().toLowerCase().replace(/ /g, '');
    		
    		if (default_value == value) {
    			return;
    		}
    		
    		$.each(sw_list, function (index, element){
    			if (element.trim().toLowerCase().replace(/ /g, '') == value) {
    				$("#att_sw_name").parent().append("<span class='valid_text' style='color:red'>이미 등록된 소프트웨어입니다.</span>");
    				return;
    			}
    		});    			
    	});
    }
});

function fn_register_sw_info () {
	
	var data = get_sw_data();
	
	if(! isValied(data)) {
		return false;
	}
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/itasset/sw/register',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	vex.defaultOptions.className = 'vex-theme-os';
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegWebsite').modal('hide');
	    		
	    		var datatable = $('#table_sw_list').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '정보 등록이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		});
	    		
	    	} else {
	    		
    			vex.dialog.open({
    				message: '정보 등록중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '확인'
    				  })],
    				  callback: function(data) {
   				 	  	if (data) {
   				 	  		$('#modalPolicyRegWebsite').modal('hide');
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


function fn_modify_sw_info () {
	
	var data = get_sw_data();
	
	if(! isValied(data)) {
		return false;
	}
	
	$.ajax({      
	    type:"POST",  
	    url:'${context}/admin/itasset/sw/modify',
	    async: false,
	    data:{
	    	data : JSON.stringify(data),
	    	_ : $.now()
	    },
	    success:function(data){
	    	vex.defaultOptions.className = 'vex-theme-os';
	    	
	    	if (data.returnCode == 'S') {
	    		$('#modalPolicyRegWebsite').modal('hide');
	    		
	    		var datatable = $('#table_sw_list').dataTable().api();
	    		datatable.ajax.reload();
	    		
	    		vex.dialog.open({
	    			message: '정보 수정이 완료되었습니다.',
	    			  buttons: [
	    			    $.extend({}, vex.dialog.buttons.YES, {
	    			      text: '확인'
	    			  })]
	    		})
	    		
	    	} else {
	    		
    			vex.dialog.open({
    				message: '정보 수정중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.',
    				  buttons: [
    				    $.extend({}, vex.dialog.buttons.YES, {
    				      text: '확인'
    				  })],
    				  callback: function(data) {
   				 	  	if (data) {
   				 	  		$('#modalPolicyRegWebsite').modal('hide');
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

	$('#intro_date').datepicker({
	 	format:"yyyy-mm-dd",
	 	RTL:false,
	 	autoclose:true
	});
	
	$('#radio_own').click(function () {
		$('#intro_date').removeAttr("disabled");
		$('#intro_date').focus();
	});
	
	$('#radio_not_own').click(function () {
		$('#intro_date').val("");
		$('#intro_date').attr("disabled", "disabled");
	});
	
	$('#register_sw_info_btn').click(function(){
		fn_register_sw_info();				
	});
	
	$('#modify_sw_info_btn').click(function(){
		fn_modify_sw_info();				
	});
});
	
</script>







