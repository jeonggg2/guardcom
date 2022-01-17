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

@WebServlet("/ax/audit/client/list")
public class axAuditClientListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axAuditClientListController() {
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

		map.put("user_duty", request.getParameter("user_duty").toString());
		map.put("user_rank", request.getParameter("user_rank").toString());
		map.put("user_number", request.getParameter("user_number").toString());
		map.put("pc_name", request.getParameter("pc_name").toString());
		map.put("module_name", request.getParameter("module_name").toString());
		map.put("description", request.getParameter("description").toString());
		
		map.put("start_date", request.getParameter("start_date").toString());
		map.put("end_date", request.getParameter("end_date").toString());

		map.put("dept", request.getParameterValues("dept[]"));
		
		HashMap<String, Object> data = action.getAuditClientLogList(map);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}

}
