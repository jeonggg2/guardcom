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

@WebServlet("/ax/agent/list")
public class axAgentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axAgentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		getAction action = new getAction();
		map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );

		map.put("user_id", request.getParameter("user_id").toString());
		map.put("user_name", request.getParameter("user_name").toString());
		map.put("user_phone", request.getParameter("user_phone").toString());
		map.put("user_installed", request.getParameter("user_installed").toString());		

		map.put("dept", request.getParameterValues("dept[]"));

		
		map.put("user_duty", request.getParameter("user_duty").toString());
		map.put("user_rank", request.getParameter("user_rank").toString());
		map.put("user_connected", request.getParameter("user_connected").toString());
		map.put("user_number", request.getParameter("user_number").toString());
		map.put("user_pc", request.getParameter("user_pc").toString());
		map.put("user_ip", request.getParameter("user_ip").toString());

		HashMap<String, Object> data = action.getAgentList(map);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}

}
