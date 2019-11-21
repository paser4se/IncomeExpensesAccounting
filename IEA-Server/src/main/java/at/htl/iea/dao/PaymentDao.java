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

    public List<Payment> getAllPayments(){
        return em.createNamedQuery("Payments.findAll", Payment.class).getResultList();
    }

    public Payment getPaymentById(Long id) {
        return em.find(Payment.class, id);
    }

    public void updatePayment(Payment payment) {
        em.merge(payment);
    }

    public void deletePayments() {
        em.createQuery("DELETE FROM Payment p where p.evaluated = false");

        flush();
    }

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

    public void flush() {
        em.flush();
    }
}
