package com.arf.plugin.pack;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class PackageViolation {
	private boolean HasData; // true,是否有违章。是 True 否 False
	private int ErrorCode;// 0 正常；其他为发生错误，请参考附录3
	private boolean Success;// 查询状态码： True成功（只能代表通讯层，业务层面请参考ErrorCode） False 失败
	private String ErrMessage;// 错误数据
	private String ResultType = "实时数据";// 实时数据
	private String LastSearchTime;// yyyy-MM-dd HH:mm:SS
	private String Other;
	private List<ViolationRecord> Records;// 违章数据

	@JSONField(name ="HasData")
	public boolean isHasData() {
		return HasData;
	}

	public void setHasData(boolean hasData) {
		HasData = hasData;
	}

	@JSONField(name ="ErrorCode")
	public int getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}

	@JSONField(name ="Success")
	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	@JSONField(name ="ErrMessage")
	public String getErrMessage() {
		return ErrMessage;
	}

	public void setErrMessage(String errMessage) {
		ErrMessage = errMessage;
	}
	
	@JSONField(name ="ResultType")
	public String getResultType() {
		return ResultType;
	}

	public void setResultType(String resultType) {
		ResultType = resultType;
	}
	
	@JSONField(name ="LastSearchTime")
	public String getLastSearchTime() {
		return LastSearchTime;
	}

	public void setLastSearchTime(String lastSearchTime) {
		LastSearchTime = lastSearchTime;
	}

	@JSONField(name ="Other")
	public String getOther() {
		return Other;
	}

	public void setOther(String other) {
		Other = other;
	}

	@JSONField(name ="Records")
	public List<ViolationRecord> getRecords() {
		return Records;
	}

	public void setRecords(List<ViolationRecord> records) {
		Records = records;
	}
	
	public static class ViolationRecord {
		private String Time;// 违章时间
		private String Location;// 违章地点
		private String Reason;// 违章原因
		private String count;// 违章罚款金额
		private String status;// 违章记录状态0 末处理 1 己处理(绝大部分情况下，车行易只能返回未处理的违章)
		private String department;// 违章采集机关
		private String Degree;// 违章扣分
		private String Code;// 违章代码
		private String Archive;// 违章项文书编号
		private String Telephone;// 联系电话
		private String Excutelocation;// 违章处理地址
		private String Excutedepartment;// 执行部门
		private String Category;// 违章分类类别（如该字段显示为“现场单”，请注意提示用户）
		private String Latefine;// 滞纳金（目前都是0，车行易不做计算）
		private String Punishmentaccording;// 处罚依据
		private String Illegalentry;// 违法条款
		private String Locationid;// 违章归属地点ID
		private String LocationName;// 违章归属地点名
		private String DataSourceID;// 查询数据源ID
		private String RecordType;// 实时数据/历史数据
		private String Poundage;// 手续费（标准价，合作方请无视）
		private String CanProcess = "0";// 是否可以代办 0 不可以 1 可以
		private String UniqueCode;// 违章信息的特征码（相同车牌，违章时间，地点，该值相同）
		private int SecondaryUniqueCode;// 违章记录ID，用于下单。
		private int DegreePoundage;// 扣分手续费
		private String Other;// 预留
		private String CanprocessMsg;// 是否代办原因

		@JSONField(name ="Time")
		public String getTime() {
			return Time;
		}

		public void setTime(String time) {
			Time = time;
		}

		@JSONField(name ="Location")
		public String getLocation() {
			return Location;
		}

		public void setLocation(String location) {
			Location = location;
		}

		@JSONField(name ="Reason")
		public String getReason() {
			return Reason;
		}

		public void setReason(String reason) {
			Reason = reason;
		}

		@JSONField(name ="count")
		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		@JSONField(name ="status")
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@JSONField(name ="department")
		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		@JSONField(name ="Degree")
		public String getDegree() {
			return Degree;
		}

		public void setDegree(String degree) {
			Degree = degree;
		}

		@JSONField(name ="Code")
		public String getCode() {
			return Code;
		}

		public void setCode(String code) {
			Code = code;
		}

		@JSONField(name ="Archive")
		public String getArchive() {
			return Archive;
		}

		public void setArchive(String archive) {
			Archive = archive;
		}

		@JSONField(name ="Telephone")
		public String getTelephone() {
			return Telephone;
		}

		public void setTelephone(String telephone) {
			Telephone = telephone;
		}

		@JSONField(name ="Excutelocation")
		public String getExcutelocation() {
			return Excutelocation;
		}

		public void setExcutelocation(String excutelocation) {
			Excutelocation = excutelocation;
		}
		
		@JSONField(name ="Excutedepartment")
		public String getExcutedepartment() {
			return Excutedepartment;
		}

		public void setExcutedepartment(String excutedepartment) {
			Excutedepartment = excutedepartment;
		}

		@JSONField(name ="Category")
		public String getCategory() {
			return Category;
		}

		public void setCategory(String category) {
			Category = category;
		}
		
		@JSONField(name ="Latefine")
		public String getLatefine() {
			return Latefine;
		}

		public void setLatefine(String latefine) {
			Latefine = latefine;
		}

		@JSONField(name ="Punishmentaccording")
		public String getPunishmentaccording() {
			return Punishmentaccording;
		}

		public void setPunishmentaccording(String punishmentaccording) {
			Punishmentaccording = punishmentaccording;
		}

		@JSONField(name ="Illegalentry")
		public String getIllegalentry() {
			return Illegalentry;
		}

		public void setIllegalentry(String illegalentry) {
			Illegalentry = illegalentry;
		}

		@JSONField(name ="Locationid")
		public String getLocationid() {
			return Locationid;
		}

		public void setLocationid(String locationid) {
			Locationid = locationid;
		}

		@JSONField(name ="LocationName")
		public String getLocationName() {
			return LocationName;
		}

		public void setLocationName(String locationName) {
			LocationName = locationName;
		}

		@JSONField(name ="DataSourceID")
		public String getDataSourceID() {
			return DataSourceID;
		}

		public void setDataSourceID(String dataSourceID) {
			DataSourceID = dataSourceID;
		}

		@JSONField(name ="RecordType")
		public String getRecordType() {
			return RecordType;
		}

		public void setRecordType(String recordType) {
			RecordType = recordType;
		}

		@JSONField(name ="Poundage")
		public String getPoundage() {
			return Poundage;
		}

		public void setPoundage(String poundage) {
			Poundage = poundage;
		}

		@JSONField(name ="CanProcess")
		public String getCanProcess() {
			return CanProcess;
		}

		public void setCanProcess(String canProcess) {
			CanProcess = canProcess;
		}

		@JSONField(name ="UniqueCode")
		public String getUniqueCode() {
			return UniqueCode;
		}

		public void setUniqueCode(String uniqueCode) {
			UniqueCode = uniqueCode;
		}

		@JSONField(name ="SecondaryUniqueCode")
		public int getSecondaryUniqueCode() {
			return SecondaryUniqueCode;
		}

		public void setSecondaryUniqueCode(int secondaryUniqueCode) {
			SecondaryUniqueCode = secondaryUniqueCode;
		}

		@JSONField(name ="DegreePoundage")
		public int getDegreePoundage() {
			return DegreePoundage;
		}

		public void setDegreePoundage(int degreePoundage) {
			DegreePoundage = degreePoundage;
		}

		@JSONField(name ="Other")
		public String getOther() {
			return Other;
		}

		public void setOther(String other) {
			Other = other;
		}

		@JSONField(name ="CanprocessMsg")
		public String getCanprocessMsg() {
			return CanprocessMsg;
		}

		public void setCanprocessMsg(String canprocessMsg) {
			CanprocessMsg = canprocessMsg;
		}
	}
}


