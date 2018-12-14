package com.arf.carbright.service.impl;

import java.util.Date;
import java.util.List;

import com.arf.carbright.entity.AxdVouchersRecord;
import com.arf.carbright.entity.AxdVouchersRecord.UsedStatus;
import com.arf.carbright.service.IAxdVouchersRecordService;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.util.MStringUntils;
import com.arf.member.entity.PointTransferRecord;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IPointTransferRecordService;
import com.arf.member.entity.PointTransferRecord.OperateType;
import com.arf.member.entity.PointTransferRecord.PointType;
import com.arf.member.entity.PointTransferRecord.Status;
import com.arf.member.entity.PointTransferRecord.SubType;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;

public class RefundDeductedPointVouchers {

	public void refundDeductedPoint(String userName,int operating,int point,MemberService memberService,ISMemberAccountService sMemberAccountServiceImpl,CommunityModelService communityModelService,IPointTransferRecordService pointTransferRecordServiceImpl){
		refundDeductedPoint(null,userName, operating, point, memberService, sMemberAccountServiceImpl, communityModelService, pointTransferRecordServiceImpl);
	}

	/**操作返还或者扣除积分
	 * @param userName 
	 * @param operating 1、完成 2、冻结 3、失败
	 * @param point
	 * @param memberService
	 * @param sMemberAccountServiceImpl
	 * @param communityModelService
	 * @param pointTransferRecordServiceImpl
	 */
	public void refundDeductedPoint(String common,String userName,int operating,int point,MemberService memberService,ISMemberAccountService sMemberAccountServiceImpl,CommunityModelService communityModelService,IPointTransferRecordService pointTransferRecordServiceImpl){
		if(point>0){//有积分可用进行返还
			//个人账户
			SMemberAccount smemberAccount = sMemberAccountServiceImpl.findByUserNameUserTypeStatus(userName, (byte)SMemberAccount.Type.member.ordinal(),
					(byte)SMemberAccount.Status.usable.ordinal());
			PointTransferRecord transferRecord=new PointTransferRecord();
			if(operating==Status.frozen.ordinal()||operating==Status.finished.ordinal()){//冻结			
				//个人账户 update				
				smemberAccount.setPoint((smemberAccount.getPoint()==null?0:smemberAccount.getPoint()) - point);
				sMemberAccountServiceImpl.update(smemberAccount);
			}else{
				//个人账户 update
				smemberAccount.setPoint((smemberAccount.getPoint()==null?0:smemberAccount.getPoint()) + point);
				sMemberAccountServiceImpl.update(smemberAccount);
			}			
			//积分流转记录
			transferRecord.setPointBalance(smemberAccount.getPoint());//积分余额
			transferRecord.setAccountId(smemberAccount.getId().toString());
			transferRecord.setAccountNumber(smemberAccount.getAccountNumber());
			transferRecord.setIdentify(RAccountTransferRecord.genIdentify(smemberAccount.getAccountNumber()));
			transferRecord.setPointSub(point);//本次流转积分
			transferRecord.setOperateTime(new Date());//操作时间
			transferRecord.setSubType((operating==3)?Integer.valueOf(SubType.Shopping_Voucher):Integer.valueOf(SubType.Shopping_Voucher_Return));
			transferRecord.setOperateType((byte)OperateType.inter.ordinal());//操作方式（1、接口2、人工3、自动）
			transferRecord.setPointType((operating==3)?(byte)PointType.gain.ordinal():(byte)PointType.consume.ordinal());//积分流转类型（1、获取 2、消费）
			transferRecord.setStatus((byte)Status.finished.ordinal());//状态（1、完成 2、冻结 3、失败）
			if (common==null) {
				transferRecord.setComment("购买点滴洗套餐".concat((operating==3)?"返还":"扣除"));//操作说明	
			}else{
				transferRecord.setComment(common.concat((operating==3)?"返还":"扣除"));//操作说明
			}
			
			
			Member member = memberService.findByUsername(userName);
			Integer propertyNumber = null;
	    	Integer branchId = null;
	    	if(MStringUntils.isNotEmptyOrNull(member.getCommunityNumber())){
		    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(member.getCommunityNumber());
		    	if(communityList != null && communityList.size()>0){
		    		propertyNumber = communityList.get(0).getProperty_number();
		    		branchId = communityList.get(0).getBranchId();
		    	}
	    	}
	    	transferRecord.setCommunityNumber(member.getCommunityNumber());//小区编号
	    	transferRecord.setPropertyNumber(propertyNumber);//物业编号 
	    	transferRecord.setBranchId(branchId);//子公司编号 
	    	pointTransferRecordServiceImpl.save(transferRecord);
		}		
	}
	
	public void refundDeductedVouchers(String userName,String vouchersNums,int operating,IAxdVouchersRecordService axdVouchersRecordServiceImpl){
		if(vouchersNums!=null){
			String[] vouchersNum=vouchersNums.split(",");
			for (String vouchers : vouchersNum) {
				UsedStatus usedStatus=(operating==1||operating==2)?UsedStatus.Available:UsedStatus.Finished;
				AxdVouchersRecord axdVouchersRecord= axdVouchersRecordServiceImpl.findUsedDishVouchersByNum(userName,vouchers,usedStatus);
				if(axdVouchersRecord!=null){
					if(operating==1||operating==2){//将代金券修改为不可用
						axdVouchersRecord.setUsedStatus(UsedStatus.Finished.ordinal());
					}else{//可用
						axdVouchersRecord.setUsedStatus(UsedStatus.Available.ordinal());
					}
					axdVouchersRecordServiceImpl.update(axdVouchersRecord);
				}				
			}
		}		
	}
}
