package at.htl.iea.dao;

import at.htl.iea.model.Payment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PaymentDao {
    @PersistenceContext
    EntityManager em;

    public List<Payment> getAllUnevaluatedPayments() {
        return em.createNamedQuery("Payments.findAllUnevaluated", Payment.class).getResultList();
    }

    public Payment getPaymentById(Long id) {
        return em.find(Payment.class, id);
    }

    public void flush() {
        em.flush();
    }
}
