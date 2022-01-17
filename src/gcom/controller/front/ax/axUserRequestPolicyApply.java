package gcom.controller.front.ax;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.user.model.MemberPolicyModel;
import gcom.user.service.UserService;
import gcom.user.service.UserServiceImpl;


/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/ax/main/req/policy")
public class axUserRequestPolicyApply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> param = new HashMap<String, Object>();
		String policyNo = request.getParameter("policy_no").toString();
		param.put("policy_no", policyNo);
		
		UserService us = new UserServiceImpl();
		
		MemberPolicyModel data = us.getSettingPolicyInfo(param);
		
		request.setAttribute("data", data);
		request.getRequestDispatcher("/WEB-INF/user/ax/pop_req_policy_apply.jsp").forward(request, response);
	}
}
