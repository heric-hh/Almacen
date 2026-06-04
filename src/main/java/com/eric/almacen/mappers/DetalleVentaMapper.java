package com.eric.almacen.mappers;

import com.eric.almacen.dto.ventas.DetalleVentaRequest;
import com.eric.almacen.dto.ventas.DetalleVentaResponse;
import com.eric.almacen.entities.DetalleVenta;
import com.eric.almacen.entities.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetalleVentaMapper {

    public DetalleVenta requestAEntidad(DetalleVentaRequest request, Producto producto) {
        if (request == null) return null;

        return DetalleVenta.builder()
                .producto(producto)
                .cantidadProducto(request.cantidadProducto())
                .precioProducto(producto.getPrecio())
                .build();
    }

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
