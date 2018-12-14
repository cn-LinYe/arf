package com.arf.gift.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "gift_goods_info",
		uniqueConstraints={@UniqueConstraint(columnNames="sku")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gift_goods_info_sequence")
public class GiftGoodsInfo extends BaseEntity<Long>{
	private static final long serialVersionUID = 2190511410182071139L;
	
	public static final String Prefix_Cache_Exchanged_Count = "GiftGoodsInfo.Exchanged_Count:";
	
	private String giftName;//礼品名称
	private String sku;//SKU编码
	private BigDecimal marketPrice	;//decimal(12,2)	not null	市场价
	private BigDecimal discountPrice	;//decimal(12,2)	not null	折扣价格
	private String displayImg;//轮播图片
	private String previewImg;//预览图片
	private String detailsInfo;//商品详情,富文本内容
	private Status status;//状态枚举:DISABLED 0 失效NORMAL 1 正常
	private int stock;//库存数量,数量为0时status置为0
	private String communityNumber;//小区编号
	
	/**
	 * 是否虚拟礼品,true:虚拟礼品,例如线下红包;false:实物礼品.如果是虚拟礼品则不能和实物礼品一起兑换,且走线下通道领取
	 */
	private Boolean virtualGifts;
	
	public enum Status{
		DISABLED,NORMAL;
	}
	@Column(length=20,nullable=false)
	public String getGiftName() {
		return giftName;
	}
	@Column(length=10,nullable=false)
	public String getSku() {
		return sku;
	}
	@Column(precision=10,scale=2,nullable=false)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	@Column(precision=10,scale=2,nullable=false)
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	@Column(length=1000)
	public String getDisplayImg() {
		return displayImg;
	}
	@Type(type = "text")
	@Column(length=2147483647)
	public String getDetailsInfo() {
		return detailsInfo;
	}
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	@Column(nullable=false)
	public int getStock() {
		return stock;
	}
	@Column(length=2000)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length=100)
	public String getPreviewImg() {
		return previewImg;
	}
	public void setPreviewImg(String previewImg) {
		this.previewImg = previewImg;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public void setDisplayImg(String displayImg) {
		this.displayImg = displayImg;
	}

	public void setDetailsInfo(String detailsInfo) {
		this.detailsInfo = detailsInfo;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Boolean getVirtualGifts() {
		return virtualGifts;
	}
	public void setVirtualGifts(Boolean virtualGifts) {
		this.virtualGifts = virtualGifts;
	}
}
