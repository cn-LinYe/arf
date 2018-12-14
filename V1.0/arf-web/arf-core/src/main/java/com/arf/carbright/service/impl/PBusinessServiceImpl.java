package com.arf.carbright.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.arf.base.PageResult;
import com.arf.carbright.condition.BusinessCondition;
import com.arf.carbright.dao.BusinessTypeDao;
import com.arf.carbright.dao.PBusinessDao;
import com.arf.carbright.dto.BusinessCachedDto;
import com.arf.carbright.dto.SearchBusinessDto;
import com.arf.carbright.entity.BusinessType;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.service.PbusinessService;
import com.arf.core.AppMessage;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.Member;
import com.arf.core.entity.UserAdviceModel;
import com.arf.core.entity.VerificationModel;
import com.arf.core.file.FileServerService;
import com.arf.core.file.FileStoreService;
import com.arf.core.file.FileType;
import com.arf.core.oldws.PushMessage;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.SmsService;
import com.arf.core.service.UserAdviceModelService;
import com.arf.core.service.VerificationModelService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.thread.ThreadPoolFactory;
import com.arf.core.util.CurrentTime;
import com.arf.core.util.MD5Util;
import com.arf.core.util.Random;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;
import com.arf.redis.CacheNameDefinition;

@Service("pBusinessService")
@Lazy(false)
public class PBusinessServiceImpl extends BaseServiceImpl<PBusiness, Long> implements PbusinessService {
	
	private static Logger log = LoggerFactory.getLogger(PBusinessServiceImpl.class);

	@Resource(name = "pBusinessDao")
	private PBusinessDao pbBusinessDao;

	@Resource(name = "pBusinessService")
	private PbusinessService pbusinessService;

	@Resource(name = "userAdviceModelServiceImpl")
	private UserAdviceModelService serAdviceModelService;
	
	@Resource(name = "businessTypeDao")
	private BusinessTypeDao businessTypeDao; 
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	@Resource(name = "verificationModelServiceImpl")
	private VerificationModelService verificationModelService;
	
	@Resource(name = "fileStoreService")
	private FileStoreService fileStoreService;
	
