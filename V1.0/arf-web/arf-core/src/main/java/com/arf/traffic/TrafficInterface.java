package com.arf.traffic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.arf.core.service.SysconfigService;
import com.arf.core.util.BeanUtils;
import com.arf.platform.service.PParkingcarService;
import com.arf.platform.service.RStoprecordService;
import com.arf.platform.vo.ArriveReportVo;
import com.arf.platform.vo.LeaveReportVo;
import com.arf.platform.vo.ParkingStatusVo;
import com.arf.traffic.dto.BaseRequestDto;
import com.arf.traffic.dto.ParkFlowInDto;
import com.arf.traffic.dto.ParkFlowOutDto;
import com.arf.traffic.dto.ResponseDto;


public class TrafficInterface {
	
	private static String HostName="https://121.42.25.176:8013";//请求地址
	private static String Authorization="Basic cDAwMDE6Y0dzd01EQXg=";//加密参数
	//private static final String KEY= "F7A0B971B199FD2A1017CEC5";//加密秘钥
	//private static final String IV = "20161216";
	//private static final String UserName="ktapi";
	//private static final String Pwd ="0306A9";
	private static final Map<String,Object> header=new HashMap<String, Object>();
	public static final String LongPattern="yyyy-MM-dd HH:mm:ss";
	private static final String ShortPattern="yyyyMMdd";
	private static final String PostParkStatusUrl="/api/v1/park/PostParkStatus";
	private static final String PostParkFlowInUrl="/api/v1/park/PostParkFlowIn";
	private static final String PostParkFlowOutUrl="/api/v1/park/PostParkFlowOut";	
	
	public static final String ParkingIdConversion="PARKINGIDCONVERSION";//停车场编号转换
	public static final String PARKING_URL="PARKING_URL";//请求停车场url
	public static final String AUTHORIZATION="AUTHORIZATION";//加密方式
	
	private static Logger log = LoggerFactory.getLogger(TrafficInterface.class);

		
	public static String getIv() {
		return DateFormatUtils.format(new Date(), ShortPattern);
	}

	public static Map<String, Object> getHeader() {
		header.put("Content-type", "application/json;charset=utf-8");
		header.put("Authorization", getAuthorization());
		return header;
	}
	
	public static String getHostName() {
		return HostName;
	}

	public static void setHostName(String hostName) {
		HostName = hostName;
	}

	public static void setAuthorization(String authorization) {
		Authorization = authorization;
	}

	public static String getAuthorization() {
		return Authorization;
	}

	public static void main(String[] args) {
		TrafficInterface inf=new TrafficInterface();
		ParkingStatusVo parkingStatusVo=new ParkingStatusVo();
		parkingStatusVo.setCommunityNo("p0001");
		parkingStatusVo.setTotal(500);
		parkingStatusVo.setEmpty(200);
		inf.parkingStatusReport(parkingStatusVo,null,null,null);
		ArriveReportVo arriveReportVo=new ArriveReportVo();
		arriveReportVo.setArriveTime(System.currentTimeMillis()/1000L+"");
		arriveReportVo.setCommunityNo("p0001");
		arriveReportVo.setLicense("粤B00001");		
		arriveReportVo.setArriveCarImgUrl("http://www.baidu.com/aa.jpg");
		inf.parkingArriveReport(arriveReportVo,null);
		LeaveReportVo leaveReportVo=new LeaveReportVo();
		
		leaveReportVo.setLeaveTime(System.currentTimeMillis()/1000L+"");
		leaveReportVo.setCommunityNo("p0001");
		leaveReportVo.setLicense("粤B00002");
		leaveReportVo.setLeaveCarImgUrl("http://www.baidu.com/bb.jpg");
		inf.parkingLeaveReport(leaveReportVo,null);
		
	}
	
