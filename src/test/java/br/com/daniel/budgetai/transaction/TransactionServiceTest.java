package br.com.daniel.budgetai.transaction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository repository;

    @Test
    void shouldCalculateIncomeExpensesAndBalance() {
        TransactionService service = new TransactionService(repository);
        LocalDate today = LocalDate.now();
        when(repository.findByOccurredOnBetweenOrderByOccurredOnDesc(today, today)).thenReturn(List.of(
                new FinancialTransaction("Salário", new BigDecimal("2000.00"), TransactionType.INCOME, "Trabalho", today),
                new FinancialTransaction("Mercado", new BigDecimal("300.00"), TransactionType.EXPENSE, "Casa", today)
        ));

        FinancialSummary summary = service.summary(today, today);

        assertEquals(new BigDecimal("2000.00"), summary.income());
        assertEquals(new BigDecimal("300.00"), summary.expenses());
        assertEquals(new BigDecimal("1700.00"), summary.balance());
    }

    @Test
    void shouldRejectFutureTransaction() {
        TransactionService service = new TransactionService(repository);
        CreateTransactionRequest request = new CreateTransactionRequest("Teste", BigDecimal.ONE,
                TransactionType.EXPENSE, "Teste", LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class, () -> service.create(request));
    }
}
