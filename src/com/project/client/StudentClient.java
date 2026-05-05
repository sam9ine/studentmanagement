package com.project.client;

import com.project.model.Student;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.*;
import java.net.*;

public class StudentClient extends JFrame {
    private JTextField tfName, tfRoll, tfCourse, tfSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentClient() {
        setTitle("Student Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // --- TOP PANEL (Fixed Space with GridBagLayout) ---
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createTitledBorder("Student Details")));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Name and Roll No
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; topPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        tfName = new JTextField(); topPanel.add(tfName, gbc);

        gbc.gridx = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; topPanel.add(new JLabel("Roll No:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        tfRoll = new JTextField(); topPanel.add(tfRoll, gbc);

        // Row 1: Course and Search
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; topPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        tfCourse = new JTextField(); topPanel.add(tfCourse, gbc);

        gbc.gridx = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; topPanel.add(new JLabel("Search (Roll No):"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        tfSearch = new JTextField(); topPanel.add(tfSearch, gbc);

        // Row 2: Search Button
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        JButton btnSearch = new JButton("Search");
        btnSearch.setPreferredSize(new Dimension(120, 30));
        topPanel.add(btnSearch, gbc);

        add(topPanel, BorderLayout.NORTH);

        // --- CENTER PANEL (Table) ---
        String[] columns = {"ID", "Name", "Roll No", "Course"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- BOTTOM PANEL (Buttons Fix) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
        bottomPanel.setBorder(new EmptyBorder(0, 0, 35, 0)); // Bottom padding for visibility

        JButton btnAdd = createStyledButton("Add Student", new Color(70, 130, 180));
        JButton btnUpdate = createStyledButton("Update Student", new Color(60, 179, 113));
        JButton btnDelete = createStyledButton("Delete Student", new Color(220, 20, 60));

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setPreferredSize(new Dimension(110, 38));

        JButton btnClear = new JButton("Clear Fields");
        btnClear.setPreferredSize(new Dimension(130, 38));

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnUpdate);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnClear);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnAdd.addActionListener(e -> sendData());
        btnClear.addActionListener(e -> {
            tfName.setText(""); tfRoll.setText(""); tfCourse.setText(""); tfSearch.setText("");
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(145, 38));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        return btn;
    }

    private void sendData() {
        String name = tfName.getText().trim();
        String roll = tfRoll.getText().trim();
        String course = tfCourse.getText().trim();

        if (name.isEmpty() || roll.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields!");
            return;
        }

        Student student = new Student(name, course, roll);

        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.writeObject(student);
            String response = in.readLine();
            JOptionPane.showMessageDialog(this, "Server Response: " + response);

            if (response != null && response.contains("Success")) {
                tableModel.addRow(new Object[]{"New", name, roll, course});
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new StudentClient();
    }
}