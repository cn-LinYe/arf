package com.arf.gas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "refuel_gas_member_data_change")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_member_data_change_sequence")
public class RefuelGasMemberDataChange extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 457892252915737730L;
	
	private String userName;//会员账号信息
	private String drivingPhoto;//行驶证照片（存在多张）数组形式存储
	private String licensePlateNumber;//车牌信息（多条记录，需保证与行驶证一一对应）
	private String changeResult;//变更审核结果
	private ChangeStatus changeStatus;/*//（0、Not_Submitted待提交资料 1、Review_Passed 审核通过 2、Change_Data_Submitted 
											变更资料已提交 3、 变更资料申请被驳回 Change_Review_Rejected 4、资料申请通过 Change_Review_Passed 5、Second_Data_Submitted 二审资料已提交
	 									6、二审驳回（待提交资料） Second_Review_Rejected ）*/
	private Integer gasNum;//加油站编号
	private Integer businessNum;//	商户信息
	private String secondReviewResult;//二审结果
	private String protocolPhoto;//协议照片
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ChangeStatus{
		Not_Submitted,//待提交资料
		Review_Passed,// 审核通过
		Change_Data_Submitted,// 变更资料已提交
		Change_Review_Rejected,//变更资料申请被驳回 
		Change_Review_Passed,//变更资料申请通过
		Second_Data_Submitted,//二审资料已提交
		Second_Review_Rejected;//二审驳回（待提交资料）
	}

	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length=200)
	public String getDrivingPhoto() {
		return drivingPhoto;
	}

	public void setDrivingPhoto(String drivingPhoto) {
		this.drivingPhoto = drivingPhoto;
	}

	@Column(length=200)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	@Column(length=100)
	public String getChangeResult() {
		return changeResult;
	}

	public void setChangeResult(String changeResult) {
		this.changeResult = changeResult;
	}

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}

	public Integer getGasNum() {
		return gasNum;
	}

	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	@Column(length=100)
	public String getSecondReviewResult() {
		return secondReviewResult;
	}

	public void setSecondReviewResult(String secondReviewResult) {
		this.secondReviewResult = secondReviewResult;
	}

	@Column(length=100)
	public String getProtocolPhoto() {
		return protocolPhoto;
	}

	public void setProtocolPhoto(String protocolPhoto) {
		this.protocolPhoto = protocolPhoto;
	}

}
