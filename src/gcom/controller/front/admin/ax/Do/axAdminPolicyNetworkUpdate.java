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
import gcom.common.util.NotificationObject;
import gcom.common.util.NotificationObject.NotificationType;
import gcom.controller.action.admin.insertAdminAction;
import gcom.controller.action.admin.updateAdminAction;

@WebServlet("/admin/policy/network/modify")
public class axAdminPolicyNetworkUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public axAdminPolicyNetworkUpdate() {
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
    	HashMap<String, Object> param = JSONUtil.convertJsonToHashMap(request.getParameter("data").toString());
    	
		updateAdminAction action = new updateAdminAction();
		HashMap<String, Object> data =  new HashMap<String, Object>();
	
		data = action.updatePolicyNetworkUpdate(param);
		
		if (data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS)) {
			NotificationObject.NotifyAllUser(NotificationType.NetworkPortInfo);
		}
		
        ServerAuditModel model = new ServerAuditModel();
		model.setAdminId((String)session.getAttribute("user_id"));
		model.setActionId(2012);
		model.setWorkIp(httpReq.getRemoteAddr());
		model.setDescription("네트워크포트 정책 수정");
		model.setParameter(parseAudit(param));
 		model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");		
		
 		insertAdminAction aud = new insertAdminAction();
		aud.insertServeriAudit(model);
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
    

    
    public String parseAudit(HashMap<String, Object> map){
    	String data = "";
    	
    	data += "포트이름 : " + map.get("net_name");
    	data += ", 포트 : " + map.get("net_port");
    	data += ", 설명 : " + map.get("descript");
    	
    	return data;
    }
}
