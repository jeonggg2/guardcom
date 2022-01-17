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
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	StringBuilder sql = new StringBuilder();
	JsonArray jsonArray = new JsonArray();
	JsonObject jsonObject = new JsonObject();
	
	try {
		
		//
		// 전체 레코드 개수 계산.
		//
		
		sql.append("SELECT COUNT(*) FROM guardcom.system_info where visible = 1");
		
		con = DbCon.getConnection();
		
		if (con == null) {
			
			out.print("{}");
			return;
		}
		
		stmt = con.createStatement();
		
		if (stmt == null) {
			
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
				
				out.print("{}");
				return;
			}
			
			if (rs.next()) {
				
				count = rs.getInt(1);
				
			} else {
				
				out.print("{}");
				return;

			}
			
			rs.close();
			rs = null;
		}
		
		if (count == 0) {
			
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
		sql.insert(offsetCount, "no, description, name, value");
				
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
					jObject.addProperty("no", rs.getInt(1));
					jObject.addProperty("desc", rs.getNString(2));
					jObject.addProperty("name", rs.getNString(3));
					jObject.addProperty("value", rs.getString(4));
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