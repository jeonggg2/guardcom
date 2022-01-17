package gcom.controller.front.ax;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcom.user.model.MemberPolicyModel;
import gcom.user.service.UserServiceImpl;
import gcom.user.service.UserService;

/**
 * Servlet implementation class axDeptController
 */
@WebServlet("/ax/main/policy")
public class axUserMemberPolicyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public axUserMemberPolicyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
    	String user_id = (String)session.getAttribute("user_id");
    	 
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("user_id", user_id);
    	
    	UserService userService = new UserServiceImpl();
    	List<MemberPolicyModel> list = userService.getMemberPolicyInfo(param);
    	
    	request.setAttribute("userPolicyInfo", list);
		request.getRequestDispatcher("/WEB-INF/user/ax/main_user_policy_ax.jsp").forward(request, response);
	}

}
