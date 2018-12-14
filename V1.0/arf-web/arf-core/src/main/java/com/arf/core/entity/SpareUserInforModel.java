package com.arf.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 物业上传用户信息（备份）
 * @author Administrator
 *
 */
@Entity
@Table(name = "spare_user_infor_model")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "spare_user_infor_model_sequence")
public class SpareUserInforModel extends BaseEntity<Long>{

/**
     * 
     */
    private static final long serialVersionUID = 6730950823611211396L;
    //	private int id;
	/**
	 * 车牌
	 */
	private String licensePlateNumber="";
	/**
	 * 小区
	 */
	private String communityNumber;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 是否激活
	 */
	private Integer isActivation=0;
	/**
	 * 0不是vip
	 */
	private Integer vipLevel=0;
	/**
	 * 缴费金额
	 */
	private BigDecimal amount;
	
	/**
     * 当前时间
     */
    @Column(name="currentTime")
    private Long currentTimes;
	
    //物业编号 
    private Integer propertyNumber;
    //子公司编号 
    private Integer branchId;
    
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
    
	@Column(name = "licensePlateNumber", length = 32)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	@Column(name = "community_number", length = 32)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
	@Column(name = "userName", length = 32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "isActivation")
	public Integer getIsActivation() {
		return isActivation;
	}
	public void setIsActivation(Integer isActivation) {
		this.isActivation = isActivation;
	}
	@Column(name = "vipLevel")
	public Integer getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}
	
	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public SpareUserInforModel(String licensePlateNumber, String communityNumber, String userName, Integer isActivation,
	        Integer vipLevel, BigDecimal amount) {
		super();
		this.licensePlateNumber = licensePlateNumber;
		this.communityNumber = communityNumber;
		this.userName = userName;
		this.isActivation = isActivation;
		this.vipLevel = vipLevel;
		this.amount = amount;
	}
	
	public SpareUserInforModel(String licensePlateNumber, String communityNumber, String userName, Integer isActivation,
	        Integer vipLevel, BigDecimal amount, Long currentTime) {
		super();
		this.licensePlateNumber = licensePlateNumber;
		this.communityNumber = communityNumber;
		this.userName = userName;
		this.isActivation = isActivation;
		this.vipLevel = vipLevel;
		this.amount = amount;
		this.currentTimes = currentTime;
	}
	public SpareUserInforModel() {
		super();
	}
	
	public Long getCurrentTimes() {
		return currentTimes;
	}
	public void setCurrentTimes(Long currentTime) {
		this.currentTimes = currentTime;
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
	
	
	
	
	
}
