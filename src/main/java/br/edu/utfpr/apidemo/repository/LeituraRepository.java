package br.edu.utfpr.apidemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apidemo.model.Leitura;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {
    List<Leitura> findBySensorIdSensor(long idSensor);
}
