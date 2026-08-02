package br.com.daniel.budgetai.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FinancialSummary(LocalDate start, LocalDate end, BigDecimal income,
                               BigDecimal expenses, BigDecimal balance, int totalTransactions) { }
