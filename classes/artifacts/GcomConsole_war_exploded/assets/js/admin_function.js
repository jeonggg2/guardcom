
function getPolicyApplyData(){
	
	var map = new Object();
	
	valFunction	= $.fn.val;
    $.fn.val = function(value) {
        if ($(this).attr('type') === 'checkbox') {
	        if (typeof value === 'undefined') {
	        	if ($(this).prop('indeterminate')) {
	        		return -1;
	        	}
				return ($(this).is(':checked') ? 1 : 0);
			} else {
				return valFunction.call(this, value);
			}
		} else {
	        if (typeof value === 'undefined') {
	            return valFunction.call(this);
			} else {
				return valFunction.call(this, value);
			}
		}
    };
	
	// 기본 탭 데이터 Set Operation
	map['isUninstall'] 			= $('#chk_isUninstall_item').val();					// 에이전트 삭제 가능 여부
	map['isFileEncryption']		= $('#chk_isFileEncryption_item').val();			// 파일실시간 암호화 여부
	map['isCdEncryption'] 		= $('#chk_isCdEncryption_item').val();				// CD실시간 암호화 여부
	map['isPrint'] 				= $('#chk_isPrint_item').val();						// 프린터 사용 여부	
	map['isCdEnabled'] 			= $('#chk_isCdEnabled_item').val();					// CD 사용여부
	map['isCdExport'] 			= $('#chk_isCdExport_item').val();					// CD 반출 여부
	map['isWlan'] 				= $('#chk_isWlan_item').val();						// 무선랜 사용 여부
	map['isNetShare'] 			= $('#chk_isNetShare_item').val();					// 공유폴더 사용 여부
	map['isWebExport'] 			= $('#chk_isWebExport_item').val();					// 메일 반출 여부
	map['isSensitiveDirEnabled'] = $('#chk_isSensitiveDirEnabled_item').val();				// 보호폴더 접근 사용여부
	map['isSensitiveFileAccess'] = $('#chk_isSensitiveFileAccess_item').val();		// 민감파일 접근 여부
	map['isStorageExport'] 		= $('#chk_isStorageExport_item').val();				// 디스크반출가능 여부
	map['isStorageAdmin'] 		= $('#chk_isStorageAdmin_item').val();				// 디스크 관리자 여부
	map['isUsbControlEnabled'] 	= $('#chk_isUsbControl_item').val();				// USB통제 여부
	map['patternFileControl'] 	= $('#chk_patternFileControl_item').val();			// 개인정보가 검출된 파일의 삭제 여부
	
	$.fn.val = valFunction;
	
	map['printLogDesc'] 		= $(':radio[name="radio_printLogDesc_item"]').is(':checked') ? $(':radio[name="radio_printLogDesc_item"]:checked').val() : -1 ;				// 프린터 인쇄 로그
	
	// USB 탭 데이터 Set Operation
	map['isUsbBlock']		= $('#att_usb_block_type').val();			// USB 차단정책
	
	// 시리얼 탭 데이터 Set Operation
	map['isComPortBlock']	= $('#att_com_port_type').val();			// 시리얼 포트 차단정책
	
	// 네트워크 탭 데이터 Set Operation
	map['isNetPortBlock']	= $('#att_net_port_type').val();			// 네트워크 포트 차단정책
	
	// 프로세스 탭 데이터 Set Operation
	map['isProcessList']	= $('#att_process_type').val();				// 프로세스 차단정책
	map['isProcessCheck']   = $(':radio[name="radio_process_block"]').is(':checked') ? 1 : -1 ;
	
	// 패턴 탭 데이터 Set Operation
	map['isFilePattern']	= $('#att_pattern_type').val();				// 민감 패턴 차단정책
	map['isPatternCheck']   = $(':radio[name="radio_pattern_block"]').is(':checked') ? 1 : -1 ;
	
	// 사이트 탭 데이터 Set Operation
	map['isWebAddr']		= $('#att_website_block_type').val();		// 사이트 차단정책
	
	// 메신저 탭 데이터 Set Operation
	map['isMsgBlock']		= $('#att_msg_block_type').val();			// 메신저 차단정책
	
	// 워터마크 탭 데이터 Set Operation
	map['isWatermark'] =$('#att_watermark_type').val();					// 워터마크 정책
	
	return map;
}

