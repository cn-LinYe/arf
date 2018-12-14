package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="illegalInformation")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "illegalInformation_sequence")
public class IllegalInformationModel extends BaseEntity<Long> {
	
	/**
     * 
     */
    private static final long serialVersionUID = -3797448526432657020L;
//    private int id ;
	/**
	 * 违规条数
	 */
	private int illegal_count;
	/**
	 * 罚款金额
	 */
	private int fine_amount;
	/**
	 * 扣分
	 */
	private int points;
	
	/**
	 * 车牌
	 * @return
	 */
	private String license_number;
	
	/**
	 * 当前查询时间
	 * 命名current_time不能自动建表
	 */
	private long current_time_select;
	
	/**
	 * 违章纪录
	 */
	private String illegalInformationRecord;
	
	
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	
	@Column(name = "illegal_count", length = 32,nullable=false)
	public int getIllegal_count() {
		return illegal_count;
	}

	public void setIllegal_count(int illegal_count) {
		this.illegal_count = illegal_count;
	}

	
	@Column(name = "fine_amount", length = 32,nullable=false)
	public int getFine_amount() {
		return fine_amount;
	}

	public void setFine_amount(int fine_amount) {
		this.fine_amount = fine_amount;
	}

	
	@Column(name = "points", length = 32,nullable=false)
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Column(name = "license_number", length = 32,nullable=false)
	public String getLicense_number() {
		return license_number;
	}

	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}

	@Column(name = "current_time_select", length = 32,nullable=false)
	public long getCurrent_time_select() {
		return current_time_select;
	}

	public void setCurrent_time_select(long current_time_select) {
		this.current_time_select = current_time_select;
	}


	public IllegalInformationModel(int illegal_count, int fine_amount, int points, String license_number,
			long current_time_select) {
		super();
		this.illegal_count = illegal_count;
		this.fine_amount = fine_amount;
		this.points = points;
		this.license_number = license_number;
		this.current_time_select = current_time_select;
	}

	public IllegalInformationModel() {
		super();
	}
}
