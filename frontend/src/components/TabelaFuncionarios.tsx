import { FuncionarioDTO } from '../types';
import { formatarCPF, formatarMoeda } from '../utils/masks';

interface TabelaFuncionariosProps {
  funcionarios: FuncionarioDTO[];
  onEdit: (id: number) => void;
  onDelete: (id: number) => void;
  onGerarFolha: (id: number) => void;
  onVerFolhas: (id: number) => void;
}

function TabelaFuncionarios({ funcionarios, onEdit, onDelete, onGerarFolha, onVerFolhas }: TabelaFuncionariosProps) {
  if (funcionarios.length === 0) {
    return (
      <table className="table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>CPF</th>
            <th>Cargo</th>
            <th>Salário Bruto</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td colSpan={5} style={{ textAlign: 'center' }}>
              Nenhum funcionário cadastrado
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
          <th>Nome</th>
          <th>CPF</th>
          <th>Cargo</th>
          <th>Salário Bruto</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        {funcionarios.map((func) => (
          <tr key={func.id}>
            <td>{func.nome}</td>
            <td>{formatarCPF(func.cpf)}</td>
            <td>{func.cargo}</td>
            <td>{formatarMoeda(func.salarioBruto)}</td>
            <td className="actions-cell">
              <button className="btn-action btn-edit" onClick={() => func.id && onEdit(func.id)} title="Editar">
                Editar
              </button>
              <button className="btn-action btn-delete" onClick={() => func.id && onDelete(func.id)} title="Deletar">
                Deletar
              </button>
              <button className="btn-action btn-folha" onClick={() => func.id && onGerarFolha(func.id)} title="Gerar Folha">
                Gerar Folha
              </button>
              <button className="btn-action btn-folhas-func" onClick={() => func.id && onVerFolhas(func.id)} title="Ver Folhas">
                Ver Folhas
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default TabelaFuncionarios;


