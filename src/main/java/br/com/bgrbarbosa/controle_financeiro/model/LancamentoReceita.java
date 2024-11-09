package br.com.bgrbarbosa.controle_financeiro.model;


import br.com.bgrbarbosa.controle_financeiro.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_lanc_receita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lancamento;

    @Column
    private String desc_lancamento;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dt_venc;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Double vl_lanc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_receita")
    private Receita receita;
}
