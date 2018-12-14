package com.arf.crowdfunding.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.hibernate.CacheMode;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.crowdfunding.dao.ZcShopkeeperPartnerOrderDao;
import com.arf.crowdfunding.entity.ZcShopkeeperPartnerOrder;
import com.arf.payment.OrderNumPrefixConstraint;

/**
 * 查找订单表数据信息 Dao
 * 
 * @author LW on 2016-06-21
 * @version 1.0
 *
 */
@Repository("zcShopkeeperPartnerOrderDao")
public class ZcShopkeeperPartnerOrderDaoImpl extends BaseDaoImpl<ZcShopkeeperPartnerOrder, Long>
		implements ZcShopkeeperPartnerOrderDao {

	// 针对店主判断众筹项目是否已有店主
	public synchronized boolean isFullShop(String community_number, String project_id) {

		boolean isFull = false;
		this.entityManager.clear();
		String sql = "from ZcShopkeeperPartnerOrder where projectId=:projectId AND communityNumber=:communityNumber and shopkeeperPartner=0 and status=4 and payStatus=1";
		
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setFlushMode(FlushModeType.COMMIT)
				.setParameter("communityNumber", community_number).setParameter("projectId", project_id)
				.getResultList();
		// 判断是否查询出信息
		if (list !=null && list.size() > 0) {
			isFull = true;
		}
		return isFull;
	}

	// 插入订单数据(店主 OR 合伙人)
	@Override
	public ZcShopkeeperPartnerOrder saveOpenShopMessage(String community_number, String user_name, String username,
			String project_id, String shopkeeper_amount, String mobile,BigDecimal leastAmount,int shopkeeperPartner) {

		ZcShopkeeperPartnerOrder zspo = new ZcShopkeeperPartnerOrder();
		zspo.setOrderNo(
				OrderNumPrefixConstraint.getInstance().genOrderNo(OrderNumPrefixConstraint.PREFIX_CROW_FOUND, 10)); // 订单号
		zspo.setCommunityNumber(community_number); // 小区编号
		zspo.setShopkeeperPartnerUser(user_name); // APP登录名
		zspo.setShopkeeperPartnerName(username); // 用户姓名
		zspo.setProjectId(project_id); // 项目ID
		zspo.setStatus(ZcShopkeeperPartnerOrder.Status.submit.ordinal()); // 订单状态
		zspo.setPayStatus(ZcShopkeeperPartnerOrder.PayStatus.no.ordinal()); // 支付状态
		zspo.setLeastPartnerAmount(leastAmount);
		if(shopkeeperPartner==0){//店主
			zspo.setShopkeeperPartner(ZcShopkeeperPartnerOrder.ShopkeeperPartner.shopkeeper.ordinal()); // 店主
		}else{
			zspo.setShopkeeperPartner(ZcShopkeeperPartnerOrder.ShopkeeperPartner.partner.ordinal()); // 合伙人
		}		
		zspo.setCreateDate(new Date()); // 创建时间
		zspo.setCrowdfundingAmount(new BigDecimal(shopkeeper_amount));// 众筹金额
		if (!StringUtil.isBlank(mobile)) // 连接方式
			zspo.setMobile(mobile);

		return entityManager.merge(zspo);
	}

	/**
	 * 查找用户未支付订单
	 * 
	 * @param community_number
	 * @param project_id
	 * @param user_name
	 */
	@Override
	public ZcShopkeeperPartnerOrder getNoPaymentOrder(String community_number, String project_id, String user_name) {

		String sql = "from ZcShopkeeperPartnerOrder where projectId=:projectId AND communityNumber=:communityNumber and shopkeeperPartnerUser=:shopkeeperPartnerUser and payStatus=0 and status=0";

		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("communityNumber", community_number).setParameter("projectId", project_id)
				.setParameter("shopkeeperPartnerUser", user_name).getResultList();
		if (list !=null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	/**
	 * 得到已经售出的众筹的金额
	 * 
	 * @param communityNumber
	 *            小区编号
	 * @param projectId
	 *            项目ID
	 */
	@Override
	public BigDecimal getOrderedShareNum(String communityNumber, String projectId) {

		this.entityManager.clear();
		String sql = "select sum(zc.crowdfundingAmount) as crowdfundingAmount from ZcShopkeeperPartnerOrder zc where communityNumber=:communityNumber and projectId=:projectId and shopkeeperPartner=1 and payStatus=1 and status=4 group by projectId";
		List<BigDecimal> list = entityManager.createQuery(sql, BigDecimal.class).setParameter("communityNumber", communityNumber)
			.setFlushMode(FlushModeType.COMMIT)
			.setParameter("projectId", projectId)
			.getResultList();
		
		return (list == null || list.size() <=0) ?BigDecimal.ZERO:list.get(0);

	}

/*	// 当支付成功时 修改订单状态
	@Override
	public int saveShopkeeperPaymentMessage(String order_no, int paymode) {

		String outTradeNo = OrderNumPrefixConstraint.getInstance()
				.genOrderNo(OrderNumPrefixConstraint.PREFIX_CROW_FOUND, 10);
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ZcShopkeeperPartnerOrder SET ").append("status=4,").append("payStatus=1,")
				.append("paymode=:paymode,outTradeNo=:outTradeNo where orderNo=:orderNo");

		int i = entityManager.createQuery(sb.toString()).setParameter("paymode", paymode)
				.setParameter("outTradeNo", outTradeNo).setParameter("orderNo", order_no).executeUpdate();
		return i > 0?i:0;
	}*/

	// 查找取消的订单是否存在
	@Override
	public boolean isExistOrder(String orderNo) {

		String sql = "from ZcShopkeeperPartnerOrder where orderNo=:orderNo";
		int size = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class).setParameter("orderNo", orderNo)
				.getResultList().size();
		if (size > 0)
			return true;
		else
			return false;
	}

	// 通过订单号查找支付订单号
	@Override
	public ZcShopkeeperPartnerOrder getOutTradeNo(String order_no,Integer shopParenter) {
		String sql = "from ZcShopkeeperPartnerOrder where orderNo=:orderNo and status=4 and payStatus=1 and shopkeeperPartner=:shopkeeperPartner";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("orderNo", order_no)
				.setParameter("shopkeeperPartner", shopParenter)
				.getResultList();
		if (list !=null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

/*	// 取消订单
	@Override
	public int cancelOrder(String orderNo) {

		String sql = "UPDATE ZcShopkeeperPartnerOrder SET status=1 where orderNo=:orderNo and status!=4 and payStatus!=1";
		try {
			return entityManager.createQuery(sql).setParameter("orderNo", orderNo).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}*/

	// 得到订单信息
	@Override
	public ZcShopkeeperPartnerOrder getOrderMessage(String order_no) {

		String sql = "from ZcShopkeeperPartnerOrder where orderNo=:orderNo and status!=4 and payStatus!=1";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("orderNo", order_no).getResultList();
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	// 得到订单信息
	@Override
	public ZcShopkeeperPartnerOrder getOrderMessage(String order_no,int status) {

		String sql = "from ZcShopkeeperPartnerOrder where orderNo=:orderNo and status=4 and payStatus=1";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("orderNo", order_no).getResultList();
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}
	
	// 通过项目ID和小区编号得到店主信息
	@Override
	public String getShopkeeperMobile(String project_id, String community_number) {

		String sql = "from ZcShopkeeperPartnerOrder where projectId=:projectId and communityNumber=:communityNumber and shopkeeperPartner=0 and status=4 and payStatus=1";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("projectId", project_id).setParameter("communityNumber", community_number)
				.getResultList();
		if (list !=null && list.size() > 0)
			return list.get(0).getMobile();
		else
			return "";
	}

	// 得到项目所有合伙人的手机号码
	@Override
	public List<ZcShopkeeperPartnerOrder> getAllPartnerMobile(String project_id) {

		String sql = "from ZcShopkeeperPartnerOrder where projectId=:projectId and status=4 and payStatus=1 and shopkeeperPartner=1";
		return entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class).setParameter("projectId", project_id)
				.getResultList();
	}

	// 当请求成为店主时 校验该项目是否已有店主
	@Override
	public boolean isExistShopkeeper(String community_number, String project_id) {

		String sql = "from ZcShopkeeperPartnerOrder where projectId=:projectId and communityNumber=:communityNumber and shopkeeperPartner=0 and status=4 and payStatus=1";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("projectId", project_id).setParameter("communityNumber", community_number)
				.getResultList();

		return list !=null && list.size() > 0 ? true : false;
	}

	// 同一个用户同一个项目的购买数
	@Override
	public BigDecimal getSameUserShares(String community_number, String project_id, String user_name) {

		String sql = "select sum(zc.crowdfundingAmount) as crowdfundingAmount  from ZcShopkeeperPartnerOrder zc where communityNumber=:communityNumber and projectId=:projectId and shopkeeperPartnerUser=:shopkeeperPartnerUser and shopkeeperPartner=1 and payStatus=1 group by projectId";

		try {
			List<BigDecimal> sameUserShares = entityManager.createQuery(sql, BigDecimal.class)
					.setParameter("communityNumber", community_number).setParameter("projectId", project_id)
					.setParameter("shopkeeperPartnerUser", user_name).getResultList();
			return (sameUserShares == null || sameUserShares.size() <= 0) ? BigDecimal.ZERO : sameUserShares.get(0);
		} catch (NoResultException e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}

	// 通过小区编号跟项目ID查找店主的账户号
	@Override
	public String getShopkeeperAccount(String community_number, String project_id) {

		String sql = "from ZcShopkeeperPartnerOrder where communityNumber=:communityNumber and projectId=:projectId and shopkeeperPartner=0 and payStatus=1 and status=4";

		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class).setParameter("communityNumber", community_number)
					.setParameter("projectId", project_id).getResultList();
		return list !=null && list.size()>0 ?list.get(0).getShopkeeperPartnerUser():"";
	}

	// 判断订单是否已被支付
	@Override
	public synchronized boolean isPayment(String orderNo) {

		String sql = "from ZcShopkeeperPartnerOrder where orderNo=:orderNo and status=4 and payStatus=1";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("orderNo", orderNo).getResultList();

		return list !=null && list.size() > 0 ? true : false;
	}

	// 判断同一名用户同意项目是否已有申请成为店主订单
	@Override
	public boolean isCreateOrder(String community_number, String project_id, String user_name) {

		String sql = "from ZcShopkeeperPartnerOrder where communityNumber=:communityNumber and projectId=:projectId and shopkeeperPartnerUser=:shopkeeperPartnerUser and shopkeeperPartner=0 and status=0 and payStatus=0";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("communityNumber", community_number).setParameter("projectId", project_id)
				.setParameter("shopkeeperPartnerUser", user_name).getResultList();

		return list !=null && list.size() > 0 ? true : false;
	}

	/**
	 * 当项目已有店主时，把所有请求成为该项目店主的订单设置为已取消
	 * @param community_number
	 * @param project_id
	 * @param pser_name
	 */
	@Override
	public ZcShopkeeperPartnerOrder updateShopKeeperOrderStatus(String community_number, String project_id) {
		
		String sql = "from ZcShopkeeperPartnerOrder where communityNumber=:communityNumber and projectId=:projectId and status=0 and shopkeeperPartner=0";
		
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql,ZcShopkeeperPartnerOrder.class)
		.setParameter("communityNumber", community_number)
		.setParameter("projectId", project_id)
		.getResultList();
		
		return list != null && list.size() > 0 ?list.get(0):null;
		
	}

	/**
	 * 当众筹已满时，对请求成为该项目合伙人的订单状态全部设置为已取消
	 * @param community_number	小区编号
	 * @param project_id		项目ID
	 */
	@Override
	public ZcShopkeeperPartnerOrder updateParenterOrderStatus(String community_number, String project_id) {
		
		String sql = "from ZcShopkeeperPartnerOrder where communityNumber=:communityNumber and projectId=:projectId and status=0 and shopkeeperPartner=1";
		
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql,ZcShopkeeperPartnerOrder.class)
		.setParameter("communityNumber", community_number)
		.setParameter("projectId", project_id)
		.getResultList();
		
		return list != null && list.size() > 0 ?list.get(0):null;
	}
	
	//判断申请成为合伙人的用户是否已是该项目的店主
	@Override
	public boolean isShopKeeper(String community_number, String project_id, String user_name) {
		
		String sql = "from ZcShopkeeperPartnerOrder where communityNumber=:communityNumber and projectId=:projectId and shopkeeperPartnerUser=:shopkeeperPartnerUser and shopkeeperPartner=0 and status=4 and payStatus=1";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("communityNumber", community_number).setParameter("projectId", project_id)
				.setParameter("shopkeeperPartnerUser", user_name).getResultList();

		return list !=null && list.size() > 0 ? true : false;
	}

	@Override
	public List<ZcShopkeeperPartnerOrder> getNoPaymentOrderSuccess(String community_number, String project_id) {

		String sql = "from ZcShopkeeperPartnerOrder where projectId=:projectId AND communityNumber=:communityNumber and payStatus=1 and status=4";

		return entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("communityNumber", community_number).setParameter("projectId", project_id)
				.getResultList();
	}

	@Override
	public int updateOpenShopMessage(long id, BigDecimal payment_amount) {

		Date createDate = new Date();
		String sql = "UPDATE ZcShopkeeperPartnerOrder SET crowdfundingAmount=:crowdfundingAmount,createDate=:createDate where id=:id";
		try {
			return entityManager.createQuery(sql).setParameter("crowdfundingAmount", payment_amount)
					.setParameter("createDate", createDate).setParameter("id", id).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ZcShopkeeperPartnerOrder> getSuccessOrder(String community_number, String project_id) {

		String sql = " from ZcShopkeeperPartnerOrder where projectId=:projectId AND communityNumber=:communityNumber and payStatus=1 and shopkeeperPartner=1 group by shopkeeperPartnerUser ";

		return  entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("communityNumber", community_number).setParameter("projectId", project_id).getResultList();
	}

	
	@Override
	public boolean isHandle(String orderNo) {
		
		String sql = "from ZcShopkeeperPartnerOrder where orderNo=:orderNo and status=4 and payStatus=1";
		List<ZcShopkeeperPartnerOrder> list = entityManager.createQuery(sql, ZcShopkeeperPartnerOrder.class)
				.setParameter("orderNo", orderNo).getResultList();
		
		return list != null && list.size() > 0?true:false;
	}

}
