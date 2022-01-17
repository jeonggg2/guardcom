package gcom.controller.front.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.Model.DeptModel;
import gcom.controller.action.deptAction;

/**
 * Servlet implementation class testController
 */
@WebServlet("/test/db")
public class testController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		deptAction act = new deptAction();
		List<DeptModel> m = act.getDeptList(1);
		deptAction ta2 = new deptAction();
		
		
	}


}
