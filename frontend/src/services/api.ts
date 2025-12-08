import { FuncionarioDTO, FolhaPagamentoDTO } from '../types/index.js';

const API_BASE_URL = 'http://localhost:8080/api';

class ApiService {
  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${API_BASE_URL}${endpoint}`;
    const config: RequestInit = {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
    };

    try {
      const response = await fetch(url, config);

      if (!response.ok) {
        let errorMessage = 'Erro na requisição';
        
        if (response.status === 404) {
          errorMessage = 'Recurso não encontrado';
        } else if (response.status === 400) {
          try {
            const errorData = await response.json();
            errorMessage = errorData.erro || 'Dados inválidos. Verifique os campos preenchidos.';
          } catch {
            errorMessage = 'Dados inválidos. Verifique os campos preenchidos.';
          }
        } else if (response.status === 500) {
          try {
            const errorData = await response.json();
            errorMessage = errorData.erro || 'Erro interno do servidor';
          } catch {
            errorMessage = 'Erro interno do servidor';
          }
        } else {
          errorMessage = `Erro ${response.status}: ${response.statusText}`;
        }
        
        throw new Error(errorMessage);
      }

      if (response.status === 204) {
        return {} as T;
      }

      return response.json();
    } catch (error) {
      if (error instanceof TypeError && error.message.includes('fetch')) {
        throw new Error('Erro de conexão. Verifique se o backend está rodando.');
      }
      throw error;
    }
  }

  async listarFuncionarios(): Promise<FuncionarioDTO[]> {
    return this.request<FuncionarioDTO[]>('/funcionarios');
  }

  async buscarFuncionario(id: number): Promise<FuncionarioDTO> {
    return this.request<FuncionarioDTO>(`/funcionarios/${id}`);
  }

  async cadastrarFuncionario(
    funcionario: FuncionarioDTO
  ): Promise<FuncionarioDTO> {
    return this.request<FuncionarioDTO>('/funcionarios', {
      method: 'POST',
      body: JSON.stringify(funcionario),
    });
  }

  async atualizarFuncionario(
    id: number,
    funcionario: FuncionarioDTO
  ): Promise<FuncionarioDTO> {
    return this.request<FuncionarioDTO>(`/funcionarios/${id}`, {
      method: 'PUT',
      body: JSON.stringify(funcionario),
    });
  }

  async deletarFuncionario(id: number): Promise<void> {
    await this.request<void>(`/funcionarios/${id}`, {
      method: 'DELETE',
    });
  }

  async gerarFolhaPagamento(
    funcionarioId: number
  ): Promise<FolhaPagamentoDTO> {
    return this.request<FolhaPagamentoDTO>(
      `/folhas/gerar/${funcionarioId}`,
      {
        method: 'POST',
      }
    );
  }

  async listarFolhas(): Promise<FolhaPagamentoDTO[]> {
    return this.request<FolhaPagamentoDTO[]>('/folhas');
  }

  async listarFolhasPorFuncionario(
    funcionarioId: number
  ): Promise<FolhaPagamentoDTO[]> {
    return this.request<FolhaPagamentoDTO[]>(
      `/folhas/funcionario/${funcionarioId}`
    );
  }

  async buscarFolha(id: number): Promise<FolhaPagamentoDTO> {
    return this.request<FolhaPagamentoDTO>(`/folhas/${id}`);
  }

  async calcularFolha(id: number): Promise<FolhaPagamentoDTO> {
    return this.request<FolhaPagamentoDTO>(`/folhas/${id}/calcular`, {
      method: 'PUT',
    });
  }
}

export const apiService = new ApiService();

