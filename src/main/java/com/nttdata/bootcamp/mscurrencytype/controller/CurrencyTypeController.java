package com.nttdata.bootcamp.mscurrencytype.controller;

import com.nttdata.bootcamp.mscurrencytype.application.CurrencyTypeService;
import com.nttdata.bootcamp.mscurrencytype.model.CurrencyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/currencytype")
@Slf4j
public class CurrencyTypeController {

    @Autowired
    private CurrencyTypeService currencyTypeService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CurrencyType>>> listCurrencyTypes() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(currencyTypeService.findAll()));
    }

    @GetMapping("/{idCurrencyType}")
    public Mono<ResponseEntity<CurrencyType>> getCurrencyTypeDetails(@PathVariable("idCurrencyType") String idCurrencyType) {
        return currencyTypeService.findById(idCurrencyType)
                .map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveCurrencyType(@Valid @RequestBody Mono<CurrencyType> personType) {
        Map<String, Object> request = new HashMap<>();
        return personType
                .flatMap(pType -> currencyTypeService.save(pType).map(baSv -> {
                            request.put("CurrencyType", baSv);
                            request.put("message", "Tipo de moneda guardado con exito");
                            request.put("timestamp", new Date());
                            return ResponseEntity.created(URI.create("/api/currencytype/".concat(baSv.getId())))
                                    .contentType(MediaType.APPLICATION_JSON).body(request);
                        })
                );
    }

    @PutMapping("/{idCurrencyType}")
    public Mono<ResponseEntity<CurrencyType>> editCurrencyType(@Valid @RequestBody CurrencyType currencyType, @PathVariable("idCurrencyType") String idCurrencyType) {
        return currencyTypeService.update(currencyType, idCurrencyType)
                .map(c -> ResponseEntity.created(URI.create("/api/currencytype/".concat(idCurrencyType)))
                        .contentType(MediaType.APPLICATION_JSON).body(c));
    }

    @DeleteMapping("/{idCurrencyType}")
    public Mono<ResponseEntity<Void>> deleteCurrencyType(@PathVariable("idCurrencyType") String idCurrencyType) {
        return currencyTypeService.delete(idCurrencyType)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }
}
