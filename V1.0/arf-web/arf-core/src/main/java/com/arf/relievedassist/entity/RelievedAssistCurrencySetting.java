package com.arf.relievedassist.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assist_currency_setting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_currency_setting_sequence")
public class RelievedAssistCurrencySetting extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9111596919332374717L;
	
	private OperateType operateType;/*//行为类型（0 Release 发布安心帮 1 Comment 评论安心帮 
									2 Parise 点赞安心帮 3 Collection 收藏安心帮 4 Forward 转发安心帮 ）*/
	private Integer currencyNumber;//获得帮币数量
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OperateType{
		Release,//发布安心帮
		Comment,//评论安心帮 
		Parise,//点赞安心帮 
		Collection,//收藏安心帮
		Forward;//转发安心帮 
	}

	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}

	public Integer getCurrencyNumber() {
		return currencyNumber;
	}

	public void setCurrencyNumber(Integer currencyNumber) {
		this.currencyNumber = currencyNumber;
	}

}
