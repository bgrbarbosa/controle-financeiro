package br.com.bgrbarbosa.controle_financeiro.model.dto;



import br.com.bgrbarbosa.controle_financeiro.model.LancamentoReceita;
import br.com.bgrbarbosa.controle_financeiro.model.Receita;
import br.com.bgrbarbosa.controle_financeiro.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoReceitaDTO {

    private Long id_lancamento;
    private String desc_lancamento;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dt_venc;
    private Status status;
    private Double vl_lanc;
    private Receita receita;

    public LancamentoReceita toEntity(LancamentoReceitaDTO dto){
        LancamentoReceita lancamento = new LancamentoReceita();
        lancamento.setId_lancamento(dto.id_lancamento);
        lancamento.setDesc_lancamento(dto.getDesc_lancamento());
        lancamento.setStatus(dto.getStatus());
        lancamento.setReceita(dto.getReceita());
        lancamento.setDt_venc(dto.getDt_venc());
        lancamento.setVl_lanc(dto.getVl_lanc());
        return lancamento;
    }

    public LancamentoReceitaDTO toDTO(LancamentoReceita lancamento){
        LancamentoReceitaDTO dto = new LancamentoReceitaDTO();
        dto.setId_lancamento(lancamento.getId_lancamento());
        dto.setDesc_lancamento(lancamento.getDesc_lancamento());
        dto.setStatus(lancamento.getStatus());
        dto.setReceita(lancamento.getReceita());
        dto.setDt_venc(lancamento.getDt_venc());
        dto.setVl_lanc(lancamento.getVl_lanc());
        return dto;
    }
}
