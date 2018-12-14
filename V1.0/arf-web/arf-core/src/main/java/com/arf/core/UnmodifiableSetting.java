/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

import java.io.Serializable;

import com.arf.core.Setting.AccountLockType;
import com.arf.core.Setting.CaptchaType;
import com.arf.core.Setting.ConsultationAuthority;
import com.arf.core.Setting.Locale;
import com.arf.core.Setting.ReviewAuthority;
import com.arf.core.Setting.RoundType;
import com.arf.core.Setting.StockAllocationTime;
import com.arf.core.Setting.WatermarkPosition;

import org.springframework.util.Assert;

/**
 * 不可变系统设置
 * 
 * @author arf
 * @version 4.0
 */
public final class UnmodifiableSetting implements Serializable {

	private static final long serialVersionUID = -83392368812436653L;

	private final Setting setting;

	public UnmodifiableSetting(Setting setting) {
		Assert.notNull(setting);

		this.setting = setting;
	}

	/**
	 * 获取网站名称
	 * 
	 * @return 网站名称
	 */
	public String getSiteName() {
		return setting.getSiteName();
	}

	/**
	 * 获取网站网址
	 * 
	 * @return 网站网址
	 */
	public String getSiteUrl() {
		return setting.getSiteUrl();
	}

	/**
	 * 获取logo
	 * 
	 * @return logo
	 */
	public String getLogo() {
		return setting.getLogo();
	}

	/**
	 * 获取热门搜索
	 * 
	 * @return 热门搜索
	 */
	public String getHotSearch() {
		return setting.getHotSearch();
	}

	/**
	 * 获取联系地址
	 * 
	 * @return 联系地址
	 */
	public String getAddress() {
		return setting.getAddress();
	}

	/**
	 * 获取联系电话
	 * 
	 * @return 联系电话
	 */
	public String getPhone() {
		return setting.getPhone();
	}

	/**
	 * 获取邮政编码
	 * 
	 * @return 邮政编码
	 */
	public String getZipCode() {
		return setting.getZipCode();
	}

	/**
	 * 获取E-mail
	 * 
	 * @return E-mail
	 */
	public String getEmail() {
		return setting.getEmail();
	}

	/**
	 * 获取备案编号
	 * 
	 * @return 备案编号
	 */
	public String getCerttext() {
		return setting.getCerttext();
	}

	/**
	 * 获取是否网站开启
	 * 
	 * @return 是否网站开启
	 */
	public Boolean getIsSiteEnabled() {
		return setting.getIsSiteEnabled();
	}

	/**
	 * 获取网站关闭消息
	 * 
	 * @return 网站关闭消息
	 */
	public String getSiteCloseMessage() {
		return setting.getSiteCloseMessage();
	}

	/**
	 * 获取商品图片(大)宽度
	 * 
	 * @return 商品图片(大)宽度
	 */
	public Integer getLargeProductImageWidth() {
		return setting.getLargeProductImageWidth();
	}

	/**
	 * 获取商品图片(大)高度
	 * 
	 * @return 商品图片(大)高度
	 */
	public Integer getLargeProductImageHeight() {
		return setting.getLargeProductImageHeight();
	}

	/**
	 * 获取商品图片(中)宽度
	 * 
	 * @return 商品图片(中)宽度
	 */
	public Integer getMediumProductImageWidth() {
		return setting.getMediumProductImageWidth();
	}

	/**
	 * 获取商品图片(中)高度
	 * 
	 * @return 商品图片(中)高度
	 */
	public Integer getMediumProductImageHeight() {
		return setting.getMediumProductImageHeight();
	}

	/**
	 * 获取商品缩略图宽度
	 * 
	 * @return 商品缩略图宽度
	 */
	public Integer getThumbnailProductImageWidth() {
		return setting.getThumbnailProductImageWidth();
	}

	/**
	 * 获取商品缩略图高度
	 * 
	 * @return 商品缩略图高度
	 */
	public Integer getThumbnailProductImageHeight() {
		return setting.getThumbnailProductImageHeight();
	}

	/**
	 * 获取默认商品图片(大)
	 * 
	 * @return 默认商品图片(大)
	 */
	public String getDefaultLargeProductImage() {
		return setting.getDefaultLargeProductImage();
	}

	/**
	 * 获取默认商品图片(小)
	 * 
	 * @return 默认商品图片(小)
	 */
	public String getDefaultMediumProductImage() {
		return setting.getDefaultMediumProductImage();
	}

