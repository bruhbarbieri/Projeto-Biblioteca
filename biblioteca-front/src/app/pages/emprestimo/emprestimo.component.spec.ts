import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmprestimoComponent } from './emprestimo.component';

describe('Emprestimo', () => {
  let component: EmprestimoComponent;
  let fixture: ComponentFixture<EmprestimoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmprestimoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EmprestimoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
