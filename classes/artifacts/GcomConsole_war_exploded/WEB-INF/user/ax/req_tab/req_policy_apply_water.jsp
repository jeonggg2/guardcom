<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	boolean isWatermark = Boolean.parseBoolean(request.getParameter("isWatermark"));
	String watermarkCode = request.getParameter("watermarkCode").toString();
%>
<script type="text/javascript" src="${context}/assets/js/app.js"></script>
<div>
	<table class="table table-bordered">
		<tr>
			<td class="th-cell-gray center-cell" width="140px" style="vertical-align: middle;">워터마크 출력선택</td>
			<td class="center-cell" style="vertical-align: middle;">
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_water_mark_print" value="Y" <% if (isWatermark){ %> checked <%}%> /><i></i> 출력
				</label>
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_water_mark_print" value="N" <% if (!isWatermark){ %> checked <%}%> /><i></i> 미출력
				</label>
			</td>
			<td class="th-cell-gray center-cell" width="100px" style="vertical-align: middle;">적용 타입</td>
			<td class="center-cell">
				<input type="number" id="att_watermark_type" name="att_waternark_type" class="form-control" value="0" style="width:80px; margin: 0;" value="<%= watermarkCode %>" disabled />
			</td>
		</tr>
	</table>
</div>

<table class="table table-bordered">
	<thead>
		<tr>
			<td>워터마크</td>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
													

<script type="text/javascript">
</script>
































