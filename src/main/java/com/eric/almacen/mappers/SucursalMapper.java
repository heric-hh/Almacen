package com.eric.almacen.mappers;

import com.eric.almacen.dto.sucursales.SucursalRequest;
import com.eric.almacen.dto.sucursales.SucursalResponse;
import com.eric.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {
    public Sucursal requestAEntidad(SucursalRequest request) {
        if (request == null) return null;

        return Sucursal.builder()
                .nombre(request.nombre().trim())
                .direccion(request.direccion().trim())
                .build();
    }

    public SucursalResponse entidadAResponse(Sucursal entidad) {
        if (entidad == null) return null;

        return new SucursalResponse(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getDireccion()
        );
    }
}
