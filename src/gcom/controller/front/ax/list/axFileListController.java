package gcom.controller.front.ax.list;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.controller.action.getAction;

@WebServlet("/ax/file/list")
public class axFileListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public axFileListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		getAction action = new getAction();
		
		map.put("type", request.getParameter("type").toString());
		map.put("no", request.getParameter("no").toString());
		
		List<HashMap<String, Object>> data = action.getFileList(map);

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("data", data);
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(result));
		
	}

}
