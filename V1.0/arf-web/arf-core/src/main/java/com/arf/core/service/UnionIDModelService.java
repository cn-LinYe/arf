package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.UnionIDModel;

/**
 * Service - UnionID表
 * 
 * @author arf
 * @version 4.0
 */
public interface UnionIDModelService extends BaseService<UnionIDModel, Long> {
	/**
	 * 查询所有微信公众号信息
	 * @return	所有微信公众号信息
	 */
	List<UnionIDModel> selectAll();
	
	/**
	 * 根据openId查询微信公众号信息
	 * @return	 关注信息
	 */
	UnionIDModel selectByOpenId(String openid);
	
	/**
	 * 批量执行sql
	 * @param wxUserUpdates
	 * 				保存需要更新的用户信息
	 * @param wxUserInserts
	 * 				保存需要新添加的用户信息
	 */
	void executeBatchBySql(List<UnionIDModel> wxUserUpdates, List<UnionIDModel> wxUserInserts);
	/**
	 * 通过微信唯一标识查找微信信息
	 * @param unionID
	 * 				微信唯一标识
	 * @return		微信信息
	 */
	List<UnionIDModel> sellectByUnionId(String unionID);
	
	/**
     * 将最新的openid一起插入到数据库中
     * @param ids
     */
    public void insertOpenids(String ids[]);
    /**
     * 获取所有unionId为空的记录
     * @return
     */
    public List<UnionIDModel> getNoUnionID();
    /**
     * 批量更新unionID
     * @param list
     */
    public void updateListUnionID(List<UnionIDModel> list);
}
