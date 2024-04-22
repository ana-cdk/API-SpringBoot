package br.edu.utfpr.apidemo.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record PessoaDTO(
    @NotBlank @Length(min = 2) String nome, 
    String email, 
    @NotBlank String senha) {
        
}
