package com.arf.access.dao.impl;

import java.math.BigInteger;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessNumQrCodeInitDao;
import com.arf.access.entity.AccessNumQrCodeInit;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessNumQrCodeInitDao")
public class AccessNumQrCodeInitDaoImpl extends
		BaseDaoImpl<AccessNumQrCodeInit, Long> implements
		IAccessNumQrCodeInitDao {

	@Override
	public int findCountByAccessNum(long accessNum) {
		String sql = "select ifnull(count(access_num),0) as COUNT from access_num_qr_code_init where access_num = :accessNum ";
		Query q = this.entityManager.createNativeQuery(sql);
		q.setParameter("accessNum", accessNum);
		Object o = q.getSingleResult();
		if(o instanceof BigInteger){
			return ((BigInteger)o).intValue();
		}else if(o instanceof Long){
			return ((Long)o).intValue();
		}else if(o instanceof Integer){
			return ((Integer)o).intValue();
		}else{
			return 0;
		}
	}
}
