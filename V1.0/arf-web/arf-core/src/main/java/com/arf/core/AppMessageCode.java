/**
 * @(#)AppMessageCode.java
 * 
 * Copyright arf.
 *
 * @Version: 1.0
 * @JDK: jdk jdk1.6.0_10
 * @Module: arf-core
 */ 
 /*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2015-11-20       arf      Created
 **********************************************
 */

package com.arf.core;
/**
 * APP接口常量表
 * author:arf
 * @author arf
 * @version 4.0
 */
public class AppMessageCode {
    /**数据请求成功**/
    public static final int CODE_SUCCESS=200;
    /**数据请求成功描述**/
    public static final String MSG_SUCCESS="操作成功";
    
    /**用户识别失败**/
    public static final int CODE_VALFAIL=201;
    /**用户识别失败描述**/
    public static final String MSG_VALFAIL="用户识别失败";
    
    /**请求错误**/
    public static final int CODE_ERROR=202;
    /**请求错误描述**/
    public static final String MSG_ERROR="参数错误,操作失败";
    
    /**数据库错误**/
    public static final int CODE_DATABASE_ERROR=203;
    /**数据库错误描述**/
    public static final String MSG_DATABASE_ERROR="数据库错误,操作失败";
    
    /**服务错误**/
    public static final int CODE_SERVER_ERROR=204;
    /**服务错误描述**/
    public static final String MSG_SERVER_ERROR="服务异常,操作失败";
    
    
    public static final int CODE_COMMUNITY_NOT_EXIST = 301;
    public static final String MSG_COMMUNITY_NOT_EXIST= "没有查询到小区";
    
    public static final int CODE_BUSINESS_NOT_FIND = 305;
    public static final String MSG_BUSINESS_NOT_FIND= "没有查询到商户";
    
    public static final int CODE_LICENSE_NOT_BIND = 302;
    public static final String MSG_LICENSE_NOT_BIND= "车牌没有绑定";
    
    public static final int CODE_LICENSE_NOT_DEVOLUTION = 303;
    public static final String MSG_LICENSE_NOT_DEVOLUTION= "车牌没有授权";
    
    public static final int CODE_LICENSE_NO_BIND_AND_NO_DEVOLUTION = 304;
    public static final String MSG_LICENSE_NO_BIND_AND_NO_DEVOLUTION= "用户没有绑定车牌，也没有被授权车牌";
    
    
    /******************************   我的电子账户   ******************************/
    
    /**充值订单已存在**/
    public static final int CODE_ORDER_EXIST=1001;
    /**充值订单已存在描述**/
    public static final String MSG_ORDER_EXIST="充值订单已存在";
    
    /**********************************   E停车  *********************************/
    
    public static final int CODE_ESCAPE_EXIST = -5068;
    public static final String MSG_ESCAPE_EXIST = "存在逃欠费，不能预约";
    
    public static final int CODE_PARKING_NOT_EXIST = -5069;
    public static final String MSG_PARKING_NOT_EXIST = "停车场不存在，请刷新重试";
    
    public static final int CODE_ACCOUNT_NO_EXIST = -5070;
    public static final String MSG_ACCOUNT_NO_EXIST = "未开通在线账户";
    
    public static final int CODE_NO_BERTH = -5071;
    public static final String MSG_NO_BERTH = "没有可预定的车位!";
    
    public static final int CODE_BOOK_FAILED = -5072;
    public static final String MSG_BOOK_FAILED = "预定失败！";
    
    public static final int CODE_SMS_CODE_ERROR = -5073;
    public static final String MSG_SMS_CODE_ERROR = "短信验证码错误!";
   
    public static final int CODE_PASSWORD_ERROR = -5074;
    public static final String MSG_PASSWORD_ERROR = "支付密码错误!";
    
    public static final int CODE_NO_SUPPORT = -5075;
    public static final String MSG_NO_SUPPORT = "不支持预定!";
    
    public static final int CODE_NO_ENOUGH = -5076;
    public static final String MSG_NO_ENOUGH = "余额不足";
    
    public static final int CODE_REPEAT_BOOK = -5077;
    public static final String MSG_REPEAT_BOOK = "重复预定";
    
    public static final int CODE_NO_CORRECT_TIME = -5078;
    public static final String MSG_NO_CORRECT_TIME = "预定时间不正确";
    
