package com.arf.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.wechat.dao.IWXPayParkingFeeDao;
import com.arf.wechat.entity.WXPayParkingFee;

@Service("wxPayParkingFeeServiceImpl")
public class WXPayParkingFeeServiceImpl extends BaseServiceImpl<WXPayParkingFee, Long>
		implements com.arf.wechat.service.WXPayParkingFeeService {

	@Resource(name = "wxPayParkingFeeDaoImpl")
	private IWXPayParkingFeeDao wxPayParkingFeeDao;
	
	@Override
	protected BaseDao<WXPayParkingFee, Long> getBaseDao() {
		return wxPayParkingFeeDao;
	}

	@Override
	public PageResult<WXPayParkingFee> getRecordByOpenId(String openId, Integer pageSize,Integer pageNO){
		return wxPayParkingFeeDao.getRecordByOpenId(openId, pageSize, pageNO);
	}
	
	@Override
	public WXPayParkingFee findByOutTradeNo(String outTradeNo) {
		return wxPayParkingFeeDao.findByOutTradeNo(outTradeNo);
	}	
	
	@Override
	public void updateBatch(List<Long> recordId,WXPayParkingFee.Status status){
		wxPayParkingFeeDao.updateBatch(recordId,status);
	}
}
