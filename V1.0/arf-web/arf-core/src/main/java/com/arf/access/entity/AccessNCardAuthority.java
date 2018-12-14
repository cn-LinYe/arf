package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_n_card_authority")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_n_card_authority_sequence")
public class AccessNCardAuthority  extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7447858648267373335L;
	
	private Long accessNCardId;//门禁卡片（卡片归属信息）
	private Issued issued;//是否下发枚举（0、Not_Issued 未下发 1、Issued 已下发）
	private String communityNumber;//小区编号
	private String midCommunityNumber;//设备小区编号
	private Long accessNEquipmentId;//归属设备id
	private String accessNumber;//门禁设备编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Issued{
		Not_Issued,Issued;
	}

	@Column(length=20,name="access_n_card_id")
	public Long getAccessNCardId() {
		return accessNCardId;
	}

	public void setAccessNCardId(Long accessNCardId) {
		this.accessNCardId = accessNCardId;
	}

	public Issued getIssued() {
		return issued;
	}

	public void setIssued(Issued issued) {
		this.issued = issued;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=20)
	public String getMidCommunityNumber() {
		return midCommunityNumber;
	}

	public void setMidCommunityNumber(String midCommunityNumber) {
		this.midCommunityNumber = midCommunityNumber;
	}

	@Column(length=20,name="access_n_equipment_id")
	public Long getAccessNEquipmentId() {
		return accessNEquipmentId;
	}

	public void setAccessNEquipmentId(Long accessNEquipmentId) {
		this.accessNEquipmentId = accessNEquipmentId;
	}

	@Column(length=20)
	public String getAccessNumber() {
		return accessNumber;
	}

	public void setAccessNumber(String accessNumber) {
		this.accessNumber = accessNumber;
	}

}