function getApplyPolicyData(data){
	
	var sOut = '<table class="table table-bordered">';	
	sOut += '<col width="300px"><col><col width="300px"><col>';
	
	if(data.isUninstall == true){
		sOut += '<tr><td class="center-cell th-cell-gray">에이전트 삭제 가능 여부:</td><td>가능';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">에이전트 삭제 가능 여부:</td><td>불가능';		
	}		
	if (data.isUninstall_temporarily) sOut += " (유효기간 :" + data.isUninstall_temporarily.split(';')[0] + ")";
	sOut += "</td>";
	
	
	
	if(data.isFileEncryption == true){
		sOut += '<td class="center-cell th-cell-gray">파일 암호화 사용:</td><td>사용';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">파일 암호화 사용:</td><td>미사용';			
	}
	if (data.isFileEncryption_temporarily) sOut += " (유효기간 :" + data.isFileEncryption_temporarily.split(';')[0] + ")";
	sOut += "</td></tr>";
	
	
	
	if(data.isPrint == true){
		sOut += '<tr><td class="center-cell th-cell-gray">프린터 사용 가능 여부:</td><td>가능';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">프린트 사용 가능 여부:</td><td>불가능';			
	}
	if (data.isPrint_temporarily) sOut += " (유효기간 :" + data.isPrint_temporarily.split(';')[0] + ")";
	sOut += "</td>";
	
	
	
	
	if(data.printLogDesc == 0){
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>로그전송안함';	
	}else if (data.printLogDesc == 1) {
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>이벤트로그';			
	} else {
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>파일원본로그';		
	}	
	if (data.printLogDesc_temporarily) sOut += " (유효기간 :" + data.printLogDesc_temporarily.split(';')[0] + ")";
	sOut += "</td></tr>";
	
	
	
	if(data.isWatermark == true){
		sOut += '<tr><td class="center-cell th-cell-gray">워터마크사용여부:</td><td>사용';//&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWatermark\'' + ', \''+ data.watermarkCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">워터마크사용여부:</td><td>미사용';			
	}
	if (data.isWatermark_temporarily) sOut += " (유효기간 :" + data.isWatermark_temporarily.split(';')[0] + ")";
	sOut += "</td>";
	
	
	
	if(data.isCdEnabled == true){
		sOut += '<tr><td class="center-cell th-cell-gray">CD 사용 여부:</td><td>사용';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">CD 사용 여부:</td><td>미사용';			
	}	
	if (data.isCdEnabled_temporarily) sOut += " (유효기간 :" + data.isCdEnabled_temporarily.split(';')[0] + ")";
	sOut += "</td></tr>";
	
		
	
//	if(data.isCdExport == true){
//		sOut += '<td class="center-cell th-cell-gray">CD 반출 가능 여부:</td><td>가능</td></tr>';		
//	}else{
//		sOut += '<td class="center-cell th-cell-gray">CD 반출 가능 여부:</td><td>불가능</td></tr>';	
//	}
	
//	if(data.isCdEncryption == true){
//		sOut += '<tr><td class="center-cell th-cell-gray">CD 암호화 사용:</td><td>사용</td>';
//	}else{
//		sOut += '<tr><td class="center-cell th-cell-gray">CD 암호화 사용:</td><td>미사용</td>';		
//	}	
	
	if(data.isWlan == true){
		sOut += '<tr><td class="center-cell th-cell-gray">무선랜 사용 여부:</td><td>사용';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">무선랜 사용 여부:</td><td>미사용';	
	}	
	if (data.isWlan_temporarily) sOut += " (유효기간 :" + data.isWlan_temporarily.split(';')[0] + ")";
	sOut += "</td>";
	
	
	
	
	if(data.isNetShare == true){
		sOut += '<td class="center-cell th-cell-gray">공유폴더 사용 여부:</td><td>사용';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">공유폴더 사용 여부:</td><td>미사용';			
	}	
	if (data.isNetShare_temporarily) sOut += " (유효기간 :" + data.isNetShare_temporarily.split(';')[0] + ")";
	sOut += "</td></tr>";
	
	
	
	
	if(data.isWebExport == true){
		sOut += '<tr><td class="center-cell th-cell-gray">웹브라우저로 파일 반출 사용 여부:</td><td>사용';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">웹브라우저로 파일 반출 사용 여부:</td><td>미사용';	
	}	
	if (data.isWebExport_temporarily) sOut += " (유효기간 :" + data.isWebExport_temporarily.split(';')[0] + ")";
	sOut += "</td>";
	
	
	
	
	
	if(data.patternFileControl == 1){
		sOut += '<tr><td class="center-cell th-cell-gray">개인정보가 검출된 파일의 삭제 여부:</td><td>삭제';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">개인정보가 검출된 파일의 삭제 여부:</td><td>격리';	
	}	
	if (data.patternFileControl_temporarily) sOut += " (유효기간 :" + data.patternFileControl_temporarily.split(';')[0] + ")";
	sOut += "</td>";
	
	
	
	
	if(data.isSensitiveDirEnabled == true){
		sOut += '<td class="center-cell th-cell-gray">보호폴더 접근 가능 여부:</td><td>가능';
		sOut += ' [ 접근코드: ' + data.quarantinePathAccessCode +' ]';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">보호폴더 접근 가능 여부:</td><td>불가능';		
	}	
	if (data.isSensitiveDirEnabled_temporarily) sOut += " (유효기간 :" + data.isSensitiveDirEnabled_temporarily.split(';')[0] + ")";
	sOut += "</td></tr>";
	
	
	
//	if(data.isSensitiveFileAccess == true){
//		sOut += '<tr><td class="center-cell th-cell-gray">민감파일 접근시 삭제 여부:</td><td>삭제</td>';		
//	}else{
//		sOut += '<tr><td class="center-cell th-cell-gray">민감파일 접근시 삭제 여부:</td><td>보호폴더로 이동</td>';	
//	}
	
	
	
	if(data.isStorageAdmin == true){
		sOut += '<tr><td class="center-cell th-cell-gray">디스크 관리기능 사용 여부:</td><td>관리자';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">디스크 관리기능 사용 여부:</td><td>일반';	
	}	
	if (data.isStorageAdmin_temporarily) sOut += " (유효기간 :" + data.isStorageAdmin_temporarily.split(';')[0] + ")";
	sOut += "</td>";
	
	
	
	
	if(data.isStorageExport == true){
		sOut += '<td class="center-cell th-cell-gray">디스크 반출 기능 사용 여부:</td><td>가능';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">디스크 반출 기능 사용 여부:</td><td>불가능';		
	}	
	if (data.isStorageExport_temporarily) sOut += " (유효기간 :" + data.isStorageExport_temporarily.split(';')[0] + ")";
	sOut += "</td></tr>";
	
	
	
//	if(data.isUsbControlEnabled == true){
//		sOut += '<td class="center-cell th-cell-gray">USB통제 기능 사용 여부:</td><td>사용</td></tr>';	
//	}else{
//		sOut += '<td class="center-cell th-cell-gray">USB통제 기능 사용 여부:</td><td>미사용</td></tr>';		
//	}
	
	
	
	
	if(data.isUsbBlock == true){
		sOut += '<td class="center-cell th-cell-gray">USB포트사용여부:</td><td>차단 ';
	}else{
		sOut += '<td class="center-cell th-cell-gray">USB포트사용여부:</td><td>허용 ';		
	}	
	if (data.isUsbBlock_temporarily) sOut += " (유효기간 :" + data.isUsbBlock_temporarily.split(';')[0] + ")";
	sOut += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isUsbBlock\'' + ', \''+ data.usbBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';
	
	
	
	
	if(data.isComPortBlock == true){
		sOut += '<tr><td class="center-cell th-cell-gray">시리얼포트사용여부:</td><td>차단 ';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">시리얼포트사용여부:</td><td>허용 ';		
	}
	if (data.isComPortBlock_temporarily) sOut += " (유효기간 :" + data.isComPortBlock_temporarily.split(';')[0] + ")";
	sOut += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isComPortBlock\'' + ', \''+ data.comPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';
	
	
	
	
	if(data.isNetPortBlock == true){
		sOut += '<td class="center-cell th-cell-gray">네트워크포트사용여부:</td><td>차단';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">네트워크포트사용여부:</td><td>허용';			
	}
	if (data.isNetPortBlock_temporarily) sOut += " (유효기간 :" + data.isNetPortBlock_temporarily.split(';')[0] + ")";
	sOut += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isNetPortBlock\'' + ', \''+ data.netPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';
	
	
	
	
	if(data.isProcessList == true){
		sOut += '<tr><td class="center-cell th-cell-gray">프로세스차단여부:</td><td>차단' ;
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">프로세스차단여부:</td><td>허용';	
	}
	if (data.isProcessList_temporarily) sOut += " (유효기간 :" + data.isProcessList_temporarily.split(';')[0] + ")";
	sOut += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isProcessList\'' + ', \''+ data.processListCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';
	
	
	
	
	if(data.isFilePattern == true){
		sOut += '<td class="center-cell th-cell-gray">민감패턴차단여부:</td><td>차단';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">민감패턴차단여부:</td><td>허용';	
	}
	if (data.isFilePattern_temporarily) sOut += " (유효기간 :" + data.isFilePattern_temporarily.split(';')[0] + ")";
	sOut += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isFilePattern\'' + ', \''+ data.filePatternCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';
	
	
	
	
	if(data.isWebAddr == true){
		sOut += '<tr><td class="center-cell th-cell-gray">사이트차단여부:</td><td>차단';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">사이트차단여부:</td><td>허용';			
	}
	if (data.isWebAddr_temporarily) sOut += " (유효기간 :" + data.isWebAddr_temporarily.split(';')[0] + ")";
	sOut += ' &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWebAddr\'' + ', \''+ data.webAddrCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';
	
	
	
	
	if(data.isMsgBlock == true){
		sOut += '<td class="center-cell th-cell-gray">메신저차단여부:</td><td>차단';
	}else{
		sOut += '<td class="center-cell th-cell-gray">메신저차단여부:</td><td>허용';
	}	
	if (data.isMsgBlock_temporarily) sOut += " (유효기간 :" + data.isMsgBlock_temporarily.split(';')[0] + ")";
	sOut += ' &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isMsgBlock\'' + ', \''+ data.msgBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';
	
	
	
	sOut += '</table>';
	
	return sOut;
}

