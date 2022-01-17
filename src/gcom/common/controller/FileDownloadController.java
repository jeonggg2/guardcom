package gcom.common.controller;

import static java.lang.System.out;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcom.common.util.ConfigInfo;


/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/common/filedownload")
public class FileDownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileDownloadController() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		String saveName = request.getParameter("save_name").toString();
		String realName = request.getParameter("real_name").toString();
        String filePath = ConfigInfo.FILE_UPLOAD_PATH;
        
        InputStream in = null;
        OutputStream os = null;
        File file = null;
        boolean skip = false;
        String client = "";

		try {
			
			try{
				file = new File(filePath, saveName);
				in = new FileInputStream(file);
	        }catch(FileNotFoundException fe){
	            skip = true;
	        }
			
			client = request.getHeader("User-Agent");
			 
	        // 파일 다운로드 헤더 지정
	        response.reset() ;
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Description", "JSP Generated Data");
	        
	        if(!skip){
	            // IE
	            if(client.indexOf("MSIE") != -1 || client.indexOf("rv") != -1 ){
	                response.setHeader ("Content-Disposition", "attachment; filename="+new String(realName.getBytes("KSC5601"),"ISO8859_1"));
	 
	            }else{
	                // 한글 파일명 처리
	            	realName = new String(realName.getBytes("utf-8"), "iso-8859-1");
	 
	                response.setHeader("Content-Disposition", "attachment; filename=\"" + realName + "\"");
	                response.setContentType("application/octer-stream"); 
	                response.setHeader("Content-Transfer-Encoding", "binary;");
	            } 
	             
	            response.setHeader ("Content-Length", ""+file.length() );
	       
	            os = response.getOutputStream();
	            byte b[] = new byte[(int)file.length()];
	            int leng = 0;
	             
	            while( (leng = in.read(b)) > 0 ){
	                os.write(b,0,leng);
	            }
	 
	        }else{
	            response.setContentType("text/html;charset=UTF-8");
	            out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
	        }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
