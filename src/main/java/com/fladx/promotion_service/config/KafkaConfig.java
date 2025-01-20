package com.fladx.promotion_service.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaConfig {
    public final static String PROMOTION_BOUGHT_TOPIC = "promotion_bought";
    public static final String NOTIFICATION_TOPIC = "notification_topic";
}
