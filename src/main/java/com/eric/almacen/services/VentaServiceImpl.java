package com.eric.almacen.services;

import com.eric.almacen.dto.ventas.VentaRequest;
import com.eric.almacen.dto.ventas.VentaResponse;
import com.eric.almacen.mappers.VentaMapper;
import com.eric.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService {

    private final VentaRepository repository;
    private final VentaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Listando todas las ventas...");

        return List.of();
    }

    @Override
    public VentaResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        return null;
    }

    @Override
    public void cancelar(Long id) {

    }
}
