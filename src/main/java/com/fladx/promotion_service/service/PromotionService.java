package com.fladx.promotion_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fladx.promotion_service.dto.EventPromotion;
import com.fladx.promotion_service.dto.UserPromotion;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final ObjectMapper objectMapper;

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

    public void processBoughtPromotion(ConsumerRecord<String, String> record) {
        String key = record.key();

        if (key.equals("user")) {
            UserPromotion userPromotion = deserializeMessage(record.value(), UserPromotion.class);
        } else if (key.equals("event")) {
            EventPromotion eventPromotion = deserializeMessage(record.value(), EventPromotion.class);
        }
    }
}
