<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

<div id="usb_policy" class="tab-pane fade in active">
	<!-- panel content -->
	<div class="panel-body">
		<div class="row">
			<div class="col-md-12">

				<!-- Standard button -->
				<button type="button" class="btn btn-default" onclick="jQuery('#pre-1').slideToggle(1,initLayout);"><i class="fa fa-filter" aria-hidden="true">&nbsp;검색필터</i></button>

				<!-- Info -->
				<button type="button" class="btn btn-info" onclick="reloadTable()"><i class="fa fa-repeat" aria-hidden="true">&nbsp;재검색</i></button>
				
				
				<!-- Primary -->
				<!-- <button type="button" class="btn btn-primary pull-right" onclick="onClickExcelButton()"><i class="fa fa-file-excel" aria-hidden="true">&nbsp;내보내기</i></button> -->
				<!-- Success -->
				<!-- <button type="button" class="btn btn-success pull-right" onclick="onClickPrintButton()"><i class="fa fa-print" aria-hidden="true">&nbsp;인쇄</i></button> -->
				<!-- Register -->
				<button type="button" class="btn btn-green pull-right" onclick="fn_open_reg_usb_popup(0)"><i class="fa fa-check" aria-hidden="true"></i> 정책등록</button>
				<div id="pre-1" class="margin-top-10 margin-bottom-10 text-left noradius text-danger softhide" style="width:400px;">
					<table id="user" class="table table-bordered">
						<tbody> 
							<tr>         
								<td width="35%">장치이름</td>
								<td>
									<input type="text" name="filterDeviceName" id="filterDeviceName" value="" class="form-control required">
								</td>
							</tr>
							<tr>         
								<td width="35%">시리얼번호</td>
								<td>
									<input type="text" name="filterSerialNumber" id="filterSerialNumber" value="" class="form-control required">
								</td>
							</tr>
							<tr>         
								<td width="35%">비고</td>
								<td>
									<input type="text" name="filterDesc" id="filterDesc" value="" class="form-control required">
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
				<table class="table table-bordered table-hover x-scroll-table" id="table_usb_policy" style="width:100%; min-width: 500px;">
					<thead>
						<tr>
							<th>No</th>
							<th>장치이름</th>
							<th>vid</th>
							<th>pid</th>
							<th>시리얼번호</th>
							<th>class</th>
							<th>subclass</th>
							<th>프로토콜</th>
							<th>비고</th>
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
			
<div id="reg_usb_popup_div"></div>
<script>

 	function onClickPrintButton(){
 		var $buttons = $('.export-print');
 		$buttons.click();
 	}
 	
 	function onClickExcelButton(){
		console.log('excel')
 		var $buttons = $('.export-csv');
 		$buttons.click();
 		
 	}
 	
 // 검색 버튼 클릭 시
 	function reloadTable(){
 		var datatable = $('#table_usb_policy').dataTable().api();
		datatable.ajax.reload();   	
 	
 	}
 	
 	function fn_open_reg_usb_popup(code) {
 		$.ajax({      
		    type:"POST",  
		    url:'${context}/admin/policy/usb/register',
		    async: false,
		    data:{ 
		    	code : code,
		    	_ : $.now()
		    },
		    success:function(data){
		    	$("#reg_usb_popup_div").html(data);
	            $('#modalPolicyRegUsb').modal('show');
		    },   
		    error:function(e){  
		        console.log(e.responseText);  
		    }  
		});
 	}
 	
 	function fn_delete_usb_policy_item(code) {
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
		    url:'${context}/admin/policy/usb/delete',
		    async: false,
		    data:{ 
		    	code : code,
		    	_ : $.now()
		    },
		    success:function(data){
		    	
		    	if (data.returnCode == 'S') {
		    		var datatable = $('#table_usb_policy').dataTable().api();
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
 	function initLayout() {
		if ($('#layout-container').length == 0) return;  
 		var hei = $('#panel-list').height();
 		$('#layout-container').height(hei);
 		$('#dept_tree').height(hei);
 		$('#layout-container').layout().resizeAll();
 	}
	$(document).ready(function(){
		
		$(".select2theme").select2({
   			  minimumResultsForSearch: -1,
   			  dropdownAutoWidth : true,
   			  width: 'auto'
   		});

		if (jQuery().dataTable) {
			var export_filename = 'Filename';
			
			var table = jQuery('#table_usb_policy');
			table.dataTable({
				"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" ><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
				//dom: 'Bfrtip',
				"ajax" : {
					"url":'${context}/ax/unauthusb/list',
				   	"type":'POST',
				   	"dataSrc" : "data",
				   	"data" :  function(param) {
						param.name = $('#filterDeviceName').val();
						param.serial = $('#filterSerialNumber').val();
						param.desc = $('#filterDesc').val();
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
				              },  					              {
			                  text: '<i class="fa fa-lg fa-clipboard">프린트</i>',
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
		 	    "ordering": true,
				"columns": [{
					data: "usbId",							
					"orderable": false	// ID
					,render : function(data, type, row, a){
						var paging = a.settings._iDisplayStart;
						return paging + a.row + 1;
						
					}
				}, {
					data: "name",
					"orderable": false	//장치이름
				}, {
					data: "vid",
					"orderable": false	//vid
				}, {
					data: "pid",
					"orderable": false	//pid
				}, {
					data: "serialNumber",
					"orderable": false	//시리얼번호
				}, {
					data: "mainclass",
					"orderable": false	//vid
				}, {
					data: "subclass",
					"orderable": false	//pid
				}, {
					data: "protocol",
					"orderable": false	//시리얼번호
				}, {
					data: "description",
					"orderable": false	//설명
				}, {
					data: "allow",
					"orderable": false	//사용여부
					,render : function(data,type,row) {
						return data == 1? '사용' : '사용안함';
					}
				}, {                                   
					data: "usbId"	//삭제
					,render : function(data,type,row) {
						return '<button type="button" id="btnDeletePolicy" class="btn btn-xs btn-danger" onclick="javascript:fn_delete_usb_policy_item('+ data +');"><i class="fa fa-trash" aria-hidden="true"></i>정책삭제</button>';
					}
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
					"class":"center-cell"
				},         
				{  // set default column settings
					'targets': [1]	// 장치이름
					,"class":"center-cell"
				}, {	
					"targets": [2]	//vid
					,"class":"center-cell"
				}, {	
					"targets": [3]	//pid
					,"class":"center-cell"
				}, {	
					"targets": [4],	//시리얼
					"class":"center-cell"
				}, {	
					"targets": [5]	//vid
					,"class":"center-cell"
				}, {	
					"targets": [6]	//pid
					,"class":"center-cell"
				}, {	
					"targets": [7],	//시리얼
					"class":"center-cell"
				}, {	
					"targets": [8]	//비고
					,"class" : "center-cell"
				}, {	
					"targets": [9]	//사용여부
					,"class" : "center-cell"
				}, {	
					"targets": [10]	//삭제
					,"class" : "center-cell"
				}],						
				"initComplete": function( settings, json ) {
					$('.export-print').hide();
//			        $('#table_usb_policy').colResizable({liveDrag:true});
				}
			});
			
			var ctbl = $('#table_usb_policy').DataTable();
			ctbl.on( 'click', 'td', function () {
				var data = ctbl.row( $(this).parent() ).data();
				
				if($(this).index() != 10) {
					fn_open_reg_usb_popup(data.usbId);
				}
				
			});
			
		}
		jQuery('#preloader').hide();
    });
</script>