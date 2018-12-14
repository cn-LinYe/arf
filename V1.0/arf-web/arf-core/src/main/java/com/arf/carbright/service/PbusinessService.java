package com.arf.carbright.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.arf.base.PageResult;
import com.arf.carbright.condition.BusinessCondition;
import com.arf.carbright.dto.SearchBusinessDto;
import com.arf.carbright.entity.PBusiness;
import com.arf.core.AppMessage;
import com.arf.core.service.BaseService;

public interface PbusinessService extends BaseService<PBusiness, Long> {
	
	/**
	 * 根据经纬度查询范围内的商户
	 * @param lat
	 * @param lng
	 * @param distance
	 * @return
	 */
	public PageResult<Map<String,Object>> findByLatAndLng(Double lat, Double lng,String distance,Integer businessServiceType, Integer pageSize, Integer pageNo);
	/**
	 * 商户登录
	 * @param loginName
	 * @param acPassword
	 * @return
	 */
	public AppMessage bussinessLogin(String loginName,String acPassword,String businessToken,String registrationId,String deviceName);
	
	/**
	 * 商户意见反馈内容
	 * @param loginName
	 * @param acPassword
	 * @param adviceContent
	 * @return
	 */
	public AppMessage bussinessAdvice(String adviceContent,String phone);
	

	/**
	 * 修改密码
	 * @param loginName
	 * @param OldPassWord
	 * @param newPassword
	 * @return
	 */
	public AppMessage modifyPassWord(String loginName,String OldPassWord,String newPassword);

	
	/**
	 * 查询用户
	 * @param loginName
	 * @return
	 */
	public AppMessage findByUserName(String userNmae);


	/**
	 * 查询用户
	 * @param loginName
	 * @return
	 */
	PBusiness findByLoginName(String loginName);

	/**
	 * 发送短信验证码
	 * @param userName 用户名, 保留字段
	 * @param mobile
	 * @param type 验证码类型, 保留字段
	 * @return
	 */
	AppMessage sendSmsVeriCode(String userName,String mobile, Integer type);
	
	/**
	 * 商户忘记密码通过短信验证码来充值 
	 * @param userName
	 * @param newPasswor
	 * @param smsCode
	 * @return
	 */
	AppMessage forgotPassword(String userName,String newPasswor, String smsCode);
	
	String uploadAvatar(String userName,Integer osName,Integer appVersionCode,MultipartFile file) throws Exception;
		
	/**通过商户编号查找商户信息
	 * @param businessNum 商户编号
	 * @return
	 */
	public PBusiness findByBusinessId(int businessNum);
	
	/**通过redis获取商户编号查找商户信息
	 * @param businessNum 商户编号
	 * @return
	 */
	public PBusiness findRedisToDbByBusinessId(int businessNum);
	
	/**通过redis获取商户编号查找商户信息
	 * @param businessId 商户编号
	 * @return
	 */
	public PBusiness findRedisToDbById(Long businessId);
	
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
	 * @param businessServiceType  商户服务类型, 0、预约停车 1、洗车 2、缴纳物业费 3、洗衣 5、保险
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
