package com.arf.payment;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.arf.core.oldws.wx.entity.WxConstants;
import com.arf.core.util.MD5Util;
import com.sun.istack.internal.Nullable;


public class WXPayUtils {
	private static Logger Log = Logger.getLogger(WXPayUtils.class);
	public static String TAG = WXPayUtils.class.getName();
	public static String DEFAULT_CHARSET = "UTF-8";
    
	public static final String APPID = WxConstants.WXAPPID;
	public static final String MCH_ID = WxConstants.MCH_ID;
	public static final String API_KEY = WxConstants.API_KEY;
	public static final String APPSECRET = WxConstants.APPSECRET;

    public enum TradeType{
    	NATIVE,
    	APP,
    	JSAPI,
    	;
    }
    
    /**
     * 统一下单方法
     * @param outTradeNo
     * @param body
     * @param notify_url
     * @param totalFee 单位:分
     * @return
     * @throws UnsupportedEncodingException
     */
	public static Map<String, Object> unifiedOrder(String outTradeNo,String body,String notify_url,int totalFee,TradeType trade_type,@Nullable String openId) throws UnsupportedEncodingException {
		String url = String
			.format("https://api.mch.weixin.qq.com/pay/unifiedorder");// 获取prePayId的url
		Random random = new Random();
		String nonceStr = MD5Util.getMD5(String.valueOf(random.nextInt(10000)));
	
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		/**
		 * 签名字段顺序必须为字典顺序
		 */
		packageParams.add(new BasicNameValuePair("appid", APPID));
//		packageParams.add(new BasicNameValuePair("body", new String(body.getBytes(),"ISO-8859-1")));
		packageParams.add(new BasicNameValuePair("body",body));
		packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		packageParams.add(new BasicNameValuePair("notify_url",notify_url));
		

		if(StringUtils.isNotBlank(openId)){
			packageParams.add(new BasicNameValuePair("openid", openId));
		}
		
		packageParams.add(new BasicNameValuePair("out_trade_no", outTradeNo));
		packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
		packageParams.add(new BasicNameValuePair("total_fee", totalFee+""));
		packageParams.add(new BasicNameValuePair("trade_type", trade_type.toString()));
		String sign = genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));
		String entity = toXml(packageParams);
		Log.info(entity);
	
		byte[] buf = httpPost(url, entity);
	
		/*
		 * 返回结果实例: result_code : SUCCESS 
		 * sign : 86433A0555AE5B81FADCCB1C4497E684
		 * mch_id : 1233848001 
		 * prepay_id : wx20150612170618f1ee5d64070318838469
		 * return_msg : OK 
		 * appid : wxf2f565574a968187 
		 * nonce_str : hIbayVVQBLtGsChE 
		 * return_code : SUCCESS trade_type : APP
		 */
		Map<String, String> prepayMap = decodeXml(new String(buf,DEFAULT_CHARSET));
		if ("SUCCESS".equals(String.valueOf(prepayMap.get("return_code")))
			&& "SUCCESS".equals(String.valueOf(prepayMap.get("result_code")))) {
		    String prepay_id = prepayMap.get("prepay_id");
//		    String packageValue = "Sign=WXPay";
//		    String noticeString = genNonceStr();
//		    String timeStamp = String.valueOf(genTimeStamp());
//		    List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//		    signParams.add(new BasicNameValuePair("appid", WXCORPID));
//		    signParams.add(new BasicNameValuePair("noncestr", noticeString));
//		    signParams.add(new BasicNameValuePair("package", packageValue));
//		    signParams.add(new BasicNameValuePair("partnerid", MCH_ID2));
//		    signParams.add(new BasicNameValuePair("prepayid", prepay_id));
//		    signParams.add(new BasicNameValuePair("timestamp", timeStamp));
//	
//		    String appSign = genAppSign(signParams);
	
		    Map<String, Object> statement = new HashMap<String, Object>();
		    statement.put("appid", APPID);
		    statement.put("nonceStr", prepayMap.get("nonce_str"));
//		    statement.put("packageValue", packageValue);
		    statement.put("partnerId", MCH_ID);
		    statement.put("prepayId", prepay_id);
		    statement.put("timestamp", new Date().getTime()/1000);
		    statement.put("sign", prepayMap.get("sign"));
		    statement.put("key", API_KEY);
		    if(StringUtils.isNotBlank(openId)){
		    	statement.put("openid", openId);
			}
		    statement.put("codeUrl", prepayMap.get("code_url"));
		    return statement;
		} else {
		    return null;
		}
    }

    private static HttpClient getNewHttpClient() {
		try {
		    KeyStore trustStore = KeyStore.getInstance(KeyStore
			    .getDefaultType());
		    trustStore.load(null, null);
	
		    SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
		    sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	
		    HttpParams params = new BasicHttpParams();
		    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		    HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	
		    SchemeRegistry registry = new SchemeRegistry();
		    registry.register(new Scheme("http", PlainSocketFactory
			    .getSocketFactory(), 80));
		    registry.register(new Scheme("https", sf, 443));
	
		    ClientConnectionManager ccm = new ThreadSafeClientConnManager(
			    params, registry);
	
		    return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
		    return new DefaultHttpClient();
		}
    }

    public static byte[] httpPost(String url, String entity) {
    	if (url == null || url.length() == 0) {
    		Log.info("httpPost, url is null");
	    return null;
    	}

		HttpClient httpClient = getNewHttpClient();
	
		HttpPost httpPost = new HttpPost(url);
	
		try {
		    httpPost.setEntity(new StringEntity(entity));
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");
		    HttpResponse resp = httpClient.execute(httpPost);
		    if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			Log.info("httpGet fail, status code = "
				+ resp.getStatusLine().getStatusCode());
			return null;
		    }
	
		    return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
		    Log.info("httpPost exception, e = " + e.getMessage());
		    e.printStackTrace();
		    return null;
		}
    }

    public static Map<String, String> decodeXml(String content) {

		try {
		    Map<String, String> xml = new HashMap<String, String>();
		    XmlPullParser parser = XmlPullParserFactory.newInstance()
			    .newPullParser();
		    parser.setInput(new StringReader(content));
		    int event = parser.getEventType();
		    while (event != XmlPullParser.END_DOCUMENT) {
	
			String nodeName = parser.getName();
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
	
			    break;
			case XmlPullParser.START_TAG:
	
			    if ("xml".equals(nodeName) == false) {
				// 实例化student对象
				xml.put(nodeName, parser.nextText());
			    }
			    break;
			case XmlPullParser.END_TAG:
			    break;
			}
			event = parser.next();
		    }
	
		    return xml;
		} catch (Exception e) {
		    Log.info(e.toString());
		}
		return null;

    }

    private static String genNonceStr() {
    	Random random = new Random();
		return MD5Util.getMD5(String.valueOf(random.nextInt(10000)));
    }

    private static long genTimeStamp() {
    	return System.currentTimeMillis() / 1000;
    }

    /**
     * 生成签名
     */

    public static String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
	
		for (int i = 0; i < params.size(); i++) {
		    sb.append(params.get(i).getName());
		    sb.append('=');
		    sb.append(params.get(i).getValue());
		    sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);
	
		String packageSign = "";
		try {
		    packageSign = MD5Util.getMD5(sb.toString()).toUpperCase();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		Log.info(packageSign);
		return packageSign;
	    }
	
	private static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
	
		for (int i = 0; i < params.size(); i++) {
		    sb.append(params.get(i).getName());
		    sb.append('=');
		    sb.append(params.get(i).getValue());
		    sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);
	
		String appSign = "";
		appSign = MD5Util.getMD5(sb.toString()).toUpperCase();
		Log.info(appSign);
		return appSign;
	    }
	
	    private static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><xml>");
		for (int i = 0; i < params.size(); i++) {
		    sb.append("<" + params.get(i).getName() + ">");
	
		    sb.append(params.get(i).getValue());
		    sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
	
		Log.info(sb.toString());
		return sb.toString();
    }

    private static class SSLSocketFactoryEx extends SSLSocketFactory {

    	SSLContext sslContext = SSLContext.getInstance("TLS");

    	public SSLSocketFactoryEx(KeyStore truststore)
    			throws NoSuchAlgorithmException, KeyManagementException,
    			KeyStoreException, UnrecoverableKeyException {
		    super(truststore);
	
		    TrustManager tm = new X509TrustManager() {
	
			@Override
			public X509Certificate[] getAcceptedIssuers() {
			    return null;
			}
	
			@Override
			public void checkClientTrusted(X509Certificate[] chain,
				String authType)
				throws java.security.cert.CertificateException {
			}
	
			@Override
			public void checkServerTrusted(X509Certificate[] chain,
				String authType)
				throws java.security.cert.CertificateException {
			}
	    };

	    sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
		boolean autoClose) throws IOException, UnknownHostException {
	    return sslContext.getSocketFactory().createSocket(socket, host,
		    port, autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
	    return sslContext.getSocketFactory().createSocket();
		}
    }
}