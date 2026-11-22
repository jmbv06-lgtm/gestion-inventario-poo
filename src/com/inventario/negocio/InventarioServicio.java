package com.inventario.negocio;

import com.inventario.modelo.Producto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventarioServicio {
    private final List<Producto> productos = new ArrayList<>();

    public void registrarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (buscarPorId(producto.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un producto registrado con el ID: " + producto.getId());
        }
        productos.add(producto);
    }

    public Producto buscarPorId(String id) {
        if (id == null) {
            return null;
        }
        for (Producto p : productos) {
            if (p.getId().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> listarProductos() {
        return Collections.unmodifiableList(productos);
    }

    public void ajustarStock(String id, int delta) {
        Producto encontrado = buscarPorId(id);
        if (encontrado == null) {
            throw new IllegalArgumentException("No se encontró un producto con el ID: " + id);
        }
        int nuevaCantidad = encontrado.getCantidad() + delta;
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("Stock insuficiente para descontar " + Math.abs(delta) + " unidades.");
        }
        encontrado.setCantidad(nuevaCantidad);
    }

    public double calcularValorTotalInventario() {
        double total = 0.0;
        for (Producto p : productos) {
            total += p.calcularSubtotal();
        }
        return total;
    }
}
