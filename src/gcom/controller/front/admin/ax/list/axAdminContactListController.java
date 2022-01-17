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

@WebServlet("/ax/admin/contact/list")
public class axAdminContactListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axAdminContactListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//paging row param
		map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );
		
		//Search param
		map.put("dept"		 , request.getParameterValues("dept[]"));
		map.put("contact_type"	 , request.getParameter("contact_type").toString());
		map.put("contact_tilte"	 , request.getParameter("contact_tilte").toString());
		map.put("contact_user"	 , request.getParameter("contact_user").toString());
		
		getAdminAction action = new getAdminAction();
		
		HashMap<String, Object> data = action.getAdminContactList(map);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}

}
