package at.htl.iea.dao;

import at.htl.iea.model.Assignment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class AssignmentDao implements PanacheRepository<Assignment> {
    @PersistenceContext
    EntityManager em;

    public Assignment getByCategory(Long id) {
        return em.createNamedQuery("Assignment.getByCat", Assignment.class).setParameter(1, id).getSingleResult();
    }

    public void merge(Assignment assignment) {
        em.merge(assignment);
    }
}
