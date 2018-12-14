package com.arf.silverleopard.entity;


import com.arf.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "p_sales_receipts")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_sales_receipts_sequence")
public class SalesReceipts extends BaseEntity<Long>{

	private static final long serialVersionUID = -1097929622745606613L;

	/* 销售单据唯一标识 */
	private Long uid;
	/* 会员唯一标识 */
	private Long customerUid;
	/* 收银员唯一标识 */
	private Long cashierUid;
	/* 单据流水号  */
	private String sn;
	/*  单据产生的时间，格式为yyyy-MM-dd hh:mm:ss */
	private String datetime;
	/*  单据总价  */
	private String totalAmount;
	/*  单据总利润 */
	private String totalProfit;
	/* 单据折扣  */
	private String discount;
	/* 零数额，比如3.1元收别人3元，0.1就是被抹零数额  */
	private String rounding;
	/* 单据类型：SELL销售单据, SELL_RETURN退货单据。  */
	private String ticketType;
	/* 值为1时表示单据已作废  */
	private String invalid;
	/*  第三方平台支付单号 */
	private String externalOrderNo;
	/*  支付方式代码 */
	private String payCode;
	/*  支付金额 */
	private String amount;
	/*  门店凭证 */
	private String appId;
	/*  商品数量 */
	private String productQuantity;
	/*  商品原价 */
	private String productPrice;

	List<SalesReceiptsItem> items;

	List<PaymentMethods> payments;

	@Transient
	public List<PaymentMethods> getPayments() {

		return payments;
	}

	public void setPayments(List<PaymentMethods> payments) {

		this.payments = payments;
	}

	@Transient
	public List<SalesReceiptsItem> getItems() {

		return items;
	}

	public String getProductQuantity() {

		return productQuantity;
	}

	public void setProductQuantity(String productQuantity) {

		this.productQuantity = productQuantity;
	}

	public String getProductPrice() {

		return productPrice;
	}

	public void setProductPrice(String productPrice) {

		this.productPrice = productPrice;
	}

	public void setItems(List<SalesReceiptsItem> items) {

		this.items = items;
	}

	public Long getUid() {

		return uid;
	}

	public void setUid(Long uid) {

		this.uid = uid;
	}

	public Long getCustomerUid() {

		return customerUid;
	}

	public void setCustomerUid(Long customerUid) {

		this.customerUid = customerUid;
	}

	public Long getCashierUid() {

		return cashierUid;
	}

	public void setCashierUid(Long cashierUid) {

		this.cashierUid = cashierUid;
	}

	public String getSn() {

		return sn;
	}

	public void setSn(String sn) {

		this.sn = sn;
	}

	public String getDatetime() {

		return datetime;
	}

	public void setDatetime(String datetime) {

		this.datetime = datetime;
	}

	public String getTotalAmount() {

		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {

		this.totalAmount = totalAmount;
	}

	public String getTotalProfit() {

		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {

		this.totalProfit = totalProfit;
	}

	public String getDiscount() {

		return discount;
	}

	public void setDiscount(String discount) {

		this.discount = discount;
	}

	public String getRounding() {

		return rounding;
	}

	public void setRounding(String rounding) {

		this.rounding = rounding;
	}

	public String getTicketType() {

		return ticketType;
	}

	public void setTicketType(String ticketType) {

		this.ticketType = ticketType;
	}

	public String getInvalid() {

		return invalid;
	}

	public void setInvalid(String invalid) {

		this.invalid = invalid;
	}

	public String getExternalOrderNo() {

		return externalOrderNo;
	}

	public void setExternalOrderNo(String externalOrderNo) {

		this.externalOrderNo = externalOrderNo;
	}

	public String getPayCode() {

		return payCode;
	}

	public void setPayCode(String payCode) {

		this.payCode = payCode;
	}

	public String getAmount() {

		return amount;
	}

	public void setAmount(String amount) {

		this.amount = amount;
	}

	public String getAppId() {

		return appId;
	}

	public void setAppId(String appId) {

		this.appId = appId;
	}
}
