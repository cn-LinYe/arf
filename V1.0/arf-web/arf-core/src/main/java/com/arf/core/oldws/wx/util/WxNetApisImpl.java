package com.arf.core.oldws.wx.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.arf.core.entity.UnionIDModel;
import com.arf.core.oldws.wx.entity.WxConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WxNetApisImpl implements IWxNetApis {

	private long time;
	private int flag = 1;// 标记第几次获取openid
	private List<String> openIds = new ArrayList<String>();// 存储openid列表
	private String result;// 请求结果
	private int num = 0;// 标记取几次openid
	private String next_openid;// 如果需要再次获取openid则传入该值
	// Http请求参数
	private Map<String, String> params;
	private List<NameValuePair> list;
	// 存储关注了公众号的所有人信息(openid、unionid、nickname、sex)
	private List<UnionIDModel> models = new ArrayList<UnionIDModel>();

	@Override
	public List<String> getWxUsers() {
		
		if (flag == 1) {
			params = new HashMap<String, String>();
			params.put("access_token", AceessTokenCache.getAccessToken());
			List<NameValuePair> list = MyUtils.getInstance().getReqParams(params);
			result = MyUtils.getInstance().doPost(WxConstants.URL_OPENID, list);
		} else {
			params = new HashMap<String, String>();
			params.put("access_token", AceessTokenCache.getAccessToken());
			params.put("next_openid", next_openid);
			List<NameValuePair> list = MyUtils.getInstance().getReqParams(params);
			result = MyUtils.getInstance().doPost(WxConstants.URL_OPENID, list);
		}
		Map map2 = GsonTools.getOpenId(result);
		if(!map2.isEmpty()){
			List<String> tempOpenIds = (List<String>) map2.get("list");
			int count = Integer.parseInt((String) map2.get("count"));
			int total = Integer.parseInt((String) map2.get("total"));
			next_openid = (String) map2.get("next_openid");

			System.out.println("count: " + count + " total: " + total
					+ " next_openid: " + next_openid);
			for (String s : tempOpenIds) {
				System.out.println("s: " + s);
				openIds.add(s);
			}

			if (flag == 1) {// 第一次进入走这个程序
				if (total > count) {// 如果总数大于当前数量，则要再取一次数据
					if (total % count == 0) {
						num = total / count - 1;
					} else {
						num = total / count;
					}
					System.out.println("---》Num: " + num);
					flag++;
					getWxUsers();
				}
			} else {// 否则循环够次数后退出
				if (flag <= num) {
					flag++;
					getWxUsers();
				}
			}
			return openIds;
		}else{
		}
		return null;
	}

	@Override
	public List<UnionIDModel> getWxUnioIDInfo(List<String> openids) {
		String URL_UNIONID_BATCH = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+AceessTokenCache.getAccessToken();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<openids.size();i++){
			JSONObject jsonObject2=new JSONObject();
			jsonObject2.put("openid", openids.get(i));
			jsonObject2.put("lang", "zh-CN");
			jsonArray.add(jsonObject2);
		}
		jsonObject.put("user_list",jsonArray);
		result = MyUtils.getInstance().doJsonPost(URL_UNIONID_BATCH,jsonObject.toString());
		List<UnionIDModel> tempModels=GsonTools.getInstance().getBatchUser(result);
		for(UnionIDModel model:tempModels){
			models.add(model);
			System.out.println(model);
		}
		return models;
	}
}
