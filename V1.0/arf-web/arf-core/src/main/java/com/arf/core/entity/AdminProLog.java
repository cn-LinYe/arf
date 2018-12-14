package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Entity - 管理员日志记录
 *
 * @author arf
 * @version 4.0
 */
@Entity
@Table(name = "xx_admin_pro_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_admin_pro_log_sequence")
public class AdminProLog extends BaseEntity<Long>{
	
	
	private static final long serialVersionUID = 1639078158346451645L;


	/**
	 * 类型
	 */
	public enum Type {
		
		/** 子公司更新自己*/
		pendingReview,
		
		/** 总公司审核*/
		through,
		
		/** 其他*/
		refuse
		
	}
	
	/** 类型 */
	private Type type;

	/** 操作员 */
	private String operator;

	/** 备注  */
	private String memo;

	/** 操作对象 */
	private Admin admin;

	/**  
	 * 获取类型  
	 * @return type 类型  
	 */
	@Column(nullable = false, updatable = false)
	public Type getType() {
		return type;
	}
	

	/**  
	 * 设置类型  
	 * @param type 类型  
	 */
	public void setType(Type type) {
		this.type = type;
	}
	

	/**  
	 * 获取操作员  
	 * @return operator 操作员  
	 */
	@Column(updatable = false)
	public String getOperator() {
		return operator;
	}
	

	/**  
	 * 设置操作员  
	 * @param operator 操作员  
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	

	/**  
	 * 获取备注 
	 * @return memo 备注   
	 */
	@Column(updatable = false)
	public String getMemo() {
		return memo;
	}
	

	/**  
	 * 设置备注  
	 * @param memo 备注   
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

	/**  
	 * 获取操作对象  
	 * @return admin 操作对象  
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admins", nullable = false, updatable = false)
	public Admin getAdmin() {
		return admin;
	}
	

	/**  
	 * 设置操作对象  
	 * @param admin 操作对象  
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	

}


















