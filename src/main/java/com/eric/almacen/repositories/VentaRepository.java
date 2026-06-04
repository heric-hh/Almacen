package com.eric.almacen.repositories;

import com.eric.almacen.entities.Venta;
import com.eric.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Boolean existsBySucursalIdAndEstadoVenta(Long sucursalId, EstadoVenta estadoVenta);
    Boolean existsByDetalleVentaProductoIdAndEstadoVenta(Long productoId, EstadoVenta estadoVenta);
}

