package gcom.controller.front.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcom.controller.action.deptAction;
import gcom.controller.action.getStatisticAction;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/dashboard")
public class dashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dashboardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpSession session = httpReq.getSession(false);

    	getStatisticAction action = new getStatisticAction();
    	deptAction deptAction = new deptAction();
    	List<Integer> dept = deptAction.getDeptIntList(Integer.parseInt(session.getAttribute("dept_no").toString()));
    	
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("dept", dept);

    	request.setAttribute("data", action.getUserAgentStatisticData(map));
		request.getRequestDispatcher("WEB-INF/dashboard/dashboard.jsp").forward(request, response);
	}
}