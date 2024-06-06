
package com.foryou.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foryou.webapp.currency.IndianCurrencyFormatter;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.repository.ImageRepository;

@Service
@Transactional
public class ProductService {
	@Autowired
	private ImageRepository imageRepository;

	public void saveImage(Image image) {
		imageRepository.save(image);
	}

	public List<Image> findByProductCategory(String productCategory) {
		List<Image> byProductCategory = imageRepository.findByProductCategory(productCategory);

		return byProductCategory;
	}

	public Page<Image> getImagesByProductCategory(String category, Pageable pageable) {
		Page<Image> byProductCategory = imageRepository.findByProductCategory(category, pageable);
		for (Image item : byProductCategory) {
			if (IndianCurrencyFormatter.isValidPrice(item.getProductPrice())) {
				double price = Double.parseDouble(item.getProductPrice());
				String formattedPrice = IndianCurrencyFormatter.formatPriceToIndianCurrency(price);
				item.setProductPrice(formattedPrice); // Set the formatted price
			} else {
				// Set default value if the price is not a valid number
				item.getProductPrice();
			}
		}
		return byProductCategory;
	}

	public Image getProductById(Long id) {
		return imageRepository.findById(id).orElse(null);
	}

	public Image findById(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Image> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getProductCountByCategory(String category) {
		return imageRepository.countProductsByCategory(category);
	}

}
