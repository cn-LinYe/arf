package com.arf.goldcard.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.goldcard.dao.IGoldCardTransferRecordDao;
import com.arf.goldcard.entity.GoldCardTransferRecord;
import com.arf.goldcard.service.IGoldCardTransferRecordService;

@Service("goldCardTransferRecordServiceImpl")
public class GoldCardTransferRecordServiceImpl extends BaseServiceImpl<GoldCardTransferRecord, Long>
		implements IGoldCardTransferRecordService {

	@Resource(name = "goldCardTransferRecordDaoImpl")
	private IGoldCardTransferRecordDao goldCardTransferRecordDao;
	
	protected BaseDao<GoldCardTransferRecord,Long> getBaseDao(){
		return goldCardTransferRecordDao;
	}
	
	@Override
	public PageResult<GoldCardTransferRecord> findByCondition(String userName, String cardNo, GoldCardTransferRecord.Type type, int pageSize,int pageNo) {
		return goldCardTransferRecordDao.findByCondition(userName, cardNo, type, pageSize, pageNo);
	}

	@Override
	public GoldCardTransferRecord genGoldCardTransferRecord(BigDecimal balance,BigDecimal amount,String remark,Integer type,String userName,Integer status,String orderNo) {
		GoldCardTransferRecord goldCardTransferRecord = new GoldCardTransferRecord();
		goldCardTransferRecord.setBalance(balance.setScale(2,BigDecimal.ROUND_HALF_UP));
		goldCardTransferRecord.setAmount(amount.setScale(2,BigDecimal.ROUND_HALF_UP));
		goldCardTransferRecord.setRemark(remark);
		goldCardTransferRecord.setType(type);
		goldCardTransferRecord.setUserName(userName);
		goldCardTransferRecord.setStatus(status);
		goldCardTransferRecord.setOrderNo(orderNo);
		return goldCardTransferRecord;
	}

	@Override
	public GoldCardTransferRecord genGoldCardTransferRecord(BigDecimal balance, BigDecimal amount, String remark,
			Integer type, String userName, Integer status, String orderNo, Integer payType) {
		GoldCardTransferRecord goldCardTransferRecord = new GoldCardTransferRecord();
		goldCardTransferRecord.setBalance(balance.setScale(2,BigDecimal.ROUND_HALF_UP));
		goldCardTransferRecord.setAmount(amount.setScale(2,BigDecimal.ROUND_HALF_UP));
		goldCardTransferRecord.setRemark(remark);
		goldCardTransferRecord.setType(type);
		goldCardTransferRecord.setUserName(userName);
		goldCardTransferRecord.setStatus(status);
		goldCardTransferRecord.setOrderNo(orderNo);
		goldCardTransferRecord.setPayType(payType);
		return goldCardTransferRecord;
	}

}
