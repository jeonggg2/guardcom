<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	boolean isWebAddr = Boolean.parseBoolean(request.getParameter("isWebAddr"));
	String webAddrCode = request.getParameter("webAddrCode").toString();
	String applyCode = webAddrCode.length() > 0 ? "," + webAddrCode : "";
	
	if (!isWebAddr){ 
		applyCode = "Y" + applyCode;
	} else {
		applyCode = "N" + applyCode;
	}
%>
<div>
	<table class="table table-bordered">
		<tr>
			<td class="th-cell-gray center-cell" width="75px" style="vertical-align: middle;">웹사이트 차단 선택</td>
			<td class="center-cell" style="vertical-align: middle;">
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_website_block" value="Y" <% if (!isWebAddr){ %> checked <%}%> /><i></i> 허용
				</label>
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_website_block" value="N" <% if (isWebAddr){ %> checked <%}%>/><i></i> 차단
				</label>
			</td>
			<td class="th-cell-gray center-cell" width="75px" style="vertical-align: middle;">적용상태</td>
			<td class="center-cell" style="vertical-align: middle;">
				<input type="text" id="att_website_block_type" name="att_website_block_type" class="form-control" value="<%= applyCode%>" disabled />
			</td>
		</tr>
	</table>
</div>

<div>
	<select class="form-control pull-left" id="site_sel_search_type" name="site_sel_search_type" style="width:200px;">
		<option value="1">사이트주소</option>
		<option value="2">설명</option>
	</select>
	<input type="text" class="form-control pull-left" id="site_att_search_text" name="site_att_search_text" placeholder="검색어를 입력해주세요." style="width:200px;" value="" />
	<button onclick="searchSiteList();" class="btn btn-info pull-left"><i class="fa fa-search"></i> 검색</button>
</div>
<table class="table table-bordered table-td-middle" id="web_site_table" style="width:100%; margin-top: 20px;">
	<col width="100px">
	<col>
	<col width="250px">
	<thead>
		<tr>
			<td class="center-cell vertical-middle">선택</td>
			<td>사이트ID</td>
			<td class="center-cell vertical-middle">사이트주소</td>
			<td class="center-cell vertical-middle">설명</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
													

<script type="text/javascript">

	function searchSiteList(){
		var datatable = $('#web_site_table').dataTable().api();
		datatable.ajax.reload();
	}

	$(document).ready(function(){
		$("input[name=radio_website_block]").change(function() {
			var chk_value = $(':radio[name="radio_website_block"]:checked').val();
			var type = $('#att_website_block_type').val();		
			if (chk_value == 'Y') {
				$('#att_website_block_type').val(type.replace('N','Y'));	
			} else {
				$('#att_website_block_type').val(type.replace('Y','N'));
			}
		});
	});

	function setWebSiteSelectInfo(check_box) {
		
		var webCode = $(check_box).val();
		var type = $('#att_website_block_type').val();
		console.log(type);
		if ($(check_box).is(':checked')) {
			
			type += "," + webCode;
			$('#att_website_block_type').val(type);
			
		} else {

			var temp = type.split(',');
			var result = temp[0];
			
			if (temp.length > 1) {
				for (var i = 1 ; i < temp.length ; i++) {
					if (temp[i] != webCode) {
						result += "," + temp[i];
					}
				}
				
				$('#att_website_block_type').val(result);
			}
			
		}
		
	}

	function web_site_table() {
			 
			if (jQuery().dataTable) {
		
				var wsTable = jQuery('#web_site_table');
				wsTable.dataTable({
					"dom": '<"row view-filter"<"col-sm-12"<"pull-left"><"pull-right"><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
					"ajax" : {
						async: false,
						"url":'${context}/ax/admin/policy/website/list',
					   	"type":'POST',
					   	"dataSrc" : "data",
					   	"data" : function(param) {
					   		param.search_type = $('#site_sel_search_type option:checked').val();
							param.search_text = $('#site_att_search_text').val();
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
						data: "siteId",			// check_box (ID)
						render : function(data,type,row) {
							var check = '<%= applyCode %>';
							var strData = data.toString();
		
							if (check.indexOf(strData) != -1 && check != '') {
								return '<input type="checkbox" name="web_site_check" class="web_site_check" value="' + data + '" onClick="setWebSiteSelectInfo( this )" checked />';
							} else {
								return '<input type="checkbox" name="web_site_check" class="web_site_check" value="' + data + '" onClick="setWebSiteSelectInfo( this )" />';							
							}
						}
					}, {
						data: "siteId"			// ID
					}, {
						data: "address"			// 사이트주소
					}, {
						data: "description"		// 설명
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
						'targets': [2]	// 사이트주소
						,"class":"center-cell"
					}, {	
						"targets": [3]	// 설명
						,"class":"center-cell"
					}],
					"initComplete": function( settings, json ) {
					}
				});
			
			}
	}
</script>
































