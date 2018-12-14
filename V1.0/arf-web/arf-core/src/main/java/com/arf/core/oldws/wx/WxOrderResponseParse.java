package com.arf.core.oldws.wx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 微信订单 回调信息解析 工具类。
 * @author no
 * @data 2015年12月6日
 */
public class WxOrderResponseParse {

	public static WxOrderResponse parseWxXmlRsp(String wxXmlRsp) throws DocumentException{
		if(null == wxXmlRsp || wxXmlRsp.isEmpty())
			return null;
		WxOrderResponse orderResponse = new WxOrderResponse();
		Document document = DocumentHelper.parseText(wxXmlRsp);
		Element rootElement = document.getRootElement();
		
		//解析
		
		String return_code = rootElement.element("return_code").getTextTrim();
		orderResponse.setResult_code(return_code);
		if(null == return_code || !"SUCCESS".equals(return_code)){
			orderResponse.setResult_code(rootElement.element("return_msg").getText());
			return orderResponse;
		}
		
		//
		orderResponse.setAppid(rootElement.element("appid").getTextTrim());
		orderResponse.setMch_id(rootElement.element("mch_id").getTextTrim());
		
		if(null != rootElement.element("device_info"))
		orderResponse.setDevice_info(rootElement.element("device_info").getTextTrim());
		orderResponse.setNonce_str(rootElement.element("nonce_str").getTextTrim());
		orderResponse.setSign(rootElement.element("sign").getTextTrim());
		orderResponse.setResult_code(rootElement.element("result_code").getTextTrim());
		
		if(null != rootElement.element("err_code"))
		orderResponse.setErr_code(rootElement.element("err_code").getTextTrim());
		if(null != rootElement.element("err_code_des"))
		orderResponse.setErr_code_des(rootElement.element("err_code_des").getTextTrim());
		orderResponse.setOpenid(rootElement.element("openid").getTextTrim());
		if(null != rootElement.element("is_subscribe"))
		orderResponse.setIs_subscribe(rootElement.element("is_subscribe").getTextTrim());
		orderResponse.setTrade_type(rootElement.element("trade_type").getTextTrim());
		orderResponse.setBank_type(rootElement.element("bank_type").getTextTrim());
		orderResponse.setTotal_fee(Integer.valueOf(rootElement.element("total_fee").getTextTrim()));
		if(null != rootElement.element("fee_type"))
		orderResponse.setFee_type(rootElement.element("fee_type").getTextTrim());
		orderResponse.setCash_fee(Integer.valueOf(rootElement.element("cash_fee").getTextTrim()));
		if(null != rootElement.element("cash_fee_type"))
		orderResponse.setCash_fee_type(rootElement.element("cash_fee_type").getTextTrim());
		if(null != rootElement.element("coupon_fee"))
		orderResponse.setCoupon_fee(Integer.valueOf(rootElement.element("coupon_fee").getTextTrim()));
		if(null != rootElement.element("coupon_count"))
		orderResponse.setCoupon_count(Integer.valueOf(rootElement.element("coupon_count").getTextTrim()));
		if(null != rootElement.element("coupon_id_$n"))
		orderResponse.setCoupon_id_$n(rootElement.element("coupon_id_$n").getTextTrim());
		if(null != rootElement.element("coupon_fee_$n"))
		orderResponse.setCoupon_fee_$n(Integer.valueOf(rootElement.element("coupon_fee_$n").getTextTrim()));
		orderResponse.setTransaction_id(rootElement.element("transaction_id").getTextTrim());
		orderResponse.setOut_trade_no(rootElement.element("out_trade_no").getTextTrim());
		if(null != rootElement.element("attach"))
		orderResponse.setAttach(rootElement.element("attach").getTextTrim());
		orderResponse.setTime_end(rootElement.element("time_end").getTextTrim());
		
		return orderResponse;
	}
	
	public static WxOrderResponse parseWxXmlRsp(InputStream ins) throws DocumentException{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		String temp = null;
		try {
			while(null != (temp = reader.readLine())){
				builder.append(temp);
				temp = null;			
			}			
			return parseWxXmlRsp(builder.toString());			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reader = null;
		}
		
	}
	
}
