package com.eric.almacen.services;

import com.eric.almacen.dto.productos.ProductoRequest;
import com.eric.almacen.dto.productos.ProductoResponse;
import com.eric.almacen.entities.Producto;
import com.eric.almacen.enums.Categoria;
import com.eric.almacen.exceptions.RecursoNoEncontradoException;
import com.eric.almacen.mappers.ProductoMapper;
import com.eric.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(
            String nombre,
            String categoria,
            BigDecimal precioMin,
            BigDecimal precioMax
    ) {
        log.info("Listando todos los productos");
        return repository
                .findAll()
                .stream()
                .map(mapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return mapper.entidadAResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando un nuevo producto...");
        Categoria categoria = Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim());
        Producto producto = mapper.requestAEntidad(request, categoria);
        repository.save(producto);
        log.info("Nuevo producto {} registrado", producto.getNombre());
        return mapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        Producto producto = obtenerProductoOException(id);
        log.info("Actualizando producto con id: {}", id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()),
                request.precio(),
                request.cantidad()
        );

        log.info("Producto {} actualizado", producto.getNombre());
        return mapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOException(id);
        log.info("Eliminando producto con id: {}", id);
        repository.delete(producto);
        log.info("Producto con id {} eliminado", id);
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Buscando producto con id: " + id);
        return repository
                .findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }


}
