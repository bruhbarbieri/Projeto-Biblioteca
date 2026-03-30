package com.project.biblioteca.controller;

import com.project.biblioteca.dto.EmprestimoRequestDTO;
import com.project.biblioteca.entity.Emprestimo;
import com.project.biblioteca.entity.Livro;
import com.project.biblioteca.service.EmprestimoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@CrossOrigin(origins = "http://localhost:4200")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public Emprestimo criarEmprestimo(@RequestBody EmprestimoRequestDTO request) {

        return emprestimoService.criarEmprestimo(
                request.getUsuarioId(),
                request.getLivroId(),
                request.getDataEmprestimo(),
                request.getDataDevolucao()
        );
    }

    @PutMapping("/{id}/devolver")
    public Emprestimo devolver(@PathVariable Long id) {
        return emprestimoService.devolverEmprestimo(id);
    }

    @GetMapping("/usuarios/{id}/recomendacoes")
    public List<Livro> recomendar(@PathVariable Long id) {
        return emprestimoService.recomendarLivros(id);
    }

    @GetMapping
    public List<Emprestimo> listar() {
        return emprestimoService.listarTodos();
    }
}
