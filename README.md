# AlphaCoach - API de Gestão de Alunos e Treinos

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de alunos, planos, agendas de treino, exercícios e treinos de uma academia ou personal trainer.

---

## Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

---

## Configuração

No arquivo `src/main/resources/application.properties`, configure sua conexão com o banco:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/alphacoach
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Entidades

| Entidade | Tabela | Descrição |
|---|---|---|
| Aluno | alunos | Cadastro de alunos com dados pessoais, plano e anamnese |
| Planos | planos | Planos disponíveis para os alunos |
| AgendaTreino | agendaAluno | Agendamentos de horários de treino |
| Exercicios | exercicios | Catálogo de exercícios disponíveis |
| Treinos | treinos | Treinos vinculados a um aluno |
| ExercicioTreino | exercicioTreino | Exercícios dentro de um treino com séries, cargas e descanso |

---

## Endpoints e Exemplos de JSON

### Alunos `/alunos`

#### POST — Cadastrar aluno
```json
{
  "nome": "Lucas Antonio Monteiro",
  "email": "lucas@email.com",
  "cpf": "99999999999",
  "dataNascimento": "1998-05-20",
  "endereco": "Rua das Avenidas, 1865, Plano Novo",
  "tipoCliente": "Presencial",
  "ativo": true,
  "telefone": "16999999999",
  "objetivo": "Ganho de massa muscular",
  "anamnese": "Masculino, 28 anos, 1,78m, 72kg. Sedentário há 1 ano. Sem doenças crônicas, relata leve desconforto no joelho direito. Alimentação irregular. Dorme 6h por noite. Disponível 4x por semana no período noturno.",
  "plano": { "id": 1 }
}
```

> **tipoCliente** aceita apenas: `"Presencial"` ou `"Online"`  
> **anamnese** suporta até 500 caracteres  
> **dataNascimento** no formato `"yyyy-MM-dd"`

---

#### PUT — Atualizar aluno completo
```json
{
  "id": 1,
  "nome": "Lucas Paulo Ferreira",
  "email": "lucas.novo@email.com",
  "cpf": "99999999999",
  "dataNascimento": "1998-05-20",
  "endereco": "Av. Paulista, 500 - São Paulo, SP",
  "tipoCliente": "Online",
  "ativo": true,
  "telefone": "16988888888",
  "objetivo": "Emagrecimento e condicionamento",
  "anamnese": "Masculino, 28 anos, 1,78m, 74kg. Praticou musculação por 8 meses há 2 anos. Sem doenças crônicas. Alimentação irregular. Disponível 3x por semana.",
  "plano": { "id": 2 }
}
```

---

#### PATCH — Atualizar campos específicos
```json
{
  "dataNascimento": "1998-05-20"
}
```
```json
{
  "email": "novo@email.com",
  "telefone": "16977777777"
}
```
```json
{
  "plano": { "id": 3 }
}
```
```json
{
  "anamnese": "Masculino, 28 anos, 1,78m, 74kg. Relata melhora no desconforto do joelho. Alimentação regularizada. Dormindo 7h por noite."
}
```

---

#### DELETE — Desativar aluno
```
DELETE /alunos/{id}
```
O aluno é removido do banco.

---

### Planos `/planos`

#### POST — Cadastrar plano
```json
{
  "descricao": "Mensal 2026",
  "valor": 159.90,
  "duracaoMeses": 1,
  "tipoPlano": "Mensal",
  "ativo": true
}
```
```json
{
  "descricao": "Trimestral 2026",
  "valor": 429.90,
  "duracaoMeses": 3,
  "tipoPlano": "Trimestral",
  "ativo": true
}
```
```json
{
  "descricao": "Semestral 2026",
  "valor": 799.90,
  "duracaoMeses": 6,
  "tipoPlano": "Semestral",
  "ativo": true
}
```

---

### Agenda de Treino `/agenda`

#### POST — Agendar horário
```json
{
  "data": "2026-06-10T08:00:00",
  "checkIn": false,
  "aluno": { "id": 1 }
}
```

> **data** no formato ISO 8601: `"yyyy-MM-ddTHH:mm:ss"`  
> **checkIn** inicia sempre como `false`

---

#### PATCH — Registrar check-in
```json
{
  "checkIn": true
}
```

---

### Exercícios `/exercicios`

#### POST — Cadastrar exercício
```json
{
  "nome": "Supino Reto",
  "descricao": "Exercício para peitoral maior com barra ou halteres no banco reto.",
  "ativo": true,
  "linkVideo": "https://www.youtube.com/watch?v=exemplo1"
}
```
```json
{
  "nome": "Agachamento Livre",
  "descricao": "Exercício composto para quadríceps, glúteos e posteriores de coxa.",
  "ativo": true,
  "linkVideo": "https://www.youtube.com/watch?v=exemplo2"
}
```
```json
{
  "nome": "Remada Curvada",
  "descricao": "Exercício para costas com barra ou halteres em posição inclinada.",
  "ativo": true,
  "linkVideo": "https://www.youtube.com/watch?v=exemplo3"
}
```

> **ativo** é definido como `true` automaticamente ao cadastrar

---

#### DELETE — Desativar exercício
```
DELETE /exercicios/{id}
```

---

### Treinos `/treinos`

#### POST — Cadastrar treino
```json
{
  "nome": "Treino A - Peito e Tríceps",
  "aluno": { "id": 1 },
  "dataCriacao": "2026-06-04",
  "status": false,
  "exercicios": []
}
```

> **status** indica se o treino foi concluído. Inicia como `false`  
> **dataCriacao** no formato `"yyyy-MM-dd"`

---

### Exercício Treino `/exercicioTreino`

Vincula um exercício a um treino com parâmetros de execução.

#### POST — Adicionar exercício ao treino
```json
{
  "treino": { "id": 1 },
  "exercicio": { "id": 1 },
  "potencia": 75.0,
  "intensidade": 80.0,
  "series": 4,
  "repeticoes": 12,
  "carga": "20kg",
  "tempoDescanso": 60,
  "status": false
}
```
```json
{
  "treino": { "id": 1 },
  "exercicio": { "id": 2 },
  "potencia": 60.0,
  "intensidade": 70.0,
  "series": 3,
  "repeticoes": 10,
  "carga": "Peso corporal",
  "tempoDescanso": 90,
  "status": false
}
```

> **tempoDescanso** em segundos  
> **status** indica se o exercício foi concluído na sessão. Inicia como `false`

---

#### PATCH — Concluir exercício
```json
{
  "status": true
}
```

---

## Observações Gerais

- Ao referenciar entidades relacionadas (aluno, treino, exercício, plano), basta enviar o `{ "id": valor }` no corpo da requisição
- Campos com soft delete (`ativo`, `status`) nunca são removidos do banco, apenas desativados
- O campo `dataNascimento` usa `LocalDate` — formato `"yyyy-MM-dd"`
- O campo `data` de `AgendaTreino` usa `LocalDateTime` — formato `"yyyy-MM-ddTHH:mm:ss"`
- O campo `dataCriacao` de `Treinos` usa `LocalDate` — formato `"yyyy-MM-dd"`

---

## Como Rodar

```bash
# Clone o repositório
git clone https://github.com/Lucas-Monte/AlphaCoach-TIC.git

# Entre na pasta
cd AlphaCoach-TIC

# Rode a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.
