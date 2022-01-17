package gcom.controller.front.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcom.controller.action.deptAction;

@WebServlet("/account/request/view")
public class axUserAccountRequestViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axUserAccountRequestViewController() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override  
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	deptAction act = new deptAction();
        request.setAttribute("dept", act.getDeptList(1));
    	
    	request.getRequestDispatcher("/WEB-INF/user/ax/main_account_request_modal_ax.jsp").forward(request, response);
	}
}
