package com.arf.wechat.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.wechat.entity.LicenseOpenid;
import com.arf.wechat.entity.LicenseOpenid.Status;

public interface LicenseOpenidService extends BaseService<LicenseOpenid, Long>{

	/**
	 * 
	 * @param openId 必填
	 * @param license 必填
	 * @param status 可为空
	 * @return
	 */
	LicenseOpenid findByOpenIdLicenseStatus(String openId, String license,Status status);

	/**
	 * 
	 * @param openId 必填
	 * @param status 可为空，即为查询所有
	 * @return
	 */
	List<LicenseOpenid> findByOpenId(String openId,Status status);

	void updateBatch(String openId,List<String> licenses,LicenseOpenid.Status status);

	List<LicenseOpenid> findByLicenseStatus(String license, Status normal);
}
