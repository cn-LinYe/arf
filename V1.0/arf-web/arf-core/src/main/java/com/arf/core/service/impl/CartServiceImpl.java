/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CartDao;
import com.arf.core.dao.CartItemDao;
import com.arf.core.entity.Cart;
import com.arf.core.entity.CartItem;
import com.arf.core.entity.Member;
import com.arf.core.entity.Product;
import com.arf.core.service.CartService;
import com.arf.core.service.MemberService;
import com.arf.core.util.WebUtils;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Service - 购物车
 * 
 * @author arf
 * @version 4.0
 */
@Service("cartServiceImpl")
public class CartServiceImpl extends BaseServiceImpl<Cart, Long> implements CartService {

	@Resource(name = "cartDaoImpl")
	private CartDao cartDao;
	@Resource(name = "cartItemDaoImpl")
	private CartItemDao cartItemDao;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Override
	protected BaseDao<Cart, Long> getBaseDao() {
		return cartDao;
	}

	public Cart getCurrent() {
		Cart cart;
		Member member = memberService.getCurrent(true);
		if (member != null) {
			cart = member.getCart();
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = requestAttributes != null ? ((ServletRequestAttributes) requestAttributes).getRequest() : null;
			if (request == null) {
				return null;
			}
			String key = WebUtils.getCookie(request, Cart.KEY_COOKIE_NAME);
			cart = cartDao.findByKey(key);
		}
		if (cart != null && cart.hasExpired()) {
			cartDao.remove(cart);
			return null;
		}
		return cart;
	}

	public Cart add(Product product, int quantity) {
		Assert.notNull(product);
		Assert.state(quantity > 0);

		Cart cart = getCurrent();
		if (cart == null) {
			cart = new Cart();
			Member member = memberService.getCurrent();
			if (member != null && member.getCart() == null) {
				cart.setMember(member);
			}
			cartDao.persist(cart);
		}
		if (cart.contains(product)) {
			CartItem cartItem = cart.getCartItem(product);
			cartItem.add(quantity);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItemDao.persist(cartItem);
			cart.getCartItems().add(cartItem);
		}
		return cart;
	}

	public void merge(Member member, Cart cart) {
		if (member == null || cart == null || cart.getMember() != null) {
			return;
		}
		Cart memberCart = member.getCart();
		if (memberCart != null && memberCart.hasExpired()) {
			cartDao.remove(memberCart);
			memberCart = null;
		}
		if (cart.hasExpired()) {
			cartDao.remove(cart);
			return;
		}
		if (memberCart != null) {
			if (cart.getCartItems() != null) {
				for (CartItem cartItem : cart.getCartItems()) {
					Product product = cartItem.getProduct();
					if (memberCart.contains(product)) {
						CartItem memberCartItem = memberCart.getCartItem(product);
						if (CartItem.MAX_QUANTITY != null && memberCartItem.getQuantity() + cartItem.getQuantity() > CartItem.MAX_QUANTITY) {
							continue;
						}
						memberCartItem.add(cartItem.getQuantity());
					} else {
						if (Cart.MAX_CART_ITEM_COUNT != null && memberCart.getCartItems().size() >= Cart.MAX_CART_ITEM_COUNT) {
							continue;
						}
						if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() > CartItem.MAX_QUANTITY) {
							continue;
						}
						CartItem item = new CartItem();
						item.setQuantity(cartItem.getQuantity());
						item.setProduct(cartItem.getProduct());
						item.setCart(memberCart);
						cartItemDao.persist(item);
						memberCart.getCartItems().add(cartItem);
					}
				}
			}
			cartDao.remove(cart);
		} else {
			cart.setMember(member);
			member.setCart(cart);
		}
	}

	public void evictExpired() {
		cartDao.evictExpired();
	}

	
	public Cart getCurrent(Member member) {
        Cart cart;
        if (member != null) {
            cart = member.getCart();
        } else {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = requestAttributes != null ? ((ServletRequestAttributes) requestAttributes).getRequest() : null;
            if (request == null) {
                return null;
            }
            String key = WebUtils.getCookie(request, Cart.KEY_COOKIE_SERVICENAME);
            cart = cartDao.findByKey(key);
        }
        if (cart != null && cart.hasExpired()) {
            cartDao.remove(cart);
            return null;
        }
        return cart;
    }
	
	public Cart add(Member member,Product product, int quantity) {
        Assert.notNull(product);
        Assert.state(quantity > 0);

//        Cart cart = getCurrent(member);
        Cart cart=null;
        if(member!=null&&member.getCart()!=null){
            cartDao.remove(member.getCart());
        }
        if (member!=null) {
        	member.setCart(null);
		}
        if (cart == null) {
            cart = new Cart();
            if (member != null && member.getCart() == null) {
                cart.setMember(member);
            }
            cartDao.persist(cart);
        }
        if (cart.contains(product)) {
            CartItem cartItem = cart.getCartItem(product);
            cartItem.add(quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItemDao.persist(cartItem);
            cart.getCartItems().add(cartItem);
        }
        return cart;
    }
}