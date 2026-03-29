package com.project.biblioteca.repository;

import com.project.biblioteca.StatusEmprestimo;
import com.project.biblioteca.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    boolean existsByLivroIdAndStatus(Long livroId, StatusEmprestimo status);

    List<Emprestimo> findByUsuarioId(Long usuarioId);
}
