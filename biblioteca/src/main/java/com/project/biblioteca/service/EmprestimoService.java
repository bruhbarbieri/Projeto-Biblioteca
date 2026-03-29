package com.project.biblioteca.service;

import com.project.biblioteca.StatusEmprestimo;
import com.project.biblioteca.entity.Emprestimo;
import com.project.biblioteca.entity.Livro;
import com.project.biblioteca.entity.Usuario;
import com.project.biblioteca.repository.EmprestimoRepository;
import com.project.biblioteca.repository.LivroRepository;
import com.project.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(
            EmprestimoRepository emprestimoRepository,
            UsuarioRepository usuarioRepository,
            LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public Emprestimo criarEmprestimo(Long usuarioId, Long livroId, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        validarDataEmprestimo(dataEmprestimo);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (livroEmprestado(livroId)) {
            throw new RuntimeException("Livro já está emprestado");
        }
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        return emprestimoRepository.save(emprestimo);
    }

    private void validarDataEmprestimo(LocalDate dataEmprestimo) {
        if (dataEmprestimo.isAfter(LocalDate.now())) {
            throw new RuntimeException("Data de empréstimo não pode ser futura");
        }
    }

    private Boolean livroEmprestado(Long livroId) {
        return emprestimoRepository
                .existsByLivroIdAndStatus(livroId, StatusEmprestimo.ATIVO);
    }

    public Emprestimo devolverEmprestimo(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        validarEmprestimo(emprestimo);

        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        return emprestimoRepository.save(emprestimo);
    }

    private void validarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo.getStatus() != StatusEmprestimo.ATIVO) {
            throw new RuntimeException("Empréstimo não está ativo");
        }
    }

    public List<Livro> recomendarLivros(Long usuarioId) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuarioId(usuarioId);

        List<Long> livrosIds = emprestimos.stream()
                .map(e -> e.getLivro().getId())
                .toList();

        List<String> categorias = emprestimos.stream()
                .map(e -> e.getLivro().getCategoria())
                .distinct()
                .toList();

        List<Livro> recomendados = livroRepository.findByCategoriaIn(categorias);
        return recomendados.stream()
                .filter(l -> !livrosIds.contains(l.getId()))
                .toList();
    }
}
