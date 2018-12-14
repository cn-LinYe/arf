package com.arf.core.service;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dto.CommunityCitynoDto;
import com.arf.core.dto.CommunityRedisDto;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.CommunityModel.AuthorizationMode;

/**
 * Service - 小区表
 * 
 * @author arf
 * @version 4.0
 */
public interface CommunityModelService extends BaseService<CommunityModel, Long> {
	
	/**
	 * 获取小区
	 * @param no
	 * 		编码
	 * @return
	 */
	List<CommunityModel> CheckByNo(String no);
	/**
	 * 用户查看小区信息
	 * @param user_name
	 * 		用户名
	 * @param documentCode
	 * 		凭证码
	 * @return
	 */

	List<CommunityModel> checkByCommunity_number(String community_number);//通过小区id查找小区
	

	/**
	 * 通过物业id查看小区
	 * @param propretyId
	 * @return
	 */
	List<CommunityModel> checkByPropretyNumber(long propretyId);
	
	/*
	 * 请使用{@link com.arf.core.service.CommunityModelService.findByNumber(String)}
	 */
	@Deprecated
	List<CommunityModel> selectByLicenseCommunitys(String communityNumber);
	
	/**
	 * 查询所有开通发短信功能的小区
	 * @param communityNumber
	 * @return
	 */
	List<CommunityModel> selectBySendMessages();
	/**
	 * 通过小区号 得到校区负责人联系方式
	 * @param community_number
	 * @return
	 */
	public String getPrincipalMobile(String community_number);
	
	CommunityModel findByNumber(String communityNumber);
	
	/**通过redis获取小区编号查找小区信息
	 * @param communityNumber 小区编号
	 * @return
	 */
	CommunityRedisDto findRedisToDbByNumber(String communityNumber);
	/**
	 * 通过小区名称查找小区
	 * @param communityName
	 * @return
	 */
	List<CommunityModel> findByCommunityName(String communityName);
	
	/**
	 * 通过小区编号组查找小区
	 * @param communityNumbers
	 * @return
	 */
	List<CommunityModel> findByCommunityNumbers(List<String> communityNumbers);
	
	/**
	 * 通过授权模式查找
	 * @param communityName
	 * @return
	 */
	List<Map<String,Object>> findByAuthorizationMode(AuthorizationMode FreeComeAndOut);
	
	/**
	 * 通过小区名称查找小区名称
	 * @param communityNumber
	 * @return
	 */
	List<Map<String, Object>> findNameByCommunityNuber(List<String> communitynumber);
	
	/**
	 * 根据执行方式查询
	 * @param executeMethod
	 * @return
	 */
	List<CommunityModel> findByExecuteMethod(Integer executeMethod);
	
	/**
	 * 通过小区编号和执行方式查询
	 * @param communityNumber
	 * @param executeMethod
	 * @return
	 */
	CommunityModel findByNumberAndExecuteMethod(String communityNumber,Integer executeMethod);
	
	/**
	 * 通过关键子匹配communityName、communityAddress
	 * @param keyword
	 * @return
	 */
	List<CommunityModel> findByKeyWords(String keyword);
	
	/**
	 * 通过经纬度查询符合距离范围的小区
	 * @param lat
	 * @param lng
	 * @param distance
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PageResult<CommunityModel> findByLatAndLng(Double lat, Double lng,String distance, Integer pageSize,Integer  pageNo);
	
	/**
	 * 根据市级编码查找小区
	 * @param cityNo
	 * @return
	 */
	List<CommunityCitynoDto> findByCityNo(String cityNo);

	/**
	 * 通过经纬度查询符合距离范围的开启临时停车的小区
	 * @param isTemporaryParking
	 * @return
	 */
	List<Map<String,Object>> findByIsTemporaryParking(Double lat, Double lng,Integer distance);
	
	/**
	 * 根据是否开通线上缴费查找小区
	 * @param disableTmpParkingFeeAgr
	 * @param disableMonthyParkingFeeAgr
	 * @param disablePropertyFeeAgr
	 * @return
	 */
	List<CommunityModel> findByDisableFeeAgr(Integer disableTmpParkingFeeAgr, Integer disableMonthyParkingFeeAgr, Integer disablePropertyFeeAgr);
	
	/**
	 * 查询所有开启安心点的小区
	 * @return
	 */
	List<CommunityModel> findByIsAxd(Integer isAxd);

}
