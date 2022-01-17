package gc.crypto;

public class SaltInfo {
	
	private int userNo;
	private byte[] saltData;	
	
	public SaltInfo() {};
	
	public SaltInfo(byte[] salt) {
		
		// userNo
		this.userNo = ((((int)(byte)salt[3]) & 0xFF) << 24) | ((((int)(byte)salt[2]) & 0xFF) << 16) | ((((int)(byte)salt[1]) & 0xFF) << 8) | (((int)(byte)salt[0]) & 0xFF);
				
		// saltData
		int length = ((((int)(byte)salt[7]) & 0xFF) << 24) | ((((int)(byte)salt[6]) & 0xFF) << 16) | ((((int)(byte)salt[5]) & 0xFF) << 8) | (((int)(byte)salt[4]) & 0xFF);
		
		this.saltData = new byte[length];
		System.arraycopy(salt, 8, this.saltData, 0, length);
		
	}
	
	public CryptoStatus init(CryptoMgr cryptoMgr, int userNo) {
		
		// userNo
		this.userNo = userNo;
		
		// saltData
		this.saltData = new byte[CryptoMgr.CM_DEFAULT_SK_BLOCK_LENGTH];
		return cryptoMgr.generateRandom(this.saltData);
		
	}
	
	public byte[] getSaltData() {
		
		return saltData;
	}
	
	public byte[] getBytes() {
		
		byte[] buffer = new byte[CryptoMgr.CM_DEFAULT_SALT_INFO_LENGTH];
		
		// UserNo
		buffer[0] = (byte)(userNo & 0x000000ff);
		buffer[1] = (byte)((userNo & 0x0000ff00) >> 8);
		buffer[2] = (byte)((userNo & 0x00ff0000) >> 16);
		buffer[3] = (byte)((userNo & 0xff000000) >> 24);
		
		// SaltDataLength
		buffer[4] = (byte)(saltData.length & 0x000000ff);
		buffer[5] = (byte)((saltData.length & 0x0000ff00) >> 8);
		buffer[6] = (byte)((saltData.length & 0x00ff0000) >> 16);
		buffer[7] = (byte)((saltData.length & 0xff000000) >> 24);
		
		// SaltData
		System.arraycopy(saltData, 0, buffer, 8, saltData.length);
		
		return buffer;
		
	}
	
}
