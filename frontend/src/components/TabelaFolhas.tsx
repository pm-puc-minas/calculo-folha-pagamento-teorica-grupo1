import { FolhaPagamentoDTO } from '../types';
import { formatarMoeda, formatarData } from '../utils/masks';

interface TabelaFolhasProps {
  folhas: FolhaPagamentoDTO[];
  onVerDetalhes: (id: number) => void;
}

function TabelaFolhas({ folhas, onVerDetalhes }: TabelaFolhasProps) {
  if (folhas.length === 0) {
    return (
      <table className="table">
        <thead>
          <tr>
            <th>Funcionário</th>
            <th>Data de Referência</th>
            <th>Salário Bruto</th>
            <th>Salário Líquido</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td colSpan={5} style={{ textAlign: 'center' }}>
              Nenhuma folha de pagamento gerada
            </td>
          </tr>
        </tbody>
      </table>
    );
  }

  return (
    <table className="table">
      <thead>
        <tr>
          <th>Funcionário</th>
          <th>Data de Referência</th>
          <th>Salário Bruto</th>
          <th>Salário Líquido</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        {folhas.map((folha) => (
          <tr key={folha.id}>
            <td>{folha.funcionario?.nome || 'N/A'}</td>
            <td>{formatarData(folha.dataReferencia)}</td>
            <td>{formatarMoeda(folha.salarioBruto || 0)}</td>
            <td>{formatarMoeda(folha.salarioLiquido || 0)}</td>
            <td className="actions-cell">
              <button className="btn-action btn-details" onClick={() => folha.id && onVerDetalhes(folha.id)} title="Detalhes">
                Detalhes
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default TabelaFolhas;

