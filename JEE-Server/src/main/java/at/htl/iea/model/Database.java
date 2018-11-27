package at.htl.iea.model;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
                    + "bookingDate TIMESTAMP NOT NULL,"
                    + "partnerName VARCHAR(255) NOT NULL,"
                    + "partnerIban VARCHAR(255) NOT NULL,"
                    + "partnerBic VARCHAR(255) NOT NULL,"
                    + "partnerAccountNumber VARCHAR(255) NOT NULL,"
                    + "partnerBankCode VARCHAR(255) NOT NULL,"
                    + "amount FLOAT(2) NOT NULL,"
                    + "currency VARCHAR(255) NOT NULL,"
                    + "bookingText VARCHAR(500) NOT NULL,"
                    + "initialRecognitionReference VARCHAR(255) NOT NULL,"
                    + "note VARCHAR(255) NOT NULL,"
                    + "valueDate TIMESTAMP NOT NULL"
                    + ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabelle zahlung" + e.getMessage());
        }
    }

    public static List<Zahlung> getAllPayments(){
        ResultSet allPayments = null;
        List<Zahlung> zahlungen = new ArrayList<>();
        try {
            Statement stat = conn.createStatement();
            String sql = "select bookingDate, amount, currency, bookingText, valueDate from zahlung";
            // nur die essenziellen infos werden zurückgeschickt
            allPayments = stat.executeQuery(sql);
            while (allPayments.next()){
                Date bookingDate = allPayments.getDate("bookingDate"); //timestamp statt date wenn error kommt
                Float amount = allPayments.getFloat("amount");
                String currency = allPayments.getString("currency");
                String bookingText = allPayments.getString("bookingText");
                Date valueDate = allPayments.getDate("valueDate"); //timestamp statt date wenn error kommt
                zahlungen.add(new Zahlung(bookingDate, amount.toString(), currency, bookingText, valueDate));
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Senden der essenziellen Informationen der Zahlungen (getAllPayments in Database.java):\n" + e.getMessage());
        }
        return zahlungen;
    }

    public static void insertIntoDatabase(Zahlung z){
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO zahlung " +
                    "(bookingDate, partnerName, partnerIban, partnerBic, partnerAccountNumber, partnerBankCode, " +
                    "amount, currency, bookingText, initialRecognitionReference, note, valueDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

            pstmt.setTimestamp(1, Timestamp.valueOf(dateFormat.format(z.getBookingDate())));
            pstmt.setString(2, z.getPartnerName());
            pstmt.setString(3, z.getPartnerIban());
            pstmt.setString(4, z.getPartnerBic());
            pstmt.setString(5, z.getPartnerAccountNumber());
            pstmt.setString(6, z.getPartnerBankCode());
            pstmt.setDouble(7, Double.parseDouble(z.getAmount()));
            pstmt.setString(8, z.getCurrency());
            pstmt.setString(9, z.getBookingText());
            pstmt.setString(10, z.getInitialRecognitionReference());
            pstmt.setString(11, z.getNote());
            pstmt.setTimestamp(12, Timestamp.valueOf(dateFormat.format(z.getValueDate())));

            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("Fehler beim Einfügen in die Tabelle zahlung" + e.getMessage());
        }
    }

    // soll NUR unter gewissen umständen aufgerufen werden
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
