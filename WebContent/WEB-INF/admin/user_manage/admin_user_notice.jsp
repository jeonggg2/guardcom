<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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
        
        <script type="text/javascript" src="${context}/assets/js/admin_function.js"></script>
	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 9000); %> 
			<% request.setAttribute("menu_sub_first", 9600); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>???????????? ??????</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">
						<div class="col-md-12">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>????????????</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
											<div>
												<select class="form-control pull-left" id="sel_search_type" name="sel_search_type" style="width:100px;">
													<option value="1">??????</option>
													<option value="2">?????????</option>
												</select>
												<input type="text" class="form-control pull-left" id="att_search_text" name="att_search_text" placeholder="???????????? ??????????????????." style="width:200px;" value="" />
												<button onclick="reloadTable();" class="btn btn-info pull-left"><i class="fa fa-search"></i> ??????</button>
											</div>
											
											<button id="btnNoticeDel" class="btn btn-amber pull-right">???????????????</button>
											<button id="btnNoticeWrite" class="btn btn-green pull-right">???????????????</button>
											
											<!-- <div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:400px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="35%">??????</td>
															<td>
																<input type="text" name="filterNoticeTitel" id="filterNoticeTitel" value="" class="form-control required">
															</td>
														</tr>
														<tr>         
															<td width="35%">?????????</td>
															<td>
																<input type="text" name="filterNoticeRegStaf" id="filterNoticeRegStaf" value="" class="form-control required">
															</td>
														</tr>
													</tbody>
												</table>	
												
												<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle();">??????</button>
																					
											</div> -->
										</div>
									</div>
									
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-striped table-bordered table-hover x-scroll-table" id="table_notice" style="width:100%; min-width: 600px;">
												<col width="20px">
												<col width="80px">
												<col width="70px">
												<col>
												<col width="120px">
												<col width="150px">
												<col width="50px">
												<thead>
													<tr>
														<th><input type="checkbox" id="all_check_info" name="all_check_info" /></th>
														<th>No</th>
														<th>??????</th>
														<th class="center-cell">??????</th>
														<th>?????????</th>
														<th>?????????</th>
														<th>??????</th>
														<th>??????????????????</th>
														<th>??????ID</th>
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
			
			<div id="notice_detail_view_div"></div>
			<div id="notice_modify_div"></div>
		</div>
		<script>
	
		 	function reloadTable(){
		 		var datatable = $('#table_notice').dataTable().api();
				datatable.ajax.reload();   	
		 	}
		 	
			function fn_bbs_detail_view(bbs_id, file_yn, file_id) {
				
				$.ajax({      
			        type:"POST",  
			        url:'${context}/admin/user/notice/view',
			        async: false,
			        data:{
			        	bbs_id : bbs_id,
			        	file_yn : file_yn,
			        	file_id : file_id,
			        	_ : $.now()
			        },
			        success:function(args){
			        	
			            $("#notice_detail_view_div").html(args);
			            $("#modalNoticeDetailView").modal('show');
			        },   
			        error:function(e){
			            console.log(e.responseText);  
			        }  
			    });
			}
			
			function fn_modify(bbs_id, file_yn, file_id) {
				
				$.ajax({      
			        type:"POST",  
			        url:'${context}/admin/user/notice/modify',
			        async: false,
			        data:{
			        	bbs_id : bbs_id,
			        	file_yn : file_yn,
			        	file_id : file_id,
			        	_ : $.now()
			        },
			        success:function(args){
			        	$("#modalNoticeDetailView").modal('hide');
			            $("#notice_modify_div").html(args);
			            $("#modalNoticeModify").modal('show');
			        },   
			        error:function(e){
			            console.log(e.responseText);  
			        }  
			    });
			}
			
			// ?????? ?????? ?????? ??? ?????? ?????? ?????? ??????
		 	var check_Info = function() {
				
		 		var checkboxLen =  $("input:checkbox[name='notice_item_check']").length;
		 		var checkedLen = $("input:checkbox[name='notice_item_check']:checked").length;
		
		 		if(checkboxLen == checkedLen){
		 			$("#all_check_info").prop("checked", true);
		 		} else {
		 			$("#all_check_info").prop("checked", false);
		 		}
		 		
		 	}
			
			function fn_delete_notice_item () {
				var checkedLen = $("input:checkbox[name='notice_item_check']:checked").length;
		 		if (checkedLen < 1) {
		 			infoAlert("?????? ??? ???????????? ??????????????????.");
		 			return false;
		 		}
		 		
		 		var delete_arr = new Array();
				
				$(":checkbox[name='notice_item_check']:checked").each(function(pi,po){
					var check_item = $(this).val();
					delete_arr.push(parseInt(check_item));
				});
				
				console.log(delete_arr);
				
				$.ajax({      
				    type:"POST", 
				    url:'${context}/admin/user/notice/delete',
				    async: false,
				    data:{
				    	delete_list : JSON.stringify(delete_arr),
				    	_ : $.now()
				    },
				    success:function(data){
				    	vex.defaultOptions.className = 'vex-theme-os'
				    	
			    		var datatable = $('#table_notice').dataTable().api();
						datatable.ajax.reload();
						
				    	if(data.returnCode == "S") {
				    			
			    			vex.dialog.open({
			    				message: '???????????? ?????? ???????????????.',
			    				  buttons: [
			    				    $.extend({}, vex.dialog.buttons.YES, {
			    				      text: '??????'
			    				  })],
			    				  callback: function(data) {
		    				 	  	if (data) {
		    				 	  		
		    				 	    }
		    				 	  }
			    			})
				    	} else {
				    			
			    			vex.dialog.open({
			    				message: '????????? ????????? ?????????????????????.',
			    				  buttons: [
			    				    $.extend({}, vex.dialog.buttons.YES, {
			    				      text: '??????'
			    				  })]
			    			})
				    	}
				    },   
				    error:function(e){  
				        console.log(e.responseText);  
				    }  
				});
			}
		 	
			$(document).ready(function(){
				
				$(".select2theme").select2({
		   			  minimumResultsForSearch: -1,
		   			  dropdownAutoWidth : true,
		   			  width: 'auto'
		   		});
				
				$('#btnNoticeWrite').click(function(){
					location.href = "${context}/admin/user/notice/write" ;					
				});
				
				$('#btnNoticeDel').click(function(){
					fn_delete_notice_item();
				});
				
				//?????? ?????? ?????? ?????? ???
				$("#all_check_info").click(function(){
					
				      if($(this).is(":checked")) {
				    	  $(".notice_item_check").prop("checked", true);
				      } else {
				    	  $(".notice_item_check").prop("checked", false);
				      }
				});
		
				loadScript(plugin_path + "datatables/media/js/jquery.dataTables.min.js", function(){
				loadScript(plugin_path + "datatables/media/js/dataTables.bootstrap.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/dataTables.buttons.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.print.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.html5.min.js", function(){
				loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.jqueryui.min.js", function(){
		 
						if (jQuery().dataTable) {
		
							var table = jQuery('#table_notice');
							table.dataTable({
								"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" ><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
								"ajax" : {
									"url":'${context}/ax/admin/notice/list',
								   	"type":'POST',
								   	"dataSrc" : "data",
								   	"data" :  function(param) {
								   		param.search_type = $('#sel_search_type option:checked').val();
										param.search_text = $('#att_search_text').val();
							        },
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
								              },  					              
								          {
							                  text: '<i class="fa fa-lg fa-clipboard">?????????</i>',
							                  extend: 'print',
							                  className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-export-btn export-print ttip hidden',
							                  exportOptions: {
							                      modifier: {
							                          search: 'applied',
							                          order: 'applied'
							                      }
							                  }
							              }, 
		
							     ],
						 		"serverSide" : true,
						 	    "ordering": false,
								"columns": [{
									data: "bbsId"
									,render : function(data, type, row){
										return '<input type="checkbox" name="notice_item_check" class="notice_item_check" value="' + data + '" onClick="javascript:check_Info()"/>'
									}
								}, {
									data: "bbsId",
									"orderable": false	//No
									,render : function(data, type, row, a){
										var paging = a.settings._iDisplayStart;
										return paging + a.row + 1;
										
									}
								}, {
									data: "bbsSpecialYN",
									"orderable": false	//??????
									,render : function(data, type, row) {
										return data == 'Y' ? '<span style="color:#e46c6c;"><i class="fa fa-star"></i></span>' : '';	
									}
								}, {
									data: "bbsTitle",
									"orderable": false	//??????
									,render : function(data, type, row) {
										var file = row.bbsAttfileYN == 'Y'? '<i class="fa fa-paperclip"></i>' : '';
										return '<b style=\"cursor:pointer;\" >' + data + '</b>&nbsp;<span style="color:#1ABC9C; font-size:15px;">' + file + '</span>';
									}
								}, {
									data: "bbsRegStaf",
									"orderable": false	//?????????
								}, {
									data: "bbsRegDate",
									"orderable": false	//?????????
								}, {
									data: "bbsClickCnt",
									"orderable": false	//?????????
								}, {
									data: "bbsAttfileYN",
									"orderable": false	//??????????????????
								}, {
									data: "attfileId",
									"orderable": false	//??????ID
								}],
								"pageLength": 20,
								"iDisplayLength": 20,
								"pagingType": "bootstrap_full_number",
								"language": {
									"info": " _PAGES_ ????????? ???  _PAGE_ ????????? / ??? _TOTAL_ ???",
									"infoEmpty": "????????? ???????????? ????????????.",
									"zeroRecords" :"????????? ???????????? ????????????.",
									"lengthMenu": "  _MENU_ ???",
									"paginate": {
										"previous":"Prev",
										"next": "Next",
										"last": "Last",
										"first": "First"
									},
									
								},
								"columnDefs": [
								{	
									"targets": [0],	//ID
									"class":"center-cell"
								}, {  
									'targets': [1]	//No
									,"class":"center-cell"
								}, {	
									"targets": [2]	//??????
									,"class":"center-cell"
								}, {	
									"targets": [3]	//??????
									,"class":"left-cell"
								}, {	
									"targets": [4],	//?????????
									"class":"center-cell"
								}, {	
									"targets": [5]	//?????????
									,"class" : "center-cell"
								}, {	
									"targets": [6]	//?????????
									,"class" : "center-cell"
								}, {	
									"targets": [7]	//?????? ??????
									,"class" : "center-cell"
									,"visible": false
								}, {	
									"targets": [8]	//?????? ID
									,"class" : "center-cell"
									,"visible": false
								}],						
									"initComplete": function( settings, json ) {
									
								}
							});
							
							var notice = $('#table_notice').DataTable();
							notice.on( 'click', 'td', function () {
								var data = notice.row( $(this).parent() ).data();
								
								if($(this).index() == 3){	// ?????? ??????
									fn_bbs_detail_view(data.bbsId, data.bbsAttfileYN, data.attfileId);
								}
							});
						}
					});
					});
					});
					});
					}); 
				});
				jQuery('#preloader').hide();
		    });
		</script>
	</body>
</html>