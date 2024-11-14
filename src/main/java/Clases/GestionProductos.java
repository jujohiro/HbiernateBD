package Clases;

import dao.ProductoDao;
import entidades.Persona;
import entidades.Producto;

import javax.swing.*;
import java.util.List;

public class GestionProductos {

    ProductoDao miProductoDao = new ProductoDao();

    public GestionProductos() {

        String menu = "MENU DE OPCIONES - GESTION PRODUCTOS\n\n";
        menu += "1. Registrar Producto\n";
        menu += "2. Consultar Producto\n";
        menu += "3. Consultar Lista de Productos\n";
        menu += "4. Consultar Personas por producto\n";
        menu += "5. Comprar Productos\n";
        menu += "6. Actualizar Producto\n";
        menu += "7. Eliminar Producto\n";
        menu += "8. Salir\n";

        int opc = 0;

        while (opc != 8) {
            try {
                opc = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opc) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        consultar();
                        break;
                    case 3:
                        consultarLista();
                        break;
                    case 4:
                        consultarPersonasPorProducto();
                        break;
                    case 5:
                        comprarProductos();
                        break;
                    case 6:
                        actualizar();
                        break;
                    case 7:
                        eliminar();
                        break;
                    case 8:
                        miProductoDao.close();
                        break;
                    default:
                        System.out.println("Por favor, ingrese una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese un valor numérico.");
            }
        }
    }

    private void registrar() {
        try {
            Producto miProducto = new Producto();
            miProducto.setIdProducto(null); // ID será generado automáticamente por la base de datos
            miProducto.setNombreProducto(JOptionPane.showInputDialog("Ingrese el nombre del Producto"));
            miProducto.setPrecioProducto(Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del Producto")));

            // Registrar el producto
            boolean resultado = Boolean.parseBoolean(miProductoDao.registrarProducto(miProducto));
            if (resultado) {
                System.out.println("Producto registrado exitosamente.");
            } else {
                System.out.println("Hubo un error al registrar el producto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese un valor numérico válido para el precio.");
        } catch (Exception e) {
            System.out.println("Error al registrar el producto: " + e.getMessage());
        }
    }

    private void consultar() {
        try {
            Long idProducto = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del Producto"));

            Producto miProducto = miProductoDao.consultarProducto(idProducto);

            if (miProducto != null) {
                System.out.println(miProducto);
            } else {
                System.out.println("No se encontró el Producto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un valor numérico.");
        }
    }

    private void consultarLista() {
        System.out.println("Lista de Productos:");
        List<Producto> listaProductos = miProductoDao.consultarListaProductos();

        if (listaProductos != null && !listaProductos.isEmpty()) {
            for (Producto producto : listaProductos) {
                System.out.println(producto);
            }
        } else {
            System.out.println("No se encontraron registros de productos.");
        }
    }

    private void consultarPersonasPorProducto() {
        try {
            long productoId = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del Producto a consultar"));
            List<Persona> listaPersonas = miProductoDao.obtenerPersonasPorProducto(productoId);

            if (listaPersonas != null && !listaPersonas.isEmpty()) {
                System.out.println("Lista de personas que tienen el producto con ID " + productoId + ":");
                for (Persona persona : listaPersonas) {
                    System.out.println(persona);
                }
            } else {
                System.out.println("No se encontraron personas asociadas a este producto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un valor numérico.");
        }
    }

    private void comprarProductos() {
        Long personaId;
        Long productoId;
        int opc = 0;

        do {
            try {
                personaId = Long.parseLong(JOptionPane.showInputDialog("Ingrese el documento de la persona"));
                productoId = Long.parseLong(JOptionPane.showInputDialog("Ingrese el código del producto"));

                boolean resultado = Boolean.parseBoolean(miProductoDao.registrarCompra(personaId, productoId));
                if (resultado) {
                    System.out.println("Compra registrada exitosamente.");
                } else {
                    System.out.println("Hubo un error al registrar la compra.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese valores numéricos válidos.");
            } catch (Exception e) {
                System.out.println("Error al registrar la compra: " + e.getMessage());
            }

            try {
                opc = Integer.parseInt(JOptionPane.showInputDialog("Ingrese 1 si desea agregar otro producto, 0 para salir"));
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese un valor numérico.");
                opc = 0; // Salir del bucle si hay error
            }
        } while (opc == 1);
    }

    private void actualizar() {
        try {
            Long idProducto = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del Producto para actualizar"));
            Producto miProducto = miProductoDao.consultarProducto(idProducto);

            if (miProducto != null) {
                miProducto.setNombreProducto(JOptionPane.showInputDialog("Ingrese el nuevo nombre del Producto"));
                miProducto.setPrecioProducto(Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo precio del Producto")));

                boolean resultado = Boolean.parseBoolean(miProductoDao.actualizarProducto(miProducto));
                if (resultado) {
                    System.out.println("Producto actualizado exitosamente.");
                } else {
                    System.out.println("Hubo un error al actualizar el producto.");
                }
            } else {
                System.out.println("No se encontró el Producto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void eliminar() {
        try {
            Long idProducto = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del Producto para eliminar"));
            Producto miProducto = miProductoDao.consultarProducto(idProducto);

            if (miProducto != null) {
                boolean resultado = Boolean.parseBoolean(miProductoDao.eliminarProducto(miProducto));
                if (resultado) {
                    System.out.println("Producto eliminado exitosamente.");
                } else {
                    System.out.println("Hubo un error al eliminar el producto.");
                }
            } else {
                System.out.println("No se encontró el Producto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un valor numérico.");
        }
    }
}
