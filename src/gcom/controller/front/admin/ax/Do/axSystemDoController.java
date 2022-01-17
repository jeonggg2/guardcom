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
import gcom.controller.action.admin.insertAdminAction;
import gcom.controller.action.admin.updateAdminAction;

@WebServlet("/admin/system/update")
public class axSystemDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axSystemDoController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);

    	HashMap<String, Object> data = new HashMap<String, Object>();
    	HashMap<String, Object> result = null;

    	data.put("name", request.getParameter("name"));
    	data.put("value", request.getParameter("value"));
    	data.put("system_no", request.getParameter("system_no"));

    	updateAdminAction action = new updateAdminAction();
    	
		result = action.updateSystemInfo(data);

        ServerAuditModel model = new ServerAuditModel();
		model.setAdminId((String)session.getAttribute("user_id"));
		model.setActionId(2070);
		model.setWorkIp(httpReq.getRemoteAddr());
		model.setDescription("시스템 정책 수정");
		model.setParameter("시스템 번호 : " + request.getParameter("system_no") + ", 데이터 : " + request.getParameter("value"));
 		model.setStatus(result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");

		insertAdminAction aud = new insertAdminAction();
		aud.insertServeriAudit(model);
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(result));
    }
}
