package gcom.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.common.util.encrypto.hashEncrypto;
import gcom.user.model.UserNoticeModel;
import gcom.user.service.UserServiceImpl;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/test/hash")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String param = request.getParameter("pass");
    	
    	String data = hashEncrypto.HashEncrypt(param);
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("result", data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(map));		

		
	}
}
