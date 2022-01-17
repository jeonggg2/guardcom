package gc.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

import gc.crypto.aria.ARIAEngine;

public class CryptoMgr {
	
	public static final int CM_DEFAULT_PK_KEY_LENGTH = 256; // 2048bit(256)
	public static final int CM_DEFAULT_PK_KEY_BIT_LENGTH = CM_DEFAULT_PK_KEY_LENGTH * 8;
	public static final int CM_DEFAULT_SK_KEY_LENGTH = 16; // 128bit(16)
	public static final int CM_DEFAULT_SK_BLOCK_LENGTH = 16;
	public static final int CM_DEFAULT_HASH_LENGTH = 64;  // SHA512(64)
	public static final int CM_DEFAULT_SALT_INFO_LENGTH = 8 + CM_DEFAULT_SK_BLOCK_LENGTH; // == UserNo(4) + Length(4) + Data(16)
	public static final int CM_DEFAULT_PSHASH_INFO_LENGTH = 8 + CM_DEFAULT_HASH_LENGTH; // == UserNo(4) + Length(4) + Data(64)
	public static final int CM_DEFAULT_EKEK_INFO_LENGTH = 12 + CM_DEFAULT_SK_KEY_LENGTH; // == KeyId(8) + Length(4) + Data(16)	
	public static final int CM_DEFAULT_CEK_INFO_LENGTH = 12 + CM_DEFAULT_SK_KEY_LENGTH; // == KeyId(8) + Length(4) + Data(16)	
	
	public CryptoMgr() {
	
	}
	
	public CryptoStatus init() {		
		return CryptoStatus.CS_SUCCESS;		
	}
	
	public void terminate() {
	}
	
	public CryptoStatus generatePK(byte[] publicKey, byte[] privateKey, int s_key) {
		
		PublicKey publicKey1 = null;
		PrivateKey privateKey1 = null;
		  
		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator;
		  
		try {
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(s_key, secureRandom);
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			publicKey1 = keyPair.getPublic();
			privateKey1 = keyPair.getPrivate();
			  
			KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec rsaPublicKeySpec = keyFactory1.getKeySpec(publicKey1, RSAPublicKeySpec.class);
			RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory1.getKeySpec(privateKey1, RSAPrivateKeySpec.class);
			    
			System.out.println("Public  key modulus : "  + rsaPublicKeySpec.getModulus());
			System.out.println("Public  key exponent: "  + rsaPublicKeySpec.getPublicExponent());
			System.out.println("Private key modulus : "  + rsaPrivateKeySpec.getModulus());
			System.out.println("Private key exponent: "  + rsaPrivateKeySpec.getPrivateExponent());
			
			System.arraycopy(publicKey1.getEncoded(), 0, publicKey, 0, publicKey.length);
			
			System.arraycopy(privateKey1.getEncoded(), 0, privateKey, 0, privateKey.length);
			
			return CryptoStatus.CS_SUCCESS;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
      

		return CryptoStatus.CS_UNSUCCESSFUL;
	}
	
	public CryptoStatus generateSK(byte[] keyData) {
		return generateRandom(keyData);
	}
	
	public CryptoStatus generateRandom(byte[] buffer) {
		SecureRandom random = new SecureRandom();
		random.nextBytes(buffer);
		return CryptoStatus.CS_SUCCESS;
	}
	
	public CryptoStatus hashData(byte[] data, int mode, byte[] hashData) {
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");

			byte[] buffer = md.digest(data);
			
			if (buffer.length > 0) {
				System.arraycopy(buffer, 0, hashData, 0, hashData.length);
				return CryptoStatus.CS_SUCCESS;
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return CryptoStatus.CS_UNSUCCESSFUL;
		
	}
	
	public CryptoStatus hashPassword(byte[] password, byte[] optionalData, byte[] hashData) {
		
		
		byte[] buffer = new byte[password.length + optionalData.length];
		System.arraycopy(password, 0, buffer, 0, password.length);
		System.arraycopy(optionalData, 0, buffer, password.length, optionalData.length);
		
		return hashData(buffer, CM_DEFAULT_HASH_LENGTH, hashData);
		
	}
	
	public CryptoStatus encryptData(byte[] keyData, int keySize, byte[] plainText, byte[] cipherText) {
		
		ARIAEngine instance;
		try {
			instance = new ARIAEngine(keySize * 8);
		    instance.setKey(keyData);
		    instance.setupRoundKeys();
		    instance.encrypt(plainText, 0, cipherText, 0);
			return CryptoStatus.CS_SUCCESS;
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		return CryptoStatus.CS_ENCRYPTION_FAILED;
	}
	
	public CryptoStatus decryptData(byte[] keyData, int keySize, byte[] cipherText, byte[] plainText) {
		ARIAEngine instance;
		try {
			instance = new ARIAEngine(keySize * 8);
		    instance.setKey(keyData);
		    instance.setupRoundKeys();
		    instance.decrypt(cipherText, 0, plainText, 0);
			return CryptoStatus.CS_SUCCESS;
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		return CryptoStatus.CS_ENCRYPTION_FAILED;
	}
	
	public CryptoStatus encryptKek(byte[] kekData, byte[] psHashData, byte[] password, byte[] ekekData) {
		
		byte[] hashData = new byte[CM_DEFAULT_HASH_LENGTH];		
		
		CryptoStatus status = hashPassword(password, psHashData, hashData);
		if (status != CryptoStatus.CS_SUCCESS) {
			
			return status;
		}
		
		return encryptData(hashData, CM_DEFAULT_SK_KEY_LENGTH, kekData, ekekData);
	}
	
	public CryptoStatus decryptKek(byte[] ekekData, byte[] psHashData, byte[] password, byte[] kekData) {
		
		byte[] hashData = new byte[CM_DEFAULT_HASH_LENGTH];		
		
		CryptoStatus status = hashPassword(password, psHashData, hashData);
		if (status != CryptoStatus.CS_SUCCESS) {
			
			return status;
		}
		
		return decryptData(hashData, CM_DEFAULT_SK_KEY_LENGTH, ekekData, kekData);
	}
	
	public CryptoStatus authenticatePassword(byte[] password, byte[] salt, byte[] psHash) {
		
		byte[] hashData = new byte[CM_DEFAULT_HASH_LENGTH];		
		byte[] psHashData = (new PsHashInfo(psHash)).getPsHashData();
		
		CryptoStatus status = hashPassword(password, (new SaltInfo(salt)).getSaltData(), hashData);
		if (status != CryptoStatus.CS_SUCCESS) {
			
			return status;
		}
		
		if (psHashData.length != CM_DEFAULT_HASH_LENGTH) {
			return CryptoStatus.CS_UNAUTHORIZED;
		}
		
		if (Arrays.equals(psHashData, hashData)) {
			return CryptoStatus.CS_SUCCESS;
		}
		
		return CryptoStatus.CS_UNAUTHORIZED;
		
	}
	
}
