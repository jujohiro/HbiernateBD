package aplicacion;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final String UNIDAD_DE_PERSISTENCIA="PruebaHibernate";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory==null) {
            factory=Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA);
        }
        return factory;
    }

    public static void shutdown() {
        if (factory!=null) {
            factory.close();
            factory=null;//se asigna null para que se pueda instanciar nuevamente
        }
    }
}
