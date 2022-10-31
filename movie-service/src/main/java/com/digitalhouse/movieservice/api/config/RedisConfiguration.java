package com.digitalhouse.movieservice.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import com.digitalhouse.movieservice.domain.model.MovieInfo;

@Configuration
public class RedisConfiguration {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(this.host);
        configuration.setPort(Integer.parseInt(this.port));

        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, MovieInfo> movieRedisTemplate() {
        RedisTemplate<String, MovieInfo> template = new RedisTemplate<String, MovieInfo>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
