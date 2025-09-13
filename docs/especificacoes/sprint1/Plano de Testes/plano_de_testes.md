# Plano de Testes Unitários
## 1. Testes de Cálculo de Salário por Hora

RF1 - Calcular Salário Hora

- Cenário 1: Verificar o cálculo do salário por hora para um salário bruto de R$ 2.500,00 com uma jornada mensal de 200 horas.  
  Valor esperado: R$ 12,50 por hora  
- Cenário 2: Testar o cálculo para uma jornada mensal de 220 horas, garantindo a flexibilidade do sistema  

## 2. Testes de Cálculo de Adicionais

RF2 - Calcular Periculosidade

- Cenário 1: Testar se o valor do adicional é calculado como 30% do salário bruto para um funcionário com direito ao benefício  
- Cenário 2: Validar que o valor do adicional é zero para um funcionário que não tem direito  

RF3 - Calcular Insalubridade

- Cenário 1: Testar o cálculo para o grau de risco baixo (10% do salário mínimo)  
- Cenário 2: Testar o cálculo para o grau de risco médio (20% do salário mínimo)  
- Cenário 3: Testar o cálculo para o grau de risco alto (40% do salário mínimo)  
- Cenário 4: Garantir que o valor seja zero para um funcionário sem direito ao adicional  

## 3. Testes de Cálculo de Benefícios e Deduções

RF4 - Calcular Vale Transporte

- Cenário 1: Validar que o desconto de 6% do salário bruto é aplicado quando o valor do vale-transporte recebido é maior ou igual a essa porcentagem  
- Cenário 2: Testar se o desconto corresponde ao valor total do vale-transporte recebido quando este é menor que 6% do salário bruto  

RF5 - Calcular Vale Alimentação

- Cenário 1: Verificar se o valor total do vale-alimentação é calculado corretamente com base no valor diário e nos dias úteis do mês  

RF6 - Calcular Desconto de INSS

- Cenário 1: Testar o cálculo progressivo do INSS para um salário que se enquadra na primeira faixa  
- Cenário 2: Testar o cálculo do INSS para um salário que atravessa múltiplas faixas  
- Cenário 3: Validar que o cálculo é limitado ao teto máximo da contribuição para salários que excedem esse valor  

RF7 - Calcular FGTS

- Cenário 1: Testar se o cálculo corresponde a 8% do salário bruto do funcionário  

RF8 - Calcular Desconto de IRRF

- Cenário 1: Testar o cálculo do imposto de renda para um funcionário sem dependentes  
- Cenário 2: Testar o cálculo para um funcionário com dependentes, verificando se a dedução é aplicada corretamente  
- Cenário 3: Validar se o valor do imposto é zero para um salário isento  
- Cenário 4: Testar se a alíquota e a dedução corretas são aplicadas para um salário na faixa máxima de contribuição  

## 4. Teste de Cálculo do Salário Líquido

RF9 - Calcular Salário Líquido

- Cenário 1: Testar se o valor final é a subtração correta de todos os descontos (obrigatórios e opcionais) do salário bruto, após a adição de todos os proventos e benefícios  
