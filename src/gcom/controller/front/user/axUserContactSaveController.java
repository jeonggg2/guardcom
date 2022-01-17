package gcom.controller.front.user;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import gcom.user.model.UserInfoModel;
import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;

/**
 * Servlet implementation class axDeptController
 */
@WebServlet("/contact/save")
public class axUserContactSaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public axUserContactSaveController() {
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
    	
    
		UserInfoModel user = userService.getUserInfo(param);
		param.put("user_no", user.getUserNo());
		param.put("titel", request.getParameter("contact_subject"));
		param.put("body", request.getParameter("contact_body"));
		param.put("eMail", request.getParameter("user_mail"));
		param.put("conType", request.getParameter("contact_type"));
		
		HashMap<String, Object> data =  new HashMap<String, Object>();;
		try {
			data = userService.insertContactSave(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}

}
