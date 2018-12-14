package com.arf.carbright.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IAxdVouchersDao;
import com.arf.carbright.entity.AxdVouchers;
import com.arf.carbright.service.IAxdVouchersService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.insurance.dto.VouchersDto;

@Service("axdVouchersServiceImpl")
public class AxdVouchersServiceImpl extends BaseServiceImpl<AxdVouchers, Long> implements IAxdVouchersService{

	@Resource(name ="axdVouchersDaoImpl")
	private IAxdVouchersDao axdVouchersDaoImpl;
	
	@Override
	protected BaseDao<AxdVouchers, Long> getBaseDao() {
		return axdVouchersDaoImpl;
	}
	
	@Override
	public AxdVouchers findByVouchersNum(String vouchersNum){
		return axdVouchersDaoImpl.findByVouchersNum(vouchersNum);
	}

	@Override
	public List<VouchersDto> findAllVouchers(Integer pageNo,Integer pageSize,Integer vouchersType,String userName) {
		return axdVouchersDaoImpl.findAllVouchers(pageNo,pageSize,vouchersType,userName);
	}

	@Override
	public List<Map<String, Object>> findVouchers(Integer cityCode,Integer privinceCode,boolean isPark) {
		return axdVouchersDaoImpl.findVouchers(cityCode,privinceCode, isPark);
	}

}
