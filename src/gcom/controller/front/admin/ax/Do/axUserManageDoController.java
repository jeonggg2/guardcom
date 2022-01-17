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
import gcom.common.util.PwCheck;
import gcom.controller.action.admin.insertAdminAction;
import gcom.controller.action.admin.updateAdminAction;


@WebServlet(urlPatterns={"/admin/user/manage/do/*"} )	//save, reject, view
public class axUserManageDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
    	String context = request.getContextPath();
		HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("user_no", request.getParameter("user_no"));

    	String requestUri = request.getRequestURI();
		HashMap<String, Object> data =  new HashMap<String, Object>();;
		
		if(requestUri.equals(context + "/admin/user/manage/do/create")){
			String password = request.getParameter("user_password");
			String id = request.getParameter("user_id");
			
			PwCheck pwCheck = new PwCheck();
			boolean pwCheckResult = pwCheck.Check(password, id); 
			
			if (!pwCheckResult) {
				data.put("returnCode", ConfigInfo.PW_PATTERN);
				data.putAll(data);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(new Gson().toJson(data));
				return;
			}
			
			param.put("password", password);
	    	param.put("dept_no", request.getParameter("user_dept"));
	    	param.put("duty", request.getParameter("user_duty"));
	    	param.put("rank", request.getParameter("user_rank"));
	    	param.put("number", request.getParameter("user_number"));
	    	param.put("name", request.getParameter("user_name"));
	    	param.put("phone", request.getParameter("user_phone"));
	    	param.put("id", id);
	    	param.put("secure_level", request.getParameter("user_secure_level"));
	    	
			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(1200);
			model.setWorkIp(request.getRemoteAddr());
			model.setDescription("사용자 생성");
			model.setParameter("ID : " + request.getParameter("user_id"));

			insertAdminAction action = new insertAdminAction();
        	data = action.insertUserInfo(param);

	   		model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);
		}else if(requestUri.equals(context + "/admin/user/manage/do/update")){
			String password = request.getParameter("user_password");
			String id = request.getParameter("user_id");
			
			if (password != null && !"".equals(password)) {
				PwCheck pwCheck = new PwCheck();
				boolean pwCheckResult = pwCheck.Check(password, id); 
				
				if (!pwCheckResult) {
					data.put("returnCode", ConfigInfo.PW_PATTERN);
					data.putAll(data);
					response.setContentType("application/json; charset=UTF-8");
					response.getWriter().write(new Gson().toJson(data));
					return;
				}
			}
			
			param.put("password", password);
	    	param.put("dept_no", request.getParameter("user_dept"));
	    	param.put("duty", request.getParameter("user_duty"));
	    	param.put("rank", request.getParameter("user_rank"));
	    	param.put("number", request.getParameter("user_number"));
	    	param.put("name", request.getParameter("user_name"));
	    	param.put("phone", request.getParameter("user_phone"));
	    	param.put("id", id);
	    	param.put("secure_level", request.getParameter("user_secure_level"));

			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(1201);
			model.setWorkIp(request.getRemoteAddr());
			model.setDescription("사용자 수정");
			model.setParameter("ID : " + request.getParameter("user_id"));
    		updateAdminAction action = new updateAdminAction();
   			data = action.updateUserInfo(param);    		

   			model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);
    	
		}else if(requestUri.equals(context + "/admin/user/manage/do/remove")){
    		updateAdminAction action = new updateAdminAction();
    		data = action.removeUserInfo(param);    		

			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(1202);
			model.setWorkIp(request.getRemoteAddr());
			model.setDescription("사용자 삭제");
			model.setParameter("ID : " + request.getParameter("user_id"));
			model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);

		}
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
