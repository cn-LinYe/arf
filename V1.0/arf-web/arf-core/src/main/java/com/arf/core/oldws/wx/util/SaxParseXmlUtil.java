package com.arf.core.oldws.wx.util;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.arf.core.oldws.wx.entity.RedEnvelopeResponse;


public class SaxParseXmlUtil extends DefaultHandler {

	// 存放遍历集合
	private List<RedEnvelopeResponse> list;
	// 构建Student对象
	private RedEnvelopeResponse mRedEnvelope;
	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;

	public List<RedEnvelopeResponse> getList() {
		return list;
	}

	public void setList(List<RedEnvelopeResponse> list) {
		this.list = list;
	}

	public RedEnvelopeResponse getRedEnvelope() {
		return mRedEnvelope;
	}

	public void setRedEnvelope(RedEnvelopeResponse mRedEnvelope) {
		this.mRedEnvelope = mRedEnvelope;
	}

	// 只调用一次 初始化list集合
	@Override
	public void startDocument() throws SAXException {
		list = new ArrayList<RedEnvelopeResponse>();
	}

	// 调用多次 开始解析
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("xml")) {
			mRedEnvelope = new RedEnvelopeResponse();
		}
		this.tagName = qName;
	}

	// 调用多次
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("xml")) {
			this.list.add(mRedEnvelope);
		}
		this.tagName = null;
	}

	// 只调用一次
	@Override
	public void endDocument() throws SAXException {
	}

	// 调用多次
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (this.tagName != null) {
			String date = new String(ch, start, length);
			if (this.tagName.equals("wxappid")) {
				this.mRedEnvelope.setWxappid(date);
			} else if (this.tagName.equals("mch_id")) {
				this.mRedEnvelope.setMch_id(date);
				;
			} else if (this.tagName.equals("mch_billno")) {
				this.mRedEnvelope.setMch_billno(date);
				;
			} else if (this.tagName.equals("return_code")) {
				this.mRedEnvelope.setReturn_code(date);
				;
			} else if (this.tagName.equals("return_msg")) {
				this.mRedEnvelope.setReturn_msg(date);
				;
			} else if (this.tagName.equals("err_code")) {
				this.mRedEnvelope.setErr_code(date);
				;
			} else if (this.tagName.equals("err_code_des")) {
				this.mRedEnvelope.setErr_code_des(date);
				;
			} else if (this.tagName.equals("re_openid")) {
				this.mRedEnvelope.setRe_openid(date);
				;
			} else if (this.tagName.equals("total_amount")) {
				this.mRedEnvelope.setTotal_amount(date);
				;
			} else if (this.tagName.equals("send_listid")) {
				this.mRedEnvelope.setSend_listid(date);
				;
			} else if (this.tagName.equals("send_time")) {
				this.mRedEnvelope.setSend_time(date);
				;
			} 
		}
	}
}
