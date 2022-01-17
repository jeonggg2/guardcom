package gcom.controller.front.admin.ax;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.user.model.UserContactModel;
import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;

//문의사항보기
@WebServlet("/admin/user/contact/view")
public class userContactViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userContactViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	int contact_id = Integer.parseInt(request.getParameter("contactId").toString());
    	
    	HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("contact_id", contact_id);
		
		UserService userService = new UserServiceImpl();
		try {
			
			UserContactModel model = userService.getUserContactDetail(param);
			request.setAttribute("UserContactDetail", model);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		request.getRequestDispatcher("/WEB-INF/admin/user_manage/ax/popup_user_contact_view.jsp").forward(request, response);
	}
}
