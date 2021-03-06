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
		
		<script type="text/javascript" src="${context}/assets/js/admin_function.js"></script>
		<script type="text/javascript" src="${context}/assets/js/date.js"></script>
		
		<!-- Alert -->
		<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
        <link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

        <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
        <script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>
        
	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<% request.setAttribute("menu_parent", 10000); %> 
			<% request.setAttribute("menu_sub_first", 10500); %> 
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			

			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>?????? ??????</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div id="layout-container" class="row" style="width: 100%; height: 100%;">
						
						<div class="ui-layout-west">
							<div id="panel-tree" class="panel panel-default">
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>?????????</strong> <!-- panel title -->
									</span>
								</div>

								<!-- panel content -->
								<div id="dept_tree" class="panel-body" style="padding-left: 0px; padding-right: 0px;">

								</div>
								<!-- /panel content -->

							</div>
							<!-- /PANEL -->
					
						</div>

						<div class="ui-layout-center">
							<div id="panel-list" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>?????? ?????? ?????? (?????? ?????? ????????? ????????? ??????/??????????????? ?????????)</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
			
											<!-- search filter button -->
											<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle(1,initLayout);"><i class="fa fa-filter" aria-hidden="true">&nbsp;????????????</i></button>
											<!-- search button -->
											<button type="button" class="btn btn-info" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;?????????</i></button>
											<!-- export button -->
											<button type="button" class="btn btn-primary pull-right" onclick="onClickExcelButton()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;????????????</i></button>
											<!-- print button -->
											<button type="button" class="btn btn-success pull-right" onclick="onClickPrintButton()"><i class="fa fa-print" aria-hidden="true">&nbsp;??????</i></button>
											
											
											<select class="select2theme" style="min-width:200px"id="filterPermit" name="filterPermit" onchange="reloadTable()">
											  <option value="">??????</option>
											  <option value="W" selected>?????? ??????</option>
											  <option value="P">?????? ?????? ??????</option>
											  <option value="R">????????? ?????? ??????</option>
											</select>

											<!-- search text content -->
											<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:700px;">
												<table id="user" class="table table-bordered">
													<tbody> 
														<tr>         
															<td width="15%">?????????</td>
															<td>
																<input type="text" name="filterUserId" id="filterUserId" value="" class="form-control">
															</td>
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserName" id="filterUserName" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserNumber" id="filterUserNumber" value="" class="form-control">
															</td>
															<td width="15%">?????????</td>
															<td>
																<input type="text" name="filterUserPhone" id="filterUserPhone" value="" class="form-control">
															</td>
														</tr>
														<tr>         
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserDuty" id="filterUserDuty" value="" class="form-control">
															</td>
															<td width="15%">??????</td>
															<td>
																<input type="text" name="filterUserRank" id="filterUserRank" value="" class="form-control">
															</td>
														</tr>																										
													</tbody>
												</table>	
												
												<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle(1,initLayout);">??????</button>
																					
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12" style="overflow: auto;">
											<table class="table table-bordered x-scroll-table" id="table_request_info" style="width:100%; min-width: 600px;">
												<thead>
													<tr>
														<th></th>
														<th>????????????</th>
														<th>??????</th>
														<th>?????????</th>
														<th>??????</th>
														<th>??????</th>
														<th>??????</th>
														<th>??????</th>
														<th>?????????</th>
														<th>IP</th>
														<th>????????????</th>
														<th>?????? ??????</th>
														<th>????????????</th>
														<th>????????????</th>
														<th>?????????</th>
														<th>?????? ??????</th>
														<th>?????? ?????????</th>
														<th>?????? ??????</th>
														<th>????????????</th>
													</tr>
												</thead>
				
												<tbody>
												</tbody>
											</table>
										
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
<script>

	var getFilterInfo = function(){
		var param = new Object();
		param.user_id = $('#filterUserId').val();
		param.user_name = $('#filterUserName').val();
		param.user_phone = $('#filterUserPhone').val();
		param.dept = getCheckedDept();
		return param;
	};
	
 	function setTree(){
		$.ajax({      
	        type:"POST",  
	        url:'${context}/common/tree/dept',
	        async: false,
	        //data:{},
	        success:function(args){   
	            $("#dept_tree").html(args);      
	        }, 
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	}
 	
 	function reloadTable(){
 		var datatable = $('#table_request_info').dataTable().api();
		datatable.ajax.reload();   	
 	
 	}

 	function onClickPrintButton(){
 		var $buttons = $('.export-print');
 		$buttons.click();
 	}
 	
 	function onClickExcelButton(){
		console.log('excel')
 		var $buttons = $('.export-csv');
 		$buttons.click();
 		
 	}
	
 	function fn_permit_request_policy(req, user_id, ip) {
 		
 		$.ajax({      
		    type:"POST",  
		    url:'${context}/admin/drm/decreq/permit',
		    async: false,
		    data:{
		    	request_no : req,
		    	user_id : user_id,
		    	ip : ip,
		    	_ : $.now()
		    },
		    success:function(data){
		    	if(data.returnCode == "S") {
		    		var datatable = $('#table_request_info').dataTable().api();
		 			datatable.ajax.reload();
		    		
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '????????? ?????? ???????????????.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '??????'
	    				  })]
	    			})
		    		
		    	} else {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '?????? ????????? ??????????????????.',
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
 	
 	function fn_reject_request_policy(req, user_id, ip) {
 		
 		$.ajax({      
		    type:"POST",  
		    url:'${context}/admin/drm/decreq/reject',
		    async: false,
		    data:{
		    	request_no : req,
		    	user_id : user_id,
		    	ip : ip,
		    	_ : $.now()
		    },
		    success:function(data){
		    	if(data.returnCode == "S") {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '?????? ?????? ????????? ????????? ???????????????.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '??????'
	    				  })],
	    				  callback: function(data) {
    				 	  	if (data) {
    				 	  		var datatable = $('#table_request_info').dataTable().api();
    				 			datatable.ajax.reload();
    				 	    }
    				 	  }
	    				  
	    			})
		    		
		    	} else {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '?????? ?????? ?????? ???????????? ??????????????????.',
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
 	
 	function requestPermitInfo(adminId, date){
		vex.defaultOptions.className = 'vex-theme-os';
		vex.dialog.open({
			input: [
			         '<label>???????????????</label>',			        
			         '<input name="adminId" type="text" readonly value=" '+ adminId +' " />',
			         '<label>????????????</label>',			        
			         '<input name="date" type="text" readonly value=" '+ date +' " />'
			     ].join(''),
			buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '??????'
		    })]
 		});
 	}
 	
 	function requestRejectInfo(adminId, date){
		vex.defaultOptions.className = 'vex-theme-os';
		vex.dialog.open({
			input: [
			         '<label>???????????????</label>',			        
			         '<input name="adminId" type="text" readonly value=" '+ adminId +' " />',
			         '<label>????????????</label>',			        
			         '<input name="date" type="text" readonly value=" '+ date +' " />'
			     ].join(''),
			buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '??????'
		    })]
 		});
 	}
 	
 	
 	function getDetailTable(data){
 		var sOut = '';
 		sOut += '<table class="table table-bordered">';			
 		sOut += '<col width="15%"><col width="35%"><col width="15%"><col width="35%">';
 		
 		switch (data.permitState) {
 		case 'W':
 			break;
 		case 'P':
 			sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ??????</b></td><td colspan="3" >'+ data.permitDate +'</td></tr>';
 			sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ?????????</b></td><td colspan="3" >'+ data.permitStaf +'</td></tr>';
 			break;
 		case 'R':
 			sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ??????</b></td><td colspan="3" >'+ data.permitDate +'</td></tr>';
 			sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ?????????</b></td><td colspan="3" >'+ data.permitStaf +'</td></tr>';
 			break;
 		}
 		
 		sOut += '<tr><td class="center-cell th-cell-gray"><b>????????????</b></td><td colspan="3" >'+ data.reqNotice +'</td></tr>';
 		sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ??????</b></td><td colspan="3" >'+ data.filePath +'</td></tr>';
 		sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ??????</b></td><td colspan="3" >'+ data.fileHash +'</td></tr>';
 		
 		if (data.fileId) {
 			sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ?????????</b></td><td colspan="3" >'+ data.fileId +'</td></tr>';
 		}
 		if (data.fileList) {
 			sOut += '<tr><td class="center-cell th-cell-gray"><b>?????? ??????</b></td><td colspan="3" >'+ data.fileList +'</td></tr>';
 		}
 		
 		sOut += '</table>';
 		return sOut;
 	}
 	
 	function setDataTable(){
 		if (jQuery().dataTable) {

			var export_filename = 'Filename';
			
			var table = jQuery('#table_request_info');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/admin/drm/decreq',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
				   		param.user_id = $('#filterUserId').val();
						param.user_name = $('#filterUserName').val();
						param.user_number = $('#filterUserNumber').val();
						param.user_duty = $('#filterUserDuty').val();
						param.user_rank = $('#filterUserRank').val();
						param.user_pc = $('#filterUserPCName').val();
						param.user_ip = $('#filterUserIPAddr').val();
						param.user_phone = $('#filterUserPhone').val();
						param.policy_permit = $('#filterPermit option:selected').val();	
						
						param.dept = getCheckedDept();
			        },
				        "beforeSend" : function(){
						jQuery('#preloader').show();
				        },
			        "dataSrc": function ( json ) {
						jQuery('#preloader').hide();
		                return json.data;
		            }
				},
				lengthMenu: [[20, 100, 99999], [20, 100, "??????"]],
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
	        				},
		    				columns: [2,3,4,	8,9,10,11,12,13],
	    	  				format: {
	  							body: function ( data, row, column, node) {
		  							 if (column === 5) {
	                                    var strStatus = $(node).text().trim();
	                                    if (strStatus != '????????????' && strStatus != '????????????') {
	                                        strStatus = '??????';
	                                    }	                                    
	                                    return strStatus;
	                                } else {
	                                    return data;    
	                                }

									
								}
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
					        },
							columns: [2,3,4,8,9,10,11,12,13],
	    	  				format: {
	  							body: function ( data, row, column, node) {
		  							 if (column === 6) {
		                                    var strStatus = $(node).text().trim();		                                    
		                                    if (strStatus != '????????????' && strStatus != '????????????') {
		                                        strStatus = '??????';
		                                    }		                                    
		                                    return strStatus;
		                                } else {
		                                    return data;    
		                                }
 								}
	      	  				}
						},
						customize: function ( win ) {
		                    $(win.document.body)
		                        .css( 'font-size', '15px' )
		 
		                    $(win.document.body).find( 'table' )
		                        .addClass( 'compact' )
		                        .css( 'font-size', 'inherit' );
		                }
					}
				],
		 		"serverSide" : true,
		 		"processing": true,
		 	    "ordering": true,
				"columns": [{
					data: "userNo",							
					"orderable": false	//????????????
				}, {
					data: "requestNo",
					"orderable": false	//????????????
				}, {
					data: "deptName",
					"orderable": false	//??????
				}, {
					data: "userId",
					"orderable": false	//?????????
				}, {
					data: "userName",
					"orderable": false	//??????
				}, {
					data: "userNumber",
					"orderable": false	//??????
				},{
					data: "duty",
					"orderable": false	//??????
				}, {
					data: "rank",
					"orderable": false	//??????
				}, {
					data: "phone",
					"orderable": false	//??????
				},{
					data: "ipAddr",
					"orderable": false	//IP
				}, {
					data: "reqClientTime",
					"orderable": false	//????????????
					,render : function (data,type,row) {
						var time = '';
						if (data != '') {
							time = new Date(data).format('yyyy-MM-dd hh:mm:ss');
						} 
						return data;
					}
				}, {
					data: "filePath",
					"orderable": false	
				}, {
					data: "permitState",
					"orderable": false	//????????????
					,render : function (data,type,row) {
						var tag = '';
						var state = data;
						if (state == 'W') {
							tag += '<button type="button" class="btn btn-success btn-xs" onclick="javascript:fn_permit_request_policy(' + row.requestNo +',\''+ row.userId +'\', \'' + row.ipAddr + '\');"><i class="fa fa-check" aria-hidden="true">&nbsp;??????</i></button>';
							tag +='<button type="button" class="btn btn-danger btn-xs" onclick="javascript:fn_reject_request_policy('+ row.requestNo +',\''+ row.userId +'\', \'' + row.ipAddr + '\');" ><i class="fa fa-remove" aria-hidden="true">&nbsp;?????????</i></button>'
						} else if (state == 'P') {
							tag += '<button type="button" class="btn btn-info btn-xs" onclick="javascript:requestPermitInfo(\''+ row.permitStaf  +'\', \'' + row.permitDate + '\')"><i class="fa fa-check" aria-hidden="true">&nbsp;????????????</i></button>';
						} else if (state == 'R') {
							tag += '<button type="button" class="btn btn-purple btn-xs" onclick="javascript:requestRejectInfo(\''+ row.permitStaf  +'\', \'' + row.permitDate + '\')"><i class="fa fa-remove" aria-hidden="true">&nbsp;????????????</i></button>';
						}
						
						return tag;
					}
				}, {
					data: "permitDate",
					"orderable": false	//????????????
				}, {
					data: "permitStaf",
					"orderable": false	//?????????
				}, {
					data: "fileHash",
					"orderable": false	
				}, {
					data: "fileId",
					"orderable": false
				}, {
					data: "fileList",
					"orderable": false	
				}, {
					data: "reqNotice",
					"orderable": false	
				}],
				// set the initial value
				"pageLength": 20,
				"iDisplayLength": 20,
				"pagingType": "bootstrap_full_number",
				"language": {
					"info": " _PAGES_ ????????? ???  _PAGE_ ????????? / ??? _TOTAL_ ?????????",
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
				"columnDefs": [{	
					"targets": [0],	//????????????
					"class":"center-cell add_detail_info",
					"render":function(data,type,row){
						return '<span class="datables-td-detail datatables-close"></span>';
					}
				}, {
					'targets': [1]	//????????????
					,"visible" : false
				}, {	
					"targets": [2]	//??????
					,"class":"center-cell"
				}, {	
					"targets": [3]	//?????????
					,"class":"center-cell"
				}, {	
					"targets": [4]	//??????
					,"class" : "center-cell"
				}, {	
					"targets": [5]	//??????
					,"class" : "center-cell"
				},{	
					"targets": [6]	//??????
					,"class" : "center-cell"
				}, {	
					"targets": [7]	//??????
					,"class" : "center-cell"
				}, {
					"targets": [8]	//?????????
					,"class" : "center-cell"
				}, {
					"targets": [9]	//IP
					,"class" : "center-cell"
				}, {
					"targets": [10]	//????????????
					,"class" : "center-cell"
				}, {
					"targets": [11]	//?????? ??????
					,"class" : "center-cell"
					,"defaultContent": ""
				}, {
					"targets": [12]	//????????????
					,"class" : "center-cell"
				}, {
					"targets": [13]	//????????????
					,"visible" : false	
				}, {
					"targets": [14]	//?????????
					,"visible" : false
				}, {
					"targets": [15]	//?????? ??????
					,"visible" : false
					,"defaultContent": ""
				}, {
					"targets": [16]	//?????? ?????????
					,"visible" : false
					,"defaultContent": ""
				}, {
					"targets": [17]	//?????? ??????
					,"visible" : false
					,"defaultContent": ""
				}, {
					"targets": [18]	//????????????
					,"class" : "center-cell"
					,"defaultContent": ""
				}],			
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
				}
			});
			
			function fnFormatDetails(oTable, nTr) {
				var aData = oTable.fnGetData(nTr);
				var sOut = getDetailTable(aData);
				return sOut;
			}
			
			var jTable = jQuery('#table_request_info');
			jTable.on('click', ' tbody td .datables-td-detail', function () {
				var nTr = jQuery(this).parents('tr')[0];
				if (table.fnIsOpen(nTr)) {
					/* This row is already open - close it */
					jQuery(this).addClass("datatables-close").removeClass("datatables-open");
					table.fnClose(nTr);
					initLayout();
				} else {	
					/* Open this row */
					jQuery(this).addClass("datatables-open").removeClass("datatables-close");
					table.fnOpen(nTr, fnFormatDetails(table, nTr), 'details');
					initLayout();
				}
			});
			
		}
 	}
 	
 	var ajaxCount = 2;
 	function initLayout() {
 		if (ajaxCount > 0) ajaxCount--;
 		if (ajaxCount == 0) {	
 			var treePanel = $('#panel-tree');
 			var listPanel = $('#panel-list');
 			var tree = $('#dept_tree');
 			var list = $('#panel-list').children('.panel-body');
 			
 	 		list.css('height','');
 	 		tree.css('height','');
 	 		
 			var height = Math.max(window.innerHeight -  $('#layout-container').offset().top - 20, $('#sideNav').height());
 			height = Math.max(height, listPanel.height());
 			height = Math.max(height, treePanel.height());
 			
			list.height(height);
 	 	 	tree.height(height - 20);
 			$('#layout-container').height(height);
 			$('#layout-container').layout().resizeAll();
 		}
 	}
 	
 	$(window).on('resize', function() {
 		initLayout();
 	});
 	
 	var layer = $('#layout-container').layout({ 
 		closable: false,
 		west__minWidth : 230,
 		center__minWidth : 500,
 		west__size : 230,
 	});
 	
	$(document).ready(function(){
		
		$(".select2theme").select2({
   			  minimumResultsForSearch: -1,
   			  dropdownAutoWidth : true
   		});

		jQuery('#preloader').hide();
     	setTree();

		$('#org_tree')
		.bind('ready.jstree', function(e, data) {
			setDataTable();
		})
    });
	
	$(document).ajaxComplete(function(){
		initLayout();
	});
    
</script>
	</body>
</html>