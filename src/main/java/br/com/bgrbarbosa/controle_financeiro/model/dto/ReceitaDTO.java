package br.com.bgrbarbosa.controle_financeiro.model.dto;

import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import br.com.bgrbarbosa.controle_financeiro.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDTO {
    private Long id_receita;

    @Size(min = 3, max = 30, message = "A descrição deve ter entre 3 a 30 caracteres")
    @NotBlank(message = "A descrição não pode ser nula ou vazia")
    private String desc_receita;

    public Receita toEntity(ReceitaDTO dto){
        Receita receita = new Receita();
        receita.setDesc_receita(dto.getDesc_receita());
        return receita;
    }

    public ReceitaDTO toDTO(Receita receita){
        ReceitaDTO dto = new ReceitaDTO();
        dto.setId_receita(receita.getId_receita());
        dto.setDesc_receita(receita.getDesc_receita());
        return dto;
    }
}
