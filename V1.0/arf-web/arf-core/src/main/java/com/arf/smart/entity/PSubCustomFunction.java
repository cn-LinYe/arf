package com.arf.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.arf.smart.entity.PCustomFunction.IconType;
import com.arf.smart.entity.PCustomFunction.Status;

@Entity
@Table(name="p_sub_custom_function")
@SequenceGenerator(name="sequenceGenerator",sequenceName="p_sub_custom_function_sequence")
public class PSubCustomFunction extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4789704696430035327L;
	
	private String iconName;//	图标名称
	private Integer iconMark;//图标标示（标示为固定内容）
	private String iconUrl;//	图标图片路径
	private String iconHints;//图标提示
	private Status status;//图标状态（0 normal 正常 1 discard 废弃）
	private Integer iconOrder;//	图标排序（下发APP进行排序操作）
	private IconType iconType;//图标类型（0 normal 正常 1 hide 隐藏）
	private String description;//图标描述
	private Integer parentMark;//父级图标标识
	private String webviewUrl;//H5页面路径
	
	@Column(length=40)
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public Integer getIconMark() {
		return iconMark;
	}
	public void setIconMark(Integer iconMark) {
		this.iconMark = iconMark;
	}
	@Column(length=100)
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getIconHints() {
		return iconHints;
	}
	public void setIconHints(String iconHints) {
		this.iconHints = iconHints;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Integer getIconOrder() {
		return iconOrder;
	}
	public void setIconOrder(Integer iconOrder) {
		this.iconOrder = iconOrder;
	}
	public IconType getIconType() {
		return iconType;
	}
	public void setIconType(IconType iconType) {
		this.iconType = iconType;
	}
	@Column(length=500)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getParentMark() {
		return parentMark;
	}
	public void setParentMark(Integer parentMark) {
		this.parentMark = parentMark;
	}
	@Column(length=100)
	public String getWebviewUrl() {
		return webviewUrl;
	}
	public void setWebviewUrl(String webviewUrl) {
		this.webviewUrl = webviewUrl;
	}

}
