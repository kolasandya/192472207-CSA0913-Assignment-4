package Task05_CurrencyConverter;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Converter extends JFrame {
    private final JTextField amountField = new JTextField(10);
    private final JComboBox<String> targetBox = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel resultLabel = new JLabel("");

    private static double rate(String currency) {
        return currency.equals("USD") ? 0.012 : 0.011;
    }

    public Converter() {
        super("Currency Converter (Lambda Style)");
        setLayout(new FlowLayout());

        JButton convertButton = new JButton("Convert");
        add(new JLabel("INR"));
        add(amountField);
        add(targetBox);
        add(convertButton);
        add(resultLabel);

        // TODO 1 Completed: Register ONE lambda listener for both Convert button and Enter key
        ActionListener convertAction = e -> convert();
        convertButton.addActionListener(convertAction);
        amountField.addActionListener(convertAction); // Triggers on Enter key

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void convert() {
        // TODO 2 Completed: Parse amount and show converted value with 2 decimals, or "Invalid amount"
        try {
            double amt = Double.parseDouble(amountField.getText().trim());
            if (amt < 0) throw new NumberFormatException();
            String target = (String) targetBox.getSelectedItem();
            double converted = amt * rate(target);
            String output = String.format("%.2f %s", converted, target);
            resultLabel.setText(output);
            JOptionPane.showMessageDialog(this, "Converted Result: " + output, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Converter().setVisible(true));
    }
}
