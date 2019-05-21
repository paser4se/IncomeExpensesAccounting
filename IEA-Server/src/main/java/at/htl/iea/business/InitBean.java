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
        Category gehalt = new Category("Gehalt");
        Category essen = new Category("Essen");
        Category strom = new Category("Strom");
        Category bank = new Category("Bank");
        Category auto = new Category("Auto");
        Category versicherung = new Category("Versicherung");
        Category tank = new Category("Tank");
        Category kleidung = new Category("Kleidung");
        Category sonstiges = new Category("Sonstiges");
        Category amazon = new Category("Amazon");
        Category bildung = new Category("Bildung");
        Category freizeit = new Category("Freizeit");

        auto.addSubcategory(versicherung);
        auto.addSubcategory(tank);
        wohnen.addSubcategory(strom);
        wohnen.addSubcategory(essen);

        em.persist(strom);
        em.persist(gehalt);
        em.persist(essen);
        em.persist(wohnen);
        em.persist(bank);
        em.persist(versicherung);
        em.persist(tank);
        em.persist(auto);
        em.persist(kleidung);
        em.persist(sonstiges);
        em.persist(amazon);
        em.persist(bildung);
        em.persist(freizeit);

        Assignment essenAssignment = new Assignment(essen);
        essenAssignment.addKeyword("Billa");
        //essenAssignment.addKeyword("Spar"); //keyword nicht optimal -> check
        essenAssignment.addKeyword("Hofer");
        essenAssignment.addKeyword("EUROSPAR");
        essenAssignment.addKeyword("INTERSPAR");
        essenAssignment.addKeyword("Penny");
        essenAssignment.addKeyword("Unimarkt");
        essenAssignment.addKeyword("Merkur");
        essenAssignment.addKeyword("Lieferservice");
        essenAssignment.addKeyword("mjam");
        essenAssignment.addKeyword("Pizza");

        Assignment freizeitAssignment = new Assignment(freizeit);
        freizeitAssignment.addKeyword("CTS Eventim Austria GmbH");
        freizeitAssignment.addKeyword("Kino");
        freizeitAssignment.addKeyword("Cineplexx");
        freizeitAssignment.addKeyword("FITINN");
        freizeitAssignment.addKeyword("HIGH5");
        freizeitAssignment.addKeyword("JOHN HARRIS");
        freizeitAssignment.addKeyword("Fitness");
        freizeitAssignment.addKeyword("clever fit");
        freizeitAssignment.addKeyword("gym");
        freizeitAssignment.addKeyword("MEDIA MARKT");

        Assignment gehaltAssignment = new Assignment(gehalt);
        gehaltAssignment.addKeyword("Lohn");
        gehaltAssignment.addKeyword("Gehalt");

        Assignment tankAssignment = new Assignment(tank);
        tankAssignment.addKeyword("Tanke ");
        tankAssignment.addKeyword("Avanti");
        tankAssignment.addKeyword("Shell");
        tankAssignment.addKeyword("BP ");
        tankAssignment.addKeyword("Avira");
        tankAssignment.addKeyword("Tankstelle");

        Assignment wohnenAssignment = new Assignment(wohnen);
        wohnenAssignment.addKeyword("IKEA");
        wohnenAssignment.addKeyword("Möbelix ");
        wohnenAssignment.addKeyword("KIKA ");
        wohnenAssignment.addKeyword("Leiner ");
        wohnenAssignment.addKeyword("XXXLutz");
        wohnenAssignment.addKeyword("Obi ");
        wohnenAssignment.addKeyword("Bauhaus");
        wohnenAssignment.addKeyword("Miete ");

        Assignment stromAssignment = new Assignment(strom);
        stromAssignment.addKeyword("Strom");
        stromAssignment.addKeyword("Licht");

        Assignment bankAssignment = new Assignment(bank);
        bankAssignment.addKeyword("AUTOMAT ");
        bankAssignment.addKeyword("SB-Eigenerlag");
        bankAssignment.addKeyword("SB-Auszahlung");
        bankAssignment.addKeyword("SB-Behebung");
        bankAssignment.addKeyword("Bank ");
        bankAssignment.addKeyword("Raiffeisen");
        bankAssignment.addKeyword("Sparkasse");

        Assignment autoAssignment = new Assignment(auto);
        autoAssignment.addKeyword("Reifen");
        autoAssignment.addKeyword("Leasing");

        Assignment versicherungAssignment = new Assignment(versicherung);
        versicherungAssignment.addKeyword("Versicherung");

        Assignment kleidungAssignment = new Assignment(kleidung);
        kleidungAssignment.addKeyword("Zalando");
        kleidungAssignment.addKeyword("Kleidung");
        kleidungAssignment.addKeyword("Asos");
        kleidungAssignment.addKeyword("ESPRIT");
        kleidungAssignment.addKeyword("NORTHLAND OUTDOOR");
        kleidungAssignment.addKeyword("Zara");


        Assignment amazonAssignment = new Assignment(amazon);
        amazonAssignment.addKeyword("Amazon");

        Assignment bildungAssignment = new Assignment(bildung);
        bildungAssignment.addKeyword("Thalia");
        bildungAssignment.addKeyword("Udemy");

        Assignment sonstigesAssignment = new Assignment(sonstiges);

        em.persist(essenAssignment);
        em.persist(gehaltAssignment);
        em.persist(tankAssignment);
        em.persist(wohnenAssignment);
        em.persist(stromAssignment);
        em.persist(bankAssignment);
        em.persist(autoAssignment);
        em.persist(versicherungAssignment);
        em.persist(kleidungAssignment);
        em.persist(amazonAssignment);
        em.persist(bildungAssignment);
        em.persist(sonstigesAssignment);
        em.persist(freizeitAssignment);
    }
}
