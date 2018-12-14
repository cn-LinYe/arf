package com.arf.core.oldws.wx.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.SortedMap;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.arf.core.oldws.wx.entity.RedEnvelopeResponse;
import com.arf.core.oldws.wx.entity.SaxParseSearchXmlUtil;
import com.arf.core.oldws.wx.entity.SaxParseXmlUtil;
import com.arf.core.oldws.wx.entity.SearchRedEnvelopeResult;
import com.arf.core.oldws.wx.entity.WxConstants;


@Service("wxRedEnvelopeApisImpl")
public class WxRedEnvelopeApisImpl implements WxRedEnvelopeApis{
	private static final String certPath = "WEB-INF/apiclient_cert_network.p12";
	private ServletContext servletContext;
	/**
	 *  发送红包
	 */
	@Override
	public RedEnvelopeResponse sendRedEnvelope(String openId, int money,String billno,String wishing,String actName) {
		
			SortedMap<String, String> sortedMap = RedEnvelopeUtils.createMap(openId, money, billno, wishing, actName);
			RedEnvelopeUtils.sign(sortedMap);
			String postXML = RedEnvelopeUtils.getRequestXml(sortedMap);
			System.out.println(postXML);
			FileInputStream instream;
			try {
				/**
				 * 嵘联合盟
				 */
//				instream = new FileInputStream(new File("/usr/local/apache-tomcat-7.0.57/webapps/apiclient_cert.p12"));
				/**
				 * 俺家网络
				 */
//				instream = new FileInputStream(new File("/usr/web/arf-web/WEB-INF/apiclient_cert_network.p12"));
				/**
				 * 另外一个tomcat
				 */
				instream = new FileInputStream(getCertFile());
//				instream = new FileInputStream(new File("F://apiclient_cert_network.p12"));
//				instream = new FileInputStream(new File("D:/apiclient_cert_network.p12"));
				String result=RedEnvelopeUtils.post(postXML, instream,WxConstants.URL_Red_envelope);
				RedEnvelopeResponse redEnvelopeResponse = parseResult(result);
				System.out.println("支付结果解析: "+redEnvelopeResponse);
				return redEnvelopeResponse;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 查询红包接口
	 */
	@Override
	public SearchRedEnvelopeResult searchRedEnvelope(String mch_billno,String mch_id) {
		SortedMap<String, String> sortedMap = RedEnvelopeUtils.createMap(mch_billno,mch_id);
		RedEnvelopeUtils.sign(sortedMap);
		String postXML = RedEnvelopeUtils.getRequestXml(sortedMap);
		FileInputStream instream;
		String result = "";//接收http请求返回结果
		try {
			//instream = new FileInputStream(new File("F:/apiclient_cert.p12"));//嵘联
//			instream = new FileInputStream(new File("D:/apiclient_cert_network.p12"));//俺家网络科技
		    instream = new FileInputStream(getCertFile());
			result=RedEnvelopeUtils.post(postXML, instream,WxConstants.URL_Red_Envelop_Sarch);
			return parseSearchResult(result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析根据商户订单号和商户号查询红包后的返回结果
	 */
	private SearchRedEnvelopeResult parseSearchResult(String result) {
		SAXParser parser = null;
		try {
			// 构建SAXParser
			parser = SAXParserFactory.newInstance().newSAXParser();
			// 实例化 DefaultHandler对象
			SaxParseSearchXmlUtil parseXml = new SaxParseSearchXmlUtil();
			parser.parse(new InputSource(new StringReader(result)), parseXml);
			// 遍历结果
			SearchRedEnvelopeResult searchRedEnvelopeResult = parseXml.getRedEnvelope();
			return searchRedEnvelopeResult;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private File getCertFile(){
		if(servletContext == null){
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
	        servletContext = webApplicationContext.getServletContext();
		}
		String res = servletContext.getRealPath("/");
		return new File(res + "/" +  certPath);
	}
	
	/**
	 * 解析发红包后的返回值
	 */
	private RedEnvelopeResponse parseResult(String result) {
		SAXParser parser = null;
		try {
			// 构建SAXParser
			parser = SAXParserFactory.newInstance().newSAXParser();
			// 实例化 DefaultHandler对象
			SaxParseXmlUtil parseXml = new SaxParseXmlUtil();
			parser.parse(new InputSource(new StringReader(result)), parseXml);
			// 遍历结果
			RedEnvelopeResponse redEnvelopeResponse = parseXml.getRedEnvelope();
			return redEnvelopeResponse;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
