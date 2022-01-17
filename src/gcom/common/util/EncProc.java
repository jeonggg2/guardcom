/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package gcom.common.util;

/**
 * Created by user on 2016-08-03.
 * by k2jeans
 */
public class EncProc{
	
    private static final int CONVERT_BYTE_LEN = 32;

    public static byte[] hexToByteArray(String hex){
        if ((hex == null) || (hex.length() == 0)) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = ((byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16));
        }
        return ba;
    }

    public static String byteArrayToHex(byte[] ba){
        if ((ba == null) || (ba.length == 0)) {
            return null;
        }
        StringBuffer sb = new StringBuffer(ba.length * 2);
        for (int x = 0; x < ba.length; x++)
        {
            String hexNumber = "0" + Integer.toHexString(0xFF & ba[x]);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }

    public static String encrypt(String message) throws Exception {
        return message;
    }


    public static String decrypt(String encrypted) throws Exception {
        return "";
    }
    
}

