package org.arf.core;

import org.apache.commons.lang.RandomStringUtils;

import com.arf.core.util.MD5Util;

public class RongshidaAPI {
	public static void main(String[] args) {
		String nonce_str = RandomStringUtils.randomAlphanumeric(12);
		
		
//		#服务API
//		1 设备出币API
//		http://api.mplink.cn/DeviceOpera.svc/PayOut?nonce_str={nonce_str}&affiliate_code={affiliate_code}&device_code={device_code}&order_code={order_code}&coin_count= {coin_count}&sign={sign}
		
//		2 查询设备状态API
		
//		3 查询所有在线设备API
		
//		4 查询订单状态API
		
//		5 查询投币/退币记录API 暂只支持查询投币记录

		
		
//		#回调API 
//		1 设备在线/离线通知API
//		2 出币结果通知API
//		3 设备投币/退币通知API
		
		String md5u =MD5Util.getMD5("18523349962");
		System.out.println(md5u);
	}
}