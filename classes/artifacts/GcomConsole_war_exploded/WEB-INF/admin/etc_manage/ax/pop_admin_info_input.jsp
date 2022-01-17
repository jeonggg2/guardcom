<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="modalAdminInfo" class="modal fade" role="dialog" aria-hidden="true" style="margin-top: 10%;">
	<div class="modal-dialog" style="width:940px;">
	<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>

			<c:if test="${popup_type == 'modify'}">
					<h4 class="modal-title" id="myModalLabel">관리자 정보 수정</h4>
			</c:if>
			<c:if test="${popup_type == 'create'}">
					<h4 class="modal-title" id="myModalLabel">관리자 추가</h4>
			</c:if>

				</div>
				<!-- /Modal Header -->
				<input type="text" name="filterUserId" id="admin_no" value="${adminInfo.adminNo}" class="form-control hidden">			
				
				<!-- Modal body -->
				<div class="modal-body">
					<div id="content" class="dashboard padding-20">
						<div class="row">
							
							<div class="col-md-12">
								<div id="panel-2" class="panel panel-default">
							
									<div class="panel-heading">
										<span class="title elipsis">
											<strong>정보입력</strong> <!-- panel title -->
										</span>
									</div>
		
									<!-- panel content -->
									<div class="panel-body">
										<div class="row">
										
											<div class="col-md-6" style="overflow: hidden;">
												<label>부서</label><br />
							                         <select class="form-control select2" id="admin_dept" style="width:100%">
				                              
				                              
<c:choose>
	<c:when test="${fn:length(deptList) > 0}">
		<c:forEach items="${deptList}" var="item">
			<c:choose>
				<c:when test="${adminInfo.dept_no == item.deptNo}">
					<option value="${item.deptNo }" selected>${item.shortName }</option>				
				</c:when>
				<c:otherwise>
					<option value="${item.deptNo }">${item.shortName }</option>				
				</c:otherwise>
				
			</c:choose>

		</c:forEach>
	</c:when>
	<c:otherwise>
       <option value="-1">선택가능한 부서가 없습니다</option>
	</c:otherwise>
</c:choose>				                              
				                              
				                                </select>
											</div>	
											<div class="col-md-6" style="overflow: hidden;">
												<label>아이디</label>
												<input type="text" name="filterUserId" id="admin_id" value="${adminInfo.adminId}" class="form-control required">			
											</div>	
										</div>
				                         <br />
										<div class="row">
											<div class="col-md-6" style="overflow: hidden;">
												<label>접속IP 1</label>
												<input type="text" class="form-control ip_address" value="${adminInfo.ipAddr}" id="admin_ip0" placeholder="주 IP주소를 입력하십시오">
											</div>	
											<div class="col-md-6" style="overflow: hidden;">
												<label>접속IP 2</label>
												<input type="text" class="form-control ip_address" value="${adminInfo.ipAddr1}" id="admin_ip1" placeholder="부 IP주소를 입력하십시오">
				                                <br />
											</div>	
										</div>										

										<div class="row">
											<div class="col-md-6" style="overflow: hidden;">
												<label>패스워드</label>
												<input type="password" name="filterUserId" id="admin_pw1" value="" class="form-control">			
				                                <br />
											</div>	
											<div class="col-md-6" style="overflow: hidden;">
												<label>패스워드 재입력</label>
												<input type="password" name="filterUserId" id="admin_pw2" value="" class="form-control">			
											</div>	
										</div>
										<div class="row">
											<div class="col-md-12" style="overflow: hidden;">
												<label>권한</label>
							                         <select class="form-control" id="admin_auth">
					<option value="0" ${adminInfo.adminMode == 0 ? 'selected' : ''} >콘솔/레포트 권한</option>				
					<option value="1" ${adminInfo.adminMode == 1 ? 'selected' : ''} >콘솔 권한</option>				
					<option value="2" ${adminInfo.adminMode == 2 ? 'selected' : ''} >레포트권한</option>				
				                              
				                                </select>
				                                <br />
											</div>	
										</div>

								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /Modal body -->
			<c:if test="${popup_type == 'modify'}">
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="fn_admin_modify();" ><i class="fa fa-check"></i> 관리자수정</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</c:if>
			<c:if test="${popup_type == 'create'}">
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="fn_admin_create();" ><i class="fa fa-check"></i> 관리자생성</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</c:if>

			</div>
		</div>
	</div>
</div>
<%-- 		<script type="text/javascript" src="${context}/assets/js/app.js"></script> --%>
		<script type="text/javascript" src="${context}/assets/plugins/form.masked/jquery.mask.min.js"></script>
<script type="text/javascript">
var options =  { 
       onKeyPress: function(cep, event, currentField, options){
            if(cep){
              var ipArray = cep.split(".");
              var lastValue = ipArray[ipArray.length-1];
              if(lastValue !== "" && parseInt(lastValue) > 255){
            	  var resultingValue = ipArray.join(".");
                  currentField.attr('value',resultingValue);
              }
        }             
    }};

$('.ip_address').mask('099.099.099.099',options);

</script>








