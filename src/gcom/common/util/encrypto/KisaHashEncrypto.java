package gcom.common.util.encrypto;

import gcom.common.util.encrypto.kisa.KISA_SHA256;

public class KisaHashEncrypto implements IHashEncrypto {
	public String HashEncrypt(String data){
		byte[] enc_result = new byte[32];		
		KISA_SHA256.SHA256_Encrpyt(data.getBytes(), data.getBytes().length, enc_result);

		String byteToString = "";
		for (int i=0; i<32; i++) {
			byteToString+=Integer.toHexString(0xff&enc_result[i]);
		}
		return byteToString;
				
	}

}
