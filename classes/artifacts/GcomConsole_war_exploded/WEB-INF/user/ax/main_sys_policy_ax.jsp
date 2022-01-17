<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<% 
	String code = request.getAttribute("code").toString();
	List<HashMap<String, Object>> list = (List<HashMap<String, Object>>)request.getAttribute("systemPolicyInfo");
	int list_cnt = list.size();
%>

<% 
if(list_cnt < 1 || list == null) { %>
	<table class="table table-bordered table-striped">
		<tbody>
			<tr><td>해당 정책은 적용되지 않았습니다.</td></tr>
		</tbody>
	</table>
<% 
} else { 
	if("system".equals(code)) { %>
	
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>이름</td>
					<td>값</td>
				</tr>
			</thead>
			<tbody>
				<% for (int i = 0; i < list_cnt; i ++) { %>
				<tr>
					<td><%= list.get(i).get("description") %></td>
					<td><%= list.get(i).get("value") %></td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
<%	} else if("pattern".equals(code)) { %>

		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>패턴이름</td>
					<td>패턴데이터</td>
					<td>설명</td>
				</tr>
			</thead>
			<tbody>
				<% for (int i = 0; i < list_cnt; i ++) { %>
				<tr>
					<td><%= list.get(i).get("description") %></td>
					<td><%= list.get(i).get("data") %></td>
					<td><%= list.get(i).get("notice") %></td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
<%	} else if("netport".equals(code)) { %>

		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>포트번호</td>
					<td>설명</td>
				</tr>
			</thead>
			<tbody>
				<% for (int i = 0; i < list_cnt; i ++) { %>
				<tr>
					<td><%= list.get(i).get("port") %></td>
					<td><%= list.get(i).get("descriptor") %></td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
<%	} else if("process".equals(code)) { %>

		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>프로세 스파일명</td>
					<td>프로세스 해시데이터</td>
					<td>설명</td>
				</tr>
			</thead>
			<tbody>
				<% for (int i = 0; i < list_cnt; i ++) { %>
				<tr>
					<td><%= list.get(i).get("process_name") %></td>
					<td><%= list.get(i).get("hash") %></td>
					<td><%= list.get(i).get("notice") %></td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
<%	} else if("usbport".equals(code)) { %>

		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>장치명</td>
					<td>벤더식별자(VID)</td>
					<td>제품식별자(PID)</td>
					<td>일련번호(SerialNumber)</td>
					<td>설명</td>
				</tr>
			</thead>
			<tbody>
				<% for (int i = 0; i < list_cnt; i ++) { %>
				<tr>
					<td><%= list.get(i).get("name") %></td>
					<td><%= list.get(i).get("vid") %></td>
					<td><%= list.get(i).get("pid") %></td>
					<td><%= list.get(i).get("serial_number") %></td>
					<td><%= list.get(i).get("description") %></td>
				</tr>
				<% } %>
		</table>
		
<%	} else if("serialport".equals(code)) { %>

		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>포트이름</td>
					<td>설명</td>
				</tr>
			</thead>
			<tbody>
				<% for (int i = 0; i < list_cnt; i ++) { %>
				<tr>
					<td><%= list.get(i).get("name") %></td>
					<td><%= list.get(i).get("description") %></td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
<%	} else if("messenger".equals(code)) { %>

		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>메신저명</td>
					<td>파일명</td>
					<td>Message로깅</td>
					<td>Message차단</td>
					<td>File전송로깅</td>
					<td>File전송차단</td>
				</tr>
			</thead>
			<tbody>
				<% 
				for (int i = 0; i < list_cnt; i ++) {
				%>
				<tr>
					<td><%= list.get(i).get("name") %></td>
					<td><%= list.get(i).get("process_name") %></td>
					<td>
						<div class="inline-group">
							<% if ("1".equals(list.get(i).get("txt_log").toString())) { %>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageLog_<%= i %>" checked="checked" disabled="disabled"><i></i> Yes
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageLog_<%= i %>" disabled="disabled"><i></i> NO
								</label>
							<% } else {%>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageLog_<%= i %>" disabled="disabled"><i></i> Yes
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageLog_<%= i %>" checked="checked" disabled="disabled"><i></i> NO
								</label>
							<% } %>
						</div>
					</td>
					<td>
						<div class="inline-group">
							<% if ("1".equals(list.get(i).get("txt_block").toString())) { %>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageBlock_<%= i %>" checked="checked" disabled="disabled"><i></i> 차단
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageBlock_<%= i %>" disabled="disabled"><i></i> 허용
								</label>
							<% } else {%>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageBlock_<%= i %>" disabled="disabled"><i></i> 차단
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="messageBlock_<%= i %>" checked="checked" disabled="disabled"><i></i> 허용
								</label>
							<% } %>
						</div>
					</td>
					<td>
						<div class="inline-group">
							<% if ("1".equals(list.get(i).get("file_log").toString())) { %>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileLog_<%= i %>" checked="checked" disabled="disabled"><i></i> Yes
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileLog_<%= i %>" disabled="disabled"><i></i> NO
								</label>
							<% } else {%>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileLog_<%= i %>" disabled="disabled"><i></i> Yes
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileLog_<%= i %>" checked="checked" disabled="disabled"><i></i> NO
								</label>
							<% } %>
						</div>
					</td>
					<td>
						<div class="inline-group">
							<% if ("1".equals(list.get(i).get("file_block").toString())) { %>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileBlock_<%= i %>" checked="checked" disabled="disabled"><i></i> 차단
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileBlock_<%= i %>" disabled="disabled"><i></i> 허용
								</label>
							<% } else {%>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileBlock_<%= i %>" disabled="disabled"><i></i> 차단
								</label>
								<label class="radio nomargin-top nomargin-bottom">
									<input type="radio" name="fileBlock_<%= i %>" checked="checked" disabled="disabled"><i></i> 허용
								</label>
							<% } %>
						</div>
					</td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
<%	} else if("siteblock".equals(code)) { %>

		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<td>사이트명</td>
					<td>주소</td>
				</tr>
			</thead>
			<tbody>
				<% for (int i = 0; i < list_cnt; i ++) { %>
				<tr>
					<td><%= list.get(i).get("description") %></td>
					<td><%= list.get(i).get("address") %></td>
				</tr>
				<% } %>
			</tbody>
		</table>
		
<%	} else {  %>
	<table class="table table-bordered table-striped">
		<tbody>
			<tr><td>해당 정책은 적용되지 않았습니다.</td></tr>
		</tbody>
	</table>	
<% 	
	}
} 
%>
