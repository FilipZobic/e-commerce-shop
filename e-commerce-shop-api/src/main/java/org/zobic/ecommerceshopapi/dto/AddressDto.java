package org.zobic.ecommerceshopapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class AddressDto {

    @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
    @NotNull
    @Size(max = 2, min = 2)
    private String countryAlpha2Code;

    @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
    @NotNull
    private String addressValue;

    @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
    @NotNull
    @Size(min = 1, max = 100)
    private String cityName;

    @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
    @NotNull
    @Pattern(regexp = "[0-9]{5}", message = "Zip code is 5 digit number")
    private String zipCode;


}
