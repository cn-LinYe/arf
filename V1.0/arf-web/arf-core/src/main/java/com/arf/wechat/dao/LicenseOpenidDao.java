package com.arf.wechat.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.wechat.entity.LicenseOpenid;
import com.arf.wechat.entity.LicenseOpenid.Status;

public interface LicenseOpenidDao extends BaseDao<LicenseOpenid, Long>{

	LicenseOpenid findByOpenIdLicenseStatus(String openId, String license,Status status);

	List<LicenseOpenid> findByOpenId(String openId,Status status);

	void updateBatch(String openId,List<String> licenses,LicenseOpenid.Status status);

	List<LicenseOpenid> findByLicenseStatus(String license, Status status);
}
