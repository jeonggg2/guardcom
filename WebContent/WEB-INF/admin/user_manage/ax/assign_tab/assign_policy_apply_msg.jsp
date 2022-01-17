<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	boolean onlyFlag = Boolean.parseBoolean(request.getParameter("onlyFlag"));
	boolean isMsgBlock = Boolean.parseBoolean(request.getParameter("isMsgBlock"));
	String msgBlockCode = request.getParameter("msgBlockCode").toString();
	String applyCode = msgBlockCode.length() > 0 ? "," + msgBlockCode : "";
	String scriptCode = onlyFlag? msgBlockCode : "";
	
	if (!isMsgBlock){ 
		applyCode = "Y" + applyCode;
	} else {
		applyCode = "N" + applyCode;
	}
%>

<div>
	<table class="table table-bordered">
		<tr>
			<td class="th-cell-gray center-cell" width="130px" style="vertical-align: middle;">메신저 차단 선택</td>
			<td class="center-cell" style="vertical-align: middle;">
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_msg_block" value="Y" <% if (!isMsgBlock){ %> checked <%}%> /><i></i> 허용
				</label>
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_msg_block" value="N" <% if (isMsgBlock){ %> checked <%}%>/><i></i> 차단
				</label>
			</td>
			<td class="th-cell-gray center-cell" width="75px" style="vertical-align: middle;">적용상태</td>
			<td class="center-cell" style="vertical-align: middle;">
				<input type="text" id="att_msg_block_type" name="att_msg_block_type" class="form-control" value="<%= applyCode%>" disabled />
			</td>
		</tr>
	</table>
</div>

<div>
	<select class="form-control pull-left" id="msg_sel_search_type" name="msg_sel_search_type" style="width:200px;">
		<option value="1">메신저명</option>
		<option value="2">파일명</option>
	</select>
	<input type="text" class="form-control pull-left" id="msg_att_search_text" name="msg_att_search_text" placeholder="검색어를 입력해주세요." style="width:200px;" value="" />
	<button onclick="searchMsgList();" class="btn btn-info pull-left"><i class="fa fa-search"></i> 검색</button>
</div>
<table class="table table-bordered table-td-middle" id="msg_block_table" style="width:100%; margin-top: 20px;">
	<col width="80px">
	<col width="140px">
	<col>
	<col width="80px">
	<col width="80px">
	<col width="80px">
	<col width="80px">
	<thead>
		<tr>
			<td class="center-cell vertical-middle">선택</td>
			<td>메신저ID</td>
			<td class="center-cell vertical-middle">메신저명</td>
			<td class="center-cell vertical-middle">파일명</td>
			<td class="center-cell vertical-middle">Message로깅</td>
			<td class="center-cell vertical-middle">Message차단</td>
			<td class="center-cell vertical-middle">File전송로깅</td>
			<td class="center-cell vertical-middle">File전송차단</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
													

<script type="text/javascript">

	function searchMsgList(){
		var datatable = $('#msg_block_table').dataTable().api();
		datatable.ajax.reload();
	}

	$(document).ready(function(){
		$("input[name=radio_msg_block]").change(function() {
			var chk_value = $(':radio[name="radio_msg_block"]:checked').val();
			var type = $('#att_msg_block_type').val();		
			if (chk_value == 'Y') {
				if (type == '') {
					$('#att_msg_block_type').val(chk_value);
				} else {
					$('#att_msg_block_type').val(type.replace('N','Y'));	
				}
			} else {
				if (type == '') {
					$('#att_msg_block_type').val(chk_value);
				} else {
					$('#att_msg_block_type').val(type.replace('Y','N'));
				}
			}
		});
	});

	function setMsgBlockSelectInfo(check_box) {
		
		var msgCode = $(check_box).val();
		var type = $('#att_msg_block_type').val();
		
		if (type == '') {
			vex.defaultOptions.className = 'vex-theme-os';
			vex.dialog.open({
				message: '차단여부를 먼저 선택해주세요.',
				  buttons: [
				    $.extend({}, vex.dialog.buttons.YES, {
				      text: '확인'
				  })]
			});
			$(check_box).prop('checked', false);
			return false;
		}
		
		if ($(check_box).is(':checked')) {
			
			type += "," + msgCode;
			$('#att_msg_block_type').val(type);
			
		} else {

			var temp = type.split(',');
			var result = temp[0];
			
			if (temp.length > 1) {
				for (var i = 1 ; i < temp.length ; i++) {
					if (temp[i] != msgCode) {
						result += "," + temp[i];
					}
				}
				
				$('#att_msg_block_type').val(result);
			}
			
		}
		
	}

	function msg_block_table() {
			 
			if (jQuery().dataTable) {
		
				var mbTable = jQuery('#msg_block_table');
				mbTable.dataTable({
					"dom": '<"row view-filter"<"col-sm-12"<"pull-left"><"pull-right"><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
					"ajax" : {
						async: false,
						"url":'${context}/ax/admin/policy/messenger/list',
					   	"type":'POST',
					   	"dataSrc" : "data",
					   	"data" : function(param) {
					   		param.search_type = $('#msg_sel_search_type option:checked').val();
							param.search_text = $('#msg_att_search_text').val();
				        },
				        "beforeSend" : function(){
							jQuery('#preloader').show();
				        },
				        "dataSrc": function ( json ) {
							jQuery('#preloader').hide();
			                return json.data;
			            }   
					},
			 		"serverSide" : true,
			 		"columns": [{
						data: "msgNo",			// check_box (ID)
						render : function(data,type,row) {
							var check = '<%= scriptCode %>';
							var strData = data.toString();
		
							if (check.indexOf(strData) != -1 && check != '') {
								return '<input type="checkbox" name="msg_block_check" class="msg_block_check" value="' + data + '" onClick="setMsgBlockSelectInfo( this )" checked />';
							} else {
								return '<input type="checkbox" name="msg_block_check" class="msg_block_check" value="' + data + '" onClick="setMsgBlockSelectInfo( this )" />';							
							}
						}
					}, {
						data: "msgNo"			// ID
					}, {
						data: "msgName"			// 메신저명
					}, {
						data: "processName"		// 메신저파일명
					}, {
						data: "txtLog"			// 텍스트 로그
						,render : function(data,type,row) {
							if(data) {
								return '사용';
							} else {
								return '미사용';
							}
						}
					}, {
						data: "txtBlock"		// 텍스트 차단
						,render : function(data,type,row) {
							if(data) {
								return '사용';
							} else {
								return '미사용';
							}
						}
					}, {
						data: "fileLog"			// 파일 로그
						,render : function(data,type,row) {
							if(data) {
								return '사용';
							} else {
								return '미사용';
							}
						}
					}, {
						data: "fileBlock"		// 파일 차단
						,render : function(data,type,row) {
							if(data) {
								return '사용';
							} else {
								return '미사용';
							}
						}
					}],  
					"pageLength": 10,
					"iDisplayLength": 10,
			 		"language": {               
						"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 사용자",
						"infoEmpty":      "검색된 데이터가 없습니다.",
						"lengthMenu": "  _MENU_ 개",
						"zeroRecords" :"검색된 정책이 없습니다.",
						"paginate": {
							"previous":"Prev",
							"next": "Next",
							"last": "Last",
							"first": "First"
						}
					},
			 	  	"columnDefs": [
					{	
						"targets": [0],	// check_box
						"class":"center-cell"
					}, {  
						'targets': [1]	// ID
						,"class":"center-cell"
						,"visible" : false
					}, {  
						'targets': [2]	// 메신저명
						,"class":"center-cell"
					}, {	
						"targets": [3]	// 메신저파일명
						,"class":"center-cell"
					}, {	
						"targets": [4]	// 텍스트 로그
						,"class":"center-cell"
					}, {	
						"targets": [5]	// 텍스트 차단
						,"class":"center-cell"
						,"visible" : false
					}, {	
						"targets": [6]	// 파일 로그
						,"class":"center-cell"
					}, {	
						"targets": [7]	// 파일 차단
						,"class":"center-cell"
						,"visible" : false
					}],
					"initComplete": function( settings, json ) {
					}
				});
			
			}
	}
</script>
































