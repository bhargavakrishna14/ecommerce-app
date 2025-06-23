package dev.bhargav.ecommerce.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "House number is required")
    private String houseNumber;

    @NotBlank(message = "Zip code is required")
    private String zipCode;
}
