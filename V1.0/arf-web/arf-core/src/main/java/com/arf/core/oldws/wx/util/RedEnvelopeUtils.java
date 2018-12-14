package com.arf.core.oldws.wx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.arf.core.oldws.wx.entity.RedEnvelopeResponse;
import com.arf.core.oldws.wx.entity.WxConstants;

public class RedEnvelopeUtils {
	public static final int FAIL = 0; // 领取失败
	public static final int SUCCESS = 1; // 领取成功
	public static final int LOCK = 2; // 已在余额表中锁定该用户的余额,防止领取的红包金额大于预算

	/**
	 * 对请求参数名ASCII码从小到大排序后签名
	 * 
	 * @param params
	 */
	public static void sign(SortedMap<String, String> params) {
		Set<Entry<String, String>> entrys = params.entrySet();
		Iterator<Entry<String, String>> it = entrys.iterator();
		StringBuffer result = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			result.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
		}
		result.append("key=").append(WxConstants.API_KEY);
		params.put("sign", DigestUtils.md5Hex(result.toString()));
	}

	/**
	 * 生成提交给微信服务器的xml格式参数
	 * 
	 * @param params
	 * @return
	 */
	public static String getRequestXml(SortedMap<String, String> params) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = params.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("nick_name".equalsIgnoreCase(k)
					|| "send_name".equalsIgnoreCase(k)
					|| "wishing".equalsIgnoreCase(k)
					|| "act_name".equalsIgnoreCase(k)
					|| "remark".equalsIgnoreCase(k)
					|| "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 创建红包所需map
	 * 
	 */
	public static SortedMap<String, String> createMap(String openid, int amount,String billno,String wishing,String actName) {
			
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("wxappid", WxConstants.WXAPPID);
		params.put("nonce_str", createNonceStr());
		params.put("mch_billno", billno);
		params.put("mch_id", WxConstants.MCH_ID);
		params.put("nick_name", WxConstants.NICK_NAME);
		params.put("send_name", WxConstants.SEND_NAME);
		params.put("re_openid", openid);
		params.put("total_amount", amount + "");
		params.put("min_value", amount + "");
		params.put("max_value", amount + "");
		params.put("total_num", WxConstants.TOTAL_NUM);
		params.put("wishing", wishing);
		params.put("client_ip", WxConstants.CLIENT_IP);
		params.put("act_name", actName);
		params.put("remark", WxConstants.REMARK);
		return params;
	}
	
	
	/**
	 * 查询发送红包记录所需map
	 * 
	 */
	public static SortedMap<String, String> createMap(String mch_billno,String mch_id) {
			
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appid", WxConstants.WXAPPID);//嵘联
		//params.put("appid", Constants.WXAPPID_ANJIANETWORK);//俺家网络科技
		params.put("nonce_str", createNonceStr());
		params.put("mch_billno", mch_billno);
		params.put("mch_id", mch_id);
		params.put("bill_type", "MCHT");
		return params;
	}


	/**
	 * 查询发送红包记录所需map
	 * 
	 */
	public static SortedMap<String, String> createMap(RedEnvelopeResponse r) {
			
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put("appid", WxConstants.WXAPPID);
		params.put("nonce_str", createNonceStr());
		params.put("mch_billno", r.getMch_billno());
		params.put("mch_id", r.getMch_id());
		params.put("bill_type", "MCHT");
		return params;
	}

	/**
	 * 生成随机字符串
	 * 
	 * @return
	 */
	public static String createNonceStr() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}

	/**
	 * 生成商户订单号
	 * 
	 * @param mch_id
	 *            商户号
	 * @param userId
	 *            该用户的userID
	 * @return
	 */
	public static String createBillNo() {
		// 组成： mch_id+yyyymmdd+10位一天内不能重复的数字
		// 10位一天内不能重复的数字实现方法如下:
		// 因为每个用户绑定了userId,他们的userId不同,加上随机生成的(10-length(userId))可保证这10位数字不一样
		/*try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}*/
		Date dt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		String nowTime = df.format(dt);
		return WxConstants.MCH_ID + nowTime + getRandomNum();
	}
	
	

	/**
	 * 生成特定位数的随机数字
	 * 
	 * @param length
	 * @return
	 */
	private static String getRandomNum() {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}

	/**
	 * post提交到微信服务器
	 *
	 * @param requestXML
	 * @param instream
	 *            传入的在微信支付的PKCS12证书的位置
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 */
	public static String post(String requestXML, InputStream instream,String url)throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		try {
			//keyStore.load(instream, Constants.MCH_ID.toCharArray());//嵘联
			keyStore.load(instream, WxConstants.MCH_ID.toCharArray());//俺家网络科技
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom()
		//.loadKeyMaterial(keyStore, Constants.MCH_ID.toCharArray()).build();//嵘联
		.loadKeyMaterial(keyStore, WxConstants.MCH_ID.toCharArray()).build();//俺家网络科技
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity reqEntity = new StringEntity(requestXML, "utf-8"); // 如果此处编码不对，可能导致客户端签名跟微信的签名不一致
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(	new InputStreamReader(entity.getContent(), "UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						result += text;
					}
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		System.out.println("--->result: "+result);
		return result;
	}
}
