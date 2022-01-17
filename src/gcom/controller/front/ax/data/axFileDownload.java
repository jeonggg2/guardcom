package gcom.controller.front.ax.data;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gcom.controller.action.getAction;

@WebServlet("/ax/file/download")
public class axFileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public axFileDownload() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("file_id", request.getParameter("file_id"));

		getAction action = new getAction();

		String filePath = action.getFilePath(map);

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		if (request.getProtocol().equals("HTTP/1.1")) {
			response.setHeader("Cache-Control", "no-cache;");
			response.setHeader("Cache-Control", "no-store;");
		}

		File file = null;
		InputStream fio = null;

		String file_name = URLDecoder.decode(request.getParameter("file_name").toString(), "UTF-8");
		Double file_offsetd = Double.parseDouble(request.getParameter("file_offset"));
		Double file_sized = Double.parseDouble(request.getParameter("file_size"));
		int file_offset = file_offsetd.intValue();// Integer.parseInt(request.getParameter("file_offset"));
		int file_size = file_sized.intValue();// Integer.parseInt(request.getParameter("file_size"));
		int file_offset2 = 0;
		int file_size2 = 0;

		if (file_size == 2147483647) {

			file_sized -= 2147483647;

			file_size2 = file_sized.intValue();
		}

		if (file_offset == 2147483647) {

			file_offsetd -= 2147483647;

			file_offset2 = file_offsetd.intValue();
		}

		file = new File(filePath);

		fio = new FileInputStream(file);

		if (file_size2 == 0) {

			response.setContentLength(file_size);
			response.setHeader("Content-Length", "" + file_size);

		}

		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + URLEncoder
						.encode(file_name.substring(file_name.lastIndexOf("\\") + 1), "UTF-8").replace("+", "%20")
				+ "\";");

		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

		fio.skip(file_offset);

		if (file_offset2 > 0) {

			fio.skip(file_offset2);
		}

		byte[] buffer = new byte[2048];
		int bufferSize = 0;
		int totalSize = file_size;

		while ((bufferSize = fio.read(buffer)) != -1) {

			if (totalSize < 2048) {

				bos.write(buffer, 0, totalSize);
				bos.flush();
				break;
			}

			bos.write(buffer, 0, bufferSize);
			bos.flush();
			totalSize -= bufferSize;
		}

		if (file_size2 > 0) {

			bufferSize = 0;
			totalSize = file_size2;

			while ((bufferSize = fio.read(buffer)) != -1) {

				if (totalSize < 2048) {

					bos.write(buffer, 0, totalSize);
					bos.flush();
					break;
				}

				bos.write(buffer, 0, bufferSize);
				bos.flush();
				totalSize -= bufferSize;
			}
		}

		bos.flush();
		if (fio != null)
			fio.close();

	}

}
