# Gestão de Contas Service

Microserviço para gestão de contas a pagar, desenvolvido apartir de um desafio técnico de uma vaga Dev Java Pleno.

## Stack

| Tecnologia | Versão |
|-----------|--------|
| Java | 25 |
| Spring Boot | 4.0.6 |
| PostgreSQL | 16 (Alpine) |
| RabbitMQ | 4.0 (Management) |
| Flyway | Migração de schema |
| JWT (jjwt) | 0.12.6 |
| Testes | JUnit 5 + Mockito |

---

## 1. Como executar

### Pré-requisitos

- Docker + Docker Compose

### Subir o ambiente completo

```bash
docker compose up --build
```

Isso inicia três containers:

| Container | Porta | Função |
|-----------|-------|--------|
| `postgres_db` | 5432 | Banco de dados |
| `rabbitmq` | 5672 / 15672 | Mensageria (AMQP + Dashboard) |
| `contas-service` | 8080 → 8081 | Aplicação Spring Boot |

A aplicação ficará disponível em `http://localhost:8080`.

> O `docker-compose.yml` expõe a porta `8080` mapeando para `8081` (porta interna da aplicação).

### Documentação Interativa (Swagger UI)

Com a aplicação em execução, acesse:

- **Swagger UI**: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)

O Swagger UI permite explorar e testar todos os endpoints da API diretamente pelo navegador. Para autenticação, utilize o endpoint `POST /api/auth/login` para obter um token JWT e clique no botão **Authorize** no topo da página para inseri-lo.

### Variáveis de ambiente (valores padrão)

| Variável | Default |
|----------|---------|
| `DATABASE_URL` | `jdbc:postgresql://localhost:5432/appdb` |
| `DATABASE_USERNAME` | `appuser` |
| `DATABASE_PASSWORD` | `apppassword` |
| `RABBITMQ_HOST` | `localhost` |
| `JWT_SECRET` | Chave HMAC-SHA256 (mín. 256 bits) |
| `JWT_EXPIRACAO` | `86400000` (24h) |

### Executar sem Docker (desenvolvimento)

```bash
# Terminal 1: PostgreSQL
docker run -p 5432:5432 -e POSTGRES_DB=appdb -e POSTGRES_USER=appuser -e POSTGRES_PASSWORD=apppassword postgres:16-alpine

# Terminal 2: RabbitMQ
docker run -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management-alpine

# Terminal 3: Aplicação
./mvnw spring-boot:run
```

---

## 2. Endpoints da API

Todos os endpoints, exceto `/api/auth/registro` e `/api/auth/login`, exigem o header:

```
Authorization: Bearer <token>
```

### Auth

#### Registrar usuário
```
POST /api/auth/registro
```
```json
{
  "nome": "Admin",
  "email": "admin@email.com",
  "senha": "12345678"
}
```

#### Login
```
POST /api/auth/login
```
```json
{
  "email": "admin@email.com",
  "senha": "12345678"
}
```

#### Buscar usuário por ID
```
GET /api/auth/{usuarioId}
```

#### Listar usuários
```
GET /api/auth
```

#### Atualizar usuário
```
PUT /api/auth/{usuarioId}
```
```json
{
  "nome": "Admin Atualizado",
  "email": "admin@email.com"
}
```

#### Atualizar senha
```
PUT /api/auth/{usuarioId}/senha
```
```json
{
  "senhaAtual": "12345678",
  "novaSenha": "87654321"
}
```

#### Deletar usuário
```
DELETE /api/auth/{usuarioId}
```

---

### Fornecedores

#### Criar fornecedor
```
POST /api/fornecedores
```
```json
{
  "nome": "Fornecedor A"
}
```

#### Listar fornecedores
```
GET /api/fornecedores
```

#### Buscar fornecedor por ID
```
GET /api/fornecedores/{fornecedorId}
```

#### Atualizar fornecedor
```
PUT /api/fornecedores/{fornecedorId}
```
```json
{
  "nome": "Fornecedor A Atualizado"
}
```

#### Deletar fornecedor
```
DELETE /api/fornecedores/{fornecedorId}
```

---

### Contas

#### Criar conta
```
POST /api/contas
```
```json
{
  "dataVencimento": "2026-07-15",
  "valor": 1500.50,
  "descricao": "Serviço de consultoria",
  "fornecedorNome": "Consultoria XYZ"
}
```

#### Listar contas
```
GET /api/contas?pagina=0&tamanho=10
```

Parâmetros opcionais: `dataVencimento`, `descricao`.

#### Buscar conta por ID
```
GET /api/contas/{contaId}
```

#### Atualizar conta
```
PUT /api/contas/{contaId}
```
```json
{
  "dataVencimento": "2026-08-20",
  "valor": 2000.00,
  "descricao": "Nova descrição",
  "fornecedorId": "{fornecedorId}"
}
```

#### Pagar conta
```
POST /api/contas/{contaId}/pagamento
```
```json
{
  "dataPagamento": "2026-06-10"
}
```

#### Cancelar conta
```
POST /api/contas/{contaId}/cancelamento
```

#### Deletar conta
```
DELETE /api/contas/{contaId}
```

#### Relatório por período
```
GET /api/contas/relatorio?inicio=2026-01-01&fim=2026-12-31
```

#### Importar CSV
```
POST /api/contas/importacao/csv
Content-Type: multipart/form-data
```

