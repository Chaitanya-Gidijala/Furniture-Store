package com.foryou.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foryou.webapp.entity.CartItem;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.entity.User;
import com.foryou.webapp.repository.CartItemRepository;

@Service
public class CartService {
	// @Autowired
	// private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	public void addToCart(Image product, int quantity, String username) {
		CartItem cartItem = new CartItem();
		cartItem.setImage(product);
		cartItem.setQuantity(quantity);
		User user = new User();
		user.getUserName();
		cartItem.setUser(user);
		// cartItem.setCart(cart);
		cartItemRepository.save(cartItem);
	}

	public List<CartItem> getCartItems() {
		return cartItemRepository.findAll();
	}

	public void deleteCartItem(Long id) {
		cartItemRepository.deleteById(id);
	}

	public List<CartItem> getCartItemsByUserId(Long userId) {
		return cartItemRepository.findByUserId(userId);
	}

}
