package gcom.controller.front.report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/report/users")
public class reportUserGroupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public reportUserGroupController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/report/user_report.jsp").forward(request, response);
	}

}