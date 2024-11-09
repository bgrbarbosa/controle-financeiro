package br.com.bgrbarbosa.controle_financeiro.model.dto;


import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDTO {

    private Long id_despesa;

    @Size(min = 3, max = 30, message = "A descrição deve ter entre 3 a 30 caracteres")
    @NotBlank(message = "A descrição não pode ser nula ou vazia")
    private String desc_despesa;

    public Despesa toEntity(DespesaDTO dto){
        Despesa despesa = new Despesa();
        despesa.setDesc_despesa(dto.getDesc_despesa());
        return despesa;
    }

    public DespesaDTO toDTO(Despesa despesa){
        DespesaDTO dto = new DespesaDTO();
        dto.setId_despesa(despesa.getId_despesa());
        dto.setDesc_despesa(despesa.getDesc_despesa());
        return dto;
    }
}
