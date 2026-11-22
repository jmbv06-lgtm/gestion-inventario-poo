package com.inventario.modelo;

import java.time.LocalDate;

public class ProductoPerecedero extends Producto {
    private LocalDate fechaVencimiento;

    public ProductoPerecedero(String id, String nombre, int cantidad, double precio, LocalDate fechaVencimiento) {
        super(id, nombre, cantidad, precio);
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria para productos perecederos.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    @Override
    public String getResumen() {
        String estado = estaVencido() ? "VENCIDO" : "VIGENTE";
        return String.format(
                "[PERECEDERO] ID: %s | Nombre: %s | Stock: %d | Precio: $%.2f | Vence: %s (%s) | Subtotal: $%.2f",
                getId(), getNombre(), getCantidad(), getPrecio(), fechaVencimiento, estado, calcularSubtotal()
        );
    }
}
