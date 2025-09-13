# Modelo do Frontend (UI)

### Tela 1: Login

**Objetivo:**  
Autenticar o usuário no sistema.

**Elementos:**
- Campo de entrada para **Usuário**  
- Campo de entrada para **Senha**  
- Botão **"ENTRAR"** para processar a autenticação  

**Interações:**
1. O usuário insere suas credenciais e clica em **"ENTRAR"**  
2. O front-end envia essas credenciais para um **endpoint de autenticação** no back-end  
3. Se a autenticação for bem-sucedida, o usuário é redirecionado para o **Menu Principal**  
4. Se falhar, é exibida uma mensagem de erro (ex.: “Usuário ou senha inválidos”)  

### Tela 2: Menu Principal

**Objetivo:**  
Atuar como o painel de controle do sistema, dando acesso às funcionalidades principais.

**Elementos:**
- Opções de navegação: **Cadastrar Funcionário**, **Calcular Folha de Pagamento**, **Listar Pagamentos**  
- Resumo de dados do sistema: total de funcionários, valor da folha mensal e número de processamentos pendentes  

**Interações:**
1. Clicar em **"Cadastrar Funcionário"** leva o usuário a uma tela/formulário para adicionar novos funcionários  
2. Clicar em **"Calcular Folha de Pagamento"** inicia o processamento dos cálculos salariais via back-end  
3. Clicar em **"Listar Pagamentos"** direciona para uma tela que exibe o histórico de pagamentos  

### Tela 3: Relatório de Pagamento

**Objetivo:**  
Exibir o demonstrativo detalhado da folha de pagamento de um funcionário.

**Elementos:**
- Informações de identificação: nome, cargo e matrícula  
- Resumo do pagamento: **Salário Bruto**, **Descontos**, **Salário Líquido**  
- Seção de detalhamento em tabela: descrição, tipo e valor de cada provento e desconto  
- Informações de referência: período da folha (mês/ano) e data de processamento  

**Interações:**
1. Tela acessada a partir da opção **"Listar Pagamentos"** no Menu Principal ou de um botão específico de relatório  
2. Dados exibidos são recebidos de um **endpoint do back-end**  
3. Possibilidade de exportar ou gerar **relatório em PDF**  