    public static final int CODE_PARAM_NOT_FIND = -5079;
    public static final String MSG_PARAM_NOT_FIND = "系统参数未找到";
    
    public static final int CODE_ORDER_HAS = -5180;
    public static final String MSG_ORDER_HAS = "存在未完成订单";
    
    public static final int CODE_NO_EXTEND_ORDER = -5181;
    public static final String MSG_NO_EXTEND_ORDER = "没有可续约订单";
    
    public static final int CODE_ORDER_NOT_PAY = -5182;
    public static final String MSG_ORDER_NOT_PAY = "存在未支付订单";
    
    public static final int CODE_BLACK_LIST = -5183;
    public static final String MSG_BLACK_LIST = "违约超过限定次数，不能预约";
    
    public static final int CODE_NO_AD = -5184;
    public static final String MSG_NO_AD = "获取广告失败";
    
    public static final int CODE_ADD_ERROR = -5185;
    public static final String MSG_ADD_ERROR = "注册广告失败";
    
    public static final int CODE_NO_BUSINESS = -5186;
    public static final String MSG_NO_BUSINESS = "业务不存在";
    
    //====================
    
    public static final int CODE_PARK_NO_RESPONSE  = -5170;
    public static final String MSG_PARK_NO_RESPONSE = "停车场无响应";
    
    public static final int CODE_PARK_FAILED_REQ  = -5171;
    public static final String MSG_PARK_FAILED_REQ = "请求停车场信息失败";
    
    //====================
    
    public static final int CODE_ORDER_NOT_EXIST = -5191;
    public static final String MSG_ORDER_NOT_EXIST = "订单不存在";
    
    public static final int CODE_ORDER_HAS_CANCELED = -5192;
    public static final String MSG_ORDER_HAS_CANCELED = "订单已经取消";
    
    public static final int CODE_ORDER_IS_FINISHED = -5193;
    public static final String MSG_ORDER_IS_FINISHED = "订单已经完成，不能取消";
    
    public static final int CODE_CANCLE_FAILED = -5194;
    public static final String MSG_CANCLE_FAILED = "取消失败";
    
    //====================
    
    public static final int CODE_ORDER_PAYED = -5200;
    public static final String MSG_ORDER_PAYED = "订单已支付";
    
    //==================众筹状态码====================================//
    public static final int CROWD_CODE_SHOP_NOEXIST = -5010;
    public static final String CROWD_MSG_SHOP_NOEXIST = "店铺不存在";
    
    public static final int CROWD_CODE_SHOP_FULL = -5011;
    public static final String CROWD_MSG_SHOP_FULL = "店铺已满";
    
    public static final int CROWD_CODE_CROWD_FULL = -5051;
    public static final String CROWD_MSG_CROWD_FULL = "众筹已满";
    
    public static final int CROWD_CODE_CROWD_NOENOUGH = -5052;
    public static final String CROWD_MSG_CROWD_NOENOUGH = "众筹数额不够";
    
    public static final int CROWD_CODE_ORDER_PAID = -5054;
    public static final String CROWD_MSG_ORDER_PAID = "订单已被支付";
    
    public static final int CROWD_CODE_NOORDER_PAY = -5055;
    public static final String CROWD_MSG_NOORDER_PAY = "没有需要支付的订单";
    
    public static final int CROWD_CODE_NOORDER = -5170;
    public static final String CROWD_MSG_NOORDER = "项目不存在";
    
    public static final int CROWD_CODE_NOPREFERENTIAL = -5180;
    public static final String CROWD_MSG_NOPREFERENTIAL = "没有优惠列表";
    
    public static final int CROWD_CODE_RET_SUCCESS = 0;
    public static final String CROWD_MSG_RET_SUCCESS = "OK";
    
    public static final int CROWD_CODE_RET_FAIL = -12;
    public static final String CROWD_MSG_RET_FAIL = "服务错误!";
    
    public static final int CROWD_CODE_EXCEED_LIMIT = -5063;
    public static final String CROWD_MSG_EXCEED_LIMIT = "购买数额超过限定！";
    
    public static final int CROWD_CODE_ISEXIST_SHOPKEEPER = -5090;
    public static final String CROWD_MSG_ISEXIST_SHOPKEEPER = "可以支付，还没店主";
    
    
    public static final int CROWD_CODE_TIME_END = -5091;
    public static final String CROWD_MSG_TIME_END = "众筹已经结束";
    
