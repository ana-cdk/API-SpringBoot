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
@Table(name = "tb_sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long idSensor;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    @JsonBackReference
    private Dispositivo dispositivo;

    //@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Leitura> leituras;
}
