package com.eric.almacen.entities;

import com.eric.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "SUCURSALES")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, unique = true, nullable = false)
    private String nombre;

    @Column(name = "DIRECCION", length = 150, nullable = false)
    private String direccion;

    public void actualizar(String nombre, String direccion) {
        StringCustomUtils.validarTamanio(nombre, 5, 50,
                "El nombre es requerido y debe tener entre 5 y 10 caracteres");
        StringCustomUtils.validarTamanio(direccion, 10, 150, "La direccion es requerida y debe tener entre 10 y 150 caracteres");
        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }
}