function getApplyUserData(data){
	var sOut = '<table class="table table-bordered">';
	
	sOut += '<col width="300px"><col><col width="300px"><col>';
		
	sOut += '<tr><td class="center-cell th-cell-gray">직책:</td><td>'+ data.duty +'</td>';
	sOut += '<td class="center-cell th-cell-gray">계급:</td><td>'+ data.rank +'</td></tr>';	

	sOut += '<tr><td class="center-cell th-cell-gray">IP:</td><td>'+ data.ipAddr +'</td>';
	sOut += '<td class="center-cell th-cell-gray">MAC:</td><td>'+ data.macAddr +'</td></tr>';	

	sOut += '<tr><td class="center-cell th-cell-gray">PC명:</td><td>'+ data.pcName +'</td>';
	sOut += '<td class="center-cell th-cell-gray"></td><td></td></tr>';	

	
	sOut += '</table>';
	
	return sOut;
}

function getApplyPolicyDetailItem(data){
	var sOut = "";
	
	sOut += '<div class="panel-body">'
	sOut += '<div class="row">'
	sOut += '<div class="col-md-12" style="overflow: hidden;">'
	
	sOut += '<div class="aside-tabs">';
	
	sOut += '<ul class="nav nav-tabs"><li class="active"><a href="#ttab1_nobg'+ data.userNo +'" data-toggle="tab">정책정보</a>';
	sOut += '</li><li class=""><a href="#ttab2_nobg'+ data.userNo +'" data-toggle="tab">사용자정보</a></li></ul>'
	sOut += '</div>'
	
	sOut += '<div class="panel-body"><div class="tab-content transparent">'

		sOut += '<div id="ttab1_nobg'+ data.userNo +'" class="tab-pane active">'
		sOut += getApplyPolicyData(data);
		sOut += '</div>'
			
		sOut += '<div id="ttab2_nobg'+ data.userNo +'" class="tab-pane">'

		sOut += getApplyUserData(data);
		sOut += '</div>'
		
	sOut += '</div></div>'
		
	
	sOut += '</div>'
	sOut += '</div>'
	sOut += '</div>'
	
	return sOut;
}

