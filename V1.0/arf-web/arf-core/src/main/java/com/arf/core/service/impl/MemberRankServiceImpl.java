/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.MemberRankDao;
import com.arf.core.dao.ProductDao;
import com.arf.core.entity.MemberRank;
import com.arf.core.service.MemberRankService;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 会员等级
 * 
 * @author arf
 * @version 4.0
 */
@Service("memberRankServiceImpl")
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank, Long> implements MemberRankService {

	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;

	@Override
	protected BaseDao<MemberRank, Long> getBaseDao() {
		return memberRankDao;
	}

	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return memberRankDao.nameExists(name);
	}

	@Transactional(readOnly = true)
	public boolean nameUnique(String previousName, String currentName) {
		if (StringUtils.equalsIgnoreCase(previousName, currentName)) {
			return true;
		} else {
			if (memberRankDao.nameExists(currentName)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public boolean amountExists(BigDecimal amount) {
		return memberRankDao.amountExists(amount);
	}

	@Transactional(readOnly = true)
	public boolean amountUnique(BigDecimal previousAmount, BigDecimal currentAmount) {
		if (previousAmount != null && previousAmount.compareTo(currentAmount) == 0) {
			return true;
		} else {
			if (memberRankDao.amountExists(currentAmount)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Transactional(readOnly = true)
	public MemberRank findDefault() {
		return memberRankDao.findDefault();
	}

	@Transactional(readOnly = true)
	public MemberRank findByAmount(BigDecimal amount) {
		return memberRankDao.findByAmount(amount);
	}

	@Override
	@Transactional
	public MemberRank save(MemberRank memberRank) {
		Assert.notNull(memberRank);

		if (BooleanUtils.isTrue(memberRank.getIsDefault())) {
			memberRankDao.setDefault(memberRank);
		}
		return super.save(memberRank);
	}

	@Override
	@Transactional
	public MemberRank update(MemberRank memberRank) {
		Assert.notNull(memberRank);

		if (BooleanUtils.isTrue(memberRank.getIsDefault())) {
			memberRankDao.setDefault(memberRank);
		}
		return super.update(memberRank);
	}

}