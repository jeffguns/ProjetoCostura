package dao;

import costurautil.CosturaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * @author jeff_
 */
public class DaoGeneric<E> {

    public void salvar(E entidade) {
        EntityManager entityManager = CosturaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(entidade);

        entityTransaction.commit();
        entityManager.close();
    }

    public E merge(E entidade) {
        EntityManager entityManager = CosturaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        E retorno = entityManager.merge(entidade);

        entityTransaction.commit();
        entityManager.close();

        return retorno;
    }

    public void delete(E entidade) {
        EntityManager entityManager = CosturaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.remove(entidade);

        entityTransaction.commit();
        entityManager.close();

    }


    public void deletePorId(E entidade) {
        EntityManager entityManager = CosturaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Object id = CosturaUtil.getPrimaryKey(entidade);
        entityManager.createNativeQuery("delete from " + entidade.getClass().getSimpleName().toLowerCase() + " where id =" + id)
                .executeUpdate();

        entityTransaction.commit();
        entityManager.close();
    }

    public List<E> listar(Class<E> entidade) {
        EntityManager entityManager = CosturaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        List<E> list = entityManager.createQuery("from " + entidade.getName()).getResultList();

        entityTransaction.commit();
        entityManager.close();

        return list;
    }

}
