package com.arf.violation.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 违章订单表
 * @author dg
 *
 */
@Entity
@Table(name = "v_traffic_violation_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "v_traffic_violation_order_sequence")
public class TrafficViolationOrder extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7883119433467045374L;
	
	private String orderNo;//订单编号
	private String userName;//用户名
	private Integer status;//0:新建,1:已取消,2:已确认/处理中,3:已拒绝,4:已完成
	private Integer feePayStatus;//0:未支付,1:已支付,2:已退款
	private Integer feePayType;//0:微信支付,1:支付宝支付,2:在线余额支付
	private String businessNum;//商户编号
	private String realName;//车主姓名
	private String userPhone;//车主电话
	private Integer needInvoice;//是否需要发票:0:不需要,1:需要
	private String invoiceAddress;//发票地址
	private BigDecimal fineAmount;//总计罚款金额
	private BigDecimal serviceAmount;//总计服务费金额
	private BigDecimal invoiceFreight;//总计发票运费金额
	private Integer fineMarks;//总计扣分数量
	private Integer additionService;//是否需要附加服务(消分服务) 0:不需要,1:需要2:商户已报价/, 3:支付完成4：拒绝附加服务
	private Integer additionServicePoint;//附加消分数量
	private String additionServiceNo;//附加服务费编号,用于再次支付附加服务费
	private BigDecimal additionServiceFee;//附加服务费
	private Integer complaintStatus;//投诉状态,0:无投诉,1:投诉中,2:投诉处理完毕
	private Integer refundStatus;//退款状态,0:无退款申请,1:退款申请中,2:退款完成,3:退款被拒绝
	private Date cancelDate;//用户取消时间/商户拒单时间
	private Date feePayTime;//支付完成时间
	private Date confirmDate;//商户确认时间
	private Date finishedTime;//完成时间
	private String consigneeName;//收货人(发票)
	private String consigneePhone;//收货电话(发票)
	private Integer reviewed;//是否评论0:未评论,1:已评论
	private Date additionServiceConfirmDate;//拒绝确认附加服务时间
	public enum RefundStatus{
		NoRefundApplication,RefundApplying, RefundFinish,RefundDenied
	}
	
	public enum Status{
		New,Cancel,Confirm,Refuse,Finish;
		public static Status get(int ordinal){
			Status status[]=Status.values();
			if (status.length<ordinal) {
				return null;
			}
			return status[ordinal];
		}
	}
	
	public enum FeePayStatus{
		Unpaid,Paid,Refunded
	}
	
	public enum AdditionService{
		NotNeed,Need,QuotedOrRefunded,Paid,Refused
	}
	public enum FeePayType{
		WeixinPay,Alipay,BalancePay;
		public static FeePayType get(int ordinal){
			FeePayType status[] =FeePayType.values();
			if (status.length<ordinal) {
				return null;
			}
			return status[ordinal];
		}
		
	}
	
	public enum ComplaintStatus{
		NoComplaints,Complaints,Completed
	}
	
	public enum Reviewed{
		NotReviewe,Reviewed
	}

	public Date getAdditionServiceConfirmDate() {
		return additionServiceConfirmDate;
	}

	public void setAdditionServiceConfirmDate(Date additionServiceConfirmDate) {
		this.additionServiceConfirmDate = additionServiceConfirmDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFeePayStatus() {
		return feePayStatus;
	}

	public void setFeePayStatus(Integer feePayStatus) {
		this.feePayStatus = feePayStatus;
	}

	public Integer getFeePayType() {
		return feePayType;
	}

	public void setFeePayType(Integer feePayType) {
		this.feePayType = feePayType;
	}

	
	@Column(length=20)
	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Integer needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public BigDecimal getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(BigDecimal fineAmount) {
		this.fineAmount = fineAmount;
	}

	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public BigDecimal getInvoiceFreight() {
		return invoiceFreight;
	}

	public void setInvoiceFreight(BigDecimal invoiceFreight) {
		this.invoiceFreight = invoiceFreight;
	}

	public Integer getFineMarks() {
		return fineMarks;
	}

	public void setFineMarks(Integer fineMarks) {
		this.fineMarks = fineMarks;
	}

	public Integer getAdditionService() {
		return additionService;
	}

	public void setAdditionService(Integer additionService) {
		this.additionService = additionService;
	}

	public Integer getAdditionServicePoint() {
		return additionServicePoint;
	}

	public void setAdditionServicePoint(Integer additionServicePoint) {
		this.additionServicePoint = additionServicePoint;
	}

	public String getAdditionServiceNo() {
		return additionServiceNo;
	}

	public void setAdditionServiceNo(String additionServiceNo) {
		this.additionServiceNo = additionServiceNo;
	}

	public BigDecimal getAdditionServiceFee() {
		return additionServiceFee;
	}

	public void setAdditionServiceFee(BigDecimal additionServiceFee) {
		this.additionServiceFee = additionServiceFee;
	}

	public Integer getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(Integer complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getFeePayTime() {
		return feePayTime;
	}

	public void setFeePayTime(Date feePayTime) {
		this.feePayTime = feePayTime;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Integer getReviewed() {
		return reviewed;
	}

	public void setReviewed(Integer reviewed) {
		this.reviewed = reviewed;
	}

	@Column(length=20)
	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	@Column(length=20)
	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

}
