package com.fladx.promotion_service.service;

import com.fladx.promotion_service.model.PromotionEvent;
import com.fladx.promotion_service.model.PromotionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetCacheService {

    public static final String USER_PROMOTION_KEY = "user_promotion";
    public static final int USER_CACHE_TIMEOUT = 1;

    public static final String EVENT_PROMOTION_KEY = "event_promotion";
    public static final int EVENT_CACHE_TIMEOUT = 1;


    private final RedisTemplate<String, Object> redisTemplate;

    public void updateUserBudget(List<PromotionUser> users) {
        LocalDateTime now = LocalDateTime.now();
        List<Long> sortedUserIds = users.stream()
                .filter(promotionUser -> promotionUser.getPromotionData().getStartDate().isBefore(now)
                        && promotionUser.getPromotionData().getEndDate().isAfter(now))
                .collect(Collectors.groupingBy(
                        promotionUser -> promotionUser.getPromotionData().getBuyerId(),
                        Collectors.summingLong(promotionUser -> promotionUser.getPromotionData().getBudgetInDay())
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(e -> e.getKey())
                .toList();

        redisTemplate.opsForValue().set(USER_PROMOTION_KEY, sortedUserIds, USER_CACHE_TIMEOUT, TimeUnit.HOURS);
    }

    public void updateEventBudget(List<PromotionEvent> events) {
        LocalDateTime now = LocalDateTime.now();
        List<Long> sortedEventIds = events.stream()
                .filter(promotionEvent -> promotionEvent.getPromotionData().getStartDate().isBefore(now)
                        && promotionEvent.getPromotionData().getEndDate().isAfter(now))
                .collect(Collectors.toMap(
                        promotionEvent -> promotionEvent.getEventId(),
                        promotionEvent -> promotionEvent.getPromotionData().getBudgetInDay()
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(e -> e.getKey())
                .toList();

        redisTemplate.opsForValue().set(EVENT_PROMOTION_KEY, sortedEventIds, EVENT_CACHE_TIMEOUT, TimeUnit.HOURS);
    }
}
