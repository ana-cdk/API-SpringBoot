package br.edu.utfpr.apidemo.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GatewayDTO(
    @NotBlank @Length(min = 2) String nome,
    @NotBlank @Length(min = 5) String descricao,
    String endereco,
    @NotNull @Min(1) long idPessoa) {
 
}
