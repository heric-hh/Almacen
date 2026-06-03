package com.eric.almacen.services;

import com.eric.almacen.dto.ventas.VentaRequest;
import com.eric.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse> listar();
    VentaResponse obtenerPorId(Long id);
    VentaResponse registrar(VentaRequest request);
    void cancelar(Long id);
}
