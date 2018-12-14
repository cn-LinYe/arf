package com.arf.silverleopard.entity;

import com.arf.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "p_sales_receipts_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_sales_receipts_item_sequence")
public class SalesReceiptsItem  extends BaseEntity<Long>{

	private static final long serialVersionUID = -4471112176582799480L;
	
	/** 单据条目商品名称  */
	private String name;
	/**  单据条目商品进货价 */
	private String buyPrice;
	/**  单据条目商品销售价 */
	private String sellPrice;
	/** 单据条目商品会员价 */
	private String customerPrice;
	/** 单据条目销售的商品数量，如3.5箱  */
	private String quantity;
	/**  单据条目所打的折扣 */
	private String discount;
	/** 单据条目所打的会员折扣  */
	private String customerDiscount;
	/** 单据条目总价  */
	private String totalAmount;
	/** 单据条目总利润  */
	private String totalProfit;
	/**  数据为1时表求享受了会员折扣 */
	private String isCustomerDiscount;
	/**  单据条目所对应的商品唯一标识 */
	private Long productUid;
	/**  单据唯一标识 */
	private Long SalesReceiptsUid;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getBuyPrice() {

		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {

		this.buyPrice = buyPrice;
	}

	public String getSellPrice() {

		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {

		this.sellPrice = sellPrice;
	}

	public String getCustomerPrice() {

		return customerPrice;
	}

	public void setCustomerPrice(String customerPrice) {

		this.customerPrice = customerPrice;
	}

	public String getQuantity() {

		return quantity;
	}

	public void setQuantity(String quantity) {

		this.quantity = quantity;
	}

	public String getDiscount() {

		return discount;
	}

	public void setDiscount(String discount) {

		this.discount = discount;
	}

	public String getCustomerDiscount() {

		return customerDiscount;
	}

	public void setCustomerDiscount(String customerDiscount) {

		this.customerDiscount = customerDiscount;
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

	public String getIsCustomerDiscount() {

		return isCustomerDiscount;
	}

	public void setIsCustomerDiscount(String isCustomerDiscount) {

		this.isCustomerDiscount = isCustomerDiscount;
	}

	public Long getProductUid() {

		return productUid;
	}

	public void setProductUid(Long productUid) {

		this.productUid = productUid;
	}

	public Long getSalesReceiptsUid() {

		return SalesReceiptsUid;
	}

	public void setSalesReceiptsUid(Long salesReceiptsUid) {

		SalesReceiptsUid = salesReceiptsUid;
	}
}
