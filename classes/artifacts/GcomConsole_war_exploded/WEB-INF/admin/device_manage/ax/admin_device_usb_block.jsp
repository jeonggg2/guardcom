<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

<div id="content" class="dashboard padding-20">
	<div id="layout-container" style="width: 100%; height: 100%;">
		<div class="ui-layout-west">
			<div id="panel-tree" class="panel panel-default">
				<div class="panel-heading">
					<span class="title elipsis">
						<strong>조직도</strong> <!-- panel title -->
					</span>
				</div>

				<!-- panel content -->
				<div id="dept_tree" class="panel-body">
				</div>
				<!-- /panel content -->

			</div>
			<!-- /PANEL -->
		</div>

		<div class="ui-layout-center">
			<div id="panel-list" class="panel panel-default">
			
				<div class="panel-heading">
					<span class="title elipsis">
						<strong>USB연결 차단현황</strong> <!-- panel title -->
					</span>
				</div> 
				<div id="usb_block" class="tab-pane fade">
					<!-- panel content -->
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12">

								<!-- Standard button -->
								<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle(1,initLayout);"><i class="fa fa-filter" aria-hidden="true">&nbsp;검색필터</i></button>

								<!-- Info -->
								<button type="button" class="btn btn-info" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;재검색</i></button>
								
								
								<!-- Primary -->
								<button type="button" class="btn btn-primary pull-right" onclick="onClickExcelButton()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;내보내기</i></button>
								<!-- Success -->
								<button type="button" class="btn btn-success pull-right" onclick="onClickPrintButton()"><i class="fa fa-print" aria-hidden="true">&nbsp;인쇄</i></button>
								<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:700px;">
									<table id="user" class="table table-bordered">
										<tbody> 
											<tr>         
												<td width="15%">아이디</td>
												<td>
													<input type="text" name="filterUserId" id="filterUserId" value="" class="form-control required">
												</td>
												<td width="15%">이름</td>
												<td>
													<input type="text" name="filterUserName" id="filterUserName" value="" class="form-control required">
												</td>

											</tr>
											<tr>         

												<td width="15%">검색시작일</td>
												<td>
				<input type="text" class="form-control datepicker" id="filterStartDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
												</td>

												<td width="15%">검색종료일</td>
												<td>
				<input type="text" class="form-control datepicker" id="filterEndDate" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false">
												</td>
											</tr>																															
											<tr>  
												<td width="15%">직책</td>
												<td>
													<input type="text" name="filterUserDuty" id="filterUserDuty" value="" class="form-control required">
												</td>
												<td width="15%">계급</td>
												<td>
													<input type="text" name="filterUserRank" id="filterUserRank" value="" class="form-control required">
												</td>       
											</tr>	
											<tr>  
												<td width="15%">장치이름</td>
												<td>
													<input type="text" name="filterUserDevice" id="filterUserDevice" value="" class="form-control required">
												</td>
												<td width="15%">PC명</td>
												<td>
													<input type="text" name="filterUserPcName" id="filterUserPcName" value="" class="form-control required">
												</td>       
											</tr>
											<tr>  
												<td width="15%">장치속성</td>
												<td colspan="3">
													<input type="text" name="filterUserDeviceDetail" id="filterUserDeviceDetail" value="" class="form-control required">
												</td>

											</tr>																															
											
										</tbody>
									</table>	
									
									<button type="button" class="btn btn-success" onclick="jQuery('#pre-1').slideToggle(1,initLayout);">접기</button>
																		
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12" style="overflow: auto;">
								<table class="table table-bordered table-hover" id="table_usb_block" style="width:100%; min-width: 500px;">
									<thead>
										<tr>
											<th style="width:20px"></th>
											<th>부서</th>
											<th>아이디</th>
											<th>이름</th>
											<th>사번</th>
											<th>직책</th>
											<th>계급</th>														
											<th>IP</th>
											<th>MAC</th>
											<th>PC이름</th>
											<th>연결시간(서버)</th>
											<th>연결시간(PC)</th>
											<th>장치이름</th>
											<th>장치속성</th>
											<th>차단분류</th>
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
</div>
		
