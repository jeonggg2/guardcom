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

import gcom.Model.DeptModel;
import gcom.Model.ServerAuditModel;
import gcom.common.util.ConfigInfo;
import gcom.controller.action.deptAction;
import gcom.controller.action.admin.insertAdminAction;

@WebServlet("/admin/do/dept/*")
public class userDeptDoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public userDeptDoController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DeptModel data = new DeptModel();
    	HashMap<String, Object> result = null;
   		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	String context = request.getContextPath();

		if (request.getParameterMap().containsKey("no")){
			data.setDeptNo(Integer.parseInt(request.getParameter("no")));
		}
		if (request.getParameterMap().containsKey("parent")){
			data.setParent(Integer.parseInt(request.getParameter("parent")));			
		}
		if (request.getParameterMap().containsKey("name")){
			data.setName(request.getParameter("name"));			
		}
		if (request.getParameterMap().containsKey("short_name")){
			data.setShortName(request.getParameter("short_name"));
		}
    	
    	String requestUri = request.getRequestURI();
    	deptAction action = new deptAction();
		
        ServerAuditModel model = new ServerAuditModel();
		model.setAdminId((String)session.getAttribute("user_id"));

		if(requestUri.equals(context + "/admin/do/dept/create")){
			result = action.insertDeptInfo(data);
			model.setActionId(1400);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("부서생성");
			model.setParameter("부서명 : " + request.getParameter("name") + ", 약칭 : " + request.getParameter("short_name")  ) ;
	 		model.setStatus(result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);
    	}else if(requestUri.equals(context + "/admin/do/dept/update")){
    		result = action.updateDeptNameInfo(data);    		
			model.setActionId(1401);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("부서수정");
			model.setParameter("부서명 : " + request.getParameter("name") + ", 약칭 : " + request.getParameter("short_name")  ) ;
	 		model.setStatus(result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);
    	}else if(requestUri.equals(context + "/admin/do/dept/remove")){
    		result = action.removeDeptInfo(data.getDeptNo());    	
			model.setActionId(1402);
			model.setWorkIp(httpReq.getRemoteAddr());
			model.setDescription("부서삭제");
			model.setParameter("삭제ID : " + request.getParameter("dept_no"));
	 		model.setStatus(result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");
			insertAdminAction aud = new insertAdminAction();
			aud.insertServeriAudit(model);
		}
		    	
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(result));
    }
}