function getPolicyIcon(data){
	
	var sOut = '';
	var now = moment(new Date());
	
	
	if (now.isAfter(moment(data.isUninstall_temporarily.split(";")[0]))) {
		if(data.isUninstall == true){
			sOut += '<i class="fa fa-trash policy_icon" style="color:#edb000" title="(유효기간 만료) 에이전트삭제 가능"></i>';
		}else{
			sOut += '<i class="fa fa-trash policy_icon" style="color:#edb000" title="(유효기간 만료) 에이전트 삭제 불가능"></i>';
		}
	}else if(data.isUninstall == true){
		sOut += '<i class="fa fa-trash policy_icon" style="color:#7ed67e" title="에이전트삭제가능"></i>';
	}else{
		sOut += '<i class="fa fa-trash policy_icon" style="color:#ea6a66" title="에이전트삭제불가능"></i>';
	}
	
	if (now.isAfter(moment(data.isFileEncryption_temporarily.split(";")[0]))) {
		if(data.isFileEncryption == true){
			sOut += '<i class="fa fa-file policy_icon" aria-hidden="true" style="color:#edb000" title="파일암호화사용"></i>';
		}else{
			sOut += '<i class="fa fa-file policy_icon" aria-hidden="true" style="color:#edb000" title="파일암호화미사용"></i>';
		}
	} else if(data.isFileEncryption == true){
		sOut += '<i class="fa fa-file policy_icon" aria-hidden="true" style="color:#7ed67e" title="파일암호화사용"></i>';
	}else{
		sOut += '<i class="fa fa-file policy_icon" aria-hidden="true" style="color:#ea6a66" title="파일암호화미사용"></i>';
	}
	
	if (now.isAfter(moment(data.isCdEnabled_temporarily.split(";")[0]))) {
		if(data.isCdEnabled == true){
			sOut += '<i class="fa fa-database policy_icon" aria-hidden="true" style="color:#edb000" title="CD사용"></i>';
		}else{
			sOut += '<i class="fa fa-database policy_icon" aria-hidden="true" style="color:#edb000" title="CD미사용"></i>';
		}
	} else if(data.isCdEnabled == true){
		sOut += '<i class="fa fa-database policy_icon" aria-hidden="true" style="color:#7ed67e" title="CD사용"></i>';
	}else{
		sOut += '<i class="fa fa-database policy_icon" aria-hidden="true" style="color:#ea6a66" title="CD미사용"></i>';
	}
	
//	if(data.isCdExport == true){
//		sOut += '<i class="fa fa-minus-circle policy_icon" aria-hidden="true" style="color:#7ed67e" title="CD반출가능"></i>';
//	}else{
//		sOut += '<i class="fa fa-minus-circle policy_icon" aria-hidden="true" style="color:#ea6a66" title="CD반출불가능"></i>';
//	}
//	if(data.isCdEncryption == true){
//		sOut += '<i class="fa fa-get-pocket policy_icon" aria-hidden="true" style="color:#7ed67e" title="CD암호화사용"></i>'
//	}else{
//		sOut += '<i class="fa fa-get-pocket policy_icon" aria-hidden="true" style="color:#ea6a66" title="CD암호화미사용"></i>';
//	}
	
	if (now.isAfter(moment(data.isPrint_temporarily.split(";")[0]))) {
		if(data.isPrint == true){
			sOut += '<i class="fa fa-print policy_icon" style="color:#edb000" title="프린터사용가능"></i>';
		}else{
			sOut += '<i class="fa fa-print policy_icon" style="color:#edb000" title="프린터사용불가능"></i>';
		}		
	}else if(data.isPrint == true){
		sOut += '<i class="fa fa-print policy_icon" style="color:#7ed67e" title="프린터사용가능"></i>';
	}else{
		sOut += '<i class="fa fa-print policy_icon" style="color:#ea6a66" title="프린터사용불가능"></i>';
	}
	
	if (now.isAfter(moment(data.isWlan_temporarily.split(";")[0]))) {
		if(data.isWlan == true){
			sOut += '<i class="fa fa-wifi policy_icon" style="color:#edb000" title="무선랜사용"></i>';
		}else{
			sOut += '<i class="fa fa-wifi policy_icon" style="color:#edb000" title="무선랜미사용"></i>';
		}		
	} else if(data.isWlan == true){
		sOut += '<i class="fa fa-wifi policy_icon" style="color:#7ed67e" title="무선랜사용"></i>';
	}else{
		sOut += '<i class="fa fa-wifi policy_icon" style="color:#ea6a66" title="무선랜미사용"></i>';
	}
	
	if (now.isAfter(moment(data.isNetShare_temporarily.split(";")[0]))) {
		if(data.isNetShare == true){
			sOut += '<i class="fa fa-share-alt policy_icon" style="color:#edb000" title="공유폴더사용"></i>';
		}else{
			sOut += '<i class="fa fa-share-alt policy_icon" style="color:#edb000" title="공유폴더미사용"></i>';
		}
	} else if(data.isNetShare == true){
		sOut += '<i class="fa fa-share-alt policy_icon" style="color:#7ed67e" title="공유폴더사용"></i>';
	}else{
		sOut += '<i class="fa fa-share-alt policy_icon" style="color:#ea6a66" title="공유폴더미사용"></i>';
	}
	
	if (now.isAfter(moment(data.isWebExport_temporarily.split(";")[0]))) {
		if(data.isWebExport == true){
			sOut += '<i class="fa fa-envelope policy_icon" style="color:#edb000" title="웹브라우저로 파일 반출 사용"></i>';	
		}else{
			sOut += '<i class="fa fa-envelope policy_icon" style="color:#edb000" title="웹브라우저로 파일 반출 사용 미여부"></i>';
		}
	} else if(data.isWebExport == true){
		sOut += '<i class="fa fa-envelope policy_icon" style="color:#7ed67e" title="웹브라우저로 파일 반출 사용"></i>';	
	}else{
		sOut += '<i class="fa fa-envelope policy_icon" style="color:#ea6a66" title="웹브라우저로 파일 반출 사용 미여부"></i>';
	}
	
	if (now.isAfter(moment(data.patternFileControl_temporarily.split(";")[0]))) {
		if(data.patternFileControl == 1){
			sOut += '<i class="fa fa-file-powerpoint-o policy_icon" style="color:#edb000" title="개인정보가 검출된 파일 삭제"></i>';		
		}else{
			sOut += '<i class="fa fa-file-powerpoint-o policy_icon" style="color:#edb000" title="개인정보가 검출된 파일 격리"></i>';	
		}	
	} else if(data.patternFileControl == 1){
		sOut += '<i class="fa fa-file-powerpoint-o policy_icon" style="color:#7ed67e" title="개인정보가 검출된 파일 삭제"></i>';		
	}else{
		sOut += '<i class="fa fa-file-powerpoint-o policy_icon" style="color:#ea6a66" title="개인정보가 검출된 파일 격리"></i>';	
	}	
	
	if (now.isAfter(moment(data.isSensitiveDirEnabled_temporarily.split(";")[0]))) {
		if(data.isSensitiveDirEnabled == true){
			sOut += '<i class="fa fa-folder-open policy_icon" style="color:#edb000" title="보호폴더접근가능"></i>';	
		}else{
			sOut += '<i class="fa fa-folder-open policy_icon" style="color:#edb000" title="보호폴더접근불가능"></i>';		
		}
	} else if(data.isSensitiveDirEnabled == true){
		sOut += '<i class="fa fa-folder-open policy_icon" style="color:#7ed67e" title="보호폴더접근가능"></i>';	
	}else{
		sOut += '<i class="fa fa-folder-open policy_icon" style="color:#ea6a66" title="보호폴더접근불가능"></i>';		
	}
	
//	if(data.isSensitiveFileAccess == true){
//		sOut += '<i class="fa fa-file-archive-o policy_icon" style="color:#7ed67e" title="민감파일접근시삭제"></i>';		
//	}else{
//		sOut += '<i class="fa fa-file-archive-o policy_icon" style="color:#ea6a66" title="민감파일접근시보호폴더로이동"></i>';	
//	}

	if (now.isAfter(moment(data.isStorageExport_temporarily.split(";")[0]))) {
		if(data.isStorageExport == true){
			sOut += '<i class="fa fa-archive policy_icon" style="color:#edb000" title="디스크반출가능"></i>';	
		}else{
			sOut += '<i class="fa fa-archive policy_icon" style="color:#edb000" title="디스크반출불가능"></i>';		
		}
	} else if(data.isStorageExport == true){
		sOut += '<i class="fa fa-archive policy_icon" style="color:#7ed67e" title="디스크반출가능"></i>';	
	}else{
		sOut += '<i class="fa fa-archive policy_icon" style="color:#ea6a66" title="디스크반출불가능"></i>';		
	}
	
	if (now.isAfter(moment(data.isStorageAdmin_temporarily.split(";")[0]))) {
		if(data.isStorageAdmin == true){
			sOut += '<i class="fa fa-id-card policy_icon" style="color:#edb000" title="디스크관리가능"></i>';		
		}else{
			sOut += '<i class="fa fa-id-card policy_icon" style="color:#edb000" title="디스크관리불가"></i>';	
		}
	} else if(data.isStorageAdmin == true){
		sOut += '<i class="fa fa-id-card policy_icon" style="color:#7ed67e" title="디스크관리가능"></i>';		
	}else{
		sOut += '<i class="fa fa-id-card policy_icon" style="color:#ea6a66" title="디스크관리불가"></i>';	
	}
	
//	if(data.isUsbControlEnabled == true){
//		sOut += '<i class="fa fa-cogs policy_icon" style="color:#7ed67e" title="USB통제기능사용"></i>';	
//	}else{
//		sOut += '<i class="fa fa-cogs policy_icon" style="color:#ea6a66" title="USB통제기능미사용"></i>';		
//	}
	
	if (now.isAfter(moment(data.isUsbBlock_temporarily.split(";")[0]))) {
		if(data.isWatermark == true){
			sOut += '<i class="fab fa-usb policy_icon" style="color:#edb000" title="USB포트차단"></i>';
		}else{
			sOut += '<i class="fab fa-usb policy_icon" style="color:#edb000" title="USB포트허용"></i>';
		}		
	} else if(data.isUsbBlock == true){
		sOut += '<i class="fab fa-usb policy_icon" style="color:#ea6a66" title="USB포트차단"></i>';
	}else{
		sOut += '<i class="fab fa-usb policy_icon" style="color:#7ed67e" title="USB포트허용"></i>';
	}
	
	if (now.isAfter(moment(data.isComPortBlock_temporarily.split(";")[0]))) {
		if(data.isComPortBlock == true){
			sOut += '<i class="fa fa-plug policy_icon" style="color:#edb000" title="시리얼포트차단"></i>';
		}else{
			sOut += '<i class="fa fa-plug policy_icon" style="color:#edb000" title="시리얼포트허용"></i>';
		}
	} else if(data.isComPortBlock == true){
		sOut += '<i class="fa fa-plug policy_icon" style="color:#ea6a66" title="시리얼포트차단"></i>';
	}else{
		sOut += '<i class="fa fa-plug policy_icon" style="color:#7ed67e" title="시리얼포트허용"></i>';
	}
	
	if (now.isAfter(moment(data.isNetPortBlock_temporarily.split(";")[0]))) {
		if(data.isNetPortBlock == true){
			sOut += '<i class="fa fa-sitemap policy_icon" aria-hidden="true" style="color:#edb000" title="네트워크포트차단"></i>';	
		}else{
			sOut += '<i class="fa fa-sitemap policy_icon" aria-hidden="true" style="color:#edb000" title="네트워크포트허용"></i>';
		}
	} else if(data.isNetPortBlock == true){
		sOut += '<i class="fa fa-sitemap policy_icon" aria-hidden="true" style="color:#ea6a66" title="네트워크포트차단"></i>';	
	}else{
		sOut += '<i class="fa fa-sitemap policy_icon" aria-hidden="true" style="color:#7ed67e" title="네트워크포트허용"></i>';
	}
	
	if (now.isAfter(moment(data.isProcessList_temporarily.split(";")[0]))) {
		if(data.isProcessList == true){ 
			sOut += '<i class="fa fa-desktop policy_icon" aria-hidden="true" style="color:#edb000" title="프로세스사용차단"></i>';
		}else{
			sOut += '<i class="fa fa-desktop policy_icon" aria-hidden="true" style="color:#edb000" title="프로세스사용허용"></i>';	
		}
	} else if(data.isProcessList == true){ 
		sOut += '<i class="fa fa-desktop policy_icon" aria-hidden="true" style="color:#ea6a66" title="프로세스사용차단"></i>';
	}else{
		sOut += '<i class="fa fa-desktop policy_icon" aria-hidden="true" style="color:#7ed67e" title="프로세스사용허용"></i>';	
	}
	
	if (now.isAfter(moment(data.isFilePattern_temporarily.split(";")[0]))) {
		if(data.isFilePattern == true){
			sOut += '<i class="fa fa-clone policy_icon" aria-hidden="true" style="color:#edb000" title="민감정보파일차단"></i>';
		}else{
			sOut += '<i class="fa fa-clone policy_icon" aria-hidden="true" style="color:#edb000" title="민감정보파일허용"></i>';
		}
	} else if(data.isFilePattern == true){
		sOut += '<i class="fa fa-clone policy_icon" aria-hidden="true" style="color:#ea6a66" title="민감정보파일차단"></i>';
	}else{
		sOut += '<i class="fa fa-clone policy_icon" aria-hidden="true" style="color:#7ed67e" title="민감정보파일허용"></i>';
	}
	
	if (now.isAfter(moment(data.isWebAddr_temporarily.split(";")[0]))) {
		if(data.isWebAddr == true){
			sOut += '<i class="fab fa-internet-explorer policy_icon" aria-hidden="true" style="color:#edb000" title="사이트차단"></i>';
		}else{
			sOut += '<i class="fab fa-internet-explorer policy_icon" aria-hidden="true" style="color:#edb000" title="사이트허용"></i>';	
		}
	} else if(data.isWebAddr == true){
		sOut += '<i class="fab fa-internet-explorer policy_icon" aria-hidden="true" style="color:#ea6a66" title="사이트차단"></i>';
	}else{
		sOut += '<i class="fab fa-internet-explorer policy_icon" aria-hidden="true" style="color:#7ed67e" title="사이트허용"></i>';	
	}
	
	if (now.isAfter(moment(data.isMsgBlock_temporarily.split(";")[0]))) {
		if(data.isMsgBlock == true){
			sOut += '<i class="fa fa-commenting policy_icon" aria-hidden="true" style="color:#edb000" title="메신저차단"></i>';
		}else{
			sOut += '<i class="fa fa-commenting policy_icon" aria-hidden="true" style="color:#edb000" title="메신저허용"></i>';
		}
	} else if(data.isMsgBlock == true){
		sOut += '<i class="fa fa-commenting policy_icon" aria-hidden="true" style="color:#ea6a66" title="메신저차단"></i>';
	}else{
		sOut += '<i class="fa fa-commenting policy_icon" aria-hidden="true" style="color:#7ed67e" title="메신저허용"></i>';
	}
	
	if (now.isAfter(moment(data.isWatermark_temporarily.split(";")[0]))) {
		if(data.isWatermark == true){
			sOut += '<i class="fa fa-tint policy_icon" style="color:#edb000" title="워터마크출력"></i>';
		}else{
			sOut += '<i class="fa fa-tint policy_icon" style="color:#edb000" title="워터마크미출력"></i>';
		}		
	} else if(data.isWatermark == true){
		sOut += '<i class="fa fa-tint policy_icon" style="color:#7ed67e" title="워터마크출력"></i>';
	}else{
		sOut += '<i class="fa fa-tint policy_icon" style="color:#ea6a66" title="워터마크미출력"></i>';
	}
	
	return sOut;

}

