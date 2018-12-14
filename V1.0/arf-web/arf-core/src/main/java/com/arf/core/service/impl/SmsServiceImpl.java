/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.fastjson.JSON;
import com.arf.core.Setting;
import com.arf.core.TemplateConfig;
import com.arf.core.entity.Member;
import com.arf.core.entity.MessageConfig;
import com.arf.core.entity.Order;
import com.arf.core.service.MessageConfigService;
import com.arf.core.service.SmsService;
import com.arf.core.service.TemplateConfigService;
import com.arf.core.util.HttpSender;
import com.arf.core.util.SettingUtils;

import cn.emay.sdk.client.api.Client;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service - 短信
 * 
 * @author arf
 * @version 4.0
 */
@Service("smsServiceImpl")
public class SmsServiceImpl implements SmsService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "templateConfigServiceImpl")
	private TemplateConfigService templateConfigService;
	@Resource(name = "messageConfigServiceImpl")
	private MessageConfigService messageConfigService;

	/**
	 * 添加短信发送任务
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param sendTime
	 *            发送时间
	 */
	private void addSendTask(final String[] mobiles, final String content, final Date sendTime) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				send(mobiles, content, sendTime);
			}
		});
	}

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param sendTime
	 *            发送时间
	 */
	private void send(String[] mobiles, String content, Date sendTime) {
		Assert.notEmpty(mobiles);
		Assert.hasText(content);

		Setting setting = SettingUtils.get();
		String smsSn = setting.getSmsSn();
		String smsKey = setting.getSmsKey();
		if (StringUtils.isEmpty(smsSn) || StringUtils.isEmpty(smsKey)) {
			return;
		}
		try {
			Client client = new Client(smsSn, smsKey);
			if (sendTime != null) {
				client.sendScheduledSMS(mobiles, content, DateFormatUtils.format(sendTime, "yyyyMMddhhmmss"));
			} else {
				client.sendSMS(mobiles, content, 5);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void send(String[] mobiles, String content, Date sendTime, boolean async) {
		Assert.notEmpty(mobiles);
		Assert.hasText(content);
		sendMessage(mobiles, content, sendTime);
//		if (async) {
//			addSendTask(mobiles, content, sendTime);
//		} else {
//			send(mobiles, content, sendTime);
//		}
	}
	/**
	 * 新发送短信接口
	 */
	public void sendMessage(String[] mobiles, String content, Date sendTime){
//	    String uri = "http://222.73.117.158/msg/main.do";// 应用地址
	    String uri = "http://222.73.117.156/msg/index.jsp";// 应用地址
        String account = "ARFZN888";// 账号
        String pswd = "Admin888";// 密码
        String ms="";
        for(int i=0;i<mobiles.length;i++){
            ms+=mobiles[i];
            if(i<mobiles.length-1){
                ms+=",";
            }
        }
        boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
        String product="";// 产品ID
        String extno="";// 扩展码
        try {
            String returnString = HttpSender.batchSend(uri, account, pswd, ms, content, needstatus, product, extno);
            logger.info("短信发送结果:(" + returnString + ")" + ",mobile=" + JSON.toJSONString(mobiles) + ",content=" + content);
            // TODO 处理返回值,参见HTTP协议文档
        } catch (Exception e) {
        	logger.info("短信发送异常: mobile=" + JSON.toJSONString(mobiles) + ",content=" + content,e);
        }
	}

	@Override
	public void send(String[] mobiles, String templatePath, Map<String, Object> model, Date sendTime, boolean async) {
		Assert.notEmpty(mobiles);
		Assert.hasText(templatePath);

		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			send(mobiles, content, sendTime, async);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void send(String mobile, String content) {
		Assert.hasText(mobile);
		Assert.hasText(content);

		send(new String[] { mobile }, content, null, true);
	}

	@Override
	public void send(String mobile, String templatePath, Map<String, Object> model) {
		Assert.hasText(mobile);
		Assert.hasText(templatePath);

		send(new String[] { mobile }, templatePath, model, null, true);
	}

	@Override
	public void sendRegisterMemberSms(Member member) {
		if (member == null || StringUtils.isEmpty(member.getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.registerMember);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("member", member);
		TemplateConfig templateConfig = templateConfigService.get("registerMemberSms");
		send(member.getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendCreateOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.createOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("createOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendUpdateOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.updateOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("updateOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendCancelOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.cancelOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("cancelOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendReviewOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.reviewOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("reviewOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendPaymentOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.paymentOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("paymentOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendRefundsOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.refundsOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("refundsOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendShippingOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.shippingOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("shippingOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendReturnsOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.returnsOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("returnsOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendReceiveOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.receiveOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("receiveOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendCompleteOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.completeOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("completeOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public void sendFailOrderSms(Order order) {
		if (order == null || order.getMember() == null || StringUtils.isEmpty(order.getMember().getMobile())) {
			return;
		}
		MessageConfig messageConfig = messageConfigService.find(MessageConfig.Type.failOrder);
		if (messageConfig == null || !messageConfig.getIsSmsEnabled()) {
			return;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", order);
		TemplateConfig templateConfig = templateConfigService.get("failOrderSms");
		send(order.getMember().getMobile(), templateConfig.getRealTemplatePath(), model);
	}

	@Override
	public long getBalance() {
		Setting setting = SettingUtils.get();
		String smsSn = setting.getSmsSn();
		String smsKey = setting.getSmsKey();
		if (StringUtils.isEmpty(smsSn) || StringUtils.isEmpty(smsKey)) {
			return -1L;
		}
		try {
			Client client = new Client(smsSn, smsKey);
			double result = client.getBalance();
			if (result >= 0) {
				return (long) (result * 10);
			}
		} catch (Exception e) {
		}
		return -1L;
	}

	@Override
	public void sendAll(String mobile, String contents) {
		if (StringUtils.isBlank(mobile)||StringUtils.isBlank(contents)) {
			return;
		}

//		String uri = "http://222.73.117.158/msg/main.do";// 应用地址
		String uri = "http://222.73.117.156/msg/index.jsp";// 应用地址
		String account = "ARFZN888";// 账号
		String pswd = "Admin888";// 密码
		String mobiles = mobile;// 手机号码，多个号码使用","分割
		String content =   contents ;// 短信内容
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String product = "";// 产品ID
		String extno = "";// 扩展码
		try {
			String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno);
			logger.info("短信发送结果:(" + returnString + ")" + ",mobile=" + mobile + ",content=" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String returnString = HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product,extno);
			logger.info("短信发送结果:(" + returnString + ")" + ",mobile=" + mobile + ",content=" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

}