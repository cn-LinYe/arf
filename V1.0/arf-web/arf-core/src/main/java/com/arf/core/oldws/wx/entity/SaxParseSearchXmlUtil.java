package com.arf.core.oldws.wx.entity;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxParseSearchXmlUtil extends DefaultHandler {

	// 存放遍历集合
	private List<SearchRedEnvelopeResult> list;
	// 构建Student对象
	private SearchRedEnvelopeResult mSearchRedEnvelopeResult;
	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;

	public List<SearchRedEnvelopeResult> getList() {
		return list;
	}

	public void setList(List<SearchRedEnvelopeResult> list) {
		this.list = list;
	}

	public SearchRedEnvelopeResult getRedEnvelope() {
		return mSearchRedEnvelopeResult;
	}

	public void setRedEnvelope(SearchRedEnvelopeResult mSearchRedEnvelopeResult) {
		this.mSearchRedEnvelopeResult = mSearchRedEnvelopeResult;
	}

	// 只调用一次 初始化list集合
	@Override
	public void startDocument() throws SAXException {
		list = new ArrayList<SearchRedEnvelopeResult>();
	}

	// 调用多次 开始解析
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("xml")) {
			mSearchRedEnvelopeResult = new SearchRedEnvelopeResult();
		}
		this.tagName = qName;
	}

	// 调用多次
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("xml")) {
			this.list.add(mSearchRedEnvelopeResult);
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
			if (this.tagName.equals("amount")) {
				this.mSearchRedEnvelopeResult.setAmount(date);
			} else if (this.tagName.equals("sign")) {
				this.mSearchRedEnvelopeResult.setSign(date);
				;
			} else if (this.tagName.equals("result_code")) {
				this.mSearchRedEnvelopeResult.setResult_code(date);
				;
			} else if (this.tagName.equals("detail_id")) {
				this.mSearchRedEnvelopeResult.setDetail_id(date);
				;
			} else if (this.tagName.equals("status")) {
				this.mSearchRedEnvelopeResult.setStatus(date);
				;
			} else if (this.tagName.equals("send_type")) {
				this.mSearchRedEnvelopeResult.setSend_type(date);
				;
			} else if (this.tagName.equals("hb_type")) {
				this.mSearchRedEnvelopeResult.setHb_type(date);
				;
			} else if (this.tagName.equals("total_num")) {
				this.mSearchRedEnvelopeResult.setTotal_num(date);
				;
			} else if (this.tagName.equals("reason")) {
				this.mSearchRedEnvelopeResult.setReason(date);
				;
			} else if (this.tagName.equals("refund_time")) {
				this.mSearchRedEnvelopeResult.setRefund_time(date);
				;
			} else if (this.tagName.equals("wishing")) {
				this.mSearchRedEnvelopeResult.setWishing(date);
				;
			} else if (this.tagName.equals("remark")) {
				this.mSearchRedEnvelopeResult.setRemark(date);
				;
			} else if (this.tagName.equals("act_name")) {
				this.mSearchRedEnvelopeResult.setAct_name(date);
				;
			} else if (this.tagName.equals("rcv_time")) {
				this.mSearchRedEnvelopeResult.setRcv_time(date);
				;
			} else if (this.tagName.equals("mch_id")) {
				this.mSearchRedEnvelopeResult.setMch_id(date);
				;
			}else if (this.tagName.equals("mch_id")) {
				this.mSearchRedEnvelopeResult.setMch_id(date);
				;
			} else if (this.tagName.equals("mch_billno")) {
				this.mSearchRedEnvelopeResult.setMch_billno(date);
				;
			} else if (this.tagName.equals("return_code")) {
				this.mSearchRedEnvelopeResult.setReturn_code(date);
				;
			} else if (this.tagName.equals("return_msg")) {
				this.mSearchRedEnvelopeResult.setReturn_msg(date);
				;
			} else if (this.tagName.equals("err_code")) {
				this.mSearchRedEnvelopeResult.setErr_code(date);
				;
			} else if (this.tagName.equals("err_code_des")) {
				this.mSearchRedEnvelopeResult.setErr_code_des(date);
				;
			} else if (this.tagName.equals("openid")) {
				this.mSearchRedEnvelopeResult.setOpenid(date);
				;
			} else if (this.tagName.equals("total_amount")) {
				this.mSearchRedEnvelopeResult.setTotal_amount(date);
				;
			} else if (this.tagName.equals("hblist")) {
				this.mSearchRedEnvelopeResult.setHblist(date);
				;
			} else if (this.tagName.equals("send_time")) {
				this.mSearchRedEnvelopeResult.setSend_time(date);
				;
			}
		}
	}
}
