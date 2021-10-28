package org.zobic.ecommerceshopapi.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CountryDto implements Serializable {

    private String name;

    private String alpha2Code;
}
