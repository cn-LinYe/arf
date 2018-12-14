package com.arf.member.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.util.MStringUntils;
import com.arf.member.dao.IRAccountTransferRecordDao;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.search.RAccountTransferRecordCondition;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.platform.entity.SMemberAccount;

@Service("rAccountTransferRecordServiceImpl")
public class RAccountTransferRecordServiceImpl extends BaseServiceImpl<RAccountTransferRecord, Long> implements IRAccountTransferRecordService{

	@Resource(name = "rAccountTransferRecordDaoImpl")
	private IRAccountTransferRecordDao rAccountTransferRecordDaoImpl;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Override
	protected BaseDao<RAccountTransferRecord, Long> getBaseDao() {
		return rAccountTransferRecordDaoImpl;
	}

	@Override
	public PageResult<RAccountTransferRecord> findListByCondition(RAccountTransferRecordCondition condition) {
		
		return rAccountTransferRecordDaoImpl.findListByCondition(condition);
	}
	
	@Override
	public List<RAccountTransferRecord> findAllExpenseList(String userName,Byte userType,Date startTime,Date endTime){
		return rAccountTransferRecordDaoImpl.findAllExpenseList(userName,userType,startTime,endTime);
	}

	@Override
	public RAccountTransferRecord genAcoountRecords(SMemberAccount sMemberAccount,String out_trade_no,double fee,String communityNo,
			Byte userType, Byte type, String comment) {
		Assert.notNull(sMemberAccount,"账户不能为空");
		Assert.notNull(userType,"用户类型不能为空");
		Assert.notNull(type,"类型不能为空");
		RAccountTransferRecord rAccountTransferRecord = new RAccountTransferRecord();
		rAccountTransferRecord.setAccountId(sMemberAccount.getId().toString());
		rAccountTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
		rAccountTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
		rAccountTransferRecord.setOrderNo(out_trade_no);
		rAccountTransferRecord.setSerialNumber(RAccountTransferRecord.genSerialNumber(sMemberAccount.getAccountNumber()));
		rAccountTransferRecord.setUserName(sMemberAccount.getUserName());
		rAccountTransferRecord.setAmount(new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_UP));
		rAccountTransferRecord.setAccountBalance(sMemberAccount.getBasicAccount());
		rAccountTransferRecord.setAccountType((byte)RAccountTransferRecord.AccountType.basicAccount.ordinal());
		rAccountTransferRecord.setUserType(userType);//0、会员 1、商户
		rAccountTransferRecord.setOperateTime(new Date());
		rAccountTransferRecord.setOperateType((byte)RAccountTransferRecord.OperateType.inter.ordinal());
		rAccountTransferRecord.setStatus((byte)RAccountTransferRecord.Status.finished.ordinal());
		rAccountTransferRecord.setType(type);
		rAccountTransferRecord.setComment(comment);
		rAccountTransferRecord.setIsConfirmed((byte)RAccountTransferRecord.IsConfirmed.ok.ordinal());
		Integer propertyNumber = null;
    	Integer branchId = null;
    	if(MStringUntils.isNotEmptyOrNull(communityNo)){
	    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(communityNo);
	    	if(communityList != null && communityList.size()>0){
	    		propertyNumber = communityList.get(0).getProperty_number();
	    		branchId = communityList.get(0).getBranchId();
	    	}
    	}
    	rAccountTransferRecord.setCommunityNumber(communityNo);
    	rAccountTransferRecord.setPropertyNumber(propertyNumber);
    	rAccountTransferRecord.setBranchId(branchId);
    	return rAccountTransferRecord;
	}

	@Override
	public Double getExpenseByCondition(String userName, Byte userType,Date startTime, Date endTime) {
		return rAccountTransferRecordDaoImpl.getExpenseByCondition(userName,userType,startTime,endTime);
	}

}
