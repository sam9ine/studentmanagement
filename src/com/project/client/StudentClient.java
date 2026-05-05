package com.project.client;

import com.project.model.Student;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class StudentClient extends JFrame {
    private JTextField tfName, tfCourse, tfEmail;
    private JButton btnSubmit;

    public StudentClient() {
        setTitle("Student Management System");
        setSize(400, 300);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Name:"));
        tfName = new JTextField();
        add(tfName);

        add(new JLabel("Course:"));
        tfCourse = new JTextField();
        add(tfCourse);

        add(new JLabel("Email:"));
        tfEmail = new JTextField();
        add(tfEmail);

        btnSubmit = new JButton("Submit");
        add(btnSubmit);

        btnSubmit.addActionListener(e -> sendData());

        setVisible(true);
    }

    private void sendData() {
        String name = tfName.getText();
        String course = tfCourse.getText();
        String email = tfEmail.getText();

        Student student = new Student(name, course, email);

        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.writeObject(student);
            String response = in.readLine();
            JOptionPane.showMessageDialog(this, "Server says: " + response);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new StudentClient();
    }
}