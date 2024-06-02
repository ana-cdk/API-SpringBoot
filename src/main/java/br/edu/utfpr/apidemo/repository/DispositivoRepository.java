package br.edu.utfpr.apidemo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.utfpr.apidemo.model.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    
    @Query("SELECT d FROM Dispositivo d WHERE d.gateway.pessoa.idPessoa = :userId")
    List<Dispositivo> findByGatewayPessoaId(@Param("userId") Long userId);

}

    

