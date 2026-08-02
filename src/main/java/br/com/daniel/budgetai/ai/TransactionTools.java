package br.com.daniel.budgetai.ai;

import br.com.daniel.budgetai.transaction.*;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TransactionTools {
    private final TransactionService service;

    public TransactionTools(TransactionService service) { this.service = service; }

    @Tool(description = "Registra uma transação financeira. type deve ser INCOME para receita ou EXPENSE para despesa. A data usa o formato yyyy-MM-dd.")
    public String createTransaction(String description, BigDecimal amount, TransactionType type, String category, String occurredOn) {
        FinancialTransaction saved = service.create(new CreateTransactionRequest(description, amount, type, category,
                LocalDate.parse(occurredOn)));
        return "Transação criada com sucesso: " + saved.getDescription() + ", valor R$ " + saved.getAmount();
    }

    @Tool(description = "Consulta o resumo financeiro de um intervalo. As datas devem estar no formato yyyy-MM-dd.")
    public FinancialSummary getFinancialSummary(String start, String end) {
        return service.summary(LocalDate.parse(start), LocalDate.parse(end));
    }
}
