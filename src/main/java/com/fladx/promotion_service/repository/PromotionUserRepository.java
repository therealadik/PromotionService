package com.fladx.promotion_service.repository;

import com.fladx.promotion_service.model.PromotionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionUserRepository extends JpaRepository<PromotionUser, Long> {
}