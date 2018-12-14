/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.Principal;
import com.arf.core.Setting;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CommunityModelDao;
import com.arf.core.dao.DepositLogDao;
import com.arf.core.dao.MemberDao;
import com.arf.core.dao.MemberRankDao;
import com.arf.core.dao.PointLogDao;
import com.arf.core.dto.MemberCommunityDto;
import com.arf.core.entity.Admin;
import com.arf.core.entity.CommunityModel.BusinessType;
import com.arf.core.entity.DepositLog;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.entity.Member;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.entity.Member.RankingType;
import com.arf.core.entity.Member.Type;
import com.arf.core.entity.MemberRank;
import com.arf.core.entity.PointLog;
import com.arf.core.file.FileServerService;
import com.arf.core.file.FileStoreService;
import com.arf.core.file.FileType;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.MailService;
import com.arf.core.service.MemberService;
import com.arf.core.service.ParkRateModelService;
import com.arf.core.service.SmsService;
import com.arf.core.util.SettingUtils;
import com.arf.propertymgr.entity.PropertyRoomInfo;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind;
import com.arf.propertymgr.entity.PropertyRoomUserBind;
import com.arf.propertymgr.service.IPropertyRoomInfoService;
import com.arf.propertymgr.service.IPropertyRoomSubuserBindService;
import com.arf.propertymgr.service.IPropertyRoomUserBindService;
import com.arf.redis.CacheNameDefinition;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Service - 会员
 * 
 * @author arf
 * @version 4.0
 */
