package gc.crypto;

public class CekInfo {
	
	private int userNo;
	private int keyIndex;
	private byte[] cekData;	
	
	public CryptoStatus init(CryptoMgr cryptoMgr, int userNo, int keyIndex, byte[] ekekData, byte[] psHashData, byte[] password) {
		
		// userNo
		this.userNo = userNo;
		
		// keyIndex
		this.keyIndex = keyIndex;
		
		// kekData
		byte[] kekData = new byte[ekekData.length];
		
		CryptoStatus status = cryptoMgr.decryptKek(ekekData, psHashData, password, kekData);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}
		
		// cekData
		byte[] cekBuff = new byte[CryptoMgr.CM_DEFAULT_SK_KEY_LENGTH];
		
		status = cryptoMgr.generateSK(cekBuff);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}		

		cekData = new byte[CryptoMgr.CM_DEFAULT_SK_KEY_LENGTH * 2]; //패딩 길이 추가
		
		status = cryptoMgr.encryptData(kekData, kekData.length, cekBuff, cekData);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}		
		
		return CryptoStatus.CS_SUCCESS;
		
	}
	
	public byte[] getCekData() {
		
		return cekData;
	}
	
	public byte[] getBytes() {
		
		byte[] buffer = new byte[CryptoMgr.CM_DEFAULT_CEK_INFO_LENGTH];
		
		// userNo
		buffer[0] = (byte)(userNo & 0x000000ff);
		buffer[1] = (byte)((userNo & 0x0000ff00) >> 8);
		buffer[2] = (byte)((userNo & 0x00ff0000) >> 16);
		buffer[3] = (byte)((userNo & 0xff000000) >> 24);
		
		// keyIndex
		buffer[4] = (byte)(keyIndex & 0x000000ff);
		buffer[5] = (byte)((keyIndex & 0x0000ff00) >> 8);
		buffer[6] = (byte)((keyIndex & 0x00ff0000) >> 16);
		buffer[7] = (byte)((keyIndex & 0xff000000) >> 24);
		
		int length = cekData.length - CryptoMgr.CM_DEFAULT_SK_KEY_LENGTH;
		
		// cekDataLength
		buffer[8] = (byte)(length & 0x000000ff);
		buffer[9] = (byte)((length & 0x0000ff00) >> 8);
		buffer[10] = (byte)((length & 0x00ff0000) >> 16);
		buffer[11] = (byte)((length & 0xff000000) >> 24);
		
		// cekData		
		System.arraycopy(cekData, 0, buffer, 12, length);
		
		return buffer;
		
	}
	
}
