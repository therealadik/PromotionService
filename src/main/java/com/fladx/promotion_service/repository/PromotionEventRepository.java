package com.fladx.promotion_service.repository;

import com.fladx.promotion_service.model.PromotionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface PromotionEventRepository extends JpaRepository<PromotionEvent, Long> {
    @Query(nativeQuery = true, value = """
            select * from promotion_event
            where start_date <= current_date and end_date >= current_date
            order by budget_in_day desc
            limit 100
            """)
    Stream<PromotionEvent> findActivePromotionEvents();
}