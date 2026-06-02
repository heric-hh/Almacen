package com.eric.almacen.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {}
