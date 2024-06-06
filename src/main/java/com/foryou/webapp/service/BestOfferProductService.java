package com.foryou.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foryou.webapp.entity.BestOfferProduct;
import com.foryou.webapp.repository.BestOfferRepository;

@Service
@Transactional
public class BestOfferProductService {
	
	    @Autowired
	    private BestOfferRepository bestOfferRepository;

	    public List<BestOfferProduct> getAllBestOffers(){
	        return bestOfferRepository.findAll();
	    }
	   
}
