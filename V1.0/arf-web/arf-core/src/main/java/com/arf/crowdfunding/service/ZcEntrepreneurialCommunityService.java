package com.arf.crowdfunding.service;

import java.math.BigDecimal;
import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.crowdfunding.entity.ZcEntrepreneurialCommunity;

/**
 * 获取开店信息接口
 * @author LW on 2016-06-18
 * @version 1.0
 *
 */
public interface ZcEntrepreneurialCommunityService extends BaseService<ZcEntrepreneurialCommunity, Long> {

	/**
	 * 得到众筹项目信息
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @return
	 */
	public List<ZcEntrepreneurialCommunity> getShopMessage(String community_number,String project_id);
	/**
	 * 得到众筹项目名
	 * @param project_id	项目ID
	 * @return
	 */
	public String getShopName(String project_id);
	/**
	 * 得到众筹项目总股数
	 * @param communityNumber
	 * @param projectId
	 * @return
	 */
	public ZcEntrepreneurialCommunity getTotalSharesNum(String communityNumber,String projectId);
	/**
	 * 得到众筹信息
	 * @param community_number	小区编号
	 * @param shopStatus	店铺状态
	 * @return
	 */
	public List<ZcEntrepreneurialCommunity> getShopMessageByCommunity(String community_number,Integer shopStatus,String shopkeeperPartnerUser);
	/**
	 * 更新众筹项目状态
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @param status			状态
	 */
	public void updateStatus(String community_number,String project_id,int status);
	/**
	 * 判断项目是否已过期
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @return
	 */
	public boolean isOverdue(String community_number,String project_id);
	/**
	 * 得到项目最小众筹金额
	 * @param community_number
	 * @param project_id
	 * @return
	 */
	public BigDecimal getLeastAmount(String community_number,String project_id);
}

