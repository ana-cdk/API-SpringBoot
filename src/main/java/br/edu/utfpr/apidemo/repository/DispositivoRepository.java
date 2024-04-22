package br.edu.utfpr.apidemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.apidemo.model.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    
}

    

