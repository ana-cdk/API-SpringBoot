package br.edu.utfpr.apidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apidemo.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
}
