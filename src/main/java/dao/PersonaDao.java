package dao;

import aplicacion.JPAUtil;
import entidades.Persona;
import entidades.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonaDao {

    EntityManager entityManager= JPAUtil.getEntityManagerFactory().createEntityManager();

    public String registrarPersona(Persona miPersona) {
        String resp = "";
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(miPersona);
            entityManager.getTransaction().commit();
            resp = "Persona Registrada!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            resp = "Error al registrar la persona: " + e.getMessage();
            e.printStackTrace();
        }
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
        String resp = "";
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(miPersona);
            entityManager.getTransaction().commit();
            resp = "Persona Actualizada!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            resp = "Error al actualizar la persona: " + e.getMessage();
            e.printStackTrace();
        }
        return resp;
    }

    public List<Producto> obtenerProductosPorPersona(Long personaId) {
        Persona persona = entityManager.find(Persona.class, personaId);
        if (persona != null) {
            return persona.getListaProductos();
        }
        return Collections.emptyList();
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