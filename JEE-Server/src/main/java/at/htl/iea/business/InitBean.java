package at.htl.iea.business;

import at.htl.iea.model.Assignment;
import at.htl.iea.model.Category;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
public class InitBean {

    @PersistenceContext
    EntityManager em;

    public InitBean() {

    }

    @PostConstruct
    private void init() {
        System.out.println("********** INIT **********");

        Category wohnen = new Category("Wohnen");
        Category essen = new Category("Essen");
        Category strom = new Category("Strom");
        Category bank = new Category("Bank");
        Category auto = new Category("Auto");
        Category versicherung = new Category("Versicherung");
        Category tank = new Category("Tank");
        Category sonstiges = new Category("Sonstiges");
        auto.addSubcategory(versicherung);
        auto.addSubcategory(tank);

        wohnen.addSubcategory(strom);
        wohnen.addSubcategory(essen);

        em.persist(strom);
        em.persist(essen);
        em.persist(wohnen);
        em.persist(bank);
        em.persist(versicherung);
        em.persist(tank);
        em.persist(auto);
        em.persist(sonstiges);

        Assignment essenAssignment = new Assignment(essen);
        essenAssignment.addKeyword("Billa");
        essenAssignment.addKeyword("Spar");
        essenAssignment.addKeyword("Hofer");

        Assignment tankAssignment = new Assignment(tank);
        tankAssignment.addKeyword("Avanti");
        tankAssignment.addKeyword("Shell");

        Assignment wohnenAssignment = new Assignment(wohnen);
        wohnenAssignment.addKeyword("TestW");

        Assignment assignment1 = new Assignment(strom);
        Assignment assignment2 = new Assignment(bank);
        Assignment assignment3 = new Assignment(auto);
        Assignment assignment4 = new Assignment(versicherung);
        Assignment assignment5 = new Assignment(sonstiges);

        em.persist(essenAssignment);
        em.persist(tankAssignment);
        em.persist(wohnenAssignment);
        em.persist(assignment1);
        em.persist(assignment2);
        em.persist(assignment3);
        em.persist(assignment4);
        em.persist(assignment5);
    }
}
