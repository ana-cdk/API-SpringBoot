package br.edu.utfpr.apidemo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LeituraDTO(
    @NotBlank String valor,
    @NotNull @Min(1) long idSensor) {


}
