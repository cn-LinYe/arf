package com.arf.core.dao;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.entity.MessagePushed;
import com.arf.core.entity.MessagePushed.AppType;
import com.arf.core.entity.MessagePushed.MsgStatus;
import com.arf.core.entity.MessagePushed.PushType;
import com.arf.core.entity.MessagePushed.UserType;

public interface MessagePushedDao extends BaseDao<MessagePushed, Long>{
	
	public final String FLAG_READ_ALL = "-1";

	/**
	 * 分页查询我的消息列表
	 * @param userName
	 * @param userType
	 * @param alias
	 * @param pageSize
	 * @param pageNo
	 * @param msgStatus
	 * @param pushType
	 * @return
	 */
	PageResult<Map<String, Object>> findListByCondition(String userName,UserType userType,String alias,
			Integer pageSize,Integer pageNo, MsgStatus msgStatus, PushType pushType,AppType appType);

	/**
	 * 统计用户未读（UNREAD）、已读（READ）、已删除消息数（DELETED）
	 * @param userName
	 * @param userType
	 * @param alias
	 * @return
	 */
	Map<String, Object> statistics(String userName,UserType userType,String alias,AppType appType);

	/**
	 * 根据用户名或别名逻辑删除推送记录
	 * @param userName
	 * @param alias
	 * @param msgIds 多个msgid逗号分隔
	 */
	int deleteRecord(String userName, String alias, String msgIds);
	
	/**
	 * 根据用户名或别名修改推送记录状态
	 * @param userName
	 * @param alias
	 * @param msgIds 不可为空,如果为FLAG_DELETE_ALL(-1)则标记为删除所有消息
	 * @param msgStatus
	 * @return
	 */
	public int operationRecord(String userName, String alias, String msgIds, MessagePushed.MsgStatus msgStatus,AppType appType);

	/**
	 * 根据msgid查询
	 * @param msgId
	 * @return
	 */
	MessagePushed findByMsgId(String msgId);
	
	/**
	 * 查询未读消息数量
	 * @param userName
	 * @param userType
	 * @return
	 */
	int findUnreadCount(String userName,MessagePushed.UserType userType);

	/**
	 * 根据msgUUID查询
	 * @param msgUUID
	 * @return
	 */
	MessagePushed findByMsgUUID(String msgUUID);

	public List<MessagePushed> findByTransactionIdContentType(String transactionId,String contentType);
}
