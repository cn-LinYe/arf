package com.arf.core.service.impl;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.dao.DevolutionLicensePlateModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.DevolutionLicensePlateModel;
import com.arf.core.oldws.BaseModelRsp;
import com.arf.core.oldws.ConstantAdminCode;
import com.arf.core.service.DevolutionLicensePlateModelService;


/**
 * Service - 授权表
 * 
 * @author arf
 * @version 4.0
 */
@Service("devolutionLicensePlateModelServiceImpl")
public class DevolutionLicensePlateModelServiceImpl extends BaseServiceImpl<DevolutionLicensePlateModel, Long> implements DevolutionLicensePlateModelService {

	@Resource(name = "devolutionLicensePlateModelDaoImpl")
	private DevolutionLicensePlateModelDao devolutionLicensePlateModelDao;

	@Override
	protected BaseDao<DevolutionLicensePlateModel, Long> getBaseDao() {
		return devolutionLicensePlateModelDao;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public DevolutionLicensePlateModel selectByLicesenUser_name(String user_name, String license_plate) {
		return devolutionLicensePlateModelDao.selectByLicesenUser_name(user_name, license_plate);
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public DevolutionLicensePlateModel selectByLicenseAndUserName(String user_name, String license_plate) {
		return devolutionLicensePlateModelDao.selectByLicenseAndUserName(user_name, license_plate);
	}
	

	@Override
	public List<DevolutionLicensePlateModel> selectDevolution(String user_name) {		
		return devolutionLicensePlateModelDao.selectDevolution(user_name);
	}

	@Override
	public List<DevolutionLicensePlateModel> CheckUser_name(String user_nane, Long endTime) {
		return devolutionLicensePlateModelDao.CheckUser_name(user_nane, endTime);
	}

	@Override
	public List<DevolutionLicensePlateModel> selectByLicense(String license_plate_number) {
		return devolutionLicensePlateModelDao.selectByLicense(license_plate_number);
	}
	
	@Override
	public DevolutionLicensePlateModel selectByLicenseUserName(String user_name,String license_plate_number){
		return devolutionLicensePlateModelDao.selectByLicenseUserName(user_name, license_plate_number);
	}
	
	@Override
	public List<DevolutionLicensePlateModel> selectDevolutionByLicense(String license_plate_number) {
		return devolutionLicensePlateModelDao.selectDevolutionByLicense(license_plate_number);
	}

	@Override
	public List<Map<String,Object>> findParkRateByUserName(String userName) {
		return devolutionLicensePlateModelDao.findParkRateByUserName(userName);
	}
	
	@Override
	public BaseModelRsp deleteDevolutions(String licensePlateNumber){
		BaseModelRsp rsp = new BaseModelRsp();
		if(StringUtils.isEmpty(licensePlateNumber)){
			rsp.setCode(ConstantAdminCode.ADMIN_PARAM_MISS_CODE);
			rsp.setMsg(ConstantAdminCode.ADMIN_PARAM_MISS_CODE_MSG);
			return rsp;
		}
		List<DevolutionLicensePlateModel> devolutionlist= this.selectDevolutionByLicense(licensePlateNumber);
		if(CollectionUtils.isEmpty(devolutionlist)){
			rsp.setCode(ConstantAdminCode.ADMIN_DB_NO_DATA_CODE);
			rsp.setMsg("没有查询到该车牌的授权记录");
			return rsp;
		}
		Long[] ids = new Long[devolutionlist.size()];
		String[] userNames = new String[devolutionlist.size()];
		int count = devolutionlist.size();
		for(int i=0;i<count;i++){
			ids[i] = devolutionlist.get(i).getId();
			userNames[i] = devolutionlist.get(i).getUser_name();
		}
		try{
			this.delete(ids);
			rsp.setCode(ConstantAdminCode.ADMIN_SUCCESS_CODE);
			rsp.setMsg(ConstantAdminCode.ADMIN_SUCCESS_CODE_MSG);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userNames", userNames);
			rsp.setExtrDatas(map);
			return rsp;
		}catch(Exception e){
			rsp.setCode(ConstantAdminCode.ADMIN_DB_DELETE_ERROR_CODE);
			rsp.setMsg("服务器异常");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userNames", userNames);
			rsp.setExtrDatas(map);
			return rsp;
		}
	}

	@Override
	public List<Map<String,Object>> findByUserNameLeftjoinLicenseplate(
			String userName) {
		return devolutionLicensePlateModelDao.findByUserNameLeftjoinLicenseplate(userName);
	}
}