package com.arf.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.core.cache.redis.RedisService;
import com.arf.core.entity.AdministrativeModel;
import com.arf.core.service.AdministrativeModelService;
import com.arf.core.service.SysconfigService;
import com.arf.eparking.dto.ParKingRealTimeStatusDto;
import com.arf.eparking.entity.ParkingInfo;
import com.arf.eparking.service.ParkingInfoService;
import com.arf.platform.LPConstants;
import com.arf.platform.entity.PParKingrealTimeStatus;
import com.arf.platform.service.BaseService;
import com.arf.platform.service.IParkingUpReportBusinessDealService;
import com.arf.platform.service.PParKingrealTimeStatusService;
import com.arf.platform.service.PParkingcarService;
import com.arf.platform.service.RStoprecordService;
import com.arf.platform.vo.ParkingStatusListVo;
import com.arf.platform.vo.ParkingStatusVo;
import com.arf.platform.vo.RequestDataVo;
import com.arf.traffic.TrafficInterface;

@Service("parkingStatusReportBusinessDealServiceImpl")
public class ParkingStatusReportBusinessDealServiceImpl extends BaseService implements IParkingUpReportBusinessDealService {
	
	private static Logger log = LoggerFactory.getLogger(ParkingStatusReportBusinessDealServiceImpl.class);
	
	/**
	 * 停车场实时状态信息服务
	 */
	@Resource(name = "PParKingrealTimeStatusServiceImpl")
	private PParKingrealTimeStatusService pParKingrealTimeStatusServiceImpl;
	
	@Resource(name = "administrativeModelServiceImpl")
	private AdministrativeModelService administrativeModelServiceImpl;
	
