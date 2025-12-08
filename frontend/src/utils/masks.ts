export function formatarCPF(cpf: string): string {
  const apenasNumeros = cpf.replace(/\D/g, '');
  if (apenasNumeros.length <= 3) {
    return apenasNumeros;
  } else if (apenasNumeros.length <= 6) {
    return `${apenasNumeros.slice(0, 3)}.${apenasNumeros.slice(3)}`;
  } else if (apenasNumeros.length <= 9) {
    return `${apenasNumeros.slice(0, 3)}.${apenasNumeros.slice(3, 6)}.${apenasNumeros.slice(6)}`;
  } else {
    return `${apenasNumeros.slice(0, 3)}.${apenasNumeros.slice(3, 6)}.${apenasNumeros.slice(6, 9)}-${apenasNumeros.slice(9, 11)}`;
  }
}

export function removerFormatacaoCPF(cpf: string): string {
  return cpf.replace(/\D/g, '');
}

export function formatarMoeda(valor: number): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(valor);
}

export function formatarData(data: string | null | undefined): string {
  if (!data) return '';
  try {
    const dateStr = data.includes('T') ? data : data + 'T00:00:00';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) {
      return data;
    }
    return date.toLocaleDateString('pt-BR');
  } catch {
    return data;
  }
}

export function validarCPF(cpf: string): boolean {
  const cpfLimpo = removerFormatacaoCPF(cpf);
  return cpfLimpo.length === 11 && /^\d{11}$/.test(cpfLimpo);
}

