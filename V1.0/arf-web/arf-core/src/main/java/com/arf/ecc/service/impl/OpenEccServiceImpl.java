package com.arf.ecc.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.axd.entity.EccPackageConfig;
import com.arf.axd.service.IEccPackageConfigService;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.MemberDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.entity.VipRecordInformationModel;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.service.ParkRateModelService;
import com.arf.core.service.VipRecordInformationModelService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.ecc.entity.SettlementAccount;
import com.arf.ecc.entity.SettlementParam;
import com.arf.ecc.entity.SettlementRecord;
import com.arf.ecc.service.IOpenEccService;
import com.arf.ecc.service.SettlementAccountService;
import com.arf.ecc.service.SettlementParamService;
import com.arf.ecc.service.SettlementRecordService;
import com.arf.goldcard.entity.GoldCardType;
import com.arf.goldcard.service.IGoldCardTypeService;
import com.arf.redis.CacheNameDefinition;

@Repository("openEccServiceImpl")
public class OpenEccServiceImpl extends BaseServiceImpl<Member, Long> implements IOpenEccService {

	@Resource(name="memberDaoImpl")
	MemberDao memberDao;
	
	@Resource(name="memberServiceImpl")
	private MemberService memberServiceImpl;
	
	@Resource(name="settlementParamServiceImpl")
	private SettlementParamService settlementParamService;
	
	@Resource(name="settlementAccountServiceImpl")
	private SettlementAccountService settlementAccountService;
	
	@Resource(name="settlementRecordServiceImpl")
	private SettlementRecordService settlementRecordService;
	
	@Resource(name = "eccPackageConfigServiceImpl")
	private IEccPackageConfigService eccPackageConfigService;
	
	@Resource(name = "vipRecordInformationModelServiceImpl")
	private VipRecordInformationModelService vipRecordInformationModelService;
	
