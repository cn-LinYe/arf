/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.plugin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.arf.core.Setting;
import com.arf.core.entity.PaymentLog;
import com.arf.core.entity.PluginConfig;
import com.arf.core.service.PaymentLogService;
import com.arf.core.service.PluginConfigService;
import com.arf.core.util.SettingUtils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

/**
 * Plugin - 支付
 * 
 * @author arf
 * @version 4.0
 */
public abstract class PaymentPlugin implements Comparable<PaymentPlugin> {

	/** "支付方式名称"属性名称 */
	public static final String PAYMENT_NAME_ATTRIBUTE_NAME = "paymentName";

	/** "手续费类型"属性名称 */
	public static final String FEE_TYPE_ATTRIBUTE_NAME = "feeType";

	/** "手续费"属性名称 */
	public static final String FEE_ATTRIBUTE_NAME = "fee";

	/** "LOGO"属性名称 */
	public static final String LOGO_ATTRIBUTE_NAME = "logo";

	/** "描述"属性名称 */
	public static final String DESCRIPTION_ATTRIBUTE_NAME = "description";

	/**
	 * 手续费类型
	 */
	public enum FeeType {

		/** 按比例收费 */
		scale,

		/** 固定收费 */
		fixed
	}

	/**
	 * 请求方法
	 */
	public enum RequestMethod {

		/** POST */
		post,

		/** GET */
		get
	}

	/**
	 * 通知方法
	 */
	public enum NotifyMethod {

		/** 通用 */
		general,

		/** 同步 */
		sync,

		/** 异步 */
		async
	}

	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;
	@Resource(name = "paymentLogServiceImpl")
	private PaymentLogService paymentLogService;

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public final String getId() {
		return getClass().getAnnotation(Component.class).value();
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public abstract String getName();

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	public abstract String getVersion();

	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	public abstract String getAuthor();

	/**
	 * 获取网址
	 * 
	 * @return 网址
	 */
	public abstract String getSiteUrl();

	/**
	 * 获取安装URL
	 * 
	 * @return 安装URL
	 */
	public abstract String getInstallUrl();

	/**
	 * 获取卸载URL
	 * 
	 * @return 卸载URL
	 */
	public abstract String getUninstallUrl();

	/**
	 * 获取设置URL
	 * 
	 * @return 设置URL
	 */
	public abstract String getSettingUrl();

	/**
	 * 获取是否已安装
	 * 
	 * @return 是否已安装
	 */
	public boolean getIsInstalled() {
		return pluginConfigService.pluginIdExists(getId());
	}

	/**
	 * 获取插件配置
	 * 
	 * @return 插件配置
	 */
	public PluginConfig getPluginConfig() {
		return pluginConfigService.findByPluginId(getId());
	}

	/**
	 * 获取是否已启用
	 * 
	 * @return 是否已启用
	 */
	public boolean getIsEnabled() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getIsEnabled() : false;
	}

	/**
	 * 获取属性值
	 * 
	 * @param name
	 *            属性名称
	 * @return 属性值
	 */
	public String getAttribute(String name) {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(name) : null;
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public Integer getOrder() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getOrder() : null;
	}

	/**
	 * 获取支付方式名称
	 * 
	 * @return 支付方式名称
	 */
	public String getPaymentName() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(PAYMENT_NAME_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取手续费类型
	 * 
	 * @return 手续费类型
	 */
	public FeeType getFeeType() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? FeeType.valueOf(pluginConfig.getAttribute(FEE_TYPE_ATTRIBUTE_NAME)) : null;
	}

