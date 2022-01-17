<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	String code = request.getParameter("code").toString();
%>
<div id="modalWebAddrSettingInfo" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myModalLabel">웹 사이트 차단 정책 적용 리스트</h4>
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
											<strong>정책적용리스트</strong> <!-- panel title -->
										</span>
									</div>
		
									<!-- panel content -->
									<div class="panel-body">
										<table class="table table-bordered" id="web_site_info_table" style="width:100%;">
											<thead>
												<tr>
													<td>선택</td>
													<td>사이트ID</td>
													<td>사이트주소</td>
													<td>설명</td>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
										
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
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
													

<script type="text/javascript">

	function web_site_info_table() {
			var code = '<%= code %>';			 
			if (jQuery().dataTable) {
		
				var wsTable = jQuery('#web_site_info_table');
				wsTable.dataTable({
					"dom": '<"row view-filter"<"col-sm-12"<"pull-left"><"pull-right"><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
					"ajax" : {
						async: false,
						"url":'${context}/ax/user/policy/website/list',
					   	"type":'POST',
					   	"dataSrc" : "data",
					   	"data" :  {
					   		code : code,
					   		_ : $.now()
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
						render : function(data, type, row, a){
							var paging = a.settings._iDisplayStart;
							return paging + a.row + 1;
							
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
						"paginate": {
							"previous":"Prev",
							"next": "Next",
							"last": "Last",
							"first": "First"
						},
						"zeroRecords":  "선택된 정책이 없습니다."
					},
			 	  	"columnDefs": [
					{	
						"targets": [0],	// check_box
						"class":"center-cell"
						,"visible" : false
					}, {  
						'targets': [1]	// ID
						,"class":"center-cell"
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
































