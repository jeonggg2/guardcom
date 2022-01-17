package gcom.controller.front.admin.ax.Do;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

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
import gcom.controller.action.deptAction;
import gcom.controller.action.admin.insertAdminAction;
import gcom.controller.action.admin.updateAdminAction;


@WebServlet(urlPatterns={"/admin/admin/manage/do/*"} )	//save, reject, view
public class axAdminManageDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HashMap<String, Object> param = new HashMap<String, Object>();

    	String requestUri = request.getRequestURI();
		HashMap<String, Object> data =  new HashMap<String, Object>();
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	String context = request.getContextPath();


		//관리자생성
		if(requestUri.equals(context + "/admin/admin/manage/do/create")){
			deptAction act = new deptAction();
			request.setAttribute("deptList", act.getDeptList(Integer.parseInt(session.getAttribute("dept_no").toString())));
			
			String password = request.getParameter("admin_password");
			String id = request.getParameter("admin_id");
			
			PwCheck pwCheck = new PwCheck();
			boolean pwCheckResult = pwCheck.Check(password, id); 
			
			if (!pwCheckResult) {
				data.put("returnCode", ConfigInfo.PW_PATTERN);
				data.putAll(data);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(new Gson().toJson(data));
				return;
			}
			param.put("id", id);
	    	param.put("pw", password);
	    	param.put("dept", request.getParameter("admin_dept"));
	    	param.put("ip1", request.getParameter("admin_ip0"));
	    	param.put("ip2", request.getParameter("admin_ip1"));
	    	param.put("auth", request.getParameter("admin_auth"));

	    	for (Entry<String, Object> obj : param.entrySet()) {
	    		if (obj.getValue() == null) {
	    			data.put("returnCode", ConfigInfo.EXIST_NOT_PARAM);
	    			data.put("param", obj.getKey());
	    			data.putAll(data);
	    			response.setContentType("application/json; charset=UTF-8");
	    			response.getWriter().write(new Gson().toJson(data));
	    			return;
	    		}
	    		if (obj.getValue().toString().length() == 0) {
	    			data.put("returnCode", ConfigInfo.EXIST_NOT_PARAM);
	    			data.put("param", obj.getKey());
	    			data.putAll(data);
	    			response.setContentType("application/json; charset=UTF-8");
	    			response.getWriter().write(new Gson().toJson(data));
	    			return;
	    		}
	    	}
	    	
			insertAdminAction action = new insertAdminAction();
        	data = action.insertAdminUserInfo(param);
        	
			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(3000);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("관리자 생성");
			param.remove("pw");
			model.setParameter("[" +request.getParameter("admin_id")+ "] 생성완료" );
	 		model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);

   		//관리자수정
		}else if(requestUri.equals(context + "/admin/admin/manage/do/update")){
			deptAction act = new deptAction();
			request.setAttribute("deptList", act.getDeptList(Integer.parseInt(session.getAttribute("dept_no").toString())));
			
			String password = request.getParameter("admin_password");
			String id = request.getParameter("admin_id");
			
			param.put("no", request.getParameter("admin_no"));
			param.put("id", id);
	    	param.put("dept", request.getParameter("admin_dept"));
	    	param.put("ip1", request.getParameter("admin_ip0"));
	    	param.put("ip2", request.getParameter("admin_ip1"));
	    	param.put("auth", request.getParameter("admin_auth"));

	    	for (Entry<String, Object> obj : param.entrySet()) {
	    		if (obj.getValue() == null) {
	    			data.put("returnCode", ConfigInfo.EXIST_NOT_PARAM);
	    			data.put("param", obj.getKey());
	    			data.putAll(data);
	    			response.setContentType("application/json; charset=UTF-8");
	    			response.getWriter().write(new Gson().toJson(data));
	    			return;
	    		}
	    		if (obj.getValue().toString().length() == 0) {
	    			data.put("returnCode", ConfigInfo.EXIST_NOT_PARAM);
	    			data.put("param", obj.getKey());
	    			data.putAll(data);
	    			response.setContentType("application/json; charset=UTF-8");
	    			response.getWriter().write(new Gson().toJson(data));
	    			return;
	    		}
	    	}

	    	param.put("pw", password);
			
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
	    	
    		updateAdminAction action = new updateAdminAction();
   			data = action.updateAdminUserInfo(param);    		

   			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(3001);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("관리자 수정");
			param.remove("pw");
			model.setParameter("[" +request.getParameter("admin_id")+ "] 수정완료" );
	 		model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);   		//관리자삭제
		}else if(requestUri.equals(context + "/admin/admin/manage/do/remove")){
	    	param.put("no", request.getParameter("admin_no"));

			updateAdminAction action = new updateAdminAction();
    		data = action.deleteAdminUserInfo(param);
    		
			ServerAuditModel model = new ServerAuditModel();
			model.setAdminId((String)session.getAttribute("user_id"));
			model.setActionId(3002);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("관리자 삭제 (" + param.get("no") + ")");
			model.setParameter("");
	 		model.setStatus(data.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");

			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);
    	}
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
