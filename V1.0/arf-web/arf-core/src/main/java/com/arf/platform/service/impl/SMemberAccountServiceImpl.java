package com.arf.platform.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CommunityModel;
import com.arf.core.service.SysconfigService;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.util.MD5Util;
import com.arf.member.entity.PointTransferRecord;
import com.arf.member.entity.PointTransferRecord.SubType;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IPointTransferRecordService;
import com.arf.platform.dao.ISMemberAccountDao;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;

@Service("sMemberAccountServiceImpl")
public class SMemberAccountServiceImpl extends BaseServiceImpl<SMemberAccount, Long> implements ISMemberAccountService {

	Logger log = LoggerFactory.getLogger(SMemberAccountServiceImpl.class);
	@Resource(name = "sMemberAccountDaoImpl")
	private ISMemberAccountDao sMemberAccountDaoImpl;
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	@Resource(name = "pointTransferRecordServiceImpl")
	private IPointTransferRecordService pointTransferRecordServiceImpl;
	@Override
	protected BaseDao<SMemberAccount, Long> getBaseDao() {
		return sMemberAccountDaoImpl;
	}

	@Override
	@Deprecated
	public SMemberAccount findByUserName(String userName) {
		return sMemberAccountDaoImpl.findByUserName(userName);
	}

	@Override
	public SMemberAccount finByAccountNumber(String accountNumber) {
		return sMemberAccountDaoImpl.finByAccountNumber(accountNumber);
	}

	@Override
	public String genAccountNumber(String userName) {
		Date now = new Date();
		String dateStr = DateFormatUtils.format(now, "yyMMddHHmmssSSS");
		String md5Str = MD5Util.getMD5(dateStr + userName);
		String words = md5Str.replaceAll("[a-zA-Z]", "");
		if(words.length() >= 7){
			words = words.substring(0, 7);
		}else{
			words = words + RandomStringUtils.randomNumeric(7-words.length());
		}
		String accountNumber = "";
		while(true){
			accountNumber =  words + RandomStringUtils.randomNumeric(3);
			SMemberAccount exist = sMemberAccountDaoImpl.finByAccountNumber(accountNumber);//查库
			if(exist == null){
				break;
			}
		}
		return accountNumber;
	}

	@Override
	public SMemberAccount findByUserNameUserType(String userName, Byte type) {
		return sMemberAccountDaoImpl.findByUserNameUserType(userName,type);
	}

	@Override
	public SMemberAccount findByUserNameUserTypeStatus(String userName,Byte type, Byte status) {
		return sMemberAccountDaoImpl.findByUserNameUserTypeStatus(userName,type,status);
	}

	@Override
	public Integer addUserPointByPayType(String userName,BigDecimal totalFeeYuan,byte type,
			String communityNumber, Integer propretyId, Integer branchId){
		if(userName==null || totalFeeYuan==null){
			log.info(">>>>>用户支付后调用赠送积分【addUserPointByPayType】错误：参数错误userName="+userName+"totalFeeYuan="+totalFeeYuan);
			return -1;
		}
		SMemberAccount sMemberAccount = this.findByUserNameUserTypeStatus(userName, (byte)SMemberAccount.Type.member.ordinal(),
				(byte)SMemberAccount.Status.usable.ordinal());
		if(sMemberAccount==null){
			log.error(">>>>>用户支付后调用赠送积分【addUserPointByPayType】错误：未找到用户账号");
			return -1;
		}
		//根据消费类型获得赠送积分比例
		String pointPercent = "0";
		if(type==SubType.ECC_MONTH_PAY || type==SubType.Temp_Parking){
			pointPercent = sysconfigService.getParentValue(CommunityModel.ECCCONFIG).get(SMemberAccount.COLLECTION_POINT_PERCENT);//代收赠送积分比例
		}
		//else(type == 1){
		//	pointPercent = sysconfigService.getParentValue(CommunityModel.ECCCONFIG).get(SMemberAccount.CONSUME_POINT_PERCENT);//消费赠送积分比例
		//}
		
		BigDecimal percent = BigDecimal.ZERO;
		try{
			percent = new BigDecimal(pointPercent);
		}catch(NumberFormatException e){
			log.error(">>>>>用户支付后调用赠送积分【addUserPointByPayType】错误：赠送积分比例转换类型错误pointPercent: "+pointPercent);
			return -1;
		}
		Integer acquisitionPoint = totalFeeYuan.multiply(percent).intValue();
		if(acquisitionPoint>0){
			Integer point = sMemberAccount.getPoint();
			point = point==null?0:point;
			sMemberAccount.setPoint(point+acquisitionPoint);
			this.update(sMemberAccount);
			
			//积分账户流转记录生成 save
			PointTransferRecord pointTransferRecord = new PointTransferRecord();
			pointTransferRecord.setAccountId(sMemberAccount.getId().toString());
			pointTransferRecord.setAccountNumber(sMemberAccount.getAccountNumber());
			pointTransferRecord.setIdentify(RAccountTransferRecord.genIdentify(sMemberAccount.getAccountNumber()));
			pointTransferRecord.setPointBalance(sMemberAccount.getPoint());
			pointTransferRecord.setPointSub(acquisitionPoint);
			pointTransferRecord.setOperateTime(new Date());
			pointTransferRecord.setOperateType((byte)(PointTransferRecord.OperateType.inter.ordinal()));
			pointTransferRecord.setPointType((byte)PointTransferRecord.PointType.gain.ordinal());
			pointTransferRecord.setStatus((byte)(PointTransferRecord.Status.finished.ordinal()));
			if(type==SubType.ECC_MONTH_PAY){
				pointTransferRecord.setComment("月租续费赠送");
				pointTransferRecord.setSubType((int)SubType.ECC_MONTH_PAY);
			}
			if(type==SubType.Temp_Parking){
				pointTransferRecord.setComment("临时停车赠送");
				pointTransferRecord.setSubType((int)SubType.Temp_Parking);
			}
			pointTransferRecord.setCommunityNumber(communityNumber);
			pointTransferRecord.setPropertyNumber(propretyId);
			pointTransferRecord.setBranchId(branchId);
			pointTransferRecordServiceImpl.save(pointTransferRecord);
		}
		return acquisitionPoint;
	}

}
