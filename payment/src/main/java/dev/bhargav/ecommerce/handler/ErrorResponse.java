package dev.bhargav.ecommerce.handler;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors,
        LocalDateTime timestamp
) {

}
