package com.eric.almacen.services;

import com.eric.almacen.dto.sucursales.SucursalRequest;
import com.eric.almacen.dto.sucursales.SucursalResponse;
import com.eric.almacen.entities.Sucursal;
import com.eric.almacen.exceptions.RecursoNoEncontradoException;
import com.eric.almacen.mappers.SucursalMapper;
import com.eric.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService {
    private final SucursalRepository repository;
    private final SucursalMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        log.info("Listando todas las sucursales");
        return repository
                .findAll()
                .stream()
                .map(mapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        return mapper.entidadAResponse(obtenerSucursalOException(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        log.info("Registrando nueva sucursal");
        validarDatosUnicos(request);
        Sucursal sucursal = mapper.requestAEntidad(request);
        repository.save(sucursal);
        return mapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {
        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Actualizando sucursal con id: {}", id);

        validarCambiosUnicos(request, id);
        sucursal.actualizar(request.nombre(), request.direccion());

        log.info("Sucursal con id {} actualizada correctamente", id);

        // repository.save(sucursal) JPA detecta cambios en la entidad y guarda automaticamente
        return mapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Eliminando sucursal con id: {}", id);

        repository.delete(sucursal);

        log.info("Sucursal con id {} eliminada", id);
    }

    private Sucursal obtenerSucursalOException(Long id) {
        log.info("Buscando sucursal con id: {}", id);

        return repository
                .findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Sucursal no encontrada con id: " + id));
    }

    private void validarDatosUnicos(SucursalRequest request) {
        log.info("Validando nombre unico...");
        if (repository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de : " + request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id) {
        log.info("Validando cambio en nombre unico...");
        if (repository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de : " + request.nombre());
    }
}
