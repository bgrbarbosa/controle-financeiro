package br.com.bgrbarbosa.controle_financeiro.controller;

import br.com.bgrbarbosa.controle_financeiro.model.LancamentoDespesa;
import br.com.bgrbarbosa.controle_financeiro.model.LancamentoReceita;
import br.com.bgrbarbosa.controle_financeiro.model.dto.GraficoDTO;
import br.com.bgrbarbosa.controle_financeiro.model.dto.LancamentoDespesaDTO;
import br.com.bgrbarbosa.controle_financeiro.model.dto.LancamentoReceitaDTO;
import br.com.bgrbarbosa.controle_financeiro.repository.LancamentoDespesaRepository;
import br.com.bgrbarbosa.controle_financeiro.repository.LancamentoReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "/relatorio/despesa")
public class RelatórioDespesaController {

    @Autowired
    private LancamentoReceitaRepository receitaRepository;

    @Autowired
    private LancamentoDespesaRepository despesaRepository;

    private Double total_despesa;
    private Double total_receita;
    private GraficoDTO graficoDTO;
    LancamentoDespesaDTO lancamentoDTO = new LancamentoDespesaDTO();

    @GetMapping("")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("/relatorio/despesa/index");
        return mv;
    }

    @GetMapping(value = "/period")
    public ModelAndView findByPeriod(
            @RequestParam("dt_init")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dt_init,

            @RequestParam("dt_final")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dt_final) {

        List<LancamentoDespesa> lancamentos = despesaRepository.findByPeriod(dt_init, dt_final);
        List<LancamentoReceita> lancamentosReceita = receitaRepository.findByPeriod(dt_init, dt_final);

        this.total_despesa = 0.00;
        this.total_receita = 0.00;

        for (LancamentoDespesa lancamento: lancamentos){
            total_despesa = total_despesa + lancamento.getVl_lanc();
        }

        for (LancamentoReceita lancamento: lancamentosReceita){
            total_receita = total_receita + lancamento.getVl_lanc();
        }

        ModelAndView mv = new ModelAndView("/relatorio/despesa/index");
        mv.addObject("lancamentos", lancamentos);
        mv.addObject("lancamentoDTO", new LancamentoReceitaDTO());
        mv.addObject("dt_init", dt_init);
        mv.addObject("dt_final", dt_final);
        mv.addObject("graficoDTO", graficoDTO);
        mv.addObject("total_despesa", this.total_despesa);
        mv.addObject("total_receita", this.total_receita);
        return mv;
    }
}
