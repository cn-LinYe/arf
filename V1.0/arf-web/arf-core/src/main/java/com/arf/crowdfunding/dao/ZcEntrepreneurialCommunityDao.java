package com.arf.crowdfunding.dao;

import java.math.BigDecimal;
import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.crowdfunding.entity.ZcEntrepreneurialCommunity;

/**
 * 获取小区店铺信息
 * @author Administrator
 *
 */
public interface ZcEntrepreneurialCommunityDao extends BaseDao<ZcEntrepreneurialCommunity, Long>{

	/**
	 * 获取众筹项目信息
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @return
	 */
	public List<ZcEntrepreneurialCommunity> getShopMessage(String community_number,String project_id);
	/**
	 * 获取众筹项目名
	 * @param project_id	项目ID
	 * @return
	 */
	public String getShopName(String project_id);
	/**
	 * 得到众筹项目的信息
	 * @param communityNumber
	 * @param projectId
	 * @return
	 */
	public ZcEntrepreneurialCommunity getTotalSharesNum(String communityNumber,String projectId);
	
	public List<ZcEntrepreneurialCommunity> getShopMessageByCommunity(String community_number,Integer shopStatus,String shopkeeperPartnerUser);
	/**
	 * 更新众筹项目状态
	 * @param community_number
	 * @param project_id
	 * @param status
	
	public void updateStatus(String community_number, String project_id, int status);
	 */
	/**
	 * 判断项目已过期
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 * @return
	 */
	public boolean isOverdue(String community_number, String project_id);
	/**
	 * 得到项目最小众筹金额
	 * @param community_number
	 * @param project_id
	 * @return
	 */
	public BigDecimal getLeastAmount(String community_number, String project_id);
	/**
	 * 获取项目
	 * @param community_number
	 * @param project_id
	 * @return
	 */
	public ZcEntrepreneurialCommunity getCommunityMessage(String community_number, String project_id);
	
}
