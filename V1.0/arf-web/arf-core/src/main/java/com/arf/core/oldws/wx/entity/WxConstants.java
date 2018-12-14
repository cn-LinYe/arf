package com.arf.core.oldws.wx.entity;

public class WxConstants {
	/**
	 * 嵘联服务号
	 */
	//公众平台服务号id(嵘联)
//	public static final String WXAPPID = "wx725a3a75a5b2a934";
//	//服务号应用秘钥
//	public static final String APPSECRET = "9c60be3efd2fe3967bc86aea09cdebda";
//	//商户号
//	public static final String MCH_ID = "1292613001";
//	//API密钥，在商户平台设置
//	public static final  String API_KEY="412fde4e9c2e2bb619514ecea142e756";
	/**
	 * 俺家网络
	 */
	//公众平台服务号id(俺家网络)
	public static final String WXAPPID = "wx7a2d481c16c28a13";
	//服务号应用秘钥
	public static final String APPSECRET = "55173fb3f42d0e7f0fd587948da6bb0b";
	//商户号
	public static final String MCH_ID = "1492880822";
	//API密钥，在商户平台设置
	public static final  String API_KEY="41t3ge4s9c2e2bb619514echa1tyj753";
	
	// 获取access_token接口地址
	public static final String URL_ACCESS_TOKEN="https://api.weixin.qq.com/cgi-bin/token";
	// 获取openid接口地址
	public static final String URL_OPENID = "https://api.weixin.qq.com/cgi-bin/user/get";
	//获取unionid接口地址
	public static final String URL_UNIONID =  "https://api.weixin.qq.com/cgi-bin/user/info";
	/**
	 * 查询红包纪录
	 */
	public static final String URL_Red_Envelop_Sarch = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";
	// 现金红包接口地址
	public static final String URL_Red_envelope = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	public static final String CLIENT_IP="192.168.1.172";
	public static final String ACT_NAME="开闸日活动";
	public static final String REMARK="红包领取";
	public static final String WISHING="成功开启vip奖励";
	public static final String TOTAL_NUM="1";
	public static final String NICK_NAME="俺家";// 提供方名称
	public static final String SEND_NAME="俺家网络科技";// 商户名称
	/**
	 * 企业号
	 */
	//公众平台企业号id
	public static final String WXCORPID = "wx7b404851adca5152";
	//企业号应用秘钥
	public static final String APPSECRET2 = "-K-Uvdepl22AaUFglFYuV9jalIq9AkeuvQzx6g_Fzf1ktJwUP_cHLL_B0e460OLM";
	//商户号
	public static final String MCH_ID2 = "1263614301";
	//企业号对应商户平台API密钥，在商户平台设置
	public static final  String API_KEY2="Anerfa20150813Anerfa201508138888";
	// 获取access_token接口地址
	public static final  String URL_ACCESS_TOKEN2 = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	//获取部门id接口地址
	public static final  String URL_DEPARTMENT_ID = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
	// 企业付款接口地址
	public static final String URL_Enterprise_pay = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	//获取部门成员接口地址
	public static final String URL_DEPARTMENT_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
	// 描述
	public static final String DESC="企业付款测试";
}
