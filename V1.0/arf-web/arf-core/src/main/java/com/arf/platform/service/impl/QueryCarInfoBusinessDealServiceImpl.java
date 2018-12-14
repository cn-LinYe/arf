package com.arf.platform.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arf.core.entity.LicensePlateModel;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.ParkRateModelService;
import com.arf.eparking.entity.EscapeRecord;
import com.arf.eparking.service.EscapeRecordService;
import com.arf.goldcard.dto.UserGoldCardAccountDto;
import com.arf.goldcard.service.IUserGoldCardAccountService;
import com.arf.platform.LPConstants;
import com.arf.platform.dto.AccountDto;
import com.arf.platform.dto.EscapeDto;
import com.arf.platform.dto.EscapeRecordDto;
import com.arf.platform.entity.SExceptionCarInfo;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.BaseService;
import com.arf.platform.service.IParkingUpReportBusinessDealService;
import com.arf.platform.service.ISExceptionCarInfoService;
import com.arf.platform.service.ISMemberAccountService;
import com.arf.platform.vo.QueryCarInfoVo;
import com.arf.platform.vo.RequestDataVo;

@Transactional
@Service("queryCarInfoBusinessDealServiceImpl")
public class QueryCarInfoBusinessDealServiceImpl implements IParkingUpReportBusinessDealService {
	
	private static Logger log = LoggerFactory.getLogger(QueryCarInfoBusinessDealServiceImpl.class);

	@Resource(name = "sMemberAccountServiceImpl")
	private ISMemberAccountService sMemberAccountServiceImpl;
	
	@Resource(name = "sExceptionCarInfoServiceImpl")
	private ISExceptionCarInfoService sExceptionCarInfoServiceImpl;
	
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateModelServiceImpl;
	
	@Resource(name = "escapeRecordServiceImpl")
	private EscapeRecordService escapeRecordServiceImpl;
	
	@Resource(name = "parkRateModelServiceImpl")
	private ParkRateModelService parkRateModelServiceImpl;
	
	@Resource(name = "userGoldCardAccountServiceImpl")
	private IUserGoldCardAccountService userGoldCardAccountService;
	
