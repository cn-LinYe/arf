package com.arf.core.oldws;


public class BaseModelRspTemplate {
	
	public static BaseModelRsp getNoLoginRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_NOT_LOGIN_CODE, ConstantAdminCode.ADMIN_NOT_LOGIN_CODE_MSG);
	}
	
	public static BaseModelRsp getParamMissRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_PARAM_MISS_CODE, ConstantAdminCode.ADMIN_PARAM_MISS_CODE_MSG);
	}
	
	public static BaseModelRsp getParamIllegalRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_PARAM_ILLEGAL_CODE, ConstantAdminCode.ADMIN_PARAM_ILLEGAL_CODE_MSG);
	}

	public static BaseModelRsp getNoPermissionRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_NO_PERMISSION_CODE, ConstantAdminCode.ADMIN_NO_PERMISSION_CODE_MSG);
	}

	public static BaseModelRsp getDbQueryErrorRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_DB_QUERY_ERROR_CODE, ConstantAdminCode.ADMIN_DB_QUERY_ERROR_CODE_MSG);
	}
	
	public static BaseModelRsp getSuccessRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_SUCCESS_CODE, ConstantAdminCode.ADMIN_SUCCESS_CODE_MSG);
	}
	
	public static BaseModelRsp getDbQuryErrrorRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_DB_QUERY_ERROR_CODE, ConstantAdminCode.ADMIN_DB_QUERY_ERROR_CODE_MSG);
	}
	
	public static BaseModelRsp getDbNoDataRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_DB_NO_DATA_CODE, ConstantAdminCode.ADMIN_DB_NO_DATA_CODE_MSG);
	}
	
	public static BaseModelRsp getDbAddErrorRsp(){
		return new BaseModelRsp(ConstantAdminCode.ADMIN_DB_ADD_ERROR_CODE, ConstantAdminCode.ADMIN_DB_ADD_ERROR_CODE_MSG);
	}
	
}
