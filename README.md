# Vini-TEF - Sistema de Autorização de Pagamentos

![Java](https://img.shields.io/badge/Java-22-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.x-brightgreen.svg)
![jPOS](https://img.shields.io/badge/jPOS-2.1.9-orange.svg)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red.svg)

## 📖 Visão Geral

O **Vini-TEF** é uma solução completa para processamento de transações financeiras eletrônicas (TEF), construída com uma arquitetura moderna e robusta. O projeto simula o fluxo de uma autorização de pagamento com cartão, desde uma requisição RESTful até a comunicação com um autorizador via protocolo ISO 8583.

A solução é dividida em dois componentes principais:
1.  **`servidor-tef`**: Um servidor Java puro que simula o _core_ autorizador. Ele se comunica através do protocolo ISO 8583 sobre TCP/IP, utilizando a biblioteca **jPOS**.
2.  **`vinitef-gateway`**: Uma API RESTful moderna construída com **Spring Boot**. Ela atua como uma fachada (gateway), traduzindo requisições HTTP com JSON para o formato ISO 8583 e as enviando para o `servidor-tef`.

Este projeto foi desenhado seguindo os princípios da **Clean Architecture**, separando as responsabilidades em camadas de domínio, aplicação e infraestrutura, o que garante alta testabilidade, flexibilidade e manutenção.

---

## 🏛️ Arquitetura

O sistema segue uma arquitetura de microsserviços desacoplada, onde o `gateway` atua como a ponte entre o mundo moderno (APIs REST) e o sistema legado de transações (ISO 8583).

```
[Cliente REST] <--> [vinitef-gateway (Spring Boot)] <--> [servidor-tef (jPOS)]
  (JSON/HTTP)               (Tradução)                 (ISO 8583/TCP)
```

### Principais Funcionalidades

* **Endpoint de Autorização**: Permite que clientes solicitem a autorização de uma transação de pagamento.
* **Comunicação ISO 8583**: Implementação robusta do protocolo usando jPOS para comunicação TCP/IP.
* **Simulação de Timeout**: O servidor pode simular timeouts para transações com valores elevados, permitindo testar a resiliência do cliente.
* **Clean Architecture**: Código organizado para máxima coesão e mínimo acoplamento.
* **Logging Estruturado**: Logs detalhados em console e arquivo, utilizando SLF4J e Logback.
* **Tratamento de Exceções**: Respostas de erro padronizadas para a API REST.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem**: Java 22
* **Gateway**: Spring Boot 3.3
* **Comunicação Transacional**: jPOS 2.1.9
* **API**: Spring Web
* **Build**: Maven
* **Logging**: SLF4J com Logback
* **Utilitários**: Lombok

---

## 🚀 Como Executar o Projeto

Para executar o ambiente completo, você precisará ter o **JDK 21+** e o **Maven** instalados.

### 1. Servidor TEF (`servidor-tef`)

Primeiro, inicie o servidor que irá escutar as requisições ISO 8583.

1.  **Navegue até a pasta do servidor:**
    ```bash
    cd servidor-tef
    ```

2.  **Compile e execute a aplicação:**
    * Utilize o Maven para compilar e executar a classe principal.
    ```bash
    mvn clean install exec:java -Dexec.mainClass="br.com.vinitefapp.ViniTEFMain"
    ```

3.  **Verifique a saída:**
    * O console exibirá o banner `VINI TEF SERVER` e a mensagem de que está escutando na porta 8000.
    * Logs serão gerados no console e no arquivo `logs/vini-tef-server.log`.

### 2. Gateway de Pagamentos (`vinitef-gateway`)

Com o servidor TEF rodando, inicie a API REST Spring Boot.

1.  **Navegue até a pasta do gateway:**
    ```bash
    cd vinitef-gateway
    ```

2.  **Execute a aplicação com o Maven:**
    ```bash
    mvn spring-boot:run
    ```

3.  **Verifique a saída:**
    * O Spring Boot iniciará, e a aplicação estará disponível na porta `8080`.
    * A documentação da API (Swagger UI) estará disponível em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 Como Usar a API

Você pode usar ferramentas como Postman, Insomnia ou `curl` para interagir com o endpoint de autorização.

### Endpoint de Autorização

* **URL**: `POST http://localhost:8080/authorization`
* **Headers**:
    * `Content-Type`: `application/json`
    * `x-identifier`: `{merchant_id}` (Ex: `MERCADOR123`)

### Exemplo de Requisição (`curl`)

```bash
curl --location 'http://localhost:8080/authorization' \
--header 'x-identifier: MERCADOR123' \
--header 'Content-Type: application/json' \
--data '{
    "external_id": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
    "value": 250.00,
    "card_number": "4900123456789012",
    "installments": 1,
    "cvv": "123",
    "exp_month": 12,
    "holder_name": "VINICIOS A RODRIGUES",
    "exp_year": 28
}'
```

### Exemplo de Resposta não autorizado (Sucesso)

Para testar o cenário de não autorizado, envie uma transação com valor superior ímpar. A API retornará um erro `200 OK` mas com o `authorization_code` nulo.

```json
{
  "value": 251.00,
  "payment_id": "0000000004",
  "response_code": "51",
  "authorization_code": null,
  "transaction_date": "2025-08-18",
  "transaction_hour": "03:16:49"
}
```

### Exemplo de Resposta (Sucesso)

```json
{
  "value": 250.00,
  "payment_id": "0000000003",
  "response_code": "00",
  "authorization_code": "000001",
  "transaction_date": "2025-08-18",
  "transaction_hour": "03:16:05"
}
```

### Exemplo de Resposta (Timeout)

Para testar o cenário de timeout, envie uma transação com valor superior a `1000.00`. A API retornará um erro `504 Gateway Timeout` após 10 segundos.

```json
{
    "timestamp": 1692234567890,
    "status": 504,
    "error": "Gateway Timeout",
    "message": "VINI-TEF-SERVER demorou muito para responder, a conexão foi encerrada",
    "path": "/authorization"
}
```

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.