package com.fladx.promotion_service.listener;

import com.fladx.promotion_service.config.KafkaConfig;
import com.fladx.promotion_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PromotionListener {

    private final PromotionService promotionService;

    @KafkaListener(topics = KafkaConfig.PROMOTION_BOUGHT_TOPIC)
    public void processBoughtPromotion(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            promotionService.processBoughtPromotion(record);
        } catch (Exception e) {
            log.error("Error processing message: {}, Error: {}", record, e.getMessage());
            throw e;
        }

        acknowledgment.acknowledge();
    }
}
