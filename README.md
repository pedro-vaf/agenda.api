# 📞 Agenda Telefônica — API REST (Spring Boot)

API REST desenvolvida em **Spring Boot**, seguindo uma especificação completa de requisitos funcionais, regras de negócio e segurança.  
A aplicação utiliza **JWT** para autenticação, CRUD completo de **Contatos** e **Telefones**, validações robustas e tratamento padronizado de erros.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
  - Spring Web  
  - Spring Data JPA  
  - Spring Security  
- **JWT (JSON Web Token)**
- **Jakarta Validation**
- **H2 / PostgreSQL**
- **Swagger/OpenAPI**

---

## 🔐 Autenticação & Autorização

A aplicação utiliza **JWT** com autenticação stateless.

### Endpoints públicos
| Método | Rota | Descrição |
|--------|-------|-----------|
| `POST` | `/auth/register` | Registra um novo usuário |
| `POST` | `/auth/login` | Autentica um usuário e retorna o token de acesso |

### Regras de Segurança
- Demais endpoints exigem **Bearer Token**.
- O token é validado via filtro customizado (`OncePerRequestFilter`).
- Cada usuário só pode acessar **seus próprios contatos e telefones**.

---

## 📇 Modelos

### Contato
- `id`
- `nome`
- `email`

### Telefone
- `id`
- `ddd`
- `numero`
- `tipo` (RESIDENCIAL, COMERCIAL, CELULAR)

---

## 📂 Endpoints

### Contatos

| Método | Rota | Descrição |
|--------|--------|-----------|
| `POST` | `/contatos` | Criação de contato |
| `GET` | `/contatos` | Listagem de contatos |
| `GET` | `/contatos/{id}` | Consulta por ID |
| `PUT` | `/contatos/{id}` | Atualização |
| `DELETE` | `/contatos/{id}` | Exclusão |

### Telefones

| Método | Rota | Descrição |
|--------|----------------------------------------------|-----------|
| `POST` | `/contatos/{idContato}/telefones` | Criação de telefone |
| `GET` | `/contatos/{idContato}/telefones` | Lista telefones do contato |
| `GET` | `/contatos/{idContato}/telefones/{id}` | Consulta específica |
| `PUT` | `/contatos/{idContato}/telefones/{id}` | Atualização |
| `DELETE` | `/contatos/{idContato}/telefones/{id}` | Exclusão |

---

## ✔️ Validações

### Contatos
- `nome`: obrigatório  
- `email`: formato válido  

Validações feitas via **Jakarta Validation**.

---

## ⚠️ Tratamento de Erros

A aplicação possui tratamento global com `@ControllerAdvice`.

Formato padrão:

```json
{
  "status": 404,
  "mensagem": "Contato não encontrado",
  "timestamp": "2025-01-15T10:24:18"
}