	/**
	 * 获取默认缩略图
	 * 
	 * @return 默认缩略图
	 */
	public String getDefaultThumbnailProductImage() {
		return setting.getDefaultThumbnailProductImage();
	}

	/**
	 * 获取水印透明度
	 * 
	 * @return 水印透明度
	 */
	public Integer getWatermarkAlpha() {
		return setting.getWatermarkAlpha();
	}

	/**
	 * 获取水印图片
	 * 
	 * @return 水印图片
	 */
	public String getWatermarkImage() {
		return setting.getWatermarkImage();
	}

	/**
	 * 获取水印位置
	 * 
	 * @return 水印位置
	 */
	public WatermarkPosition getWatermarkPosition() {
		return setting.getWatermarkPosition();
	}

	/**
	 * 获取价格精确位数
	 * 
	 * @return 价格精确位数
	 */
	public Integer getPriceScale() {
		return setting.getPriceScale();
	}

	/**
	 * 获取价格精确方式
	 * 
	 * @return 价格精确方式
	 */
	public RoundType getPriceRoundType() {
		return setting.getPriceRoundType();
	}

	/**
	 * 获取是否前台显示市场价
	 * 
	 * @return 是否前台显示市场价
	 */
	public Boolean getIsShowMarketPrice() {
		return setting.getIsShowMarketPrice();
	}

	/**
	 * 获取默认市场价换算比例
	 * 
	 * @return 默认市场价换算比例
	 */
	public Double getDefaultMarketPriceScale() {
		return setting.getDefaultMarketPriceScale();
	}

	/**
	 * 获取是否开放注册
	 * 
	 * @return 是否开放注册
	 */
	public Boolean getIsRegisterEnabled() {
		return setting.getIsRegisterEnabled();
	}

	/**
	 * 获取是否允许E-mail重复注册
	 * 
	 * @return 是否允许E-mail重复注册
	 */
	public Boolean getIsDuplicateEmail() {
		return setting.getIsDuplicateEmail();
	}

	/**
	 * 获取禁用用户名
	 * 
	 * @return 禁用用户名
	 */
	public String getDisabledUsername() {
		return setting.getDisabledUsername();
	}

	/**
	 * 获取用户名最小长度
	 * 
	 * @return 用户名最小长度
	 */
	public Integer getUsernameMinLength() {
		return setting.getUsernameMinLength();
	}

	/**
	 * 获取用户名最大长度
	 * 
	 * @return 用户名最大长度
	 */
	public Integer getUsernameMaxLength() {
		return setting.getUsernameMaxLength();
	}

	/**
	 * 获取密码最小长度
	 * 
	 * @return 密码最小长度
	 */
	public Integer getPasswordMinLength() {
		return setting.getPasswordMinLength();
	}

	/**
	 * 获取密码最大长度
	 * 
	 * @return 密码最大长度
	 */
	public Integer getPasswordMaxLength() {
		return setting.getPasswordMaxLength();
	}

	/**
	 * 获取注册初始积分
	 * 
	 * @return 注册初始积分
	 */
	public Long getRegisterPoint() {
		return setting.getRegisterPoint();
	}

	/**
	 * 获取注册协议
	 * 
	 * @return 注册协议
	 */
	public String getRegisterAgreement() {
		return setting.getRegisterAgreement();
	}

	/**
	 * 获取是否允许E-mail登录
	 * 
	 * @return 是否允许E-mail登录
	 */
	public Boolean getIsEmailLogin() {
		return setting.getIsEmailLogin();
	}

	/**
	 * 获取验证码类型
	 * 
	 * @return 验证码类型
	 */
	public CaptchaType[] getCaptchaTypes() {
		return setting.getCaptchaTypes();
	}

	/**
	 * 获取账号锁定类型
	 * 
	 * @return 账号锁定类型
	 */
	public AccountLockType[] getAccountLockTypes() {
		return setting.getAccountLockTypes();
	}

	/**
	 * 获取连续登录失败最大次数
	 * 
	 * @return 连续登录失败最大次数
	 */
	public Integer getAccountLockCount() {
		return setting.getAccountLockCount();
	}

	/**
	 * 获取自动解锁时间
	 * 
	 * @return 自动解锁时间
	 */
	public Integer getAccountLockTime() {
		return setting.getAccountLockTime();
	}

	/**
	 * 获取安全密匙有效时间
	 * 
	 * @return 安全密匙有效时间
	 */
	public Integer getSafeKeyExpiryTime() {
		return setting.getSafeKeyExpiryTime();
	}

