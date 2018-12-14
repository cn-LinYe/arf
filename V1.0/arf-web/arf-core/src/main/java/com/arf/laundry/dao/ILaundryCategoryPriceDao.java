package com.arf.laundry.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.laundry.entity.LaundryCategoryPrice;

public interface ILaundryCategoryPriceDao extends BaseDao<LaundryCategoryPrice, Long> {

	/**
	 * 通过商户编码查询该商户
	 * 能服务的衣服（大类）类型
	 * @param businessNum
	 * @return
	 */
	List<LaundryCategoryPrice> findSuperCategoryByBusinessNumStatus(Integer businessNum,LaundryCategoryPrice.Status status);

	/**
	 * 通过商户编码、大类编码查询该商户
	 * 能服务的衣服（子类）类型
	 * @param businessNum
	 * @param superCategoryNum
	 * @return
	 */
	List<LaundryCategoryPrice> findCategoryByBusinessNumAndSuperCategoryStatus(Integer businessNum, String superCategoryNum,LaundryCategoryPrice.Status status);

	/**
	 * 通过条件查询商户价目表
	 * @param businessNum 必填
	 * @param superCategoryNum 可为空，为空时查询出所有大类
	 * @param status 可为空，为空时查询出所有状态
	 * @return
	 */
	List<LaundryCategoryPrice> findByCondition(Integer businessNum, String superCategoryNum, LaundryCategoryPrice.Status status);
	
	/**
	 * 根据类目编号查询
	 * @param categoryNum
	 * @return
	 */
	LaundryCategoryPrice findByCategoryNumStatus(String categoryNum,LaundryCategoryPrice.Status status);

	/**
	 * 根据商户编码类目编码查询,如果status为null，则忽略status状态
	 * @param businessNum
	 * @param categoryNum
	 * @param status
	 * @return
	 */
	LaundryCategoryPrice findByBusinessNumCategoryNumStatus(String businessNum,String categoryNum,LaundryCategoryPrice.Status status);
}
