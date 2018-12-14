package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "quan_prov_city_area")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "quan_prov_city_area_sequence")
public class QuanProvCityAreaModel extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1979690919172056325L;
	private int no;
	private String areaname;
	private int topno;
	private String areacode;
	private int arealevel;
	private String typename;
	// private int id;

	// 坐标
	private Float lng;
	private Float lat;

	public QuanProvCityAreaModel() {
		super();
	}

	public QuanProvCityAreaModel(int no, String areaname, int topno, String areacode, int arealevel, String typename) {
		super();
		this.no = no;
		this.areaname = areaname;
		this.topno = topno;
		this.areacode = areacode;
		this.arealevel = arealevel;
		this.typename = typename;
	}

	@Column(name = "no", length = 255, nullable = false)
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	@Column(name = "areaname", length = 255, nullable = false)
	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	@Column(name = "topno", length = 255, nullable = false)
	public int getTopno() {
		return topno;
	}

	public void setTopno(int topno) {
		this.topno = topno;
	}

	@Column(name = "areacode", length = 255, nullable = false)
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	@Column(name = "arealevel", length = 255, nullable = false)
	public int getArealevel() {
		return arealevel;
	}

	public void setArealevel(int arealevel) {
		this.arealevel = arealevel;
	}

	@Column(name = "typename", length = 255, nullable = false)
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "lng", nullable = true)
	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	@Column(name = "lat", nullable = true)
	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	
	
	// @Id
	// @Column(name = "id")
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// public int getId() {
	// return id;
	// }
	//
	// public void setId(int id) {
	// this.id = id;
	// }
}
