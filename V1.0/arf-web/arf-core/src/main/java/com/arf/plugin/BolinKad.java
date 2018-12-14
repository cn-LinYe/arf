package com.arf.plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.arf.core.util.DesUtil;
import com.arf.core.util.MD5Util;
import com.arf.plugin.vo.BolinKadVo;

import net.sf.json.JSONObject;

public class BolinKad {
	
	public static final String SBOLINHOST = "http://a.bolink.club/bolinkad/showad";
	public static final String INSERTADUSERPARKHOST = "http://a.bolink.club/bolinkad/insertAdUserParkTb";
	public static final String UKEY = "243B69878E4075D6";
	public static final String UNIONID = "832858B8";
	public static final String CODING = "UTF-8";
	public static final String HREAD = "Content-Type";
	public static final String HREADTYPE = "application/json";
	public static final String REQUESTMETHOD = "POST";

	public static String insertPark(BolinKadVo vo) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		Map<String,Object> oldMap = new HashMap<String,Object>();
		parameterMap.put("unionId", UNIONID);
		oldMap.put("userId", "anerfa");
		oldMap.put("parkId", vo.getParkId());
		oldMap.put("parkName", vo.getParkName());
		oldMap.put("parkType", vo.getParkType()==null?"1":vo.getParkType());
		oldMap.put("address", vo.getAddress());
		oldMap.put("cityCode", vo.getCityCode()==null?"0":vo.getCityCode());
		oldMap.put("lon", vo.getLon());
		oldMap.put("lat", vo.getLat());
		
		String desStr = "";
		try {
			desStr = DesUtil.encrypt(JSONArray.toJSONString(oldMap), UKEY);
			if(StringUtils.isBlank(desStr)) {
				return null;
			}
			parameterMap.put("data", desStr);
			return sendHttpPost(INSERTADUSERPARKHOST, JSONArray.toJSONString(parameterMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String showad(BolinKadVo vo) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		Map<String,Object> oldMap = new HashMap<String,Object>();
		parameterMap.put("unionId", UNIONID);
		Integer adPosId = vo.getAdPosId()!=null?vo.getAdPosId():1;
		oldMap.put("cityCode", vo.getCityCode()==null?"0":vo.getCityCode());
		oldMap.put("parkType", vo.getParkType()==null?"1":vo.getParkType());
		oldMap.put("carType", vo.getCarType()==null?"1":vo.getCarType());
		oldMap.put("parkId", vo.getParkId());
		oldMap.put("adPosId", adPosId);
		oldMap.put("plateNumber", StringUtils.isBlank(vo.getPlateNumber())?"0":vo.getPlateNumber());
		oldMap.put("clientId", StringUtils.isBlank(vo.getClientId())?"0":MD5Util.getMD5(vo.getClientId()));
		oldMap.put("orderId", StringUtils.isBlank(vo.getOrderNo())?"0":vo.getOrderNo());
		oldMap.put("addPv", vo.isAddPv());
		
		String desStr = "";
		try {
			desStr = DesUtil.encrypt(JSONArray.toJSONString(oldMap), UKEY);
			if(StringUtils.isBlank(desStr)) {
				return null;
			}
			parameterMap.put("data", desStr);
			String json = JSONArray.toJSONString(parameterMap);
			String result = sendHttpPost(SBOLINHOST, json).replace(":null", ":0");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String sendHttpPost(String urls, String jsonData) throws IOException {
		String returninfo = "";
		URL httpUrl = new URL(urls); 
        HttpURLConnection huc = (HttpURLConnection) httpUrl.openConnection(); 
        huc.setRequestMethod(REQUESTMETHOD);
        huc.setDoInput(true);  
        huc.setDoOutput(true);
        huc.setRequestProperty(HREAD,  HREADTYPE);  
        //链接地址  
        huc.connect(); 
        OutputStreamWriter writer = new OutputStreamWriter(huc.getOutputStream());  
        //发送参数  
        writer.write(jsonData); 
      //清理当前编辑器的左右缓冲区，并使缓冲区数据写入基础流  
        writer.flush(); 
        BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(),CODING)) ;
        String line;
        Map js;
        while ((line = br.readLine()) != null) {
            returninfo = line;
            js=(Map)JSONObject.fromObject(returninfo); 
            System.out.println(js.toString());
        }        
        huc.connect();   
        br.close();
        return returninfo;
	}
	
}
