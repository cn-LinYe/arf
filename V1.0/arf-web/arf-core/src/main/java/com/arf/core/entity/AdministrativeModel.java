package com.arf.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 	管理员基本信息
 * @author Administrator
 *
 */

@Entity
@Table(name = "administrative")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "administrative_sequence")
public class AdministrativeModel extends BaseEntity<Long>{

	private static final long serialVersionUID = 1675322079158386307L;
//    private int id;
	private String name; // 名称
	private String description; // 描述
	private String contact_name;// 联系人
	private String phone; // 联系人电话
	private String address;// 地址
	private Integer level;// 级别1：总公司    级别2：代表子公司3:代表物业
	
	//基本信息>>
	//总公司id
	private int headoffice_id=0;
	//子公司或物业特有信息子公司 唯一标识id
	private int branch_id=0;
	//物业唯一标识id
	private Integer property_number = 0;
	
	private Integer provinceno = 0;
	private Integer cityno     = 0;
	private Integer areano     = 0;

	/**
	 * 是否要发短信提醒
	 */
	private Integer isSendMessage=0;
	
	/**
	 * 定时器查询几次发短信
	 */
	private Integer timerMessage;
	/**
	 * 一天内已经发送多少
	 */
	private Integer sendTimes;
	/**
	 * 发送时间
	 */
	private Date sendMessageTime;
	
	/**
	 * 法人身份证个img url
	 */
	private String cardUrl;
	
	/**
	 * 营业执照img url
	 */
	private String licenseUrl;
	
	
	/**
	 * 总公司构造
	 * @param name   
	 * @param description
	 * @param contact_name 联系人
	 * @param phone        联系人电话
	 * @param address      地址
	 */
	
	public AdministrativeModel(String name, String description, String contact_name, String phone,
			String address) {
		super();
		this.name = name;
		this.description = description;
		this.contact_name = contact_name;
		this.phone = phone;
		this.address = address;
		this.level = 1;
	}
		
	/**
	 * 子公司构造
	 * @param name   
	 * @param description
	 * @param contact_name 联系人
	 * @param phone        联系人电话
	 * @param address      地址
	 * @param branch_id    子公司唯一标识
	 */
	
	public AdministrativeModel(String name, String description, String contact_name, String phone,
			String address,int branch_id,int headoffice_id) {
		super();
		this.name = name;
		this.description = description;
		this.contact_name = contact_name;
		this.phone = phone;
		this.address = address;
		this.branch_id = branch_id;
		this.level = 2;
		this.headoffice_id = headoffice_id;
	}
	
	/**
	 * 物业构造
	 * @param name
	 * @param description
	 * @param contact_name 联系人
	 * @param phone        联系人电话
	 * @param address      地址
	 * @param branch_id
	 * @param property_number
	 */
	
	public AdministrativeModel(String name, String description, String contact_name, String phone,
			String address, int branch_id, int property_number,int headoffice_id) {
		super();
		this.name = name;
		this.description = description;
		this.contact_name = contact_name;
		this.phone = phone;
		this.address = address;
		this.branch_id = branch_id;
		this.level = 3;
		this.property_number = property_number;
		this.headoffice_id = headoffice_id;
	}
	
	public AdministrativeModel() {
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
	
	
	
	@Column(name = "timerMessage")
	public Integer getTimerMessage() {
		return timerMessage;
	}
	public void setTimerMessage(Integer timerMessage) {
		this.timerMessage = timerMessage;
	}
	@Column(name = "sendTimes")
	public Integer getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}
	@Column(name = "sendMessageTime")
	public Date getSendMessageTime() {
		return sendMessageTime;
	}
	public void setSendMessageTime(Date sendMessageTime) {
		this.sendMessageTime = sendMessageTime;
	}
	
	
	
	
	
	
	
	
	
	
	@Column(name = "isSendMessage", length = 32)
	public Integer getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(Integer isSendMessage) {
		this.isSendMessage = isSendMessage;
	}

	@Column(name = "name", length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "contact_name", length = 16)
	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	@Column(name = "phone", length = 16)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address", length = 64)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "level",length = 3)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
	@Column(name = "branch_id")
	public int getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}
		
	@Column(name = "property_number", nullable = true)
	public Integer getProperty_number() {
		return property_number;
	}

	public void setProperty_number(Integer property_number) {
		this.property_number = property_number;
	}
	
	
	@Column(name = "headoffice_id",length =32, nullable = true)
	public int getHeadoffice_id() {
		return headoffice_id;
	}

	public void setHeadoffice_id(int headoffice_id) {
		this.headoffice_id = headoffice_id;
	}

	@Column(name = "provinceno", nullable = true)
	public Integer getProvinceno() {
		return provinceno;
	}

	public void setProvinceno(Integer provinceno) {
		this.provinceno = provinceno;
	}

	@Column(name = "cityno", nullable = true)
	public Integer getCityno() {
		return cityno;
	}  
	public void setCityno(Integer cityno) {
		this.cityno = cityno;
	}

	@Column(name = "areano", nullable = true)
	public Integer getAreano() {
		return areano;
	}

	public void setAreano(Integer areano) {
		this.areano = areano;
	}

	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}
}
