package com.foryou.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foryou.webapp.entity.SubscribedUser;

@Repository
public interface SubscribedUserRepository extends JpaRepository<SubscribedUser, Long> {
}