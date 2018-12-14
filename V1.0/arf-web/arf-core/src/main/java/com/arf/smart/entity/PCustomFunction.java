package com.arf.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="p_custom_function")
@SequenceGenerator(name="sequenceGenerator",sequenceName="p_custom_function_sequence")
public class PCustomFunction extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1186303602227957252L;
	
	private String iconName;//图标名称
	private Integer iconMark;//图标标示（标示为固定内容）
	private String bigIcon;//大图标
	private String smallIcon;//小图标
	private String iconHints;//图标提示
	private IconType iconType;//图标类型（0 normal 正常 1 hide 隐藏）
	private Status status;//图标状态（0 normal 正常 1 discard 废弃）
	private CustomType customType;//自定义类型（0 general 通用 1bigIcon 大图标 2 smallIcon 小图标 3 horizontalIcon 横图标）
	private Integer iconOrder;//图标排序（下发APP进行排序操作）
	private String horizontalIcon;//横图标
	private String description;//图片描述
	
	private String webviewUrl;//跳转URL
	
	private String cities;//可见城市
	private String exculudeCities;//不可见城市
	
	private String communities;//可见小区
	private String exculudeCommunities;//不可见小区
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IconType{
		normal,//0 normal 正常
		hide;// 1 hide 隐藏
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		normal,//0 normal 正常 
		discard;//1 discard 废弃
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CustomType{
		general,//0 general 通用 
		bigIcon,//1 bigIcon 大图标 
		smallIcon,//2 smallIcon 小图标
		horizontalIcon,//3 horizontalIcon 横图标
		;
	}

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
	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}

	@Column(length=100)
	public String getSmallIcon() {
		return smallIcon;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public String getIconHints() {
		return iconHints;
	}

	@Column(length=40)
	public void setIconHints(String iconHints) {
		this.iconHints = iconHints;
	}

	public IconType getIconType() {
		return iconType;
	}

	public void setIconType(IconType iconType) {
		this.iconType = iconType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public CustomType getCustomType() {
		return customType;
	}

	public void setCustomType(CustomType customType) {
		this.customType = customType;
	}

	public Integer getIconOrder() {
		return iconOrder;
	}

	public void setIconOrder(Integer iconOrder) {
		this.iconOrder = iconOrder;
	}

	@Column(length=100)
	public String getHorizontalIcon() {
		return horizontalIcon;
	}

	public void setHorizontalIcon(String horizontalIcon) {
		this.horizontalIcon = horizontalIcon;
	}

	@Column(length=100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length=200)
	public String getWebviewUrl() {
		return webviewUrl;
	}

	public void setWebviewUrl(String webviewUrl) {
		this.webviewUrl = webviewUrl;
	}
	@Column(length=2000)
	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}
	@Column(length=2000)
	public String getExculudeCities() {
		return exculudeCities;
	}

	public void setExculudeCities(String exculudeCities) {
		this.exculudeCities = exculudeCities;
	}
	@Column(length=4000)
	public String getCommunities() {
		return communities;
	}

	public void setCommunities(String communities) {
		this.communities = communities;
	}
	@Column(length=4000)
	public String getExculudeCommunities() {
		return exculudeCommunities;
	}

	public void setExculudeCommunities(String exculudeCommunities) {
		this.exculudeCommunities = exculudeCommunities;
	}
}
