package br.edu.utfpr.apidemo.model;

import java.time.LocalDateTime;

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
@Table(name = "tb_leitura")
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long idLeitura;

    @Column(nullable = false)
    private String valor;

    @Column(nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @JsonBackReference
    private Sensor sensor;

}

