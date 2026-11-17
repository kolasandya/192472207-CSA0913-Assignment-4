package Task01_FeePayment;

import java.sql.SQLException;

public class FeeService {
    private final FeeDAO feeDAO = new FeeDAO();

    public void processPayment(String regNo, String amountStr) throws Exception {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be empty.");
        }
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format. Please enter a valid number.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Simulate secure parameterized database update
        feeDAO.insertFeeRecord(regNo.trim(), amount);
    }
}
