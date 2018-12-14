/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.template.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Goods.OrderType;
import com.arf.core.entity.Goods.Type;
import com.arf.core.service.GoodsService;
import com.arf.core.util.FreeMarkerUtils;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 货品列表
 * 
 * @author arf
 * @version 4.0
 */
@Component("goodsListDirective")
public class GoodsListDirective extends BaseDirective {

	/** "类型"参数名称 */
	private static final String TYPE_PARAMETER_NAME = "type";

	/** "商品分类ID"参数名称 */
	private static final String PRODUCT_CATEGORY_ID_PARAMETER_NAME = "productCategoryId";

	/** "品牌ID"参数名称 */
	private static final String BRAND_ID_PARAMETER_NAME = "brandId";

	/** "促销ID"参数名称 */
	private static final String PROMOTION_ID_PARAMETER_NAME = "promotionId";

	/** "标签ID"参数名称 */
	private static final String TAG_ID_PARAMETER_NAME = "tagId";

	/** "属性值"参数名称 */
	private static final String ATTRIBUTE_VALUE_PARAMETER_NAME = "attributeValue";

	/** "最低价格"参数名称 */
	private static final String START_PRICE_PARAMETER_NAME = "startPrice";

	/** "最高价格"参数名称 */
	private static final String END_PRICE_PARAMETER_NAME = "endPrice";

	/** "是否存在促销"参数名称 */
	private static final String HAS_PROMOTION_PARAMETER_NAME = "hasPromotion";

	/** "排序类型"参数名称 */
	private static final String ORDER_TYPE_PARAMETER_NAME = "orderType";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "goodsList";

	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Type type = FreeMarkerUtils.getParameter(TYPE_PARAMETER_NAME, Type.class, params);
		Long productCategoryId = FreeMarkerUtils.getParameter(PRODUCT_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Long brandId = FreeMarkerUtils.getParameter(BRAND_ID_PARAMETER_NAME, Long.class, params);
		Long promotionId = FreeMarkerUtils.getParameter(PROMOTION_ID_PARAMETER_NAME, Long.class, params);
		Long tagId = FreeMarkerUtils.getParameter(TAG_ID_PARAMETER_NAME, Long.class, params);
		Map<String, String> attributeValue = FreeMarkerUtils.getParameter(ATTRIBUTE_VALUE_PARAMETER_NAME, Map.class, params);
		BigDecimal startPrice = FreeMarkerUtils.getParameter(START_PRICE_PARAMETER_NAME, BigDecimal.class, params);
		BigDecimal endPrice = FreeMarkerUtils.getParameter(END_PRICE_PARAMETER_NAME, BigDecimal.class, params);
		Boolean hasPromotion = FreeMarkerUtils.getParameter(HAS_PROMOTION_PARAMETER_NAME, Boolean.class, params);
		OrderType orderType = FreeMarkerUtils.getParameter(ORDER_TYPE_PARAMETER_NAME, OrderType.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Goods.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(env, params);
		Map<Long, String> attributeValueMap = new HashMap<Long, String>();
		if (attributeValue != null) {
			for (Entry<String, String> entry : attributeValue.entrySet()) {
				if (NumberUtils.isDigits(entry.getKey())) {
					Long attributeId = Long.valueOf(entry.getKey());
					attributeValueMap.put(attributeId, entry.getValue());
				}
			}
		}
		List<Goods> goodsList = goodsService.findList(type, productCategoryId, brandId, promotionId, tagId, attributeValueMap, startPrice, endPrice, true, true, null, null, null, hasPromotion, orderType, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, goodsList, env, body);
	}

}