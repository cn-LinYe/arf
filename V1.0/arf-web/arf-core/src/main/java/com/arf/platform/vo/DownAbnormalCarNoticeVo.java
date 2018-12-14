package com.arf.platform.vo;

public class DownAbnormalCarNoticeVo extends DownRequestDataVo {

	private String version;//接口版本号
	private String licenseLen;//车牌长度，字节数，为如果为0时没有license
	private String license;//车牌
	private String exceptionCode;//异常代码
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLicenseLen() {
		return licenseLen;
	}
	public void setLicenseLen(String licenseLen) {
		this.licenseLen = licenseLen;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
}
