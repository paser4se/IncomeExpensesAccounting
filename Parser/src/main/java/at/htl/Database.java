package at.htl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    public static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db";
    public static final String USER = "app";
    public static final String PASSWORD = "app";
    private static Connection conn;

    public static void initJdbc(){
        //verbindung zu db herstellen
        try {
            Class.forName(DRIVER_STRING);
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Verbindung zur Datenbank nicht möglich\n" + e.getMessage() + "\n");
            System.exit(1);
        }

        //erstellen der tabelle vehicle
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE zahlung ("
                    + "id INT CONSTRAINT zahlung_pk PRIMARY KEY"
                    + " GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "buchungsdatum VARCHAR(255) NOT NULL,"
                    + "partner_name VARCHAR(255) NOT NULL,"
                    + "partner_iban VARCHAR(255) NOT NULL,"
                    + "partner_bic VARCHAR(255) NOT NULL,"
                    + "partner_kontonummer VARCHAR(255) NOT NULL,"
                    + "partner_bankcode VARCHAR(255) NOT NULL,"
                    + "betrag VARCHAR(255) NOT NULL,"
                    + "währung VARCHAR(255) NOT NULL,"
                    + "buchungstext VARCHAR(255) NOT NULL,"
                    + "ersterfassungsreferenz VARCHAR(255) NOT NULL,"
                    + "notiz VARCHAR(255) NOT NULL,"
                    + "valutadatum VARCHAR(255) NOT NULL"
                    + ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabelle zahlung" + e.getMessage());
        }
    }

    public static void insertIntoDatabase(Zahlung z){
        try {
            Statement stat = conn.createStatement();
            String sql = "INSERT INTO zahlung " +
                    "(buchungsdatum, partner_name, partner_iban, partner_bic, partner_kontonummer, partner_bankcode, " +
                    "betrag, währung, buchungstext, ersterfassungsreferenz, notiz, valutadatum) " +
                    "VALUES ("+z.getBuchungsdatum()+z.getPartner_name()+z.getPartner_iban()+z.getPartner_bic()
                        +z.getPartner_kontonummer()+z.getPartner_bankcode()+z.getBetrag()+z.getWährung()
                        +z.getBuchungstext()+z.getErsterfassungsreferenz()+z.getNotiz()+z.getValutadatum()+")";
            stat.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Fehler beim Einfügen in die Tabelle zahlung");
        }
    }

    public static void teardownJdbc() {
        // tabelle zahlung löschen
        try {
            conn.createStatement().execute("DROP TABLE zahlung");
            System.out.println("Tabelle zahlung gelöscht");
        } catch (SQLException e) {
            System.out.println("Tabelle zahlung konnte nicht gelöscht werden:\n" + e.getMessage());
        }

        // connection schließen
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Auf Wiedersehen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
