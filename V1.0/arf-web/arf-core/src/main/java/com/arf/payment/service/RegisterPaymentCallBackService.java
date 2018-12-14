package com.arf.payment.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.arf.core.AppMessage;
import com.arf.core.AppMessageCode;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.OrderNumPrefixConstraint;
import com.arf.payment.WeixinPaymentCallBackData;
import com.arf.thirdlogin.service.ThirdPartyAccountService;

/**
 * 支付回调接口注册类,所有实现了{@link com.arf.payment.service.PaymentCallbackService}的实现类
 * 都需要在其实例化时将其注册到
 * {@link com.arf.payment.service.RegisterPaymentCallBackService.registerService}
 * 中来 具体注册示例代码:
 * @Override public void onApplicationEvent(ContextRefreshedEvent event) {
 * 				if (event.getApplicationContext().getParent() == null) {
 *           		//注册本service为支付回调业务逻辑
 *           		RegisterPaymentCallBackService.registerService(OrderNumPrefixConstraint.PREFIX_CARBRIGHTER_PKG_BUY, getClass()); 
 *           	}
 *           }
 * @author Caolibao 2016年7月25日 下午12:00:32
 *
 */
@Component("registerPaymentCallBackService")
public class RegisterPaymentCallBackService implements ServletContextAware{
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final Map<String,Class<? extends PaymentCallbackService>> tranactionServicesMap = new ConcurrentHashMap<String,Class<? extends PaymentCallbackService>>();
	
	private ServletContext servletContext;
	
	@Resource(name="thirdPartyAccountService")
	ThirdPartyAccountService thirdPartyAccountService;
	
	public boolean weixiCallback(WeixinPaymentCallBackData callbackData){
		if(callbackData == null || StringUtils.isBlank(callbackData.getOut_trade_no())){
			return false;
		}
		String outTradeNo = callbackData.getOut_trade_no();
		PaymentCallbackService service = getServiceBean(outTradeNo);
		boolean success = false;
		if(service != null){
			success = service.weixiCallback(callbackData);
			log.info(String.format("微信回调接口------>处理结果%s:%s", outTradeNo, success));
		}else{
			log.info("没有获取到订单编号"+outTradeNo+"业务处理逻辑,请检查是否注册了该订单前缀"+outTradeNo.substring(0, OrderNumPrefixConstraint.DEFAULT_PREFIX_LEN)+"的微信回调逻辑");
		}
		return success;
	}
	
	public boolean alipayCallback(AliPaymentCallBackData callbackData){
		if(callbackData == null || StringUtils.isBlank(callbackData.getOut_trade_no())){
			return false;
		}
		String verifyResult = verifyAlipayResponse(callbackData.getNotify_id(),callbackData.getSeller_id())+"";
		if(!"true".equalsIgnoreCase(verifyResult)){
			return false;
		}
		
		String outTradeNo = callbackData.getOut_trade_no();
		PaymentCallbackService service = getServiceBean(outTradeNo);
		
		boolean success = false;
		if(service != null){
			success = service.alipayCallback(callbackData);
			log.info(String.format("支付宝回调接口------>处理结果%s:%s", outTradeNo, success));
		}else{
			log.info("没有获取到订单编号"+outTradeNo+"业务处理逻辑,请检查是否注册了该订单前缀"+outTradeNo.substring(0, OrderNumPrefixConstraint.DEFAULT_PREFIX_LEN)+"的支付宝回调逻辑");
		}
		return success;
	}
	
	public AppMessage walletAccountCallback(String outTradeNo,BigDecimal totalFee){
		PaymentCallbackService service = getServiceBean(outTradeNo);
		
		AppMessage message = null;
		if(service != null){
			message = service.walletAccountCallback(outTradeNo, totalFee);
			log.info(String.format("电子钱包回调接口------>处理结果%s:%s", outTradeNo, message.getCode() == AppMessageCode.CODE_SUCCESS));
		}else{
			message = AppMessage.error("没有获取到订单编号"+outTradeNo+"业务处理逻辑");
			log.info("没有获取到订单编号"+outTradeNo+"业务处理逻辑,请检查是否注册了该订单前缀"+outTradeNo.substring(0, OrderNumPrefixConstraint.DEFAULT_PREFIX_LEN)+"的支付宝回调逻辑");
		}
		return message;
	}

	private PaymentCallbackService getServiceBean(String outTradeNo) {
		PaymentCallbackService service = null;
		Class<? extends PaymentCallbackService> serviceClass = tranactionServicesMap.get(outTradeNo.substring(0, OrderNumPrefixConstraint.DEFAULT_PREFIX_LEN));
		Annotation annotations[] = serviceClass.getDeclaredAnnotations();
		if(annotations != null && annotations.length > 0){
			for(Annotation anno : annotations){
				if(org.springframework.stereotype.Service.class == anno.annotationType()){
					try {
						org.springframework.stereotype.Service annoService = (Service) anno;
						service = (PaymentCallbackService) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(annoService.value());
					} catch (Exception e) {
						
					}
					if(service != null){
						break;
					}
				}
				if(org.springframework.stereotype.Component.class == anno.annotationType()){
					try {
						org.springframework.stereotype.Component annoService = (Component) anno;
						service = (PaymentCallbackService) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(annoService.value());
					} catch (Exception e) {
						
					}
					if(service != null){
						break;
					}
				}
			}
		}else{
			service = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(serviceClass);
		}
		return service;
	}
	
	/**
	 * 向该service注册你的订单回调处理逻辑
	 * @param orderNumPrefix {@link com.arf.core.OrderNumPrefixConstraint}
	 * @param serviceClass
	 * @return 注册提示
	 */
	public static String registerService(String orderNumPrefix,Class<? extends PaymentCallbackService> serviceClass){
		try {
			if(StringUtils.isBlank(orderNumPrefix)){
				return "订单前缀不能为空白";
			}
			if(tranactionServicesMap.get(orderNumPrefix) != null){
				System.err.println("已经注册过该订单前缀业务逻辑处理了orderNumPrefix="+orderNumPrefix);
				System.exit(-1);
				return "已经注册过该订单前缀业务逻辑处理了";
			}
			tranactionServicesMap.put(orderNumPrefix, serviceClass);
		} catch (Exception e) {
			e.printStackTrace();
			return "注册出现异常"+e.getMessage();
		}
		return "注册成功";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	/**
	 * 获取支付宝远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String verifyAlipayResponse(String notify_id, String partner) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		
		//支付宝消息验证地址
		String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
		String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

		return checAlipaykUrl(veryfy_url);
	}

	/**
	 * 获取支付宝远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
	 *		true
	 *         返回正确信息
	 *		false 
	 *			无效的通知
	 */
	private static String checAlipaykUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;
	}
}
