package com.arf.review.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.review.dao.ServiceReviewDao;
import com.arf.review.entity.ServiceReview;
import com.arf.review.entity.ServiceReview.Type;
import com.arf.review.service.ServiceReviewService;

@Service("serviceReviewService")
public class ServiceReviewServiceImpl extends BaseServiceImpl<ServiceReview, Long> implements ServiceReviewService{

	@Resource(name = "serviceReviewDao")
	private ServiceReviewDao serviceReviewDao;

	@Override
	protected BaseDao<ServiceReview, Long> getBaseDao() {
		return serviceReviewDao;
	}

	@Override
	public List<ServiceReview> findByOutTradeNo(String orderNo) {
		return serviceReviewDao.findByOutTradeNo(orderNo);
	}

	@Override
	public ServiceReview findByOutTradeNoAndForReview(String outTradeNo,ServiceReview forReview) {
		return serviceReviewDao.findByOutTradeNoAndForReview(outTradeNo,forReview);
	}

	@Override
	public PageResult<Map<String,Object>> findReviewInfo(String orderNo, Integer score, Type type, Integer pageSize,
			Integer pageNo, Integer isImages, Integer anonymous, Integer businessNum, String userName) {
		return serviceReviewDao.findReviewInfo(orderNo, score, type, pageSize, pageNo, isImages, anonymous, businessNum, userName);
	}

	@Override
	public List<ServiceReview> findByOutTradeNos(List<Object> orderNos) {
		return serviceReviewDao.findByOutTradeNos(orderNos);
	}

	@Override
	public  Map<String,Object> selectCountImages(String orderNo, Integer score, Type type, Integer isImages, Integer anonymous, Integer businessNum, String userName) {
		return serviceReviewDao.selectCountImages( orderNo,  score,  type, isImages,  anonymous,  businessNum,  userName);
	}

	@Override
	public Map<String, Object> findCountByScores(String orderNo, Integer score, Type type, Integer isImages, Integer anonymous, Integer businessNum, String userName) {
		return serviceReviewDao.findCountByScores( orderNo,  score,  type, isImages,  anonymous,  businessNum,  userName);
	}
}
