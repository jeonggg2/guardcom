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
import gcom.controller.action.admin.deleteAdminAction;
import gcom.controller.action.admin.insertAdminAction;

@WebServlet("/admin/policy/msg/delete")
public class axAdminPolicyMessengerDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public axAdminPolicyMessengerDelete() {
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
    	
    	String msgNo = request.getParameter("code").toString();
    	
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("msg_no", msgNo);
    	
    	deleteAdminAction action = new deleteAdminAction();
		HashMap<String, Object> data =  new HashMap<String, Object>();
		try {
			data = action.daletePolicyMsgData(param);
            ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(2000);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("메신저 정책 삭제");
			model.setParameter("삭제ID : " + msgNo);
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