    public static final int CROWD_CODE_AMOUNT_EXCEED = -5092;
    public static final String CROWD_MSG_AMOUNT_EXCEED = "金额超出限制";
    
    public static final int CROWD_CODE_SHOOPER_ALLOW = -5093;
    public static final String CROWD_MSG_SHOOPER_ALLOW = "店主不允许购买";
    
    public static final int CROWD_CODE_SAMEUSER_EXCEED = -5094;
    public static final String CROWD_MSG_SAMEUSER_EXCEED = "同一个合伙人购买金额超出限制";
    
    public static final int CROWD_CODE_NOTFULL_PARTER = -5100;
    public static final String CROWD_MSG_NOTFULL_PARTER = "可以支付，没有过限定";
    
    public static final int CROWD_CODE_ORDER_ISCREATED = -5110;
    public static final String CROWD_MSG_ORDER_ISCREATED = "订单已经生成";
    
    public static final int CROWD_CODE_CANCEL_NO = -5120;
    public static final String CROWD_MSG_CANCEL_NO = "取消订单失败，未找到订单或订单已完成";
    
    /**********************************   安心点 V1.1.1  *********************************/
    
    public static final int CODE_NICKNAME_TOOLONG = 1001;
    public static final String MSG_NICKNAME_TOOLONG = "昵称太长";
    
    public static final int CODE_BERTH_WRONG = 1002;
    public static final String MSG_BERTH_WRONG = "生日格式不对";
    
    public static final int CODE_GENDER_WRONG = 1003;
    public static final String MSG_GENDER_WRONG = "性别不对";
    
    //=====
    public static final int CODE_BANKNUMBER_NOTEXITE = 1000;
    public static final String MSG_BANKNUMBER_NOTEXITE = "银行卡号不正确";
    
    public static final int CODE_BANKNAME_NULL = 1001;
    public static final String MSG_BANKNAME_NULL = "银行名称不能为空";
    
    public static final int CODE_BANKNUMBER_NULL = 1002;
    public static final String MSG_BANKNUMBER_NULL = "账户不能为空";
    
    public static final int CODE_REALNAME_NULL = 1003;
    public static final String MSG_REALNAME_NULL = "开户人不能为空";
    
    public static final int CODE_BANKNUMBER_BOUND = 1004;
    public static final String MSG_BANKNUMBER_BOUND = "账户已被他人绑定";
    
    public static final int CODE_WITHDRAWALS_LIMIT = 1005;
    public static final String MSG_WITHDRAWALS_LIMIT = "单次提现金额太大";
    
    public static final int CODE_BANKNUMBER_NO = 1006;
    public static final String MSG_BANKNUMBER_NO = "账户为空或被禁用";
    
    public static final int CODE_BANKACCOUNT_NULL = 1007;
    public static final String MSG_BANKACCOUNT_NULL = "无法找到卡信息或卡不可用";
    
    public static final int CODE_ISVIP_TRUE = 1008;
    public static final String MSG_ISVIP_TRUE = "用户已开通会员，无需再次开通";
    
    public static final int CODE_OPENECC_ERROR = 1009;
    public static final String MSG_OPENECC_ERROR = "开通ECC获取赠送套餐错误";
    
    public static final int CODE_EXCHANGE_CODE_NULL = 1010;
    public static final String MSG_EXCHANGE_CODE_NULL = "VIP验证码为空,请查证后重新输入";
    
    public static final int CODE_CODE_NOTEXIST = 1011;
    public static final String MSG_CODE_NOTEXIST = "VIP验证码不存在,请查证后重新输入";
    
    public static final int CODE_CODE_USEED = 1012;
    public static final String MSG_CODE_USEED = "VIP验证码被使用,请查证后重新输入";
    
    public static final int CODE_SIGN_ERROR = 1013;
    public static final String MSG_SIGN_ERROR = "签名错误";
    
    /**********************************   洗衣帮帮 v0.0.1  *********************************/
    
    //getLaundryBusiness / bookOrder
    
    public static final int CODE_BUSINESS_NOT_EXIST = 1001;
    public static final String MSG_BUSINESS_NOT_EXIST = "附近不存在服务商家";
    
    public static final int CODE_BUSINESS_NO_SERVICE = 1002;
    public static final String MSG_BUSINESS_NOT_SERVICE = "服务商家没有能服务的洗衣类别";
    
	//cancelOrder
    
    public static final int CODE_LAUNDRY_ORDER_NOT_EXIST = 1001;
    public static final String MSG_LAUNDRY_ORDER_NOT_EXIST = "未能查询到该订单";
    
    public static final int CODE_LAUNDRY_ORDER_CANNOT_CANCEL = 1002;
    public static final String MSG_LAUNDRY_ORDER_CANNOT_CANCEL = "订单已不可取消";
    
	//receivedOrder
    
    public static final int CODE_LAUNDRY_ORDER_CANNOT_RECEIVED = 1002;
    public static final String MSG_LAUNDRY_ORDER_CANNOT_RECEIVED = "订单不可签收";
    
	//payOrder
    
    public static final int CODE_LAUNDRY_ORDER_ALREADY_PAY = 1002;
    public static final String MSG_LAUNDRY_ORDER_ALREADY_PAY = "订单已支付";
    
    public static final int CODE_LAUNDRY_ORDER_CANNOT_PAY = 1003;
    public static final String MSG_LAUNDRY_ORDER_CANNOT_PAY = "订单不可支付";
    
    public static final int CODE_LAUNDRY_ORDER_BALANCE_NOT_ENOUGH = 1004;
    public static final String MSG_LAUNDRY_ORDER_BALANCE_NOT_ENOUGH = "账户余额不足,请充值后再支付";
    
	//promptOrder
    
    public static final int CODE_LAUNDRY_ORDER_CANNOT_PROMPT = 1002;
    public static final String MSG_LAUNDRY_ORDER_CANNOT_PROMPT = "催一催不可使用";
    
    public static final int CODE_LAUNDRY_ORDER_PROMPT_FREQUENTLY = 1003;
    public static final String MSG_LAUNDRY_ORDER_PROMPT_FREQUENTLY = "操作频繁，请稍后再试";
    
	//refundOrder
    
    public static final int CODE_LAUNDRY_ORDER_NOT_PAID = 1002;
    public static final String MSG_LAUNDRY_ORDER_NOT_PAID = "订单还未支付,不可申请退款";
    
    public static final int CODE_LAUNDRY_ORDER_REPEATED_REFUND = 1003;
    public static final String MSG_LAUNDRY_ORDER_REPEATED_REFUND = "重复的退款申请";
    
    //complaintOrder
    
    public static final int CODE_LAUNDRY_ORDER_CANNOT_COMPLAINT = 1002;
    public static final String MSG_LAUNDRY_ORDER_CANNOT_COMPLAINT = "订单不可投诉";
    
    public static final int CODE_LAUNDRY_CATEGORY_PRICE_NULL = 1005;
    public static final String MSG_LAUNDRY_CATEGORY_PRICE_NULL = "洗衣分类价目数据不能为空";
    
    //com.arf.controller.review.service.impl.ReviewServiceImpl 评价
    
    public static final int CODE_REVIEW_TYPE_NOT_EXIST= 1001;
    public static final String MSG_REVIEW_TYPE_NOT_EXIST = "没有要评价的服务类型";
    
    public static final int CODE_REVIEW_ORDER_NOT_EXIST= 1002;
    public static final String MSG_REVIEW_ORDER_NOT_EXIST = "没有查询到要评价的订单";
    
    public static final int CODE_REVIEW_SCORE_NOT_RIGHT= 1003;
    public static final String MSG_REVIEW_SCORE_NOT_RIGHT = "请给本次服务打分";
    
    public static final int CODE_REVIEW_CONTENT_TOO_LONG= 1004;
    public static final String MSG_REVIEW_CONTENT_TOO_LONG = "评论内容超过字数限制";
    
    public static final int CODE_REVIEW_FORREVIEW_NOT_EXIST= 1005;
    public static final String MSG_REVIEW_FORREVIEW_NOT_EXIST = "没有查询到要回复的评价";
    
    public static final int CODE_REVIEW_CANNOT= 1006;
    public static final String MSG_REVIEW_CANNOT = "暂不能评论";
    
    public static final int CODE_LAUNDRY_ORDER_CANCEL = 1007;
    public static final String MSG_LAUNDRY_ORDER_CANCEL = "订单已取消";
    
    
    
