package br.com.bgrbarbosa.controle_financeiro.controller;

import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import br.com.bgrbarbosa.controle_financeiro.model.LancamentoDespesa;
import br.com.bgrbarbosa.controle_financeiro.model.LancamentoReceita;
import br.com.bgrbarbosa.controle_financeiro.model.dto.LancamentoDespesaDTO;
import br.com.bgrbarbosa.controle_financeiro.model.dto.LancamentoReceitaDTO;
import br.com.bgrbarbosa.controle_financeiro.model.dto.ReceitaDTO;
import br.com.bgrbarbosa.controle_financeiro.model.enums.Status;
import br.com.bgrbarbosa.controle_financeiro.repository.DespesaRepository;
import br.com.bgrbarbosa.controle_financeiro.repository.LancamentoDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/lancamentodespesa")
public class LancamentoDespesaController {

    @Autowired
    private LancamentoDespesaRepository repository;

    @Autowired
    private DespesaRepository despesaRepository;

    List<Despesa> despesas = new ArrayList<>();
    LancamentoDespesaDTO lancamentoDTO = new LancamentoDespesaDTO();
    LancamentoDespesa lancamentoSelecionado = new LancamentoDespesa();


    @GetMapping("")
    public ModelAndView index() {
        List<LancamentoDespesa> lancamentos = repository.findAll();
        ModelAndView mv = new ModelAndView("lancamentodespesa/index");
        mv.addObject("lancamentos", lancamentos);
        mv.addObject("lancamentoDTO", new LancamentoDespesaDTO());
        return mv;
    }
    @GetMapping(value = "/period")
    public ModelAndView findByPeriod(
            @RequestParam("dt_init")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dt_init,

            @RequestParam("dt_final")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dt_final) {

        List<LancamentoDespesa> lancamentos = repository.findByPeriod(dt_init, dt_final);
        ModelAndView mv = new ModelAndView("lancamentodespesa/index");
        mv.addObject("lancamentos", lancamentos);
        mv.addObject("lancamentoDTO", new LancamentoReceitaDTO());
        mv.addObject("dt_init", dt_init);
        mv.addObject("dt_final", dt_final);
        return mv;
    }


    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        this.despesas = despesaRepository.findAll();
        ModelAndView mv = new ModelAndView("lancamentodespesa/cadastro");
        mv.addObject("lancamentoDTO", this.lancamentoDTO);
        mv.addObject("despesas", this.despesas);
        mv.addObject("statusOptions", Status.values());
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastro(LancamentoDespesaDTO dto, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("lancamentodespesa/cadastro");
        if (bindingResult.hasErrors()) {
            mv.addObject("despesas", repository.findAll());
            mv.addObject("lancamentoDTO", lancamentoDTO);
            return mv;
        } else {
            LancamentoDespesa lancamento = dto.toEntity(dto);
            this.repository.save(lancamento);
            return new ModelAndView("redirect:/lancamentodespesa");
        }
    }

    @GetMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id) {
        Optional<LancamentoDespesa> aux = repository.findById(id);
        if (aux.isPresent()) {
            this.lancamentoSelecionado = aux.get();
            this.despesas = despesaRepository.findAll();
            ModelAndView mv = new ModelAndView("lancamentodespesa/edicao");
            mv.addObject("lancamentoSelecionado", this.lancamentoSelecionado);
            mv.addObject("despesas", despesas);
            mv.addObject("statusOptions", Status.values());
            return mv;
        } else {
            return new ModelAndView("redirect:/lancamentodespesa");
        }
    }

    @PostMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id, @Valid LancamentoDespesaDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("lancamentodespesa/edicao");
            this.lancamentoSelecionado.setId_lancamento(id);
            this.lancamentoSelecionado.setDesc_lancamento(dto.getDesc_lancamento());
            this.lancamentoSelecionado.setDespesa(dto.getDespesa());
            this.lancamentoSelecionado.setStatus(dto.getStatus());
            this.lancamentoSelecionado.setDt_venc(dto.getDt_venc());
            this.lancamentoSelecionado.setVl_lanc(dto.getVl_lanc());
            mv.addObject("lancamentoSelecionado", this.lancamentoSelecionado);
            mv.addObject("hasErrors", true);
            return mv;
        } else {
            Optional<LancamentoDespesa> optional = this.repository.findById(id);
            LancamentoDespesa lancamento = new LancamentoDespesa();
            if (optional.isPresent()) {
                lancamento.setId_lancamento(id);
                lancamento.setDesc_lancamento(dto.getDesc_lancamento());
                lancamento.setDespesa(dto.getDespesa());
                lancamento.setStatus(dto.getStatus());
                lancamento.setDt_venc(dto.getDt_venc());
                lancamento.setVl_lanc(dto.getVl_lanc());
                repository.save(lancamento);
                return new ModelAndView("redirect:/lancamentodespesa");
            } else {
                return new ModelAndView("redirect:/lancamentodespesa"); // Se id não for encontrado
            }
        }
    }

    @GetMapping("/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        try {
            this.repository.deleteById(id);
            //mv.addObject("erro", false);
        }
        catch (EmptyResultDataAccessException e) {
            System.out.println(e);
           // mv = this.retornaErroProfessor("DELETE ERROR: Professor #" + id + " não encontrado no banco!");
        }
        return new ModelAndView("redirect:/lancamentodespesa");
    }


}