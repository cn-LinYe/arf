/**   
* @Title: PushServiceImpl.java 
* @author 广智发   
* @date 2015年12月16日 下午4:07:57 
* @version V1.0   
*/
package com.arf.core.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.arf.core.Setting;
import com.arf.core.service.MessageConfigService;
import com.arf.core.service.PushService;
import com.arf.core.service.SmsService;
import com.arf.core.service.TemplateConfigService;
import com.arf.core.util.SettingUtils;

import cn.emay.sdk.client.api.Client;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service - 极光推送
 * 
 * @author arf
 * @version 1.0
 * @Deprecated {@link com.arf.core.oldws.PushUntils}
 */
@Service("pushServiceImpl")
@Deprecated
public class PushServiceImpl implements PushService{
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "templateConfigServiceImpl")
	private TemplateConfigService templateConfigService;
	@Resource(name = "messageConfigServiceImpl")
	private MessageConfigService messageConfigService;
	
	
	private void addSendTask(final String[] alias, final String content, final  int  maxRetryTimes) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				send(alias, content, maxRetryTimes);
			}
		});
	}

	/**
	 * 发送短信
	 * 
	 * @param alias
	 *            手机号码
	 * @param content
	 *            内容
	 * @param maxRetryTimes
	 *            最大重试次数
	 */
	private void send(String[] alias, String content, int  maxRetryTimes) {
		Assert.notEmpty(alias);
		Assert.hasText(content);

		Setting setting = SettingUtils.get();
		String masterSecret = setting.getMasterSecret();
		String appKey = setting.getSmsKey();
		if (StringUtils.isEmpty(masterSecret) || StringUtils.isEmpty(appKey)) {
			return;
		}
		try {
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
			PushPayload pushPayLoad= PushPayload.newBuilder()
             .setPlatform(Platform.all())
             .setAudience(Audience.alias(alias))
             .setNotification(Notification.alert(content))
             .build();
			PushResult result = jpushClient.sendPush(pushPayLoad);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	
	
	public void send(String[] alias, String templatePath, Map<String, Object> model, int maxRetryTimes, boolean async) {
		Assert.notEmpty(alias);
		Assert.hasText(templatePath);

		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			send(alias, content, maxRetryTimes, async);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void send(String[] alias, String content, int  maxRetryTimes, boolean async) {
		Assert.notEmpty(alias);
		Assert.hasText(content);

		if (async) {
			addSendTask(alias, content, maxRetryTimes);
		} else {
			send(alias, content, maxRetryTimes);
		}
	}

	
	@Override
	public void send(String alias, String content) {
		Assert.hasText(alias);
		Assert.hasText(content);
		send(new String[] { alias }, content, 0, true);
		
	}

	
	@Override
	public void send(String alias, String templatePath, Map<String, Object> model) {
		Assert.hasText(alias);
		Assert.hasText(templatePath);
		send(new String[] { alias }, templatePath, model, 0, true);
	}

	
}
