package com.arf.plugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.service.SysconfigService;
import com.arf.plugin.dto.ViolationInfo;
import com.arf.plugin.pack.PackageViolation;
import com.arf.plugin.pack.PackageViolation.ViolationRecord;
import com.arf.plugin.vo.ViolationVo;
import com.arf.redis.CacheNameDefinition;
import com.arf.violation.service.ITrafficViolationRecordService;

public class Violation {
	public static final String APPCODE = "APPCODE ceb7075f7a9b4026bd4de21e3f1df289";
	public static final String HOST = "http://jisuqgclwz.market.alicloudapi.com";
	public static final String SCJUNMHOST = "http://jisuscjsnm.market.alicloudapi.com";
	private static final String[] SCJUNM=new String[]{"川","苏","蒙"};//特殊省份
	public static final String PATH = "/illegal/query";
	public static final String METHOD = "GET";
	public static final Logger log = LoggerFactory.getLogger(Violation.class);
	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	
	/**统一暴露接口
	 * @param com 
	 * @param 
	 * @return
	 */
	public static ViolationInfo getViolationInfo(ViolationVo vo,RedisService redisService){
		if(vo!=null){//判断传递参数vo是否为空
			String lsprefix=vo.getLsprefix();//获取车牌前缀
			String lsnum=vo.getLsnum();//获取车牌后半部分
			String license=vo.getLicense();
			if(StringUtils.isBlank(lsprefix)||StringUtils.isBlank(lsnum)){
				if(StringUtils.isBlank(license)){
					return null;//未传数据过来
				}else{
					lsprefix=license.substring(0, 1);
					lsnum=license.substring(1);
				}
			}else{
				license=lsprefix.concat(lsnum);
			}
			String violationInfo=redisService.hGet(String.format(CacheNameDefinition.Violation_Inquire_Info, license), license);
			if(StringUtils.isNotBlank(violationInfo)){
				ViolationInfo dto=JSON.parseObject(violationInfo, ViolationInfo.class);
				return dto;
			}
			String engineno=vo.getEngineno();//发动机号
			String frameno=vo.getFrameno();//车架号
			if(StringUtils.isBlank(frameno)||StringUtils.isBlank(engineno)){
				return null;
			}
			return getViolation(lsprefix, lsnum,engineno,frameno,license,redisService);
		}
		return null;
	}
	
	/**
	 * 用于验证车牌号、发动机号、车架号是否正确，不走缓存，redisService可为空
	 * @param vo
	 * @param redisService
	 * @return
	 */
	public static ViolationInfo getViolationInfoStrict(ViolationVo vo,RedisService redisService){
		if(vo!=null){//判断传递参数vo是否为空
			String lsprefix=vo.getLsprefix();//获取车牌前缀
			String lsnum=vo.getLsnum();//获取车牌后半部分
			String license=vo.getLicense();
			if(StringUtils.isBlank(lsprefix)||StringUtils.isBlank(lsnum)){
				if(StringUtils.isBlank(license)){
					return null;//未传数据过来
				}else{
					lsprefix=license.substring(0, 1);
					lsnum=license.substring(1);
				}
			}else{
				license=lsprefix.concat(lsnum);
			}
			String engineno=vo.getEngineno();//发动机号
			String frameno=vo.getFrameno();//车架号
//			String violationInfo=redisService.hGet(String.format(CacheNameDefinition.Violation_Inquire_Info_Strict, license,engineno,frameno), license);
//			if(StringUtils.isNotBlank(violationInfo)){
//				ViolationInfo dto=JSON.parseObject(violationInfo, ViolationInfo.class);
//				return dto;
//			}
			if(StringUtils.isBlank(frameno)||StringUtils.isBlank(engineno)){
				return null;
			}
			return getViolation(lsprefix, lsnum,engineno,frameno,license,redisService);
		}
		return null;
	}
	
	@Deprecated
	public static String compatibleViolation(ViolationVo vo,RedisService redisService){
		ViolationInfo vinfo =getViolationInfo(vo,redisService);
		if(vinfo!=null){
			String engineno=vo.getEngineno();//发动机号
			String frameno=vo.getFrameno();//车架号
			String apiengineno=vinfo.getEngineno();//发动机号
			String apiframeno=vinfo.getFrameno();//车架号
			PackageViolation pack=new PackageViolation();
			int status =vinfo.getStatus();
			pack.setErrMessage(vinfo.getMsg());
			if(StringUtils.isBlank(engineno)||StringUtils.isBlank(frameno)){
				status=201;
				pack.setErrMessage("车架号或者车牌输入有误！");
			}
			if(StringUtils.isBlank(apiengineno)||StringUtils.isBlank(apiframeno)){
				status=201;
				pack.setErrMessage("车架号或者车牌输入有误！");
			}
			if(!engineno.equals(apiengineno)||!frameno.equals(apiframeno)){
				status=201;
				pack.setErrMessage("车架号或者车牌输入有误！");
			}
			pack.setHasData(status==0);
			pack.setErrorCode(status);
			
			pack.setSuccess(status==0);
			pack.setLastSearchTime(df.format(new Date()));
			JSONArray array=vinfo.getList();
			List<ViolationRecord> list=new ArrayList<ViolationRecord>();
			if(array!=null){				
				for (Object item : array) {
					if(item!=null){
						JSONObject result=JSONObject.parseObject(item.toString());						
						PackageViolation.ViolationRecord record=new PackageViolation.ViolationRecord();
						record.setTime(result.getString("time"));// 违章时间
						record.setLocation(result.getString("address"));// 违章地点
						record.setReason(result.getString("content"));// 违章原因
						record.setCount(result.getString("price"));// 违章罚款金额
						record.setStatus("0");// 违章记录状态0 末处理 1 己处理(绝大部分情况下，车行易只能返回未处理的违章)
						record.setDepartment(result.getString("agency"));// 违章采集机关
						record.setDegree(result.getString("score"));// 违章扣分
						//record.setCode("");// 违章代码						
						record.setArchive(result.getString("number"));// 违章项文书编号
						String locationName="";
						String province=result.getString("province");//获取省信息
						if(StringUtils.isNotBlank(province)){
							locationName=locationName.concat(province);
						}
						String city=result.getString("city");//获取市信息
						if(StringUtils.isNotBlank(city)){
							locationName=locationName.concat(city);
						}
						String town=result.getString("town");//获取县信息
						if(StringUtils.isNotBlank(town)){
							locationName=locationName.concat(town);
						}
						record.setLocationName(locationName);
						record.setUniqueCode(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+RandomStringUtils.randomNumeric(8));//生成唯一编码
						record.setSecondaryUniqueCode(Integer.parseInt(RandomStringUtils.randomNumeric(8)));//生成唯一编码
						//record.setTelephone("");// 联系电话
						//record.setExcutedepartment("");// 执行部门
						//record.setExcutelocation("");// 违章处理地址
						//record.setCategory("");// 违章分类类别（如该字段显示为“现场单”，请注意提示用户）
						//record.setLatefine("");// 滞纳金（目前都是0，车行易不做计算）
						//record.setPunishmentaccording("");//处罚依据
						/**
						 * Illegalentry;// 违法条款 
						 * Locationid;// 违章归属地点ID 
						 * LocationName;// 违章归属地点名
						 * DataSourceID;// 查询数据源ID 
						 * RecordType;// 实时数据/历史数据 
						 * Poundage;// 手续费（标准价，合作方请无视）
						 * CanProcess = "0";// 是否可以代办 0 不可以 1 可以
						 * UniqueCode;// 违章信息的特征码（相同车牌，违章时间，地点，该值相同）
						 * SecondaryUniqueCode;// 违章记录ID，用于下单。
						 * DegreePoundage;// 扣分手续费 
						 * Other;// 预留 
						 * CanprocessMsg;// 是否代办原因
						 * */	
						list.add(record);
					}
				}				
			}
			pack.setRecords(list);			
			return JSONObject.toJSONString(pack);
		}
		return null;
	}
	
	public static String compatibleViolation(ViolationVo vo,RedisService redisService,ITrafficViolationRecordService trafficViolationRecordService){
		ViolationInfo vinfo =getViolationInfo(vo,redisService);
		if(vinfo!=null){
			String engineno=vo.getEngineno();//发动机号
			String frameno=vo.getFrameno();//车架号
			String apiengineno=vinfo.getEngineno();//发动机号
			String apiframeno=vinfo.getFrameno();//车架号
			PackageViolation pack=new PackageViolation();
			int status =vinfo.getStatus();
			pack.setErrMessage(vinfo.getMsg());
			if(StringUtils.isBlank(engineno)||StringUtils.isBlank(frameno)){
				status=201;
				pack.setErrMessage("车架号或者车牌输入有误！");
			}
			if(StringUtils.isBlank(apiengineno)||StringUtils.isBlank(apiframeno)){
				status=201;
				pack.setErrMessage("车架号或者车牌输入有误！");
			}
			//TODO
			String licensePlateNumber = vo.getLicense();
			Integer[] verificationCondition = trafficViolationRecordService.getVerificationCondition(licensePlateNumber);
			Integer violationsQueryCarEngineLen = 99; 
			Integer violationsQueryCarCodeLen = 99; 
			if(verificationCondition != null){
				violationsQueryCarCodeLen = verificationCondition[0];
				violationsQueryCarEngineLen = verificationCondition[1];
			}
			if((violationsQueryCarEngineLen == null || violationsQueryCarEngineLen > 0) && !engineno.endsWith(apiengineno)){
				status=201;
				pack.setErrMessage("车架号或者车牌输入有误！");
			}
			if(!frameno.endsWith(apiframeno)){
				status=201;
				pack.setErrMessage("车架号或者车牌输入有误！");
			}
			pack.setHasData(status==0);
			pack.setErrorCode(status);
			
			pack.setSuccess(status==0);
			pack.setLastSearchTime(df.format(new Date()));
			JSONArray array=vinfo.getList();
			List<ViolationRecord> list=new ArrayList<ViolationRecord>();
			if(array!=null){				
				for (Object item : array) {
					if(item!=null){
						JSONObject result=JSONObject.parseObject(item.toString());						
						PackageViolation.ViolationRecord record=new PackageViolation.ViolationRecord();
						record.setTime(result.getString("time"));// 违章时间
						record.setLocation(result.getString("address"));// 违章地点
						record.setReason(result.getString("content"));// 违章原因
						record.setCount(result.getString("price"));// 违章罚款金额
						record.setStatus("0");// 违章记录状态0 末处理 1 己处理(绝大部分情况下，车行易只能返回未处理的违章)
						record.setDepartment(result.getString("agency"));// 违章采集机关
						record.setDegree(result.getString("score"));// 违章扣分
						//record.setCode("");// 违章代码						
						record.setArchive(result.getString("number"));// 违章项文书编号
						String locationName="";
						String province=result.getString("province");//获取省信息
						if(StringUtils.isNotBlank(province)){
							locationName=locationName.concat(province);
						}
						String city=result.getString("city");//获取市信息
						if(StringUtils.isNotBlank(city)){
							locationName=locationName.concat(city);
						}
						String town=result.getString("town");//获取县信息
						if(StringUtils.isNotBlank(town)){
							locationName=locationName.concat(town);
						}
						record.setLocationName(locationName);
						record.setUniqueCode(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+RandomStringUtils.randomNumeric(8));//生成唯一编码
						record.setSecondaryUniqueCode(Integer.parseInt(RandomStringUtils.randomNumeric(8)));//生成唯一编码
						//record.setTelephone("");// 联系电话
						//record.setExcutedepartment("");// 执行部门
						//record.setExcutelocation("");// 违章处理地址
						//record.setCategory("");// 违章分类类别（如该字段显示为“现场单”，请注意提示用户）
						//record.setLatefine("");// 滞纳金（目前都是0，车行易不做计算）
						//record.setPunishmentaccording("");//处罚依据
						/**
						 * Illegalentry;// 违法条款 
						 * Locationid;// 违章归属地点ID 
						 * LocationName;// 违章归属地点名
						 * DataSourceID;// 查询数据源ID 
						 * RecordType;// 实时数据/历史数据 
						 * Poundage;// 手续费（标准价，合作方请无视）
						 * CanProcess = "0";// 是否可以代办 0 不可以 1 可以
						 * UniqueCode;// 违章信息的特征码（相同车牌，违章时间，地点，该值相同）
						 * SecondaryUniqueCode;// 违章记录ID，用于下单。
						 * DegreePoundage;// 扣分手续费 
						 * Other;// 预留 
						 * CanprocessMsg;// 是否代办原因
						 * */	
						list.add(record);
					}
				}				
			}
			pack.setRecords(list);			
			return JSONObject.toJSONString(pack);
		}
		return null;
	}
	public static boolean CheckScJsNm(String lsprefix){
		for (int i = 0; i < SCJUNM.length; i++) {
			if(lsprefix.startsWith(SCJUNM[i])){
				return true;
			}			
		}
		return false;
	} 
	private static ViolationInfo getViolation(String lsprefix,String lsnum,String engineno,String frameno,String license,RedisService redisService) {
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization",APPCODE);
		Map<String, String> querys = new HashMap<String, String>();
	    querys.put("carorg", "");
	    querys.put("engineno", engineno);
	    querys.put("frameno", frameno);
	    querys.put("iscity", "1");
	    querys.put("lsnum", lsnum);
	    querys.put("lsprefix", lsprefix);
	    querys.put("lstype", "");
		ExecutorService pool = Executors.newFixedThreadPool(1);		
		try {
			// 推送给商家信息
			Callable<String> myCallable=null;
			if(CheckScJsNm(lsprefix)){				
				myCallable = new MyCallable(SCJUNMHOST, PATH, METHOD, headers, querys);
			}else{
				myCallable = new MyCallable(HOST, PATH, METHOD, headers, querys);
			}
			Future<String> fback = pool.submit(myCallable);
			// 从Future对象上获取任务的返回值
			String violationInfo = fback.get();
			log.error("查询违章信息结果(" + license + "," + engineno + "," + frameno + "):" + violationInfo);
			return getViolationIn(engineno,frameno,violationInfo,license,redisService);
		} catch (Exception e) {
			log.error("违章接口异常," + HOST + "异常信息如下:", e);
		} finally {
			// 关闭线程池
			pool.shutdown();
		}
		return null;
	}
	
