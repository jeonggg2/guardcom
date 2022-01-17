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
import gcom.common.util.JSONUtil;
import gcom.drm.service.DrmAutoEncryptioinFileExtensionService;

@WebServlet("/admin/drm/autoencfileext/register")
public class DrmAutoEncryptionFileExtensionRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrmAutoEncryptionFileExtensionRegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override  
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     	
     	HashMap<String, Object> param = JSONUtil.convertJsonToHashMap(request.getParameter("data").toString());
		DrmAutoEncryptioinFileExtensionService svc = new DrmAutoEncryptioinFileExtensionService();
		
 		HashMap<String, Object> data = new HashMap<String, Object>();
 		if (svc.register(param.get("value").toString())) {
 			data.put("returnCode", ConfigInfo.RETURN_CODE_SUCCESS);
 		} else {
 			data.put("returnCode", ConfigInfo.RETURN_CODE_ERROR);
 		}
 		
 		data.putAll(data);
 		response.setContentType("application/json; charset=UTF-8");
 		response.getWriter().write(new Gson().toJson(data));
 	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setAttribute("Title", "자동 암호화 파일 확장자 등록");
    	request.setAttribute("Name", "확장자");
    	request.setAttribute("RegisterUrl", "/admin/drm/autoencfileext/register");
		request.getRequestDispatcher("/WEB-INF/admin/drm/popup/register.jsp").forward(request, response);
	}
    
    
    
}
