/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.arf.core.AppMessage;
import com.arf.core.AppMessageCode;
import com.arf.core.TemplateConfig;
import com.arf.core.dao.ArticleDao;
import com.arf.core.dao.BrandDao;
import com.arf.core.dao.GoodsDao;
import com.arf.core.dao.PromotionDao;
import com.arf.core.entity.Article;
import com.arf.core.entity.Brand;
import com.arf.core.entity.Consultation;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Product;
import com.arf.core.entity.Promotion;
import com.arf.core.entity.Review;
import com.arf.core.service.ConsultationService;
import com.arf.core.service.ReviewService;
import com.arf.core.service.StaticService;
import com.arf.core.service.TemplateConfigService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service - 静态化
 * 
 * @author arf
 * @version 4.0
 */
@Service("staticServiceImpl")
public class StaticServiceImpl implements StaticService, ServletContextAware {

	/** Sitemap最大地址数 */
	private static final Integer SITEMAP_MAX_SIZE = 10000;

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "templateConfigServiceImpl")
	private TemplateConfigService templateConfigService;
	@Resource(name = "articleDaoImpl")
	private ArticleDao articleDao;
	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;
	@Resource(name = "brandDaoImpl")
	private BrandDao brandDao;
	@Resource(name = "promotionDaoImpl")
	private PromotionDao promotionDao;
	@Resource(name = "reviewServiceImpl")
    private ReviewService reviewService;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Transactional(readOnly = true)
	public int build(String templatePath, String staticPath, Map<String, Object> model) {
		Assert.hasText(templatePath);
		Assert.hasText(staticPath);

		Writer writer = null;
		try {
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
			File staticFile = new File(servletContext.getRealPath(staticPath));
			File staticDir = staticFile.getParentFile();
			if (staticDir != null) {
				staticDir.mkdirs();
			}
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(staticFile), "UTF-8"));
			template.process(model, writer);
			writer.flush();
			return 1;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	@Transactional(readOnly = true)
	public int build(Article article) {
		if (article == null) {
			return 0;
		}

		delete(article);
		int buildCount = 0;
		if (article.getIsPublication()) {
			TemplateConfig templateConfig = templateConfigService.get("articleContent");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("article", article);
			for (int pageNumber = 1; pageNumber <= article.getTotalPages(); pageNumber++) {
				article.setPageNumber(pageNumber);
				buildCount += build(templateConfig.getRealTemplatePath(), article.getPath(), model);
			}
			article.setPageNumber(null);
		}
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int build(Goods goods) {
		if (goods == null) {
			return 0;
		}

		delete(goods);
		int buildCount = 0;
		if (goods.getIsMarketable()) {
			TemplateConfig templateConfig = templateConfigService.get("goodsContent");
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("goods", goods);
			buildCount += build(templateConfig.getRealTemplatePath(), goods.getPath(), model);
			
			templateConfig = templateConfigService.get("appgoodsContent");
            model.put("content", getGoods(goods));
            build(templateConfig.getRealTemplatePath(),templateConfig.getRealStaticPath()+"/"+goods.getId()+".html", model);
		}
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildIndex() {
		TemplateConfig templateConfig = templateConfigService.get("index");
		return build(templateConfig.getRealTemplatePath(), templateConfig.getRealStaticPath(), null);
	}

	@Transactional(readOnly = true)
	public int buildSitemap() {
		int buildCount = 0;
		TemplateConfig sitemapIndexTemplateConfig = templateConfigService.get("sitemapIndex");
		TemplateConfig sitemapTemplateConfig = templateConfigService.get("sitemap");
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> sitemapPaths = new ArrayList<String>();
		forLoop: for (int step = 0, index = 0, first = 0, count = SITEMAP_MAX_SIZE;;) {
			model.put("index", index);
			switch (step) {
			case 0:
				List<Article> articles = articleDao.findList(first, count, null, null);
				model.put("articles", articles);
				if (articles.size() < count) {
					step++;
					first = 0;
					count -= articles.size();
				} else {
					buildCount += build(sitemapTemplateConfig.getRealTemplatePath(), sitemapTemplateConfig.getRealStaticPath(model), model);
					articleDao.flush();
					articleDao.clear();
					sitemapPaths.add(sitemapTemplateConfig.getRealStaticPath(model));
					model.clear();
					index++;
					first += articles.size();
					count = SITEMAP_MAX_SIZE;
				}
				break;
			case 1:
				List<Goods> goods = goodsDao.findList(first, count, null, null);
				model.put("goods", goods);
				if (goods.size() < count) {
					step++;
					first = 0;
					count -= goods.size();
				} else {
					buildCount += build(sitemapTemplateConfig.getRealTemplatePath(), sitemapTemplateConfig.getRealStaticPath(model), model);
					goodsDao.flush();
					goodsDao.clear();
					sitemapPaths.add(sitemapTemplateConfig.getRealStaticPath(model));
					model.clear();
					index++;
					first += goods.size();
					count = SITEMAP_MAX_SIZE;
				}
				break;
			case 2:
				List<Brand> brands = brandDao.findList(first, count, null, null);
				model.put("brands", brands);
				if (brands.size() < count) {
					step++;
					first = 0;
					count -= brands.size();
				} else {
					buildCount += build(sitemapTemplateConfig.getRealTemplatePath(), sitemapTemplateConfig.getRealStaticPath(model), model);
					brandDao.flush();
					brandDao.clear();
					sitemapPaths.add(sitemapTemplateConfig.getRealStaticPath(model));
					model.clear();
					index++;
					first += brands.size();
					count = SITEMAP_MAX_SIZE;
				}
				break;
			case 3:
				List<Promotion> promotions = promotionDao.findList(first, count, null, null);
				model.put("promotions", promotions);
				buildCount += build(sitemapTemplateConfig.getRealTemplatePath(), sitemapTemplateConfig.getRealStaticPath(model), model);
				promotionDao.flush();
				promotionDao.clear();
				sitemapPaths.add(sitemapTemplateConfig.getRealStaticPath(model));
				if (promotions.size() < count) {
					break forLoop;
				} else {
					model.clear();
					index++;
					first += promotions.size();
					count = SITEMAP_MAX_SIZE;
				}
				break;
			}
		}
		model.put("sitemapPaths", sitemapPaths);
		buildCount += build(sitemapIndexTemplateConfig.getRealTemplatePath(), sitemapIndexTemplateConfig.getRealStaticPath(), model);
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildOther() {
		int buildCount = 0;
		TemplateConfig shopCommonJsTemplateConfig = templateConfigService.get("shopCommonJs");
		TemplateConfig adminCommonJsTemplateConfig = templateConfigService.get("adminCommonJs");
		buildCount += build(shopCommonJsTemplateConfig.getRealTemplatePath(), shopCommonJsTemplateConfig.getRealStaticPath(), null);
		buildCount += build(adminCommonJsTemplateConfig.getRealTemplatePath(), adminCommonJsTemplateConfig.getRealStaticPath(), null);
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildAll() {
		int buildCount = 0;
		for (int i = 0; i < articleDao.count(); i += 20) {
			List<Article> articles = articleDao.findList(i, 20, null, null);
			for (Article article : articles) {
				buildCount += build(article);
			}
			articleDao.clear();
		}
		for (int i = 0; i < goodsDao.count(); i += 20) {
			List<Goods> goods = goodsDao.findList(i, 20, null, null);
			for (Goods g : goods) {
				buildCount += build(g);
			}
			goodsDao.clear();
		}
		buildIndex();
		buildSitemap();
		buildOther();
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int delete(String staticPath) {
		Assert.hasText(staticPath);

		File staticFile = new File(servletContext.getRealPath(staticPath));
		if (staticFile.exists()) {
			staticFile.delete();
			return 1;
		}
		return 0;
	}

	@Transactional(readOnly = true)
	public int delete(Article article) {
		if (article == null) {
			return 0;
		}

		int deleteCount = 0;
		for (int pageNumber = 1; pageNumber <= article.getTotalPages() + 1000; pageNumber++) {
			article.setPageNumber(pageNumber);
			int count = delete(article.getPath());
			if (count < 1) {
				break;
			}
			deleteCount += count;
		}
		article.setPageNumber(null);
		return deleteCount;
	}

	@Transactional(readOnly = true)
	public int delete(Goods goods) {
		if (goods == null) {
			return 0;
		}

		return delete(goods.getPath());
	}

	@Transactional(readOnly = true)
	public int deleteIndex() {
		TemplateConfig templateConfig = templateConfigService.get("index");
		return delete(templateConfig.getRealStaticPath());
	}

	@Transactional(readOnly = true)
	public int deleteOther() {
		int deleteCount = 0;
		TemplateConfig shopCommonJsTemplateConfig = templateConfigService.get("shopCommonJs");
		TemplateConfig adminCommonJsTemplateConfig = templateConfigService.get("adminCommonJs");
		deleteCount += delete(shopCommonJsTemplateConfig.getRealStaticPath());
		deleteCount += delete(adminCommonJsTemplateConfig.getRealStaticPath());
		return deleteCount;
	}
	/**
	 * 获取物品信息
	 * @return
	 */
	public String getGoods(Goods g){
	    SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
	    AppMessage msg=new AppMessage();
        Map<String,Object> maps=new HashMap<String,Object>();
        try{
//            Goods g=goodsService.find(id);
            Product p=g.getDefaultProduct();
            Map<String,Object> m=new HashMap<String,Object>();
            m.put("productImages", g.getProductImages());//产品图片列表
            m.put("name", g.getName());//物品名
            m.put("sn",g.getSn());//编号
            m.put("type", g.getType());//类型
            m.put("scoreCount",g.getScoreCount());//评分数
            m.put("score", g.getScore());//评分
            m.put("marketPrice",p.getMarketPrice());//市场价
            m.put("price", p.getPrice());//价钱
            m.put("id",p.getId());
            m.put("gid",g.getId());
//            m.put("isValidPromotions",g.getValidPromotions().isEmpty());//是否促销
//            m.put("validPromotions",g.getValidPromotions());
            m.put("rewardPoint",p.getRewardPoint());//获取积分
            m.put("exchangePoint",p.getExchangePoint());//兑换积分，当商品为兑换商品时使用
            m.put("hasSpecification",g.hasSpecification());//是否存在规格
//            p.getSpecificationValueIds();
//            List<SpecificationItem> spelist=g.getSpecificationItems();//规格列表
            m.put("specificationItem", g.getSpecificationItems());//规格列表
            m.put("specificationValueIds",p.getSpecificationValueIds());//默认选中的规格索引
            m.put("introduction",g.getIntroduction());//商品介绍
            m.put("parameterValues",g.getParameterValues());//商品参数
            m.put("isOutOfStock",p.getIsOutOfStock());
            m.put("vouchers",g.getVouchers());//有效抵扣金额
//            m.put("phone",g.getMemberBiz()==null?"":g.getMemberBiz().getPhone());//商户联系电话
            m.put("phone",g.getCtnumber());//商户联系电话
            
            Set<Promotion> setpm=g.getValidPromotions();
            Iterator<Promotion> itpm=setpm.iterator();
            String pms="";
            while(itpm.hasNext()){
                pms+=itpm.next().getName()+" ";
            }
            m.put("validPromotions", pms);//有效促销
            
            //products
            Set<Product> pset=g.getProducts();
            Iterator<Product> pit=pset.iterator();
            Map<String,Object> pm=null;
            List<Map<String,Object>> plist=new ArrayList<Map<String,Object>>();
            Product p1=null;
            while(pit.hasNext()){
                pm=new HashMap<String, Object>();
                p1=pit.next();
                pm.put("id",p1.getId());
                pm.put("price",p1.getPrice());
                pm.put("marketPrice",p1.getMarketPrice());
                pm.put("rewardPoint",p1.getRewardPoint());
                pm.put("exchangePoint",p1.getExchangePoint());
                pm.put("isOutOfStock",p1.getIsOutOfStock());
                pm.put("specificationValueIds",p1.getSpecificationValueIds());
                plist.add(pm);
            }
            m.put("products",plist);
            
//            Set<Review> setrev=g.getReviews();
            List<Review> setrev = reviewService.findList(null, g.getId(),null,true,5,null,null,false);
            Iterator<Review> it=setrev.iterator();
            Map<String,Object> m1=null;
            List<Map<String,Object>> revlist=new ArrayList<Map<String,Object>>();
            Review r=null;
            while(it.hasNext()){
                m1=new HashMap<String,Object>();
                r=it.next();
                if(r.getIsShow()){
                    m1.put("id", r.getId());
                    m1.put("content",r.getContent());
                    m1.put("createDate", sim.format(r.getCreateDate()));
                    m1.put("ip", r.getIp());
                    m1.put("score",r.getScore());
                    m1.put("memberId", r.getMember()==null?-1:r.getMember().getId());
                    m1.put("userName", r.getMember()==null?"未登录用户":r.getMember().getUsername());//会员昵称
                    revlist.add(m1);
                }
            }
            m.put("reviews",revlist);//商品评论
            
//            Set<Consultation> setcl=g.getConsultations();//商品咨询
            List<Consultation> consultations = consultationService.findList(null,g.getId(), true,5,null,null, false);
            Iterator<Consultation> it1=consultations.iterator();
            revlist=new ArrayList<Map<String,Object>>();
            Consultation c1=null;
            Set<Consultation> replycon=null;
            while(it1.hasNext()){
                m1=new HashMap<String,Object>();
                c1=it1.next();
                if(c1.getIsShow()){
                    m1.put("id", c1.getId());//评论id
                    m1.put("content",c1.getContent());//内容
                    m1.put("createDate",sim.format(c1.getCreateDate()));//创建日期
                    m1.put("ip", c1.getIp());//ip
                    m1.put("memberId", c1.getMember()==null?-1:c1.getMember().getId());//会员id
                    m1.put("userName",c1.getMember()==null?"未登录用户":c1.getMember().getUsername());//会员昵称
                    replycon=c1.getReplyConsultations();
                    if(replycon!=null){
                        Iterator<Consultation> it2=replycon.iterator();
                        Map<String,Object> m2=null;
                        List<Map<String,Object>> listcon=new ArrayList<Map<String,Object>>();
                        while(it2.hasNext()){
                            m2=new HashMap<String,Object>();
                            c1=it2.next();
                            if(c1.getIsShow()){
                                m2.put("id", c1.getId());//评论id
                                m2.put("content",c1.getContent());//内容
                                m2.put("createDate",sim.format(c1.getCreateDate()));//创建日期
//                                m2.put("isShow", c1.getIsShow());//是否显示
                                m2.put("ip", c1.getIp());//ip
//                                m2.put("memberId", c1.getMember()==null?-1:c1.getMember().getId());//会员id
//                                m2.put("userName",c1.getMember()==null?"游客":c1.getMember().getUsername());//会员昵称
                                listcon.add(m2);
                            }
                        }
                        m1.put("replyConsultations",listcon);
                    }
                    revlist.add(m1);
                }
            }
            
            m.put("consultations",revlist);//商品咨询
            m.put("coordinate",g.getCoordinate());//坐标
            m.put("ctaddress",g.getCtaddress());//联系地址
            m.put("nickname",g.getMemberBiz()==null?"":g.getMemberBiz().getNickname());
//            m.put("mobile",g.getMemberBiz()==null?"":g.getMemberBiz().getMobile());
            m.put("mobile",g.getMobile());
            m.put("sales",g.getSales());//销量
            m.put("companyName",g.getMemberBiz()==null?"":g.getMemberBiz().getCompanyName());
            
            maps.put("good",m);
            msg.setExtrDatas(maps);
            msg.setCode(AppMessageCode.CODE_SUCCESS);
            msg.setMsg(AppMessageCode.MSG_SUCCESS);
        }catch(Exception e){
            msg.setCode(AppMessageCode.CODE_ERROR);
            msg.setMsg(AppMessageCode.MSG_ERROR);
        }
        Gson gson=new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
        return gson.toJson(msg);
	}
	
	@Resource(name = "consultationServiceImpl")
    private ConsultationService consultationService;
}