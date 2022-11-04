package com.nttdata.bootcamp.mscurrencytype;

import com.nttdata.bootcamp.mscurrencytype.model.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
public class MsCurrencyTypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCurrencyTypeApplication.class, args);
    }

    @Bean
    public ReactiveRedisTemplate<String, CurrencyType> reactiveJsonPostRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, CurrencyType> serializationContext = RedisSerializationContext
                .<String, CurrencyType>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(CurrencyType.class))
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

}
