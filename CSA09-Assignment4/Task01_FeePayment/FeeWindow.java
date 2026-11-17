package Task01_FeePayment;

import java.awt.*;
import java.awt.event.*;

public class FeeWindow extends Frame {
    private final TextField regNoField = new TextField(15);
    private final TextField amountField = new TextField(15);
    private final Label statusLabel = new Label("Status: Ready");
    private final FeeService feeService = new FeeService();

    public FeeWindow() {
        super("Student Fee Payment Portal");
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new Label("Register Number:"));
        add(regNoField);
        add(new Label("Amount (INR):"));
        add(amountField);

        Button payButton = new Button("Pay");
        Button clearButton = new Button("Clear");
        add(payButton);
        add(clearButton);
        add(statusLabel);

        payButton.addActionListener(e -> {
            statusLabel.setText("Processing payment...");
            // Run off EDT to keep UI responsive
            new Thread(() -> {
                try {
                    feeService.processPayment(regNoField.getText(), amountField.getText());
                    EventQueue.invokeLater(() -> statusLabel.setText("SUCCESS: Payment Recorded"));
                } catch (Exception ex) {
                    EventQueue.invokeLater(() -> statusLabel.setText("Error: " + ex.getMessage()));
                }
            }).start();
        });

        clearButton.addActionListener(e -> {
            regNoField.setText("");
            amountField.setText("");
            statusLabel.setText("Status: Cleared");
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        setSize(350, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FeeWindow();
    }
}
