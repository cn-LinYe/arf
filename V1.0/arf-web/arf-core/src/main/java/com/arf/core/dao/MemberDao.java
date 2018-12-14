/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dto.MemberCommunityDto;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Member;
import com.arf.core.entity.MemberAttribute;

/**
 * Dao - 会员
 * 
 * @author arf
 * @version 4.0
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 查找会员
	 * 
	 * @param loginPluginId
	 *            登录插件ID
	 * @param openId
	 *            openID
	 * @return 会员，若不存在则返回null
	 */
	Member find(String loginPluginId, String openId);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);

	/**
	 * 根据E-mail查找会员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByEmail(String email);

	/**
	 * 查找会员分页
	 * 
	 * @param rankingType
	 *            排名类型
	 * @param memType
	 *            会员类型
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPage(Member.RankingType rankingType,Member.Type memtype, Pageable pageable);
	
	/**
	 * 查询会员注册数
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员注册数
	 */
	Long registerMemberCount(Date beginDate, Date endDate);

	/**
	 * 清空会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 */
	void clearAttributeValue(MemberAttribute memberAttribute);
	/**
	 * 根据识别码获取会员
	 * @param code  识别码
	 * @return
	 */
	public List<Member> findListByDocumentCode(String code,String username);
	
	/**
	 * 根据类型以及是否启用状态获取会员（商户）分页
	 * @param rankingType
	 * 			排名类型
	 * @param memtype
	 * 			会员类型
	 * @param bl
	 * 			是否启用
	 * @param pageable
	 * 			分页信息
	 * @return
	 * 			会员（商户分页）
	 */
	Page<Member> findPage(Member.RankingType rankingType,Member.Type memtype, Member.State state, Pageable pageable);
	
	/**
	 *修改是否启用
	 *（商户审核）
	 * @param bl
	 * 是否启用
	 */
	void updateEnabled(Boolean bl,Long id);

	/**
	 * 根据子公司和商户的名字查询商户
	 * @param name
	 * 			商户名
	 * @param admin
	 * 			所属子公司
	 * @return 商户
	 */
	Member findByUsername(String name,Admin admin);
	/**
	 * 根据子公司以及是否启用状态获取集合
	 * @param admin
	 * 			子公司
	 * @param isEnabled
	 * 			是否启用
	 * @return  商户集合
	 */
	List<Member> findListByadmin(Admin admin,Member.Type type);
	
	 /**
     * 查小区的注册总数
     * @param communityNumber
     * @return
     */
    BigInteger findListByCommunity(String communityNumber);
    /**
     * 查小区的vip或者非vip数
     * @param communityNumber
     * @param vip
     * @return
     */
    BigInteger findListByCommunityVip(String communityNumber,int vip);

	public void createTestUser();
	
	  /**
     * 是否推送
     * @param isPushSign
     * @return
     */
    List<Member> findByIsPushSign(Integer isPushSign,String CommunityNumber);
    
    /**
     * 通过用户名集合查找用户
     * @param isPushSign
     * @param CommunityNumber
     * @return
     */
    List<Member> findByIsPushSign(List<Object> list);

	/**
	 * 查找用户相关小区
	 * @param userName
	 * @param queryType 0 所有 1 白名单相关 2 当前绑定
	 * @return
	 */
    List<MemberCommunityDto> findCommunityByUsername(String userName, int queryType);
    
    /**
     * 通过用户名集合查找用户
     * @param userlist
     * @return
     */
    List<Member> findByUserNames(List<String> userlist);
}