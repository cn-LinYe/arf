package com.arf.carbright.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.carbright.dao.CabinetDao;
import com.arf.carbright.entity.Cabinet;
import com.arf.carbright.service.CabinetService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("cabinetService")
public class CabinetServiceImpl extends BaseServiceImpl<Cabinet, Long> implements CabinetService{
	
	@Resource(name = "cabinetDao")
	private CabinetDao cabinetDao;
	
	
	@Override
	protected BaseDao<Cabinet, Long> getBaseDao() {
		return cabinetDao;
	}


	@Override
	public List<Cabinet> findByCommunityNum(String communityNum) {
		return cabinetDao.findByCommunityNum(communityNum);
	}


	@Override
	public Cabinet findByNum(String communityNum, String cabinetNum) {
		List<Cabinet> cabs = this.findList(null, null,new Filter("parkingId", Operator.eq, communityNum),new Filter("cabinetNum", Operator.eq, cabinetNum));
		if(CollectionUtils.isNotEmpty(cabs)){
			return cabs.get(0);
		}else{
			return null;
		}
	}

}
