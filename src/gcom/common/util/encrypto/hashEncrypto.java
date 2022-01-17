package gcom.common.util.encrypto;

public class hashEncrypto {

	public static String HashEncrypt(String data){
		
		IHashEncrypto encrypto = new KisaHashEncrypto();
		
		return encrypto.HashEncrypt(data);
	}
	
}
