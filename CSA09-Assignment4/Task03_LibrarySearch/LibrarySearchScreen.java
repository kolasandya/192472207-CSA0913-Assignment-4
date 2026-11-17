package Task03_LibrarySearch;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LibrarySearchScreen extends JFrame {
    public LibrarySearchScreen() {
        super("Campus Library Search");
        setLayout(new BorderLayout(10, 10));

        // 1. Title Bar (NORTH)
        JLabel titleLabel = new JLabel("Campus Library Catalog Search", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 2. Search Row Panel (Top of Center or NORTH sub-panel)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JTextField searchField = new JTextField(20);
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"All Categories", "Books", "Journals", "Theses", "E-Resources"});
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Keyword:"));
        searchPanel.add(searchField);
        searchPanel.add(categoryBox);
        searchPanel.add(searchButton);

        // 3. Results Area (CENTER) - JTable inside JScrollPane
        String[] columns = {"Accession No", "Book Title", "Author", "Category", "Status"};
        Object[][] data = {
            {"B1001", "Java Concurrency in Practice", "Brian Goetz", "Books", "Available"},
            {"B1002", "Effective Java", "Joshua Bloch", "Books", "Checked Out"},
            {"J2015", "IEEE Transactions on Software", "IEEE", "Journals", "Available"}
        };
        JTable resultsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(resultsTable);

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(searchPanel, BorderLayout.NORTH);
        centerContainer.add(scrollPane, BorderLayout.CENTER);
        add(centerContainer, BorderLayout.CENTER);

        // 4. Bottom Row: Prev, Next, Reserve + Status (SOUTH)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel navButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton prevButton = new JButton("<< Prev");
        JButton nextButton = new JButton("Next >>");
        JButton reserveButton = new JButton("Reserve Selected");
        navButtonsPanel.add(prevButton);
        navButtonsPanel.add(nextButton);
        navButtonsPanel.add(reserveButton);

        JLabel statusLabel = new JLabel(" Status: Ready. Showing 3 results.");
        bottomPanel.add(navButtonsPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibrarySearchScreen::new);
    }
}