	/**
	 * 获取手续费
	 * 
	 * @return 手续费
	 */
	public BigDecimal getFee() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? new BigDecimal(pluginConfig.getAttribute(FEE_ATTRIBUTE_NAME)) : null;
	}

	/**
	 * 获取LOGO
	 * 
	 * @return LOGO
	 */
	public String getLogo() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(LOGO_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取描述
	 * 
	 * @return 描述
	 */
	public String getDescription() {
		PluginConfig pluginConfig = getPluginConfig();
		return pluginConfig != null ? pluginConfig.getAttribute(DESCRIPTION_ATTRIBUTE_NAME) : null;
	}

	/**
	 * 获取请求URL
	 * 
	 * @return 请求URL
	 */
	public abstract String getRequestUrl();

	/**
	 * 获取请求方法
	 * 
	 * @return 请求方法
	 */
	public abstract RequestMethod getRequestMethod();

	/**
	 * 获取请求字符编码
	 * 
	 * @return 请求字符编码
	 */
	public abstract String getRequestCharset();

	/**
	 * 获取请求参数
	 * 
	 * @param sn
	 *            编号
	 * @param description
	 *            描述
	 * @param request
	 *            httpServletRequest
	 * @return 请求参数
	 */
	public abstract Map<String, Object> getParameterMap(String sn, String description, HttpServletRequest request);

	/**
	 * 验证通知是否合法
	 * 
	 * @param notifyMethod
	 *            通知方法
	 * @param request
	 *            httpServletRequest
	 * @return 通知是否合法
	 */
	public abstract boolean verifyNotify(NotifyMethod notifyMethod, HttpServletRequest request);

	/**
	 * 获取编号
	 * 
	 * @param request
	 *            httpServletRequest
	 * @return 编号
	 */
	public abstract String getSn(HttpServletRequest request);

	/**
	 * 获取通知返回消息
	 * 
	 * @param notifyMethod
	 *            通知方法
	 * @param request
	 *            httpServletRequest
	 * @return 通知返回消息
	 */
	public abstract String getNotifyMessage(NotifyMethod notifyMethod, HttpServletRequest request);

	/**
	 * 计算支付手续费
	 * 
	 * @param amount
	 *            金额
	 * @return 支付手续费
	 */
	public BigDecimal calculateFee(BigDecimal amount) {
		Setting setting = SettingUtils.get();
		if (PaymentPlugin.FeeType.scale.equals(getFeeType())) {
			return setting.setScale(amount.multiply(getFee()));
		} else {
			return setting.setScale(getFee());
		}
	}

	/**
	 * 计算支付金额
	 * 
	 * @param amount
	 *            金额
	 * @return 支付金额
	 */
	public BigDecimal calculateAmount(BigDecimal amount) {
		return amount.add(calculateFee(amount)).setScale(2, RoundingMode.UP);
	}

	/**
	 * 根据编号查找支付记录
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	protected PaymentLog getPaymentLog(String sn) {
		return paymentLogService.findBySn(sn);
	}

	/**
	 * 获取通知URL
	 * 
	 * @param notifyMethod
	 *            通知方法
	 * @return 通知URL
	 */
	protected String getNotifyUrl(NotifyMethod notifyMethod) {
		Setting setting = SettingUtils.get();
		if (notifyMethod != null) {
			return setting.getSiteUrl() + "/payment/plugin_notify/" + getId() + "/" + notifyMethod + ".jhtml";
		} else {
			return setting.getSiteUrl() + "/payment/plugin_notify/" + getId() + "/" + NotifyMethod.general + ".jhtml";
		}
	}

	/**
	 * 连接Map键值对
	 * 
	 * @param map
	 *            Map
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @param separator
	 *            连接符
	 * @param ignoreEmptyValue
	 *            忽略空值
	 * @param ignoreKeys
	 *            忽略Key
	 * @return 字符串
	 */
	protected String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
		List<String> list = new ArrayList<String>();
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = ConvertUtils.convert(entry.getValue());
				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
					list.add(key + "=" + (value != null ? value : ""));
				}
			}
		}
		return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
	}

	/**
	 * 连接Map值
	 * 
	 * @param map
	 *            Map
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @param separator
	 *            连接符
	 * @param ignoreEmptyValue
	 *            忽略空值
	 * @param ignoreKeys
	 *            忽略Key
	 * @return 字符串
	 */
	protected String joinValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
		List<String> list = new ArrayList<String>();
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = ConvertUtils.convert(entry.getValue());
				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
					list.add(value != null ? value : "");
				}
			}
		}
		return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PaymentPlugin other = (PaymentPlugin) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}

	public int compareTo(PaymentPlugin paymentPlugin) {
		return new CompareToBuilder().append(getOrder(), paymentPlugin.getOrder()).append(getId(), paymentPlugin.getId()).toComparison();
	}

}