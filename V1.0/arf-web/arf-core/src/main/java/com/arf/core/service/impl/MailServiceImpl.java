/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.Setting;
import com.arf.core.TemplateConfig;
import com.arf.core.entity.Member;
import com.arf.core.entity.MessageConfig;
import com.arf.core.entity.Order;
import com.arf.core.entity.ProductNotify;
import com.arf.core.entity.SafeKey;
import com.arf.core.service.MailService;
import com.arf.core.service.MessageConfigService;
import com.arf.core.service.TemplateConfigService;
import com.arf.core.util.SettingUtils;
import com.arf.core.util.SpringUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service - 邮件
 * 
 * @author arf
 * @version 4.0
 */
@Service("mailServiceImpl")
public class MailServiceImpl implements MailService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "templateConfigServiceImpl")
	private TemplateConfigService templateConfigService;
	@Resource(name = "messageConfigServiceImpl")
	private MessageConfigService messageConfigService;

	/**
	 * 添加邮件发送任务
	 * 
	 * @param smtpHost
	 *            SMTP服务器地址
	 * @param smtpPort
	 *            SMTP服务器端口
	 * @param smtpUsername
	 *            SMTP用户名
	 * @param smtpPassword
	 *            SMTP密码
	 * @param smtpSSLEnabled
	 *            SMTP是否启用SSL
	 * @param smtpFromMail
	 *            发件人邮箱
	 * @param toMails
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	private void addSendTask(final String smtpHost, final int smtpPort, final String smtpUsername, final String smtpPassword, final boolean smtpSSLEnabled, final String smtpFromMail, final String[] toMails, final String subject, final String content) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content);
			}
		});
	}

	/**
	 * 发送邮件
	 * 
	 * @param smtpHost
	 *            SMTP服务器地址
	 * @param smtpPort
	 *            SMTP服务器端口
	 * @param smtpUsername
	 *            SMTP用户名
	 * @param smtpPassword
	 *            SMTP密码
	 * @param smtpSSLEnabled
	 *            SMTP是否启用SSL
	 * @param smtpFromMail
	 *            发件人邮箱
	 * @param toMails
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	private void send(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String[] toMails, String subject, String content) {
		Assert.hasText(smtpHost);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(smtpFromMail);
		Assert.notEmpty(toMails);
		Assert.hasText(subject);
		Assert.hasText(content);

		try {
			Setting setting = SettingUtils.get();
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpHost);
			email.setSmtpPort(smtpPort);
			email.setAuthentication(smtpUsername, smtpPassword);
			email.setSSLOnConnect(smtpSSLEnabled);
			email.setFrom(smtpFromMail, setting.getSiteName());
			email.addTo(toMails);
			email.setSubject(subject);
			email.setCharset("UTF-8");
			email.setHtmlMsg(content);
			email.send();
		} catch (EmailException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void send(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String[] toMails, String subject, String content, boolean async) {
		Assert.hasText(smtpHost);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(smtpFromMail);
		Assert.notEmpty(toMails);
		Assert.hasText(subject);
		Assert.hasText(content);

		if (async) {
			addSendTask(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content);
		} else {
			send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content);
		}
	}

	public void send(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String[] toMails, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Assert.hasText(smtpHost);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(smtpFromMail);
		Assert.notEmpty(toMails);
		Assert.hasText(subject);
		Assert.hasText(templatePath);

		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content, async);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void send(String[] toMails, String subject, String content, boolean async) {
		Assert.notEmpty(toMails);
		Assert.hasText(subject);
		Assert.hasText(content);

		Setting setting = SettingUtils.get();
		String smtpHost = setting.getSmtpHost();
		int smtpPort = setting.getSmtpPort();
		String smtpUsername = setting.getSmtpUsername();
		String smtpPassword = setting.getSmtpPassword();
		boolean smtpSSLEnabled = setting.getSmtpSSLEnabled();
		String smtpFromMail = setting.getSmtpFromMail();
		send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, content, async);
	}

	public void send(String[] toMails, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Assert.notEmpty(toMails);
		Assert.hasText(subject);
		Assert.hasText(templatePath);

		Setting setting = SettingUtils.get();
		String smtpHost = setting.getSmtpHost();
		int smtpPort = setting.getSmtpPort();
		String smtpUsername = setting.getSmtpUsername();
		String smtpPassword = setting.getSmtpPassword();
		boolean smtpSSLEnabled = setting.getSmtpSSLEnabled();
		String smtpFromMail = setting.getSmtpFromMail();
		send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, toMails, subject, templatePath, model, async);
	}

	public void send(String toMail, String subject, String content) {
		Assert.hasText(toMail);
		Assert.hasText(subject);
		Assert.hasText(content);

		send(new String[] { toMail }, subject, content, true);
	}

	public void send(String toMail, String subject, String templatePath, Map<String, Object> model) {
		Assert.hasText(toMail);
		Assert.hasText(subject);
		Assert.hasText(templatePath);

		send(new String[] { toMail }, subject, templatePath, model, true);
	}

	public void sendTestSmtpMail(String smtpHost, int smtpPort, String smtpUsername, String smtpPassword, boolean smtpSSLEnabled, String smtpFromMail, String toMail) {
		Assert.hasText(smtpHost);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(smtpFromMail);
		Assert.hasText(toMail);

		Setting setting = SettingUtils.get();
		String subject = SpringUtils.getMessage("admin.mail.testSmtpSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("testSmtpMail");
		send(smtpHost, smtpPort, smtpUsername, smtpPassword, smtpSSLEnabled, smtpFromMail, new String[] { toMail }, subject, templateConfig.getRealTemplatePath(), null, false);
	}

	public void sendFindPasswordMail(String toMail, String username, SafeKey safeKey) {
		if (StringUtils.isEmpty(toMail) || StringUtils.isEmpty(username) || safeKey == null) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", username);
		model.put("safeKey", safeKey);
		String subject = SpringUtils.getMessage("shop.mail.findPasswordSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("findPasswordMail");
		send(toMail, subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendProductNotifyMail(ProductNotify productNotify) {
		if (productNotify == null || StringUtils.isEmpty(productNotify.getEmail())) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productNotify", productNotify);
		String subject = SpringUtils.getMessage("shop.mail.productNotifySubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("productNotifyMail");
		send(productNotify.getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendRegisterMemberMail(Member member) {
		if (member == null || StringUtils.isEmpty(member.getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.registerMember);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("member", member);
		String subject = SpringUtils.getMessage("shop.mail.registerMemberSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("registerMemberMail");
		send(member.getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendCreateOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.createOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.createOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("createOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendUpdateOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.updateOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.updateOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("updateOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendCancelOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.cancelOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.cancelOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("cancelOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendReviewOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.reviewOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.reviewOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("reviewOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendPaymentOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.paymentOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.paymentOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("paymentOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendRefundsOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.refundsOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.refundsOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("refundsOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendShippingOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.shippingOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.shippingOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("shippingOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendReturnsOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.returnsOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.returnsOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("returnsOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendReceiveOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.receiveOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.receiveOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("receiveOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendCompleteOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.completeOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.completeOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("completeOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

	public void sendFailOrderMail(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getEmail())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.failOrder);
		if (messageConfig == null || !messageConfig.getIsMailEnabled()) {
			return;
		}
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		String subject = SpringUtils.getMessage("shop.mail.failOrderSubject", setting.getSiteName());
		TemplateConfig templateConfig = templateConfigService.get("failOrderMail");
		send(order.getMember().getEmail(), subject, templateConfig.getRealTemplatePath(), model);
	}

}