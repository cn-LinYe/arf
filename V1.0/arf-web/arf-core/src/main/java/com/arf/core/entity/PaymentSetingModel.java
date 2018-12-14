package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "payment_seting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "payment_seting_sequence")
public class PaymentSetingModel extends BaseEntity<Long>{
	
	private static final long serialVersionUID = 8695988088719409804L;
	
	/**小区编号/商户编号/安尔发**/
	private String community_number;
	//0 微信  1支付宝  2 微信 支付宝 银行卡 微信公众号（新增配置项往后追加）
	private Integer type;
	/**微信appid**/
	private String wx_app_id;
	/**微信商户号**/
	private String wx_mch_id;
	/**微信appkey**/
	private String wx_api_key;
	/**支付宝商户收款账号**/
	private String ali_seller;
	/**支付宝公钥**/
	private String ali_rsa_public;
	/**支付宝私钥**/
	private String ali_rsa_private;
	/**支付宝商户PID**/
	private String ali_partner;
	/**物业编号**/ 
	private Integer propertyNumber;
	/**子公司编号**/ 
	private Integer branchId;
	
	/**主体类型枚举:COMMUNITY 0小区;MERCHANT 1商户;ANERFA 5安尔发**/ 
	private Type objectType;
	
	/** 银行名称 **/
	private String bankBankname;
	/** 银行开户名 **/
	private String bankOwner;
	/** 银行账户号码 **/
	private String bankNumber;
	/** 银行预留手机号码 **/
	private String bankBundmobile;
	/** 分行 **/
	private String bindBankBranch;
	/** 分行行号 **/
	private String bindBankBranchNo;
	/** 微信公众号appid **/
	private String wxgzhAppid;
	/** 微信公众号appkey **/
	private String wxgzhAppkey;
	/** 微信公众号openid **/
	private String wxgzhOpenid;
	/** 微信公众号对应的商户编号 **/
	private String wxgzhMchId;
	
	
	/**
	 * 主体类型枚举:
	 * COMMUNITY 0小区;
	 * MERCHANT 1商户;
	 * ANERFA 5安尔发
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		COMMUNITY, //小区		0
		MERCHANT, //商户		1
		PROPERTY, //物业 		2
		BRANCH, //子公司 		3
		AGENT_BRANCH, //代理中转公司		4
		ANERFA, //安尔发 		5
		BIZ_CHANNEL, //渠道 6
		;
	}
	
	@Column(name = "community_number", length = 32, nullable = false)
	public String getCommunity_number() {
		return community_number;
	}
	
	public void setCommunity_number(String community_number) {
		this.community_number = community_number;
	}
	
	@Column(name = "type", nullable = false)
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "wx_app_id", length = 255, nullable = true)
	public String getWx_app_id() {
		return wx_app_id;
	}
	public void setWx_app_id(String wx_app_id) {
		this.wx_app_id = wx_app_id;
	}
	
	@Column(name = "wx_mch_id", length = 255, nullable = true)
	public String getWx_mch_id() {
		return wx_mch_id;
	}
	public void setWx_mch_id(String wx_mch_id) {
		this.wx_mch_id = wx_mch_id;
	}
	
	@Column(name = "wx_api_key", length = 255, nullable = true)
	public String getWx_api_key() {
		return wx_api_key;
	}
	public void setWx_api_key(String wx_api_key) {
		this.wx_api_key = wx_api_key;
	}
	
	@Column(name = "ali_seller", length = 255, nullable = true)
	public String getAli_seller() {
		return ali_seller;
	}
	public void setAli_seller(String ali_seller) {
		this.ali_seller = ali_seller;
	}
	
	@Column(name = "ali_rsa_public", length = 1023, nullable = true)	
	public String getAli_rsa_public() {
		return ali_rsa_public;
	}
	public void setAli_rsa_public(String ali_rsa_public) {
		this.ali_rsa_public = ali_rsa_public;
	}
	
	@Column(name = "ali_rsa_private", length = 1023, nullable = true)	
	public String getAli_rsa_private() {
		return ali_rsa_private;
	}
	public void setAli_rsa_private(String ali_rsa_private) {
		this.ali_rsa_private = ali_rsa_private;
	}
	
	@Column(name = "ali_partner", length = 255, nullable = true)
	public String getAli_partner() {
		return ali_partner;
	}
	public void setAli_partner(String ali_partner) {
		this.ali_partner = ali_partner;
	}

	public Integer getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Type getObjectType() {
		return objectType;
	}

	public void setObjectType(Type objectType) {
		this.objectType = objectType;
	}

	@Column(length = 40)
	public String getBankBankname() {
		return bankBankname;
	}

	@Column(length = 40)
	public String getBankOwner() {
		return bankOwner;
	}

	@Column(length = 20)
	public String getBankNumber() {
		return bankNumber;
	}

	@Column(length = 15)
	public String getBankBundmobile() {
		return bankBundmobile;
	}

	@Column(length = 40)
	public String getBindBankBranch() {
		return bindBankBranch;
	}

	@Column(length = 20)
	public String getWxgzhAppid() {
		return wxgzhAppid;
	}

	@Column(length = 40)
	public String getWxgzhAppkey() {
		return wxgzhAppkey;
	}

	@Column(length = 40)
	public String getWxgzhOpenid() {
		return wxgzhOpenid;
	}

	public void setBankBankname(String bankBankname) {
		this.bankBankname = bankBankname;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public void setBankBundmobile(String bankBundmobile) {
		this.bankBundmobile = bankBundmobile;
	}

	public void setBindBankBranch(String bindBankBranch) {
		this.bindBankBranch = bindBankBranch;
	}

	public void setWxgzhAppid(String wxgzhAppid) {
		this.wxgzhAppid = wxgzhAppid;
	}

	public void setWxgzhAppkey(String wxgzhAppkey) {
		this.wxgzhAppkey = wxgzhAppkey;
	}

	public void setWxgzhOpenid(String wxgzhOpenid) {
		this.wxgzhOpenid = wxgzhOpenid;
	}

	public String getWxgzhMchId() {
		return wxgzhMchId;
	}

	public void setWxgzhMchId(String wxgzhMchId) {
		this.wxgzhMchId = wxgzhMchId;
	}
	@Column(length = 50)
	public String getBindBankBranchNo() {
		return bindBankBranchNo;
	}

	public void setBindBankBranchNo(String bindBankBranchNo) {
		this.bindBankBranchNo = bindBankBranchNo;
	}
}
