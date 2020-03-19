package at.htl.iea.dao;

import at.htl.iea.model.Payment;
import at.htl.iea.model.TempPayment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class PaymentDao implements PanacheRepository<Payment> {
    @PersistenceContext
    EntityManager em;

    public void savePayment(Payment payment) {
        if (payment.getId() != null) {
            em.merge(payment);
        } else {
            em.persist(payment);
        }
    }

    public void deletePayment(Payment payment) {
        em.createQuery("DELETE FROM Payment p where p.id = "+payment.getId()).executeUpdate();
        flush();
    }

    public void merge(Payment payment) {
        em.merge(payment);
    }

}
