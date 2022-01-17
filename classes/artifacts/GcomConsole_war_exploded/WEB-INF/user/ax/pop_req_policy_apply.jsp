<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="gcom.user.model.MemberPolicyModel"%>
<% 
	MemberPolicyModel data = (MemberPolicyModel)request.getAttribute("data");
%>

<!-- Alert -->
<link href="${context}/assets/plugins/vex/css/vex.css" rel="stylesheet" type="text/css"  />
<link href="${context}/assets/plugins/vex/css/vex-theme-os.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.min.js"></script>
<script type="text/javascript" src="${context}/assets/plugins/vex/js/vex.combined.min.js"></script>

<script type="text/javascript" src="${context}/assets/js/date.js"></script>

<div id="modalApplyPolicy" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 5%;">
	<div class="modal-dialog" style="width:940px;">
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
											<strong>개인정보정책 정보</strong> <!-- panel title -->
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
																<tbody>
																	<tr>
																		<td class="th-cell-gray" width="300px;">에이전트 삭제 가능 여부</td>
																		<td><input type="checkbox" value="Y" id="chk_isUninstall_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsUninstall())){ %> checked <%}%> /></td>
																	</tr>	
																	<tr>
																		<td class="th-cell-gray">파일실시간 암호화</td>
																		<td><input type="checkbox" value="Y" id="chk_isFileEncryption_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsFileEncryption())){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">CD 사용 여부</td>
																		<td><input type="checkbox" value="Y" id="chk_isCdEnabled_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsCdEnabled())){ %> checked <%}%> /></td>
																	</tr>
																	<!-- <tr>
																		<td class="th-cell-gray">CD 반출 여부</td>
																		<td><input type="checkbox" value="Y" id="chk_isCdExport_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsCdExport())){ %> checked <%}%> /></td>
																	</tr>																	
																	<tr>
																		<td class="th-cell-gray">CD실시간 암호화</td>
																		<td><input type="checkbox" value="Y" id="chk_isCdEncryption_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsCdEncryption())){ %> checked <%}%> /></td>
																	</tr>
																	-->
																	<tr>
																		<td class="th-cell-gray">프린터 사용 여부</td>
																		<td><input type="checkbox" value="Y" id="chk_isPrint_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsPrint())){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">프린터 인쇄 로그</td>
																		<td>
																			<input type="radio" value="0" id="radio_printLogDesc_item" name="radio_printLogDesc_item" <% if (data.getPrintLogDesc() == 0){ %> checked <%}%> />로그전송안함
																			<input type="radio" value="1" id="radio_printLogDesc_item" name="radio_printLogDesc_item" <% if (data.getPrintLogDesc() == 1){ %> checked <%}%> />이벤트로그
																			<input type="radio" value="2" id="radio_printLogDesc_item" name="radio_printLogDesc_item" <% if (data.getPrintLogDesc() == 2){ %> checked <%}%> />파일원본로그
																		</td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">무선랜 사용 여부</td>
																		<td><input type="checkbox" value="Y" id="chk_isWlan_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsWlan())){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">공유폴더 사용 여부</td>
																		<td><input type="checkbox" value="Y" id="chk_isNetShare_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsNetShare())){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">웹브라우저로 파일 반출 사용 여부</td>
																		<td><input type="checkbox" value="Y" id="chk_isWebExport_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsWebExport())){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">개인정보가 검출된 파일의 삭제 여부</td>
																		<td><input type="checkbox" value="1" id="chk_patternFileControl_item" name="chk_policy_item" <% if (data.getPatternFileControl() == 1 ){ %> checked <%}%> /> (비활성화 시 격리조치)</td>
																	</tr>																	
																	<tr>
																		<td class="th-cell-gray">보호폴더 접근 가능 여부</td>
																		<td><input type="checkbox" value="1" id="chk_isSensitiveDirEnabled_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsSensitiveDirEnabled())){ %> checked <%}%> /></td>
																	</tr>
																	<!-- 
																	<tr>
																		<td class="th-cell-gray">민감파일 접근시 삭제 여부</td>
																		<td><input type="checkbox" value="1" id="chk_isSensitiveFileAccess_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsSensitiveFileAccess())){ %> checked <%}%> /></td>
																	</tr>
																	-->
																	<tr>
																		<td class="th-cell-gray">디스크 반출 가능 여부</td>
																		<td><input type="checkbox" value="1" id="chk_isStorageExport_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsStorageExport())){ %> checked <%}%> /></td>
																	</tr>
																	<tr>
																		<td class="th-cell-gray">디스크 관리자 여부</td>
																		<td><input type="checkbox" value="1" id="chk_isStorageAdmin_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsStorageAdmin())){ %> checked <%}%> /></td>
																	</tr>
																	<!-- <tr>
																		<td class="th-cell-gray">USB통제 기능 사용 여부</td>
																		<td><input type="checkbox" value="1" id="chk_isUsbControl_item" name="chk_policy_item" <% if (Boolean.TRUE.equals(data.getIsUsbControlEnabled())){ %> checked <%}%> /></td>
																	</tr>
																	-->


																	
																</tbody>
															</table>
															
															<table class="table table-bordered" style="margin:0;">
															<tbody>
																<tr>
																	<td class="th-cell-gray" width="300px;" style="vertical-align: middle;">요청사유</td>
																	<td><input type="text" class="form-control" value="" id="att_change_policy_notice" name="att_change_policy_notice" /></td>
																</tr>
															</tbody>
														</table>
														</div>
														
														<!-- USB 차단정책 -->
														<div id="usb" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_usb.jsp" flush="false" >
   																<jsp:param name="isUsbBlock" value="<%= data.getIsUsbBlock()%>"/>
   																<jsp:param name="usbBlockCode" value="<%= data.getUsbBlockCode() %>"/>
															</jsp:include>
														</div>
														
														<!-- 시리얼 포트 차단정책 -->
														<div id="serial" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_serial.jsp" flush="false" >
   																<jsp:param name="isComPortBlock" value="<%= data.getIsComPortBlock() %>" />
   																<jsp:param name="comPortBlockCode" value="<%= data.getComPortBlockCode() %>" />
   															</jsp:include>
														</div>
														
														<!-- 네트워크 포트 차단정책 -->
														<div id="network" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_network.jsp" flush="false" >
   																<jsp:param name="isNetPortBlock" value="<%= data.getIsNetPortBlock() %>" />
   																<jsp:param name="netPortBlockCode" value="<%= data.getNetPortBlockCode() %>" />
   															</jsp:include>
														</div>
														
														<!-- 프로그램 차단정책 -->
														<div id="program" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_process.jsp" flush="false" >
   																<jsp:param name="isProcessList" value="<%= data.getIsProcessList()%>" />
   																<jsp:param name="processListCode" value="<%= data.getProcessListCode() %>" />
   															</jsp:include>
														</div>
														
														<!-- 민감 패턴 차단정책 -->
														<div id="pattern" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_pattern.jsp" flush="false" >
   																<jsp:param name="isFilePattern" value="<%= data.getIsFilePattern() %>" />
   																<jsp:param name="filePatternCode" value="<%= data.getFilePatternCode() %>" />
   															</jsp:include>
														</div>
														
														<!-- 사이트 차단정책 -->
														<div id="website" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_website.jsp" flush="false" >
   																<jsp:param name="isWebAddr" value="<%= data.getIsWebAddr()%>" />
   																<jsp:param name="webAddrCode" value="<%= data.getWebAddrCode() %>" />
   															</jsp:include>
														</div>
														
														<!-- 메신저 차단정책 -->
														<div id="msg" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_msg.jsp" flush="false" >
   																<jsp:param name="isMsgBlock" value="<%= data.getIsMsgBlock() %>" />
   																<jsp:param name="msgBlockCode" value="<%= data.getMsgBlockCode() %>" />
   															</jsp:include>
														</div>
														
														<!-- 워터마크 정책 -->
														<div id="water" class="tab-pane fade">
															<jsp:include page="/WEB-INF/user/ax/req_tab/req_policy_apply_water.jsp" flush="false" >
   																<jsp:param name="isWatermark" value="<%=data.getIsWatermark() %>"/>
   																<jsp:param name="watermarkCode" value="<%=data.getWatermarkCode() %>"/>
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
					<button type="button" class="btn btn-primary" onclick="fn_policy_apply_save();" ><i class="fa fa-check"></i> 정책요청</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${context}/assets/plugins/datatables/media/js/dataTables.bootstrap.min.js"></script>

