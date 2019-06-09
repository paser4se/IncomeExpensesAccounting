package at.htl.iea.dao;

import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CategoryDao {
    @PersistenceContext
    EntityManager em;

    public Category getCategoryById(Long id) {
        return em.find(Category.class, id);
    }

    public List<Category> getSortedCategories() {
        return em.createNamedQuery("Category.getSortedCategories", Category.class).getResultList();
    }

    public Assignment getAssignmentByCategory(Long id) {
        return em.createNamedQuery("Assignment.getByCat", Assignment.class).setParameter(1, id).getSingleResult();
    }

    public void saveCategory(Category category) {
        em.persist(category);
    }

    public void saveAssignment(Assignment assignment) {
        em.persist(assignment);
    }

    public void flush() {
        em.flush();
    }
}
