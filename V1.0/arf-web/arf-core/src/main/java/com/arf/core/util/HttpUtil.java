package com.arf.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 这个Https协议工具类，采用HttpsURLConnection实现。 提供get和post两种请求静态方法
 * 
 * @author huangl
 * @version 1.0
 */
public class HttpUtil {
	
	
	private static TrustManager myX509TrustManager = new X509TrustManager() {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	};

	public static String sendHttpsPOST(String url, String data) {
		String result = null;

		try {
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

			// 打开连接
			// 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
			URL requestUrl = new URL(url);
			HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl.openConnection();

			// 设置套接工厂
			httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

			// 加入数据
			httpsConn.setRequestMethod("POST");
			httpsConn.setDoOutput(true);
			OutputStream out = httpsConn.getOutputStream();

			if (data != null)
				out.write(data.getBytes("UTF-8"));
			out.flush();
			out.close();

			// 获取输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()));
			int code = httpsConn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				String temp = in.readLine();
				/* 连接成一个字符串 */
				while (temp != null) {
					if (result != null)
						result += temp;
					else
						result = temp;
					temp = in.readLine();
				}
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String sendHttpsGET(String url) {
		String result = null;
		try {
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

			// 打开连接
			// 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
			URL requestUrl = new URL(url);
			HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl.openConnection();

			// 设置套接工厂
			httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

			// 加入数据
			httpsConn.setRequestMethod("GET");
			// httpsConn.setDoOutput(true);

			// 获取输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(),"UTF-8"));
			int code = httpsConn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				String temp = in.readLine();
				/* 连接成一个字符串 */
				while (temp != null) {
					if (result != null)
						result += temp;
					else
						result = temp;
					temp = in.readLine();
				}
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public static String sendHttpGET(String url) {
		String result = null;

		try {
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

			// 打开连接
			// 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
			URL requestUrl = new URL(url);
			HttpURLConnection httpConn=(HttpURLConnection)requestUrl.openConnection();
			// 加入数据
			httpConn.setRequestMethod("GET");
			// httpsConn.setDoOutput(true);
			// 获取输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
			int code = httpConn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				String temp = in.readLine();
				/* 连接成一个字符串 */
				while (temp != null) {
					if (result != null)
						result += temp;
					else
						result = temp;
					temp = in.readLine();
				}
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	 /** 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendHttpPost(String url, String params) {
		BufferedReader in = null;
		String result = "";
		try {
			URL urlStr = new URL(url);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) urlStr.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			int length = connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
				System.out.println(result);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	 /** 
	  * 该方法不会对异常进行处理
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws IOException 
	 * 
	 */
	public static String sendHttpPostThrows(String url, String params) throws IOException{
		BufferedReader in = null;
		String result = "";
		URL urlStr = new URL(url);// 创建连接
		HttpURLConnection connection = (HttpURLConnection) urlStr.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
		connection.setRequestProperty("U-Authorization", "68EE908C6365B641DC449B909D405870");
		connection.connect();
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
		out.append(params);
		out.flush();
		out.close();
		// 读取响应
		int length = connection.getContentLength();// 获取长度
		InputStream is = connection.getInputStream();
		if (length != -1) {
			byte[] data = new byte[length];
			byte[] temp = new byte[512];
			int readLen = 0;
			int destPos = 0;
			while ((readLen = is.read(temp)) > 0) {
				System.arraycopy(temp, 0, data, destPos, readLen);
				destPos += readLen;
			}
			result = new String(data, "UTF-8"); // utf-8编码
			System.out.println(result);
			return result;
		}
		return result;
	}
	
	
	public static String doHttpPost(String url,Map<String, String> params) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if(params!=null){
			for (Map.Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
		httpPost.setHeader("U-Authorization", "68EE908C6365B641DC449B909D405870");
		
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(200000).setConnectTimeout(200000).build();//设置请求和传输超时时间
		
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try{

			response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			
            if (httpEntity != null)
            {
            	return EntityUtils.toString(httpEntity, "UTF-8");
            }
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					throw new Exception(e);
				}
			}
			if(httpClient!=null){
				try {
					httpClient.close();
				} catch (IOException e) {
					throw new Exception(e);
				}
			}
		}
		return "";
	}
	
	public static byte[] readInputStream(InputStream inStream) throws IOException{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}
	public static byte[] httpsPost(String url, String data) {
		 HttpsURLConnection httpsConn = null;
		 OutputStream out = null;
		try {
			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

			// 打开连接
			// 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
			URL requestUrl = new URL(url);
			httpsConn = (HttpsURLConnection) requestUrl.openConnection();

			// 设置套接工厂
			httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());

			// 加入数据
			httpsConn.setRequestMethod("POST");
			httpsConn.setDoOutput(true);
			out = httpsConn.getOutputStream();

			if (data != null)
				out.write(data.getBytes("UTF-8"));
			out.flush();
			

			int code = httpsConn.getResponseCode();
			if (HttpsURLConnection.HTTP_OK == code) {
				// 获取输入流
				return readInputStream(httpsConn.getInputStream());
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try{
				httpsConn.disconnect();
			}catch(Exception e){
				
			}
			try{
				out.close();
			}catch(Exception e){
				
			}
		}

		return null;
	}
	public static String queryString(Map<String,Object> params){
		if(params == null){
			return null;
		}
		if(params.size() == 0){
			return "";
		}
		String tmp = "";
		int index = params.size();
		for(String key : params.keySet()){
			index--;
			Object val = params.get(key);
			if(val == null){
				continue;
			}
			if(index > 0){
				tmp += (key + "=" + val + "&");
			}else{
				tmp += (key + "=" + val);
			}
		}
		return tmp;
	}
}
