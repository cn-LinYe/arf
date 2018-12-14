/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dto.MemberCommunityDto;
import com.arf.core.entity.Admin;
import com.arf.core.entity.CommunityModel.BusinessType;
import com.arf.core.entity.DepositLog;
import com.arf.core.entity.Member;
import com.arf.core.entity.PointLog;

/**
 * Service - 会员
 * 
 * @author arf
 * @version 4.0
 */
public interface MemberService extends BaseService<Member, Long> {

	
	/**
	 * 筛选实体对象数量
	 * 
	 * @param admin
	 *            管理员
	 * @return 实体对象数量
	 */
	long countFilterMember(Admin admin);
	
	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断用户名是否禁用
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否禁用
	 */
	boolean usernameDisabled(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param previousEmail
	 *            修改前E-mail(忽略大小写)
	 * @param currentEmail
	 *            当前E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(String previousEmail, String currentEmail);

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
	 *   		 会员类型
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPage(Member.RankingType rankingType,Member.Type memType, Pageable pageable);
	
	/**
	 * 判断会员是否登录
	 * 
	 * @return 会员是否登录
	 */
	boolean isAuthenticated();

	/**
	 * 获取当前登录会员
	 * 
	 * @return 当前登录会员，若不存在则返回null
	 */
	Member getCurrent();

	/**
	 * 获取当前登录会员
	 * 
	 * @param lock
	 *            是否锁定
	 * @return 当前登录会员，若不存在则返回null
	 */
	Member getCurrent(boolean lock);

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名，若不存在则返回null
	 */
	String getCurrentUsername();

	/**
	 * 增加余额
	 * 
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 * @param type
	 *            类型
	 * @param operator
	 *            操作员
	 * @param memo
	 *            备注
	 */
	void addBalance(Member member, BigDecimal amount, DepositLog.Type type, Admin operator, String memo);

	/**
	 * 增加积分
	 * 
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 * @param type
	 *            类型
	 * @param operator
	 *            操作员
	 * @param memo
	 *            备注
	 */
	void addPoint(Member member, long amount, PointLog.Type type, Admin operator, String memo);
	/**
     * 增加积分
     * 
     * @param member
     *            会员
     * @param amount
     *            值
     * @param type
     *            类型
     * @param operator
     *            操作员
     * @param memo
     *            备注
     */
	void addPoint(Member member, long amount, PointLog.Type type, Member operator, String memo,boolean flag);

	/**
	 * 增加消费金额
	 * 
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 */
	void addAmount(Member member, BigDecimal amount);

	/**
     * 根据识别码获取会员
     * @param code  识别码
     * @return
     */
    public List<Member> findListByDocumentCode(String code,String userName);
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
     * @return  会员（商户）分页
     */
    public Page<Member> findPage(Member.RankingType rankingType,Member.Type memtype, Member.State state, Pageable pageable);
    /**
     * 修改是否启用（商户审核）
     * @param bl
     * 是否启用
     */
    public void updateEnabled(Boolean bl ,Long id);

    /**
     * 根据子公司和商户名查询商户
     * @param username
     * 			商户名
     * @param admin
     * 			子公司
     * @return
     */
    Member findByUsername(String username,Admin admin);
    /**
     * 根据子公司以及是否启用获取商户集合
     * @param admin
     * 			子公司
     * @param isEnabled
     * 			是否启用
     * @return 商户集合
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
     * 上传用户头像
     * @param userName 用户名
     * @param avatarPath 头像路径
     * @return 保存成功则返回路径
     */
    String uploadAvatar(String userName,Integer osName,Integer appVersionCode,MultipartFile avatar) throws Exception;
    
    /**
     * 查找小区下的所有用户
     * @param communityNumber
     * @return
     */
    List<Member> findByCommunityNumber(String communityNumber);
    
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
    
    /**通过redis获取用户名查找用户信息
	 * @param userName 用户名
	 * @return
	 */
	public Member findRedisToDbByUserName(String userName);

	/**
	 * 查找用户相关小区,如果用户当前未绑定小区，则需要判空
	 * @param userName
	 * @param queryType 0 所有 1 白名单相关 2 当前绑定
	 * @return
	 */
	List<MemberCommunityDto> findCommunityByUsername(String userName,int queryType);
	
	/**
     * 通过用户名集合查找用户
     * @param userlist
     * @return
     */
    List<Member> findByUserNames(List<String> userlist);

    /**
     * 根据业务查询用户的小区
     * @param userName
     * @param businessTypes com.arf.core.entity.CommunityModel.BusinessType
     * @return property=Set,parkrate=Set
     */
    Map<String,Set<String>> findCommunityByBusiness(String userName, BusinessType ... businessTypes);
}