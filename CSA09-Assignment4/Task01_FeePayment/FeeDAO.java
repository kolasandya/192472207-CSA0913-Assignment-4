package Task01_FeePayment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeeDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/college_db";
    private static final String USER = "root";
    private static final String PWD = "password";

    public void insertFeeRecord(String regNo, double amount) throws SQLException {
        String sql = "INSERT INTO fees (reg_no, amount, payment_date) VALUES (?, ?, NOW())";
        try (Connection c = DriverManager.getConnection(URL, USER, PWD);
             PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, regNo);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
        }
    }
}
