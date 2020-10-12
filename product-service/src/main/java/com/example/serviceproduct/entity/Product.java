package com.example.serviceproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "tbl_products")
@Data
@AllArgsConstructor //Lombok Constructor
@NoArgsConstructor // Lombok Constructor Vacio
@Builder // Para poder Instanciarlo
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "El campo no puede estar vacío")
    private String name;
    private String description;
    @Positive(message = "El campo debe ser mayor a cero")
    private Double stock;
    private Double price;
    private String status;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @NotNull(message = "La categoría no puede ser vacía")
    // EAGER Genera todas las categorias LAZY genera la que necesita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    //Ignoramos el error de hibernate
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

}
