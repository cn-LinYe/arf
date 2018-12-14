package com.arf.laundry.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(
		name = "l_laundry_category_price",
		uniqueConstraints={
				@UniqueConstraint(columnNames={"businessNum","categoryNum"},name="unique_businessNum_categoryNum"),
		}
		)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "l_laundry_category_price_sequence")
public class LaundryCategoryPrice extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态:0:正常,1:隐藏,2:删除
	 */
	public enum Status{
		Normal,
		Hidden,
		Deleted;
		
		public static Status get(int ordinal){
			Status status[] = Status.values();
			if(ordinal<0 || ordinal>status.length-1){
				return null;
			}
			return status[ordinal];
		}
	}
	
	private String categoryNum;//类目编号,唯一
	private String superCategoryNum;//所属大类,为null或者0即为大类
	private String superCategoryName;//所属大类名称
	private BigDecimal price;//单价
	private Byte status;//状态:0:正常,1:隐藏,2:删除
	private String categoryName;//类目名称
	private String img;//显示图片 ,单张
	private Integer orderIndex;//排序
	private String businessNum;//所属商户
	private String areaCode;//归属地区编码,可以具体到省/市/区, eg.441901
	
	@Column(length = 20)
	public String getCategoryNum() {
		return categoryNum;
	}
	@Column(length = 20)
	public String getSuperCategoryNum() {
		return superCategoryNum;
	}
	@Column(length = 20)
	public String getSuperCategoryName() {
		return superCategoryName;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPrice() {
		return price;
	}
	@Column(length = 4)
	public Byte getStatus() {
		return status;
	}
	@Column(length = 20)
	public String getCategoryName() {
		return categoryName;
	}
	@Column(length = 120)
	public String getImg() {
		return img;
	}
	@Column(length = 11)
	public Integer getOrderIndex() {
		return orderIndex;
	}
	@Column(length = 30)
	public String getBusinessNum() {
		return businessNum;
	}
	@Column(length = 20)
	public String getAreaCode() {
		return areaCode;
	}
	public void setCategoryNum(String categoryNum) {
		this.categoryNum = categoryNum;
	}
	public void setSuperCategoryNum(String superCategoryNum) {
		this.superCategoryNum = superCategoryNum;
	}
	public void setSuperCategoryName(String superCategoryName) {
		this.superCategoryName = superCategoryName;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
