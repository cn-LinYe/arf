package com.arf.traffic;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;  

public class ThreeDes {

	private final static String CharsetName="UTF-8";//设置字符转换的编码格式
	private final static String AlgorithmName="DESede";//设置加密方式
	private final static String PaddingName="/CBC/PKCS5Padding";//设置加密填充方式
	//private final byte[] key="F7A0B971B199FD2A1017CEC5".getBytes("UTF-8");
	
	public static void main(String[] args) throws Exception {  
         
        byte[] key = "F7A0B971B199FD2A1017CEC5".getBytes(CharsetName);
        byte[] keyiv = "20150120".getBytes(CharsetName);  
          String str="{\"poateNo\":\"A12345\"}";
        System.out.println("原始字符串为： " + str);  
          
        byte[] data = str.getBytes(CharsetName);  
          
        System.out.println("开始CBC加密解密");  
          
        byte[] strEncode = des3EncodeCBC(key, keyiv, data); 
        System.out.println("以BASE64编码输出加密后的byte为：" + Base64.encodeBase64String(strEncode));    
        
          
        byte[] strDecode = des3DecodeCBC(key, keyiv, strEncode);  
        String strGet = new String(strDecode, "UTF-8");  
        System.out.println("解密后的字符串内容为：" + strGet);  
         
          
          
    }  
      
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception  
    {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(AlgorithmName);  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance(AlgorithmName.concat(PaddingName));  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
      
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception  
    {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(AlgorithmName);  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance(AlgorithmName.concat(PaddingName));  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
}  