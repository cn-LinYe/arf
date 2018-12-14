package com.arf.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name="p_custom_icon")
@SequenceGenerator(name="sequenceGenerator",sequenceName="p_custom_icon_sequence")
public class PCustomIcon extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6363451598337606275L;
	
	private String iconName;//图标名称
	private IconType iconType;//图标类型（1、月租缴费 2、物业缴费 3、点滴洗 4、e停车 5、安心停车卡 6、安心点 7、 洗衣帮帮 8、车保超市 9、快递驿站 10、社区农场 11、违章查询 12、门禁）
	private Integer iconOrder;//图标排序（下发APP进行排序操作）
	
	public enum IconType{
		NotUse,
		MonthlyRent_payment,//1、月租缴费
		Property_payment,//2、物业缴费 
		Carbright,// 3、点滴洗 
		EParking,//4、e停车 
		AXGoldCard,//5、安心停车卡
		AXD,//6、安心点 
		Laundry,//7、 洗衣帮帮 
		Insurance,//8、车保超市
		Express,//9、快递驿站
		CommunityFarms,// 10、社区农场
		Violation,//11、违章查询 
		Access;//12、门禁
	}

	@Column(length=20)
	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public IconType getIconType() {
		return iconType;
	}

	public void setIconType(IconType iconType) {
		this.iconType = iconType;
	}

	public Integer getIconOrder() {
		return iconOrder;
	}

	public void setIconOrder(Integer iconOrder) {
		this.iconOrder = iconOrder;
	}

}
