package at.htl.iea.dao;

import at.htl.iea.model.Payment;
import at.htl.iea.model.TempPayment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class TempPaymentDao implements PanacheRepository<TempPayment> {
    @PersistenceContext
    EntityManager em;

    public void savePayment(TempPayment payment) {
        if (payment.getId() != null) {
            em.merge(payment);
        } else {
            em.persist(payment);
        }
    }

    public void deletePayment(TempPayment payment) {
        em.createQuery("DELETE FROM TempPayment p where p.id = "+payment.getId()).executeUpdate();
        flush();
    }

    public void merge(TempPayment payment) {
        em.merge(payment);
    }
}
