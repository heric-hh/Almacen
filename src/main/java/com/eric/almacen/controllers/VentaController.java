package com.eric.almacen.controllers;

import com.eric.almacen.dto.ventas.VentaRequest;
import com.eric.almacen.dto.ventas.VentaResponse;
import com.eric.almacen.services.VentaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerPorId(
            @Positive(message = "El id debe ser positivo")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<VentaResponse>> listarVentasCanceladas() {
        return ResponseEntity.ok(ventaService.listarCanceladas());
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(
            @Valid
            @RequestBody VentaRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id
    ) {
        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
