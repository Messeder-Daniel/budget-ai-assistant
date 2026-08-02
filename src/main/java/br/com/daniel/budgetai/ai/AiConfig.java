package br.com.daniel.budgetai.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Bean
    ChatClient budgetChatClient(ChatClient.Builder builder, TransactionTools tools) {
        return builder.defaultSystem("""
                Você é um assistente financeiro em português. Ajude com educação e objetividade.
                Use as ferramentas disponíveis sempre que o usuário pedir para criar uma transação ou consultar um resumo.
                Para criar uma transação, confirme que descrição, valor, tipo, categoria e data foram informados.
                Nunca invente valores ou datas. Não ofereça aconselhamento financeiro profissional.
                """).defaultTools(tools).build();
    }
}
