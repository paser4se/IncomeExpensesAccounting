package at.htl.iea.database;

import at.htl.iea.model.Payment;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    private final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db";
    private final String USER = "app";
    private final String PASSWORD = "app";
    private Connection conn;
    private static Database instance;

    private Database() {
        initJdbc();
    }

    public static Database getInstance() {
        if (Database.instance == null) {
            Database.instance = new Database();
        }
        return Database.instance;
    }

    public void initJdbc(){
        try {
            Class.forName(DRIVER_STRING);
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Verbindung zur Datenbank nicht möglich\n" + e.getMessage() + "\n");
            System.exit(1);
        }

        try {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "PAYMENT", null);

            if (! tables.next()) {
                Statement stmt = conn.createStatement();
                String sql = "CREATE TABLE Payment ("
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
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabelle Payment " + e.getMessage());
        }
    }
    
    // nur die essenziellen infos werden zurückgeschickt
    public List<Payment> getAllPayments(){
        ResultSet allPayments;
        List<Payment> payments = new ArrayList<>();
        try {
            Statement stat = conn.createStatement();
            allPayments = stat.executeQuery("select bookingDate, amount, currency, bookingText, valueDate from Payment");
            
            while (allPayments.next()){
                Payment tmpPayment = new Payment();
                tmpPayment.setBookingDate(allPayments.getTimestamp("bookingDate").toLocalDateTime());
                tmpPayment.setAmount(allPayments.getDouble("amount"));
                tmpPayment.setCurrency(allPayments.getString("currency"));
                tmpPayment.setBookingText(allPayments.getString("bookingText"));
                tmpPayment.setValueDate(allPayments.getTimestamp("valueDate").toLocalDateTime());
                
                payments.add(tmpPayment);
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Senden der essenziellen Informationen der Zahlungen (getAllPayments in Database.java): " + e.getMessage());
        }
        return payments;
    }

    public void insertIntoDatabase(Payment payment){
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Payment " +
                    "(bookingDate, partnerName, partnerIban, partnerBic, partnerAccountNumber, partnerBankCode, " +
                    "amount, currency, bookingText, initialRecognitionReference, note, valueDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            if (payment.getBookingDate() != null)
                pstmt.setTimestamp(1, Timestamp.valueOf(payment.getBookingDate()));
            pstmt.setString(2, payment.getPartnerName());
            pstmt.setString(3, payment.getPartnerIban());
            pstmt.setString(4, payment.getPartnerBic());
            pstmt.setString(5, payment.getPartnerAccountNumber());
            pstmt.setString(6, payment.getPartnerBankCode());
            pstmt.setDouble(7, payment.getAmount());
            pstmt.setString(8, payment.getCurrency());
            pstmt.setString(9, payment.getBookingText());
            pstmt.setString(10, payment.getInitialRecognitionReference());
            pstmt.setString(11, payment.getNote());
            if (payment.getValueDate() != null)
                pstmt.setTimestamp(12, Timestamp.valueOf(payment.getValueDate()));

            pstmt.execute();
        } catch (SQLException e) {
            System.err.println("Fehler beim Einfügen in die Tabelle Payment: " + e.getMessage());
        }
    }
}
