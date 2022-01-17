package gcom.common.util.encrypto.filehash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.Base64Encoder;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import gc.crypto.CryptoMgr;
import gc.crypto.CryptoStatus;

public class filehash {

	public static String getFileHash(String path,HttpServletRequest request){
		
		String hash = "";
		
		try {
			MultipartRequest mr = new MultipartRequest(request, path, 1024*1024*128, "utf-8", new DefaultFileRenamePolicy());

			File file = mr.getFile("hash");

			FileInputStream fis = new FileInputStream(file);
			int fileLength = (int)file.length();
			
			if (fileLength > 10000000) {
				fileLength = 10000000;
			}			
			
			CryptoStatus status;
			CryptoMgr cryptoMgr = new CryptoMgr();
			
			byte[] data = new byte[fileLength];
			byte[] hashData = new byte[32];

			status = cryptoMgr.init();
			if (status != CryptoStatus.CS_SUCCESS) {
			}
			fis.read(data, 0, fileLength);	
			
			cryptoMgr.hashData(data, hashData.length, hashData);		
			
			hash = Base64Encoder.encode(hashData);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return hash;
	}
	
}
