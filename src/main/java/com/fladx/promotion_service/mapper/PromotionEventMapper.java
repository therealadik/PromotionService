package com.fladx.promotion_service.mapper;

import com.fladx.promotion_service.dto.PromotionEventDto;
import com.fladx.promotion_service.model.PromotionEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromotionEventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "promotionData.buyerId")
    @Mapping(source = "budgetInDay", target = "promotionData.budgetInDay")
    @Mapping(target = "promotionData.startDate", expression = "java(getNowDate())")
    @Mapping(target = "promotionData.endDate", expression = "java(calculateEndDate(promotionEventDto.countDays()))")
    PromotionEvent toEntity(PromotionEventDto promotionEventDto);

    default LocalDateTime calculateEndDate(Long countDays) {
        return LocalDateTime.now().plusDays(countDays);
    }

    default LocalDateTime getNowDate() {
        return LocalDateTime.now();
    }
}