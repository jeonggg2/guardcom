package gcom.controller.front.ax.list;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.user.model.UserNoticeModel;
import gcom.user.service.UserServiceImpl;
import gcom.user.service.UserService;

@WebServlet("/ax/user/notice/list")
public class axUserNoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axUserNoticeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("search_type", request.getParameter("search_type").toString());
		param.put("search_text", request.getParameter("search_text").toString());
		param.put("startRow", request.getParameter("start_idx").toString());
		param.put("endRow", request.getParameter("end_idx").toString());
		
		UserService userService = new UserServiceImpl();
		int cnt = userService.getUserNoticeListCount(param);
    	List<UserNoticeModel> list = userService.getUserNoticeList(param);
    	
    	request.setAttribute("start_idx", request.getParameter("start_idx").toString());
    	request.setAttribute("list_cnt", cnt);
    	request.setAttribute("UserNoticeList", list);
		request.getRequestDispatcher("/WEB-INF/user/ax/notice_list_ax.jsp").forward(request, response);
		
	}

}
