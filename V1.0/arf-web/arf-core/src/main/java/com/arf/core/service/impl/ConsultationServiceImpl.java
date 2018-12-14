/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ConsultationDao;
import com.arf.core.dao.GoodsDao;
import com.arf.core.dao.MemberDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Consultation;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Member;
import com.arf.core.service.ConsultationService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 咨询
 * 
 * @author arf
 * @version 4.0
 */
@Service("consultationServiceImpl")
public class ConsultationServiceImpl extends BaseServiceImpl<Consultation, Long> implements ConsultationService {

	@Resource(name = "consultationDaoImpl")
	private ConsultationDao consultationDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;

	@Override
	protected BaseDao<Consultation, Long> getBaseDao() {
		return consultationDao;
	}

	@Transactional(readOnly = true)
	public List<Consultation> findList(Member member, Goods goods, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		return consultationDao.findList(member, goods, isShow, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "consultation", condition = "#useCache")
	public List<Consultation> findList(Long memberId, Long goodsId, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return new ArrayList<Consultation>();
		}
		Goods goods = goodsDao.find(goodsId);
		if (goodsId != null && goods == null) {
			return new ArrayList<Consultation>();
		}
		return consultationDao.findList(member, goods, isShow, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public Page<Consultation> findPage(Member member, Goods goods, Boolean isShow, Pageable pageable) {
		return consultationDao.findPage(member, goods, isShow, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, Goods goods, Boolean isShow) {
		return consultationDao.count(member, goods, isShow);
	}

	@CacheEvict(value = "consultation", allEntries = true)
	public void reply(Consultation consultation, Consultation replyConsultation) {
		if (consultation == null || replyConsultation == null) {
			return;
		}
		consultation.setIsShow(true);

		replyConsultation.setIsShow(true);
		replyConsultation.setGoods(consultation.getGoods());
		replyConsultation.setForConsultation(consultation);
		consultationDao.persist(replyConsultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "consultation", allEntries = true)
	public Consultation save(Consultation consultation) {
		return super.save(consultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "consultation", allEntries = true)
	public Consultation update(Consultation consultation) {
		return super.update(consultation);
	}

	@Override
	@Transactional
	@CacheEvict(value = "consultation", allEntries = true)
	public Consultation update(Consultation consultation, String... ignoreProperties) {
		return super.update(consultation, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "consultation", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "consultation", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "consultation", allEntries = true)
	public void delete(Consultation consultation) {
		super.delete(consultation);
	}

	@Override
	public Page<Consultation> findPage(Admin admin, Pageable pageable) {
		return consultationDao.findPage(admin, pageable);
	}

}