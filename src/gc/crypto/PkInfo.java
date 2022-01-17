package gc.crypto;

public class PkInfo {
	
	//private int userNo;
	
	private byte[] privateKeyData;
	
	private byte[] publicKeyData;	
	
	public CryptoStatus init(CryptoMgr cryptoMgr, int userNo, byte[] ekekData, byte[] psHashData, byte[] password) {

		CryptoStatus status;
		byte[] publicKey = new byte[CryptoMgr.CM_DEFAULT_PK_KEY_LENGTH];
		byte[] privateKey = new byte[CryptoMgr.CM_DEFAULT_PK_KEY_LENGTH];
		
		byte[] kekData = new byte[ekekData.length];
		
		status = cryptoMgr.generatePK(publicKey, privateKey, CryptoMgr.CM_DEFAULT_PK_KEY_BIT_LENGTH);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}
		
		//
		status = cryptoMgr.decryptKek(ekekData, psHashData, password, kekData);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}
		
		// UserNo
		//this.userNo = userNo;
		
		// PublicKeyLength
		int length = 8 + publicKey.length;
		
		// PublicKeyData
		publicKeyData = new byte[length];
		
		publicKeyData[0] = (byte)(userNo & 0x000000ff);
		publicKeyData[1] = (byte)((userNo & 0x0000ff00) >> 8);
		publicKeyData[2] = (byte)((userNo & 0x00ff0000) >> 16);
		publicKeyData[3] = (byte)((userNo & 0xff000000) >> 24);
		
		publicKeyData[4] = (byte)(publicKey.length & 0x000000ff);
		publicKeyData[5] = (byte)((publicKey.length & 0x0000ff00) >> 8);
		publicKeyData[6] = (byte)((publicKey.length & 0x00ff0000) >> 16);
		publicKeyData[7] = (byte)((publicKey.length & 0xff000000) >> 24);
		
		System.arraycopy(publicKey, 0,  publicKeyData, 8, publicKey.length);
		
		// PrivateKeyLength
		length = 8 + privateKey.length;
		
		// PrivateKeyData
		privateKeyData = new byte[length + privateKey.length];
		
		privateKeyData[0] = (byte)(userNo & 0x000000ff);
		privateKeyData[1] = (byte)((userNo & 0x0000ff00) >> 8);
		privateKeyData[2] = (byte)((userNo & 0x00ff0000) >> 16);
		privateKeyData[3] = (byte)((userNo & 0xff000000) >> 24);
		
		privateKeyData[4] = (byte)(privateKey.length & 0x000000ff);
		privateKeyData[5] = (byte)((privateKey.length & 0x0000ff00) >> 8);
		privateKeyData[6] = (byte)((privateKey.length & 0x00ff0000) >> 16);
		privateKeyData[7] = (byte)((privateKey.length & 0xff000000) >> 24);
		
		System.arraycopy(publicKey, 0,  publicKeyData, 8, privateKey.length);
		
		status = cryptoMgr.encryptData(kekData, kekData.length, privateKeyData, privateKeyData);
		if (status != CryptoStatus.CS_SUCCESS) {
			return status;
		}
		
		return CryptoStatus.CS_SUCCESS;
		
	}
	
	public byte[] getPublicKeyData() {
		return publicKeyData;
	}
	
	public byte[] getPrivateKeyData() {
		return privateKeyData;
	}
	
	/*
	public byte[] getBytes() {
		
		byte[] buffer = new byte[CryptoMgr.CM_DEFAULT_SALT_INFO_LENGTH];
		
		// UserNo
		buffer[0] = (byte)(userNo & 0x000000ff);
		buffer[1] = (byte)((userNo & 0x0000ff00) >> 8);
		buffer[2] = (byte)((userNo & 0x00ff0000) >> 16);
		buffer[3] = (byte)((userNo & 0xff000000) >> 24);
		
		// publicKeyLength
		buffer[4] = (byte)(publicKeyLength & 0x000000ff);
		buffer[5] = (byte)((publicKeyLength & 0x0000ff00) >> 8);
		buffer[6] = (byte)((publicKeyLength & 0x00ff0000) >> 16);
		buffer[7] = (byte)((publicKeyLength & 0xff000000) >> 24);
		
		// publicKeyData
		for (int i=0; i < publicKeyLength; i++) {
			buffer[8 + i] = publicKeyData[i];
		}
		
		return buffer;
		
	}
	*/
}
