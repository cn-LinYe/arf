package com.arf.carbright.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.entity.AxdVouchersRecord.FeePayStatus;
import com.arf.carbright.entity.AxdVouchersRecord.UsedStatus;
import com.arf.carbright.entity.AxdVouchersRecord.VouchersType;
import com.arf.core.service.BaseService;

public interface IAxdVouchersRecordService extends BaseService<AxdVouchersRecord,Long>{

	/**
	 * 可用、未过时的套餐类型
	 * @param isEnabled
	 * @param endTime
	 * @return
	 */
	public List<AxdVouchersRecord> findByStatusAndEndTime(UsedStatus usedStatus, Date endTime);
	
	/**获取用户代金券
	 * @param userName 用户登录手机号码
	 * @param vouchersType 代金券类型0点滴洗服务1安心点充值服务2安心点停车服务
	 * @param usedStatus 代金券状态(0可用 1用完 2已过期)
	 * @param pageSize 数据条数
	 * @param pageNo 第几页
	 * @return
	 */
	public PageResult<AxdVouchersRecord> findUserVouchersRecord(String userName,VouchersType vouchersType,UsedStatus usedStatus,int pageSize, int pageNo);
	
	/**获取用户可用代金券数量
	 * @param userName 用户登录手机号码
	 * @param usedStatus 代金券状态(0可用 1用完 2已过期)
	 * @return
	 */
	public Integer findUserAllVouchersRecord(String userName,UsedStatus usedStatus);
	
	/**获取用户可用每种代金券可用数量
	 * @param userName 用户登录手机号码
	 * @param usedStatus 代金券状态(0可用 1用完 2已过期)
	 * @return
	 */
	public List<Map<String , Object>> findUserVouchersType(String userName,UsedStatus usedStatus);
	
	/**根据代金券编号获取代金券信息
	 * @param vouchersNums 代金券
	 * @return
	 */
	public AxdVouchersRecord findUsedDishVouchersByNum(String userName,String vouchersNums,UsedStatus usedStatus);
	
	/**根据代金券编号以及用户名获取代金券信息
	 * @param vouchersNums 代金券
	 * @return
	 */
	public AxdVouchersRecord findVouchersByNum(String userName,String vouchersNums);
	

	/**
	 * 根据订单编号和状态获取代金券信息
	 * @param vouchersNums
	 * @param usedStatus
	 * @return
	 */
	public AxdVouchersRecord findUsedDishVouchersByNum(String orderNo,FeePayStatus feePayStatus);
	
	/**
	 * 使用了代金券后批量更新
	 * @param useStatus
	 * @param feePayType
	 * @param outTradeNo
	 * @param feePayStatus
	 * @param list
	 */
	public void updateBatch(Integer useStatus,Integer feePayType,String outTradeNo,Integer feePayStatus,List<String> list);


}
