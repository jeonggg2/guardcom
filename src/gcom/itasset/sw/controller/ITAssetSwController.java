package gcom.itasset.sw.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import gcom.common.util.JSONUtil;
import gcom.itasset.model.ITAssetHwModel;
import gcom.itasset.model.ITAssetSwModel;
import gcom.itasset.service.ITAssetSwService;

//부서관리
@WebServlet("/admin/itasset/sw/*")
public class ITAssetSwController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ITAssetSwController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getRequestURI().endsWith("/getpopup")) {
	    	int no = Integer.parseInt(request.getParameter("no").toString());
	    	
	    	if (no != -1) {
	    		ITAssetSwService s = new ITAssetSwService();	    		
	    		ITAssetSwModel m = s.getItem(no);
	    		request.setAttribute("data", m);
	    	}
	    	
	    	request.setAttribute("no", no);
			request.getRequestDispatcher("/WEB-INF/admin/itasset/sw/template/popup.jsp").forward(request, response);
			return;
		}
		
		if (request.getRequestURI().endsWith("/getcommitpopup")) {
			List<Map<String, Object>> apply_list = JSONUtil.convertJsonToHashListMap(request.getParameter("apply_list").toString());
	    	request.setAttribute("apply_list", apply_list);
			request.getRequestDispatcher("/WEB-INF/admin/itasset/sw/template/commit_popup.jsp").forward(request, response);
			return;
		}
		

		if (request.getRequestURI().endsWith("/getUsageList")) {
			
			String sw_name = request.getParameter("sw_name");
			
			if (sw_name == null) {
				return;
			}
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("sw_name", sw_name);
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			ITAssetSwService as = new ITAssetSwService();
			
			List<ITAssetHwModel> list = as.getUsageList(map);
			data.put("data", list);
			
			int total = as.getUsageListCount(map);
			data.put("recordsTotal", total);
			data.putAll(data);
			
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(data));		
			return;
			
		}
		
		if (request.getRequestURI().endsWith("/commit")) {
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> param = JSONUtil.convertJsonToHashMap(request.getParameter("data").toString());
			List<Map<String, Object>> apply_list = JSONUtil.convertJsonToHashListMap(request.getParameter("apply_list").toString());
			
			ITAssetSwService s = new ITAssetSwService();
			HashMap<String, Object> data = new HashMap<String, Object>();
			
			for(Map<String, Object> item : apply_list) {				
				map.put("no", item.get("no"));
				map.put("type", param.get("type"));
				map.put("has_own", param.get("has_own"));
				map.put("intro_date", param.get("intro_date"));
				map.put("has_managed", param.get("has_managed"));
				map.put("is_commit", true);				
				data = s.modifyItem(map);
				map.clear();
			}
			
			data.putAll(data);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(data));
			return;
		}
		
		if (request.getRequestURI().endsWith("/list")) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			if (request.getParameter("start") == null) {
				map.put("startRow", 0);
			} else {
				map.put("startRow", Integer.parseInt( request.getParameter("start").toString()) );
			}
			if (request.getParameter("length") != null) {
				map.put("endRow", 20);
			} else {
				map.put("endRow", Integer.parseInt( request.getParameter("length").toString()) );
			}
			
			map.putAll( JSONUtil.convertJsonToHashMap(request.getParameter("search_condition")));
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			ITAssetSwService as = new ITAssetSwService();
			
			List<ITAssetSwModel> list = as.getList(map);
			data.put("data", list);
			
			int total = as.getListCount(map);
			data.put("recordsTotal", total);
			data.put("recordsFiltered", total);	
			data.putAll(data);
			
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(data));		
			return;
		}
		
		if (request.getRequestURI().endsWith("/register")) {
			HashMap<String, Object> map = JSONUtil.convertJsonToHashMap(request.getParameter("data").toString());			
			ITAssetSwService s = new ITAssetSwService();			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data = s.addItem(map);			
			data.putAll(data);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(data));
			return;
		}
		
		if (request.getRequestURI().endsWith("/unregister")) {

			HashMap<String, Object> map = new HashMap<String, Object>();			
			List<Map<String, Object>> apply_list = JSONUtil.convertJsonToHashListMap(request.getParameter("apply_list").toString());

			ITAssetSwService s = new ITAssetSwService();	
			HashMap<String, Object> data = null;
			
			for(Map<String, Object> item : apply_list) {				
				map.put("no", item.get("no"));
				data = s.deleteItem(map);
				map.clear();
			}
			
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(data));
			return;
		}
		
		if (request.getRequestURI().endsWith("/modify")) {
			HashMap<String, Object> map = JSONUtil.convertJsonToHashMap(request.getParameter("data").toString());	
			ITAssetSwService s = new ITAssetSwService();			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data = s.modifyItem(map);			
			data.putAll(data);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(data));
			return;
		}
		
		super.doPost(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		if (request.getRequestURI().endsWith("/list")) {		
			ITAssetSwService as = new ITAssetSwService();				
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(new Gson().toJson(as.getSwNameList()));		
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/admin/itasset/sw/admin_itasset_sw.jsp").forward(request, response);
	}
}

