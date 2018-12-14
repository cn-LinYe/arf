package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 车保服务类型表
 * @author Administrator
 *
 */
@Entity
@Table(name = "carbaoservicemodel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "carbaoservicemodel_sequence")
public class CarBaoServiceModel extends BaseEntity<Long>{

//	private int id;
	
    private static final long serialVersionUID = 4425893023586587250L;

    private int levle;// 服务类型123456
	
	private int topno;

	private int no;

	private String companyName;// 公司名称

	private String price;// 服务价格

	private String contact;// 联系人
	
	private String contactPhone;// 联系人电话

	private String address;// 服务地址

	private String service_phone;// 服务电话

	private String intence;// 坐标点
	
	private String levelName;//选择的类型代表的服务
	
	private String servicePoint;//所属服务点

	public CarBaoServiceModel(int levle, int topno, int no, String companyName, String price, String contact,
			String contactPhone, String address, String service_phone, String intence, String levelName,
			String servicePoint) {
		super();
		this.levle = levle;
		this.topno = topno;
		this.no = no;
		this.companyName = companyName;
		this.price = price;
		this.contact = contact;
		this.contactPhone = contactPhone;
		this.address = address;
		this.service_phone = service_phone;
		this.intence = intence;
		this.levelName = levelName;
		this.servicePoint = servicePoint;
	}

	public CarBaoServiceModel() {
		super();
	}

//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	@Column(name = "no", length = 8, nullable = false)
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	
	@Column(name = "topno", length = 8, nullable = false)
	public int getTopno() {
		return topno;
	}

	public void setTopno(int topno) {
		this.topno = topno;
	}

	
	@Column(name = "levle", length = 8, nullable = false)
	public int getLevle() {
		return levle;
	}

	public void setLevle(int levle) {
		this.levle = levle;
	}

	@Column(name = "companyName", length = 16, nullable = true)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
	@Column(name = "price", length = 16, nullable = true)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "contactPhone", length = 16, nullable = true)
	public String getContactPhone() {
		return contactPhone;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	
	@Column(name = "address", length = 32, nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "service_phone", length = 16, nullable = true)
	public String getService_phone() {
		return service_phone;
	}

	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	
	
	@Column(name = "intence", length = 16, nullable = true)
	public String getIntence() {
		return intence;
	}

	public void setIntence(String intence) {
		this.intence = intence;
	}

	@Column(name = "contact", length = 16, nullable = true)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "levelName", length = 64, nullable = true)
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Column(name = "servicePoint", length = 64, nullable = true)
	public String getServicePoint() {
		return servicePoint;
	}

	public void setServicePoint(String servicePoint) {
		this.servicePoint = servicePoint;
	}
}
