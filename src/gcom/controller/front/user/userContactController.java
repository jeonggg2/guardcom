package gcom.controller.front.user;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcom.user.model.UserInfoModel;
import gcom.user.service.UserServiceImpl;
import gcom.user.service.UserService;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/contact")
public class userContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userContactController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
    	String user_id = (String)session.getAttribute("user_id");
    	 
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("user_id", user_id);
    	
    	UserService userService = new UserServiceImpl();
    	UserInfoModel data = userService.getUserInfo(param);
    	
    	request.setAttribute("userInfo", data);
    	
		request.getRequestDispatcher("/WEB-INF/user/contact.jsp").forward(request, response);
	}
}
