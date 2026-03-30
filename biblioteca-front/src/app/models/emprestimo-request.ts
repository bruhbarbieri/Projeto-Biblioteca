export interface EmprestimoRequest {
  usuarioId: number;
  livroId: number;
  dataEmprestimo: string;
  dataDevolucao: string;
}