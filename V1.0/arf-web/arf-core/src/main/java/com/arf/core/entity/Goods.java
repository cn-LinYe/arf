/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.entity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeConverter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.io.ClassPathResource;

import com.arf.core.BaseAttributeConverter;
import com.arf.core.BigDecimalNumericFieldBridge;
import com.arf.core.CommonAttributes;
import com.arf.core.util.FreeMarkerUtils;

import freemarker.template.TemplateException;

/**
 * Entity - 货品
 * 
 * @author arf
 * @version 4.0
 */
@Indexed
@Entity
@Table(name = "xx_goods")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_goods_sequence")
public class Goods extends BaseEntity<Long> {

	private static final long serialVersionUID = -6977025562650112419L;

	/** 点击数缓存名称 */
	public static final String HITS_CACHE_NAME = "goodsHits";

	/** 属性值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

	/** 属性值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/** 静态路径 */
	private static String staticPath;

	/**是新增or修改*/
	public enum Neworup {
		/** 新增 */
		isnew,		
		/**修改*/
		isup,	
	}
	/**
	 * 类型
	 */
	public enum Type {

		/** 普通商品 */
		general,
		
		/**定价服务*/
		pricing,
		
		/**未定价服务*/
		noPricing,
		
		/**保险服务*/
		insurance,
		
		/** 兑换商品 */
		exchange,

		/** 赠品 */
		gift
	}

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 置顶降序 */
		topDesc,

		/** 价格升序 */
		priceAsc,

		/** 价格降序 */
		priceDesc,

		/** 销量升序 */
        salesAsc,
        
		/** 销量降序 */
		salesDesc,

		/** 评分升序 */
        scoreAsc,
        
		/** 评分降序 */
		scoreDesc,

		/** 日期升序 */
        dateAsc,
        
