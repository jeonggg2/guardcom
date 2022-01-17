package gcom.controller.front.ax.Do;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.common.util.ConfigInfo;
import gcom.common.util.PwCheck;
import gcom.controller.action.updateAction;

/**
 * Servlet implementation class axCommonUI
 */
@WebServlet("/user/info/save")
public class axUserInfoSave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<String, Object> param =  new HashMap<String, Object>();
		
		String userNo = request.getParameter("user_no").toString();
		String changePassword = request.getParameter("change_password_input");
		String changePasswordCheck = request.getParameter("change_password_check");
		String phone = request.getParameter("save_mem_phone");
		String changePasswordYn = "N";
		String id = request.getParameter("id");
		String number = request.getParameter("number");
		
		HashMap<String, Object> data =  new HashMap<String, Object>();
		
		if (!changePassword.equals(changePasswordCheck)) {
			data.put("returnCode", ConfigInfo.EXIST_FAIL_VERIFY);
		} else {
			
			if (!"".equals(changePassword)) {
				changePasswordYn = "Y";

				PwCheck pwCheck = new PwCheck();
				boolean pwCheckResult = pwCheck.Check(changePassword, id); 
				
				if (!pwCheckResult) {
					param.put("returnCode", ConfigInfo.PW_PATTERN);
					param.putAll(param);
					response.setContentType("application/json; charset=UTF-8");
					response.getWriter().write(new Gson().toJson(param));
					return;
				}
			}
			
			param.put("user_no", userNo);
			param.put("password", changePassword);
			param.put("changePasswordYn", changePasswordYn);
			param.put("phone", phone);
			param.put("number", number);
			
			updateAction action = new updateAction();
			
			try {
				data = action.updateUserInfoData(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
	}
}
