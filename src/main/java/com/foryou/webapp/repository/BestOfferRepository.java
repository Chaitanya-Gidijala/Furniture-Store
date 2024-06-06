package com.foryou.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foryou.webapp.entity.BestOfferProduct;

public interface BestOfferRepository extends JpaRepository<BestOfferProduct, Long> {
}