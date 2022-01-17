package gcom.drm.controller;

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
import gcom.common.util.ConfigInfo;
import gcom.controller.action.admin.insertAdminAction;
import gcom.drm.service.DrmDecryptionRequestService;

@WebServlet("/admin/drm/decreq/permit")
public class DrmDecryptionRequestPermitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrmDecryptionRequestPermitController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("request_no", request.getParameter("request_no"));
		param.put("admin_id", (String)session.getAttribute("user_id"));
		param.put("user_id", request.getParameter("user_id"));
		param.put("ip", request.getParameter("ip"));
		
		DrmDecryptionRequestService svc = new DrmDecryptionRequestService();
		HashMap<String, Object> data = new HashMap<String, Object>();
		try {
			data = svc.permitRequest(param);
			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(9203);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("파일 반출 허용");
	   		model.setStatus("성공");
			model.setParameter("[" + param.get("user_id") + "] 정책승인");
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
