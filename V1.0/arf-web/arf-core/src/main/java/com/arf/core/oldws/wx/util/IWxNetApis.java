package com.arf.core.oldws.wx.util;

import java.util.List;

import com.arf.core.entity.UnionIDModel;


public interface IWxNetApis {
	/**
	 * 获取微信用户列表
	 * @return
	 */
	public List<String> getWxUsers();
	/**
	 * 获取详细用户信息(最多一百个)
	 * @param opendids
	 * @return
	 */
	public List<UnionIDModel> getWxUnioIDInfo(List<String> opendids);
	
	

}
