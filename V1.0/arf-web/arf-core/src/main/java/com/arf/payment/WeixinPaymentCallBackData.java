package com.arf.payment;

import java.io.Serializable;

public class WeixinPaymentCallBackData implements Serializable{
	private static final long serialVersionUID = 5860520072646107003L;

	/**
	 * 返回状态码  SUCCESS  SUCCESS/FAIL
	 * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 */
	private String return_code;
	
	/**
	 * 返回信息
	 * 签名失败 
	 * 返回信息，如非空，为错误原因
	 */
	private String return_msg;

	// ----以下字段在return_code为SUCCESS的时候有返回
//	字段名 	变量名 	必填 	类型 	示例值 	描述
	/**
	 * 微信分配的公众账号ID（企业号corpid即为此appId）
	 */
	private String appid;
	/**
	 * 微信支付分配的商户号
	 */
	private String mch_id; 	
	/**
	 * 微信支付分配的终端设备号 	
	 */
	private String device_info;
	/**
	 * 随机字符串，不长于32位
	 */
	private String nonce_str;
	/**
	 * 签名，详见签名算法
	 */
	private String sign;
	/**
	 * 业务结果  SUCCESS 	SUCCESS/FAIL	
	 */
	private String result_code;
	/**
	 * 错误代码 ,错误返回的信息描述
	 */
	private String err_code;
	/**
	 * 错误代码描述 
	 * 错误返回的信息描述
	 */
	private String err_code_des;
	/**
	 * 用户在商户appid下的唯一标识
	 */
	private String  openid;
	/**
	 * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	 */
	private String  is_subscribe;
	/*
	 * 交易类型 JSAPI、NATIVE、APP
	 */
	private String trade_type;
	/**
	 * 付款银行 ,银行类型，采用字符串类型的银行标识，银行类型见银行列表
	 */
	private String bank_type;	
	/**
	 * 订单总金额，单位为分
	 */
	private int total_fee;
	/**
	 * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。	
	 */
	private int settlement_total_fee;
	/**
	 * 货币种类 ,符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String fee_type; 	
	/**
	 * 现金支付金额订单现金支付金额，详见支付金额 	
	 */
	private int cash_fee;	
	/**
	 * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String cash_fee_type ;
	/**
	 * 代金券金额 
	 * 代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，详见支付金额
	 */
	private int coupon_fee;
	/**
	 * 代金券使用数量 	
	 */
	private int coupon_count;
	/*
	 * 代金券类型 
	 * CASH--充值代金券 
	 * NO_CASH---非充值代金券
	 * 订单使用代金券时有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
	 */
	private int coupon_type_$n;

	/**
	 * 代金券ID,$n为下标，从0开始编号
	 */
	private String coupon_id_$n;
	/**
	 * 单个代金券支付金额,$n为下标，从0开始编号
	 */
	private int coupon_fee_$n;	
	/**
	 * 微信支付订单号 	
	 */
	private String transaction_id;
	/**
	 * 商户系统的订单号，与请求一致。
	 */
	private String out_trade_no;
	/**
	 * 商家数据包，原样返回
	 */
	private String attach;
	/**
	 * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则	
	 */
	private String time_end;
	public String getReturn_code() {
		return return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public String getOpenid() {
		return openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public int getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public int getCash_fee() {
		return cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public int getCoupon_fee() {
		return coupon_fee;
	}
	public int getCoupon_count() {
		return coupon_count;
	}
	public int getCoupon_type_$n() {
		return coupon_type_$n;
	}
	public String getCoupon_id_$n() {
		return coupon_id_$n;
	}
	public int getCoupon_fee_$n() {
		return coupon_fee_$n;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public void setSettlement_total_fee(int settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public void setCash_fee(int cash_fee) {
		this.cash_fee = cash_fee;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public void setCoupon_fee(int coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public void setCoupon_count(int coupon_count) {
		this.coupon_count = coupon_count;
	}
	public void setCoupon_type_$n(int coupon_type_$n) {
		this.coupon_type_$n = coupon_type_$n;
	}
	public void setCoupon_id_$n(String coupon_id_$n) {
		this.coupon_id_$n = coupon_id_$n;
	}
	public void setCoupon_fee_$n(int coupon_fee_$n) {
		this.coupon_fee_$n = coupon_fee_$n;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
}
