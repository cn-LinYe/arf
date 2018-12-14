package com.arf.platform.dao;

import com.arf.core.dao.BaseDao;
import com.arf.platform.entity.SMemberAccount;

public interface ISMemberAccountDao extends BaseDao<SMemberAccount, Long>{

	/**
	 * [已无效的]查询方法,如果一个userName即是普通用户又是商户,将会有两条记录,查询出的结果将会不正确,
	 * 请使用{@link com.arf.platform.dao.ISMemberAccountDao.findByUserNameUserType(String, Byte)}
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
	 * 通过用户名和用户类型查询 会员账户 s_account
	 * @param userName
	 * @param type 用户类型 {@link com.arf.platform.entity.SMemberAccount.Type}
	 * @return
	 */
	SMemberAccount findByUserNameUserType(String userName, Byte type);

	/**
	 * 通过用户名和用户类型查询和账户状态 会员账户 s_account
	 * @param userName
	 * @param type type 用户类型 {@link com.arf.platform.entity.SMemberAccount.Type}
	 * @param status  状态  {@link com.arf.platform.entity.SMemberAccount.Status}
	 * @return
	 */
	SMemberAccount findByUserNameUserTypeStatus(String userName, Byte type,Byte status);

}
