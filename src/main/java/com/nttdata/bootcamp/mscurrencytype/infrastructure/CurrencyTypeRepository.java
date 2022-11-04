package com.nttdata.bootcamp.mscurrencytype.infrastructure;

import com.nttdata.bootcamp.mscurrencytype.model.CurrencyType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class CurrencyTypeRepository {
    private final ReactiveRedisOperations<String, CurrencyType> reactiveRedisOperations;

    public Flux<CurrencyType> findAll() {
        return this.reactiveRedisOperations.<String, CurrencyType>opsForHash().values("currencyTypes");
    }

    public Mono<CurrencyType> findById(String id) {
        return this.reactiveRedisOperations.<String, CurrencyType>opsForHash().get("currencyTypes", id);
    }

    public Mono<CurrencyType> save(CurrencyType currencyType) {
        if (currencyType.getId() == null) {
            String id = UUID.randomUUID().toString();
            currencyType.setId(id);
        }
        return this.reactiveRedisOperations.<String, CurrencyType>opsForHash()
                .put("currencyTypes", currencyType.getId(), currencyType).log().map(p -> currencyType);
    }

    public Mono<Void> delete(CurrencyType currencyType) {
        return this.reactiveRedisOperations.<String, CurrencyType>opsForHash()
                .remove("currencyTypes", currencyType.getId())
                .flatMap(p -> Mono.just(currencyType)).then();
    }

}
