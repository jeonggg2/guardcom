<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- level1 : 사용자단  success -->
<!-- level2 : 에러  danger -->
<!-- level3 : 정책단변경  warning-->

<ul class="list-unstyled list-hover slimscroll height-300" data-slimscroll-visible="true">
<c:choose>
	<c:when test="${fn:length(list) > 0}">
		<c:forEach items="${list }" var="item">
	
	<li>
	
	<c:choose>
		<c:when test="${item.level == 0}">
		<span class="label label-success"><i class="fa fa-user size-15"></i></span>
		</c:when>
		<c:when test="${item.level == 1}">
		<span class="label label-danger"><i class="fa fa-bell-o size-15"></i></span>
		</c:when>
		<c:when test="${item.level == 2}">
		<span class="label label-warning"><i class="fa fa-cogs size-15"></i></span>
		</c:when>	
	</c:choose>
		${item.action } (${item.userName})
	</li>

		</c:forEach>
	</c:when>
	<c:otherwise>
					<tr>
						<td>검색결과가 없습니다.</td>
					</tr>
	</c:otherwise>
</c:choose>
				
</ul>