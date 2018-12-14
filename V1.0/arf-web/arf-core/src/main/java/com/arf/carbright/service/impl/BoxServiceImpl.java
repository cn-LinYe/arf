package com.arf.carbright.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arf.carbright.dao.BoxDao;
import com.arf.carbright.entity.Box;
import com.arf.carbright.entity.Cabinet;
import com.arf.carbright.service.BoxService;
import com.arf.carbright.service.CabinetService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("boxService")
public class BoxServiceImpl extends BaseServiceImpl<Box, Long> implements BoxService {
	
	@Resource(name="boxDao")
	private BoxDao boxDao;
	
	@Resource(name="cabinetService")
	private CabinetService cabinetService;
	
	@Override
	protected BaseDao<Box, Long> getBaseDao() {
		return boxDao;
	}

	@Override
	public Box findAvailableOne(String communityNumber,String cabinetNum) {
		List<Box> boxes = boxDao.findByUseStatus(communityNumber,Box.UseStatus.Not_Used,cabinetNum);
		if(!CollectionUtils.isEmpty(boxes)){
			return boxes.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Box findByNumber(String communityNumber,String cabinetNum,String boxNum) {
		return boxDao.findByNumber(communityNumber,cabinetNum,boxNum);
	}

	@Override
	public List<Box> findByCabinetNumber(String cabinetNumber) {
		return boxDao.findByCabinetNumber(cabinetNumber);
	}

	@Override
	public List<Box> findAllByCommunityNum(String communityNum) {
		if(StringUtils.isBlank(communityNum)){
			return Collections.emptyList();
		}
		List<Box> boxes = new ArrayList<Box>();
		List<Cabinet> cabs = cabinetService.findByCommunityNum(communityNum);
		for(Cabinet cab : cabs){
			List<Box> list = this.findByCabinetNumber(cab.getCabinetNum());
			if(!org.springframework.util.CollectionUtils.isEmpty(list)){
				boxes.addAll(list);
			}
		}
		return boxes;
	}

	@Override
	public List<HashMap<String, Object>> cabinetBoxUsedCount(String communityNumber, String cabinetNum) {		
		return boxDao.cabinetBoxUsedCount(communityNumber, cabinetNum);
	}

	@Override
	public List<Box> findByCabinetBox(String communityNumber, String cabinetNum, String boxNum) {
		return boxDao.findByCabinetBox(communityNumber, cabinetNum, boxNum);
	}

	@Override
	@javax.transaction.Transactional
	public void releaseUsedBoxes(Date before) {
		boxDao.releaseUsedBoxes(before);
	}
	
}