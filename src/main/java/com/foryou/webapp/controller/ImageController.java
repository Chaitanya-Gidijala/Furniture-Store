package com.foryou.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foryou.webapp.currency.IndianCurrencyFormatter;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.service.ProductService;

@CrossOrigin(origins =  "http://localhost:3000")
@Controller
public class ImageController {
    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("category") String category,
    						  @RequestParam("productName") String productName,
    						  @RequestParam("productPath") String productpath,
    						  @RequestParam("originalPrice") String originalPrice,
    						  @RequestParam("extraOfferPrice") String extraOfferPrice,
    						  @RequestParam("productPrice") String productPrice,
                              @RequestParam("productDescription") String description,
                              RedirectAttributes redirectAttributes) {
        try {
            // Save product details to the database
            Image image = new Image();
            System.out.println("in post method");
            image.setProductCategory(category);
            image.setProductName(productName);
            image.setProductPath(productpath);
            image.setOriginalPrice(originalPrice); 
            image.setExtraOfferPrice(extraOfferPrice); 
            image.setProductPrice(productPrice); 
            image.setProductDescription(description);
            
            productService.saveImage(image);

            redirectAttributes.addFlashAttribute("message", "Image URL saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to save image URL!");
        }
        return "redirect:/furniture-store/product/upload";
    }

    @GetMapping("/images/{category}")
    public String getImagesByCategory(
    		@PathVariable("category") String category,
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page
    		) {
    	
        int pageSize = 9; // Number of images per page
        
        // Create a page request for pagination
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Image> pageImages = productService.getImagesByProductCategory(category, pageable);
       
        Long productCount = productService.getProductCountByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("productCount", productCount);
        
        model.addAttribute("images", pageImages.getContent());
        model.addAttribute("currentPage", pageImages.getNumber() + 1);
        model.addAttribute("totalItems", pageImages.getTotalElements());
        model.addAttribute("totalPages", pageImages.getTotalPages());
        model.addAttribute("pageSize", pageSize);

        return "images";
    }

    @GetMapping("/product/{id}")
    public String getProductById( @PathVariable Long id, Model model) {
    	Image product = productService.getProductById(id);
    	if (IndianCurrencyFormatter.isValidPrice(product.getProductPrice())) {
			double price = Double.parseDouble(product.getProductPrice());
			String formattedPrice = IndianCurrencyFormatter.formatPriceToIndianCurrency(price);
			
			product.setProductPrice(formattedPrice); // Set the formatted price
		} else {
			// Set default value if the price is not a valid number
			product.getProductPrice();
		}
    	
    	System.out.println("\nproduct price"+product.getOriginalPrice());
    	System.out.println("product Extra offer price"+product.getExtraOfferPrice());
    	System.out.println("product price"+product.getProductPrice());
    	
    	model.addAttribute("product",product);
    	return "product-details";	
    }
}



