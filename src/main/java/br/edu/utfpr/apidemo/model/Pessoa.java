package br.edu.utfpr.apidemo.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "tb_pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long idPessoa;

    @Column(nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String senha;

    //@OneToMany(mappedBy = "pessoa")
    //private List<Gateway> gateways;

    public Long getIdPessoa() {
        return idPessoa;
    }

}


