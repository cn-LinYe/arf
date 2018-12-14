package com.arf.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.arf.plugin.dto.BankAttributionInfo;

public class BankAttribution {
	public static final String APPCODE  ="APPCODE e6002a634c7244feb8c7d371adfde0b4";
	public static final String HOST  ="http://jisuyhkgsd.market.alicloudapi.com";
	public static final String PATH  ="/bankcard/query";
	public static final String METHOD  ="GET";
	public static final String ALIYUNHOST = "https://ccdcapi.alipay.com";
	public static final String ALIYUNPATH = "/validateAndCacheCardInfo.json";
	public static final Logger log=LoggerFactory.getLogger(BankAttribution.class);
	
	public static BankAttributionInfo getBankAttributionInfo(String bankcard){
		String host = HOST;
		String path = PATH;
		String method = METHOD;
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105	    
		headers.put("Authorization", APPCODE);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("bankcard", bankcard);
		BankAttributionInfo info=null;
		ExecutorService pool = Executors.newFixedThreadPool(2);
		Future<String> fback=null;
		   try {
			   // 推送给商家信息
			   Callable<String> myCallable=new MyCallable(host, path, method, headers, querys);
			   fback = pool.submit(myCallable);
			   //从Future对象上获取任务的返回值 
			   String bankAttributionInfo=fback.get(5000,TimeUnit.MILLISECONDS);
			   if(bankAttributionInfo!=null){
				   info=getBankAttributionInfo(bankcard,bankAttributionInfo);
				   if(info!=null){
					   Map<String, String> aliyunquerys = new HashMap<String, String>();
					   aliyunquerys.put("_input_charset", "utf-8");
					   aliyunquerys.put("cardNo", bankcard);
					   aliyunquerys.put("cardBinCheck", "true");
					   myCallable=new MyCallable(ALIYUNHOST, ALIYUNPATH, method, null, aliyunquerys);
					   fback = pool.submit(myCallable);
					   //从Future对象上获取任务的返回值 
					   String json=fback.get(3000,TimeUnit.MILLISECONDS); 
					   JSONObject jsonObject=null;
					   if(StringUtils.isNotBlank(json))
					   jsonObject=JSONObject.parseObject(json);
						if(jsonObject!=null){
							String bank=jsonObject.getString("bank");
							Boolean validated=jsonObject.getBoolean("validated");
							if(validated!=null &&validated){
								info.setBankShortName(bank);
							}			
						}
				   }				   
		       }
		    } catch (Exception e) {
		    	if(fback!=null){
		    		fback.cancel(true);
		    	}
				log.error("银行卡归属地统一接口异常,"+host+"异常信息如下:",e);
			}finally{			
		       pool.shutdown(); //关闭线程池 
			}
		return info; 
	}

	private static BankAttributionInfo getBankAttributionInfo(String bankcard, String bankAttributionInfo) {
		BankAttributionInfo  info =new BankAttributionInfo();
		if(StringUtils.isNotBlank(bankAttributionInfo)){
			JSONObject jsonObject=JSONObject.parseObject(bankAttributionInfo);
			Integer status= jsonObject.getInteger("status");//0正常有数据
			if(status==0){
				JSONObject result=jsonObject.getJSONObject("result");//获取result节点数据
				info.setBankBranch(result.getString("province").concat("-").concat(result.getString("city")));	
				info.setBankName(result.getString("bank"));
				info.setBankNumber(result.getString("bankcard"));
				info.setCity(result.getString("city"));
				info.setIscorrect(result.getInteger("iscorrect"));
				info.setLen(result.getInteger("len"));
				info.setLogo(result.getString("logo"));
				info.setName(result.getString("name"));
				info.setProvince(result.getString("province"));
				info.setStatus(status);
				info.setTel(result.getString("tel"));
				info.setWebsite(result.getString("website"));
			}else{
				String msg=jsonObject.getString("msg");//获取提示语
				if(StringUtils.isNotBlank(msg)){
					info.setMsg(msg);
				}else{
					info.setMsg("获取银行归属地接口暂时不可用");
				}				
				info.setStatus(-1);
			}
		}else{
			info.setMsg("获取银行归属地接口暂时不可用");
			info.setStatus(-1);
		}		  
		return info;
	}
	

	public static void main(String[] args) {
		System.out.println(JSONObject.toJSON(getBankAttributionInfo("6225758314840793")));
	}
}
