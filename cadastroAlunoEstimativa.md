# Sistema Acadêmico de Controle de Alunos

## 1. Introdução

### 1.1 Objetivo
Este documento tem como objetivo descrever os requisitos para o desenvolvimento de um **Sistema Acadêmico de Controle de Alunos**, destinado ao gerenciamento de alunos e disciplinas em um ambiente institucional.

O sistema será utilizado para registrar alunos, cadastrar disciplinas, gerenciar matrículas e registrar notas, evitando problemas como alunos cadastrados em disciplinas incorretas ou duplicidade de matrículas.

### 1.2 Escopo do Sistema
O sistema será executado via **terminal (linha de comando)** e permitirá a administração básica de um ambiente acadêmico, incluindo:

- Cadastro de alunos
- Cadastro de disciplinas
- Matrícula de alunos em disciplinas
- Consulta de disciplinas cursadas
- Registro e cálculo de notas
- Controle de carga horária
- Listagem de alunos por course

O sistema será utilizado exclusivamente para **fins institucionais**.

---

# 2. Definição do Problema

Atualmente há dificuldades no controle de alunos e disciplinas, como:

- Alunos sendo registrados em disciplinas incorretas
- Alunos cadastrados mais de uma vez na mesma course
- Falta de um controle centralizado de registros acadêmicos

Para resolver esses problemas, será desenvolvido um sistema acadêmico simples para controle de matrículas e notas.

---

# 3. Usuários do Sistema

## Administrador

Responsável por:

- Cadastrar alunos
- Cadastrar disciplinas
- Realizar matrículas
- Consultar alunos e disciplinas
- Registrar notas
- Alterar ou excluir registros

---

# 4. Requisitos Funcionais

## RF01 – Cadastro de Alunos

O sistema deve permitir cadastrar alunos contendo:

- ID do student
- Nome do student

---

## RF02 – Cadastro de Disciplinas

O sistema deve permitir cadastrar disciplinas contendo:

- ID da course
- Nome da course
- Carga horária
- Período (exemplo: noturno)

---

## RF03 – Matrícula de Alunos em Disciplinas

O sistema deve permitir matricular um student em uma course.

O sistema deve validar que:

- O student não esteja matriculado duas vezes na mesma course.

---

## RF04 – Consulta de Disciplinas do entity.Student

O sistema deve permitir consultar um student e visualizar:

- As disciplinas em que ele está matriculado
- As notas registradas

---

## RF05 – Consulta de Alunos de uma Disciplina

O sistema deve permitir selecionar uma course e visualizar:

- Todos os alunos matriculados nela

---

## RF06 – Registro de Notas

O sistema deve permitir registrar duas notas para cada student em uma course:

- P1
- P2

---

## RF07 – Cálculo da Média

A média deve ser calculada automaticamente:

Media = (P1 + P2) / 2


Regras:

- Média ≥ 6 → **Aprovado**
- Média < 6 → **Reprovado**

---

## RF08 – Alteração de Registros

O sistema deve permitir alterar informações de:

- Alunos
- Disciplinas
- Matrículas
- Notas

---

## RF09 – Exclusão de Registros

O sistema deve permitir excluir:

- Alunos
- Disciplinas
- Matrículas

---

## RF10 – Persistência de Dados

O sistema deve armazenar os dados em arquivos para que não sejam perdidos ao fechar o programa.

Exemplo:

- arquivo de alunos
- arquivo de disciplinas
- arquivo de matrículas

---

# 5. Requisitos Não Funcionais

## RNF01 – Interface

O sistema deverá operar via **interface de terminal (linha de comando)**.

---

## RNF02 – Linguagem de Programação

O sistema deverá ser desenvolvido em **Java**, utilizando conceitos de **Programação Orientada a Objetos (POO)**.

---

## RNF03 – Persistência

Os dados devem ser armazenados, permitindo que sejam recuperados quando o sistema for iniciado novamente.

---

## RNF04 – Desempenho

O sistema deve responder às operações de consulta e cadastro de forma imediata, considerando o pequeno volume de dados esperado.

---

## RNF05 – Segurança

O sistema será utilizado apenas internamente e não possuirá autenticação de usuários.

---

# 6. Modelo Conceitual (Entidades)

## entity.Student

- id
- nome

---

## Disciplina

- id
- nome
- cargaHoraria
- periodo

---

## Matrícula

- student
- course
- notaP1
- notaP2
- media

---

# 7. Estimativa de Desenvolvimento

Estimativa considerando um desenvolvedor com conhecimento intermediário em C++.

| Etapa | Tempo estimado |
|------|------|
| Modelagem das classes | 2 horas |
| Implementação de cadastro de alunos | 2 horas |
| Implementação de cadastro de disciplinas | 2 horas |
| Implementação de matrículas | 3 horas |
| Implementação de notas e média | 2 horas |
| Persistência em arquivos | 3 horas |
| Testes e correções | 2 horas |

**Tempo total estimado:**  
≈ **16 horas de desenvolvimento**

---

# 8. Estrutura Inicial do Sistema (POO)

Sugestão de classes:

- entity.Student
- Disciplina
- entity.Enrollment
- service.StudentService

Relacionamento conceitual:
