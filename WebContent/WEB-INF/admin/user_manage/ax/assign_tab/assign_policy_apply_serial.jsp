<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	boolean onlyFlag = Boolean.parseBoolean(request.getParameter("onlyFlag"));
	boolean isComPortBlock = Boolean.parseBoolean(request.getParameter("isComPortBlock"));
	String comPortBlockCode = request.getParameter("comPortBlockCode").toString();
	String applyCode = comPortBlockCode.length() > 0 ? "," + comPortBlockCode : "";
	String scriptCode = onlyFlag? comPortBlockCode : "";
	
	if (!isComPortBlock){ 
		applyCode = "Y" + applyCode;
	} else {
		applyCode = "N" + applyCode;
	}
%>
<div>
	<table class="table table-bordered">
		<tr>
			<td class="th-cell-gray center-cell" width="160px" style="vertical-align: middle;">시리얼 포트 차단 선택</td>
			<td class="center-cell" style="vertical-align: middle;">
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_com_port_block" value="Y" <% if (!isComPortBlock){ %> checked <%}%>/><i></i> 허용
				</label>
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_com_port_block" value="N" <% if (isComPortBlock){ %> checked <%}%>/><i></i> 차단
				</label>
			</td>
			<td class="th-cell-gray center-cell" width="75px" style="vertical-align: middle;">적용상태</td>
			<td class="center-cell" style="vertical-align: middle;">
				<input type="text" id="att_com_port_type" name="att_com_port_type" class="form-control" value="<%= applyCode%>" disabled />
			</td>
		</tr>
	</table>
</div>

<div>
	<select class="form-control pull-left" id="serial_sel_search_type" name="serial_sel_search_type" style="width:200px;">
		<option value="1">포트이름</option>
	</select>
	<input type="text" class="form-control pull-left" id="serial_att_search_text" name="serial_att_search_text" placeholder="검색어를 입력해주세요." style="width:200px;" value="" />
	<button onclick="searchSerialList();" class="btn btn-info pull-left"><i class="fa fa-search"></i> 검색</button>
</div>
<table class="table table-bordered table-td-middle" id="com_port_table" style="width:100%; margin-top: 20px;">
	<col width="10%">
	<col width="45%">
	<col width="45%">
	<thead>
		<tr>
			<th class="center-cell vertical-middle">선택</th>
			<th>포트ID</th>
			<th class="center-cell vertical-middle">포트이름</th>
			<th class="center-cell vertical-middle">설명</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
													

<script type="text/javascript">

	function searchSerialList(){
		var datatable = $('#com_port_table').dataTable().api();
		datatable.ajax.reload();
	}

	$(document).ready(function(){
		$("input[name=radio_com_port_block]").change(function() {
			var chk_value = $(':radio[name="radio_com_port_block"]:checked').val();
			var type = $('#att_com_port_type').val();		
			if (chk_value == 'Y') {
				if (type == '') {
					$('#att_com_port_type').val(chk_value);
				} else {
					$('#att_com_port_type').val(type.replace('N','Y'));
				}
			} else {
				if (type == '') {
					$('#att_com_port_type').val(chk_value);
				} else {
					$('#att_com_port_type').val(type.replace('Y','N'));
				}
			}
		});
	});
	
	function setSerialSelectInfo(check_box) {
		
		var serialCode = $(check_box).val();
		var type = $('#att_com_port_type').val();
		
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
			
			type += "," + serialCode;
			$('#att_com_port_type').val(type);
			
		} else {
	
			var temp = type.split(',');
			var result = temp[0];
			
			if (temp.length > 1) {
				for (var i = 1 ; i < temp.length ; i++) {
					if (temp[i] != serialCode) {
						result += "," + temp[i];
					}
				}
				
				$('#att_com_port_type').val(result);
			}
			
		}
		
	}
	
	function com_port_table() {
		
			if (jQuery().dataTable) {
		
				var cpTable = jQuery('#com_port_table');
				cpTable.dataTable({
					"dom": '<"row view-filter"<"col-sm-12"<"pull-left"><"pull-right"><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
					"ajax" : {
					 	async: false,
						"url":'${context}/ax/admin/policy/serial/list',
					   	"type":'POST',
					   	"dataSrc" : "data",
					   	"data" : function(param) {
					   		param.search_type = $('#serial_sel_search_type option:checked').val();
							param.search_text = $('#serial_att_search_text').val();
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
						data: "serialNo",			// check_box (ID)
						render : function(data,type,row) {
							var check = '<%= scriptCode %>';
							var strData = data.toString();
							
							if (check.indexOf(strData) != -1 && check != '') {
								return '<input type="checkbox" name="com_port_check" class="com_port_check" value="' + data + '" onClick="setSerialSelectInfo( this )" checked />';
							} else {
								return '<input type="checkbox" name="com_port_check" class="com_port_check" value="' + data + '" onClick="setSerialSelectInfo( this )" />';							
							}
						}
					}, {
						data: "serialNo"			// ID
					}, {
						data: "serialName"			// 포트이름
					}, {
						data: "description"			// 설명
					}, {
						data: "allow"			// 사용여부
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
						'targets': [2]	// 포트이름
						,"class":"center-cell"
					}, {	
						"targets": [3]	// 설명
						,"class":"center-cell"
					}, {	
						"targets": [4]	// 사용여부
						,"class":"center-cell"
						,"visible":false
					}],
					"initComplete": function( settings, json ) {
					}
				});
			
			}

	}
</script>
































