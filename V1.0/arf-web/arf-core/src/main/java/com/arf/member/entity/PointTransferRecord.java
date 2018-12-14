package com.arf.member.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_point_transfer_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_point_transfer_record_sequence")
public class PointTransferRecord extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	public static final String POINT_GIFT = "POINT_GIFT";//redis配置参数父节点
	public static final String REVIEW_POINT_PERCENT = "REVIEW_POINT_PERCENT";//评价赠送积分比例(eg.配置为1表示一次评价赠送一个积分)
	
	/**
	 * 操作方式（1、接口2、人工3、自动）
	 * @author arf
	 *
	 */
	public enum OperateType{
		nouse,inter,manul,auto
	}
	/**
	 * 积分流转类型（1、获取 2、消费）
	 * @author arf
	 *
	 */
	public enum PointType{
		nouse,gain,consume;
		
		public static PointType get(int ordinal){
			PointType pointTypes[] = PointType.values();
			if(ordinal > pointTypes.length -1){
				return null;
			}else{
				return pointTypes[ordinal];
			}
		}
	}
	/**
	 * 状态（1、完成 2、冻结 3、失败）
	 * @author arf
	 *
	 */
	public enum Status{
		nouse,finished,frozen,failed;
		public static Status get(int ordinal){
			Status statuss[] = Status.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	/**
	 * 赠送积分业务类型
	 * 0、签到 1、安心点 2、评论商品或服务 3、购买商品或服务
	 * @author arf
	 *
	 */
	public enum PointGiftBusinessType{
		ECC_SIGN,//签到
		ECC_AXD,//安心点
		REVIEW,//评论商品或服务
		PURCHASE,//购买商品或服务
	}
	
	private String accountId;//账户id
	private String accountNumber;//账户编码
	private String identify;//唯一标识
	private Integer pointBalance;//积分余额
	private Integer pointSub;//本次流转积分
	private Date operateTime;//操作时间
	private Byte operateType;//操作方式（1、接口2、人工3、自动）
	private Byte pointType;//积分流转类型（1、获取 2、消费）
	private Byte status;//状态（1、完成 2、冻结 3、失败）
	private String comment;//操作说明
	
	/**
	 * 积分子类型
	 * 1-49为获得积分
	 * 50-99为消费积分
	 * @See {@link com.arf.member.entity.PointTransferRecord.SubType}
	 */
	private Integer subType;
	
	private String communityNumber;//小区编号
	private Integer propertyNumber;//物业编号 
	private Integer branchId;//子公司编号 

	/**
	 * 积分子类型
	 * 每增加一个子类型需要在resources\commoncfg\images\point-icon增加一个对应的icon(png)
	 * @author Caolibao
	 * 2016年9月14日 下午12:28:06
	 *
	 */
	public class SubType{
		public static final byte Charge_Gift = 1;//充值赠送
		public static final byte ECC_AXD_Gift = 2;//每日任务:安心点开闸 赠送
		public static final byte Shopping_Voucher_Return=3;//购物抵扣消费积分返还
		public static final byte Pre_Payback_Reward=4;//购物抵扣消费积分返还
		public static final byte ECC_SIGN_Gift = 5;//每日签到赠送
		public static final byte ECC_MONTH_PAY = 6;//月租续费赠送
		public static final byte Gift_Voucher_Exchange_Ramin = 7;//礼品券兑换礼品剩余额度转积分
		public static final byte Review_Gift = 8;//评论赠送
		public static final byte Temp_Parking = 9;//临时停车赠送
		//-------------------------分割线-----------------------------------
		
		public static final byte Shopping_Voucher = 50;//购物抵扣消费积分
	}
	
	@Column(length=50)
	public String getAccountId() {
		return accountId;
	}
	@Column(length=50)
	public String getAccountNumber() {
		return accountNumber;
	}
	@Column(length=50)
	public String getIdentify() {
		return identify;
	}
	public Integer getPointBalance() {
		return pointBalance;
	}
	public Integer getPointSub() {
		return pointSub;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public Byte getOperateType() {
		return operateType;
	}
	public Byte getPointType() {
		return pointType;
	}
	public Byte getStatus() {
		return status;
	}
	@Column(length=50)
	public String getComment() {
		return comment;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public void setPointBalance(Integer pointBalance) {
		this.pointBalance = pointBalance;
	}
	public void setPointSub(Integer pointSub) {
		this.pointSub = pointSub;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public void setOperateType(Byte operateType) {
		this.operateType = operateType;
	}
	public void setPointType(Byte pointType) {
		this.pointType = pointType;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
	/**
	 * 记录子类型
	 * 1-49为获得积分
	 * 50-99为消费积分
	 * @See {@link com.arf.member.entity.PointTransferRecord.SubType}
	 */
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
}
