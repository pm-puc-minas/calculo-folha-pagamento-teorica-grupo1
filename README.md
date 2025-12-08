# Sistema de Gestão de Folha de Pagamento

## Visão Geral
Este projeto é um sistema de gestão de folha de pagamento para um software de recursos humanos, desenvolvido como trabalho final da disciplina de Programação Modular.  

A aplicação segue os princípios de modularidade, programação orientada a objetos (POO) e padrões de qualidade de software, com uma arquitetura dividida em back-end e front-end.  

O desenvolvimento será realizado em quatro sprints, com o objetivo de entregar uma aplicação completa e funcional.

**Professor:** Paulo Coelho  
**GitHub:** https://github.com/paulohdscoelho

### Integrantes do Projeto
- Matheus Guilherme Viana Pereira  
- Arthur Gonçalves Vieira  

#### Sprint 1: Análise e Modelagem
Esta entrega foca na fase inicial de análise e modelagem do sistema, preparando a base para as próximas sprints de implementação.  

Artefatos desenvolvidos:

- Diagrama de Classes: modelo UML que define a estrutura de classes do projeto, seus atributos, métodos e relacionamentos  
- Cartões CRC: documentação detalhada que especifica as responsabilidades de cada classe e suas colaborações  
- Modelo de Frontend (UI): descrição das telas e interações esperadas para a interface do usuário  
- Plano de Testes: documento que descreve os cenários de testes unitários que serão implementados nas próximas sprints  
- Esqueleto do Projeto: estrutura inicial do projeto em Java Spring Boot

#### Sprint 2: Herança, Interfaces, Polimorfismo e Testes Unitários
Implementação das classes e interfaces planejadas, aplicando conceitos de reuso através de herança, polimorfismo e interfaces. Foram desenvolvidas interfaces `IAdicional`, `IDesconto` e `IBeneficio` que definem contratos para os cálculos da folha de pagamento. Classes concretas implementam essas interfaces para realizar cálculos específicos como INSS, IRRF, Periculosidade, Insalubridade, Vale Transporte e Vale Alimentação. Testes unitários foram implementados para validar as funcionalidades desenvolvidas.

#### Sprint 3: Coleções, Streams, Persistência e Eventos
Utilização de coleções (`ArrayList`, `LinkedHashMap`) para organizar adicionais, descontos e benefícios. Implementação de Streams para processamento e filtragem de dados no cálculo do salário líquido. Persistência de dados em PostgreSQL com Spring Data JPA. Sistema de eventos implementado com a classe `RegistradorEventos` (padrão Singleton) para registrar logs de cadastro de funcionários e notificações de geração de folhas. Arquitetura preparada para integração com frontend através de DTOs e endpoints REST.

#### Sprint 4: Frontend Web, Integração e Padrões de Projeto
Implementação de frontend web utilizando React com TypeScript e Vite. Interface desenvolvida com componentes reutilizáveis para cadastro de funcionários, listagem, geração e visualização de folhas de pagamento. Consumo dos endpoints REST do backend através de serviço de API centralizado. Aplicação do padrão Factory no backend através da classe `CalculoFactory`, que centraliza a criação de instâncias de adicionais, descontos e benefícios, encapsulando a lógica de instanciação e facilitando a manutenção e extensão do sistema. Integração completa entre frontend e backend com tratamento de erros e validações. Sistema containerizado com Docker Compose para facilitar o deploy e execução.

## Tecnologias Utilizadas

**Backend:**
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

**Frontend:**
- React 18
- TypeScript
- Vite
- CSS3

**Infraestrutura:**
- Docker
- Docker Compose
- Nginx

## Endpoints da API

A API REST está disponível em `http://localhost:8080/api` e oferece os seguintes endpoints:

### Funcionários (`/api/funcionarios`)

- `GET /api/funcionarios` - Lista todos os funcionários
- `GET /api/funcionarios/{id}` - Busca funcionário por ID
- `POST /api/funcionarios` - Cadastra novo funcionário
- `PUT /api/funcionarios/{id}` - Atualiza funcionário existente
- `DELETE /api/funcionarios/{id}` - Remove funcionário

### Folhas de Pagamento (`/api/folhas`)

- `GET /api/folhas` - Lista todas as folhas de pagamento
- `GET /api/folhas/{id}` - Busca folha por ID
- `GET /api/folhas/funcionario/{funcionarioId}` - Lista folhas de um funcionário específico
- `POST /api/folhas/gerar/{funcionarioId}` - Gera nova folha de pagamento para um funcionário
- `PUT /api/folhas/{id}/calcular` - Recalcula uma folha de pagamento existente

Todos os endpoints retornam respostas em JSON e utilizam códigos HTTP apropriados (200 para sucesso, 201 para criação, 400 para erros de validação, 404 para recursos não encontrados).

## Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 21 (para desenvolvimento local)
- Node.js 20+ (para desenvolvimento local do frontend)

### Execução com Docker Compose
```bash
docker compose up --build
```

O sistema estará disponível em:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api

### Execução Local

**Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm run dev
```  