	public enum ParkInterface{
		PostParkStatus,//3.1停车场实时状态推送
		PostParkFlowIn,//3.2停车场车辆进场数据推送
		PostParkFlowOut;//3.3停车场车辆出场数据推送
	}

	
	private ResponseDto TrafficParkReport(String url,String params,Map<String, Object> header,String charset){		
		ExecutorService pool = Executors.newFixedThreadPool(1); 
	       try {
		       //推送消息到交管局	    	   
	    	   Callable<ResponseDto> myCallable=new MyCallable(url, params, header, charset);
		       Future<ResponseDto> fback = pool.submit(myCallable); 
		       //从Future对象上获取任务的返回值 
		       ResponseDto dto=fback.get();
		       return dto;
			} catch (Exception e) {
				log.error("请求协议地址"+url+"参数如下"+params,e);
			}finally{
			//关闭线程池 
	           pool.shutdown(); 
			}
	       return new ResponseDto();
	}
	
	private String requestTrafficInterface(final String params,ParkInterface parkInterface){		
		if(parkInterface!=null){
			String url = null;
			if(parkInterface.ordinal()==ParkInterface.PostParkStatus.ordinal()){//如果是停车场状态汇报
				url=PostParkStatusUrl;
			}
			
			if(parkInterface.ordinal()==ParkInterface.PostParkFlowIn.ordinal()){//如果是停车场车辆进场数据推送				
				url=PostParkFlowInUrl;
			}
			
			if(parkInterface.ordinal()==ParkInterface.PostParkFlowOut.ordinal()){//如果是停车场车辆出场数据推送
				url=PostParkFlowOutUrl;				
			}			
			final String requrl=TrafficInterface.getHostName().concat(url);
			ExecutorService es = Executors.newCachedThreadPool();
			es.execute(new Runnable() {
				@Override
				public void run() {
					try {
						ResponseDto dto=TrafficParkReport(requrl,params,TrafficInterface.getHeader(),null);
						log.info("请求协议"+requrl+" 返回HTTPCODE:"+dto.getHttpCode()+" 返回MESSAGE:"+dto.getHttpMess() +" 返回协议体"+dto.getJsonObject());
					} catch (Exception e) {
						log.error("【异常】请求协议地址"+requrl+"参数如下"+params,e);
					}
				}
			});
			es.shutdown();
		}
		return null;
	}
	public void parkingStatusReport(ParkingStatusVo parkingStatusVo,SysconfigService sysconfigService,PParkingcarService pParkingcarServiceImpl,RStoprecordService rStoprecordServiceImpl){//停车场定时汇报协议
		/*【2017-3-14 11:23:34屏蔽掉停车场流量汇报——保留车来车走汇报】if(parkingStatusVo!=null){
			String communityNo=parkingStatusVo.getCommunityNo();
			String parkingId=chackParkingId(communityNo, sysconfigService);//获取到停车场ID
			if(StringUtils.isNotBlank(parkingId)){//如果不包含停车场就返回
				ParkStatusDto statusDto=new ParkStatusDto();
				statusDto.setFreePortNum(parkingStatusVo.getEmpty());//获取停车场空车位
				statusDto.setTotalPortNum(parkingStatusVo.getTotal());//获取停车场总车位
				int innum=0;int outnum=0;
				try {
					innum=pParkingcarServiceImpl.findAdmissionTodayByParkingId(communityNo);//当天进入的车辆
					outnum=rStoprecordServiceImpl.findAppearanceTodayByParkingId(communityNo);//当天出场车辆
				} catch (Exception e) {}				
				statusDto.setInNum(innum+outnum);//当天进入车辆 （当天在场记录+出场的记录）
				statusDto.setOutNum(outnum);//当天出场车辆
				statusDto.setParkingId(parkingId);//停车场编号
				List<Map<String,Object>> statusList =new ArrayList<Map<String,Object>>();
				statusList.add(BeanUtils.bean2Map(statusDto));
				BaseRequestDto requestDto=new BaseRequestDto(parkingId,"1",statusList);			
				requestTrafficInterface(JSONObject.toJSONString(requestDto),ParkInterface.PostParkStatus);
			}			
		}*/
	}
	
