package com.nttdata.bootcamp.mscurrencytype.model;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class CurrencyType.
 * CurrencyType microservice class CurrencyType.
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyType {

    @Id
    private String id;

    @NotNull
    private String currencyType;

    @NotNull
    private String value;

}
