package com.arf.core.util;

import java.util.TreeMap;

import com.arf.core.util.HttpUtil;
import com.arf.core.util.MD5Util;

public class SignUtil {
	/**
	 * 按照params参数中的key字典排序签名
	 * @param params 参数
	 * @param params 荣事达api key
	 * @return
	 */
	public static String sign(TreeMap<String,Object> params,String apiKey){
		String tmp = HttpUtil.queryString(params);
		tmp += "&key=" + apiKey;
		String signStr = MD5Util.getMD5(tmp);
		return signStr;
	}
}
