package gcom.controller.front.admin.ax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/ax/admin/policy/export")
public class axAdminPolicyExport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tabCode = request.getParameter("tabCode").toString();
		String loadAxUrl = "/WEB-INF/admin/policy_manage/ax/policy_export_network_list_ax.jsp";
		 if ("network".equals(tabCode)) {
			 loadAxUrl = "/WEB-INF/admin/policy_manage/ax/policy_export_network_list_ax.jsp";
         } else if ("serial".equals(tabCode)) {
        	 loadAxUrl = "/WEB-INF/admin/policy_manage/ax/policy_export_serial_list_ax.jsp";	
         } else if ("website".equals(tabCode)) {
        	 loadAxUrl = "/WEB-INF/admin/policy_manage/ax/policy_export_website_list_ax.jsp";	
         } 
		
		request.getRequestDispatcher(loadAxUrl).forward(request, response);
	}
}
