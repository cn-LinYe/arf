package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
/**
 * 摄像机表
 * @author Administrator
 *
 */

@Entity(name = "camerapostmodel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "camerapostmodel_sequence")
public class CameraCodeModel extends BaseEntity<Long>{

	/**
     * 
     */
    private static final long serialVersionUID = -4462272841660616134L;
    
//    private int id;
	private String state;// 摄像机状态
	private String cameraCode;// 摄像机id
	private String cameraName;//摄像机名称
	private String communityNumber;//小区id
	private String importMark;//进出闸标示。
	public CameraCodeModel() {
		super();
	}

	public CameraCodeModel(String cameraCode) {
		super();
		this.cameraCode = cameraCode;
	}
	
	public CameraCodeModel(String state, String cameraCode) {
		super();
		this.state = state;
		this.cameraCode = cameraCode;
	}

//	@Id
//	@Column
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	@Column(name = "cameraCode", length = 32)
	public String getCameraCode() {
		return cameraCode;
	}

	public void setCameraCode(String cameraCode) {
		this.cameraCode = cameraCode;
	}
	
	@Column(name = "state", length = 8)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "cameraName", length = 16)
	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	@Column(name = "communityNumber", length = 16)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	
	@Column(name = "importMark", length = 16)
	public String getImportMark() {
		return importMark;
	}

	public void setImportMark(String importMark) {
		this.importMark = importMark;
	}

	public CameraCodeModel(String state, String cameraCode, String cameraName, String communityNumber, String importMark) {
		super();
		this.state = state;
		this.cameraCode = cameraCode;
		this.cameraName = cameraName;
		this.communityNumber = communityNumber;
		this.importMark = importMark;
	}
}