	@Resource(name="sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "fileServerService")
	private FileServerService fileServerService;
	
	@Override
	protected BaseDao<PBusiness, Long> getBaseDao() {
		return pbBusinessDao;
	}

	@Override
	public AppMessage bussinessLogin(final String loginName, String acPassword,String businessToken,String registrationId,final String deviceName) {
		try {
			AppMessage appMessage = new AppMessage();
			PBusiness pBusiness = pbBusinessDao.findByUserPass(loginName, MD5Util.getMD5(acPassword));
			if (pBusiness == null) {
				appMessage.setCode(10001);
				appMessage.setMsg("用户名或密码错误");
				return appMessage;
			}
			if(pBusiness.getAuditStatus() == PBusiness.AuditStatus.Not_Audit.ordinal()){
				appMessage.setCode(10002);
				appMessage.setMsg("您的商户平台账户还未审核");
				return appMessage;
			}
			if(pBusiness.getAuditStatus() == PBusiness.AuditStatus.Disabled.ordinal()){
				appMessage.setCode(10003);
				appMessage.setMsg("您的商户平台账户已被禁用,请联系客服");
				return appMessage;
			}
			Date now = new Date();
			if((pBusiness.getStartTime() !=null && now.before(pBusiness.getStartTime())) || (pBusiness.getEndTime() != null && now.after(pBusiness.getEndTime()))){
				appMessage.setCode(10004);
				appMessage.setMsg("您的商户平台账户已过有效期,请联系客服");
				return appMessage;
			}
			try {
				String pwd = null;
				//查询username对应电子账户表
				SMemberAccount sMemberAccount = sMemberAccountServiceImpl.findByUserNameUserType(loginName,(byte)Member.Type.memberBip.ordinal());
				//对应账户表不存在，开通一个对应的账户
				if(sMemberAccount == null){
					sMemberAccount = new SMemberAccount();
					sMemberAccount.setAccountNumber(sMemberAccountServiceImpl.genAccountNumber(loginName));
					sMemberAccount.setUserId(Integer.valueOf(pBusiness.getId().toString()));
					sMemberAccount.setUserName(loginName);
					sMemberAccount.setCredit(0);//信用
					sMemberAccount.setBasicAccount(new BigDecimal(0));
					sMemberAccount.setBasicAvaliableAccount(new BigDecimal(0));
					sMemberAccount.setGiftAccount(new BigDecimal(0));
					sMemberAccount.setConsumAmount(new BigDecimal(0));
					sMemberAccount.setPoint(0);//积分
					sMemberAccount.setStatus((byte)SMemberAccount.Status.usable.ordinal());
					sMemberAccount.setActPwd(SMemberAccount.actPwdDefault);//账户明文密码
					pwd = MD5Util.getMD5(loginName.subSequence(4, 9) + MD5Util.getMD5(SMemberAccount.actPwdDefault));
					sMemberAccount.setPwd(pwd);
					sMemberAccount.setType((byte)Member.Type.memberBip.ordinal());
					sMemberAccount = sMemberAccountServiceImpl.save(sMemberAccount);
					log.info(String.format(">>>>>:[商户密码登录接口passwordLogin]电子账户初始化成功: loginName:%s, 电子钱包账户密码pwd:%s", 
							loginName, pwd));
				}
			} catch (Exception e) {
				log.error(String.format(">>>>>:[商户密码登录接口passwordLogin]电子账户初始化失败: loginName:%s",loginName));
				log.error(e.getMessage());
			}
			
			pBusiness.setLastLoginTime(new Date());
			pbusinessService.update(pBusiness);
			redisService.del(String.format(CacheNameDefinition.PBusiness_DB_Redis, pBusiness.getId()));
			redisService.del(String.format(CacheNameDefinition.PBusiness_DB_Redis, pBusiness.getBusinessNum()));
			//校验businessToken(商户token)
			/*if(StringUtils.isBlank(businessToken) || !redisService.exists(PBusiness.Prefix_Cache_Key_Business_Token + businessToken)){
				businessToken = genBusinessToken(pBusiness.getLoginName());
				redisService.set(PBusiness.Prefix_Cache_Key_Business_Token + businessToken, pBusiness, 24 * 60 * 60);
			}*/
			BusinessCachedDto businessCachedDto = redisService.get(BusinessCachedDto.Prefix_Cache_Key_Business_UserName + loginName, 
					BusinessCachedDto.class);
			//将原来的设备强制其下线
			if(businessCachedDto != null){
				final String dtoRegistrationId = businessCachedDto.getRegistrationId(); 
				String dtoDeviceName = businessCachedDto.getDeviceName();
				if(StringUtils.isNotBlank(dtoRegistrationId) 
						&& ((StringUtils.isNotBlank(registrationId) && !dtoRegistrationId.equals(registrationId))
								|| (StringUtils.isNotBlank(deviceName) && StringUtils.isNotBlank(dtoDeviceName) && !deviceName.equals(dtoDeviceName)))){
					ThreadPoolFactory.getInstance().addTask(new Runnable() {
						@Override
						public void run() {
							try {
								PushMessage pushMessage = new PushMessage("系统检测到您的账号在"+(StringUtils.isNotBlank(deviceName)?deviceName:"其他")+"设备上登录成功。"
										+ "如果不是您的操作，账号可能泄露，请及时更改密码。",
										PushMessage.ContentType.MERCHANT_FORCE_LOGOUT,dtoRegistrationId);
								pushMessage.put("currentLoginUser", loginName);
								PushUntils.getMerchantPushUntils().pushCustomMsgSingle(pushMessage, 10L, dtoRegistrationId);
							} catch (Exception e) {
								log.error("商户登录采用registrationId进行强制下线推送发生异常:",e);
							}
						}
					});
				}
			}
			if(StringUtils.isBlank(businessToken) || businessCachedDto==null){
				businessToken = genBusinessToken(pBusiness.getLoginName());
				businessCachedDto = new BusinessCachedDto(loginName,businessToken,new Date(),registrationId);
				businessCachedDto.setDeviceName(deviceName);
				redisService.set(BusinessCachedDto.Prefix_Cache_Key_Business_UserName + loginName, businessCachedDto, 24 * 60 * 60);
			}
			if(StringUtils.isNotBlank(registrationId) && !redisService.exists(BusinessCachedDto.Prefix_Cache_Key_Business_RegistrationId + registrationId)){
				redisService.set(BusinessCachedDto.Prefix_Cache_Key_Business_RegistrationId + registrationId, businessCachedDto, 24 * 60 * 60);
				//更新registrationId
				businessCachedDto.setRegistrationId(registrationId);
				businessCachedDto.setDeviceName(deviceName);
				redisService.set(BusinessCachedDto.Prefix_Cache_Key_Business_UserName + loginName, businessCachedDto, 24 * 60 * 60);
			}
			
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("businessToken", businessToken);
			return AppMessage.success("登录成功",res);
		} catch (Exception e) {
			return AppMessage.serverError("服务器异常");
		}
		
	}
	
	private String genBusinessToken(String loginName){
		return System.currentTimeMillis() / 1000 + loginName + UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public AppMessage bussinessAdvice(String adviceContent, String phone) {
		AppMessage appMessage = new AppMessage();
		try {
			if (adviceContent.getBytes("UTF-8").length < 255) {
				UserAdviceModel userAdviceModel = new UserAdviceModel();
				userAdviceModel.setUser_type(Byte.valueOf("2"));
				userAdviceModel.setStatus(Byte.valueOf("0"));
				userAdviceModel.setPhone(phone);
				userAdviceModel.setUser_advice(adviceContent);
				userAdviceModel.setAdvice_time(CurrentTime.getTime());
				userAdviceModel.setUser_name(phone);
				serAdviceModelService.save(userAdviceModel);
			} else {
				appMessage.setCode(32002);// 字符超范围
				appMessage.setMsg("字符超范围");
				return appMessage;// 字符超范围
			}
		} catch (NumberFormatException e) {
			return AppMessage.serverError("服务器异常");

		} catch (UnsupportedEncodingException e) {
			return AppMessage.serverError("服务器异常");
		}
		return AppMessage.success("反馈成功");
	}

	@Override
	public AppMessage modifyPassWord(String loginName, String OldPassWord, String newPassword) {
		try {
			PBusiness pBusiness = pbBusinessDao.findByUserPass(loginName, MD5Util.getMD5(OldPassWord));
			if (pBusiness == null) {
				return AppMessage.error("原用户名或密码不正确");
			}
			pBusiness.setAcPassword(newPassword);
			pBusiness.setPassword(MD5Util.getMD5(newPassword));
			pbusinessService.update(pBusiness);
			redisService.del(String.format(CacheNameDefinition.PBusiness_DB_Redis, pBusiness.getId()));
			redisService.del(String.format(CacheNameDefinition.PBusiness_DB_Redis, pBusiness.getBusinessNum()));
			try {
				redisService.del(BusinessCachedDto.Prefix_Cache_Key_Business_UserName + loginName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return AppMessage.success("操作成功");
		} catch (Exception e) {
			return AppMessage.serverError("服务器异常");
		}
	}
	
	
	@Override
	public AppMessage forgotPassword(String loginName, String newPassword, String smsCode) {
		try {
			List<VerificationModel> codeList = verificationModelService.checkByPhone(loginName);
			if(CollectionUtils.isEmpty(codeList)){
				return AppMessage.error("没有短信验证码,请先获取验证码");
			}
			VerificationModel verifCode = codeList.get(0);
			long sendTime = verifCode.getCurrentTimes();
			if ((System.currentTimeMillis() - sendTime) > 120000) {
				return AppMessage.error("短信验证码已经失效了,请重新获取");
			}
			if (!verifCode.getVerificationCodes().equalsIgnoreCase(MD5Util.getMD5(smsCode))) {
				return AppMessage.error("短信验证码错误了");
			}
			PBusiness pBusiness = pbBusinessDao.findByUserName(loginName);
			if (pBusiness == null) {
				return AppMessage.error("账户不存在,请确认手机号码后重试");
			}
			
			//验证通过
			pBusiness.setAcPassword(newPassword);
			pBusiness.setPassword(MD5Util.getMD5(newPassword));
			pbusinessService.update(pBusiness);
			redisService.del(String.format(CacheNameDefinition.PBusiness_DB_Redis, pBusiness.getId()));
			redisService.del(String.format(CacheNameDefinition.PBusiness_DB_Redis, pBusiness.getBusinessNum()));
			try {
				redisService.del(BusinessCachedDto.Prefix_Cache_Key_Business_UserName + loginName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return AppMessage.success("操作成功");
		} catch (Exception e) {
			return AppMessage.serverError("服务器异常");
		}
	}

	@Override
	public AppMessage findByUserName(String userName) {
		AppMessage appMessage = new AppMessage();
		Map<String, Object> map = new HashMap<String, Object>();
		PBusiness pbBusiness = pbBusinessDao.findByUserName(userName);
		if (pbBusiness == null) {
			appMessage.setCode(10001);
			appMessage.setMsg("用户不存在");
			return appMessage;
		}
		map.put("businessId", pbBusiness.getId());
		map.put("businessNum", pbBusiness.getBusinessNum());
		map.put("loginName", pbBusiness.getLoginName());
		
		Integer businessTypeId = pbBusiness.getBusinessTypeId();
		map.put("businessTypeId", businessTypeId);
		if(businessTypeId != null){
			BusinessType businessType = businessTypeDao.find(pbBusiness.getBusinessTypeId().longValue());
			if(businessType != null){
				map.put("businessTypeName", businessType.getBusinessTypeName());
			}
		}
		
		map.put("businessName", pbBusiness.getBusinessName());
		map.put("webAddress", pbBusiness.getWebAddress());
		map.put("contactPerson", pbBusiness.getContactPerson());
		map.put("contactPhone", pbBusiness.getContactPhone());
		map.put("mobile", pbBusiness.getMobile());
		map.put("address", pbBusiness.getAddress());
		map.put("auditStatus", pbBusiness.getAuditStatus());
		map.put("createTime", pbBusiness.getCreateTime());
		map.put("lastLoginTime", pbBusiness.getLastLoginTime());
		map.put("remark", pbBusiness.getRemark());
		map.put("startTime", pbBusiness.getStartTime());
		map.put("endTime", pbBusiness.getEndTime());
		map.put("businessPic", pbBusiness.getBusinessPic());
		map.put("branchId", pbBusiness.getBranchId());
		map.put("headOfficeId", pbBusiness.getHeadOfficeId());
		map.put("propretyNumber", pbBusiness.getPropretyNumber());
		//***************************新增返回字段*********************************
		map.put("businessServicePackageNums", pbBusiness.getBusinessServicePackageNums());
		map.put("businessHours", pbBusiness.getBusinessHours());
		map.put("businessDescription", pbBusiness.getBusinessDescription());
		map.put("businessStatus", pbBusiness.getBusinessStatus());
		map.put("businessUsualTime", pbBusiness.getBusinessUsualTime());
		
		appMessage.setExtrDatas(map);
		appMessage.setCode(200);
		appMessage.setMsg("查询成功");
		return appMessage;
	}

	@Override
	public PBusiness findByLoginName(String loginName) {
		return pbBusinessDao.findByUserName(loginName);
	}

	@Override
	public AppMessage sendSmsVeriCode(String userName, String mobile, Integer type) {
		if(StringUtils.isBlank(mobile) || mobile.length() != 11){
			return AppMessage.error("发送短信的手机号码格式不正确");
		}
		List<VerificationModel> list = verificationModelService.checkByPhone(mobile);
		String random = Random.getRandom();
		if (!CollectionUtils.isEmpty(list)) {
			VerificationModel veriCode = list.get(0);
			veriCode.setVerificationCodes(MD5Util.getMD5(random));
			veriCode.setCurrentTimes(new Date().getTime());
			verificationModelService.update(veriCode);
		} else {
			verificationModelService.save(new VerificationModel(new Date().getTime(), MD5Util.getMD5(random), mobile));
		}
		
		smsService.send(mobile, "尾号" + StringUtils.right(mobile, 4) + "的用户您好,您获取到的验证码是" + random);
		return AppMessage.success();
	}

	@Override
	public String uploadAvatar(String userName,Integer osName,Integer appVersionCode, MultipartFile file) throws Exception {
		PBusiness business = this.findByLoginName(userName);
		if(business != null){
			String path = null;
			
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			File tempFile = File.createTempFile(UUID.randomUUID().toString(), "."+extension);
			file.transferTo(tempFile);
			if((osName != null && osName == 0 && appVersionCode > 26)
					||(osName != null && osName == 1 && appVersionCode > 32)){
				path = fileServerService.upload2Server(tempFile, FileType.IMAGE, null, null, null);
			}else{
				//安卓的app版本小于27，ios的app版本小于36需要保持在两个地方
				path = fileStoreService.storeFile(FileType.IMAGE, tempFile);
				String fileName = FilenameUtils.getName(path);
				fileServerService.upload2Server(tempFile, FileType.IMAGE, null, fileName, null);
			}
			
			try{
				if(tempFile != null && tempFile.exists())
					tempFile.delete();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String businessPic =null;// business.getBusinessPic(); spf 注释解决商户图片上传堆叠 2016年8月16日10:15:42
			JSONArray pics = null;
			try{
				pics = JSON.parseArray(businessPic);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(pics == null){
				pics = new JSONArray();
			}
			pics.add(0, path);
			business.setBusinessPic(pics.toJSONString());
			return path;
		}
		return null;
	}

	@Override
	public PBusiness findByBusinessId(int businessNum) {
		return pbBusinessDao.findByBusinessId(businessNum);
	}
	
	@Override
	public PBusiness findByBusinessNumAndType(Integer businessNum,String businessServiceType){
		return pbBusinessDao.findByBusinessNumAndType(businessNum, businessServiceType);
	}
	
	@Override
	public List<PBusiness> findByconidtion(String communityNumber,String businessServiceType){
		return pbBusinessDao.findByconidtion(communityNumber, businessServiceType);
	}

	@Override
	public PageResult<Map<String,Object>> findByLatAndLng(Double lat, Double lng, String distance,Integer businessServiceType, Integer pageSize, Integer pageNo) {
		return pbBusinessDao.findByLatAndLng(lat, lng,distance, businessServiceType,  pageSize,  pageNo);

	}

	@Override
	public PBusiness findRedisToDbByBusinessId(int businessNum) {
		PBusiness model=redisService.hGet(String.format(CacheNameDefinition.PBusiness_DB_Redis, businessNum), String.valueOf(businessNum), PBusiness.class);
		if(model!=null){
			return model;
		}
		model=findByBusinessId(businessNum);
		if(model!=null){
			redisService.hset(String.format(CacheNameDefinition.PBusiness_DB_Redis, businessNum), String.valueOf(businessNum), model);
			redisService.setExpiration(String.format(CacheNameDefinition.PBusiness_DB_Redis, businessNum), CacheNameDefinition.Default_Expiration);
		}
		return model;
	}

	@Override
	public PBusiness findRedisToDbById(Long businessId) {
		PBusiness model=redisService.hGet(String.format(CacheNameDefinition.PBusiness_DB_Redis, businessId), String.valueOf(businessId), PBusiness.class);
		if(model!=null){
			return model;
		}
		model=find(businessId);
		if(model!=null){
			redisService.hset(String.format(CacheNameDefinition.PBusiness_DB_Redis, businessId), String.valueOf(businessId), model);
			redisService.setExpiration(String.format(CacheNameDefinition.PBusiness_DB_Redis, businessId), CacheNameDefinition.Default_Expiration);
		}
		return model;
	}

	@Override
	public List<SearchBusinessDto> searchBusiness(BusinessCondition condition) {
		return pbBusinessDao.searchBusiness(condition);
	}

	@Override
	public List<PBusiness> findByBusiness(List<Integer> business) {
		return pbBusinessDao.findByBusiness(business);
	}
	
}
