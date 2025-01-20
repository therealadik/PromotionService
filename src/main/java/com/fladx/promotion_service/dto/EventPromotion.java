package com.fladx.promotion_service.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record EventPromotion(Long userId,
                             Long eventId,
                             Long budgetInDay,
                             Long countDays) implements Serializable {
}
