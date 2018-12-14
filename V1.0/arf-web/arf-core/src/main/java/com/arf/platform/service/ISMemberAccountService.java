package com.arf.platform.service;

import java.math.BigDecimal;

import com.arf.core.service.BaseService;
import com.arf.member.entity.PointTransferRecord;
import com.arf.platform.entity.SMemberAccount;

public interface ISMemberAccountService  extends BaseService<SMemberAccount, Long>{

	/**
	 * [已无效的]查询方法,如果一个userName即是普通用户又是商户,将会有两条记录,查询出的结果将会不正确,
	 * 请使用{@link com.arf.platform.service.ISMemberAccountService.findByUserNameUserType(String, Byte)}
	 * 通过用户名查询 会员账户 s_account
	 * @param userName
	 * @return
	 */
	@Deprecated
	SMemberAccount findByUserName(String userName);
	
	/**
	 * 通过账户编码查询 会员账户 s_account
	 * @param accountNumber
	 * @return
	 */
	SMemberAccount finByAccountNumber(String accountNumber);
	
	/**
	 * 生成电子账户编码
	 * @param userName
	 * @return
	 */
	String genAccountNumber(String userName);
	
	/**
	 *  通过用户名和用户类型查询 会员账户 s_account
	 * @param userName 用户名
	 * @param type 用户类型 {@link com.arf.platform.entity.SMemberAccount.Type}
	 * @return
	 */
	SMemberAccount findByUserNameUserType(String userName,Byte type);
	
	/**
	 * 通过用户名和用户类型查询和账户状态 会员账户 s_account
	 * @param userName 
	 * @param type 用户类型 {@link com.arf.platform.entity.SMemberAccount.Type}
	 * @param status 状态  {@link com.arf.platform.entity.SMemberAccount.Status}
	 * @return
	 */
	SMemberAccount findByUserNameUserTypeStatus(String userName,Byte type,Byte status);
	
	/**
	 * 用户(消费/代收)付款成功后赠送积分
	 * 目前支持月租缴费,小区临时停车赠送积分，如别情况需要赠送，则应扩展该方法
	 * @author hemingcheng 2016/12/14
	 * 
	 * @param userName 用户名
	 * @param totalFeeYuan 消费金额（圆）
	 * @param type 积分子类型，参考：com.arf.member.entity.PointTransferRecord.SubType
	 * @param communityNumber
	 * @param propretyId
	 * @param branchId
	 * @return 本次赠送的积分数，-1 为写入失败。
	 */
	Integer addUserPointByPayType(String userName,BigDecimal totalFeeYuan,byte type,
			String communityNumber, Integer propretyId, Integer branchId);

}
