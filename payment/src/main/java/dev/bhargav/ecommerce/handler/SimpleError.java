package dev.bhargav.ecommerce.handler;

import java.time.LocalDateTime;

public record SimpleError(String error, LocalDateTime timestamp) {
}
