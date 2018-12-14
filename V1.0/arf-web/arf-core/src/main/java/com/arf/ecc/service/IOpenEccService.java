package com.arf.ecc.service;

import java.util.Map;

import com.arf.core.entity.Member;
import com.arf.core.service.BaseService;
import com.arf.ecc.entity.SettlementParam;

public interface IOpenEccService extends BaseService<Member, Long> {

	String EXCEPTION_LOG_KEY = "Open_Ecc_EXCEPTION_LOG_KEY:";
	
	/**
	 * 用户开通ecc公用方法
	 * @param userName 用户名
	 * @param assignType 开通ecc类型（通过什么途径开通ecc）
	 * @param outTradeNo 订单编号， 如果是套餐开通，此参数不能为空。
	 * @param typeNum 金卡类型，  如果是购买金卡开通，此参数不能为空。
	 * @param licensePlate 车牌，添加白名单时上传的车牌
	 * @return
	 */
	public Map<String,Object> openEcc(String userName, SettlementParam.AssignType assignType,String outTradeNo,String typeNum,String licensePlate);
}
