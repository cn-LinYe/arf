package com.arf.core.oldws;

import java.util.List;

public class IllegalInformation {
	private  final static IllegalInformation mIllegalInformation = new IllegalInformation();
	
	private String jsonStr;
	
	private Boolean Success;
	private Integer ErrorCode;
    private String ErrMessage;
    private Boolean HasData;
    private String LastSearchTime;
    private List<ViolationRecord> Records;
    
    public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public Boolean getSuccess() {
		return Success;
	}

	public void setSuccess(Boolean success) {
		Success = success;
	}

	public Integer getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(Integer errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrMessage() {
		return ErrMessage;
	}

	public void setErrMessage(String errMessage) {
		ErrMessage = errMessage;
	}

	public Boolean getHasData() {
		return HasData;
	}

	public void setHasData(Boolean hasData) {
		HasData = hasData;
	}

	public String getLastSearchTime() {
		return LastSearchTime;
	}

	public void setLastSearchTime(String lastSearchTime) {
		LastSearchTime = lastSearchTime;
	}

	public List<ViolationRecord> getRecords() {
		return Records;
	}

	public void setRecords(List<ViolationRecord> records) {
		Records = records;
	}

	public static ViolationRecord getNewViolationRecord(){
		return mIllegalInformation.new ViolationRecord();
	}
	
	public class ViolationRecord{

    	private String Time;
    	private String Location;
    	private String Reason;
    	private String count;
    	private String status;
    	private String department;
    	private String Degree;
		public String getTime() {
			return Time;
		}
		public void setTime(String time) {
			Time = time;
		}
		public String getLocation() {
			return Location;
		}
		public void setLocation(String location) {
			Location = location;
		}
		public String getReason() {
			return Reason;
		}
		public void setReason(String reason) {
			Reason = reason;
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getDegree() {
			return Degree;
		}
		public void setDegree(String degree) {
			Degree = degree;
		}
    
    	
    }

}
