package br.com.daniel.budgetai.transaction;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotBlank String description,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        @NotNull TransactionType type,
        @NotBlank String category,
        @NotNull LocalDate occurredOn
) { }
