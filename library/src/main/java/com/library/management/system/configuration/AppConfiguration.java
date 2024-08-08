package com.library.management.system.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "filter")
@Getter
@Setter
@EnableCaching
public class AppConfiguration {

    @Bean
    public Caffeine<?, ?> caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}