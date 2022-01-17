package gcom.controller.front.admin.ax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//문의사항 답변등록
@WebServlet("/admin/user/contact/write")
public class userContactCommentWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userContactCommentWrite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String contactId = request.getParameter("contact_id").toString();
    	
    	request.setAttribute("contact_id", contactId);
		request.getRequestDispatcher("/WEB-INF/admin/user_manage/ax/contact_popup/admin_contact_comment_write.jsp").forward(request, response);
	}
}
