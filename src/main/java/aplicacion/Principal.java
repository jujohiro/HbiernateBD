package aplicacion;

import Clases.GestionPersonas;
import Clases.GestionMascotas;

import javax.swing.*;

public class Principal {

    public static void main (String [] args){
        String menu="MENU DE OPCIONES\n\n";
        menu+="1. Gestionar Personas\n";
        menu+="2. Gestionar Mascotas\n";
        menu+="3. Salir\n\n";

        int opc=0;

        while (opc!=3){
            opc=Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opc){
                case 1: new GestionPersonas(); break;
                case 2: new GestionMascotas(); break;
            }
        }
    }

}
