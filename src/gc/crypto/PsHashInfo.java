package gc.crypto;

public class PsHashInfo {
	
	private int userNo;
	private byte[] psHashData;	
	
	public PsHashInfo() {}
	
	public PsHashInfo(byte[] psHash) {
		
		// userNo
		this.userNo = ((((int)psHash[3]) & 0xFF) << 24) | ((((int)psHash[2]) & 0xFF) << 16) | ((((int)psHash[1]) & 0xFF) << 8) | (((int)psHash[0]) & 0xFF);
				
		// psHashData
		int length = ((((int)psHash[7]) & 0xFF) << 24) | ((((int)psHash[6]) & 0xFF) << 16) | ((((int)psHash[5]) & 0xFF) << 8) | (((int)psHash[4]) & 0xFF);
		
		this.psHashData = new byte[length];
		System.arraycopy(psHash, 8, this.psHashData, 0, length);
		
	}
	
	public CryptoStatus init(CryptoMgr cryptoMgr, int userNo, byte[] saltData, byte[] password) {
		
		// userNo
		this.userNo = userNo;
		
		// psHashData
		this.psHashData = new byte[CryptoMgr.CM_DEFAULT_HASH_LENGTH];
		return cryptoMgr.hashPassword(password, saltData, this.psHashData);
		
	}
	
	public byte[] getPsHashData() {
		
		return this.psHashData;
	}
	
	public byte[] getBytes() {
		
		byte[] buffer = new byte[CryptoMgr.CM_DEFAULT_PSHASH_INFO_LENGTH];
		
		// UserNo
		buffer[0] = (byte)(this.userNo & 0x000000ff);
		buffer[1] = (byte)((this.userNo & 0x0000ff00) >> 8);
		buffer[2] = (byte)((this.userNo & 0x00ff0000) >> 16);
		buffer[3] = (byte)((this.userNo & 0xff000000) >> 24);

		// PsHashLength
		buffer[4] = (byte)(psHashData.length & 0x000000ff);
		buffer[5] = (byte)((psHashData.length & 0x0000ff00) >> 8);
		buffer[6] = (byte)((psHashData.length & 0x00ff0000) >> 16);
		buffer[7] = (byte)((psHashData.length & 0xff000000) >> 24);
		
		// PsHash
		System.arraycopy(psHashData, 0, buffer, 8, psHashData.length);
		
		return buffer;
		
	}
	
}
