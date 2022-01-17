<%@page import="java.io.StringReader"%>
<%@page import="java.io.Reader"%>
<%@page import="com.google.gson.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.mysql.*"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
Connection connection = null;
PreparedStatement pstmt = null;
try {
	JsonParser parser = new JsonParser();
	JsonElement element = parser.parse(request.getReader());
	
	String agentNo, hw_list, sw_list;
	
	agentNo = element.getAsJsonObject().get("agent_no").getAsString();
	hw_list = element.getAsJsonObject().get("hw_info").toString();
	sw_list = element.getAsJsonObject().get("sw_info").toString();
    Class.forName("com.mysql.jdbc.Driver");
    connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/guardcom" , "root", "1234");
    
    connection.setAutoCommit(false);
  
	pstmt = connection.prepareStatement("DELETE FROM `guardcom`.`device_info` WHERE `agent_no`=?");
	pstmt.setString(1, agentNo);	
	pstmt.execute();
	pstmt.close();
	pstmt = null;
    
    pstmt = connection.prepareStatement("INSERT INTO device_info(agent_no, hw_list, sw_list) VALUES(?,?,?)");
    pstmt.setString(1, agentNo);
    pstmt.setString(2, hw_list);
    pstmt.setString(3, sw_list);
	pstmt.execute();
	    
	connection.commit();
	
	connection.close();
} catch (SQLException se1) {
    se1.printStackTrace();
} catch (Exception ex) {
    ex.printStackTrace();
} finally {
    try {
        if (pstmt != null)
            pstmt.close();
    } catch (SQLException se2) {
    }
    try {
        if (connection != null)
            connection.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}    
%>