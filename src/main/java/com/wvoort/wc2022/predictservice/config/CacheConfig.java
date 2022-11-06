package com.wvoort.wc2022.predictservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;
import java.time.Instant;
import java.util.Arrays;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    private static final String MATCHES = "matches";
    @Bean
    public CacheManager cacheManager() {
        // configure and return an implementation of Spring's CacheManager SPI
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache(MATCHES)));
        return cacheManager;
    }

    @CacheEvict(allEntries = true, value = {MATCHES})
    @Scheduled(fixedDelay = Timer.ONE_DAY)
    public void reportCacheEvict() {
        log.info("Fixtures cache flushed at {}", Instant.now());
    }

}
