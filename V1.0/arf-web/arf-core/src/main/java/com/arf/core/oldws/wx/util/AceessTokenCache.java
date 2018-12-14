package com.arf.core.oldws.wx.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import com.arf.core.oldws.wx.entity.WxConstants;

public class AceessTokenCache {


	public synchronized static String getAccessToken() {
		String accessToken = "";
		long deathTime = 0;
		// Http请求参数
		Map<String, String> params;
		List<NameValuePair> list;
		// 1 判断是否过期
		
		if (0 == deathTime || System.currentTimeMillis() >= deathTime) {
			// 重新获取
			params = new HashMap<String, String>();
			params.put("grant_type", "client_credential");
			params.put("appid", WxConstants.WXAPPID);
			params.put("secret", WxConstants.APPSECRET);
			list = MyUtils.getInstance().getReqParams(params);
			String result = MyUtils.getInstance().doPost(WxConstants.URL_ACCESS_TOKEN, list);
			accessToken = GsonTools.getAccessToken(result);
			System.out.println("access_token: "+accessToken+"  当前时间: "+System.currentTimeMillis());
			deathTime = System.currentTimeMillis() + 7200*1000;
			System.out.println("--->Death Time: "+deathTime);
		}

		// 2 返回
		return accessToken;
	}
}
