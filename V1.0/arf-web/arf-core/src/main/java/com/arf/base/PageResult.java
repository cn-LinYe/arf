package com.arf.base;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {

    private List<T> list;// 保存结果集

    private int totalNum;// 总记录数
    
    public PageResult(List<T> list,int totalNum){
    	this.list = list;
    	this.totalNum = totalNum;
    }
    public PageResult(){
    }
	public List<T> getList() {
		return list == null ? new ArrayList<T>() : list;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
    
}
