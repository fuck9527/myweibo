package com.renren.api.client.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class Md5Utils {

	public static String md5(String string) {
		if (string == null || string.trim().length() < 1) {
			return null;
		}
		try {
			return getMD5(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String md52(String string) {
		if (string == null || string.trim().length() < 1) {
			return null;
		}
		try {
			return getMD52(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); 
			
			char str[] = new char[16 * 2]; 
			
			int k = 0; 
			for (int i = 0; i < 16; i++) { 
				
				byte byte0 = tmp[i]; 
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
				
				str[k++] = hexDigits[byte0 & 0xf]; 
			}
			s = new String(str); 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	private static String getMD52(byte[] source) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			StringBuffer result = new StringBuffer();
			for (byte b : md5.digest(source)) {
				result.append(Integer.toHexString((b & 0xf0) >>> 4));
				result.append(Integer.toHexString(b & 0x0f));
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		System.out
				.println(md5("api_key=1a90a2bf034049f39d5c41d040b0ff54call_id=1253782990268format=XMLid=2method=share.publishsession_key=2.8531c1a354d387b07a4984ae50fabd4c.3600.1253790000-261912373share_date={\"link\":\"http://mininurse.renren.com\",\"pic\":\"\",\"title\":\"小护士\",\"sumary\":\"\",\"comment\":\"gool\"}type=6uid=261912373v=1.094601c5cddab4da0b7bf81f68d50c2d7"));
		System.out
				.println(md52("api_key=1a90a2bf034049f39d5c41d040b0ff54call_id=1253782990268format=XMLid=2method=share.publishsession_key=2.8531c1a354d387b07a4984ae50fabd4c.3600.1253790000-261912373share_date={\"link\":\"http://mininurse.renren.com\",\"pic\":\"\",\"title\":\"小护士\",\"sumary\":\"\",\"comment\":\"gool\"}type=6uid=261912373v=1.094601c5cddab4da0b7bf81f68d50c2d7"));
	}
}
