package com.arf.laundry.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.laundry.dao.ILaundryOrderComplaintDao;
import com.arf.laundry.entity.LaundryOrderComplaint;

@Repository("laundryOrderComplaintDaoImpl")
public class LaundryOrderComplaintDaoImpl extends BaseDaoImpl<LaundryOrderComplaint, Long> implements ILaundryOrderComplaintDao {

	
}
