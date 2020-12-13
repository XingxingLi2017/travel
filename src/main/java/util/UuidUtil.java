package util;

import java.util.UUID;

/**
 * UUID String generating tool
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
	/**
	 * test
	 */
	public static void main(String[] args) {
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
		System.out.println(UuidUtil.getUuid());
	}
}
