import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EmprestimoService } from '../../services/emprestimo.service';
import { EmprestimoRequest } from '../../models/emprestimo-request';
import { Emprestimo } from '../../models/emprestimo';
import { HttpClientModule } from '@angular/common/http';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-emprestimo',
  templateUrl: './emprestimo.component.html',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
})
export class EmprestimoComponent implements OnInit {

  emprestimos: Emprestimo[] = [];

  novo: EmprestimoRequest = {
    usuarioId: 0,
    livroId: 0,
    dataEmprestimo: '',
    dataDevolucao: ''
  };

  constructor(
    private service: EmprestimoService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.carregar();
  }

  async carregar() {
    this.emprestimos = await this.service.listar();
  }

  async criar() {
    await this.service.criar(this.novo);
    this.carregar();
  }

  async devolver(id: number) {
    await this.service.devolver(id);
  
    this.emprestimos = this.emprestimos.map(e =>
      e.id === id ? { ...e, status: 'DEVOLVIDO' } : e
    );
    this.cd.detectChanges();
  }
}
