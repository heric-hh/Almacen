package com.eric.almacen.enums;

import com.eric.almacen.exceptions.RecursoNoEncontradoException;
import com.eric.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Categoria {
    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electrónica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacios(descripcion, "La descripcion es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion.trim());
        for (Categoria categoria: values()) {
            if (StringCustomUtils.quitarAcentos(categoria.descripcion).equalsIgnoreCase(descripcionNormalizada)) {
                return categoria;
            }
        }
        throw new RecursoNoEncontradoException("No existe una categoria con la descripcion: " + descripcion);
    }
}
