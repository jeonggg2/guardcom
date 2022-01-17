package gcom.controller.front.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.controller.action.getStatisticAction;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/report/dashboard")
public class reportDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public reportDashboardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

    	getStatisticAction action = new getStatisticAction();

    	Map<String,Object> map = new HashMap<String, Object>();
    	request.setAttribute("data", action.getUserAgentStatisticData(map));
		request.getRequestDispatcher("/WEB-INF/dashboard/dashboard_rep.jsp").forward(request, response);
	}
}