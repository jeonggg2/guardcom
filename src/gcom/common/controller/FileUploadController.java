package gcom.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import gcom.common.util.ConfigInfo;


/**
 * Servlet implementation class dashboardServlet
 */
@WebServlet("/common/fileupload")
public class FileUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileUploadController() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);	// Multipart 파일전송 여부 확인

		Map<String, Object> resultMap = new HashMap<String, Object>();			// 결과를 위한 Map 
		UUID uuid = UUID.randomUUID();											// 파일명 break 방지
        String saveFileName = uuid.toString();									// 저장할 파일명
        String viewFileName = "";												// 업로드한 파일명 
        String fieldName = "";													// 파일 업로드 Element 필드명
        String contentType = "";
        String filePath = ConfigInfo.FILE_UPLOAD_PATH;

		try {
			if (isMultipart) {
				File tempFile = null;											// 업로드 된 파일의 임시 저장 폴더
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setRepository(tempFile);

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(10 * 1024 * 1024);	// 10M 파일 크기 제한   

				List<FileItem> items = upload.parseRequest(request);			//request 업로드 파일 생성

				Iterator iter = items.iterator();
				while (iter.hasNext()) {										// 파일을 가져와 확인
					FileItem fileItem = (FileItem) iter.next();

					if (!fileItem.isFormField()) {								// 업로드 되는 파일 형태 확인 ture : 텍스트 , false : file
						if (fileItem.getSize() > 0) {							// 파일이 업로드 되었는지 확인 0이상  : 업로드 완료, 0 : 업로드 실패
							fieldName 		= fileItem.getFieldName();
							viewFileName 	= fileItem.getName();
							contentType 	= fileItem.getContentType();
							
							resultMap.put("fieldName", fieldName);
							resultMap.put("viewFileName", viewFileName);
							resultMap.put("contentType", contentType);
							resultMap.put("saveFileName", saveFileName);
							resultMap.put("filepath", filePath);
							
	                        File path = new File(filePath);					// 업로드 경로 생성
	                        if (!path.exists()) {								// 업로드 경로의 dir 존재 여부 확인
	                            boolean status = path.mkdirs();					// 업로드 경로가 존재 하지 않을 경우, 해당 경로에 dir 생성
	                            
	                            if(status) {									// dir 생성 여부 확인
		                        	System.out.println("생성");
		                        } else {
		                        	System.out.println("실패");
		                        }
	                        }
	                        
							try {
								File uploadedFile = new File(path + "/" + saveFileName);	// 실제 업로드되는 파일 경로와 이름 생성
								fileItem.write(uploadedFile);								// 해당 경로에 file 쓰기
								fileItem.delete(); 											// 카피 완료후 temp폴더의 temp파일을 제거
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}

					}
				}
			}
			resultMap.put("returnCode", ConfigInfo.RETURN_CODE_SUCCESS);
		} catch (org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException e) {
			resultMap.put("returnCode", ConfigInfo.EXIST_OVER_FILE_SIZE);
			System.out.println("파일 사이즈가 100메가 보다 더 초과되었습니다");
		} catch (Exception e) {
			resultMap.put("returnCode", ConfigInfo.RETURN_CODE_ERROR);
			System.out.println("업로드시 예기치 못한 오류가 발생하였습니다");
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(resultMap);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(new Gson().toJson(data));

	}
}
