/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.SysconfigDao;
import com.arf.core.entity.Sysconfig;
import com.arf.core.service.SysconfigService;



/**
 * Service - 系统配置表
 * 
 * @author arf
 * @version 4.0
 */
@Service("sysconfigServiceImpl")
public class SysconfigServiceImpl extends BaseServiceImpl<Sysconfig, Long> implements SysconfigService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "sysconfigDaoImpl")
	private SysconfigDao sysconfigDao;

	@Resource(name = "redisService")
	private RedisService redisService;
	
	public static final String SYSTEMCONFIG_KEY_PREFIX="SYSTEMCONFIG:";//系统配置redi存储key 前缀
	public static final String SYSTEMCONFIGMAP=SYSTEMCONFIG_KEY_PREFIX+"ALLMAP";//系统配置redi存储key
	public static final long SYSTEMCONFIGEXPIREDTIME=30*60;//系统配置过期时间30分钟
	public static final String SYSTEMCONFIGPARENTMAP=SYSTEMCONFIG_KEY_PREFIX+"PARENTMAP%s";//系统配置redi存储key
	
	
	@Override
	protected BaseDao<Sysconfig, Long> getBaseDao() {
		return sysconfigDao;
	}

    @Override
    @Deprecated
    public Sysconfig getValueByName(String name,String defvalue,String defdesc) {
        return sysconfigDao.getValueByName(name,defvalue,defdesc);
    }

    /**
     * 根据参数名获取参数值，参数名在表中不存在时创建
     * @param name
     * @return
     */
    @Override
	public Sysconfig getValueByName(String name){
        return sysconfigDao.getValueByName(name);
    }

	@Override
	public List<Sysconfig> getAllValue() {		
		return sysconfigDao.getAllValue();
	}

	@Override
	public List<Sysconfig> getValueByParent(String parentname) {		
		return sysconfigDao.getValueByParent(parentname);
	}

	@Override
	public String getValue(String name) {
		String value= redisService.hGet(SYSTEMCONFIGMAP, name);
		if(value==null){
			Map<String,String> parmMap= storageRedisDate(name);
			if(parmMap!=null&&parmMap.size()>0)
				return parmMap.get(name);
		}		
		return value;
	}

	@Override
	public Map<String, String> getParentValue(String parentname) {
		Map<String,String> mapValue= redisService.hgetAll(String.format(SYSTEMCONFIGPARENTMAP, parentname));
		if(mapValue!=null&&mapValue.size()>0){
			return mapValue;
		}
		return storageRedisDate(parentname);
	}
	
	private Map<String, String> storageRedisDate(String parentname){
		Map<String,Map<byte[],byte[]>> parmMap=new HashMap<String, Map<byte[],byte[]>>();//存储redis变量
		List<Sysconfig> sysconfig= getAllValue();//获取所有配置信息
		Map<String, String> backMap=new HashMap<String, String>();
		for (Sysconfig sysconf : sysconfig) {
			String dbarentname= sysconf.getParentname();
			String parmname= sysconf.getParmname();//参数名称
			String parmvalue=sysconf.getParmvalue();//参数值
			if(StringUtils.isBlank(parmname)||StringUtils.isBlank(parmvalue)){continue;}//进行节点配置文件存储
			if(StringUtils.isNotEmpty(dbarentname)){//参数父节点不为空时				
				Map<byte[],byte[]> parentMap=parmMap.get(String.format(SYSTEMCONFIGPARENTMAP, dbarentname));
				if(parentMap==null){
					parentMap=new HashMap<byte[], byte[]>();				
				}
				parentMap.put(parmname.getBytes(), parmvalue.getBytes());
				parmMap.put(String.format(SYSTEMCONFIGPARENTMAP, dbarentname), parentMap);
				if(parentname.equals(dbarentname)){					
					backMap.put(parmname, parmvalue);
				}
			}else{
				Map<byte[],byte[]> parmDbMap=parmMap.get(SYSTEMCONFIGMAP);
				if(parmDbMap==null){
					parmDbMap=new HashMap<byte[], byte[]>();
				}
				parmDbMap.put(parmname.getBytes(), parmvalue.getBytes());
				parmMap.put(SYSTEMCONFIGMAP, parmDbMap);
				if(parentname.equals(parmname)){					
					backMap.put(parmname, parmvalue);
				}
			}			
		}
		if(parmMap!=null&&parmMap.size()>0){//如果系统配置有数据直接存储redis
			for (String parm : parmMap.keySet()) {
				redisService.hMSet(parm, parmMap.get(parm));
				redisService.setExpiration(parm, SYSTEMCONFIGEXPIREDTIME);
			}
		}
		return backMap;
	}

	@Override
	public String getValueByNameSpace(String ns) {
		if(StringUtils.isBlank(ns)){
			return null;
		}
		if(ns.contains(".")){
			String[] names = ns.split("\\.");
			if(names.length != 2){
				log.error("查询参数ns格式错误(" + ns + ")");
				return null;
			}
			String parent = names[0];
			String paramName = names[1];
			String val = redisService.hGet(String.format(SYSTEMCONFIGPARENTMAP, parent), paramName);
			if(StringUtils.isNotBlank(val)){
				return val;
			}else{ //缓存没找到  从数据库查找
				List<Sysconfig> configList = this.findList(null, (List<Order>)null, new Filter("parentname",Operator.eq,parent),
						new Filter("parmname",Operator.eq,paramName));
				if(CollectionUtils.isNotEmpty(configList)){
					val = configList.get(0).getParmvalue();
					redisService.hset(String.format(SYSTEMCONFIGPARENTMAP, parent), paramName,val);
					return val;
				}else{
					return null;
				}
			}
		}else{
			String val = redisService.hGet(SYSTEMCONFIGMAP, ns);
			if(StringUtils.isNotBlank(val)){
				return val;
			}else{ //缓存没找到  从数据库查找
				List<Sysconfig> configList = this.findList(null, (List<Order>)null,new Filter("parmname",Operator.eq,ns));
				if(CollectionUtils.isNotEmpty(configList)){
					val = configList.get(0).getParmvalue();
					//缓存到redis
					redisService.hset(SYSTEMCONFIGMAP, ns,val);
					return val;
				}else{
					return null;
				}
			}
		}
	}

	@Override
	public String getValue(String parent, String paraName) {
		if(StringUtils.isBlank(paraName)){
			return null;
		}
		if(StringUtils.isBlank(parent)){
			return this.getValueByNameSpace(paraName);
		}else{
			return this.getValueByNameSpace(parent + "." + paraName);
		}
		
	}

}