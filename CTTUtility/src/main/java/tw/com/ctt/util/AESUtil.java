package tw.com.ctt.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil
{

	/**
	 * AES加密
	 * @param content 要加密的内容
	 * @param key 密钥
	 * @param iv iv
	 * @return
	 */
	public static String encrypt_CBC(String content, String key, String iv)
	{
		if(key == null || key.length() != 16)
		{
			System.err.println("AES key 的长度必须是16位！");
			return null;
		}
		if(iv == null || iv.length() != 16)
		{
			System.err.println("AES iv 的长度必须是16位！");
			return null;
		}
		try
		{
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = content.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0)
			{
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return new BASE64Encoder().encode(encrypted);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encrypt_ECB(String content, String key)
	{
		if(key == null || key.length() != 16)
		{
			System.err.println("AES key 的长度必须是16位！");
			return null;
		}
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = content.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0)
			{
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return new BASE64Encoder().encode(encrypted);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES加密，key和iv一样
	 * @param content 要加密的内容
	 * @param key 密钥
	 * @return
	 */
	public static String encrypt_CBC(String content, String key)
	{
		return encrypt_CBC(content, key, key);
	}

	/**
	 * AES解密
	 * @param content 要解密的内容
	 * @param key 密钥
	 * @param iv iv
	 * @return
	 */
	public static String decrypt_CBC(String content, String key, String iv)
	{
		try
		{
			byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted);
			return new String(original);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt_ECB(String content, String key)
	{
		try
		{
			byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
//			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
//			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted);
			return new String(original);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * AES解密
	 * @param content 要解密的内容
	 * @param key 密钥
	 * @param iv iv
	 * @return
	 */
	public static String decrypt_CBC(String content, String key)
	{
		return decrypt_CBC(content, key, key);
	}

	public static void main(String[] args)
	{
		String content = "a1AA**4b12a1AA**4b12";
		String key = "aaaabbbbccccdddd";
		String encrypted = encrypt_CBC(content, key);
//		System.out.println("CBC加密之前:" + content);
//		System.out.println("CBC加密之后:" + encrypted);
//		System.out.println("CBC解密之后:" + decrypt_CBC(encrypted, key));
		
		encrypted = encrypt_ECB(content, key);
//		System.out.println("ECB加密之前:" + content);
//		System.out.println("ECB加密之后:" + encrypted);
//		System.out.println("ECB解密之后:" + decrypt_ECB(encrypted, key));
	}
}