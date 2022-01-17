package gcom.controller.front.ax.list;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.controller.action.getAction;

@WebServlet("/ax/unauthusb/list")
public class axUnauthUsbListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axUnauthUsbListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		getAction action = new getAction();
		
		map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );

		map.put("name", request.getParameterMap().containsKey("name") ? request.getParameter("name").toString() : "") ;
		map.put("serial", request.getParameterMap().containsKey("serial") ? request.getParameter("serial").toString() : "");
		map.put("vid", request.getParameterMap().containsKey("vid") ? request.getParameter("vid").toString() : "");
		map.put("pid", request.getParameterMap().containsKey("pid") ? request.getParameter("pid").toString() : "");

		map.put("desc", request.getParameterMap().containsKey("desc") ? request.getParameter("desc").toString() : "");
		//map.put("allow", request.getParameterMap().containsKey("allow") ? request.getParameter("allow").toString() : "");
		map.put("allow", "");
		
		HashMap<String, Object> data = action.getUnAuthUsbList(map);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}

}
