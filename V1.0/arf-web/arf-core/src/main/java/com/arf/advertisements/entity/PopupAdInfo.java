package com.arf.advertisements.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="button_ad_popup_info")
@SequenceGenerator(name="sequenceGenerator",sequenceName="button_ad_popup_info_sequence")
public class PopupAdInfo extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2632899390675624440L;
	
	private String popupNum;//弹窗广告编号
	private String popupName;//弹窗广告名称
	private PopupType popupType;/*弹窗广告位置0、home 首页1、personal 个人中心2、Carbright点滴洗
								3、EParking e停车4、Property_payment物业缴费5、MonthlyRent_payment月租缴费
								6、AXGoldCard安心停车卡7、Insurance车保超市8、AXD安心店9、CommunityFarms社区农场
								10、Express快递驿站11、gasRefule家加油12、vouchers卡券13、integral积分
								14、wallet钱包15、myOrder我的订单16、address收货地址17、roomAccess房屋门禁
								18、lock门锁19、car车辆20、vioce语音识别21、axdOrder安心店订单
								22、   ecc ECC会员服务23、help 帮助与反馈24、share分享安心点*/
	private String imgUrl;//弹窗广告图片路径
	private LinkType linkType;//是否跳转0 not_link不跳转1跳转link
	private PopupStatus popupStatus;//按钮状态0 not_use未开始 1using进行中2 end已结束
	private Date popupStartTime;//有效开始时间
	private Date popupEndTime;//有效截止时间
	private String excludeCities;//可见城市（城市编号，以“,”分割）
	private String startTime;//每天开始时间（存小时）
	private String endTime;//每天截止时间（存小时）
	private String remark;//备注
	private ClickType clickType;/*跳转类型（0、 custom 自定义1、home 首页2、personal 个人中心3、Carbright点滴洗
										4、EParking e停车5、Property_payment物业缴费6、MonthlyRent_payment月租缴费
										7、AXGoldCard安心停车卡8、Insurance车保超市9、AXD安心店10、CommunityFarms社区农场
										11、Express快递驿站12、gasRefule家加油13、vouchers卡券14、integral积分
										15、wallet钱包16、myOrder我的订单17、address收货地址18、roomAccess房屋门禁
										19、lock门锁20、car车辆21、vioce语音识别22、axdOrder安心店订单
										23、   ecc ECC会员服务24、help 帮助与反馈25、share分享安心点）*/
	private String clickLinkUrl;//跳转链接
	private OpenType openType;//弹出方式0 onlyOne只弹一次 1 custom自定义
	private Integer intervalTime;//弹窗时间间隔
	private String communities;//广告可见小区
	private String excludeCommunities;//广告不可见小区
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PopupType{
		home,personal,carbright,eParking,
		property_payment,monthlyRent_payment,aXGoldCard,
		insurance,axd,communityFarms,express,gasRefule,
		vouchers,integral,wallet,myOrder,address,roomAccess,
		lock,car,vioce,axdOrder,ecc,help,share;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum LinkType{
		notLink,link;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PopupStatus{
		normal,unShelves;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ClickType{
		custom,home,personal,carbright,eParking,
		property_payment,monthlyRent_payment,aXGoldCard,
		insurance,axd,communityFarms,express,gasRefule,
		vouchers,integral,wallet,myOrder,address,roomAccess,
		lock,car,vioce,axdOrder,ecc,help,share;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OpenType{
		onlyOne,custom;
	}
	@Column(length=10)
	public String getPopupNum() {
		return popupNum;
	}
	public void setPopupNum(String popupNum) {
		this.popupNum = popupNum;
	}
	@Column(length=32)
	public String getPopupName() {
		return popupName;
	}
	public void setPopupName(String popupName) {
		this.popupName = popupName;
	}
	public PopupType getPopupType() {
		return popupType;
	}
	public void setPopupType(PopupType popupType) {
		this.popupType = popupType;
	}
	@Column(length=100)
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public LinkType getLinkType() {
		return linkType;
	}
	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}
	public PopupStatus getPopupStatus() {
		return popupStatus;
	}
	public void setPopupStatus(PopupStatus popupStatus) {
		this.popupStatus = popupStatus;
	}
	public Date getPopupStartTime() {
		return popupStartTime;
	}
	public void setPopupStartTime(Date popupStartTime) {
		this.popupStartTime = popupStartTime;
	}
	public Date getPopupEndTime() {
		return popupEndTime;
	}
	public void setPopupEndTime(Date popupEndTime) {
		this.popupEndTime = popupEndTime;
	}
	public String getExcludeCities() {
		return excludeCities;
	}
	public void setExcludeCities(String excludeCities) {
		this.excludeCities = excludeCities;
	}
	@Column(length=10)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(length=10)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(length=150)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ClickType getClickType() {
		return clickType;
	}
	public void setClickType(ClickType clickType) {
		this.clickType = clickType;
	}
	@Column(length=100)
	public String getClickLinkUrl() {
		return clickLinkUrl;
	}
	public void setClickLinkUrl(String clickLinkUrl) {
		this.clickLinkUrl = clickLinkUrl;
	}
	public OpenType getOpenType() {
		return openType;
	}
	public void setOpenType(OpenType openType) {
		this.openType = openType;
	}
	public Integer getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}
	@Lob
	public String getCommunities() {
		return communities;
	}
	public void setCommunities(String communities) {
		this.communities = communities;
	}
	@Lob
	public String getExcludeCommunities() {
		return excludeCommunities;
	}
	public void setExcludeCommunities(String excludeCommunities) {
		this.excludeCommunities = excludeCommunities;
	}

}
