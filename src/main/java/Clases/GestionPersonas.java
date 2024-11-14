package Clases;

import dao.PersonaDao;
import entidades.Mascota;
import entidades.Nacimiento;
import entidades.Persona;
import entidades.Producto;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class GestionPersonas {

    PersonaDao miPersonaDao =new PersonaDao();

    public GestionPersonas() {
        String menu =" MENU DE OPCIONES - GESTION PERSONAS\n\n";
        menu+="1. Registrar Persona\n";
        menu+="2. Consultar Persona \n";
        menu+="3. Consultar lista de Personas\n";
        menu+="4. Consultar productos por Personas\n";
        menu+="5. Actualizar Persona\n";
        menu+="6. Eliminar Persona\n";
        menu+="7. Salir\n";

        int opc=0;

        while(opc!=7) {
            opc=Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opc) {
                case 1: registrar(); break;
                case 2: consultar(); break;
                case 3: consultarLista();break;
                case 4:	consultarProductosPersona(); break;
                case 5: actualizarNombre();	break;
                case 6: eliminar(); 	break;
                case 7:	miPersonaDao.close(); break;
            }
        }
    }

    private void consultarProductosPersona() {
        long id=Long.parseLong(JOptionPane.showInputDialog("Ingrese el id de la persona a consultar"));
        List<Producto> listaProductos = miPersonaDao.obtenerProductosPorPersona(id);

        System.out.println("Lista de productos persona "+id);
        for (Producto producto : listaProductos) {
            System.out.println(producto);
        }

    }

    private void registrar() {
        try {
            Persona miPersona = new Persona();

            // Obtener datos básicos de la persona
            miPersona.setIdPersona(Long.parseLong(JOptionPane.
                    showInputDialog("Ingrese el documento de la persona")));
            miPersona.setNombre(JOptionPane.showInputDialog("Ingrese el nombre de la Persona"));
            miPersona.setTelefono(JOptionPane.showInputDialog("Ingrese el telefono de la Persona"));
            miPersona.setProfesion(JOptionPane.showInputDialog("Ingrese la profesion de la Persona"));
            miPersona.setTipo(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tipo")));

            // Obtener datos de nacimiento
            miPersona.setNacimiento(obtenerDatosNacimiento());
            int opc = 0;
            // Opción para agregar mascotas

            do {
                opc = Integer.parseInt(JOptionPane.showInputDialog("Desea Registrar una Mascota?"
                        + "\n1. SI\n2.No"));

                if (opc == 1) {
                    // Agregar la mascota a la lista
                    miPersona.getListaMascotas().add(obtenerDatosMascota(miPersona));
                }
            } while (opc != 2);

            // Registrar la persona, independientemente de si tiene mascotas o no
            boolean resultado = Boolean.parseBoolean(miPersonaDao.registrarPersona(miPersona));

            // Mostrar un mensaje indicando si la operación fue exitosa o no
            if (resultado) {
                System.out.println("Persona registrada exitosamente.");
            } else {
                System.out.println("Hubo un error al registrar la persona.");
            }

            System.out.println(miPersona);
            System.out.println();

        } finally {

        }
    }
    private Mascota obtenerDatosMascota(Persona miPersona) {
        Mascota miMascota=new Mascota();
        miMascota.setIdMascota(null);
        miMascota.setNombre(JOptionPane.showInputDialog("Ingrese el nombre de la mascota"));
        miMascota.setRaza(JOptionPane.showInputDialog("Ingrese la raza de la mascota"));
        miMascota.setColorMascota(JOptionPane.showInputDialog("Ingrese el color de la mascota"));
        miMascota.setSexo(JOptionPane.showInputDialog("Ingrese el sexo de su mascota"));
        miMascota.setDuenio(miPersona);//Agregamos el dueño definido en la relación

        return miMascota;

    }

    private Nacimiento obtenerDatosNacimiento() {
        int dia=Integer.parseInt(JOptionPane.
                showInputDialog("Ingrese el DIA de Nacimiento"));
        int mes=Integer.parseInt(JOptionPane.
                showInputDialog("Ingrese el MES de Nacimiento"));
        int anio=Integer.parseInt(JOptionPane.
                showInputDialog("Ingrese el Anio de Nacimiento"));
        Nacimiento datosNacimiento=new Nacimiento();
        datosNacimiento.setIdNacimiento(null);
        datosNacimiento.setFechaNacimiento(LocalDate.of(anio, mes, dia));
        datosNacimiento.setCiudadNacimiento(JOptionPane.
                showInputDialog("Ingrese la ciudad de Nacimiento"));
        datosNacimiento.setDepartamentoNacimiento(JOptionPane.
                showInputDialog("Ingrese el departamento de Nacimiento"));
        datosNacimiento.setPaisNacimiento(JOptionPane.
                showInputDialog("Ingrese el pais de Nacimiento"));

        return datosNacimiento;
    }

    private void consultar() {
        Long idPersona = Long.parseLong(JOptionPane.
                showInputDialog("Ingrese el id de la Persona"));

        Persona miPersona = miPersonaDao.consultarPersona(idPersona);

        if (miPersona != null) {
            System.out.println(miPersona);
            System.out.println();
        } else {
            System.out.println();
            System.out.println("No se encontró la mascota");
        }
        System.out.println();
    }

    private void actualizarNombre() {
        try {
            Long idPersona = Long.parseLong(JOptionPane.
                    showInputDialog("Ingrese el id de la Persona para actualizar su nombre"));
            Persona miPersona = miPersonaDao.consultarPersona(idPersona);

            if (miPersona != null) {
                System.out.println(miPersona);
                System.out.println();

                // Actualizar el nombre de la persona
                miPersona.setNombre(JOptionPane.showInputDialog("Ingrese el nuevo nombre de la Persona"));

                // Opción para agregar una mascota
                int opc = 0;
                do {
                    opc = Integer.parseInt(JOptionPane.showInputDialog("Desea Registrar una Mascota?\n1. SI\n2.No"));

                    if (opc == 1) {
                        miPersona.getListaMascotas().add(obtenerDatosMascota(miPersona)); // Agregar la mascota a la lista
                    }

                } while (opc != 2);

                // Actualizar persona en la base de datos
                String resultado = miPersonaDao.actualizarPersona(miPersona);
                System.out.println(resultado);
            } else {
                System.out.println("No se encontró la Persona");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, ingrese un valor numérico válido.");
        } catch (Exception e) {
            System.out.println("Error al actualizar la persona: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private void consultarLista() {

            List<Persona> listaPersonas = miPersonaDao.consultarListaPersonas();

            // Verificar si la lista no está vacía
            if (listaPersonas != null && !listaPersonas.isEmpty()) {
                StringBuilder personasInfo = new StringBuilder("Lista de Personas:\n");

                // Recorrer la lista de personas y añadir información al StringBuilder
                for (Persona persona : listaPersonas) {
                    personasInfo.append(persona).append("\n");
                }

                // Mostrar la lista de personas por consola
                System.out.println(personasInfo.toString());
            } else {
                // Si no hay personas registradas, mostrar un mensaje por consola
                System.out.println("No se encontraron registros de personas.");
            }
            System.out.println();
        }

    private void eliminar() {
        Long idPersona = Long.parseLong(JOptionPane.
                showInputDialog("Ingrese el id de la Persona para eliminar"));
        Persona miPersona = miPersonaDao.consultarPersona(idPersona);

        if (miPersona != null) {
            System.out.println(miPersona);
            System.out.println();

            System.out.println(miPersonaDao.eliminarPersona(miPersona));
            System.out.println();
        } else {
            System.out.println();
            System.out.println("No se encontró la Persona");
        }
        System.out.println();
    }
}