@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {
	
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;
	@Resource(name = "depositLogDaoImpl")
	private DepositLogDao depositLogDao;
	@Resource(name = "pointLogDaoImpl")
	private PointLogDao pointLogDao;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	
	@Resource(name = "communityModelDaoImpl")
	private CommunityModelDao communityModelDao;
	
	@Resource(name = "fileStoreService")
	private FileStoreService fileStoreService;

	@Resource(name = "fileServerService")
	private FileServerService fileServerService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Resource(name = "propertyRoomUserBindServiceImpl")
	private IPropertyRoomUserBindService propertyRoomUserBindServiceImpl;
	
	@Resource(name = "propertyRoomInfoServiceImpl")
	private IPropertyRoomInfoService propertyRoomInfoService;
	
	@Resource(name = "propertyRoomSubuserBindServiceImpl")
	private IPropertyRoomSubuserBindService propertyRoomSubuserBindService;
	
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateModelService;
	
	@Resource(name = "parkRateModelServiceImpl")
	private ParkRateModelService parkRateModelService;
	
	@Override
	protected BaseDao<Member, Long> getBaseDao() {
		return memberDao;
	}

	@Transactional(readOnly = true)
	public long countFilterMember(Admin admin) {
		Filter file=new Filter();
		if(Admin.Type.admin!=admin.getType()){
			file.setProperty("admin");
			file.setOperator(Operator.eq);
			file.setValue(admin);
		}else file=null;
		return getBaseDao().count(file);
	}
	
	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return memberDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public boolean usernameDisabled(String username) {
		Assert.hasText(username);

		Setting setting = SettingUtils.get();
		if (setting.getDisabledUsernames() != null) {
			for (String disabledUsername : setting.getDisabledUsernames()) {
				if (StringUtils.containsIgnoreCase(username, disabledUsername)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return memberDao.emailExists(email);
	}

	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		}
		return !memberDao.emailExists(currentEmail);
	}

	@Transactional(readOnly = true)
	public Member find(String loginPluginId, String openId) {
		return memberDao.find(loginPluginId, openId);
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<Member> findListByEmail(String email) {
		return memberDao.findListByEmail(email);
	}

	@Transactional(readOnly = true)
	public Page<Member> findPage(Member.RankingType rankingType,Type memType, Pageable pageable) {
		return memberDao.findPage(rankingType,memType, pageable);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		return requestAttributes != null && requestAttributes.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, RequestAttributes.SCOPE_SESSION) != null;
	}

	@Transactional(readOnly = true)
	public Member getCurrent() {
		return getCurrent(false);
	}

	@Transactional(readOnly = true)
	public Member getCurrent(boolean lock) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		Principal principal = requestAttributes != null ? (Principal) requestAttributes.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, RequestAttributes.SCOPE_SESSION) : null;
		Long id = principal != null ? principal.getId() : null;
		if (lock) {
			return memberDao.find(id, LockModeType.PESSIMISTIC_WRITE);
		} else {
			return memberDao.find(id);
		}
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		Principal principal = requestAttributes != null ? (Principal) requestAttributes.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, RequestAttributes.SCOPE_SESSION) : null;
		return principal != null ? principal.getUsername() : null;
	}

	public void addBalance(Member member, BigDecimal amount, DepositLog.Type type, Admin operator, String memo) {
		memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);

		Assert.notNull(member);
		Assert.notNull(amount);
		Assert.notNull(type);
		Assert.notNull(member.getBalance());
		Assert.state(member.getBalance().add(amount).compareTo(BigDecimal.ZERO) >= 0);

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		member.setBalance(member.getBalance().add(amount));
		memberDao.flush();

		DepositLog depositLog = new DepositLog();
		depositLog.setType(type);
		depositLog.setCredit(amount.compareTo(BigDecimal.ZERO) > 0 ? amount : BigDecimal.ZERO);
		depositLog.setDebit(amount.compareTo(BigDecimal.ZERO) < 0 ? amount.abs() : BigDecimal.ZERO);
		depositLog.setBalance(member.getBalance());
		depositLog.setOperator(operator);
		depositLog.setMemo(memo);
		depositLog.setMember(member);
		depositLogDao.persist(depositLog);
	}

	public void addPoint(Member member, long amount, PointLog.Type type, Admin operator, String memo) {
		memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);

		Assert.notNull(member);
		Assert.notNull(type);
		Assert.notNull(member.getPoint());
		Assert.state(member.getPoint() + amount >= 0);

		if (amount == 0) {
			return;
		}

		member.setPoint(member.getPoint() + amount);
		memberDao.flush();

		PointLog pointLog = new PointLog();
		pointLog.setType(type);
		pointLog.setCredit(amount > 0 ? amount : 0L);
		pointLog.setDebit(amount < 0 ? Math.abs(amount) : 0L);
		pointLog.setBalance(member.getPoint());
		pointLog.setOperator(operator);
		pointLog.setMemo(memo);
		pointLog.setMember(member);
		pointLogDao.persist(pointLog);
	}

	public void addPoint(Member member, long amount, PointLog.Type type, Member operator, String memo,boolean flag) {
        memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);

        Assert.notNull(member);
        Assert.notNull(type);
        Assert.notNull(member.getPoint());
        Assert.state(member.getPoint() + amount >= 0);

        if (amount == 0) {
            return;
        }

        member.setPoint(member.getPoint() + amount);
        memberDao.flush();

        PointLog pointLog = new PointLog();
        pointLog.setType(type);
        pointLog.setCredit(amount > 0 ? amount : 0L);
        pointLog.setDebit(amount < 0 ? Math.abs(amount) : 0L);
        pointLog.setBalance(member.getPoint());
        pointLog.setOperator(operator.getUsername());
        pointLog.setMemo(memo);
        pointLog.setMember(member);
        pointLogDao.persist(pointLog);
    }
	
	public void addAmount(Member member, BigDecimal amount) {
		memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);

		Assert.notNull(member);
		Assert.notNull(amount);
		Assert.notNull(member.getAmount());
		Assert.state(member.getAmount().add(amount).compareTo(BigDecimal.ZERO) >= 0);

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		member.setAmount(member.getAmount().add(amount));
		MemberRank memberRank = member.getMemberRank();
		if (memberRank != null && BooleanUtils.isFalse(memberRank.getIsSpecial())) {
			MemberRank newMemberRank = memberRankDao.findByAmount(member.getAmount());
			if (newMemberRank != null && newMemberRank.getAmount() != null && newMemberRank.getAmount().compareTo(memberRank.getAmount()) > 0) {
				member.setMemberRank(newMemberRank);
			}
		}
		memberDao.flush();
	}

	@Override
	@Transactional
	public Member save(Member member) {
		Assert.notNull(member);

		Member pMember = super.save(member);
		mailService.sendRegisterMemberMail(pMember);
		smsService.sendRegisterMemberSms(pMember);
		return pMember;
	}

    @Override
    public List<Member> findListByDocumentCode(String code,String username) {
        return memberDao.findListByDocumentCode(code,username);
    }
    
    @Override
	public Page<Member> findPage(RankingType rankingType, Type memtype, Member.State state, Pageable pageable) {
		
		return memberDao.findPage(rankingType, memtype, state, pageable);
	}

	@Override
	public void updateEnabled(Boolean bl,Long id) {
		memberDao.updateEnabled(bl ,id);
		//redisService.de
	}

	@Override
	public Member findByUsername(String username, Admin admin) {		
		return memberDao.findByUsername(username, admin);
	}

	@Override
	public List<Member> findListByadmin(Admin admin, Member.Type type ) {
		return memberDao.findListByadmin(admin, type);
	}

	public void createTestUser(){
	    memberDao.createTestUser();
	}

	@Override
	public BigInteger findListByCommunity(String communityNumber) {
		return memberDao.findListByCommunity(communityNumber);
	}

	@Override
	public BigInteger findListByCommunityVip(String communityNumber, int vip) {
		return memberDao.findListByCommunityVip(communityNumber, vip);
	}
	
	@Override
	public String uploadAvatar(String userName,Integer osName,Integer appVersionCode,MultipartFile avatar) throws Exception{
		Member member = memberDao.findByUsername(userName);
		if(member == null){
			return "";
		}
		String path = null;
		
		String extension = FilenameUtils.getExtension(avatar.getOriginalFilename());
		File tempFile = File.createTempFile(UUID.randomUUID().toString(), "."+extension);
		avatar.transferTo(tempFile);
		
		if((osName != null && osName == 0 && appVersionCode >= 27)
				||(osName != null && osName == 1 && appVersionCode >= 36)){
			path = fileServerService.upload2Server(tempFile, FileType.IMAGE, null, null, null);
		}else{
			//安卓的app版本小于27，ios的app版本小于36需要保持在两个地方
			path = fileStoreService.storeFile(FileType.IMAGE, tempFile);
			String fileName = FilenameUtils.getName(path);
			fileServerService.upload2Server(tempFile, FileType.IMAGE, null, fileName, null);
		}
		
		try{
			if(tempFile != null && tempFile.exists())
				tempFile.delete();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(path)){
			member.setPhoto(path);
			this.update(member);
			redisService.hdel(String.format(CacheNameDefinition.Member_DB_Redis, member.getUsername()),member.getUsername());
			return path;
		}else{
			return "";
		}
	}

	@Override
	public List<Member> findByCommunityNumber(String communityNumber) {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("communityNumber", Operator.eq, communityNumber, true));
		return this.findList(null, filters, null);
	}

	@Override
	public List<Member> findByIsPushSign(Integer isPushSign,String CommunityNumber) {
		return memberDao.findByIsPushSign(isPushSign,CommunityNumber);
	}

	@Override
	public List<Member> findByIsPushSign(List<Object> list) {
		return memberDao.findByIsPushSign(list);
	}

	@Override
	public Member findRedisToDbByUserName(String userName) {
		Member member = redisService.hGet(String.format(CacheNameDefinition.Member_DB_Redis, userName),userName, Member.class);
		if(member!=null){
			return member;
		}
		member = memberDao.findByUsername(userName);
		if(member != null){
			redisService.hset(String.format(CacheNameDefinition.Member_DB_Redis, userName),userName, member);
			redisService.setExpiration(String.format(CacheNameDefinition.Member_DB_Redis, userName), CacheNameDefinition.Default_Expiration);
		}
		return member;
	}

	@Override
	public List<MemberCommunityDto> findCommunityByUsername(String userName,int queryType) {
		return memberDao.findCommunityByUsername(userName,queryType);
	}

	@Override
	public List<Member> findByUserNames(List<String> userlist) {
		return memberDao.findByUserNames(userlist);
	}

	@Override
	public Map<String,Set<String>> findCommunityByBusiness(String userName,
			BusinessType... businessTypes) {
		Map<String,Set<String>> communityMap = Maps.newHashMap();
		Set<String> propertyCommunitySet = Sets.newHashSet();
		Set<String> parkrateCommunitySet = Sets.newHashSet();
		if(StringUtils.isNotBlank(userName)
				&& businessTypes != null
				&& businessTypes.length > 0){
			for (BusinessType businessType : businessTypes) {
				//房屋门禁小区
				if(BusinessType.PROPERTY.equals(businessType)){
					List<PropertyRoomUserBind> propertyRoomUserBindList = propertyRoomUserBindServiceImpl.findByUserName(userName);
					if(CollectionUtils.isNotEmpty(propertyRoomUserBindList)){
						for (PropertyRoomUserBind propertyRoomUserBind : propertyRoomUserBindList) {
							if(propertyRoomUserBind.getStatus() == 1){
								PropertyRoomInfo propertyRoomInfo = propertyRoomInfoService.findByRoomNumber(propertyRoomUserBind.getRoomNumber());
								if(propertyRoomInfo != null
										&& StringUtils.isNotBlank(propertyRoomInfo.getCommunityNumber())){
									propertyCommunitySet.add(propertyRoomInfo.getCommunityNumber());
								}
							}
						}
					}
					List<PropertyRoomSubuserBind> PropertyRoomSubuserBindList = propertyRoomSubuserBindService.findByUserName(userName);
					if(CollectionUtils.isNotEmpty(PropertyRoomSubuserBindList)){
						for (PropertyRoomSubuserBind propertyRoomSubuserBind : PropertyRoomSubuserBindList) {
							if(propertyRoomSubuserBind.getStatus().ordinal() == 1){
								PropertyRoomInfo propertyRoomInfo = propertyRoomInfoService.findByRoomNumber(propertyRoomSubuserBind.getRoomNumber());
								if(propertyRoomInfo != null
										&& StringUtils.isNotBlank(propertyRoomInfo.getCommunityNumber())){
									propertyCommunitySet.add(propertyRoomInfo.getCommunityNumber());
								}
							}
						}
					}
				}
				//白名单小区
				else if(BusinessType.PARKRATE.equals(businessType)){
					List<LicensePlateModel> licensePlateModelList = licensePlateModelService.CheckUser_name(userName);
					if(CollectionUtils.isNotEmpty(licensePlateModelList)){
						for (LicensePlateModel licensePlateModel : licensePlateModelList) {
							String licensePlate = licensePlateModel.getLicense_plate_number();
							if(StringUtils.isNotBlank(licensePlate)){
								List<ParkRateModel> parkRateModelList = parkRateModelService.selectListByLicensePlate(licensePlate);
								if(CollectionUtils.isNotEmpty(parkRateModelList)){
									for (ParkRateModel parkRateModel : parkRateModelList) {
										if(StringUtils.isNotBlank(parkRateModel.getCommunityNumber())){
											parkrateCommunitySet.add(parkRateModel.getCommunityNumber());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		communityMap.put("property", propertyCommunitySet);
		communityMap.put("parkrate", parkrateCommunitySet);
		return communityMap;
	}
}