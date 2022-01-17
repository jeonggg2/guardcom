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

@WebServlet("/admin/policy/pattern/modify")
public class axAdminPolicyPatternUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public axAdminPolicyPatternUpdate() {
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
			data = action.updatePolicyPatternUpdate(param);
		    ServerAuditModel model = new ServerAuditModel();
				
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(2021);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("민감정보 정책 수정");
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
    	
    	data += "패턴명 : " + map.get("pat_name");
    	data += ", 데이터 : " + map.get("pat_data");
    	data += ", 설명 : " + map.get("notice");
    	data += ", 사용여부: " + map.get("use_type");
    	
    	return data;
    }
}
