package com.arf.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.wechat.dao.LicenseOpenidDao;
import com.arf.wechat.entity.LicenseOpenid;
import com.arf.wechat.entity.WXPayParkingFee;
import com.arf.wechat.entity.LicenseOpenid.Status;
import com.arf.wechat.service.LicenseOpenidService;

@Service("licenseOpenidServiceImpl")
public class LicenseOpenidServiceImpl extends BaseServiceImpl<LicenseOpenid, Long> implements LicenseOpenidService {

	@Resource(name = "licenseOpenidDaoImpl")
	private LicenseOpenidDao licenseOpenidDaoImpl;
	
	@Override
	protected BaseDao<LicenseOpenid, Long> getBaseDao() {
		return licenseOpenidDaoImpl;
	}

	@Override
	public LicenseOpenid findByOpenIdLicenseStatus(String openId,String license, Status status) {
		return licenseOpenidDaoImpl.findByOpenIdLicenseStatus(openId,license,status);
	}

	@Override
	public List<LicenseOpenid> findByOpenId(String openId,Status status) {
		return licenseOpenidDaoImpl.findByOpenId(openId,status);
	}

	@Override
	public void updateBatch(String openId,List<String> licenses,LicenseOpenid.Status status){
		licenseOpenidDaoImpl.updateBatch(openId,licenses, status);
	}

	@Override
	public List<LicenseOpenid> findByLicenseStatus(String license, Status status) {
		return licenseOpenidDaoImpl.findByLicenseStatus(license,status);
	}
}
