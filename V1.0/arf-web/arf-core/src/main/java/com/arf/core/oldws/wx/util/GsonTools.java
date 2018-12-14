package com.arf.core.oldws.wx.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.arf.core.entity.UnionIDModel;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import net.sf.json.JSONObject;

public class GsonTools {

	private static GsonTools util = null;

	public synchronized static GsonTools getInstance() {
		if (null == util) {
			synchronized (GsonTools.class) {
				util = new GsonTools();
			}
		}
		return util;
	}

	private GsonTools() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 服务号、企业号通用 获取access_token
	 */
	public static String getAccessToken(String str) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(str);
			if (null == jsonObject.opt("errcode")) {
				String access_token = jsonObject.getString("access_token");
				return access_token;
			}
		} catch (JSONException e) {
			System.out.println("--->Exception: " + e.toString());
		}
		return null;
	}

	/**
	 * 服务号: 获取openid
	 */
	public static Map getOpenId(String str) {
		List<String> datas = new ArrayList<String>();
		Map map = new HashMap();
		String count = "";
		String total = "";
		String next_openid = "";
		try {
			JSONObject jsonObject = JSONObject.fromObject(str);
			if (null == jsonObject.opt("errcode")) {// 返回结果正确
				count = jsonObject.optString("count");
				total = jsonObject.optString("total");
				next_openid = jsonObject.optString("next_openid");
				map.put("count", count);
				map.put("total", total);
				map.put("next_openid", next_openid);
				JSONArray jsonArray = jsonObject.getJSONObject("data")
						.getJSONArray("openid");
				for (int i = 0; i < jsonArray.size(); i++) {
					String open_id = jsonArray.getString(i);
					datas.add(open_id);
				}
				map.put("list", datas);
				return map;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

//	/**
//	 * 服务号: 通过openid、access_token查询UnionID
//	 */
//	public static  getUserInfo(String str) {
//		try {
//			JSONObject jsonObject = JSONObject.fromObject(str);
//			if (null == jsonObject.opt("errcode")) {
//				u = new User();
//				String openid = jsonObject.optString("openid");
//				String unionid = jsonObject.optString("unionid");
//				String nickname = jsonObject.optString("nickname");
//				String sex = jsonObject.optString("sex");
//				u.setOpenid(openid);
//				u.setUnionid(unionid);
//				u.setNickname(nickname);
//				u.setSex(sex);
//				return u;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 企业号: 获得部门id
	 */
	public static List<String> getDepartmentId(String str) {
		List<String> datas = new ArrayList<String>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(str);
			if ("ok".equals(jsonObject.get("errmsg"))) {
				JSONArray jsonArray = jsonObject.getJSONArray("department");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					String id = jsonObject2.optString("id");
					datas.add(id);
				}
				return datas;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据 部门id查询下面人员userID
	 */
	public static List<String> getUserFromDep(String id) {
		List<String> userids = new ArrayList<String>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(id);
			if ("ok".equals(jsonObject.get("errmsg"))) {
				JSONArray jsonArray = jsonObject.getJSONArray("userlist");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					String userid = jsonObject2.optString("userid");
					userids.add(userid);
				}
				return userids;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 批量获取unionid
	 */
	public List<UnionIDModel> getBatchUser(String result) {
		List<UnionIDModel> models = new ArrayList<UnionIDModel>();
		UnionIDModel model = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(result);

			if (null == jsonObject.opt("errcode")) {
				JSONArray jsonArray = jsonObject.getJSONArray("user_info_list");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					model = new UnionIDModel();
					String openid = jsonObject2.optString("openid");
					String unionid = jsonObject2.optString("unionid");
					String subscribe_time = jsonObject2
							.optString("subscribe_time");
					model.setOpenid(openid);
					model.setUnionID(unionid);
					if (!StringUtils.isBlank(subscribe_time)) {
						model.setSubscribe_time(Long.parseLong(subscribe_time));
					}
					models.add(model);
				}
				return models;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
