import { useState, useEffect } from 'react';
import { FuncionarioDTO } from '../types';
import { formatarCPF } from '../utils/masks';

interface DialogFolhaProps {
  open: boolean;
  funcionarios: FuncionarioDTO[];
  onClose: () => void;
  onGerar: (funcionarioId: number) => void;
  loading: boolean;
}

function DialogFolha({ open, funcionarios, onClose, onGerar, loading }: DialogFolhaProps) {
  const [funcionarioId, setFuncionarioId] = useState<string>('');

  useEffect(() => {
    if (open) {
      setFuncionarioId('');
    }
  }, [open]);

  const handleGerar = () => {
    const id = parseInt(funcionarioId);
    if (!id) {
      alert('Selecione um funcionário');
      return;
    }
    onGerar(id);
  };

  if (!open) return null;

  return (
    <dialog className="dialog" open={open} onClose={onClose}>
      <div className="dialog-content">
        <h2>Gerar Nova Folha de Pagamento</h2>
        <div className="form-group">
          <label htmlFor="select-funcionario-folha">Funcionário:</label>
          <select
            id="select-funcionario-folha"
            className="select"
            value={funcionarioId}
            onChange={(e: React.ChangeEvent<HTMLSelectElement>) => setFuncionarioId(e.target.value)}
          >
            <option value="">Selecione um funcionário</option>
            {funcionarios.map((func) => (
              <option key={func.id} value={func.id}>
                {func.nome} - {formatarCPF(func.cpf)}
              </option>
            ))}
          </select>
        </div>
        <div className="dialog-actions">
          <button className="btn btn-primary" onClick={handleGerar} disabled={loading}>
            {loading ? 'Gerando...' : 'Gerar'}
          </button>
          <button className="btn btn-secondary" onClick={onClose}>
            Cancelar
          </button>
        </div>
      </div>
    </dialog>
  );
}

export default DialogFolha;

