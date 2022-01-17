<%@page language="java" contentType="application/x-json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.google.gson.JsonElement"%>
<%@page import="com.google.gson.JsonParser"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="gc.admin.AdminVo"%>
<%@page import="gc.db.DbCon"%>
<%
	
	if(! request.getMethod().equalsIgnoreCase("POST")) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
	
	response.setHeader("Pragma", "no-cache;");
	response.setHeader("Expires", "-1;");
	
	if(request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control", "no-cache;");
		response.setHeader("Cache-Control", "no-store;");
	}
	
	final String sqlString = "SELECT number, user_info.name, guardcom.agent_info.no, pc_name, ip_addr, mac_addr, hw_list, login_server_time, login_client_time, install_server_time, install_client_time, connect_server_time, connect_client_time, version, guardcom.dept_info.name as dept_name FROM ";
	
	class nodeInfo {
		
		int no;
		boolean isParent;
		
		public nodeInfo(int no, boolean isParent) {
			this.no = no;
			this.isParent = isParent;
		}
		
		public boolean getSQLParam(Connection con, StringBuilder sql,  int adminNo) throws Exception {
									
			if (isParent) {
				
				int minChildNo, maxChildNo;
				
				con = DbCon.getConnection();
				
				if (con == null) {
					
					return false;
				}
				
				PreparedStatement pstmt;
				
				pstmt = con.prepareStatement("SELECT min_child_no, max_child_no FROM guardcom.dept_info WHERE (no = ?)");
				
				if (pstmt == null) {
					
					return false;
				}
				
				pstmt.setInt(1, this.no);
				
				if (pstmt.execute()) {
					
					ResultSet rs = pstmt.getResultSet();
					
					if (rs == null) {
						
						pstmt.close();
						return false;
					}
					
					if (rs.next()) {
						
						minChildNo = rs.getInt(1) - 1;
						maxChildNo = rs.getInt(2);
						
					} else {
						
						pstmt.close();
						return false;
						
					}
					
					rs.close();
					rs = null;
					
				} else {
					
					pstmt.close();
					return false;
					
				}
				 
				//
				// 루트가 아닌 부모 노드를 선택했을 경우
				//
				sql.append(sqlString);
				sql.append("guardcom.agent_info, guardcom.user_info, guardcom.dept_info WHERE (guardcom.agent_info.dept_no = guardcom.dept_info.no) AND (guardcom.user_info.no = own_user_no) AND (");
				
				if (adminNo != 0) {
					sql.append("(admin_no = ");
					sql.append(adminNo);
					sql.append(	") AND ");	
				}
				
				sql.append("guardcom.agent_info.dept_no BETWEEN ");
				sql.append(minChildNo);
				sql.append(" AND ");
				sql.append(maxChildNo);
				sql.append(")");
				
			}	else {
				
				sql.append(sqlString);
				sql.append("guardcom.agent_info, guardcom.user_info, guardcom.dept_info WHERE (guardcom.user_info.no = own_user_no) AND (guardcom.agent_info.dept_no = guardcom.dept_info.no) AND (guardcom.agent_info.dept_no = ");
				sql.append(no);
				sql.append(")");
				
			}
			
			return true;
			
		}
		
	}
	
	HttpSession Session = request.getSession(false);
	AdminVo adminVo = (AdminVo)application.getAttribute(request.getParameter("lid"));
	
	if (adminVo == null) return;	
	
	int depth = 0;
	int page_arg;
	int rows_arg;
	
	if (request.getParameter("page") == null) {
		page_arg = 65536;
	} else {
		page_arg = Integer.parseInt(request.getParameter("page"));
	}
		
	if (request.getParameter("rows") == null) {
		rows_arg = 65536;
	} else {
		rows_arg = Integer.parseInt(request.getParameter("rows"));
	}
	
	int adminDepth = adminVo.getDept_depth();
	
	int count = 0;
	int pageCount = 0;
	
	String parent_arg = request.getParameter("parent");
	boolean parent = Boolean.parseBoolean(parent_arg);
	
	StringBuffer whereCondition = new StringBuffer();
	
	if (request.getParameter("_search") == null) {
		
	} else if (request.getParameter("_search").equalsIgnoreCase("true")) {
		
		String oper;
		String fieldName;
		String opName;
		String data;
		
		JsonParser parser = new JsonParser();
		
		JsonElement je =  parser.parse(request.getParameter("filters"));
		JsonObject jo = je.getAsJsonObject();
		
		oper = jo.get("groupOp").getAsString();
		
		JsonArray jaRule = jo.getAsJsonArray("rules");
		Iterator<JsonElement> iterator = jaRule.iterator();
		
		if (iterator.hasNext()) {
			
			for (;;) {
				
				jo = iterator.next().getAsJsonObject();
				
				fieldName = jo.get("field").getAsString();
				opName = jo.get("op").getAsString();
				data = jo.get("data").getAsString();
				
				if (opName.equalsIgnoreCase("eq")) {
					
					whereCondition.append("(");
					whereCondition.append(fieldName);
					whereCondition.append(" = '");
					whereCondition.append(data);
					whereCondition.append("') ");
					
				} else if (opName.equalsIgnoreCase("ne")) {
					
					whereCondition.append("(");
					whereCondition.append(fieldName);
					whereCondition.append(" != '");
					whereCondition.append(data);
					whereCondition.append("') ");
					
				} else if (opName.equalsIgnoreCase("bw")) {
					
					whereCondition.append("(");
					whereCondition.append(fieldName);
					whereCondition.append(" like '");
					whereCondition.append(data);
					whereCondition.append("%') ");
					
				} else if (opName.equalsIgnoreCase("bn")) {

					whereCondition.append("NOT ((");
					whereCondition.append(fieldName);
					whereCondition.append(" like '");
					whereCondition.append(data);
					whereCondition.append("%')) ");					
					
				} else if (opName.equalsIgnoreCase("ew")) {
					
					whereCondition.append("(");
					whereCondition.append(fieldName);
					whereCondition.append(" like '%");
					whereCondition.append(data);
					whereCondition.append("') ");
					
				} else if (opName.equalsIgnoreCase("en")) {

					whereCondition.append("NOT ((");
					whereCondition.append(fieldName);
					whereCondition.append(" like '%");
					whereCondition.append(data);
					whereCondition.append("')) ");					
					
				} else if (opName.equalsIgnoreCase("cn")) {
					
					whereCondition.append("(");
					whereCondition.append(fieldName);
					whereCondition.append(" like '%");
					whereCondition.append(data);
					whereCondition.append("%') ");			
					
				} else if (opName.equalsIgnoreCase("nc")) {

					whereCondition.append("NOT ((");
					whereCondition.append(fieldName);
					whereCondition.append(" like '%");
					whereCondition.append(data);
					whereCondition.append("%')) ");
					
				}
				
				if (! iterator.hasNext()) break;
				whereCondition.append(oper);
				whereCondition.append(" ");
			}
			
		}
		
	}
	
	int node = -1;
	nodeInfo nodes[] = null;
	
	String nodes_arg = request.getParameter("nodes");
	
	if (nodes_arg == null) {
		
		nodes = new nodeInfo[1];
		
		try {
			node = Integer.parseInt(request.getParameter("node"));
			nodes[0] = new nodeInfo(node, parent);
		} catch (NumberFormatException e) {
			
		}
		
	} else {
		
		String n[] = nodes_arg.split(";");
		nodes = new nodeInfo[n.length];
		
		try {
			String ni[];
			for (int i = 0; i < n.length; i++) {
				ni = n[i].split(":");
				nodes[i] = new nodeInfo(Integer.parseInt(ni[0]), Boolean.parseBoolean(ni[1]));
				ni = null;
			}
			
		} catch (NumberFormatException e) {
			
		}
		
	}

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	StringBuilder sql = new StringBuilder();
	JsonArray jsonArray = new JsonArray();
	JsonObject jsonObject = new JsonObject();
	
	try {
		
		if ((adminVo.getDept_no() == node) ||
			((parent_arg == null) && (nodes_arg == null))) {
			
			//
			// 루트 노드를 클릭하거나 페이지를 처음 로드하는 경우.
			//
			
			if (adminVo.getNo() == 0) {
				
				//
				// 최고 관리자가 사용자 정보 조회 페이지를 최초로 접근했을 경우에는 모든 사용자가 보이도록 합니다.
				//
				
				sql.append("SELECT COUNT(*) FROM (");
				sql.append(sqlString);
				sql.append("guardcom.agent_info, guardcom.user_info, guardcom.dept_info WHERE (guardcom.agent_info.dept_no = guardcom.dept_info.no) AND (guardcom.user_info.no = own_user_no)) as result");
				
			} else {
				
				//
				// 부서 관리자가 사용자 정보 조회 페이지를 최초로 접근했을 경우에는 자신이 관리하는 부서의 모든 사용자가 보이도록 합니다.
				//
				
				sql.append("SELECT COUNT(*) FROM (");
				sql.append(sqlString);
				sql.append("guardcom.agent_info, guardcom.user_info, guardcom.dept_info WHERE (guardcom.agent_info.dept_no = guardcom.dept_info.no) AND (guardcom.user_info.no = own_user_no) AND guardcom.agent_info.dept_no in (SELECT no FROM guardcom.dept_info WHERE (admin_No = ");
				sql.append(adminVo.getNo());
				sql.append("))) as result");
				
			}
			
		} else {
			
			sql.append("SELECT COUNT(*) FROM (");
			
			for (int i = 0; i < nodes.length; i++) {
				
				if (nodes[i].getSQLParam(con, sql, adminVo.getNo())) {
				
					if (i < nodes.length -1) sql.append(" UNION ");
					
				} else {
					
					response.setContentType("application/x-json; charset=euc-kr");
					out.print("{}");
					return;
					
				}
				
			}
			
			sql.append(") as result");
			
		}
		
		//
		// 전체 레코드 개수 계산.
		//
		
		if (con == null) con = DbCon.getConnection();
		
		if (con == null) {
			
			response.setContentType("application/x-json; charset=euc-kr");
			out.print("{}");
			return;
		}
		
		stmt = con.createStatement();
		
		if (stmt == null) {
			
			response.setContentType("application/x-json; charset=euc-kr");
			out.print("{}");
			return;
		}
		
		if (whereCondition.length() > 0) {
			sql.append(" WHERE ");
			sql.append(whereCondition);
		}
		
		if (stmt.execute(sql.toString())) {
			
			rs = stmt.getResultSet();
			
			if (rs == null) {
				
				response.setContentType("application/x-json; charset=euc-kr");
				out.print("{}");
				return;
			}
			
			if (rs.next()) {
				
				count = rs.getInt(1);
				
			} else {
				
				response.setContentType("application/x-json; charset=euc-kr");
				out.print("{}");
				return;

			}
			
			rs.close();
			rs = null;
		}
		
		if (count == 0) {
			
			response.setContentType("application/x-json; charset=euc-kr");
			out.print("{}");
			return;
		}
		
		if (count > 0) {
			pageCount = (int)Math.ceil((double)count / rows_arg);  
		} else {
			pageCount = 0;
		}
		
		if (page_arg > pageCount) page_arg=pageCount;
		
		//
		// 레코드 조회
		//
		
		int offsetCount = sql.indexOf("COUNT");
		sql.delete(offsetCount, offsetCount + "COUNT(*)".length());
		sql.insert(offsetCount, "number, name, no, pc_name, ip_addr, mac_addr, hw_list, login_server_time, login_client_time, install_server_time, install_client_time, connect_server_time, connect_client_time, version, dept_name");
				
		sql.append(" ORDER BY ");
		sql.append(request.getParameter("sidx"));
		sql.append(" ");
		sql.append(request.getParameter("sord"));
		sql.append(" limit ");
		sql.append( rows_arg * page_arg - rows_arg);
		sql.append(", ");
		sql.append( rows_arg );
		
		if (stmt.execute(sql.toString())) {
			
			rs = stmt.getResultSet();
			
			if (rs != null) {
				
				while (rs.next()) {
					JsonObject jObject = new JsonObject();
					jObject.addProperty("user_number", rs.getNString(1));
					jObject.addProperty("user_name", rs.getNString(2));
					jObject.addProperty("no", rs.getInt(3));
					jObject.addProperty("pc_name", rs.getNString(4));
					jObject.addProperty("ip", rs.getNString(5));
					jObject.addProperty("mac", rs.getNString(6));
					jObject.addProperty("hw_list", rs.getNString(7));
					if (rs.getTimestamp(8) != null) jObject.addProperty("login_svr_time",  rs.getTimestamp(8).toString());
					if (rs.getTimestamp(9) != null) jObject.addProperty("login_cli_time",  rs.getTimestamp(9).toString());
					if (rs.getTimestamp(10) != null) jObject.addProperty("inst_svr_time",  rs.getTimestamp(10).toString());
					if (rs.getTimestamp(11) != null) jObject.addProperty("inst_cli_time",  rs.getTimestamp(11).toString());
					if (rs.getTimestamp(12) != null) jObject.addProperty("con_svr_time",  rs.getTimestamp(12).toString());
					if (rs.getTimestamp(13) != null) jObject.addProperty("con_cli_time",  rs.getTimestamp(13).toString());
					jObject.addProperty("version", rs.getNString(14));
					jObject.addProperty("dept_name", rs.getNString(15));
					jsonArray.add(jObject);
				}
				
				jsonObject.addProperty("page", page_arg);
				jsonObject.addProperty("total", pageCount);
				jsonObject.addProperty("records", count);
				jsonObject.add("rows", jsonArray);
				
				response.setContentType("application/x-json; charset=euc-kr");
				out.print(jsonObject);
				return;
			}
			
		}
		
	} catch (Exception e) {
		
		System.out.println(e);
		
	} finally {
		
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (con != null) con.close();
		
	}
%>