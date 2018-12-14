package com.arf.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.arf.core.Filter;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.Setting;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CartDao;
import com.arf.core.dao.CouponCodeDao;
import com.arf.core.dao.MemberDao;
import com.arf.core.dao.OrderDao;
import com.arf.core.dao.OrderItemDao;
import com.arf.core.dao.OrderLogDao;
import com.arf.core.dao.PaymentDao;
import com.arf.core.dao.ProductDao;
import com.arf.core.dao.RefundsDao;
import com.arf.core.dao.ReturnsDao;
import com.arf.core.dao.ShippingDao;
import com.arf.core.dao.ShippingItemDao;
import com.arf.core.dao.SnDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Cart;
import com.arf.core.entity.CartItem;
import com.arf.core.entity.Coupon;
import com.arf.core.entity.CouponCode;
import com.arf.core.entity.DepositLog;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Insurance;
import com.arf.core.entity.Invoice;
import com.arf.core.entity.Member;
import com.arf.core.entity.NoPricingOrder;
import com.arf.core.entity.Order;
import com.arf.core.entity.Order.Status;
import com.arf.core.entity.Order.Type;
import com.arf.core.entity.OrderItem;
import com.arf.core.entity.OrderLog;
import com.arf.core.entity.Payment;
import com.arf.core.entity.PaymentMethod;
import com.arf.core.entity.PointLog;
import com.arf.core.entity.Product;
import com.arf.core.entity.Receiver;
import com.arf.core.entity.Refunds;
import com.arf.core.entity.Returns;
import com.arf.core.entity.ReturnsItem;
import com.arf.core.entity.Shipping;
import com.arf.core.entity.ShippingItem;
import com.arf.core.entity.ShippingMethod;
import com.arf.core.entity.Sn;
import com.arf.core.entity.StockLog;
import com.arf.core.service.AdminService;
import com.arf.core.service.CartService;
import com.arf.core.service.GoodsService;
import com.arf.core.service.InsuranceService;
import com.arf.core.service.MailService;
import com.arf.core.service.MemberService;
import com.arf.core.service.NoPricingOrderService;
import com.arf.core.service.OrderService;
import com.arf.core.service.ProductService;
import com.arf.core.service.ShippingMethodService;
import com.arf.core.service.SmsService;
import com.arf.core.service.StaticService;
import com.arf.core.util.SettingUtils;
import com.arf.member.entity.RAccountTransferRecord;
import com.arf.member.service.IRAccountTransferRecordService;
import com.arf.platform.entity.SMemberAccount;
import com.arf.platform.service.ISMemberAccountService;
import com.arf.redis.CacheNameDefinition;

/**
 * Service - 订单
 * 
 * @author arf
 * @version 4.0
 */
