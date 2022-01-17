package gcom.controller.front.admin.ax;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcom.controller.action.deptAction;
import gcom.controller.action.admin.getAdminAction;


@WebServlet("/ax/admin/admininput/*")
public class adminInfoInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String requestUri = request.getRequestURI();
		HashMap<String, Object> param =  new HashMap<String, Object>();

        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpSession session = httpReq.getSession(false);

        String context = httpReq.getContextPath();
		deptAction act = new deptAction();
		request.setAttribute("deptList", act.getDeptList(Integer.parseInt(session.getAttribute("dept_no").toString())));
				
		if(request.getParameterMap().containsKey("admin_no"))
			param.put("no",  request.getParameter("admin_no"));

		
		if(requestUri.equals(context + "/ax/admin/admininput/modify")){
			request.setAttribute("popup_type", "modify");
			
			getAdminAction uAction = new getAdminAction();
			request.setAttribute("adminInfo", uAction.getAdminUserInfo(param));

			request.getRequestDispatcher("/WEB-INF/admin/etc_manage/ax/pop_admin_info_input.jsp").forward(request, response);

		}else if(requestUri.equals(context + "/ax/admin/admininput/create")){
			request.setAttribute("popup_type", "create");    		
        	request.getRequestDispatcher("/WEB-INF/admin/etc_manage/ax/pop_admin_info_input.jsp").forward(request, response);
    	
		}
		
	}
}
