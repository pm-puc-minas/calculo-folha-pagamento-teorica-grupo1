# Relatório Sprint 3 - Coleções, Streams, Persistência e Eventos

## Coleções Java

O projeto utiliza diferentes tipos de coleções para organizar os dados da folha de pagamento. Trabalhamos com `ArrayList` para armazenar listas de adicionais, descontos e benefícios dentro da classe `FolhaPagamento`, além de usar essas listas nos retornos dos Services e Controllers. 

Na classe `FolhaPagamento`, implementamos três listas principais:
- `List<IAdicional> adicionais` - armazena adicionais aplicáveis como periculosidade e insalubridade
- `List<IDesconto> descontos` - armazena descontos obrigatórios como INSS e IRRF
- `List<IBeneficio> beneficios` - armazena benefícios como vale transporte e vale alimentação

Para manter a ordem dos cálculos realizados, implementamos um `LinkedHashMap` no campo `detalhamentoCalculos`, que armazena cada item calculado com sua descrição e valor, preservando a sequência de inserção. O sistema de autenticação utiliza `HashMap` para armazenar as credenciais dos usuários.

As coleções são inicializadas no construtor da classe `FolhaPagamento` e populadas através do método `inicializarCalculos()`, que adiciona as instâncias de cálculo às respectivas listas.

## Processamento com Streams

Os Streams foram aplicados principalmente no método `calcularSalarioLiquido()` da classe `FolhaPagamento`, onde processamos as listas de adicionais, descontos e benefícios através de operações de filtragem e soma.

Para os adicionais, utilizamos a cadeia de operações:
```java
adicionais.stream()
    .filter(adicional -> adicional.temDireito(funcionario))
    .mapToDouble(adicional -> adicional.calcular(funcionario))
    .sum()
```

Essa mesma abordagem foi aplicada para descontos e benefícios, processando apenas os itens aplicáveis ao funcionário e calculando seus valores de forma declarativa. Durante o processamento, cada valor calculado é armazenado no `Map<String, Double> detalhamentoCalculos` para posterior consulta.

Também utilizamos Streams nos Controllers para converter as entidades em DTOs. Por exemplo, no `FuncionarioController`, a listagem utiliza:
```java
funcionarioService.listarFuncionarios().stream()
    .map(FuncionarioDTO::new)
    .collect(Collectors.toList())
```

## Persistência de Dados

Configuramos o PostgreSQL como banco de dados relacional, integrando o Spring Data JPA e Hibernate para gerenciar a persistência. As dependências `spring-boot-starter-data-jpa` e `postgresql` foram adicionadas ao projeto.

As classes `Funcionario` e `FolhaPagamento` foram anotadas como entidades JPA com `@Entity`, utilizando `@Id` e `@GeneratedValue` para geração automática de identificadores. A classe `JornadaTrabalho` foi marcada como `@Embeddable`, sendo embutida na tabela de funcionários através da anotação `@Embedded`.

O relacionamento entre `FolhaPagamento` e `Funcionario` foi estabelecido com `@ManyToOne`, criando uma chave estrangeira `funcionario_id` na tabela de folhas. Campos de cálculo foram marcados como `@Transient` para não serem persistidos, mantendo apenas os dados essenciais no banco.

Criamos repositórios que estendem `JpaRepository` com métodos customizados:
- `FuncionarioRepository.findByCpf(String cpf)` - busca funcionário por CPF
- `FolhaPagamentoRepository.findByFuncionarioId(Long id)` - busca folhas de um funcionário

A camada de serviços foi estruturada com anotações `@Service` e `@Transactional`. O Hibernate foi configurado com `ddl-auto=update` para criação automática de tabelas.

## Sistema de Eventos

Implementamos a classe `RegistradorEventos` seguindo o padrão Singleton, com dois métodos principais:

- `logFuncionarioCadastrado(Funcionario funcionario)`: registra um log quando um funcionário é cadastrado, exibindo nome, CPF e cargo com timestamp formatado
- `notificarFolhaGerada(FolhaPagamento folha)`: envia uma notificação quando uma folha de pagamento é gerada, contendo informações do funcionário e o salário líquido calculado

Esses eventos foram integrados aos métodos dos Services para serem acionados automaticamente durante as operações. No `FuncionarioService`, o evento é disparado após salvar o funcionário no banco de dados. No `FolhaPagamentoService`, a notificação é enviada após calcular e salvar a folha.

As mensagens são formatadas com data e hora no padrão `dd/MM/yyyy HH:mm:ss` e exibidas no console do sistema.

## Preparação para o Frontend

A arquitetura foi organizada seguindo o padrão MVC, com DTOs para comunicação com o frontend. Foram implementados três DTOs principais:

- `FuncionarioDTO`: encapsula dados de funcionário, incluindo `JornadaTrabalhoDTO` embutido, com métodos de conversão para entidade
- `FolhaPagamentoDTO`: encapsula dados de folha, calculando totais de adicionais e descontos a partir do Map de detalhamento
- `JornadaTrabalhoDTO`: encapsula dados de jornada de trabalho

Os Controllers REST oferecem endpoints completos para operações CRUD:
- `FuncionarioController`: POST, GET, GET por ID, PUT e DELETE para funcionários
- `FolhaPagamentoController`: POST para gerar folha, PUT para recalcular, GET para listar e buscar folhas

Todos os endpoints retornam respostas JSON com `ResponseEntity` e tratamento de erros com códigos HTTP apropriados (400 para requisições inválidas, 404 para recursos não encontrados, 201 para criação).

A estrutura separa claramente as responsabilidades entre Model (entidades JPA), View (DTOs), Controllers (endpoints REST), Services (regras de negócio e validações) e Repositories (acesso a dados via Spring Data JPA).

## Testes das Novas Funcionalidades

Desenvolvemos testes unitários para o `RegistradorEventos`. Os testes validam:

- Geração de log de cadastro: confirma que o método `logFuncionarioCadastrado()` gera mensagem correta com dados do funcionário
- Geração de notificação: verifica que `notificarFolhaGerada()` produz notificação com informações da folha e salário líquido

Os testes capturam a saída do console através de `ByteArrayOutputStream`, validando o formato e o conteúdo das mensagens geradas.