    /**********************************   停车卡业务返回码   *********************************/
    public static final int CODE_GOLDCARD_NOT_EXIST = 1001;
    public static final String MSG_GOLDCARD_NOT_EXIST = "用户没有停车卡";
    
    /**********************************   违章业务返回码   *********************************/
    public static final int CODE_VIOLATION_NOT_BIND_LICENSE = 1001;
    public static final String MSG_VIOLATION_NOT_BIND_LICENSE = "尚未绑定车牌";
    public static final int CODE_VIOLATION_NOT_CARNUM = 1002;
    public static final String MSG_VIOLATION_NOT_CARNUM = "请填写车架号发动机号";
    
    public static final int CODE_VIOLATION_NOT = 1006;
    public static final String MSG_VIOLATION_NOT = "没查到违章记录";
    public static final int CODE_VIOLATION_NOT_EXIST = 1003;
    public static final String MSG_VIOLATION_NOT_EXIST = "违章信息不存在";
    public static final int CODE_VIOLATION_REQ_OVERTIME = 1004;
    public static final String MSG_VIOLATION_REQ_OVERTIME = "请求超时";
    public static final String MSG_VIOLATION_ORDER_NOT_EXIST = "订单不存在";
    public static final int CODE_VIOLATION_REFUND_ELECTRONIC_ERROR = 1005;
    public static final String MSG_VIOLATION_REFUND_ELECTRONIC_ERROR = "退款到电子钱包异常";
    
    
    /**********************************   操作员操作小区业务返回码   *********************************/
    public static final int CODE_SALESMAN_NOT_EXIT = 1001;
    public static final String MSG_SALESMAN_NOT_EXIT = "用户信息不存在";
    public static final int CODE_SALESMAN_PAWWWORD_ERROR = 1002;
    public static final String MSG_SALESMAN_PAWWWORD_ERROR = "密码错误";
    public static final int CODE_SALESMAN_USER_FROZEN = 1003;
    public static final String MSG_SALESMAN_USER_FROZEN = "账号被冻结";
    
    public static final int CODE_SALESMAN_STATUS_HAVE_CLOSE = 1004;
    public static final String MSG_SALESMAN_STATUS_HAVE_CLOSE = "闸门已经处于关闭状态";
    
    public static final int CODE_SALESMAN_STATUS_HAVE_OPEN = 1006;
    public static final String MSG_SALESMAN_STATUS_HAVE_OPEN = "闸门已经处于开启状态";
    
    public static final int CODE_SALESMAN_USER_NOT_JURISDICTION = 1005;
    public static final String MSG_SALESMAN_USER_NOT_JURISDICTION = "此用户没有权限操作";
    
    
    /**********************************   门锁分期购业务返回码   *********************************/
    public static final int CODE_LOCK_USER_NOT_BUY = 1001;
    public static final String MSG_LOCK_USER_NOT_BUY = "用户未购买安心锁分期乐";
    public static final int CODE_LOCK_ACTIVITY_END = 1002;
    public static final String MSG_LOCK_ACTIVITY_END = "活动结束";
    
    public static final int CODE_LOCK_NOT_FUND_ORDER = 1001;
    public static final String MSG_LOCK_NOT_FUND_ORDER = "没有查询到订单记录";
    public static final int CODE_LOCK_HAVE_REFUNDED = 1002;
    public static final String MSG_LOCK_HAVE_REFUNDED = "已经申请过退货(款)了,无需再次申请";
    public static final int CODE_LOCK_REFUND_DONE = 1003;
    public static final String MSG_LOCK_REFUND_DONE = "您的退货(款)已完成,不可再次申请";
    public static final int CODE_LOCK_REFUND_AMOUNT_ERROR = 1004;
    public static final String MSG_LOCK_REFUND_AMOUNT_ERROR = "退款金额错误";
    
    /**********************************   物业缴费业务返回码   *********************************/
    public static final int CODE_PROPERTY_NOT_BIND = 1001;
    public static final String MSG_PROPERTY_NOT_BIND = "未绑定房间或房间未验证";
    
