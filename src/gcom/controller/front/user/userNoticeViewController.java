package gcom.controller.front.user;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.Model.FileInfoModel;
import gcom.service.Personal.IPersonalService;
import gcom.service.Personal.PersonalServiceImpl;
import gcom.user.model.UserNoticeModel;
import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/notice/view")
public class userNoticeViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userNoticeViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	int bbsId = Integer.parseInt(request.getParameter("bbs_id").toString());
    	String fileFlag = request.getParameter("file_yn").toString();
    	int fileId = Integer.parseInt(request.getParameter("file_id").toString());
    	
    	HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("bbs_id", bbsId);
		
		UserService userService = new UserServiceImpl();
		IPersonalService as = new PersonalServiceImpl();
		
		try {
			UserNoticeModel model = userService.getUserNoticeDetail(param);
			request.setAttribute("UserNoticeDetail", model);
			request.setAttribute("att_file_flag", fileFlag);
			
			userService.updateNoticeViewCount(param);
			
			if ("Y".equals(fileFlag)) {
				param.put("file_id", fileId);
				
				FileInfoModel file = as.getAttFileInfo(param);
				request.setAttribute("AttFileInfo", file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
    	request.getRequestDispatcher("/WEB-INF/user/ax/notice_view_ax.jsp").forward(request, response);
	}
}
