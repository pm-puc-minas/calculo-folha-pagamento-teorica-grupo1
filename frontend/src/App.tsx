import { useState, useEffect } from 'react';
import { apiService } from './services/api';
import { FuncionarioDTO, FolhaPagamentoDTO } from './types';
import FormularioFuncionario from './components/FormularioFuncionario';
import TabelaFuncionarios from './components/TabelaFuncionarios';
import TabelaFolhas from './components/TabelaFolhas';
import DialogFolha from './components/DialogFolha';
import DialogDetalhes from './components/DialogDetalhes';
import Alert from './components/Alert';
import Login from './components/Login';

function App() {
  const [autenticado, setAutenticado] = useState(false);
  const [usuarioLogado, setUsuarioLogado] = useState<string | null>(null);
  const [funcionarios, setFuncionarios] = useState<FuncionarioDTO[]>([]);
  const [folhas, setFolhas] = useState<FolhaPagamentoDTO[]>([]);
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState<{ message: string; type: 'success' | 'error' } | null>(null);
  const [dialogFolhaOpen, setDialogFolhaOpen] = useState(false);
  const [dialogDetalhesOpen, setDialogDetalhesOpen] = useState(false);
  const [folhaDetalhes, setFolhaDetalhes] = useState<FolhaPagamentoDTO | null>(null);
  const [funcionarioEditando, setFuncionarioEditando] = useState<FuncionarioDTO | null>(null);

  useEffect(() => {
    verificarAutenticacao();
  }, []);

  useEffect(() => {
    if (autenticado) {
      carregarDados();
    }
  }, [autenticado]);

  const verificarAutenticacao = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/auth/status');
      const data = await response.json();
      if (data.autenticado) {
        setAutenticado(true);
        setUsuarioLogado(data.usuario);
      }
    } catch (error) {
    }
  };

  const handleLogin = (usuario: string) => {
    setAutenticado(true);
    setUsuarioLogado(usuario);
  };

  const handleLogout = async () => {
    try {
      await fetch('http://localhost:8080/api/auth/logout', {
        method: 'POST',
      });
    } catch (error) {
    }
    setAutenticado(false);
    setUsuarioLogado(null);
  };

  const carregarDados = async () => {
    await Promise.all([
      carregarFuncionarios(true), 
      carregarFolhas(true) 
    ]);
  };

   // pra evitar o erro no carregamento inicial, apenas deixa a lista vazia (não compromete o funcionamento do sistema)
  const carregarFuncionarios = async (silenciarErro = false) => {
    try {
      const data = await apiService.listarFuncionarios();
      setFuncionarios(data);
    } catch (error) {
      const errorMessage = (error as Error).message;
      if (silenciarErro || errorMessage.includes('conexão') || errorMessage.includes('fetch')) {
        setFuncionarios([]);
        return;
      }
      mostrarErro('Erro ao carregar funcionários: ' + errorMessage);
    }
  };

  const carregarFolhas = async (silenciarErro = false) => {
    try {
      const data = await apiService.listarFolhas();
      setFolhas(data);
    } catch (error) {
      const errorMessage = (error as Error).message;
      if (silenciarErro || errorMessage.includes('conexão') || errorMessage.includes('fetch')) {
        setFolhas([]);
        return;
      }
      mostrarErro('Erro ao carregar folhas: ' + errorMessage);
    }
  };

  const mostrarSucesso = (mensagem: string) => {
    setAlert({ message: mensagem, type: 'success' });
    setTimeout(() => setAlert(null), 5000);
  };

  const mostrarErro = (mensagem: string) => {
    setAlert({ message: mensagem, type: 'error' });
    setTimeout(() => setAlert(null), 5000);
  };

  const salvarFuncionario = async (funcionario: FuncionarioDTO) => {
    try {
      setLoading(true);
      if (funcionarioEditando?.id) {
        await apiService.atualizarFuncionario(funcionarioEditando.id, funcionario);
        mostrarSucesso('Funcionário atualizado com sucesso!');
      } else {
        await apiService.cadastrarFuncionario(funcionario);
        mostrarSucesso('Funcionário cadastrado com sucesso!');
      }
      setFuncionarioEditando(null);
      await carregarFuncionarios();
    } catch (error) {
      mostrarErro('Erro ao salvar funcionário: ' + (error as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const editarFuncionario = async (id: number) => {
    try {
      setLoading(true);
      const funcionario = await apiService.buscarFuncionario(id);
      setFuncionarioEditando(funcionario);
      document.getElementById('form-funcionario')?.scrollIntoView({ behavior: 'smooth' });
    } catch (error) {
      mostrarErro('Erro ao buscar funcionário: ' + (error as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const deletarFuncionario = async (id: number) => {
    if (!confirm('Tem certeza que deseja deletar este funcionário?')) return;
    try {
      setLoading(true);
      await apiService.deletarFuncionario(id);
      mostrarSucesso('Funcionário deletado com sucesso!');
      await carregarDados();
    } catch (error) {
      mostrarErro('Erro ao deletar funcionário: ' + (error as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const gerarFolha = async (funcionarioId: number) => {
    try {
      setLoading(true);
      await apiService.gerarFolhaPagamento(funcionarioId);
      mostrarSucesso('Folha de pagamento gerada com sucesso!');
      await carregarFolhas(false);
      setDialogFolhaOpen(false);
    } catch (error) {
      mostrarErro('Erro ao gerar folha: ' + (error as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const verDetalhesFolha = async (id: number) => {
    try {
      setLoading(true);
      const folha = await apiService.buscarFolha(id);
      setFolhaDetalhes(folha);
      setDialogDetalhesOpen(true);
    } catch (error) {
      mostrarErro('Erro ao buscar folha: ' + (error as Error).message);
    } finally {
      setLoading(false);
    }
  };

  const verFolhasFuncionario = async (funcionarioId: number) => {
    try {
      setLoading(true);
      const folhasFunc = await apiService.listarFolhasPorFuncionario(funcionarioId);
      setFolhas(folhasFunc);
      if (folhasFunc.length === 0) {
        mostrarErro('Nenhuma folha encontrada para este funcionário');
      }
    } catch (error) {
      mostrarErro('Erro ao buscar folhas: ' + (error as Error).message);
        setFolhas([]);
    } finally {
      setLoading(false);
    }
  };

  const recalcularFolha = async (id: number) => {
    try {
      setLoading(true);
      const folhaRecalculada = await apiService.calcularFolha(id);
      await carregarFolhas(false);
      setFolhaDetalhes(folhaRecalculada);
      mostrarSucesso('Folha recalculada com sucesso!');
    } catch (error) {
      mostrarErro('Erro ao recalcular folha: ' + (error as Error).message);
    } finally {
      setLoading(false);
    }
  };

  if (!autenticado) {
    return <Login onLogin={handleLogin} />;
  }

  return (
    <div className={`container ${loading ? 'loading' : ''}`}>
      <header style={{ textAlign: 'left' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '16px' }}>
          <h1 style={{ margin: 0 }}>Sistema de Gestão de Folha de Pagamento</h1>
          <div style={{ display: 'flex', alignItems: 'center', gap: '16px' }}>
            <span style={{ color: '#64748b', fontSize: '0.875rem' }}>
              Usuário: <strong>{usuarioLogado}</strong>
            </span>
            <button 
              className="btn btn-secondary" 
              onClick={handleLogout}
              style={{ fontSize: '0.875rem', padding: '8px 16px' }}
            >
              Sair
            </button>
          </div>
        </div>
      </header>

      {alert && <Alert message={alert.message} type={alert.type} onClose={() => setAlert(null)} />}

      <section className="section">
        <h2>Cadastro de Funcionário</h2>
        <FormularioFuncionario
          funcionario={funcionarioEditando}
          onSubmit={salvarFuncionario}
          onCancel={() => setFuncionarioEditando(null)}
          loading={loading}
        />
      </section>

      <section className="section">
        <div className="section-header">
          <h2>Funcionários</h2>
          <button className="btn btn-primary" onClick={() => setDialogFolhaOpen(true)}>
            Gerar Nova Folha
          </button>
        </div>
        <TabelaFuncionarios
          funcionarios={funcionarios}
          onEdit={editarFuncionario}
          onDelete={deletarFuncionario}
          onGerarFolha={gerarFolha}
          onVerFolhas={verFolhasFuncionario}
        />
      </section>

      <section className="section">
        <h2>Folhas de Pagamento</h2>
        <TabelaFolhas folhas={folhas} onVerDetalhes={verDetalhesFolha} />
      </section>

      <DialogFolha
        open={dialogFolhaOpen}
        funcionarios={funcionarios}
        onClose={() => setDialogFolhaOpen(false)}
        onGerar={gerarFolha}
        loading={loading}
      />

      <DialogDetalhes
        open={dialogDetalhesOpen}
        folha={folhaDetalhes}
        onClose={() => {
          setDialogDetalhesOpen(false);
          setFolhaDetalhes(null);
        }}
        onRecalcular={recalcularFolha}
      />
    </div>
  );
}

export default App;