Campo: `arquivo` (tipo file).

O CSV deve seguir o formato abaixo — a primeira linha (cabeçalho) é ignorada:

```
data_vencimento,valor,descricao,fornecedor_nome
2026-07-15,1500.00,Serviço de consultoria,Consultoria ABC
2026-08-10,850.50,Manutenção predial,Empresa XYZ
```

Resposta:
```json
{
  "protocoloId": "uuid",
  "mensagem": "Arquivo recebido e enviado para processamento."
}
```

> Para testes manuais, importe o arquivo `gestao-contas-service.postman_collection.json` no Postman.

---

## 3. Decisões Arquiteturais

### DDD (Domain-Driven Design)

Organização em 3 camadas com responsabilidades bem definidas:

```
domain/          → Núcleo do negócio (entidades, value objects, interfaces de repositório)
application/     → Casos de uso (orquestração do fluxo, sem dependência de frameworks)
infrastructure/  → Implementações concretas (JPA, REST, RabbitMQ, Security)
```

- **Entidades de domínio puras**:`Conta`, `Fornecedor`, `Usuario` são POJOs sem anotações JPA. Isso mantém o núcleo do negócio livre de acoplamento com frameworks.
- **Value Objects como `record`**: `Dinheiro` e `Descricao` são imutáveis, com validação no construtor compacto. Isso garante integridade dos dados.
- **Repositories como interfaces no domínio**: as interfaces `ContaRepository`, `UsuarioRepository`, `FornecedorRepository` estão no pacote `domain.repository`, definindo o contrato. As implementações ficam na infraestrutura.
- **Casos de uso (Use Cases)**: cada operação de negócio tem sua própria classe `@Service` com uma única responsabilidade (SRP). Ex: `CriarContaUseCase`, `PagarContaUseCase`, `CancelarContaUseCase`.

### Clean Architecture / SOLID

| Princípio | Aplicação |
|-----------|-----------|
| **Single Responsibility** | Cada Use Case executa exatamente uma operação |
| **Dependency Inversion** | Application depende de interfaces do domínio, não de implementações concretas |
| **Open/Closed** | Novos comportamentos são adicionados criando novos Use Cases, sem modificar existentes |
| **Interface Segregation** | Repositories têm métodos específicos por agregado |
| **Dependency Injection** | Injeção por construtor em todos os serviços |

### Performance JPA

- **Sem relacionamentos `@OneToMany`/`@ManyToOne`**: `ContaJpaEntity` armazena `fornecedorId` e `fornecedorNome` como colunas planas, eliminando o problema N+1.
- **Consultas JPQL customizadas**: Filtragem paginada, total pago por período e contagem são feitas via `@Query` com SQL otimizado.
- **`open-in-view: false`**: Evita lazy loading acidental em visualizações.
- **DTOs como `record`**: Imutáveis e leves para tráfego na API.

### Resiliência (Processamento Assíncrono)

- **RabbitMQ com DLQ**: mensagens com falha são redirecionadas automaticamente para a Dead Letter Queue (`conta.importacao.dlq`), permitindo auditoria e reprocessamento.
- **Tratamento granular de erros no CSV**: erros por linha são logados e contados, sem interromper o processamento das demais linhas. Apenas falhas fatais (ex: I/O) rejeitam a mensagem para a DLQ.
- **Exception Handler global**: `@RestControllerAdvice` mapeia exceções para códigos HTTP apropriados (400, 404, 409, 422, 500) com respostas padronizadas.

### Segurança

- **JWT (HMAC-SHA256)**: autenticação stateless via tokens.
- **BCrypt**: senhas hashadas com `BCryptPasswordEncoder` antes de persistir.
- **Rotas públicas vs protegidas**: `/api/auth/**` é público (registro/login); demais exigem token Bearer.
- **Filtro JWT customizado**: `OncePerRequestFilter` que valida o token e estabelece o `SecurityContext`.

## 4. Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/totvs/gestao_contas_service/
│   │   ├── domain/
│   │   │   ├── entity/          Conta, Fornecedor, Usuario, Situacao
│   │   │   ├── valueobject/     Dinheiro, Descricao
│   │   │   ├── repository/      ContaRepository, UsuarioRepository, FornecedorRepository
│   │   │   ├── exception/       DomainException, EntityNotFoundException
│   │   │   └── event/           ImportacaoSolicitadaEvent
│   │   ├── application/
│   │   │   ├── usecase/         Casos de uso (conta/, usuario/, fornecedor/, shared/)
│   │   │   ├── dto/             Records de request/response
│   │   │   └── mapper/          ContaMapper, UsuarioMapper, FornecedorMapper
│   │   └── infrastructure/
│   │       ├── persistence/     JPA Entities, JPA Repositories, Repository Impls
│   │       ├── rest/            Controllers, GlobalExceptionHandler, ErrorResponse
│   │       ├── security/        SecurityConfig, JwtUtil
│   │       └── amqp/            RabbitmqConfig, ImportacaoCsvConsumer
│   └── resources/
│       ├── application.yml
│       └── db/migration/        Flyway (V1__criar_tabelas_iniciais.sql)
└── test/
    └── java/...
        ├── domain/entity/       ContaTest, FornecedorTest, UsuarioTest
        ├── domain/valueobject/  DinheiroTest, DescricaoTest
        ├── application/usecase/ Testes com Mockito para casos de uso
        └── application/mapper/  ContaMapperTest
```