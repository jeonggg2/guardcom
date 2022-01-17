function getPolicyDetailTable(data){
	var sOut = '<table class="table table-bordered">';
	
	sOut += '<col width="300px"><col><col width="300px"><col>';
		
	if(data.isUninstall == true){
		sOut += '<tr><td class="center-cell th-cell-gray">에이전트 삭제 가능 여부:</td><td>가능</td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">에이전트 삭제 가능 여부:</td><td>불가능</td>';		
	}

	if(data.isFileEncryption == true){
		sOut += '<td class="center-cell th-cell-gray">파일 암호화 사용:</td><td>사용</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">파일 암호화 사용:</td><td>미사용</td></tr>';			
	}
	
	if(data.isCdEnabled == true){
		sOut += '<tr><td class="center-cell th-cell-gray">CD 사용 여부:</td><td>사용</td>';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">CD 사용 여부:</td><td>미사용</td>';			
	}
	
//	if(data.isCdExport == true){
//		sOut += '<td class="center-cell th-cell-gray">CD 반출 가능 여부:</td><td>가능</td></tr>';		
//	}else{
//		sOut += '<td class="center-cell th-cell-gray">CD 반출 가능 여부:</td><td>불가능</td></tr>';	
//	}
//	
//	if(data.isCdEncryption == true){
//		sOut += '<tr><td class="center-cell th-cell-gray">CD 암호화 사용:</td><td>사용</td>';
//	}else{
//		sOut += '<tr><td class="center-cell th-cell-gray">CD 암호화 사용:</td><td>미사용</td>';		
//	}
	
	if(data.isPrint == true){
		sOut += '<td class="center-cell th-cell-gray">프린터 사용 가능 여부:</td><td>가능</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">프린트 사용 가능 여부:</td><td>불가능</td></tr>';			
	}
	
	if(data.printLogDesc == 0){
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>로그전송안함</td></tr>';	
	}else if (data.printLogDesc == 1) {
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>이벤트로그</td></tr>';			
	} else {
		sOut += '<td class="center-cell th-cell-gray">프린터 인쇄 로그:</td><td>파일원본로그</td></tr>';		
	}
	
	
	if(data.isWlan == true){
		sOut += '<tr><td class="center-cell th-cell-gray">무선랜 사용 여부:</td><td>사용</td>';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">무선랜 사용 여부:</td><td>미사용</td>';	
	}
	
	if(data.isNetShare == true){
		sOut += '<td class="center-cell th-cell-gray">공유폴더 사용 여부:</td><td>사용</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">공유폴더 사용 여부:</td><td>미사용</td></tr>';			
	}
	
	if(data.isWebExport == true){
		sOut += '<tr><td class="center-cell th-cell-gray">웹브라우저로 파일 반출 사용 여부:</td><td>사용</td>';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">웹브라우저로 파일 반출 사용 여부:</td><td>미사용</td>';	
	}
	
	if(data.patternFileControl == 1){
		sOut += '<tr><td class="center-cell th-cell-gray">검출된 패턴파일 삭제 여부:</td><td>삭제</td>';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">검출된 패턴파일 삭제 여부:</td><td>격리</td>';	
	}
	
	if(data.isSensitiveDirEnabled == true){
		sOut += '<td class="center-cell th-cell-gray">보호폴더 접근 가능 여부:</td><td>가능</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">보호폴더 접근 가능 여부:</td><td>불가능</td></tr>';		
	}
	
//	if(data.isSensitiveFileAccess == true){
//		sOut += '<tr><td class="center-cell th-cell-gray">민감파일 접근시 삭제 여부:</td><td>삭제</td>';		
//	}else{
//		sOut += '<tr><td class="center-cell th-cell-gray">민감파일 접근시 삭제 여부:</td><td>보호폴더로 이동</td>';	
//	}
	
	if(data.isStorageExport == true){
		sOut += '<td class="center-cell th-cell-gray">디스크 반출 가능 여부:</td><td>가능</td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">디스크 반출 가능 여부:</td><td>불가능</td></tr>';		
	}
	
	if(data.isStorageAdmin == true){
		sOut += '<tr><td class="center-cell th-cell-gray">디스크 관리자 여부:</td><td>관리자</td>';		
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">디스크 관리자 여부:</td><td>일반</td>';	
	}
	
//	if(data.isUsbControlEnabled == true){
//		sOut += '<td class="center-cell th-cell-gray">USB통제 기능 사용 여부:</td><td>사용</td></tr>';	
//	}else{
//		sOut += '<td class="center-cell th-cell-gray">USB통제 기능 사용 여부:</td><td>미사용</td></tr>';		
//	}
//	

	if(data.isUsbBlock == true){
		sOut += '<td class="center-cell th-cell-gray">USB포트사용여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isUsbBlock\'' + ', \''+ data.usbBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">USB포트사용여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isUsbBlock\'' + ', \''+ data.usbBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';		
	}
	
	if(data.isComPortBlock == true){
		sOut += '<tr><td class="center-cell th-cell-gray">시리얼포트사용여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isComPortBlock\'' + ', \''+ data.comPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">시리얼포트사용여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isComPortBlock\'' + ', \''+ data.comPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';		
	}
	
	if(data.isNetPortBlock == true){
		sOut += '<td class="center-cell th-cell-gray">네트워크포트사용여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isNetPortBlock\'' + ', \''+ data.netPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">네트워크포트사용여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isNetPortBlock\'' + ', \''+ data.netPortBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';			
	}
	
	if(data.isProcessList == true){
		sOut += '<tr><td class="center-cell th-cell-gray">프로세스차단여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isProcessList\'' + ', \''+ data.processListCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">프로세스차단여부:</td><td>허용 </td>';	
	}
	
	if(data.isFilePattern == true){
		sOut += '<td class="center-cell th-cell-gray">민감패턴차단여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isFilePattern\'' + ', \''+ data.filePatternCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';	
	}else{
		sOut += '<td class="center-cell th-cell-gray">민감패턴차단여부:</td><td>허용 </td></tr>';	
	}
	
	if(data.isWebAddr == true){
		sOut += '<tr><td class="center-cell th-cell-gray">사이트차단여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWebAddr\'' + ', \''+ data.webAddrCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">사이트차단여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWebAddr\'' + ', \''+ data.webAddrCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';			
	}
	
	if(data.isMsgBlock == true){
		sOut += '<td class="center-cell th-cell-gray">메신저차단여부:</td><td>차단 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isMsgBlock\'' + ', \''+ data.msgBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';
	}else{
		sOut += '<td class="center-cell th-cell-gray">메신저차단여부:</td><td>허용 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isMsgBlock\'' + ', \''+ data.msgBlockCode +'\');" ><i class="fa fa-search"></i> 상세</a></td></tr>';
	}
	
	if(data.isWatermark == true){
		sOut += '<tr><td class="center-cell th-cell-gray">워터마크사용여부:</td><td>사용&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="javascript:fn_sel_policy_detailOpen(' + '\'isWatermark\'' + ', \''+ data.watermarkCode +'\');" ><i class="fa fa-search"></i> 상세</a></td>';	
	}else{
		sOut += '<tr><td class="center-cell th-cell-gray">워터마크사용여부:</td><td>미사용</td>';			
	}
	
	sOut += '</table>';
	
	return sOut;
}