<script type="text/javascript">

	function getPolicyApplyData(){
		var map = new Object();
		
		// 기본 탭 데이터 Set Operation
		map['isUninstall'] 			= $('#chk_isUninstall_item').is(':checked') == true ? 1 : 0 ;					// 에이전트 삭제 가능 여부
		map['isFileEncryption']		= $('#chk_isFileEncryption_item').is(':checked') == true ? 1 : 0 ;				// 파일실시간 암호화 여부
		map['isCdEncryption'] 		= $('#chk_isCdEncryption_item').is(':checked') == true ? 1 : 0 ;				// CD실시간 암호화 여부
		map['isPrint'] 				= $('#chk_isPrint_item').is(':checked') == true ? 1 : 0 ;						// 프린터 사용 여부	
		map['isCdEnabled'] 			= $('#chk_isCdEnabled_item').is(':checked') == true ? 1 : 0 ;					// CD 사용여부
		map['isCdExport'] 			= $('#chk_isCdExport_item').is(':checked') == true ? 1 : 0 ;					// CD 반출 여부
		map['isWlan'] 				= $('#chk_isWlan_item').is(':checked') == true ? 1 : 0 ;						// 무선랜 사용 여부
		map['isNetShare'] 			= $('#chk_isNetShare_item').is(':checked') == true ? 1 : 0 ;					// 공유폴더 사용 여부
		map['isWebExport'] 			= $('#chk_isWebExport_item').is(':checked') == true ? 1 : 0 ;					// 메일 반출 여부
		
		map['isSensitiveDirEnabled'] = $('#chk_isSensitiveDirEnabled_item').is(':checked') == true ? 1 : 0 ;				// 보호폴더 접근 사용여부
		map['isSensitiveFileAccess'] = $('#chk_isSensitiveFileAccess_item').is(':checked') == true ? 1 : 0 ;		// 민감파일 접근 여부
		map['isStorageExport'] 		= $('#chk_isStorageExport_item').is(':checked') == true ? 1 : 0 ;				// 디스크반출가능 여부
		map['isStorageAdmin'] 		= $('#chk_isStorageAdmin_item').is(':checked') == true ? 1 : 0 ;				// 디스크 관리자 여부
		map['isUsbControlEnabled'] 	= $('#chk_isUsbControl_item').is(':checked') == true ? 1 : 0 ;					// USB통제 여부
		map['patternFileControl'] 	= $('#chk_patternFileControl_item').is(':checked') == true ? 1 : 0 ;			// 개인정보가 검출된 파일의 삭제 여부
		
		map['printLogDesc'] 		= $(':radio[name="radio_printLogDesc_item"]:checked').val();					// 프린터 인쇄 로그
		
		// USB 탭 데이터 Set Operation
		map['isUsbBlock']		= $('#att_usb_block_type').val();			// USB 차단정책
		
		// 시리얼 탭 데이터 Set Operation
		map['isComPortBlock']	= $('#att_com_port_type').val();			// 시리얼 포트 차단정책
		
		// 네트워크 탭 데이터 Set Operation
		map['isNetPortBlock']	= $('#att_net_port_type').val();			// 네트워크 포트 차단정책
		
		// 프로세스 탭 데이터 Set Operation
		map['isProcessList']	= $('#att_process_type').val();				// 프로세스 차단정책
		
		// 패턴 탭 데이터 Set Operation
		map['isFilePattern']	= $('#att_pattern_type').val();				// 민감 패턴 차단정책
		
		// 사이트 탭 데이터 Set Operation
		map['isWebAddr']		= $('#att_website_block_type').val();		// 사이트 차단정책
		
		// 메신저 탭 데이터 Set Operation
		map['isMsgBlock']		= $('#att_msg_block_type').val();			// 메신저 차단정책
		
		
		map['isWatermark'] = $('#att_watermark_type').val();
		
		return map;
	}
	
	$(document).ready(function(){
		usb_policy_table();
		com_port_table();
		net_port_table();
		process_table();
		pattern_table();
		web_site_table();
		msg_block_table();
	});

	function fn_policy_apply_save() {
		var data = getPolicyApplyData();
		data['agent_no'] = <%= data.getAgentNo() %>
		data['user_no'] = <%= data.getUserNo() %>
		data['policy_no'] = <%= data.getPolicyNo() %>
		data['notice'] = $('#att_change_policy_notice').val();
		
		if(!isValid(data)){
			return false;
		}
		
		$.ajax({      
		    type:"POST",  
		    url:'${context}/user/req/policy/save',
		    async: false,
		    data:{
		    	request_policy : JSON.stringify(data),
		    	_ : $.now()
		    },
		    success:function(data){
		    	$('#modalApplyPolicy').modal('hide');
		    	
		    	if(data.returnCode == "S") {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책이 요청 되었습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })]
	    			})
		    		
		    	} else {
		    		vex.defaultOptions.className = 'vex-theme-os'
		    			
	    			vex.dialog.open({
	    				message: '정책 요청에 실패했습니다.',
	    				  buttons: [
	    				    $.extend({}, vex.dialog.buttons.YES, {
	    				      text: '확인'
	    				  })]
	    			})
		    	}
		    },   
		    error:function(e){  
		        console.log(e.responseText);  
		    }  
		});
		
	}
	
	function isValid(policy_data) {

		return true;
	}
	
	
</script>
































