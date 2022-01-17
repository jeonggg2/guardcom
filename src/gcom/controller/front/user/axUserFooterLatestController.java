package gcom.controller.front.user;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.controller.action.admin.getAdminAction;
import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/ax/footer/latest")
public class axUserFooterLatestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axUserFooterLatestController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = new UserServiceImpl();
		HashMap<String, Object> data = userService.getLatestListData();

		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
