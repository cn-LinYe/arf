package com.arf.axd.axdgift.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "axd_gift_statistic_backups")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "axd_gift_statistic_backups_sequence")
public class AxdGiftStatisticBackups extends BaseEntity<Long>{

	private static final long serialVersionUID = -6436030408900294188L;
	
	private Date modifyTime;
	private String hkey;//键值 key
	private String hvalue;//map json字符串
	
	public Date getModifyTime() {
		return modifyTime;
	}
	public String getHkey() {
		return hkey;
	}
	public String getHvalue() {
		return hvalue;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public void setHkey(String hkey) {
		this.hkey = hkey;
	}
	public void setHvalue(String hvalue) {
		this.hvalue = hvalue;
	}
	
}
