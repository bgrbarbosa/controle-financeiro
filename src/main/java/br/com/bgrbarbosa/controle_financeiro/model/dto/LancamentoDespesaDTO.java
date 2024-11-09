package br.com.bgrbarbosa.controle_financeiro.model.dto;


import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import br.com.bgrbarbosa.controle_financeiro.model.LancamentoDespesa;
import br.com.bgrbarbosa.controle_financeiro.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoDespesaDTO {

    private Long id_lancamento;
    private String desc_lancamento;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dt_venc;
    private Status status;
    private Double vl_lanc;
    private Despesa despesa;

    public LancamentoDespesa toEntity(LancamentoDespesaDTO dto){
        LancamentoDespesa lancamento = new LancamentoDespesa();
        lancamento.setId_lancamento(dto.id_lancamento);
        lancamento.setDesc_lancamento(dto.getDesc_lancamento());
        lancamento.setStatus(dto.getStatus());
        lancamento.setDespesa(dto.getDespesa());
        lancamento.setDt_venc(dto.getDt_venc());
        lancamento.setVl_lanc(dto.getVl_lanc());
        return lancamento;
    }

    public LancamentoDespesaDTO toDTO(LancamentoDespesa lancamento){
        LancamentoDespesaDTO dto = new LancamentoDespesaDTO();
        dto.setId_lancamento(lancamento.getId_lancamento());
        dto.setDesc_lancamento(lancamento.getDesc_lancamento());
        dto.setStatus(lancamento.getStatus());
        dto.setDespesa(lancamento.getDespesa());
        dto.setDt_venc(lancamento.getDt_venc());
        dto.setVl_lanc(lancamento.getVl_lanc());
        return dto;
    }
}
