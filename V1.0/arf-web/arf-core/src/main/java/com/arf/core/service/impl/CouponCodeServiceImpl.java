/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CouponCodeDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Coupon;
import com.arf.core.entity.CouponCode;
import com.arf.core.entity.Member;
import com.arf.core.entity.PointLog;
import com.arf.core.service.CouponCodeService;
import com.arf.core.service.MemberService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 优惠码
 * 
 * @author arf
 * @version 4.0
 */
@Service("couponCodeServiceImpl")
public class CouponCodeServiceImpl extends BaseServiceImpl<CouponCode, Long> implements CouponCodeService {

	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Override
	protected BaseDao<CouponCode, Long> getBaseDao() {
		return couponCodeDao;
	}

	@Transactional(readOnly = true)
	public boolean codeExists(String code) {
		return couponCodeDao.codeExists(code);
	}

	@Transactional(readOnly = true)
	public CouponCode findByCode(String code) {
		return couponCodeDao.findByCode(code);
	}

	public CouponCode build(Coupon coupon, Member member) {
		return couponCodeDao.build(coupon, member);
	}

	public List<CouponCode> build(Coupon coupon, Member member, Integer count) {
		return couponCodeDao.build(coupon, member, count);
	}

	public CouponCode exchange(Coupon coupon, Member member, Admin operator) {
		Assert.notNull(coupon);
		Assert.notNull(coupon.getPoint());
		Assert.state(coupon.getIsEnabled() && coupon.getIsExchange() && !coupon.hasExpired());
		Assert.notNull(member);
		Assert.notNull(member.getPoint());
		Assert.state(member.getPoint() >= coupon.getPoint());

		if (coupon.getPoint() > 0) {
			memberService.addPoint(member, -coupon.getPoint(), PointLog.Type.exchange, operator, null);
		}

		return couponCodeDao.build(coupon, member);
	}

	@Transactional(readOnly = true)
	public Page<CouponCode> findPage(Member member, Pageable pageable) {
		return couponCodeDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		return couponCodeDao.count(coupon, member, hasBegun, hasExpired, isUsed);
	}

}