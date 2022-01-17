package gcom.controller.front.ax.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.controller.action.getDataAction;

@WebServlet("/ax/audit/client/work")
public class axAuditWorkDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axAuditWorkDataController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getDataAction action = new getDataAction();

		int key = Integer.parseInt(request.getParameter("audit_id").toString());
		
		String data = action.getServerAuditWorkData(key);

		response.setContentType("application/text; charset=UTF-8");
		response.getWriter().write(data);		
	}

}
