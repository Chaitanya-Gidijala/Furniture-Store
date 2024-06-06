package com.foryou.webapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foryou.webapp.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductCategory(String category);

    @Query("SELECT COUNT(i) FROM Image i WHERE i.productCategory = ?1")
    Long countProductsByCategory(String category);
    
    Page<Image> findByProductCategory(String category, Pageable pageable);
    
}
