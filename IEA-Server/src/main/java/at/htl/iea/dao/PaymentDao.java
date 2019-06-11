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

    public List<Payment> getAllEvaluatedPayments() {
        return em.createNamedQuery("Payments.findAllEvaluated", Payment.class).getResultList();
    }

    public Payment getPaymentById(Long id) {
        return em.find(Payment.class, id);
    }

    public void updatePayment(Payment payment) {
        em.merge(payment);
    }

    public void commitPayments(List<Payment> payments) {
        em.createQuery("DELETE FROM Payment p where p.evaluated = false");

        payments.forEach(item -> em.persist(item));
        flush();
    }

    public void flush() {
        em.flush();
    }
}
