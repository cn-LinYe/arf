package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 支付之前获取金额
 * @author Administrator
 *
 */
@Entity
@Table(name = "paymoneyway")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "paymoneyway_sequence")
public class PayMoneyWayModel extends BaseEntity<Long> {

	/**
     * 
     */
    private static final long serialVersionUID = -1677629901460802033L;
    
//    private int id;
	/**
	 * 支付方式
	 */
	private String level_name; 
	/**
	 * 支付金额
	 */
	private int amount;
	/**
	 *  支付类型
	 */
	private int level; 
	
	/**
	 * 红包活动日开始时间
	 */
	private long redStartTime=0;
	
	/**
	 * 红包
	 */
	
	private long redEndTime=0;
	

	/**
	 * 公众号名称
	 * @return
	 */
	private String weiXinNmuber="";
	
	/**
	 * 此支付方式是否启用
	 * @return
	 */
	private boolean isEnable=false;
	
//	@Id
//	@Column(name = "user_id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	
	@Column(name = "level_name", length = 64, nullable = true)
	public String getLevel_name() {
		return level_name;
	}
	
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	
	@Column(name = "amount", nullable = true)
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Column(name = "level", length = 32, nullable = true)
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public PayMoneyWayModel(String level_name, int amount, int level) {
		super();
		this.level_name = level_name;
		this.amount = amount;
		this.level = level;
	}
	public PayMoneyWayModel() {
		super();
	}
	
	
	
	@Column(name = "redStartTime", nullable = true)
	public long getRedStartTime() {
		return redStartTime;
	}
	public void setRedStartTime(long redStartTime) {
		this.redStartTime = redStartTime;
	}
	@Column(name = "redEndTime", nullable = true)
	public long getRedEndTime() {
		return redEndTime;
	}
	public void setRedEndTime(long redEndTime) {
		this.redEndTime = redEndTime;
	}
	@Column(name = "weiXinNmuber")
	public String getWeiXinNmuber() {
		return weiXinNmuber;
	}
	public void setWeiXinNmuber(String weiXinNmuber) {
		this.weiXinNmuber = weiXinNmuber;
	}
	@Column(name = "isEnable",nullable = false)
	public boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	
}
