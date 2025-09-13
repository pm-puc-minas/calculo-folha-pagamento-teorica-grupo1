# Cartões CRC 

### 1. Funcionário

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| Funcionario | • Armazenar dados pessoais do funcionário (nome, CPF)<br>• Controlar data de admissão<br>• Gerenciar cargo e salário bruto<br>• Manter informações da jornada de trabalho | • JornadaTrabalho<br>• FolhaPagamento<br>• SistemaAutenticacao |


### 2. Jornada de Trabalho

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| JornadaTrabalho | • Definir horas diárias de trabalho<br>• Definir dias da semana trabalhados<br>• Calcular jornada semanal total | • Funcionario |


### 3. Folha de Pagamento

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| FolhaPagamento | • Gerenciar mês de referência<br>• Controlar salário bruto<br>• Calcular salário líquido<br>• Gerar relatório de pagamento<br>• Coordenar adicionais, benefícios e descontos | • Funcionario<br>• Adicional<br>• Beneficio<br>• Desconto |


### 4. Sistema de Autenticação

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| SistemaAutenticacao | • Cadastrar usuários no sistema<br>• Validar credenciais de acesso<br>• Gerenciar sessões de usuários<br>• Controlar acesso ao sistema | • Funcionario |


### 5. Interface Adicional

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| Adicional | • Definir contrato para cálculo de valores adicionais<br>• Padronizar método calcularValor() | • Periculosidade<br>• Insalubridade<br>• FolhaPagamento |


### 6. Periculosidade

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| Periculosidade | • Armazenar percentual de periculosidade<br>• Calcular valor do adicional de periculosidade<br>• Implementar regras específicas de periculosidade | • Adicional<br>• FolhaPagamento |


### 7. Insalubridade

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| Insalubridade | • Definir grau de insalubridade<br>• Armazenar percentual correspondente<br>• Calcular valor do adicional de insalubridade<br>• Aplicar regras por grau de insalubridade | • Adicional<br>• FolhaPagamento |


### 8. Interface Benefício

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| Beneficio | • Definir contrato para cálculo de benefícios<br>• Padronizar método calcularValor() | • ValeTransporte<br>• ValeAlimentacao<br>• FGTS<br>• FolhaPagamento |


### 9. Vale Transporte

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| ValeTransporte | • Armazenar valor total do vale transporte<br>• Calcular valor do benefício de transporte<br>• Aplicar regras específicas do vale transporte | • Beneficio<br>• FolhaPagamento |


### 10. Vale Alimentação

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| ValeAlimentacao | • Definir valor diário da alimentação<br>• Controlar dias úteis do mês<br>• Calcular valor total do vale alimentação<br>• Aplicar regras do benefício alimentação | • Beneficio<br>• FolhaPagamento |


### 11. FGTS

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| FGTS | • Manter percentual padrão do FGTS (8%)<br>• Calcular valor do FGTS sobre salário<br>• Aplicar regras legais do FGTS | • Beneficio<br>• FolhaPagamento |


### 12. Interface Desconto

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| Desconto | • Definir contrato para cálculo de descontos<br>• Padronizar método calcularValor() | • INSS<br>• IRRF<br>• FolhaPagamento |


### 13. INSS

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| INSS | • Manter tabela de alíquotas do INSS<br>• Calcular desconto conforme faixas salariais<br>• Aplicar regras progressivas do INSS<br>• Respeitar teto de contribuição | • Desconto<br>• FolhaPagamento |


### 14. IRRF

| **Classes** | **Responsabilidades** | **Colaboradores** |
|-------------|----------------------|-------------------|
| IRRF | • Controlar número de dependentes<br>• Manter tabela de alíquotas do IR<br>• Calcular imposto de renda retido na fonte<br>• Aplicar deduções por dependentes<br>• Considerar faixas de isenção | • Desconto<br>• FolhaPagamento |


