<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	boolean onlyFlag = Boolean.parseBoolean(request.getParameter("onlyFlag"));
	boolean isWatermark = Boolean.parseBoolean(request.getParameter("isWatermark"));
	String watermarkCode = request.getParameter("watermarkCode").toString();
	String applyCode = watermarkCode.length() > 0 ? "," + watermarkCode : "";
	String scriptCode = onlyFlag? watermarkCode : "";
	
	if (isWatermark){ 
		applyCode = "Y" + applyCode;
	} else {
		applyCode = "N" + applyCode;
	}
%>
<div>
	<table class="table table-bordered">
		<tr>
			<td class="th-cell-gray center-cell" width="110px" style="vertical-align: middle;">워터마크 선택</td>
			<td class="center-cell" style="vertical-align: middle;">
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_watermark" value="Y" <% if (isWatermark){ %> checked <%}%> /><i></i> 사용
				</label>
				<label class="radio nomargin-top nomargin-bottom">
					<input type="radio" name="radio_watermark" value="N" <% if (!isWatermark){ %> checked <%}%>/><i></i> 사용안함
				</label>
			</td>
			<td class="th-cell-gray center-cell" width="75px" style="vertical-align: middle;">적용 상태</td>
			<td class="center-cell" style="vertical-align: middle;">
				<input type="text" id="att_watermark_type" name="att_watermark_type" class="form-control" value="<%= applyCode%>" disabled />
			</td>
		</tr>
	</table>
</div>
													

<script type="text/javascript">

	$(document).ready(function(){
		$("input[name=radio_watermark]").change(function() {
			var chk_value = $(':radio[name="radio_watermark"]:checked').val();
			var type = $('#att_watermark_type').val();		
			if (chk_value == 'Y') {
				if (type == '') {
					$('#att_watermark_type').val(chk_value);
				} else {
					$('#att_watermark_type').val(type.replace('N','Y'));	
				}
			} else {
				if (type == '') {
					$('#att_watermark_type').val(chk_value);
				} else {
					$('#att_watermark_type').val(type.replace('Y','N'));
				}
			}
		});
	});
</script>
































