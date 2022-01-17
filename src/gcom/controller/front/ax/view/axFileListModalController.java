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

@WebServlet("/ax/report/filelist/detail")
public class axFileListModalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axFileListModalController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	 
		String no = request.getParameter("no").toString();
		String type = request.getParameter("type").toString();
		String file_id = request.getParameter("file_id").toString();

		request.setAttribute("no", no);
		request.setAttribute("type", type);
		request.setAttribute("file_id", file_id);

		request.getRequestDispatcher("/WEB-INF/report/ax/file_list_modal_ax.jsp").forward(request, response);
														 
	}

}
