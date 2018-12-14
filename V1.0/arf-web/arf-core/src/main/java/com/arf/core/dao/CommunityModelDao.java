package com.arf.core.dao;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dto.CommunityCitynoDto;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.CommunityModel.AuthorizationMode;
import com.arf.core.entity.CommunityModel.IsTemporaryParking;

/**
 * Dao - 小区表
 * 
 * @author arf
 * @version 4.0
 */
public interface CommunityModelDao extends BaseDao<CommunityModel, Long>{
	/**
	 * 获取小区
	 * @param no
	 * 		编码
	 * @return
	 */
	List<CommunityModel> CheckByNo(String no);
	/**
	 * 根据ID查找小区
	 * @param community_number
	 * @return
	 */
	List<CommunityModel> checkByCommunity_number(String community_number);//通过小区id查找小区
	
	/**
	 * 通过物业id查看小区
	 * @param propretyId
	 * @return
	 */
	List<CommunityModel> checkByPropretyNumber(long propretyId);
	/**
	 * 查询开通发送短信的小区
	 * @return
	 */
	List<CommunityModel> selectBySendMessages();
	
	public String getPrincipalMobile(String community_number);
	
	/**
	 * 通过小区名称查找小区
	 * @param communityName
	 * @return
	 */
	List<CommunityModel> findByCommunityName(String communityName);
	
	/**
	 * 通过小区名称查找小区名称
	 * @param communityNumber
	 * @return
	 */
	List<Map<String, Object>> findNameByCommunityNuber(List<String> communitynumber);
	
	/**
	 * 通过小区编号组查找小区
	 * @param communityNumbers
	 * @return
	 */
	List<CommunityModel> findByCommunityNumbers(List<String> communityNumbers);
	
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
	PageResult<CommunityModel> findByLatAndLng(Double lat, Double lng,String distance, Integer pageSize, Integer pageNo);
	
	/**
	 * 根据市级编码查找小区
	 * @param cityNo
	 * @return
	 */
	List<CommunityCitynoDto> findByCityNo(String cityNo);
	
	/**
	 * 通过授权模式查找
	 * @param communityName
	 * @return
	 */
	List<Map<String,Object>> findByAuthorizationMode(AuthorizationMode freeComeAndOut);
	
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