	@Resource(name = "goldCardTypeService")
	private IGoldCardTypeService goldCardTypeService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "parkRateModelServiceImpl")
	private ParkRateModelService parkRateModelService;
	
	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Override
	protected BaseDao<Member, Long> getBaseDao() {
		return memberDao;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Map<String,Object> openEcc(String userName, SettlementParam.AssignType assignType,String outTradeNo,String typeNum,String licensePlate) {
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			//参数校验
			if(StringUtils.isEmpty(userName)){
				result.put("result_code", "fail");
				result.put("result_msg", "参数错误");
				return result;
			}
			//判断并设置用户为VIP，如已开通则直接返回
			Member member = memberServiceImpl.findByUsername(userName);
			if(member == null){
				result.put("result_code", "fail");
				result.put("result_msg", "无法找到用户信息");
				return result;
			}
			if(member.getVip()==Member.Vip.vip.ordinal()){
				result.put("result_code", "fail");
				result.put("result_msg", "用户已开通ecc");
				return result;
			}
			
			//如需与子公司物业进行结算，处理结算逻辑（开通ecc是否需要结算，根据 结算参数表 settlement_param 是否有改小区的配置而定，有相关配置，则进行结算）
			String communityNumber = member.getCommunityNumber();
			if(assignType == SettlementParam.AssignType.upload_parkrate && StringUtils.isNotEmpty(licensePlate)){
				//如果是上传白名单途径进入，则结算小区为白名单中的小区
				List<ParkRateModel> parkRateLicense = parkRateModelService.selectListByLicensePlate(licensePlate);
				if (!CollectionUtils.isEmpty(parkRateLicense)) {
					communityNumber = parkRateLicense.get(0).getCommunityNumber();
				}
			}
			SettlementParam settlementParam = settlementParamService.findByNumber(communityNumber,assignType);
			if(settlementParam != null){
				//根据结算小区得到结算物业及结算子公司
				Integer propertyNumber ;
				Integer branchId;
				try{
					CommunityModel community = communityModelService.findByNumber(communityNumber);
					if(community!=null){
						propertyNumber = community.getProperty_number();
						branchId = community.getBranchId();
					
						//根据开通ecc类型，获取分配比例
						BigDecimal propertyAmount = BigDecimal.ZERO;
						BigDecimal branchAmoun = BigDecimal.ZERO;
						//计算分配金额并写入各表
						if(SettlementParam.AssignMode.fixed.ordinal() == settlementParam.getAssignMode()){
							//按固定金额分配
							propertyAmount = settlementParam.getEccFixedAmountProperty();
							branchAmoun = settlementParam.getEccFixedAmountBranch();
						}else{
							//按比例分配
							BigDecimal totalAmout = BigDecimal.ZERO;
							switch(assignType){
								case upload_parkrate:
									//上传白名单只按固定金额分配
									break;
								case package_ecc:
								case package_exchange:
								case package_goldcard:
									//1.根据订单号找支付记录表vip_record_information（支付纪录表）
									VipRecordInformationModel vipRecordInfor = vipRecordInformationModelService.selectByouttradeno(outTradeNo);
									if(vipRecordInfor != null){
										//获得开通ecc选择的套餐集合并将套餐赠送给用户
										EccPackageConfig eccPakageConfig = eccPackageConfigService.find(vipRecordInfor.getEccPackageConfigId());
										totalAmout = eccPakageConfig.getProfitProrataAmount()==null ? BigDecimal.ZERO : eccPakageConfig.getProfitProrataAmount();
									}else{
										logger.info(">>>>>:开通ecc结算错误 openEcc -------------->根据订单号获取用户购买套餐记录失败,outTradeNo:%s", outTradeNo);
									}
									break;
								case goldcard_buy:
								case goldcard_exchange:
									GoldCardType goldCardType = goldCardTypeService.findByTypeNum(typeNum);
									if(goldCardType != null){
										totalAmout = goldCardType.getProfitProrataAmount()==null ? BigDecimal.ZERO : goldCardType.getProfitProrataAmount();
									}else{
										logger.info(">>>>>:开通ecc结算错误 openEcc -------------->根据金卡类型获取金卡记录失败,typeNum:%s", typeNum);
									}
									break;
								default:
									break;
							}
							propertyAmount = totalAmout.multiply(new BigDecimal(settlementParam.getEccProrataPercentProperty().toString())).divide(BigDecimal.TEN.multiply(BigDecimal.TEN));
							branchAmoun = totalAmout.multiply(new BigDecimal(settlementParam.getEccProrataPercentBranch().toString())).divide(BigDecimal.TEN.multiply(BigDecimal.TEN));
						}
						if(propertyAmount.compareTo(BigDecimal.ZERO)==1){
							//分别更新物业和子公司的待结转金额
							SettlementAccount propertyAccount = settlementAccountService.findByNumber(propertyNumber.toString(), SettlementAccount.Type.property);
							if(propertyAccount==null){
								//账户为空，生成结算账户
								propertyAccount = new SettlementAccount();
								propertyAccount.setNumber(propertyNumber.toString());
								propertyAccount.setType(SettlementAccount.Type.property.ordinal());
								propertyAccount.setStatus(SettlementAccount.Status.on.ordinal());
								propertyAccount.setBalanceAmount(BigDecimal.ZERO);
								propertyAccount.setSettlingAmount(propertyAmount);
								propertyAccount.setSettledAmount(BigDecimal.ZERO);
								settlementAccountService.save(propertyAccount);
							}else{
								propertyAccount.setSettlingAmount(propertyAccount.getSettlingAmount()==null ? propertyAmount : propertyAccount.getSettlingAmount().add(propertyAmount));
								settlementAccountService.update(propertyAccount);
							}
								//生成结算记录
							SettlementRecord propertyRecord = new SettlementRecord();
							propertyRecord.setNumber(propertyNumber.toString());
							propertyRecord.setType(SettlementRecord.Type.property.ordinal());
							propertyRecord.setStatus(SettlementRecord.Status.finish.ordinal());
							propertyRecord.setSettlementAmount(propertyAmount);
							propertyRecord.setSettlementType(SettlementRecord.SettlementType.open_ecc.ordinal());
							propertyRecord.setUserName(userName);
							propertyRecord.setCommunityNumber(communityNumber);
							settlementRecordService.save(propertyRecord);
						}
						
						if(branchAmoun.compareTo(BigDecimal.ZERO) == 1){
							SettlementAccount branchAccount = settlementAccountService.findByNumber(branchId.toString(), SettlementAccount.Type.branch);
							if(branchAccount==null){
								branchAccount = new SettlementAccount();
								branchAccount.setNumber(branchId.toString());
								branchAccount.setType(SettlementAccount.Type.branch.ordinal());
								branchAccount.setStatus(SettlementAccount.Status.on.ordinal());
								branchAccount.setBalanceAmount(BigDecimal.ZERO);
								branchAccount.setSettlingAmount(branchAmoun);
								branchAccount.setSettledAmount(BigDecimal.ZERO);
								settlementAccountService.save(branchAccount);
							}else{
								branchAccount.setSettlingAmount(branchAccount.getSettlingAmount()==null ? branchAmoun : branchAccount.getSettlingAmount().add(branchAmoun));
								settlementAccountService.update(branchAccount);
							}
								//生成结算记录
							SettlementRecord branchRecord = new SettlementRecord();
							branchRecord.setNumber(branchId.toString());
							branchRecord.setType(SettlementRecord.Type.branch.ordinal());
							branchRecord.setStatus(SettlementRecord.Status.finish.ordinal());
							branchRecord.setSettlementAmount(branchAmoun);
							branchRecord.setSettlementType(SettlementRecord.SettlementType.open_ecc.ordinal());
							branchRecord.setUserName(userName);
							branchRecord.setCommunityNumber(communityNumber);
							settlementRecordService.save(branchRecord);
						}
				
					}else{
						logger.info(">>>>>:开通ecc结算错误 openEcc -------------->无法根据小区编号找到小区信息,communityNumber:%s", communityNumber);
					}
				}catch(NonUniqueResultException e){
					logger.info(e.getMessage());
				}
			}
			
			if(assignType == SettlementParam.AssignType.upload_parkrate){
				member.setActivation(1);
				member.setRegisterWay(1);
				member.setSpLicNum(licensePlate);
//				member.setRegisterWay(1);
			}
			member.setUserlevel(1);
			member.setVip(Member.Vip.vip.ordinal());
			member.setVipTime(new Date());
			memberServiceImpl.update(member);
			redisService.hdel(String.format(CacheNameDefinition.Member_DB_Redis,member.getUsername()), member.getUsername());		
			result.put("result_code", "success");
			result.put("result_msg", "success");
			return result;
		} catch (Exception e) {
			logger.info("开通ECC结算出现异常",e);
			redisService.set(EXCEPTION_LOG_KEY + userName + "_" + System.currentTimeMillis() / 1000, "userName="+userName+",assignType="+assignType+",outTradeNo="+outTradeNo+",typeNum="+typeNum+",licensePlate="+licensePlate);
			
			result.put("result_code", "fail");
			result.put("result_msg", "异常");
			return result;
		}
	}
}
