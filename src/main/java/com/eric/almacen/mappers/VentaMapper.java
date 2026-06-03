package com.eric.almacen.mappers;

import com.eric.almacen.dto.ventas.VentaRequest;
import com.eric.almacen.dto.ventas.VentaResponse;
import com.eric.almacen.entities.Sucursal;
import com.eric.almacen.entities.Venta;
import com.eric.almacen.enums.EstadoVenta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;


@Component
@RequiredArgsConstructor
public class VentaMapper {

    private final SucursalMapper sucursalMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    public Venta requestAEntidad(VentaRequest request, Sucursal sucursal) {
        if (request == null) return null;

        return Venta.builder()
                .fecha(LocalDate.now())
                .estadoVenta(EstadoVenta.REGISTRADA) // A falta de un estado inicial por defecto
                .sucursal(sucursal)
                .detalleVenta(new ArrayList<>())
                .build();
    }

    public VentaResponse entidadAResponse(Venta entidad) {
        if (entidad == null) return null;

        return new VentaResponse(
                entidad.getId(),
                entidad.getFecha().toString(),
                entidad.getEstadoVenta().getDescripcion(),
                sucursalMapper.entidadAResponse(entidad.getSucursal()),
                entidad.getDetalleVenta().stream()
                        .map(detalleVentaMapper::entidadAResponse)
                        .toList(),
                entidad.getTotal()
        );
    }
}