    /**********************************   微信二维码临时车缴费业务返回码   *********************************/
    public static final int CODE_LICENSE_FORMAT_ERROR = 1001;
    public static final String MSG_LICENSE_FORMAT_ERROR = "车牌格式不正确";
    public static final int CODE_LICENSE_VERIFICATION_ERROR = 1002;
    public static final String MSG_LICENSE_VERIFICATION_ERROR = "车牌号、车架号、发动机号验证未通过";
    public static final int CODE_WX_LICENSE_BOUND = 1003;
    public static final String MSG_WX_LICENSE_BOUND = "车牌已绑定";
    public static final int CODE_WX_ADD_LICENSE_FREQUENT = 1004;
    public static final String MSG_WX_ADD_LICENSE_FREQUENT = "添加车牌过于频繁，请稍后再试";
    
    public static final int CODE_WX_NOT_FOUND_RESULT = 1001;
    public static final String MSG_WX_NOT_FOUND_RESULT = "没有查询到该微信用户停车记录";
    
    /**********************************   加油业务业务返回码   *********************************/
    public static final int CODE_CARD_AMOUNT_INSUFFICIENT = 10000  ;
    public static final String MSG_CARD_AMOUNT_INSUFFICIENT = "油卡账号金额不足";
    
    public static final int CODE_NOT_SUPPORT_FREEPAYMENT = 10001 ;
    public static final String MSG_NOT_SUPPORT_FREEPAYMENT = "用户未开启免密支付";
    
    public static final int CODE_FREEPAYMENT_QUOTA_INSUFFICIENT = 10002 ;
    public static final String MSG_FREEPAYMENT_QUOTA_INSUFFICIENT = "免密支付额度不足";
    
    public static final int CODE_PASSWORD_PAYMENT_ERROR = 10003  ;
    public static final String MSG_PASSWORD_PAYMENT_ERROR = "用户密码输入错误";
    
    public static final int CODE_PASSWORD_EXCEED_LIMIT_ERROR = 10003;
    public static final String MSG_PASSWORD_EXCEED_LIMIT_ERROR = "用户密码输入错误超过5次 ";
    public static final int CODE_FINGERPRINTPASSWORD_PAYMENT_ERROR = 10004;
    public static final String MSG_FINGERPRINTPASSWORD_PAYMENT_ERROR = "用户密码输入错误";
    public static final int CODE_MEMBER_NOT_EXIT_ERROR = 10005;
    public static final String MSG_MEMBER_NOT_EXIT_ERROR = "没有成为会员或者油卡信息不存在";
    public static final int CODE_ORDER_NOT_EXIT_ERROR = 10006;
    public static final String MSG_ORDER_NOT_EXIT_ERROR = "订单不存在";
    public static final int CODE_STATION_NOT_EXIT_ERROR = 10007;
    public static final String MSG_STATION_NOT_EXIT_ERROR = "油站信息不存在";
    public static final int CODE_TUBINGGUN_NOT_ENOUGH_ERROR = 10008;
    public static final String MSG_STATION_NOT_ENOUGH_ERROR = "油罐容量不足";
    
    public static final int CODE_CARD_NOTUSE = 10009  ;
    public static final String MSG_CARD_NOTUSE = "油卡无法使用";
    
    public static final int CODE_MEMBER_CARD_NOT_EXIT = 13001;//用户油卡不存在
    public static final int CODE_MEMBER_NOT_EXIT = 13002;//用户不存在
    
    public static final int CODE_MEMBER_CARD_HAVE_EXIT = 13002;//用户油卡已经存在
    public static final int CODE_MEMBER_ORDER_NOT_EXIT = 13003;//用户油卡不存在
    public static final int CODE_BATCH_ORDER_USER_EXIT = 13004;//批量生成订单用户已存在
    public static final int CODE_GAS_IDCARD_EXITED =  13005; //身份证号重复
    
    public static final int CODE_REFUEL_USER_NOT_EXIT =  1000 ;//账号不存在 
	public static final int CODE_REFUEL_VERIFICATIONCODE_ERROR = 10001 ;//验证码不存在或者过期 
	public static final int CODE_REFUEL_USER_PASSWORD_ERROR =   10002; //密码不正确 
	public static final int CODE_REFUEL_USER_HAVE_DISABLE =  10003; //账号已经被禁用
	public static final int CODE_USER_INFO_HAVE_EXIT =  10008; //用户资料已存在
	public static final int CODE_GAS_STATION_NOT_EXIT =  10009; //查不到此账号油站信息
}











