import { FolhaPagamentoDTO } from '../types';
import { processarFolhaPagamento } from '../utils/calculos';
import { formatarMoeda, formatarData, formatarCPF } from '../utils/masks';

interface DialogDetalhesProps {
  open: boolean;
  folha: FolhaPagamentoDTO | null;
  onClose: () => void;
  onRecalcular: (id: number) => void;
}

function DialogDetalhes({ open, folha, onClose, onRecalcular }: DialogDetalhesProps) {
  if (!open || !folha) return null;

  const { proventos, descontos, resumo } = processarFolhaPagamento(
    folha.funcionario,
    folha.detalhamentoCalculos
  );

  return (
    <dialog className="dialog" open={open} onClose={onClose}>
      <div className="dialog-content">
        <div className="folha-container">
          <div className="folha-header">
            <h2>Folha de Pagamento</h2>
            <p className="folha-data">{formatarData(folha.dataReferencia)}</p>
          </div>

          <div className="folha-section">
            <h3>Dados do Funcionário</h3>
            <div className="dados-grid">
              <div><strong>Nome:</strong> {folha.funcionario.nome}</div>
              <div><strong>CPF:</strong> {formatarCPF(folha.funcionario.cpf)}</div>
              <div><strong>Cargo:</strong> {folha.funcionario.cargo}</div>
              <div><strong>Salário por Hora:</strong> {formatarMoeda(resumo.salarioHora)}</div>
            </div>
          </div>

          <div className="folha-section">
            <h3>Proventos</h3>
            <table className="folha-table">
              <thead>
                <tr>
                  <th>Descrição</th>
                  <th>Valor</th>
                </tr>
              </thead>
              <tbody>
                {proventos.map((provento, index) => (
                  <tr key={index}>
                    <td>{provento.descricao}</td>
                    <td className="valor-positivo">{formatarMoeda(provento.valor)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="folha-section">
            <h3>Descontos</h3>
            <table className="folha-table">
              <thead>
                <tr>
                  <th>Descrição</th>
                  <th>Valor</th>
                </tr>
              </thead>
              <tbody>
                {descontos.map((desconto, index) => (
                  <tr key={index}>
                    <td>{desconto.descricao}</td>
                    <td className="valor-negativo">{formatarMoeda(desconto.valor)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="folha-resumo">
            <div className="resumo-item">
              <span>Salário Bruto:</span>
              <span>{formatarMoeda(resumo.salarioBruto)}</span>
            </div>
            <div className="resumo-item">
              <span>Total Proventos:</span>
              <span className="valor-positivo">{formatarMoeda(resumo.totalProventos)}</span>
            </div>
            <div className="resumo-item">
              <span>Total Descontos:</span>
              <span className="valor-negativo">{formatarMoeda(resumo.totalDescontos)}</span>
            </div>
            <div className="resumo-item resumo-destaque">
              <span>Salário Líquido:</span>
              <span>{formatarMoeda(resumo.salarioLiquido)}</span>
            </div>
          </div>

          <div className="folha-actions">
            {folha.id && (
              <button className="btn btn-primary" onClick={() => folha.id && onRecalcular(folha.id)}>
                Recalcular Folha
              </button>
            )}
          </div>
        </div>
        <div className="dialog-actions">
          <button className="btn btn-secondary" onClick={onClose}>
            Fechar
          </button>
        </div>
      </div>
    </dialog>
  );
}

export default DialogDetalhes;

