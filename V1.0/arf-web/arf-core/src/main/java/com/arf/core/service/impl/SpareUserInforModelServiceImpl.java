package com.arf.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.SpareUserInforModelDao;
import com.arf.core.entity.SpareUserInforModel;
import com.arf.core.service.SpareUserInforModelService;

/**
 * Dao -物业上传用户信息（备份）
 * @author arf  zgr
 * @version 4.0
 *
 */
@Service("spareUserInforModelServiceImpl")
public class SpareUserInforModelServiceImpl extends BaseServiceImpl<SpareUserInforModel, Long> implements SpareUserInforModelService {

	@Resource(name = "spareUserInforModelDaoImpl")
	private SpareUserInforModelDao spareUserInforModelDao;
	
	@Override
	protected BaseDao<SpareUserInforModel, Long> getBaseDao() {
		return spareUserInforModelDao;
	}

	@Override
	public SpareUserInforModel selectByLicenseCommunity(String community, String LicensePlate) {
		return spareUserInforModelDao.selectByLicenseCommunity(community, LicensePlate);
	}


	@Override
	public List<SpareUserInforModel> selectByLicenseCommunitys(String LicensePlate,String community) {
		return spareUserInforModelDao.selectByLicenseCommunitys(LicensePlate,community);
	}
	
	@Override
	public SpareUserInforModel selectByLicensePlate(String LicensePlate) {
		return spareUserInforModelDao.selectByLicensePlate(LicensePlate);
	}

	@Override
	public List<SpareUserInforModel> selectByCommunity(String community) {
		return spareUserInforModelDao.selectByCommunity(community);
	}

	public List<SpareUserInforModel> selectByCommunitySingle(String community){
	    return spareUserInforModelDao.selectByCommunitySingle(community);
	}

}
