package gcom.controller.front.admin.ax.list;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.controller.action.getAction;
import gcom.controller.action.admin.getAdminAction;

@WebServlet("/ax/deviceinfo/list")
public class axDeviceDiskInfoListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axDeviceDiskInfoListController() {
        super();
        // TODO Auto-generated constructor stub
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
		map.put("duty", request.getParameter("user_duty").toString());
		map.put("rank", request.getParameter("user_rank").toString());
		map.put("user_number", request.getParameter("user_number").toString());
		map.put("pc_name", request.getParameter("pc_name").toString());

		map.put("guid", request.getParameter("guid").toString());
		map.put("label", request.getParameter("label").toString());
		map.put("type", request.getParameter("type").toString());
		map.put("hwinfo", request.getParameter("hwinfo").toString());
		map.put("start_date", request.getParameter("start_date").toString());
		map.put("end_date", request.getParameter("end_date").toString());
		map.put("status", request.getParameter("status").toString());

		getAction action = new getAction();
		HashMap<String, Object> data = action.getDiskInfoList(map);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}

}
