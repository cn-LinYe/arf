package com.arf.insurance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_insurance_order_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_insurance_order_record_sequence")
public class InsuranceOrderRecord extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
	
		private String  userName;            /*'车主帐号',*/
		private String   phone;                /*'车主联系电话',*/
		private String   fullName;            /*'车主姓名',*/
		private BigDecimal   fee ;               /* '支付费用',*/
		private BigDecimal    rebateFee;          /*'返点费用',*/
		private String   feeDetails;          /* '费用详情（描述）',*/
		private String   orderNo;            /* '订单编号',*/
		private String   outOrderNo;        /* 支付订单编号',*/
		private Byte   status;               /*'订单状态：0未确认，1已确认，2已取消3服务中 4商户确认完成 5 用户确认完成 6用户已评论；',*/
		private Byte   feePayStatus;       /*'支付状态0;未支付1;已支付 2;支付失败',*/
		private Byte   feePayType;         /*'支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付',*/
		private Date   buyingDate;          /* '购买日期',*/
		private String   insuranceType;      /* 保险ID(外键)',*/
	    private Integer   insuranceCompanyId; /*'保险公司名称ID（外键）',*/
	    private Date    insuranceStartDate; /* '保险起始日期',*/
	    private Date    insuranceEndDate;   /*'保险截止日期',*/
	    private Date    orderCancelDate;    /*'订单取消日期',*/
	    private String   cancelRemark;       /*  '订单取消备注',*/
	    private String   communityNumber;    /* '小区编号',*/
	    private Integer  businessNum;       /* '商户编号',*/
	    private String   sharePeople;       /*'分享人帐号',*/
	    private BigDecimal   shareFee;           /* '分享人返现',*/
	    private String  drivingLicensePic;  /*（图片）',*/
	    private String license  ;            /* '车牌号码',*/
	    private String oldInsurancePic;    /* '老保险单(照片)',*/
	    private String idCardPic;         /* '身份证照片(正反面及手持照片)',*/
		private Integer  branchId ;          /* '子公司名称',*/
		private Integer  popertyNumber;       /* '物业信息',*/
		/**
		 * 2016.11.7新增字段
		 */
		private String vouchersId;//代金券id	
		private String vouchersName;//代金券名称
		private BigDecimal amount ;//抵扣金额
		private Byte isReturnVouchers;// 是否需要返回代金券0需要（代表微信或者支付宝支付更改状态）1不需要 （支付完成）
		private Date returnDate;//返还日期
		
		
		public enum IsReturnVouchers{
			NEED,NOTNEED
		}
		
		public enum Status{
			NotConfirmed,Confirmed,Canceled,Service
			,MerchantConfirmation,UserConfirmation,UserComment
		}
		public enum FeePayStatus{
			NotPay,Paid,failPay
		}
		public enum FeePayType{			
			AgentPay,//支付方式 0;待支付
			Weixin,//，1微信支付
			Alipay,//，2支付宝支付
			BankCard,//，3 银行卡 
			Cash,//4、现金 
			ElectronicWallet,//5、钱包支付 
			PackagePay//6、套餐支付
			;		
			public static FeePayType get(int ordinal) {
				FeePayType statuss[] = FeePayType.values();
				if (ordinal > statuss.length - 1) {
					return null;
				} else {
					return statuss[ordinal];
				}
			}
		}
		
		
		public String getVouchersId() {
			return vouchersId;
		}
		public void setVouchersId(String vouchersId) {
			this.vouchersId = vouchersId;
		}
		
		@Column(length=40)
		public String getVouchersName() {
			return vouchersName;
		}
		public void setVouchersName(String vouchersName) {
			this.vouchersName = vouchersName;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public Byte getIsReturnVouchers() {
			return isReturnVouchers;
		}
		public void setIsReturnVouchers(Byte isReturnVouchers) {
			this.isReturnVouchers = isReturnVouchers;
		}
		public Date getReturnDate() {
			return returnDate;
		}
		public void setReturnDate(Date returnDate) {
			this.returnDate = returnDate;
		}
		
		
		@Column(length = 40)
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		@Column(length = 40)
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		@Column(length = 40)
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		@Column(precision=10,scale=2)
		public BigDecimal getFee() {
			return fee;
		}
		public void setFee(BigDecimal fee) {
			this.fee = fee;
		}
		@Column(precision=10,scale=2)
		public BigDecimal getRebateFee() {
			return rebateFee;
		}
		public void setRebateFee(BigDecimal rebateFee) {
			this.rebateFee = rebateFee;
		}
		@Column(length = 100)
		public String getFeeDetails() {
			return feeDetails;
		}
		public void setFeeDetails(String feeDetails) {
			this.feeDetails = feeDetails;
		}
		@Column(length = 40)
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		@Column(length = 40)
		public String getOutOrderNo() {
			return outOrderNo;
		}
		public void setOutOrderNo(String outOrderNo) {
			this.outOrderNo = outOrderNo;
		}
		public Byte getStatus() {
			return status;
		}
		public void setStatus(Byte status) {
			this.status = status;
		}
		public Byte getFeePayStatus() {
			return feePayStatus;
		}
		public void setFeePayStatus(Byte feePayStatus) {
			this.feePayStatus = feePayStatus;
		}
		public Byte getFeePayType() {
			return feePayType;
		}
		public void setFeePayType(Byte feePayType) {
			this.feePayType = feePayType;
		}
		public Date getBuyingDate() {
			return buyingDate;
		}
		public void setBuyingDate(Date buyingDate) {
			this.buyingDate = buyingDate;
		}
		@Column(length = 100)
		public String getInsuranceType() {
			return insuranceType;
		}
		public void setInsuranceType(String insuranceType) {
			this.insuranceType = insuranceType;
		}
		public Integer getInsuranceCompanyId() {
			return insuranceCompanyId;
		}
		public void setInsuranceCompanyId(Integer insuranceCompanyId) {
			this.insuranceCompanyId = insuranceCompanyId;
		}
		public Date getInsuranceStartDate() {
			return insuranceStartDate;
		}
		public void setInsuranceStartDate(Date insuranceStartDate) {
			this.insuranceStartDate = insuranceStartDate;
		}
		public Date getInsuranceEndDate() {
			return insuranceEndDate;
		}
		public void setInsuranceEndDate(Date insuranceEndDate) {
			this.insuranceEndDate = insuranceEndDate;
		}
		public Date getOrderCancelDate() {
			return orderCancelDate;
		}
		public void setOrderCancelDate(Date orderCancelDate) {
			this.orderCancelDate = orderCancelDate;
		}
		
		
		@Column(length = 1000)
		public String getCancelRemark() {
			return cancelRemark;
		}
		public void setCancelRemark(String cancelRemark) {
			this.cancelRemark = cancelRemark;
		}
		@Column(length = 32)
		public String getCommunityNumber() {
			return communityNumber;
		}
		public void setCommunityNumber(String communityNumber) {
			this.communityNumber = communityNumber;
		}
		public Integer getBusinessNum() {
			return businessNum;
		}
		public void setBusinessNum(Integer businessNum) {
			this.businessNum = businessNum;
		}
		@Column(length = 40)
		public String getSharePeople() {
			return sharePeople;
		}
		public void setSharePeople(String sharePeople) {
			this.sharePeople = sharePeople;
		}
		@Column(precision=10,scale=2)
		public BigDecimal getShareFee() {
			return shareFee;
		}
		public void setShareFee(BigDecimal shareFee) {
			this.shareFee = shareFee;
		}
		@Column(length = 200)
		public String getDrivingLicensePic() {
			return drivingLicensePic;
		}
		public void setDrivingLicensePic(String drivingLicensePic) {
			this.drivingLicensePic = drivingLicensePic;
		}
		@Column(length = 40)
		public String getLicense() {
			return license;
		}
		public void setLicense(String license) {
			this.license = license;
		}
		@Column(length = 200)
		public String getOldInsurancePic() {
			return oldInsurancePic;
		}
		public void setOldInsurancePic(String oldInsurancePic) {
			this.oldInsurancePic = oldInsurancePic;
		}
		@Column(length = 500)
		public String getIdCardPic() {
			return idCardPic;
		}
		public void setIdCardPic(String idCardPic) {
			this.idCardPic = idCardPic;
		}
		public Integer getBranchId() {
			return branchId;
		}
		public void setBranchId(Integer branchId) {
			this.branchId = branchId;
		}
		public Integer getPopertyNumber() {
			return popertyNumber;
		}
		public void setPopertyNumber(Integer popertyNumber) {
			this.popertyNumber = popertyNumber;
		}
}
