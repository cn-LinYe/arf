/**
 * @(#)ServiceValidte.java
 * 
 * Copyright arf.
 *
 * @Version: 1.0
 * @JDK: jdk jdk1.6.0_10
 * @Module: arf-core
 */ 
 /*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2016-1-8       arf      Created
 **********************************************
 */

package com.arf.core.oldws;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 旧接口工具方法
 * author:arf
 * @author arf
 * @version 4.0
 */
public class ServiceValidte {

	
	/** 微信回调根元素*/
	private static Element rootElement;
	
	/**
	 * 获得微信回调解析后的内容集合
	 * @param wxXmlRsp
	 * 				结果内容
	 * @return	解析后的内容集合
	 * @throws DocumentException
	 */
	public static Map<String,Object> parseWxXmlRsp(String wxXmlRsp) throws DocumentException{
		//判定请求内容是否为空
		if(StringUtils.isBlank(wxXmlRsp)){
			return null;
		}
		//获得微信回调根元素
		Document document = DocumentHelper.parseText(wxXmlRsp);
		rootElement = document.getRootElement();
		//将解析的内容放入map中
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("appid",getTextByElementName("appid"));
		map.put("mch_id",getTextByElementName("mch_id"));
		map.put("device_info",getTextByElementName("device_info"));
		map.put("nonce_str",getTextByElementName("nonce_str"));
		map.put("sign",getTextByElementName("sign"));

		String return_code = getTextByElementName("return_code");
		if(StringUtils.isBlank(return_code) || !"SUCCESS".equals(return_code)){
			map.put("result_code",null == rootElement.element("return_msg")?null:rootElement.element("return_msg").getText());
		} else{
			map.put("result_code",getTextByElementName("result_code"));
		}
		map.put("err_code",getTextByElementName("err_code"));
		map.put("err_code_des",getTextByElementName("err_code_des"));
		map.put("openid",getTextByElementName("openid"));
		map.put("is_subscribe",getTextByElementName("is_subscribe"));

		map.put("trade_type",getTextByElementName("trade_type"));
		map.put("bank_type",getTextByElementName("bank_type"));
		map.put("total_fee",null == getTextByElementName("total_fee")?null:Integer.valueOf(getTextByElementName("total_fee")));
		map.put("fee_type",getTextByElementName("fee_type"));
		map.put("cash_fee",null == getTextByElementName("cash_fee")?null:Integer.valueOf(getTextByElementName("cash_fee")));

		map.put("cash_fee_type",getTextByElementName("cash_fee_type"));
		map.put("coupon_fee",null == getTextByElementName("coupon_fee")?null:Integer.valueOf(getTextByElementName("coupon_fee")));
		map.put("coupon_count",null == getTextByElementName("coupon_count")?null:Integer.valueOf(getTextByElementName("coupon_count")));
		map.put("coupon_id_$n",getTextByElementName("coupon_id_$n"));
		map.put("coupon_fee_$n",null == getTextByElementName("coupon_fee_$n")?null:Integer.valueOf(getTextByElementName("coupon_fee_$n")));

		map.put("transaction_id",getTextByElementName("transaction_id"));
		map.put("out_trade_no",getTextByElementName("out_trade_no"));
		map.put("attach",getTextByElementName("attach"));
		map.put("time_end",getTextByElementName("time_end"));
		
		return map;
	}
	
	/**
	 * 解析文档
	 * @param str
	 * 			元素名
	 * @return
	 * 			元素名所对应内容
	 */
	public static String getTextByElementName(String str){
		if(StringUtils.isBlank(str)||null == rootElement){
			return null;
		}
		return null == rootElement.element(str)?null:rootElement.element(str).getTextTrim();
	}
	
	/**
	 * 判断手机号true为真正手机号
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	/**
	 * 验证车牌正确性 true为真的车牌
	 * @param licensePlateNumber
	 * @return
	 */
	public static boolean isLicensePlateNumber(String licensePlateNumber) {
			Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");
			Matcher matcher = pattern.matcher(licensePlateNumber);
			return matcher.matches();
	}
	/**
	 * 判断是否为空字符串或者null
	 * 
	 * @param str
	 * @return ""或null 返回true. 否:返回false;
	 */
	
	public static boolean isEmptyOrNull(String str) {
		boolean bl = false;
		if(str==null){
			bl = true;
		}else{			
			if("".equals(str.trim())){
				bl = true;
			}	
		}		
		return bl;
	}
}
