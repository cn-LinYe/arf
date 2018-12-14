/**
 * @(#)OldwsInterceptor.java
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
 *  1     2015-12-26       arf      Created
 **********************************************
 */

package com.arf.core.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.RequestFacade;
//import org.apache.catalina.connector.Request;
//import org.apache.catalina.connector.RequestFacade;
//import org.apache.catalina.util.ParameterMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor - 旧接口拦截器,将json数据转换成paramter数据
 * 
 * @author arf
 * @version 4.0
 */
public class OldwsInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//      boolean first = true;
//      Enumeration<String> emParams = request.getHeaderNames();
//      StringBuffer strbuf = new StringBuffer("");
//      do{
//        if (!emParams.hasMoreElements()){
//          break;
//        }
//        String sParam = (String) emParams.nextElement();
//        System.out.println("parm:"+sParam+" value:"+request.getHeader(sParam));
////          String[] sValues = request.getParameterValues(sParam);
////          String sValue = "";
////          for (int i = 0; i < sValues.length; i++){
////            sValue = sValues[i];
////            if (sValue != null && sValue.trim().length() != 0&& first == true) {
////              first = false;
////              strbuf.append(sParam).append("=").append(
////                  URLEncoder.encode(sValue, "utf-8"));
////            }
////            else if (sValue != null && sValue.trim().length() != 0
////                     && first == false){
////              strbuf.append("&").append(sParam).append("=").append(
////                  URLEncoder.encode(sValue, "utf-8"));
////            }
////          }
//      }
//      while (true);
//      System.out.println("pars:"+strbuf.toString());

       
        StringBuilder sb = new StringBuilder();//json数据  
        try{
        	 BufferedReader reader = request.getReader();
             char[]buff = new char[1024];  
             int len;  
             while((len = reader.read(buff)) != -1) {  
                sb.append(buff,0, len);  
            }  
        }catch (IOException e) {  
            e.printStackTrace();  
        }
        if("".equals(sb.toString().trim())){
        	 return true;
        }
      //  System.out.println("接收到的参数值:"+sb.toString());
        try{
            HashMap<String,ArrayList<String>> map=new HashMap<String,ArrayList<String>>();
//            ParameterMap<String,String> 
            map=transMap("",formatString(sb));
     //       System.out.println("解析出的参数值:"+map.toString());
            Field field=null;
            field = RequestFacade.class.getDeclaredField("request");
            field.setAccessible(true);
            Object classic=field.get(request);//获取Request对象
            Field tomcatrequest_field = classic.getClass().getDeclaredField("coyoteRequest");//获取request中tomcat中的request对像字段
            tomcatrequest_field.setAccessible(true);
            Object tomcatrequest=tomcatrequest_field.get(classic);//获取request中tomcat中的request对像值
            Field tomcatpar_field = tomcatrequest.getClass().getDeclaredField("parameters");
            tomcatpar_field.setAccessible(true);
            Object tomcatparamter=tomcatpar_field.get(tomcatrequest);//获取request中paramter对像
            Field tomcatrequestpar_field = tomcatparamter.getClass().getDeclaredField("paramHashValues");
            tomcatrequestpar_field.setAccessible(true);
            tomcatrequestpar_field.set(tomcatparamter,map);
//            Field classic_field=classic.getClass().getDeclaredField("parameterMap");
//            System.out.println(classic_field.toString());
//            classic_field.setAccessible(true);
//            classic_field.set(classic,map);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        //parameterMap
        
        return true;
    }
    /**
     * 格式化json字符串
     * @author arf add by 2015.12.29
     * @param sb
     * @return
     */
    public StringBuilder formatString(StringBuilder sb){
        StringBuilder nsb=new StringBuilder();
        char c=' ';
        long yhcount=0;
        while(sb.length()>0){
            c=sb.charAt(0);
            if(c=='\"'){//判断是否为有效的双引号
                if(yhcount%2==0){//起始的单引号
                    yhcount++;
                }else{//判断它是字符串还是有效双引号
                    if(getXgcount(nsb)%2==0){//如果末尾的反斜杠条数为双数，表示为有效双引号
                        yhcount++;
                    }
                }
                nsb.append(c);
            }else if(c==' '){
                if(yhcount%2!=0){
                    nsb.append(c);
                }
            }else{
                nsb.append(c);
            }
            sb.deleteCharAt(0);
        }
        return nsb;
    }
    /**
     * 将字符串转换成map对象
     * @author arf add by 2015.12.29
     * @param pre 变量名前缀
     * @param sb
     * @return
     */
    private HashMap<String,ArrayList<String>> transMap(String pre,StringBuilder sb){
        HashMap<String,ArrayList<String>> map=new HashMap<String,ArrayList<String>>();
        if(sb.length()==0){
            return map;
        }
        //首先去除首尾的大括号
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        /**
         * 如果为大括号，获取下一个有效}的索引  则以key值做为pre，递归调用处理对象
         * 如果为[，找出下一个有效]的索引，并将对象做为数组处理
         * 如果为,逗号，将未被双引号
         */
        long yhcount=0;
        String str="";//临时字符串
        char c=' ';
        String key="";
        ArrayList<String> list=new ArrayList<String>();
        //
        while(sb.length()>0){
            c=sb.charAt(0);
            if(c=='\"'){//判断是否为有效的双引号
                if(yhcount%2==0){//起始的引号
                    str="";
                    yhcount++;
                }else{//判断它是字符串还是有效双引号
                    if(getXgcount(str)%2==0){//如果末尾的反斜杠条数为双数，表示为有效双引号
                        yhcount++;
                    }else{
                        str+=c;
                    }
                }
            }else if(c==':'){
                if(yhcount%2==0){//如果为有效冒号，略过
                    key=str;
//                    list.add(pre+str);
                    str="";
                }else{
                    str+=c;
                }
            }else if(c=='{'){
                if(yhcount%2==0){//如果为有效大括号
                    int i1=getNextIndex('}',sb);
                    if(i1!=-1){
                        map.putAll(transMap(pre+key+".",new StringBuilder(sb.substring(0, i1+1))));
                        key="";
                    }
                    sb.delete(0, i1);
                }else{
                    str+=c;
                }
            }else if(c=='['){
                if(yhcount%2==0){//如果为有效中括号
                    int i1=getNextIndex(']',sb);
                    if(i1!=-1){
                        String s1=sb.substring(1, i1);
                        map.put(pre+key, getArrayByStr(s1));
                        key="";
                    }
                    sb.delete(0, i1);
                }else{
                    str+=c;
                }
            }else if(c==','){//如果
                if(yhcount%2==0){
                    if(!str.endsWith("\"")){
                        list.add(str);
                        map.put(pre+key, list);
                        list=new ArrayList<String>();
                        key="";
                        str="";
                    }
                }else{
                    str+=c;
                }
            }else{
                str+=c;
            }
            sb.deleteCharAt(0);
        }
        if(!"".equals(key)){
            list=new ArrayList<String>();
            list.add(str);
            map.put(pre+key,list);
        }
//        System.out.println(map.toString());
//        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i).toString());
//        }
        return map;
    }
    
    /**
     * 获取字符串末尾连续的反斜杠条数
     * @return
     */
    public int getXgcount(String str){
        int c=0;
        for(int i=str.length()-1;i>=0;i--){
            if(str.charAt(i)=='\\'){
                c++;
            }else{
                break;
            }
        }
        return c;
    }
    /**
     * 获取反斜杠数量
     * @param str
     * @return
     */
    public int getXgcount(StringBuilder str){
        int c=0;
        for(int i=str.length()-1;i>=0;i--){
            if(str.charAt(i)=='\\'){
                c++;
            }else{
                break;
            }
        }
        return c;
    }
    /**
     * 获取下一个索引
     * @param c1
     * @param sb
     * @return
     */
    public int getNextIndex(char c1,StringBuilder sb){
        int yhcount=0;
        char c=' ';
        for(int i=1;i<sb.length();i++){
            c=sb.charAt(i);
            if(yhcount%2==0){
                if(c==c1){
                    return i;
                }
            }
        }
        return -1;
    }
    /**
     * 生成字符串数组
     * @param str
     * @return
     */
    public ArrayList<String> getArrayByStr(String str){
        ArrayList<String> list=new ArrayList<String>();
        if("".equals(str)){
           return list; 
        }
        int yhcount=0;
        char c=' ';
        String s="";
        for(int i=0;i<str.length();i++){
            c=str.charAt(i);
            if(c=='\"'&&getXgcount(str)%2==0){
                yhcount++;
            }else if(c==','){
                if(yhcount%2==0){
                    list.add(s);
                    s="";
                }
            }else{
                s+=c;
            }
        }
        list.add(s);
        return list;
    }
}
