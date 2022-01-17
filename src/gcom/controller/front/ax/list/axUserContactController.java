package gcom.controller.front.ax.list;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import gcom.user.service.UserServiceImpl;
import gcom.user.service.UserService;

@WebServlet("/ax/contact/list")
public class axUserContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axUserContactController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
    	HttpSession session = httpReq.getSession(false);
    	
		HashMap<String, Object> param = new HashMap<String, Object>();
		String user_id = (String)session.getAttribute("user_id");
		param.put("user_id", user_id);
		
		param.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		param.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );
		
		
		UserService userService = new UserServiceImpl();
		
		HashMap<String, Object> data =  userService.getUserContactInfo(param);

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}

}
