package com.arf.core.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 道路救援信息
 * @author Administrator
 */

@Entity
@Table(name = "rodeRescue")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "rodeRescue_sequence")
public class RodeRescueModel extends BaseEntity<Long>{

//	private int id;
	

	/**
     * 
     */
    private static final long serialVersionUID = 3491647120457346295L;

    /**
	 * 车牌
	 */
	private String licensePlateNumber="";
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 *联系电话
	 */
	private String contentPhone;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 地区
	 */
	private String area;
	/**
	 * 事故类型
	 */
	private String rescueType;
	/**
	 * 地区
	 */
	private int no;
	/**
	 * 横坐标
	 */
	private String abscissa;
	/**
	 * 纵坐标
	 */
	private String orzinate;
	
	/**
	 * 发送时间
	 */
	private Date time;
	/**
	 * 是否处理
	 */
	private boolean isHandle=false;
	/**
	 *需要处理的子公司id
	 * @return
	 */
	private int branchId=0;
	
	
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	
	@Column(name = "licensePlateNumber",  nullable = false)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	@Column(name = "contentPhone")
	public String getContentPhone() {
		return contentPhone;
	}
	public void setContentPhone(String contentPhone) {
		this.contentPhone = contentPhone;
	}
	@Column(name = "province")
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "area")
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "rescueType",length=1024)
	public String getRescueType() {
		return rescueType;
	}
	public void setRescueType(String rescueType) {
		this.rescueType = rescueType;
	}
	@Column(name = "no")
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	@Column(name = "abscissa")
	public String getAbscissa() {
		return abscissa;
	}
	public void setAbscissa(String abscissa) {
		this.abscissa = abscissa;
	}
	@Column(name = "orzinate")
	public String getOrzinate() {
		return orzinate;
	}
	public void setOrzinate(String orzinate) {
		this.orzinate = orzinate;
	}
	@Column(name = "userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "time")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Column(name = "isHandle")
	public boolean isHandle() {
		return isHandle;
	}
	public void setHandle(boolean isHandle) {
		this.isHandle = isHandle;
	}
	@Column(name = "branchId",nullable = false)
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public RodeRescueModel(String licensePlateNumber, String userName, String contentPhone, String province,
			String city, String rescueType, String abscissa, String orzinate, Date time, boolean isHandle,
			int branchId) {
		super();
		this.licensePlateNumber = licensePlateNumber;
		this.userName = userName;
		this.contentPhone = contentPhone;
		this.province = province;
		this.city = city;
		this.rescueType = rescueType;
		this.abscissa = abscissa;
		this.orzinate = orzinate;
		this.time = time;
		this.isHandle = isHandle;
		this.branchId = branchId;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
