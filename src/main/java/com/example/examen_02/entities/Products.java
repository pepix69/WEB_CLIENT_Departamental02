package com.example.examen_02.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @NotNull
    private String product_name;

    private String product_description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int stock_quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at = new Date();
}
