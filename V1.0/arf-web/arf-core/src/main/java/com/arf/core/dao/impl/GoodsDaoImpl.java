/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.arf.core.Filter;
import com.arf.core.HQLResultConvert;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.Setting;
import com.arf.core.dao.GoodsDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Attribute;
import com.arf.core.entity.Brand;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Goods.OrderType;
import com.arf.core.entity.Goods.State;
import com.arf.core.entity.Goods.Type;
import com.arf.core.entity.Member;
import com.arf.core.entity.Product;
import com.arf.core.entity.ProductCategory;
import com.arf.core.entity.ProductImage;
import com.arf.core.entity.Promotion;
import com.arf.core.entity.Tag;
import com.arf.core.util.JsonUtils;
import com.arf.core.util.SettingUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 货品
 * 
 * @author arf
 * @version 4.0
 */
@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, Long> implements GoodsDao {

	public boolean snExists(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}

		String jpql = "select count(*) from Goods goods where lower(goods.sn) = lower(:sn)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("sn", sn).getSingleResult();
		return count > 0;
	}

	public Goods findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}

		String jpql = "select goods from Goods goods where lower(goods.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Goods.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Goods> findList(Goods.Type type, ProductCategory productCategory, Brand brand, Promotion promotion, Tag tag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean hasPromotion, Goods.OrderType orderType, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("promotions"), promotion));
		}
		if (tag != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("tags"), tag));
		}
		if (attributeValueMap != null) {
			for (Entry<Attribute, String> entry : attributeValueMap.entrySet()) {
				String propertyName = Goods.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isOutOfStock != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			if (isOutOfStock) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, allocatedStock));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isStockAlert != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (hasPromotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(root.join("promotions")));
		}
		criteriaQuery.where(restrictions);
		if (orderType != null) {
			switch (orderType) {
			case topDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceAsc:
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case salesDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case scoreDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case dateDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
				break;
			}
		} else if (CollectionUtils.isEmpty(orders)) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Goods> findList(ProductCategory productCategory, Boolean isMarketable, Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
		return super.findList(criteriaQuery, first, count, null, null);
	}

	public Page<Goods> findPage(Goods.Type type, ProductCategory productCategory, Brand brand, Promotion promotion, Tag tag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean hasPromotion, Goods.OrderType orderType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("promotions"), promotion));
		}
		if (tag != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("tags"), tag));
		}
		if (attributeValueMap != null) {
			for (Entry<Attribute, String> entry : attributeValueMap.entrySet()) {
				String propertyName = Goods.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isOutOfStock != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			if (isOutOfStock) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, allocatedStock));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isStockAlert != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (hasPromotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(root.join("promotions")));
		}
		criteriaQuery.where(restrictions);
		if (orderType != null) {
			switch (orderType) {
			case topDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceAsc:
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case salesAsc:
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
                break;
			case salesDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case scoreAsc:
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
                break;
			case scoreDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case dateDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
				break;
			}
		} else if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && (CollectionUtils.isEmpty(pageable.getOrders())))) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Goods> findPage(Goods.Type type, ProductCategory productCategory, Brand brand, Promotion promotion, Tag tag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock,
            Boolean isStockAlert, Boolean hasPromotion, Goods.OrderType orderType,State state,Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
        Root<Goods> root = criteriaQuery.from(Goods.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (productCategory != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
        }
        if (brand != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
        }
        if (promotion != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("promotions"), promotion));
        }
        if (tag != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("tags"), tag));
        }
        if (attributeValueMap != null) {
            for (Entry<Attribute, String> entry : attributeValueMap.entrySet()) {
                String propertyName = Goods.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
            }
        }
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal temp = startPrice;
            startPrice = endPrice;
            endPrice = temp;
        }
        if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
        }
        if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
        }
        if (isMarketable != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
        }
        if (isList != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
        }
        if (isTop != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
        }
        if(state!=null){
        	restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("state"),state));
        }
        
        if (isOutOfStock != null) {
            Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
            Root<Product> subqueryRoot = subquery.from(Product.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            if (isOutOfStock) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, allocatedStock));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        if (isStockAlert != null) {
            Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
            Root<Product> subqueryRoot = subquery.from(Product.class);
            subquery.select(subqueryRoot);
            Path<Integer> stock = subqueryRoot.get("stock");
            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
            Setting setting = SettingUtils.get();
            if (isStockAlert) {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            } else {
                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
            }
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
        }
        if (hasPromotion != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(root.join("promotions")));
        }
        criteriaQuery.where(restrictions);
        if (orderType != null) {
            switch (orderType) {
            case topDesc:
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
                break;
            case priceAsc:
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
                break;
            case priceDesc:
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
                break;
            case salesAsc:
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
                break;
            case salesDesc:
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
                break;
            case scoreAsc:
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
                break;
            case scoreDesc:
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
                break;
            case dateDesc:
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
                break;
            }
        } else if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && (CollectionUtils.isEmpty(pageable.getOrders())))) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
        }
        return super.findPage(criteriaQuery, pageable);
    }
	
	public Page<Goods> findPage(Goods.Type type, ProductCategory productCategory,Tag tag,BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock,
            Boolean isStockAlert, Boolean hasPromotion, Goods.OrderType orderType,State state,String areaNo,Pageable pageable) {
	    List<Object> list=null;
	    String sql="select DISTINCT g.id id,g.product_images,g.price,g.sales,g.name,g.image,g.score_count,g.score,g.ctnumber phone,g.coordinate,m.nickname,g.mobile mobile from xx_goods g inner join xx_goods_area ga on g.id=ga.goods inner join xx_member m on m.id=g.member_biz inner join (";
		if(StringUtils.isBlank(areaNo)){
		    sql+="select id from xx_area";
		}else{
		    sql+="select b.id from xx_area a inner join xx_area b on a.no='"+areaNo+"' and b.tree_path like CONCAT('%,',a.id,',%') union all select id from xx_area where no='"+areaNo+"'";
		}
	    sql+=") aid on aid.id=ga.areas where 1=1";
//	    list.add(areaNo);
	    
	    String whereStr="";
	    if(productCategory!=null){//如果需要按分类来查
	        whereStr=" and g.product_category in (select id from xx_product_category where id="+productCategory.getId()+" or tree_path like '%,"+productCategory.getId()+",%')";
	    }
	    if(isMarketable!=null){
	        whereStr+=" and g.is_marketable="+(isMarketable?1:0);
	    }
	    if(isTop!=null){
            whereStr+=" and g.is_top="+(isTop?1:0);
        }
	    if(isList!=null){
            whereStr+=" and g.is_list="+(isList?1:0);
        }
	    if(state!=null){
	        whereStr+=" and g.state="+state.ordinal();
	    }
	    if(type!=null){
	        whereStr+=" and g.type="+type.ordinal();
	    }
	    
	    sql+=whereStr;
	    String orderStr="";
	    if (orderType != null) {
            switch (orderType) {
            case topDesc:
                orderStr=" order by g.is_top desc,g.create_date desc";
                break;
            case priceAsc:
                orderStr=" order by g.price asc,g.create_date desc";
                break;
            case priceDesc:
                orderStr=" order by g.price desc,g.create_date desc";
                break;
            case salesAsc:
                orderStr=" order by g.sales asc,g.create_date desc";
                break;
            case salesDesc:
                orderStr=" order by g.sales desc,g.create_date desc";
                break;
            case scoreAsc:
                orderStr=" order by g.score asc,g.create_date desc";
                break;
            case scoreDesc:
                orderStr=" order by g.score desc,g.create_date desc";
                break;
            case dateDesc:
                orderStr=" order by g.create_date desc";
                break;
            }
        } else if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && (CollectionUtils.isEmpty(pageable.getOrders())))) {
            orderStr=" order by g.is_top desc,g.create_date desc";
        }
	    sql+=orderStr;
	    return findPageBySQL(sql, pageable,list,new HQLResultConvert<Goods>() {
            @Override
            public List<Goods> convert(List list) {
                List<Goods> goodlist=new ArrayList<Goods>();
                String productImage="";
                for(int i=0;i<list.size();i++){
                    Object[] obj=(Object[])list.get(i);
                    Goods goods=new Goods();
                    goods.setId(obj[0]==null?null:Long.parseLong(obj[0].toString()));
                    productImage=(obj[1]==null?"":obj[1].toString());
                    Gson gson=new Gson();
                    
                    List<ProductImage> imglist=gson.fromJson(productImage,new TypeToken<ArrayList<ProductImage>>(){
                    }.getType());
                    goods.setProductImages(imglist);
                    goods.setPrice(BigDecimal.valueOf(obj[2]==null?0.00:Double.parseDouble(obj[2].toString())));
                    goods.setSales(obj[3]==null?0:Long.parseLong(obj[3].toString()));
                    goods.setName(obj[4]==null?"":obj[4].toString());
                    goods.setImage(obj[5]==null?"":obj[5].toString());
                    goods.setScoreCount(obj[6]==null?0:Long.parseLong(obj[6].toString()));
                    goods.setScore(obj[7]==null?0:Float.parseFloat(obj[7].toString()));
                    goods.setSeoTitle(obj[8]==null?"":obj[8].toString());
                    goods.setCoordinate(obj[9]==null?"":obj[9].toString());
                    goods.setSeoKeywords(obj[10]==null?"":obj[10].toString());
                    goods.setSeoDescription(obj[11]==null?"":obj[11].toString());
                    goodlist.add(goods);
                }
                return goodlist;
            }
        });
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
//        Root<Goods> root = criteriaQuery.from(Goods.class);
//        criteriaQuery.select(root);
//        Predicate restrictions = criteriaBuilder.conjunction();
//        if (type != null) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
//        }
//        if (productCategory != null) {
//            restrictions = criteriaBuilder.and(restrictions,
//                    criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
//        }
//        if (tag != null) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("tags"), tag));
//        }
//        if (attributeValueMap != null) {
//            for (Entry<Attribute, String> entry : attributeValueMap.entrySet()) {
//                String propertyName = Goods.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
//                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
//            }
//        }
//        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
//            BigDecimal temp = startPrice;
//            startPrice = endPrice;
//            endPrice = temp;
//        }
//        if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
//        }
//        if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
//        }
//        if (isMarketable != null) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
//        }
//        if (isList != null) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
//        }
//        if (isTop != null) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
//        }
//        if(state!=null){
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("state"),state));
//        }
//        
//        if (isOutOfStock != null) {
//            Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
//            Root<Product> subqueryRoot = subquery.from(Product.class);
//            subquery.select(subqueryRoot);
//            Path<Integer> stock = subqueryRoot.get("stock");
//            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
//            if (isOutOfStock) {
//                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
//            } else {
//                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, allocatedStock));
//            }
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
//        }
//        if (isStockAlert != null) {
//            Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
//            Root<Product> subqueryRoot = subquery.from(Product.class);
//            subquery.select(subqueryRoot);
//            Path<Integer> stock = subqueryRoot.get("stock");
//            Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
//            Setting setting = SettingUtils.get();
//            if (isStockAlert) {
//                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
//            } else {
//                subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
//            }
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
//        }
//        if (hasPromotion != null) {
//            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(root.join("promotions")));
//        }
//        criteriaQuery.where(restrictions);
//        if (orderType != null) {
//            switch (orderType) {
//            case topDesc:
//                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
//                break;
//            case priceAsc:
//                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
//                break;
//            case priceDesc:
//                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
//                break;
//            case salesAsc:
//                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
//                break;
//            case salesDesc:
//                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
//                break;
//            case scoreAsc:
//                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
//                break;
//            case scoreDesc:
//                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
//                break;
//            case dateDesc:
//                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
//                break;
//            }
//        } else if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && (CollectionUtils.isEmpty(pageable.getOrders())))) {
//            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
//        }
//        return super.findPage(criteriaQuery, pageable);
    }
	
	public Page<Goods> findPage(Admin admin,Goods.RankingType rankingType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();	
		if(admin.getType()!=Admin.Type.admin){
		restrictions =	criteriaBuilder.and(criteriaBuilder.equal(root.get("admin"),admin));
		}
		if (rankingType != null) {
			switch (rankingType) {
			case score:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")), criteriaBuilder.desc(root.get("scoreCount")));
				break;
			case scoreCount:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreCount")), criteriaBuilder.desc(root.get("score")));
				break;
			case weekHits:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("weekHits")));
				break;
			case monthHits:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("monthHits")));
				break;
			case hits:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("hits")));
				break;
			case weekSales:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("weekSales")));
				break;
			case monthSales:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("monthSales")));
				break;
			case sales:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")));
				break;
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	public Page<Goods> findPage(Goods.RankingType rankingType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);			
		if (rankingType != null) {
			switch (rankingType) {
			case score:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")), criteriaBuilder.desc(root.get("scoreCount")));
				break;
			case scoreCount:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("scoreCount")), criteriaBuilder.desc(root.get("score")));
				break;
			case weekHits:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("weekHits")));
				break;
			case monthHits:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("monthHits")));
				break;
			case hits:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("hits")));
				break;
			case weekSales:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("weekSales")));
				break;
			case monthSales:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("monthSales")));
				break;
			case sales:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")));
				break;
			}
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Goods> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.join("favoriteMembers"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Goods.Type type, Member favoriteMember, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (favoriteMember != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("favoriteMembers"), favoriteMember));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isOutOfStock != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			if (isOutOfStock) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, allocatedStock));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isStockAlert != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}
	
	public Long countFilterGoods(Admin admin, Goods.Type type, Member favoriteMember, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(Admin.Type.admin!=admin.getType()){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("admin"), admin));
		}
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (favoriteMember != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("favoriteMembers"), favoriteMember));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isOutOfStock != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			if (isOutOfStock) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, allocatedStock));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isStockAlert != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public void clearAttributeValue(Attribute attribute) {
		if (attribute == null || attribute.getPropertyIndex() == null || attribute.getProductCategory() == null) {
			return;
		}

		String jpql = "update Goods goods set goods." + Goods.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + attribute.getPropertyIndex() + " = null where goods.productCategory = :productCategory";
		entityManager.createQuery(jpql).setParameter("productCategory", attribute.getProductCategory()).executeUpdate();
	}

	@Override
	public Page<Goods> findPage(State state, Type type, ProductCategory productCategory, Brand brand,
			Promotion promotion, Tag tag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean hasPromotion, OrderType orderType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Goods> criteriaQuery = criteriaBuilder.createQuery(Goods.class);
		Root<Goods> root = criteriaQuery.from(Goods.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(state!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("state"), state));
		}
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory), criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId() + ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("promotions"), promotion));
		}
		if (tag != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("tags"), tag));
		}
		if (attributeValueMap != null) {
			for (Entry<Attribute, String> entry : attributeValueMap.entrySet()) {
				String propertyName = Goods.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isOutOfStock != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			if (isOutOfStock) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, allocatedStock));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isStockAlert != null) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			Path<Integer> stock = subqueryRoot.get("stock");
			Path<Integer> allocatedStock = subqueryRoot.get("allocatedStock");
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				subquery.where(criteriaBuilder.equal(subqueryRoot.get("goods"), root), criteriaBuilder.greaterThan(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (hasPromotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(root.join("promotions")));
		}
		criteriaQuery.where(restrictions);
		if (orderType != null) {
			switch (orderType) {
			case topDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceAsc:
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case salesDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case scoreDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")), criteriaBuilder.desc(root.get("createDate")));
				break;
			case dateDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
				break;
			}
		} else if (pageable == null || ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable.getOrderDirection() == null) && (CollectionUtils.isEmpty(pageable.getOrders())))) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")), criteriaBuilder.desc(root.get("createDate")));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}