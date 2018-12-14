package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.RedRecordModel;

/**
 * Service - 发送红包纪录表
 * 
 * @author arf
 * @version 4.0
 */
public interface RedRecordModelService extends BaseService<RedRecordModel, Long> {
	/**
	 * 查询未成功的红包信息
	 * @return	未成功的红包信息
	 */
	List<RedRecordModel> sellectByStatus();
	
	RedRecordModel findByOutTradeNo(String outTradeNo);
	/**
	 * 给指定openId用户发送红包,当系统中存在存在用户关注微信信息记录和充值记录才会顺利执行该方法,两者缺一个都会立即返回
	 * @param unionId
	 * @param openId
	 * @return
	 */
	boolean sendRedPkg(String unionId,String openId);
}
