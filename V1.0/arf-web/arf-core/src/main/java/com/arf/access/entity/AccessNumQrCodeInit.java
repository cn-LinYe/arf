package com.arf.access.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 初始化门禁编号二维码的临时编号记录表,
 * @author Caolibao
 *
 */
@Entity
@Table(name = "access_num_qr_code_init")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_card_unlock_log_sequence")
public class AccessNumQrCodeInit extends BaseEntity<Long> {
	private static final long serialVersionUID = -6168459814830055431L;
	private Long accessNum; // 门禁编号,8位数字编号
	
	private String batchNum;//批次编号
	
	public Long getAccessNum() {
		return accessNum;
	}
	public void setAccessNum(Long accessNum) {
		this.accessNum = accessNum;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
}
