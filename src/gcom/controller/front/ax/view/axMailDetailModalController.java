package gcom.controller.front.ax.view;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcom.Model.MailExportContentModel;
import gcom.controller.action.getAction;
import gcom.user.model.MemberPolicyModel;
import gcom.user.service.UserServiceImpl;

@WebServlet("/ax/report/mail/detail")
public class axMailDetailModalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axMailDetailModalController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	 
    	HashMap<String, Object> param = new HashMap<String, Object>();
    	param.put("mail_no", request.getParameter("mail_no"));

    	getAction action = new getAction();
    	
    	MailExportContentModel att = action.getMailExportContent(param);
    	
    	request.setAttribute("mail",  att);
		request.getRequestDispatcher("/WEB-INF/report/ax/mail_content_modal_ax.jsp").forward(request, response);
														 
	}

}
