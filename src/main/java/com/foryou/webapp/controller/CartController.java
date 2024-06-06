package com.foryou.webapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foryou.webapp.currency.IndianCurrencyFormatter;
import com.foryou.webapp.entity.CartItem;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.entity.User;
import com.foryou.webapp.repository.CartItemRepository;
import com.foryou.webapp.repository.ImageRepository;
import com.foryou.webapp.service.CartService;
import com.foryou.webapp.service.ProductService;
import com.foryou.webapp.service.UserService;

@Controller
public class CartController {

	private ImageRepository imageRepository;

	private CartItemRepository cartItemRepository;

	private ProductService productService;

	private CartService cartService;

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService, ProductService productService, CartService cartService,
			CartItemRepository cartItemRepository, ImageRepository imageRepository) {
		this.userService = userService;
		this.productService = productService;
		this.cartService = cartService;
		this.cartItemRepository = cartItemRepository;
		this.imageRepository = imageRepository;
	}

	// Adding product to cart
	@PostMapping("/addToCart/{productId}")
	public String addToCartItem(@PathVariable Long productId, @RequestParam int quantity, Model model) {
		// Retrieve the authenticated user's username from the security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName(); // Get the username of the authenticated user

		// Find the user by username
		User user = userService.findByUserName(currentPrincipalName);

		if (user == null) {
			throw new IllegalArgumentException("User not found with username: " + currentPrincipalName);
		}
		System.out.println("I am in post mapping in cart page....");
		Image product = imageRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + productId));

		// Check if the product already exists in the user's cart
		CartItem existingCartItem = cartItemRepository.findByUserAndImage(user, product);
		if (existingCartItem != null) {
			// Product already exists, update its quantity
			existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
			cartItemRepository.save(existingCartItem); // Update the existing cart item
		} else {
			// Product does not exist in the cart, add it as a new item
			CartItem cartItem = new CartItem();
			cartItem.setImage(product);
			cartItem.setQuantity(quantity);
			cartItem.setUser(user);
			cartItemRepository.save(cartItem);
			
		}
		String successMessage = "Product added to cart successfully!";
		model.addAttribute("successMessage", successMessage);

		return "redirect:/products/cart-items"; // Redirect to the product list page
	}

	@GetMapping("/product/{id}/addedToCart")
	public String getProductById(@PathVariable Long id, Model model) {
		Image product = productService.getProductById(id);
		model.addAttribute("product", product);
		return "product-details";
	}

	// Shopping cart
	@GetMapping("/products/cart-items")
	public String viewCart(Model model, Principal principal) {
		// Retrieve the username of the authenticated user
		String username = principal.getName();

		// Find the user by username to get the user ID
		User user = userService.findByUserName(username);

		if (user == null) {
			// Handle case where user is not found (e.g., redirect to login page or show an
			// error)
			return "redirect:/login";
		}

		// Retrieve cart items based on the user ID
		List<CartItem> cartItems = cartService.getCartItemsByUserId(user.getId());

		// Perform any necessary calculations or formatting on cart items
		for (CartItem item : cartItems) {
			if (IndianCurrencyFormatter.isValidPrice(item.getImage().getProductPrice())) {
				double price = Double.parseDouble(item.getImage().getProductPrice());
				System.out.println("Total quantity is : "+price);

				double total = price * item.getQuantity();
				
				System.out.println("Total price is : "+total);
				
				String formattedPrice = IndianCurrencyFormatter.formatPriceToIndianCurrency(total);
				item.getImage().setProductPrice(formattedPrice); // Set the formatted price
			} else {
				// Set default value if the price is not a valid number
				item.getImage().getProductPrice();
			}
		}

		// Calculate total price
		Double totalPriceDouble = calculateTotalPrice1(cartItems);
		String totalAmount = IndianCurrencyFormatter.formatPriceToIndianCurrency(totalPriceDouble);

		// Add cart items and total amount to the model
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalAmount", totalAmount);

		return "productCart";
	}

	// Delete product from cart
	@DeleteMapping("/cart/{productId}")
	public String deleteCartItem(@PathVariable("productId") Long productId) {
		System.out.println("This is from /cart/{cartId}");
		cartService.deleteCartItem(productId);
		return "redirect:/products/cart-items"; // Redirect to the cart page after deletion
	}

	// Calculating total cart amount..
	private double calculateTotalPrice1(List<CartItem> cartItems) {
		double totalPrice = 0.0;
		for (CartItem item : cartItems) {
			if (item == null || item.getImage() == null || item.getImage().getProductPrice() == null) {
				System.err.println("Invalid item or price data: " + item);
				continue; // Skip invalid items
			}

			String priceString = item.getImage().getProductPrice();
			if (priceString.isEmpty()) {
				System.err.println("Empty price string for item: " + item.getImage().getProductName());
				continue; // Skip items with empty price strings
			}

			// Remove any non-numeric characters except for dots (.) and commas (,) for
			// decimal values
			priceString = priceString.replaceAll("[^\\d.,]", "");
			// Remove commas to handle thousands separator
			priceString = priceString.replace(",", "");

			System.out.println("Price string for item " + item.getImage().getProductName() + ": " + priceString);

			try {
				double price = Double.parseDouble(priceString);
				System.out.println("Parsed price for item " + item.getImage().getProductName() + ": " + price);
				totalPrice += price;
				System.out.println(totalPrice);
			} catch (NumberFormatException e) {
				// Log invalid price format and continue to the next item
				System.err.println("Invalid price format for item: " + item.getImage().getProductName());
				System.err.println("Price value: " + priceString);
			}
		}
		System.out.println("Total price: " + totalPrice);
		return totalPrice;
	}

}