	public void parkingArriveReport(ArriveReportVo arriveReportVo,SysconfigService sysconfigService){//停车场车来汇报协议
		if(arriveReportVo!=null){
			String communityNo=arriveReportVo.getCommunityNo();
			String parkingId=chackParkingId(communityNo, sysconfigService);//获取到停车场ID
			if(StringUtils.isNotBlank(parkingId)){//如果不包含停车场就返回
				ParkFlowInDto flowInDto=new ParkFlowInDto();
				flowInDto.setParkingId(parkingId);//停车场编号
				flowInDto.setPlateNo(arriveReportVo.getLicense());//车来汇报车牌号码
				String arriveTime=arriveReportVo.getArriveTime();
				Date arriveDate=new Date();
				if(StringUtils.isNumeric(arriveTime)){
					arriveDate= new Date(Long.valueOf(arriveTime)*1000l);
				} 
				flowInDto.setEntryTime(DateFormatUtils.format(arriveDate, LongPattern));//车来汇报车来时间
				flowInDto.setEntryImg(arriveReportVo.getArriveCarImgUrl());//车来汇报照片
				List<Map<String,Object>> flowInList =new ArrayList<Map<String,Object>>();
				flowInList.add(BeanUtils.bean2Map(flowInDto));
				BaseRequestDto requestDto=new BaseRequestDto(parkingId,"1",flowInList);
				requestTrafficInterface(JSONObject.toJSONString(requestDto), ParkInterface.PostParkFlowIn);
			}			
		}
	}
	
	public void parkingLeaveReport(LeaveReportVo leaveReportVo,SysconfigService sysconfigService){//停车场车走汇报协议
		if(leaveReportVo!=null){
			try {
				String communityNo=leaveReportVo.getCommunityNo();
				String parkingId=chackParkingId(communityNo, sysconfigService);//获取到停车场ID
				if(StringUtils.isNotBlank(parkingId)){//如果不包含停车场就返回
					ParkFlowOutDto flowOutDto=new ParkFlowOutDto();
					flowOutDto.setParkingId(parkingId);//停车场编号
					flowOutDto.setPlateNo(leaveReportVo.getLicense());//车走汇报车牌号码
					String leaveTime=leaveReportVo.getLeaveTime();
					Date leaveDate=new Date();
					if(StringUtils.isNumeric(leaveTime)){
						leaveDate= new Date(Long.valueOf(leaveTime)*1000l);
					}
					flowOutDto.setLeaveTime(DateFormatUtils.format(leaveDate, LongPattern));//车来汇报车走时间
					flowOutDto.setLeaveImg(leaveReportVo.getLeaveCarImgUrl());//车走汇报照片
					List<Map<String,Object>> flowOutList =new ArrayList<Map<String,Object>>();
					flowOutList.add(BeanUtils.bean2Map(flowOutDto));
					BaseRequestDto requestDto=new BaseRequestDto(parkingId,"1",flowOutList);
					requestTrafficInterface(JSONObject.toJSONString(requestDto), ParkInterface.PostParkFlowOut);
				}
			} catch (Exception e) {
				log.error("E停车调用推送[车辆出场信息]交管局接口异常",e);
			}						
		}
	}
	
	/**将arf项目中parkingid 转换为交管局识别的ID
	 * @param communityNo
	 * @param sysconfigService
	 * @return
	 */
	public String chackParkingId(String communityNo,SysconfigService sysconfigService){
		if(sysconfigService!=null&&StringUtils.isNotBlank(communityNo)){//校验参数是否为空及小区编号是否为空
			Map<String, String> parkingIds=sysconfigService.getParentValue(ParkingIdConversion);			
			if(parkingIds!=null&&parkingIds.size()>0){
				String parkingUrl=parkingIds.get(PARKING_URL);//获取请求URL
				if(StringUtils.isNotBlank(parkingUrl)){
					setHostName(parkingUrl);
				}
				String authorizationdb=parkingIds.get(AUTHORIZATION);//获取加密方式
				if(StringUtils.isNotBlank(authorizationdb)){
					setAuthorization(authorizationdb);
				}
				if(parkingIds.containsKey(communityNo)){
					return parkingIds.get(communityNo);
				}
			}
		}
		return null;		
	}
	
}
