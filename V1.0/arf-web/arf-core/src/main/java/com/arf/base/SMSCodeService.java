package com.arf.base;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.arf.core.cache.redis.RedisService;
import com.arf.core.service.SmsService;
import com.arf.propertymgr.entity.PropertyPushMsg;

/**
 * 短信验证码基础Service
 * @author Caolibao
 *
 */
@Component("SMSCodeService")
public class SMSCodeService {
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	public long Default_Alive_Time = 2 * 60;
	
	public final String Key_Prefix_Sms_Code = "AXD_SMS_Code_Cache:";
	
	public enum SmsCodeType{
		Register, //注册
		ModifyPassword, //修改密码
		Login, //登陆
		Axd_Gates_ForgetPassword,//安心点闸门业务员修改密码
		ThirdPartyLogin,//第三方登录
		BindBrankCard,//绑定银行卡
		OIL_Card,//加油开卡
		Refuel_Gas_SetPassword,//家加油设置支付密码，免密支付，指纹密码
		Refuel_Gas_UnbindLicense,//家加油解绑车辆,
		Property_Access_Activate,//门禁-物业激活业主门禁卡
		Oil_Card_Activation_Lost,//家加油油卡挂失激活
		Property_BindRoom_Owner_Audit,//房屋绑定，前端用户选择业主审核时获取验证码key
		SHOPKEEP_RESET_PWD,//安心店长重置密码
		OfficeRegister, //办公室门禁注册
		OfficeModifyPassword, //办公室门禁修改密码
		OfficeLogin, //办公室门禁登陆
		;
	}
	
	/**
	 * 获取手机短信验证码
	 * @param mobile
	 * @param codeType
	 * @return
	 */
	public String getSMSCode(String mobile,SmsCodeType codeType,Long aliveTime,String ... otherParameter){
		String randomCode = RandomStringUtils.randomNumeric(4);
		redisService.set(Key_Prefix_Sms_Code + mobile + codeType, randomCode, (aliveTime == null || aliveTime <= 0)?Default_Alive_Time:aliveTime);
		sendSmsText(mobile, codeType,randomCode,otherParameter);
		return randomCode;
	}
	
	private void sendSmsText(String mobile,SmsCodeType codeType,String randomCode,String ... otherParameter){
		String content = "";
		switch(codeType){
			case Register :
				content = "您注册安心点的短信验证码是" + randomCode;
				break;
				
			case ModifyPassword :
				content = "您正在通过手机短信验证码修改安心点密码,本次验证码是"+randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
				
			case Login :
				content = "您正在通过短信验证码登录安心点,本次短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
			case Axd_Gates_ForgetPassword :
				content = "您正在通过短信验证码修改安心点闸门业务员密码,本次短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
			case ThirdPartyLogin :
				content = "您正在通过第三方账号登录安心点,本次短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;	
			case BindBrankCard :
				content = "您正在通过短信验证码绑定银行卡账号,本次短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
			case Refuel_Gas_SetPassword :
				content = "您正在通过短信验证码设置安心点家加油支付密码,本次短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
			case Refuel_Gas_UnbindLicense :
				content = "您正在通过短信验证码解绑安心点家加油绑定车辆,本次短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
			case Property_BindRoom_Owner_Audit :
				//【验证码：4078，王二（13278097890）请求加入深南花园-5栋-1802房屋，同意请提供验证码给该用户。
//				String formatContent = String.format("【验证码：%s】%s（%s）请求加入%s房屋，同意请提供验证码给该用户。",
//						randomCode,otherParameter[0],otherParameter[1],otherParameter[2]);
				String formatContent = String.format(PropertyPushMsg.RENTERAPPLY_TOTENEMENT,
						randomCode,otherParameter[0],otherParameter[1],otherParameter[2]);
				content = formatContent;
				break;
			case SHOPKEEP_RESET_PWD :
				content = "您安心店长的短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
			case OfficeRegister :
				content = "您注册办公门禁APP的短信验证码是" + randomCode;
				break;
				
			case OfficeModifyPassword :
				content = "您正在通过手机短信验证码修改办公门禁APP密码,本次验证码是"+randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
				
			case OfficeLogin :
				content = "您正在通过短信验证码登录办公门禁APP,本次短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
			default :
				content = "您安心点的短信验证码是" + randomCode+",为了您的账户安全,请不要将验证码告知他人";
				break;
		}
		smsService.send(mobile, content);
	}
	
	/**
	 * 验证手机短信验证码
	 * @param mobile
	 * @param codeType
	 * @param SMSCode
	 * @return
	 */
	public boolean verifySMSCode(String mobile,SmsCodeType codeType,String SMSCode){
		if(StringUtils.isBlank(SMSCode)){
			return false;
		}
		String randomCode = redisService.get(Key_Prefix_Sms_Code + mobile + codeType);
		return SMSCode.equals(randomCode);
	}
}
