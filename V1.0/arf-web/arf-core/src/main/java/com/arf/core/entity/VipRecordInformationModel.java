package com.arf.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity(name = "vipRecordInformation")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "vipRecordInformation_sequence")
public class VipRecordInformationModel extends BaseEntity<Long> {

	/**
     * 
     */
    private static final long serialVersionUID = -6450215830202487769L;
	private String out_trade_no;// 订单号
	private String subject; // 商品名称
	private String payment_type;// 支付类型 1:阿里支付宝,2:微信,3:电子钱包支付,4兑换码兑换开通ecc
	private String trade_status;// 交易状态
	private BigDecimal total_fee; // 交易金额
	private int pay_choice;//支付方式 1ecc开通类型-直接开通 3ecc开通类型-活动开闸日 4ecc开通类型-获取门锁开通 
	private String user_name;//用户名
	private String unionID="";//微信唯一标示
	private int isRed=0;//是否发红包
	/**
	 * 创建订单时间
	 */
	private long createTime=0;
	/**
	 * 生成订单时间
	 */
	private long updateTime=0;
	/**
	 * 哪种方式支付
	 */
	private int chargeType=0;
	/**
	 * 支付单价
	 */
	private BigDecimal pricePer=new BigDecimal(0);
	
	/**
	 * 预留
	 */
	private String attributeInfor="";
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	//小区编号
	private String communityNumber;
	
	/**
	 * 开通ECC赠送套餐Id,新增字段
	 * @since 安心点2.0.7
	 */
	private Long eccPackageConfigId;
	
	public VipRecordInformationModel() {
	super();
}

	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, String user_name) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.user_name = user_name;
	}


	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, int pay_choice, String user_name) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.pay_choice = pay_choice;
		this.user_name = user_name;
	}
	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, int pay_choice, String user_name, String unionID, int isRed) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.pay_choice = pay_choice;
		this.user_name = user_name;
		this.unionID = unionID;
		this.isRed = isRed;
	}
	
	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, int pay_choice, String user_name, String unionID, int isRed, long createTime) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.pay_choice = pay_choice;
		this.user_name = user_name;
		this.unionID = unionID;
		this.isRed = isRed;
		this.createTime = createTime;
	}
	
	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, int pay_choice, String user_name, String unionID, int isRed, long createTime,
			int chargeType, BigDecimal pricePer, String attributeInfor) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.pay_choice = pay_choice;
		this.user_name = user_name;
		this.unionID = unionID;
		this.isRed = isRed;
		this.createTime = createTime;
		this.chargeType = chargeType;
		this.pricePer = pricePer;
		this.attributeInfor = attributeInfor;
	}
	
	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, int pay_choice, String user_name, String unionID, int isRed, long createTime,
			int chargeType, BigDecimal pricePer, String attributeInfor,Integer propertyNumber,Integer branchId,String communityNumber) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.pay_choice = pay_choice;
		this.user_name = user_name;
		this.unionID = unionID;
		this.isRed = isRed;
		this.createTime = createTime;
		this.chargeType = chargeType;
		this.pricePer = pricePer;
		this.attributeInfor = attributeInfor;
		this.propertyNumber = propertyNumber;
		this.branchId = branchId;
		this.communityNumber = communityNumber;
	}
	
	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, int pay_choice, String user_name, String unionID, int isRed, long createTime,
			int chargeType, BigDecimal pricePer, String attributeInfor,Integer propertyNumber,Integer branchId,
			String communityNumber,Long eccPackageConfigId) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.pay_choice = pay_choice;
		this.user_name = user_name;
		this.unionID = unionID;
		this.isRed = isRed;
		this.createTime = createTime;
		this.chargeType = chargeType;
		this.pricePer = pricePer;
		this.attributeInfor = attributeInfor;
		this.propertyNumber = propertyNumber;
		this.branchId = branchId;
		this.communityNumber = communityNumber;
		this.eccPackageConfigId = eccPackageConfigId;
	}
	
	public VipRecordInformationModel(String out_trade_no, String subject, String payment_type, String trade_status,
			BigDecimal total_fee, int pay_choice, String user_name, String unionID, int isRed, long createTime,
			 BigDecimal pricePer) {
		super();
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.trade_status = trade_status;
		this.total_fee = total_fee;
		this.pay_choice = pay_choice;
		this.user_name = user_name;
		this.unionID = unionID;
		this.isRed = isRed;
		this.createTime = createTime;
		this.pricePer = pricePer;
	}


	@Column(name = "out_trade_no", length = 64)
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	@Column(name = "subject", length = 128)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	@Column(name = "payment_type", length = 4)
	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	@Column(name = "trade_status", length = 64)
	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	@Column(name = "total_fee")
	public BigDecimal getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}

	@Column(name = "user_name",length=64)
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "pay_choice",nullable=false)
	public int getPay_choice() {
		return pay_choice;
	}
	public void setPay_choice(int pay_choice) {
		this.pay_choice = pay_choice;
	}
	@Column(name = "unionID",length=128)
	public String getUnionID() {
		return unionID;
	}
	public void setUnionID(String unionID) {
		this.unionID = unionID;
	}
	@Column(name = "isRed",nullable=false)
	public int getIsRed() {
		return isRed;
	}
	public void setIsRed(int isRed) {
		this.isRed = isRed;
	}
	@Column(name = "createTime",nullable=false)
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@Column(name = "updateTime",nullable=false)
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "chargeType",nullable=false)
	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	@Column(name = "pricePer",nullable=false)
	public BigDecimal getPricePer() {
		return pricePer;
	}

	public void setPricePer(BigDecimal pricePer) {
		this.pricePer = pricePer;
	}

	@Column(name = "attributeInfor",nullable=false)
	public String getAttributeInfor() {
		return attributeInfor;
	}

	public void setAttributeInfor(String attributeInfor) {
		this.attributeInfor = attributeInfor;
	}

	public Integer getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public Long getEccPackageConfigId() {
		return eccPackageConfigId;
	}

	public void setEccPackageConfigId(Long eccPackageConfigId) {
		this.eccPackageConfigId = eccPackageConfigId;
	}
}
