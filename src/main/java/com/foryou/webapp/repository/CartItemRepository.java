package com.foryou.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foryou.webapp.entity.CartItem;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findByUserId(Long userId);
	
    CartItem findByUserAndImage(User user, Image image);

}
