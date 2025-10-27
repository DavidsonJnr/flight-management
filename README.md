# Flight Reservation API

API RESTful para gerenciamento de voos, incluindo integração com fornecedores externos.

## 🛠 Tecnologias

* Java 21
* Spring Boot
* Maven
* Postgres
* Docker
* WebClient (para integração com APIs externas)
* JUnit 5 + Mockito (testes unitários)

---

## 📦 Estrutura do Projeto

* `entity/` → Entidades JPA (`Flight`, `BaseEntity`, etc.)
* `repository/` → Repositórios Spring Data JPA
* `service/` → Serviços de negócio (`FlightSaveService`, `FlightUpdateService`, `FlightSearchService`, `FlightDeleteService`)
* `controller/` → Controllers REST
* `dto/` → Classes de Request e Response
* `exception/` → Tratamento global de exceções
* `config/` → Configurações do Spring Boot
* `pagination/` → Classes utilitárias para paginação

---

## ⚡ Funcionalidades

* Criar, atualizar, deletar e buscar voos
* Busca e filtro por: origem, destino, companhia aérea, horários de partida e chegada
* Integração com fornecedores externos (`CrazySupplier`)
* Validação de horários de voo
* Controle de versão otimista (`@Version`)
* Tratamento global de exceções
* Paginação customizada para resultados

---

## 🚀 Setup e Execução

### 1. Clonar o projeto

```bash
git clone https://github.com/DavidsonJnr/flight-management.git
cd flight-reservation
```

### 2. Configurar banco de dados

Atualize o arquivo `application.yml` com as credenciais corretas.

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/flight_db
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 3. Build e execução

```bash
mvn clean install
mvn spring-boot:run
```

### 4. Endpoints principais

| Método | Endpoint        | Descrição                           |
| ------ | --------------- | ----------------------------------- |
| POST   | `/flights`      | Criar novo voo                      |
| PUT    | `/flights/{id}` | Atualizar voo                       |
| DELETE | `/flights/{id}` | Deletar voo                         |
| GET    | `/flights`      | Buscar voos com filtros e paginação |
| GET    | `/flights/{id}` | Obter voo por ID                    |

> A API retorna respostas padrão com tipo, messageKey, detalhes e timestamp.

---

## 🧪 Testes Unitários

Executar testes com Maven:

```bash
mvn test
```

---
