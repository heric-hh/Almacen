package com.eric.almacen.mappers;

import com.eric.almacen.dto.ventas.DetalleVentaResponse;
import com.eric.almacen.entities.DetalleVenta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetalleVentaMapper {

    public DetalleVentaResponse entidadAResponse(DetalleVenta entidad) {
        if (entidad == null) return null;

        BigDecimal subtotal = entidad.getPrecioProducto()
                .multiply(BigDecimal.valueOf(entidad.getCantidadProducto()));

        return new DetalleVentaResponse(
                entidad.getId(),
                entidad.getProducto().getNombre(),
                entidad.getCantidadProducto(),
                entidad.getPrecioProducto(),
                subtotal
        );
    }
}
