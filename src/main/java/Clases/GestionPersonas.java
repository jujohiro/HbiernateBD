package Clases;

import dao.PersonaDao;
import entidades.Nacimiento;
import entidades.Persona;

import javax.swing.*;
import java.time.LocalDate;

public class GestionPersonas {

    PersonaDao miPersonaDao =new PersonaDao();

    public GestionPersonas() {
        String menu =" MENU DE OPCIONES - GESTION PERSONAS\n\n";
        menu+="1. Registrar Persona\n";
        menu+="2. Consultar Persona \n";
        menu+="3. Consultar lista de Personas\n";
        menu+="4. Actualizar Persona\n";
        menu+="5. Eliminar Persona\n";
        menu+="6. Salir\n";

        int opc=0;

        while (opc!=6) {
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
                    actualizarNombre();
                    break;
                case 5:
                    eliminar();
                    break;
                case 6:
                    miPersonaDao.close();
                    break;
            }
        }
            }

    private void registrar() {
        Persona miPersona =new Persona();
        miPersona.setIdPersona(Long.parseLong(JOptionPane.
                showInputDialog("Ingrese el documento de la persona")));
        miPersona.setNombre(JOptionPane.showInputDialog("Ingrese el nombre de la Persona"));
        miPersona.setTelefono(JOptionPane.showInputDialog("Ingrese el telefono de la Persona"));
        miPersona.setProfesion(JOptionPane.showInputDialog("Ingrese la profesion de la Persona"));
        miPersona.setTipo(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tipo")));

        miPersona.setNacimiento(obtenerDatosNacimiento());

        System.out.println(miPersonaDao.registrarPersona(miPersona));
        System.out.println(miPersona);
        System.out.println();

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
        Long idPersona = Long.parseLong(JOptionPane.
                showInputDialog("Ingrese el id de la Persona para"
                        + " actualizar su nombre"));
        Persona miPersona = miPersonaDao.consultarPersona(idPersona);

        if (miPersona != null) {
            System.out.println(miPersona);
            System.out.println();
            miPersona.setNombre(JOptionPane.
                    showInputDialog("Ingrese el nombre de la Persona"));

            System.out.println(miPersonaDao.actualizarPersona(miPersona));
            System.out.println();
        } else {
            System.out.println();
            System.out.println("No se encontró la Persona");
        }
        System.out.println();
    }

    private void consultarLista() {
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



