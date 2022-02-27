package com.challenge.config;

//import com.in2.common.lib.exceptions.handler.In2CacheErrorHandler;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.*;

@Configuration
@ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true")
@Slf4j
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    // Cache configuration
    @Value("${spring.cache.redis.time-to-live:PT0s}")
    private String redisTTL;
    @Value("${spring.cache.cache-names}")
    private String cacheNames;

    // Sentinel configuration
    @Value("${spring.redis.sentinel.nodes}")
    private String sentinelNodes;
    @Value("${spring.redis.sentinel.master}")
    private String sentinelMaster;

    // Redis configuration
    @Value("${spring.redis.database:0}")
    private Integer redisDatabase;
    @Value("${spring.redis.password}")
    private String redisPassword;
    @Value("${spring.redis.timeout:PT5s}")
    private String redisTimeout;
    @Value("${spring.redis.lettuce.shutdown-timeout:PT0.1s}")
    private String redisShutdownTimeout;

    /**
     * Redis Sentinel configuration
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Get sentinel nodes
        List<String> sentinelNodesArray = Arrays.asList(this.sentinelNodes.split(","));
        Set<RedisNode> sentinelNodes = new HashSet<>();
        sentinelNodesArray.forEach(
                sentinelNode-> {
                    String [] node = sentinelNode.split(":");
                    sentinelNodes.add(new RedisNode(node[0], Integer.parseInt(node[1])));
                }
        );

        // Configure sentinel nodes
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master(this.sentinelMaster);
        sentinelConfig.setSentinels(sentinelNodes);
        sentinelConfig.setPassword(this.redisPassword);
        sentinelConfig.setDatabase(this.redisDatabase);

        // Client configuration
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder().pingBeforeActivateConnection(true).build())
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .commandTimeout(Duration.parse(this.redisTimeout))
                .shutdownTimeout(Duration.parse(this.redisShutdownTimeout))
                .build();

        // Connection factory
        return new LettuceConnectionFactory(sentinelConfig, clientConfig);
    }

    @Bean
    public CacheManager cacheManager(){

        // Redis json serializer
        RedisSerializationContext.SerializationPair<Object> jsonSerializer =
                RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

        // Redis cache default configuration
        RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.parse(this.redisTTL)).serializeValuesWith(jsonSerializer);

        // Set configuration for every cache name (with default configuration)
        List<String> cacheNames = Arrays.asList(this.cacheNames.split(","));

        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        cacheNames.forEach(cacheName-> {
            // Disable caching for null values
            RedisCacheConfiguration redisCacheConfigNullDisabled = redisCacheConfig.disableCachingNullValues();
            cacheConfigs.put(cacheName, redisCacheConfigNullDisabled);

        });

        // Configure all caches
        return RedisCacheManager.builder(this.redisConnectionFactory())
                .withInitialCacheConfigurations(cacheConfigs)
                .build();
    }
}
