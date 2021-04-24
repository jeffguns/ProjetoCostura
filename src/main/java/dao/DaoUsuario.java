package dao;

import costurautil.CosturaUtil;
import entidades.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

import static costurautil.CosturaUtil.getEntityManager;

public class DaoUsuario<E> extends DaoGeneric<Pessoa> {


    public Pessoa consultarUsuario(String login, String senha) {

        Pessoa pessoa = null;

        EntityManager entityManager = CosturaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        pessoa = (Pessoa) entityManager
                .createQuery("select p from Pessoa p where p.login = '" + login + "'and p.senha = '" + senha + "'")
                .getSingleResult();

        entityTransaction.commit();
        entityManager.close();

        return pessoa;
    }


    public void removerUsuario(Pessoa pessoa) throws Exception {
        getEntityManager().getTransaction().begin();

        getEntityManager().remove(pessoa);

        getEntityManager().getTransaction().commit();

    }

    public List<Pessoa> pesquisar(String campoPesquisa) {

        Query query = getEntityManager()
                .createQuery("from Pessoa where nome like '%" + campoPesquisa + "%'");

        return query.getResultList();
    }

}
