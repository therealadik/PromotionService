package com.fladx.promotion_service.controller;

import com.fladx.promotion_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promotion")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping("/events")
    public List<Long> getEvents() {
        return promotionService.getEvents();
    }

    @GetMapping("/users")
    public List<Long> getUsers() {
        return promotionService.getUsers();
    }
}
