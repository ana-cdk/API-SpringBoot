package br.edu.utfpr.apidemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apidemo.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long>{
    List<Sensor> findByDispositivoIdDispositivo(long idDispositivo);
}
