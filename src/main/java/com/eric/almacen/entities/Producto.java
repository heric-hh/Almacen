package com.eric.almacen.entities;

import com.eric.almacen.enums.Categoria;
import com.eric.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "PRODUCTOS")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTOS")
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;

    @Column(name = "CATEGORIA", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void aumentarCantidad(int cantidad) {
        if (cantidad < 0)
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad(int cantidad) {
        if (this.cantidad < cantidad)
            throw new IllegalArgumentException("Cantidad insuficiente para descontar");
        this.cantidad -= cantidad;
    }

    private void validarDatos(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        StringCustomUtils.validarTamanio(nombre, 5, 30, "El nombre es requerido y debe tener entre 5 y 30 caracteres");
        if (categoria == null)
            throw new IllegalArgumentException("La categoria es requerida");
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio es requerido y debe ser positivo");
        if (cantidad == null || cantidad < 0)
            throw new IllegalArgumentException("La cantidad es requerida y debe ser positiva");
    }
}