	@Resource(name = "sysconfigServiceImpl")
	private SysconfigService sysconfigService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "parkingInfoServiceImpl")
	private ParkingInfoService parkingInfoService;

	@Resource(name = "PParkingcarServiceImpl")
	private PParkingcarService pParkingcarServiceImpl;
	
	@Resource(name = "RStoprecordServiceImpl")
	private RStoprecordService rStoprecordServiceImpl;
	
	@Override
	public Map<String,Object> process(String version, String communityNo, RequestDataVo vo) {
		StringBuffer failedList = new StringBuffer();
		ParkingStatusListVo parkingStatusListVo = (ParkingStatusListVo)vo;
		List<ParkingStatusVo> listVo = parkingStatusListVo.getVo();
		
		//循环处理停车场实时状态信息
		for (ParkingStatusVo parkingStatusVo : listVo) {
			String versionStr = parkingStatusListVo.getVo().get(0).getVer();
			int ver = Integer.valueOf(versionStr);
			Map<String,Object> mapPro = new HashMap<String,Object>();
			//数据验证，小区/停车场是否存在
			Map<String, Object> responseMsg = null;
			List<ParkingStatusVo> parkingStatusVoList = new ArrayList<ParkingStatusVo>();
			parkingStatusVoList.add(parkingStatusVo);
			responseMsg = checkData(BaseService.PARKINGUPREQ_TYPE_STATUS,parkingStatusVoList);
			if(responseMsg != null){
				failedList.append(parkingStatusVo.getCommunityNo()).append(",");
				continue;
			}
			//判断版本号
			if(ver == 1){
				try {
					//判断停车场类型
					if(parkingStatusVo.getParkingType() == BaseService.PARKING_TYPE_COMMUNITY){
						mapPro = processVersion1(parkingStatusVo);
					}else if(parkingStatusVo.getParkingType() == BaseService.PARKING_TYPE_EMERGENCY){
						mapPro = processVersion1Emergency(parkingStatusVo);
					}
					TrafficInterface inf=new TrafficInterface();//调用数据汇报接口（E停车珠海妇幼保健医院到交管局）出场汇报
					inf.parkingStatusReport(parkingStatusVo, sysconfigService,pParkingcarServiceImpl,rStoprecordServiceImpl);
				} catch (Exception e) {
					//处理失败，添加到失败名单
					log.error(">>>>>:[停车场实时状态汇报process]操作异常", e);
					e.printStackTrace();
					failedList.append(parkingStatusVo.getCommunityNo()).append(",");
					continue;
				}
				//处理失败，添加到失败名单
				if(!"0".equals(mapPro.get(LPConstants.RESULTMAP_KEY_STATUS))){
					failedList.append(parkingStatusVo.getCommunityNo()).append(",");
				}
			}else{
				failedList.append(parkingStatusVo.getCommunityNo()).append(",");
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS,LPConstants.RETURNCODE_SUCCESS );
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK!");
		//格式“小区编号1，小区编号2，小区编号3，...”
		map.put(LPConstants.RETURNKEY_FAILED_LIST,failedList);
		return map;
		
	}

	/**
	 * 版本1的处理方式(小区(物业信息))
	 * @param vo
	 * @return
	 */
	private Map<String,Object> processVersion1(ParkingStatusVo vo) {
		//1、取出业务数据
		Integer total = vo.getTotal();//总车位数
		Integer empty = vo.getEmpty();//总空车位数
		Integer sheduled = vo.getSheduled();//总可预定车位数,如无可预定车位用0表示
		Integer rscheduled = vo.getRscheduled();//空可预定车位,如无空可预定车位用0表示
		Integer internal = vo.getInternal();//内部总车位数量,如车位不分内外车位填0
		Integer rinternal = vo.getRinternal();//内部空车位数量,如车位不分内外车位填0
		Integer status = vo.getStatus();//车位使用状态 0充足；1紧张；2已满
		
		//==========操作缓存
		//2、查询缓存
//		ParKingRealTimeStatusDto cachedObj = redisService.get(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status 
//				+ vo.getCommunityNo(),ParKingRealTimeStatusDto.class);
		ParKingRealTimeStatusDto cachedObj = redisService.hGet(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,
				vo.getCommunityNo(),ParKingRealTimeStatusDto.class);
		long redisUpdate = 0;
		long redisSave = 0;
		if(cachedObj != null){
			if(cachedObj.getParkingInfo() != null){
				cachedObj.setParkingInfo(null);
			}
			cachedObj.setParkingId(vo.getCommunityNo());
			cachedObj.setParkingName(vo.getCommunity().getCommunityName());//设置小区
			cachedObj.setEmpty(total);
			cachedObj.setTotalEmpty(empty);
			cachedObj.setSheduled(sheduled);
			cachedObj.setRscheduled(rscheduled);
			cachedObj.setInternal(internal);
			cachedObj.setInternalEmpty(rinternal);
			cachedObj.setStatus(Short.valueOf(status.toString()));
			cachedObj.setLastTime(new Date());
			
			Integer popertyNumberCached = null;
			if(vo.getCommunity()!=null){
				popertyNumberCached = vo.getCommunity().getProperty_number();
			}
			//查询小区物业信息
			AdministrativeModel administrativeModelCached = null;
			if(popertyNumberCached != null){
				administrativeModelCached = administrativeModelServiceImpl.sellectByPropretyNumber(popertyNumberCached);
			}
			if(administrativeModelCached != null){
				cachedObj.setBranchId(administrativeModelCached.getBranch_id());
				cachedObj.setHeadOfficeId(administrativeModelCached.getHeadoffice_id());
			}
			cachedObj.setPopertyNumber(popertyNumberCached);
			
			redisService.hset(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,vo.getCommunityNo(), cachedObj);
		}else{//==========redis没有，操作数据库	
			//2、创建 停车场实时状态 实体类
			PParKingrealTimeStatus pParKingrealTimeStatus = null;
			//3、查询数据库，是否存在 停车场实时状态 记录
			List<PParKingrealTimeStatus> listPParKingrealTimeStatus = pParKingrealTimeStatusServiceImpl.getPParKingrealTimeStatusByCommunityNo(vo.getCommunityNo());
			if(CollectionUtils.isEmpty(listPParKingrealTimeStatus)){
				pParKingrealTimeStatus = new PParKingrealTimeStatus();
			}else{
				pParKingrealTimeStatus = listPParKingrealTimeStatus.get(0);
			}
			pParKingrealTimeStatus.setParkingId(vo.getCommunityNo());
			pParKingrealTimeStatus.setParkingName(vo.getCommunity().getCommunityName());//设置小区
			pParKingrealTimeStatus.setEmpty(total);
			pParKingrealTimeStatus.setTotalEmpty(empty);
			pParKingrealTimeStatus.setSheduled(sheduled);
			pParKingrealTimeStatus.setRscheduled(rscheduled);
			pParKingrealTimeStatus.setInternal(internal);
			pParKingrealTimeStatus.setInternalEmpty(rinternal);
			pParKingrealTimeStatus.setStatus(Short.valueOf(status.toString()));
			pParKingrealTimeStatus.setLastTime(new Date());
			
			Integer popertyNumber = null;
			if(vo.getCommunity()!=null){
				popertyNumber = vo.getCommunity().getProperty_number();
			}
			//查询小区物业信息
			AdministrativeModel administrativeModel = null;
			if(popertyNumber != null){
				administrativeModel = administrativeModelServiceImpl.sellectByPropretyNumber(popertyNumber);
			}
			if(administrativeModel != null){
				pParKingrealTimeStatus.setBranchId(administrativeModel.getBranch_id());
				pParKingrealTimeStatus.setHeadOfficeId(administrativeModel.getHeadoffice_id());
			}
			pParKingrealTimeStatus.setPopertyNumber(popertyNumber);
			//4、数据库操作
			if(CollectionUtils.isEmpty(listPParKingrealTimeStatus)){
				pParKingrealTimeStatus.setIsDrop((short)0);
				pParKingrealTimeStatusServiceImpl.save(pParKingrealTimeStatus);
			}else{
				pParKingrealTimeStatusServiceImpl.update(pParKingrealTimeStatus);
			}
			
			cachedObj = new ParKingRealTimeStatusDto();
			cachedObj.setParkingInfo(null);
			cachedObj.setParkingId(vo.getCommunityNo());
			cachedObj.setParkingName(vo.getCommunity().getCommunityName());//设置小区
			cachedObj.setEmpty(total);
			cachedObj.setTotalEmpty(empty);
			cachedObj.setSheduled(sheduled);
			cachedObj.setRscheduled(rscheduled);
			cachedObj.setInternal(internal);
			cachedObj.setInternalEmpty(rinternal);
			cachedObj.setStatus(Short.valueOf(status.toString()));
			cachedObj.setLastTime(new Date());
			//小区物业信息
			if(administrativeModel != null){
				cachedObj.setBranchId(administrativeModel.getBranch_id());
				cachedObj.setHeadOfficeId(administrativeModel.getHeadoffice_id());
			}
			cachedObj.setPopertyNumber(popertyNumber);
			
			redisService.hset(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,vo.getCommunityNo(), cachedObj);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		return map;
	}
	
	/**
	 * 版本1的处理方式(紧急场所(停车场信息))
	 * @param vo
	 * @return
	 */
	private Map<String,Object> processVersion1Emergency(ParkingStatusVo vo) {
		//1、取出业务数据
		Integer total = vo.getTotal();//总车位数
		Integer empty = vo.getEmpty();//总空车位数
		Integer sheduled = vo.getSheduled();//总可预定车位数,如无可预定车位用0表示
		Integer rscheduled = vo.getRscheduled();//空可预定车位,如无空可预定车位用0表示
		Integer internal = vo.getInternal();//内部总车位数量,如车位不分内外车位填0
		Integer rinternal = vo.getRinternal();//内部空车位数量,如车位不分内外车位填0
		Integer status = vo.getStatus();//车位使用状态 0充足；1紧张；2已满
		
		//==========操作缓存
		//2、查询缓存
//		ParKingRealTimeStatusDto cachedObj = redisService.get(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status 
//						+ vo.getCommunityNo(),ParKingRealTimeStatusDto.class);
		ParKingRealTimeStatusDto cachedObj = redisService.hGet(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,
				vo.getCommunityNo(),ParKingRealTimeStatusDto.class);
		ParkingInfo parkingInfo = vo.getParkingInfo();
		Integer parkingState = parkingInfo.getParkingState();//状态:-1:已删除：0:新建（草稿）状态；1:运营中；
		if(parkingState != null && parkingState == 1){//停车场为运营中的
			long redisUpdate = 0;
			long redisSave = 0;
			if(cachedObj != null){//缓存存在（更新缓存）
				if(cachedObj.getParkingInfo() == null){
//					parkingInfo = parkingInfoService.findByParkingNo(vo.getCommunityNo());
					cachedObj.setParkingInfo(parkingInfo);
				}
				cachedObj.setModifyDate(new Date());
				cachedObj.setParkingId(vo.getCommunityNo());
				cachedObj.setParkingName(vo.getParkingInfo().getParkingName());
				cachedObj.setEmpty(total);
				cachedObj.setTotalEmpty(empty);
				cachedObj.setSheduled(sheduled);
				cachedObj.setRscheduled(rscheduled);
				cachedObj.setInternal(internal);
				cachedObj.setInternalEmpty(rinternal);
				cachedObj.setStatus(Short.valueOf(status.toString()));
				cachedObj.setLastTime(new Date());
				
				redisService.hset(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,vo.getCommunityNo(), cachedObj);
			}else{//缓存不存在（操作数据库、写入缓存）
				//2、创建 停车场实时状态 实体类
				PParKingrealTimeStatus pParKingrealTimeStatus = null;
				//3、查询数据库，是否存在 停车场实时状态 记录
				List<PParKingrealTimeStatus> listPParKingrealTimeStatus = pParKingrealTimeStatusServiceImpl.getPParKingrealTimeStatusByCommunityNo(vo.getCommunityNo());
				if(CollectionUtils.isEmpty(listPParKingrealTimeStatus)){
					pParKingrealTimeStatus = new PParKingrealTimeStatus();
				}else{
					pParKingrealTimeStatus = listPParKingrealTimeStatus.get(0);
				}
				pParKingrealTimeStatus.setParkingId(vo.getCommunityNo());
				pParKingrealTimeStatus.setParkingName(vo.getParkingInfo().getParkingName());
				pParKingrealTimeStatus.setEmpty(total);
				pParKingrealTimeStatus.setTotalEmpty(empty);
				pParKingrealTimeStatus.setSheduled(sheduled);
				pParKingrealTimeStatus.setRscheduled(rscheduled);
				pParKingrealTimeStatus.setInternal(internal);
				pParKingrealTimeStatus.setInternalEmpty(rinternal);
				pParKingrealTimeStatus.setStatus(Short.valueOf(status.toString()));
				pParKingrealTimeStatus.setLastTime(new Date());
				
				Integer popertyNumber = null;
				if(vo.getCommunity()!=null){
					popertyNumber = vo.getCommunity().getProperty_number();
				}
				//查询小区物业信息
				AdministrativeModel administrativeModel = null;
				if(popertyNumber != null){
					administrativeModel = administrativeModelServiceImpl.sellectByPropretyNumber(popertyNumber);
				}
				if(administrativeModel != null){
					pParKingrealTimeStatus.setBranchId(administrativeModel.getBranch_id());
					//pParKingrealTimeStatus.setHeadOfficeId(administrativeModel.getHeadoffice_id());
				}
				pParKingrealTimeStatus.setPopertyNumber(popertyNumber);
				
				//4、数据库操作
				if(CollectionUtils.isEmpty(listPParKingrealTimeStatus)){
//					parkingInfo = parkingInfoService.findByParkingNo(vo.getCommunityNo());
					pParKingrealTimeStatus.setIsDrop((short)0);
					pParKingrealTimeStatusServiceImpl.save(pParKingrealTimeStatus);
				}else{
					pParKingrealTimeStatusServiceImpl.update(pParKingrealTimeStatus);
				}
				//第一次实时状态汇报，同时更新到redis
				cachedObj = new ParKingRealTimeStatusDto(parkingInfo);
				cachedObj.setParkingInfo(parkingInfo);
				cachedObj.setModifyDate(new Date());
				cachedObj.setParkingId(vo.getCommunityNo());
				cachedObj.setParkingName(vo.getParkingInfo().getParkingName());
				cachedObj.setEmpty(total);
				cachedObj.setTotalEmpty(empty);
				cachedObj.setSheduled(sheduled);
				cachedObj.setRscheduled(rscheduled);
				cachedObj.setInternal(internal);
				cachedObj.setInternalEmpty(rinternal);
				cachedObj.setStatus(Short.valueOf(status.toString()));
				cachedObj.setLastTime(new Date());
				redisService.hset(PParKingrealTimeStatus.Key_Prefix_Cache_Parking_Realtime_Status_Map,vo.getCommunityNo(), cachedObj);
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		return map;
	}

}
