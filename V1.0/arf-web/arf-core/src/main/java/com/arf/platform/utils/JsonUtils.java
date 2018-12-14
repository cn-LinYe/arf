package com.arf.platform.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	
	
	public static  String  json(Map<String, Object> req,String url){
		String param = new Gson().toJson(req);
		String result = null; 
		System.out.println("请求串：" + param);
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();		
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json");
			conn.setRequestProperty("charset", "UTF-8");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			writer.write(param);
			writer.flush();
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			 result = reader.readLine();
			reader.close();
			System.out.println("应答串：" + result);
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * json 转  map
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	
	/**
	 * JSONObject ——> Map
	 * @param jsonObject
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map parseToMap(JSONObject jsonObject, Map map) {  
        for (Iterator iterator = jsonObject.entrySet().iterator(); iterator.hasNext();) {  
            String entryStr = String.valueOf(iterator.next());  
            String key = entryStr.substring(0, entryStr.indexOf("="));  
            String value = entryStr.substring(entryStr.indexOf("=") + 1,entryStr.length());  
            if (jsonObject.get(key).getClass().equals(JSONObject.class)) {  
                HashMap _map = new HashMap();  
                map.put(key, _map);  
                parseToMap(jsonObject.getJSONObject(key), ((Map) (_map)));  
            } else if (jsonObject.get(key).getClass().equals(JSONArray.class)) {  
                ArrayList list = new ArrayList();  
                map.put(key, list);  
                parseToArray(jsonObject.getJSONArray(key), list);  
            } else {  
                map.put(key, jsonObject.get(key));  
            }  
        }  
  
        return map;  
    } 

	/**
	 * JSONArray ——> List
	 * @param jsonArray
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void parseToArray(JSONArray jsonArray, List list) {  
		for (int i = 0; i < jsonArray.size(); i++){
			if (jsonArray.get(i).getClass().equals(JSONArray.class)) {  
			    ArrayList _list = new ArrayList();  
			    list.add(_list);  
			    parseToArray(jsonArray.getJSONArray(i), _list);  
			} else if (jsonArray.get(i).getClass().equals(JSONObject.class)) {  
			    HashMap _map = new HashMap();  
			    list.add(_map);  
			    parseToMap(jsonArray.getJSONObject(i), _map);  
			} else {  
			    list.add(jsonArray.get(i));  
			}
		}
	}
	
	public static String objToJSONStr(Object ob){
		JSONObject jsonStr = JSONObject.fromObject(ob);
		return jsonStr.toString();
	}
	
//	public static void main(String[] args) {
//		DownCreateOrderVo vo = new DownCreateOrderVo();
//		System.out.println(objToJSONStr(vo));
//	}
}
