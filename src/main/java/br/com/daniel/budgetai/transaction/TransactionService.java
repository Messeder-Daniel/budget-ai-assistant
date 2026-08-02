package br.com.daniel.budgetai.transaction;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public FinancialTransaction create(CreateTransactionRequest request) {
        if (request.occurredOn().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data da transação não pode estar no futuro.");
        }
        return repository.save(new FinancialTransaction(request.description(), request.amount(), request.type(),
                request.category(), request.occurredOn()));
    }

    public List<FinancialTransaction> list(LocalDate start, LocalDate end) {
        return repository.findByOccurredOnBetweenOrderByOccurredOnDesc(start, end);
    }

    public FinancialSummary summary(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) throw new IllegalArgumentException("A data final deve ser posterior à inicial.");
        List<FinancialTransaction> transactions = list(start, end);
        BigDecimal income = totalByType(transactions, TransactionType.INCOME);
        BigDecimal expenses = totalByType(transactions, TransactionType.EXPENSE);
        return new FinancialSummary(start, end, income, expenses, income.subtract(expenses), transactions.size());
    }

    private BigDecimal totalByType(List<FinancialTransaction> transactions, TransactionType type) {
        return transactions.stream().filter(item -> item.getType() == type)
                .map(FinancialTransaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
