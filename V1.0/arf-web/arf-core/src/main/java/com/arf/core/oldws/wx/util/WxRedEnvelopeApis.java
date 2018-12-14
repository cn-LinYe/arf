package com.arf.core.oldws.wx.util;

import com.arf.core.oldws.wx.entity.RedEnvelopeResponse;
import com.arf.core.oldws.wx.entity.SearchRedEnvelopeResult;

public interface WxRedEnvelopeApis {
	
	/**
	 * 发送商家红包
	 * @param openId
	 * @param money 红包金额 单位:分
	 * @param billno 商户唯一订单号
	 * @param wishing 红包许愿
	 * @param actName 活动名称
	 * @return
	 */
	public RedEnvelopeResponse sendRedEnvelope(String openId, int money,String billno,String wishing,String actName);
	
	/**
	 * 查询红包记录
	 * @param mch_billno
	 * @return
	 */
	public SearchRedEnvelopeResult searchRedEnvelope(String mch_billno,String mch_id);
}