function getRequestPolicyDetailTable(data){
	var sOut = '';
	/*sOut += '<button type="button" class="btn btn-blue pull-right" onClick="javascript:fn_permit_request_policy(' + data.oldPolicy.policyNo + ','+ data.requestNo +');" style="margin:0 0 8px 0px;">요청정책승인</button>';*/
	
	sOut += '<table class="table table-bordered">';			
	sOut += '<col width="15%"><col width="35%"><col width="15%"><col width="35%">';
	sOut += '<tr><td class="center-cell th-cell-gray"><b>요청사유</b></td><td colspan="3" >'+ data.reqNotice +'</td></tr>';
	
	sOut += '<tr><td class="center-cell th-cell-gray"><b>정책명</b></td><td class="center-cell th-cell-gray"><b>기존 및 요청 정책</b></td>';
	sOut +=	'<td class="center-cell th-cell-gray"><b>정책명</b></td><td class="center-cell th-cell-gray"><b>기존 및 요청 정책</b></td></tr>';
		
	if(data.oldPolicy.isUninstall == true){
		sOut += '<tr><td class="center-cell th-cell-gray">에이전트 삭제 가능 여부:</td><td>가능';
		if (data.isUninstall == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>불가능</span>';
		}
		sOut += '</td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">에이전트 삭제 가능 여부:</td><td>불가능';	
		if (data.isUninstall == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>가능</span>';
		}
		sOut += '</td>';
	}

	if(data.oldPolicy.isFileEncryption == true){
		sOut += '<td class="center-cell th-cell-gray">파일 암호화 사용:</td><td>사용';
		if (data.isFileEncryption == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
		}
		sOut += '</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">파일 암호화 사용:</td><td>미사용';
		if (data.isFileEncryption == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용</span>';
		}
		sOut += '</td></tr>';			
	}
	
	if(data.oldPolicy.isCdEnabled == true){
		sOut += '<tr><td class="center-cell th-cell-gray">CD 사용 여부:</td><td>사용';
		if (data.isCdEnabled == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
		}
		sOut += '</td>';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">CD 사용 여부:</td><td>미사용';
		if (data.isCdEnabled == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용</span>';
		}
		sOut += '</td>';			
	}
	
//	if(data.oldPolicy.isCdExport == true){
//		sOut += '<td class="center-cell th-cell-gray">CD 반출 가능 여부:</td><td>가능';
//		if (data.isCdExport == false) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>불가능</span>';
//		}
//		sOut += '</td></tr>';		
//	}else{
//		sOut += '<td class="center-cell th-cell-gray">CD 반출 가능 여부:</td><td>불가능';
//		if (data.isCdExport == true) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>가능</span>';
//		}
//		sOut += '</td></tr>';	
//	}
//	
//	if(data.oldPolicy.isCdEncryption == true){
//		sOut += '<tr><td class="center-cell th-cell-gray">CD 암호화 사용</td><td>사용';
//		if (data.isCdEncryption == false) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
//		}
//		sOut += '</td>';
//	}else{
//		sOut += '<tr><td class="center-cell th-cell-gray">CD 암호화 사용</td><td>미사용';
//		if (data.isCdEncryption == true) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용</span>';
//		}
//		sOut += '</td>';		
//	}
	
	if(data.oldPolicy.isPrint == true){
		sOut += '<td class="center-cell th-cell-gray">프린터 사용 가능 여부:</td><td>가능';
		if (data.isPrint == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>불가능</span>';
		}
		sOut += '</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">프린터 사용 가능 여부:</td><td>불가능';
		if (data.isPrint == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>가능</span>';
		}
		sOut += '</td></tr>';			
	}

	
	if(data.oldPolicy.printLogDesc == 0){
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>로그전송안함';
		if (data.printLogDesc != 0) {
			var strPLD = data.printLogDesc == 1? '이벤트로그' : '파일원본로그';
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>'+ strPLD +'</span>';
		}
		sOut += '</td></tr>';	
	}else if (data.oldPolicy.printLogDesc == 1) {
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>이벤트로그';
		if (data.printLogDesc != 1) {
			var strPLD = data.printLogDesc == 2? '파일원본로그' : '로그전송안함';
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>'+ strPLD +'</span>';
		}	 
		sOut += '</td></tr>';			
	} else if (data.oldPolicy.printLogDesc == 2) {
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>파일원본로그';
		if (data.printLogDesc != 2) {
			var strPLD = data.printLogDesc == 0? '로그전송안함' : '이벤트로그';
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>'+ strPLD +'</span>';
		}
		sOut += '</td></tr>';		
	}
	
	if(data.oldPolicy.isWlan == true){
		sOut += '<tr><td class="center-cell th-cell-gray">무선랜 사용 여부:</td><td>사용';
		if (data.isWlan == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
		}
		sOut += '</td>';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">무선랜 사용 여부:</td><td>미사용';
		if (data.isWlan == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용</span>';
		}
		sOut += '</td>';	
	}
	
	if(data.oldPolicy.isNetShare == true){
		sOut += '<td class="center-cell th-cell-gray">공유폴더 사용 여부:</td><td>사용';
		if (data.isNetShare == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
		}
		sOut += '</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">공유폴더 사용 여부:</td><td>미사용';
		if (data.isNetShare == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용</span>';
		}
		sOut += '</td></tr>';	
	}
	
	if(data.oldPolicy.isWebExport == true){
		sOut += '<tr><td class="center-cell th-cell-gray">웹브라우저로 파일 반출 사용 여부:</td><td>사용';
		if (data.isWebExport == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
		}
		sOut += '</td>';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">웹브라우저로 파일 반출 사용 여부:</td><td>미사용';
		if (data.isWebExport == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용</span>';
		}
		sOut += '</td>';	
	}
	
	if(data.oldPolicy.patternFileControl == 1){
		sOut += '<tr><td class="center-cell th-cell-gray">검출된 패턴파일 삭제 여부:</td><td>삭제';
		if (data.patternFileControl != 1) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>격리</span>';
		}
		sOut += '</td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">검출된 패턴파일 삭제 여부:</td><td>격리';
		if (data.patternFileControl == 1) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>삭제</span>';
		}
		sOut += '</td>';
	}
	
	if(data.oldPolicy.isSensitiveDirEnabled == true){
		sOut += '<td class="center-cell th-cell-gray">보호폴더 접근 가능 여부:</td><td>가능';	
		if (data.isSensitiveDirEnabled == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>불가능</span>';
		}
		sOut += '</td></tr>';
	}else{
		sOut += '<td class="center-cell th-cell-gray">보호폴더 접근 가능 여부:</td><td>불가능';
		if (data.isSensitiveDirEnabled == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>가능</span>';
		}
		sOut += '</td></tr>';
	}
	
