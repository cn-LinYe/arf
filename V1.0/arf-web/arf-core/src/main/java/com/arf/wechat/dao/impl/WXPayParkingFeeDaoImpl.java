package com.arf.wechat.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.wechat.dao.IWXPayParkingFeeDao;
import com.arf.wechat.entity.WXPayParkingFee;

@Repository("wxPayParkingFeeDaoImpl")
public class WXPayParkingFeeDaoImpl extends BaseDaoImpl<WXPayParkingFee, Long> implements IWXPayParkingFeeDao {

	private String allFields = "a.id,a.create_date as createDate,a.modify_date as modifyDate,a.version,a.license"
			+ ",a.open_id as openId,a.arrive_time as arriveTime,a.leave_time as leaveTime,a.paid_date as paidDate"
			+ ",a.parking_number as parkingNumber,a.parking_name as parkingName,a.duration,a.receivables_money as receivablesMoney"
			+ ",a.received_money as receivedMoney,a.fee,a.pay_status as payStatus,a.status,a.out_trade_no as outTradeNo"
			+ ",a.stop_type as stopType,a.user_name as userName";
	
	@SuppressWarnings("unchecked")
	@Override
	public PageResult<WXPayParkingFee> getRecordByOpenId(String openId, Integer pageSize,Integer pageNO){
		if(StringUtils.isBlank(openId)){
			return null;
		}
		StringBuffer hql  = new StringBuffer("select "+allFields+" from p_wx_pay_parking_fee a "
				+ " left join p_license_openid b on a.license = b.license "
				+ " where a.open_id =:openId and a.status != 1 and b.status = 0 order by a.leave_time desc");
		StringBuffer sqlCount = new StringBuffer("select count(1) from p_wx_pay_parking_fee a "
				+ " left join p_license_openid b on a.license = b.license "
				+ " where a.open_id =:openId and a.status != 1 and b.status = 0 ");
		Query query = entityManager.createNativeQuery(hql.toString());
		Query queryCount = entityManager.createNativeQuery(sqlCount.toString());
		query.setParameter("openId", openId);
		queryCount.setParameter("openId", openId);
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNO-1)*pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		List<WXPayParkingFee> list = new ArrayList<>();
		if(!CollectionUtils.isEmpty(rows)){
			for(Map<String,Object> row:rows){
				list.add(JSON.toJavaObject(new JSONObject(row), WXPayParkingFee.class));
			}
		}
		int count = 0;
		try{
			count = Integer.valueOf(queryCount.getSingleResult().toString());
		} catch(Exception e){
			e.printStackTrace();
		}
		PageResult<WXPayParkingFee> result = new PageResult<WXPayParkingFee>(list,count);
		return result;
	}
	
	@Override
	public WXPayParkingFee findByOutTradeNo(String outTradeNo) {
		StringBuffer hql  = new StringBuffer("from WXPayParkingFee where outTradeNo =:outTradeNo");
		TypedQuery<WXPayParkingFee> query = entityManager.createQuery(hql.toString(),WXPayParkingFee.class);
		query.setParameter("outTradeNo",outTradeNo);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void updateBatch(List<Long> recordId,WXPayParkingFee.Status status){
		if(recordId == null){
			return;
		}
		String sql = "update WXPayParkingFee set status = :stauts where id in (:list)";
		entityManager.createQuery(sql)
			.setParameter("stauts", status)
			.setParameter("list", recordId).executeUpdate();
	}
}
