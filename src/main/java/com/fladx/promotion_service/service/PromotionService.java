package com.fladx.promotion_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fladx.promotion_service.dto.PromotionEventDto;
import com.fladx.promotion_service.dto.PromotionUserDto;
import com.fladx.promotion_service.mapper.PromotionEventMapper;
import com.fladx.promotion_service.mapper.PromotionUserMapper;
import com.fladx.promotion_service.model.PromotionEvent;
import com.fladx.promotion_service.model.PromotionUser;
import com.fladx.promotion_service.repository.PromotionEventRepository;
import com.fladx.promotion_service.repository.PromotionUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionService {

    private final ObjectMapper objectMapper;
    private final PromotionUserMapper promotionUserMapper;
    private final PromotionEventMapper promotionEventMapper;
    private final PromotionEventRepository promotionEventRepository;
    private final PromotionUserRepository promotionUserRepository;
    private final BudgetCacheService budgetCacheService;

    private <T> T deserializeMessage(String message, Class<T> clazz) {
        try {
            return objectMapper.readValue(message, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка десериализации сообщения", e);
        }
    }

    private String convertToJson(Serializable request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сериализации JSON", e);
        }
    }

    @Transactional
    public void processBoughtPromotion(ConsumerRecord<String, String> record) {
        String key = record.key();

        if (key.equals("user")) {
            PromotionUserDto promotionUserDto = deserializeMessage(record.value(), PromotionUserDto.class);
            PromotionUser promotionUser = promotionUserMapper.toEntity(promotionUserDto);
            promotionUserRepository.save(promotionUser);
            budgetCacheService.updateUserBudget(promotionUserRepository.findAll());
        } else if (key.equals("event")) {
            PromotionEventDto promotionEventDTO = deserializeMessage(record.value(), PromotionEventDto.class);
            PromotionEvent promotionEvent = promotionEventMapper.toEntity(promotionEventDTO);
            promotionEventRepository.save(promotionEvent);
            budgetCacheService.updateEventBudget(promotionEventRepository.findAll());
        }
    }
}