function getPolicyIcon(data){
	var sOut = '';
	
	if(data.isUninstall == true){
		sOut += '<i class="fa fa-trash policy_icon" style="color:#7ed67e" title="에이전트삭제가능"></i>';
	}else{
		sOut += '<i class="fa fa-trash policy_icon" style="color:#ea6a66" title="에이전트삭제불가능"></i>';
	}

	if(data.isFileEncryption == true){
		sOut += '<i class="fa fa-file policy_icon" aria-hidden="true" style="color:#7ed67e" title="파일암호화사용"></i>';
	}else{
		sOut += '<i class="fa fa-file policy_icon" aria-hidden="true" style="color:#ea6a66" title="파일암호화미사용"></i>';
	}
	if(data.isCdEnabled == true){
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
//	
	if(data.isPrint == true){
		sOut += '<i class="fa fa-print policy_icon" style="color:#7ed67e" title="프린터사용가능"></i>';
	}else{
		sOut += '<i class="fa fa-print policy_icon" style="color:#ea6a66" title="프린터사용불가능"></i>';
	}
	
	
	if(data.isWlan == true){
		sOut += '<i class="fa fa-wifi policy_icon" style="color:#7ed67e" title="무선랜사용"></i>';
	}else{
		sOut += '<i class="fa fa-wifi policy_icon" style="color:#ea6a66" title="무선랜미사용"></i>';
	}
	
	if(data.isNetShare == true){
		sOut += '<i class="fa fa-share-alt policy_icon" style="color:#7ed67e" title="공유폴더사용"></i>';
	}else{
		sOut += '<i class="fa fa-share-alt policy_icon" style="color:#ea6a66" title="공유폴더미사용"></i>';
	}
	
	if(data.isWebExport == true){
		sOut += '<i class="fa fa-envelope policy_icon" style="color:#7ed67e" title="메일반출사용"></i>';	
	}else{
		sOut += '<i class="fa fa-envelope policy_icon" style="color:#ea6a66" title="메일반출미사용"></i>';
	}
	
	if(data.patternFileControl == 1){
		sOut += '<i class="fa fa-file-powerpoint-o policy_icon" style="color:#7ed67e" title="검출된패턴파일삭제"></i>';		
	}else{
		sOut += '<i class="fa fa-file-powerpoint-o policy_icon" style="color:#ea6a66" title="검출된패턴파일격리"></i>';	
	}
	
	
	if(data.isSensitiveDirEnabled == true){
		sOut += '<i class="fa fa-folder-open policy_icon" style="color:#7ed67e" title="보호폴더접근가능"></i>';	
	}else{
		sOut += '<i class="fa fa-folder-open policy_icon" style="color:#ea6a66" title="보호폴더접근불가능"></i>';		
	}
	
//	if(data.isSensitiveFileAccess == true){
//		sOut += '<i class="fa fa-file-archive-o policy_icon" style="color:#7ed67e" title="민감파일접근시삭제"></i>';		
//	}else{
//		sOut += '<i class="fa fa-file-archive-o policy_icon" style="color:#ea6a66" title="민감파일접근시보호폴더로이동"></i>';	
//	}
//	
	if(data.isStorageExport == true){
		sOut += '<i class="fa fa-archive policy_icon" style="color:#7ed67e" title="디스크반출가능"></i>';	
	}else{
		sOut += '<i class="fa fa-archive policy_icon" style="color:#ea6a66" title="디스크반출불가능"></i>';		
	}
	
	if(data.isStorageAdmin == true){
		sOut += '<i class="fa fa-id-card policy_icon" style="color:#7ed67e" title="디스크관리가능"></i>';		
	}else{
		sOut += '<i class="fa fa-id-card policy_icon" style="color:#ea6a66" title="디스크관리불가"></i>';	
	}
	
//	if(data.isUsbControlEnabled == true){
//		sOut += '<i class="fa fa-cogs policy_icon" style="color:#7ed67e" title="USB통제기능사용"></i>';	
//	}else{
//		sOut += '<i class="fa fa-cogs policy_icon" style="color:#ea6a66" title="USB통제기능미사용"></i>';		
//	}
//	

	if(data.isUsbBlock == true){
		sOut += '<i class="fab fab-usb policy_icon" style="color:#ea6a66" title="USB포트차단"></i>';
	}else{
		sOut += '<i class="fab fab-usb policy_icon" style="color:#7ed67e" title="USB포트허용"></i>';
	}
	
	if(data.isComPortBlock == true){
		sOut += '<i class="fa fa-plug policy_icon" style="color:#ea6a66" title="시리얼포트차단"></i>';
	}else{
		sOut += '<i class="fa fa-plug policy_icon" style="color:#7ed67e" title="시리얼포트허용"></i>';
	}
	
	if(data.isNetPortBlock == true){
		sOut += '<i class="fa fa-sitemap policy_icon" aria-hidden="true" style="color:#ea6a66" title="네트워크포트차단"></i>';	
	}else{
		sOut += '<i class="fa fa-sitemap policy_icon" aria-hidden="true" style="color:#7ed67e" title="네트워크포트허용"></i>';
	}
	
	if(data.isProcessList == true){ 
		sOut += '<i class="fa fa-desktop policy_icon" aria-hidden="true" style="color:#ea6a66" title="프로세스사용차단"></i>';
	}else{
		sOut += '<i class="fa fa-desktop policy_icon" aria-hidden="true" style="color:#7ed67e" title="프로세스사용허용"></i>';	
	}
	
	if(data.isFilePattern == true){
		sOut += '<i class="fa fa-clone policy_icon" aria-hidden="true" style="color:#ea6a66" title="민감정보파일차단"></i>';
	}else{
		sOut += '<i class="fa fa-clone policy_icon" aria-hidden="true" style="color:#7ed67e" title="민감정보파일허용"></i>';
	}
	
	if(data.isWebAddr == true){
		sOut += '<i class="fab fa-internet-explorer policy_icon" aria-hidden="true" style="color:#ea6a66" title="사이트차단"></i>';
	}else{
		sOut += '<i class="fab fa-internet-explorer policy_icon" aria-hidden="true" style="color:#7ed67e" title="사이트허용"></i>';	
	}
	
	if(data.isMsgBlock == true){
		sOut += '<i class="fa fa-commenting policy_icon" aria-hidden="true" style="color:#ea6a66" title="메신저차단"></i>';
	}else{
		sOut += '<i class="fa fa-commenting policy_icon" aria-hidden="true" style="color:#7ed67e" title="메신저허용"></i>';
	}
	
	if(data.isWatermark == true){
		sOut += '<i class="fa fa-tint policy_icon" style="color:#7ed67e" title="워터마크출력"></i>';
	}else{
		sOut += '<i class="fa fa-tint policy_icon" style="color:#ea6a66" title="워터마크미출력"></i>';
	}
	
	return sOut;

}