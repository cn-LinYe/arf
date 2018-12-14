package com.arf.axd.axdgift.entity;import java.math.BigDecimal;import javax.persistence.Column;import javax.persistence.Entity;import javax.persistence.SequenceGenerator;import javax.persistence.Table;import com.arf.core.entity.BaseEntity;@Entity@Table(name = "r_axd_gift_record")@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_account_transfer_record_sequence")public class AxdGiftRecord extends BaseEntity<Long> {	private static final long serialVersionUID = 1L;		/**	 * (0-积分,1-现金 ,2-积分现金混合)	 *	 */	public enum GiftType{		Point,Cash,PointAndCash	}		private String userName;//用户名 	private BigDecimal giftAmout;//赠送现金金额 	private Integer giftPoint;//赠送积分数量 	private Integer giftType;//返还类型:0-积分,1-现金 ,2-积分现金混合,等.实体中为枚举类型 	private String communityNo;//小区编号 	private String propertyNumber;//物业编号 	private String branchId;//子公司编号 	private String license;//安心点的车牌号 	private String comment;//备注说明 		@Column(length=30)	public String getUserName() {		return userName;	}	@Column(precision=10,scale=2)	public BigDecimal getGiftAmout() {		return giftAmout;	}	public Integer getGiftPoint() {		return giftPoint;	}	public Integer getGiftType() {		return giftType;	}	@Column(length=20)	public String getCommunityNo() {		return communityNo;	}	@Column(length=20)	public String getPropertyNumber() {		return propertyNumber;	}	@Column(length=20)	public String getBranchId() {		return branchId;	}	@Column(length=10)	public String getLicense() {		return license;	}	@Column(length=100)	public String getComment() {		return comment;	}	public void setUserName(String userName) {		this.userName = userName;	}	public void setGiftAmout(BigDecimal giftAmout) {		this.giftAmout = giftAmout;	}	public void setGiftPoint(Integer giftPoint) {		this.giftPoint = giftPoint;	}	public void setGiftType(Integer giftType) {		this.giftType = giftType;	}	public void setCommunityNo(String communityNo) {		this.communityNo = communityNo;	}	public void setPropertyNumber(String propertyNumber) {		this.propertyNumber = propertyNumber;	}	public void setBranchId(String branchId) {		this.branchId = branchId;	}	public void setLicense(String license) {		this.license = license;	}	public void setComment(String comment) {		this.comment = comment;	}}