package com.arf.carbright.dto;

import com.arf.carbright.entity.PBusiness;

public class SearchBusinessDto extends PBusiness{
	private static final long serialVersionUID = 192823183482711741L;
	private double distance;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
