<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="utf-8">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>GuardCom Report</title>

		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />

		<!-- CORE CSS -->
		<link href="${context}/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- THEME CSS -->
		<link href="${context}/assets/css/essentials.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/layout.css" rel="stylesheet" type="text/css" />
		<link href="${context}/assets/css/color_scheme/green.css" rel="stylesheet" type="text/css" id="color_scheme" />
		<link href="${context}/assets/plugins/jstree/themes/default/style.min.css" rel="stylesheet" type="text/css" id="color_scheme" />

	</head>
	<body>
		<!-- WRAPPER -->
		<div id="wrapper" class="clearfix">

			<!-- 
				ASIDE 
				Keep it outside of #wrapper (responsive purpose)
			-->
			<jsp:include page="/WEB-INF/common/left_menu.jsp" flush="false" />
			
			<!-- /ASIDE -->
			<!-- HEADER -->
			<jsp:include page="/WEB-INF/common/top_navi.jsp" flush="false" />			
			<!-- /HEADER -->

			<!-- 
				MIDDLE 
			-->
			<section id="middle">
			
				<!-- page title -->
				<header id="page-header">
					<h1>계정정보변경</h1>
				</header>
				<!-- /page title -->
			
				<div id="content" class="dashboard padding-20">
					<div class="row">
						<div class="col-md-12">
							<div id="panel-2" class="panel panel-default">
						
								<div class="panel-heading">
									<span class="title elipsis">
										<strong>계정정보변경</strong> <!-- panel title -->
									</span>
								</div>
	
								<!-- panel content -->
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12" style="max-width: 700px">
											
											<label>아이디</label>
											<div class="fancy-form"><!-- input -->
												<i class="fa fa-user"></i>
												<input type="text" class="form-control" value="Admin" readonly="readonly">
											</div>
											<br />

											<label>비밀번호</label>
											<div class="fancy-form"><!-- input -->
												<i class="fa fa-unlock-alt"></i>
												<input type="text" class="form-control" placeholder="현재 비밀번호를 입력해주세요.">
											</div>
											<br />

											<label>새비밀번호</label>
											<div class="fancy-form"><!-- input -->
												<i class="fa fa-unlock"></i>
												<input type="text" class="form-control" placeholder="변경하실 비밀번호를 입력해주세요.">
											</div>
											<br />

											<label>새비밀번호 재입력</label>
											<div class="fancy-form"><!-- input -->
												<i class="fa fa-unlock"></i>
												<input type="text" class="form-control" placeholder="변경하실 비밀번호를 재입력해주세요.">
											</div>
											<br />
											
										
											<label>IP주소1</label>
											<input type="text" class="form-control masked" data-format="999.999.999.999" data-placeholder="_" placeholder="192.168.1.1">				
											<br />

											<label>IP주소2</label>
											<input type="text" class="form-control masked" data-format="999.999.999.999" data-placeholder="_" placeholder="192.168.1.1">
				
											<br />
											<button type="button" class="btn btn-success center-block">
												<i class="fa fa-cog fa-lg" aria-hidden="true"></i>
												수정
											</button>
																					
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
	
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = '${context}/assets/plugins/';</script>
		
		
<script>

	//사용자정보/에이전트정보 뷰타입변경
	var currentType=1;
	function onTypeCheck(radioType){
		if(currentType != radioType.value){
			currentType = radioType.value;
			setColumnType(currentType);
		}
	}
	
	//라디오타입에 따라 컬럼 hide/show
	function setColumnType(cType){
		
		var datatable = $('#table_userinfo').dataTable().api();
		var aColumn = datatable.columns('.agentinfo' );
		var uColumn = datatable.columns('.userinfo' );
		if(cType == 1){
			uColumn.visible(true);
			aColumn.visible(false);			
		}else if(cType == 2){
			uColumn.visible(false);
			aColumn.visible(true);			
		}		
	}
	
	var getPageinfoHash = function(){
		//페이지번호
		//라디오타입
		//검색필터정보
	};
	
	var getFilterInfo = function(){
		
	};

    $(document).ready(function(){
     	$(document).ready(function() {
    		  $(".select2theme").select2({
    			  minimumResultsForSearch: -1,
    			  dropdownAutoWidth : true,
    			  width: 'auto'
    		  });
    	}); 
    	
        $('#org_tree').jstree({
            'core' : {
                'check_callback' : true
            },
            "contextmenu":{         
                "items": function($node) {
                    var tree = $("#org_tree").jstree(true);
                    return {
                        "Create": {
                            "separator_before": false,
                            "separator_after": false,
                            "label": "Create",
                            "action": function (obj) { 
                                $node = tree.create_node($node);
                                tree.edit($node);
                            }
                        },
                        "Rename": {
                            "separator_before": false,
                            "separator_after": false,
                            "label": "Rename",
                            "action": function (obj) { 
                                tree.edit($node);
                            }
                        },                         
                        "Remove": {
                            "separator_before": false,
                            "separator_after": false,
                            "label": "Remove",
                            "action": function (obj) { 
                                tree.delete_node($node);
                            }
                        }
                    };
                }
            },
            'plugins' : [ 'types', 'dnd', 'checkbox', 'contextmenu' ],
            'types' : {
                'default' : {
                    'icon' : 'fa fa-user-circle'
                },
                'root' : {
                    'icon' : 'fa fa-building'
                }

            }
        });

        
        loadScript(plugin_path + "datatables/js/jquery.dataTables.min.js", function(){
			loadScript(plugin_path + "datatables/dataTables.bootstrap.js", function(){
				loadScript(plugin_path + "datatables/js/dataTables.colResize.js", function(){
					loadScript(plugin_path + "datatables/js/dataTables.colVis.js", function(){

				if (jQuery().dataTable) {

					var table = jQuery('#table_userinfo');
					table.dataTable({
						//"autoWidth": true,
						"colResize": {
							"rtl": true
						},
						//"dom": '<"row view-filter"<"col-sm-12"<"pull-left" i ><"pull-right"><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
						//dom: 'C<"clear">RZlfrtp',
						"dom": 'RZrt<"row view-filter"<"col-sm-12"<"pull-left" i ><"pull-right"><"clearfix">>>t<"row view-pager"<"col-sm-12"<"pull-left"<"toolbar">><"pull-right"p>>>',
						//l이 갯수
						"columns": [{
							"orderable": false
						}, {
							"orderable": true
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": true
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}, {
							"orderable": false
						}],
						// set the initial value
						"pageLength": 20,
						"iDisplayLength": 20,
						"pagingType": "bootstrap_full_number",
						"language": {
							"info": " _PAGES_ 페이지 중  _PAGE_ 페이지 / 총 _TOTAL_ 사용자",
							"infoEmpty":      "검색된 데이터가 없습니다.",
							"lengthMenu": "  _MENU_ 개",
							"paginate": {
								"previous":"Prev",
								"next": "Next",
								"last": "Last",
								"first": "First"
							}
						},
						"columnDefs": [{  // set default column settings
							'targets': [0]	//부서
							,"class":"center-cell"
						}, {	
							"targets": [1]	//아이디
							,"class":"center-cell"
						}, {	
							"targets": [2]	//이름
							,"class":"center-cell"
						}, {	
							"targets": [3],	//번호
							"class":"center-cell"
						}, {	
							"targets": [4]	//직책
							,"class" : "userinfo center-cell"
						}, {	
							"targets": [5]	//계급
							,"class" : "userinfo center-cell"
						}, {	
							"targets": [6]	//연락처
							,"class" : "userinfo center-cell"
						}, {	
							"targets": [7]	//설치유무
							,"class":"center-cell"
						}, {	
							"targets": [8]	//IP
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [9]	//MAC
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [10]	//PC이름
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [11]	//버전
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [12]	//접속여부
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [13]	//설치시간
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [14]	//서버접속시간
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [15]	//PC접속시간
							,"class" : "agentinfo center-cell"
						}, {	
							"targets": [16],	//추가정보
							"class":"center-cell"
						}],						
						"initComplete": function( settings, json ) {
							setColumnType(1);
						}
					});

/* 					var tableWrapper = jQuery('#datatable_sample_wrapper');

					table.find('.group-checkable').change(function () {
						var set = jQuery(this).attr("data-set");
						var checked = jQuery(this).is(":checked");
						jQuery(set).each(function () {
							if (checked) {
								jQuery(this).attr("checked", true);
								jQuery(this).parents('tr').addClass("active");
							} else {
								jQuery(this).attr("checked", false);
								jQuery(this).parents('tr').removeClass("active");
							}
						});
						//jQuery.uniform.update(set);
					});

					table.on('change', 'tbody tr .checkboxes', function () {
						jQuery(this).parents('tr').toggleClass("active");
					});
 */

				}
			});
			});
			});
		});
		jQuery('#preloader').hide();

    });
</script>
		
		
	</body>
</html>