	/**
	 * 获取上传文件最大限制
	 * 
	 * @return 上传文件最大限制
	 */
	public Integer getUploadMaxSize() {
		return setting.getUploadMaxSize();
	}

	/**
	 * 获取允许上传图片扩展名
	 * 
	 * @return 允许上传图片扩展名
	 */
	public String getUploadImageExtension() {
		return setting.getUploadImageExtension();
	}

	/**
	 * 获取允许上传Flash扩展名
	 * 
	 * @return 允许上传Flash扩展名
	 */
	public String getUploadFlashExtension() {
		return setting.getUploadFlashExtension();
	}

	/**
	 * 获取允许上传媒体扩展名
	 * 
	 * @return 允许上传媒体扩展名
	 */
	public String getUploadMediaExtension() {
		return setting.getUploadMediaExtension();
	}

	/**
	 * 获取允许上传文件扩展名
	 * 
	 * @return 允许上传文件扩展名
	 */
	public String getUploadFileExtension() {
		return setting.getUploadFileExtension();
	}

	/**
	 * 获取图片上传路径
	 * 
	 * @return 图片上传路径
	 */
	public String getImageUploadPath() {
		return setting.getImageUploadPath();
	}

	/**
	 * 获取Flash上传路径
	 * 
	 * @return Flash上传路径
	 */
	public String getFlashUploadPath() {
		return setting.getFlashUploadPath();
	}

	/**
	 * 获取媒体上传路径
	 * 
	 * @return 媒体上传路径
	 */
	public String getMediaUploadPath() {
		return setting.getMediaUploadPath();
	}

	/**
	 * 获取文件上传路径
	 * 
	 * @return 文件上传路径
	 */
	public String getFileUploadPath() {
		return setting.getFileUploadPath();
	}

	/**
	 * 获取SMTP服务器地址
	 * 
	 * @return SMTP服务器地址
	 */
	public String getSmtpHost() {
		return setting.getSmtpHost();
	}

	/**
	 * 获取SMTP服务器端口
	 * 
	 * @return SMTP服务器端口
	 */
	public Integer getSmtpPort() {
		return setting.getSmtpPort();
	}

	/**
	 * 获取SMTP用户名
	 * 
	 * @return SMTP用户名
	 */
	public String getSmtpUsername() {
		return setting.getSmtpUsername();
	}

	/**
	 * 获取SMTP密码
	 * 
	 * @return SMTP密码
	 */
	public String getSmtpPassword() {
		return setting.getSmtpPassword();
	}

	/**
	 * 获取SMTP是否启用SSL
	 * 
	 * @return SMTP是否启用SSL
	 */
	public Boolean getSmtpSSLEnabled() {
		return setting.getSmtpSSLEnabled();
	}

	/**
	 * 获取发件人邮箱
	 * 
	 * @return 发件人邮箱
	 */
	public String getSmtpFromMail() {
		return setting.getSmtpFromMail();
	}

	/**
	 * 获取货币符号
	 * 
	 * @return 货币符号
	 */
	public String getCurrencySign() {
		return setting.getCurrencySign();
	}

	/**
	 * 获取货币单位
	 * 
	 * @return 货币单位
	 */
	public String getCurrencyUnit() {
		return setting.getCurrencyUnit();
	}

	/**
	 * 获取库存警告数
	 * 
	 * @return 库存警告数
	 */
	public Integer getStockAlertCount() {
		return setting.getStockAlertCount();
	}

	/**
	 * 获取库存分配时间点
	 * 
	 * @return 库存分配时间点
	 */
	public StockAllocationTime getStockAllocationTime() {
		return setting.getStockAllocationTime();
	}

	/**
	 * 获取默认积分换算比例
	 * 
	 * @return 默认积分换算比例
	 */
	public Double getDefaultPointScale() {
		return setting.getDefaultPointScale();
	}

	/**
	 * 获取是否开启开发模式
	 * 
	 * @return 是否开启开发模式
	 */
	public Boolean getIsDevelopmentEnabled() {
		return setting.getIsDevelopmentEnabled();
	}

	/**
	 * 获取是否开启评论
	 * 
	 * @return 是否开启评论
	 */
	public Boolean getIsReviewEnabled() {
		return setting.getIsReviewEnabled();
	}

	/**
	 * 获取是否审核评论
	 * 
	 * @return 是否审核评论
	 */
	public Boolean getIsReviewCheck() {
		return setting.getIsReviewCheck();
	}

