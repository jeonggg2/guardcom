package gcom.controller.front.admin.ax.IO;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import gcom.common.util.encrypto.filehash.filehash;
import gcom.controller.action.deptAction;
import gcom.controller.action.admin.getAdminAction;


@WebServlet("/ax/admin/filehash")
public class axFileHash extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletContext().getRealPath("/tmp");

		String hash = filehash.getFileHash(path, request);

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("hash", hash);
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));

		
	}
}
