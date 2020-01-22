package at.htl.iea.dao;

import at.htl.iea.model.Account;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class AccountDao implements PanacheRepository<Account> {
    @PersistenceContext
    EntityManager em;

    public Account getAccountByUsername(String username) {
        return em.createNamedQuery("Account.getAccountByUsername", Account.class).setParameter(1, username).getSingleResult();
    }

    public Account getAccountByEmail(String email){
        return em.createNamedQuery("Account.getAccountByEmail", Account.class).setParameter(1, email)
                .getSingleResult();
    }

    public Account getAccountById(Long id) {
        return em.find(Account.class, id);
    }

    public void merge(Account account) {
        em.merge(account);
        em.flush();
    }

}
