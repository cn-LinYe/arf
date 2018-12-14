package com.arf.gas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "refuel_gas_station")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_station_sequence")
public class RefuelGasStation extends BaseEntity<Long>{

	/**
	 * 加油家油站表
	 */
	private static final long serialVersionUID = 87768456966505803L;
	
	private Integer gasNum	;//int	否		普通	油站编号
	private String gasName;//	varchar(50)	否		普通	油站名称
	private Integer businessNum;//int	否		普通	商户编号
	private GasStatus gasStatus;//否油站状态（0、正常使用 Normal_Use 1、Prohibited_Use 禁止使用 2、Lack_Oil 油量不足 3、Abnormal 异常 4、已冻结 Freeze）
	private OilStatus oilStatus ;/*//加油状态（0、随时可加 Ready 1、排队10分钟 Queue_Ten_Minutes 2、排队20分钟 Queue_Twenty_Minutes
							3、接受待客加油 Accept_Hospitality 4、今天歇业 Closed_Today 5、今天无油 Not_Oil_Today） 此状态可以缓存redis不用采用数据库存储*/
	private Integer rewardAmount;//给油站的奖励金额
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum GasStatus{
		 Normal_Use ,Prohibited_Use,Lack_Oil ,Abnormal,Freeze
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OilStatus{
		Ready,Queue_Ten_Minutes,Queue_Twenty_Minutes,Accept_Hospitality,Closed_Today,Not_Oil_Today;
	}

	

	public Integer getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Integer rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public Integer getGasNum() {
		return gasNum;
	}

	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}
	@Column(length = 50)
	public String getGasName() {
		return gasName;
	}

	public void setGasName(String gasName) {
		this.gasName = gasName;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public GasStatus getGasStatus() {
		return gasStatus;
	}

	public void setGasStatus(GasStatus gasStatus) {
		this.gasStatus = gasStatus;
	}

	public OilStatus getOilStatus() {
		return oilStatus;
	}

	public void setOilStatus(OilStatus oilStatus) {
		this.oilStatus = oilStatus;
	}
	
}
