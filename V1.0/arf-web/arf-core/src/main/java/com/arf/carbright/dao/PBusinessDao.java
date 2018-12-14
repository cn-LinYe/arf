package com.arf.carbright.dao;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.carbright.condition.BusinessCondition;
import com.arf.carbright.dto.SearchBusinessDto;
import com.arf.carbright.entity.PBusiness;
import com.arf.core.dao.BaseDao;

public interface PBusinessDao extends BaseDao<PBusiness, Long> {
	
	
	/**
	 * 根据经纬度查询范围内的商户
	 * @param lat
	 * @param lng
	 * @param distance
	 * @return
	 */
	public PageResult<Map<String,Object>> findByLatAndLng(Double lat, Double lng,String distance,Integer businessServiceType, Integer pageSize, Integer pageNo);
	
	
	/**
	 * 用户名密码查询用户
	 * @param loginName
	 * @param Password
	 * @return
	 */
	public PBusiness findByUserPass(String loginName,String password);
	
	
	/**
	 * 用户名密码查询用户
	 * @param loginName
	 * @return
	 */
	public PBusiness findByUserName(String loginName);
	
	
	/**通过商户编号查找商户信息
	 * @param businessNum 商户编号
	 * @return
	 */
	public PBusiness findByBusinessId(int businessNum);
	/**
	 * 根据商户编码和商户服务类型查找商户
	 * @param businessNum
	 * @param businessServiceType  商户服务类型, 0、预约停车 1、洗车 2、缴纳物业费 3、洗衣
	 * @return
	 */
	public PBusiness findByBusinessNumAndType(Integer businessNum,String businessServiceType);
	
	/**
	 * 根据小区编号和商户服务类型查找商户
	 * @param communityNumber
	 * @param businessServiceType 商户服务类型, 0、预约停车 1、洗车 2、缴纳物业费 3、洗衣 5、保险
	 * @return
	 */
	public List<PBusiness> findByconidtion(String communityNumber,String businessServiceType);
	
	/**
	 * 条件搜索商户
	 * @param condition
	 * @return
	 */
	public List<SearchBusinessDto> searchBusiness(BusinessCondition condition);
	
	/**
	 * 通过商户编号集合查找商户
	 * @param business
	 * @return
	 */
	public List<PBusiness> findByBusiness(List<Integer> business);
}
