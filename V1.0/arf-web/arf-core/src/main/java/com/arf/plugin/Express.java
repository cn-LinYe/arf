package com.arf.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.cache.redis.RedisService;
import com.arf.plugin.dto.ExpressInfo;
import com.arf.redis.CacheNameDefinition;


public class Express {
	public static final String APPCODE  ="APPCODE ceb7075f7a9b4026bd4de21e3f1df289";
	public static final String HOST  ="http://ali-deliver.showapi.com";
	public static final String PATH  ="/showapi_expInfo";
	public static final String METHOD  ="GET";
	public static final Logger log=LoggerFactory.getLogger(Express.class);	
	
	/**统一暴露接口
	 * @param com 
	 * @param nu 快递编号
	 * @return
	 */
	public static ExpressInfo getLogisticsInfo(String com,String nu,RedisService redisService){
		if(StringUtils.isBlank(nu)){//快递单号为空
			return null;
		}
		//获取缓存中快递信息
		String expressInfo=redisService.hGet(String.format(CacheNameDefinition.Express_Delivery_Info, nu), nu);
		if(StringUtils.isNotBlank(expressInfo)){
			ExpressInfo dto=JSON.parseObject(expressInfo, ExpressInfo.class);
			return dto;
		}
	   return getOnlineExpressInfo(com, nu,redisService);
	}
	
	private static ExpressInfo getOnlineExpressInfo(String com,String nu,RedisService redisService){
		 String host = HOST;
		    String path = PATH;
		    String method = METHOD;
		    Map<String, String> headers = new HashMap<String, String>();
		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105	    
		    headers.put("Authorization", APPCODE);
		    Map<String, String> querys = new HashMap<String, String>();
		    //auto 填写auto则自动识别。 对应"快递公司查询"接口中的simpleName快递公司简称
		    querys.put("com", com);
//		    883420070249072469测试订单号
		    querys.put("nu", nu);	
		    ExpressInfo info=null;
		    ExecutorService pool = Executors.newFixedThreadPool(1); 
		       try {
			       // 推送给商家信息
		    	   Callable<String> myCallable=new MyCallable(host, path, method, headers, querys);
			       Future<String> fback = pool.submit(myCallable); 
			       //从Future对象上获取任务的返回值 
			       String expressInfo=fback.get();
			       if(expressInfo!=null){
			    	   return getExpressInfo(nu,expressInfo,redisService);
			       }
				} catch (Exception e) {
					log.error("快递统一接口异常,"+host+"异常信息如下:",e);
				}finally{
				//关闭线程池 
		           pool.shutdown(); 
				}
			return info; 
	}
	
	private static ExpressInfo getExpressInfo(String nu,String expressInfo,RedisService redisService){
		ExpressInfo info=new ExpressInfo();
		if(expressInfo!=null){
			JSONObject obj = JSONObject.parseObject(expressInfo);
			String showapi_res_code=obj.getString("showapi_res_code");
			if(showapi_res_code!=null&&"0".equals(showapi_res_code)){				
				info.setResCode(0);
			}
			info.setResError(obj.getString("showapi_res_error"));
			JSONObject showapiResBody=obj.getJSONObject("showapi_res_body");
			if(showapiResBody!=null){
				Boolean flag =showapiResBody.getBoolean("flag");
				Integer retCode=showapiResBody.getInteger("ret_code");
				if (flag!=null&&retCode!=null&&flag&&0==retCode) {
					info.setMailNo(showapiResBody.getString("mailNo"));
					info.setTel(showapiResBody.getString("tel"));
					info.setUpdateStr(showapiResBody.getString("updateStr"));
					info.setExpTextName(showapiResBody.getString("expTextName"));
					info.setExpSpellName(showapiResBody.getString("expSpellName"));
					Integer status= showapiResBody.getInteger("status");
					if(status!=null){						
						info.setStatus(ExpressInfo.Status.get(status));
					}
					info.setData(showapiResBody.getJSONArray("data"));
				}else{
					info.setResCode(-1);
				}
			}
			HashMap<byte[], byte[]> expressInMap=new HashMap<byte[], byte[]>();
			String expressIninfo =JSONObject.toJSONString(info);
			expressInMap.put(nu.getBytes(), expressIninfo.getBytes());
			redisService.hMSet(String.format(CacheNameDefinition.Express_Delivery_Info, nu), expressInMap);
			redisService.setExpiration(String.format(CacheNameDefinition.Express_Delivery_Info, nu), CacheNameDefinition.Express_Delivery_Info_Expiration);
		}
		return info;		
	}
}
