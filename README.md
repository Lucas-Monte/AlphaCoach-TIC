# AlphaCoach - API de Gestão de Alunos, Matrículas e Treinos

API REST desenvolvida em **Java com Spring Boot** para a gestão de uma academia ou de um personal trainer. Cobre o ciclo completo do aluno: cadastro, matrícula em um plano, geração e pagamento de parcelas, agenda de aulas com check-in, montagem de treinos e exportação da ficha de treino em planilha Excel.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Como executar](#como-executar)
- [Documentação interativa (Swagger)](#documentação-interativa-swagger)
- [Arquitetura](#arquitetura)
- [Modelo de dados](#modelo-de-dados)
- [Regras de negócio](#regras-de-negócio)
- [Autenticação e segurança](#autenticação-e-segurança)
- [Endpoints](#endpoints)
- [Tratamento de erros](#tratamento-de-erros)
- [Exportação de planilha](#exportação-de-planilha)
- [Próximos passos](#próximos-passos)

---

## Funcionalidades

- Cadastro, inativação e recuperação de alunos
- Catálogo de planos (mensal, trimestral, semestral, anual e personalizado)
- Matrícula de um aluno em um plano, com **geração automática das parcelas**
- Registro, alteração e estorno de pagamentos de parcelas
- Agenda de treinos com **check-in controlado por janela de tempo**
- Catálogo de exercícios e treinos personalizados por aluno
- Download da ficha de treino em `.xlsx`
- Autenticação com **JWT** e documentação interativa com **Swagger UI**

---

## Tecnologias

| Item | Detalhe |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 4.0.6 |
| Persistência | Spring Data JPA, Hibernate, PostgreSQL |
| Segurança | Spring Security, JWT (`java-jwt` 4.4.0), BCrypt |
| Validação | Jakarta Validation |
| Documentação da API | springdoc-openapi 2.8.9 |
| Planilhas | Apache POI 5.5.0 |
| Utilitários | Lombok, Spring Boot DevTools |
| Build | Maven (com Maven Wrapper) |

---

## Como executar

### Pré-requisitos

- JDK 21
- PostgreSQL em execução, com um banco criado (por exemplo, `alphacoach`)

### Variáveis de ambiente

As credenciais **não** ficam no código. Defina as variáveis abaixo antes de subir a aplicação:

| Variável | Descrição |
|---|---|
| `DB_URL` | URL JDBC. Ex.: `jdbc:postgresql://localhost:5432/alphacoach` |
| `DB_USER` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `SECRET` | Segredo usado para assinar e validar os tokens JWT |

Exemplo (Linux/macOS):

```bash
export DB_URL=jdbc:postgresql://localhost:5432/alphacoach
export DB_USER=seu_usuario
export DB_PASSWORD=sua_senha
export SECRET=um_segredo_longo_e_aleatorio
```

Exemplo (Windows PowerShell):

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/alphacoach"
$env:DB_USER="seu_usuario"
$env:DB_PASSWORD="sua_senha"
$env:SECRET="um_segredo_longo_e_aleatorio"
```

### Configuração da aplicação

```properties
spring.application.name=app
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Com `ddl-auto=update`, o Hibernate cria e ajusta as tabelas automaticamente.

### Subindo a aplicação

```bash
git clone https://github.com/Lucas-Monte/AlphaCoach-TIC.git
cd AlphaCoach-TIC
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## Documentação interativa (Swagger)

- Interface: `http://localhost:8080/swagger-ui/index.html`
- Especificação OpenAPI (JSON): `http://localhost:8080/v3/api-docs`

Para testar rotas protegidas, faça login em `/auth/login` e envie o token no cabeçalho `Authorization: Bearer <token>`.

---

## Arquitetura

O projeto segue uma arquitetura em camadas:

```
br.com.alphacoach.app
├── controller/   Endpoints REST
├── service/      Orquestração e controle de transação
├── model/        Entidades JPA com regras de negócio no domínio
│   └── enums/    Status e tipos
├── repository/   Interfaces Spring Data JPA
├── dto/
│   ├── request/  Records de entrada, com validações
│   └── response/ Records de saída
├── config/       Segurança, JWT e filtro de autenticação
├── exception/    Exceções de negócio e handler global
└── export/       Geração da planilha de treino
```

Decisões de design:

- **Regras no domínio:** as entidades concentram as regras em métodos próprios (`Parcela.pagar()`, `Matricula.cancelarMatricula()`, `AgendaTreino.realizarCheckIn()` e outros). Os services buscam os dados, chamam esses métodos e persistem.
- **Raízes de agregado:** `Matricula` (com `Parcela`), `PagamentoAluno` (com `PagamentoParcela`) e `Treinos` (com `ExercicioTreino`).
- **Exclusão lógica:** alunos, planos, exercícios e treinos são ativados e desativados, não apagados.
- **Valores monetários** em `BigDecimal` (precisão 10, escala 2).
- **Datas** no formato brasileiro: `dd/MM/yyyy` e `dd/MM/yyyy HH:mm`.

---

## Modelo de dados

| Entidade | Tabela | Descrição |
|---|---|---|
| `User` | `users` | Usuário do sistema (login) |
| `Aluno` | `alunos` | Dados pessoais, objetivo e anamnese |
| `Planos` | `planos` | Planos vendidos pela academia |
| `Matricula` | `matricula` | Vínculo de um aluno com um plano |
| `Parcela` | `parcela` | Parcelas geradas por uma matrícula |
| `PagamentoAluno` | `pagamento_aluno` | Pagamento feito por um aluno |
| `PagamentoParcela` | `pagamento_parcela` | Liga um pagamento a uma parcela |
| `AgendaTreino` | `agendaAluno` | Horários de treino agendados |
| `Exercicios` | `exercicios` | Catálogo de exercícios |
| `Treinos` | `treinos` | Treino de um aluno |
| `ExercicioTreino` | `exercicioTreino` | Exercício dentro de um treino, com a prescrição |

Relacionamentos principais:

- `Matricula` → `Aluno` (N:1), `Matricula` → `Planos` (N:1), `Matricula` → `Parcela` (1:N)
- `PagamentoAluno` → `Aluno` (N:1), `PagamentoAluno` → `PagamentoParcela` (1:N)
- `PagamentoParcela` → `Parcela` (N:1)
- `AgendaTreino` → `Aluno` (N:1)
- `Treinos` → `Aluno` (N:1), `Treinos` → `ExercicioTreino` (1:N), `ExercicioTreino` → `Exercicios` (N:1)

Enumerações:

| Enum | Valores |
|---|---|
| `AlunosTypes` | `ONLINE`, `PRESENCIAL` |
| `TipoPlano` | `MENSAL`, `TRIMESTRAL`, `SEMESTRAL`, `ANUAL`, `PERSONALIZADO` |
| `StatusMatricula` | `ATIVO`, `CANCELADO`, `VENCIDO` |
| `StatusParcela` | `EM_DIA`, `ATRASADA`, `PAGA`, `CANCELADA` |
| `FormaPagamento` | `DINHEIRO`, `CREDITO`, `DEBITO`, `PIX`, `BOLETO` |
| `UserTypes` | `PROFESSOR`, `ALUNO` |

---

## Regras de negócio

### Matrícula e parcelas

- Só é possível matricular um aluno **ativo** em um plano **ativo**.
- Não pode haver duas matrículas ativas do mesmo aluno no mesmo plano.
- Ao criar a matrícula, o status é `ATIVO` e a data de início é o dia atual.
- É gerada **uma parcela por mês de duração do plano**. O valor é dividido com arredondamento para baixo em duas casas e o resto vai para a **última parcela**.
- O vencimento da parcela *n* é a data de início mais *n* meses. A competência é o primeiro dia do mês do vencimento.
- Não é possível trocar o plano nem a data de início. Nesses casos, cancele e crie outra matrícula.
- Trocar o aluno da matrícula só é possível se nenhuma parcela estiver paga, o novo aluno estiver ativo e não tiver matrícula ativa no mesmo plano.
- Cancelar a matrícula cancela as parcelas em aberto.

### Parcelas

- Parcela paga ou cancelada não pode ser paga.
- Estorno: só vale para parcela paga e a devolve para `ATRASADA` (vencimento passado) ou `EM_DIA`.
- Parcela paga não pode ser cancelada (estorne antes).
- Só é possível alterar o vencimento de parcela em aberto.

### Pagamentos

- **Não existe pagamento parcial:** o valor pago deve ser exatamente o valor da parcela.
- Uma parcela não pode ser vinculada duas vezes ao mesmo pagamento.
- Pagamento com parcelas vinculadas não pode mudar de aluno nem de valor.
- Remover uma parcela do pagamento a estorna. Se o pagamento ficar sem parcelas, ele é excluído.

### Agenda e check-in

- Não pode haver dois agendamentos do mesmo aluno no mesmo horário.
- O check-in só pode ser feito de **30 minutos antes até 30 minutos depois** do horário agendado.
- Não é permitido check-in duplicado. Só é possível cancelar um check-in já realizado.
- Reagendar exige uma data no futuro.

### Alunos, exercícios e treinos

- O CPF do aluno é único.
- Um exercício vinculado a algum treino não pode ser desativado.
- O link de vídeo do exercício deve ser uma URL `http` ou `https` válida.

---

## Autenticação e segurança

1. `POST /auth/register` cria um usuário (senha salva com BCrypt).
2. `POST /auth/login` retorna um token JWT (HMAC256, validade de 7 dias).
3. As demais rotas exigem `Authorization: Bearer <token>`.

Rotas públicas: `POST /auth/login`, `POST /auth/register`, `GET /swagger-ui/**` e `GET /v3/api-docs/**`.

A sessão é *stateless* e o CSRF está desativado.

> **Atenção:** o enum `UserTypes` existe, mas as regras de autorização por perfil estão comentadas em `SecurityConfig`. Hoje, qualquer usuário autenticado acessa todos os endpoints.

---

## Endpoints

Datas: `dd/MM/yyyy` e `dd/MM/yyyy HH:mm`.

### Autenticação - `/auth`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/auth/register` | Cria um usuário |
| POST | `/auth/login` | Autentica e retorna o token |

```json
{ "name": "Maria Souza", "email": "maria@email.com", "password": "senha123", "cpf": "99999999999" }
```

```json
{ "email": "maria@email.com", "password": "senha123" }
```

### Alunos - `/alunos`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/alunos` | Lista alunos ativos |
| GET | `/alunos/todos` | Lista todos os alunos |
| GET | `/alunos/{id}` | Busca por id |
| POST | `/alunos` | Cadastra aluno |
| PATCH | `/alunos/{id}` | Altera apenas os campos enviados |
| PATCH | `/alunos/{id}/remover` | Inativa o aluno |
| PATCH | `/alunos/{id}/recuperar` | Reativa o aluno |

```json
{
  "nome": "Lucas Antonio Monteiro",
  "email": "lucas@email.com",
  "cpf": "99999999999",
  "dataNascimento": "20/05/1998",
  "telefone": "16999999999",
  "objetivo": "Ganho de massa muscular",
  "anamnese": "Sedentário há 1 ano, sem doenças crônicas.",
  "tipoAluno": "PRESENCIAL",
  "endereco": "Rua das Avenidas, 1865"
}
```

### Planos - `/planos`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/planos` | Cria plano |
| GET | `/planos` | Lista planos |
| GET | `/planos/{id}` | Busca por id |
| PATCH | `/planos/{id}` | Altera campos enviados |
| PATCH | `/planos/{id}/ativar` | Ativa o plano |
| PATCH | `/planos/{id}/desativar` | Desativa o plano |

```json
{ "descricao": "Trimestral 2026", "valor": 429.90, "duracaoMeses": 3, "tipoPlano": "TRIMESTRAL" }
```

### Matrículas - `/matricula`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/matricula` | Cria matrícula e gera as parcelas |
| GET | `/matricula` | Lista matrículas ativas |
| GET | `/matricula/todos` | Lista todas |
| GET | `/matricula/{id}` | Busca por id |
| PATCH | `/matricula/{id}` | Altera (ver regras) |
| PATCH | `/matricula/{id}/desativar` | Cancela a matrícula e as parcelas em aberto |

### Parcelas - `/parcela`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/parcela/{id}` | Altera o vencimento |
| PATCH | `/parcela/{id}/estornar` | Estorna o pagamento |
| PATCH | `/parcela/{id}/cancelar` | Cancela a parcela |
| PATCH | `/parcela/{id}/atualizar/status` | Atualiza o status conforme o vencimento |

### Pagamentos - `/pagamento`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/pagamento` | Registra o pagamento de uma parcela |
| GET | `/pagamento` | Lista pagamentos |
| GET | `/pagamento/{id}` | Busca por id |
| PATCH | `/pagamento/{id}` | Altera dados do pagamento |
| DELETE | `/pagamento/{id}` | Estorna todas as parcelas e exclui o pagamento |
| DELETE | `/pagamento/{id}/remover/{idItem}` | Remove uma parcela do pagamento (`idItem` = id do `PagamentoParcela`) |

```json
{
  "alunoId": 1,
  "dataPagamento": "17/10/2026",
  "valorPago": 143.30,
  "formaPagamento": "PIX",
  "parcelaId": 10
}
```

### Agenda - `/agendatreino`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/agendatreino` | Agenda um horário |
| GET | `/agendatreino` | Lista agendamentos |
| GET | `/agendatreino/{id}` | Busca por id |
| PATCH | `/agendatreino/{id}` | Altera aluno e/ou horário |
| PATCH | `/agendatreino/{id}/realiza/checkin` | Faz check-in |
| PATCH | `/agendatreino/{id}/cancelar/checkin` | Cancela o check-in |
| PATCH | `/agendatreino/{id}/reagendar/data` | Reagenda |
| DELETE | `/agendatreino/{id}` | Remove o agendamento |

```json
{ "alunoId": 1, "dataEHorario": "20/10/2026 08:00" }
```

### Exercícios - `/exercicios`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/exercicios` | Cadastra exercício |
| GET | `/exercicios` | Lista exercícios ativos |
| GET | `/exercicios/todos` | Lista todos |
| GET | `/exercicios/{id}` | Busca por id |
| PATCH | `/exercicios/{id}` | Altera campos enviados |
| PATCH | `/exercicios/{id}/remover` | Desativa (bloqueado se estiver em algum treino) |
| PATCH | `/exercicios/{id}/recuperarExercicio` | Reativa |

```json
{ "nome": "Supino Reto", "descricao": "Peitoral maior com barra no banco reto.", "link": "https://www.youtube.com/watch?v=exemplo" }
```

### Treinos - `/treinos`

| Método | Rota | Descrição |
|---|---|---|
| POST | `/treinos` | Cria treino com seus exercícios |
| GET | `/treinos` | Lista treinos |
| GET | `/treinos/{id}` | Busca por id |
| PATCH | `/treinos/{id}` | Altera nome/aluno e **adiciona** os exercícios enviados |
| PATCH | `/treinos/{id}/ativar` | Ativa o treino |
| PATCH | `/treinos/{id}/desativar` | Desativa o treino |
| GET | `/treinos/{id}/relatorio/download` | Baixa a ficha de treino em `.xlsx` |

```json
{
  "nome": "Treino A - Peito e Tríceps",
  "alunoId": 1,
  "exercicios": [
    {
      "exercicioId": 1,
      "series": 4,
      "repeticoes": 12,
      "carga": "20kg",
      "potencia": 75.0,
      "intensidade": 80.0,
      "tempoDescanso": 60,
      "observacao": "Controlar a descida"
    }
  ]
}
```

Obrigatórios em cada exercício: `exercicioId`, `series`, `repeticoes` e `carga`. O `tempoDescanso` é em segundos.

### Exercícios do treino - `/exerciciotreino`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/exerciciotreino` | Lista |
| GET | `/exerciciotreino/{id}` | Busca por id |
| PATCH | `/exerciciotreino/{id}` | Altera a prescrição (carga, séries, repetições, descanso, intensidade, potência, status, observação) |
| DELETE | `/exerciciotreino/{id}` | Remove o exercício do treino |

Os exercícios entram no treino pelos endpoints de `/treinos`.

---

## Tratamento de erros

O `GlobalExceptionHandler` devolve um corpo padronizado:

```json
{ "timestamp": "17/10/2026 10:30", "status": 400, "message": "Parcela já está paga", "path": "/pagamento" }
```

| Exceção | HTTP | Quando |
|---|---|---|
| `BusinessException` | 400 | Violação de regra de negócio |
| `CheckInException` | 422 | Check-in duplicado |

---

## Exportação de planilha

`GET /treinos/{id}/relatorio/download` gera o arquivo `planilha-treino.xlsx` (Apache POI) com:

- Cabeçalho "ALPHACOACH - FICHA DE TREINO"
- Dados do aluno, objetivo, nome do treino e data de criação
- Tabela de exercícios: exercício, séries, repetições, carga, descanso (segundos), intensidade, concluído, link do vídeo e observações
- Coluna "Concluído" com lista suspensa (Sim/Não)

---

## Próximos passos

- Ampliar o `GlobalExceptionHandler` (404 para recurso não encontrado, 400 para validação e check-in fora da janela)
- Ativar a autorização por perfil (`PROFESSOR` / `ALUNO`) e restringir `/auth/register`
- Substituir o retorno de entidades JPA por DTOs de resposta nas listagens
- Declarar o esquema de segurança Bearer no OpenAPI para testar rotas protegidas no Swagger
- Ampliar a cobertura de testes (regras de parcelas, pagamentos e check-in)

---

## Autor

Desenvolvido por [Lucas Monte](https://github.com/Lucas-Monte).