package br.edu.utfpr.apidemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apidemo.model.Atuador;

public interface AtuadorRepository extends JpaRepository<Atuador, Long> {
    List<Atuador> findByDispositivoIdDispositivo(long idDispositivo);

}
