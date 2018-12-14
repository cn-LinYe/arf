package com.arf.crowdfunding.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.crowdfunding.dao.ZcShopkeeperPartnerOrderDao;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerOrder;
import com.arf.crowdfunding.service.ZcShopkeeperPartnerOrderService;
import com.arf.payment.OrderNumPrefixConstraint;

/**
 * 查询订单数据信息数据 Service
 * 
 * @author LW on 2016-06-18
 * @version 1.0
 *
 */
@Service("zcShopkeeperPartnerOrderServiceImpl")
public class ZcShopkeeperPartnerOrderServiceImpl extends BaseServiceImpl<ZcShopkeeperPartnerOrder, Long>
		implements ZcShopkeeperPartnerOrderService {

	@Resource(name = "zcShopkeeperPartnerOrderDao")
	private ZcShopkeeperPartnerOrderDao zcShopkeeperPartnerOrderDao;

	@Override
	public synchronized boolean isFullShop(String community_number, String project_id) {

		return zcShopkeeperPartnerOrderDao.isFullShop(community_number, project_id);
	}

	@Override
	public ZcShopkeeperPartnerOrder saveOpenShopMessage(String community_number, String user_name, String username,
			String project_id, String shopkeeper_amount, String mobile,BigDecimal leastAmount,int shopkeeperPartner) {

		return zcShopkeeperPartnerOrderDao.saveOpenShopMessage(community_number, user_name, username, project_id,
				shopkeeper_amount, mobile,leastAmount,shopkeeperPartner);
	}

	@Override
	public ZcShopkeeperPartnerOrder getNoPaymentOrder(String community_number, String project_id, String user_name) {

		return zcShopkeeperPartnerOrderDao.getNoPaymentOrder(community_number, project_id, user_name);
	}

	@Override
	public BigDecimal getOrderedShareNum(String communityNumber, String projectId) {

		return zcShopkeeperPartnerOrderDao.getOrderedShareNum(communityNumber, projectId);
	}

	@Override
	public int saveShopkeeperPaymentMessage(String order_no, int paymode) {

		String outTradeNo = OrderNumPrefixConstraint.getInstance()
				.genOrderNo(OrderNumPrefixConstraint.PREFIX_CROW_FOUND, 10);
		
		ZcShopkeeperPartnerOrder zc = zcShopkeeperPartnerOrderDao.getOrderMessage(order_no);
		if(zc != null){
			zc.setStatus(ZcShopkeeperPartnerOrder.Status.complete.ordinal());
			zc.setPayStatus(ZcShopkeeperPartnerOrder.PayStatus.yes.ordinal());
			zc.setPaymode(paymode);
			zc.setOutTradeNo(outTradeNo);	
			ZcShopkeeperPartnerOrder updated = update(zc);
			this.getBaseDao().flush();
			return updated != null ?1:0;
		}else{
			return 0;
		}
		
	}

	@Override
	public boolean isExistOrder(String orderNo) {

		return zcShopkeeperPartnerOrderDao.isExistOrder(orderNo);
	}

	@Override
	public ZcShopkeeperPartnerOrder getOutTradeNo(String order_no,Integer shopParenter) {

		return zcShopkeeperPartnerOrderDao.getOutTradeNo(order_no,shopParenter);
	}

	@Override
	public int cancelOrder(String orderNo) {

		ZcShopkeeperPartnerOrder zc = zcShopkeeperPartnerOrderDao.getOrderMessage(orderNo);
		if(zc != null){
			zc.setStatus(ZcShopkeeperPartnerOrder.Status.cancel.ordinal());
			ZcShopkeeperPartnerOrder update = update(zc);	
			this.getBaseDao().flush();
			return update != null ?1:0;
		}else
			return 0;
	}

	@Override
	public ZcShopkeeperPartnerOrder getOrderMessage(String order_no) {

		return zcShopkeeperPartnerOrderDao.getOrderMessage(order_no);
	}

	@Override
	public String getShopkeeperMobile(String project_id, String community_number) {

		return zcShopkeeperPartnerOrderDao.getShopkeeperMobile(project_id, community_number);
	}

	@Override
	public List<ZcShopkeeperPartnerOrder> getAllPartnerMobile(String project_id) {

		return zcShopkeeperPartnerOrderDao.getAllPartnerMobile(project_id);
	}

	@Override
	public boolean isExistShopkeeper(String community_number, String project_id) {

		return zcShopkeeperPartnerOrderDao.isExistShopkeeper(community_number, project_id);
	}
	
	@Override
	public BigDecimal getSameUserShares(String community_number, String project_id, String user_name) {
		
		return zcShopkeeperPartnerOrderDao.getSameUserShares(community_number,project_id,user_name);
	}


	@Override
	protected BaseDao<ZcShopkeeperPartnerOrder, Long> getBaseDao() {

		return zcShopkeeperPartnerOrderDao;
	}


	@Override
	public List<ZcShopkeeperPartnerOrder> getNoPaymentOrderSuccess(String community_number, String project_id) {
		return zcShopkeeperPartnerOrderDao.getNoPaymentOrderSuccess(community_number, project_id);
	}

	@Override
	public int updateOpenShopMessage(long id, BigDecimal payment_amount) {
		return zcShopkeeperPartnerOrderDao.updateOpenShopMessage(id, payment_amount);
	}

	@Override
	public String getShopkeeperAccount(String community_number, String project_id) {	
		return zcShopkeeperPartnerOrderDao.getShopkeeperAccount(community_number,project_id);
	}

	@Override
	public synchronized boolean isPayment(String orderNo) {
		
		return zcShopkeeperPartnerOrderDao.isPayment(orderNo);
	}

	@Override
	public boolean isCreateOrder(String community_number, String project_id, String user_name) {
		
		return zcShopkeeperPartnerOrderDao.isCreateOrder( community_number,  project_id, user_name);
	}

	@Override
	public List<ZcShopkeeperPartnerOrder> getSuccessOrder(String community_number, String project_id) {
		return zcShopkeeperPartnerOrderDao.getSuccessOrder(community_number, project_id);
	}

	@Override
	public void updateShopKeeperOrderStatus(String community_number, String project_id) {
		
		ZcShopkeeperPartnerOrder zc = zcShopkeeperPartnerOrderDao.updateShopKeeperOrderStatus(community_number, project_id);
		if(zc != null){
			zc.setStatus(ZcShopkeeperPartnerOrder.Status.cancel.ordinal());
			update(zc);
			this.getBaseDao().flush();
		}		

	}

	@Override
	public void updateParenterOrderStatus(String community_number, String project_id) {
		
		ZcShopkeeperPartnerOrder zc = zcShopkeeperPartnerOrderDao.updateParenterOrderStatus(community_number,  project_id);
		if(zc != null){
			zc.setStatus(ZcShopkeeperPartnerOrder.Status.cancel.ordinal());
			update(zc);
			this.getBaseDao().flush();
		}			
		
	}

	@Override
	public boolean isShopKeeper(String community_number, String project_id, String user_name) {
		
		return zcShopkeeperPartnerOrderDao.isShopKeeper(community_number, project_id, user_name);
	}

	
}
