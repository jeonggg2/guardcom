package gcom.controller.front.admin.ax.Do;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import gcom.Model.ServerAuditModel;
import gcom.Model.SubAdminModel;
import gcom.common.util.ConfigInfo;
import gcom.controller.action.admin.insertAdminAction;
import gcom.service.management.IManagementService;
import gcom.service.management.ManagementServiceImpl;

/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/admin/user/comment/save")
public class axAdminContactCommentSave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
    	String user_id = (String)session.getAttribute("user_id");
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("user_id", user_id);
    	
    	IManagementService managementService = new ManagementServiceImpl();
    	SubAdminModel admin = managementService.getAdminUserIdToNo(param);
		
		param.put("reg_admin_staf_no", admin.getAdminNo());
		param.put("contact_id", request.getParameter("contact_id").toString());
		param.put("reply_content", request.getParameter("reply_content").toString());
		
		insertAdminAction action = new insertAdminAction();
		
		HashMap<String, Object> data =  new HashMap<String, Object>();
		try {
			data = action.insertContactCommentSave(param);
			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(1300);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("문의사항 답변");
	   		model.setStatus("성공");
			model.setParameter("문의사항 답변");
	 		model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
	 		insertAdminAction aud = new insertAdminAction();
   			aud.insertServeriAudit(model);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
