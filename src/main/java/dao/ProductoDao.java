package dao;

import aplicacion.JPAUtil;
import entidades.Persona;
import entidades.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    EntityManager entityManager= JPAUtil.getEntityManagerFactory().createEntityManager();

    public String registrarProducto(Producto miProducto) {
        String resp="";
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(miProducto);
            entityManager.getTransaction().commit();

            resp="Producto Registrado!";
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se puede registrar "
                            + "el Producto",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public Producto consultarProducto(Long idProducto) {

        Producto miProducto=entityManager.find(Producto.class,idProducto);

        if (miProducto!=null) {
            return miProducto;
        }else {
            return null;
        }

    }

    public List<Producto> consultarListaProductos() {

        List<Producto> listaProductos=new ArrayList<Producto>();
        Query query=entityManager.createQuery("SELECT p FROM Producto p");
        listaProductos=query.getResultList();

        return listaProductos;
    }

    public String actualizarProducto(Producto miProducto) {
        String resp="";
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(miProducto);
            entityManager.getTransaction().commit();

            resp="Producto Actualizado!";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se puede actualizar "
                            + "el Producto ",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String eliminarProducto(Producto miProducto) {

        entityManager.getTransaction().begin();
        entityManager.remove(miProducto);
        entityManager.getTransaction().commit();

        String resp="Producto Actualizado!";

        return resp;
    }

    public void close() {
        entityManager.close();
        JPAUtil.shutdown();
    }

    public String registrarCompra(Long personaId, Long productoId) {
        String resp = "";
        try {
            entityManager.getTransaction().begin();

            Persona persona = entityManager.find(Persona.class, personaId);
            Producto producto = entityManager.find(Producto.class, productoId);

            if (persona == null || producto == null) {
                throw new Exception("Persona o producto no encontrados.");
            }

            persona.getListaProductos().add(producto);
            entityManager.merge(persona);

            entityManager.getTransaction().commit();
            resp = "Se realizó la compra del producto!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(null, "No se puede registrar la compra del Producto", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return resp;
    }
    public List<Persona> obtenerPersonasPorProducto(Long productoId) {
        String jpql = "SELECT p FROM Persona p JOIN p.listaProductos prod WHERE prod.id = :productoId";

        // Creamos una lista y asignamos el resultado de la consulta
        List<Persona> listaPersonas = entityManager.createQuery(jpql, Persona.class)
                .setParameter("productoId", productoId)
                .getResultList();
        // Retornamos la lista creada
        return listaPersonas;
    }
}

