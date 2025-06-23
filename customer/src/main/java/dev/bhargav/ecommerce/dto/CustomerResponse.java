package dev.bhargav.ecommerce.dto;

import dev.bhargav.ecommerce.entity.Address;

public record CustomerResponse(
        String id,

        String firstname,

        String lastname,

        String email,

        Address address
) {

}


