package com.arf.carbright.dao;

import java.util.List;
import java.util.Map;

import com.arf.carbright.entity.AxdVouchers;
import com.arf.core.dao.BaseDao;
import com.arf.insurance.dto.VouchersDto;

public interface IAxdVouchersDao extends BaseDao<AxdVouchers, Long>{
	
	public AxdVouchers findByVouchersNum(String vouchersNum);
	
	/**
	 * 查找所有代金券信息
	 * @return
	 */
	List<VouchersDto> findAllVouchers(Integer pageNo,Integer pageSize,Integer vouchersType,String userName);
	
	/**
	 * 查找可见代金券
	 * @param cityCode
	 * @param isPark
	 * @return
	 */
	List<Map<String,Object>> findVouchers(Integer cityCode,Integer privinceCode,boolean isPark);
}
