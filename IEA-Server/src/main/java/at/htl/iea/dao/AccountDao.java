package at.htl.iea.dao;

import at.htl.iea.model.Account;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AccountDao {
    @PersistenceContext
    EntityManager em;

    public Account getAccountByUsername(String username) {
        return em.createNamedQuery("Account.getAccountByUsername", Account.class).setParameter(1, username)
                .getSingleResult();
    }

    public Account getAccountByEmail(String email){
        return em.createNamedQuery("Account.getAccountByEmail", Account.class).setParameter(1, email)
                .getSingleResult();
    }

    public void persist(Account account) {
        em.persist(account);
        em.flush();
    }

}
