package gc.crypto;

public class EkekInfo {
	
	private int userNo;
	private byte[] ekekData;
	
	public CryptoStatus init( int userNo, byte[] ekekData) {
		
		// userNo
		this.userNo = userNo;
		
		// ekekData
		this.ekekData = new byte[ekekData.length]; //패딩 길이 추가
		
		return CryptoStatus.CS_SUCCESS;
		
	}
	
	public CryptoStatus init(CryptoMgr cryptoMgr, int userNo, byte[] psHashData, byte[] password, byte[] kekData) {
		
		// userNo
		this.userNo = userNo;
		
		// ekekData
		ekekData = new byte[kekData.length + CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH]; //패딩 길이 추가
		
		CryptoStatus status = cryptoMgr.encryptKek(kekData, psHashData, password, ekekData);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}
		
		return CryptoStatus.CS_SUCCESS;
		
	}
	public CryptoStatus init(CryptoMgr cryptoMgr, int userNo, byte[] psHashData, byte[] password) {
		
		// userNo
		this.userNo = userNo;
		
		// ekekData
		byte[] kekData = new byte[CryptoMgr.CM_DEFAULT_SK_KEY_LENGTH];
		
		CryptoStatus status = cryptoMgr.generateSK(kekData);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}		
		
		ekekData = new byte[CryptoMgr.CM_DEFAULT_SK_KEY_LENGTH];
		
		status = cryptoMgr.encryptKek(kekData, psHashData, password, ekekData);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}
		
		return CryptoStatus.CS_SUCCESS;
		
	}
	
	public byte[] getEkekData() {
		
		return ekekData;
	}
	
	public byte[] getBytes() {
		
		byte[] buffer = new byte[CryptoMgr.CM_DEFAULT_EKEK_INFO_LENGTH];
		
		// UserNo
		buffer[0] = (byte)(userNo & 0x000000ff);
		buffer[1] = (byte)((userNo & 0x0000ff00) >> 8);
		buffer[2] = (byte)((userNo & 0x00ff0000) >> 16);
		buffer[3] = (byte)((userNo & 0xff000000) >> 24);
		
		// keyIndex
		buffer[4] = 0;
		buffer[5] = 0;
		buffer[6] = 0;
		buffer[7] = 0;
		
		int length = ekekData.length - CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH;
		
		// ekekDataLength
		buffer[8] = (byte)(length & 0x000000ff);
		buffer[9] = (byte)((length & 0x0000ff00) >> 8);
		buffer[10] = (byte)((length & 0x00ff0000) >> 16);
		buffer[11] = (byte)((length & 0xff000000) >> 24);
		
		// ekekData
		System.arraycopy(ekekData, 0, buffer, 12, length);
		
		return buffer;
		
	}
	
}
