package costurautil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author jeff_
 */
public class CosturaUtil {
    private static EntityManagerFactory factory = null;

    static {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("oficina-costura");
        }
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static Object getPrimaryKey(Object entity) {
        return factory.getPersistenceUnitUtil().getIdentifier(entity);
    }
}