	private static ViolationInfo getViolationIn(String engineno,String frameno,String violationInfo,String license,RedisService redisService){
		ViolationInfo info=new ViolationInfo();
		info.setEngineno(engineno);
		info.setFrameno(frameno);
		info.setStatus(-1);
		if(violationInfo!=null){
			JSONObject vijson=JSONObject.parseObject(violationInfo);
			if(vijson!=null){
				 int status=vijson.getIntValue("status");//获取协议中返回值状态
				 if(status==0){//状态==0代表获取成功
					 info.setStatus(status);
					 info.setMsg(vijson.getString("msg"));//获取msg
					 JSONObject result=vijson.getJSONObject("result");//获取协议体内容
					 if(result!=null){
						 String lsprefix=result.getString("lsprefix");//车牌前缀
						 String lsnum=result.getString("lsnum");//车牌剩余部分
						 info.setLsprefix(lsprefix);
						 info.setLsnum(lsnum);
						 info.setLicense(lsprefix.concat(lsnum));
						 info.setCarorg(result.getString("carorg"));//获取车管局信息
						 info.setUsercarid(result.getString("usercarid"));//获取用户信息
						 String list=result.getString("list");
						 if(StringUtils.isNotBlank(list)){
							 JSONArray array=JSONArray.parseArray(list);
							 info.setList(array);
						 }
					 }else{
						 info.setStatus(-1);
						 return info;
					 }
				 }else{
					 info.setStatus(status);
					 info.setMsg(vijson.getString("msg"));//获取msg
					 return info;
				 }
			}
			HashMap<byte[], byte[]> violationInMap=new HashMap<byte[], byte[]>();
			String violationIn =JSONObject.toJSONString(info);
			if(redisService!=null){
				violationInMap.put(license.getBytes(), violationIn.getBytes());
				redisService.hMSet(String.format(CacheNameDefinition.Violation_Inquire_Info, license), violationInMap);
				redisService.setExpiration(String.format(CacheNameDefinition.Violation_Inquire_Info, license), CacheNameDefinition.Default_Expiration);
			}
		}
		return info;
	}
	
	public static void main(String[] args) {
		/*ViolationVo vo=new ViolationVo("渝B72R05", "123456", "014130");
		String info =compatibleViolation(vo, null);
		System.out.println(info);*/
		
		String Violation_Inquire_Info_Strict="Violation_Inquire_Info_Strict:License_%s,engineno_%s,frameno_%s";//违章查询信息redis,License:车牌号，engineno:发动机号，frameno:车架号
		System.out.println(String.format(Violation_Inquire_Info_Strict, "aaa",null,""));
		
	}
}
