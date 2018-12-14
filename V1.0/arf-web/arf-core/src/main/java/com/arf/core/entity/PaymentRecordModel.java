package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * 支付记录
 */
@Entity(name = "payment_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "payment_record_sequence")
public class PaymentRecordModel extends BaseEntity<Long> {

/**
     * 
     */
    private static final long serialVersionUID = 7736236625718417479L;
    //	private int id;
	private int amount;//支付金额
	private long time;//支付时间
	private String user_name;// 充值的用户外键
	private String amount_type;//支付类型
	private int discount_year = 0;//获得赠积分年限
//	private ;

	public PaymentRecordModel() {
		super();
	}

	public PaymentRecordModel( int amount, long time, String user_name, String amount_type, int discount_year) {
		super();
		this.amount = amount;
		this.time = time;
		this.user_name = user_name;
		this.amount_type = amount_type;
		this.discount_year = discount_year;
	}
	public PaymentRecordModel(int amount, long time, String user_name, String amount_type) {
		super();
		this.amount = amount;
		this.time = time;
		this.user_name = user_name;
		this.amount_type = amount_type;
	}

//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	@Column(name = "amount", length = 32, nullable = false)
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Column(name = "time", nullable = false)
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Column(name = "user_name", length = 32, nullable = false)
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "amount_type", length = 32, nullable = false)
	public String getAmount_type() {
		return amount_type;
	}

	public void setAmount_type(String amount_type) {
		this.amount_type = amount_type;
	}

	@Column(name = "discount_year", length = 32)
	public int getDiscount_year() {
		return discount_year;
	}

	public void setDiscount_year(int discount_year) {
		this.discount_year = discount_year;
	}
}
