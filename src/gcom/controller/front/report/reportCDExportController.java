package gcom.controller.front.report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/report/cdexportlog")
public class reportCDExportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public reportCDExportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/report/log_cd_export.jsp").forward(request, response);
	}
}