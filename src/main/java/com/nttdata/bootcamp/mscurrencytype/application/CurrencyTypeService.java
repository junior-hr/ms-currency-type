package com.nttdata.bootcamp.mscurrencytype.application;

import com.nttdata.bootcamp.mscurrencytype.model.CurrencyType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyTypeService {
    public Flux<CurrencyType> findAll();
    public Mono<CurrencyType> findById(String idCurrencyType);
    public Mono<CurrencyType> save(CurrencyType currencyType);
    public Mono<CurrencyType> update(CurrencyType currencyType, String idCurrencyType);
    public Mono<Void> delete(String idCurrencyType);

}
