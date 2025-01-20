package com.fladx.promotion_service.model.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Embeddable
public class PromotionData {
    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "budgetday")
    private Integer budgetDay;

    @Column(name = "startdate")
    private LocalDate startDate;

    @Column(name = "enddate")
    private LocalDate endDate;
}
