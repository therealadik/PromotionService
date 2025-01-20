package com.fladx.promotion_service.repository;

import com.fladx.promotion_service.model.PromotionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionEventRepository extends JpaRepository<PromotionEvent, Long> {
}