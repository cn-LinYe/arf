package com.arf.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 违章查询接口 用户车辆信息Db缓存 
 * @author no
 * @data 2015年11月27日
 */
@Entity
@Table(name = "user_license_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "user_license_record_sequence")
public class UserLicenseRecordModel extends BaseEntity<Long> {

	/**
     * 
     */
    private static final long serialVersionUID = 6798401144290137452L;
//    private Integer id;
//	private String user_name;
	private String his_no;
	
	private String license_plate_number;
	
	/**
     * 违章记录前半       
     */
    private String illegalFront;
    /**
     * 违章纪录后半
     */
    private String illegalAfter;
    /**
     * 更新的时间
     * @return
     */
    private Date updateTime;
    /**
     * 是否有违章
     */
    private Boolean hasData;
    /***
     * 违章记录json数据
     */
    private String records;
	
    
    
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
	
//	@Column(name = "user_name", length = 64, nullable = false)
//	public String getUser_name() {
//		return user_name;
//	}
//	public void setUser_name(String user_name) {
//		this.user_name = user_name;
//	}
	
	/**  
     * 获取是否有违章  
     * @return hasData 是否有违章  
     */
    @Column(name="has_data")
    public Boolean getHasData() {
        return hasData;
    }

    /**  
     * 设置是否有违章  
     * @param hasData 是否有违章  
     */
    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }

    /**  
     * 获取违章记录json数据  
     * @return records 违章记录json数据  
     */
    @Column(name="records",length=10000)
    public String getRecords() {
        return records;
    }

    /**  
     * 设置违章记录json数据  
     * @param records 违章记录json数据  
     */
    public void setRecords(String records) {
        this.records = records;
    }

    @Column(name = "his_no", length = 32, nullable = true)
	public String getHis_no() {
		return his_no;
	}
	
	public void setHis_no(String his_no) {
		this.his_no = his_no;
	}
	
	@Column(name = "License_plate_number", length = 32, nullable = false)		
	public String getLicense_plate_number() {
		return license_plate_number;
	}
	public void setLicense_plate_number(String license_plate_number) {
		this.license_plate_number = license_plate_number;
	}
	
	
	
	/**  
     * 获取违章记录前半  
     * @return illegalFront 违章记录前半  
     */
	@Column(name = "illegalFront",length=2048)
    public String getIllegalFront() {
        return illegalFront;
    }
    /**  
     * 设置违章记录前半  
     * @param illegalFront 违章记录前半  
     */
    public void setIllegalFront(String illegalFront) {
        this.illegalFront = illegalFront;
    }
    /**  
     * 获取违章纪录后半  
     * @return illegalAfter 违章纪录后半  
     */
    @Column(name = "illegalAfter",length=2048)
    public String getIllegalAfter() {
        return illegalAfter;
    }
    /**  
     * 设置违章纪录后半  
     * @param illegalAfter 违章纪录后半  
     */
    public void setIllegalAfter(String illegalAfter) {
        this.illegalAfter = illegalAfter;
    }
    /**  
     * 获取更新的时间@return  
     * @return updateTime 更新的时间@return  
     */
    @Column(name = "updateTime")
    public Date getUpdateTime() {
        return updateTime;
    }
    /**  
     * 设置更新的时间@return  
     * @param updateTime 更新的时间@return  
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public UserLicenseRecordModel() {
		super();
	}
	
	public UserLicenseRecordModel(String his_no, String engine_numbers, String license_plate_number) {
		super();
//		this.user_name = user_name;
		this.his_no = his_no;
		this.license_plate_number = license_plate_number;
	}
	
//	public UserLicenseRecordModel(Integer id, String user_name, String his_no, String engine_numbers,
//			String license_plate_number) {
//		super();
//		this.id = id;
//		this.user_name = user_name;
//		this.his_no = his_no;
//		this.engine_numbers = engine_numbers;
//		this.License_plate_number = license_plate_number;
//	}
	
	public void autoSetFullIllegal(String illegal){
        if(null != illegal && !illegal.isEmpty()){
            int length = illegal.length();
            int half = length / 2;

            this.illegalFront = illegal.substring(0,half);
            this.illegalAfter = illegal.substring(half,length);
        }
        
    }
    
    public String autoGetFullIllegal(){
        if(null == this.illegalFront)
            return null;
        StringBuilder builder = new StringBuilder();
        builder.append(illegalFront);
        if(null != this.illegalAfter)
            builder.append(illegalAfter);
        return builder.toString();
    }
}