//	if(data.oldPolicy.isSensitiveFileAccess == true){
//		sOut += '<tr><td class="center-cell th-cell-gray">민감파일 접근시 삭제 여부:</td><td>삭제';
//		if (data.isSensitiveFileAccess == false) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>보호폴더로 이동</span>';
//		}
//		sOut += '</td>';
//	}else{
//		sOut += '<tr><td class="center-cell th-cell-gray">민감파일 접근시 삭제 여부:</td><td>보호폴더로 이동';
//		if (data.isSensitiveFileAccess == true) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>삭제</span>';
//		}
//		sOut += '</td>';
//	}
	
	if(data.oldPolicy.isStorageExport == true){
		sOut += '<td class="center-cell th-cell-gray">디스크 반출 기능 사용 여부:</td><td>가능';
		if (data.isStorageExport == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>불가능</span>';
		}
		sOut += '</td></tr>';
	}else{
		sOut += '<td class="center-cell th-cell-gray">디스크 반출 기능 사용 여부:</td><td>불가능';
		if (data.isStorageExport == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>가능</span>';
		}
		sOut += '</td></tr>';
	}
	
	if(data.oldPolicy.isStorageAdmin == true){
		sOut += '<tr><td class="center-cell th-cell-gray">디스크 관리 기능 사용 여부:</td><td>관리자';
		if (data.isStorageAdmin == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>일반</span>';
		}
		sOut += '</td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">디스크 관리 기능 사용 여부:</td><td>일반';
		if (data.isStorageAdmin == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>관리자</span>';
		}
		sOut += '</td>';
	}
	
