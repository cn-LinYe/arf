package com.arf.access.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessTempPasswdDao;
import com.arf.access.entity.AccessInitialSecretkey;
import com.arf.access.entity.AccessTempPasswd;
import com.arf.access.entity.AccessTempPasswd.Status;
import com.arf.access.service.IAccessInitialSecretkeyService;
import com.arf.access.service.IAccessTempPasswdService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessTempPassword")
public class AccessTempPasswordImpl extends BaseServiceImpl<AccessTempPasswd, Long> implements IAccessTempPasswdService{
	private final static Logger logger = LoggerFactory.getLogger(AccessTempPasswordImpl.class);
	@Resource(name="accessTempPasswdDaoImpl")
	private IAccessTempPasswdDao accessTempPasswdDao;
	@Resource(name="accessInitialSecretkeyServiceImpl")
	private IAccessInitialSecretkeyService accessInitialSecretkeyService;
	
	@Override
	protected BaseDao<AccessTempPasswd, Long> getBaseDao() {
		return accessTempPasswdDao;
	}
	

	@Override
	public AccessTempPasswd allocateOnePasswd(String communityNumber,int building,String userName,String bluetoothMac) {
		if (StringUtils.isBlank(communityNumber)||StringUtils.isBlank(userName)) {
			logger.error(String.format("分配临时密码时参数有误,communityNumber=%s,"
					+ "building=%s,userName=%s,bluetoothMac=%s", communityNumber,building,userName,bluetoothMac));
			return null;
		}
		AccessTempPasswd accessTempPasswd=null;
		try {
			//门禁分区
			int region =getRegion();
			//楼栋编号
			String buildingNum =autoGenericCode(building+"", 3);
			AccessInitialSecretkey accessInitialSecretkey =accessInitialSecretkeyService.findByCondition(communityNumber, null, region);
			if (accessInitialSecretkey==null||StringUtils.isBlank(accessInitialSecretkey.getPasswd())) {
				logger.error("分配临时密码时门禁初始化秘钥AccessInitialSecretkey没有设置,请先设置门禁秘钥并同步到门禁设备中.");
				return null;
			}
			
			int number = new Random().nextInt(8)+1;
			String[] groupArray =accessInitialSecretkey.getPasswd().split(",");
			if (groupArray==null||groupArray.length!=9) {
				logger.error("分配临时密码时门禁初始化秘钥组数据长度有误.");
				return null;
			}
			//得到分组密码 
			 String group=groupArray[number-1];
			 String time = DateFormatUtils.format(new Date(), "HHmm");
			 int buildingResult=building+Integer.parseInt(time.substring(3));
			 if (buildingResult<=999) {
				 buildingNum =autoGenericCode(buildingResult+"", 3);
			}
			//得到临时密码
			String generate =buildingNum+time+number;
			String password= generatePassword(generate, group);
			logger.info(String.format(">>>>>[获取临时密码成功]临时密码参数，password:%s,generate:%s,group:%s,number:%s",password,generate,group,number));
			 accessTempPasswd =new AccessTempPasswd();
			 accessTempPasswd.setPasswd(password);
			 accessTempPasswd.setBluetoothMac(bluetoothMac);
			 accessTempPasswd.setUsedBy(userName);
			 accessTempPasswd.setUsedDate(new Date());
			 accessTempPasswd.setCommunityNumber(communityNumber);
			 accessTempPasswd.setBuilding(building);
			 accessTempPasswd.setStatus(Status.USED);
			 accessTempPasswd = this.save(accessTempPasswd);
			 logger.info(String.format(">>>>>[获取临时密码成功]参数，password:%s",password));
		} catch (Exception e) {
			logger.error("获取临时密码异常",e);
		}
		return accessTempPasswd;
	}
	
	//根据两个编号组合生成新的密码
	public  String generatePassword(String generate,String group){
		if (StringUtils.isBlank(generate)||generate.length()!=8||StringUtils.isBlank(group)||group.length()!=8) {
			return null;
		}
		String password="";
		char[] groupChar =group.toCharArray();
		char[] generateChar=generate.toCharArray();
		List<String> list =new ArrayList<String>();
		for (int i = 0; i < generateChar.length; i++) {
			list.add(generateChar[i]+"");
		}
		for (char element : groupChar) {
			String key=list.get(Integer.parseInt(element+"")-1);
			password=password+key;
		}
		return password;
	}
	
	//得到指定位数楼栋编号
	public static String autoGenericCode(String code, int num) {
        String result = "";
        result = String.format("%0" + num + "d", Integer.parseInt(code));
        return result;
    }
	/**
	 * 得到门禁的区
	 * @return
	 */
	public int getRegion(){
		 Calendar cal = Calendar.getInstance();
		 int day= cal.get(Calendar.DAY_OF_MONTH);
		 int region =0;
		 region= day%7;
		 return region;
	}


	@Override
	public AccessTempPasswd findByBlueMacAndPwd(String password, String bluetoothMac) {
		return accessTempPasswdDao.findByBlueMacAndPwd(password, bluetoothMac);
	}
}
