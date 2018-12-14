package com.arf.wechat.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.wechat.dao.LicenseOpenidDao;
import com.arf.wechat.entity.LicenseOpenid;
import com.arf.wechat.entity.LicenseOpenid.Status;

@Repository("licenseOpenidDaoImpl")
public class LicenseOpenidDaoImpl extends BaseDaoImpl<LicenseOpenid, Long> implements LicenseOpenidDao {

	@Override
	public LicenseOpenid findByOpenIdLicenseStatus(String openId,String license, Status status) {
		String hql = "from LicenseOpenid where openId = :openId and license = :license";
		if(status!=null){
			hql += " and status = :status";
		}
		TypedQuery<LicenseOpenid> query = entityManager.createQuery(hql, LicenseOpenid.class);
		query.setParameter("openId", openId);
		query.setParameter("license", license);
		if(status!=null){
			query.setParameter("status", status);
		}
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<LicenseOpenid> findByOpenId(String openId,Status status) {
		if(openId == null){
			return null;
		}
		StringBuffer hql =  new StringBuffer("from LicenseOpenid where openId = :openId");
		if(status != null){
			hql.append(" and status = :status");
		}
		hql.append(" order by lastWxUsed desc,createDate desc");
		TypedQuery<LicenseOpenid> query = entityManager.createQuery(hql.toString(), LicenseOpenid.class);
		query.setParameter("openId", openId);
		if(status != null){
			query.setParameter("status", status);
		}
		return query.getResultList();
	}

	@Override
	public void updateBatch(String openId,List<String> licenses,LicenseOpenid.Status status){
		if(licenses == null || status == null){
			return;
		}
		String sql = "update LicenseOpenid set status = :stauts where license in (:list)";
		if(StringUtils.isNotBlank(openId)){
			sql += " and open_id =:openId ";
		}
		Query query = entityManager.createQuery(sql)
				.setParameter("stauts", status)
				.setParameter("list", licenses);
		if(StringUtils.isNotBlank(openId)){
			query.setParameter("openId", openId);
		}
		query.executeUpdate();
	}

	@Override
	public List<LicenseOpenid> findByLicenseStatus(String license, Status status) {
		if(license == null){
			return null;
		}
		StringBuffer hql =  new StringBuffer("from LicenseOpenid where license = :license");
		if(status != null){
			hql.append(" and status = :status");
		}
		hql.append(" order by lastWxUsed desc,createDate desc");
		TypedQuery<LicenseOpenid> query = entityManager.createQuery(hql.toString(), LicenseOpenid.class);
		query.setParameter("license", license);
		if(status != null){
			query.setParameter("status", status);
		}
		List<LicenseOpenid> result =  query.getResultList();
		if(CollectionUtils.isEmpty(result) && license.length() == 8){//如果没查询到该车牌记录且输入车牌号为8位,最后一位当做误输入在查询一次
			result = this.findByLicenseStatus(license.substring(0, 7), status);
		}
		return result;
	}
	
}
