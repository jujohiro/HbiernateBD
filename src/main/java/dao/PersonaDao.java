package dao;

import aplicacion.JPAUtil;
import entidades.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDao {

    EntityManager entityManager= JPAUtil.getEntityManagerFactory().createEntityManager();

    public String registrarPersona(Persona miPersona) {

        entityManager.getTransaction().begin();
        entityManager.persist(miPersona);
        entityManager.getTransaction().commit();

        String resp="Persona Registrada!";

        return resp;
    }

    public Persona consultarPersona(Long idPersona) {

        Persona miPersona=entityManager.find(Persona.class,idPersona);

        if (miPersona!=null) {
            return miPersona;
        }else {
            return null;
        }

    }

    public List<Persona> consultarListaPersonas() {

        List<Persona> listaPersonas=new ArrayList<Persona>();
        Query query=entityManager.createQuery("SELECT p FROM Persona p");
        listaPersonas=query.getResultList();

        return listaPersonas;
    }

    public String actualizarPersona(Persona miPersona) {

        entityManager.getTransaction().begin();
        entityManager.merge(miPersona);
        entityManager.getTransaction().commit();

        String resp="Persona Actualizada!";

        return resp;
    }

    public String eliminarPersona(Persona miPersona) {
        String resp="";
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(miPersona);
            entityManager.getTransaction().commit();
            resp="Persona Eliminada!";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se puede eliminar la persona"
                            + " verifique qué no tenga registros pendientes",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void close() {
        entityManager.close();
        JPAUtil.shutdown();
    }
}