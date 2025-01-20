package com.fladx.promotion_service.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PromotionUserDto(Long userId,
                               Long budgetInDay,
                               Long countDays) implements Serializable {
}
