package com.arf.platform;

import org.springframework.stereotype.Component;

@Component("LPConstants")
public class LPConstants {

	// *************************************  KEY键 start ****************************************
	
	/** 下行请求参数 接口类型(interface_lanpeng) key */
	public static final String REQUEST_KEY_INTERFACE = "interface_lanpeng";
	/** 下行请求参数 小区编号(communityNo) key */
	public static final String REQUEST_KEY_COMMUNITY_NO = "communityNo";
	/** 下行请求参数 业务数据(bizData) key */
	public static final String REQUEST_KEY_BIZDATA = "bizData";
	
	/** 下行请求返回的Map中 状态(code)的Key*/
	public static final String RESULTMAP_KEY_STATUS = "code";
	/** 下行请求返回的Map中 返回信息(msg)的Key */
	public static final String RESULTMAP_KEY_MSG = "msg";
	/** 下行请求返回的Map中 其他返回信息(extrDatas)的Key */
	public static final String RESULTMAP_KEY_DATA = "extrDatas";
	
	/**
	 * 停车场实时状态汇报处理失败名单信息，key键
	 */
	public static final String RETURNKEY_FAILED_LIST = "failedList";
	
	
	
	// *************************************  KEY键 end ****************************************
	
	// ********************************公共返回码信息start****************************************

	/**
	 * 返回码:成功<br/>
	 * msg:Ok
	 */
	public static final String RETURNCODE_SUCCESS = "0";

	/**
	 * 返回码:社区不存在<br/>
	 * msg:Community no exist!
	 */
	public static final String RETURNCODE_COMMUNITY_NO_EXIST = "-1";

	/**
	 * 返回码:业务数据解析出错！<br/>
	 * msg:Data Error!
	 */
	public static final String RETURNCODE_DATA_ERROR = "-2";

	/**
	 * 返回码:请求参数有误!<br/>
	 * msg:Param Error!
	 */
	public static final String RETURNCODE_PARAM_ERROR = "-3";

	/**
	 * 返回码:操作过于频繁，请求受限，稍做延时后再请求！<br/>
	 * msg:Request limit!
	 */
	public static final String RETURNCODE_REQUEST_LIMIT = "-4";

	/**
	 * 返回码:无操作权限!<br/>
	 * msg:Permission denied!!
	 */
	public static final String RETURNCODE_PERMISSION_DENIED = "-5";

	/**
	 * 返回码:服务器内部处理出错！<br/>
	 * msg:Server Error!
	 */
	public static final String RETURNCODE_SERVER_ERROR = "-6";
	
	/**
	 * 返回码:请求超时
	 */
	public static final String RETURNCODE_OVERTIME = "-7";
	/**
	 * 返回码:其他错误
	 */
	public static final String RETURNCODE_OTHERERROR = "-8";

	// *************************公共返回码信息end*******************************************************

	// *************************业务私有返回码信息start************************************************

	/**
	 * 返回码:订单不存在！<br/>
	 * msg:Order no exist!
	 */
	public static final String RETURNKEY_ORDER_ON_EXIST = "-202";
	
	/**
	 * 返回码:数据重复汇报！<br/>
	 * msg:Duplicatet Report!
	 */
	public static final String RETURNKEY_DUPLICATET_REPORT_ARRIVE = "-201";
	
	/**
	 * 返回码:来车时间有误！<br/>
	 * msg:arriveTime error!
	 */
	public static final String RETURNKEY__ARRIVETIME_ERROR = "-203";

	// *************************
	
	/**
	 * 返回码:数据重复汇报！<br/>
	 * msg:Duplicatet Leave!
	 */
	public static final String RETURNKEY_DUPLICATET_REPORT_LEAVE = "-301";

	/**
	 * 返回码:时间有误，如来车时间大于走车时间！<br/>
	 * msg:leaveTime Error!
	 */
	public static final String RETURNKEY_LEAVETIME_ERROR = "-302";
	
	/**
	 * 返回码:未找到对应的来车汇报！<br/>
	 * msg:no arrive report!
	 */
	public static final String RETURNKEY_NO_ARRIVE_REPORT = "-303";
	
	/**
	 * 返回码:电子钱包结算，存在逃欠费，不允许出场！<br/>
	 * msg:Leave Forbidden
	 */
	public static final String RETURNKEY_LEAVE_FORBIDDEN = "-304";

	// *************************
	
	/**
	 * 返回码:不能通过车牌找到对应车主！<br/>
	 * msg:license_no_account!
	 */
	public static final String RETURNKEY_NO_ACCOUNT = "-1001";

	/**
	 * 车辆信息，车牌号key键
	 */
	public static final String RETURNKEY_CAR_LICENSE = "license";

	/**
	 * 车辆信息，账户信息key键
	 */
	public static final String RETURNKEY_CAR_ACCOUNT = "account";

	/**
	 * 车辆信息，逃欠费key键
	 */
	public static final String RETURNKEY_CAR_ESCAPE = "escape";

	/**
	 * 车辆信息，异常车辆key键
	 */
	public static final String RETURNKEY_CAR_EXCEPTION = "exception";

	// *************************业务私有返回码信息end***************************************************

}
