package com.foryou.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foryou.webapp.entity.BestOfferProduct;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.repository.BestOfferRepository;

@Controller
public class MainController {

	@Autowired
	private BestOfferRepository bestOfferRepository;

	@GetMapping({ "/home", "/index","/"})
	public String HomePage(Model model) {
		List<BestOfferProduct> bestofferproduct = bestOfferRepository.findAll();
		model.addAttribute("bestofferproduct", bestofferproduct);
		return "navigation";
	}
	
	@GetMapping("/furniture-store/product/upload")
	public String FormToAddProduct(Model model) {
		Image image = new Image();
		model.addAttribute("image", image);
		return "form-for-add-product";
	}

	@GetMapping("/cart")
	public String cartPage() {
		return "productCart";
	}

	@GetMapping("/aboutSection")
	public String getAboutSection(Model model) {
		return "contact";
	}
	@GetMapping("/contact")
	public String getFooter(Model model) {
		return "contact";
	}
	@GetMapping("/invoice")
	public String getInvoice(Model model) {
		return "Invoice";
	}
	@GetMapping("/user/payment")
	public String getPayment(Model model) {
		return "paymentPage";
	}
}
