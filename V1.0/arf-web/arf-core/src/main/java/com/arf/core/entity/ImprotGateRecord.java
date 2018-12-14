package com.arf.core.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "improtGateRecord")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "improtGateRecord_sequence")
public class ImprotGateRecord extends BaseEntity<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_name; // 
	private String license_plate_number;// 车牌号
	private String state;// 车闸状态
	private String cameraCode;
	private Date time;
	private String communityNumber;//小区id


}
