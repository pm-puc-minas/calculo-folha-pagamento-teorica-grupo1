export interface JornadaTrabalhoDTO {
  horasMensais: number;
  horasDiarias: number;
  diasTrabalhados: number;
}

export interface FuncionarioDTO {
  id?: number;
  nome: string;
  cpf: string;
  cargo: string;
  salarioBruto: number;
  dataAdmissao: string;
  numeroDependentes: number;
  temPericulosidade: boolean;
  temInsalubridade: boolean;
  grauInsalubridade: string;
  valorValeTransporte: number;
  valorValeAlimentacao: number;
  jornadaTrabalho: JornadaTrabalhoDTO;
}

export interface Provento {
  descricao: string;
  valor: number;
}

export interface Desconto {
  descricao: string;
  valor: number;
}

export interface ResumoFolha {
  salarioBruto: number;
  totalProventos: number;
  totalDescontos: number;
  salarioLiquido: number;
  salarioHora: number;
}

export interface FolhaPagamentoDTO {
  id?: number;
  funcionario: FuncionarioDTO;
  dataReferencia: string;
  salarioBruto: number;
  salarioLiquido: number;
  totalAdicionais: number;
  totalDescontos: number;
  detalhamentoCalculos: Record<string, number>;
}

export interface DadosFuncionario {
  nome: string;
  cpf: string;
  cargo: string;
  dataAdmissao: string;
}

