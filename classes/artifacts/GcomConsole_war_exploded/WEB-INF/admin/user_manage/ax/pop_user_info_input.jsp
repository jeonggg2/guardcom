<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="modalUserInfo" class="modal fade" role="dialog" aria-hidden="true" style="margin-top: 10%;">
	<div class="modal-dialog" style="width:940px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>

			<c:if test="${popup_type == 'modify'}">
					<h4 class="modal-title" id="myModalLabel">사용자 정보 수정</h4>
			</c:if>
			<c:if test="${popup_type == 'create'}">
					<h4 class="modal-title" id="myModalLabel">사용자 추가</h4>
			</c:if>

				</div>
				<!-- /Modal Header -->
				<input type="text" name="filterUserId" id="user_no" value="${userInfo.userNo}" class="form-control hidden">			
				
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
							                         <select class="form-control select2" style="width:100%" id="user_dept">
				                              
				                              
<c:choose>
	<c:when test="${fn:length(deptList) > 0}">
		<c:forEach items="${deptList}" var="item">
			<c:choose>
				<c:when test="${userInfo.deptId == item.deptNo}">
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
												<label>이름</label>
												<input type="text" name="filterUserId" id="user_name" value="${userInfo.userName}" class="form-control required">			
											</div>	
										</div>
				                         <br />

										<div class="row">
											<div class="col-md-6" style="overflow: hidden;">
												<label>직책</label>
												<input type="text" name="filterUserId" id="user_duty" value="${userInfo.duty}" class="form-control required">			
				                                <br />
											</div>	
											<div class="col-md-6" style="overflow: hidden;">
												<label>계급</label>
												<input type="text" name="filterUserId" id="user_rank" value="${userInfo.rank}" class="form-control required">			
											</div>	
										</div>

										<div class="row">
											<div class="col-md-6" style="overflow: hidden;">
												<label>아이디</label>
												<input type="text" name="filterUserId" id="user_id" value="${userInfo.userId}" class="form-control required">			
											</div>	
											<div class="col-md-6" style="overflow: hidden;">
												<label>핸드폰번호</label>
												<input type="text" class="form-control" value="${userInfo.phone}" id="user_phone" placeholder="-를 포함하여 번호를입력하여 주세요">
				                                <br />
											</div>	
										</div>
										<div class="row">
											<div class="col-md-6" style="overflow: hidden;">
												<label>패스워드</label>
												<input type="password" name="filterUserId" id="user_password" value="" class="form-control required">			
				                                <br />
											</div>	
											<div class="col-md-6" style="overflow: hidden;">
												<label>패스워드 재입력</label>
												<input type="password" name="filterUserId" id="user_password2" value="" class="form-control required">			
				                                <br />
											</div>	

										</div>
										<div class="row">
											<div class="col-md-6" style="overflow: hidden;">
												<label>사번</label>
												<input type="text" name="filterUserId" id="user_number" value="${userInfo.number}" class="form-control required">			
				                                <br />
											</div>	
											<div class="col-md-6" style="overflow: hidden;">
												<label>보안등급</label>
												<br>
												일반 <input type="radio" name="user_secure_level" id="user_secure_level" value="0" <c:if test="${userInfo.secureLevel == 0}"> <c:out value = "checked" /> </c:if>>
												
												&emsp;대외비취급 <input type="radio" name="user_secure_level" id="user_secure_level" value="1" <c:if test="${userInfo.secureLevel == 1}"> <c:out value = "checked" /> </c:if>>			
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
					<button type="button" class="btn btn-primary" onclick="fn_user_modify();" ><i class="fa fa-check"></i> 사용자수정</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</c:if>
			<c:if test="${popup_type == 'create'}">
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="fn_user_create();" ><i class="fa fa-check"></i> 사용자생성</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times" aria-hidden="true"></i> 닫기</button>
				</div>
			</c:if>

			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

</script>
































