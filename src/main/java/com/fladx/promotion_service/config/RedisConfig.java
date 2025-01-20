package com.fladx.promotion_service.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, Long.class);
        Jackson2JsonRedisSerializer<List<Long>> valueSerializer = new Jackson2JsonRedisSerializer<>(type);

        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);

        return template;
    }
}
