<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gcom.user.model.UserInfoModel"%>
<% 
	UserInfoModel data = (UserInfoModel)request.getAttribute("userInfo");
	String name = data.getName();
	String phone = data.getPhone();
%>
<!doctype html>
<html lang="utf-8">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom User</title>
	
		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />
	
		<!-- CORE CSS -->
		<link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- THEME CSS -->
		<link href="${context}/assets/css/user_essentials.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/user_layout.css" rel="stylesheet" type="text/css" />
		
		<!-- PAGE LEVEL STYLE -->
		<link href="${context}/assets/css/user_header.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/color_scheme/user_green.css" rel="stylesheet" type="text/css" id="color_scheme" />
		
		<!-- Alert -->
		<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
		<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />
		
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

	</head>
	<body class="smoothscroll enable-animation">
		<jsp:include page="/WEB-INF/common/user_header.jsp" flush="false" />
		
		<!-- Contact Form -->
		<section>
			<div class="container">
			<!-- FORM -->
				<div class="col-md-12 col-sm-12">
					<div class="col-md-6 col-sm-6" style="padding-left: 0;">
						<h3><i class="fa fa-paper-plane" aria-hidden="true"></i> 문의 등록</h3>
					</div>
					<!-- <div class="col-md-6 col-sm-6" style="text-align:right;">
						<button type="button" class="btn btn-primary" onClick="location.href='#contact_list'"><i class="fa fa-list"></i>등록문의확인</button>
					</div> -->
				</div>
				<div class="col-md-12 col-sm-12">
					<!-- Alert Success -->
					<div id="alert_success" class="alert alert-success margin-bottom-30">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<strong>Thank You!</strong> Your message successfully sent!
					</div>
					<!-- /Alert Success -->


					<!-- Alert Failed -->
					<div id="alert_failed" class="alert alert-danger margin-bottom-30">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<strong>[SMTP] Error!</strong> Internal server error!
					</div>
					<!-- /Alert Failed -->


					<!-- Alert Mandatory -->
					<div id="alert_mandatory" class="alert alert-danger margin-bottom-30">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<strong>Sorry!</strong> You need to complete all mandatory (*) fields!
					</div>
					<!-- /Alert Mandatory -->


					<form id="frmContact" action="${context}/contact/save" method="post">
						<fieldset>
							<!-- <input type="hidden" name="action" value="contact_send" /> -->

							<div class="row">
								<div class="form-group">
									<div class="col-md-12">
										<label for="contact:name">성명 *</label>
										<input type="text" value="<%= name %>" class="form-control" name="name" id="name" disabled />
									</div>
									<div class="col-md-12">
										<label for="contact:phone">핸드폰 *</label>
										<input type="text" value="<%= phone %>" class="form-control" name="phone" id="phone" disabled />
									</div>
									<div class="col-md-12">
										<label for="contact:email">이메일 </label>
										<input type="email" value="" class="form-control" name="user_mail" id="user_mail" placeholder="E-Mail" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<div class="col-md-8">
										<label for="contact:subject">제목 *</label>
										<input required type="text" value="" class="form-control" name="contact_subject" id="contact_subject" placeholder="제목">
									</div>
									<div class="col-md-4">
										<label for="contact_department">문의구분</label>
										<select class="form-control pointer" name="contact_type" id="contact_type">
											<option value="1">단순문의</option>
											<option value="2">서비스문의</option>
											<option value="3">버그문의</option>
										</select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<div class="col-md-12">
										<label for="contact:message">문의내용 *</label>
										<textarea required maxlength="10000" rows="8" class="form-control" name="contact_body" id="contact_body"></textarea>
									</div>
								</div>
							</div>
							<!--
							<div class="row">
								<div class="form-group">
									<div class="col-md-12">
										<label for="contact:attachment">File Attachment</label>

										/* custom file upload */
										<input class="custom-file-upload" type="file" id="file" name="contact[attachment]" id="contact:attachment" data-btn-text="Select a File" />
										<small class="text-muted block">Max file size: 10Mb (zip/pdf/jpg/png)</small>

									</div>
								</div>
							</div> -->

						</fieldset>
						<div class="row">
							<div class="col-md-12">
								<button id="btnContactSave" class="btn btn-primary"><i class="fa fa-check"></i> 문의등록</button>
							</div>
						</div>
					</form>
				</div>
			<!-- /FORM -->
			</div>
		</section>
		<!-- /Contact Form -->

		<!-- Contact Table-->
		<section id="contact_list" style="padding:20px 0;">
			<div class="container">

				<h4>나의 등록 문의</h4>
				<table class="table table-bordered table-hover" id="contact_table">
					<col width="20px;">
					<col width="80px;">
					<col width="120px;">
					<col>
					<col width="150px;">
					<col width="80px;">			
					<thead>
						<tr>
							<th></th>
							<th>문의코드</th>
							<th>문의구분</th>
							<th>제목</th>
							<th>등록일</th>
							<th>답변여부</th>
							<th>답변등록자</th>
							<th>답변등록일</th>
							<th>답변내용</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</section>
		<!-- /Contact Table-->
		
		<!-- Ajax Notice Detail Popup -->
		<div id="contact_view_div"></div>
		<!-- /Ajax Notice Detail Popup -->
		
		<jsp:include page="/WEB-INF/common/user_footer.jsp" flush="false" />
	
		<!-- PAGE LEVEL SCRIPTS -->
		<script type="text/javascript" src="${context}/assets/plugins/select2/js/select2.full.min.js"></script>
		<script type="text/javascript" src="${context}/assets/plugins/jquery/jquery.form.js" ></script>
		
		<script type="text/javascript">
		
		function fn_reg_contact_proc() {
			
			var option = {
			        url:       		"${context}/contact/save",
			    	type:      		"post",       
			    	success:     	fn_contact_callback,
			    	fail:			callbackFail,
			    	cache: 			false,
			        resetForm: 		true,
			};
			
			$("#frmContact").ajaxSubmit(option);			
		}
		
		function callbackFail(){}
		
		function fn_contact_callback(data){
			vex.defaultOptions.className = 'vex-theme-os';
			
			if(data.returnCode== "S"){
				
				vex.dialog.open({
					message: '문의가 등록 되었습니다.',
					buttons: [
				    	$.extend({}, vex.dialog.buttons.YES, {
				     	text: '확인'
				  	})]
				});
				
				var datatable = $('#contact_table').dataTable().api();
				datatable.ajax.reload();
			}else{
				switch (data.returnCode){
				  case "E":
					  vex.dialog.open({
							message: '문의 등록에 실패 하였습니다.',
							buttons: [
						    	$.extend({}, vex.dialog.buttons.YES, {
						     	text: '확인'
						  	})]
						});
				    break;
				   default: break;
				}
			}
			
		}
		
		function fn_open_contact_view(contactId) {
			$.ajax({      
		        type:"POST",  
		        url:'${context}/contact/view',
		        async: false,
		        data:{
		        	contactId : contactId,
		        	_ : $.now()
		        },
		        success:function(args){
		            $("#contact_view_div").html(args);
		            $("#modalContactView").modal('show');
		            
		        },   
		        //beforeSend:showRequest,  
		        error:function(e){
		            console.log(e.responseText);  
		        }  
		    });
		}
							
		$(document).ready(function(){
	     	$(document).ready(function() {
	    		  $(".select2theme").select2({
	    			  minimumResultsForSearch: -1,
	    			  dropdownAutoWidth : true,
	    			  width: 'auto'
	    		  });
	    		  
	    		  $("#btnContactSave").on("click" , function(e){
						e.preventDefault();
						fn_reg_contact_proc();
					});
	    		  
	    	}); 
	        
	     	loadScript(plugin_path + "datatables/media/js/jquery.dataTables.min.js", function(){
     		loadScript(plugin_path + "datatables/media/js/dataTables.bootstrap.min.js", function(){
     		loadScript(plugin_path + "datatables/extensions/Buttons/js/dataTables.buttons.min.js", function(){
     		loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.print.min.js", function(){
     		loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.html5.min.js", function(){
     		loadScript(plugin_path + "datatables/extensions/Buttons/js/buttons.jqueryui.min.js", function(){
     		 
     						if (jQuery().dataTable) {
     							
     							var table = jQuery('#contact_table');
	     							table.dataTable({
	     								"dom": '<"row view-filter"<"col-sm-12"<"pull-right"><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-left" i ><"pull-right"p>>>',
     									"ajax" : {
     									"url":'${context}/ax/contact/list',
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
     						 		"serverSide" : true,
     						 		"columns": [{
     									data: "contactId"			//추가정보
     								}, {
     									data: "contactId"			//문의코드
     								}, {
     									data: "contactTypeName"		//문의구분
     								}, {
     									data: "contactTitle"		//제목
     								}, {
     									data: "regDt"				//등록일
     								}, {
     									data: "commentYN"			//답변여부
     								}, {
     									data: "commnetRegStafId"	//답변등록자
     								}, {
     									data: "commentRegDt"		//답변등록일
     								}, {
     									data: "replyContent"		//답변내용
     								}],
     						 		"language": {
	     								"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 문의사항",
	     								"infoEmpty":      "검색된 데이터가 없습니다.",
	     								"lengthMenu": "  _MENU_ 개",
	     								"paginate": {
	     									"previous":"Prev",
	     									"next": "Next",
	     									"last": "Last",
	     									"first": "First"
	     								}
	     							},
	     							"pageLength": 10,
     						 	  	"columnDefs": [
										{	
											"targets": [0],	//추가정보
											"class":"center-cell add_detail_info",
											"render":function(data,type,row){
												return '<span class="datables-td-detail datatables-close"></span>';
											}
										},         
										{  // set default column settings
											'targets': [1]	//문의코드
											,"class":"center-cell"
										}, {	
											"targets": [2]	//문의구분
											,"class":"center-cell"
										}, {	
											"targets": [3]	//제목
										
										}, {	
											"targets": [4],	//등록일
											"class":"center-cell"
										}, {	
											"targets": [5]	//답변여부
											,"class" : "center-cell"
											,"render":function(data,type,row){
												return data=="Y"?'<i class="fa fa-pencil">':'' ;
											}
										}, {	
											"targets": [6]	//답변등록자
											,"visible":false
										}, {	
											"targets": [7]	//답변등록일
											,"visible":false
										}, {	
											"targets": [8]	//답변내용
											,"visible":false
										}]
     							});
     							
     							function fnFormatDetails(oTable, nTr) {
     								var aData = oTable.fnGetData(nTr);

     								var reFlag = aData.commentYN;
     								var sOut = '<table class="table fixed" style="width:100%; overflow:auto; margin:0;">';

     								if( reFlag == 'Y' ) {
     									sOut += '<tr><td class="comment-cell">답변 : ';
     									sOut += '<i class="fa fa-clock-o"></i> '+ aData.commentRegDt + '&nbsp;&nbsp;&nbsp;&nbsp;';
     									sOut += '<i class="fa fa-user"></i> '+ aData.commnetRegStafId ;
     									sOut += '</td></tr>';
     									sOut += '<tr><td class="comment-cell" style="padding:20px 10px;">'+ aData.replyContent +'</td></tr>';
     								} else {
     									sOut += '<tr><td class="comment-cell" style="padding:20px 10px;">등록된 답변이 없습니다.</td></tr>';
     								}
     								
     								sOut += '</table>';
     								
     								return sOut;
     							}
     							
     							var jTable = jQuery('#contact_table');
     							jTable.on('click', ' tbody td .datables-td-detail', function () {
     								var nTr = jQuery(this).parents('tr')[0];
     								if (table.fnIsOpen(nTr)) {
     									/* This row is already open - close it */
     									jQuery(this).addClass("datatables-close").removeClass("datatables-open");
     									table.fnClose(nTr);
     								} else {
     									/* Open this row */
     									jQuery(this).addClass("datatables-open").removeClass("datatables-close");
     									table.fnOpen(nTr, fnFormatDetails(table, nTr), 'details');
     								}
     							});
     							
     							var con = $('#contact_table').DataTable();
     							con.on( 'click', 'td', function () {
     								var data = con.row( $(this).parent() ).data();
     								
     								if($(this).index() == 3) {	// 제목 클릭
     									fn_open_contact_view(data.contactId);
     								}
     							});
     						}
     					});
     					});
     					});
     				});
     			}); 
     		});	       
		});
		</script>
	</body>
</html>