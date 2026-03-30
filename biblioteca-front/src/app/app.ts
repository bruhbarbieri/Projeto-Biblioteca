import { Component } from '@angular/core';
import { EmprestimoComponent } from './pages/emprestimo/emprestimo.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [EmprestimoComponent],
  templateUrl: './app.html'
})
export class App {}
