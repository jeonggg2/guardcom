package gcom.controller.front.admin.ax;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.service.Personal.IPersonalService;
import gcom.service.Personal.PersonalServiceImpl;

//문의사항 답변등록
@WebServlet("/admin/user/contact/modify")
public class userContactCommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userContactCommentModify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	String commentId = request.getParameter("comment_id").toString();
    	param.put("comment_id", commentId);
    	
    	IPersonalService as = new PersonalServiceImpl();
    	HashMap<String, Object> comment_info = as.getCommentInfo(param); 
    	
    	request.setAttribute("comment_id", commentId);
    	request.setAttribute("comment_info", comment_info);
		request.getRequestDispatcher("/WEB-INF/admin/user_manage/ax/contact_popup/admin_contact_comment_modify.jsp").forward(request, response);
	}
}
