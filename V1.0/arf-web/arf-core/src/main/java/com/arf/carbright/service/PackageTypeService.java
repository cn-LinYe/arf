package com.arf.carbright.service;

import java.util.List;

import com.arf.carbright.entity.PackageType;
import com.arf.core.service.BaseService;

public interface PackageTypeService extends BaseService<PackageType, Long> {
	
	/**
	 * 根据商户id找到商户使用的套餐
	 * @param businessId
	 * @param enabled
	 * @return
	 */
	public List<PackageType> findByBusinessId(Long businessId,PackageType.IsEnabled enabled);
	
	/**
	 * 根据商户id和套餐类型找到此商户提供的符合范围的套餐列表
	 * @param businessId
	 * @param enabled
	 * @param businessServiceType
	 * @return
	 */
	public List<PackageType> findByBusinessIdAndType(Long businessId,PackageType.IsEnabled enabled,String businessServiceType);
	
	/**
	 * 根据套餐id查找此商户符合范围的套餐
	 * @param packTypeId
	 * @return
	 */
	public PackageType getPackageType(Integer packTypeId);
	
	/**
	 * 查看是否重复提交
	 * @param startTimeNum
	 * @return
	 */
	public PackageType findByStartTimeNum(Long startTimeNum);

}
