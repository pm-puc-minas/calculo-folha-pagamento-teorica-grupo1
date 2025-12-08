import { FuncionarioDTO, Provento, Desconto, ResumoFolha } from '../types/index.js';

const SALARIO_MINIMO = 1380.60;
const HORAS_MENSAIS_PADRAO = 200;

export function calcularSalarioHora(
  salarioBruto: number,
  horasMensais: number = HORAS_MENSAIS_PADRAO
): number {
  if (horasMensais <= 0) {
    throw new Error('Horas mensais devem ser maiores que zero');
  }
  return Math.round((salarioBruto / horasMensais) * 100) / 100;
}

export function calcularPericulosidade(salarioBruto: number): number {
  return Math.round(salarioBruto * 0.30 * 100) / 100;
}

export function calcularInsalubridade(grau: string): number {
  const percentuais: Record<string, number> = {
    BAIXO: 0.10,
    MEDIO: 0.20,
    ALTO: 0.40,
  };
  const percentual = percentuais[grau] || 0;
  return Math.round(SALARIO_MINIMO * percentual * 100) / 100;
}

export function calcularValeTransporte(
  valorSolicitado: number,
  salarioBruto: number
): number {
  const limiteDesconto = salarioBruto * 0.06;
  return Math.round(Math.min(valorSolicitado, limiteDesconto) * 100) / 100;
}

export function calcularValeAlimentacao(valorDiario: number): number {
  const diasUteis = 26;
  return Math.round(valorDiario * diasUteis * 100) / 100;
}

export function calcularINSS(salario: number): number {
  const TETO_INSS = 877.24;
  
  if (salario <= 1302.00) {
    return Math.round(salario * 0.075 * 100) / 100;
  } else if (salario <= 2571.29) {
    const parte1 = 1302.00 * 0.075;
    const parte2 = (salario - 1302.00) * 0.09;
    return Math.round((parte1 + parte2) * 100) / 100;
  } else if (salario <= 3856.94) {
    const parte1 = 1302.00 * 0.075;
    const parte2 = (2571.29 - 1302.00) * 0.09;
    const parte3 = (salario - 2571.29) * 0.12;
    return Math.round((parte1 + parte2 + parte3) * 100) / 100;
  } else if (salario <= 7507.49) {
    const parte1 = 1302.00 * 0.075;
    const parte2 = (2571.29 - 1302.00) * 0.09;
    const parte3 = (3856.94 - 2571.29) * 0.12;
    const parte4 = (salario - 3856.94) * 0.14;
    return Math.round((parte1 + parte2 + parte3 + parte4) * 100) / 100;
  } else {
    return TETO_INSS;
  }
}

export function calcularFGTS(salarioBruto: number): number {
  return Math.round(salarioBruto * 0.08 * 100) / 100;
}

export function calcularIRRF(
  salarioBruto: number,
  descontoINSS: number,
  numeroDependentes: number
): number {
  const DEDUCAO_DEPENDENTE = 189.59;
  const baseCalculo = salarioBruto - descontoINSS - (numeroDependentes * DEDUCAO_DEPENDENTE);
  
  if (baseCalculo <= 0) {
    return 0;
  }
  
  if (baseCalculo <= 1903.98) {
    return 0;
  } else if (baseCalculo <= 2826.65) {
    return Math.max(0, Math.round((baseCalculo * 0.075 - 142.80) * 100) / 100);
  } else if (baseCalculo <= 3751.05) {
    return Math.max(0, Math.round((baseCalculo * 0.15 - 354.80) * 100) / 100);
  } else if (baseCalculo <= 4664.68) {
    return Math.max(0, Math.round((baseCalculo * 0.225 - 636.13) * 100) / 100);
  } else {
    return Math.max(0, Math.round((baseCalculo * 0.275 - 869.36) * 100) / 100);
  }
}

export function processarFolhaPagamento(
  funcionario: FuncionarioDTO,
  detalhamentoCalculos: Record<string, number>
): {
  proventos: Provento[];
  descontos: Desconto[];
  resumo: ResumoFolha;
} {
  const proventos: Provento[] = [];
  const descontos: Desconto[] = [];
  
  let totalProventos = 0;
  let totalDescontos = 0;
  
  for (const [key, value] of Object.entries(detalhamentoCalculos)) {
    if (key === 'Salário Bruto' || key === 'Total Adicionais' || key === 'Salário Líquido' || key === 'Total Descontos') {
      continue;
    }
    
    if (value > 0) {
      proventos.push({ descricao: key, valor: value });
      totalProventos += value;
    } else if (value < 0) {
      descontos.push({ descricao: key, valor: Math.abs(value) });
      totalDescontos += Math.abs(value);
    }
  }
  
  const salarioBruto = detalhamentoCalculos['Salário Bruto'] || funcionario.salarioBruto;
  const salarioHora = detalhamentoCalculos['Salário por Hora'] || 
    calcularSalarioHora(salarioBruto, funcionario.jornadaTrabalho.horasMensais);
  const salarioLiquido = detalhamentoCalculos['Salário Líquido'] || 
    (salarioBruto + totalProventos - totalDescontos);
  
  const resumo: ResumoFolha = {
    salarioBruto,
    totalProventos,
    totalDescontos,
    salarioLiquido,
    salarioHora,
  };
  
  return { proventos, descontos, resumo };
}

