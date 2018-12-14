/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import net.sf.ehcache.CacheManager;
import com.arf.core.dao.AttributeDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.BrandDao;
import com.arf.core.dao.GoodsDao;
import com.arf.core.dao.ProductCategoryDao;
import com.arf.core.dao.ProductDao;
import com.arf.core.dao.PromotionDao;
import com.arf.core.dao.SnDao;
import com.arf.core.dao.StockLogDao;
import com.arf.core.dao.TagDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Goods.Type;
import com.arf.core.entity.Member;
import com.arf.core.entity.Product;
import com.arf.core.entity.StockLog;
import com.arf.core.service.ProductService;
import com.arf.core.service.StaticService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 商品
 * 
 * @author arf
 * @version 4.0
 */
@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;
	@Resource(name = "productCategoryDaoImpl")
	private ProductCategoryDao productCategoryDao;
	@Resource(name = "brandDaoImpl")
	private BrandDao brandDao;
	@Resource(name = "promotionDaoImpl")
	private PromotionDao promotionDao;
	@Resource(name = "tagDaoImpl")
	private TagDao tagDao;
	@Resource(name = "attributeDaoImpl")
	private AttributeDao attributeDao;
	@Resource(name = "stockLogDaoImpl")
	private StockLogDao stockLogDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Override
	protected BaseDao<Product, Long> getBaseDao() {
		return productDao;
	}

	@Transactional(readOnly = true)
	public boolean snExists(String sn) {
		return productDao.snExists(sn);
	}

	@Transactional(readOnly = true)
	public Product findBySn(String sn) {
		return productDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public List<Product> search(Goods.Type type, String keyword, Set<Product> excludes, Integer count) {
		return productDao.search(type, keyword, excludes, count);
	}

	public void addStock(Product product, int amount, StockLog.Type type, Admin operator, String memo) {
		productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);

		Assert.notNull(product);
		Assert.notNull(type);
		Assert.notNull(product.getStock());
		Assert.state(product.getStock() + amount >= 0);

		if (amount == 0) {
			return;
		}

		product.setStock(product.getStock() + amount);
		productDao.flush();

		StockLog stockLog = new StockLog();
		stockLog.setType(type);
		stockLog.setInQuantity(amount > 0 ? amount : 0);
		stockLog.setOutQuantity(amount < 0 ? Math.abs(amount) : 0);
		stockLog.setStock(product.getStock());
		stockLog.setOperator(operator);
		stockLog.setMemo(memo);
		stockLog.setProduct(product);
		stockLogDao.persist(stockLog);
	}

	public void addStock(Product product, int amount, StockLog.Type type,Member operator, String memo) {
        productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);

        Assert.notNull(product);
        Assert.notNull(type);
        Assert.notNull(product.getStock());
        Assert.state(product.getStock() + amount >= 0);

        if (amount == 0) {
            return;
        }

        product.setStock(product.getStock() + amount);
        productDao.flush();

        StockLog stockLog = new StockLog();
        stockLog.setType(type);
        stockLog.setInQuantity(amount > 0 ? amount : 0);
        stockLog.setOutQuantity(amount < 0 ? Math.abs(amount) : 0);
        stockLog.setStock(product.getStock());
        stockLog.setOperator(operator.getUsername());
        stockLog.setMemo(memo);
        stockLog.setProduct(product);
        stockLogDao.persist(stockLog);
    }
	
	public void addAllocatedStock(Product product, int amount) {
		productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);

		Assert.notNull(product);
		Assert.notNull(product.getAllocatedStock());
		Assert.state(product.getAllocatedStock() + amount >= 0);

		if (amount == 0) {
			return;
		}

		product.setAllocatedStock(product.getAllocatedStock() + amount);
		productDao.flush();
	}

	@Transactional(readOnly = true)
	public void filter(List<Product> products) {
		CollectionUtils.filter(products, new Predicate() {
			public boolean evaluate(Object object) {
				Product product = (Product) object;
				return product != null && product.getStock() != null;
			}
		});
	}

	@Override
	public List<Product> search(Type type, String keyword, Set<Product> excludes, Integer count, Admin admin) {
		return productDao.search(type, keyword, excludes, count, admin);
	}

}