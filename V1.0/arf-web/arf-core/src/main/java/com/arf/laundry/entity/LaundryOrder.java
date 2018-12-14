package com.arf.laundry.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "l_laundry_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "l_laundry_order_sequence")
public class LaundryOrder extends BaseEntity<Long>{

	private static final long serialVersionUID = 138037282581084004L;
	
	/**
	 * 状态:0:新建,1:用户取消订单,2: 商户确认接单,3:商户拒绝接单, 4:商户已取包裹,5:已送回包裹,6:用户签收
	 */
	public enum Status{
		New,
		Canceled,
		Business_Confirmed,
		Business_Refused,
		Business_Received,
		Business_Send_Back,
		Customer_Sign_In;
		
		public static Status get(int ordinal){
			Status status[] = Status.values();
			if(ordinal<0 || ordinal>status.length-1){
				return null;
			}else{
				return status[ordinal];
			}
		}
	}
	
	/**
	 * 支付状态0;未支付1;已支付 2;支付失败
	 *
	 */
	public enum PaidStatus{ //0;未支付1;已支付 2;支付失败 
		Not_Paid,
		Paid,
		Paid_Failed,
		;
	}
	
	/**
	 * 投诉状态,0:无投诉,1:投诉中,2:投诉处理完毕
	 */
	public enum ComplaintStatus{
		No_Complaint,
		Complainting,
		Complainted,
	}
	
	/**
	 * 支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付
	 */
	public enum PayType{
		Paid_Agent, //代支付
		Paid_WeiXin, //微信支付
		Paid_Alipay, //支付宝支付
		Paid_UnionPay, //银行卡
		Paid_Cash, //现金
		Paid_Account, //钱包支付
		Paid_Package //套餐支付
	}
	
	/**
	 * 退款状态,0:无退款申请,1:退款申请中,2:退款完成,3:退款被拒绝 
	 */
	public enum RefundStatus{
		No_Refund,
		Refunding,
		Refunded,
		Refund_Refuse;
		
		public static RefundStatus get(int ordinal){
			RefundStatus refundStatus[] = RefundStatus.values();
			if(ordinal<0 || ordinal > refundStatus.length-1){
				return null;
			}else{
				return refundStatus[ordinal];
			}
		}
	}

	private BigDecimal totalFee;//订单总价
	private BigDecimal actualAmount;//实际订单金额
	private Byte status;//状态:0:新建,1:用户取消订单,2: 商户确认接单,3:商户拒绝接单, 4:商户已取包裹,5:已送回包裹,6:用户签收
	private String outTradeNo;//订单编号,支付使用
	private String businessNum;//所属商户
	private Integer totalCount;//总件数
	private Date cancelDate;//用户取消时间
	private Date finishedDate;//订单完成时间（用户操作）
	private Date businessConfirmDate;//商户确认/拒绝时间
	private Date businessTakenDate;//商户取包裹时间
	private Date businessCompleteDate;//商户完成/送回包裹时间
	private Byte feePayStatus;//支付状态0;未支付1;已支付 2;支付失败
	private Date paidDate;//支付完成时间
	private String areaCode;//归属地区编码,eg.441901
	private String userRealName;//用户姓名
	private String address;//收单地址
	private String takingDate;//收衣时间 yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss
	private String userPhone;//用户联系电话
	private String remark;//备注信息
	private Byte complaintStatus;//投诉状态,0:无投诉,1:投诉中,2:投诉处理完毕
	private String userName;//用户名
	private Byte payType;//支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付
	private Byte refundStatus;//退款状态,0:无退款申请,1:退款申请中,2:退款完成,3:退款被拒绝 
	private String rejectReason;//商户拒单理由
	
	private List<LaundryOrderItems> laundryOrderItems = new ArrayList<LaundryOrderItems>();

	@Column(length = 512)
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	@Column(length = 4)
	public Byte getStatus() {
		return status;
	}
	@Column(length = 30)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	@Column(length = 30)
	public String getBusinessNum() {
		return businessNum;
	}
	@Column(length = 11)
	public Integer getTotalCount() {
		return totalCount;
	}

	public Date getBusinessConfirmDate() {
		return businessConfirmDate;
	}

	public Date getBusinessTakenDate() {
		return businessTakenDate;
	}

	public Date getBusinessCompleteDate() {
		return businessCompleteDate;
	}
	@Column(length = 4)
	public Byte getFeePayStatus() {
		return feePayStatus;
	}

	public Date getPaidDate() {
		return paidDate;
	}
	@Column(length = 10)
	public String getAreaCode() {
		return areaCode;
	}
	@Column(length = 10)
	public String getUserRealName() {
		return userRealName;
	}
	@Column(length = 200)
	public String getAddress() {
		return address;
	}
	@Column(length = 100)
	public String getTakingDate() {
		return takingDate;
	}
	@Column(length = 20)
	public String getUserPhone() {
		return userPhone;
	}
	@Column(length = 256)
	public String getRemark() {
		return remark;
	}
	@Column(length = 4)
	public Byte getComplaintStatus() {
		return complaintStatus;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(length = 4)
	public Byte getPayType() {
		return payType;
	}
	@Column(length = 4)
	public Byte getRefundStatus() {
		return refundStatus;
	}
	@OneToMany(mappedBy = "outTradeNo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<LaundryOrderItems> getLaundryOrderItems() {
		return laundryOrderItems;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public void setBusinessConfirmDate(Date businessConfirmDate) {
		this.businessConfirmDate = businessConfirmDate;
	}

	public void setBusinessTakenDate(Date businessTakenDate) {
		this.businessTakenDate = businessTakenDate;
	}

	public void setBusinessCompleteDate(Date businessCompleteDate) {
		this.businessCompleteDate = businessCompleteDate;
	}

	public void setFeePayStatus(Byte feePayStatus) {
		this.feePayStatus = feePayStatus;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTakingDate(String takingDate) {
		this.takingDate = takingDate;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setComplaintStatus(Byte complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPayType(Byte payType) {
		this.payType = payType;
	}

	public void setRefundStatus(Byte refundStatus) {
		this.refundStatus = refundStatus;
	}

	public void setLaundryOrderItems(List<LaundryOrderItems> laundryOrderItems) {
		this.laundryOrderItems = laundryOrderItems;
	}
	
}
