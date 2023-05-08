package com.codigo.softwaredemercado.Control;

import com.codigo.softwaredemercado.model.Produto;
import com.codigo.softwaredemercado.repository.ProdutoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping(value = "/cadastrar")
    public String cadastrarProduto(@RequestBody Produto produto){
        if(produtoRepository.existsByNome(produto.getNome())){
            return"Esse produto já existe, verifique na lista!";
        }
        else{
            try {
                produtoRepository.save(produto);
            }
            catch (ConstraintViolationException ex){
                return "ERRO: " + ex.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessageTemplate)
                        .findFirst()
                        .orElse("Erro desconhecido");
            }
        }
        return "Cadastrado com sucesso!";
    }

    @PostMapping(value = "/atualizarProduto")
    public String atualizarProduto(@RequestBody @Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "ERRO: " + result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Erro desconhecido");
        }else {
            if (produtoRepository.existsById(produto.getID())) {
                return "Produto atualizado!";
            } else {
                return "Produto não encontrado!";
            }
        }
    }

    @GetMapping(value = "/produto")
    public String procurarProdutoNome(Model modelo, @RequestParam(required = false) String nome, @RequestParam(required = false) String tipo){
        List<Produto> produtos = null;
        if(nome != null){
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        }
        if(tipo != null){
            produtos = produtoRepository.findByTipoContainingIgnoreCase(tipo);
        }
        if(tipo != null && nome !=null){
            produtos = produtoRepository.findByNomeContainingIgnoreCaseAndTipoContainingIgnoreCase(nome,tipo);
        }
        else{
            produtos = produtoRepository.findAll();
        }
        modelo.addAttribute("produtos",produtos);
        return "inicio";
    }

    @GetMapping("/")
    public String paginaInicial(Model modelo){
        List<Produto> produtos = produtoRepository.findAll();
        modelo.addAttribute("produtos",produtos);
        return "inicio";
    }



    @DeleteMapping(value = "/deletar/{id}")
    public String deletarProduto(@PathVariable long id){
        if( produtoRepository.existsById(id)){
            produtoRepository.deleteById(id);
            return "produto deletado";
        }
        else{
            return "produto não encontrado";
        }
    }

}
