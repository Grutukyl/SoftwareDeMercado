package com.codigo.softwaredemercado.Control;

import com.codigo.softwaredemercado.model.Produto;
import com.codigo.softwaredemercado.repository.ProdutoRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping(value = "/cadastrar")
    public ResponseEntity<Object> cadastrarProduto(Produto produto) {
        if (produtoRepository.existsByNome(produto.getNome())) {
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
        } else {
            try {
                produtoRepository.save(produto);
                return new ResponseEntity(HttpStatus.OK);
            } catch (ConstraintViolationException ex) {
                return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(@RequestParam(required = false) String id, Model modelo) {
        if (id != null) {
            Optional<Produto> produto;
            produto = produtoRepository.findById(Long.parseLong(id));
            modelo.addAttribute("produto", produto);
            System.out.println("Retornando : " + produto.get().getNome());
            return "cadastro";
        }
        return "cadastro";
    }

    @PostMapping(value = "/atualizarProduto")
    public ResponseEntity atualizarProduto(Produto produto) {
        if (produtoRepository.existsByNome(produto.getNome())) {
            try {
                produtoRepository.save(produto);
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
            } catch (ConstraintViolationException ex) {
                return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/produto")
    public String procurarProdutoNome(Model modelo, @RequestParam(required = false) String nome, @RequestParam(required = false) String tipo, Authentication authentication) {
        List<Produto> produtos = null;
        if (nome != null) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        }
        if (tipo != null) {
            produtos = produtoRepository.findByTipoContainingIgnoreCase(tipo);
        }
        if (tipo != null && nome != null) {
            produtos = produtoRepository.findByNomeContainingIgnoreCaseAndTipoContainingIgnoreCase(nome, tipo);
        } else {
            produtos = produtoRepository.findAll();
        }
        modelo.addAttribute("produtos", produtos);
        if (authentication != null) {
            modelo.addAttribute("autenticado", authentication.isAuthenticated());
        }
        return "inicio";
    }

    @GetMapping("/inicio")
    public String paginaInicial(Model modelo, Authentication authentication) {
        List<Produto> produtos = produtoRepository.findAll();
        modelo.addAttribute("produtos", produtos);
        if (authentication != null) {
            modelo.addAttribute("autenticado", authentication.isAuthenticated());
        }

        return "inicio";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/deletar/{id}")
    public ResponseEntity deletarProduto(@PathVariable long id, Authentication authentication) {
        if (authentication != null) {
            if (produtoRepository.existsById(id)) {
                produtoRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
