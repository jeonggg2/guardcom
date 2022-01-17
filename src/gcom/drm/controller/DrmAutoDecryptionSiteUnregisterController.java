package gcom.drm.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.common.util.ConfigInfo;
import gcom.drm.service.DrmAutoDecryptionSiteService;

@WebServlet("/admin/drm/autodecsite/unregister")
public class DrmAutoDecryptionSiteUnregisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrmAutoDecryptionSiteUnregisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DrmAutoDecryptionSiteService svc = new DrmAutoDecryptionSiteService();
		
 		HashMap<String, Object> data = new HashMap<String, Object>();
 		if (svc.unregister(request.getParameter("data"))) {
 			data.put("returnCode", ConfigInfo.RETURN_CODE_SUCCESS);
 		} else {
 			data.put("returnCode", ConfigInfo.RETURN_CODE_ERROR);
 		}
 		
 		data.putAll(data);
 		response.setContentType("application/json; charset=UTF-8");
 		response.getWriter().write(new Gson().toJson(data));
	}
}
