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
import gcom.common.util.ConfigInfo;
import gcom.controller.action.admin.getAdminAction;
import gcom.controller.action.admin.insertAdminAction;
import gcom.controller.action.admin.updateAdminAction;


@WebServlet(urlPatterns={"/admin/enroll/do/*"} )	//save, reject, view
public class axAdminEnrollDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
    	String admin_id = (String)session.getAttribute("user_id");
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("admin_id", admin_id);
    	param.put("req_id", request.getParameter("req_id"));

    	String context = request.getContextPath();
    	
		ServerAuditModel model = new ServerAuditModel();
		model.setAdminId(admin_id);
		model.setWorkIp(httpReq.getRemoteAddr());
    	
    	String requestUri = request.getRequestURI();
		HashMap<String, Object> data =  new HashMap<String, Object>();;
		
		if(requestUri.equals(context + "/admin/enroll/do/dupcheck")){
        	getAdminAction action = new getAdminAction();
        	data = action.getEnrollRequestCheckDupl(param);

		}else if(requestUri.equals(context + "/admin/enroll/do/save")){
   			model.setActionId(1001);
			model.setDescription("회원가입승인");
			model.setParameter("가입요청 ("+request.getParameter("req_id")+") 가입 승인 완료");
        	insertAdminAction action = new insertAdminAction();
   			data = action.insertUserInfoFromRequest(param);    
   			
			model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");

   			insertAdminAction aud = new insertAdminAction();
   			aud.insertServeriAudit(model);
			
    	}else if(requestUri.equals(context + "/admin/enroll/do/reject")){
    		model.setActionId(1002);
    		model.setDescription("회원가입반려");
			model.setParameter("가입요청 ("+request.getParameter("req_id")+") 반려");
    		updateAdminAction action = new updateAdminAction();
    		data = action.updateEnrollRequestReject(param);    		

			model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");

    		insertAdminAction aud = new insertAdminAction();
    		aud.insertServeriAudit(model);
    	}

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
