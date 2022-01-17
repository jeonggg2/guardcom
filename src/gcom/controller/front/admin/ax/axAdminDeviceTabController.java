package gcom.controller.front.admin.ax;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.common.util.JSONUtil;

//비인가 USB관리
@WebServlet("/admin/device/tab")
public class axAdminDeviceTabController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public axAdminDeviceTabController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String tabCdoe = request.getParameter("tabCode").toString();	
    	String tabUrl = "/WEB-INF/admin/device_manage/ax/admin_device_unauthusb.jsp";
    	if ("usb_policy".equals(tabCdoe)) {
    		tabUrl = "/WEB-INF/admin/device_manage/ax/admin_device_unauthusb.jsp";
    	} else if ("usb_block".equals(tabCdoe)) {
    		tabUrl = "/WEB-INF/admin/device_manage/ax/admin_device_usb_block.jsp";
    	}
    	
		request.getRequestDispatcher(tabUrl).forward(request, response);
	}
}
