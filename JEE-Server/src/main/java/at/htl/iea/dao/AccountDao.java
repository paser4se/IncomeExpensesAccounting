package at.htl.iea.dao;

import at.htl.iea.model.Account;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
//@Singleton
public class AccountDao {
    @PersistenceContext
    EntityManager em;

    public Account getAccountByUsername(String username){
        return em.createNamedQuery("Account.getAccountByUsername", Account.class).setParameter(1, username).getSingleResult();
    }

}
