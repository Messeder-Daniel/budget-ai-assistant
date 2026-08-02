package br.com.daniel.budgetai.transaction;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private String category;
    private LocalDate occurredOn;
    private LocalDateTime createdAt;

    protected FinancialTransaction() { }

    public FinancialTransaction(String description, BigDecimal amount, TransactionType type, String category, LocalDate occurredOn) {
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.occurredOn = occurredOn;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getDescription() { return description; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getCategory() { return category; }
    public LocalDate getOccurredOn() { return occurredOn; }
}
