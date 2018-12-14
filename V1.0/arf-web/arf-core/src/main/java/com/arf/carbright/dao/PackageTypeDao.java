package com.arf.carbright.dao;

import java.util.Date;
import java.util.List;

import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.PackageType.IsEnabled;
import com.arf.core.dao.BaseDao;


public interface PackageTypeDao extends BaseDao<PackageType, Long>{

	/**
	 * 查找在使用范围内并且启用的套餐
	 * @param parkingID
	 * @return
	 */
	List<PackageType> findbyUrnenabled(String useRangeNum,Integer isEnabled);
	
	/**
	 * 根据可用的套餐编号查询套餐
	 * @param useRangeNum
	 * @param isEnabled
	 * @return
	 */
	PackageType findbyPackageTypeNum(String packageTypeNum,Integer isEnabled);
	/**
	 * 查询商家设置的套餐总数
	 */
	List<PackageType> findByBusinessId(Long businessId,PackageType.IsEnabled enabled);
	
	/**
	 * 根据商户id和套餐类型查找此商户符合范围的套餐列表
	 * @param businessId
	 * @param enabled
	 * @param businessServiceType
	 * @return
	 */
	public List<PackageType> findByBusinessIdAndType(Long businessId, IsEnabled enabled,String businessServiceType);
	
	/**
	 * 根据套餐id查找此商户符合范围的套餐
	 * @param packTypeId
	 * @return
	 */
	public PackageType getPackageType(Integer packTypeId);

	/**
	 * 可用、未过时的套餐类型
	 * @param isEnabled
	 * @param endTime
	 * @return
	 */
	List<PackageType> findByStatusAndEndTime(Integer isEnabled, Date endTime);
	
	/**
	 * 查看是否重复提交
	 * @param startTimeNum
	 * @return
	 */
	public PackageType findByStartTimeNum(Long startTimeNum);
	
}
