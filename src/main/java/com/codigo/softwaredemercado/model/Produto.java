package com.codigo.softwaredemercado.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @Column
    @NotBlank(message = "O tipo é obrigatório")
    private String tipo;
    @Column
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor é obrigatóriamente positivo e maior que zero")
    private double valor;
    @Column
    @NotNull(message = "O estoque é obrigatório")
    @PositiveOrZero (message = "O estoque é obrigatóriamente positivo ou zero")
    private int estoque;
}
