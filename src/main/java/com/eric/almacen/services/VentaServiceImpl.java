package com.eric.almacen.services;

import com.eric.almacen.dto.ventas.DetalleVentaRequest;
import com.eric.almacen.dto.ventas.VentaRequest;
import com.eric.almacen.dto.ventas.VentaResponse;
import com.eric.almacen.entities.DetalleVenta;
import com.eric.almacen.entities.Producto;
import com.eric.almacen.entities.Sucursal;
import com.eric.almacen.entities.Venta;
import com.eric.almacen.exceptions.RecursoNoEncontradoException;
import com.eric.almacen.mappers.DetalleVentaMapper;
import com.eric.almacen.mappers.VentaMapper;
import com.eric.almacen.repositories.ProductoRepository;
import com.eric.almacen.repositories.SucursalRepository;
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

    private final VentaRepository ventaRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;

    private final VentaMapper ventaMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    // TODO: Listar ventas unicamente con estatus de REGISTRADO
    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Listando todas las ventas...");

        return ventaRepository
                .findAll()
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    // TODO: Debe obtener solo las ventas con estatus REGISTRADA
    @Override
    public VentaResponse obtenerPorId(Long id) {
        return ventaMapper.entidadAResponse(obtenerVentaOException(id));
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando una nueva venta...");

        // Obtener sucursal del request
        Sucursal sucursal = obtenerSucursalOException(request.idSucursal());
        Venta venta = ventaMapper.requestAEntidad(request, sucursal);

        procesarDetallesVenta(request.productos(), venta);
        Venta ventaGuardada = ventaRepository.save(venta);
        log.info("Venta registrada con exito");

        return ventaMapper.entidadAResponse(ventaGuardada);
    }

    // TODO: Al cancelar la venta, regresar al stock la cantidad de producto vendido. Usar verbo DELETE en controller
    @Override
    public void cancelar(Long id) {
        log.info("Cancelando venta con id {}", id);

        Venta venta = obtenerVentaOException(id);

        venta.cancelarVenta();
        devolverCantidadesAStock(venta.getDetalleVenta());
    }

    // TODO: Listar ventas con estatus CANCELADA
    public void listarVentasCanceladas() {

    }

    private Venta obtenerVentaOException(Long id) {
        log.info("Buscando venta con id {} ", id);

        return ventaRepository
                .findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con id" + id));
    }

    private Sucursal obtenerSucursalOException(Long id) {
        log.info("Buscando sucursal con id {}", id);

        return sucursalRepository
                .findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Sucursal no encontrada con id " + id));
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Buscando producto con id {} ", id);

        return productoRepository
                .findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id " + id));
    }

    private void procesarDetallesVenta(List<DetalleVentaRequest> items, Venta venta) {
        items.forEach(item -> {
            Producto producto = obtenerProductoOException(item.idProducto());

            // Si la cantidad no es suficiente, mostrara excepcion
            producto.descontarCantidad(item.cantidadProducto());

            DetalleVenta detalle = detalleVentaMapper.requestAEntidad(item, producto);
            venta.agregarDetalleVenta(detalle);
        });
    }

    private void devolverCantidadesAStock(List<DetalleVenta> detalles) {
        detalles.forEach(detalleVenta -> {
            detalleVenta.getProducto().aumentarCantidad(detalleVenta.getCantidadProducto());
        });
    }
}
