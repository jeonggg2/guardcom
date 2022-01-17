package gcom.controller.front.admin.ax.list;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.common.util.StringUtil;
import gcom.controller.action.admin.getAdminAction;

@WebServlet("/ax/admin/policy/serial/list")
public class axAdminPolicySerialListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axAdminPolicySerialListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		getAdminAction action = new getAdminAction();
		
		map.put("search_type", StringUtil.nullToString((String)request.getParameter("search_type"), "1"));
		map.put("search_text", StringUtil.nullToString((String)request.getParameter("search_text"), ""));
		map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );
		HashMap<String, Object> data = action.getPolicySerialList(map);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}

}
