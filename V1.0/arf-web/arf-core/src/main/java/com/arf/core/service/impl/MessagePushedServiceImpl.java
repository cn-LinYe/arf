package com.arf.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.MessagePushedDao;
import com.arf.core.entity.MessagePushed;
import com.arf.core.entity.MessagePushed.AppType;
import com.arf.core.entity.MessagePushed.MsgStatus;
import com.arf.core.entity.MessagePushed.PushType;
import com.arf.core.entity.MessagePushed.UserType;
import com.arf.core.service.MessagePushedService;

@Service("messagePushedService")
public class MessagePushedServiceImpl extends BaseServiceImpl<MessagePushed, Long> implements MessagePushedService {

	@Resource(name = "messagePushedDao")
	private MessagePushedDao messagePushedDao;
	
	@Override
	protected BaseDao<MessagePushed, Long> getBaseDao() {
		return messagePushedDao;
	}

	@Override
	public PageResult<Map<String, Object>> findListByCondition(String userName,UserType userType,String alias,
			Integer pageSize,Integer pageNo, MsgStatus msgStatus, PushType pushType,AppType appType) {
		return messagePushedDao.findListByCondition(userName,userType,alias,pageSize,pageNo,msgStatus,pushType,appType);
	}

	@Override
	public Map<String, Object> statistics(String userName,UserType userType,String alias,AppType appType) {
		return messagePushedDao.statistics(userName,userType,alias,appType);
	}

	@Override
	public int deleteRecord(String userName, String alias, String msgIds) {
		return messagePushedDao.deleteRecord(userName,alias,msgIds);
	}
	
	@Override
	public int operationRecord(String userName, String alias, String msgIds, MessagePushed.MsgStatus msgStatus,AppType appType){
		return messagePushedDao.operationRecord(userName, alias, msgIds, msgStatus, appType);
	}

	@Override
	public MessagePushed findByMsgId(String msgId) {
		return messagePushedDao.findByMsgId(msgId);
	}

	@Override
	public int findUnreadCount(String userName, UserType userType) {
		return messagePushedDao.findUnreadCount(userName, userType);
	}

	@Override
	public MessagePushed findByMsgUUID(String msgUUID) {
		return messagePushedDao.findByMsgUUID(msgUUID);
	}

	@Override
	public List<MessagePushed> findByTransactionIdContentType(String transactionId,String contentType){
		return messagePushedDao.findByTransactionIdContentType(transactionId,contentType);
	}
}
