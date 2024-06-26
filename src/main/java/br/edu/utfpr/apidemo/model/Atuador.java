package br.edu.utfpr.apidemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_atuador")
public class Atuador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long idAtuador;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    @JsonBackReference
    private Dispositivo dispositivo;

}
