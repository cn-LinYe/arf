package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * 弘基金表
 * @author Administrator
 *
 */
@Entity(name = "hungfundmodel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "hungfundmodel_sequence")
public class HungFundModel  extends BaseEntity<Long>{

	
	
	/**
     * 
     */
    private static final long serialVersionUID = 3730840893829409266L;
//    private int id;//主键
	/**
	 * 支付金额
	 */
	private int amount;
	/**
	 * 支付类型
	 */
	private String pay_type;
	/**
	 * 捐款时间
	 */
	private long time;
	/**
	 * 姓名
	 */
	private String name;
	
	
	
	
	
	
	
	public HungFundModel( int amount, String pay_type, long time,String name) {
		super();
		this.amount = amount;
		this.pay_type = pay_type;
		this.time = time;
	}
	public HungFundModel() {
		super();
	}
	
	
	
//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	
	
	@Column(name = "amount", nullable = false)
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Column(name = "pay_type", nullable = false)
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	
	@Column(name = "time", nullable = false)
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	


	
	
	
	
	
	
	
	
}
