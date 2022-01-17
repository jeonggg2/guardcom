package gcom.common.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import gcom.Model.ServerAuditModel;
import gcom.common.util.ConfigInfo;
import gcom.controller.action.commonAction;
import gcom.controller.action.admin.insertAdminAction;

/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/login/check")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   	 HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpSession session = httpReq.getSession();
		
    	String loginType = request.getParameter("loginType");
    	String userId = request.getParameter("att_staf_id");
    	String userPw = request.getParameter("att_staf_pwd");
    	
    	String context = httpReq.getContextPath();
    	
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("loginType", loginType);
		param.put("userId", userId);
		param.put("userPw", userPw);
		param.put("userIp", request.getRemoteAddr());
		param.put("session", request.getSession());
		commonAction comm = new commonAction();
		
		HashMap<String, Object> data = comm.getLoginCheckResult(param);
		data.put("parameter", "로그인");
		
		String ReturnCode = data.get("returnCode").toString();

		ServerAuditModel model = new ServerAuditModel();
		model.setAdminId(userId);
		model.setActionId(1000);
		model.setWorkIp(httpReq.getRemoteAddr());
		
		
		//로그인 성공시
		if (ConfigInfo.RETURN_CODE_SUCCESS.equals(ReturnCode)) {
			session.setAttribute("user_id", data.get("userId"));
			if ("U".equals(loginType)){	//유저로그인 성공시
				model.setDescription("사용자 로그인");
				model.setParameter("사용자 ::" +  userId + " 로그인");
				model.setStatus("성공");

				session.setAttribute("user_nm", data.get("userName"));
				session.setAttribute("admin_mode", -1); 
				session.setAttribute("login_root", context+"/main");

			}else{	//관리자 로그인 성공시
				model.setDescription("관리자 로그인");
				model.setParameter("관리자 ::" +  userId + " 로그인");
				model.setStatus("성공");
				session.setAttribute("admin_mode", data.get("adminMode")); 
				
				if(data.get("adminMode").toString().equals("2")){
					session.setAttribute("login_root", context+"/report/dashboard");					
				}else{
					session.setAttribute("login_root", context+"/dashboard");					
				}
				
			}
			session.setAttribute("dept_no", data.get("deptNo"));

		}else{
			//로그인 실패시
			if ("C".equals(loginType)){
				model.setDescription("관리자 로그인");

				if(ReturnCode.equals(ConfigInfo.NOT_CORRECT_IP) ){	//IP실패일경우
					model.setParameter("관리자 :: (아이디 : " + data.get("userId").toString() + ") 비인가 단말기에서 접속시도 - " + request.getRemoteAddr());

				}else if(ReturnCode.equals(ConfigInfo.NOT_EXIST_USER)){
					ReturnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
					model.setParameter("관리자 :: 존재하지 않는 아이디 (" + userId + ") 로그인 시도");

				}else if(ReturnCode.equals(ConfigInfo.NOT_CORRECT_PASSWORD)){
					ReturnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
					model.setParameter("관리자 :: 아이디 : " + data.get("userId").toString() + " 패스워드가 불일치 ");
				}				
			}else{	//사용자로그인 실패시
				model.setDescription("사용자 로그인");

				if(ReturnCode.equals(ConfigInfo.NOT_EXIST_USER)){
					ReturnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
					model.setParameter("사용자 :: 존재하지 않는 아이디 (" + userId + ") 로그인 시도");
				}else if(ReturnCode.equals(ConfigInfo.NOT_CORRECT_PASSWORD)){
					ReturnCode = ConfigInfo.NOT_CORRECT_PASSWORD_ID;
					model.setParameter("사용자 :: 아이디 : " + data.get("userId").toString() + " 패스워드가 불일치 ");
				}				
			}
			model.setStatus("실패");

		}
		
		insertAdminAction action = new insertAdminAction();
		action.insertServeriAudit(model);
		
		data.put("returnCode", ReturnCode);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