@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;
	@Resource(name = "orderItemDaoImpl")
	private OrderItemDao orderItemDao;
	@Resource(name = "orderLogDaoImpl")
	private OrderLogDao orderLogDao;
	@Resource(name = "cartDaoImpl")
	private CartDao cartDao;
	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	@Resource(name = "shippingDaoImpl")
	private ShippingDao shippingDao;
	@Resource(name = "shippingItemDaoImpl")
	private ShippingItemDao shippingItemDao;
	@Resource(name = "returnsDaoImpl")
	private ReturnsDao returnsDao;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "noPricingOrderServiceImpl")
    private NoPricingOrderService noPricingOrderService;

	@Resource(name = "insuranceServiceImpl")
    private InsuranceService insuranceService;
	
	@Resource(name="adminServiceImpl")
	private AdminService adminService;
	
	@Resource(name="staticServiceImpl")
	private StaticService staticService;
	
	@Resource(name = "sMemberAccountServiceImpl")
	private ISMemberAccountService memberAccountService;
	@Resource(name = "rAccountTransferRecordServiceImpl")
	private IRAccountTransferRecordService accountTransferRecordService;

	@Resource(name = "redisService")
	private RedisService redisService;

	@Override
	protected BaseDao<Order, Long> getBaseDao() {
		return orderDao;
	}

	@Override
	@Transactional(readOnly = true)
	public Order findBySn(String sn) {
		return orderDao.findBySn(sn);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Order> findList(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Integer count, List<Filter> filters, List<com.arf.core.Order> orders) {
		return orderDao.findList(type, status, member, isPendingReceive, isPendingRefunds, isAllocatedStock, hasExpired, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Order> findPage(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		return orderDao.findPage(type, status, member, isPendingReceive, isPendingRefunds, isAllocatedStock, hasExpired, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Order> findPageFilterOrder(Order.Type type, Order.Status status,Admin admin,Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		return orderDao.findPageFilterOrder(type, status, admin, isPendingReceive, isPendingRefunds, isAllocatedStock, hasExpired, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countFilterOrder(Order.Type type, Order.Status status,Admin admin, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired) {
		return orderDao.countFilterOrder(type, status, admin, goods, isPendingReceive, isPendingRefunds, isAllocatedStock, hasExpired);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count(Order.Type type, Order.Status status, Member member, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired) {
		return orderDao.count(type, status, member, goods, isPendingReceive, isPendingRefunds, isAllocatedStock, hasExpired);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateTax(BigDecimal price, BigDecimal promotionDiscount, BigDecimal couponDiscount, BigDecimal offsetAmount) {
		Assert.notNull(price);
		Assert.state(price.compareTo(BigDecimal.ZERO) >= 0);
		Assert.state(promotionDiscount == null || promotionDiscount.compareTo(BigDecimal.ZERO) >= 0);
		Assert.state(couponDiscount == null || couponDiscount.compareTo(BigDecimal.ZERO) >= 0);

		Setting setting = SettingUtils.get();
		if (!setting.getIsTaxPriceEnabled()) {
			return BigDecimal.ZERO;
		}
		BigDecimal amount = price;
		if (promotionDiscount != null) {
			amount = amount.subtract(promotionDiscount);
		}
		if (couponDiscount != null) {
			amount = amount.subtract(couponDiscount);
		}
		if (offsetAmount != null) {
			amount = amount.add(offsetAmount);
		}
		BigDecimal tax = amount.multiply(new BigDecimal(String.valueOf(setting.getTaxRate())));
		return tax.compareTo(BigDecimal.ZERO) >= 0 ? setting.setScale(tax) : BigDecimal.ZERO;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateTax(Order order) {
		Assert.notNull(order);

		if (order.getInvoice() == null) {
			return BigDecimal.ZERO;
		}
		return calculateTax(order.getPrice(), order.getPromotionDiscount(), order.getCouponDiscount(), order.getOffsetAmount());
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateAmount(BigDecimal price, BigDecimal fee, BigDecimal freight, BigDecimal tax, BigDecimal promotionDiscount, BigDecimal couponDiscount, BigDecimal offsetAmount) {
		Assert.notNull(price);
		Assert.state(price.compareTo(BigDecimal.ZERO) >= 0);
		Assert.state(fee == null || fee.compareTo(BigDecimal.ZERO) >= 0);
		Assert.state(freight == null || freight.compareTo(BigDecimal.ZERO) >= 0);
		Assert.state(tax == null || tax.compareTo(BigDecimal.ZERO) >= 0);
		Assert.state(promotionDiscount == null || promotionDiscount.compareTo(BigDecimal.ZERO) >= 0);
		Assert.state(couponDiscount == null || couponDiscount.compareTo(BigDecimal.ZERO) >= 0);

		Setting setting = SettingUtils.get();
		BigDecimal amount = price;
		if (fee != null) {
			amount = amount.add(fee);
		}
		if (freight != null) {
			amount = amount.add(freight);
		}
		if (tax != null) {
			amount = amount.add(tax);
		}
		if (promotionDiscount != null) {
			amount = amount.subtract(promotionDiscount);
		}
		if (couponDiscount != null) {
			amount = amount.subtract(couponDiscount);
		}
		if (offsetAmount != null) {
			amount = amount.add(offsetAmount);
		}
		return amount.compareTo(BigDecimal.ZERO) >= 0 ? setting.setScale(amount) : BigDecimal.ZERO;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateAmount(Order order) {
		Assert.notNull(order);

		return calculateAmount(order.getPrice(), order.getFee(), order.getFreight(), order.getTax(), order.getPromotionDiscount(), order.getCouponDiscount(), order.getOffsetAmount());
	}

	@Override
	public boolean isLocked(Order order, Admin admin, boolean autoLock) {
		Assert.notNull(order);
		Assert.notNull(admin);

		boolean isLocked = order.getLockExpire() != null && order.getLockExpire().after(new Date()) && StringUtils.isNotEmpty(order.getLockKey()) && !StringUtils.equals(order.getLockKey(), admin.getLockKey());
		if (autoLock && !isLocked && StringUtils.isNotEmpty(admin.getLockKey())) {
			order.setLockKey(admin.getLockKey());
			order.setLockExpire(DateUtils.addSeconds(new Date(), Order.LOCK_EXPIRE));
		}
		return isLocked;
	}

	@Override
	public boolean isLocked(Order order, Member member, boolean autoLock) {
		Assert.notNull(order);
		Assert.notNull(member);

		boolean isLocked = order.getLockExpire() != null && order.getLockExpire().after(new Date()) && StringUtils.isNotEmpty(order.getLockKey()) && !StringUtils.equals(order.getLockKey(), member.getLockKey());
		if (autoLock && !isLocked && StringUtils.isNotEmpty(member.getLockKey())) {
			order.setLockKey(member.getLockKey());
			order.setLockExpire(DateUtils.addSeconds(new Date(), Order.LOCK_EXPIRE));
		}
		return isLocked;
	}

	@Override
	public void lock(Order order, Admin admin) {
		Assert.notNull(order);
		Assert.notNull(admin);

		boolean isLocked = order.getLockExpire() != null && order.getLockExpire().after(new Date()) && StringUtils.isNotEmpty(order.getLockKey()) && !StringUtils.equals(order.getLockKey(), admin.getLockKey());
		if (!isLocked && StringUtils.isNotEmpty(admin.getLockKey())) {
			order.setLockKey(admin.getLockKey());
			order.setLockExpire(DateUtils.addSeconds(new Date(), Order.LOCK_EXPIRE));
		}
	}

	@Override
	public void lock(Order order, Member member) {
		Assert.notNull(order);
		Assert.notNull(member);

		boolean isLocked = order.getLockExpire() != null && order.getLockExpire().after(new Date()) && StringUtils.isNotEmpty(order.getLockKey()) && !StringUtils.equals(order.getLockKey(), member.getLockKey());
		if (!isLocked && StringUtils.isNotEmpty(member.getLockKey())) {
			order.setLockKey(member.getLockKey());
			order.setLockExpire(DateUtils.addSeconds(new Date(), Order.LOCK_EXPIRE));
		}
	}

	@Override
	public void allocateStock(Order order) {
		if (order == null || BooleanUtils.isNotFalse(order.getIsAllocatedStock())) {
			return;
		}
		if (order.getOrderItems() != null) {
			for (OrderItem orderItem : order.getOrderItems()) {
				orderItemDao.refresh(orderItem, LockModeType.PESSIMISTIC_WRITE);
				Product product = orderItem.getProduct();
				if (product != null) {
					productService.addAllocatedStock(product, orderItem.getQuantity() - orderItem.getShippedQuantity());
				}
			}
		}
		order.setIsAllocatedStock(true);
	}

	@Override
	public void releaseAllocatedStock(Order order) {
		if (order == null || BooleanUtils.isNotTrue(order.getIsAllocatedStock())) {
			return;
		}
		if (order.getOrderItems() != null) {
			for (OrderItem orderItem : order.getOrderItems()) {
				orderItemDao.refresh(orderItem, LockModeType.PESSIMISTIC_WRITE);
				Product product = orderItem.getProduct();
				if (product != null) {
					productService.addAllocatedStock(product, -(orderItem.getQuantity() - orderItem.getShippedQuantity()));
				}
			}
		}
		order.setIsAllocatedStock(false);
	}

	@Override
	public void releaseExpiredAllocatedStock() {
		List<Order> orders = orderDao.findList(null, null, null, null, null, true, true, null, null, null);
		for (Order order : orders) {
			releaseAllocatedStock(order);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Order generate(Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo) {
		Assert.notNull(type);
		Assert.notNull(cart);
		Assert.notNull(cart.getMember());
		Assert.notEmpty(cart.getCartItems());

		Setting setting = SettingUtils.get();
		Member member = cart.getMember();
		
		Order order = new Order();
		order.setType(type);
		order.setPrice(cart.getPrice());
		order.setFee(BigDecimal.ZERO);
		order.setPromotionDiscount(cart.getDiscount());
		order.setOffsetAmount(BigDecimal.ZERO);
		order.setRefundAmount(BigDecimal.ZERO);
		order.setRewardPoint(cart.getEffectiveRewardPoint());
		order.setExchangePoint(cart.getExchangePoint());
		order.setWeight(cart.getWeight());
		order.setQuantity(cart.getQuantity());
		order.setShippedQuantity(0);
		order.setReturnedQuantity(0);
		order.setMemo(memo);
		order.setIsAllocatedStock(false);
		order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);
		order.setPaymentMethod(paymentMethod);
		order.setMember(member);
		order.setPromotionNames(cart.getPromotionNames());
		order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));

		if (shippingMethod != null && shippingMethod.isSupported(paymentMethod) && !cart.isFreeShipping()) {
			order.setFreight(shippingMethodService.calculateFreight(shippingMethod, receiver, cart.getWeight()));
			order.setShippingMethod(shippingMethod);
		} else {
			order.setFreight(BigDecimal.ZERO);
		}

		if (couponCode != null && cart.isCouponAllowed() && cart.isValid(couponCode)) {
			BigDecimal couponDiscount = cart.getEffectivePrice().subtract(couponCode.getCoupon().calculatePrice(cart.getEffectivePrice(), cart.getQuantity()));
			order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount : BigDecimal.ZERO);
			order.setCouponCode(couponCode);
		} else {
			order.setCouponDiscount(BigDecimal.ZERO);
		}

		order.setTax(calculateTax(order));
		order.setAmount(calculateAmount(order));

		if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0 && balance.compareTo(member.getBalance()) <= 0 && balance.compareTo(order.getAmount()) <= 0) {
			order.setAmountPaid(balance);
		} else {
			order.setAmountPaid(BigDecimal.ZERO);
		}

		if (receiver != null) {
			order.setConsignee(receiver.getConsignee());
			order.setAreaName(receiver.getAreaName());
			order.setAddress(receiver.getAddress());
			order.setZipCode(receiver.getZipCode());
			order.setPhone(receiver.getPhone());
			order.setArea(receiver.getArea());
		}

		List<OrderItem> orderItems = order.getOrderItems();
		for (CartItem cartItem : cart.getCartItems()) {
			Product product = cartItem.getProduct();
			if (product != null) {
				OrderItem orderItem = new OrderItem();
				orderItem.setSn(product.getSn());
				orderItem.setName(product.getName());
				orderItem.setType(product.getType());
				orderItem.setPrice(cartItem.getPrice());
				orderItem.setWeight(product.getWeight());
				orderItem.setThumbnail(product.getThumbnail());
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnedQuantity(0);
				orderItem.setProduct(cartItem.getProduct());
				orderItem.setOrder(order);
				orderItem.setSpecifications(product.getSpecifications());
				orderItems.add(orderItem);
			}
		}

		for (Product gift : cart.getGifts()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setSn(gift.getSn());
			orderItem.setName(gift.getName());
			orderItem.setType(gift.getType());
			orderItem.setPrice(BigDecimal.ZERO);
			orderItem.setWeight(gift.getWeight());
			orderItem.setThumbnail(gift.getThumbnail());
			orderItem.setQuantity(1);
			orderItem.setShippedQuantity(0);
			orderItem.setReturnedQuantity(0);
			orderItem.setProduct(gift);
			orderItem.setOrder(order);
			orderItem.setSpecifications(gift.getSpecifications());
			orderItems.add(orderItem);
		}

		return order;
	}

	@Override
	public Order create(Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo) {
		Assert.notNull(type);
		Assert.notNull(cart);
		Assert.notNull(cart.getMember());
		Assert.notEmpty(cart.getCartItems());
		Assert.notNull(receiver);
		Assert.notNull(shippingMethod);
		Assert.state(shippingMethod.isSupported(paymentMethod));

		for (CartItem cartItem : cart.getCartItems()) {
			Product product = cartItem.getProduct();
			productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);
			if (product == null || !product.getIsMarketable() || cartItem.getQuantity() > product.getAvailableStock()) {
				throw new IllegalArgumentException();
			}
		}

		for (Product gift : cart.getGifts()) {
			productDao.refresh(gift, LockModeType.PESSIMISTIC_WRITE);
			if (!gift.getIsMarketable() || gift.getIsOutOfStock()) {
				throw new IllegalArgumentException();
			}
		}

		Setting setting = SettingUtils.get();
		Member member = cart.getMember();
		memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);

		Order order = new Order();
		order.setSn(snDao.generate(Sn.Type.order));
		order.setType(type);
		order.setPrice(cart.getPrice());
		order.setFee(BigDecimal.ZERO);
		order.setFreight(!cart.isFreeShipping() ? shippingMethodService.calculateFreight(shippingMethod, receiver, cart.getWeight()) : BigDecimal.ZERO);
		order.setPromotionDiscount(cart.getDiscount());
		order.setOffsetAmount(BigDecimal.ZERO);
		order.setAmountPaid(BigDecimal.ZERO);
		order.setRefundAmount(BigDecimal.ZERO);
		order.setRewardPoint(cart.getEffectiveRewardPoint());
		order.setExchangePoint(cart.getExchangePoint());
		order.setWeight(cart.getWeight());
		order.setQuantity(cart.getQuantity());
		order.setShippedQuantity(0);
		order.setReturnedQuantity(0);
		order.setConsignee(receiver.getConsignee());
		order.setAreaName(receiver.getAreaName());
		order.setAddress(receiver.getAddress());
		order.setZipCode(receiver.getZipCode());
		order.setPhone(receiver.getPhone());
		order.setArea(receiver.getArea());
		order.setMemo(memo);
		order.setIsAllocatedStock(false);
		order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);
		order.setPaymentMethod(paymentMethod);
		order.setShippingMethod(shippingMethod);
		order.setMember(member);
		order.setPromotionNames(cart.getPromotionNames());
		order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));

		if (couponCode != null) {
			couponCodeDao.refresh(couponCode, LockModeType.PESSIMISTIC_WRITE);
			if (!cart.isCouponAllowed() || !cart.isValid(couponCode)) {
				throw new IllegalArgumentException();
			}
			BigDecimal couponDiscount = cart.getEffectivePrice().subtract(couponCode.getCoupon().calculatePrice(cart.getEffectivePrice(), cart.getQuantity()));
			order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount : BigDecimal.ZERO);
			order.setCouponCode(couponCode);
			couponCode.setIsUsed(true);
			couponCode.setUsedDate(new Date());
		} else {
			order.setCouponDiscount(BigDecimal.ZERO);
		}

		order.setTax(calculateTax(order));
		order.setAmount(calculateAmount(order));

		if (balance != null && (balance.compareTo(BigDecimal.ZERO) < 0 || balance.compareTo(member.getBalance()) > 0 || balance.compareTo(order.getAmount()) > 0)) {
			throw new IllegalArgumentException();
		}
		BigDecimal amountPayable = balance != null && balance.compareTo(BigDecimal.ZERO) > 0 ? order.getAmount().subtract(balance) : order.getAmount();
		if ((amountPayable.compareTo(BigDecimal.ZERO) > 0 && paymentMethod == null) || amountPayable.compareTo(BigDecimal.ZERO) <= 0 && paymentMethod != null) {
			throw new IllegalArgumentException();
		}

		if (amountPayable.compareTo(BigDecimal.ZERO) <= 0) {
			order.setStatus(Order.Status.pendingReview);
		} else {
			order.setStatus(paymentMethod != null && PaymentMethod.Type.deliveryAgainstPayment.equals(paymentMethod.getType()) ? Order.Status.pendingPayment : Order.Status.pendingReview);
		}

		if (paymentMethod != null && paymentMethod.getTimeout() != null && Order.Status.pendingPayment.equals(order.getStatus())) {
			order.setExpire(DateUtils.addMinutes(new Date(), paymentMethod.getTimeout()));
		}

		List<OrderItem> orderItems = order.getOrderItems();
		for (CartItem cartItem : cart.getCartItems()) {
			Product product = cartItem.getProduct();
			OrderItem orderItem = new OrderItem();
			orderItem.setSn(product.getSn());
			orderItem.setName(product.getName());
			orderItem.setType(product.getType());
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setWeight(product.getWeight());
			orderItem.setThumbnail(product.getThumbnail());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setShippedQuantity(0);
			orderItem.setReturnedQuantity(0);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			orderItem.setSpecifications(product.getSpecifications());
			orderItems.add(orderItem);
		}

		for (Product gift : cart.getGifts()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setSn(gift.getSn());
			orderItem.setName(gift.getName());
			orderItem.setType(gift.getType());
			orderItem.setPrice(BigDecimal.ZERO);
			orderItem.setWeight(gift.getWeight());
			orderItem.setThumbnail(gift.getThumbnail());
			orderItem.setQuantity(1);
			orderItem.setShippedQuantity(0);
			orderItem.setReturnedQuantity(0);
			orderItem.setProduct(gift);
			orderItem.setOrder(order);
			orderItem.setSpecifications(gift.getSpecifications());
			orderItems.add(orderItem);
		}

		orderDao.persist(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		if (Setting.StockAllocationTime.order.equals(setting.getStockAllocationTime()) || (Setting.StockAllocationTime.payment.equals(setting.getStockAllocationTime()) && order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0)) {
			allocateStock(order);
		}

		if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0) {
			Payment payment = new Payment();
			payment.setMethod(Payment.Method.deposit);
			payment.setFee(BigDecimal.ZERO);
			payment.setAmount(balance);
			payment.setOrder(order);
			payment(order, payment, null);
		}

		if (order.getExchangePoint() != null && order.getExchangePoint() > 0) {
			memberService.addPoint(member, -order.getExchangePoint(), PointLog.Type.exchange, null, null);
		}

		if (paymentMethod != null && PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
			lock(order, member);
		}

		mailService.sendCreateOrderMail(order);
		smsService.sendCreateOrderSms(order);

		if (!cart.isNew()) {
			cartDao.remove(cart);
		}

		return order;
	}

	@Override
	public void update(Order order, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(!order.hasExpired() && (Order.Status.pendingPayment.equals(order.getStatus()) || Order.Status.pendingReview.equals(order.getStatus())));

		order.setAmount(calculateAmount(order));
		if (order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
			order.setStatus(Order.Status.pendingReview);
			order.setExpire(null);
		} else {
			if (order.getPaymentMethod() != null && PaymentMethod.Type.deliveryAgainstPayment.equals(order.getPaymentMethod().getType())) {
				order.setStatus(Order.Status.pendingPayment);
			} else {
				order.setStatus(Order.Status.pendingReview);
				order.setExpire(null);
			}
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.update);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendUpdateOrderMail(order);
		smsService.sendUpdateOrderSms(order);
	}

	@Override
	public void cancel(Order order) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(!order.hasExpired() && (Order.Status.pendingPayment.equals(order.getStatus()) || Order.Status.pendingReview.equals(order.getStatus())));

		order.setStatus(Order.Status.canceled);
		order.setExpire(null);

		CouponCode couponCode = order.getCouponCode();
		if (couponCode != null) {
			couponCode.setIsUsed(false);
			couponCode.setUsedDate(null);
			order.setCouponCode(null);
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.cancel);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendCancelOrderMail(order);
		smsService.sendCancelOrderSms(order);
	}

	@Override
	public void review(Order order, boolean passed, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(!order.hasExpired() && Order.Status.pendingReview.equals(order.getStatus()));

		order.setStatus(passed ? Order.Status.pendingDelivery : Order.Status.denied);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.review);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendReviewOrderMail(order);
		smsService.sendReviewOrderSms(order);
	}

	@Override
	public void review(Order order, boolean passed, Member operator) {
        Assert.notNull(order);
        Assert.isTrue(!order.isNew());
        Assert.state(!order.hasExpired() && Order.Status.pendingReview.equals(order.getStatus()));

        order.setStatus(passed ? Order.Status.pendingDelivery : Order.Status.denied);

        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.review);
        orderLog.setOperator(operator.getUsername()+"[商户]");
        orderLog.setOrder(order);
        orderLogDao.persist(orderLog);

        mailService.sendReviewOrderMail(order);
        smsService.sendReviewOrderSms(order);
    }
	
	@Override
	public void payment(Order order, Payment payment, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.notNull(payment);
		Assert.isTrue(payment.isNew());
		Assert.notNull(payment.getAmount());
		Assert.state(payment.getAmount().compareTo(BigDecimal.ZERO) > 0);

		payment.setSn(snDao.generate(Sn.Type.payment));
		payment.setOrder(order);
		paymentDao.persist(payment);

		if (order.getMember() != null && Payment.Method.deposit.equals(payment.getMethod())) {
			memberService.addBalance(order.getMember(), payment.getEffectiveAmount().negate(), DepositLog.Type.payment, operator, null);
		}

		Setting setting = SettingUtils.get();
		if (Setting.StockAllocationTime.payment.equals(setting.getStockAllocationTime())) {
			allocateStock(order);
		}

		order.setAmountPaid(order.getAmountPaid().add(payment.getEffectiveAmount()));
		order.setFee(order.getFee().add(payment.getFee()));
		if (!order.hasExpired() && Order.Status.pendingPayment.equals(order.getStatus()) && order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
//			order.setStatus(Order.Status.pendingReview);
		    BigDecimal nblance=order.getBalance()==null?BigDecimal.ZERO:order.getBalance();
		    nblance=nblance.add(payment.getAmount());
		    order.setBalance(nblance);
		    order.setStatus(Order.Status.pendingDelivery);
			order.setExpire(null);
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.payment);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendPaymentOrderMail(order);
		smsService.sendPaymentOrderSms(order);
	}
	/**
	 * 未定价服务支付
	 * @throws Exception 
	 */
	@Override
	public void paymentNoprice(Order order, Payment payment, Admin operator) throws Exception {
        Assert.notNull(order);
        Assert.isTrue(!order.isNew());
        Assert.notNull(payment);
        Assert.isTrue(payment.isNew());
        Assert.notNull(payment.getAmount());
        Assert.state(payment.getAmount().compareTo(BigDecimal.ZERO) > 0);

        payment.setSn(snDao.generate(Sn.Type.payment));
        payment.setOrder(order);
        paymentDao.persist(payment);

//        if (order.getMember() != null && Payment.Method.deposit.equals(payment.getMethod())) {
//            memberService.addBalance(order.getMember(), payment.getEffectiveAmount().negate(), DepositLog.Type.payment, operator, null);
//        }

        Setting setting = SettingUtils.get();
        if (Setting.StockAllocationTime.payment.equals(setting.getStockAllocationTime())) {
            allocateStock(order);
        }
        
        List<NoPricingOrder> nporderlist=noPricingOrderService.findByOrderSn(order.getSn());
        
        if(nporderlist.isEmpty()){
            throw new Exception();
        }
        String log="";
        NoPricingOrder npo=nporderlist.get(0);
        
        order.setAmountPaid(order.getAmountPaid().add(payment.getEffectiveAmount()));
        order.setFee(order.getFee().add(payment.getFee()));
//        System.out.println("order:"+order.getStatus());
        if (!order.hasExpired()) {
            BigDecimal nblance=order.getBalance()==null?BigDecimal.ZERO:order.getBalance();
            nblance=nblance.add(payment.getAmount());
            order.setBalance(nblance);
            if(com.arf.core.entity.NoPricingOrder.Status.submit.equals(npo.getStatus())){//用户预付款
                npo.setStatus(com.arf.core.entity.NoPricingOrder.Status.payment);
                log="用户完成预付款金额:"+payment.getAmount();
            }else{//商户定价后用户再次付款
//                order.setStatus(Order.Status.pendingReview);
                order.setStatus(Order.Status.pendingDelivery);
                npo.setStatus(com.arf.core.entity.NoPricingOrder.Status.userpayment);
                log="用户第二次支付金额:"+payment.getAmount();
            }
            order.setExpire(null);
        }
        noPricingOrderService.update(npo);

        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.payment);
        orderLog.setOperator(operator);
        orderLog.setContent(log);
        orderLog.setOrder(order);
        
        orderLogDao.persist(orderLog);

        mailService.sendPaymentOrderMail(order);
        smsService.sendPaymentOrderSms(order);
    }

	@Override
	public void refunds(Order order, Refunds refunds, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(order.getRefundableAmount().compareTo(BigDecimal.ZERO) > 0);
		Assert.notNull(refunds);
		Assert.isTrue(refunds.isNew());
		Assert.notNull(refunds.getAmount());
		Assert.state(refunds.getAmount().compareTo(BigDecimal.ZERO) > 0 && refunds.getAmount().compareTo(order.getRefundableAmount()) <= 0);

		refunds.setSn(snDao.generate(Sn.Type.refunds));
		refunds.setOrder(order);
		refundsDao.persist(refunds);
		Member member = order.getMember();
		if (Refunds.Method.deposit.equals(refunds.getMethod())) {
			//member对象的balance已经不在使用,由安心点在线账户余额代替
			memberService.addBalance(member, refunds.getAmount(), DepositLog.Type.refunds, operator, null);
		}

		//NOTE: 无论何种支付,这里都将退款打入安心点在线余额中并生成电子账户流转记录  modified by caolibao 2016-12-08 16:41
		SMemberAccount memberAccount = memberAccountService.findByUserNameUserType(member.getUsername(),
				(byte) SMemberAccount.Type.member.ordinal());
		BigDecimal basicAccount = memberAccount.getBasicAccount();
		basicAccount = basicAccount == null ? BigDecimal.ZERO:memberAccount.getBasicAccount();
		memberAccount.setBasicAccount(basicAccount.add(refunds.getAmount()));
		
		//生成电子账户流转记录
		RAccountTransferRecord transRecord = new RAccountTransferRecord();
		transRecord.setAccountBalance(memberAccount.getBasicAccount());
		transRecord.setAccountId(memberAccount.getId()+"");
		transRecord.setAccountNumber(memberAccount.getAccountNumber());
		transRecord.setAccountType((byte) RAccountTransferRecord.AccountType.basicAccount.ordinal());
		transRecord.setAmount(refunds.getAmount());
		transRecord.setBranchId(member.getBranchId());
		transRecord.setComment("商城退款");
		transRecord.setCommunityNumber(member.getCommunityNumber());
		transRecord.setIdentify(RAccountTransferRecord.genIdentify(memberAccount.getAccountNumber()));
		transRecord.setIsConfirmed((byte) RAccountTransferRecord.IsConfirmed.ok.ordinal());
		transRecord.setOperateTime(new Date());
		transRecord.setOperateType((byte) RAccountTransferRecord.OperateType.inter.ordinal());
		transRecord.setOrderNo(order.getSn());
		transRecord.setPropertyNumber(member.getPropertyNumber());
		transRecord.setSerialNumber(RAccountTransferRecord.genSerialNumber(memberAccount.getAccountNumber()));
		transRecord.setStatus((byte) RAccountTransferRecord.Status.finished.ordinal());
		transRecord.setType(RAccountTransferRecord.Type.chargeShopMallRefunds);
		transRecord.setUserName(member.getUsername());
		transRecord.setUserType((byte) RAccountTransferRecord.UserType.Member.ordinal());
		accountTransferRecordService.save(transRecord);
		memberAccountService.update(memberAccount);
		//TODO 如果使用了积分 则还需要退还积分操作
		//Note:END
		
		
		order.setAmountPaid(order.getAmountPaid().subtract(refunds.getAmount()));
		order.setRefundAmount(order.getRefundAmount().add(refunds.getAmount()));

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.refunds);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendRefundsOrderMail(order);
		smsService.sendRefundsOrderSms(order);
	}

	@Override
	public void shipping(Order order, Shipping shipping, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(order.getShippableQuantity() > 0);
		Assert.notNull(shipping);
		Assert.isTrue(shipping.isNew());
		Assert.notEmpty(shipping.getShippingItems());

		shipping.setSn(snDao.generate(Sn.Type.shipping));
		shipping.setOrder(order);
		shippingDao.persist(shipping);

		Setting setting = SettingUtils.get();
		if (Setting.StockAllocationTime.ship.equals(setting.getStockAllocationTime())) {
			allocateStock(order);//为订单分配库存
		}

		for (ShippingItem shippingItem : shipping.getShippingItems()) {
			OrderItem orderItem = order.getOrderItem(shippingItem.getSn());
			orderItemDao.refresh(orderItem, LockModeType.PESSIMISTIC_WRITE);
			if (orderItem == null || shippingItem.getQuantity() > orderItem.getShippableQuantity()) {
				throw new IllegalArgumentException();
			}
			orderItem.setShippedQuantity(orderItem.getShippedQuantity() + shippingItem.getQuantity());
			Product product = shippingItem.getProduct();
			productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);
			if (product != null) {
				if (shippingItem.getQuantity() > product.getStock()) {
					throw new IllegalArgumentException();
				}
				productService.addStock(product, -shippingItem.getQuantity(), StockLog.Type.stockOut, operator, null);
				productService.addAllocatedStock(product, -shippingItem.getQuantity());
			}
		}

		order.setShippedQuantity(order.getShippedQuantity() + shipping.getQuantity());
		if (order.getShippedQuantity() >= order.getQuantity()) {
			order.setStatus(Order.Status.delivered);
			order.setIsAllocatedStock(false);
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.shipping);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendShippingOrderMail(order);
		smsService.sendShippingOrderSms(order);
	}

	@Override
	public void shipping(Order order, Shipping shipping,Member operator) {
        Assert.notNull(order);
        Assert.isTrue(!order.isNew());
        Assert.state(order.getShippableQuantity() > 0);
        Assert.notNull(shipping);
        Assert.isTrue(shipping.isNew());
        Assert.notEmpty(shipping.getShippingItems());

        shipping.setSn(snDao.generate(Sn.Type.shipping));
        shipping.setOrder(order);
        shippingDao.persist(shipping);

        Setting setting = SettingUtils.get();
        if (Setting.StockAllocationTime.ship.equals(setting.getStockAllocationTime())) {
            allocateStock(order);//为订单分配库存
        }

        for (ShippingItem shippingItem : shipping.getShippingItems()) {
            OrderItem orderItem = order.getOrderItem(shippingItem.getSn());
            orderItemDao.refresh(orderItem, LockModeType.PESSIMISTIC_WRITE);
            if (orderItem == null || shippingItem.getQuantity() > orderItem.getShippableQuantity()) {
                throw new IllegalArgumentException();
            }
            orderItem.setShippedQuantity(orderItem.getShippedQuantity() + shippingItem.getQuantity());
            Product product = shippingItem.getProduct();
            productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);
            if (product != null) {
                if (shippingItem.getQuantity() > product.getStock()) {
                    throw new IllegalArgumentException();
                }
                productService.addStock(product, -shippingItem.getQuantity(), StockLog.Type.stockOut, operator, null);
                productService.addAllocatedStock(product, -shippingItem.getQuantity());
            }
        }

        order.setShippedQuantity(order.getShippedQuantity() + shipping.getQuantity());
        if (order.getShippedQuantity() >= order.getQuantity()) {
            order.setStatus(Order.Status.delivered);
            order.setIsAllocatedStock(false);
        }

        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.shipping);
        orderLog.setOperator(operator.getUsername());
        orderLog.setOrder(order);
        orderLogDao.persist(orderLog);

        mailService.sendShippingOrderMail(order);
        smsService.sendShippingOrderSms(order);
    }
	
	@Override
	public void returns(Order order, Returns returns, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(order.getReturnableQuantity() > 0);
		Assert.notNull(returns);
		Assert.isTrue(returns.isNew());
		Assert.notEmpty(returns.getReturnsItems());

		returns.setSn(snDao.generate(Sn.Type.returns));
		returns.setOrder(order);
		returnsDao.persist(returns);

		for (ReturnsItem returnsItem : returns.getReturnsItems()) {
			OrderItem orderItem = order.getOrderItem(returnsItem.getSn());
			orderItemDao.refresh(orderItem, LockModeType.PESSIMISTIC_WRITE);
			if (orderItem == null || returnsItem.getQuantity() > orderItem.getReturnableQuantity()) {
				throw new IllegalArgumentException();
			}
			orderItem.setReturnedQuantity(orderItem.getReturnedQuantity() + returnsItem.getQuantity());
		}

		order.setReturnedQuantity(order.getReturnedQuantity() + returns.getQuantity());

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.returns);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendReturnsOrderMail(order);
		smsService.sendReturnsOrderSms(order);
	}
	
	@Override
	public void returns(Order order, Returns returns,Member operator) {
        Assert.notNull(order);
        Assert.isTrue(!order.isNew());
        Assert.state(order.getReturnableQuantity() > 0);
        Assert.notNull(returns);
        Assert.isTrue(returns.isNew());
        Assert.notEmpty(returns.getReturnsItems());

        returns.setSn(snDao.generate(Sn.Type.returns));
        returns.setOrder(order);
        returnsDao.persist(returns);

        for (ReturnsItem returnsItem : returns.getReturnsItems()) {
            OrderItem orderItem = order.getOrderItem(returnsItem.getSn());
            orderItemDao.refresh(orderItem, LockModeType.PESSIMISTIC_WRITE);
            if (orderItem == null || returnsItem.getQuantity() > orderItem.getReturnableQuantity()) {
                throw new IllegalArgumentException();
            }
            orderItem.setReturnedQuantity(orderItem.getReturnedQuantity() + returnsItem.getQuantity());
        }

        order.setReturnedQuantity(order.getReturnedQuantity() + returns.getQuantity());

        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.returns);
        orderLog.setOperator(operator.getUsername());
        orderLog.setOrder(order);
        orderLogDao.persist(orderLog);

        mailService.sendReturnsOrderMail(order);
        smsService.sendReturnsOrderSms(order);
    }

	@Override
	public void receive(Order order, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(!order.hasExpired() && Order.Status.delivered.equals(order.getStatus()));

		order.setStatus(Order.Status.received);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.receive);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendReceiveOrderMail(order);
		smsService.sendReceiveOrderSms(order);
	}
	
	@Override
	public void receive(Order order, Member operator,boolean flag) {
        Assert.notNull(order);
        Assert.isTrue(!order.isNew());
        Assert.state(!order.hasExpired() && Order.Status.delivered.equals(order.getStatus()));

        order.setStatus(Order.Status.received);

        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.receive);
        orderLog.setOperator(operator.getUsername()+"[商户]");
        orderLog.setOrder(order);
        orderLogDao.persist(orderLog);

        mailService.sendReceiveOrderMail(order);
        smsService.sendReceiveOrderSms(order);
    }

	@Override
	public void complete(Order order, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(!order.hasExpired() && Order.Status.received.equals(order.getStatus()));

		Member member = order.getMember();
		if (order.getRewardPoint() > 0) {
			memberService.addPoint(member, order.getRewardPoint(), PointLog.Type.reward, operator, null);
		}
		if (CollectionUtils.isNotEmpty(order.getCoupons())) {
			for (Coupon coupon : order.getCoupons()) {
				couponCodeDao.build(coupon, member);
			}
		}
		if (order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0) {
			memberService.addAmount(member, order.getAmountPaid());
		}
		for (OrderItem orderItem : order.getOrderItems()) {
			Product product = orderItem.getProduct();
			if (product != null && product.getGoods() != null) {
				goodsService.addSales(product.getGoods(), orderItem.getQuantity());
				try{
					staticService.build(product.getGoods());
				}catch (Exception e) {
					log.error("商户完成订单时重新build 商品详情缓存出现异常[id="+ product.getGoods().getId() +","
							+ "goodName="+ product.getGoods().getName() +",admin=" + operator==null?"null":operator.getUsername() + "]",e);
				}
			}
		}

		order.setStatus(Order.Status.completed);
		order.setCompleteDate(new Date());

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.complete);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendCompleteOrderMail(order);
		smsService.sendCompleteOrderSms(order);
	}

	@Override
	public void complete(Order order, Member operator,boolean flag) {
        Assert.notNull(order);
        Assert.isTrue(!order.isNew());
        Assert.state(!order.hasExpired() && Order.Status.received.equals(order.getStatus()));

        Member member = order.getMember();
        if (order.getRewardPoint() > 0) {
            memberService.addPoint(member, order.getRewardPoint(), PointLog.Type.reward, operator, null,true);
        }
        if (CollectionUtils.isNotEmpty(order.getCoupons())) {
            for (Coupon coupon : order.getCoupons()) {
                couponCodeDao.build(coupon, member);
            }
        }
        if (order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0) {
            memberService.addAmount(member, order.getAmountPaid());
        }
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            if (product != null && product.getGoods() != null) {
                goodsService.addSales(product.getGoods(), orderItem.getQuantity());
                try{
					staticService.build(product.getGoods());
				}catch (Exception e) {
					log.error("用户完成订单时重新build 商品详情缓存出现异常[id="+ product.getGoods().getId() +","
							+ "goodName="+ product.getGoods().getName() +",member=" + operator==null?"null":operator.getUsername() + "]",e);
				}
            }
        }

        order.setStatus(Order.Status.completed);
        order.setCompleteDate(new Date());
        
        //未定价服务需要修改未定价订单状态
        if(Type.noPricing.equals(order.getType())){
            List<NoPricingOrder> list=order.getNoPricingOrderss();
            if(list!=null&&!list.isEmpty()){
                NoPricingOrder npo=list.get(0);
                npo.setStatus(com.arf.core.entity.NoPricingOrder.Status.completed);
            }
        }

        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.complete);
        orderLog.setOperator(operator.getUsername());
        orderLog.setOrder(order);
        orderLogDao.persist(orderLog);

        mailService.sendCompleteOrderMail(order);
        smsService.sendCompleteOrderSms(order);
    }
	
	@Override
	public void fail(Order order, Admin operator) {
		Assert.notNull(order);
		Assert.isTrue(!order.isNew());
		Assert.state(!order.hasExpired() && (Order.Status.pendingDelivery.equals(order.getStatus()) || Order.Status.delivered.equals(order.getStatus()) || Order.Status.received.equals(order.getStatus())));

		order.setStatus(Order.Status.failed);

		CouponCode couponCode = order.getCouponCode();
		if (couponCode != null) {
			couponCode.setIsUsed(false);
			couponCode.setUsedDate(null);
			order.setCouponCode(null);
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.fail);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		mailService.sendFailOrderMail(order);
		smsService.sendFailOrderSms(order);
	}

	@Override
	public void fail(Order order, Member operator,boolean flag) {
        Assert.notNull(order);
        Assert.isTrue(!order.isNew());
        Assert.state(!order.hasExpired() && (Order.Status.pendingDelivery.equals(order.getStatus()) || Order.Status.delivered.equals(order.getStatus()) || Order.Status.received.equals(order.getStatus())));

        order.setStatus(Order.Status.failed);

        CouponCode couponCode = order.getCouponCode();
        if (couponCode != null) {
            couponCode.setIsUsed(false);
            couponCode.setUsedDate(null);
            order.setCouponCode(null);
        }

        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.fail);
        orderLog.setOperator(operator.getUsername());
        orderLog.setOrder(order);
        orderLogDao.persist(orderLog);

        mailService.sendFailOrderMail(order);
        smsService.sendFailOrderSms(order);
    }
	
	@Override
	@Transactional
	public void delete(Order order) {
		if (order != null) {
			releaseAllocatedStock(order);
		}

		super.delete(order);
	}
	/**
     * 使用余额支付
     */
    public final static Integer USEBALANCE=0;
    /**
     * 不适用余额支付
     */
    public final static Integer NOUSEBALANCE=1;
    @Override
	@Transactional
    	public Order appcreate(Member member,Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo,String paymentPluginId,Integer useBalance,String imgurl,String memberAddress,String memberPoint) {
            Assert.notNull(type);
            Assert.notNull(cart);
            Assert.notNull(cart.getMember());
            Assert.notEmpty(cart.getCartItems());
            Assert.notNull(receiver);
    
            String log="";
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);
                if (product == null || !product.getIsMarketable() || cartItem.getQuantity() > product.getAvailableStock()) {
                    throw new IllegalArgumentException();
                }
            }
    
            for (Product gift : cart.getGifts()) {
                productDao.refresh(gift, LockModeType.PESSIMISTIC_WRITE);
                if (!gift.getIsMarketable() || gift.getIsOutOfStock()) {
                    throw new IllegalArgumentException();
                }
            }
    
            Setting setting = SettingUtils.get();
            memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
            
            BigDecimal amountpaid=BigDecimal.ZERO;//获取用户的余额和
    
            Order order = new Order();
            order.setSn(snDao.generate(Sn.Type.order));
            order.setType(type);
            order.setPrice(cart.getPrice());
            order.setFee(BigDecimal.ZERO);
            order.setFreight(!cart.isFreeShipping() ? shippingMethodService.calculateFreight(shippingMethod, receiver, cart.getWeight()) : BigDecimal.ZERO);
            order.setPromotionDiscount(cart.getDiscount());
            order.setOffsetAmount(BigDecimal.ZERO);
            order.setAmountPaid(BigDecimal.ZERO);
            order.setRefundAmount(BigDecimal.ZERO);
            order.setRewardPoint(cart.getEffectiveRewardPoint());
            order.setExchangePoint(cart.getExchangePoint());
            order.setWeight(cart.getWeight());
            order.setQuantity(cart.getQuantity());
            order.setShippedQuantity(0);
            order.setReturnedQuantity(0);
            order.setConsignee(receiver.getConsignee());
            order.setAreaName(receiver.getAreaName());
            order.setAddress(receiver.getAddress());
            order.setZipCode(receiver.getZipCode());
            order.setPhone(receiver.getPhone());
            order.setArea(receiver.getArea());
            order.setMemo(memo);
            order.setIsAllocatedStock(false);
            order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);
            order.setPaymentMethod(paymentMethod);
            order.setShippingMethod(shippingMethod);
            order.setMember(member);
            order.setPromotionNames(cart.getPromotionNames());
            order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));
            order.setMemberAddress(memberAddress);
            order.setMemberPoint(memberPoint);
            if (couponCode != null) {
                couponCodeDao.refresh(couponCode, LockModeType.PESSIMISTIC_WRITE);
                if (!cart.isCouponAllowed() || !cart.isValid(couponCode)) {
                    throw new IllegalArgumentException();
                }
                BigDecimal couponDiscount = cart.getEffectivePrice().subtract(couponCode.getCoupon().calculatePrice(cart.getEffectivePrice(), cart.getQuantity()));
                order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount : BigDecimal.ZERO);
                order.setCouponCode(couponCode);
                couponCode.setIsUsed(true);
                couponCode.setUsedDate(new Date());
            } else {
                order.setCouponDiscount(BigDecimal.ZERO);
            }
    
            order.setTax(calculateTax(order));
            order.setAmount(calculateAmount(order));
    
            if (balance != null && (balance.compareTo(BigDecimal.ZERO) < 0 || balance.compareTo(member.getBalance()) > 0 || balance.compareTo(order.getAmount()) > 0)) {
                throw new IllegalArgumentException();
            }
            BigDecimal amountPayable = balance != null && balance.compareTo(BigDecimal.ZERO) > 0 ? order.getAmount().subtract(balance) : order.getAmount();
            if ((amountPayable.compareTo(BigDecimal.ZERO) > 0 && paymentMethod == null) || amountPayable.compareTo(BigDecimal.ZERO) <0 && paymentMethod != null) {
                throw new IllegalArgumentException();
            }
    
            if (amountPayable.compareTo(BigDecimal.ZERO) <= 0) {
                order.setStatus(Order.Status.pendingReview);
            } else {
                order.setStatus(paymentMethod != null && PaymentMethod.Type.deliveryAgainstPayment.equals(paymentMethod.getType()) ? Order.Status.pendingPayment : Order.Status.pendingReview);
            }
    
            if (paymentMethod != null && paymentMethod.getTimeout() != null && Order.Status.pendingPayment.equals(order.getStatus())) {
                order.setExpire(DateUtils.addMinutes(new Date(), paymentMethod.getTimeout()));
            }
    
            List<OrderItem> orderItems = order.getOrderItems();
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                OrderItem orderItem = new OrderItem();
                orderItem.setSn(product.getSn());
                orderItem.setName(product.getName());
                orderItem.setType(product.getType());
                orderItem.setPrice(cartItem.getPrice());
                orderItem.setWeight(product.getWeight());
                orderItem.setThumbnail(product.getThumbnail());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setShippedQuantity(0);
                orderItem.setReturnedQuantity(0);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setOrder(order);
                orderItem.setSpecifications(product.getSpecifications());
                orderItems.add(orderItem);
                
                if(type!=Type.insurance){
                    log+=member.getUsername()+"抵扣之前现金劵:"+member.getBalance()+",消费劵:"+member.getVouchers();
                    BigDecimal vs=product.getGoods().getVouchers();
                    if(vs==null){
                        vs=BigDecimal.ZERO;
                    }
                    if(vs.compareTo(member.getVouchers())>0){//如果商品的抵扣价大于用户的消费劵
                        amountpaid=member.getVouchers();//设置已支付金额
                        order.setVouchers(member.getVouchers());
                        member.setVouchers(BigDecimal.ZERO);//设置会员的抵扣劵
                    }else{
                        amountpaid=vs;
                        member.setVouchers(member.getVouchers().subtract(vs));
                        order.setVouchers(vs);
                    }
                    order.setAmountPaid(order.getAmountPaid().add(amountpaid));//重新设置优惠金额
                    if(useBalance==USEBALANCE){//如果使用余额付款，扣除余额
                        if(member.getBalance().compareTo(BigDecimal.ZERO)>0){//如果会员余额大于0
                            //剩下的需支付的金额
                            BigDecimal b2=order.getAmount().subtract(order.getAmountPaid());
                            if(b2.compareTo(member.getBalance())>0){//如果用户余额不足
                                amountpaid=member.getBalance();
                                order.setBalance(member.getBalance());
                                member.setBalance(BigDecimal.ZERO);
                            }else{
                                amountpaid=b2;
                                member.setBalance(member.getBalance().subtract(b2));
                                order.setBalance(b2);
                            }
                            order.setAmountPaid(order.getAmountPaid().add(amountpaid));//重新设置优惠金额
                        }else{
                            order.setBalance(BigDecimal.ZERO);
                        }
                    }else{
                        order.setBalance(BigDecimal.ZERO);
                    }
                }
                order.setMemberBiz(product.getGoods().getMemberBiz());
