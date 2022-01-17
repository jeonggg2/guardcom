package gcom.controller.front.ax.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.Model.DeptModel;
import gcom.controller.action.deptAction;
//import gcom.controller.action.getDataAction;

@WebServlet("/dept/data/info")
public class axDeptInfoDataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axDeptInfoDataController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deptAction action = new deptAction();

		int deptNo = Integer.parseInt(request.getParameter("dept_no").toString());
		
		DeptModel data = action.getDeptInfo(deptNo);

		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));		

	}

}
