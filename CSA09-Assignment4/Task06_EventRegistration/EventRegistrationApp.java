package Task06_EventRegistration;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EventRegistrationApp extends JFrame {
    private JTextField regNoField, nameField;
    private JComboBox<String> eventComboBox;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    // Database connection parameters (update as needed)
    private static final String URL = "jdbc:mysql://localhost:3306/campus_events";
    private static final String USER = "root";
    private static final String PWD = "password";

    public EventRegistrationApp() {
        super("Campus Event Registration System");
        setLayout(new BorderLayout(10, 10));

        // Top Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("New Registration Form"));

        formPanel.add(new JLabel("Student Register No:"));
        regNoField = new JTextField(15);
        formPanel.add(regNoField);

        formPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField(15);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Select Event:"));
        eventComboBox = new JComboBox<>(new String[]{"Cultural Fest 2026", "Coding Hackathon", "Robo Wars", "AI Symposium"});
        formPanel.add(eventComboBox);

        JButton registerButton = new JButton("Register");
        JButton refreshButton = new JButton("Refresh Table");
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(registerButton);
        btnPanel.add(refreshButton);
        formPanel.add(btnPanel);

        add(formPanel, BorderLayout.NORTH);

        // Center Table Panel
        String[] columns = {"ID", "Register No", "Student Name", "Event Name", "Registration Date"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Status Bar
        statusLabel = new JLabel(" Status: Ready");
        add(statusLabel, BorderLayout.SOUTH);

        // Action Listeners
        registerButton.addActionListener(e -> registerStudent());
        refreshButton.addActionListener(e -> loadRegistrations());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        // Initial load (Mock or DB)
        loadRegistrations();
    }

    private void registerStudent() {
        String regNo = regNoField.getText().trim();
        String name = nameField.getText().trim();
        String event = (String) eventComboBox.getSelectedItem();

        if (regNo.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Run off EDT using SwingWorker or background thread to prevent UI freezing
        new SwingWorker<Boolean, Void>() {
            String errorMsg = null;
            @Override
            protected Boolean doInBackground() {
                try (Connection c = DriverManager.getConnection(URL, USER, PWD)) {
                    // 1. Check duplicate registration
                    String checkSql = "SELECT COUNT(*) FROM registrations WHERE reg_no = ? AND event_name = ?";
                    try (PreparedStatement pstmt = c.prepareStatement(checkSql)) {
                        pstmt.setString(1, regNo);
                        pstmt.setString(2, event);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next() && rs.getInt(1) > 0) {
                                errorMsg = "Student is already registered for this event!";
                                return false;
                            }
                        }
                    }

                    // 2. Insert registration
                    String insertSql = "INSERT INTO registrations (reg_no, student_name, event_name, reg_date) VALUES (?, ?, ?, NOW())";
                    try (PreparedStatement pstmt = c.prepareStatement(insertSql)) {
                        pstmt.setString(1, regNo);
                        pstmt.setString(2, name);
                        pstmt.setString(3, event);
                        pstmt.executeUpdate();
                    }
                    return true;
                } catch (SQLException ex) {
                    // Fallback to in-memory demonstration if MySQL is offline
                    errorMsg = "DB Connection failed (" + ex.getMessage() + "). Adding to local view.";
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        statusLabel.setText("SUCCESS: Registered " + name + " for " + event);
                        regNoField.setText("");
                        nameField.setText("");
                        loadRegistrations();
                    } else {
                        // If DB offline, add row locally for demonstration
                        if (errorMsg.contains("DB Connection")) {
                            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, regNo, name, event, "Just now"});
                            statusLabel.setText("Offline Mode: Registered locally.");
                        } else {
                            JOptionPane.showMessageDialog(EventRegistrationApp.this, errorMsg, "Duplicate Error", JOptionPane.ERROR_MESSAGE);
                            statusLabel.setText("Error: Registration rejected.");
                        }
                    }
                } catch (Exception ignored) {}
            }
        }.execute();
    }

    private void loadRegistrations() {
        tableModel.setRowCount(0);
        try (Connection c = DriverManager.getConnection(URL, USER, PWD);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM registrations ORDER BY id DESC LIMIT 100")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("reg_no"),
                    rs.getString("student_name"),
                    rs.getString("event_name"),
                    rs.getString("reg_date")
                });
            }
            statusLabel.setText("Loaded registrations from database.");
        } catch (Exception e) {
            // Load dummy rows if DB not running so UI is fully testable
            tableModel.addRow(new Object[]{1, "REG1001", "Alice Smith", "Cultural Fest 2026", "2026-03-01 10:00"});
            tableModel.addRow(new Object[]{2, "REG1002", "Bob Jones", "Coding Hackathon", "2026-03-01 11:15"});
            statusLabel.setText("Database offline. Displaying sample records.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EventRegistrationApp::new);
    }
}
