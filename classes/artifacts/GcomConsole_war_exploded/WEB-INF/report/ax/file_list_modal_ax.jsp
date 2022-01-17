<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="fileListModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:940px;">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">파일목록</h4>
			</div>

			<!-- Modal Body -->
			<div class="modal-body">
			
<%--  				<input type="text" value="${no}" />
 --%> 			
				<div class="row">
					<div class="col-md-12">
						<table class="table table-striped table-bordered table-hover" id="table_filelist" style="width: 100%;">
							<thead>
								<tr>
									<th>번호</th>
									<th>파일명</th>
									<th>파일사이즈</th>
									<th>옵셋</th>
									<th>다운로드</th>
								</tr>
							</thead>				
							<tbody>
							</tbody>
						</table>									
					</div>
				</div>
			</div>

			<!-- Modal Footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
			</div>

		</div>
	</div>
</div>

<script type="text/javascript">

function fn_download(size, offset, fileName){
	
	var formData = new FormData();
	formData.append('file_id', '${file_id}');
	formData.append('file_name', encodeURI(fileName));
	formData.append('file_offset', offset);
	formData.append('file_size', size);
	
	window.open('${context}/ax/file/download?&file_id=${file_id}&file_offset=' + offset + '&file_size=' + size + '&file_name=' + encodeURI(fileName));

	
/* 	$.ajax({
		  url: '/ax/file/download',
		  type: 'POST',
		  processData: false, // important
		  contentType: false, // important
		  dataType : 'json',
		  data: formData,
		  success:function(data){
		  },   
		  error:function(e){
		     alert("파일 다운로드에 실패하였습니다.");
		     console.log(e.responseText);  
		   }  
		})
 */}

if (jQuery().dataTable) {
	var table = jQuery('#table_filelist');

	table.dataTable({
		"dom": '<"row view-filter"<"col-sm-12"<"pull-left" iB ><"pull-right" l><"clearfix">>>tr<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
		dom: 't',
		"ajax" : {
			"url":'${context}/ax/file/list',
		   	"type":'POST',
		   	"dataSrc" : "data",
		   	"data" :  function(param) {
				param.type = '${type}';
				param.no = '${no}';
		   	},
		        "beforeSend" : function(){
				jQuery('#preloader').show();
		        },
	        "dataSrc": function ( json ) {
				jQuery('#preloader').hide();
                return json.data;
            }   
		},
		tableTools: {
	          "sSwfPath": plugin_path + "datatables/extensions/Buttons/js/swf/flashExport.swf"
	        },
 	    "ordering": true,
		"columns": [{
			data: "index",
			"orderable": false	
		}, {
			data: "fileName",
			"orderable": false	
		},{
			data: "fileSize",
			"orderable": false	
		},{
			data: "fileOffset",
			"orderable": false	
		},{
			data: "index",
			"orderable": false	
		}],
		// set the initial value
		"pageLength": 20,
		"iDisplayLength": 20,
		"pagingType": "bootstrap_full_number",
		"language": {
			"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 관리자",
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
			"targets": [0],	//번호
			"class":"center-cell"
		},         
		{  // set default column settings
			'targets': [1]	//파일명
			,"class":"center-cell"
		},{  // set default column settings
			'targets': [2]	//파일사이즈
			,"class":"center-cell"
		},{  // set default column settings
			'targets': [3]	//옵셋
			,"class":"center-cell"
			,"visible" : false

		},{  // set default column settings
			'targets': [4]	//다운로드
			,"class":"center-cell"
			,"render": function(data,type,row){
				if('${file_id}' == ''){
					return '<i title="파일없음" class="fa fa-ban" aria-hidden="true" onclick="">'
					
				}else{
					return '<i title="다운로드" class="fa fa-download" aria-hidden="true" onclick="fn_download('+ row.fileSize +','+ row.fileOffset +',\''+ encodeURI(row.fileName) +'\')">'
					
				}
			}

		}

	],						
		"initComplete": function( settings, json ) {
		}
	});
}

</script>