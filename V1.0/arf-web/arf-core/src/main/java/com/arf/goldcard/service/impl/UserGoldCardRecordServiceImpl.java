package com.arf.goldcard.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.goldcard.dao.IUserGoldCardRecordDao;
import com.arf.goldcard.entity.UserGoldCardRecord;
import com.arf.goldcard.service.IUserGoldCardRecordService;

@Service("userGoldCardRecordService")
public class UserGoldCardRecordServiceImpl extends BaseServiceImpl<UserGoldCardRecord, Long> implements IUserGoldCardRecordService{

	@Resource(name = "userGoldCardRecordDao")
	private IUserGoldCardRecordDao userGoldCardRecordDao;
	
	@Override
	protected BaseDao<UserGoldCardRecord, Long> getBaseDao() {
		return userGoldCardRecordDao;
	}

	@Override
	public PageResult<Map<String,Object>> getGoldCardOrders(String userName,Integer pageSize,Integer pageNO) {
		return userGoldCardRecordDao.getGoldCardOrders(userName,pageSize,pageNO);
	}

	@Override
	public UserGoldCardRecord findByCardNo(String cardNo) {
		List<UserGoldCardRecord> records = this.findList(null, null, new Filter("cardNo",Operator.eq,cardNo,false));
		if(CollectionUtils.isEmpty(records)){
			return null;
		}
		return records.get(0);
	}
}
