package br.com.bgrbarbosa.controle_financeiro.controller;


import br.com.bgrbarbosa.controle_financeiro.model.LancamentoReceita;
import br.com.bgrbarbosa.controle_financeiro.model.Receita;
import br.com.bgrbarbosa.controle_financeiro.model.dto.LancamentoReceitaDTO;
import br.com.bgrbarbosa.controle_financeiro.model.enums.Status;
import br.com.bgrbarbosa.controle_financeiro.repository.LancamentoReceitaRepository;
import br.com.bgrbarbosa.controle_financeiro.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/lancamentoreceita")
public class LancamentoReceitaController {

    @Autowired
    private LancamentoReceitaRepository repository;

    @Autowired
    private ReceitaRepository receitaRepository;

    List<Receita> receitas = new ArrayList<>();
    LancamentoReceitaDTO lancamentoDTO = new LancamentoReceitaDTO();
    LancamentoReceita lancamentoSelecionado = new LancamentoReceita();


    @GetMapping("")
    public ModelAndView index() {
        List<LancamentoReceita> lancamentos = repository.findAll();
        ModelAndView mv = new ModelAndView("lancamentoreceita/index");
        mv.addObject("lancamentos", lancamentos);
        mv.addObject("lancamentoDTO", new LancamentoReceitaDTO());
        return mv;
    }
    @GetMapping(value = "/period")
    public ModelAndView findByPeriod(
            @RequestParam("dt_init")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dt_init,

            @RequestParam("dt_final")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dt_final) {

        List<LancamentoReceita> lancamentos = repository.findByPeriod(dt_init, dt_final);
        ModelAndView mv = new ModelAndView("lancamentoreceita/index");
        mv.addObject("lancamentos", lancamentos);
        mv.addObject("lancamentoDTO", new LancamentoReceitaDTO());
        mv.addObject("dt_init", dt_init);
        mv.addObject("dt_final", dt_final);
        return mv;
    }


    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        this.receitas = receitaRepository.findAll();
        ModelAndView mv = new ModelAndView("lancamentoreceita/cadastro");
        mv.addObject("lancamentoDTO", this.lancamentoDTO);
        mv.addObject("receitas", this.receitas);
        mv.addObject("statusOptions", Status.values());
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastro(LancamentoReceitaDTO dto, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("lancamentoreceita/cadastro");
        if (bindingResult.hasErrors()) {
            mv.addObject("receitas", repository.findAll());
            mv.addObject("lancamentoDTO", lancamentoDTO);
            return mv;
        } else {
            LancamentoReceita lancamento = dto.toEntity(dto);
            this.repository.save(lancamento);
            return new ModelAndView("redirect:/lancamentoreceita");
        }
    }

    @GetMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id) {
        Optional<LancamentoReceita> aux = repository.findById(id);
        if (aux.isPresent()) {
            this.lancamentoSelecionado = aux.get();
            this.receitas = receitaRepository.findAll();
            ModelAndView mv = new ModelAndView("lancamentoreceita/edicao");
            mv.addObject("lancamentoSelecionado", this.lancamentoSelecionado);
            mv.addObject("receitas", receitas);
            mv.addObject("statusOptions", Status.values());
            return mv;
        } else {
            return new ModelAndView("redirect:/lancamentoreceita");
        }
    }

    @PostMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id, @Valid LancamentoReceitaDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("lancamentoreceita/edicao");
            this.lancamentoSelecionado.setId_lancamento(id);
            this.lancamentoSelecionado.setDesc_lancamento(dto.getDesc_lancamento());
            this.lancamentoSelecionado.setReceita(dto.getReceita());
            this.lancamentoSelecionado.setStatus(dto.getStatus());
            this.lancamentoSelecionado.setDt_venc(dto.getDt_venc());
            this.lancamentoSelecionado.setVl_lanc(dto.getVl_lanc());
            mv.addObject("lancamentoSelecionado", this.lancamentoSelecionado);
            mv.addObject("hasErrors", true);
            return mv;
        } else {
            Optional<LancamentoReceita> optional = this.repository.findById(id);
            LancamentoReceita lancamento = new LancamentoReceita();
            if (optional.isPresent()) {
                lancamento.setId_lancamento(id);
                lancamento.setDesc_lancamento(dto.getDesc_lancamento());
                lancamento.setReceita(dto.getReceita());
                lancamento.setStatus(dto.getStatus());
                lancamento.setDt_venc(dto.getDt_venc());
                lancamento.setVl_lanc(dto.getVl_lanc());
                repository.save(lancamento);
                return new ModelAndView("redirect:/lancamentoreceita");
            } else {
                return new ModelAndView("redirect:/lancamentoreceita"); // Se id não for encontrado
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
        return new ModelAndView("redirect:/lancamentoreceita");
    }


}