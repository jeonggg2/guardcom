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
import gcom.controller.action.admin.updateAdminAction;
import gcom.service.management.IManagementService;
import gcom.service.management.ManagementServiceImpl;

/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/update/disk/info")
public class axAdminDiskUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
    	HashMap<String, Object> param = new HashMap<String, Object>();

    	param.put("disk_no", request.getParameter("disk_no"));
		param.put("status", request.getParameter("status"));
		
		updateAdminAction action = new updateAdminAction();
		
		HashMap<String, Object> data =  new HashMap<String, Object>();
		try {
			data = action.updateDiskAllow(param);
            ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(1302);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("디스크 허용/차단");
	
			String status = Integer.parseInt(request.getParameter("status").toString()) == 0 ? "차단" : "허용";
			model.setParameter("[" + request.getParameter("user_id") + "] 디스크 " +  status );
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
