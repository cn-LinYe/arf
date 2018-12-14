package com.arf.axd.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_ecc_package_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_ecc_package_config_sequence")
public class EccPackageConfig extends BaseEntity<Long>{

	private static final long serialVersionUID = -4554012342153187540L;
	
	/**
	 * 开通招行e账号（0未开通 1开通）
	 */
	public enum EAccount{
		Unopened,Opened
	}
	
	/**
	 * 状态 （0可用 1不可用 2删除）
	 */
	public enum Status{
		Useable,Disable,Deleted
	}

	/**
	 * ecc套餐类型(0赠送实物 1赠送洗车代金券 2赠送保险优惠券 3 赠送现金券 4组合套餐,5购买停车卡)
	 */
	public enum PackageType{
		Gift,WashCar,Insurance,Cash,Combination,GoldenCard

	}
	
	private BigDecimal packageAmount;//套餐价格
	private String packageName;//套餐名称
	private Byte packageIndex;//套餐顺序（从大到小显示不允许重复）
	private String vouchersNum;//代金券编号
	private Byte vouchersCount;//代金券数量
	private BigDecimal vouchersTotalAmount;//代金券总金额
	private Byte eAccount;//开通招行e账号（0未开通 1开通）
	private String cashNum;//现金券编号
	private Byte cashCount;//现金券数量
	private BigDecimal cashTotalAmount;//现金券总金额
	private String packageDescription;//套餐描述(充值120元，可获得200元洗车代金券)
	private Byte status;// 状态 （0可用 1不可用 2删除）
	private Date startReleasedDate;//开始发行时间
	private Date endReleasedDate;//结束发行时间
	private String url;//跳转webview地址
	private Byte packageType; //ecc套餐类型(0赠送实物 1赠送洗车代金券 2赠送保险优惠券 3 赠送现金券 4组合套餐,5购买停车卡)
	private String giftName;//赠送名称（洗车代金券或者智能门锁）
	private BigDecimal giftAmount;//赠送实物价格
	private Byte backgroundType;//背景颜色下标(0默认值 1-5分别代表)
	private BigDecimal profitProrataAmount;//利益分配金额
	
	@Column(precision=10,scale=2)
	public BigDecimal getPackageAmount() {
		return packageAmount;
	}
	@Column(length = 40)
	public String getPackageName() {
		return packageName;
	}
	@Column(length = 4)
	public Byte getPackageIndex() {
		return packageIndex;
	}
	@Column(length = 40)
	public String getVouchersNum() {
		return vouchersNum;
	}
	@Column(length = 4)
	public Byte getVouchersCount() {
		return vouchersCount;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getVouchersTotalAmount() {
		return vouchersTotalAmount;
	}
	@Column(length = 4)
	public Byte geteAccount() {
		return eAccount;
	}
	@Column(length = 40)
	public String getCashNum() {
		return cashNum;
	}
	@Column(length = 4)
	public Byte getCashCount() {
		return cashCount;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getCashTotalAmount() {
		return cashTotalAmount;
	}
	@Column(length = 1000)
	public String getPackageDescription() {
		return packageDescription;
	}
	@Column(length = 4)
	public Byte getStatus() {
		return status;
	}
	@Column(length = 200)
	public String getUrl() {
		return url;
	}
	public Date getStartReleasedDate() {
		return startReleasedDate;
	}
	public Date getEndReleasedDate() {
		return endReleasedDate;
	}
	public void setPackageAmount(BigDecimal packageAmount) {
		this.packageAmount = packageAmount;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public void setPackageIndex(Byte packageIndex) {
		this.packageIndex = packageIndex;
	}
	public void setVouchersNum(String vouchersNum) {
		this.vouchersNum = vouchersNum;
	}
	public void setVouchersCount(Byte vouchersCount) {
		this.vouchersCount = vouchersCount;
	}
	public void setVouchersTotalAmount(BigDecimal vouchersTotalAmount) {
		this.vouchersTotalAmount = vouchersTotalAmount;
	}
	public void seteAccount(Byte eAccount) {
		this.eAccount = eAccount;
	}
	public void setCashNum(String cashNum) {
		this.cashNum = cashNum;
	}
	public void setCashCount(Byte cashCount) {
		this.cashCount = cashCount;
	}
	public void setCashTotalAmount(BigDecimal cashTotalAmount) {
		this.cashTotalAmount = cashTotalAmount;
	}
	public void setPackageDescription(String packageDescription) {
		this.packageDescription = packageDescription;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setStartReleasedDate(Date startReleasedDate) {
		this.startReleasedDate = startReleasedDate;
	}
	public void setEndReleasedDate(Date endReleasedDate) {
		this.endReleasedDate = endReleasedDate;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(length = 4,nullable=false)
	public Byte getPackageType() {
		return packageType;
	}
	public void setPackageType(Byte packageType) {
		this.packageType = packageType;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getGiftAmount() {
		return giftAmount;
	}
	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}
	public Byte getBackgroundType() {
		return backgroundType;
	}
	public void setBackgroundType(Byte backgroundType) {
		this.backgroundType = backgroundType;
	}
	public BigDecimal getProfitProrataAmount() {
		return profitProrataAmount;
	}
	public void setProfitProrataAmount(BigDecimal profitProrataAmount) {
		this.profitProrataAmount = profitProrataAmount;
	}
}
