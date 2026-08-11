# Budget AI Assistant 🤖💰

API REST de gerenciamento financeiro desenvolvida com **Java, Spring Boot e Spring AI**.

O projeto combina regras tradicionais de backend com **inteligência artificial**, permitindo registrar e consultar transações utilizando comandos em linguagem natural.

A aplicação utiliza **Tool Calling** para que o modelo interprete a intenção do usuário, enquanto as operações reais permanecem sob controle da aplicação.

## 🎯 Objetivo

Demonstrar na prática a construção de uma API backend integrada a recursos de IA, mantendo separadas:

- interpretação por IA;
- regras de negócio;
- persistência;
- validações financeiras.

## 🚀 Principais funcionalidades:
## O que o projeto faz

- Cria e consulta transações financeiras;
- Gera resumo de receitas, despesas e saldo por período;
- Usa o `ChatClient` do Spring AI para entender comandos em português;
- Disponibiliza ferramentas de IA para criar transações e consultar o resumo;
- Transcreve áudio e sintetiza a resposta por meio da API da OpenAI;
- Valida valores positivos e impede lançamentos com data futura.

## Melhoria implementada

Além do fluxo de IA, foi adicionada uma camada de **validação financeira e resumo por período**. Assim, a IA não consegue gravar valores inválidos ou transações futuras, e a pessoa usuária pode obter receitas, despesas, saldo e quantidade de lançamentos em um intervalo de datas.

## Tecnologias

- Java 21+
- Spring Boot 3.5
- Spring AI / ChatClient / Tool Calling
- Spring Data JPA
- Banco H2
- OpenAI API (chat, transcrição e voz)
- Maven e JUnit 5

## Como executar

1. Execute a aplicação (os endpoints financeiros diretos funcionam mesmo sem chave de IA):

```bash
mvn spring-boot:run
```

2. Para usar os endpoints de IA, crie uma chave de API da OpenAI e exporte-a no terminal antes de iniciar a aplicação:

```bash
export OPENAI_API_KEY="sua_chave_aqui"
```

3. A API estará disponível em `http://localhost:8080`.

O console do H2 pode ser acessado em `http://localhost:8080/h2-console` com JDBC URL `jdbc:h2:mem:budgetdb`, usuário `sa` e senha vazia.

## Testando o fluxo principal

Criar uma transação diretamente:

```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -d '{"description":"Freelance","amount":1500.00,"type":"INCOME","category":"Trabalho","occurredOn":"2026-08-02"}'
```

Consultar o resumo:

```bash
curl "http://localhost:8080/api/transactions/summary?start=2026-08-01&end=2026-08-31"
```

Enviar um comando para a IA (com a chave configurada):

```bash
curl -X POST http://localhost:8080/api/assistant/text \
  -H "Content-Type: application/json" \
  -d '{"message":"Registre uma despesa de 45 reais com transporte hoje"}'
```

Enviar um áudio e receber a resposta em MP3:

```bash
curl -X POST http://localhost:8080/api/assistant/voice \
  -F "audio=@comando.mp3" \
  --output resposta.mp3
```

## O que foi aprendido

O projeto demonstra como manter a IA separada das regras de negócio: o modelo interpreta o comando, mas as operações reais são executadas por ferramentas controladas pela aplicação. Dessa forma, persistência, validações e cálculo financeiro continuam sob responsabilidade do backend.
