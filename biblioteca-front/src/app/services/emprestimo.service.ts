import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Emprestimo } from '../models/emprestimo';
import { EmprestimoRequest } from '../models/emprestimo-request';

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {

  private api = 'http://localhost:8080/emprestimos';

  constructor(private http: HttpClient) {}

  async listar(): Promise<Emprestimo[]> {
    return await firstValueFrom(this.http.get<Emprestimo[]>(this.api));
  }

  async criar(data: EmprestimoRequest) {
    return await firstValueFrom(this.http.post(this.api, data));
  }

  async devolver(id: number) {
    return await firstValueFrom(this.http.put(`${this.api}/${id}/devolver`, {}));
  }
}