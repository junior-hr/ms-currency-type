package com.nttdata.bootcamp.mscurrencytype.application;

import com.nttdata.bootcamp.mscurrencytype.exception.ResourceNotFoundException;
import com.nttdata.bootcamp.mscurrencytype.infrastructure.CurrencyTypeRepository;
import com.nttdata.bootcamp.mscurrencytype.model.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyTypeServiceImpl implements CurrencyTypeService {

    @Autowired
    private CurrencyTypeRepository currencyTypeRepository;

    @Override
    public Flux<CurrencyType> findAll() {
        return currencyTypeRepository.findAll();
    }

    @Override
    public Mono<CurrencyType> findById(String idCurrencyType) {
        return Mono.just(idCurrencyType)
                .flatMap(currencyTypeRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Currency Type", "idCurrencyType", idCurrencyType)));
    }

    @Override
    public Mono<CurrencyType> save(CurrencyType currencyType) {
        return Mono.just(currencyType).flatMap(dc -> currencyTypeRepository.save(dc));
    }

    @Override
    public Mono<CurrencyType> update(CurrencyType currencyType, String idCurrencyType) {
        return currencyTypeRepository.findById(idCurrencyType)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Currency Type", "idCurrencyType", idCurrencyType)))
                .flatMap(dcu -> {
                    dcu.setCurrencyType(currencyType.getCurrencyType());
                    dcu.setValue(currencyType.getValue());
                    return currencyTypeRepository.save(dcu);
                });
    }

    @Override
    public Mono<Void> delete(String idCurrencyType) {
        return currencyTypeRepository.findById(idCurrencyType)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Currency Type", "idCurrencyType", idCurrencyType)))
                .flatMap(dcu -> currencyTypeRepository.delete(dcu));
    }

    @Override
    public Mono<CurrencyType> findByCurrencyType(String currencyType) {
        return Mono.just(currencyType)
                .flatMap(currencyTypeRepository::findByCurrencyType)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Currency Type", "currencyType", currencyType)));
    }
}
