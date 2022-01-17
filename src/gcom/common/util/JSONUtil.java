package gcom.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;

/**
 * JSONUtil 는 Json Parse 관련 Utility
 *
 * @author gillee
 * @version  / 1.0
 * @since 2017-06-27
 */
public class JSONUtil {

	public static JSONUtil getInstance() {
        JSONUtil instance = new JSONUtil();
		return instance;
	}
	
	/**
     *
	 * ajax result JSONObject
     *
	 * @param response response 객체
	 * @param returnCode 리턴코드
	 * @param message 리턴메세지
	 * @param args Object
	 */
	public void returnResultObject(HttpServletResponse response, JSONObject jsonObject) {

		PrintWriter out = null;
		try {
			response.setContentType("application/x-json; charset=UTF-8");
			out = response.getWriter();
			out.print(jsonObject);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if( out != null) out.close();
		}
	}

	/**
     *
	 * ajax result ContentType (plain/text)
     *
	 * @param response response 객체
	 * @param message 리턴메세지
	 * @param args Object
	 */
	public void returnResultText(HttpServletResponse response, String message) {

		PrintWriter out = null;
		try {
			response.setContentType("plain/text; charset=UTF-8");
			out = response.getWriter();
			out.write(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if( out != null) out.close();
		}
	}
	
    public static HashMap<String, Object> convertJsonToObject(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() { };
        HashMap<String, Object> object = objectMapper.readValue(json, typeReference);
        return object;
    }
    
	/**
    *
	 * ajax result ContentType (plain/text)
    *
	 * @param json String
	 * @param return HashMap
	 */
	 public static HashMap<String, Object> convertJsonToHashMap(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() { };
        HashMap<String, Object> object = objectMapper.readValue(json, typeReference);
        return object;
    }
	 
		/**
	    *
		 * ajax result ContentType (plain/text)
	    *
		 * @param json String
		 * @param return HashMap
		 */
		 public static List<Map<String, Object>> convertJsonToHashListMap(String json) throws JsonParseException, JsonMappingException, IOException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        TypeReference<ArrayList<Map<String, Object>>> typeReference = new TypeReference<ArrayList<Map<String, Object>>>() { };
	        ArrayList<Map<String, Object>> object = objectMapper.readValue(json, typeReference);
	        return object;
	    }
}
