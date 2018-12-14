package com.arf.laundry.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(
		name = "l_laundry_category_price_tpl",
		uniqueConstraints={
				@UniqueConstraint(columnNames={"categoryNum"},name="unique_index_categoryNum")
		}
		)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "l_laundry_category_price_tpl_sequence")
public class LaundryCategoryPriceTpl extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态:0:正常,1:隐藏,2:删除
	 */
	public enum Status{
		Normal,
		Hidden,
		Deleted,
	}
	
	private String categoryNum;//类目编号,唯一
	private String superCategoryNum;//所属大类,为null或者0即为大类
	private BigDecimal price;//单价
	private Byte status;//状态:0:正常,1:隐藏,2:删除
	private String categoryName;//类目名称
	private String img;//显示图片 ,单张
	private Integer orderIndex;//排序
	
	public String getCategoryNum() {
		return categoryNum;
	}
	public String getSuperCategoryNum() {
		return superCategoryNum;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public Byte getStatus() {
		return status;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public String getImg() {
		return img;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setCategoryNum(String categoryNum) {
		this.categoryNum = categoryNum;
	}
	public void setSuperCategoryNum(String superCategoryNum) {
		this.superCategoryNum = superCategoryNum;
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
	
}
