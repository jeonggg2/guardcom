<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
		<link href="${context}/assets/css/simplePagination/simplePagination.css" rel="stylesheet" type="text/css" />
		
	</head>
	<body class="smoothscroll enable-animation">
		<jsp:include page="/WEB-INF/common/user_header.jsp" flush="false" />
		
		<!-- Notice Table -->
		<!-- -->
		<section style="min-height:700px;">
			<div class="container">

				<h3><i class="fa fa-table"></i> 공지사항</h3>
				<div class="table-responsive">
					<div>
						<a href="javascript:fn_txt_search();" class="btn btn-primary pull-right"  style="margin-top: 0px;"><i class="fa fa-check"></i> 검색</a>
						<input type="text" class="form-control pull-right" id="att_search_text" name="att_search_text" placeholder="검색어를 입력해주세요." style="width:200px;" value="" />
						<select class="form-control pull-right" id="sel_search_type" name="sel_search_type" style="width:100px;">
							<option value="1">제목</option>
							<option value="2">등록자</option>
						</select> 
					</div>
					<!-- Ajax Notice Table -->
					<div id="notice_table_div"></div>
					<!-- /Ajax Notice Table -->
					
					<!-- PAGINATION -->
					<div class="text-center margin-top-20">
						<div class="pagination-page" id="pagination-page"></div>
					</div>
					<!-- /PAGINATION -->
					
				</div>
				
				<!-- Ajax Notice Detail Popup -->
				<div id="notice_view_div"></div>
				<!-- /Ajax Notice Detail Popup -->
			</div>
		</section>
		<!-- /Notice Table -->
		
		<jsp:include page="/WEB-INF/common/user_footer.jsp" flush="false" />
		
		<!-- PAGE LEVEL SCRIPT -->
		<script type="text/javascript" src="${context}/assets/plugins/simplePagination/jquery.simplePagination.js"></script>
		
		<script type="text/javascript">
			var tableRowCount 	= 20;
			var selectedPage 	= 1;
			var bPageLoad		= false;
		
			$(document).ready(function(){
				
				$('.pagination-page').pagination({
					items: 0,
					itemsOnPage: tableRowCount,
					cssStyle: 'green-theme',
					prevText:"Previous",
					nextText:"Next",
					onPageClick: function(page){
						selectPage(page);
					},
				});
				
				fn_notice_list();
			});
			
			function selectPage(page){
				fn_search(page);
				selectedPage	= page;
			}
			
			function fn_notice_list() {
				if ( !bPageLoad ){
					fn_search(1);
					bPageLoad = true;
				}
			}
			
			function fn_txt_search(){
				fn_search(1);
			}
			
			function fn_search(page){
				var search_type = $('#sel_search_type option:selected').val();
				var search_text = $('#att_search_text').val();
				
				var start_idx		= (page - 1) * tableRowCount;
				var end_idx			= tableRowCount;
				
				$.ajax({      
			        type:"POST",  
			        url:'${context}/ax/user/notice/list',
			        async: false,
			        data:{
			        	search_type : search_type,
			        	search_text : search_text,
			        	start_idx : start_idx,
			        	end_idx : end_idx,
			        	_ : $.now()
			        },
			        success:function(args){
			            $("#notice_table_div").html(args);
			            
			            $('#pagination-page').pagination('updateItems',$('#att_list_cnt').val());
			            $('#pagination-page').pagination('drawPage', page);
			        },   
			        //beforeSend:showRequest,  
			        error:function(e){
			            console.log(e.responseText);  
			        }  
			    });
			}
			
			function fn_bbs_detail(bbs_id, file_yn, file_id) {
				
				$.ajax({      
			        type:"POST",  
			        url:'${context}/notice/view',
			        async: false,
			        data:{
			        	bbs_id : bbs_id,
			        	file_yn : file_yn,
			        	file_id : file_id,
			        	_ : $.now()
			        },
			        success:function(args){
			            $("#notice_view_div").html(args);
			            $("#modalNoticeView").modal('show')
			            
			        },   
			        //beforeSend:showRequest,  
			        error:function(e){
			            console.log(e.responseText);  
			        }  
			    });
			}
			
		</script>
	</body>
</html>