<script type="text/javascript">
	//라디오타입에 따라 컬럼 hide/show
	var setColumnType = function(cType){
		
		var datatable = $('#table_usb_block').dataTable().api();
		var aColumn = datatable.columns('.agentinfo' );
		var uColumn = datatable.columns('.userinfo' );
		if(cType == 1){
			uColumn.visible(true);
			aColumn.visible(false);			

 			var jTable = $('#table_usb_block').dataTable();;

//			var nsTr = $('tbody > td > .datables-td-detail').parents('tr')[0];
			var nsTr = $('#table_usb_block tr');
			for(var i = 0; i < nsTr.length; i++){
				var nTr = nsTr[i];
				jTable.fnClose(nTr);
			}
		}else if(cType == 2){
			uColumn.visible(false);
			aColumn.visible(true);	

			var nsTr = $('#table_usb_block tr td').find('span.datables-td-detail');
			nsTr.addClass("datatables-close").removeClass("datatables-open");
		}		
	}
	
 	function setTree(){
		$.ajax({      
	        type:"POST",  
	        url:'${context}/common/tree/dept',
	        async: false,
	        //data:{},
	        success:function(args){   
	            $("#dept_tree").html(args);      
	        },   
	        //beforeSend:showRequest,  
	        error:function(e){  
	            console.log(e.responseText);  
	        }  
	    }); 
	}
 	
 	function reloadTable(){
 		var datatable = $('#table_usb_block').dataTable().api();
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
 	
 	function fn_select_device_save(code) {
 		
 		vex.defaultOptions.className = 'vex-theme-os';
    	
   		vex.dialog.open({
   			message: '선택하신 해당 장치를 저장하고 사용자에게 적용 하시겠습니까?',
   			  buttons: [
   			    $.extend({}, vex.dialog.buttons.YES, {
   			      text: '확인'
   			  	}),
   			 	$.extend({}, vex.dialog.buttons.NO, {
  			      text: '취소'
  			  })],
   			  callback: function(data) {
		 	  	if (data) {
		 	  		excuteDeviceSave(code);
		 	    }
		 	  }
   				
   		})
 	}
 	
 	var excuteDeviceSave = function(code) {
 		
 		$.ajax({      
 		    type:"POST",  
 		    url:'${context}/admin/policy/device/save',
 		    async: false,
 		    data:{
 		    	code : code,
 		    	_ : $.now()
 		    },
 		    success:function(data){
 		    	if (data.returnCode == 'S') {
 		    		successAlert(data);
 		    	} else if(data.returnCode == 'ENA') {
 		    		failAlert('장치 허용에 실패했습니다. 정책정보에 장치는 추가되었으나 해당 사용자 Agent 정보가 존재하지 않습니다.');
 		    	} else if(data.returnCode == 'ENP') {
 		    		failAlert('장치 허용에 실패했습니다. 장치정보 추가 시 문제가 발생하였습니다.');
 		    	} else {
 		    		failAlert('정책 수정중 예기치 못한 오류가 발생하여 등록에 실패하였습니다.');
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
 	
 	var successAlert = function (data) {
 		vex.defaultOptions.className = 'vex-theme-os';
 		
 		var resultText = '';
 		
 		if (data.fail_cnt == 0) {
 			resultText = '장치 허용이 완료되었습니다.';
 		} 
 		
 		if (data.success_cnt == 0 && data.fail_cnt > 0) {
 			resultText = '장치정보가 추가 되었으나 해당 사용자의 정책정보가 존재 하지 않습니다.';
 		}
 		
 		if (data.success_cnt != 0 && data.fail_cnt != 0) {
 			resultText = '장치 허용이 완료되었습니다. 성공 : ' + data.success_cnt + ' 건, 실패 : ' + data.fail_cnt + ' 건';
 		}
 		
 		vex.dialog.open({
 			message: resultText,
 			  buttons: [
 			    $.extend({}, vex.dialog.buttons.YES, {
 			      text: '확인'
 			  })] 		    				
 		})
 		var axtbl = $('#table_usb_block').dataTable().api();
 		axtbl.ajax.reload();
 	}
 	
 	var failAlert = function (meg) {
 		vex.defaultOptions.className = 'vex-theme-os';
 		
 		vex.dialog.open({
 			message: meg,
 			  buttons: [
 			    $.extend({}, vex.dialog.buttons.YES, {
 			      text: '확인'
 			  })] 		    				
 		})
 	}
 	
 	function setDataTable(){
 		
 		if (jQuery().dataTable) {
		
			var export_filename = 'Filename';
			
			var table = jQuery('#table_usb_block');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/usbblocklist',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
						param.user_id = $('#filterUserId').val();
						param.user_name = $('#filterUserName').val();
						param.start_date = $('#filterStartDate').val();
						param.end_date = $('#filterEndDate').val();
						param.duty = $('#filterUserDuty').val();
						param.rank = $('#filterUserRank').val();
						param.device_name = $('#filterUserDevice').val();
						param.pc_name = $('#filterUserPcName').val();
						param.device_property = $('#filterUserDeviceDetail').val();
						
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
						    },
						    columns: [1,2,3,11,12,14]
						}
					},  					              
					{
						text: '<i class="fa fa-lg fa-clipboard">프린트</i>',
						extend: 'print',
						className: 'btn btn-xs btn-primary p-5 m-0 width-35 assets-export-btn export-print ttip hidden',
						exportOptions: {
						    modifier: {
						        search: 'applied',
						        order: 'applied'
						    },
							columns: [1,2,3,11,12,14]
						},
						customize: function ( win ) {
		                    $(win.document.body)
		                        .css( 'font-size', '10px' )
		 
		                    $(win.document.body).find( 'table' )
		                        .addClass( 'compact' )
		                        .css( 'font-size', 'inherit' );
		                }
					}
			    ],
		 		"serverSide" : true,
		 	    "ordering": true,
				"columns": [{
					data: "usbNo",							
					"orderable": false	//추가정보
				}, {
					data: "deptName",
					"orderable": false	//부서
				}, {
					data: "userId",
					"orderable": false	//아이디
				}, {
					data: "userName",
					"orderable": false	//이름
				}, {
					data: "userNo",
					"orderable": false	//번호
				}, {
					data: "duty",
					"orderable": false	//직책
				}, {
					data: "rank",
					"orderable": false	//계급
				}, {
					data: "ipAddr",
					"orderable": false	//IP
				}, {
					data: "macAddr",
					"orderable": false	//MAC
				}, {
					data: "pcName",
					"orderable": false	//PC이름
				}, {
					data: "connectServerTime",
					"orderable": false	//연결시간(서버)
				}, {
					data: "connectClientTime",
					"orderable": false	//연결시간(PC)
				}, {
					data: "deviceName",
					"orderable": false	//장치이름
				}, {
					data: "deviceProperty",
					"orderable": false	//장치속성
				}, {
					data: "notice",
					"orderable": false	//차단분류
				}],
				// set the initial value
				"pageLength": 20,
				"iDisplayLength": 20,
				"pagingType": "bootstrap_full_number",
				"language": {
					"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 개 로그",
					"infoEmpty": "검색된 데이터가 없습니다.",
					"zeroRecords" :"검색된 데이터가 없습니다.",
					"lengthMenu": "  _MENU_ 개",
					"paginate": {
						"previous":"Prev",
						"next": "Next",
						"last": "Last",
						"first": "First"
					},
					
				},
				"columnDefs": [
				{	
					"targets": [0],	//추가정보
					"class":"center-cell add_detail_info",
					"render":function(data,type,row){
						return '<span class="datables-td-detail datatables-close"></span>';
					}
				},         
				{  // set default column settings
					'targets': [1]	//부서
					,"class":"center-cell"
				}, {	
					"targets": [2]	//아이디
					,"class":"center-cell"
				}, {	
					"targets": [3]	//이름
					,"class":"center-cell"
				}, {	
					"targets": [4],	//번호
					"class":"center-cell"
				}, {	
					"targets": [5]	//직책
					,"class" : "center-cell"
				}, {	
					"targets": [6]	//계급
					,"class" : "center-cell"
				}, 
				{	
					"targets": [7]	//IP
					,"class" : "center-cell"
					//,"visible" : false
					,"render":function(data,type,row){
							if(data == ''){
								return '-'
							}else{
								return data;
							}
						}								
				}, {	
					"targets": [8]	//MAC
					,"class" : "center-cell"
					,"visible" : false
						,"render":function(data,type,row){
							if(data == ''){
								return '-'
							}else{
								return data;
							}
						}								
				}, {	
					"targets": [9]	//PC이름
					,"class" : "center-cell"
					,"visible" : false
						,"render":function(data,type,row){
							if(data == ''){
								return '-'
							}else{
								return data;
							}
						}								
				}, {	
					"targets": [10]	//서버연결시간
					,"class" : "center-cell"
					,"visible" : false
				}, {	
					"targets": [11]	//PC연결시간
					,"class" : "center-cell"
				}, {	
					"targets": [12]	//장치이름
					,"class" : "center-cell"
				}, {	
					"targets": [13]	//장치속성
					,"class" : "center-cell"
					,"visible" : false	
				}, {	
					"targets": [14]	//차단분류
				,"class" : "center-cell"
			}],						
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
//			        $('#table_usb_block').colResizable({liveDrag:true});

				}
			});
			
			function fnFormatDetails(oTable, nTr) {
				var aData = oTable.fnGetData(nTr);
				var sOut = '<table class="table table-bordered" style="width:100%; min-width: 500px;">';
				sOut += '<tr><td class="center-cell th-cell-gray">MAC</td><td>' + aData.macAddr + '</td>';
				sOut += '<td class="center-cell th-cell-gray">PC명</td><td>' + aData.pcName + '</td>';
				sOut += '<td class="center-cell th-cell-gray">서버연결시간</td><td>' + aData.connectServerTime ;
				sOut += '<button type="button" class="btn btn-xs btn-blue pull-right" onclick="fn_select_device_save(\'' + aData.usbNo +'\')" style="margin:0;"><i class="fa fa-check" aria-hidden="true"></i>장치관리목록추가</button></td></tr>';
				sOut += '<tr><td class="center-cell th-cell-gray">장치<br/>속성</td><td colspan="5">' + aData.deviceProperty + '</td></tr>';
										
				sOut += '</table>';

				return sOut;
			}
			
			var jTable = jQuery('#table_usb_block');
			jTable.on('click', ' tbody td .datables-td-detail', function () {
				var nTr = jQuery(this).parents('tr')[0];
				var row = jTable
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
 	
 	function initLayout() {
		if ($('#layout-container').length == 0) return;  
 		var hei = $('#panel-list').height();
 		$('#layout-container').height(hei);
 		$('#dept_tree').height(hei);
 		$('#layout-container').layout().resizeAll();
 	}
 	
 	var layer = $('#layout-container').layout({ 
 		closable: false,
 		west__minWidth : 230,
 		center__minWidth : 500,
 		west__size : 230,
 	});
 	
	$(document).ready(function(){
		
		$(".select2theme").select2({
   			  minimumResultsForSearch: -1,
   			  dropdownAutoWidth : true,
   			  width: 'auto'
   		});

		
     	setTree();
     	
		$('#org_tree')
		.bind('ready.jstree', function(e, data) {
			console.log('dddddddddddd');
			setDataTable();
		})
				
		jQuery('#preloader').hide();
    });
	
	$(document).ajaxComplete(function(){
		initLayout();
	});
	
</script>