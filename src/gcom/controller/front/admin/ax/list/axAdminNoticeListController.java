package gcom.controller.front.admin.ax.list;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.controller.action.admin.getAdminAction;

@WebServlet("/ax/admin/notice/list")
public class axAdminNoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axAdminNoticeListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		getAdminAction action = new getAdminAction();
		map.put("search_type", Integer.parseInt( request.getParameter("search_type").toString()) );
		map.put("search_text", request.getParameter("search_text").toString() );
		map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );
		
		HashMap<String, Object> data = action.getAdminNoticeList(map);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}

}