//                amountpaid.add();
            }
    
            for (Product gift : cart.getGifts()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setSn(gift.getSn());
                orderItem.setName(gift.getName());
                orderItem.setType(gift.getType());
                orderItem.setPrice(BigDecimal.ZERO);
                orderItem.setWeight(gift.getWeight());
                orderItem.setThumbnail(gift.getThumbnail());
                orderItem.setQuantity(1);
                orderItem.setShippedQuantity(0);
                orderItem.setReturnedQuantity(0);
                orderItem.setProduct(gift);
                orderItem.setOrder(order);
                orderItem.setSpecifications(gift.getSpecifications());
                orderItems.add(orderItem);
            }
            order.setMember(member);
            
            if(order.getAmountPayable().compareTo(BigDecimal.ZERO)<=0){//如果订单的应付金额小于0
                if(type!=Type.noPricing&&type!=Type.insurance){
                    order.setStatus(Order.Status.pendingDelivery);
                }
            }
            
            orderDao.persist(order);
            //未定价商品
            if(type==Type.noPricing){
                /**
                 * 如果用户预付款小于等于0
                 * 
                 */
                NoPricingOrder nopricingOrder=new NoPricingOrder();
                if(order.getAmountPayable().compareTo(BigDecimal.ZERO)>0){
                    nopricingOrder.setPrice(order.getAmountPayable());
                    nopricingOrder.setStatus(com.arf.core.entity.NoPricingOrder.Status.submit);
                }else{
                    nopricingOrder.setPrice(BigDecimal.ZERO);
                    nopricingOrder.setStatus(com.arf.core.entity.NoPricingOrder.Status.payment);
                }
                nopricingOrder.setOrder(order);
                noPricingOrderService.save(nopricingOrder);
            }else if(type==Type.insurance){
                Insurance insurance=new Insurance();
                insurance.setOrder(order);
                insurance.setStatus(com.arf.core.entity.Insurance.Status.submit);
                insurance.setLastyearImage(imgurl);
                
                insuranceService.save(insurance);
            }
            
            memberDao.flush();
            memberDao.refresh(member);
    
            OrderLog orderLog = new OrderLog();
            orderLog.setType(OrderLog.Type.create);
            orderLog.setContent(log);
            orderLog.setOrder(order);
            orderLogDao.persist(orderLog);
    
            if (Setting.StockAllocationTime.order.equals(setting.getStockAllocationTime()) || (Setting.StockAllocationTime.payment.equals(setting.getStockAllocationTime()) && order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0)) {
                allocateStock(order);
            }
    
            if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0) {
                Payment payment = new Payment();
                payment.setMethod(Payment.Method.deposit);
                payment.setFee(BigDecimal.ZERO);
                payment.setAmount(balance);
                payment.setOrder(order);
                payment(order, payment, null);
            }
    
            if (order.getExchangePoint() != null && order.getExchangePoint() > 0) {
                memberService.addPoint(member, -order.getExchangePoint(), PointLog.Type.exchange, null, null);
            }
    
            if (paymentMethod != null && PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
                lock(order, member);
            }
    
            mailService.sendCreateOrderMail(order);
            smsService.sendCreateOrderSms(order);
    
            if (!cart.isNew()) {
                cartDao.remove(cart);
            }
    
            return order;
        }
    
    @Override
	@Transactional
    public void appcancel(Member member,Order order) {
            Assert.notNull(order);
            Assert.isTrue(!order.isNew());
            Assert.state(!order.hasExpired() && (Order.Status.pendingPayment.equals(order.getStatus()) || Order.Status.pendingDelivery.equals(order.getStatus())));

            order.setStatus(Order.Status.canceled);
            order.setExpire(null);

            CouponCode couponCode = order.getCouponCode();
            if (couponCode != null) {
                couponCode.setIsUsed(false);
                couponCode.setUsedDate(null);
                order.setCouponCode(null);
            }
            //用户取消的时候不退钱给用户
//            member.setBalance(member.getBalance().add(order.getBalance()));
//            member.setVouchers(member.getVouchers().add(order.getVouchers()));
//            if(Order.Status.pendingReview.equals(order.getStatus())){//如果是等待审核的商品，应将用户已支付的金额退还懂啊消费劵
//                member.setBalance(member.getBalance().add(order.getAmountPayable()));
//            }
            OrderLog orderLog = new OrderLog();
            orderLog.setType(OrderLog.Type.cancel);
            orderLog.setContent(member.getUsername()+"取消订单:退还现金劵"+order.getBalance()+",消费劵"+order.getVouchers());
            if(Order.Status.pendingReview.equals(order.getStatus())){//如果是等待审核的商品
                orderLog.setContent(orderLog.getContent()+",支付金额"+order.getAmountPayable());
            }
            orderLog.setOrder(order);
            orderLogDao.persist(orderLog);

            mailService.sendCancelOrderMail(order);
            smsService.sendCancelOrderSms(order);
        }
    
    @Override
	@Transactional   
    public void appcancelNopricing(Member member,Order order,String memo,NoPricingOrder npo) {
            Assert.notNull(order);
            Assert.isTrue(!order.isNew());
            Assert.state(!order.hasExpired());

            order.setStatus(Order.Status.canceled);
            order.setExpire(null);

            CouponCode couponCode = order.getCouponCode();
            if (couponCode != null) {
                couponCode.setIsUsed(false);
                couponCode.setUsedDate(null);
                order.setCouponCode(null);
            }
//            member.setBalance(member.getBalance().add(order.getBalance()));
//            member.setVouchers(member.getVouchers().add(order.getVouchers()));
//            if(com.arf.core.entity.NoPricingOrder.Status.payment.equals(npo.getStatus())){//只有订单状态Wie支付时，才退回金额
//                //取消付款时，返回用户金额，将预付款金额还给用户，并修改订单状态
//                member.setBalance(member.getBalance().add(npo.getPrice()));//添加预付款金额
//            }
            npo.setMemo(memo);
            npo.setStatus(com.arf.core.entity.NoPricingOrder.Status.canceled);
            noPricingOrderService.update(npo);
            memberService.update(member);
            redisService.hdel(String.format(CacheNameDefinition.Member_DB_Redis, member.getUsername()),member.getUsername());
            
            OrderLog orderLog = new OrderLog();
            orderLog.setType(OrderLog.Type.cancel);
            orderLog.setOrder(order);
            orderLogDao.persist(orderLog);

            mailService.sendCancelOrderMail(order);
            smsService.sendCancelOrderSms(order);
        }
    	
    @Override
	@Transactional(readOnly = true)
    public Order appgenerate(Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, Invoice invoice, BigDecimal balance, String memo) throws Exception{
            Assert.notNull(type);
            Assert.notNull(cart);
//            Assert.notNull(cart.getMember());
            Assert.notEmpty(cart.getCartItems());

            Setting setting = SettingUtils.get();
            Member member = cart.getMember();
            
            Order order = new Order();
            order.setType(type);
            order.setPrice(cart.getPrice());
            order.setFee(BigDecimal.ZERO);
            order.setPromotionDiscount(cart.getDiscount());
            order.setOffsetAmount(BigDecimal.ZERO);
            order.setRefundAmount(BigDecimal.ZERO);
            order.setRewardPoint(cart.getEffectiveRewardPoint());
            order.setExchangePoint(cart.getExchangePoint());
            order.setWeight(cart.getWeight());
            order.setQuantity(cart.getQuantity());
            order.setShippedQuantity(0);
            order.setReturnedQuantity(0);
            order.setMemo(memo);
            order.setIsAllocatedStock(false);
            order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);
            order.setPaymentMethod(paymentMethod);
            order.setMember(member);
            order.setPromotionNames(cart.getPromotionNames());
            order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));

            if (shippingMethod != null && shippingMethod.isSupported(paymentMethod) && !cart.isFreeShipping()) {
                order.setFreight(shippingMethodService.calculateFreight(shippingMethod, receiver, cart.getWeight()));
                order.setShippingMethod(shippingMethod);
            } else {
                order.setFreight(BigDecimal.ZERO);
            }

            if (couponCode != null && cart.isCouponAllowed() && cart.isValid(couponCode)) {
                BigDecimal couponDiscount = cart.getEffectivePrice().subtract(couponCode.getCoupon().calculatePrice(cart.getEffectivePrice(), cart.getQuantity()));
                order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount : BigDecimal.ZERO);
                order.setCouponCode(couponCode);
            } else {
                order.setCouponDiscount(BigDecimal.ZERO);
            }

            order.setTax(calculateTax(order));
            order.setAmount(calculateAmount(order));

            if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0 && balance.compareTo(member.getBalance()) <= 0 && balance.compareTo(order.getAmount()) <= 0) {
                order.setAmountPaid(balance);
            } else {
                order.setAmountPaid(BigDecimal.ZERO);
            }

            if (receiver != null) {
                order.setConsignee(receiver.getConsignee());
                order.setAreaName(receiver.getAreaName());
                order.setAddress(receiver.getAddress());
                order.setZipCode(receiver.getZipCode());
                order.setPhone(receiver.getPhone());
                order.setArea(receiver.getArea());
            }

            List<OrderItem> orderItems = order.getOrderItems();
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                if (product != null) {
                    if(Type.general.equals(type)){
                        if(!com.arf.core.entity.Goods.Type.general.equals(product.getType())){
                            throw new Exception("商品类型不匹配");
                        }
                    }
                    if(Type.pricing.equals(type)){
                        if(!com.arf.core.entity.Goods.Type.pricing.equals(product.getType())){
                            throw new Exception("商品类型不匹配");
                        }
                    }
                    if(Type.noPricing.equals(type)){
                        if(!com.arf.core.entity.Goods.Type.noPricing.equals(product.getType())){
                            throw new Exception("商品类型不匹配");
                        }
                    }
                    if(Type.insurance.equals(type)){
                        if(!com.arf.core.entity.Goods.Type.insurance.equals(product.getType())){
                            throw new Exception("商品不属于保险");
                        }
                    }
                    OrderItem orderItem = new OrderItem();
                    orderItem.setSn(product.getSn());
                    orderItem.setName(product.getName());
                    orderItem.setType(product.getType());
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setWeight(product.getWeight());
                    orderItem.setThumbnail(product.getThumbnail());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setShippedQuantity(0);
                    orderItem.setReturnedQuantity(0);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setOrder(order);
                    orderItem.setSpecifications(product.getSpecifications());
                    orderItems.add(orderItem);
                }
            }

            for (Product gift : cart.getGifts()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setSn(gift.getSn());
                orderItem.setName(gift.getName());
                orderItem.setType(gift.getType());
                orderItem.setPrice(BigDecimal.ZERO);
                orderItem.setWeight(gift.getWeight());
                orderItem.setThumbnail(gift.getThumbnail());
                orderItem.setQuantity(1);
                orderItem.setShippedQuantity(0);
                orderItem.setReturnedQuantity(0);
                orderItem.setProduct(gift);
                orderItem.setOrder(order);
                orderItem.setSpecifications(gift.getSpecifications());
                orderItems.add(orderItem);
            }

            return order;
        }

    @Override
    public Page<Order> findPagebiz(Type type,
                com.arf.core.entity.Order.Status status, Member member,
                Boolean isPendingReceive, Boolean isPendingRefunds,
                Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
            return orderDao.findPagebiz(type, status, member, isPendingReceive, isPendingRefunds, isAllocatedStock, hasExpired, pageable);
        }
   		
    private final static String[] types=new String[]{"member","member_biz"};
    private final static String[] deltypes=new String[]{"memberdel","memberbizdel"};
    @Override
	public List<Map<String,Object>> getOrderSize(Member member,Integer type){
        String sql="select 'all' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'pendingPayment' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.pendingPayment.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'pendingReview' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.pendingReview.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'pendingDelivery' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.pendingDelivery.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'delivered' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.delivered.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'received' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.received.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'completed' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.completed.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'failed' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.failed.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'canceled' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.canceled.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        sql+=" union all select 'denied' name,count(1) cou from xx_order where "+types[type]+"="+member.getId()+" and status="+Status.denied.ordinal()+" and ("+deltypes[type]+" is null or "+deltypes[type]+"=0)";
        
        return orderDao.getOrderSize(sql);
    }
    
    /**
     * 订单退款
     */
    @Override
	@Transactional(readOnly = true)
    public void retOrderMoney(Admin member,Order order){
        //使用的现金劵
        BigDecimal balance=order.getBalance()==null?BigDecimal.ZERO:order.getBalance();
        //使用的消费劵
        BigDecimal vouchers=order.getVouchers()==null?BigDecimal.ZERO:order.getVouchers();
        //已返还的现金劵
        BigDecimal retBalance=order.getRetBalance()==null?BigDecimal.ZERO:order.getRetBalance();
        //已返还的消费劵
        BigDecimal retVouchers=order.getRetVouchers()==null?BigDecimal.ZERO:order.getRetVouchers();
        
        Member m=order.getMember();
        m.setBalance(m.getBalance().add(balance));
        m.setVouchers(m.getVouchers().add(vouchers));
        
        retBalance=retBalance.add(balance);
        retVouchers=retVouchers.add(vouchers);
        
        order.setRetBalance(retBalance);
        order.setRetVouchers(retVouchers);
        order.setBalance(BigDecimal.ZERO);
        order.setVouchers(BigDecimal.ZERO);
        order.setStatus(Status.failretmoney);
        if(Type.noPricing.equals(order.getType())){//如果是未定价商品
            List<NoPricingOrder> list=order.getNoPricingOrderss();
            if(list!=null&&!list.isEmpty()){
                NoPricingOrder npo=list.get(0);
                npo.setStatus(com.arf.core.entity.NoPricingOrder.Status.failretmoney);
            }
        }
        
        orderDao.flush();
        orderDao.refresh(order);
        
        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.cancel);
        orderLog.setOrder(order);
        orderLog.setContent("订单退款成功,现金劵:"+retBalance+";消费劵:"+retVouchers);
        if(member==null){
            orderLog.setOperator(memberService.getCurrentUsername());
        }else{
            orderLog.setOperator(member.getUsername());
        }
        orderLogDao.persist(orderLog);
    }
    
    /**
     * 订单退款
     */
    @Override
	@Transactional(readOnly = true)
    public void noretOrderMoney(Admin member,Order order){
        order.setStatus(Status.failnoretmoney);
        if(Type.noPricing.equals(order.getType())){//如果是未定价商品
            List<NoPricingOrder> list=order.getNoPricingOrderss();
            if(list!=null&&!list.isEmpty()){
                NoPricingOrder npo=list.get(0);
                npo.setStatus(com.arf.core.entity.NoPricingOrder.Status.failnoretmoney);
            }
        }
        OrderLog orderLog = new OrderLog();
        orderLog.setType(OrderLog.Type.cancel);
        orderLog.setOrder(order);
        orderLog.setContent("取消订单失败，未退款");
        orderLog.setOperator(member.getUsername());
        orderLogDao.persist(orderLog);
    }

    @Override
    public Long getNoReviewOrderId(Long goodId, Long memberId) {
        return orderDao.getNoReviewOrderId(goodId, memberId);
    }
}