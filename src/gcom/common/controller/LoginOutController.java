package gcom.common.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcom.Model.ServerAuditModel;
import gcom.controller.action.admin.insertAdminAction;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/logout")
public class LoginOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginOutController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 HttpServletRequest httpReq = (HttpServletRequest)request;
         HttpSession session = httpReq.getSession();
         
 		ServerAuditModel model = new ServerAuditModel();
 		model.setAdminId((String)session.getAttribute("user_id"));
 		model.setActionId(1000);
 		model.setWorkIp(httpReq.getRemoteAddr());
 		model.setDescription("로그아웃");
 		model.setParameter("사용자 ::" +  (String)session.getAttribute("user_id") + " 로그아웃");
 		model.setStatus("성공");
		insertAdminAction action = new insertAdminAction();
		action.insertServeriAudit(model);
         session.invalidate();
         String context = request.getContextPath();
         response.sendRedirect(context + "/");
//         request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	}
}
