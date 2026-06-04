package com.eric.almacen.controllers;

import com.eric.almacen.dto.ventas.VentaRequest;
import com.eric.almacen.dto.ventas.VentaResponse;
import com.eric.almacen.services.VentaService;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(
            @Valid
            @RequestBody VentaRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }
}
