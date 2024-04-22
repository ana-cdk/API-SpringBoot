package br.edu.utfpr.apidemo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_dispositivo")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long idDispositivo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String localizacao;

    @Column(nullable = false)
    private String endereco;

    @ManyToOne
    @JoinColumn(name = "gateway_id")
    private Gateway gateway;

    //@OneToMany(mappedBy = "dispositivo")
    //private List<Sensor> sensores;

   //@OneToMany(mappedBy = "dispositivo")
    //private List<Atuador> atuadores;


}
