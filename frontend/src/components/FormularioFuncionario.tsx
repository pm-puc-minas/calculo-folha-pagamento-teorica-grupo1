import { useState, useEffect } from 'react';
import { FuncionarioDTO } from '../types';
import { formatarCPF, removerFormatacaoCPF, validarCPF } from '../utils/masks';

interface FormularioFuncionarioProps {
  funcionario: FuncionarioDTO | null;
  onSubmit: (funcionario: FuncionarioDTO) => Promise<void>;
  onCancel: () => void;
  loading: boolean;
}

function FormularioFuncionario({ funcionario, onSubmit, onCancel, loading }: FormularioFuncionarioProps) {
  const [formData, setFormData] = useState<FuncionarioDTO>({
    nome: '',
    cpf: '',
    cargo: '',
    salarioBruto: 0,
    dataAdmissao: '',
    numeroDependentes: 0,
    temPericulosidade: false,
    temInsalubridade: false,
    grauInsalubridade: 'NENHUM',
    valorValeTransporte: 0,
    valorValeAlimentacao: 0,
    jornadaTrabalho: {
      horasMensais: 200,
      horasDiarias: 8,
      diasTrabalhados: 22,
    },
  });

  useEffect(() => {
    if (funcionario) {
      setFormData({
        ...funcionario,
        cpf: formatarCPF(funcionario.cpf),
      });
    } else {
      setFormData({
        nome: '',
        cpf: '',
        cargo: '',
        salarioBruto: 0,
        dataAdmissao: '',
        numeroDependentes: 0,
        temPericulosidade: false,
        temInsalubridade: false,
        grauInsalubridade: 'NENHUM',
        valorValeTransporte: 0,
        valorValeAlimentacao: 0,
        jornadaTrabalho: {
          horasMensais: 200,
          horasDiarias: 8,
          diasTrabalhados: 22,
        },
      });
    }
  }, [funcionario]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    const checked = (e.target as HTMLInputElement).checked;

    if (name.startsWith('jornada.')) {
      const field = name.split('.')[1];
      setFormData(prev => {
        const novaJornada = {
          ...prev.jornadaTrabalho,
          [field]: type === 'number' ? parseFloat(value) || 0 : parseInt(value) || 0,
        };
        
        if (field === 'horasDiarias' || field === 'diasTrabalhados') {
          novaJornada.horasMensais = novaJornada.horasDiarias * novaJornada.diasTrabalhados;
        }
        
        return {
          ...prev,
          jornadaTrabalho: novaJornada,
        };
      });
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: type === 'checkbox' ? checked : (type === 'number' ? parseFloat(value) || 0 : value),
      }));
    }
  };

  const handleCPFChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const formatted = formatarCPF(e.target.value);
    setFormData(prev => ({ ...prev, cpf: formatted }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    const dados: FuncionarioDTO = {
      ...formData,
      cpf: removerFormatacaoCPF(formData.cpf),
    };

    if (!validarCPF(dados.cpf)) {
      alert('CPF inválido');
      return;
    }

    if (!dados.nome || dados.nome.length < 3) {
      alert('Nome deve ter pelo menos 3 caracteres');
      return;
    }

    if (!dados.cargo || dados.cargo.length < 2) {
      alert('Cargo deve ter pelo menos 2 caracteres');
      return;
    }

    if (dados.salarioBruto <= 0) {
      alert('Salário bruto deve ser maior que zero');
      return;
    }

    await onSubmit(dados);
  };

  return (
    <form id="form-funcionario" className="form" onSubmit={handleSubmit}>
      <div className="form-section">
        <h3>Dados Pessoais</h3>
        <div className="form-section-grid">
          <div className="form-group">
            <label htmlFor="nome">Nome Completo *</label>
            <input
              type="text"
              id="nome"
              name="nome"
              value={formData.nome}
              onChange={handleChange}
              placeholder="Digite o nome completo"
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="cpf">CPF *</label>
            <input
              type="text"
              id="cpf"
              name="cpf"
              value={formData.cpf}
              onChange={handleCPFChange}
              placeholder="000.000.000-00"
              maxLength={14}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="cargo">Cargo *</label>
            <input
              type="text"
              id="cargo"
              name="cargo"
              value={formData.cargo}
              onChange={handleChange}
              placeholder="Digite o cargo"
              required
            />
          </div>
        </div>
      </div>

      <div className="form-section">
        <h3>Dados Trabalhistas</h3>
        <div className="form-section-grid">
          <div className="form-group">
            <label htmlFor="salario">Salário Bruto (R$) *</label>
            <input
              type="number"
              id="salario"
              name="salarioBruto"
              value={formData.salarioBruto || ''}
              onChange={handleChange}
              step="0.01"
              min="0"
              placeholder="0.00"
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="data-admissao">Data de Admissão *</label>
            <input
              type="date"
              id="data-admissao"
              name="dataAdmissao"
              value={formData.dataAdmissao}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="dependentes">Número de Dependentes</label>
            <input
              type="number"
              id="dependentes"
              name="numeroDependentes"
              value={formData.numeroDependentes}
              onChange={handleChange}
              min="0"
              required
            />
          </div>
        </div>
      </div>

      <div className="form-section">
        <h3>Adicionais</h3>
        <div className="form-section-grid">
          <div className="form-group checkbox-group">
            <label>
              <input
                type="checkbox"
                id="periculosidade"
                name="temPericulosidade"
                checked={formData.temPericulosidade}
                onChange={handleChange}
              />
              <span>Adicional de Periculosidade (30%)</span>
            </label>
          </div>
          <div className="form-group checkbox-group">
            <label>
              <input
                type="checkbox"
                id="insalubridade"
                name="temInsalubridade"
                checked={formData.temInsalubridade}
                onChange={handleChange}
              />
              <span>Adicional de Insalubridade</span>
            </label>
          </div>
          <div className="form-group">
            <label htmlFor="grau-insalubridade">Grau de Insalubridade</label>
            <select
              id="grau-insalubridade"
              name="grauInsalubridade"
              value={formData.grauInsalubridade}
              onChange={handleChange}
            >
              <option value="NENHUM">Nenhum</option>
              <option value="BAIXO">Baixo (10%)</option>
              <option value="MEDIO">Médio (20%)</option>
              <option value="ALTO">Alto (40%)</option>
            </select>
          </div>
        </div>
      </div>

      <div className="form-section">
        <h3>Benefícios</h3>
        <div className="form-section-grid">
          <div className="form-group">
            <label htmlFor="vale-transporte">Vale Transporte (R$)</label>
            <input
              type="number"
              id="vale-transporte"
              name="valorValeTransporte"
              value={formData.valorValeTransporte || ''}
              onChange={handleChange}
              step="0.01"
              min="0"
              placeholder="0.00"
              required
            />
            <small>Desconto máximo: 6% do salário bruto</small>
          </div>
          <div className="form-group">
            <label htmlFor="vale-alimentacao">Vale Alimentação - Valor Diário (R$)</label>
            <input
              type="number"
              id="vale-alimentacao"
              name="valorValeAlimentacao"
              value={formData.valorValeAlimentacao || ''}
              onChange={handleChange}
              step="0.01"
              min="0"
              placeholder="0.00"
              required
            />
            <small>Calculado sobre 26 dias úteis (sem desconto na folha)</small>
          </div>
        </div>
      </div>

      <div className="form-section">
        <h3>Jornada de Trabalho</h3>
        <div className="form-section-grid">
          <div className="form-group">
            <label htmlFor="horas-mensais">Horas Mensais *</label>
            <input
              type="number"
              id="horas-mensais"
              name="jornada.horasMensais"
              value={formData.jornadaTrabalho.horasMensais}
              onChange={handleChange}
              min="1"
              required
              readOnly
            />
            <small>Calculado automaticamente: {formData.jornadaTrabalho.horasDiarias} horas × {formData.jornadaTrabalho.diasTrabalhados} dias = {formData.jornadaTrabalho.horasMensais} horas</small>
          </div>
          <div className="form-group">
            <label htmlFor="horas-diarias">Horas Diárias *</label>
            <input
              type="number"
              id="horas-diarias"
              name="jornada.horasDiarias"
              value={formData.jornadaTrabalho.horasDiarias}
              onChange={handleChange}
              min="1"
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="dias-trabalhados">Dias Trabalhados *</label>
            <input
              type="number"
              id="dias-trabalhados"
              name="jornada.diasTrabalhados"
              value={formData.jornadaTrabalho.diasTrabalhados}
              onChange={handleChange}
              min="1"
              required
            />
          </div>
        </div>
      </div>

      <div className="form-actions">
        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? 'Salvando...' : 'Salvar Funcionário'}
        </button>
        <button type="button" className="btn btn-secondary" onClick={onCancel}>
          Cancelar
        </button>
      </div>
    </form>
  );
}

export default FormularioFuncionario;


