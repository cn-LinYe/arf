package com.arf.laundry.dto;

import org.springframework.beans.BeanUtils;

import com.arf.laundry.entity.LaundryOrder;
import com.arf.review.entity.ServiceReview;

public class UserOrderDto extends LaundryOrder {

	private static final long serialVersionUID = 1L;
	
	private String businessName;
	private String businessPic;
	private String businessPhone;
	private boolean userGraded;
	private Integer gradeScore;
	private String gradeRemark;
	private ServiceReview serviceReview;
	
	public UserOrderDto(){}
	
	public UserOrderDto(LaundryOrder laundryOrder) {
		BeanUtils.copyProperties(laundryOrder, this);
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getBusinessPic() {
		return businessPic;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setBusinessPic(String businessPic) {
		this.businessPic = businessPic;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public boolean isUserGraded() {
		return userGraded;
	}

	public Integer getGradeScore() {
		return gradeScore;
	}

	public String getGradeRemark() {
		return gradeRemark;
	}

	public void setUserGraded(boolean userGraded) {
		this.userGraded = userGraded;
	}

	public void setGradeScore(Integer gradeScore) {
		this.gradeScore = gradeScore;
	}

	public void setGradeRemark(String gradeRemark) {
		this.gradeRemark = gradeRemark;
	}

	public ServiceReview getServiceReview() {
		return serviceReview;
	}

	public void setServiceReview(ServiceReview serviceReview) {
		this.serviceReview = serviceReview;
	}
	
	

}
