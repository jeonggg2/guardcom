<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	List<HashMap<String, Object>> apply_list = (List<HashMap<String, Object>>)request.getAttribute("apply_list");
	boolean onlyFlag = Boolean.parseBoolean(request.getAttribute("only_flag").toString());
	HashMap<String, Object> data = (HashMap<String, Object>)request.getAttribute("current_policy");
%>

<script type="text/javascript" src="${context}/assets/js/admin_function.js"></script>
<script type="text/javascript" src="${context}/assets/js/date.js"></script>
<div id="modalApplyPolicy" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog" style="width:1024px;">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title" id="myModalLabel">정책 항목 수정</h4>
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
											<strong>정책 정보</strong> <!-- panel title -->
										</span>
									</div>
		
									<!-- panel content -->
									<div class="panel-body">
										<div class="row">
											<div class="col-md-12" style="overflow: hidden;">
												<div class="aside-tabs">
													<ul class="nav nav-tabs nav-top-border">
														<li class="active"><a href="#basics" data-toggle="tab"><i class="fa fa-lock" title="기본정책"></i>기본</a></li>
														<li><a href="#usb" data-toggle="tab"><i class="fab fa-usb" title="USB 차단정책"></i>USB</a></li>
														<li><a href="#serial" data-toggle="tab"><i class="fa fa-plug" title="시리얼 포트 차단정책"></i>시리얼</a></li>
														<li><a href="#network" data-toggle="tab"><i class="fa fa-sitemap" title="네트워크 포트 차단정책"></i>네트워크</a></li>
														<li><a href="#program" data-toggle="tab"><i class="fa fa-desktop" title="프로세스 차단정책"></i>프로세스</a></li>
														<li><a href="#pattern" data-toggle="tab"><i class="fa fa-clone" title="민감패턴 차단정책"></i>민감패턴</a></li>
														<li><a href="#website" data-toggle="tab"><i class="fab fa-internet-explorer" title="사이트 차단정책"></i>사이트</a></li>
														<li><a href="#msg" data-toggle="tab"><i class="fa fa-commenting" title="메신저 차단정책"></i>메신저</a></li>
														<li><a href="#water" data-toggle="tab"><i class="fa fa-tint" title="워터마크 정책"></i>워터마크</a></li>
													</ul>
													
													<div class="tab-content">
														<!-- 기본정책 -->
														<div id="basics" class="tab-pane fade in active">
															<table class="table table-bordered">
																<thead>
																<tr>
																<th>정책 이름</th>
																<th>정책 값</th>
																<th>적용 기간(만료일)</th>
																</tr>
																</thead>
																<tbody>																	
																	<tr>
																		<td class="th-cell-gray" width="300px;">에이전트 삭제 가능 여부</td>																		
																		<td><input type="checkbox" id="chk_isUninstall_item" name="chk_policy_item" <% if (data.get("isUninstall") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isUninstall"))){ %> checked <%}%> /></td>
																	</tr>	
																	<tr>
																		<td class="th-cell-gray">파일 암호화 사용</td>
																		<td><input type="checkbox" id="chk_isFileEncryption_item" name="chk_policy_item" <% if (data.get("isFileEncryption") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isFileEncryption"))){ %> checked <%}%> /></td>
																	</tr>																	
																	<tr>
																		<td class="th-cell-gray">CD 사용 여부</td>
																		<td><input type="checkbox" id="chk_isCdEnabled_item" name="chk_policy_item" <% if (data.get("isCdEnabled") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isCdEnabled"))){ %> checked <%}%> /></td>
																	</tr>
																	
																	<tr>
																		<td class="th-cell-gray">CD 암호화 사용</td>
																		<td><input type="checkbox" id="chk_isCdEncryption_item" name="chk_policy_item" <% if (data.get("isCdEncryption") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isCdEncryption"))){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">CD 반출 가능 여부</td>
																		<td><input type="checkbox" id="chk_isCdExport_item" name="chk_policy_item" <% if (data.get("isCdExport") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isCdExport"))){ %> checked <%}%> /></td>
																	</tr>
																																		
																	<tr>
																		<td class="th-cell-gray">프린터 사용 가능 여부</td>
																		<td><input type="checkbox" id="chk_isPrint_item" name="chk_policy_item" <% if (data.get("isPrint") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isPrint"))){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">프린터 인쇄 로그</td>
																		<td>
																			<% int printLogDesc = Integer.parseInt(data.get("printLogDesc").toString()); %>
																			<input type="radio" value="0" id="radio_printLogDesc_item" name="radio_printLogDesc_item" <% if (printLogDesc == 0){ %> checked <%}%> />로그전송안함
																			<input type="radio" value="1" id="radio_printLogDesc_item" name="radio_printLogDesc_item" <% if (printLogDesc == 1){ %> checked <%}%> />이벤트로그
																			<input type="radio" value="2" id="radio_printLogDesc_item" name="radio_printLogDesc_item" <% if (printLogDesc == 2){ %> checked <%}%> />파일원본로그
																		</td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">무선랜 사용 여부</td>
																		<td><input type="checkbox" id="chk_isWlan_item" name="chk_policy_item" <% if (data.get("isWlan") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isWlan"))){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">공유폴더 사용 여부</td>
																		<td><input type="checkbox" id="chk_isNetShare_item" name="chk_policy_item" <% if (data.get("isNetShare") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isNetShare"))){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">웹브라우저로 파일 반출 사용 여부</td>
																		<td><input type="checkbox" id="chk_isWebExport_item" name="chk_policy_item" <% if (data.get("isWebExport") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isWebExport"))){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">개인정보가 검출된 파일의 삭제 여부</td>
																		<td><input type="checkbox" id="chk_patternFileControl_item" name="chk_policy_item" <% if (data.get("patternFileControl") == null){ %> indeterminate="indeterminate"  <%} else if (Integer.parseInt(data.get("patternFileControl").toString()) == 1){ %> checked <%}%> /> (비활성화 시 격리조치)</td>
																	</tr>
																																																							
																	<tr>
																		<td class="th-cell-gray">보호폴더 접근 가능 여부</td>
																		<td><input type="checkbox" id="chk_isSensitiveDirEnabled_item" name="chk_policy_item" <% if (data.get("isSensitiveDirEnabled") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isSensitiveDirEnabled"))){ %> checked <%}%> /></td>
																	</tr>
																	
																	<tr>
																		<td class="th-cell-gray">민감파일 접근시 삭제 여부</td>
																		<td><input type="checkbox" id="chk_isSensitiveFileAccess_item" name="chk_policy_item" <% if (data.get("isSensitiveFileAccess") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isSensitiveFileAccess"))){ %> checked <%}%> /></td>
																	</tr>
																	
																	<tr>
																		<td class="th-cell-gray">디스크 반출 기능 사용 여부</td>
																		<td><input type="checkbox" id="chk_isStorageExport_item" name="chk_policy_item" <% if (data.get("isStorageExport") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isStorageExport"))){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">디스크 관리기능 사용 여부</td>
																		<td><input type="checkbox" id="chk_isStorageAdmin_item" name="chk_policy_item" <% if (data.get("isStorageAdmin") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isStorageAdmin"))){ %> checked <%}%> /></td>
																	</tr>
																	
																	<tr>
																		<td class="th-cell-gray">USB통제 기능 사용 여부</td>
																		<td><input type="checkbox" id="chk_isUsbControl_item" name="chk_policy_item" <% if (data.get("isUsbControlEnabled") == null){ %> indeterminate="indeterminate"  <%} else if (Boolean.TRUE.equals(data.get("isUsbControlEnabled"))){ %> checked <%}%> /></td>
																	</tr>
																</tbody>
															</table>
														</div>
														
														<!-- USB 차단정책 -->
														<div id="usb" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_usb.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isUsbBlock" value="<%= data.get(\"isUsbBlock\")%>"/>
   																<jsp:param name="usbBlockCode" value="<%= data.get(\"usbBlockCode\").toString() %>"/>
															</jsp:include>
														</div>
														
														<!-- 시리얼 포트 차단정책 -->
														<div id="serial" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_serial.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isComPortBlock" value="<%= data.get(\"isComPortBlock\")%>"/>
   																<jsp:param name="comPortBlockCode" value="<%= data.get(\"comPortBlockCode\").toString() %>"/>
   															</jsp:include>
														</div>
														
														<!-- 네트워크 포트 차단정책 -->
														<div id="network" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_network.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isNetPortBlock" value="<%= data.get(\"isNetPortBlock\")%>"/>
   																<jsp:param name="netPortBlockCode" value="<%= data.get(\"netPortBlockCode\").toString() %>"/>
   															</jsp:include>
														</div>
														
														<!-- 프로그램 차단정책 -->
														<div id="program" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_process.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isProcessList" value="<%= data.get(\"isProcessList\")%>"/>
   																<jsp:param name="processListCode" value="<%= data.get(\"processListCode\").toString() %>"/>
   															</jsp:include>
														</div>
														
														<!-- 민감 패턴 차단정책 -->
														<div id="pattern" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_pattern.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isFilePattern" value="<%= data.get(\"isFilePattern\")%>"/>
   																<jsp:param name="filePatternCode" value="<%= data.get(\"filePatternCode\").toString() %>"/>
   															</jsp:include>
														</div>
														
														<!-- 사이트 차단정책 -->
														<div id="website" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_website.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isWebAddr" value="<%= data.get(\"isWebAddr\")%>"/>
   																<jsp:param name="webAddrCode" value="<%= data.get(\"webAddrCode\").toString() %>"/>
   															</jsp:include>
														</div>
														
														<!-- 메신저 차단정책 -->
														<div id="msg" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_msg.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isMsgBlock" value="<%= data.get(\"isMsgBlock\")%>"/>
   																<jsp:param name="msgBlockCode" value="<%= data.get(\"msgBlockCode\").toString() %>"/>
   															</jsp:include>
														</div>
														
														<!-- 워터마크 정책 -->
														<div id="water" class="tab-pane fade">
															<jsp:include page="/WEB-INF/admin/user_manage/ax/assign_tab/assign_policy_apply_water.jsp" flush="false" >
																<jsp:param name="onlyFlag" value="<%= onlyFlag%>"/>
   																<jsp:param name="isWatermark" value="<%= data.get(\"isWatermark\")%>"/>
   																<jsp:param name="watermarkCode" value="<%= data.get(\"watermarkCode\").toString() %>"/>
   															</jsp:include>
														</div>
														
													</div>
												</div>	
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
				<!-- /Modal body -->

				<!-- Modal Footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="fn_policy_apply_save();" ><i class="fa fa-check"></i> 정책적용</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
	
 <script type="text/javascript" src="${context}/assets/plugins/jquery.tristate.js"></script>
 <script type="text/javascript" src="${context}/assets/plugins/moment/moment.js"></script>
 <script type="text/javascript" src="${context}/assets/plugins/moment/locale/ko.js"></script>
 <script type="text/javascript" src="${context}/assets/plugins/bootstrap.datetimepicker/js/bootstrap-datetimepicker.min.js"></script>

	var oriData = new Object();
	
	<style type="text/css">
	.bootstrap-datetimepicker-widget {
	    top: 0;
	    left: 0;
	    width: 350px;
	    padding: 4px;
	    padding-left: 10px;
	    margin-top: 1px;
	    margin-left: 5px;
	    z-index: 99999;
	    border-radius: 4px;
	}
	</style>
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		
		debugger;
		
		usb_policy_table();
		com_port_table();
		net_port_table();
		process_table();
		pattern_table();
		web_site_table();
		msg_block_table();
		
		$('#basics > table > tbody > tr').each(function () {
			$(this).append('<td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" '+  ($(this).children('td:last').children().val() == -1 ? 'disabled ' : '') +'type="text" id="dt_' + $(this).children('td:last').children().attr('id').split('_')[1] + '" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		});
		
		$('#usb').css('min-height' ,'320px');
		$('#usb > div:first > table:first > tbody > tr:last > td:nth-child(2)')
			.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isUsbBlock" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		
		$('#serial').css('min-height' ,'320px');
		$('#serial > div:first > table:first > tbody > tr:last > td:nth-child(2)')
			.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isComPortBlock" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		
		$('#network').css('min-height' ,'320px');
		$('#network > div:first > table:first > tbody > tr:last > td:nth-child(2)')
			.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isNetPortBlock" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		
		$('#program').css('min-height' ,'320px');
		$('#program > div:first > table:first > tbody > tr:last > td:nth-child(2)')
			.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isProcessList" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		
		$('#pattern').css('min-height' ,'320px');
		$('#pattern > div:first > table:first > tbody > tr:last > td:nth-child(2)')
			.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isFilePattern" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		
		$('#website').css('min-height' ,'320px');
		$('#website > div:first > table:first > tbody > tr:last > td:nth-child(2)')
			.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isWebAddr" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		
		$('#msg').css('min-height' ,'320px');
		$('#msg > div:first > table:first > tbody > tr:last > td:nth-child(2)')
			.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isMsgBlock" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
		
		$('#water').css('min-height' ,'320px');
		$('#water > div:first > table:first > tbody > tr:last > td:nth-child(2)')
		.after('<td width="45" class="th-cell-gray center-cell" style="vertical-align: middle;">기간</td><td style="vertical-align: middle;"><div class="row"><div class="col-sm-12"><div class="expired-date input-group date"><input class="form-control temporarily" type="text" id="dt_isWatermark" class="form-control"><span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></div></div></div></td>');
	
		
		$('.expired-date').datetimepicker({
			locale: 'ko',
			sideBySide: true,
			stepping:30,
			collapse: false,
			showTodayButton: true,
			showClear: true,
			allowInputToggle: true,			
			widgetPositioning : {
	            horizontal: 'right',
	            vertical: 'auto'
	        },
			format: 'YYYY-MM-DD HH:mm',
			toolbarPlacement : 'top',
			dayViewHeaderFormat : 'YYYY MMMM'
		}).on('dp.show', function(e){
			$(this).data("DateTimePicker").minDate(new Date());
		}).on('dp.change', function(e){
			$(this).data("DateTimePicker").minDate(new Date());	
			if (moment(new Date()).isAfter(e.date)) {
				return false;
			}
		});
<% if (! onlyFlag){ %>
		$('#basics > table > tbody > tr > td input[type=checkbox]').tristate({
			checked: 1,
		    unchecked: 0,
		    indeterminate: -1,
			change: function(state, value) {
				$('#dt_' + $(this).attr('id').split('_')[1]).prop('disabled', value == -1);
			}
		}).each(function () {
			if ($(this).prop('indeterminate')) {
				$('#dt_' + $(this).attr('id').split('_')[1]).prop('disabled', true);
			}
		});
<% } %>
		initData();
		
<% for (Entry<String, Object> entry : data.entrySet()) if (entry.getKey().indexOf("_temporarily") != -1) {%> oriData['<%=entry.getKey()%>'] = '<%=entry.getValue() == null ? "" : entry.getValue().toString().split(";")[0]%>'; <% }%>
		
		$('.temporarily').each(function () {
			var name = $(this).attr('id').split('_')[1];
			$(this).val(oriData[name + '_temporarily']);
		});
	});
	
	function initData() {
		oriData = getPolicyApplyData();
	}
	
	function fn_policy_apply_save() {
		debugger;
		var policy_data = getPolicyApplyData();
		
		$('.temporarily').each(function () {			
			var name = $(this).attr('id').split('_')[1];
			if (! $(this).prop('disabled')) {
				var dt = $(this).val();
				if (dt) {
					policy_data[name + '_temporarily'] = $(this).val() + ";" + oriData[name];
				} else {
					policy_data[name + '_temporarily'] = '';
				}
			}
		});
		
		var apply_list = getApplyPolicyUserData();
		policy_data['apply_list'] = apply_list;
		if(!isChangeValueCheck(policy_data)) {
			vex.defaultOptions.className = 'vex-theme-os'
				
			vex.dialog.open({
				message: '변경된 정책이 없습니다. 적용할 정책을 선택 후 등록해주세요.',
				  buttons: [
				    $.extend({}, vex.dialog.buttons.YES, {
				      text: '확인'
				  })]
			})
			return false;
		}
		
		if(!isValid(policy_data)){
			return false;
		}

		if(apply_list.length > 1) {
			vex.defaultOptions.className = 'vex-theme-os'
				
			vex.dialog.open({
				message: '주의! 한명 이상에게 정책 적용 시 변경 된 정책만 적용되오니 유의하시기 바랍니다. 선택 된 정책을 적용하시겠습니까?',
				  buttons: [
				    $.extend({}, vex.dialog.buttons.YES, {
				      text: '확인'
				  }),
				  $.extend({}, vex.dialog.buttons.NO, {
				      text: '취소'
				  })],
				  callback: function(data) {
			 	  	if (data) {
			 	  		saveData(policy_data);
			 	    } else {
			 	    	$('#modalApplyPolicy').modal('hide');
			 	  		return false;
			 	    }
			 	  }
			})
		} else {
			saveData(policy_data);
		}
	}
	
	function saveData (policy_data) {
		
		$.ajax({      
		    type:"POST",  
		    url:'${context}/admin/user/apply/save',
		    async: false,
		    data:{
		    	apply_policy : JSON.stringify(policy_data),
		    	_ : $.now()
		    },
		    success:function(data){
		    	if(data.returnCode == "S") {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책이 적용 되었습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })],
	    				  callback: function(data) {
    				 	  	if (data) {
    				 	  		var datatable = $('#table_apply_policy').dataTable().api();
								datatable.ajax.reload();   	
    				 	  		$('#modalApplyPolicy').modal('hide');
    				 	    }
    				 	  }
	    				  
	    			})
		    		
		    	} else {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책이 적용에 실패했습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })],
	    				  callback: function(data) {
    				 	  	if (data) {
    				 	  		top.location.reload();
    				 	  		$('#modalApplyPolicy').modal('hide');
    				 	    }
    				 	  }
	    				  
	    			})
		    	}
		    },   
		    error:function(e){  
		        console.log(e.responseText);  
		    }  
		});
		
	}
	
	function getApplyPolicyUserData(){
		var arr = new Array();
		
		<% for(int i = 0; i < apply_list.size(); i++) { %>
			var map = new Object();
			map['agent_no'] = <%= apply_list.get(i).get("agentNo") %>
			map['user_no'] = <%= apply_list.get(i).get("uno") %>
			map['policy_no'] = <%= apply_list.get(i).get("policyNo") %>
			map['user_id'] = '<%= apply_list.get(i).get("userId") %>'
			arr.push(map);
		<% } %>
		
		console.log(arr);
		
		return arr;
	}
	
	function isChangeValueCheck(change_policy_data) {
		
		debugger;
		var hasChanged = false;
		
		$.each( oriData, function( key, value ) {
			if (key.indexOf('temporarily') == -1) {
				if (value != change_policy_data[key]) {
					hasChanged = true;
					return false;
				}
			} else {
				if (change_policy_data[key] == null) {
					
				} else {
					if (value != change_policy_data[key].split(";")[0]) {
						hasChanged = true;
						return false;
					}
				}
			}				
		});
		
		return hasChanged;
	}
	
	function isValid(policy_data) {
		return true;
	}
	
	
</script>

