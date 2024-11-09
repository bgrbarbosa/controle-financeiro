package br.com.bgrbarbosa.controle_financeiro.model.dto;


import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraficoDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dt_init;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dt_final;
/*
    private Double total_despesa;

    private Double total_receita;*/
}
