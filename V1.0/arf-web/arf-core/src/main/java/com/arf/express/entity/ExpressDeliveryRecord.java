package com.arf.express.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "e_express_delivery_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "e_express_delivery_record_sequence")
public class ExpressDeliveryRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String fullName;

	private String expressNum;
	
	private String expressName;
	
	private String orderNo;
	
	private Integer status;

	private Date collectionDate;

	private  Date receiveDate;

	private String communityNumber;
	
	private Integer businessNum;
	
	private Integer branchId;
	
	private Integer popertyNumber;
	
	public enum Status{
//		0 未领取，1已领取，2误输入，3已删除 4用户已评论；
		Unclaimed,Hasreceive,ErrorInput,HasDelete,UserComment
	}

	
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 40)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(length = 40)
	public String getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}
	@Column(length = 40)
	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	@Column(length = 40)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	@Column(length = 40)
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


