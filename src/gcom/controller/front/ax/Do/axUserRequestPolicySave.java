package gcom.controller.front.ax.Do;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.common.util.JSONUtil;
import gcom.controller.action.insertAction;
import gcom.controller.action.admin.insertAdminAction;

/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/user/req/policy/save")
public class axUserRequestPolicySave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<String, Object> param = JSONUtil.convertJsonToHashMap(request.getParameter("request_policy").toString());
		
		insertAction action = new insertAction();
		
		HashMap<String, Object> data =  new HashMap<String, Object>();
		try {
			data = action.insertRequestPolicyInfoSave(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
