package com.arf.core.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ParkingFeeDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int RET_SUCCESS = 0;
	public static final int SOCKET_SERVER_EXCEPTION = 500;
	public static final int EXCEPTION = 501;
	public static final int EXECUTE_METHOD_WRONG = 601;

	private int ret;// 结果值
	private String msg;
	private BigDecimal totalFee;// 总费用
	private BigDecimal paidFee;// 已经支付的费用
	private BigDecimal unpaidFee;//未支付费用 = totalFee - paidFee
	private Long inTime;// 停车时长 单位:秒
	private Long parkingStartTime; // 停车开始时间戳 单位:秒
	private String communityNum;
	private String communityName;
	private String license;
	private String barrierGateOrderNo;//道闸生成的订单号
	
	private long parkingCarId; //停车记录表中的主键Id
	
	private String outTradeNo;//代支付订单号
	
	private BigDecimal escapeFee;//停车欠费

	/**
	 * 停车类型{@link com.arf.platform.entity.PParkingcar.StopType}
	 */
	private Integer stopType;

	public ParkingFeeDto() {

	}

	public ParkingFeeDto(int ret, String msg) {
		this.ret = ret;
		this.msg = msg;
	}

	/**
	 * @param ret
	 *            结果码 {@link #RET_SUCCESS} {@link #SOCKET_SERVER_EXCEPTION}
	 * @param msg
	 *            消息
	 * @param inTime
	 *            停车时长 单位:秒
	 * @param parkingStartTime
	 *            //停车开始时间戳 单位:秒
	 * @param communityNum
	 *            小区编号
	 * @param communityName
	 *            小区名
	 * @param license
	 *            车牌
	 * @param barrierGateOrderNo
	 *            道闸生成订单号
	 * @param unpaidFee
	 *            请求得到的停车费
	 */
	public ParkingFeeDto(int ret, String msg, Long inTime, Long parkingStartTime, String communityNum, String communityName,
			String license, String barrierGateOrderNo,BigDecimal unpaidFee) {
		this.ret = ret;
		this.msg = msg;
		this.inTime = inTime;
		this.parkingStartTime = parkingStartTime;
		this.communityNum = communityNum;
		this.communityName = communityName;
		this.license = license;
		this.barrierGateOrderNo = barrierGateOrderNo;
		if(unpaidFee != null)
			unpaidFee = unpaidFee.setScale(2, RoundingMode.DOWN);
		this.unpaidFee = unpaidFee;
	}

	public int getRet() {
		return ret;
	}

	public String getMsg() {
		return msg;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public BigDecimal getPaidFee() {
		return paidFee;
	}

	public Long getInTime() {
		return inTime;
	}

	public String getCommunityNum() {
		return communityNum;
	}

	public String getCommunityName() {
		return communityName;
	}

	public String getLicense() {
		return license;
	}

	public Integer getStopType() {
		return stopType;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setTotalFee(BigDecimal totalFee) {
		if(totalFee != null)
			totalFee = totalFee.setScale(2, RoundingMode.DOWN);
		this.totalFee = totalFee;
	}

	public void setPaidFee(BigDecimal paidFee) {
		if(paidFee != null)
			paidFee = paidFee.setScale(2, RoundingMode.DOWN);
		this.paidFee = paidFee;
	}

	public void setInTime(Long inTime) {
		this.inTime = inTime;
	}

	public void setCommunityNum(String communityNum) {
		this.communityNum = communityNum;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setStopType(Integer stopType) {
		this.stopType = stopType;
	}

	public long getParkingCarId() {
		return parkingCarId;
	}

	public void setParkingCarId(long parkingCarId) {
		this.parkingCarId = parkingCarId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Long getParkingStartTime() {
		return parkingStartTime;
	}

	public void setParkingStartTime(Long parkingStartTime) {
		this.parkingStartTime = parkingStartTime;
	}

	public String getBarrierGateOrderNo() {
		return barrierGateOrderNo;
	}

	public void setBarrierGateOrderNo(String barrierGateOrderNo) {
		this.barrierGateOrderNo = barrierGateOrderNo;
	}

	public BigDecimal getUnpaidFee() {
		return unpaidFee;
	}

	public void setUnpaidFee(BigDecimal unpaidFee) {
		if(unpaidFee != null)
			unpaidFee = unpaidFee.setScale(2, RoundingMode.DOWN);
		this.unpaidFee = unpaidFee;
	}

	public BigDecimal getEscapeFee() {
		return escapeFee;
	}

	public void setEscapeFee(BigDecimal escapeFee) {
		if(escapeFee != null){
			escapeFee = escapeFee.setScale(2, RoundingMode.DOWN);
		}
		this.escapeFee = escapeFee;
	}
	
	
}
