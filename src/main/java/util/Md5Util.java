package util;

import java.security.MessageDigest;

/**
 * use MD5 algorithm to convert password to MD5 code
 */
public final class Md5Util {
	private Md5Util(){}
	/**
	 * convert password to MD5 password
	 */
	public static String encodeByMd5(String password) throws Exception{
		// MD5 and SHA algorithm can be found in MessageDigest Class , we use MD5 here
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] byteArray = md5.digest(password.getBytes());
        // MessageDigest can only convert String to byte[] , we need to do the rest work
		return byteArrayToHexString(byteArray);
	}
	/**
	 * Convert byte array to hexdecimal string
	 */
	private static String byteArrayToHexString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		for(byte b : byteArray){
			String hex = byteToHexString(b);
			sb.append(hex);
		}
		return sb.toString();
	}
	/**
	 * convert byte to hexdecimal
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if(n < 0){
		    // for negtive number, we can convert it to positive by shift
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hex[d1] + hex[d2];
	}
	private static String[] hex = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	/**
	 * test
	 */
	public static void main(String[] args) throws Exception{
		String password = "123456";
		String passwordMD5 = Md5Util.encodeByMd5(password);
		System.out.println(password);
		System.out.println(passwordMD5);
	}
}
