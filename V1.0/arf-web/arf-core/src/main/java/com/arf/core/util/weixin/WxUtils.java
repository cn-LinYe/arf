/**
 * @(#)WxUtils.java
 * 
 * Copyright arf.
 *
 * @Version: 1.0
 * @JDK: jdk jdk1.6.0_10
 * @Module: arf-core
 */ 
 /*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2016-3-3       arf      Created
 **********************************************
 */

package com.arf.core.util.weixin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.entity.UnionIDModel;
import com.arf.core.oldws.PushUntils;
import com.arf.core.service.WxAccessTokenService;
import com.arf.core.util.HttpUtil;


/**
 * 微信接口流程
 *
 * @author arf
 * @since 2016-3-3
 */
public class WxUtils {
	
	public static final String CLASSIFIER = "arfaxdtoken";
	
    private final static WxUtils utils=new WxUtils();
    private WxUtils(){
    	try {
			Properties properties = new Properties();
			URL res = PushUntils.class.getClassLoader().getResource("dbconfig.properties");
			properties.load(new FileInputStream(new File(res.getPath())));
			this.weixinServer = properties.getProperty("arf.weixin.server");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static WxUtils getInstance(){
        return utils;
    }
    /**获取access_token的url*/
    public final static String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    /**获取用户openid的url*/
    public final static String OPEN_ID_URL="https://api.weixin.qq.com/cgi-bin/user/get?";
    /**批量获取用户unionid的url*/
    public final static String UNION_ID_URL="https://api.weixin.qq.com/cgi-bin/user/info/batchget?";
    
    /** 获取用户基本信息（包括UnionID机制） */
    public final static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?";
    
    /**参数名:保存next_openid*/
    public final static String PARAM_NAME="WEIXINOPENID";
    
    private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    
    /**提前时间*/
    private final long times=600L;
    /**access_token过期时间*/
    private Long expires_in=0L;
    /**access_token创建时间*/
    private Long create_time=0L;
    /**access_token*/
    private String access_token="";
    /** 企业微信服务器地址 */
    private String weixinServer = "";
    
    private final static Logger log = LoggerFactory.getLogger(WxUtils.class);
    
    /***
     * 获取access_token
     * @return
     */
    public synchronized String getAccessToken(WxAccessTokenService accessTokenService){
//    	WxAccessToken tokenBean = accessTokenService.findByClassifier(CLASSIFIER);
//    	String token="";
//    	if(tokenBean == null || 
//    			(System.currentTimeMillis() - tokenBean.getCreateTime() >= tokenBean.getExpireSeconds() * 1000)){
//    		 try{
//                 String result=doGet(ACCESS_TOKEN_URL+"&appid="+WxConstants.WXAPPID+"&secret="+WxConstants.APPSECRET);
//                 result = result+"";
//                 if(result.indexOf("errcode") <= -1){
//                 	JSONObject jsonObject = JSONObject.parseObject(result);
//                     //true {"access_token":"ACCESS_TOKEN","expires_in":7200}
//                     //false {"errcode":40013,"errmsg":"invalid appid"}
//                     token=jsonObject.getString("access_token");
//                     
//                 	access_token=token;
//                     create_time=System.currentTimeMillis();
//                     expires_in=jsonObject.getLong("expires_in")*1000;
//                     
//                     WxAccessToken entity = accessTokenService.findByClassifier(CLASSIFIER);
//                     if(entity != null){
//                     	entity.setAccessToken(access_token);
//                     	entity.setCreateTime(create_time);
//                     	entity.setExpireSeconds(expires_in.intValue());
//                     	accessTokenService.update(entity);
//                     }else{
//                     	entity = new WxAccessToken(access_token, expires_in.intValue(), CLASSIFIER, create_time);
//                     	accessTokenService.save(entity);
//                     }
//                 }else{
//                 	JSONObject jsonObject = JSONObject.parseObject(result);
//                 	String errorcode = jsonObject.getString("errorcode");
//                 	System.out.println(errorcode+"  "+jsonObject.getString("errmsg"));
//                 }
//             }catch(Exception e){
//                 e.printStackTrace();
//                 expires_in=0L;
//                 create_time=0L;
//             }
//    		 
//    	}else{
//    		token = tokenBean.getAccessToken();
//    	}
    	String token = getTokenByWeixinServer(null);
    	if(StringUtils.isNotBlank(token)){
    		access_token = token;
    	}
    	return token;
    }
    
    
    private String getTokenByWeixinServer(Integer retry){
    	String token = "";
    	try{
    		String resp = HttpUtil.sendHttpsGET(weixinServer + "/api/getAccessToken");
        	if(StringUtils.isNotBlank(resp)){
        		JSONObject obj = JSON.parseObject(resp);
        		token = obj.getString("accessToken");
        	}
    	}catch (Exception e) {
    		log.error("获取微信服务器token失败",e);
		}
    	if(retry == null){
			retry = 0;
		}
    	if(StringUtils.isBlank(token) && retry < 3){
    		retry = retry + 1;
    		token = getTokenByWeixinServer(retry);
    	}
    	return token;
    }
    /**
     * 强制从微信服务器获取token
     * @param accessTokenService
     * @return
     */
    public synchronized String getAccessTokenForce(WxAccessTokenService accessTokenService){
//    	String token="";
//    	try{
//            String result=doGet(ACCESS_TOKEN_URL+"&appid="+WxConstants.WXAPPID+"&secret="+WxConstants.APPSECRET);
//            result = result+"";
//            if(result.indexOf("errcode") <= -1){
//            	JSONObject jsonObject = JSONObject.parseObject(result);
//                //true {"access_token":"ACCESS_TOKEN","expires_in":7200}
//                //false {"errcode":40013,"errmsg":"invalid appid"}
//                token=jsonObject.getString("access_token");
//                
//            	access_token=token;
//                create_time=System.currentTimeMillis();
//                expires_in=jsonObject.getLong("expires_in")*1000;
//                
//                WxAccessToken entity = accessTokenService.findByClassifier(CLASSIFIER);
//                if(entity != null){
//                	entity.setAccessToken(access_token);
//                	entity.setCreateTime(create_time);
//                	entity.setExpireSeconds(expires_in.intValue());
//                	accessTokenService.update(entity);
//                }else{
//                	entity = new WxAccessToken(access_token, expires_in.intValue(), CLASSIFIER, create_time);
//                	accessTokenService.save(entity);
//                }
//            }else{
//            	JSONObject jsonObject = JSONObject.parseObject(result);
//            	String errorcode = jsonObject.getString("errorcode");
//            	System.out.println(errorcode+"  "+jsonObject.getString("errmsg"));
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            expires_in=0L;
//            create_time=0L;
//        }
//    	
//    	return token;
    	return getTokenByWeixinServer(null);
    }
    
    
    public Openids getNowUserOpenid(String nextopenid){
        Openids openids=null;
        String url=OPEN_ID_URL+"access_token="+access_token;
        if(StringUtils.isNotBlank(nextopenid)){
            url+="&next_openid="+nextopenid;
        }
        try{
            String result=doGet(url);
            String errorcode="";
            JSONObject jsonObject = JSONObject.parseObject(result);
            System.out.println(result);
            //true {"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
            //false {"errcode":40013,"errmsg":"invalid appid"}
            try{
                errorcode=jsonObject.getString("errcode");
            }catch(Exception e){
            	e.printStackTrace();
            }
            if(StringUtils.isBlank(errorcode)){//如果没错误
                openids=new Openids();
                openids.setTotal(jsonObject.getLong("total"));
                openids.setCount(jsonObject.getLong("count"));
                openids.setNextopenid(jsonObject.getString("next_openid"));
                JSONObject json=JSONObject.parseObject(jsonObject.getString("data"));//获取data
                String ids=json.getString("openid");//获取openid列表
                String[] oids= JSON.parseArray(ids).toArray(new String[]{});
                openids.setOpenid(oids);
            }else{
                System.out.println(errorcode+"  "+jsonObject.getString("errmsg"));
            }
        }catch(Exception e){
        }
        return openids;
    }
    /**
     * 根据openid获取unionid
     * @param list
     */
    public void getUnionID(List<UnionIDModel> list){
        if(list==null||list.isEmpty()){
            return;
        }
        /**
         * 微信每次最多只能获取100个unionID
         */
        String url=UNION_ID_URL+"access_token="+access_token;
        Map<String,Object> maps=new HashMap<String,Object>();
        List<Map<String,String>> parlist=new ArrayList<Map<String,String>>();
        for(int i=0;i<list.size();i++){
            Map<String,String> m=new HashMap<String,String>();
            m.put("openid",list.get(i).getOpenid());
            m.put("lang","zh-CN");
            parlist.add(m);
            if(i>0&&i%99==0||i==list.size()-1){
                maps.put("user_list", parlist);
                String result=doPOSTJson(url,JSON.toJSON(maps).toString());
                System.out.println(result);
                JSONObject jobj1=JSON.parseObject(result);
                JSONArray jarry=JSON.parseArray(jobj1.getString("user_info_list"));
                for(int j=0;j<jarry.size();j++){
                    //获取openid unionid  subscribe_time
                    JSONObject jobj=jarry.getJSONObject(j);
                    try{
                        String openid=jobj.getString("openid");
                        String unionid=jobj.getString("unionid");
                        String nickname="";
//                        Integer sex=jobj.getInt("sex");
                        
                        String sex1="";
                        try{
                            sex1=jobj.getString("sex");
                        }catch(Exception e){
                        	e.printStackTrace();
                        }
                        Integer sex=0;
                        if(StringUtils.isBlank(sex1)){
                            try{
                            sex=Integer.parseInt(sex1);
                            }catch(Exception e){}
                        }
                        String stime1="";
                        try{
                            stime1=jobj.getString("subscribe_time");
                        }catch(Exception e){
                        	e.printStackTrace();
                        }
                        Long stime=0L;
                        if(StringUtils.isBlank(stime1)){
                            try{
                                stime=Long.parseLong(stime1);
                            }catch(Exception e){}
                        }
                        
                        String city="";
                        try{
                            city=jobj.getString("city");
                        }catch(Exception e){
                        	e.printStackTrace();
                        }
                        String province="";
                        try{
                            province=jobj.getString("province");
                        }catch(Exception e){
                        	e.printStackTrace();
                        }
                        String country="";
                        try{
                            country=jobj.getString("country");
                        }catch(Exception e){e.printStackTrace();}
                        for(int k=i;k>=0;k--){
                            if(list.get(k).getOpenid().equals(openid)){
                                list.get(k).setUnionID(unionid);
                                list.get(k).setSubscribe_time(stime==null?0L:stime.longValue());
                                list.get(k).setNickname(nickname);
                                list.get(k).setSex(sex==null?0:sex.intValue());
                                list.get(k).setCity(city);
                                list.get(k).setCountry(country);
                                list.get(k).setProvince(province);
                                break;
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                }
                parlist=new ArrayList<Map<String,String>>();
                maps=new HashMap<String,Object>();
//                break;
            }
        }
        
        
    }
    
    public static String doGet(String url){
        HttpURLConnection conn;
        String result="";
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder builder = new StringBuilder();
            String temp = null;
            while(null != (temp = reader.readLine())){
                builder.append(temp);
                temp = null;
            }
            reader.close();
            reader = null;
            result = builder.toString();
            builder = null;
            temp = null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
    
    public String doPOSTJson(String urls,String parm){
//        HttpClient httpclient = new DefaultHttpClient();
        String result="";
        try {
//            String encoderJson = URLEncoder.encode(parm,"UTF-8");
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
//            StringEntity entity = new StringEntity(encoderJson);
//            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));    
//            entity.setContentType(CONTENT_TYPE_TEXT_JSON);    
//            httpPost.setEntity(entity);
//            HttpResponse respon =httpclient.execute(httpPost);
//            if (respon.getStatusLine().getStatusCode() == 200) {
//                HttpEntity resEntity = respon.getEntity();
//                result = EntityUtils.toString(resEntity,"UTF-8");
//            }
//            httpclient.getConnectionManager().shutdown();
            
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(parm.getBytes("UTF-8"));//这样可以处理中文乱码问题
            out.flush();
            out.close();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            result=sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
