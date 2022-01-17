package gcom.policy.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import gcom.common.util.JSONUtil;
import gcom.policy.model.PolicyModel;
import gcom.policy.service.IPolicyService;
import gcom.policy.service.PolicyService;

@WebServlet("/admin/policy/*")
public class DeptPolicyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeptPolicyController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getRequestURI().endsWith("/save")) {

			HashMap<String, Object> data = new HashMap<String, Object>();
			HashMap<String, Object> param = JSONUtil.convertJsonToHashMap(request.getParameter("apply_policy").toString());
			
	    	IPolicyService svc = new PolicyService();
	    	data = svc.savePolicyData(param);
	    	
			data.putAll(data);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(data));
			return;
		}
		
		super.doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		if (request.getRequestURI().endsWith("/get_policy_form")) {
	    	int no = Integer.parseInt(request.getParameter("no").toString());
	    	
	    	IPolicyService svc = new PolicyService();			
	    	PolicyModel data = svc.getPolicyDataByNo(no);
	    	
	    	ObjectMapper oMapper = new ObjectMapper();
	    	
	    	@SuppressWarnings("unchecked")
			HashMap<String, Object> map = oMapper.convertValue(data, HashMap.class);
	    	
	    	request.setAttribute("data", map);
	    	request.setAttribute("no", no);

			request.getRequestDispatcher("/WEB-INF/admin/policy/template/policy_form.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/admin/policy/dept.jsp").forward(request, response);
	}
}

