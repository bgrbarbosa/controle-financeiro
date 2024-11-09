package br.com.bgrbarbosa.controle_financeiro.controller;

import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import br.com.bgrbarbosa.controle_financeiro.model.dto.DespesaDTO;
import br.com.bgrbarbosa.controle_financeiro.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/despesa")
public class DespesaController {

    @Autowired
    private DespesaRepository repository;

    DespesaDTO despesaDTO = new DespesaDTO();
    DespesaDTO despesaSelecionada = new DespesaDTO();

    @GetMapping("")
    public ModelAndView index() {
        List<Despesa> despesas = repository.findAll();
        ModelAndView mv = new ModelAndView("despesa/index");
        mv.addObject("despesas", despesas);
        mv.addObject("despesaDTO", new DespesaDTO()); // Adiciona um novo DTO vazio
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        List<Despesa> despesas = repository.findAll();
        ModelAndView mv = new ModelAndView("despesa/cadastro");
        mv.addObject("despesaDTO", this.despesaDTO);
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastro(@Valid DespesaDTO dto, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("despesa/cadastro");
        if (bindingResult.hasErrors()) {
            mv.addObject("despesas", repository.findAll());
            mv.addObject("despesaDTO", dto);
            return mv;
        } else {
            Despesa despesa = dto.toEntity(dto);
            this.repository.save(despesa);
            return new ModelAndView("redirect:/despesa");
        }
    }

    @GetMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id) {
        Optional<Despesa> aux = repository.findById(id);
        if (aux.isPresent()) {
            this.despesaSelecionada = this.despesaSelecionada.toDTO(aux.get());
            ModelAndView mv = new ModelAndView("despesa/edicao");
            mv.addObject("despesaSelecionada", this.despesaSelecionada.toDTO(aux.get()));
            return mv;
        } else {
            return new ModelAndView("redirect:/despesa");
        }
    }

    @PostMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id, @Valid DespesaDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("despesa/edicao");
            this.despesaSelecionada.setId_despesa(id);
            this.despesaSelecionada.setDesc_despesa(dto.getDesc_despesa());
            mv.addObject("despesaSelecionada", this.despesaSelecionada);
            mv.addObject("hasErrors", true);
            return mv;
        } else {
            Optional<Despesa> optional = this.repository.findById(id);
            Despesa despesa = new Despesa();
            if (optional.isPresent()) {
                despesa.setId_despesa(id);
                despesa.setDesc_despesa(dto.getDesc_despesa());
                repository.save(despesa);
                return new ModelAndView("redirect:/despesa");
            } else {
                return new ModelAndView("redirect:/despesa"); // Se id não for encontrado
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
        return new ModelAndView("redirect:/despesa");
    }
}