package com.codigo.softwaredemercado.repository;

import com.codigo.softwaredemercado.model.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto,Long> {

    boolean existsByNome(String nome);

    @Override
    boolean existsById(Long id);
    ArrayList<Produto> findByNomeContainingIgnoreCase(String nome);
    ArrayList<Produto> findByTipoContainingIgnoreCase(String tipo);

    ArrayList<Produto> findByNomeContainingIgnoreCaseAndTipoContainingIgnoreCase(String nome, String tipo);

    ArrayList<Produto> findAll ();
}
