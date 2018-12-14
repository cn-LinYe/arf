package com.arf.base;

public class BaseSearchForm {

	private int operation;
	private int pageNo=1;
	private int pageSize=20;
	private int startRowNum;
	private boolean usePage =true;//是否分页
	
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}

	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		if(pageNo<1)
			this.pageNo = 1;
		else
			this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isUsePage(){
		return usePage;
	}
	
	public void setUsePage(boolean usePage){
		this.usePage = usePage;
	}
	
	public int getStartRowNum(){
		return (pageNo-1)*pageSize;
	}

	public void reset() {
		this.operation = 0;
		this.pageNo = 1;
		this.pageSize = 0;
	}
	
	public static String trimString(String getString) {
		if (getString != null && getString.length() != 0) {
			return getString.trim();
		} else {
			return getString;
		}
	}
	

}
