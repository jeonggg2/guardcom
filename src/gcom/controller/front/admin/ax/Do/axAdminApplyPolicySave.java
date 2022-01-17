package gcom.controller.front.admin.ax.Do;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import gcom.Model.ServerAuditModel;
import gcom.common.util.ConfigInfo;
import gcom.common.util.JSONUtil;
import gcom.controller.action.admin.insertAdminAction;

/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/admin/user/apply/save")
public class axAdminApplyPolicySave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);

		HashMap<String, Object> param = JSONUtil.convertJsonToHashMap(request.getParameter("apply_policy").toString());
		insertAdminAction action = new insertAdminAction();
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			data = action.applyPolicyDataSave(param);
			
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> apply_list = (List<HashMap<String, Object>>) param.get("apply_list");

			for(HashMap<String, Object> item : apply_list) {
				ServerAuditModel model = new ServerAuditModel();
				model.setAdminId((String)session.getAttribute("user_id"));
				model.setActionId(1203);
				model.setWorkIp(httpReq.getRemoteAddr());
				model.setDescription("사용자 정책 할당");
		   		model.setStatus("성공");
				model.setParameter("[" + item.get("user_id") + "] 정책 할당");
		 		model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
		 		insertAdminAction aud = new insertAdminAction();
	   			aud.insertServeriAudit(model);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
