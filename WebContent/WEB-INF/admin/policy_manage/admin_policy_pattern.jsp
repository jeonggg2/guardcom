<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="utf-8">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Console</title>

		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />

		<!-- CORE CSS -->
		<link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- THEME CSS -->
		<link href="${context}/assets/css/essentials.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/layout.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/color_scheme/green.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/jstree/themes/default/style.min.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/datatables/extensions/Buttons/css/buttons.jqueryui.min.css" rel="stylesheet" type="text/css"  />
		
		<!-- Alert -->
		<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />
		
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 5000); %> 
			<% request.setAttribute("menu_sub_first", 5300); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>민감정보 관리</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">
						
						<div class="col-md-12">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>민감 정보</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<button type="button" id="btnRegPattern" class="btn btn-sm btn-green pull-right" onclick="javascript:fn_open_reg_pattern_popup(0);"><i class="fa fa-check"></i>정책 등록</button>
											<table id="table-pattern-policy" class="table table-bordered table-hover">
												<thead>
													<tr>
														<th>No</th>
														<th>패턴 이름</th>
														<th>패턴 데이터</th>
														<th>설명</th>
														<th>사용여부</th>
														<th>정책삭제</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
									</div>
											
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
			</section>
		</div>

		<div id="reg_pattern_popup_div"></div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				fn_get_pattern_policy_data();
			});
			
			function fn_open_reg_pattern_popup(code){
				
				$.ajax({      
				    type:"POST",  
				    url:'${context}/admin/policy/pattern/register',
				    async: false,
				    data:{ 
				    	code : code,
				    	_ : $.now()
				    },
				    success:function(data){
				    	$("#reg_pattern_popup_div").html(data);
			            $('#modalPolicyRegPattern').modal('show');
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
			}
			
			function fn_delete_pattern_policy_item(code) {
				vex.defaultOptions.className = 'vex-theme-os';
	    		
	    		vex.dialog.open({
					message: '해당 정책을 삭제 하시겠습니까?',
					buttons: [
				    	$.extend({}, vex.dialog.buttons.YES, {
				     	text: '삭제'
				  	}),
				  	$.extend({}, vex.dialog.buttons.NO, {
				    	text: '취소'
				  	})],
				  	callback: function(data) {
			 	  		if (data) {
			 	  			delete_data(code);
			 	    	} else {
			 	  			return false;
			 	    	}
			 	  	}
				})
			}
			
			function delete_data(code){
				
				$.ajax({      
				    type:"POST",  
				    url:'${context}/admin/policy/pattern/delete',
				    async: false,
				    data:{ 
				    	code : code,
				    	_ : $.now()
				    },
				    success:function(data){
				    	
				    	if (data.returnCode == 'S') {
				    		var datatable = $('#table-pattern-policy').dataTable().api();
				    		datatable.ajax.reload();
				    		
				    		vex.dialog.open({
				    			message: '정책 삭제가 완료되었습니다.',
				    			  buttons: [
				    			    $.extend({}, vex.dialog.buttons.YES, {
				    			      text: '확인'
				    			  })]
				    		})
				    		
				    	} else {
			    			vex.dialog.open({
			    				message: '정책 삭제중 예기치 못한 오류가 발생하여 삭제에 실패하였습니다.',
			    				  buttons: [
			    				    $.extend({}, vex.dialog.buttons.YES, {
			    				      text: '확인'
			    				  })]
			    			});
				    	}
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
			}
		
			function fn_get_pattern_policy_data() {
				console.log("dataTable");
				loadScript(plugin_path + "datatables/media/js/jquery.dataTables.min.js", function(){
				loadScript(plugin_path + "datatables/media/js/dataTables.bootstrap.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/dataTables.buttons.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.print.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.html5.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.jqueryui.min.js", function(){
					 
					if (jQuery().dataTable) {
				
						var table = jQuery('#table-pattern-policy');
						table.dataTable({
							"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right"><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
							"ajax" : {
							"url":'${context}/ax/admin/policy/pattern/list',
						   	"type":'POST',
						   	"dataSrc" : "data",
						   	"data" :  {},
					        "beforeSend" : function(){
								jQuery('#preloader').show();
					        },
					        "dataSrc": function ( json ) {
								jQuery('#preloader').hide();
				                return json.data;
				            }   
						},
						lengthMenu: [[20, 100, 1000], [20, 100, 1000]],
						tableTools: {
					          "sSwfPath": plugin_path + "datatables/extensions/Buttons/js/swf/flashExport.swf"
					        },
					    "buttons": [
				             {
				                  text: '<i class="fa fa-lg fa-clipboard">csv</i>',
				                  extend: 'csvHtml5',
				                  className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-csv-btn export-csv ttip hidden',
				                  bom: true,
				                  exportOptions: {
				                      modifier: {
				                          search: 'applied',
				                          order: 'applied'
				                      }
				                  }
				              }
					     ],
					    "ordering" : false,
				 		"serverSide" : true,
				 		"columns": [{
							data: "patNo"			//ID
							,render : function(data, type, row, a){
								var paging = a.settings._iDisplayStart;
								return paging + a.row + 1;
								
							}
						}, {
							data: "PatName"			//패턴 이름
						}, {
							data: "data"			//패턴 데이터
						}, {
							data: "notice"			//설명
						}, {                                   
							data: "valid"			//사용여부
							,render : function(data,type,row) {
								return data == 1? '사용' : '사용안함';
							}
						}, {                                   
							data: "patNo"			//삭제
							,render : function(data,type,row) {
								return '<button type="button" id="btnDeletePolicy" class="btn btn-xs btn-danger" onclick="javascript:fn_delete_pattern_policy_item('+ data +');"><i class="fa fa-trash" aria-hidden="true"></i>정책삭제</button>';
							}
						}],  
						"pageLength": 20,
						"iDisplayLength": 20,
				 		"language": {               
							"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 개",
							"infoEmpty":      "검색된 데이터가 없습니다.",
							"lengthMenu": "  _MENU_ 개",
							"paginate": {
								"previous":"Prev",
								"next": "Next",
								"last": "Last",
								"first": "First"
							},
							"zeroRecords": "정책이 존재하지 않습니다."
						},
				 	  	"columnDefs": [
						{	
							"targets": [0],	//ID
							"class":"center-cell"
						}, {  
							'targets': [1]	//패턴 이름
							,"class":"center-cell"
						}, {	
							"targets": [2]	//패펀 데이터
							,"class":"center-cell"
						}, {	
							"targets": [3]	//설명
							,"class":"center-cell"
						}, {	
							"targets": [4],	//사용여부
							"visible":false,
							"class":"center-cell"
						}, {	
							"targets": [5],	//삭제
							"class":"center-cell"
						}],
						"initComplete": function( settings, json ) {
//							 $('#table-pattern-policy').colResizable({liveDrag:true});
						}
					});
						
					var ctbl = $('#table-pattern-policy').DataTable();
					ctbl.on( 'click', 'td', function () {
						var data = ctbl.row( $(this).parent() ).data();
						
						if($(this).index() != 5) {
							fn_open_reg_pattern_popup(data.patNo);
						}
						
					});
				}
		   		});
				});
				});
				});
				});
				});
			}
		</script>
	</body>
</html>
		