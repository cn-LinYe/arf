package com.arf.core;
/**
 * 管理后台   常量返回码 
 * @author arf
 *
 */

public class ConstantAdminCode {

	//常量命名规则  ADMIN_???(具体代码描述)_CODE
	
	/**
	 * 操作成功。
	 */
	public static final int ADMIN_SUCCESS_CODE = 59999;
	public static final String ADMIN_SUCCESS_CODE_MSG = "操作成功";
	
	/**
	 * 请求参数缺失。
	 */
	
	public static final int ADMIN_PARAM_MISS_CODE = 50000;
	public static final String ADMIN_PARAM_MISS_CODE_MSG = "请求参数缺失";
	
	/**
	 * 没有权限
	 */
	public static final int ADMIN_NO_PERMISSION_CODE = 50001;
	public static final String ADMIN_NO_PERMISSION_CODE_MSG = "没有权限";
	
	/**
	 * 参数非法
	 */
	
	public static final int ADMIN_PARAM_ILLEGAL_CODE = 50002;
	public static final String ADMIN_PARAM_ILLEGAL_CODE_MSG = "参数非法";
	
	/**
	 * 数据库添加失败
	 */
	
	public static final int ADMIN_DB_ADD_ERROR_CODE = 50003;
	public static final String ADMIN_DB_ADD_ERROR_CODE_MSG = "数据库添加失败，重新提交";
	/**
	 * 数据库查询失败
	 */
	
	public static final int ADMIN_DB_QUERY_ERROR_CODE = 50004;
	public static final String ADMIN_DB_QUERY_ERROR_CODE_MSG = "数据库查询失败";
	/**
	 * 没有登陆
	 */
	public static final int ADMIN_NOT_LOGIN_CODE = 50005;
	public static final String ADMIN_NOT_LOGIN_CODE_MSG = "没有登陆";
	
	/**
	 * 账号不唯一
	 */
	public static final int ADMIN_AMOUNT_NOT_UNIQUE_CODE = 54001;
	public static final String ADMIN_AMOUNT_NOT_UNIQUE_CODE_MSG = "数据库已存在该账号";
	
	/**
	 *数据库没有相关的数据 
	 */
	public static final int ADMIN_DB_NO_DATA_CODE = 50006;
	public static final String ADMIN_DB_NO_DATA_CODE_MSG = "数据没有你要的数据";
	
	/**
	 * 数据库更新失败
	 */
	public static final int ADMIN_DB_UPDATE_ERROR_CODE = 50007;
	public static final String ADMIN_DB_UPDATE_ERROR_CODE_MSG = "数据库更新失败";
	/**
	 * 操作成功。
	 */
	public static final int ADMIN_DOCUMENTCODEERROR_CODE = 50005;
	public static final String ADMIN_DOCUMENTCODEERROR_CODE_MSG = "用户不存在";
	/**
	 * 删除失败
	 */
	public static final int ADMIN_DB_DELETE_ERROR_CODE = 50008;
	public static final String ADMIN_DB_DELETE_ERROR_CODE_MSG = "删除失败";
	
	
	public static final int ADMIN_SMS_SEND_FAILED_CODE = 58002;
	public static final String ADMIN_SMS_SEND_FAILED_CODE_MSG = "验证码发送失败";
	/**
	 * 验证码错误。
	 */
	public static final int ADMIN_VERIFICATION_CODE_ERROR = 58001;
	public static final String ADMIN_VERIFICATION_CODE_ERROR_MSG = "验证码错误";
	
	/**
	 * 绑定QQ、微信、微博。
	 */
	public static final int ADMIN_BINDTHIRD_CODE_ERROR = 10000;
	public static final String ADMIN_BINDTHIRD_CODE_ERROR_MSG = "没有绑定，需绑定";
	
	/**
	 * 验证码为空
	 */
	public static final int ADMIN_VERIFICATION_CODE_NULL = 10001;
	public static final String ADMIN_VERIFICATION_CODE_NULL_MSG = "验证码为空";

	
}
