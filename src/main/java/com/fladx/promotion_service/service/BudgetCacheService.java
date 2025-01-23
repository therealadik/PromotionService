package com.fladx.promotion_service.service;

import com.fladx.promotion_service.model.PromotionEvent;
import com.fladx.promotion_service.model.PromotionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetCacheService {

    public static final String USER_PROMOTION_KEY = "user_promotion";
    public static final int USER_CACHE_TIMEOUT = 1;

    public static final String EVENT_PROMOTION_KEY = "event_promotion";
    public static final int EVENT_CACHE_TIMEOUT = 1;

    public static final TimeUnit TIME_UNIT_EXPIRED_CACHE = TimeUnit.MINUTES;

    private final RedisTemplate<String, Object> redisTemplate;

    public List<Long> updateUsersCache(Stream<PromotionUser> users) {
        LocalDateTime now = LocalDateTime.now();
        List<Long> sortedUserIds = users
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

        redisTemplate.opsForValue().set(USER_PROMOTION_KEY, sortedUserIds, USER_CACHE_TIMEOUT, TIME_UNIT_EXPIRED_CACHE);
        log.info("Updated users cache");
        return sortedUserIds;
    }

    public List<Long> updateEventsCache(Stream<PromotionEvent> events) {
        LocalDateTime now = LocalDateTime.now();
        List<Long> sortedEventIds = events
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

        redisTemplate.opsForValue().set(EVENT_PROMOTION_KEY, sortedEventIds, EVENT_CACHE_TIMEOUT, TIME_UNIT_EXPIRED_CACHE);
        log.info("Updated events cache");
        return sortedEventIds;
    }

    public boolean hasEventCache() {
        return redisTemplate.hasKey(EVENT_PROMOTION_KEY);
    }

    public boolean hasUserCache() {
        return redisTemplate.hasKey(USER_PROMOTION_KEY);
    }

    @SuppressWarnings("unchecked")
    public List<Long> getEvents() {
        return (List<Long>) redisTemplate.opsForValue().get(EVENT_PROMOTION_KEY);
    }

    @SuppressWarnings("unchecked")
    public List<Long> getUsers() {
        return (List<Long>) redisTemplate.opsForValue().get(USER_PROMOTION_KEY);
    }
}
