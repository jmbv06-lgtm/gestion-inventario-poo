package com.inventario.interfaz;

import com.inventario.modelo.Producto;
import com.inventario.modelo.ProductoNoPerecedero;
import com.inventario.modelo.ProductoPerecedero;
import com.inventario.negocio.InventarioServicio;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventarioServicio servicio = new InventarioServicio();
        Scanner scanner = new Scanner(System.in);
        boolean activo = true;

        while (activo) {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE INVENTARIO ===");
            System.out.println("1. Registrar producto no perecedero");
            System.out.println("2. Registrar producto perecedero");
            System.out.println("3. Listar productos");
            System.out.println("4. Ajustar stock (entrada/salida)");
            System.out.println("5. Consultar valor total del inventario");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");

            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> registrarNoPerecedero(scanner, servicio);
                    case "2" -> registrarPerecedero(scanner, servicio);
                    case "3" -> listarProductos(servicio);
                    case "4" -> ajustarStock(scanner, servicio);
                    case "5" -> System.out.printf("Valor total del inventario: $%.2f%n", servicio.calcularValorTotalInventario());
                    case "0" -> {
                        activo = false;
                        System.out.println("Saliendo del sistema...");
                    }
                    default -> System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un valor numérico válido.");
            } catch (DateTimeParseException e) {
                System.out.println("Error: Formato de fecha inválido. Use YYYY-MM-DD (ejemplo: 2026-12-31).");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void registrarNoPerecedero(Scanner scanner, InventarioServicio servicio) {
        String id = leerTextoObligatorio(scanner, "ID del producto: ");
        String nombre = leerTextoObligatorio(scanner, "Nombre del producto: ");
        int cantidad = leerEnteroObligatorio(scanner, "Cantidad: ");
        double precio = leerDecimalObligatorio(scanner, "Precio unitario: ");
        int garantia = leerEnteroObligatorio(scanner, "Garantía (meses): ");

        Producto producto = new ProductoNoPerecedero(id, nombre, cantidad, precio, garantia);
        servicio.registrarProducto(producto);
        System.out.println("Producto no perecedero registrado con éxito.");
    }

    private static void registrarPerecedero(Scanner scanner, InventarioServicio servicio) {
        String id = leerTextoObligatorio(scanner, "ID del producto: ");
        String nombre = leerTextoObligatorio(scanner, "Nombre del producto: ");
        int cantidad = leerEnteroObligatorio(scanner, "Cantidad: ");
        double precio = leerDecimalObligatorio(scanner, "Precio unitario: ");
        String fechaTexto = leerTextoObligatorio(scanner, "Fecha de vencimiento (YYYY-MM-DD): ");
        LocalDate fechaVencimiento = LocalDate.parse(fechaTexto);

        Producto producto = new ProductoPerecedero(id, nombre, cantidad, precio, fechaVencimiento);
        servicio.registrarProducto(producto);
        System.out.println("Producto perecedero registrado con éxito.");
    }

    private static void listarProductos(InventarioServicio servicio) {
        List<Producto> lista = servicio.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados en el inventario.");
            return;
        }
        System.out.println("\n--- INVENTARIO ACTUAL ---");
        for (Producto p : lista) {
            System.out.println(p.getResumen());
        }
    }

    private static void ajustarStock(Scanner scanner, InventarioServicio servicio) {
        String id = leerTextoObligatorio(scanner, "ID del producto: ");
        int delta = leerEnteroObligatorio(scanner, "Cantidad a sumar (+) o restar (-): ");
        servicio.ajustarStock(id, delta);
        System.out.println("Stock actualizado correctamente.");
    }

    private static String leerTextoObligatorio(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        if (entrada.isEmpty()) {
            throw new IllegalArgumentException("El campo '" + mensaje.replace(":", "").trim() + "' es obligatorio.");
        }
        return entrada;
    }

    private static int leerEnteroObligatorio(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        if (entrada.isEmpty()) {
            throw new IllegalArgumentException("El campo '" + mensaje.replace(":", "").trim() + "' es obligatorio.");
        }
        return Integer.parseInt(entrada);
    }

    private static double leerDecimalObligatorio(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        if (entrada.isEmpty()) {
            throw new IllegalArgumentException("El campo '" + mensaje.replace(":", "").trim() + "' es obligatorio.");
        }
        return Double.parseDouble(entrada);
    }
}
