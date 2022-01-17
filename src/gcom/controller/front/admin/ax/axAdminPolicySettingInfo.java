package gcom.controller.front.admin.ax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.common.util.StringUtil;


/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/admin/user/assign/setting/info")
public class axAdminPolicySettingInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = StringUtil.nullToString(request.getParameter("type").toString(), "" );
		String code = StringUtil.nullToString(request.getParameter("code").toString(), "");
		
		String loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_usb.jsp";
		 if ("isUsbBlock".equals(type)) {
			 loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_usb.jsp";
         } else if ("isComPortBlock".equals(type)) {
        	 loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_serial.jsp";	
         } else if ("isNetPortBlock".equals(type)) {
        	 loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_network.jsp";	
         } else if ("isProcessList".equals(type)) {
        	 loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_process.jsp";	
         } else if ("isFilePattern".equals(type)) {
        	 loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_pattern.jsp";	
         } else if ("isWebAddr".equals(type)) {
        	 loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_website.jsp";	
         } else if ("isMsgBlock".equals(type)) {
        	 loadAxUrl = "/WEB-INF/admin/user_manage/ax/assign_popup/assign_policy_popup_msg.jsp";	
         } 
		
		request.setAttribute("code", code);
		request.getRequestDispatcher(loadAxUrl).forward(request, response);
	}
}
