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
import gcom.common.util.JSONUtil;
import gcom.controller.action.admin.insertAdminAction;
import gcom.controller.action.admin.updateAdminAction;

@WebServlet("/admin/policy/msg/modify")
public class axAdminPolicyMessengerUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public axAdminPolicyMessengerUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HashMap<String, Object> param = JSONUtil.convertJsonToHashMap(request.getParameter("data").toString());

		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
		updateAdminAction action = new updateAdminAction();
		HashMap<String, Object> data =  new HashMap<String, Object>();
		try {
			data = action.updatePolicyMsgUpdate(param);
            ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(1301);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("메신저 정책 수정");
			model.setParameter(parseAudit(param));
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
    
    public String parseAudit(HashMap<String, Object> map){
    	String data = "";
    	
    	data += "메신저명 : " + map.get("msg_name");
    	data += ", 프로세스명 : " + map.get("msg_pro_name");
    	data += ", 텍스트로그 : " + map.get("txt_log");
    	data += ", 파일로그: " + map.get("file_log");
    	
    	return data;
    }
}
