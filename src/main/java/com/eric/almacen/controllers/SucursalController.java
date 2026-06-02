package com.eric.almacen.controllers;

import com.eric.almacen.dto.sucursales.SucursalRequest;
import com.eric.almacen.dto.sucursales.SucursalResponse;
import com.eric.almacen.services.SucursalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@AllArgsConstructor
@Validated
public class SucursalController {
    private final SucursalService service;

    @GetMapping
    public ResponseEntity<List<SucursalResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> obtenerPorId(
            @Positive(message = "El id debe ser positivo")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SucursalResponse> registrar(
            @Valid
            @RequestBody SucursalRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponse> actualizar(
            @PathVariable
            @Positive(message = "El id debe ser positivo")
            Long id,
            @Valid @RequestBody SucursalRequest request
    ) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id
    ) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
