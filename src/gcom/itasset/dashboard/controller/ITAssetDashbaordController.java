package gcom.itasset.dashboard.controller;

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
import gcom.itasset.dashboard.service.ITAssetDashboardSerivce;

@WebServlet("/admin/itasset/dashboard")
public class ITAssetDashbaordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ITAssetDashbaordController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpSession session = httpReq.getSession(false);

    	deptAction deptAction = new deptAction();
    	List<Integer> dept = deptAction.getDeptIntList(Integer.parseInt(session.getAttribute("dept_no").toString()));
    	
        ITAssetDashboardSerivce svc = new ITAssetDashboardSerivce();

    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("dept", dept);
    	
    	request.setAttribute("data", svc.getStatisticData(map));
		request.getRequestDispatcher("/WEB-INF/admin/itasset/dashboard/itasset_dashboard.jsp").forward(request, response);
	}
}
