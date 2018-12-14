package com.arf.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.dao.LicensePlateModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.service.LicensePlateModelService;

/**
 * Service - 车牌表
 * 
 * @author arf
 * @version 4.0
 */
@Service("licensePlateModelServiceImpl")
public class LicensePlateModelServiceImpl extends BaseServiceImpl<LicensePlateModel, Long>
		implements LicensePlateModelService {

	@Resource(name = "licensePlateModelDaoImpl")
	private LicensePlateModelDao licensePlateModelDao;

	@Override
	protected BaseDao<LicensePlateModel, Long> getBaseDao() {
		return licensePlateModelDao;
	}

	@Override
	public List<LicensePlateModel> CheckLicensePlate(String License) {
		return licensePlateModelDao.CheckLicensePlate(License);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public LicensePlateModel selectByLicesenUser_name(String user_name, String license_plate) {
		return licensePlateModelDao.selectByLicesenUser_name(user_name, license_plate);
	}

	@Override
	public List<LicensePlateModel> CheckUser_name(String userName) {
		return licensePlateModelDao.CheckUser_name(userName);
	}

	@Override
	public List<LicensePlateModel> CheckLicense_plate_id() {
		return licensePlateModelDao.CheckLicense_plate_id();
	}

	@Override
	public LicensePlateModel selectByCarCode(String licensePlate, String carCode) {

		return licensePlateModelDao.selectByCarCode(licensePlate, carCode);
	}

	@Override
	public String getLicenseId() {

		String maxId = licensePlateModelDao.findMaxLicenseId();
		if (maxId == null || maxId.length() <= 0) {
			maxId = "0";
		}
		int count = 1;
		String id = "";
		while (true) {
			try {
				Integer maxIdInt = Integer.parseInt(maxId);
				maxIdInt = maxIdInt + count;
				String idStr = maxIdInt + "";
				if (idStr.length() < 8) {
					int len = idStr.length();
					for (int i = 0; i < 8 - len; i++) {
						idStr = "0" + idStr;
					}
				}

				LicensePlateModel licensePlateModel = licensePlateModelDao.findByLicenseId(idStr);
				if (licensePlateModel == null) {
					id = idStr;
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			count = count + 1;
		}
		return id;

	}

	/**
	 * 获取vip用户所有已经认证的车牌
	 * 
	 * @return
	 */
	public List<LicensePlateModel> getVipLicensePlate() {
		return licensePlateModelDao.getVipLicensePlate();
	}

	/**
	 * 获取用户所有绑定的车牌
	 * 
	 * @return
	 */
	public List<LicensePlateModel> getVipLicensePlate(String userName) {
		return licensePlateModelDao.getVipLicensePlate(userName);
	}

	@Override
	public List<LicensePlateModel> findNotMonthlyLicense(String userName) {
		return licensePlateModelDao.findNotMonthlyLicense(userName);
	}

	@Override
	public List<LicensePlateModel> findByLicenseAndNotNulluser(String license) {
		return licensePlateModelDao.findByLicenseAndNotNulluser(license);
	}

	@Override
	public List<Map<String,Object>> findParkRateByUserName(String userName) {
		return licensePlateModelDao.findParkRateByUserName(userName);
	}

	@Override
	public List<Map<String, Object>> findByUserNameAboutDevolution(
			String userName,String licensePlateNumber) {
		return licensePlateModelDao.findByUserNameAboutDevolution(userName,licensePlateNumber);
	}

	@Override
	public List<Map<String, Object>> findAllParkRate() {
		return licensePlateModelDao.findAllParkRate();
	}
}