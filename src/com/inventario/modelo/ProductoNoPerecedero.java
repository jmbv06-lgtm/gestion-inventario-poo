package com.inventario.modelo;

public class ProductoNoPerecedero extends Producto {
    private int garantiaMeses;

    public ProductoNoPerecedero(String id, String nombre, int cantidad, double precio, int garantiaMeses) {
        super(id, nombre, cantidad, precio);
        if (garantiaMeses < 0) {
            throw new IllegalArgumentException("La garantía en meses no puede ser negativa.");
        }
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    @Override
    public String getResumen() {
        return String.format(
                "[NO PERECEDERO] ID: %s | Nombre: %s | Stock: %d | Precio: $%.2f | Garantía: %d meses | Subtotal: $%.2f",
                getId(), getNombre(), getCantidad(), getPrecio(), garantiaMeses, calcularSubtotal()
        );
    }
}
