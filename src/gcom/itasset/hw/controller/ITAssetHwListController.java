package gcom.itasset.hw.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.controller.action.admin.getAdminAction;

@WebServlet("/admin/itasset/hw/list")
public class ITAssetHwListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//paging row param
		map.put("startRow", 0);
		map.put("endRow", 20);
		
		//Search param
		map.put("dept", "");
		map.put("user_id", "");
		map.put("user_name", "");
		map.put("user_phone", "");
		map.put("user_duty", "");
		map.put("user_rank", "");
		map.put("user_number", "");
		map.put("user_pc", "");
		map.put("user_ip", "");
		map.put("sw_name", "");
		getAdminAction action = new getAdminAction();		
		HashMap<String, Object> data = action.getITAssetHwList(map);
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));		
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//paging row param
		map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );
		
		//Search param
		map.put("dept", request.getParameterValues("dept[]"));
		map.put("user_id", request.getParameter("user_id").toString());
		map.put("user_name", request.getParameter("user_name").toString());
		map.put("user_phone", request.getParameter("user_phone").toString());
		map.put("user_duty", request.getParameter("user_duty").toString());
		map.put("user_rank", request.getParameter("user_rank").toString());
		map.put("user_number", request.getParameter("user_number").toString());
		map.put("user_pc", request.getParameter("user_pc").toString());
		map.put("user_ip", request.getParameter("user_ip").toString());
		map.put("sw_name", request.getParameter("sw_name").toString());
		map.put("hw_name", request.getParameter("hw_name").toString());
		getAdminAction action = new getAdminAction();		
		HashMap<String, Object> data = action.getITAssetHwList(map);
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		String s = new Gson().toJson(data);
		response.getWriter().write(s);		
	}
}
