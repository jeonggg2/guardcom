package gcom.drm.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.drm.service.DrmDecryptionRequestService;

@WebServlet("/admin/drm/decreq")
public class DrmDecryptionRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrmDecryptionRequestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/admin/drm/decryption_request.jsp").forward(request, response);
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//paging row param
		map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
		map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );
		
		//Search param
		map.put("dept", request.getParameterValues("dept[]"));
		map.put("user_id", request.getParameter("user_id").toString());
		map.put("user_name", request.getParameter("user_name").toString());
		map.put("user_phone", request.getParameter("user_phone").toString());
		map.put("user_duty", request.getParameter("user_duty").toString());
		map.put("user_rank", request.getParameter("user_rank").toString());
		map.put("user_number", request.getParameter("user_number").toString());
		map.put("policy_permit", request.getParameter("policy_permit").toString());
		
		DrmDecryptionRequestService svc = new DrmDecryptionRequestService();
		HashMap<String, Object> data = svc.getList(map);
		
		data.putAll(data);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));
		
	}
	
}
