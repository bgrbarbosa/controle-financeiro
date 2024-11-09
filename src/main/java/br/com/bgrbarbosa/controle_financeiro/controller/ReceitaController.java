package br.com.bgrbarbosa.controle_financeiro.controller;

import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import br.com.bgrbarbosa.controle_financeiro.model.Receita;
import br.com.bgrbarbosa.controle_financeiro.model.dto.DespesaDTO;
import br.com.bgrbarbosa.controle_financeiro.model.dto.ReceitaDTO;
import br.com.bgrbarbosa.controle_financeiro.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/receita")
public class ReceitaController {

    @Autowired
    private ReceitaRepository repository;

    ReceitaDTO receitaDTO = new ReceitaDTO();
    ReceitaDTO receitaSelecionada = new ReceitaDTO();

    @GetMapping("")
    public ModelAndView index() {
        List<Receita> receitas = repository.findAll();
        ModelAndView mv = new ModelAndView("receita/index");
        mv.addObject("receitas", receitas);
        mv.addObject("receitaDTO", new ReceitaDTO()); // Adiciona um novo DTO vazio
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        List<Receita> despesas = repository.findAll();
        ModelAndView mv = new ModelAndView("receita/cadastro");
        mv.addObject("receitaDTO", this.receitaDTO);
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastro(@Valid ReceitaDTO dto, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("receita/cadastro");
        if (bindingResult.hasErrors()) {
            mv.addObject("receitas", repository.findAll());
            mv.addObject("receitaDTO", dto);
            return mv;
        } else {
            Receita receita = dto.toEntity(dto);
            this.repository.save(receita);
            return new ModelAndView("redirect:/receita");
        }
    }

    @GetMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id) {
        Optional<Receita> aux = repository.findById(id);
        if (aux.isPresent()) {
            this.receitaSelecionada = this.receitaSelecionada.toDTO(aux.get());
            ModelAndView mv = new ModelAndView("receita/edicao");
            mv.addObject("receitaSelecionada", this.receitaSelecionada.toDTO(aux.get()));
            return mv;
        } else {
            return new ModelAndView("redirect:/receita");
        }
    }

    @PostMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id, @Valid ReceitaDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("despesa/edicao");
            this.receitaSelecionada.setId_receita(id);
            this.receitaSelecionada.setDesc_receita(dto.getDesc_receita());
            mv.addObject("receitaSelecionada", this.receitaSelecionada);
            mv.addObject("hasErrors", true);
            return mv;
        } else {
            Optional<Receita> optional = this.repository.findById(id);
            Receita receita = new Receita();
            if (optional.isPresent()) {
                receita.setId_receita(id);
                receita.setDesc_receita(dto.getDesc_receita());
                repository.save(receita);
                return new ModelAndView("redirect:/receita");
            } else {
                return new ModelAndView("redirect:/receita"); // Se id não for encontrado
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
        return new ModelAndView("redirect:/receita");
    }
}