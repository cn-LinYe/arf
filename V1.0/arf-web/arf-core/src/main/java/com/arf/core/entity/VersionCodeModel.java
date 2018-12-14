package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "versioncodemodel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "versioncodemodel_sequence")
public class VersionCodeModel extends BaseEntity<Long> {
	
    private static final long serialVersionUID = -4659112825573422836L;
	private String androidCurrentVersion;
	private String androidDownloadurl;
	private String iphoneCurrentVersion;	
	private String iphoneDownloadurl;
	private boolean forceUpdate;//是否强制更新
	
	

	private String testAndroidCurrentVersion;
	private String testAndroidDownloadurl;
	private String testIphoneCurrentVersion;	
	private String tsetIphoneDownloadurl;
	
	private String descriptionList;//升级内容描述，存储为一个JSON数组字符串

	public VersionCodeModel() {
		super();
	}

	public VersionCodeModel(String iphoneCurrentVersion, String androidCurrentVersion, String iphoneDownloadurl,
			String androidDownloadurl) {
		super();
		this.iphoneCurrentVersion = iphoneCurrentVersion;
		this.androidCurrentVersion = androidCurrentVersion;
		this.iphoneDownloadurl = iphoneDownloadurl;
		this.androidDownloadurl = androidDownloadurl;
	}
	@Column(name = "testAndroidCurrentVersion", length = 255)
	public String getTestAndroidCurrentVersion() {
		return testAndroidCurrentVersion;
	}
	public void setTestAndroidCurrentVersion(String testAndroidCurrentVersion) {
		this.testAndroidCurrentVersion = testAndroidCurrentVersion;
	}
	@Column(name = "testAndroidDownloadurl", length = 255)
	public String getTestAndroidDownloadurl() {
		return testAndroidDownloadurl;
	}
	public void setTestAndroidDownloadurl(String testAndroidDownloadurl) {
		this.testAndroidDownloadurl = testAndroidDownloadurl;
	}
	@Column(name = "testIphoneCurrentVersion", length = 255)
	public String getTestIphoneCurrentVersion() {
		return testIphoneCurrentVersion;
	}
	public void setTestIphoneCurrentVersion(String testIphoneCurrentVersion) {
		this.testIphoneCurrentVersion = testIphoneCurrentVersion;
	}
	@Column(name = "tsetIphoneDownloadurl", length = 255)
	public String getTsetIphoneDownloadurl() {
		return tsetIphoneDownloadurl;
	}
	public void setTsetIphoneDownloadurl(String tsetIphoneDownloadurl) {
		this.tsetIphoneDownloadurl = tsetIphoneDownloadurl;
	}
	
	@Column(name = "iphoneCurrentVersion", length = 255, nullable = false)
	public String getIphoneCurrentVersion() {
		return iphoneCurrentVersion;
	}

	public void setIphoneCurrentVersion(String iphoneCurrentVersion) {
		this.iphoneCurrentVersion = iphoneCurrentVersion;
	}

	@Column(name = "androidCurrentVersion", length = 255, nullable = false)
	public String getAndroidCurrentVersion() {
		return androidCurrentVersion;
	}

	public void setAndroidCurrentVersion(String androidCurrentVersion) {
		this.androidCurrentVersion = androidCurrentVersion;
	}

	@Column(name = "iphoneDownloadurl", length = 255, nullable = false)
	public String getIphoneDownloadurl() {
		return iphoneDownloadurl;
	}

	public void setIphoneDownloadurl(String iphoneDownloadurl) {
		this.iphoneDownloadurl = iphoneDownloadurl;
	}

	@Column(name = "androidDownloadurl", length = 255, nullable = false)
	public String getAndroidDownloadurl() {
		return androidDownloadurl;
	}

	public void setAndroidDownloadurl(String androidDownloadurl) {
		this.androidDownloadurl = androidDownloadurl;
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(Boolean forceUpdate) {
		if(forceUpdate != null){
			this.forceUpdate = forceUpdate;
		}
	}

	@Column(length = 2000)
	public String getDescriptionList() {
		return descriptionList;
	}
	public void setDescriptionList(String descriptionList) {
		this.descriptionList = descriptionList;
	}
	
}
