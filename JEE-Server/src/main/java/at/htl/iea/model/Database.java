package at.htl.iea.model;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
                    + "id INT PRIMARY KEY"
                    + " GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "buchungsdatum TIMESTAMP NOT NULL,"
                    + "partner_name VARCHAR(255) NOT NULL,"
                    + "partner_iban VARCHAR(255) NOT NULL,"
                    + "partner_bic VARCHAR(255) NOT NULL,"
                    + "partner_kontonummer VARCHAR(255) NOT NULL,"
                    + "partner_bankcode VARCHAR(255) NOT NULL,"
                    + "betrag FLOAT(2) NOT NULL,"
                    + "waehrung VARCHAR(255) NOT NULL,"
                    + "buchungstext VARCHAR(500) NOT NULL,"
                    + "ersterfassungsreferenz VARCHAR(255) NOT NULL,"
                    + "notiz VARCHAR(255) NOT NULL,"
                    + "valutadatum TIMESTAMP NOT NULL"
                    + ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabelle zahlung" + e.getMessage());
        }
    }

    public static void insertIntoDatabase(Zahlung z){
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO zahlung " +
                    "(buchungsdatum, partner_name, partner_iban, partner_bic, partner_kontonummer, partner_bankcode, " +
                    "betrag, waehrung, buchungstext, ersterfassungsreferenz, notiz, valutadatum) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

            pstmt.setTimestamp(1, Timestamp.valueOf(dateFormat.format(z.getBuchungsdatum())));
            pstmt.setString(2, z.getPartner_name());
            pstmt.setString(3, z.getPartner_iban());
            pstmt.setString(4, z.getPartner_bic());
            pstmt.setString(5, z.getPartner_kontonummer());
            pstmt.setString(6, z.getPartner_bankcode());
            System.out.println(z.getBetrag());
            pstmt.setDouble(7, Double.parseDouble(z.getBetrag()));
            pstmt.setString(8, z.getWährung());
            pstmt.setString(9, z.getBuchungstext());
            pstmt.setString(10, z.getErsterfassungsreferenz());
            pstmt.setString(11, z.getNotiz());
            pstmt.setTimestamp(12, Timestamp.valueOf(dateFormat.format(z.getValutadatum())));

            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("Fehler beim Einfügen in die Tabelle zahlung" + e.getMessage());
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
