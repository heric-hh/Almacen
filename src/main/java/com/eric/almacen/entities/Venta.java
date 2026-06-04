package com.eric.almacen.entities;

import com.eric.almacen.enums.EstadoVenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVenta  = new ArrayList<>();

    public void agregarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalleVenta == null) {
            throw new IllegalArgumentException("El detalle es requerido");
        }
        // Actualiza la lista de detalles de venta de la entidad Ventas
        this.detalleVenta.add(detalleVenta);
        // Actualiza la propiedad VENTA de la entidad DetalleVenta
        detalleVenta.setVenta(this);
    }

    public void cancelarVenta() {
        if (this.estadoVenta == EstadoVenta.CANCELADA)
            throw new IllegalArgumentException("La venta ya esta cancelada");
        this.estadoVenta = EstadoVenta.CANCELADA;
    }

    public BigDecimal getTotal() {
        return detalleVenta.stream()
                .map(detalle -> detalle.getPrecioProducto().multiply(BigDecimal.valueOf(detalle.getCantidadProducto())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