	@Override
	public Map<String,Object> process(String version, String communityNo, RequestDataVo vo) {

		Map<String,Object> map = null;
		
		int ver = Integer.parseInt(version.trim());
		
		//判断版本号
		if(ver == 1){
			//判断停车场类型
			if(vo.getParkingType() == BaseService.PARKING_TYPE_COMMUNITY){
				map = processVersion1((QueryCarInfoVo)vo);
			}else if(vo.getParkingType() == BaseService.PARKING_TYPE_EMERGENCY){
				map = processVersion1Emergency((QueryCarInfoVo)vo);
			}
			if(map != null){
				return map;
			}
		}
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS,LPConstants.RETURNCODE_PARAM_ERROR );
		map.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
		map.put(LPConstants.RETURNKEY_CAR_LICENSE, ((QueryCarInfoVo)vo).getLicense());
		map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
		map.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
		map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
		return map;
		
	}

	/**
	 * 版本1的处理方式(小区)
	 * @param vo
	 * @return
	 */
	private Map<String,Object> processVersion1(QueryCarInfoVo vo) {
		
		Map<String,Object> map = null;
		//1、取出业务数据
		String license = vo.getLicense();//车牌
		Integer queryType = vo.getQueryType();//要查询的信息 0所有信息；1可结算帐户信息　2逃欠费信息、3是否违章信息
		String communityNo = vo.getCommunityNo();
		
		//判断此车是否是会员车
		String userName = null;//会员名称
		boolean hasUserName = false; 
		LicensePlateModel licensePlateModel = null;//车牌表
		List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.CheckLicensePlate(license);
		if(CollectionUtils.isEmpty(listLicensePlateModel)){//车牌表license_plate 为空
			log.info(">>>>>:[车辆信息查询processVersion1]此车牌 \"" + license + "\" 未在平台绑定!");
		}else{//车牌表license_plate 不为空
			licensePlateModel = listLicensePlateModel.get(0);
			userName = licensePlateModel.getUser_name();
			if("nullUser".equals(userName.trim())){//已解绑
				log.info(">>>>>:[车辆信息查询processVersion1]此车牌 \"" + license + "\" 未在平台绑定!");
			}else{
				hasUserName = true;
			}
		}
		if(!hasUserName){
			map = new HashMap<String,Object>();
			map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_NO_ACCOUNT);
			map.put(LPConstants.RESULTMAP_KEY_MSG, "license_no_account!");
			map.put(LPConstants.RETURNKEY_CAR_LICENSE, vo.getLicense());
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
			return map;
		}
		
		//account: 帐户信息
		AccountDto account = null;
		account = findAccount(userName,license,communityNo);
		
		//escape:逃欠费记录
		EscapeDto escape =  new EscapeDto();
		//查看有没有未追缴的逃欠费记录
		List<EscapeRecord> escapeRecordList = escapeRecordServiceImpl.findByUserNameAndRecoveryStatus(userName,EscapeRecord.RecoveryStatus.no_recovery);
		if(CollectionUtils.isNotEmpty(escapeRecordList)){
			escape.setTotalCount(escapeRecordList.size()+"");
			int totalFee = 0;
			double total = 0;
			for (EscapeRecord escapeRecord : escapeRecordList) {
				total = total + escapeRecord.getEscapeFee().doubleValue();
			}
			totalFee = (int) (total * 100) ;
			escape.setTotalFee(totalFee);
			List<EscapeRecordDto> listRecordDto = new ArrayList<EscapeRecordDto>();
			for(EscapeRecord escapeRecord : escapeRecordList){
				EscapeRecordDto recordDto = new EscapeRecordDto();
				recordDto.setType(escapeRecord.getFlag()==EscapeRecord.Flag.escape.ordinal()?1:0);
				recordDto.setRecordId(escapeRecord.getId().toString());
				recordDto.setFee(escapeRecord.getEscapeFee().multiply(new BigDecimal(100)).intValue());
				listRecordDto.add(recordDto);
			}
			escape.setRecords(listRecordDto);
		}else{
			escape.setTotalFee(0);
			escape.setTotalCount(0 + "");
			escape.setRecords(null);
		}
		
		//exception:异常车辆类型
		Map<String,Object> exception = null;
		Byte exceptionType = null;
		SExceptionCarInfo sExceptionCarInfo = findExceptionCarInfo(license);
		if(sExceptionCarInfo!= null){
			exceptionType = sExceptionCarInfo.getType();
			exception = new HashMap<String,Object>();
			exception.put("exceptionType", exceptionType);
		}
		
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		map.put(LPConstants.RETURNKEY_CAR_LICENSE, vo.getLicense());
		if(queryType == 0){//所有信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, account);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, escape);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, exception);
		}else if(queryType == 1){//1可结算帐户信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, account);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
		}else if(queryType == 2){//2逃欠费信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, escape);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
		}else if(queryType == 3){//3是否违章信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, exception);
		}
		return map;
	}
	
	/**
	 * 版本1的处理方式(紧急场所)
	 * @param vo
	 * @return
	 */
	private Map<String,Object> processVersion1Emergency(QueryCarInfoVo vo) {
		Map<String,Object> map = null;
		//1、取出业务数据
		String license = vo.getLicense();//车牌
		Integer queryType = vo.getQueryType();//要查询的信息 0所有信息；1可结算帐户信息　2逃欠费信息、3是否违章信息
		String communityNo = vo.getCommunityNo();
		
		//判断此车是否是会员车
		String userName = null;//会员名称
		boolean hasUserName = false; 
		LicensePlateModel licensePlateModel = null;//车牌表
		List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.CheckLicensePlate(license);
		if(CollectionUtils.isEmpty(listLicensePlateModel)){//车牌表license_plate 为空
			log.info(">>>>>:[车辆信息查询processVersion1Emergency]此车牌 \"" + license + "\" 未在平台绑定!");
		}else{//车牌表license_plate 不为空
			licensePlateModel = listLicensePlateModel.get(0);
			userName = licensePlateModel.getUser_name();
			if("nullUser".equals(userName.trim())){//已解绑
				log.info(">>>>>:[车辆信息查询processVersion1Emergency]此车牌 \"" + license + "\" 未在平台绑定!");
			}else{
				hasUserName = true;
			}
		}
		if(!hasUserName){
			map = new HashMap<String,Object>();
			map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNKEY_NO_ACCOUNT);
			map.put(LPConstants.RESULTMAP_KEY_MSG, "license_no_account!");
			map.put(LPConstants.RETURNKEY_CAR_LICENSE, vo.getLicense());
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
			return map;
		}
		
		//account: 帐户信息
		AccountDto account = null;
		account = findAccount(userName,license,communityNo);
		 
		//escape:逃欠费记录
		EscapeDto escape =  new EscapeDto();;
		//查看有没有未追缴的逃欠费记录
		List<EscapeRecord> escapeRecordList = escapeRecordServiceImpl.findByUserNameAndRecoveryStatus(userName,EscapeRecord.RecoveryStatus.no_recovery);
		if(CollectionUtils.isNotEmpty(escapeRecordList)){
			escape.setTotalCount(escapeRecordList.size()+"");
			int totalFee = 0;
			double total = 0;
			for (EscapeRecord escapeRecord : escapeRecordList) {
				total = total + escapeRecord.getEscapeFee().doubleValue();
				break;
			}
			totalFee = (int) (total * 100) ;
			escape.setTotalFee(totalFee);
			List<EscapeRecordDto> listRecordDto = new ArrayList<EscapeRecordDto>();
			for(EscapeRecord escapeRecord : escapeRecordList){
				EscapeRecordDto recordDto = new EscapeRecordDto();
				recordDto.setType(escapeRecord.getFlag()==EscapeRecord.Flag.escape.ordinal()?1:0);
				recordDto.setRecordId(escapeRecord.getId().toString());
				recordDto.setFee(escapeRecord.getEscapeFee().multiply(new BigDecimal(100)).intValue());
				listRecordDto.add(recordDto);
				break;
			}
			escape.setRecords(listRecordDto);
		}else{
			escape.setTotalFee(0);
			escape.setTotalCount(0 + "");
			escape.setRecords(null);
		}
		
		//exception:异常车辆类型
		Map<String,Object> exception = null;
		Byte exceptionType = null;
		SExceptionCarInfo sExceptionCarInfo = findExceptionCarInfo(license);
		if(sExceptionCarInfo!= null){
			exceptionType = sExceptionCarInfo.getType();
			exception = new HashMap<String,Object>();
			exception.put("exceptionType", exceptionType);
		}
		
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		map.put(LPConstants.RETURNKEY_CAR_LICENSE, vo.getLicense());
		if(queryType == 0){//所有信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, account);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, escape);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, exception);
		}else if(queryType == 1){//1可结算帐户信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, account);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
		}else if(queryType == 2){//2逃欠费信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, escape);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, null);
		}else if(queryType == 3){//3是否违章信息
			map.put(LPConstants.RETURNKEY_CAR_ACCOUNT, null);
			map.put(LPConstants.RETURNKEY_CAR_ESCAPE, null);
			map.put(LPConstants.RETURNKEY_CAR_EXCEPTION, exception);
		}
		return map;
	}
	
	/**
	 * account: 帐户信息
	 * @param userName
	 * @return
	 */
	private AccountDto findAccount(String userName,String licensePlate,String communityNo){
		AccountDto account = null;
		SMemberAccount sMemberAccount = null;
		sMemberAccount = sMemberAccountServiceImpl.findByUserNameUserType(userName,(byte)SMemberAccount.Type.member.ordinal());
		if(sMemberAccount != null){
			BigDecimal basicAccount = sMemberAccount.getBasicAccount();//基本账户
			if(basicAccount != null){//电子账户余额：小于0直接返回，大于等于0的加上停车卡的余额
				if(basicAccount.compareTo(new BigDecimal(0)) == -1){
					basicAccount = basicAccount.multiply(new BigDecimal(100));
				}else{
					UserGoldCardAccountDto dto = userGoldCardAccountService.findByUserName(userName);
					if(dto != null){
						BigDecimal balanceGoldCard = dto.getBalance();
						if(balanceGoldCard != null){
							basicAccount = basicAccount.add(balanceGoldCard);
						}
					}
					basicAccount = basicAccount.multiply(new BigDecimal(100));
				}
			}else{
				basicAccount = new BigDecimal(0);
			}
			account = new AccountDto();
			account.setRemain(basicAccount.intValue());
			ParkRateModel parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(communityNo, licensePlate);
			if(parkRateModel == null){
				account.setType("0");//帐户类型0电子钱包帐户、1内部月卡车用户
			}else{
				Date endTime = parkRateModel.getEndTime();
				if(endTime == null){
					account.setType("0");//帐户类型0电子钱包帐户、1内部月卡车用户
				}else{
					if(new Date().getTime() > endTime.getTime()){//月租车过期
						account.setType("0");//帐户类型0电子钱包帐户、1内部月卡车用户
					}else{
						account.setType("1");//帐户类型0电子钱包帐户、1内部月卡车用户
					}
				}
			}
		}
		return account;
	}
	
	/**
	 * exception:异常车辆类型
	 * @param license
	 * @return
	 */
	private SExceptionCarInfo findExceptionCarInfo(String license){
		SExceptionCarInfo sExceptionCarInfo = null;
		List<SExceptionCarInfo> listSExceptionCarInfo = sExceptionCarInfoServiceImpl.findByLicense(license);
		if(listSExceptionCarInfo!=null&&listSExceptionCarInfo.size()!=0){
			sExceptionCarInfo = listSExceptionCarInfo.get(0);
		}
		return sExceptionCarInfo;
	}

}
