package com.arf.carbright.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.carbright.dao.PUserRangeDao;
import com.arf.carbright.dao.PackageTypeDao;
import com.arf.carbright.entity.PBusiness;
import com.arf.carbright.entity.PUserRange;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.PackageType.IsEnabled;
import com.arf.carbright.service.PUserRangService;
import com.arf.carbright.service.PbusinessService;
import com.arf.core.AppMessage;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.core.util.BeanUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service("puserRangService")
public class PUserRangServiceImpl extends BaseServiceImpl<PUserRange, Long> implements PUserRangService {

	@Resource(name = "pUserRangeDao")
	private PUserRangeDao pUserRangeDao;

	@Resource(name = "packageTypeDao")
	private PackageTypeDao packageTypeDao;
	
	@Resource(name = "pBusinessService")
	private PbusinessService businessService;
	
	@Resource(name = "redisService")
	private RedisService redisService;

	@Override
	protected BaseDao<PUserRange, Long> getBaseDao() {
		return pUserRangeDao;
	}

	public AppMessage selectPackage(String parkingId) {
		AppMessage message = new AppMessage();
		List<Map<String, Object>> types = new ArrayList<Map<String, Object>>();
		List<PUserRange> psList = pUserRangeDao.findbyparkingID(parkingId);
		if (psList == null || psList.isEmpty()) {
			message.setCode(10001);
			message.setMsg("没有符合范围的套餐");
			return message;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (PUserRange pUserRange : psList) {
			
			//查询商户信息
			long businessId = pUserRange.getBusinessId();
			final PBusiness business = businessService.findRedisToDbById(businessId);//商户
			
			List<PackageType> packageTypes = packageTypeDao.findbyUrnenabled(pUserRange.getUseRangeNum(),IsEnabled.enablem.ordinal());
			if (packageTypes == null || packageTypes.isEmpty()) {
				continue;
			}
			List<Map<String,Object>> pkgTypes = Lists.transform(packageTypes, new Function<PackageType, Map<String,Object>>() {

				@Override
				public Map<String,Object> apply(PackageType input) {
					Map<String,Object> mapBean = BeanUtils.bean2Map(input);
					if(business !=  null){
						mapBean.put("businessName", business.getBusinessName());
						mapBean.put("businessNum", business.getBusinessNum());
						mapBean.put("businessId", business.getId());
						
						//商户营业时间
						String openingTimes = redisService.get(PBusiness.Prefix_Cache_Key_Opening_Times + business.getBusinessNum());
						mapBean.put("openingTimes", openingTimes);
					}
					return mapBean;
				}
				
			});
			types.addAll(pkgTypes);
		}
		
		map.put("data", types);
		
		message.setCode(200);
		message.setMsg("查询成功");
		message.setExtrDatas(map);
		return message;
	}

	@Override
	public PUserRange findByRangeNum(String rangeNum) {
		List<PUserRange> ranges = this.findList(null, null, new Filter("useRangeNum",Operator.eq,rangeNum));
		if(CollectionUtils.isNotEmpty(ranges)){
			return ranges.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<PUserRange> findbyBusinessId(int businessId) {
		return pUserRangeDao.findbyBusinessId(businessId);
	}
}