	/**
	 * 获取评论权限
	 * 
	 * @return 评论权限
	 */
	public ReviewAuthority getReviewAuthority() {
		return setting.getReviewAuthority();
	}

	/**
	 * 获取是否开启咨询
	 * 
	 * @return 是否开启咨询
	 */
	public Boolean getIsConsultationEnabled() {
		return setting.getIsConsultationEnabled();
	}

	/**
	 * 获取是否审核咨询
	 * 
	 * @return 是否审核咨询
	 */
	public Boolean getIsConsultationCheck() {
		return setting.getIsConsultationCheck();
	}

	/**
	 * 获取咨询权限
	 * 
	 * @return 咨询权限
	 */
	public ConsultationAuthority getConsultationAuthority() {
		return setting.getConsultationAuthority();
	}

	/**
	 * 获取是否开启发票功能
	 * 
	 * @return 是否开启发票功能
	 */
	public Boolean getIsInvoiceEnabled() {
		return setting.getIsInvoiceEnabled();
	}

	/**
	 * 获取是否开启含税价
	 * 
	 * @return 是否开启含税价
	 */
	public Boolean getIsTaxPriceEnabled() {
		return setting.getIsTaxPriceEnabled();
	}

	/**
	 * 获取税率
	 * 
	 * @return 税率
	 */
	public Double getTaxRate() {
		return setting.getTaxRate();
	}

	/**
	 * 获取Cookie路径
	 * 
	 * @return Cookie路径
	 */
	public String getCookiePath() {
		return setting.getCookiePath();
	}

	/**
	 * 获取Cookie作用域
	 * 
	 * @return Cookie作用域
	 */
	public String getCookieDomain() {
		return setting.getCookieDomain();
	}

	/**
	 * 获取快递100授权KEY
	 * 
	 * @return 快递100授权KEY
	 */
	public String getKuaidi100Key() {
		return setting.getKuaidi100Key();
	}

	/**
	 * 获取是否开启CNZZ统计
	 * 
	 * @return 是否开启CNZZ统计
	 */
	public Boolean getIsCnzzEnabled() {
		return setting.getIsCnzzEnabled();
	}

	/**
	 * 获取CNZZ统计站点ID
	 * 
	 * @return CNZZ统计站点ID
	 */
	public String getCnzzSiteId() {
		return setting.getCnzzSiteId();
	}

	/**
	 * 获取CNZZ统计密码
	 * 
	 * @return CNZZ统计密码
	 */
	public String getCnzzPassword() {
		return setting.getCnzzPassword();
	}

	/**
	 * 获取短信服务序列号
	 * 
	 * @return 短信服务序列号
	 */
	public String getSmsSn() {
		return setting.getSmsSn();
	}

	/**
	 * 获取短信服务密钥
	 * 
	 * @return 短信服务密钥
	 */
	public String getSmsKey() {
		return setting.getSmsKey();
	}

	/**
	 * 获取区域设置
	 * 
	 * @return 区域设置
	 */
	public Locale getLocale() {
		return setting.getLocale();
	}

	/**
	 * 获取主题
	 * 
	 * @return 主题
	 */
	public String getTheme() {
		return setting.getTheme();
	}

	/**
	 * 获取热门搜索关键词
	 * 
	 * @return 热门搜索关键词
	 */
	public String[] getHotSearches() {
		return setting.getHotSearches();
	}

	/**
	 * 获取禁用用户名
	 * 
	 * @return 禁用用户名
	 */
	public String[] getDisabledUsernames() {
		return setting.getDisabledUsernames();
	}

	/**
	 * 获取允许上传图片扩展名
	 * 
	 * @return 允许上传图片扩展名
	 */
	public String[] getUploadImageExtensions() {
		return setting.getUploadImageExtensions();
	}

	/**
	 * 获取允许上传Flash扩展名
	 * 
	 * @return 允许上传Flash扩展名
	 */
	public String[] getUploadFlashExtensions() {
		return setting.getUploadFlashExtensions();
	}

	/**
	 * 获取允许上传媒体扩展名
	 * 
	 * @return 允许上传媒体扩展名
	 */
	public String[] getUploadMediaExtensions() {
		return setting.getUploadMediaExtensions();
	}

	/**
	 * 获取允许上传文件扩展名
	 * 
	 * @return 允许上传文件扩展名
	 */
	public String[] getUploadFileExtensions() {
		return setting.getUploadFileExtensions();
	}

}