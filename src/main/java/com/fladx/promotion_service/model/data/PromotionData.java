package com.fladx.promotion_service.model.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class PromotionData {
    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "budget_in_day")
    private Integer budgetInDay;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