//	if(data.oldPolicy.isUsbControlEnabled == true){
//		sOut += '<td class="center-cell th-cell-gray">USB통제 기능 사용 여부:</td><td>사용';
//		if (data.isUsbControlEnabled == false) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
//		}
//		sOut += '</td></tr>';
//	}else{
//		sOut += '<td class="center-cell th-cell-gray">USB통제 기능 사용 여부:</td><td>미사용';
//		if (data.isUsbControlEnabled == true) {
//			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용</span>';
//		}
//		sOut += '</td></tr>';
//	}
	
	if(data.oldPolicy.isUsbBlock == true){
		sOut += '<td class="center-cell th-cell-gray">USB포트사용여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isUsbBlock\'' + ', \''+ data.oldPolicy.usbBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isUsbBlock == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isUsbBlock\'' + ', \''+ data.usbBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">USB포트사용여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isUsbBlock\'' + ', \''+ data.oldPolicy.usbBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isUsbBlock == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isUsbBlock\'' + ', \''+ data.usbBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td></tr>';		
	}
	
	if(data.oldPolicy.isComPortBlock == true){
		sOut += '<tr><td class="center-cell th-cell-gray">시리얼포트사용여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isComPortBlock\'' + ', \''+ data.oldPolicy.comPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isComPortBlock == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isComPortBlock\'' + ', \''+ data.comPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">시리얼포트사용여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isComPortBlock\'' + ', \''+ data.oldPolicy.comPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isComPortBlock == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isComPortBlock\'' + ', \''+ data.comPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td>';		
	}
	
	if(data.oldPolicy.isNetPortBlock == true){
		sOut += '<td class="center-cell th-cell-gray">네트워크포트사용여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isNetPortBlock\'' + ', \''+ data.oldPolicy.netPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isNetPortBlock == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isNetPortBlock\'' + ', \''+ data.netPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">네트워크포트사용여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isNetPortBlock\'' + ', \''+ data.oldPolicy.netPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isNetPortBlock == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isNetPortBlock\'' + ', \''+ data.netPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td></tr>';			
	}
	
	if(data.oldPolicy.isProcessList == true){
		sOut += '<tr><td class="center-cell th-cell-gray">프로세스차단여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isProcessList\'' + ', \''+ data.oldPolicy.processListCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isProcessList == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>허용</span>';
		}
		sOut += '</td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">프로세스차단여부:</td><td>허용'; 
		if (data.isProcessList == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isProcessList\'' + ', \''+ data.processListCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td>';	
	}
	
	if(data.oldPolicy.isFilePattern == true){
		sOut += '<td class="center-cell th-cell-gray">민감패턴차단여부:</td><td>차단&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isFilePattern\'' + ', \''+ data.oldPolicy.filePatternCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isFilePattern == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>허용</span>';
		}
		sOut += '</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">민감패턴차단여부:</td><td>허용'; 
		if (data.isFilePattern == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>차단&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isFilePattern\'' + ', \''+ data.filePatternCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		} 
		sOut += '</td></tr>';	
	}
	
	if(data.oldPolicy.isWebAddr == true){
		sOut += '<tr><td class="center-cell th-cell-gray">사이트차단여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWebAddr\'' + ', \''+ data.oldPolicy.webAddrCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isWebAddr == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWebAddr\'' + ', \''+ data.webAddrCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td>';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">사이트차단여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWebAddr\'' + ', \''+ data.oldPolicy.webAddrCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isWebAddr == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWebAddr\'' + ', \''+ data.webAddrCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		} 
		sOut += '</td>';			
	}
	
	if(data.oldPolicy.isMsgBlock == true){
		sOut += '<td class="center-cell th-cell-gray">메신저차단여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isMsgBlock\'' + ', \''+ data.oldPolicy.msgBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isMsgBlock == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isMsgBlock\'' + ', \''+ data.msgBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		} 
		sOut += '</td></tr>';
	}else{
		sOut += '<td class="center-cell th-cell-gray">메신저차단여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isMsgBlock\'' + ', \''+ data.oldPolicy.msgBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isMsgBlock == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isMsgBlock\'' + ', \''+ data.msgBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		} 
		sOut += '</td></tr>';
	}
	
	if(data.oldPolicy.isWatermark == true){
		sOut += '<tr><td class="center-cell th-cell-gray">워터마크사용여부:</td><td>사용&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWatermark\'' + ', \''+ data.oldPolicy.watermarkCode +'\');" ><i class="fa fa-search"></i> 상세</a>';
		if (data.isWatermark == false) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>미사용</span>';
		}
		sOut += '</td>';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">워터마크사용여부:</td><td>미사용';
		if (data.isWatermark == true) {
			sOut += '<span><i class="fa fa-arrow-right" aria-hidden="true" style="margin:0 10px; color:#fb827a;"></i>사용&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWatermark\'' + ', \''+ data.watermarkCode +'\');" ><i class="fa fa-search"></i> 상세</a></span>';
		}
		sOut += '</td>';			
	}
	
	sOut += '</table>';
	
	return sOut;

}

function completeAlert(){
	vex.defaultOptions.className = 'vex-theme-os'
		
	vex.dialog.open({
		message: '완료되었습니다.',
		  buttons: [
		    $.extend({}, vex.dialog.buttons.YES, {
		      text: '확인'
		  })]
	})
}

function failAlert(){
	vex.defaultOptions.className = 'vex-theme-os'
		
		vex.dialog.open({
			message: '서버와의 통신에 문제가 발생하였습니다.',
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
}

function infoAlert(str){
	vex.defaultOptions.className = 'vex-theme-os'
		
		vex.dialog.open({
			message: str,
			  buttons: [
			    $.extend({}, vex.dialog.buttons.YES, {
			      text: '확인'
			  })]
		})
}