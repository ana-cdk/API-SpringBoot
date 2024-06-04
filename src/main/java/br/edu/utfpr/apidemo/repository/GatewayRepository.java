package br.edu.utfpr.apidemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apidemo.model.Gateway;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {
    List<Gateway> findByPessoaIdPessoa(long pessoaId);
}

