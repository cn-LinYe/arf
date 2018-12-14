package com.arf.propertymgr.entity;

public class PropertyPushMsg {

	//房屋门禁 2.2.5
	
	//业主添加住户（消息推送）
	/**
	 * 被添加住户收到消息：
	 * 	尊敬的住户，深南花园-5栋-1802房屋的住户王二（13278097890）已将您添加至该房屋。
	 */
	public static final String HOUSEHOLDERADDRENTER_TORENTER = "尊敬的住户，%s房屋的住户%s（%s）已将您添加至该房屋。";
	
	/**
	 * 业主添加住户，其他业主收到消息：
	 * 	尊敬的住户，深南花园-5栋-1802房屋的王二（13278097890）已将李三（15018522773）添加至该房屋。
	 */
	public static final String HOUSEHOLDERADDRENTER_TOOTHERRENTER = "尊敬的住户，%s房屋的%s（%s）已将%s（%s）添加至该房屋。";
	
	//住户申请加入房间-住户审核方式（短信+消息推送）
	/**
	 * 被补全号码的用户收到验证码短信：
	 * 	【验证码：4078】王二（13278097890）请求加入深南花园-5栋-1802房屋，同意请提供验证码给该用户。
	 */
	public static final String RENTERAPPLY_TOTENEMENT = "【验证码：%s】%s（%s）请求加入%s房屋，同意请提供验证码给该用户。";
	
	//住户申请加入房间-物业审核方式（短信+消息推送）
	/**
	 * 收到提交审核消息短信：    08月30日09：05，用户135****123申请成为X栋X单元X房间号的住户，需要物业处审核，请及时处理！
	 */
	public static final String RENTERAPPLY_PROPERTY = "%s，用户（%s）申请成为%s的住户，需要物业处审核，请及时处理！";
	
	/**
	 * 收到物业审核通过的短信：
	 * 	尊敬的用户，物业已同意您所提交的深南花园-5栋-1802房屋绑定申请，您可以正常使用安心点服务。
	 */
	public static final String PROPERTYPASS_TORENTER = "尊敬的用户，物业已同意您所提交的%s房屋绑定申请，您可以正常使用安心点服务。";
	
	/**
	 * 物业同意申请，业主收到房客/业主加入房间信息-消息推送：
	 * 	尊敬的深南花园-5栋-1802房屋的住户，物业已同意王小二（13278097890）加入房屋。如有疑问，请立即与物业联系。
	 */
	public static final String PROPERTYPASS_TOHOUSEHOLDER = "尊敬的%s房屋的住户，物业已同意%s（%s）加入房屋。如有疑问，请立即与物业联系。";
	
	/**
	 * 收到物业审核未通过的短信：
	 * 	尊敬的用户，您所提交的深南花园-5栋-1802房屋绑定申请，经物业审核，资料不符，认证失败。如有疑问，请于物业联系。
	 */
	public static final String PROPERTYREFUSE_TORENTER = "尊敬的用户，您所提交的%s房屋绑定申请，经物业审核，资料不符，认证失败。如有疑问，请于物业联系。";
	
	//业主把住户移出房间（消息推送）、房产证登记业主把非房产证业主移出房间（消息推送）
	/**
	 * 被删除的房客收到消息（被删除的非房产证业主收到消息）：
	 * 	尊敬的住户，深南花园-5栋-1802房屋的住户已将您移出房间。
	 */
	public static final String HOUSEHOLDERREMOVERENTER_TORENTER = "尊敬的住户，%s房屋的住户已将您移出房间。";
	
	/**
	 * 业主把房客删除，其他业主收到消息（把非房产证业主/房客删除，其他业主收到消息）：
	 * 	尊敬的住户，深南花园-5栋-1802房屋的住户王二（13278097890）已将李呵呵（15026622445）移出房间。
	 */
	public static final String HOUSEHOLDERREMOVERENTER_TOOTHERHOUSEHOLDER = "尊敬的住户，%s房屋的住户%s（%s）已将%s（%s）移出房间。";
	
	//住户删除房间（消息推送）
	/**
	 * 其他业主收到房客/业主删除房间的消息：
	 * 	尊敬的深南花园-5栋-1802房屋的住户，王二（13278097890）已退出了您的房间。
	 */
	public static final String RENTERMOVESELF_TOHOUSEHOLDER = "尊敬的%s房屋的住户，%s（%s）已退出了您的房间。";
	
	//微信用户请求开启门禁（消息推送）
	/**
	 * 开启应答设置的住户：
	 * 	微信用户XX请求进入小区及楼宇，请立即前往安心点APP首页进行处理。
	 */
	public static final String GUESTAPPLY_TOTENEMENT = "微信用户%s请求进入小区及楼宇，请立即前往安心点APP首页进行处理。";
	
	//业主/房客短信发送预约来访（短信推送）
	
	/**
	 * 预约来访短信的接收人：
	 * 	临时门禁密码：00035547#，仅限锦明花园小区7栋3单元203 的大门与本楼栋的门禁机上各使用一次，并且在 1小时内有效。
	 */
	public static final String SENDBARCODETEMPPWD = "临时门禁密码：%s，仅限%s 的大门与本楼栋的门禁机上各使用一次，并且在 %s小时内有效。";
	
	//门禁初始密码（短信推送）
	/**
	 * 所有住户收到短信：
	 * 	深南花园-5栋门禁系统升级，楼宇门禁初始密码为02147660，同一房屋共用一个密码，请尽快前往安心点APP【我的-我的小区-门禁密码】进行修改。
	 */
	public static final String ACCESSPASSWORD_TOTENEMENT = "%s门禁系统升级，楼宇门禁初始密码为%s，同一房屋共用一个密码，请尽快前往安心点APP【我的-我的小区-门禁密码】进行修改。";
	
	//物业解绑房屋（短信+消息推送）
	/**
	 * 房产证业主/业主/房客 收到物业解绑信息：
	 * 	尊敬的住户，深南花园-5栋-1802房屋已被物业解绑。如有疑问，请与物业联系！
	 */
	public static final String PROPERTYUNBOUND_TOTENEMENT = "尊敬的住户，%s房屋已被物业解绑。如有疑问，请与物业联系！";
	
}