		/** 日期降序 */
		dateDesc
	}

	/**
	 * 排名类型
	 */
	public enum RankingType {

		/** 评分 */
		score,

		/** 评分数 */
		scoreCount,

		/** 周点击数 */
		weekHits,

		/** 月点击数 */
		monthHits,

		/** 点击数 */
		hits,

		/** 周销量 */
		weekSales,

		/** 月销量 */
		monthSales,

		/** 销量 */
		sales
	}
	
	/**
	 * 商品状态
	 */
	public enum State{
		
		/**等待审核*/
		pendingReview,
		
		/**通过审核*/
		through,
		
		/**拒绝*/
		refuse
		
	}

	static {
		try {
			File shopxxXmlFile = new ClassPathResource(CommonAttributes.SHOPXX_XML_PATH).getFile();
			org.dom4j.Document document = new SAXReader().read(shopxxXmlFile);
			org.dom4j.Element element = (org.dom4j.Element) document.selectSingleNode("/shopxx/templateConfig[@id='goodsContent']");
			staticPath = element.attributeValue("staticPath");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/** 编号 */
	private String sn;

	/** 名称 */
	private String name;

	/** 类型 */
	private Type type;

	/** 销售价 */
	private BigDecimal price;

	/** 市场价 */
	private BigDecimal marketPrice;

	/** 展示图片 */
	private String image;

	/** 单位 */
	private String unit;

	/** 重量 */
	private Integer weight;

	/** 是否上架 */
	private Boolean isMarketable;

	/** 是否列出 */
	private Boolean isList;

	/** 是否置顶 */
	private Boolean isTop;

	/** 介绍 */
	private String introduction;

	/** 备注 */
	private String memo;

	/** 搜索关键词 */
	private String keyword;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 评分 */
	private Float score;

	/** 总评分 */
	private Long totalScore;

	/** 评分数 */
	private Long scoreCount;

	/** 周点击数 */
	private Long weekHits;

	/** 月点击数 */
	private Long monthHits;

	/** 点击数 */
	private Long hits;

	/** 周销量 */
	private Long weekSales;

	/** 月销量 */
	private Long monthSales;

	/** 销量 */
	private Long sales;

	/** 周点击数更新日期 */
	private Date weekHitsDate;

	/** 月点击数更新日期 */
	private Date monthHitsDate;

	/** 周销量更新日期 */
	private Date weekSalesDate;

	/** 月销量更新日期 */
	private Date monthSalesDate;

	/** 属性值0 */
	private String attributeValue0;

	/** 属性值1 */
	private String attributeValue1;

	/** 属性值2 */
	private String attributeValue2;

	/** 属性值3 */
	private String attributeValue3;

	/** 属性值4 */
	private String attributeValue4;

	/** 属性值5 */
	private String attributeValue5;

	/** 属性值6 */
	private String attributeValue6;

	/** 属性值7 */
	private String attributeValue7;

	/** 属性值8 */
	private String attributeValue8;

	/** 属性值9 */
	private String attributeValue9;

	/** 属性值10 */
	private String attributeValue10;

	/** 属性值11 */
	private String attributeValue11;

	/** 属性值12 */
	private String attributeValue12;

	/** 属性值13 */
	private String attributeValue13;

	/** 属性值14 */
	private String attributeValue14;

	/** 属性值15 */
	private String attributeValue15;

	/** 属性值16 */
	private String attributeValue16;

	/** 属性值17 */
	private String attributeValue17;

	/** 属性值18 */
	private String attributeValue18;

	/** 属性值19 */
	private String attributeValue19;
	
	/**商品归属的子公司*/
	private Admin admin;
	
	/**商品归属商户*/
	private Member memberBiz;
	
	/**商品状态*/
	private State state;
	/**抵扣的消费劵*/
	private BigDecimal vouchers;
	
	/** 商品可销售区域*/
	private Set<Area> areas = new HashSet<Area>();

	/** 商品分类 */
	private ProductCategory productCategory;

	/** 品牌 */
	private Brand brand;

	
	/** 商品图片 */
	private List<ProductImage> productImages = new ArrayList<ProductImage>();

	/** 参数值 */
	private List<ParameterValue> parameterValues = new ArrayList<ParameterValue>();

	/** 规格项 */
	private List<SpecificationItem> specificationItems = new ArrayList<SpecificationItem>();

	/** 标签 */
	private Set<Tag> tags = new HashSet<Tag>();

	/** 促销 */
	private Set<Promotion> promotions = new HashSet<Promotion>();

	/** 评论 */
	private Set<Review> reviews = new HashSet<Review>();

	/** 咨询 */
	private Set<Consultation> consultations = new HashSet<Consultation>();

	/** 收藏会员 */
	private Set<Member> favoriteMembers = new HashSet<Member>();

	/** 商品 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 审核备注 */
	private String shmemo;
	/**新增or修改*/
	private Neworup neworup;
	
	/**坐标*/
	private String coordinate;

	/**联系电话*/
	private String ctnumber;
	/**联系地址*/
	private String ctaddress;
	/**手机*/
	private String mobile;
	/***
	 * 理财产品编号
	 */
	private String productSn;
	
	
	
	/**  
     * 获取理财产品编号  
     * @return productSn 理财产品编号  
     */
	@Column(name="productSn",length=500)
    public String getProductSn() {
        return productSn;
    }

    /**  
     * 设置理财产品编号  
     * @param productSn 理财产品编号  
     */
    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    /**获取联系手机电话
	 * @return the 手机
	 */
	@Column
	public String getMobile() {
		return mobile;
	}

	/**设置联系手机电话
	 * @param 手机 the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**获取联系电话
	 * @return the 联系电话
	 */
	@Column
	public String getCtnumber() {
		return ctnumber;
	}

	/**设置联系电话
	 * @param 联系电话 the ctnumber to set
	 */
	public void setCtnumber(String ctnumber) {
		this.ctnumber = ctnumber;
	}
	




	/**获取联系地址
	 * @return the 联系地址
	 */
	@Column
	public String getCtaddress() {
		return ctaddress;
	}
	




	/**设置联系地址
	 * @param 联系地址 the ctaddress to set
	 */
	public void setCtaddress(String ctaddress) {
		this.ctaddress = ctaddress;
	}
	




	/**获取坐标值
	 * @return the 坐标
	 */
	@Column
	public String getCoordinate() {
		return coordinate;
	}
	



	/**设置坐标
	 * @param 坐标 the coordinate to set
	 */
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	



	/**获取状态
	 * @return the 新增or修改
	 */
	public Neworup getNeworup() {
		return neworup;
	}
	


	/**设置状态
	 * @param 新增or修改 the neworup to set
	 */
	public void setNeworup(Neworup neworup) {
		this.neworup = neworup;
	}
	


	/**
	 * 获取审核备注
	 * @return the 审核备注
	 */
	public String getShmemo() {
		return shmemo;
	}
	

	/**
	 * 设置审核备注
	 * @param 审核备注 the shmemo to set
	 */
	public void setShmemo(String shmemo) {
		this.shmemo = shmemo;
	}
	

	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	@Length(max = 100)
	@Column(nullable = false, updatable = false, unique = true)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置编号
	 * 
	 * @param sn
	 *            编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	
	/**  
     * 获取抵扣的消费劵  
     * @return vouchers 抵扣的消费劵  
     */
	@Column(precision = 21, scale = 6)
    public BigDecimal getVouchers() {
        return vouchers;
    }

    /**  
     * 设置抵扣的消费劵  
     * @param vouchers 抵扣的消费劵  
     */
    public void setVouchers(BigDecimal vouchers) {
        this.vouchers = vouchers;
    }

    /**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull(groups = Save.class)
	@Column(nullable = false, updatable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取销售价
	 * 
	 * @return 销售价
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置销售价
	 * 
	 * @param price
	 *            销售价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取市场价
	 * 
	 * @return 市场价
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@NumericField
	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@Column(nullable = false, precision = 12, scale = 2)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	/**
	 * 设置市场价
	 * 
	 * @param marketPrice
	 *            市场价
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * 获取展示图片
	 * 
	 * @return 展示图片
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getImage() {
		return image;
	}

	/**
	 * 设置展示图片
	 * 
	 * @param image
	 *            展示图片
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取单位
	 * 
	 * @return 单位
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@Length(max = 200)
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取重量
	 * 
	 * @return 重量
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@NumericField
	@Min(0)
	public Integer getWeight() {
		return weight;
	}

	/**
	 * 设置重量
	 * 
	 * @param weight
	 *            重量
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * 获取是否上架
	 * 
	 * @return 是否上架
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	/**
	 * 设置是否上架
	 * 
	 * @param isMarketable
	 *            是否上架
	 */
	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	/**
	 * 获取是否列出
	 * 
	 * @return 是否列出
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsList() {
		return isList;
	}

	/**
	 * 设置是否列出
	 * 
	 * @param isList
	 *            是否列出
	 */
	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param isTop
	 *            是否置顶
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Lob
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	@Length(max = 200)
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取搜索关键词
	 * 
	 * @return 搜索关键词
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Length(max = 200)
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 设置搜索关键词
	 * 
	 * @param keyword
	 *            搜索关键词
	 */
	public void setKeyword(String keyword) {
		if (keyword != null) {
			keyword = keyword.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.keyword = keyword;
	}

	/**
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Length(max = 200)
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		if (seoKeywords != null) {
			seoKeywords = seoKeywords.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@Length(max = 200)
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取评分
	 * 
	 * @return 评分
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Column(nullable = false, precision = 12, scale = 6)
	public Float getScore() {
		return score;
	}

	/**
	 * 设置评分
	 * 
	 * @param score
	 *            评分
	 */
	public void setScore(Float score) {
		this.score = score;
	}

	/**
	 * 获取总评分
	 * 
	 * @return 总评分
	 */
	@Column(nullable = false)
	public Long getTotalScore() {
		return totalScore;
	}

	/**
	 * 设置总评分
	 * 
	 * @param totalScore
	 *            总评分
	 */
	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * 获取评分数
	 * 
	 * @return 评分数
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Column(nullable = false)
	public Long getScoreCount() {
		return scoreCount;
	}

	/**
	 * 设置评分数
	 * 
	 * @param scoreCount
	 *            评分数
	 */
	public void setScoreCount(Long scoreCount) {
		this.scoreCount = scoreCount;
	}

	/**
	 * 获取周点击数
	 * 
	 * @return 周点击数
	 */
	@Column(nullable = false)
	public Long getWeekHits() {
		return weekHits;
	}

	/**
	 * 设置周点击数
	 * 
	 * @param weekHits
	 *            周点击数
	 */
	public void setWeekHits(Long weekHits) {
		this.weekHits = weekHits;
	}

	/**
	 * 获取月点击数
	 * 
	 * @return 月点击数
	 */
	@Column(nullable = false)
	public Long getMonthHits() {
		return monthHits;
	}

	/**
	 * 设置月点击数
	 * 
	 * @param monthHits
	 *            月点击数
	 */
	public void setMonthHits(Long monthHits) {
		this.monthHits = monthHits;
	}

	/**
	 * 获取点击数
	 * 
	 * @return 点击数
	 */
	@Column(nullable = false)
	public Long getHits() {
		return hits;
	}

	/**
	 * 设置点击数
	 * 
	 * @param hits
	 *            点击数
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	/**
	 * 获取周销量
	 * 
	 * @return 周销量
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Column(nullable = false)
	public Long getWeekSales() {
		return weekSales;
	}

	/**
	 * 设置周销量
	 * 
	 * @param weekSales
	 *            周销量
	 */
	public void setWeekSales(Long weekSales) {
		this.weekSales = weekSales;
	}

	/**
	 * 获取月销量
	 * 
	 * @return 月销量
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Column(nullable = false)
	public Long getMonthSales() {
		return monthSales;
	}

	/**
	 * 设置月销量
	 * 
	 * @param monthSales
	 *            月销量
	 */
	public void setMonthSales(Long monthSales) {
		this.monthSales = monthSales;
	}

	/**
	 * 获取销量
	 * 
	 * @return 销量
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Column(nullable = false)
	public Long getSales() {
		return sales;
	}

	/**
	 * 设置销量
	 * 
	 * @param sales
	 *            销量
	 */
	public void setSales(Long sales) {
		this.sales = sales;
	}

	/**
	 * 获取周点击数更新日期
	 * 
	 * @return 周点击数更新日期
	 */
	@Column(nullable = false)
	public Date getWeekHitsDate() {
		return weekHitsDate;
	}

	/**
	 * 设置周点击数更新日期
	 * 
	 * @param weekHitsDate
	 *            周点击数更新日期
	 */
	public void setWeekHitsDate(Date weekHitsDate) {
		this.weekHitsDate = weekHitsDate;
	}

	/**
	 * 获取月点击数更新日期
	 * 
	 * @return 月点击数更新日期
	 */
	@Column(nullable = false)
	public Date getMonthHitsDate() {
		return monthHitsDate;
	}

	/**
	 * 设置月点击数更新日期
	 * 
	 * @param monthHitsDate
	 *            月点击数更新日期
	 */
	public void setMonthHitsDate(Date monthHitsDate) {
		this.monthHitsDate = monthHitsDate;
	}

	/**
	 * 获取周销量更新日期
	 * 
	 * @return 周销量更新日期
	 */
	@Column(nullable = false)
	public Date getWeekSalesDate() {
		return weekSalesDate;
	}

	/**
	 * 设置周销量更新日期
	 * 
	 * @param weekSalesDate
	 *            周销量更新日期
	 */
	public void setWeekSalesDate(Date weekSalesDate) {
		this.weekSalesDate = weekSalesDate;
	}

	/**
	 * 获取月销量更新日期
	 * 
	 * @return 月销量更新日期
	 */
	@Column(nullable = false)
	public Date getMonthSalesDate() {
		return monthSalesDate;
	}

	/**
	 * 设置月销量更新日期
	 * 
	 * @param monthSalesDate
	 *            月销量更新日期
	 */
	public void setMonthSalesDate(Date monthSalesDate) {
		this.monthSalesDate = monthSalesDate;
	}

	/**
	 * 获取属性值0
	 * 
	 * @return 属性值0
	 */
	@Length(max = 200)
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置属性值0
	 * 
	 * @param attributeValue0
	 *            属性值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取属性值1
	 * 
	 * @return 属性值1
	 */
	@Length(max = 200)
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置属性值1
	 * 
	 * @param attributeValue1
	 *            属性值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取属性值2
	 * 
	 * @return 属性值2
	 */
	@Length(max = 200)
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置属性值2
	 * 
	 * @param attributeValue2
	 *            属性值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取属性值3
	 * 
	 * @return 属性值3
	 */
	@Length(max = 200)
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置属性值3
	 * 
	 * @param attributeValue3
	 *            属性值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取属性值4
	 * 
	 * @return 属性值4
	 */
	@Length(max = 200)
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置属性值4
	 * 
	 * @param attributeValue4
	 *            属性值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取属性值5
	 * 
	 * @return 属性值5
	 */
	@Length(max = 200)
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置属性值5
	 * 
	 * @param attributeValue5
	 *            属性值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取属性值6
	 * 
	 * @return 属性值6
	 */
	@Length(max = 200)
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置属性值6
	 * 
	 * @param attributeValue6
	 *            属性值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取属性值7
	 * 
	 * @return 属性值7
	 */
	@Length(max = 200)
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置属性值7
	 * 
	 * @param attributeValue7
	 *            属性值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取属性值8
	 * 
	 * @return 属性值8
	 */
	@Length(max = 200)
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置属性值8
	 * 
	 * @param attributeValue8
	 *            属性值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取属性值9
	 * 
	 * @return 属性值9
	 */
	@Length(max = 200)
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置属性值9
	 * 
	 * @param attributeValue9
	 *            属性值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	/**
	 * 获取属性值10
	 * 
	 * @return 属性值10
	 */
	@Length(max = 200)
	public String getAttributeValue10() {
		return attributeValue10;
	}

	/**
	 * 设置属性值10
	 * 
	 * @param attributeValue10
	 *            属性值10
	 */
	public void setAttributeValue10(String attributeValue10) {
		this.attributeValue10 = attributeValue10;
	}

	/**
	 * 获取属性值11
	 * 
	 * @return 属性值11
	 */
	@Length(max = 200)
	public String getAttributeValue11() {
		return attributeValue11;
	}

	/**
	 * 设置属性值11
	 * 
	 * @param attributeValue11
	 *            属性值11
	 */
	public void setAttributeValue11(String attributeValue11) {
		this.attributeValue11 = attributeValue11;
	}

	/**
	 * 获取属性值12
	 * 
	 * @return 属性值12
	 */
	@Length(max = 200)
	public String getAttributeValue12() {
		return attributeValue12;
	}

	/**
	 * 设置属性值12
	 * 
	 * @param attributeValue12
	 *            属性值12
	 */
	public void setAttributeValue12(String attributeValue12) {
		this.attributeValue12 = attributeValue12;
	}

	/**
	 * 获取属性值13
	 * 
	 * @return 属性值13
	 */
	@Length(max = 200)
	public String getAttributeValue13() {
		return attributeValue13;
	}

	/**
	 * 设置属性值13
	 * 
	 * @param attributeValue13
	 *            属性值13
	 */
	public void setAttributeValue13(String attributeValue13) {
		this.attributeValue13 = attributeValue13;
	}

	/**
	 * 获取属性值14
	 * 
	 * @return 属性值14
	 */
	@Length(max = 200)
	public String getAttributeValue14() {
		return attributeValue14;
	}

	/**
	 * 设置属性值14
	 * 
	 * @param attributeValue14
	 *            属性值14
	 */
	public void setAttributeValue14(String attributeValue14) {
		this.attributeValue14 = attributeValue14;
	}

	/**
	 * 获取属性值15
	 * 
	 * @return 属性值15
	 */
	@Length(max = 200)
	public String getAttributeValue15() {
		return attributeValue15;
	}

	/**
	 * 设置属性值15
	 * 
	 * @param attributeValue15
	 *            属性值15
	 */
	public void setAttributeValue15(String attributeValue15) {
		this.attributeValue15 = attributeValue15;
	}

	/**
	 * 获取属性值16
	 * 
	 * @return 属性值16
	 */
	@Length(max = 200)
	public String getAttributeValue16() {
		return attributeValue16;
	}

	/**
	 * 设置属性值16
	 * 
	 * @param attributeValue16
	 *            属性值16
	 */
	public void setAttributeValue16(String attributeValue16) {
		this.attributeValue16 = attributeValue16;
	}

	/**
	 * 获取属性值17
	 * 
	 * @return 属性值17
	 */
	@Length(max = 200)
	public String getAttributeValue17() {
		return attributeValue17;
	}

	/**
	 * 设置属性值17
	 * 
	 * @param attributeValue17
	 *            属性值17
	 */
	public void setAttributeValue17(String attributeValue17) {
		this.attributeValue17 = attributeValue17;
	}

	/**
	 * 获取属性值18
	 * 
	 * @return 属性值18
	 */
	@Length(max = 200)
	public String getAttributeValue18() {
		return attributeValue18;
	}

	/**
	 * 设置属性值18
	 * 
	 * @param attributeValue18
	 *            属性值18
	 */
	public void setAttributeValue18(String attributeValue18) {
		this.attributeValue18 = attributeValue18;
	}

	/**
	 * 获取属性值19
	 * 
	 * @return 属性值19
	 */
	@Length(max = 200)
	public String getAttributeValue19() {
		return attributeValue19;
	}

	/**
	 * 设置属性值19
	 * 
	 * @param attributeValue19
	 *            属性值19
	 */
	public void setAttributeValue19(String attributeValue19) {
		this.attributeValue19 = attributeValue19;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * 获取品牌
	 * 
	 * @return 品牌
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Brand getBrand() {
		return brand;
	}

	/**
	 * 设置品牌
	 * 
	 * @param brand
	 *            品牌
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/**
	 * 获取商品图片
	 * 
	 * @return 商品图片
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = ProductImageConverter.class)
	public List<ProductImage> getProductImages() {
		return productImages;
	}

	/**
	 * 设置商品图片
	 * 
	 * @param productImages
	 *            商品图片
	 */
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	/**
	 * 获取参数值
	 * 
	 * @return 参数值
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = ParameterValueConverter.class)
	public List<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	/**
	 * 设置参数值
	 * 
	 * @param parameterValues
	 *            参数值
	 */
	public void setParameterValues(List<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * 获取规格项
	 * 
	 * @return 规格项
	 */
	@Valid
	@Column(length = 4000)
	@Convert(converter = SpecificationItemConverter.class)
	public List<SpecificationItem> getSpecificationItems() {
		return specificationItems;
	}

	/**
	 * 设置规格项
	 * 
	 * @param specificationItems
	 *            规格项
	 */
	public void setSpecificationItems(List<SpecificationItem> specificationItems) {
		this.specificationItems = specificationItems;
	}

	/**
	 * 获取标签
	 * 
	 * @return 标签
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_goods_tag")
	@OrderBy("order asc")
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * 设置标签
	 * 
	 * @param tags
	 *            标签
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * 获取促销
	 * 
	 * @return 促销
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_goods_promotion")
	@OrderBy("order asc")
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * 设置促销
	 * 
	 * @param promotions
	 *            促销
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	@OneToMany(mappedBy = "goods", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * 设置评论
	 * 
	 * @param reviews
	 *            评论
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * 获取咨询
	 * 
	 * @return 咨询
	 */
	@OneToMany(mappedBy = "goods", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Consultation> getConsultations() {
		return consultations;
	}

	/**
	 * 设置咨询
	 * 
	 * @param consultations
	 *            咨询
	 */
	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	/**
	 * 获取收藏会员
	 * 
	 * @return 收藏会员
	 */
	@ManyToMany(mappedBy = "favoriteGoods", fetch = FetchType.LAZY)
	public Set<Member> getFavoriteMembers() {
		return favoriteMembers;
	}

	/**
	 * 设置收藏会员
	 * 
	 * @param favoriteMembers
	 *            收藏会员
	 */
	public void setFavoriteMembers(Set<Member> favoriteMembers) {
		this.favoriteMembers = favoriteMembers;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "goods", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置商品
	 * 
	 * @param products
	 *            商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}


	/**  
	 * 获取商品归属的子公司  
	 * @return admin 商品归属的子公司  
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getAdmin() {
		return admin;
	}
	

	/**  
	 * 设置商品归属的子公司  
	 * @param admin 商品归属的子公司  
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	

	
	/**  
	 * 获取商品归属商户  
	 * @return memberBiz 商品归属商户  
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMemberBiz() {
		return memberBiz;
	}
	

	/**  
	 * 设置商品归属商户  
	 * @param memberBiz 商品归属商户  
	 */
	public void setMemberBiz(Member memberBiz) {
		this.memberBiz = memberBiz;
	}
	

	/**  
	 * 获取商品状态  
	 * @return state 商品状态  
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull(groups = Save.class)
	@Column(nullable = false)
	public State getState() {
		return state;
	}
	

	/**  
	 * 设置商品状态  
	 * @param state 商品状态  
	 */
	public void setState(State state) {
		this.state = state;
	}
	

	/**  
	 * 获取商品可销售区域  
	 * @return areas 商品可销售区域  
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_goods_area")
	@OrderBy("grade asc")
	public Set<Area> getAreas() {
		return areas;
	}
	

	/**  
	 * 设置商品可销售区域  
	 * @param areas 商品可销售区域  
	 */
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
	

	/**
	 * 获取缩略图
	 * 
	 * @return 缩略图
	 */
	@Transient
	public String getThumbnail() {
		if (CollectionUtils.isEmpty(getProductImages())) {
			return null;
		}
		return getProductImages().get(0).getThumbnail();
	}

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("goods", this);
			return FreeMarkerUtils.process(staticPath, model);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取规格项条目ID
	 * 
	 * @return 规格项条目ID
	 */
	@Transient
	public List<Integer> getSpecificationItemEntryIds() {
		List<Integer> specificationItemEntryIds = new ArrayList<Integer>();
		if (CollectionUtils.isNotEmpty(getSpecificationItems())) {
			for (SpecificationItem specificationItem : getSpecificationItems()) {
				if (CollectionUtils.isNotEmpty(specificationItem.getEntries())) {
					for (SpecificationItem.Entry entry : specificationItem.getEntries()) {
						specificationItemEntryIds.add(entry.getId());
					}
				}
			}
			Collections.sort(specificationItemEntryIds);
		}
		return specificationItemEntryIds;
	}

	/**
	 * 获取默认商品
	 * 
	 * @return 默认商品
	 */
	@Transient
	public Product getDefaultProduct() {
		return (Product) CollectionUtils.find(getProducts(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				Product product = (Product) object;
				return product != null && product.getIsDefault();
			}
		});
	}

	/**
	 * 获取有效促销
	 * 
	 * @return 有效促销
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public Set<Promotion> getValidPromotions() {
		if (!Type.general.equals(getType()) || CollectionUtils.isEmpty(getPromotions())) {
			return Collections.emptySet();
		}

		return new HashSet<Promotion>(CollectionUtils.select(getPromotions(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				Promotion promotion = (Promotion) object;
				return promotion != null && promotion.hasBegun() && !promotion.hasEnded() && CollectionUtils.isNotEmpty(promotion.getMemberRanks());
			}
		}));
	}

	/**
	 * 是否存在规格
	 * 
	 * @return 是否存在规格
	 */
	@Transient
	public boolean hasSpecification() {
		return CollectionUtils.isNotEmpty(getSpecificationItems());
	}

	/**
	 * 判断促销是否有效
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销是否有效
	 */
	@Transient
	public boolean isValid(Promotion promotion) {
		if (!Type.general.equals(getType()) || promotion == null || !promotion.hasBegun() || promotion.hasEnded() || CollectionUtils.isEmpty(promotion.getMemberRanks())) {
			return false;
		}
		if (getValidPromotions().contains(promotion)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取属性值
	 * 
	 * @param attribute
	 *            属性
	 * @return 属性值
	 */
	@Transient
	public String getAttributeValue(Attribute attribute) {
		if (attribute == null || attribute.getPropertyIndex() == null) {
			return null;
		}

		try {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + attribute.getPropertyIndex();
			return (String) PropertyUtils.getProperty(this, propertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param attribute
	 *            属性
	 * @param attributeValue
	 *            属性值
	 */
	@Transient
	public void setAttributeValue(Attribute attribute, String attributeValue) {
		if (attribute == null || attribute.getPropertyIndex() == null) {
			return;
		}

		try {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + attribute.getPropertyIndex();
			PropertyUtils.setProperty(this, propertyName, attributeValue);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 移除所有属性值
	 */
	@Transient
	public void removeAttributeValue() {
		for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		if (CollectionUtils.isNotEmpty(getProductImages())) {
			Collections.sort(getProductImages());
		}
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		if (CollectionUtils.isNotEmpty(getProductImages())) {
			Collections.sort(getProductImages());
		}
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Member> favoriteMembers = getFavoriteMembers();
		if (favoriteMembers != null) {
			for (Member favoriteMember : favoriteMembers) {
				favoriteMember.getFavoriteGoods().remove(this);
			}
		}
	}

	/**
	 * 类型转换 - 商品图片
	 * 
	 * @author arf
	 * @version 4.0
	 */
	@Converter
	public static class ProductImageConverter extends BaseAttributeConverter<List<ProductImage>> implements AttributeConverter<Object, String> {
	}

	/**
	 * 类型转换 - 参数值
	 * 
	 * @author arf
	 * @version 4.0
	 */
	@Converter
	public static class ParameterValueConverter extends BaseAttributeConverter<List<ParameterValue>> implements AttributeConverter<Object, String> {
	}

	/**
	 * 类型转换 - 规格项
	 * 
	 * @author arf
	 * @version 4.0
	 */
	@Converter
	public static class SpecificationItemConverter extends BaseAttributeConverter<List<SpecificationItem>> implements AttributeConverter<Object, String> {
	}

}