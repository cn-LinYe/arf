package com.arf.laundry.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.laundry.dao.ILaundryOrderComplaintDao;
import com.arf.laundry.entity.LaundryOrderComplaint;
import com.arf.laundry.service.ILaundryOrderComplaintService;

@Service("laundryOrderComplaintServiceImpl")
public class LaundryOrderComplaintServiceImpl extends BaseServiceImpl<LaundryOrderComplaint, Long> implements ILaundryOrderComplaintService {

	@Resource(name = "laundryOrderComplaintDaoImpl")
	private ILaundryOrderComplaintDao laundryOrderComplaintDaoImpl;
	
	@Override
	protected BaseDao<LaundryOrderComplaint, Long> getBaseDao() {
		return laundryOrderComplaintDaoImpl;
	}

}
