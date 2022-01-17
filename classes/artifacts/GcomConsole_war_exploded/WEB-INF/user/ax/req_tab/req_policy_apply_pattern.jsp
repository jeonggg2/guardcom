<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	boolean isFilePattern = Boolean.parseBoolean(request.getParameter("isFilePattern"));
	String filePatternCode = request.getParameter("filePatternCode").toString();
	String applyCode = filePatternCode.length() > 0 ? filePatternCode : "";
		
%>
<div>
	<table class="table table-bordered">
		<tr>
			<td class="th-cell-gray center-cell" width="150px" style="vertical-align: middle;">패턴 차단 선택</td>
			<td class="center-cell" style="vertical-align: middle;">
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_pattern_block" value="Y" <% if (!isFilePattern){ %> checked <%}%> /><i></i> 허용
				</label>
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_pattern_block" value="N" <% if (isFilePattern){ %> checked <%}%> /><i></i> 차단
				</label>
			</td>
			<td class="th-cell-gray center-cell" width="75px" style="vertical-align: middle;">적용상태</td>
			<td class="center-cell" style="vertical-align: middle;">
				<input type="text" id="att_pattern_type" name="att_pattern_type" class="form-control" value="<%= applyCode%>" disabled/>
			</td>
		</tr>
	</table>
</div>

<div>
	<select class="form-control pull-left" id="pattern_sel_search_type" name="pattern_sel_search_type" style="width:200px;">
		<option value="1">패턴이름</option>
		<option value="2">패턴데이터</option>
	</select>
	<input type="text" class="form-control pull-left" id="pattern_att_search_text" name="pattern_att_search_text" placeholder="검색어를 입력해주세요." style="width:200px;" value="" />
	<button onclick="searchPatternList();" class="btn btn-info pull-left"><i class="fa fa-search"></i> 검색</button>
</div>
<table class="table table-bordered table-td-middle" id="pattern_table" style="width: 100%; margin-top: 20px;">
	<col width="90px">
	<col width="120px">
	<col width="">
	<col width="180px">
	<thead>
		<tr>
			<td class="center-cell vertical-middle">선택</td>
			<td>패턴ID</td>
			<td class="center-cell vertical-middle">패턴이름</td>
			<td class="center-cell vertical-middle">패턴데이터</td>
			<td class="center-cell vertical-middle">설명</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
													

<script type="text/javascript">

function searchPatternList(){
	var datatable = $('#pattern_table').dataTable().api();
	datatable.ajax.reload();
}

$(document).ready(function(){
	$("input[name=radio_pattern_block]").change(function() {
		var chk_value = $(':radio[name="radio_pattern_block"]:checked').val();
		var type = $('#att_pattern_type').val();		
		if (chk_value == 'Y') {
			$('#att_pattern_type').val('');
			$('.pattern_check').prop("checked",false);
		} else {
			vex.defaultOptions.className = 'vex-theme-os';
    		
    		vex.dialog.open({
				message: '차단 패턴을 선택하면 차단 됩니다.',
				buttons: [
			    	$.extend({}, vex.dialog.buttons.YES, {
			     	text: '확인'
			  	})]
			});
			$('input:radio[name=radio_pattern_block]:input[value="Y"]').prop("checked", true);
		} 
	});
});

function setPatternSelectInfo(check_box) {
	
	var patCode = $(check_box).val();
	var type = $('#att_pattern_type').val();
	
	if ($(check_box).is(':checked')) {
		
		if (type.length < 1) {
			type += patCode;
			$('input:radio[name=radio_pattern_block]:input[value="N"]').prop("checked", true);
		} else {
			type += "," + patCode;
		}
		
		$('#att_pattern_type').val(type);
		
	} else {

		var temp = type.split(',');
		var result = [];

		for (var i = 0 ; i < temp.length ; i++) {
			if (temp[i] != patCode) {
				result.push(temp[i]);
			}
		}
		
		$('#att_pattern_type').val(result.toString());
		
	}
	
}

function pattern_table() {
	
		if (jQuery().dataTable) {
	
			var nprTable = jQuery('#pattern_table');
			nprTable.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left"><"pull-right"><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				"ajax" : {
				 	async: false,
					"url":'${context}/ax/admin/policy/pattern/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" : function(param) {
				   		param.search_type = $('#pattern_sel_search_type option:checked').val();
						param.search_text = $('#pattern_att_search_text').val();
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
					data: "patNo",			// check_box (ID)
					render : function(data,type,row) {
						var check = '<%= applyCode %>';
						var strData = data.toString();
						
						if (check.indexOf(strData) != -1 && check != '') {
							return '<input type="checkbox" name="pattern_check" class="pattern_check" value="' + data + '" onClick="setPatternSelectInfo( this )" checked />';
						} else {
							return '<input type="checkbox" name="pattern_check" class="pattern_check" value="' + data + '" onClick="setPatternSelectInfo( this )" />';							
						}
					}
				}, {
					data: "patNo"			// ID
				}, {
					data: "PatName"			// 패턴 이름
				}, {
					data: "data"			// 패턴 데이터
				}, {
					data: "notice"			// 설명
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
					'targets': [2]	// 패턴 이름
					,"class":"center-cell"
				}, {	
					"targets": [3]	// 패턴 데이터
					,"class":"center-cell"
				}, {	
					"targets": [4]	// 설명
				,"class":"center-cell"
				}],
				"initComplete": function( settings, json ) {
				}
			});
		
		}

}
</script>
































